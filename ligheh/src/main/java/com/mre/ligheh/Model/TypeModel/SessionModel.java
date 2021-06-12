package com.mre.ligheh.Model.TypeModel;

import com.mre.ligheh.Model.Madule.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SessionModel extends TypeModel {
    private String id = "";
    private RoomModel room;
    private CaseModel caseModel;
    private com.mre.ligheh.Model.Madule.List clients;
    private String status;
    private String type = "";
    private String selected_field = "";
    private boolean group_session = false;
    private String payment_status = "";
    private String selection_type = "";
    private int clients_number ;
    private String clients_type = "";
    private String client_reminder = "";
    private String description = "";
    private int duration;
    private int opens_at;
    private int closed_at;
    private int started_at;
    private int canceled_at;
    private JSONArray fields;
    private JSONArray practices;
    private List samples;
    private String report = "";
    private String encryption_type = "";
    private ArrayList transactions;

    public SessionModel(JSONObject jsonObject) {
        super(jsonObject);
        try {
            setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("room"))
                setRoom(new RoomModel(jsonObject.getJSONObject("room")));
            if (!jsonObject.isNull("case"))
                setCaseModel(new CaseModel(jsonObject.getJSONObject("case")));
            if (!jsonObject.isNull("clients") && jsonObject.getJSONArray("clients").length() != 0){
                clients = new com.mre.ligheh.Model.Madule.List();
                for (int i = 0; i < jsonObject.getJSONArray("clients").length(); i++) {
                    clients.add(new UserModel(jsonObject.getJSONArray("clients").getJSONObject(i)));
                }
            }
            if (!jsonObject.isNull("status"))
                setStatus(jsonObject.getString("status"));
            if (!jsonObject.isNull("type"))
                setType(jsonObject.getString("type"));
            if (!jsonObject.isNull("selected_field"))
                setSelected_field(jsonObject.getString("selected_field"));
            if (!jsonObject.isNull("group_session"))
                setGroup_session(jsonObject.getBoolean("group_session"));
            if (!jsonObject.isNull("payment_status"))
                setPayment_status(jsonObject.getString("payment_status"));
            if (!jsonObject.isNull("selection_type"))
                setSelection_type(jsonObject.getString("selection_type"));
            if (!jsonObject.isNull("clients_number"))
                setClients_number(jsonObject.getInt("clients_number"));
            if (!jsonObject.isNull("clients_type"))
                setClients_type(jsonObject.getString("clients_type"));
            if (!jsonObject.isNull("description"))
                setDescription(jsonObject.getString("description"));
            if (!jsonObject.isNull("duration"))
                setDuration(jsonObject.getInt("duration"));
            if (!jsonObject.isNull("opens_at"))
                setOpens_at(jsonObject.getInt("opens_at"));
            if (!jsonObject.isNull("closed_at"))
                setClosed_at(jsonObject.getInt("closed_at"));
            if (!jsonObject.isNull("started_at"))
                setStarted_at(jsonObject.getInt("started_at"));
            if (!jsonObject.isNull("canceled_at"))
                setCanceled_at(jsonObject.getInt("canceled_at"));
            if (!jsonObject.isNull("fields")) {
                fields = new JSONArray();
                for (int i = 0; i < jsonObject.getJSONArray("fields").length(); i++) {
                    fields.put(jsonObject.getJSONArray("fields").getJSONObject(i));
                }
            }
            if (!jsonObject.isNull("practices")) {
                practices = new JSONArray();
                for (int i = 0; i < jsonObject.getJSONArray("practices").length(); i++) {
                    practices.put(jsonObject.getJSONArray("practices").getJSONObject(i));
                }
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

    public RoomModel getRoom() {
        return room;
    }

    public void setRoom(RoomModel room) {
        this.room = room;
    }

    public CaseModel getCaseModel() {
        return caseModel;
    }

    public void setCaseModel(CaseModel caseModel) {
        this.caseModel = caseModel;
    }

    public com.mre.ligheh.Model.Madule.List getClients() {
        return clients;
    }

    public void setClients(com.mre.ligheh.Model.Madule.List clients) {
        this.clients = clients;
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

    public String getSelected_field() {
        return selected_field;
    }

    public void setSelected_field(String selected_field) {
        this.selected_field = selected_field;
    }

    public boolean isGroup_session() {
        return group_session;
    }

    public void setGroup_session(boolean group_session) {
        this.group_session = group_session;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getSelection_type() {
        return selection_type;
    }

    public void setSelection_type(String selection_type) {
        this.selection_type = selection_type;
    }

    public int getClients_number() {
        return clients_number;
    }

    public void setClients_number(int clients_number) {
        this.clients_number = clients_number;
    }

    public String getClients_type() {
        return clients_type;
    }

    public void setClients_type(String clients_type) {
        this.clients_type = clients_type;
    }

    public String getClient_reminder() {
        return client_reminder;
    }

    public void setClient_reminder(String client_reminder) {
        this.client_reminder = client_reminder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getOpens_at() {
        return opens_at;
    }

    public void setOpens_at(int opens_at) {
        this.opens_at = opens_at;
    }

    public int getClosed_at() {
        return closed_at;
    }

    public void setClosed_at(int closed_at) {
        this.closed_at = closed_at;
    }

    public int getStarted_at() {
        return started_at;
    }

    public void setStarted_at(int started_at) {
        this.started_at = started_at;
    }

    public int getCanceled_at() {
        return canceled_at;
    }

    public void setCanceled_at(int canceled_at) {
        this.canceled_at = canceled_at;
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

    public List getSamples() {
        return samples;
    }

    public void setSamples(List samples) {
        this.samples = samples;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getEncryption_type() {
        return encryption_type;
    }

    public void setEncryption_type(String encryption_type) {
        this.encryption_type = encryption_type;
    }

    public ArrayList getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList transactions) {
        this.transactions = transactions;
    }
}
