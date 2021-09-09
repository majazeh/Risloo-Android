package com.mre.ligheh.Model.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentModel extends TypeModel {
    private String id = "";
    private String redirect = "";
    private TreasuriesModel treasury;
    private int amount;
    private String status = "";
    private String title = "";
    private String authorized_key = "";
    private int expires_at ;
    private int created_at ;
    private int updated_at ;

    public PaymentModel(JSONObject jsonObject) {
        super(jsonObject);
        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("redirect"))
                setRedirect(jsonObject.getString("redirect"));
            if (!jsonObject.isNull("treasury"))
                setTreasury(new TreasuriesModel(jsonObject.getJSONObject("treasury")));
            if (!jsonObject.isNull("amount"))
                setAmount(jsonObject.getInt("amount"));
            if (!jsonObject.isNull("status"))
                setStatus(jsonObject.getString("status"));
            if (!jsonObject.isNull("title"))
                setTitle(jsonObject.getString("title"));
            if (!jsonObject.isNull("authorized_key"))
                setAuthorized_key(jsonObject.getString("authorized_key"));
            if (!jsonObject.isNull("expires_at"))
                setExpires_at(jsonObject.getInt("expires_at"));
            if (!jsonObject.isNull("created_at"))
                setCreated_at(jsonObject.getInt("created_at"));
            if (!jsonObject.isNull("updated_at"))
                setUpdated_at(jsonObject.getInt("updated_at"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TreasuriesModel getTreasury() {
        return treasury;
    }

    public void setTreasury(TreasuriesModel treasury) {
        this.treasury = treasury;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorized_key() {
        return authorized_key;
    }

    public void setAuthorized_key(String authorized_key) {
        this.authorized_key = authorized_key;
    }

    public int getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(int expires_at) {
        this.expires_at = expires_at;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }
}
