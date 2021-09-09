package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TransactionModel;
import com.mre.ligheh.Model.TypeModel.TreasuriesModel;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Treasury extends Model{
    static String endpoint = "treasuries";
    public TreasuriesModel model;

    public Treasury(JSONObject jsonObject) {
        super.data = new TreasuriesModel(jsonObject);
        model = (TreasuriesModel) super.data;
    }

    public static void list(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.list(endpoint, data, header, response, TreasuriesModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void transaction(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.show(endpoint + "/" + data.get("id") + "/dashboard", data, header, response, null);
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

    public static void create(HashMap<String, Object> data, HashMap<String, Object> header, Response response)  {
        try {
            Model.put(endpoint, data, header, response, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
