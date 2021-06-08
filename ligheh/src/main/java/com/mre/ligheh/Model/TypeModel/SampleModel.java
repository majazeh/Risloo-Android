package com.mre.ligheh.Model.TypeModel;

import com.mre.ligheh.Model.Madule.SampleForm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SampleModel extends TypeModel {
    private String sampleId="";
    private String sampleTitle="";
    private int sampleVersion;
    private String sampleEdition="";
    private int sampleEditionVersion;
    private String filler="";
    private String sampleScaleId="";
    private String sampleScaleTitle="";
    private String sampleDescription="";
    private JSONArray items;
    private String chain="";
    private RoomModel SampleRoom;
    private CaseModel SampleCase;
    private JSONArray prerequisites;
    private JSONArray terms;
    private String primaryTerm="";
    private String sampleStatus="";
    private SampleForm sampleForm;
    private UserModel client;
    private String psychologist_description = "";
    private int created_at;
    private int started_at;
    private int scored_at;
    private int closed_at;
    private int code;

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
        if (!jsonObject.isNull("psychologist_description"))
            setPsychologist_description(jsonObject.getString("psychologist_description"));
        if (!jsonObject.isNull("room"))
            setSampleRoom(new RoomModel(jsonObject.getJSONObject("room")));
        if (!jsonObject.isNull("case"))
            setSampleCase(new CaseModel(jsonObject.getJSONObject("case")));
        if (!jsonObject.isNull("edition_version"))
            setSampleEditionVersion(jsonObject.getInt("edition_version"));
        if (!jsonObject.isNull("created_at"))
            setCreated_at(jsonObject.getInt("created_at"));
        if (!jsonObject.isNull("started_at"))
            setStarted_at(jsonObject.getInt("started_at"));
        if (!jsonObject.isNull("scored_at"))
            setScored_at(jsonObject.getInt("scored_at"));
        if (!jsonObject.isNull("closed_at"))
            setClosed_at(jsonObject.getInt("closed_at"));
        if (!jsonObject.isNull("code"))
            setCode(jsonObject.getInt("code"));
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
        if (!jsonObject.isNull("client"))
            setClient(new UserModel(jsonObject.getJSONObject("client")));
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

    public UserModel getClient() {
        return client;
    }

    public void setClient(UserModel client) {
        this.client = client;
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

    public CaseModel getSampleCase() {
        return SampleCase;
    }

    public void setSampleCase(CaseModel sampleCase) {
        SampleCase = sampleCase;
    }

    public String getPsychologist_description() {
        return psychologist_description;
    }

    public void setPsychologist_description(String psychologist_description) {
        this.psychologist_description = psychologist_description;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getStarted_at() {
        return started_at;
    }

    public void setStarted_at(int started_at) {
        this.started_at = started_at;
    }

    public int getScored_at() {
        return scored_at;
    }

    public void setScored_at(int scored_at) {
        this.scored_at = scored_at;
    }

    public int getClosed_at() {
        return closed_at;
    }

    public void setClosed_at(int closed_at) {
        this.closed_at = closed_at;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
