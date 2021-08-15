package com.mre.ligheh.Model.TypeModel;

import com.mre.ligheh.Model.Madule.List;

import org.json.JSONException;
import org.json.JSONObject;

public class RoomModel extends TypeModel {
    private String RoomId="";
    private UserModel RoomCreator;
    private UserModel RoomManager;
    private CenterModel RoomCenter;
    private AcceptationModel RoomAcceptation;
    private String RoomStatus="";
    private String RoomType="";
    private int RoomCreated_at;
    private int RoomUpdated_at;
    private List session_platforms;
    private List pinned_tags;

    public RoomModel(JSONObject jsonObject) throws JSONException {
        super(jsonObject);
        setRoomId(jsonObject.getString("id"));
        if (!jsonObject.isNull("creator"))
            setRoomCreator(new UserModel(jsonObject.getJSONObject("creator")));
        if (!jsonObject.isNull("manager"))
            setRoomManager(new UserModel(jsonObject.getJSONObject("manager")));
        if (!jsonObject.isNull("center"))
            setRoomCenter(new CenterModel(jsonObject.getJSONObject("center")));
        if (!jsonObject.isNull("acceptation"))
            setRoomAcceptation(new AcceptationModel(jsonObject.getJSONObject("acceptation")));
        if (!jsonObject.isNull("status"))
            setRoomStatus(jsonObject.getString("status"));
        if (!jsonObject.isNull("type"))
            setRoomType(jsonObject.getString("type"));
        if (!jsonObject.isNull("created_at"))
            setRoomCreated_at(jsonObject.getInt("created_at"));
        if (!jsonObject.isNull("updated_at"))
            setRoomUpdated_at(jsonObject.getInt("updated_at"));
        if (!jsonObject.isNull("session_platforms") && jsonObject.getJSONArray("session_platforms").length() != 0) {
            session_platforms = new com.mre.ligheh.Model.Madule.List();
            for (int i = 0; i < jsonObject.getJSONArray("session_platforms").length(); i++) {
                session_platforms.add(new SessionPlatformModel(jsonObject.getJSONArray("session_platforms").getJSONObject(i)));
            }
        }else{
            setSession_platforms(new List());
        }
        if (!jsonObject.isNull("pinned_tags") && jsonObject.getJSONArray("pinned_tags").length() != 0) {
            pinned_tags = new com.mre.ligheh.Model.Madule.List();
            for (int i = 0; i < jsonObject.getJSONArray("pinned_tags").length(); i++) {
                pinned_tags.add(new TagModel(jsonObject.getJSONArray("pinned_tags").getJSONObject(i)));
            }
        }else{
            setPinned_tags(new List());
        }
    }

    public String getRoomId() {
        return RoomId;
    }

    public void setRoomId(String roomId) {
        RoomId = roomId;
    }

    public UserModel getRoomCreator() {
        return RoomCreator;
    }

    public void setRoomCreator(UserModel roomCreator) {
        RoomCreator = roomCreator;
    }

    public UserModel getRoomManager() {
        return RoomManager;
    }

    public void setRoomManager(UserModel roomManager) {
        RoomManager = roomManager;
    }

    public CenterModel getRoomCenter() {
        return RoomCenter;
    }

    public void setRoomCenter(CenterModel roomCenter) {
        RoomCenter = roomCenter;
    }

    public AcceptationModel getRoomAcceptation() {
        return RoomAcceptation;
    }

    public void setRoomAcceptation(AcceptationModel roomAcceptation) {
        RoomAcceptation = roomAcceptation;
    }

    public String getRoomStatus() {
        return RoomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        RoomStatus = roomStatus;
    }

    public String getRoomType() {
        return RoomType;
    }

    public void setRoomType(String roomType) {
        RoomType = roomType;
    }

    public int getRoomCreated_at() {
        return RoomCreated_at;
    }

    public void setRoomCreated_at(int roomCreated_at) {
        RoomCreated_at = roomCreated_at;
    }

    public int getRoomUpdated_at() {
        return RoomUpdated_at;
    }

    public void setRoomUpdated_at(int roomUpdated_at) {
        RoomUpdated_at = roomUpdated_at;
    }

    public List getSession_platforms() {
        return session_platforms;
    }

    public void setSession_platforms(List session_platforms) {
        this.session_platforms = session_platforms;
    }

    public List getPinned_tags() {
        return pinned_tags;
    }

    public void setPinned_tags(List pinned_tags) {
        this.pinned_tags = pinned_tags;
    }
}
