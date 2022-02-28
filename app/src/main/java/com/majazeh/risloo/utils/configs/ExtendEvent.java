package com.majazeh.risloo.utils.configs;

import com.majazeh.risloo.utils.managers.DialogManager;
import com.mre.ligheh.API.APIEvents;
import com.mre.ligheh.API.APIRequest;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Model;
import com.mre.ligheh.Model.Res;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ExtendEvent extends APIEvents {

    public ExtendEvent(Response callback, Class classs, OkHttpClient client, Request request) {
        super(callback, classs, client, request);
        request(callback, classs, client, request);
    }

    @Override
    public void onOK(Response callback, Object response, Class classs) {
        try {
            if (response.getClass().getName().equals("okhttp3.Response")) {
                int code = ((okhttp3.Response) response).code();
                String body = ((okhttp3.Response) response).body().string();
                JSONObject object = new JSONObject(body);
                APIRequest.code = code;
                Res res = new Res(object, classs);
                callback.onOK(res.Build());
            } else {
                callback.onOK(response);
            }

            Model.request = false;
        } catch (IOException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | JSONException e) {
            e.printStackTrace();
        }
    }

    public static void cancelRequest() {
        cancel(() -> ExtendException.activity.runOnUiThread(DialogManager::dismissDialogLoading));
    }

}