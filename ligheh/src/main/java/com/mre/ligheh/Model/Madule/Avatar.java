package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.Model.TypeModel.AvatarModel;

import org.json.JSONArray;
import org.json.JSONException;

public class Avatar extends Model {
    public AvatarModel model;
    public Avatar(JSONArray jsonArray) throws JSONException {
        super.data = new AvatarModel(jsonArray);
        model = (AvatarModel) super.data;
    }
}
