package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import com.mre.ligheh.Model.Madule.List;

import org.json.JSONException;
import org.json.JSONObject;

public class BulkSampleModel extends TypeModel {
    private String id = "";
    private String title = "";
    private String link = "";
    private String status = "";
    private String case_status = "";
    private RoomModel room;
    private CaseModel casse;
    private int members_count;
    private int joined;
    private List scales;
    private List members;
    private List samples;

    public BulkSampleModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("title"))
                setTitle(jsonObject.getString("title"));
            if (!jsonObject.isNull("link"))
                setLink(jsonObject.getString("link"));
            if (!jsonObject.isNull("status"))
                setStatus(jsonObject.getString("status"));
            if (!jsonObject.isNull("case_status"))
                setCaseStatus(jsonObject.getString("case_status"));

            if (!jsonObject.isNull("room"))
                setRoom(new RoomModel(jsonObject.getJSONObject("room")));
            if (!jsonObject.isNull("case"))
                setCasse(new CaseModel(jsonObject.getJSONObject("case")));

            if (!jsonObject.isNull("members_count"))
                setMembersCount(jsonObject.getInt("members_count"));
            if (!jsonObject.isNull("joined"))
                setJoined(jsonObject.getInt("joined"));

            if (!jsonObject.isNull("scales")) {
                scales = new List();

                for (int i = 0; i < jsonObject.getJSONArray("scales").length(); i++)
                    scales.add(new ScaleModel(jsonObject.getJSONArray("scales").getJSONObject(i)));

                setScales(scales);
            } else {
                setScales(new List());
            }

            if (!jsonObject.isNull("members")) {
                members = new List();

                for (int i = 0; i < jsonObject.getJSONArray("members").length(); i++)
                    members.add(new UserModel(jsonObject.getJSONArray("members").getJSONObject(i)));

                setMembers(members);
            } else {
                setMembers(new List());
            }

            if (!jsonObject.isNull("samples")) {
                samples = new List();

                for (int i = 0; i < jsonObject.getJSONArray("samples").length(); i++)
                    samples.add(new SampleModel(jsonObject.getJSONArray("samples").getJSONObject(i)));

                setSamples(samples);
            } else {
                setSamples(new List());
            }

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCaseStatus() {
        return case_status;
    }

    public void setCaseStatus(String case_status) {
        this.case_status = case_status;
    }

    public RoomModel getRoom() {
        return room;
    }

    public void setRoom(RoomModel room) {
        this.room = room;
    }

    public CaseModel getCasse() {
        return casse;
    }

    public void setCasse(CaseModel casse) {
        this.casse = casse;
    }

    public int getMembersCount() {
        return members_count;
    }

    public void setMembersCount(int members_count) {
        this.members_count = members_count;
    }

    public int getJoined() {
        return joined;
    }

    public void setJoined(int joined) {
        this.joined = joined;
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

    public boolean compareTo(BulkSampleModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (!title.equals(model.getTitle()))
                return false;

            if (!link.equals(model.getLink()))
                return false;

            if (!status.equals(model.getStatus()))
                return false;

            if (!case_status.equals(model.getCaseStatus()))
                return false;

            if (room != model.getRoom())
                return false;

            if (casse != model.getCasse())
                return false;

            if (members_count != model.getMembersCount())
                return false;

            if (joined != model.getJoined())
                return false;

            if (scales != model.getScales())
                return false;

            if (members != model.getMembers())
                return false;

            if (samples != model.getSamples())
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
            super.toObject().put("link", getLink());
            super.toObject().put("status", getStatus());
            super.toObject().put("case_status", getCaseStatus());
            super.toObject().put("room", getRoom().toObject());
            super.toObject().put("case", getCasse().toObject());
            super.toObject().put("members_count", getMembersCount());
            super.toObject().put("joined", getJoined());
            super.toObject().put("scales", getScales());
            super.toObject().put("members", getMembers());
            super.toObject().put("samples", getSamples());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "BulkSampleModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", status='" + status + '\'' +
                ", case_status='" + case_status + '\'' +
                ", room=" + room +
                ", case=" + casse +
                ", members_count=" + members_count +
                ", joined=" + joined +
                ", scales=" + scales +
                ", members=" + members +
                ", samples=" + samples +
                '}';
    }

}