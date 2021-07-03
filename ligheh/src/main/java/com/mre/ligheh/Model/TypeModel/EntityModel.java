package com.mre.ligheh.Model.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

public class EntityModel extends TypeModel {
    private int offset;
    private String title;
    private String description;
    private int position;

    public EntityModel(JSONObject jsonObject) {
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
