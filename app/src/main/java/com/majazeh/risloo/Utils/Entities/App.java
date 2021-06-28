package com.majazeh.risloo.Utils.Entities;

import android.content.res.Configuration;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

import com.majazeh.risloo.BuildConfig;
import com.mre.ligheh.API.APIRequest;
import com.mre.ligheh.API.Exceptioner;

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        APIRequest.ExternalAPIEvents = ExtendEvent.class;
        Exceptioner.External = ExtendOnFailureException.class;

        if (BuildConfig.BUILD_TYPE.equals("debug"))
            APIRequest.baseUrl = "https://bapi.risloo.ir/api/";
        else
            APIRequest.baseUrl = "https://api.risloo.ir/api/";
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}