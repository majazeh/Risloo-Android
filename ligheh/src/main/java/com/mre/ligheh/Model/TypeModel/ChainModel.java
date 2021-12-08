package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class ChainModel extends TypeModel {
    private String id = "";
    private String title = "";
    private String status = "";

    public ChainModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("title"))
                setTitle(jsonObject.getString("title"));
            if (!jsonObject.isNull("status"))
                setStatus(jsonObject.getString("status"));
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean compareTo(ChainModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (!title.equals(model.getTitle()))
                return false;

            if (!status.equals(model.getStatus()))
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
        return "ChainModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}