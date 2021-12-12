package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import com.mre.ligheh.Model.Madule.List;

import org.json.JSONException;
import org.json.JSONObject;

public class CenterModel extends TypeModel {
    private String id = "";
    private String status = "";
    private String type = "";
    private int created_at = 0;
    private int updated_at = 0;
    private UserModel manager;
    private AcceptationModel acceptation;
    private JSONObject detail;
    private List treasuries = new List();

    public CenterModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("status"))
                setStatus(jsonObject.getString("status"));
            if (!jsonObject.isNull("type"))
                setType(jsonObject.getString("type"));

            if (!jsonObject.isNull("created_at"))
                setCreatedAt(jsonObject.getInt("created_at"));
            if (!jsonObject.isNull("updated_at"))
                setUpdatedAt(jsonObject.getInt("updated_at"));

            if (!jsonObject.isNull("manager"))
                setManager(new UserModel(jsonObject.getJSONObject("manager")));
            if (!jsonObject.isNull("acceptation"))
                setAcceptation(new AcceptationModel(jsonObject.getJSONObject("acceptation")));

            if (!jsonObject.isNull("detail"))
                setDetail(jsonObject.getJSONObject("detail"));

            if (!jsonObject.isNull("treasuries") && jsonObject.getJSONArray("treasuries").length() != 0) {
                for (int i = 0; i < jsonObject.getJSONArray("treasuries").length(); i++)
                    treasuries.add(new TreasuriesModel(jsonObject.getJSONArray("treasuries").getJSONObject(i)));
            }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(int created_at) {
        this.created_at = created_at;
    }

    public int getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(int updated_at) {
        this.updated_at = updated_at;
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

    public List getTreasuries() {
        return treasuries;
    }

    public void setTreasuries(List treasuries) {
        this.treasuries = treasuries;
    }

    public boolean compareTo(CenterModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (!status.equals(model.getStatus()))
                return false;

            if (!type.equals(model.getType()))
                return false;

            if (created_at != model.getCreatedAt())
                return false;

            if (updated_at != model.getUpdatedAt())
                return false;

            if (manager != model.getManager())
                return false;

            if (acceptation != model.getAcceptation())
                return false;

            if (detail != model.getDetail())
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
        try {
            super.toObject().put("id", getId());
            super.toObject().put("status", getStatus());
            super.toObject().put("type", getType());
            super.toObject().put("created_at", getCreatedAt());
            super.toObject().put("updated_at", getUpdatedAt());
            super.toObject().put("manager", getManager().toObject());
            super.toObject().put("acceptation", getAcceptation().toObject());
            super.toObject().put("detail", getDetail());
            super.toObject().put("treasuries", getTreasuries().toObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "CenterModel{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", manager=" + manager +
                ", acceptation=" + acceptation +
                ", detail=" + detail +
                ", treasuries=" + treasuries +
                '}';
    }

}