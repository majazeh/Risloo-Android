package com.majazeh.risloo.Utils.Entities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.ScheduleModel;
import com.mre.ligheh.Model.TypeModel.TreasuriesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Singleton {

    // Objects
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public Singleton(@NonNull Activity activity) {
        sharedPreferences = activity.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();
        editor.apply();
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

    public void update(UserModel userModel) {
        if (userModel != null)
            setUserModel(userModel);
    }

    public void regist(String mobile, String password) {
        if (mobile != null && password != null)
            setRegist(mobile, password);
    }

    public void logout() {
        editor.remove("token");
        editor.remove("authorization");
        editor.remove("usermodel");
        editor.apply();
    }

    // -------------------- Payment

    public void fillPayment(String key, ScheduleModel scheduleModel) {
        if (!key.equals(""))
            setPaymentAuthKey(key);

        if (scheduleModel != null)
            setPaymentAuthScheduleModel(scheduleModel);
    }

    public void clearPayment() {
        editor.remove("payment_auth_key");
        editor.remove("payment_auth_schedulemodel");
        editor.apply();
    }

    /*
    ---------- Setters ----------
    */

    private void setToken(String value) {
        editor.putString("token", value);
        editor.apply();
    }

    private void setAuthorization(String value) {
        editor.putString("authorization", value);
        editor.apply();
    }

    private void setUserModel(UserModel userModel) {
        editor.putString("usermodel", userModel.object.toString());
        editor.apply();
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

    // -------------------- Payment

    private void setPaymentAuthKey(String value) {
        editor.putString("payment_auth_key", value);
        editor.apply();
    }

    private void setPaymentAuthScheduleModel(ScheduleModel scheduleModel) {
        editor.putString("payment_auth_schedulemodel", scheduleModel.object.toString());
        editor.apply();
    }

    /*
    ---------- Getters ----------
    */

    public String getToken() {
        if (!sharedPreferences.getString("token", "").equals(""))
            return sharedPreferences.getString("token", "");

        return "";
    }

    public String getAuthorization() {
        if (!sharedPreferences.getString("authorization", "").equals(""))
            return sharedPreferences.getString("authorization", "");

        return "";
    }

    public UserModel getUserModel() {
        try {
            if (!sharedPreferences.getString("usermodel", "").equals("")) {
                JSONObject jsonObject = new JSONObject(sharedPreferences.getString("usermodel", ""));

                return new UserModel(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } return null;
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

    // -------------------- Payment

    public String getPaymentAuthKey() {
        if (!sharedPreferences.getString("payment_auth_key", "").equals(""))
            return sharedPreferences.getString("payment_auth_key", "");

        return "";
    }

    public ScheduleModel getPaymentAuthScheduleModel() {
        try {
            if (!sharedPreferences.getString("payment_auth_schedulemodel", "").equals("")) {
                JSONObject jsonObject = new JSONObject(sharedPreferences.getString("payment_auth_schedulemodel", ""));

                return new ScheduleModel(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } return null;
    }

    /*
    ---------- UserModel Properties ----------
    */

    public String getId() {
        if (getUserModel().getId() != null)
            return getUserModel().getId();

        return "";
    }

    public String getName() {
        if (getUserModel().getName() != null)
            return getUserModel().getName();

        return "";
    }

    public String getAvatar() {
        if (getUserModel().getAvatar() != null && getUserModel().getAvatar().getMedium() != null && getUserModel().getAvatar().getMedium().getUrl() != null && !getUserModel().getAvatar().getMedium().getUrl().equals(""))
            return getUserModel().getAvatar().getMedium().getUrl();

        return "";
    }

    public String getMoney() {
        int total = 0;

        if (getUserModel().getTreasuries() != null) {
            for (TypeModel typeModel : getUserModel().getTreasuries().data()) {
                TreasuriesModel model = (TreasuriesModel) typeModel;

                if (model.getSymbol().equals("wallet") || model.getSymbol().equals("gift"))
                    total += model.getBalance();
            }
        }

        return String.valueOf(total);
    }

}