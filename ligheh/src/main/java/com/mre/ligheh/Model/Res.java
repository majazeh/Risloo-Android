package com.mre.ligheh.Model;

import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;

public class Res {
    private final JSONObject data;
    private final Class aClass;

    public Res(JSONObject data, Class aClass) {
        this.data = data;
        this.aClass = aClass;
    }

    public Object Build() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, JSONException {
        System.out.println(data);
        if (aClass != null) {
            if (data.has("data")) {
                JSONObject jsonObject;
                switch (data.get("data").getClass().getName()) {
                    case "org.json.JSONObject":
                        jsonObject = data.getJSONObject("data");
                        if (hasToken(data)) {
                            jsonObject.put("token", data.get("token"));
                        }
                        return aClass.getDeclaredConstructor(JSONObject.class).newInstance(jsonObject);
                    case "org.json.JSONArray":
                        JSONArray jsonArray = data.getJSONArray("data");
                        List list = new List();
                        list.setData(data);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            list.add((TypeModel) aClass.getDeclaredConstructor(JSONObject.class).newInstance(jsonObject));
                        }
                        return list;
                    default:
                        return data.get("data");
                }
            } else {
                return aClass.getDeclaredConstructor(JSONObject.class).newInstance(data);

            }
        } else
            return data;
    }

    public boolean hasToken(JSONObject jsonObject) {
        return jsonObject.has("token");
    }

}
