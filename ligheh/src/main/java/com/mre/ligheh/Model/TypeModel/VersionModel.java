package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class VersionModel extends TypeModel {
    private ClientModel android;

    public VersionModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("version")) {
                if (!jsonObject.getJSONObject("version").isNull("android"))
                    setAndroid(new ClientModel(jsonObject.getJSONObject("version").getJSONObject("android")));
            }
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

    public boolean compareTo(VersionModel model) {
        if (model != null) {
            if (android != model.getAndroid())
                return false;

            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject toObject() {
        try {
            super.toObject().put("android", getAndroid().toObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "VersionModel{" +
                "android=" + android +
                '}';
    }

}