package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class ClientModel extends TypeModel {
    private String current = "";
    private String force = "";

    public ClientModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("current"))
                setCurrent(jsonObject.getString("current"));
            if (!jsonObject.isNull("force"))
                setForce(jsonObject.getString("force"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getForce() {
        return force;
    }

    public void setForce(String force) {
        this.force = force;
    }

    public boolean compareTo(ClientModel model) {
        if (model != null) {
            if (!current.equals(model.getCurrent()))
                return false;

            if (!force.equals(model.getForce()))
                return false;

            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject toObject() {
        try {
            super.toObject().put("current", getCurrent());
            super.toObject().put("force", getForce());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "ClientModel{" +
                "current='" + current + '\'' +
                ", force='" + force + '\'' +
                '}';
    }

}