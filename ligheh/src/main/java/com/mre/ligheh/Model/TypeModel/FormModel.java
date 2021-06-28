package com.mre.ligheh.Model.TypeModel;

public class FormModel extends TypeModel {
    private String title;
    private String type;
    private Object object;

    public FormModel(String title, String type, Object object) {
        this.title = title;
        this.type = type;
        this.object = object;
    }

    public FormModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
