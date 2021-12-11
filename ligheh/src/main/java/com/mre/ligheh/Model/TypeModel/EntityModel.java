package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class EntityModel extends TypeModel {
    private int offset;
    private int position;
    private String title = "";
    private String description = "";

    public EntityModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("offset"))
                setOffset(jsonObject.getInt("offset"));
            if (!jsonObject.isNull("position"))
                setPosition(jsonObject.getInt("position"));

            if (!jsonObject.isNull("title"))
                setTitle(jsonObject.getString("title"));
            else
                setTitle("بخش " + getPosition());
            if (!jsonObject.isNull("description"))
                setDescription(jsonObject.getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean compareTo(EntityModel model) {
        if (model != null) {
            if (offset != model.getOffset())
                return false;

            if (position != model.getPosition())
                return false;

            if (!title.equals(model.getTitle()))
                return false;

            if (!description.equals(model.getDescription()))
                return false;

            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject toObject() {
        try {
            super.toObject().put("offset", getOffset());
            super.toObject().put("position", getPosition());
            super.toObject().put("title", getTitle());
            super.toObject().put("description", getDescription());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "EntityModel{" +
                "offset=" + offset +
                ", position=" + position +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}