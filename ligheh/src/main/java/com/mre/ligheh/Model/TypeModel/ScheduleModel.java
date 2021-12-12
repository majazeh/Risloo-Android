package com.mre.ligheh.Model.TypeModel;

import com.mre.ligheh.Model.Madule.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScheduleModel extends TypeModel {
    private String id = "";
    private String status = "";
    private String type = "";
    private String description = "";
    private String payment_status = "";
    private String selection_type = "";
    private String clients_type = "";
    private int duration;
    private int clients_number;
    private int opens_at;
    private int closed_at;
    private int started_at;
    private int canceled_at;
    private boolean group_session = false;
    private RoomModel room;
    private CaseModel casse;
    private JSONArray fields;
    private List clients;
    private List session_platforms;
    private List treasuries;

    public ScheduleModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("status"))
                setStatus(jsonObject.getString("status"));
            if (!jsonObject.isNull("type"))
                setType(jsonObject.getString("type"));
            if (!jsonObject.isNull("description"))
                setDescription(jsonObject.getString("description"));
            if (!jsonObject.isNull("payment_status"))
                setPaymentStatus(jsonObject.getString("payment_status"));
            if (!jsonObject.isNull("selection_type"))
                setSelectionType(jsonObject.getString("selection_type"));
            if (!jsonObject.isNull("clients_type"))
                setClientsType(jsonObject.getString("clients_type"));

            if (!jsonObject.isNull("duration"))
                setDuration(jsonObject.getInt("duration"));
            if (!jsonObject.isNull("clients_number"))
                setClientsNumber(jsonObject.getInt("clients_number"));
            if (!jsonObject.isNull("opens_at"))
                setOpensAt(jsonObject.getInt("opens_at"));
            if (!jsonObject.isNull("closed_at"))
                setClosedAt(jsonObject.getInt("closed_at"));
            if (!jsonObject.isNull("started_at"))
                setStartedAt(jsonObject.getInt("started_at"));
            if (!jsonObject.isNull("canceled_at"))
                setCanceledAt(jsonObject.getInt("canceled_at"));

            if (!jsonObject.isNull("group_session"))
                setGroupSession(jsonObject.getBoolean("group_session"));

            if (!jsonObject.isNull("room"))
                setRoom(new RoomModel(jsonObject.getJSONObject("room")));
            if (!jsonObject.isNull("case"))
                setCasse(new CaseModel(jsonObject.getJSONObject("case")));

            if (!jsonObject.isNull("fields") && jsonObject.getJSONArray("fields").length() != 0) {
                fields = new JSONArray();

                for (int i = 0; i < jsonObject.getJSONArray("fields").length(); i++)
                    fields.put(jsonObject.getJSONArray("fields").getJSONObject(i));

                setFields(fields);
            } else {
                setFields(new JSONArray());
            }

            if (!jsonObject.isNull("clients") && jsonObject.getJSONArray("clients").length() != 0) {
                clients = new List();

                for (int i = 0; i < jsonObject.getJSONArray("clients").length(); i++)
                    clients.add(new UserModel(jsonObject.getJSONArray("clients").getJSONObject(i)));

                setClients(clients);
            } else {
                setClients(new List());
            }

            if (!jsonObject.isNull("session_platforms") && jsonObject.getJSONArray("session_platforms").length() != 0) {
                session_platforms = new List();

                for (int i = 0; i < jsonObject.getJSONArray("session_platforms").length(); i++)
                    session_platforms.add(new SessionPlatformModel(jsonObject.getJSONArray("session_platforms").getJSONObject(i)));

                setSessionPlatforms(session_platforms);
            } else {
                setSessionPlatforms(new List());
            }

            if (!jsonObject.isNull("treasuries") && jsonObject.getJSONArray("treasuries").length() != 0) {
                treasuries = new List();

                for (int i = 0; i < jsonObject.getJSONArray("treasuries").length(); i++)
                    treasuries.add(new TreasuriesModel(jsonObject.getJSONArray("treasuries").getJSONObject(i)));

                setTreasuries(treasuries);
            } else {
                setTreasuries(new List());
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentStatus() {
        return payment_status;
    }

    public void setPaymentStatus(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getSelectionType() {
        return selection_type;
    }

    public void setSelectionType(String selection_type) {
        this.selection_type = selection_type;
    }

    public String getClientsType() {
        return clients_type;
    }

    public void setClientsType(String clients_type) {
        this.clients_type = clients_type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getClientsNumber() {
        return clients_number;
    }

    public void setClientsNumber(int clients_number) {
        this.clients_number = clients_number;
    }

    public int getOpensAt() {
        return opens_at;
    }

    public void setOpensAt(int opens_at) {
        this.opens_at = opens_at;
    }

    public int getClosedAt() {
        return closed_at;
    }

    public void setClosedAt(int closed_at) {
        this.closed_at = closed_at;
    }

    public int getStartedAt() {
        return started_at;
    }

    public void setStartedAt(int started_at) {
        this.started_at = started_at;
    }

    public int getCanceledAt() {
        return canceled_at;
    }

    public void setCanceledAt(int canceled_at) {
        this.canceled_at = canceled_at;
    }

    public boolean isGroupSession() {
        return group_session;
    }

    public void setGroupSession(boolean group_session) {
        this.group_session = group_session;
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

    public JSONArray getFields() {
        return fields;
    }

    public void setFields(JSONArray fields) {
        this.fields = fields;
    }

    public List getClients() {
        return clients;
    }

    public void setClients(List clients) {
        this.clients = clients;
    }

    public List getSessionPlatforms() {
        return session_platforms;
    }

    public void setSessionPlatforms(List session_platforms) {
        this.session_platforms = session_platforms;
    }

    public List getTreasuries() {
        return treasuries;
    }

    public void setTreasuries(List treasuries) {
        this.session_platforms = session_platforms;
    }

}