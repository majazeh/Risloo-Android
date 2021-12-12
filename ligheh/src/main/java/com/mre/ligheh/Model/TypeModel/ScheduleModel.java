package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

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
    private int duration = 0;
    private int clients_number = 0;
    private int opens_at = 0;
    private int closed_at = 0;
    private int started_at = 0;
    private int canceled_at = 0;
    private boolean group_session = false;
    private RoomModel room;
    private CaseModel casse;
    private JSONArray fields = new JSONArray();
    private List clients = new List();
    private List session_platforms = new List();
    private List treasuries = new List();

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
                for (int i = 0; i < jsonObject.getJSONArray("fields").length(); i++)
                    fields.put(jsonObject.getJSONArray("fields").getJSONObject(i));
            }

            if (!jsonObject.isNull("clients") && jsonObject.getJSONArray("clients").length() != 0) {
                for (int i = 0; i < jsonObject.getJSONArray("clients").length(); i++)
                    clients.add(new UserModel(jsonObject.getJSONArray("clients").getJSONObject(i)));
            }

            if (!jsonObject.isNull("session_platforms") && jsonObject.getJSONArray("session_platforms").length() != 0) {
                for (int i = 0; i < jsonObject.getJSONArray("session_platforms").length(); i++)
                    session_platforms.add(new SessionPlatformModel(jsonObject.getJSONArray("session_platforms").getJSONObject(i)));
            }

            if (!jsonObject.isNull("treasuries") && jsonObject.getJSONArray("treasuries").length() != 0) {
                for (int i = 0; i < jsonObject.getJSONArray("treasuries").length(); i++)
                    treasuries.add(new TreasuriesModel(jsonObject.getJSONArray("treasuries").getJSONObject(i)));
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

    public boolean compareTo(ScheduleModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (!status.equals(model.getStatus()))
                return false;

            if (!type.equals(model.getType()))
                return false;

            if (!description.equals(model.getDescription()))
                return false;

            if (!payment_status.equals(model.getPaymentStatus()))
                return false;

            if (!selection_type.equals(model.getSelectionType()))
                return false;

            if (!clients_type.equals(model.getClientsType()))
                return false;

            if (duration != model.getDuration())
                return false;

            if (clients_number != model.getClientsNumber())
                return false;

            if (opens_at != model.getOpensAt())
                return false;

            if (closed_at != model.getClosedAt())
                return false;

            if (started_at != model.getStartedAt())
                return false;

            if (canceled_at != model.getCanceledAt())
                return false;

            if (!isGroupSession())
                return false;

            if (room != model.getRoom())
                return false;

            if (casse != model.getCasse())
                return false;

            if (fields != model.getFields())
                return false;

            if (clients != model.getClients())
                return false;

            if (session_platforms != model.getSessionPlatforms())
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
            super.toObject().put("status", getStatus());
            super.toObject().put("type", getType());
            super.toObject().put("description", getDescription());
            super.toObject().put("payment_status", getPaymentStatus());
            super.toObject().put("selection_type", getSelectionType());
            super.toObject().put("clients_type", getClientsType());
            super.toObject().put("duration", getDuration());
            super.toObject().put("clients_number", getClientsNumber());
            super.toObject().put("opens_at", getOpensAt());
            super.toObject().put("closed_at", getClosedAt());
            super.toObject().put("started_at", getStartedAt());
            super.toObject().put("canceled_at", getCanceledAt());
            super.toObject().put("group_session", isGroupSession());
            super.toObject().put("room", getRoom().toObject());
            super.toObject().put("casse", getCasse().toObject());
            super.toObject().put("fields", getFields());
            super.toObject().put("clients", getClients());
            super.toObject().put("session_platforms", getSessionPlatforms());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "ScheduleModel{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", payment_status='" + payment_status + '\'' +
                ", selection_type='" + selection_type + '\'' +
                ", clients_type='" + clients_type + '\'' +
                ", duration=" + duration +
                ", clients_number=" + clients_number +
                ", opens_at=" + opens_at +
                ", closed_at=" + closed_at +
                ", started_at=" + started_at +
                ", canceled_at=" + canceled_at +
                ", group_session=" + group_session +
                ", room=" + room +
                ", casse=" + casse +
                ", fields=" + fields +
                ", clients=" + clients +
                ", session_platforms=" + session_platforms +
                '}';
    }

}