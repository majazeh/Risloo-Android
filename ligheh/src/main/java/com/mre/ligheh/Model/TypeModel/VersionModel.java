package com.mre.ligheh.Model.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

public class VersionModel extends TypeModel {
    private ClientModel android;


    public VersionModel(JSONObject jsonObject) {
        super(jsonObject);
        try {
            jsonObject = jsonObject.getJSONObject("version");
            System.out.println(jsonObject);
            if (!jsonObject.isNull("android"))
                setAndroid(new ClientModel(jsonObject.getJSONObject("android")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ClientModel getAndroid() {
        return android;
    }

    public void setAndroid(ClientModel android) {
        this.android = android;
    }
}
