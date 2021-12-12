package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import com.mre.ligheh.Model.Madule.List;

import org.json.JSONException;
import org.json.JSONObject;

public class ReportModel extends TypeModel {
    private String id = "";
    private String title = "";
    private String content = "";
    private int reported_at = 0;
    private List clients = new List();
    private List viewers = new List();

    public ReportModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("title"))
                setTitle(jsonObject.getString("title"));
            if (!jsonObject.isNull("content"))
                setContent(jsonObject.getString("content"));

            if (!jsonObject.isNull("reported_at"))
                setReportedAt(jsonObject.getInt("reported_at"));

            if (!jsonObject.isNull("clients") && jsonObject.getJSONArray("clients").length() != 0) {
                for (int i = 0; i < jsonObject.getJSONArray("clients").length(); i++)
                    clients.add(new UserModel(jsonObject.getJSONArray("clients").getJSONObject(i)));
            }

            if (!jsonObject.isNull("viewers") && jsonObject.getJSONArray("viewers").length() != 0) {
                for (int i = 0; i < jsonObject.getJSONArray("viewers").length(); i++)
                    viewers.add(new UserModel(jsonObject.getJSONArray("viewers").getJSONObject(i)));
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReportedAt() {
        return reported_at;
    }

    public void setReportedAt(int reported_at) {
        this.reported_at = reported_at;
    }

    public List getClients() {
        return clients;
    }

    public void setClients(List clients) {
        this.clients = clients;
    }

    public List getViewers() {
        return viewers;
    }

    public void setViewers(List viewers) {
        this.viewers = viewers;
    }

    public boolean compareTo(ReportModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (!title.equals(model.getTitle()))
                return false;

            if (!content.equals(model.getContent()))
                return false;

            if (reported_at != model.getReportedAt())
                return false;

            if (clients != model.getClients())
                return false;

            if (viewers != model.getViewers())
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
            super.toObject().put("content", getContent());
            super.toObject().put("reported_at", getReportedAt());
            super.toObject().put("clients", getClients().toObject());
            super.toObject().put("viewers", getViewers().toObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "ReportModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", reported_at=" + reported_at +
                ", clients=" + clients +
                ", viewers=" + viewers +
                '}';
    }

}