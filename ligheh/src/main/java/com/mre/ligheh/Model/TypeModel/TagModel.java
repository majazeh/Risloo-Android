package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class TagModel extends TypeModel {
    private String id = "";
    private String title = "";
    private int order;

    public TagModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("title"))
                setTitle(jsonObject.getString("title"));
            if (!jsonObject.isNull("order"))
                setOrder(jsonObject.getInt("order"));
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean compareTo(TagModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (!title.equals(model.getTitle()))
                return false;

            if (order != model.getOrder())
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
            super.toObject().put("title", getTitle());
            super.toObject().put("order", getOrder());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "TagModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", order=" + order +
                '}';
    }

}