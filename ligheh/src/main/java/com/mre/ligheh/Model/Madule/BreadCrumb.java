package com.mre.ligheh.Model.Madule;

import java.util.ArrayList;
import java.util.HashMap;

public class BreadCrumb {
    public ArrayList<String> breadCrumbFa = new ArrayList<>();
    public ArrayList<String> breadCrumbEn = new ArrayList<>();

    public ArrayList<String> dashboard() {
        breadCrumbFa.add("dashboard");
        breadCrumbEn.add( "dashboardFragment");
        return breadCrumbFa;
    }

    // user
    public ArrayList<String> users(){
        dashboard().add("users");
        breadCrumbEn.add("usersFragment");
        return breadCrumbFa;
    }

    public ArrayList<String> user(String id){
        users().add(id);
        breadCrumbEn.add( "usersFragment");
        return breadCrumbFa;
    }

    public ArrayList<String> editUser(String id){
        user(id).add(id);
        breadCrumbEn.add("editUserFragment");
        return breadCrumbFa;
    }

    // end of user

    // center
    public ArrayList<String> centers(){
        dashboard().add( "centers");
        breadCrumbEn.add( "centersFragment");
        return breadCrumbFa;
    }

    public ArrayList<String> center(String id){
        centers().add( id);
        breadCrumbEn.add( "centerFragment");
        return breadCrumbFa;
    }

    public ArrayList<String> centerUsers(String id){
        center(id).add("centerUsers");
        breadCrumbEn.add("editCenterFragment");
        return breadCrumbFa;
    }

    public ArrayList<String> createCenterUser(String id){
        center(id).add( "addCenterUser");
        breadCrumbEn.add("createCenterFragment");
        return breadCrumbFa;
    }
    // end of center

//    // room
//    public HashMap rooms(){
//        dashboard().put("rooms", "rooms");
//        breadCrumbEn.put("id", "createCenterFragment");
//        return breadCrumbFa;
//    }

    public ArrayList<String> room(String centerId,String roomId){
        center(centerId).add( roomId);
        breadCrumbEn.add("roomFragment");
        return breadCrumbFa;
    }

    public ArrayList<String> roomUsers(String centerId,String roomId){
        room(centerId,roomId).add("roomUsers");
        breadCrumbEn.add("roomUsersFragment");
        return breadCrumbFa;
    }

    public ArrayList<String> createRoomUsers(String centerId,String roomId){
        roomUsers(centerId,roomId).add("addRoomUsers");
        breadCrumbEn.add("createRoomUserFragment");
        return breadCrumbFa;
    }
    // end of room

//    // case
//    public HashMap cases(){
//        dashboard().put("cases", "cases");
//        return breadCrumbFa;
//    }

    public ArrayList<String> Case(String centerId,String roomId,String caseId){
        room(centerId,roomId).add( caseId);
        breadCrumbEn.add( "caseFragment");
        return breadCrumbFa;
    }

    public ArrayList<String> createCaseUser(String centerId,String roomId,String caseId){
        Case(centerId,roomId,caseId).add("addCaseuser");
        breadCrumbEn.add("createCaseFragment");
        return breadCrumbFa;
    }
    // end of case

    // session
    public ArrayList<String> sessions(){
        dashboard().add("sessions");
        breadCrumbEn.add("sessionsFragment");
        return breadCrumbFa;
    }

    public ArrayList<String> session(String centerId,String roomId,String caseId,String sessionId){
        Case(centerId,roomId,caseId).add(sessionId);
        breadCrumbEn.add("sessionFragment");
        return breadCrumbFa;
    }

    // sample
    public ArrayList<String> assessments(){
        dashboard().add( "scales");
        breadCrumbEn.add("scalesFragment");
        return breadCrumbFa;
    }

    public ArrayList<String> samples(){
        assessments().add( "samples");
        breadCrumbEn.add( "samplesFragment");
        return breadCrumbFa;
    }

    public ArrayList<String> sample(){
        assessments().add( "sample");
        breadCrumbEn.add( "sampleFragment");
        return breadCrumbFa;
    }

    public ArrayList<String> createSample(){
        samples().add("createSample");
        breadCrumbEn.add( "createSampleFragment");
        return breadCrumbFa;
    }

    public ArrayList<String> bulkSamples(){
        assessments().add( "bulkSamples");
        breadCrumbEn.add("bulkSamplesFragment");
        return breadCrumbFa;
    }

    public ArrayList<String> bulkSample(){
        assessments().add( "bulkSample");
        breadCrumbEn.add("bulkSampleFragment");
        return breadCrumbFa;
    }
    // end of sample

    public ArrayList<String> getBreadCrumbFa() {
        return breadCrumbFa;
    }

    public ArrayList<String> getBreadCrumbEn() {
        if (!breadCrumbEn.isEmpty()){
            breadCrumbEn.remove(breadCrumbEn.size()-1);
        }
        return breadCrumbEn;
    }


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
