package com.majazeh.risloo.Utils.Entities;

import com.mre.ligheh.API.APIEvents;
import com.mre.ligheh.API.APIRequest;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Model;
import com.mre.ligheh.Model.Res;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ExtendEvent extends APIEvents {

    public ExtendEvent(Response callback, Class aClass, okhttp3.OkHttpClient client, okhttp3.Request request) {
        super(callback, aClass, client, request);
        request(callback, aClass, client, request);
    }

    @Override
    public void onResponsed(Response callback, Object response, Class aClass) {
        try {
            if (response.getClass().getName().equals("okhttp3.Response")) {
                int code = ((okhttp3.Response) response).code();
                JSONObject object = new JSONObject(((okhttp3.Response) response).body().string());
                APIRequest.code = code;
                Res res = new Res(object, aClass);
                callback.onOK(res.Build());
            } else {
                callback.onOK(response);
            }

            Model.request = false;
        } catch (IOException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | JSONException e) {
            e.printStackTrace();
        }
    }

}