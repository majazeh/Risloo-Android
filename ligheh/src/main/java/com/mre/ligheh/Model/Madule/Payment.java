package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.PaymentModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Payment extends Model{
    static String endpoint = "payments";
    public AuthModel model;

    public Payment(JSONObject jsonObject) throws JSONException {
        super.data = new AuthModel(jsonObject);
        model = (AuthModel) super.data;
    }

    public static void auth(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.post("auth", data, header, response, PaymentModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void list(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.list(endpoint, data, header, response, PaymentModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void post(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.post(endpoint, data, header, response, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
