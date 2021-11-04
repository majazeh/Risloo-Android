package com.mre.ligheh.Model.Madule;

import androidx.annotation.Nullable;

import com.mre.ligheh.API.APIRequest;
import com.mre.ligheh.API.RequestData;
import com.mre.ligheh.API.RequestHeader;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Model {
    public TypeModel data;
    public static boolean request = false;


    public static void changeBaseUrl(String newBaseUrl) {
        APIRequest.baseUrl = newBaseUrl;
    }

    public static void show(String endpoint, HashMap<String, Object> data, HashMap<String, Object> header, Response response, Class aClass) throws IOException {
            APIRequest.get(endpoint, setData(data), setHeader(header), response, aClass);
    }

    public static void list(String endpoint, HashMap<String, Object> data, HashMap<String, Object> header, Response response,@Nullable Class aClass) throws IOException {
        APIRequest.get(endpoint, setData(data), setHeader(header), response, aClass);
    }

    public static void get(String endpoint, HashMap<String, Object> data, HashMap<String, Object> header, Response response,@Nullable Class aClass) throws IOException {
        APIRequest.get(endpoint, setData(data), setHeader(header), response, aClass);
    }

    public static void create(String endpoint, HashMap<String, Object> data, HashMap<String, Object> header, Response response,@Nullable Class aClass) throws IOException {
        APIRequest.post(endpoint, setData(data), setHeader(header), response, aClass);
    }

    public static void delete(String endpoint, HashMap<String, Object> data, HashMap<String, Object> header, Response response,@Nullable Class aClass) throws IOException {
        APIRequest.delete(endpoint, setData(data), setHeader(header), response, aClass);
    }

    public static void put(String endpoint, HashMap<String, Object> data, HashMap<String, Object> header, Response response,@Nullable Class aClass) throws IOException {
        APIRequest.put(endpoint, setData(data), setHeader(header), response, aClass);
    }


    public static void post(String endpoint, HashMap<String, Object> data, HashMap<String, Object> header, Response response,@Nullable Class aClass) throws IOException {
        APIRequest.post(endpoint, setData(data), setHeader(header), response, aClass);
    }
    public static RequestData setData(HashMap<String, Object> data) {
        System.out.println("data: " + data.toString());
        RequestData requestData = new RequestData();
        for (String key : data.keySet()) {
            if (data.get(key).getClass().getSimpleName().equals("File"))
                requestData.set(key, (File)data.get(key));
            else
            requestData.set(key, data.get(key));
        }
        return requestData;
    }

    public static RequestHeader setHeader(HashMap<String, Object> header) {
        RequestHeader requestHeader = new RequestHeader();
        for (String key : header.keySet()) {
            requestHeader.set(key, header.get(key));
        }
        return requestHeader;
    }

    protected static boolean has(HashMap<String, Object> data, String key){
        return data.containsKey(key);
    }
}