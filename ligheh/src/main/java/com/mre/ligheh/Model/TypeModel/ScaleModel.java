package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class ScaleModel extends TypeModel {
    private String id = "";
    private String title = "";
    private String version = "";
    private String edition = "";
    private String filler = "";
    private String scaleId = "";
    private String scaleTitle = "";
    private String status = "";
    private int edition_version;

    public ScaleModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("title"))
                setTitle(jsonObject.getString("title"));
            if (!jsonObject.isNull("version"))
                setVersion(jsonObject.getString("version"));
            if (!jsonObject.isNull("edition"))
                setEdition(jsonObject.getString("edition"));
            if (!jsonObject.isNull("filler"))
                setFiller(jsonObject.getString("filler"));
            if (!jsonObject.isNull("scale")) {
                JSONObject scale = jsonObject.getJSONObject("scale");

                if (!jsonObject.isNull("ScaleId"))
                    setScaleId(scale.getString("ScaleId"));
                if (!jsonObject.isNull("ScaleTitle"))
                    setScaleTitle(scale.getString("ScaleTitle"));
            }
            if (!jsonObject.isNull("status"))
                setStatus(jsonObject.getString("status"));

            if (!jsonObject.isNull("edition_version"))
                setEditionVersion(jsonObject.getInt("edition_version"));
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public String getScaleId() {
        return scaleId;
    }

    public void setScaleId(String scaleId) {
        this.scaleId = scaleId;
    }

    public String getScaleTitle() {
        return scaleTitle;
    }

    public void setScaleTitle(String scaleTitle) {
        this.scaleTitle = scaleTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getEditionVersion() {
        return edition_version;
    }

    public void setEditionVersion(int edition_version) {
        this.edition_version = edition_version;
    }

    public boolean compareTo(ScaleModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (!title.equals(model.getTitle()))
                return false;

            if (!version.equals(model.getVersion()))
                return false;

            if (!edition.equals(model.getEdition()))
                return false;

            if (!filler.equals(model.getFiller()))
                return false;

            if (!scaleId.equals(model.getScaleId()))
                return false;

            if (!scaleTitle.equals(model.getScaleTitle()))
                return false;

            if (!status.equals(model.getStatus()))
                return false;

            if (edition_version != model.getEditionVersion())
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
            super.toObject().put("title", getTitle());
            super.toObject().put("version", getVersion());
            super.toObject().put("edition", getEdition());
            super.toObject().put("filler", getFiller());
            super.toObject().put("scaleId", getScaleId());
            super.toObject().put("scaleTitle", getScaleTitle());
            super.toObject().put("status", getStatus());
            super.toObject().put("edition_version", getEditionVersion());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "ScaleModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", version='" + version + '\'' +
                ", edition='" + edition + '\'' +
                ", filler='" + filler + '\'' +
                ", scaleId='" + scaleId + '\'' +
                ", scaleTitle='" + scaleTitle + '\'' +
                ", status='" + status + '\'' +
                ", edition_version=" + edition_version +
                '}';
    }

}