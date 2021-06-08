package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.API.APIRequest;
import com.mre.ligheh.API.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SampleAnswers {
    public static ArrayList<ArrayList<String>> localAnswers;
    public static ArrayList<ArrayList<String>> remoteAnswers;

    public SampleAnswers() {
        if (localAnswers == null)
            localAnswers = new ArrayList();
        if (remoteAnswers == null)
            remoteAnswers = new ArrayList();
    }

    public static void sendRequest(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        if (data.containsKey("items")) {
            remoteAnswers = (ArrayList<ArrayList<String>>) data.get("items");
            if (Model.request) {
                for (int i = 0; i < remoteAnswers.size(); i++) {
                    localAnswers.add(remoteAnswers.get(i));
                }
                remoteAnswers.clear();
            } else {
                for (int i = 0; i < localAnswers.size(); i++) {
                    remoteAnswers.add(localAnswers.get(i));
                }
                localAnswers.clear();
                data.put("items", remoteAnswers);
                try {
                    Model.post(Sample.endpoint + "/samples/" + data.get("id") + "/items", data, header, new Response() {
                        @Override
                        public void onOK(Object object) {
                            remoteAnswers.clear();
                            localAnswers.clear();
                            response.onOK(null);
                        }

                        @Override
                        public void onFailure(String response) {
                        }
                    }, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
