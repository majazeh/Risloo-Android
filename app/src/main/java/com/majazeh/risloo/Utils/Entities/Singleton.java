package com.majazeh.risloo.Utils.Entities;

import android.app.Activity;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class Singleton {

    // Objects
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Singleton(@NonNull Activity activity) {
        try {
            MasterKey masterKey = new MasterKey.Builder(activity, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            sharedPreferences = EncryptedSharedPreferences.create(
                    activity, "encrypted_shared_preferences", masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

            editor = sharedPreferences.edit();
            editor.apply();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    /*
    ---------- Voids ----------
    */

    public void login(AuthModel authModel) {
        if (authModel.getToken() != null)
            setToken(authModel.getToken());

        if (authModel.getToken() != null)
            setAuthorization("Bearer " + authModel.getToken());

        if (authModel.getUser() != null)
            setUserModel(authModel.getUser());
    }

    public void params(UserModel userModel) {
        if (userModel != null)
            setParams(userModel);
    }

    public void update(UserModel userModel) {
        if (userModel != null)
            setUserModel(userModel);
    }

    public void regist(String mobile, String password) {
        if (mobile != null && password != null)
            setRegist(mobile, password);
    }

    public void logout() {
        if (OtherUser.getInstance().getUserModel() != null) {
            OtherUser.getInstance().logout();
        }

        editor.remove("token");
        editor.remove("authorization");
        editor.remove("usermodel");
        editor.apply();
    }

    /*
    ---------- OtherUser ----------
    */

    public void loginOtherUser(AuthModel authModel) {
        OtherUser.getInstance().login(authModel);
    }

    public void logoutFromOtherUser() {
        OtherUser.getInstance().logout();
    }

    /*
    ---------- Setters ----------
    */

    private void setToken(String value) {
        if (!OtherUser.getInstance().getToken().equals("")) {
            OtherUser.getInstance().setToken(value);
        } else {
            editor.putString("token", value);
            editor.apply();
        }
    }

    private void setAuthorization(String value) {
        if (!OtherUser.getInstance().getAuthorization().equals("")) {
            OtherUser.getInstance().setAuthorization(value);
        } else {
            editor.putString("authorization", value);
            editor.apply();
        }
    }

    private void setParams(UserModel userModel) {
        if (OtherUser.getInstance().getUserModel() != null) {
            OtherUser.getInstance().setUserModel(userModel);
        } else {
            try {
                JSONObject jsonObject = getUserModel().object;

                jsonObject.put("name", userModel.getName());
                jsonObject.put("mobile", userModel.getMobile());
                jsonObject.put("email", userModel.getEmail());
                jsonObject.put("birthday", userModel.getBirthday());
                jsonObject.put("status", userModel.getUserStatus());
                jsonObject.put("type", userModel.getUserType());
                jsonObject.put("gender", userModel.getGender());

                JSONArray avatarArray = new JSONArray();

                if (userModel.getAvatar() != null) {
                    JSONObject small = userModel.getAvatar().getSmall().object;
                    JSONObject medium = userModel.getAvatar().getMedium().object;
                    JSONObject original = userModel.getAvatar().getOriginal().object;
                    JSONObject large = userModel.getAvatar().getLarge().object;

                    if (small != null)
                        avatarArray.put(small);

                    if (medium != null)
                        avatarArray.put(medium);

                    if (original != null)
                        avatarArray.put(original);

                    if (large != null)
                        avatarArray.put(large);
                }

                jsonObject.put("avatar", avatarArray);

                editor.putString("usermodel", jsonObject.toString());
                editor.apply();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUserModel(UserModel userModel) {
        if (OtherUser.getInstance().getUserModel() != null) {
            OtherUser.getInstance().setUserModel(userModel);
        } else {
            editor.putString("usermodel", userModel.object.toString());
            editor.apply();
        }
    }

    private void setRegist(String mobile, String password) {
        try {
            ArrayList<TypeModel> models = new ArrayList<>();

            if (!sharedPreferences.getString("regists", "").equals("")) {
                models = new Gson().fromJson(sharedPreferences.getString("regists", ""), new TypeToken<ArrayList<TypeModel>>() {}.getType());

                boolean updated = false;
                for (TypeModel model : models) {
                    if (model.object.getString("mobile").equals(mobile)) {
                        model.object.put("password", password);
                        updated = true;
                        break;
                    }
                }

                if (!updated) {
                    TypeModel user = new TypeModel(new JSONObject().put("mobile", mobile).put("password", password));
                    models.add(user);
                }
            } else {
                TypeModel user = new TypeModel(new JSONObject().put("mobile", mobile).put("password", password));
                models.add(user);
            }

            editor.putString("regists", new Gson().toJson(models));
            editor.apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
    ---------- Getters ----------
    */

    public String getTokenMain() {
        if (!sharedPreferences.getString("token", "").equals(""))
            return sharedPreferences.getString("token", "");

        return "";
    }

    public String getAuthorizationMain() {
        if (!sharedPreferences.getString("authorization", "").equals(""))
            return sharedPreferences.getString("authorization", "");

        return "";
    }

    public String getToken() {
        if (!OtherUser.getInstance().getToken().equals("")) {
            return OtherUser.getInstance().getToken();
        } else {
            if (!sharedPreferences.getString("token", "").equals(""))
                return sharedPreferences.getString("token", "");

            return "";
        }
    }

    public String getAuthorization() {
        if (!OtherUser.getInstance().getAuthorization().equals("")) {
            return OtherUser.getInstance().getAuthorization();
        } else {
            if (!sharedPreferences.getString("authorization", "").equals(""))
                return sharedPreferences.getString("authorization", "");

            return "";
        }
    }

    public UserModel getUserModel() {
        if (OtherUser.getInstance().getUserModel() != null) {
            return OtherUser.getInstance().getUserModel();
        } else {
            try {
                if (!sharedPreferences.getString("usermodel", "").equals("")) {
                    JSONObject jsonObject = new JSONObject(sharedPreferences.getString("usermodel", ""));

                    return new UserModel(jsonObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } return null;
        }
    }

    public String getRegistPassword(String mobile) {
        try {
            if (!sharedPreferences.getString("regists", "").equals("")) {
                ArrayList<TypeModel> models = new Gson().fromJson(sharedPreferences.getString("regists", ""), new TypeToken<ArrayList<TypeModel>>() {}.getType());

                for (TypeModel model : models) {
                    if (model.object.getString("mobile").equals(mobile))
                        return model.object.getString("password");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } return "";
    }

    public ArrayList<String> getRegistMobiles() {
        try {
            if (!sharedPreferences.getString("regists", "").equals("")) {
                ArrayList<TypeModel> models = new Gson().fromJson(sharedPreferences.getString("regists", ""), new TypeToken<ArrayList<TypeModel>>() {}.getType());
                ArrayList<String> mobiles = new ArrayList<>();

                for (TypeModel model : models)
                    mobiles.add(model.object.getString("mobile"));

                return mobiles;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } return new ArrayList<>();
    }

}