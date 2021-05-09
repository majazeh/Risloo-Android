package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.API.APIRequest;
import com.mre.ligheh.API.Response;
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

    public static void list(HashMap<String, Object> data, HashMap<String, Object> header, Response response) throws IOException {
        Model.list(endpoint, data, header, response, User.class);
    }

    public static void NoPersonalManagers(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.show(endpoint+ "?personal_clinic=no", data, header, response, UserModel.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void show(HashMap<String, Object> data, HashMap<String, Object> header, Response response) throws IOException {
        Model.show(endpoint, data, header, response, User.class);
    }

    public static void create(HashMap<String, Object> data, HashMap<String, Object> header, Response response) throws IOException {
        Model.create(endpoint, data, header, response, User.class);
    }

    public static void delete(HashMap<String, Object> data, HashMap<String, Object> header, Response response) throws IOException {
        Model.delete(endpoint, data, header, response, User.class);
    }

    public static void register(HashMap<String, Object> data, HashMap<String, Object> header, Response response) throws IOException {
        APIRequest.post("register", setData(data), setHeader(header), response, User.class);
    }
}