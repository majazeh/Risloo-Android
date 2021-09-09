package com.mre.ligheh.Model.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

public class TreasuriesModel extends TypeModel {
    private String id = "";
    private String symbol = "";
    private String title = "";
    private boolean creditable;
    private boolean my_treasury;
    private int balance;
    private UserModel userModel;
    private CenterModel centerModel;

    public TreasuriesModel(JSONObject jsonObject) {
        super(jsonObject);
        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("symbol"))
                setSymbol(jsonObject.getString("symbol"));
            if (!jsonObject.isNull("title"))
                setTitle(jsonObject.getString("title"));
            if (!jsonObject.isNull("creditable"))
                setCreditable(jsonObject.getBoolean("creditable"));
            if (!jsonObject.isNull("my_treasury"))
                setMy_treasury(jsonObject.getBoolean("my_treasury"));
            if (!jsonObject.isNull("balance"))
                setBalance(jsonObject.getInt("balance"));
            if (!jsonObject.isNull("center"))
                setCenterModel(new CenterModel(jsonObject.getJSONObject("center")));
            if (!jsonObject.isNull("user"))
                setUserModel(new UserModel(jsonObject.getJSONObject("user")));
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCreditable() {
        return creditable;
    }

    public void setCreditable(boolean creditable) {
        this.creditable = creditable;
    }

    public boolean isMy_treasury() {
        return my_treasury;
    }

    public void setMy_treasury(boolean my_treasury) {
        this.my_treasury = my_treasury;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public CenterModel getCenterModel() {
        return centerModel;
    }

    public void setCenterModel(CenterModel centerModel) {
        this.centerModel = centerModel;
    }
}
