package com.mre.ligheh.Model.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileModel extends TypeModel {
    private String id;
    private String file_name;
    private String slug;
    private String mode;
    private String url;
    private String type;
    private String mime;
    private String exec;


    public ProfileModel(JSONObject jsonObject) {
        super(jsonObject);
            try {
        if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
                if (!jsonObject.isNull("file_name"))
                    setFile_name(jsonObject.getString("file_name"));
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

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
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
}
