package com.mre.ligheh.Model.Madule;

import java.util.HashMap;

public class BreadCrumb {
    public HashMap breadCrumbFa = new HashMap();
    public HashMap breadCrumbEn = new HashMap();

    public HashMap dashboard() {
        breadCrumbFa.put("dashboard", "dashboard");
        breadCrumbEn.put("id", "dashboardFragment");
        return breadCrumbFa;
    }

    // user
    public HashMap users(){
        dashboard().put("users", "users");
        breadCrumbEn.put("id", "usersFragment");
        return breadCrumbFa;
    }

    public HashMap user(String id){
        users().put(id, "fragment_account");
        breadCrumbEn.put("id", "usersFragment");
        return breadCrumbFa;
    }

    public HashMap editUser(String id){
        user(id).put(id, "editAccountFragment");
        breadCrumbEn.put("id", "editUserFragment");
        return breadCrumbFa;
    }

    // end of user

    // center
    public HashMap centers(){
        dashboard().put("centers", "centers");
        breadCrumbEn.put("id", "centersFragment");
        return breadCrumbFa;
    }

    public HashMap center(String id){
        centers().put("center", id);
        breadCrumbEn.put("id", "centerFragment");
        return breadCrumbFa;
    }

    public HashMap centerUsers(String id){
        center(id).put("centerUsers","centerUsers");
        breadCrumbEn.put("id", "editCenterFragment");
        return breadCrumbFa;
    }

    public HashMap createCenterUser(String id){
        center(id).put("addCenterUser", "addCenterUser");
        breadCrumbEn.put("id", "createCenterFragment");
        return breadCrumbFa;
    }
    // end of center

//    // room
//    public HashMap rooms(){
//        dashboard().put("rooms", "rooms");
//        breadCrumbEn.put("id", "createCenterFragment");
//        return breadCrumbFa;
//    }

    public HashMap room(String centerId,String roomId){
        center(centerId).put("room", roomId);
        breadCrumbEn.put("id", "roomFragment");
        return breadCrumbFa;
    }

    public HashMap roomUsers(String centerId,String roomId){
        room(centerId,roomId).put("roomUsers","roomUsers");
        breadCrumbEn.put("id", "roomUsersFragment");
        return breadCrumbFa;
    }

    public HashMap createRoomUsers(String centerId,String roomId){
        roomUsers(centerId,roomId).put("addRoomUsers","addRoomUsers");
        breadCrumbEn.put("id", "createRoomUserFragment");
        return breadCrumbFa;
    }
    // end of room

//    // case
//    public HashMap cases(){
//        dashboard().put("cases", "cases");
//        return breadCrumbFa;
//    }

    public HashMap Case(String centerId,String roomId,String caseId){
        room(centerId,roomId).put("case", caseId);
        breadCrumbEn.put("id", "caseFragment");
        return breadCrumbFa;
    }

    public HashMap createCaseUser(String centerId,String roomId,String caseId){
        Case(centerId,roomId,caseId).put("addCaseUser", "addCaseuser");
        breadCrumbEn.put("id", "createCaseFragment");
        return breadCrumbFa;
    }
    // end of case

    // session
    public HashMap sessions(){
        dashboard().put("sessions","sessions");
        breadCrumbEn.put("id", "sessionsFragment");
        return breadCrumbFa;
    }

    public HashMap session(String centerId,String roomId,String caseId,String sessionId){
        Case(centerId,roomId,caseId).put("session",sessionId);
        breadCrumbEn.put("id", "sessionFragment");
        return breadCrumbFa;
    }

    // sample
    public HashMap assessments(){
        dashboard().put("scales", "scales");
        breadCrumbEn.put("id", "scalesFragment");
        return breadCrumbFa;
    }

    public HashMap samples(){
        assessments().put("samples", "samples");
        breadCrumbEn.put("id", "samplesFragment");
        return breadCrumbFa;
    }

    public HashMap sample(){
        assessments().put("sample", "sample");
        breadCrumbEn.put("id", "sampleFragment");
        return breadCrumbFa;
    }

    public HashMap createSample(){
        samples().put("createSample", "createSample");
        breadCrumbEn.put("id", "createSampleFragment");
        return breadCrumbFa;
    }

    public HashMap bulkSamples(){
        assessments().put("bulkSamples", "bulkSamples");
        breadCrumbEn.put("id", "bulkSamplesFragment");
        return breadCrumbFa;
    }

    public HashMap bulkSample(){
        assessments().put("bulkSample", "bulkSample");
        breadCrumbEn.put("id", "bulkSampleFragment");
        return breadCrumbFa;
    }
    // end of sample

//    // document
//    public HashMap documents(){
//        dashboard().put("documents","documents");
//        return breadCrumbFa;
//    }
//
//    public HashMap createDocument(){
//        documents().put("createDocuments","createDocuments");
//        return breadCrumbFa;
//    }

}
