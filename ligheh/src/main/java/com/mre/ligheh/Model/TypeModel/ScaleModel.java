package com.mre.ligheh.Model.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ScaleModel extends TypeModel {
    private String id;
    private String title;
    private String version;
    private String edition;
    private int edition_version;
    private String filler;
    private String ScaleId;
    private String ScaleTitle;
    private String status;

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
            if (!jsonObject.isNull("edition_version"))
                setEdition_version(jsonObject.getInt("edition_version"));
            if (!jsonObject.isNull("filler"))
                setFiller(jsonObject.getString("filler"));
            JSONObject scale = jsonObject.getJSONObject("scale");
            if (!jsonObject.isNull("ScaleId"))
                setScaleId(scale.getString("ScaleId"));
            if (!jsonObject.isNull("ScaleTitle"))
                setScaleTitle(scale.getString("ScaleTitle"));
            if (!jsonObject.isNull("status"))
                setStatus(jsonObject.getString("status"));
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public int getEdition_version() {
        return edition_version;
    }

    public void setEdition_version(int edition_version) {
        this.edition_version = edition_version;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public String getScaleId() {
        return ScaleId;
    }

    public void setScaleId(String scaleId) {
        ScaleId = scaleId;
    }

    public String getScaleTitle() {
        return ScaleTitle;
    }

    public void setScaleTitle(String scaleTitle) {
        ScaleTitle = scaleTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
