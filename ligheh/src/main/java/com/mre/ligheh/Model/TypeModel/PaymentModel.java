package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentModel extends TypeModel {
    private String id = "";
    private String redirect = "";
    private String status = "";
    private String title = "";
    private String authorized_key = "";
    private int amount;
    private int expires_at;
    private int created_at;
    private int updated_at;
    private TreasuriesModel treasury;

    public PaymentModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("redirect"))
                setRedirect(jsonObject.getString("redirect"));
            if (!jsonObject.isNull("status"))
                setStatus(jsonObject.getString("status"));
            if (!jsonObject.isNull("title"))
                setTitle(jsonObject.getString("title"));
            if (!jsonObject.isNull("authorized_key"))
                setAuthorizedKey(jsonObject.getString("authorized_key"));

            if (!jsonObject.isNull("amount"))
                setAmount(jsonObject.getInt("amount"));
            if (!jsonObject.isNull("expires_at"))
                setExpiresAt(jsonObject.getInt("expires_at"));
            if (!jsonObject.isNull("created_at"))
                setCreatedAt(jsonObject.getInt("created_at"));
            if (!jsonObject.isNull("updated_at"))
                setUpdatedAt(jsonObject.getInt("updated_at"));

            if (!jsonObject.isNull("treasury"))
                setTreasury(new TreasuriesModel(jsonObject.getJSONObject("treasury")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
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

    public String getAuthorizedKey() {
        return authorized_key;
    }

    public void setAuthorizedKey(String authorized_key) {
        this.authorized_key = authorized_key;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getExpiresAt() {
        return expires_at;
    }

    public void setExpiresAt(int expires_at) {
        this.expires_at = expires_at;
    }

    public int getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(int created_at) {
        this.created_at = created_at;
    }

    public int getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(int updated_at) {
        this.updated_at = updated_at;
    }

    public TreasuriesModel getTreasury() {
        return treasury;
    }

    public void setTreasury(TreasuriesModel treasury) {
        this.treasury = treasury;
    }

    public boolean compareTo(PaymentModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (!redirect.equals(model.getRedirect()))
                return false;

            if (!status.equals(model.getStatus()))
                return false;

            if (!title.equals(model.getTitle()))
                return false;

            if (!authorized_key.equals(model.getAuthorizedKey()))
                return false;

            if (amount != model.getAmount())
                return false;

            if (expires_at != model.getExpiresAt())
                return false;

            if (created_at != model.getCreatedAt())
                return false;

            if (updated_at != model.getUpdatedAt())
                return false;

            if (treasury != model.getTreasury())
                return false;

            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject toObject() {
        try {
            super.toObject().put("id", getId());
            super.toObject().put("redirect", getRedirect());
            super.toObject().put("status", getStatus());
            super.toObject().put("title", getTitle());
            super.toObject().put("authorized_key", getAuthorizedKey());
            super.toObject().put("amount", getAmount());
            super.toObject().put("expires_at", getExpiresAt());
            super.toObject().put("created_at", getCreatedAt());
            super.toObject().put("updated_at", getUpdatedAt());
            super.toObject().put("treasury", getTreasury().toObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "PaymentModel{" +
                "id='" + id + '\'' +
                ", redirect='" + redirect + '\'' +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", authorized_key='" + authorized_key + '\'' +
                ", amount=" + amount +
                ", expires_at=" + expires_at +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", treasury=" + treasury +
                '}';
    }

}