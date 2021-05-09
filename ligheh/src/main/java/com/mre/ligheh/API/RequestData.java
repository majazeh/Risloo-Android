package com.mre.ligheh.API;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RequestData {
    private Boolean hasFile = false;
    private HashMap<String, Object> data = new HashMap<>();

    public void set(String key, String value) {
        if (value != null)
            data.put(key, value);
    }

    public void set(String key, File value) {
        hasFile = true;
        data.put(key, value);
    }

    public void set(String key, Object value) {
        data.put(key, value);
    }

    public HashMap<String, Object> get() {
        return data;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public String body(String url) {
        String new_url;
        if (url.contains("?")) {
            new_url = url + '&';
        } else {
            new_url = url + '?';
        }
        ArrayList<String> qp = new ArrayList<>();
        for (String key : data.keySet()) {
            qp.add(key + "=" + data.get(key));
        }

        new_url += String.join("&", qp);
        return new_url;
    }

    public RequestBody body() {
        if (hasFile) {
            return multipartBody();
        }
        return formBody();
    }

    public RequestBody formBody() {
        return RequestBody.create( new JSONObject(data).toString(),MediaType.get("application/json; charset=utf-8"));
    }

    public RequestBody multipartBody() {
        RequestBody requestBody;
        MultipartBody.Builder multiPart = new MultipartBody.Builder();
        for (String key : data.keySet()) {
            if (data.get(key).getClass().getSimpleName().equals("File")) {
                File file = (File) data.get(key);
                multiPart.setType(MultipartBody.FORM);
                multiPart.addFormDataPart(key, key, RequestBody.create(file,MediaType.parse("image/png")));
            } else {
                multiPart.addFormDataPart(key, (String) data.get(key));
            }
        }
        requestBody = multiPart.build();
        return requestBody;
    }

}
