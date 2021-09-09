package com.mre.ligheh.Model.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

public class BillingItemModel extends BillingModel{
    private String type = "";
    private String actionString = "";


    public BillingItemModel(JSONObject jsonObject) {
        super(jsonObject);
            try {
        if (!jsonObject.isNull("type"))
                setType(jsonObject.getString("type"));
                if (!jsonObject.isNull("action"))
                    setActionString(jsonObject.getString("action"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    public String getActionString() {
        return actionString;
    }

    public void setActionString(String actionString) {
        this.actionString = actionString;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
