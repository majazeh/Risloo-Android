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
                    onResponsed(callback, response, aClass);
                } else {
                    Exceptioner.make(response);
                    Model.request = false;
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Exceptioner.make(e);
                Model.request = false;
            }

        });
    }

    public abstract void onResponsed(Response callback, Object response, Class aClass);
}
