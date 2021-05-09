package com.mre.ligheh.Model.Madule;

import java.util.HashMap;

public class BreadCrumb {
    public HashMap breadCrumb = new HashMap();

    public HashMap dashboard() {
        breadCrumb.put("dashboard", "dashboard");
        return breadCrumb;
    }

    // user
    public HashMap users(){
        dashboard().put("users", "users");
        return breadCrumb;
    }

    public HashMap user(String id){
        users().put(id, "fragment_account");
        return breadCrumb;
    }

    public HashMap editUser(String id){
        user(id).put(id, "editAccountFragment");
        return breadCrumb;
    }

    public HashMap createUser(){
        users().put("createUser","createDocumentFragment");
        return breadCrumb;
    }
    // end of user

    // center
    public HashMap centers(){
        dashboard().put("centers", "centers");
        return breadCrumb;
    }

    public HashMap center(String id){
        centers().put("center", id);
        return breadCrumb;
    }

    public HashMap centerUsers(String id){
        center(id).put("centerUsers","centerUsers");
        return breadCrumb;
    }

    public HashMap addCenterUser(String id){
        center(id).put("addCenterUser", "addCenterUser");
        return breadCrumb;
    }
    // end of center

    // room
    public HashMap rooms(){
        dashboard().put("rooms", "rooms");
        return breadCrumb;
    }

    public HashMap room(String centerId,String roomId){
        center(centerId).put("room", roomId);
        return breadCrumb;
    }

    public HashMap roomUsers(String centerId,String roomId){
        room(centerId,roomId).put("roomUsers","roomUsers");
        return breadCrumb;
    }

    public HashMap addRoomUsers(String centerId,String roomId){
        roomUsers(centerId,roomId).put("addRoomUsers","addRoomUsers");
        return breadCrumb;
    }
    // end of room

    // case
    public HashMap cases(){
        dashboard().put("cases", "cases");
        return breadCrumb;
    }

    public HashMap Case(String centerId,String roomId,String caseId){
        room(centerId,roomId).put("case", caseId);
        return breadCrumb;
    }

    public HashMap addCaseUser(String centerId,String roomId,String caseId){
        Case(centerId,roomId,caseId).put("addCaseUser", "addCaseuser");
        return breadCrumb;
    }
    // end of case

    // session
    public HashMap sessions(){
        dashboard().put("sessions","sessions");
        return breadCrumb;
    }

    public HashMap session(String centerId,String roomId,String caseId,String sessionId){
        Case(centerId,roomId,caseId).put("session",sessionId);
        return breadCrumb;
    }

    // sample
    public HashMap assessments(){
        dashboard().put("assessments", "assessments");
        return breadCrumb;
    }

    public HashMap samples(){
        assessments().put("samples", "samples");
        return breadCrumb;
    }

    public HashMap createSample(){
        samples().put("createSample", "createSample");
        return breadCrumb;
    }
    // end of sample

    // document
    public HashMap documents(){
        dashboard().put("documents","documents");
        return breadCrumb;
    }

    public HashMap createDocument(){
        documents().put("createDocuments","createDocuments");
        return breadCrumb;
    }

}
