package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class BillingModel extends TypeModel {
    private String id = "";
    private String title = "";
    private String description = "";
    private String type = "";
    private int amount;
    private int created_at;
    private JSONObject action;
    private TreasuriesModel creditor;
    private TreasuriesModel debtor;

    public BillingModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("title"))
                setTitle(jsonObject.getString("title"));
            if (!jsonObject.isNull("description"))
                setDescription(jsonObject.getString("description"));
            if (!jsonObject.isNull("type"))
                setType(jsonObject.getString("type"));

            if (!jsonObject.isNull("amount"))
                setAmount(jsonObject.getInt("amount"));
            if (!jsonObject.isNull("created_at"))
                setCreatedAt(jsonObject.getInt("created_at"));

            if (!jsonObject.isNull("action")) {
                if (jsonObject.get("action").getClass().getName().equals("org.json.JSONObject")) {
                    setAction(jsonObject.getJSONObject("action"));
                } else {
                    JSONObject object = new JSONObject();

                    object.put("method", jsonObject.getString("action"));
                    setAction(object);
                }
            }

            if (!jsonObject.isNull("creditor"))
                setCreditor(new TreasuriesModel(jsonObject.getJSONObject("creditor")));
            if (!jsonObject.isNull("debtor"))
                setDebtor(new TreasuriesModel(jsonObject.getJSONObject("debtor")));
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(int created_at) {
        this.created_at = created_at;
    }

    public JSONObject getAction() {
        return action;
    }

    public void setAction(JSONObject action) {
        this.action = action;
    }

    public TreasuriesModel getCreditor() {
        return creditor;
    }

    public void setCreditor(TreasuriesModel creditor) {
        this.creditor = creditor;
    }

    public TreasuriesModel getDebtor() {
        return debtor;
    }

    public void setDebtor(TreasuriesModel debtor) {
        this.debtor = debtor;
    }

    public boolean compareTo(BillingModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (!title.equals(model.getTitle()))
                return false;

            if (!description.equals(model.getDescription()))
                return false;

            if (!type.equals(model.getType()))
                return false;

            if (amount != model.getAmount())
                return false;

            if (created_at != model.getCreatedAt())
                return false;

            if (action != model.getAction())
                return false;

            if (creditor != model.getCreditor())
                return false;

            if (debtor != model.getDebtor())
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
            super.toObject().put("title", getTitle());
            super.toObject().put("description", getDescription());
            super.toObject().put("type", getType());
            super.toObject().put("amount", getAmount());
            super.toObject().put("created_at", getCreatedAt());
            super.toObject().put("action", getAction());
            super.toObject().put("creditor", getCreditor().toObject());
            super.toObject().put("debtor", getDebtor().toObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "BillingModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", created_at=" + created_at +
                ", action=" + action +
                ", creditor=" + creditor +
                ", debtor=" + debtor +
                '}';
    }

}