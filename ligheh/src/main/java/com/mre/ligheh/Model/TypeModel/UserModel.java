package com.mre.ligheh.Model.TypeModel;

import com.mre.ligheh.Model.Madule.List;

import org.json.JSONException;
import org.json.JSONObject;

public class UserModel extends TypeModel {

    private String userId;
    private String name;
    private String email;
    private String mobile;
    private String gender;
    private String userStatus;
    private boolean no_password;
    private String position;
    private String userType;
    private JSONObject groups;
    private String username;
    private UserModel creator;
    private String public_key;
    private String birthday;
    private int userCreated_at;
    private int userUpdated_at;
    private AvatarModel avatar;
    private List centerList;

    public UserModel() {
        super();
    }

    public UserModel(JSONObject jsonObject) throws JSONException {
        setUserId(jsonObject.getString("id"));
        if (!jsonObject.isNull("name"))
            setName(jsonObject.getString("name"));
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
        if (!jsonObject.isNull("public_key"))
            setPublic_key(jsonObject.getString("public_key"));
        if (!jsonObject.isNull("birthday"))
            setBirthday(jsonObject.getString("birthday"));
        if (!jsonObject.isNull("created_at"))
            setUserCreated_at(jsonObject.getInt("created_at"));
        if (!jsonObject.isNull("updated_at"))
            setUserUpdated_at(jsonObject.getInt("updated_at"));
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

    @Override
    public String toString() {
        return "UserModel{" +
                "UserId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender='" + gender + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", position='" + position + '\'' +
                ", userType='" + userType + '\'' +
                ", groups=" + groups +
                ", username='" + username + '\'' +
                ", public_key='" + public_key + '\'' +
                ", birthday='" + birthday + '\'' +
                ", userCreated_at=" + userCreated_at +
                ", userUpdated_at=" + userUpdated_at +
                ", avatar=" + avatar +
                ", centerList=" + centerList +
                '}';
    }
}
