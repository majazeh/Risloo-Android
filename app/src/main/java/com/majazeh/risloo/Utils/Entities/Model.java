package com.majazeh.risloo.Utils.Entities;

import org.json.JSONException;
import org.json.JSONObject;

public class Model extends Main {
    public static boolean request = false;

    public Model(JSONObject attributes) throws JSONException {
        super(attributes);
    }

}