package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.API.Exceptioner;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.TypeModel.AuthModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Auth extends Model {
    static String endpoint = "auth";
    public AuthModel model;

    public Auth(JSONObject jsonObject) throws JSONException {
        super.data = new AuthModel(jsonObject);
        model = (AuthModel) super.data;
    }

    public static void auth(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.post(endpoint, data, header, response, AuthModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void auth_theory(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "key")) {
                String key = (String) data.get("key");
                data.remove("key");
                Model.post(endpoint + "/theory" + "/" + key, data, header, response, AuthModel.class);
            } else {
                Exceptioner.make(response,"کلید را وارد کنید");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void me(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(header, "Authorization")) {
                Model.get("me", data, header, response, AuthModel.class);
            } else {
                Exceptioner.make(response,"شما لاگین نیستید");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editProfile(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(header, "Authorization")) {
                Model.put("me", data, header, response, AuthModel.class);
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
                if (has(data, "id")) {
                    Model.put("users/" + data.get("id") + "/change-password", data, header, response, AuthModel.class);
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

    public static void register(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "mobile")) {
                Model.create("register?key=register", data, header, response, AuthModel.class);
            } else {
                Exceptioner.make(response,"شماره موبایل الزامی است");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void verification(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        if (has(data, "mobile")) {
            try {
                Model.post("verification", data, header, response, AuthModel.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Exceptioner.make(response,"شماره موبایل الزامی است");
        }
    }

    public static void recovery(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        if (has(data, "mobile")) {
            try {
                Model.post(endpoint+"/recovery?key=recovery", data, header, response, AuthModel.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Exceptioner.make(response,"شماره موبایل الزامی است");
        }
    }

    public static void logout(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        if (has(header, "Authorization")) {
            try {
                Model.post("logout", data, header, response, AuthModel.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Exceptioner.make(response,"شما لاگین نیستید!");
        }
    }
    public static void changeAvatar(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        if (has(header, "Authorization")) {
            if (has(data, "id")) {
                try {
                    Model.post("users/" + data.get("id") + "/avatar", data, header, response, AuthModel.class);
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
