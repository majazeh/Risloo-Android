package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class PrerequisitesModel extends TypeModel {
    private String index = "";
    private String type = "";
    private String text = "";
    private String alias = "";
    private String label = "";
    private String force = "";
    private String user_answered = "";
    private JSONObject answer = new JSONObject();

    public PrerequisitesModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("index"))
                setIndex(String.valueOf(jsonObject.getInt("index")));
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

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
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

    public JSONObject getAnswer() {
        return answer;
    }

    public void setAnswer(JSONObject answer) {
        this.answer = answer;
    }

    public boolean compareTo(PrerequisitesModel model) {
        if (model != null) {
            if (!index.equals(model.getIndex()))
                return false;

            if (!type.equals(model.getType()))
                return false;

            if (!text.equals(model.getText()))
                return false;

            if (!alias.equals(model.getAlias()))
                return false;

            if (!label.equals(model.getLabel()))
                return false;

            if (!force.equals(model.getForce()))
                return false;

            if (!user_answered.equals(model.getUser_answered()))
                return false;

            if (answer != model.getAnswer())
                return false;

            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject toObject() {
        try {
            super.toObject().put("index", getIndex());
            super.toObject().put("type", getType());
            super.toObject().put("text", getText());
            super.toObject().put("alias", getAlias());
            super.toObject().put("label", getLabel());
            super.toObject().put("force", getForce());
            super.toObject().put("user_answered", getUser_answered());
            super.toObject().put("answer", getAnswer());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "PrerequisitesModel{" +
                "index='" + index + '\'' +
                ", type='" + type + '\'' +
                ", text='" + text + '\'' +
                ", alias='" + alias + '\'' +
                ", label='" + label + '\'' +
                ", force='" + force + '\'' +
                ", user_answered='" + user_answered + '\'' +
                ", answer=" + answer +
                '}';
    }

}