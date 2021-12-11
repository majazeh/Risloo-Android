package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class TreasuriesModel extends TypeModel {
    private String id = "";
    private String symbol = "";
    private String title = "";
    private int balance;
    private boolean creditable;
    private boolean my_treasury;
    private UserModel user;
    private CenterModel center;

    public TreasuriesModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("symbol"))
                setSymbol(jsonObject.getString("symbol"));
            if (!jsonObject.isNull("title"))
                setTitle(jsonObject.getString("title"));

            if (!jsonObject.isNull("balance"))
                setBalance(jsonObject.getInt("balance"));

            if (!jsonObject.isNull("creditable"))
                setCreditable(jsonObject.getBoolean("creditable"));
            if (!jsonObject.isNull("my_treasury"))
                setMyTreasury(jsonObject.getBoolean("my_treasury"));

            if (!jsonObject.isNull("user"))
                setUser(new UserModel(jsonObject.getJSONObject("user")));
            if (!jsonObject.isNull("center"))
                setCenter(new CenterModel(jsonObject.getJSONObject("center")));
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isCreditable() {
        return creditable;
    }

    public void setCreditable(boolean creditable) {
        this.creditable = creditable;
    }

    public boolean isMyTreasury() {
        return my_treasury;
    }

    public void setMyTreasury(boolean my_treasury) {
        this.my_treasury = my_treasury;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public CenterModel getCenter() {
        return center;
    }

    public void setCenter(CenterModel center) {
        this.center = center;
    }

    public boolean compareTo(TreasuriesModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (!symbol.equals(model.getSymbol()))
                return false;

            if (!title.equals(model.getTitle()))
                return false;

            if (balance != model.getBalance())
                return false;

            if (!creditable)
                return false;

            if (!my_treasury)
                return false;

            if (user != model.getUser())
                return false;

            if (center != model.getCenter())
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
            super.toObject().put("symbol", getSymbol());
            super.toObject().put("title", getTitle());
            super.toObject().put("balance", getBalance());
            super.toObject().put("creditable", isCreditable());
            super.toObject().put("my_treasury", isMyTreasury());
            super.toObject().put("user", getUser().toObject());
            super.toObject().put("center", getCenter().toObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "TreasuriesModel{" +
                "id='" + id + '\'' +
                ", symbol='" + symbol + '\'' +
                ", title='" + title + '\'' +
                ", balance=" + balance +
                ", creditable=" + creditable +
                ", my_treasury=" + my_treasury +
                ", user=" + user +
                ", center=" + center +
                '}';
    }

}