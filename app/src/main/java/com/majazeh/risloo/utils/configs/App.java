package com.majazeh.risloo.utils.configs;

import android.app.Application;

import com.majazeh.risloo.BuildConfig;
import com.mre.ligheh.API.APIRequest;
import com.mre.ligheh.API.Exceptioner;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        APIRequest.ExternalAPIEvents = ExtendEvent.class;
        Exceptioner.External = ExtendException.class;

        if (BuildConfig.BUILD_TYPE.equals("debug"))
            APIRequest.baseUrl = "https://bapi.risloo.ir/api/";
        else
            APIRequest.baseUrl = "https://api.risloo.ir/api/";
    }

}