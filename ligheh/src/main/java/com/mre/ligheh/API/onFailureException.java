package com.mre.ligheh.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public abstract class onFailureException {
    String body;
    int statusCode;
    String message_text;
    String MESSAGE_TEXT;

    public onFailureException(Response callback, Object object) {
        switch (object.getClass().getName()) {
            case "java.net.UnknownHostException":
            case "java.net.SocketTimeoutException":
                onClient("اینترنت خود را چک کنید!");
                break;
            case "java.io.IOException":
            case "JSONException":
            case "IllegalAccessException":
            case "InstantiationException":
            case "NoSuchMethodException":
            case "InvocationTargetException":
                onClient((String) object);
                statusCode = 100;
                break;
            case "java.lang.String":
                onClient((String) object);
                break;
            case "okhttp3.Response":
                try {
                    okhttp3.Response response = (okhttp3.Response) object;
                    body = response.body().string();
                    callback.onFailure(body);
                    statusCode = response.code();
                    if (statusCode == 401) {
                        // delete token
                    }

                    if (isJSONObject((body))) {
                        JSONObject jsonObject = new JSONObject(body);
                        message_text = jsonObject.getString("message_text");
                        MESSAGE_TEXT = jsonObject.getString("message");

                        if (hasValidation(jsonObject)) {
                            validation(jsonObject);
                        }else{
                        onServerFail(jsonObject.getString("message_text"));
                        }
                    } else {
                        onServerFail(body);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println(object);
        }
    }

    private boolean hasValidation(JSONObject response) {
        return !response.isNull("errors");
    }

    private boolean isJSONObject(String string) {
        return string.startsWith("{") && string.endsWith("}");
    }

    private void validation(JSONObject jsonObject) {
        HashMap<String, Object> validationData = new HashMap<>();
        try {
            Iterator<String> keys = (jsonObject.getJSONObject("errors").keys());
            while (keys.hasNext()) {
                String key = keys.next();
                for (int i = 0; i < jsonObject.getJSONObject("errors").getJSONArray(key).length(); i++) {
                    validationData.put(key, jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                }
            }
            onValidation(validationData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public abstract void onValidation(HashMap<String, Object> map);

    public abstract void onClient(String s);

    public abstract void onServerFail(String s);

}
