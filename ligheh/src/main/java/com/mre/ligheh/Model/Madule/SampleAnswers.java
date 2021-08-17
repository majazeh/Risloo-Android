package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.API.Response;

import java.util.ArrayList;
import java.util.HashMap;

public class SampleAnswers {
    public ArrayList<ArrayList<String>> localAnswers;
    public ArrayList<ArrayList<String>> remoteAnswers;
    public ArrayList<ArrayList<String>> prerequisites;
    public String id;

    public SampleAnswers() {
        localAnswers = new ArrayList<>();
        remoteAnswers = new ArrayList<>();
        prerequisites = new ArrayList<>();
    }

    public void sendRequest( String authorization, Response response) {
        if (!remoteAnswers.isEmpty()) {
            if (Model.request) {
                remoteToLocal(remoteAnswers);
            } else {
                HashMap data = new HashMap();
                HashMap header = new HashMap();
                localToRemote();
                remoteAnswers.addAll(remoteAnswers);
                data.put("items", remoteAnswers);
                data.put("id", id);
                header.put("Authorization", "Bearer " + authorization);
                Sample.items(data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        remoteAnswers.clear();
                        response.onOK(null);
                    }

                    @Override
                    public void onFailure(String response) {
                        try {
                            Thread.sleep(300);
                            sendRequest(authorization, null);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public void sendPrerequisites(String authorization,Response response){
        if (!prerequisites.isEmpty()) {
                HashMap data = new HashMap();
                HashMap header = new HashMap();
                data.put("prerequisites", prerequisites);
                data.put("id", id);
                header.put("Authorization", "Bearer " + authorization);
                Sample.items(data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        prerequisites.clear();
                        response.onOK(null);
                    }

                    @Override
                    public void onFailure(String response) {

                    }
                });

        }
    }

    public void addToRemote(int key,Object value){
        ArrayList arrayList = new ArrayList<String>();
        arrayList.add(key);
        arrayList.add(value);
        remoteAnswers.add(arrayList);
    }

    public void addToPrerequisites(int key,Object value){
        ArrayList arrayList = new ArrayList<String>();
        arrayList.add(key);
        arrayList.add(value);
        prerequisites.add(arrayList);
    }

    private void remoteToLocal(ArrayList remote) {
        for (int i = 0; i < remote.size(); i++) {
            localAnswers.add((ArrayList<String>) remote.get(i));
        }
    }


    private void localToRemote() {
        for (int i = 0; i < localAnswers.size(); i++) {
            remoteAnswers.add(localAnswers.get(i));
        }
        localAnswers.clear();
    }


}
