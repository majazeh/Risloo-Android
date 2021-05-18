package com.mre.ligheh.Model.TypeModel;

import com.mre.ligheh.Model.Madule.User;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthModel extends TypeModel {
    private String theory;
    private String key;
    private String callback;
    private String token;
    private UserModel user;

    public AuthModel(JSONObject jsonObject) throws JSONException {
        super(jsonObject);
        if (!jsonObject.isNull("theory"))
            setTheory(jsonObject.getString("theory"));
        if (!jsonObject.isNull("key"))
            setKey(jsonObject.getString("key"));
        if (!jsonObject.isNull("callback"))
            setCallback(jsonObject.getString("callback"));
        if (!jsonObject.isNull("token"))
            setToken(jsonObject.getString("token"));
        if (jsonObject.isNull("key"))
            setUser(new UserModel(jsonObject));
    }

    public String getTheory() {
        return theory;
    }

    public void setTheory(String theory) {
        this.theory = theory;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "theory='" + theory + '\'' +
                ", key='" + key + '\'' +
                ", callback='" + callback + '\'' +
                ", token='" + token + '\'' +
                ", user=" + user;
    }
}
