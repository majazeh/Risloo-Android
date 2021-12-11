package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AvatarModel extends TypeModel {
    private ProfileModel large;
    private ProfileModel medium;
    private ProfileModel original;
    private ProfileModel small;

    public AvatarModel(JSONArray jsonArray) {
        super(jsonArray);

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                if (!jsonArray.getJSONObject(i).isNull("mode")) {
                    switch (jsonArray.getJSONObject(i).getString("mode")) {
                        case "large":
                            setLarge(new ProfileModel(jsonArray.getJSONObject(i)));
                            break;
                        case "medium":
                            setMedium(new ProfileModel(jsonArray.getJSONObject(i)));
                            break;
                        case "original":
                            setOriginal(new ProfileModel(jsonArray.getJSONObject(i)));
                            break;
                        case "small":
                            setSmall(new ProfileModel(jsonArray.getJSONObject(i)));
                            break;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ProfileModel getLarge() {
        return large;
    }

    public void setLarge(ProfileModel large) {
        this.large = large;
    }

    public ProfileModel getMedium() {
        return medium;
    }

    public void setMedium(ProfileModel medium) {
        this.medium = medium;
    }

    public ProfileModel getOriginal() {
        return original;
    }

    public void setOriginal(ProfileModel original) {
        this.original = original;
    }

    public ProfileModel getSmall() {
        return small;
    }

    public void setSmall(ProfileModel small) {
        this.small = small;
    }

    public boolean compareTo(AvatarModel model) {
        if (model != null) {
            if (large != model.getLarge())
                return false;

            if (medium != model.getMedium())
                return false;

            if (original != model.getOriginal())
                return false;

            if (small != model.getSmall())
                return false;

            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject toObject() {
        try {
            super.toObject().put("large", getLarge().toObject());
            super.toObject().put("medium", getMedium().toObject());
            super.toObject().put("original", getOriginal().toObject());
            super.toObject().put("small", getSmall().toObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "AvatarModel{" +
                "large=" + large +
                ", medium=" + medium +
                ", original=" + original +
                ", small=" + small +
                '}';
    }

}