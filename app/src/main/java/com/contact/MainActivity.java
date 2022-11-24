package com.contact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.PopupMenu;

import com.Models.AllContactModel;
import com.Models.DataItem;
import com.ViewModel.AppViewModel;
import com.example.contact.R;
import com.example.contact.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    ConnectivityManager connectivityManager;

    @Inject
    NetworkRequest networkRequest;

    ActivityMainBinding binding;

    NavHostFragment navHostFragment;
    NavController navController;

    AppViewModel appViewModel;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this , R.layout.activity_main);

        setUpNavigationComponent();

    }

    private void setUpNavigationComponent() {

            navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            navController = navHostFragment.getNavController();

            compositeDisposable = new CompositeDisposable();


            setUpSmoothBottonMenu();
            setupAppViewModel();
            CheckConnection();


    }

    private void CheckConnection() {
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback(){
            @Override
            public void onAvailable(@androidx.annotation.NonNull Network network) {
                CallListApiRequest();
               // Log.e("TAG" , "onAvailable: ");

            }

            @Override
            public void onLost(@androidx.annotation.NonNull Network network) {
                Log.e("TAG" , "onLost: ");
                Snackbar.make(binding.mainCon , "Internet connection lost" , 4000).show();
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        }else{
            connectivityManager.registerNetworkCallback(networkRequest , networkCallback);
        }
    }

    private void CallListApiRequest() {
        Observable.interval(15 , TimeUnit.SECONDS)
                .flatMap(n-> appViewModel.ContactFutureCall().get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AllContactModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull AllContactModel allContactModel) {
                        //Log.e("Tag" , "onNext: " + allContactModel.getResults().get(0).getPicture());
                        appViewModel.insertAllContact(allContactModel);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("Tag" , "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                     //   Log.e("Tag" , "onNext: ");
                    }
                });
    }

    private void setupAppViewModel() {
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
    }

    private void setUpSmoothBottonMenu() {
        PopupMenu popupMenu = new PopupMenu(this , null);
        popupMenu.inflate(R.menu.bottom_navigation_menu);
        Menu menu = popupMenu.getMenu();

        binding.bottomNavigation.setupWithNavController(menu , navController);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}