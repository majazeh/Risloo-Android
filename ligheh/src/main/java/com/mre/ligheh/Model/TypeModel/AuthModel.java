package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthModel extends TypeModel {
    private String theory = "";
    private String key = "";
    private String callback = "";
    private String authorized_key = "";
    private String token = "";
    private UserModel user;

    public AuthModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("theory"))
                setTheory(jsonObject.getString("theory"));
            if (!jsonObject.isNull("key"))
                setKey(jsonObject.getString("key"));
            if (!jsonObject.isNull("callback"))
                setCallback(jsonObject.getString("callback"));
            if (!jsonObject.isNull("authorized_key"))
                setAuthorizedKey(jsonObject.getString("authorized_key"));
            if (!jsonObject.isNull("token"))
                setToken(jsonObject.getString("token"));

            if (jsonObject.isNull("key"))
                setUser(new UserModel(jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public String getAuthorizedKey() {
        return authorized_key;
    }

    public void setAuthorizedKey(String authorized_key) {
        this.authorized_key = authorized_key;
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

    public boolean compareTo(AuthModel model) {
        if (model != null) {
            if (!theory.equals(model.getTheory()))
                return false;

            if (!key.equals(model.getKey()))
                return false;

            if (!callback.equals(model.getCallback()))
                return false;

            if (!authorized_key.equals(model.getAuthorizedKey()))
                return false;

            if (!token.equals(model.getToken()))
                return false;

            if (user != model.getUser())
                return false;

            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject toObject() {
        try {
            super.toObject().put("theory", getTheory());
            super.toObject().put("key", getKey());
            super.toObject().put("callback", getCallback());
            super.toObject().put("authorized_key", getAuthorizedKey());
            super.toObject().put("token", getToken());
            super.toObject().put("user", getUser().toObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "AuthModel{" +
                "theory='" + theory + '\'' +
                ", key='" + key + '\'' +
                ", callback='" + callback + '\'' +
                ", authorized_key='" + authorized_key + '\'' +
                ", token='" + token + '\'' +
                ", user=" + user +
                '}';
    }

}