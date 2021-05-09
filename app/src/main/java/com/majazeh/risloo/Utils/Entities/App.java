package com.majazeh.risloo.Utils.Entities;

import android.content.res.Configuration;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

import com.mre.ligheh.API.APIRequest;
import com.mre.ligheh.API.Exceptioner;

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        APIRequest.ExternalAPIEvents = ExtendEvent.class;
        Exceptioner.External = ExtendOnFailureException.class;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}