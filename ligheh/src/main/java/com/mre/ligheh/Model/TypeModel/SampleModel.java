package com.mre.ligheh.Model.TypeModel;

import com.mre.ligheh.Model.Madule.SampleForm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SampleModel extends TypeModel {
    private String sampleId;
    private String sampleTitle;
    private int sampleVersion;
    private String sampleEdition;
    private int sampleEditionVersion;
    private String filler;
    private String sampleScaleId;
    private String sampleScaleTitle;
    private String sampleDescription;
    private JSONArray items;
    private String chain;
    private RoomModel SampleRoom;
    private JSONArray prerequisites;
    private JSONArray terms;
    private String primaryTerm;
    private String sampleStatus;
    private SampleForm sampleForm;

    public SampleModel(JSONObject jsonObject) throws JSONException {
        super(jsonObject);
        if (!jsonObject.isNull("id"))
            setSampleId(jsonObject.getString("id"));
        if (!jsonObject.isNull("title"))
            setSampleTitle(jsonObject.getString("title"));
        if (!jsonObject.isNull("version"))
            setSampleVersion(jsonObject.getInt("version"));
        if (!jsonObject.isNull("edition"))
            setSampleEdition(jsonObject.getString("edition"));
        if (!jsonObject.isNull("room"))
            setSampleRoom(new RoomModel(jsonObject.getJSONObject("room")));
        if (!jsonObject.isNull("edition_version"))
            setSampleEditionVersion(jsonObject.getInt("edition_version"));
        if (!jsonObject.isNull("filler"))
            setFiller(jsonObject.getString("filler"));
        if (!jsonObject.isNull("scale")) {
            if (!jsonObject.getJSONObject("scale").isNull("id"))
                setSampleScaleId(jsonObject.getJSONObject("scale").getString("id"));
            if (!jsonObject.getJSONObject("scale").isNull("title"))
                setSampleScaleTitle(jsonObject.getJSONObject("scale").getString("title"));
        }
        if (!jsonObject.isNull("description"))
            setSampleDescription(jsonObject.getString("description"));
        if (!jsonObject.isNull("items")) {
            items = new JSONArray();
            for (int i = 0; i < jsonObject.getJSONArray("items").length(); i++) {
                items.put(new Item(jsonObject.getJSONArray("items").getJSONObject(i)));
            }
        }
        if (!jsonObject.isNull("chain")) {
                setChain(jsonObject.getString("chain"));
        }
        if (!jsonObject.isNull("prerequisites")) {
            prerequisites = new JSONArray();
            for (int i = 0; i < jsonObject.getJSONArray("prerequisites").length(); i++) {
                prerequisites.put(new Prerequisites(jsonObject.getJSONArray("prerequisites").getJSONObject(i)));
            }
        }
        if (!jsonObject.isNull("items") && !jsonObject.isNull("prerequisites"))
            sampleForm = new SampleForm(items, chain, prerequisites, getSampleDescription());
        if (!jsonObject.isNull("terms"))
            setTerms(jsonObject.getJSONArray("terms"));
        if (!jsonObject.isNull("primary_term"))
            setPrimaryTerm(jsonObject.getString("primary_term"));
        if (!jsonObject.isNull("status"))
            setSampleStatus(jsonObject.getString("status"));
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleTitle() {
        return sampleTitle;
    }

    public void setSampleTitle(String sampleTitle) {
        this.sampleTitle = sampleTitle;
    }

    public int getSampleVersion() {
        return sampleVersion;
    }

    public void setSampleVersion(int sampleVersion) {
        this.sampleVersion = sampleVersion;
    }

    public String getSampleEdition() {
        return sampleEdition;
    }

    public void setSampleEdition(String sampleEdition) {
        this.sampleEdition = sampleEdition;
    }

    public int getSampleEditionVersion() {
        return sampleEditionVersion;
    }

    public void setSampleEditionVersion(int sampleEditionVersion) {
        this.sampleEditionVersion = sampleEditionVersion;
    }

    public RoomModel getSampleRoom() {
        return SampleRoom;
    }

    public void setSampleRoom(RoomModel sampleRoom) {
        SampleRoom = sampleRoom;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public String getSampleScaleId() {
        return sampleScaleId;
    }

    public void setSampleScaleId(String sampleScaleId) {
        this.sampleScaleId = sampleScaleId;
    }

    public String getSampleScaleTitle() {
        return sampleScaleTitle;
    }

    public void setSampleScaleTitle(String sampleScaleTitle) {
        this.sampleScaleTitle = sampleScaleTitle;
    }

    public String getSampleDescription() {
        return sampleDescription;
    }

    public void setSampleDescription(String sampleDescription) {
        this.sampleDescription = sampleDescription;
    }

    public JSONArray getTerms() {
        return terms;
    }

    public void setTerms(JSONArray terms) {
        this.terms = terms;
    }

    public String getPrimaryTerm() {
        return primaryTerm;
    }

    public void setPrimaryTerm(String primaryTerm) {
        this.primaryTerm = primaryTerm;
    }

    public String getSampleStatus() {
        return sampleStatus;
    }

    public void setSampleStatus(String sampleStatus) {
        this.sampleStatus = sampleStatus;
    }

    public JSONArray getItems() {
        return items;
    }

    public void setItems(JSONArray items) {
        this.items = items;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public JSONArray getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(JSONArray prerequisites) {
        this.prerequisites = prerequisites;
    }

    public SampleForm getSampleForm() {
        return sampleForm;
    }

    public void setSampleForm(SampleForm sampleForm) {
        this.sampleForm = sampleForm;
    }

    @Override
    public String toString() {
        return "SampleModel{" +
                "sampleId='" + sampleId + '\'' +
                ", sampleTitle='" + sampleTitle + '\'' +
                ", sampleVersion=" + sampleVersion +
                ", sampleEdition='" + sampleEdition + '\'' +
                ", sampleEditionVersion=" + sampleEditionVersion +
                ", filler='" + filler + '\'' +
                ", sampleScaleId='" + sampleScaleId + '\'' +
                ", sampleScaleTitle='" + sampleScaleTitle + '\'' +
                ", sampleDescription='" + sampleDescription + '\'' +
                ", items=" + items +
                ", terms=" + terms +
                ", primaryTerm='" + primaryTerm + '\'' +
                ", sampleStatus='" + sampleStatus + '\'' +
                ", sampleForm=" + sampleForm +
                '}';
    }
}
