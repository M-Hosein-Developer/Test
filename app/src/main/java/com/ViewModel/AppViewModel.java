package com.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.RoomDb.Entites.ContactListEntity;
import com.example.AppRepository;
import com.Models.AllContactModel;

import java.util.ArrayList;
import java.util.concurrent.Future;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

@HiltViewModel
public class AppViewModel extends AndroidViewModel {
    MutableLiveData<ArrayList<Integer>> mutableLiveData = new MutableLiveData<>();

    @Inject
    AppRepository appRepository;

    @Inject
    public AppViewModel(@NonNull Application application) {
        super(application);

    }
    public Future<Observable<AllContactModel>> ContactFutureCall(){
        return (Future<Observable<AllContactModel>>) appRepository.makeListFuturCall();
    }


    public MutableLiveData<ArrayList<Integer>> getMutableLiveData() {
        return mutableLiveData;
    }


    public void insertAllContact(AllContactModel allContactModel){
        appRepository.InsertAllContatc(allContactModel);
    }

    public Flowable<ContactListEntity> getAllContactData(){
        return appRepository.getAllContactData();
    }

}
