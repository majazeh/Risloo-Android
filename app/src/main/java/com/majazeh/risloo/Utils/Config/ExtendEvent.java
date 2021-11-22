package com.majazeh.risloo.Utils.Config;

import com.majazeh.risloo.Utils.Managers.DialogManager;

import com.mre.ligheh.API.APIEvents;
import com.mre.ligheh.API.APIRequest;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Model;
import com.mre.ligheh.Model.Res;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class ExtendEvent extends APIEvents {

    @SuppressWarnings("rawtypes")
    public ExtendEvent(Response callback, Class classs, okhttp3.OkHttpClient client, okhttp3.Request request) {
        super(callback, classs, client, request);
        request(callback, classs, client, request);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void onOK(Response callback, Object response, Class classs) {
        try {
            if (response.getClass().getName().equals("okhttp3.Response")) {
                int code = ((okhttp3.Response) response).code();
                JSONObject object = new JSONObject(Objects.requireNonNull(((okhttp3.Response) response).body()).string());
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
        cancel(() -> ExtendException.activity.runOnUiThread(DialogManager::dismissLoadingDialog));
    }

}