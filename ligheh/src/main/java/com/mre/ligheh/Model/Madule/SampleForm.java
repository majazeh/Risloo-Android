package com.mre.ligheh.Model.Madule;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;

public class SampleForm {
    private List items;
    private JSONArray chain;
    private List prerequisites;
    private String description;
    private JSONArray sampleForm;
    public JSONArray currentForm;
    private int position = 0;

    public SampleForm(List items, @Nullable JSONArray chain, List prerequisites, String description) {
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
            addForm("زنجیره", chain);
        addForm("اطلاعات", prerequisites);
        addForm("توضیحات", description);
        for (int i = 0; i < items.size(); i++) {
            addForm(String.valueOf(i + 1), items.data().get(i));
        }
        addForm("پایان", "Close");
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

    public void addForm(String title, Object object) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(title);
        jsonArray.put(object);
        sampleForm.put(jsonArray);
    }


}
