package com.mre.ligheh.Model.TypeModel;

import com.mre.ligheh.Model.Madule.List;

import org.json.JSONException;
import org.json.JSONObject;

public class BulkSampleModel extends TypeModel {
    private String id;
    private RoomModel room;
    private String status;
    private CaseModel caseModel;
    private String case_status;
    private List scales;
    private List members;
    private List samples;
    private int members_count;
    private int joined;
    private String title;
    private String link;

    public BulkSampleModel(JSONObject jsonObject) {
        super(jsonObject);
        try {
            if (!jsonObject.isNull("id")) {
                setId(jsonObject.getString("id"));
            }
            if (!jsonObject.isNull("room"))
                setRoom(new RoomModel(jsonObject.getJSONObject("room")));
            if (!jsonObject.isNull("status"))
                setStatus(jsonObject.getString("status"));
            if (!jsonObject.isNull("case"))
                setCaseModel(new CaseModel(jsonObject.getJSONObject("case")));
            if (!jsonObject.isNull("case_status"))
                setCase_status(jsonObject.getString("case_status"));
            if (!jsonObject.isNull("scales")) {
                com.mre.ligheh.Model.Madule.List scales = new com.mre.ligheh.Model.Madule.List();
                for (int i = 0; i < jsonObject.getJSONArray("scales").length(); i++) {
                    scales.add(new ScaleModel(jsonObject.getJSONArray("scales").getJSONObject(i)));
                }
                setScales(scales);
            } else {
                setScales(new com.mre.ligheh.Model.Madule.List());
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
            if (!jsonObject.isNull("samples")) {
                com.mre.ligheh.Model.Madule.List samples = new com.mre.ligheh.Model.Madule.List();
                for (int i = 0; i < jsonObject.getJSONArray("samples").length(); i++) {
                    samples.add(new SampleModel(jsonObject.getJSONArray("samples").getJSONObject(i)));
                }
                setSamples(samples);
            } else {
                setSamples(new com.mre.ligheh.Model.Madule.List());
            }
            if (!jsonObject.isNull("members_count"))
                setMembers_count(jsonObject.getInt("members_count"));
            if (!jsonObject.isNull("joined"))
                setJoined(jsonObject.getInt("joined"));
            if (!jsonObject.isNull("title"))
                setTitle(jsonObject.getString("title"));
            if (!jsonObject.isNull("link"))
                setLink(jsonObject.getString("link"));
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

    public RoomModel getRoom() {
        return room;
    }

    public void setRoom(RoomModel room) {
        this.room = room;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CaseModel getCaseModel() {
        return caseModel;
    }

    public void setCaseModel(CaseModel caseModel) {
        this.caseModel = caseModel;
    }

    public String getCase_status() {
        return case_status;
    }

    public void setCase_status(String case_status) {
        this.case_status = case_status;
    }

    public List getScales() {
        return scales;
    }

    public void setScales(List scales) {
        this.scales = scales;
    }

    public List getMembers() {
        return members;
    }

    public void setMembers(List members) {
        this.members = members;
    }

    public List getSamples() {
        return samples;
    }

    public void setSamples(List samples) {
        this.samples = samples;
    }

    public int getMembers_count() {
        return members_count;
    }

    public void setMembers_count(int members_count) {
        this.members_count = members_count;
    }

    public int getJoined() {
        return joined;
    }

    public void setJoined(int joined) {
        this.joined = joined;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public JSONObject toObject() {
        try {
            if (notNull(getRoom()))
                super.toObject().put("room", getRoom().toObject());
            if (notNull(getStatus()))
                super.toObject().put("status", getStatus());
            if (notNull(getCaseModel()))
                super.toObject().put("caseModel", getCaseModel().toObject());
            if (notNull(getCase_status()))
                super.toObject().put("case_status", getCase_status());
            if (notNull(getScales()))
                super.toObject().put("scales", getScales().toObject());
            if (notNull(getMembers()))
                super.toObject().put("members", getMembers().toObject());
            if (notNull(getSamples()))
                super.toObject().put("samples", getSamples().toObject());
            if (notNull(getMembers_count()))
                super.toObject().put("members_count", getMembers_count());
            if (notNull(getJoined()))
                super.toObject().put("joined", getJoined());
            if (notNull(getTitle()))
                super.toObject().put("title", getTitle());
            if (notNull(getLink()))
                super.toObject().put("link", getLink());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.toObject();
    }

    @Override
    public String toString() {
        return "BulkSampleModel{" +
                "id='" + id + '\'' +
                ", room=" + room +
                ", status='" + status + '\'' +
                ", caseModel=" + caseModel +
                ", case_status='" + case_status + '\'' +
                ", scales=" + scales +
                ", members=" + members +
                ", samples=" + samples +
                ", members_count=" + members_count +
                ", joined=" + joined +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
