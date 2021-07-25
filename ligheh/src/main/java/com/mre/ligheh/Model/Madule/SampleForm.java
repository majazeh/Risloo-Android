package com.mre.ligheh.Model.Madule;

import androidx.annotation.Nullable;

import com.mre.ligheh.Model.TypeModel.EntityModel;
import com.mre.ligheh.Model.TypeModel.FormModel;
import com.mre.ligheh.Model.TypeModel.ItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SampleForm {
    private List items;
    private List chain;
    private List prerequisites;
    private List entities;
    private String description = "";
    private String psychologist_description;
    private JSONArray sampleForm;
    private JSONArray itemPositions;
    public FormModel currentForm;
    private JSONArray forms;
    private int position = 0;
    private int itemPosition = 1;

    public SampleForm(List items, String psychologist_description, @Nullable List chain, List entities, List prerequisites, String description) {
        this.items = items;
        if (chain != null)
            this.chain = chain;
        if (prerequisites != null)
            this.prerequisites = prerequisites;
        if (description != null)
            this.description = description;
        if (psychologist_description != null)
            this.psychologist_description = psychologist_description;
        if (entities != null)
            this.entities = entities;
        sampleForm = new JSONArray();
        forms = new JSONArray();
        currentForm = new FormModel();
        itemPositions = new JSONArray();
        sampleFormInitializer();
    }

    private void sampleFormInitializer() {
        if (psychologist_description != null) {
            addForm(new FormModel("توضیحات روان\u200Cشناس", "psychologist_description", psychologist_description));
        }
        if (chain != null) {
            addForm(new FormModel("زنجیره", "chain", chain));
        }
        if (prerequisites != null) {
            addForm(new FormModel("اطلاعات", "prerequisites", prerequisites));
        }
        if (description != null) {
            addForm(new FormModel("توضیحات", "description", description));
        }
        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < entities.size(); j++) {
                if (entities.size() != 0) {
                    if (((EntityModel) entities.data().get(j)).getOffset() == i) {
                        addForm(new FormModel(((EntityModel) entities.data().get(j)).getTitle(), "entities", ((EntityModel) entities.data().get(j))));
                    }
                }

            }
            addForm(new FormModel(String.valueOf(i + 1), "item", items.data().get(i)));
            itemPosition++;
        }
        itemPosition--;
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
        System.out.println(position);
        position = Math.min(sampleForm.length() - 1, position + 1);
        return getCurrentForm();
    }

    public FormModel prev() {
        position = Math.max(0, position - 1);
        return getCurrentForm();
    }

    public FormModel first() {
        position = 0;
        return getCurrentForm();
    }

    public FormModel last() {
        position = sampleForm.length() - 1;
        return getCurrentForm();
    }


    public FormModel goTo(String title) {
        for (int i = 0; i < sampleForm.length(); i++) {
            try {
                FormModel formModel = (FormModel) sampleForm.get(i);
                if (title.equals(formModel.getTitle())) {
                    position = i;
                    return formModel;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public FormModel getFirst() {
        try {
            return (FormModel) sampleForm.get(0);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public FormModel getModel(String title) {
        for (int i = 0; i < sampleForm.length(); i++) {
            try {
                FormModel formModel = (FormModel) sampleForm.get(i);
                if (title.equals(formModel.getTitle())) {
                    return formModel;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public int itemSize() {
        if (items != null)
            return items.size();
        else
            return 0;
    }

    public JSONArray getForms() {
        return forms;
    }

    public JSONArray getSampleForm() {
        return sampleForm;
    }

    public int getItemPosition() {
        try {
            return itemPositions.getInt(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getPosition() {
        return position;
    }

    public void addForm(FormModel formModel) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", formModel.getTitle());
            boolean answer = false;
            if (formModel.getObject().getClass().getName().equals("com.mre.ligheh.Model.TypeModel.ItemModel"))
                if (!((ItemModel) formModel.getObject()).getUser_answered().equals(""))
                    answer = true;
            jsonObject.put("answer", answer);
            forms.put(jsonObject);
            itemPositions.put(itemPosition);
            sampleForm.put(formModel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
