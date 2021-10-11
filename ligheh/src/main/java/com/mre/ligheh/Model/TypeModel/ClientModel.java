package com.mre.ligheh.Model.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ClientModel extends TypeModel{
    private String current;
    private String force;

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
}
