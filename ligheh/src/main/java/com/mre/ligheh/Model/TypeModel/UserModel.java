package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import com.mre.ligheh.Model.Madule.List;

import org.json.JSONException;
import org.json.JSONObject;

public class UserModel extends TypeModel {
    private String id = "";
    private String user_id = "";
    private String name = "";
    private String email = "";
    private String mobile = "";
    private String gender = "";
    private String birthday = "";
    private String status = "";
    private String position = "";
    private String type = "";
    private String username = "";
    private String public_key = "";
    private int created_at = 0;
    private int updated_at = 0;
    private int accepted_at = 0;
    private int kicked_at = 0;
    private boolean no_password = false;
    private AvatarModel avatar;
    private UserModel creator;
    private FieldModel field;
    private SessionPlatformModel session_platform;
    private JSONObject groups;
    private JSONObject meta;
    private JSONObject dalily_schedule_exports;
    private List centers = new List();
    private List rooms = new List();
    private List cases = new List();
    private List samples = new List();
    private List treasuries = new List();

    public UserModel(JSONObject jsonObject) {
        super(jsonObject);

        try {
            if (!jsonObject.isNull("id"))
                setId(jsonObject.getString("id"));
            if (!jsonObject.isNull("user_id"))
                setUserId(jsonObject.getString("user_id"));
            if (!jsonObject.isNull("name"))
                setName(jsonObject.getString("name"));
            if (!jsonObject.isNull("email"))
                setEmail(jsonObject.getString("email"));
            if (!jsonObject.isNull("mobile"))
                setMobile(jsonObject.getString("mobile"));
            if (!jsonObject.isNull("gender"))
                setGender(jsonObject.getString("gender"));
            if (!jsonObject.isNull("birthday"))
                setBirthday(jsonObject.getString("birthday"));
            if (!jsonObject.isNull("status"))
                setStatus(jsonObject.getString("status"));
            if (!jsonObject.isNull("position"))
                setPosition(jsonObject.getString("position"));
            if (!jsonObject.isNull("type"))
                setType(jsonObject.getString("type"));
            if (!jsonObject.isNull("username"))
                setUsername(jsonObject.getString("username"));
            if (!jsonObject.isNull("public_key"))
                setPublicKey(jsonObject.getString("public_key"));

            if (!jsonObject.isNull("created_at"))
                setCreatedAt(jsonObject.getInt("created_at"));
            if (!jsonObject.isNull("updated_at"))
                setUpdatedAt(jsonObject.getInt("updated_at"));
            if (!jsonObject.isNull("accepted_at"))
                setAcceptedAt(jsonObject.getInt("accepted_at"));
            if (!jsonObject.isNull("kicked_at"))
                setKickedAt(jsonObject.getInt("kicked_at"));

            if (!jsonObject.isNull("no_password"))
                setNoPassword(jsonObject.getBoolean("no_password"));

            if (!jsonObject.isNull("avatar"))
                setAvatar(new AvatarModel(jsonObject.getJSONArray("avatar")));
            if (!jsonObject.isNull("creator"))
                setCreator(new UserModel(jsonObject.getJSONObject("creator")));
            if (!jsonObject.isNull("field"))
                setField(new FieldModel(jsonObject.getJSONObject("field")));
            if (!jsonObject.isNull("session_platform"))
                setSessionPlatform(new SessionPlatformModel(jsonObject.getJSONObject("session_platform")));

            if (!jsonObject.isNull("groups"))
                setGroups(jsonObject.getJSONObject("groups"));
            if (!jsonObject.isNull("meta"))
                setMeta(jsonObject.getJSONObject("meta"));
            if (!jsonObject.isNull("dalily_schedule_exports"))
                setDalilyScheduleExports(jsonObject.getJSONObject("dalily_schedule_exports"));

            if (!jsonObject.isNull("centers") && jsonObject.getJSONArray("centers").length() != 0) {
                for (int i = 0; i < jsonObject.getJSONArray("centers").length(); i++)
                    centers.add(new CenterModel(jsonObject.getJSONArray("centers").getJSONObject(i)));
            }

            if (!jsonObject.isNull("rooms") && jsonObject.getJSONArray("rooms").length() != 0) {
                for (int i = 0; i < jsonObject.getJSONArray("rooms").length(); i++)
                    rooms.add(new RoomModel(jsonObject.getJSONArray("rooms").getJSONObject(i)));
            }

            if (!jsonObject.isNull("cases") && jsonObject.getJSONArray("cases").length() != 0) {
                for (int i = 0; i < jsonObject.getJSONArray("cases").length(); i++)
                    cases.add(new CaseModel(jsonObject.getJSONArray("cases").getJSONObject(i)));
            }

            if (!jsonObject.isNull("samples") && jsonObject.getJSONArray("samples").length() != 0) {
                for (int i = 0; i < jsonObject.getJSONArray("samples").length(); i++)
                    samples.add(new SampleModel(jsonObject.getJSONArray("samples").getJSONObject(i)));
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

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPublicKey() {
        return public_key;
    }

    public void setPublicKey(String public_key) {
        this.public_key = public_key;
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

    public int getAcceptedAt() {
        return accepted_at;
    }

    public void setAcceptedAt(int accepted_at) {
        this.accepted_at = accepted_at;
    }

    public int getKickedAt() {
        return kicked_at;
    }

    public void setKickedAt(int kicked_at) {
        this.kicked_at = kicked_at;
    }

    public boolean isNoPassword() {
        return no_password;
    }

    public void setNoPassword(boolean no_password) {
        this.no_password = no_password;
    }

    public AvatarModel getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarModel avatar) {
        this.avatar = avatar;
    }

    public UserModel getCreator() {
        return creator;
    }

    public void setCreator(UserModel creator) {
        this.creator = creator;
    }

    public FieldModel getField() {
        return field;
    }

    public void setField(FieldModel field) {
        this.field = field;
    }

    public SessionPlatformModel getSessionPlatform() {
        return session_platform;
    }

    public void setSessionPlatform(SessionPlatformModel session_platform) {
        this.session_platform = session_platform;
    }

    public JSONObject getGroups() {
        return groups;
    }

    public void setGroups(JSONObject groups) {
        this.groups = groups;
    }

    public JSONObject getMeta() {
        return meta;
    }

    public void setMeta(JSONObject meta) {
        this.meta = meta;
    }

    public JSONObject getDalilyScheduleExports() {
        return dalily_schedule_exports;
    }

    public void setDalilyScheduleExports(JSONObject dalily_schedule_exports) {
        this.dalily_schedule_exports = dalily_schedule_exports;
    }

    public List getCenters() {
        return centers;
    }

    public void setCenters(List centers) {
        this.centers = centers;
    }

    public List getRooms() {
        return rooms;
    }

    public void setRooms(List rooms) {
        this.rooms = rooms;
    }

    public List getCases() {
        return cases;
    }

    public void setCases(List cases) {
        this.cases = cases;
    }

    public List getSamples() {
        return samples;
    }

    public void setSamples(List samples) {
        this.samples = samples;
    }

    public List getTreasuries() {
        return treasuries;
    }

    public void setTreasuries(List treasuries) {
        this.treasuries = treasuries;
    }

    public boolean compareTo(UserModel model) {
        if (model != null) {
            if (!id.equals(model.getId()))
                return false;

            if (!user_id.equals(model.getUserId()))
                return false;

            if (!name.equals(model.getName()))
                return false;

            if (!email.equals(model.getEmail()))
                return false;

            if (!mobile.equals(model.getMobile()))
                return false;

            if (!gender.equals(model.getGender()))
                return false;

            if (!birthday.equals(model.getBirthday()))
                return false;

            if (!status.equals(model.getStatus()))
                return false;

            if (!position.equals(model.getPosition()))
                return false;

            if (!type.equals(model.getType()))
                return false;

            if (!username.equals(model.getUsername()))
                return false;

            if (!public_key.equals(model.getPublicKey()))
                return false;

            if (!isNoPassword())
                return false;

            if (created_at != model.getCreatedAt())
                return false;

            if (updated_at != model.getUpdatedAt())
                return false;

            if (accepted_at != model.getAcceptedAt())
                return false;

            if (kicked_at != model.getKickedAt())
                return false;

            if (avatar != model.getAvatar())
                return false;

            if (creator != model.getCreator())
                return false;

            if (field != model.getField())
                return false;

            if (session_platform != model.getSessionPlatform())
                return false;

            if (groups != model.getGroups())
                return false;

            if (meta != model.getMeta())
                return false;

            if (dalily_schedule_exports != model.getDalilyScheduleExports())
                return false;

            if (centers != model.getCenters())
                return false;

            if (rooms != model.getRooms())
                return false;

            if (cases != model.getCases())
                return false;

            if (samples != model.getSamples())
                return false;

            if (treasuries != model.getTreasuries())
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
            super.toObject().put("user_id", getUserId());
            super.toObject().put("name", getName());
            super.toObject().put("email", getEmail());
            super.toObject().put("mobile", getMobile());
            super.toObject().put("gender", getGender());
            super.toObject().put("birthday", getBirthday());
            super.toObject().put("status", getStatus());
            super.toObject().put("position", getPosition());
            super.toObject().put("type", getType());
            super.toObject().put("username", getUsername());
            super.toObject().put("public_key", getPublicKey());
            super.toObject().put("no_password", isNoPassword());
            super.toObject().put("created_at", getCreatedAt());
            super.toObject().put("updated_at", getUpdatedAt());
            super.toObject().put("accepted_at", getAcceptedAt());
            super.toObject().put("kicked_at", getKickedAt());
            super.toObject().put("avatar", getAvatar().toObject());
            super.toObject().put("creator", getCreator().toObject());
            super.toObject().put("field", getField().toObject());
            super.toObject().put("session_platform", getSessionPlatform().toObject());
            super.toObject().put("groups", getGroups());
            super.toObject().put("meta", getMeta());
            super.toObject().put("dalily_schedule_exports", getDalilyScheduleExports());
            super.toObject().put("centers", getCenters());
            super.toObject().put("rooms", getRooms());
            super.toObject().put("cases", getCases());
            super.toObject().put("samples", getSamples());
            super.toObject().put("treasuries", getTreasuries());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.toObject();
    }

    @NonNull
    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", status='" + status + '\'' +
                ", position='" + position + '\'' +
                ", type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", public_key='" + public_key + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", accepted_at=" + accepted_at +
                ", kicked_at=" + kicked_at +
                ", no_password=" + no_password +
                ", avatar=" + avatar +
                ", creator=" + creator +
                ", field=" + field +
                ", session_platform=" + session_platform +
                ", groups=" + groups +
                ", meta=" + meta +
                ", dalily_schedule_exports=" + dalily_schedule_exports +
                ", centers=" + centers +
                ", rooms=" + rooms +
                ", cases=" + cases +
                ", samples=" + samples +
                ", treasuries=" + treasuries +
                '}';
    }

}