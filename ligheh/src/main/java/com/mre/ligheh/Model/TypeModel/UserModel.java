package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import com.mre.ligheh.Model.Madule.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserModel extends TypeModel {

    private String id = "";
    private String userId = "";
    private String name = "";
    private String email = "";
    private String mobile = "";
    private String gender = "";
    private String userStatus = "";
    private boolean no_password;
    private String position = "";
    private String userType = "";
    private JSONObject groups;
    private String username = "";
    private UserModel creator;
    private String public_key = "";
    private String birthday = "";
    private int userCreated_at;
    private int userUpdated_at;
    private int userAccepted_at;
    private int userKicked_at;
    private JSONObject meta;
    private AvatarModel avatar;
    private List centerList;
    private List roomList;
    private List caseList;
    private List sampleList;
    private List treasuries;
    private JSONObject dalilyScheduleExports;

    public UserModel() {
        super();
    }

    public UserModel(JSONObject jsonObject) throws JSONException {
        super(jsonObject);
        setId(jsonObject.getString("id"));
        if (!jsonObject.isNull("name"))
            setName(jsonObject.getString("name"));
        if (!jsonObject.isNull("user_id"))
            setUserId(jsonObject.getString("user_id"));
        if (!jsonObject.isNull("email"))
            setEmail(jsonObject.getString("email"));
        if (!jsonObject.isNull("mobile"))
            setMobile(jsonObject.getString("mobile"));
        if (!jsonObject.isNull("gender"))
            setGender(jsonObject.getString("gender"));
        if (!jsonObject.isNull("status"))
            setUserStatus(jsonObject.getString("status"));
        if (!jsonObject.isNull("position"))
            setPosition(jsonObject.getString("position"));
        if (!jsonObject.isNull("public_key"))
            setPublic_key(jsonObject.getString("public_key"));
        if (!jsonObject.isNull("no_password"))
            setNo_password(jsonObject.getBoolean("no_password"));
        if (!jsonObject.isNull("type"))
            setUserType(jsonObject.getString("type"));
        if (!jsonObject.isNull("groups"))
            setGroups(jsonObject.getJSONObject("groups"));
        if (!jsonObject.isNull("username"))
            setUsername(jsonObject.getString("username"));
        if (!jsonObject.isNull("birthday"))
            setBirthday(jsonObject.getString("birthday"));
        if (!jsonObject.isNull("created_at"))
            setUserCreated_at(jsonObject.getInt("created_at"));
        if (!jsonObject.isNull("updated_at"))
            setUserUpdated_at(jsonObject.getInt("updated_at"));
        if (!jsonObject.isNull("accepted_at"))
            setUserAccepted_at(jsonObject.getInt("accepted_at"));
        if (!jsonObject.isNull("kicked_at"))
            setUserKicked_at(jsonObject.getInt("kicked_at"));
        if (!jsonObject.isNull("meta"))
            setMeta(jsonObject.getJSONObject("meta"));
        if (!jsonObject.isNull("creator"))
            setCreator(new UserModel(jsonObject.getJSONObject("creator")));
        if (!jsonObject.isNull("avatar"))
            setAvatar(new AvatarModel(jsonObject.getJSONArray("avatar")));


        if (!jsonObject.isNull("centers")) {
            List centers = new List();
            for (int i = 0; i < jsonObject.getJSONArray("centers").length(); i++) {
                centers.add(new CenterModel(jsonObject.getJSONArray("centers").getJSONObject(i)));
            }
            setCenterList(centers);
        } else {
            setCenterList(new List());
        }
        if (!jsonObject.isNull("rooms")) {
            List rooms = new List();
            for (int i = 0; i < jsonObject.getJSONArray("rooms").length(); i++) {
                rooms.add(new RoomModel(jsonObject.getJSONArray("rooms").getJSONObject(i)));
            }
            setRoomList(rooms);
        } else {
            setRoomList(new List());
        }
        if (!jsonObject.isNull("cases")) {
            List cases = new List();
            for (int i = 0; i < jsonObject.getJSONArray("cases").length(); i++) {
                cases.add(new CaseModel(jsonObject.getJSONArray("cases").getJSONObject(i)));
            }
            setCaseList(cases);
        } else {
            setCaseList(new List());
        }
        if (!jsonObject.isNull("samples")) {
            List samples = new List();
            for (int i = 0; i < jsonObject.getJSONArray("samples").length(); i++) {
                samples.add(new SampleModel(jsonObject.getJSONArray("samples").getJSONObject(i)));
            }
            setSampleList(samples);
        } else {
            setSampleList(new List());
        }


        if (!jsonObject.isNull("treasuries")) {
            List treasuries = new List();
            for (int i = 0; i < jsonObject.getJSONArray("treasuries").length(); i++) {
                treasuries.add(new TreasuriesModel(jsonObject.getJSONArray("treasuries").getJSONObject(i)));
            }
            setTreasuries(treasuries);
        } else {
            setTreasuries(new List());
        }

        if (!jsonObject.isNull("dalily_schedule_exports")) {
            setDalilyScheduleExports(jsonObject.getJSONObject("dalily_schedule_exports"));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public boolean isNo_password() {
        return no_password;
    }

    public void setNo_password(boolean no_password) {
        this.no_password = no_password;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public JSONObject getGroups() {
        return groups;
    }

    public void setGroups(JSONObject groups) {
        this.groups = groups;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getUserCreated_at() {
        return userCreated_at;
    }

    public void setUserCreated_at(int userCreated_at) {
        this.userCreated_at = userCreated_at;
    }

    public int getUserUpdated_at() {
        return userUpdated_at;
    }

    public void setUserUpdated_at(int userUpdated_at) {
        this.userUpdated_at = userUpdated_at;
    }

    public AvatarModel getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarModel avatar) {
        this.avatar = avatar;
    }

    public List getCenterList() {
        return centerList;
    }

    public void setCenterList(List centerList) {
        this.centerList = centerList;
    }

    public UserModel getCreator() {
        return creator;
    }

    public void setCreator(UserModel creator) {
        this.creator = creator;
    }

    public List getRoomList() {
        return roomList;
    }

    public void setRoomList(List roomList) {
        this.roomList = roomList;
    }

    public List getCaseList() {
        return caseList;
    }

    public void setCaseList(List caseList) {
        this.caseList = caseList;
    }

    public List getSampleList() {
        return sampleList;
    }

    public void setSampleList(List sampleList) {
        this.sampleList = sampleList;
    }

    public List getTreasuries() {
        return treasuries;
    }

    public void setTreasuries(List treasuries) {
        this.treasuries = treasuries;
    }

    public int getUserAccepted_at() {
        return userAccepted_at;
    }

    public void setUserAccepted_at(int userAccepted_at) {
        this.userAccepted_at = userAccepted_at;
    }

    public int getUserKicked_at() {
        return userKicked_at;
    }

    public void setUserKicked_at(int userKicked_at) {
        this.userKicked_at = userKicked_at;
    }

    public JSONObject getMeta() {
        return meta;
    }

    public void setMeta(JSONObject meta) {
        this.meta = meta;
    }

    public JSONObject getDalilyScheduleExports() {
        return dalilyScheduleExports;
    }

    public void setDalilyScheduleExports(JSONObject dalilyScheduleExports) {
        this.dalilyScheduleExports = dalilyScheduleExports;
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
            super.toObject().put("status", getUserStatus());
            super.toObject().put("position", getPosition());
            super.toObject().put("public_key", getPublic_key());
            super.toObject().put("no_password", isNo_password());
            super.toObject().put("type", getUserType());
            super.toObject().put("group", getGroups());
            super.toObject().put("username", isNo_password());
            super.toObject().put("birthday", getBirthday());
            super.toObject().put("created_at", getUserCreated_at());
            super.toObject().put("updated_at", getUserUpdated_at());
            super.toObject().put("accepted_at", getUserAccepted_at());
            super.toObject().put("kicked_at", getUserKicked_at());
            super.toObject().put("meta", getMeta());
            super.toObject().put("creator", getCreator().toObject());
            super.toObject().put("avatar", getAvatar().toObject());
            JSONArray centers = new JSONArray();
            for (int i = 0; i < getCenterList().size(); i++) {
                CenterModel centerModel = (CenterModel) getCenterList().data().get(i);
                centers.put(centerModel.toObject());
            }
            super.toObject().put("centers", centers);

            JSONArray rooms = new JSONArray();
            for (int i = 0; i < getRoomList().size(); i++) {
                RoomModel roomModel = (RoomModel) getCenterList().data().get(i);
                rooms.put(roomModel.toObject());
            }
            super.toObject().put("rooms", rooms);

            JSONArray cases = new JSONArray();
            for (int i = 0; i < getCaseList().size(); i++) {
                CaseModel caseModel = (CaseModel) getCenterList().data().get(i);
                cases.put(caseModel.toObject());
            }
            super.toObject().put("cases", cases);

            JSONArray samples = new JSONArray();
            for (int i = 0; i < getSampleList().size(); i++) {
                SampleModel sampleModel = (SampleModel) getCenterList().data().get(i);
                samples.put(sampleModel.toObject());
            }
            super.toObject().put("samples", samples);

            JSONArray treasuries = new JSONArray();
            for (int i = 0; i < getTreasuries().size(); i++) {
                TreasuriesModel treasuriesModel = (TreasuriesModel) getCenterList().data().get(i);
                treasuries.put(treasuriesModel.toObject());
            }
            super.toObject().put("treasuries", treasuries);

            super.toObject().put("dalily_schedule_exports", getDalilyScheduleExports());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.toObject();
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender='" + gender + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", no_password=" + no_password +
                ", position='" + position + '\'' +
                ", userType='" + userType + '\'' +
                ", groups=" + groups +
                ", username='" + username + '\'' +
                ", creator=" + creator +
                ", public_key='" + public_key + '\'' +
                ", birthday='" + birthday + '\'' +
                ", userCreated_at=" + userCreated_at +
                ", userUpdated_at=" + userUpdated_at +
                ", userAccepted_at=" + userAccepted_at +
                ", userKicked_at=" + userKicked_at +
                ", meta=" + meta +
                ", avatar=" + avatar +
                ", centerList=" + centerList +
                ", roomList=" + roomList +
                ", caseList=" + caseList +
                ", sampleList=" + sampleList +
                ", treasuries=" + treasuries +
                ", dalilyScheduleExports=" + dalilyScheduleExports +
                '}';
    }
}