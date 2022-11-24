package com.Hilt.Module;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ActivityContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(ActivityComponent.class)
public class HiltAppMadule {

    @Provides
    ConnectivityManager ProvideConnectivityManager(@ActivityContext Context context){
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    NetworkRequest ProvideNetworkeRequest(){
        return new NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build();
    }
}
