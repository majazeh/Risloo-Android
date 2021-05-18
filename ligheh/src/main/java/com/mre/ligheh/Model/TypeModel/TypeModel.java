package com.mre.ligheh.Model.TypeModel;

import org.json.JSONArray;
import org.json.JSONObject;

public class TypeModel {
    public JSONObject object;
    public JSONArray array;
    public TypeModel(JSONObject jsonObject) {
        this.object = jsonObject;
    }
    public TypeModel(JSONArray jsonArray) {
        this.array = jsonArray;
    }

    public TypeModel() {
    }
}
