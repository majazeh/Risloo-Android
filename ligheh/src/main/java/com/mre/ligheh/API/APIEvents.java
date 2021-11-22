package com.mre.ligheh.API;

import com.mre.ligheh.Model.Madule.Model;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public abstract class APIEvents {
    public APIEvents(Response callback, Class aClass, OkHttpClient client, Request request) {

    }

    public APIEvents() {
    }

    public void request(Response callback, Class aClass, OkHttpClient client, Request request) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) {

                if (response.isSuccessful()) {
                    onOK(callback, response, aClass);
                } else {
                    Exceptioner.make(callback,response);
                    Model.request = false;
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Exceptioner.make(callback,e);
                Model.request = false;
            }

        });
    }

    public static void cancel(CancelRequest cancelRequest){
        for (Call call : APIRequest.client.dispatcher().runningCalls()) {
            System.out.println("cancle requests");
            call.cancel();
        }
            cancelRequest.onCanceled();
    }

    public abstract void onOK(Response callback, Object response, Class aClass);
}
