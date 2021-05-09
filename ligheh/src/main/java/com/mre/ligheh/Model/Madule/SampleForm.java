package com.mre.ligheh.Model.Madule;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;

public class SampleForm {
    private JSONArray items;
    private JSONArray chain;
    private JSONArray prerequisites;
    private String description;
    private JSONArray sampleForm;
    private JSONArray currentForm;
    private int position = 0;

    public SampleForm(JSONArray items, @Nullable JSONArray chain, JSONArray prerequisites, String description) {
        this.items = items;
        if (chain != null)
            this.chain = chain;
        this.prerequisites = prerequisites;
        this.description = description;
        sampleForm = new JSONArray();
        currentForm = new JSONArray();
        sampleFormInitializer();
    }

    private void sampleFormInitializer() {
        if (chain != null)
            addForm(chain, "زنجیره");
        addForm(prerequisites, "اطلاعات");
        addForm(description, "توضیحات");
        for (int i = 0; i < items.length(); i++) {
            try {
                addForm(items.get(i), String.valueOf(i + 1));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        addForm("Close", "پایان");
    }

    public JSONArray getForm(int position) {
        if (sampleForm.length() > position) {
            try {
                return sampleForm.getJSONArray(position);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else
            return null;
    }

    public JSONArray getCurrentForm() {
        try {
            return currentForm = sampleForm.getJSONArray(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray next() {
        try {
            position = Math.min(sampleForm.length() - 1, position + 1);
            return currentForm = sampleForm.getJSONArray(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray last() {
        try {
            position = sampleForm.length() - 1;
            return sampleForm.getJSONArray(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray prev() {
        try {
            position = Math.max(0, position - 1);
            return currentForm = sampleForm.getJSONArray(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray goTo(int position) {
        try {
            position = Math.min(sampleForm.length() - 1, Math.max(0, position));
            return currentForm = sampleForm.getJSONArray(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addForm(Object object, String title) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(object);
        jsonArray.put(title);
        sampleForm.put(jsonArray);
    }


}
