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
    private String description = "";
    private JSONArray sampleForm;
    public FormModel currentForm;
    private JSONArray itemIndexs;
    private JSONArray forms;
    private int position = 0;
    private int indexPosition = 0;

    public SampleForm(List items, @Nullable List chain, List entities, List prerequisites, String description) {
        this.items = items;
        if (chain != null)
            this.chain = chain;
        if (prerequisites != null)
            this.prerequisites = prerequisites;
        if (description != null)
            this.description = description;
        if (entities!= null)
        this.entities = entities;
        sampleForm = new JSONArray();
        itemIndexs = new JSONArray();
        forms = new JSONArray();
        currentForm = new FormModel();
        sampleFormInitializer();
    }

    private void sampleFormInitializer() {
        if (chain != null) {
            addForm(new FormModel("زنجیره", "chain", chain));
            itemIndexs.put(1);
        }
        if (prerequisites!= null) {
            addForm(new FormModel("اطلاعات", "prerequisites", prerequisites));
            itemIndexs.put(1);
        }
        if (description!= null) {
            addForm(new FormModel("توضیحات", "description", description));
            itemIndexs.put(1);
        }
        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < entities.size(); j++) {
                if (entities.size() != 0) {
                    if (((EntityModel) entities.data().get(j)).getOffset() == i) {
                        addForm(new FormModel(((EntityModel) entities.data().get(j)).getTitle(), "entities", ((EntityModel) entities.data().get(j))));
                        itemIndexs.put(i+1);
                    }
                }

            }
            addForm(new FormModel(String.valueOf(i + 1), "item", items.data().get(i)));
            itemIndexs.put(i+1);
        }
        addForm(new FormModel("پایان", "close", "close"));
        itemIndexs.put(sampleForm.length());
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
        indexPosition =  Math.min(itemIndexs.length() - 1, indexPosition + 1);
        return getCurrentForm();
    }

    public FormModel prev() {
        position = Math.max(0, position - 1);
        indexPosition =  Math.max(0, indexPosition - 1);
        return getCurrentForm();
    }

    public FormModel first() {
        position = 0;
        try {
            indexPosition = itemIndexs.getInt(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getCurrentForm();
    }

    public FormModel last() {
        position = sampleForm.length() - 1;
        indexPosition = itemIndexs.length() -1;
        return getCurrentForm();
    }


    public FormModel goTo(String title) {
//        this.position = Math.min(sampleForm.length() - 1, Math.max(0, position));
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

    public int currentItemIndex(){
        return indexPosition;
    }

    public JSONArray forms(){
        return forms;
    }



    public void addForm(FormModel formModel) {
        forms.put(formModel.getTitle());
        sampleForm.put(formModel);
    }


}
