package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileModel extends TypeModel {
    private String id = "";
    private String file_name = "";
    private String slug = "";
    private String mode = "";
    private String url = "";
    private String type = "";
    private String mime = "";
    private String exec = "";

    public ProfileModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("file_name"))
                setFileName(jsonObject.getString("file_name"));
            if (!jsonObject.isNull("slug"))
                setSlug(jsonObject.getString("slug"));
            if (!jsonObject.isNull("mode"))
                setMode(jsonObject.getString("mode"));
            if (!jsonObject.isNull("url"))
                setUrl(jsonObject.getString("url"));
            if (!jsonObject.isNull("type"))
                setType(jsonObject.getString("type"));
            if (!jsonObject.isNull("mime"))
                setMime(jsonObject.getString("mime"));
            if (!jsonObject.isNull("exec"))
                setExec(jsonObject.getString("exec"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return file_name;
    }

    public void setFileName(String file_name) {
        this.file_name = file_name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getExec() {
        return exec;
    }

    public void setExec(String exec) {
        this.exec = exec;
    }

    public boolean compareTo(ProfileModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (!file_name.equals(model.getFileName()))
                return false;

            if (!slug.equals(model.getSlug()))
                return false;

            if (!mode.equals(model.getMode()))
                return false;

            if (!url.equals(model.getUrl()))
                return false;

            if (!mime.equals(model.getMime()))
                return false;

            if (!exec.equals(model.getExec()))
                return false;

            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject toObject() {
        try {
            super.toObject().put("id", getId());
            super.toObject().put("file_name", getFileName());
            super.toObject().put("slug", getSlug());
            super.toObject().put("mode", getMode());
            super.toObject().put("url", getUrl());
            super.toObject().put("mime", getMime());
            super.toObject().put("exec", getExec());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "ProfileModel{" +
                "id='" + id + '\'' +
                ", file_name='" + file_name + '\'' +
                ", slug='" + slug + '\'' +
                ", mode='" + mode + '\'' +
                ", url='" + url + '\'' +
                ", mime='" + mime + '\'' +
                ", exec='" + exec + '\'' +
                '}';
    }

}