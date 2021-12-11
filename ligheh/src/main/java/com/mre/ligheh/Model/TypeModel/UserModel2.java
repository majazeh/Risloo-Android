package com.mre.ligheh.Model.TypeModel;

import com.mre.ligheh.Model.Madule.List;

import org.json.JSONObject;

public class UserModel2 extends TypeModel {

    public UserModel2(JSONObject jsonObject) {
        super(jsonObject);
    }

    public UserModel2() {
        super();
    }

    public String getId() {
        return (String) returnValue("id","");
    }

    public String getUserId() {
        return (String) returnValue("user_id","");
    }

    public String getName() {
        return (String) returnValue("name","");
    }

    public String getEmail() {
        return (String) returnValue("email","");
    }

    public String getMobile() {
        return (String) returnValue("mobile","");
    }

    public String getGender() {
        return (String) returnValue("gender","");
    }

    public boolean isNo_password() {
        return (boolean) returnValue("no_password",false);
    }

    public String getUserStatus() {
        return (String) returnValue("status","");
    }

    public String getPosition() {
        return (String) returnValue("position","");
    }

    public String getUserType() {
        return (String) returnValue("type","");
    }

    public JSONObject getGroups() {
        return (JSONObject) returnValue("group",new JSONObject());
    }

    public String getUsername() {
        return (String) returnValue("username","");
    }

    public String getPublic_key() {
        return (String) returnValue("public_key","");
    }

    public String getBirthday() {
        return (String) returnValue("birthday","");
    }

    public int getUserCreated_at() {
        return (int) returnValue("created_at");
    }

    public int getUserUpdated_at() {
        return (int) returnValue("updated_at");
    }

    public AvatarModel getAvatar() {
        return (AvatarModel) toTypeModel("avatar", AvatarModel.class);
    }

    public List getCenterList() {
        return toList("centers", CenterModel.class);
    }

    public UserModel getCreator() {
        return (UserModel) toTypeModel("creator", UserModel.class);
    }

    public List getRoomList() {
        return toList("rooms", RoomModel.class);
    }

    public List getCaseList() {
        return toList("cases", CaseModel.class);
    }

    public List getSampleList() {
        return toList("samples", SampleModel.class);
    }

    public List getTreasuries() {
        return toList("treasuries", TreasuriesModel.class);
    }

    public int getUserAccepted_at() {
        return (int) returnValue("accepted_at");
    }

    public int getUserKicked_at() {
        return (int) returnValue("kicked_at");
    }

    public JSONObject getMeta() {
        return (JSONObject) returnValue("meta",new JSONObject());
    }

    public JSONObject getDalilyScheduleExports() {
        return (JSONObject) returnValue("dalily_schedule_exports",new JSONObject());
    }

}