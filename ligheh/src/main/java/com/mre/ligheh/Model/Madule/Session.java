package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.TypeModel.ReportModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Session extends Model {
    static String endpoint = "sessions";
    public SessionModel model;

    public Session(JSONObject jsonObject) {
        super.data = new SessionModel(jsonObject);
        model = (SessionModel) super.data;
    }


    public static void list(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.list(endpoint, data, header, response, SessionModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void show(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.show(endpoint + "/" + data.get("id"), data, header, response, SessionModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showDashborad(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.show(endpoint + "/" + data.get("id") + "/dashboard", data, header, response, SessionModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showDashboradWithFillAccount(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.show(endpoint + "/" + data.get("id") + "/dashboard?fillAccounting=1", data, header, response, SessionModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void create(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.post("rooms/" + data.get("id") + "/schedules", data, header, response, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void edit(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.put(endpoint + "/" + data.get("id"), data, header, response, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editUser(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.put(endpoint + "/" + data.get("id") + "/users/" + data.get("userId"), data, header, response, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.post(endpoint + "/" + data.get("id") + "/users" , data, header, response, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reports(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.get(" client-reports/all/"+ data.get("id"), data, header, response, SessionModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
