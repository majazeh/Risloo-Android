package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.API.APIRequest;
import com.mre.ligheh.API.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Headers;

public class SampleAnswers {
    private ArrayList<ArrayList<String>> localAnswers;
    public ArrayList<ArrayList<String>> remoteAnswers;
    public String id;

    public SampleAnswers() {
        localAnswers = new ArrayList<>();
        remoteAnswers = new ArrayList<>();
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

                    }
                });
            }
        }
    }

    public void addToRemote(int key,Object value){
        ArrayList arrayList = new ArrayList<String>();
        arrayList.add(key-1);
        arrayList.add(value);
        remoteAnswers.add(arrayList);
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
