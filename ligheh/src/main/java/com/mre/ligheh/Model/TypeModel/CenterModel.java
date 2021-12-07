package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import com.mre.ligheh.Model.Madule.List;

import org.json.JSONException;
import org.json.JSONObject;

public class CenterModel extends TypeModel {
    private String centerId = "";
    private String centerStatus = "";
    private String centerType = "";
    private UserModel2 manager;
    private AcceptationModel acceptation;
    private JSONObject detail;
    private int created_at;
    private int updated_at;
    private List treasuries;

    public CenterModel(JSONObject jsonObject) throws JSONException {
        super(jsonObject);

        setCenterId(jsonObject.getString("id"));
        if (!jsonObject.isNull("manager"))
            setManager(new UserModel2(jsonObject.getJSONObject("manager")));
        if (!jsonObject.isNull("acceptation"))
            setAcceptation(new AcceptationModel(jsonObject.getJSONObject("acceptation")));
        if (!jsonObject.isNull("status"))
            setCenterStatus(jsonObject.getString("status"));
        if (!jsonObject.isNull("type"))
            setCenterType(jsonObject.getString("type"));
        if (!jsonObject.isNull("detail"))
            setDetail(jsonObject.getJSONObject("detail"));
        if (!jsonObject.isNull("created_at"))
            setCreated_at(jsonObject.getInt("created_at"));
        if (!jsonObject.isNull("updated_at"))
            setUpdated_at(jsonObject.getInt("updated_at"));

        if (!jsonObject.isNull("treasuries")) {
            List treasuries = new List();
            for (int i = 0; i < jsonObject.getJSONArray("treasuries").length(); i++) {
                treasuries.add(new TreasuriesModel(jsonObject.getJSONArray("treasuries").getJSONObject(i)));
            }
            setTreasuries(treasuries);
        } else {
            setTreasuries(new List());
        }
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getCenterStatus() {
        return centerStatus;
    }

    public void setCenterStatus(String centerStatus) {
        this.centerStatus = centerStatus;
    }

    public String getCenterType() {
        return centerType;
    }

    public void setCenterType(String centerType) {
        this.centerType = centerType;
    }

    public UserModel2 getManager() {
        return manager;
    }

    public void setManager(UserModel2 manager) {
        this.manager = manager;
    }

    public AcceptationModel getAcceptation() {
        return acceptation;
    }

    public void setAcceptation(AcceptationModel acceptation) {
        this.acceptation = acceptation;
    }

    public JSONObject getDetail() {
        return detail;
    }

    public void setDetail(JSONObject detail) {
        this.detail = detail;
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

    public List getTreasuries() {
        return treasuries;
    }

    public void setTreasuries(List treasuries) {
        this.treasuries = treasuries;
    }

    public boolean compareTo(CenterModel model) {
        if (model != null) {
            if (!centerId.equals(model.getCenterId()))
                return false;

            if (!centerStatus.equals(model.getCenterStatus()))
                return false;

            if (!centerType.equals(model.getCenterType()))
                return false;

            if (manager != model.getManager())
                return false;

            if (acceptation != model.getAcceptation())
                return false;

            if (detail != model.getDetail())
                return false;

            if (created_at != model.getCreated_at())
                return false;

            if (updated_at != model.getUpdated_at())
                return false;

            if (treasuries != model.getTreasuries())
                return false;

            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject toObject() {
        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "CenterModel{" +
                "centerId='" + centerId + '\'' +
                ", centerStatus='" + centerStatus + '\'' +
                ", centerType='" + centerType + '\'' +
                ", manager=" + manager +
                ", acceptation=" + acceptation +
                ", detail=" + detail +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", treasuries=" + treasuries +
                '}';
    }

}