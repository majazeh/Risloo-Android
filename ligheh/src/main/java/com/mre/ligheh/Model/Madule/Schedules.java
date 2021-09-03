package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.API.Exceptioner;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.TypeModel.ScheduleModel;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Schedules extends Model {
    static String endpoint = "schedules";
    public ScheduleModel model;

    public Schedules(JSONObject jsonObject) {
        super.data = new ScheduleModel(jsonObject);
        model = (ScheduleModel) super.data;
    }

    public static void roomList(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id"))
                Model.show( "rooms/" + data.get("id") + "/schedules", data, header, response, ScheduleModel.class);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void centerList(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id"))
                Model.show( "centers/" + data.get("id") + "/schedules", data, header, response, ScheduleModel.class);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void show(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.show(endpoint + "/" + data.get("id"), data, header, response, ScheduleModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void booking(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            if (has(data, "id"))
                Model.post(endpoint + "/" + data.get("id") + "/booking", data, header, response, null);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
