package com.mre.ligheh.API;


public interface Response {
    void onOK(Object object);
    void onFailure(String response);
}
