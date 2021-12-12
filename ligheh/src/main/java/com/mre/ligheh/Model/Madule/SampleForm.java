package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.Model.TypeModel.EntityModel;
import com.mre.ligheh.Model.TypeModel.FormModel;
import com.mre.ligheh.Model.TypeModel.ItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SampleForm {
    private String psychologist_description = "";
    private List chain;
    private List prerequisites;
    private String description = "";
    private List entities;
    private List items;
    private JSONArray sampleForm;
    private JSONArray itemPositions;
    private FormModel currentForm;
    private JSONArray forms;
    private int position = 0;
    private int itemPosition = 1;

    public SampleForm(String psychologist_description, List chain, List prerequisites, String description, List entities, List items) {
        this.psychologist_description = psychologist_description;
        this.chain = chain;
        this.prerequisites = prerequisites;
        this.description = description;
        this.entities = entities;
        this.items = items;

        sampleForm = new JSONArray();
        forms = new JSONArray();
        currentForm = new FormModel();
        itemPositions = new JSONArray();

        sampleFormInitializer();
    }

    private void sampleFormInitializer() {
        if (!psychologist_description.equals("")) {
            addForm(new FormModel("توضیحات روان\u200Cشناس", "psychologist_description", psychologist_description));
        }

        if (chain.size() != 0) {
            addForm(new FormModel("زنجیره", "chain", chain));
        }

        if (prerequisites.size() != 0) {
            addForm(new FormModel("اطلاعات", "prerequisites", prerequisites));
        }

        if (!description.equals("")) {
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
                if (!((ItemModel) formModel.getObject()).getUserAnswered().equals(""))
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