package com.majazeh.risloo.Utils.Config;


import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.mre.ligheh.API.CancelRequest;
import com.mre.ligheh.API.Response;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ExtendRequest extends ExtendEvent {
    public ExtendRequest(Response callback, Class classs, OkHttpClient client, Request request) {
        super(callback, classs, client, request);
    }

    @Override
    public void onOK(Response callback, Object response, Class classs) {
        super.onOK(callback, response, classs);
    }

    public static void cancelRequest() {
        cancel(() -> ExtendException.activity.runOnUiThread(DialogManager::dismissLoadingDialog));
    }
}
