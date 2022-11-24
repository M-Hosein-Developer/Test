package com.Hilt.Module;

import com.RoomDb.RoomDao;
import com.example.AppRepository;
import com.Retrofit.RequestApi;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class HiltNetworkModule {

    String BaseUrl = "https://api.randomuser.me/";

    @Provides
    @Singleton
    OkHttpClient ProvideOkHttpClient(){
        return new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("m-hosein-h" , "8DEK-XAQJ-VEBD-74VO")
                        .build();
                return chain.proceed(request);
            }
        }).build();
    }

    @Provides
    @Singleton
    Retrofit ProvidRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    RequestApi ProvideRequstApi(Retrofit retrofit){
        return retrofit.create(RequestApi.class);
    }

    @Provides
    @Singleton
    AppRepository ProvideAppRepository(RequestApi requestApi , RoomDao roomDao){
        return new AppRepository(requestApi , roomDao);
    }
}
