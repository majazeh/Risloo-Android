package com.mre.ligheh.API;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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


    public String body(String url) {
        String new_url;
        if (url.contains("?")) {
            new_url = url + '&';
        } else {
            new_url = url + '?';
        }
        ArrayList<String> qp = new ArrayList<>();
        for (String key : data.keySet()) {
            qp.add(key + "=" + data.get(key) + "&");
        }
        for (int i = 0; i < qp.size(); i++) {
         new_url += qp.get(i);
        }
        return new_url.substring(0, new_url.length()-1);
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
        Iterator<String> iterator = data.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            if (data.get(key).getClass().getSimpleName().equals("File")) {
                File file = (File) data.get(key);
                multiPart.setType(MultipartBody.FORM);
                multiPart.addFormDataPart(key, key, RequestBody.create(file,MediaType.parse("image/png")));
                iterator.remove();
            } else {
//                multiPart.addFormDataPart(key, (String) data.get(key));
            }
                multiPart.addPart(RequestBody.create(new JSONObject(data).toString(),MediaType.get("application/json; charset=utf-8")));
        }
        requestBody = multiPart.build();
        return requestBody;
    }

}
