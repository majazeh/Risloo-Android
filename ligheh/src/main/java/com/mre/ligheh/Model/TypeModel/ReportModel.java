package com.mre.ligheh.Model.TypeModel;

import com.mre.ligheh.Model.Madule.List;

import org.json.JSONException;
import org.json.JSONObject;

public class ReportModel extends TypeModel {
    private String id;
    private String title;
    private String content;
    private int reported_at;
    private List clients;
    private List viewers;

    public ReportModel(JSONObject jsonObject) {
        super(jsonObject);
        try {
            setId(jsonObject.getString("id"));
        if (!jsonObject.isNull("title"))
            setTitle(jsonObject.getString("title"));
        if (!jsonObject.isNull("content"))
            setContent(jsonObject.getString("content"));
            if (!jsonObject.isNull("reported_at"))
                setReported_at(jsonObject.getInt("reported_at"));
        if (!jsonObject.isNull("clients")) {
            com.mre.ligheh.Model.Madule.List users = new com.mre.ligheh.Model.Madule.List();
            for (int i = 0; i < jsonObject.getJSONArray("clients").length(); i++) {
                users.add(new UserModel(jsonObject.getJSONArray("clients").getJSONObject(i)));
            }
            setClients(users);
        } else {
            setClients(new com.mre.ligheh.Model.Madule.List());
        }
            if (!jsonObject.isNull("viewers")) {
                com.mre.ligheh.Model.Madule.List viewers = new com.mre.ligheh.Model.Madule.List();
                for (int i = 0; i < jsonObject.getJSONArray("viewers").length(); i++) {
                    viewers.add(new UserModel(jsonObject.getJSONArray("viewers").getJSONObject(i)));
                }
                setViewers(viewers);
            } else {
                setViewers(new com.mre.ligheh.Model.Madule.List());
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

    public int getReported_at() {
        return reported_at;
    }

    public void setReported_at(int reported_at) {
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
}
