package com.mre.ligheh.Model.TypeModel;

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
}
