package com.Retrofit;

import com.Models.AllContactModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface RequestApi {


    @GET ("https://api.randomuser.me")
    Observable<AllContactModel> AllContactCall();

}
