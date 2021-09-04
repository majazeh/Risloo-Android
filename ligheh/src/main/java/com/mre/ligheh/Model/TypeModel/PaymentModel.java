package com.mre.ligheh.Model.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentModel extends AuthModel{
    private String redirect = "";
    public PaymentModel(JSONObject jsonObject) throws JSONException {
        super(jsonObject);
        if (!jsonObject.isNull("redirect")){
            setRedirect(jsonObject.getString("redirect"));
        }
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
}
