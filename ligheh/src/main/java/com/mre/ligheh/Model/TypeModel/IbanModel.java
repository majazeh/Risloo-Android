package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class IbanModel extends TypeModel {
    private String id = "";
    private String iban = "";
    private String status = "";
    private String owner = "";
    private String notic = "";
    private BankModel bank;

    public IbanModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("iban"))
                setIban(jsonObject.getString("iban"));
            if (!jsonObject.isNull("status"))
                setStatus(jsonObject.getString("status"));
            if (!jsonObject.isNull("owner"))
                setOwner(jsonObject.getString("owner"));
            if (!jsonObject.isNull("notic"))
                setNotic(jsonObject.getString("notic"));

            if (!jsonObject.isNull("bank"))
                setBank(new BankModel(jsonObject.getJSONObject("bank")));
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

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getNotic() {
        return notic;
    }

    public void setNotic(String notic) {
        this.notic = notic;
    }

    public BankModel getBank() {
        return bank;
    }

    public void setBank(BankModel bank) {
        this.bank = bank;
    }

    public boolean compareTo(IbanModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (!iban.equals(model.getIban()))
                return false;

            if (!status.equals(model.getStatus()))
                return false;

            if (!owner.equals(model.getOwner()))
                return false;

            if (!notic.equals(model.getNotic()))
                return false;

            if (bank != model.getBank())
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
            super.toObject().put("iban", getIban());
            super.toObject().put("status", getStatus());
            super.toObject().put("owner", getOwner());
            super.toObject().put("notic", getNotic());
            super.toObject().put("bank", getBank().toObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "IbanModel{" +
                "id='" + id + '\'' +
                ", iban='" + iban + '\'' +
                ", status='" + status + '\'' +
                ", owner='" + owner + '\'' +
                ", notic='" + notic + '\'' +
                ", bank=" + bank +
                '}';
    }

}