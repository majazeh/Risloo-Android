package com.mre.ligheh.Model.Madule;

import android.icu.text.Edits;

import androidx.annotation.MainThread;

import com.mre.ligheh.API.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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

    public void sendRequest(String authorization, Response response) {
        System.out.println("remote: " + remoteAnswers);
        System.out.println("local: " + localAnswers);
        if (Model.request) {
        } else {
            HashMap data = new HashMap();
            HashMap header = new HashMap();
            localToRemote();
            localAnswers.clear();
            data.put("items", remoteAnswers);
            data.put("id", id);
            if (authorization.startsWith("Bearer"))
                header.put("Authorization", authorization);
            else
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
                        Thread.sleep(5000);
                        Model.request = false;
                        sendRequest(authorization, null);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    public void sendPrerequisites(String authorization, Response response) {
        if (!prerequisites.isEmpty()) {
            HashMap data = new HashMap();
            HashMap header = new HashMap();
            data.put("prerequisites", prerequisites);
            data.put("id", id);
            if (authorization.startsWith("Bearer"))
                header.put("Authorization", authorization);
            else
                header.put("Authorization", "Bearer " + authorization);
            Sample.items(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    prerequisites.clear();
                    response.onOK(null);
                }

                @Override
                public void onFailure(String response) {
                    try {
                        Thread.sleep(5000);
                        Model.request = false;

                        sendPrerequisites(authorization, null);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    public void add(int key, Object value) {
        ArrayList arrayList = new ArrayList<String>();
        arrayList.add(key);
        arrayList.add(value);
        if (Model.request)
            localAnswers.add(arrayList);
        else
            remoteAnswers.add(arrayList);
    }

    public void addToPrerequisites(int key, Object value) {
        ArrayList arrayList = new ArrayList<String>();
        arrayList.add(key);
        arrayList.add(value);
        Iterator<ArrayList<String>> iterator = prerequisites.iterator();
        while (iterator.hasNext()) {
            ArrayList a = iterator.next();
            if (arrayList.get(0).equals(a.get(0))) {
                prerequisites.remove(a);
            }
        }
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
