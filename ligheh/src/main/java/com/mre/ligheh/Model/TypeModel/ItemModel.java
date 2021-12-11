package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemModel extends TypeModel {
    private String type = "";
    private String image_url = "";
    private String text = "";
    private ItemAnswer answer;
    private String category = "";
    private String description = "";
    private String user_answered = "";
    private String index = "";

    public ItemModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("type"))
                setType(jsonObject.getString("type"));
            if (!jsonObject.isNull("image_url"))
                setImageUrl(jsonObject.getString("image_url"));
            if (!jsonObject.isNull("text"))
                setText(jsonObject.getString("text"));
            if (!jsonObject.isNull("answer"))
                setAnswer(new ItemAnswer(jsonObject.getJSONObject("answer")));
            if (!jsonObject.isNull("user_answered"))
                setUserAnswered(jsonObject.getString("user_answered"));
            if (!jsonObject.isNull("category"))
                setCategory(jsonObject.getString("category"));
            if (!jsonObject.isNull("description"))
                setDescription(jsonObject.getString("description"));
            if (!jsonObject.isNull("index"))
                setIndex(String.valueOf(jsonObject.getInt("index")));
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

    public String getImageUrl() {
        return image_url;
    }

    public void setImageUrl(String image_url) {
        this.image_url = image_url + ".png";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ItemAnswer getAnswer() {
        return answer;
    }

    public void setAnswer(ItemAnswer answer) {
        this.answer = answer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserAnswered() {
        return user_answered;
    }

    public void setUserAnswered(String user_answered) {
        this.user_answered = user_answered;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public boolean compareTo(ItemModel model) {
        if (model != null) {
            if (!type.equals(model.getType()))
                return false;

            if (!image_url.equals(model.getImageUrl()))
                return false;

            if (!text.equals(model.getText()))
                return false;

            if (answer != model.getAnswer())
                return false;

            if (!category.equals(model.getCategory()))
                return false;

            if (!description.equals(model.getDescription()))
                return false;

            if (!user_answered.equals(model.getUserAnswered()))
                return false;

            if (!index.equals(model.getIndex()))
                return false;

            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject toObject() {
        try {
            super.toObject().put("type", getType());
            super.toObject().put("image_url", getImageUrl());
            super.toObject().put("text", getText());
            super.toObject().put("answer", getAnswer());
            super.toObject().put("category", getCategory());
            super.toObject().put("description", getDescription());
            super.toObject().put("user_answered", getUserAnswered());
            super.toObject().put("index", getIndex());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "ItemModel{" +
                "type='" + type + '\'' +
                ", image_url='" + image_url + '\'' +
                ", text='" + text + '\'' +
                ", answer=" + answer +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", user_answered='" + user_answered + '\'' +
                ", index='" + index + '\'' +
                '}';
    }

}