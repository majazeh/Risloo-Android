package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import com.mre.ligheh.Model.Madule.User;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthModel extends TypeModel {
    private String theory;
    private String key;
    private String callback;
    private String token;
    private UserModel user;
    private String authorized_key;

    public AuthModel(JSONObject jsonObject) throws JSONException {
        super(jsonObject);
        if (!jsonObject.isNull("theory"))
            setTheory(jsonObject.getString("theory"));
        if (!jsonObject.isNull("key"))
            setKey(jsonObject.getString("key"));
        if (!jsonObject.isNull("callback"))
            setCallback(jsonObject.getString("callback"));
        if (!jsonObject.isNull("authorized_key"))
            setAuthorized_key(jsonObject.getString("authorized_key"));
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

    public String getAuthorized_key() {
        return authorized_key;
    }

    public void setAuthorized_key(String authorized_key) {
        this.authorized_key = authorized_key;
    }

    @Override
    public JSONObject toObject() {
        try {
            super.toObject().put("theory", getTheory());
            super.toObject().put("key", getKey());
            super.toObject().put("callback", getCallback());
            super.toObject().put("token", getToken());
            super.toObject().put("authorized_key", getAuthorized_key());
            super.toObject().put("user", getUser().toObject());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "theory='" + theory + '\'' +
                ", key='" + key + '\'' +
                ", callback='" + callback + '\'' +
                ", token='" + token + '\'' +
                ", user=" + user;
    }
}
