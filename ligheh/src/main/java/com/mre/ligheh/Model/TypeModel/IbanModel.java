package com.mre.ligheh.Model.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

public class IbanModel extends TypeModel {
    private String id = "";
    private String iban = "";
    private BankModel bank = new BankModel(new JSONObject());
    private String status = "";
    private String owner = "";
    private String notic = "";

    public IbanModel(JSONObject jsonObject) {
        super(jsonObject);
        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("iban"))
                setIban(jsonObject.getString("iban"));
            if (!jsonObject.isNull("bank"))
                setBank(new BankModel(jsonObject.getJSONObject("bank")));
            if (!jsonObject.isNull("status"))
                setStatus(jsonObject.getString("status"));
            if (!jsonObject.isNull("owner"))
                setOwner(jsonObject.getString("owner"));
            if (!jsonObject.isNull("notic"))
                setNotic(jsonObject.getString("notic"));
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

    public BankModel getBank() {
        return bank;
    }

    public void setBank(BankModel bank) {
        this.bank = bank;
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
}
