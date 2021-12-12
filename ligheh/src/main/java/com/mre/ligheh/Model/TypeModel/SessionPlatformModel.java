package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class SessionPlatformModel extends TypeModel {
    private String id = "";
    private String type = "";
    private String title = "";
    private String identifier = "";
    private String identifier_type = "";
    private int selected_level = 0;
    private boolean selected = false;
    private boolean available = false;
    private boolean pin = false;
    private boolean selected_default = false;

    public SessionPlatformModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("type"))
                setType(jsonObject.getString("type"));
            if (!jsonObject.isNull("title"))
                setTitle(jsonObject.getString("title"));
            if (!jsonObject.isNull("identifier"))
                setIdentifier(jsonObject.getString("identifier"));
            if (!jsonObject.isNull("identifier_type"))
                setIdentifierType(jsonObject.getString("identifier_type"));

            if (!jsonObject.isNull("selected_level"))
                setSelectedLevel(jsonObject.getInt("selected_level"));

            if (!jsonObject.isNull("selected"))
                setSelected(jsonObject.getBoolean("selected"));
            if (!jsonObject.isNull("available"))
                setAvailable(jsonObject.getBoolean("available"));
            if (!jsonObject.isNull("pin"))
                setPin(jsonObject.getBoolean("pin"));
            if (!jsonObject.isNull("selected_default"))
                setSelectedDefault(jsonObject.getBoolean("selected_default"));
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifierType() {
        return identifier_type;
    }

    public void setIdentifierType(String identifier_type) {
        this.identifier_type = identifier_type;
    }

    public int getSelectedLevel() {
        return selected_level;
    }

    public void setSelectedLevel(int selected_level) {
        this.selected_level = selected_level;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isPin() {
        return pin;
    }

    public void setPin(boolean pin) {
        this.pin = pin;
    }

    public boolean isSelectedDefault() {
        return selected_default;
    }

    public void setSelectedDefault(boolean selected_default) {
        this.selected_default = selected_default;
    }

    public boolean compareTo(SessionPlatformModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (!type.equals(model.getType()))
                return false;

            if (!title.equals(model.getTitle()))
                return false;

            if (!identifier.equals(model.getIdentifier()))
                return false;

            if (!identifier_type.equals(model.getIdentifierType()))
                return false;

            if (selected_level != model.getSelectedLevel())
                return false;

            if (!isSelected())
                return false;

            if (!isAvailable())
                return false;

            if (!isPin())
                return false;

            if (!isSelectedDefault())
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
            super.toObject().put("type", getType());
            super.toObject().put("title", getTitle());
            super.toObject().put("identifier", getIdentifier());
            super.toObject().put("identifier_type", getIdentifierType());
            super.toObject().put("selected_level", getSelectedLevel());
            super.toObject().put("selected", isSelected());
            super.toObject().put("available", isAvailable());
            super.toObject().put("pin", isPin());
            super.toObject().put("selected_default", isSelectedDefault());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "SessionPlatformModel{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", identifier='" + identifier + '\'' +
                ", identifier_type='" + identifier_type + '\'' +
                ", selected_level=" + selected_level +
                ", selected=" + selected +
                ", available=" + available +
                ", pin=" + pin +
                ", selected_default=" + selected_default +
                '}';
    }

}