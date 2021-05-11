package com.mre.ligheh.API;


import okhttp3.ResponseBody;

public interface Response {
    void onOK(Object object);
    void onFailure(String response);
}
