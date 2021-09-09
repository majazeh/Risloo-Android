package com.mre.ligheh.Model.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

public class TransactionModel extends TypeModel {
    private String id = "";
    private BillingModel billing;
    private String credit = "";
    private String debt = "";
    private String balance = "";
    private int created_at;

    public TransactionModel(JSONObject jsonObject) {
        super(jsonObject);
        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("billing"))
                setBilling(new BillingModel(jsonObject.getJSONObject("billing")));
            if (!jsonObject.isNull("credit"))
                setCredit(jsonObject.getString("credit"));
            if (!jsonObject.isNull("debt"))
                setDebt(jsonObject.getString("debt"));
            if (!jsonObject.isNull("balance"))
                setBalance(jsonObject.getString("balance"));
            if (!jsonObject.isNull("created_at"))
                setCreated_at(jsonObject.getInt("created_at"));
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

    public BillingModel getBilling() {
        return billing;
    }

    public void setBilling(BillingModel billing) {
        this.billing = billing;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }
}
