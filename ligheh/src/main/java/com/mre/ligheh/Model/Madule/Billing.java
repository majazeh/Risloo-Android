package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.TypeModel.BillingModel;
import com.mre.ligheh.Model.TypeModel.PaymentModel;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Billing extends Model {
    static String endpoint = "billings";
    public BillingModel model;


    public Billing(JSONObject jsonObject) {
        super.data = new BillingModel(jsonObject);
        model = (BillingModel) super.data;
    }

    public static void list(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.list(endpoint, data, header, response, BillingModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void settled(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.post(endpoint + "/" + data.get("id") + "/settled", data, header, response, PaymentModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void finall(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.post(endpoint + "/" + data.get("billingId") + "/final", data, header, response, PaymentModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void show(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.show(endpoint + "/" + data.get("id") + "/dashboard", data, header, response, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addBill(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.post(endpoint + "/" + data.get("id"), data, header, response, BillingModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
