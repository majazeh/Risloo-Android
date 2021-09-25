package com.mre.ligheh.Model.TypeModel;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

public class TypeModel implements Parcelable {

    public JSONObject object;
    public JSONArray array;

    public TypeModel(JSONObject jsonObject) {
        this.object = jsonObject;
    }

    public TypeModel(JSONArray jsonArray) {
        this.array = jsonArray;
    }

    public TypeModel() {

    }

    /*
    ---------- Parcelable Implements ----------
    */

    protected TypeModel(Parcel in) {

    }

    public static final Creator<TypeModel> CREATOR = new Creator<>() {
        @Override
        public TypeModel createFromParcel(Parcel in) {
            return new TypeModel(in);
        }

        @Override
        public TypeModel[] newArray(int size) {
            return new TypeModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public JSONObject toObject(){
        return object;
    }

    public JSONArray toArray(){
        return array;
    }

}