package com.mre.ligheh.API;

import java.util.HashMap;

import okhttp3.Headers;

public class RequestHeader {
    private HashMap<String, Object> data = new HashMap<>();

    public void set(String key, String value) {
        if (value != null)
            data.put(key, value);
    }

    public void set(String key,Object value){
        data.put(key,value);
    }

    public HashMap<String,Object> get() {
        return data;
    }

    public Headers headersBody(){
        Headers headers;
        Headers.Builder builder= new Headers.Builder();
        for (String key : data.keySet()) {
            builder.add(key, (String) data.get(key));
        }
        headers = builder.build();
        return headers;
    }
}
