package com.mre.ligheh.Model.Madule;


import com.mre.ligheh.API.Exceptioner;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Case extends Model {
    static String endpoint = "cases";
    public CaseModel model;

    public Case(JSONObject jsonObject) {
        super.data = new CaseModel(jsonObject);
        model = (CaseModel) super.data;
    }


    public static void list(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.list(endpoint, data, header, response, CaseModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void show(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.show(endpoint + "/" + data.get("id"), data, header, response, CaseModel.class);
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

    public static void addClient(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "id"))
                Model.post(endpoint + "/" + data.get("id") + "/users", data, header, response, null);
            else
                Exceptioner.make(response,"آیدی را وارد کنید!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
