package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class TransactionModel extends TypeModel {
    private String id = "";
    private String credit = "";
    private String debt = "";
    private String balance = "";
    private int created_at = 0;
    private BillingModel billing;

    public TransactionModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("credit"))
                setCredit(jsonObject.getString("credit"));
            if (!jsonObject.isNull("debt"))
                setDebt(jsonObject.getString("debt"));
            if (!jsonObject.isNull("balance"))
                setBalance(jsonObject.getString("balance"));

            if (!jsonObject.isNull("created_at"))
                setCreatedAt(jsonObject.getInt("created_at"));

            if (!jsonObject.isNull("billing"))
                setBilling(new BillingModel(jsonObject.getJSONObject("billing")));
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

    public int getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(int created_at) {
        this.created_at = created_at;
    }

    public BillingModel getBilling() {
        return billing;
    }

    public void setBilling(BillingModel billing) {
        this.billing = billing;
    }

    public boolean compareTo(TransactionModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (!credit.equals(model.getCredit()))
                return false;

            if (!debt.equals(model.getDebt()))
                return false;

            if (!balance.equals(model.getBalance()))
                return false;

            if (created_at != model.getCreatedAt())
                return false;

            if (billing != model.getBilling())
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
            super.toObject().put("credit", getCredit());
            super.toObject().put("debt", getDebt());
            super.toObject().put("balance", getBalance());
            super.toObject().put("created_at", getCreatedAt());
            super.toObject().put("billing", getBilling().toObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "TransactionModel{" +
                "id='" + id + '\'' +
                ", credit='" + credit + '\'' +
                ", debt='" + debt + '\'' +
                ", balance='" + balance + '\'' +
                ", created_at=" + created_at +
                ", billing=" + billing +
                '}';
    }

}