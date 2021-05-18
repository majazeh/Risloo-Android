package com.mre.ligheh.Model.TypeModel;

import com.mre.ligheh.Model.Madule.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CaseModel extends TypeModel {
    private String caseId;
    private UserModel caseManager;
    private RoomModel caseRoom;
    private com.mre.ligheh.Model.Madule.List clients;
    private JSONObject detail;
    private int sessions_count;
    private int caseCreated_at;

    public CaseModel(JSONObject jsonObject) {
        super(jsonObject);
        try {
            setCaseId(jsonObject.getString("id"));
            if (!jsonObject.isNull("manager"))
                setCaseManager(new UserModel(jsonObject.getJSONObject("manager")));
            if (!jsonObject.isNull("room"))
                setCaseRoom(new RoomModel(jsonObject.getJSONObject("room")));
            if (!jsonObject.isNull("clients")) {
                com.mre.ligheh.Model.Madule.List users = new com.mre.ligheh.Model.Madule.List();
                for (int i = 0; i < jsonObject.getJSONArray("clients").length(); i++) {
                    users.add(new UserModel(jsonObject.getJSONArray("clients").getJSONObject(i)));
                }
                setClients(users);
            } else {
                setClients(new com.mre.ligheh.Model.Madule.List());
            }
            if (!jsonObject.isNull("detail"))
                setDetail(jsonObject.getJSONObject("detail"));
            if (!jsonObject.isNull("sessions_count"))
                setSessions_count(jsonObject.getInt("sessions_count"));
            if (!jsonObject.isNull("created_at"))
                setCaseCreated_at(jsonObject.getInt("created_at"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public UserModel getCaseManager() {
        return caseManager;
    }

    public void setCaseManager(UserModel caseManager) {
        this.caseManager = caseManager;
    }

    public RoomModel getCaseRoom() {
        return caseRoom;
    }

    public void setCaseRoom(RoomModel caseRoom) {
        this.caseRoom = caseRoom;
    }

    public List getClients() {
        return clients;
    }

    public void setClients(List clients) {
        this.clients = clients;
    }

    public JSONObject getDetail() {
        return detail;
    }

    public void setDetail(JSONObject detail) {
        this.detail = detail;
    }

    public int getSessions_count() {
        return sessions_count;
    }

    public void setSessions_count(int sessions_count) {
        this.sessions_count = sessions_count;
    }

    public int getCaseCreated_at() {
        return caseCreated_at;
    }

    public void setCaseCreated_at(int caseCreated_at) {
        this.caseCreated_at = caseCreated_at;
    }
}
