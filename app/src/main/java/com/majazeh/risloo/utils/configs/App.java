package com.majazeh.risloo.Utils.configs;

import android.app.Application;

import com.majazeh.risloo.BuildConfig;
import com.mre.ligheh.API.APIRequest;
import com.mre.ligheh.API.Exceptioner;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        APIRequest.ExternalAPIEvents = com.majazeh.risloo.utils.configs.ExtendEvent.class;
        Exceptioner.External = com.majazeh.risloo.utils.configs.ExtendException.class;

        if (BuildConfig.BUILD_TYPE.equals("debug"))
            APIRequest.baseUrl = "https://dapi.risloo.ir/api/";
        else
            APIRequest.baseUrl = "https://api.risloo.ir/api/";
    }

}