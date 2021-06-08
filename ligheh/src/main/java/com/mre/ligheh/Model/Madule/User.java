package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.API.APIRequest;
import com.mre.ligheh.API.Exceptioner;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;


public class User extends Model {
    static String endpoint = "users";
    public UserModel model;

    public User(JSONObject jsonObject) throws JSONException {
        super.data = new UserModel(jsonObject);
        model = (UserModel) super.data;
    }

    public static void list(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.list(endpoint, data, header, response, UserModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void show(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.show(endpoint + "/" + data.get("id"), data, header, response, UserModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void dashboard(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.get(endpoint + "/" + data.get("user") + "/profile", data, header, response, UserModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void create(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.create(endpoint, data, header, response, UserModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.delete(endpoint, data, header, response, UserModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void register(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            APIRequest.post("register", setData(data), setHeader(header), response, UserModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editProfile(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(header, "Authorization")) {
                Model.put(endpoint +"/"+ data.get("user"), data, header, response, AuthModel.class);
            } else {
                Exceptioner.make(response,"شما لاگین نیستید");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editPassword(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(header, "Authorization")) {
                if (has(data, "user")) {
                    Model.put(endpoint + "/" + data.get("user") + "/change-password", data, header, response, null);
                } else {
                    Exceptioner.make(response,"آیدی را وارد کنید");
                }
            } else {
                Exceptioner.make(response,"شما لاگین نیستید");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeAvatar(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        if (has(header, "Authorization")) {
            if (has(data, "id")) {
                try {
                    Model.post(endpoint + "/" + data.get("id") + "/avatar", data, header, response, AuthModel.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Exceptioner.make(response,"آیدی را وارد کنید!");
            }
        } else {
            Exceptioner.make(response,"شما لاگین نیستید!");
        }
    }

}
