package com.mre.ligheh.Model.Madule;

import androidx.annotation.Nullable;

import com.mre.ligheh.Model.TypeModel.EntityModel;
import com.mre.ligheh.Model.TypeModel.FormModel;

import org.json.JSONArray;
import org.json.JSONException;

public class SampleForm {
    private List items;
    private List chain;
    private List prerequisites;
    private List entities;
    private String description;
    private JSONArray sampleForm;
    public FormModel currentForm;
    private int position = 0;

    public SampleForm(List items, @Nullable List chain, List entities, List prerequisites, String description) {
        this.items = items;
        if (chain != null)
            this.chain = chain;
        this.prerequisites = prerequisites;
        this.description = description;
        this.entities = entities;
        sampleForm = new JSONArray();
        currentForm = new FormModel();
        sampleFormInitializer();
    }

    private void sampleFormInitializer() {
        if (chain != null)
            addForm(new FormModel("زنجیره", "chain", chain));
        addForm(new FormModel("اطلاعات", "description", prerequisites));
        addForm(new FormModel("توضیحات", "description", description));
        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < entities.size(); j++) {
                if (entities.size() != 0) {
                    if (((EntityModel) entities.data().get(j)).getOffset() == i) {
                        addForm(new FormModel(((EntityModel) entities.data().get(j)).getTitle(), "entities", ((EntityModel) entities.data().get(j))));
                    }
                }

            }
            addForm(new FormModel(String.valueOf(i + 1), "item", items.data().get(i)));
        }
        addForm(new FormModel("پایان", "close", "close"));
    }

    public FormModel getCurrentForm() {
        try {
            return currentForm = (FormModel) sampleForm.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public FormModel next() {
        try {
            position = Math.min(sampleForm.length() - 1, position + 1);
            return currentForm = (FormModel) sampleForm.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public FormModel last() {
        try {
            position = sampleForm.length() - 1;
            return (FormModel) sampleForm.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public FormModel prev() {
        try {
            position = Math.max(0, position - 1);
            return currentForm = (FormModel) sampleForm.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public FormModel goTo(int position) {
        try {
            position = Math.min(sampleForm.length() - 1, Math.max(0, position));
            return currentForm = (FormModel) sampleForm.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addForm(FormModel formModel) {
        sampleForm.put(formModel);
    }


}
