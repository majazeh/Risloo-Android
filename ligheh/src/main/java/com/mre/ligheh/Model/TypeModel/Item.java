package com.mre.ligheh.Model.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

public class Item {
    private String type="";
    private String image_url="";
    private ItemAnswer answer;
    private String user_answered="";

    public Item(JSONObject jsonObject) {
        try {
            if (!jsonObject.isNull("type"))
                setType(jsonObject.getString("type"));
            if (!jsonObject.isNull("image_url"))
                setImage_url(jsonObject.getString("image_url"));
            if (!jsonObject.isNull("answer"))
                setAnswer(new ItemAnswer(jsonObject.getJSONObject("answer")));
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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public ItemAnswer getAnswer() {
        return answer;
    }

    public void setAnswer(ItemAnswer answer) {
        this.answer = answer;
    }

    public String getUser_answered() {
        return user_answered;
    }

    public void setUser_answered(String user_answered) {
        this.user_answered = user_answered;
    }

    @Override
    public String toString() {
        return "Item{" +
                "type='" + type + '\'' +
                ", image_url='" + image_url + '\'' +
                ", answer=" + answer +
                ", user_answered='" + user_answered + '\'' +
                '}';
    }
}
