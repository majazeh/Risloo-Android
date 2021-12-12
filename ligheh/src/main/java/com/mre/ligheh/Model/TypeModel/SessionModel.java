package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import com.mre.ligheh.Model.Madule.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SessionModel extends TypeModel {
    private String id = "";
    private String status = "";
    private String type = "";
    private String description = "";
    private String report = "";
    private String selected_field = "";
    private String client_reminder = "";
    private String payment_status = "";
    private String selection_type = "";
    private String clients_type = "";
    private String encryption_type = "";
    private String opens_at_type = "";
    private String closed_at_type = "";
    private int duration;
    private int clients_number;
    private int opens_at;
    private int closed_at;
    private int started_at;
    private int canceled_at;
    private boolean group_session;
    private RoomModel room;
    private CaseModel casse;
    private JSONArray fields;
    private JSONArray practices;
    private List clients;
    private List samples;
    private List session_platforms;
    private List transactions;
    private List available_session_platforms;

    public SessionModel(JSONObject jsonObject) {
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
            if (!jsonObject.isNull("report"))
                setReport(jsonObject.getString("report"));
            if (!jsonObject.isNull("selected_field"))
                setSelectedField(jsonObject.getString("selected_field"));
            if (!jsonObject.isNull("client_reminder"))
                setClientReminder(jsonObject.getString("client_reminder"));
            if (!jsonObject.isNull("payment_status"))
                setPaymentStatus(jsonObject.getString("payment_status"));
            if (!jsonObject.isNull("selection_type"))
                setSelectionType(jsonObject.getString("selection_type"));
            if (!jsonObject.isNull("clients_type"))
                setClientsType(jsonObject.getString("clients_type"));
            if (!jsonObject.isNull("encryption_type"))
                setEncryptionType(jsonObject.getString("encryption_type"));
            if (!jsonObject.isNull("opens_at_type"))
                setOpensAtType(jsonObject.getString("opens_at_type"));
            if (!jsonObject.isNull("closed_at_type"))
                setClosedAtType(jsonObject.getString("closed_at_type"));

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

            if (!jsonObject.isNull("practices") && jsonObject.getJSONArray("practices").length() != 0) {
                practices = new JSONArray();

                for (int i = 0; i < jsonObject.getJSONArray("practices").length(); i++)
                    practices.put(jsonObject.getJSONArray("practices").getJSONObject(i));

                setPractices(practices);
            } else {
                setPractices(new JSONArray());
            }

            if (!jsonObject.isNull("clients") && jsonObject.getJSONArray("clients").length() != 0) {
                clients = new List();

                for (int i = 0; i < jsonObject.getJSONArray("clients").length(); i++)
                    clients.add(new UserModel(jsonObject.getJSONArray("clients").getJSONObject(i)));

                setClients(clients);
            } else {
                setClients(new List());
            }

            if (!jsonObject.isNull("samples") && jsonObject.getJSONArray("samples").length() != 0) {
                samples = new List();

                for (int i = 0; i < jsonObject.getJSONArray("samples").length(); i++)
                    samples.add(new SampleModel(jsonObject.getJSONArray("samples").getJSONObject(i)));

                setSamples(samples);
            } else {
                setSamples(new List());
            }

            if (!jsonObject.isNull("session_platforms") && jsonObject.getJSONArray("session_platforms").length() != 0) {
                session_platforms = new List();

                for (int i = 0; i < jsonObject.getJSONArray("session_platforms").length(); i++)
                    session_platforms.add(new SessionPlatformModel(jsonObject.getJSONArray("session_platforms").getJSONObject(i)));

                setSessionPlatforms(session_platforms);
            } else {
                setSessionPlatforms(new List());
            }

            if (!jsonObject.isNull("transactions")) {
                transactions = new List();

                for (int i = 0; i < jsonObject.getJSONArray("transactions").length(); i++)
                    transactions.add(new BillingModel(jsonObject.getJSONArray("transactions").getJSONObject(i)));

                setTransactions(transactions);
            } else {
                setTransactions(new List());
            }

            if (!jsonObject.isNull("available_session_platforms") && jsonObject.getJSONArray("available_session_platforms").length() != 0) {
                available_session_platforms = new List();

                for (int i = 0; i < jsonObject.getJSONArray("available_session_platforms").length(); i++)
                    available_session_platforms.add(new SessionPlatformModel(jsonObject.getJSONArray("available_session_platforms").getJSONObject(i)));

                setAvailableSessionPlatforms(available_session_platforms);
            } else {
                setAvailableSessionPlatforms(new List());
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

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getSelectedField() {
        return selected_field;
    }

    public void setSelectedField(String selected_field) {
        this.selected_field = selected_field;
    }

    public String getClientReminder() {
        return client_reminder;
    }

    public void setClientReminder(String client_reminder) {
        this.client_reminder = client_reminder;
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

    public String getEncryptionType() {
        return encryption_type;
    }

    public void setEncryptionType(String encryption_type) {
        this.encryption_type = encryption_type;
    }

    public String getOpensAtType() {
        return opens_at_type;
    }

    public void setOpensAtType(String opens_at_type) {
        this.opens_at_type = opens_at_type;
    }

    public String getClosedAtType() {
        return closed_at_type;
    }

    public void setClosedAtType(String closed_at_type) {
        this.closed_at_type = closed_at_type;
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

    public JSONArray getPractices() {
        return practices;
    }

    public void setPractices(JSONArray practices) {
        this.practices = practices;
    }

    public List getClients() {
        return clients;
    }

    public void setClients(List clients) {
        this.clients = clients;
    }

    public List getSamples() {
        return samples;
    }

    public void setSamples(List samples) {
        this.samples = samples;
    }

    public List getSessionPlatforms() {
        return session_platforms;
    }

    public void setSessionPlatforms(List session_platforms) {
        this.session_platforms = session_platforms;
    }

    public List getTransactions() {
        return transactions;
    }

    public void setTransactions(List transactions) {
        this.transactions = transactions;
    }

    public List getAvailableSessionPlatforms() {
        return available_session_platforms;
    }

    public void setAvailableSessionPlatforms(List available_session_platforms) {
        this.available_session_platforms = available_session_platforms;
    }

    public boolean compareTo(SessionModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (!status.equals(model.getStatus()))
                return false;

            if (!type.equals(model.getType()))
                return false;

            if (!description.equals(model.getDescription()))
                return false;

            if (!report.equals(model.getReport()))
                return false;

            if (!selected_field.equals(model.getSelectedField()))
                return false;

            if (!client_reminder.equals(model.getClientReminder()))
                return false;

            if (!payment_status.equals(model.getPaymentStatus()))
                return false;

            if (!selection_type.equals(model.getSelectionType()))
                return false;

            if (!clients_type.equals(model.getClientsType()))
                return false;

            if (!encryption_type.equals(model.getEncryptionType()))
                return false;

            if (!opens_at_type.equals(model.getOpensAtType()))
                return false;

            if (!closed_at_type.equals(model.getClosedAtType()))
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

            if (practices != model.getPractices())
                return false;

            if (clients != model.getClients())
                return false;

            if (samples != model.getSamples())
                return false;

            if (session_platforms != model.getSessionPlatforms())
                return false;

            if (transactions != model.getTransactions())
                return false;

            if (available_session_platforms != model.getAvailableSessionPlatforms())
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
            super.toObject().put("report", getReport());
            super.toObject().put("selected_field", getSelectedField());
            super.toObject().put("client_reminder", getClientReminder());
            super.toObject().put("payment_status", getPaymentStatus());
            super.toObject().put("selection_type", getSelectionType());
            super.toObject().put("clients_type", getClientsType());
            super.toObject().put("encryption_type", getEncryptionType());
            super.toObject().put("opens_at_type", getOpensAtType());
            super.toObject().put("closed_at_type", getClosedAtType());
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
            super.toObject().put("practices", getPractices());
            super.toObject().put("clients", getClients());
            super.toObject().put("samples", getSamples());
            super.toObject().put("session_platforms", getSessionPlatforms());
            super.toObject().put("transactions", getTransactions());
            super.toObject().put("available_session_platforms", getAvailableSessionPlatforms());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "SessionModel{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", report='" + report + '\'' +
                ", selected_field='" + selected_field + '\'' +
                ", client_reminder='" + client_reminder + '\'' +
                ", payment_status='" + payment_status + '\'' +
                ", selection_type='" + selection_type + '\'' +
                ", clients_type='" + clients_type + '\'' +
                ", encryption_type='" + encryption_type + '\'' +
                ", opens_at_type='" + opens_at_type + '\'' +
                ", closed_at_type='" + closed_at_type + '\'' +
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
                ", practices=" + practices +
                ", clients=" + clients +
                ", samples=" + samples +
                ", session_platforms=" + session_platforms +
                ", transactions=" + transactions +
                ", available_session_platforms=" + available_session_platforms +
                '}';
    }

}