package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class AcceptationModel extends TypeModel {
    private String id = "";
    private String name = "";
    private String position = "";
    private String kicked_at = "";
    private int created_at;
    private int accepted_at;

    public AcceptationModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("id"))
                setId((jsonObject.getString("id")));
            if (!jsonObject.isNull("name"))
                setName((jsonObject.getString("name")));
            if (!jsonObject.isNull("position"))
                setPosition(jsonObject.getString("position"));
            if (!jsonObject.isNull("kicked_at"))
                setKickedAt(jsonObject.getString("kicked_at"));

            if (!jsonObject.isNull("created_at"))
                setCreatedAt(jsonObject.getInt("created_at"));
            if (!jsonObject.isNull("accepted_at"))
                setAcceptedAt(jsonObject.getInt("accepted_at"));
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getKickedAt() {
        return kicked_at;
    }

    public void setKickedAt(String kicked_at) {
        this.kicked_at = kicked_at;
    }

    public int getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(int created_at) {
        this.created_at = created_at;
    }

    public int getAcceptedAt() {
        return accepted_at;
    }

    public void setAcceptedAt(int accepted_at) {
        this.accepted_at = accepted_at;
    }

    public boolean compareTo(AcceptationModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (!name.equals(model.getName()))
                return false;

            if (!position.equals(model.getPosition()))
                return false;

            if (!kicked_at.equals(model.getKickedAt()))
                return false;

            if (created_at != model.getCreatedAt())
                return false;

            if (accepted_at != model.getAcceptedAt())
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
            super.toObject().put("name", getName());
            super.toObject().put("position", getPosition());
            super.toObject().put("kicked_at", getKickedAt());
            super.toObject().put("created_at", getCreatedAt());
            super.toObject().put("accepted_at", getAcceptedAt());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "AcceptationModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", kicked_at='" + kicked_at + '\'' +
                ", created_at=" + created_at +
                ", accepted_at=" + accepted_at +
                '}';
    }

}