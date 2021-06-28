package com.mre.ligheh.Model.TypeModel;

import com.mre.ligheh.Model.Madule.List;
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
    private List items;
    private String caseStatus = "";
    private int membersCount;
    private int joined;
    private List chain;
    private RoomModel SampleRoom;
    private CaseModel SampleCase;
    private List prerequisites;
    private JSONArray terms;
    private String primaryTerm="";
    private String sampleStatus="";
    private String caseId = "";
    private String sessionId = "";
    private SampleForm sampleForm;
    private UserModel client;
    private List entities;
    private List members;
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
        if (!jsonObject.isNull("case_id"))
            setCaseId(jsonObject.getString("case_id"));
        if (!jsonObject.isNull("case_status"))
            setCaseStatus(jsonObject.getString("case_status"));
        if (!jsonObject.isNull("session_id"))
            setSessionId(jsonObject.getString("session_id"));
        if (!jsonObject.isNull("started_at"))
            setStarted_at(jsonObject.getInt("started_at"));
        if (!jsonObject.isNull("scored_at"))
            setScored_at(jsonObject.getInt("scored_at"));
        if (!jsonObject.isNull("closed_at"))
            setClosed_at(jsonObject.getInt("closed_at"));
        if (!jsonObject.isNull("members_count"))
            setMembersCount(jsonObject.getInt("members_count"));
        if (!jsonObject.isNull("joined"))
            setJoined(jsonObject.getInt("joined"));
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
        if (!jsonObject.isNull("members")) {
            com.mre.ligheh.Model.Madule.List members = new com.mre.ligheh.Model.Madule.List();
            for (int i = 0; i < jsonObject.getJSONArray("members").length(); i++) {
                members.add(new UserModel(jsonObject.getJSONArray("members").getJSONObject(i)));
            }
            setMembers(members);
        } else {
            setMembers(new com.mre.ligheh.Model.Madule.List());
        }
        if (!jsonObject.isNull("entities")) {
            com.mre.ligheh.Model.Madule.List entities = new com.mre.ligheh.Model.Madule.List();
            for (int i = 0; i < jsonObject.getJSONArray("entities").length(); i++) {
                entities.add(new EntityModel(jsonObject.getJSONArray("entities").getJSONObject(i)));
            }
            setEntities(entities);
        } else {
            setEntities(new com.mre.ligheh.Model.Madule.List());
        }
        if (!jsonObject.isNull("description"))
            setSampleDescription(jsonObject.getString("description"));
        if (!jsonObject.isNull("client"))
            setClient(new UserModel(jsonObject.getJSONObject("client")));

        if (!jsonObject.isNull("items")) {
            com.mre.ligheh.Model.Madule.List items = new com.mre.ligheh.Model.Madule.List();
            for (int i = 0; i < jsonObject.getJSONArray("items").length(); i++) {
                items.add(new ItemModel(jsonObject.getJSONArray("items").getJSONObject(i)));
            }
            setItems(items);
        } else {
            setItems(new com.mre.ligheh.Model.Madule.List());
        }


//        if (!jsonObject.isNull("chain")) {
//                setChain(jsonObject.getJSONObject("chain").getJSONArray("list"));
//        }

        if (!jsonObject.isNull("prerequisites")) {
            com.mre.ligheh.Model.Madule.List prerequisites = new com.mre.ligheh.Model.Madule.List();
            for (int i = 0; i < jsonObject.getJSONArray("prerequisites").length(); i++) {
                prerequisites.add(new SampleModel(jsonObject.getJSONArray("prerequisites").getJSONObject(i)));
            }
            setPrerequisites(prerequisites);
        } else {
            setPrerequisites(new com.mre.ligheh.Model.Madule.List());
        }
        if (!jsonObject.isNull("items") && !jsonObject.isNull("prerequisites") &&!jsonObject.isNull("entities"))
            sampleForm = new SampleForm(items, chain,entities, prerequisites, getSampleDescription());
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

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public int getMembersCount() {
        return membersCount;
    }

    public List getEntities() {
        return entities;
    }

    public void setEntities(List entities) {
        this.entities = entities;
    }

    public void setMembersCount(int membersCount) {
        this.membersCount = membersCount;
    }

    public int getJoined() {
        return joined;
    }

    public void setJoined(int joined) {
        this.joined = joined;
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

    public List getMembers() {
        return members;
    }

    public void setMembers(List members) {
        this.members = members;
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

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public List getChain() {
        return chain;
    }

    public void setChain(List chain) {
        this.chain = chain;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List prerequisites) {
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
