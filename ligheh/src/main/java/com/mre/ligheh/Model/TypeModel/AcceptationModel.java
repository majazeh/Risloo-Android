package com.mre.ligheh.Model.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

public class AcceptationModel extends TypeModel {
    private String id = "";
    private String name="";
    private String position="";
    private int created_at;
    private int accepted_at;
    private String kicked_at="";

    public AcceptationModel(JSONObject jsonObject) {
        super(jsonObject);
        try {
            setId(jsonObject.getString("id"));
        if (!jsonObject.isNull("name"))
            setName((jsonObject.getString("name")));
        if (!jsonObject.isNull("position"))
            setPosition(jsonObject.getString("position"));
        if (!jsonObject.isNull("created_at"))
            setCreated_at(jsonObject.getInt("created_at"));
        if (!jsonObject.isNull("type"))
            setAccepted_at(jsonObject.getInt("accepted_at"));
        if (!jsonObject.isNull("kicked_at"))
            setKicked_at(jsonObject.getString("kicked_at"));
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

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getAccepted_at() {
        return accepted_at;
    }

    public void setAccepted_at(int accepted_at) {
        this.accepted_at = accepted_at;
    }

    public String getKicked_at() {
        return kicked_at;
    }

    public void setKicked_at(String kicked_at) {
        this.kicked_at = kicked_at;
    }
}
