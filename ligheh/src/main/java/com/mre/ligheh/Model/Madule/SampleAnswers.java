package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.API.APIRequest;
import com.mre.ligheh.API.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Headers;

public class SampleAnswers {
    public ArrayList<ArrayList<String>> localAnswers;
    public ArrayList<ArrayList<String>> remoteAnswers;
    public String id;

    public SampleAnswers() {
        localAnswers = new ArrayList<>();
        remoteAnswers = new ArrayList<>();
    }

    public void sendRequest(ArrayList<ArrayList<String>> remote, String authorization, Response response) {
        if (!remote.isEmpty()) {
            if (Model.request) {
                remoteToLocal(remote);
            } else {
                HashMap data = new HashMap();
                HashMap header = new HashMap();
                localToRemote();
                remoteAnswers.addAll(remote);
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

    public void localToRemote() {
        for (int i = 0; i < localAnswers.size(); i++) {
            remoteAnswers.add(localAnswers.get(i));
        }
        localAnswers.clear();
    }

    public void remoteToLocal(ArrayList remote) {
        for (int i = 0; i < remote.size(); i++) {
            localAnswers.add((ArrayList<String>) remote.get(i));
        }
    }

}
