package com.mre.ligheh.Model.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

public class SessionPlatformModel extends TypeModel {
    private String id;
    private String type;
    private String title;
    private String identifier;
    private String identifier_type;
    private boolean selected;
    private boolean available;
    private boolean pin;
    private int selected_level;
    private boolean selected_default;

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
                setIdentifier_type(jsonObject.getString("identifier_type"));
            if (!jsonObject.isNull("selected"))
                setSelected(jsonObject.getBoolean("selected"));
            if (!jsonObject.isNull("available"))
                setAvailable(jsonObject.getBoolean("available"));
            if (!jsonObject.isNull("pin"))
                setPin(jsonObject.getBoolean("pin"));
            if (!jsonObject.isNull("selected_level"))
                setSelected_level(jsonObject.getInt("selected_level"));
            if (!jsonObject.isNull("selected_default"))
                setSelected_default(jsonObject.getBoolean("selected_default"));
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

    public String getIdentifier_type() {
        return identifier_type;
    }

    public void setIdentifier_type(String identifier_type) {
        this.identifier_type = identifier_type;
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

    public int getSelected_level() {
        return selected_level;
    }

    public void setSelected_level(int selected_level) {
        this.selected_level = selected_level;
    }

    public boolean isSelected_default() {
        return selected_default;
    }

    public void setSelected_default(boolean selected_default) {
        this.selected_default = selected_default;
    }
}
