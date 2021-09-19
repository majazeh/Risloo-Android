package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;

public class AvatarModel extends TypeModel {
    private AvatarDetail large;
    private AvatarDetail medium;
    private AvatarDetail original;
    private AvatarDetail small;

    public AvatarModel(JSONArray jsonArray) throws JSONException {
        super(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            switch (jsonArray.getJSONObject(i).getString("mode")) {
                case "small":
                    setSmall(new AvatarDetail(jsonArray.getJSONObject(i)));
                    break;
                case "original":
                    setOriginal(new AvatarDetail(jsonArray.getJSONObject(i)));
                    break;
                case "medium":
                    setMedium(new AvatarDetail(jsonArray.getJSONObject(i)));
                    break;
                case "large":
                    setLarge(new AvatarDetail(jsonArray.getJSONObject(i)));
                    break;
            }

        }
    }

    public AvatarDetail getLarge() {
        return large;
    }

    public void setLarge(AvatarDetail large) {
        this.large = large;
    }

    public AvatarDetail getMedium() {
        return medium;
    }

    public void setMedium(AvatarDetail medium) {
        this.medium = medium;
    }

    public AvatarDetail getOriginal() {
        return original;
    }

    public void setOriginal(AvatarDetail original) {
        this.original = original;
    }

    public AvatarDetail getSmall() {
        return small;
    }

    public void setSmall(AvatarDetail small) {
        this.small = small;
    }






    @NonNull
    @Override
    public String toString() {
        return "large=" + large +
                ", medium=" + medium +
                ", original=" + original +
                ", small=" + small;
    }
}
