package com.Fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Adapters.ContactRvAdapter;
import com.Models.AllContactModel;
import com.Models.DataItem;
import com.RoomDb.Entites.ContactListEntity;
import com.ViewModel.AppViewModel;
import com.example.contact.R;
import com.example.contact.databinding.FragmentContatcBinding;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Contatc extends Fragment {

    FragmentContatcBinding binding;

    AppViewModel appViewModel;
    CompositeDisposable compositeDisposable;

    List<DataItem> ContatRV;

    ContactRvAdapter contactRvAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_contatc , container , false);

        compositeDisposable = new CompositeDisposable();
        //ContatRV = new ArrayList<>();

        setupViewModel();
        getAllContatcDataFromDb();

        return binding.getRoot();
    }

    private void setupViewModel() {
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
    }

    private void getAllContatcDataFromDb() {
        Disposable disposable = appViewModel.getAllContactData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ContactListEntity>() {
                    @Override
                    public void accept(ContactListEntity contactListEntity) throws Throwable {
                        AllContactModel allContactModel = contactListEntity.getAllContactModel();
                        ContatRV = allContactModel.getResults();

                        LinearLayoutManager llm = new LinearLayoutManager(getContext());
                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                        binding.ContactListRv.setLayoutManager(llm);
                        if (binding.ContactListRv.getAdapter() == null){
                            contactRvAdapter = new ContactRvAdapter((ArrayList<DataItem>) ContatRV);
                            binding.ContactListRv.setAdapter(contactRvAdapter);
                        }else {
                            contactRvAdapter = (ContactRvAdapter) binding.ContactListRv.getAdapter();
                            contactRvAdapter.updataData((ArrayList<DataItem>) ContatRV);
                        }




                    }
                });
        compositeDisposable.add(disposable);

    }


}