package com.mre.ligheh.Model.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

public class AvatarDetail {
    private String id;
    private String file_name;
    private String slug;
    private String mode;
    private String url;
    private String type;
    private String mime;
    private String exec;

    public AvatarDetail(JSONObject jsonObject) {

        try {
            setId(jsonObject.getString("id"));
            setFile_name(jsonObject.getString("file_name"));
            setSlug(jsonObject.getString("slug"));
            setMode(jsonObject.getString("mode"));
            setUrl(jsonObject.getString("url"));
            setType(jsonObject.getString("type"));
            setMime(jsonObject.getString("mime"));
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
