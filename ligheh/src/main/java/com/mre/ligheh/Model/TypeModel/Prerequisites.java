package com.mre.ligheh.Model.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

public class Prerequisites extends TypeModel {
    private String type="";
    private String text="";
    private JSONObject answer;
    private String alias="";
    private String label="";
    private String force="";
    private String user_answered="";

    public Prerequisites(JSONObject jsonObject) {
        super(jsonObject);
        try {
            if (!jsonObject.isNull("type"))
                setType(jsonObject.getString("type"));
            if (!jsonObject.isNull("text"))
                setText(jsonObject.getString("text"));
            if (!jsonObject.isNull("answer"))
                setAnswer(jsonObject.getJSONObject("answer"));
            if (!jsonObject.isNull("alias"))
                setAlias(jsonObject.getString("alias"));
            if (!jsonObject.isNull("label"))
                setLabel(jsonObject.getString("label"));
            if (!jsonObject.isNull("force"))
                setForce(jsonObject.getString("force"));
            if (!jsonObject.isNull("user_answered"))
                setUser_answered(jsonObject.getString("user_answered"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public JSONObject getAnswer() {
        return answer;
    }

    public void setAnswer(JSONObject answer) {
        this.answer = answer;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getForce() {
        return force;
    }

    public void setForce(String force) {
        this.force = force;
    }

    public String getUser_answered() {
        return user_answered;
    }

    public void setUser_answered(String user_answered) {
        this.user_answered = user_answered;
    }

    @Override
    public String toString() {
        return "Prerequisites{" +
                "type='" + type + '\'' +
                ", text='" + text + '\'' +
                ", answer=" + answer +
                ", alias='" + alias + '\'' +
                ", label='" + label + '\'' +
                ", force='" + force + '\'' +
                ", user_answered='" + user_answered + '\'' +
                '}';
    }
}
