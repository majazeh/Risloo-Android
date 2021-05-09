package com.mre.ligheh.Model.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;


public class CenterModel extends TypeModel {
    private String CenterId;
    private String centerStatus;
    private String centerType;
    private UserModel manager;
    private AcceptationModel acceptation;
    private JSONObject detail;
    private int created_at;
    private int updated_at;

    public CenterModel() {
        super();
    }

    public CenterModel(JSONObject jsonObject) throws JSONException {
        setCenterId(jsonObject.getString("id"));
        if (!jsonObject.isNull("manager"))
            setManager(new UserModel(jsonObject.getJSONObject("manager")));
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
    }

    public String getCenterId() {
        return CenterId;
    }

    public void setCenterId(String centerId) {
        CenterId = centerId;
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

    public UserModel getManager() {
        return manager;
    }

    public void setManager(UserModel manager) {
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

    @Override
    public String toString() {
        return "CenterModel{" +
                "CenterId='" + CenterId + '\'' +
                ", centerStatus='" + centerStatus + '\'' +
                ", centerType='" + centerType + '\'' +
                ", manager=" + manager +
                ", acceptation=" + acceptation +
                ", detail=" + detail +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}