package com.example;

import android.util.Log;

import com.Models.AllContactModel;
import com.Retrofit.RequestApi;
import com.RoomDb.Entites.ContactListEntity;
import com.RoomDb.RoomDao;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AppRepository {

    RequestApi requestApi;
    RoomDao roomDao;

    public AppRepository(RequestApi requestApi, RoomDao roomDao) {
        this.requestApi = requestApi;
        this.roomDao = roomDao;
    }

    public Future<Observable<AllContactModel>> makeListFuturCall(){
        final ExecutorService executor = Executors.newSingleThreadExecutor();

        final Callable<Observable<AllContactModel>> myNetworkCAllable = new Callable<Observable<AllContactModel>>() {
            @Override
            public Observable<AllContactModel> call() throws Exception {
                return requestApi.AllContactCall();
            }
        };

        final Future<Observable<AllContactModel>> futureObservable = new Future<Observable<AllContactModel>>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                if (mayInterruptIfRunning){
                    executor.shutdown();
                }
                return false;
            }

            @Override
            public boolean isCancelled() {
                return executor.isShutdown();
            }

            @Override
            public boolean isDone() {
                return executor.isTerminated();
            }

            @Override
            public Observable<AllContactModel> get() throws ExecutionException, InterruptedException {
                return executor.submit(myNetworkCAllable).get();
            }

            @Override
            public Observable<AllContactModel> get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
                return executor.submit(myNetworkCAllable).get(timeout , unit);
            }
        };
        return futureObservable;
    }

    public void InsertAllContatc(AllContactModel allContactModel){
        Completable.fromAction(() -> roomDao.insert(new ContactListEntity(allContactModel)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("InsertAllContact" , "onComplite: ok");
                    }

                    @Override
                    public void onComplete() {
                        Log.e("InsertAllContact" , "onComplite: ok");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("InsertAllContact" , "onEroor: " + e.getMessage());
                    }
                });
    }

    public Flowable<ContactListEntity> getAllContactData(){
        return roomDao.getAllContatcData();
    }

}
