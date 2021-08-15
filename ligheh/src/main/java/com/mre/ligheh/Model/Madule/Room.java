package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.API.Exceptioner;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.ScheduleModel;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TagModel;
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

    public static void list(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.list(endpoint, data, header, response, RoomModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void show(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.show(endpoint + "/" + data.get("id"), data, header, response, RoomModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void roomSessionPlatform(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id"))
                Model.show(endpoint + "/" + data.get("id") + "/settings/session-platforms", data, header, response, SessionPlatformModel.class);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void tags(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.show("rooms" + "/" + data.get("id") + "/settings/pinned-tags", data, header, response, TagModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void orderTags(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.put("rooms" + "/" + data.get("id") + "/settings/pinned-tags", data, header, response, TagModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editRoomSessionPlatform(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id"))
                Model.put(endpoint + "/" + data.get("id") + "/settings/session-platforms/" + data.get("platformId"), data, header, response, null);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createRoomSessionPlatform(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id"))
                Model.post(endpoint + "/" + data.get("id") + "/settings/session-platforms", data, header, response, null);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void schedule(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id"))
                Model.show(endpoint + "/" + data.get("id") + "/schedules", data, header, response, ScheduleModel.class);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createSchedule(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id"))
                Model.post(endpoint + "/" + data.get("id") + "/schedules", data, header, response, ScheduleModel.class);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void showDashboard(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.show(endpoint + "/" + data.get("id") + "/dashboard", data, header, response, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void request(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id"))
                Model.post("centers/" + data.get("id") + "/request", data, header, response, RoomModel.class);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void create(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id"))
                Model.create("centers/" + data.get("id") + "/rooms", data, header, response, RoomModel.class);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createCase(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "roomId"))
                Model.create(endpoint + "/" + data.get("roomId") + "/cases", data, header, response, CaseModel.class);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void users(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id"))
                Model.show(endpoint + "/" + data.get("id") + "/users", data, header, response, UserModel.class);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createUser(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id"))
                Model.post(endpoint + "/" + data.get("id") + "/users", data, header, response, null);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void user(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id") && has(data, "userId"))
                Model.show("centers/" + data.get("id") + "/users/" + data.get("userId") + "/profile", data, header, response, UserModel.class);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void edit(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.put(endpoint, data, header, response, RoomModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
