package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import com.mre.ligheh.Model.Madule.List;

import org.json.JSONException;
import org.json.JSONObject;

public class CaseModel extends TypeModel {
    private String id = "";
    private UserModel manager;
    private RoomModel room;
    private JSONObject detail;
    private int sessions_count;
    private int created_at;
    private List clients;
    private List sessions;
    private List samples;
    private List tags;

    public CaseModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));

            if (!jsonObject.isNull("manager"))
                setManager(new UserModel(jsonObject.getJSONObject("manager")));
            if (!jsonObject.isNull("room"))
                setRoom(new RoomModel(jsonObject.getJSONObject("room")));

            if (!jsonObject.isNull("detail"))
                setDetail(jsonObject.getJSONObject("detail"));

            if (!jsonObject.isNull("sessions_count"))
                setSessionsCount(jsonObject.getInt("sessions_count"));
            if (!jsonObject.isNull("created_at"))
                setCreatedAt(jsonObject.getInt("created_at"));

            if (!jsonObject.isNull("clients") && jsonObject.getJSONArray("clients").length() != 0) {
                clients = new List();

                for (int i = 0; i < jsonObject.getJSONArray("clients").length(); i++)
                    clients.add(new UserModel(jsonObject.getJSONArray("clients").getJSONObject(i)));

                setClients(clients);
            } else {
                setClients(new List());
            }

            if (!jsonObject.isNull("sessions") && jsonObject.getJSONArray("sessions").length() != 0) {
                sessions = new List();

                for (int i = 0; i < jsonObject.getJSONArray("sessions").length(); i++)
                    sessions.add(new SessionModel(jsonObject.getJSONArray("sessions").getJSONObject(i)));

                setSessions(sessions);
            } else {
                setSessions(new List());
            }

            if (!jsonObject.isNull("samples") && jsonObject.getJSONArray("samples").length() != 0) {
                samples = new List();

                for (int i = 0; i < jsonObject.getJSONArray("samples").length(); i++)
                    samples.add(new SampleModel(jsonObject.getJSONArray("samples").getJSONObject(i)));

                setSamples(samples);
            } else {
                setSamples(new List());
            }

            if (!jsonObject.isNull("tags") && jsonObject.getJSONArray("tags").length() != 0) {
                tags = new List();

                for (int i = 0; i < jsonObject.getJSONArray("tags").length(); i++)
                    tags.add(new TagModel(jsonObject.getJSONArray("tags").getJSONObject(i)));

                setTags(tags);
            } else {
                setTags(new List());
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

    public UserModel getManager() {
        return manager;
    }

    public void setManager(UserModel manager) {
        this.manager = manager;
    }

    public RoomModel getRoom() {
        return room;
    }

    public void setRoom(RoomModel room) {
        this.room = room;
    }

    public JSONObject getDetail() {
        return detail;
    }

    public void setDetail(JSONObject detail) {
        this.detail = detail;
    }

    public int getSessionsCount() {
        return sessions_count;
    }

    public void setSessionsCount(int sessions_count) {
        this.sessions_count = sessions_count;
    }

    public int getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(int created_at) {
        this.created_at = created_at;
    }

    public List getClients() {
        return clients;
    }

    public void setClients(List clients) {
        this.clients = clients;
    }

    public List getSessions() {
        return sessions;
    }

    public void setSessions(List sessions) {
        this.sessions = sessions;
    }

    public List getSamples() {
        return samples;
    }

    public void setSamples(List samples) {
        this.samples = samples;
    }

    public List getTags() {
        return tags;
    }

    public void setTags(List tags) {
        this.tags = tags;
    }

    public boolean compareTo(CaseModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (manager != model.getManager())
                return false;

            if (room != model.getRoom())
                return false;

            if (detail != model.getDetail())
                return false;

            if (sessions_count != model.getSessionsCount())
                return false;

            if (created_at != model.getCreatedAt())
                return false;

            if (clients != model.getClients())
                return false;

            if (sessions != model.getSessions())
                return false;

            if (samples != model.getSamples())
                return false;

            if (tags != model.getTags())
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
            super.toObject().put("manager", getManager().toObject());
            super.toObject().put("room", getRoom().toObject());
            super.toObject().put("detail", getDetail());
            super.toObject().put("sessions_count", getSessionsCount());
            super.toObject().put("created_at", getCreatedAt());
            super.toObject().put("clients", getClients());
            super.toObject().put("sessions", getSessions());
            super.toObject().put("samples", getSamples());
            super.toObject().put("tags", getTags());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "CaseModel{" +
                "id='" + id + '\'' +
                ", manager=" + manager +
                ", room=" + room +
                ", detail=" + detail +
                ", sessions_count=" + sessions_count +
                ", created_at=" + created_at +
                ", clients=" + clients +
                ", sessions=" + sessions +
                ", samples=" + samples +
                ", tags=" + tags +
                '}';
    }

}