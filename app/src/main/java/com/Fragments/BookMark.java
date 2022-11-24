package com.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Adapters.BookMarkRvAdapter;
import com.Models.AllContactModel;
import com.Models.DataItem;
import com.RoomDb.Entites.ContactListEntity;
import com.ViewModel.AppViewModel;
import com.example.contact.R;
import com.example.contact.databinding.FragmentBookMarkBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class BookMark extends Fragment {

    FragmentBookMarkBinding fragmentBookMarkBinding;
    AppViewModel appViewModel;
    CompositeDisposable compositeDisposable;

    @Nullable
    List<DataItem> dataItemList;
    @Nullable
    ArrayList<String> bookmarksArray;
    @Nullable
    ArrayList<DataItem> finalData;


    BookMarkRvAdapter bookMarkRvAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentBookMarkBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_book_mark , container , false);
        finalData = new ArrayList<>();

        setupViewModel();
        getAllBookMarDataFromDb();

        ReadDataStore();
        getContactListDataFromDb();


        return fragmentBookMarkBinding.getRoot();
    }

    private void getContactListDataFromDb() {
        Disposable disposable = appViewModel.getAllContactData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ContactListEntity>() {
                    @Override
                    public void accept(ContactListEntity contactListEntity) throws Throwable {
                        AllContactModel allContactModel = contactListEntity.getAllContactModel();
                        dataItemList = allContactModel.getResults();


//                        finalData.clear();
//                        for (int i = 0;i < bookmarksArray.size();i++){
//                            for (int j = 0;j < dataItemList.size();j++){
//                                if (bookmarksArray.get(i).equals(dataItemList.get(j))){
//                                    finalData.add(dataItemList.get(j));
//                                }
//                            }
//                        }

                        if (fragmentBookMarkBinding.BookMarkListRv.getAdapter() != null) {
                            bookMarkRvAdapter = (BookMarkRvAdapter) fragmentBookMarkBinding.BookMarkListRv.getAdapter();
                            bookMarkRvAdapter.updataData(finalData);
                        } else {
                            bookMarkRvAdapter = new BookMarkRvAdapter(finalData);
                            fragmentBookMarkBinding.BookMarkListRv.setAdapter(bookMarkRvAdapter);
                        }

                    }
                });
    }

    private void ReadDataStore() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("bookmarks", String.valueOf(new ArrayList<String>()));
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        bookmarksArray = gson.fromJson(json, type);
        Log.e("TAG", "ReadDataStore: " + bookmarksArray);
    }

    private void setupViewModel() {
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
    }

    private void getAllBookMarDataFromDb() {

    }
}