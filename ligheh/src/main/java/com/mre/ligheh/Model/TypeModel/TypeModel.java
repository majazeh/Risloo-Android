package com.mre.ligheh.Model.TypeModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.mre.ligheh.Model.Madule.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;

public class TypeModel implements Parcelable {
    public JSONObject object;
    public JSONArray array;

    public TypeModel() {

    }

    public TypeModel(JSONObject jsonObject) {
        this.object = jsonObject;
    }

    public TypeModel(JSONArray jsonArray) {
        this.array = jsonArray;
    }

    protected Object returnValue(String key) {
        return returnValue(key, null);
    }

    protected Object returnValue(String key, Object defaultResult) {
        try {
            return object.get(key);
        } catch (JSONException e) {
            return defaultResult;
        }
    }

    protected Object toTypeModel(String key, Class aClass) {
        try {
            if (!object.isNull(key))
                return aClass.getDeclaredConstructor(key.getClass()).newInstance(object.get(key));
            else
                return aClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | JSONException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            return null;
        }
    }

    protected List toList(String key, Class aClass) {
        if (!object.isNull(key)) {
            try {
                List list = new List();

                for (int i = 0; i < ((JSONArray) object.get(key)).length(); i++)
                    list.add((TypeModel) aClass.getDeclaredConstructor(key.getClass()).newInstance(object.get(key)));

                return list;
            } catch (NoSuchMethodException | JSONException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return new List();
    }

    public void setter(String key, Object value) {
        try {
            object.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
    ---------- Parcelable Implements ----------
    */

    protected TypeModel(Parcel in) {

    }

    public static final Creator<TypeModel> CREATOR = new Creator<TypeModel>() {

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

    protected boolean notNull(Object object) {
        return object != null;
    }

    protected boolean notNull(String string) {
        return string != null;
    }

    protected boolean notNull(List list) {
        return list.size() != 0;
    }

    public JSONObject toObject() {
        return object;
    }

    public JSONArray toArray() {
        return array;
    }

}