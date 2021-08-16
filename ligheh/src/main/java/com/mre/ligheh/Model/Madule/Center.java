package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.API.Exceptioner;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.ReportModel;
import com.mre.ligheh.Model.TypeModel.ScheduleModel;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TagModel;
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
                Model.show(endpoint + "/" + data.get("id"), data, header, response, CenterModel.class);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void tags(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.show("rooms" + "/" + data.get("id") + "/settings/pinned-tags", data, header, response, TagModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void orderTags(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.put("rooms" + "/" + data.get("roomId") + "/settings/pinned-tags", data, header, response, TagModel.class);
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
            if (has(data, "id"))
                Model.show(endpoint + "/" + data.get("id") + "/dashboard", data, header, response, null);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void centerSessionPlatform(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id"))
                Model.show(endpoint + "/" + data.get("id") + "/settings/session-platforms", data, header, response, SessionPlatformModel.class);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editCenterSessionPlatform(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id"))
                Model.put(endpoint + "/" + data.get("id") + "/settings/session-platforms/" + data.get("platformId"), data, header, response, null);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createCenterSessionPlatform(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id"))
                Model.post(endpoint + "/" + data.get("id") + "/settings/session-platforms", data, header, response,null );
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

    public static void request(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id"))
                Model.post(endpoint + "/" + data.get("id") + "/request", data, header, response, CenterModel.class);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changePosition(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id") || has(data,"userId"))
                Model.put(endpoint + "/" + data.get("id") + "/users/" + data.get("userId"), data, header, response, UserModel.class);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeStatus(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id") || has(data,"userId"))
                Model.put(endpoint + "/" + data.get("id") + "/users/" + data.get("userId"), data, header, response, UserModel.class);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
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
                Exceptioner.make(response, "کلید را وارد کنید");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void user(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id") && has(data, "userId"))
                Model.show(endpoint + "/" + data.get("id") + "/users/" + data.get("userId") + "/profile", data, header, response, UserModel.class);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editUser(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id") && has(data, "userId"))
                Model.put(endpoint + "/" + data.get("id") + "/users/" + data.get("userId"), data, header, response, null);
            else
                Exceptioner.make(response, "آیدی را وارد کنید!");
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

    public static void editAvatar(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.post(endpoint + "/" + data.get("id")+ "/avatar", data, header, response, CenterModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}