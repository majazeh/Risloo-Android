package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.API.Exceptioner;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Room extends Model {
    static String endpoint = "rooms";
    public RoomModel model;

    public Room(JSONObject jsonObject) throws JSONException {
        super.data = new CenterModel(jsonObject);
        model = (RoomModel) super.data;
    }

    public static void list(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.list(endpoint, data, header, response, RoomModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void show(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.show(endpoint + "/" + data.get("id"), data, header, response, RoomModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showDashboard(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.show(endpoint + "/" + data.get("id")+ "/dashboard", data, header, response, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void create(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.create(endpoint, data, header, response, RoomModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createCase(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "roomId"))
                Model.create(endpoint + "/" + data.get("roomId") + "/cases", data, header, response, CaseModel.class);
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

    public static void edit(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.put(endpoint, data, header, response, RoomModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
