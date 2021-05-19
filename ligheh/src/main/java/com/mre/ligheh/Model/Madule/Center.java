package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.API.Exceptioner;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Center extends Model {
    static String endpoint = "centers";
    public CenterModel model;

    public Center(JSONObject jsonObject) throws JSONException {
        super.data = new CenterModel(jsonObject);
        model = (CenterModel) super.data;
    }

    public static void list(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.list(endpoint, data, header, response, CenterModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void show(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id"))
                Model.show(endpoint +"/"+ data.get("id"), data, header, response, CenterModel.class);
            else
                Exceptioner.make(response,"آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void users(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id"))
                Model.show(endpoint + "/" + data.get("id") + "/users", data, header, response, UserModel.class);
            else
                Exceptioner.make(response,"آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void createUser(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id"))
                Model.post(endpoint + "/" + data.get("id") + "/users", data, header, response, null);
            else
                Exceptioner.make(response,"آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void theory(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "key")) {
                String key = (String) data.get("key");
                data.remove("key");
                Model.post("auth" + "/theory" + "/" + key, data, header, response, null);
            } else {
                Exceptioner.make(response,"کلید را وارد کنید");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void user(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id") && has(data, "userId"))
                Model.show(endpoint + "/" + data.get("id") + "/users/" + data.get("userId"), data, header, response, UserModel.class);
            else
                Exceptioner.make(response,"آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editUser(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id") && has(data, "userId"))
                Model.put(endpoint + "/" + data.get("id") + "/users/" + data.get("userId"), data, header, response, null);
            else
                Exceptioner.make(response,"آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void create(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.create(endpoint, data, header, response, CenterModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void edit(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.put(endpoint + "/" + data.get("id"), data, header, response, CenterModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}