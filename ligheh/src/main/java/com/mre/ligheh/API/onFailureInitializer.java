package com.mre.ligheh.API;

import java.util.HashMap;

public class onFailureInitializer extends onFailureException {

    public onFailureInitializer(Response callback,Object object) {
        super(callback,object);
    }

    @Override
    public void onValidation(HashMap<String, Object> map) {
        log("onValidation: " + map);
    }

    @Override
    public void onClient(String message) {
        System.out.println(message);
    }

    @Override
    public void onServerFail(String message) {
        System.out.println(message);
    }


    public void log(Object object) {
        System.out.println(object);
    }

}
