package com.mre.ligheh.Model.Madule;

import com.mre.ligheh.API.Exceptioner;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.BulkSampleModel;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.ScaleModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Sample extends Model {
    static String endpoint = "$";
    public SampleModel model;


    public Sample(JSONObject jsonObject) throws JSONException {
        super.data = new SampleModel(jsonObject);
        model = (SampleModel) super.data;
    }

    public static void assessmentsList(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.list(endpoint, data, header, response, ScaleModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sampleList(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.list(endpoint + "/samples", data, header, response, SampleModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void theory(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "key")) {
                String key = (String) data.get("key");
                data.remove("key");
                Model.post("auth" + "/theory" + "/" + key, data, header, response, SampleModel.class);
            } else {
                Exceptioner.make(response, "کلید را وارد کنید");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void bulkList(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.list("bulk-samples", data, header, response, BulkSampleModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void listInstance(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            data.put("instance", 1);
            Model.list(endpoint, data, header, response, SampleModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void show(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (data.containsKey("id")) {
                Model.show(endpoint + "/samples/" + data.get("id"), data, header, response, SampleModel.class);
            } else {
                Exceptioner.make(response, "آیدی را وارد کنید");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void bulkShow(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (data.containsKey("id")) {
                Model.show("bulk-samples/" + data.get("id"), data, header, response, BulkSampleModel.class);
            } else {
                Exceptioner.make(response, "آیدی را وارد کنید");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void close(SampleAnswers sampleAnswers, HashMap<String, Object> data, HashMap<String, Object> header, Response response1) {
        try {
            if (data.containsKey("id")) {
                String id = (String) data.get("id");
                if (sampleAnswers != null) {
                    if (!sampleAnswers.localAnswers.isEmpty() && !sampleAnswers.remoteAnswers.isEmpty()) {
                        sampleAnswers.sendRequest((String) header.get("Authorization"), new Response() {
                            @Override
                            public void onOK(Object object) {
                                try {
                                    Model.put(endpoint + "/samples/" + id + "/close", data, header, response1, null);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(String response) {
                                response1.onFailure(response);
                            }
                        });
                    } else {
                        Model.put(endpoint + "/samples/" + id + "/close", data, header, response1, null);
                    }
                } else {
                    Model.put(endpoint + "/samples/" + id + "/close", data, header, response1, null);
                }
            } else {
                Exceptioner.make(response1, "آیدی را وارد کنید");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void open(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (data.containsKey("id")) {
                String id = (String) data.get("id");
                data.remove("id");
                Model.put(endpoint + "/samples/" + id + "/open", data, header, response, null);
            } else {
                Exceptioner.make(response, "آیدی را وارد کنید");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fill(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (data.containsKey("id")) {
                String id = (String) data.get("id");
                data.remove("id");
                Model.put("command/assessment/fill/" + data.get("id") + "replace=on", data, header, response, null);
            } else {
                Exceptioner.make(response, "آیدی را وارد کنید");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void chain(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (data.containsKey("chain")) {
                String id = (String) data.get("id");
                data.remove("id");
                Model.put(endpoint + "/samples" + data.get("chain"), data, header, response, SampleModel.class);
            } else {
                Exceptioner.make(response, "آیدی زنجیره را وارد کنید");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void score(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (data.containsKey("id")) {
                String id = (String) data.get("id");
                Model.post(endpoint + "/samples/" + id + "/scoring", data, header,response, SampleModel.class);
            } else {
                Exceptioner.make(response, "آیدی را وارد کنید");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getScore(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (data.containsKey("id")) {
                Model.get(endpoint + "/samples/" + data.get("id") + "/scoring", data, header, new Response() {
                            @Override
                            public void onOK(Object object) {
                                boolean repeat = false;
                                SampleModel sampleModel = (SampleModel) object;
                                    if (sampleModel.getSampleStatus().equals("scoring") || (sampleModel.getSampleStatus().equals("creating_files"))) {
                                        repeat = true;
                                    }

                                if (repeat) {
                                    try {
                                        TimeUnit.SECONDS.sleep(3);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    getScore(data, header, response);
                                } else {
                                    response.onOK(object);
                                }
                            }

                            @Override
                            public void onFailure(String response) {

                            }
                        }
                        , SampleModel.class);
            }else{
                Exceptioner.make(response, "آیدی را وارد کنید");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void create(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            Model.create(endpoint + "/samples", data, header, response, SampleModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void items(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (data.containsKey("id")) {
                Model.post(Sample.endpoint + "/samples/" + data.get("id") + "/items", data, header, response, null);
            } else {
                Exceptioner.make(response, "آیدی را وارد کنید");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void auth(HashMap<String, Object> data, HashMap<String, Object> header, Response response) {
        try {
            if (has(data, "authorized_key")) {
                Model.post("auth" , data, header, response, AuthModel.class);
            } else {
                Exceptioner.make(response, "کلید را وارد کنید");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
