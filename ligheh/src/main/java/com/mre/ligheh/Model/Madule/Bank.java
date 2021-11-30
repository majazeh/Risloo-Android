package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.TypeModel.IbanModel;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Bank extends Model {
    static String endpoint = "bank";
    public IbanModel model;

    public Bank(JSONObject jsonObject) {
        super.data = new IbanModel(jsonObject);
        model = (IbanModel) super.data;
    }

    public static void getIbans(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.list(endpoint, data, header, response, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void settleds(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.post(endpoint, data, header, response, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addIban(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.post(endpoint + "/" + data.get("id"), data, header, response, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
