package com.mre.ligheh.Model.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

public class BillingModel extends TypeModel {
    private String id = "";
    private TreasuriesModel creditor;
    private TreasuriesModel debtor;
    private int amount;
    private JSONObject action;
    private String title = "";
    private String description = "";
    private int created_at;
    private String type = "";

    public BillingModel(JSONObject jsonObject) {
        super(jsonObject);
        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("creditor"))
                setCreditor(new TreasuriesModel(jsonObject.getJSONObject("creditor")));
            if (!jsonObject.isNull("debtor"))
                setDebtor(new TreasuriesModel(jsonObject.getJSONObject("debtor")));
            if (!jsonObject.isNull("amount"))
                setAmount(jsonObject.getInt("amount"));
            if (!jsonObject.isNull("action")) {
                if (jsonObject.get("action").getClass().getName().equals("org.json.JSONObject")) {
                    setAction(jsonObject.getJSONObject("action"));
                } else {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("method", jsonObject.getString("action"));
                    setAction(jsonObject1);
                }
            }
            if (!jsonObject.isNull("description"))
                setDescription(jsonObject.getString("description"));
            if (!jsonObject.isNull("title"))
                setTitle(jsonObject.getString("title"));
            if (!jsonObject.isNull("created_at"))
                setCreated_at(jsonObject.getInt("created_at"));
            if (!jsonObject.isNull("type"))
                setType(jsonObject.getString("type"));
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public JSONObject getAction() {
        return action;
    }

    public void setAction(JSONObject action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
