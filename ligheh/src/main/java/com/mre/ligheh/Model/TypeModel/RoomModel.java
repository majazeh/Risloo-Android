package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import com.mre.ligheh.Model.Madule.List;

import org.json.JSONException;
import org.json.JSONObject;

public class RoomModel extends TypeModel {
    private String id = "";
    private String status = "";
    private String type = "";
    private int created_at;
    private int updated_at;
    private UserModel creator;
    private UserModel manager;
    private CenterModel center;
    private AcceptationModel acceptation;
    private List session_platforms;
    private List pinned_tags;

    public RoomModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("status"))
                setStatus(jsonObject.getString("status"));
            if (!jsonObject.isNull("type"))
                setType(jsonObject.getString("type"));

            if (!jsonObject.isNull("created_at"))
                setCreatedAt(jsonObject.getInt("created_at"));
            if (!jsonObject.isNull("updated_at"))
                setUpdatedAt(jsonObject.getInt("updated_at"));

            if (!jsonObject.isNull("creator"))
                setCreator(new UserModel(jsonObject.getJSONObject("creator")));
            if (!jsonObject.isNull("manager"))
                setManager(new UserModel(jsonObject.getJSONObject("manager")));
            if (!jsonObject.isNull("center"))
                setCenter(new CenterModel(jsonObject.getJSONObject("center")));
            if (!jsonObject.isNull("acceptation"))
                setAcceptation(new AcceptationModel(jsonObject.getJSONObject("acceptation")));

            if (!jsonObject.isNull("session_platforms")) {
                session_platforms = new List();

                for (int i = 0; i < jsonObject.getJSONArray("session_platforms").length(); i++)
                    session_platforms.add(new SessionPlatformModel(jsonObject.getJSONArray("session_platforms").getJSONObject(i)));

                setSessionPlatforms(session_platforms);
            } else {
                setSessionPlatforms(new List());
            }

            if (!jsonObject.isNull("pinned_tags")) {
                pinned_tags = new List();

                for (int i = 0; i < jsonObject.getJSONArray("pinned_tags").length(); i++)
                    pinned_tags.add(new TagModel(jsonObject.getJSONArray("pinned_tags").getJSONObject(i)));

                setPinnedTags(pinned_tags);
            } else {
                setPinnedTags(new List());
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

    public int getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(int created_at) {
        this.created_at = created_at;
    }

    public int getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(int updated_at) {
        this.updated_at = updated_at;
    }

    public UserModel getCreator() {
        return creator;
    }

    public void setCreator(UserModel creator) {
        this.creator = creator;
    }

    public UserModel getManager() {
        return manager;
    }

    public void setManager(UserModel manager) {
        this.manager = manager;
    }

    public CenterModel getCenter() {
        return center;
    }

    public void setCenter(CenterModel center) {
        this.center = center;
    }

    public AcceptationModel getAcceptation() {
        return acceptation;
    }

    public void setAcceptation(AcceptationModel acceptation) {
        this.acceptation = acceptation;
    }

    public List getSessionPlatforms() {
        return session_platforms;
    }

    public void setSessionPlatforms(List session_platforms) {
        this.session_platforms = session_platforms;
    }

    public List getPinnedTags() {
        return pinned_tags;
    }

    public void setPinnedTags(List pinned_tags) {
        this.pinned_tags = pinned_tags;
    }

    public boolean compareTo(RoomModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (!status.equals(model.getStatus()))
                return false;

            if (!type.equals(model.getType()))
                return false;

            if (created_at != model.getCreatedAt())
                return false;

            if (updated_at != model.getUpdatedAt())
                return false;

            if (creator != model.getCreator())
                return false;

            if (manager != model.getManager())
                return false;

            if (center != model.getCenter())
                return false;

            if (acceptation != model.getAcceptation())
                return false;

            if (session_platforms != model.getSessionPlatforms())
                return false;

            if (pinned_tags != model.getPinnedTags())
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
            super.toObject().put("created_at", getCreatedAt());
            super.toObject().put("updated_at", getUpdatedAt());
            super.toObject().put("creator", getCreator().toObject());
            super.toObject().put("center", getCenter().toObject());
            super.toObject().put("manager", getManager().toObject());
            super.toObject().put("acceptation", getAcceptation().toObject());
            super.toObject().put("session_platforms", getSessionPlatforms().toObject());
            super.toObject().put("pinned_tags", getPinnedTags().toObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "RoomModel{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", creator=" + creator +
                ", manager=" + manager +
                ", center=" + center +
                ", acceptation=" + acceptation +
                ", session_platforms=" + session_platforms +
                ", pinned_tags=" + pinned_tags +
                '}';
    }

}