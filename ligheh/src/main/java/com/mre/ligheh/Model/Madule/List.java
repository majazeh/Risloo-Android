package com.mre.ligheh.Model.Madule;

import androidx.annotation.NonNull;

import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class List {
    private ArrayList<TypeModel> models;
    private JSONObject data;
    private int page = 1;
    private int code = 0;

    public List() {
        models = new ArrayList<>();
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public Object getData() {
        if (data == null)
            return null;
        else
            return data;
    }

    public void add(TypeModel model) {
        models.add(model);
    }

    public JSONObject meta() {
        try {
            if (getData() != null) {
                return ((JSONObject) getData()).getJSONObject("meta");
            } else
                return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void setPage(int page) {
        this.page = page;
    }

    public int nextPage() {
        return getPage() + 1;
    }

    public int prevPage() {
        return getPage() - 1;
    }

    public int getPage() {
        try {
            if (meta() != null && !meta().isNull("current_page"))
                return meta().getInt("current_page");
            else
                return page;
        } catch (JSONException e) {
            e.printStackTrace();
            return page;
        }
    }

    public int getTotal() {
        try {
            if (meta() != null && !meta().isNull("total"))
                return meta().getInt("total");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public JSONObject filters() {
        try {
            if (meta() != null)
                return meta().getJSONObject("filters");
            else
                return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap<String, Object> filterAllows() {
        if (filters() != null) {
            HashMap hashMap = new HashMap();
            JSONObject allowed = null;
            try {
                allowed = filters().getJSONObject("allowed");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Iterator<String> keys = allowed.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                try {
                    hashMap.put(key, allowed.get(key));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return hashMap;
        } else
            return null;
    }

    public ArrayList<String> filtersAllowsKeys() {
        if (filterAllows() != null) {
            return new ArrayList(filterAllows().keySet());
        } else
            return null;
    }

    public Object filterAllowed(String key) {
        if (filterAllows() != null)
            if (filtersAllowsKeys().contains(key))
                return filterAllows().get(key);
            else
                return null;
        else
            return null;
    }

    public HashMap filterCurrents() {
        if (filters() != null) {
            if (!filters().isNull("current")) {
                try {
                    if ("org.json.JSONObject".equals(filters().get("current").getClass().getName())) {
                        JSONObject current = filters().getJSONObject("current");
                        HashMap hashMap = new HashMap();
                        Iterator<String> keys = current.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            hashMap.put(key, current.get(key));
                        }
                        return hashMap;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public boolean filterCurrent(String key) {
        if (filters() != null) {
            if (!filters().isNull("current")) {
                try {
                    if ("org.json.JSONObject".equals(filters().get("current").getClass().getName())) {
                        JSONObject current = filters().getJSONObject("current");
                        HashMap hashMap = new HashMap();
                        Iterator<String> keys = current.keys();
                        while (keys.hasNext()) {
                            String key1 = keys.next();
                            if (key.equals(key1))
                                return true;
                        }
                        return false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public ArrayList filterSingle(String key) {
        if (filtersAllowsKeys() != null) {
            if (filtersAllowsKeys().contains(key)) {
                ArrayList arrayList = new ArrayList();

            }
        }
        return null;
    }

    public JSONArray toObject() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < size(); i++) {
            jsonArray.put(data().get(i).toObject());
        }
        return jsonArray;
    }


    public ArrayList<TypeModel> data() {
        return models;
    }

    public int size() {
        return models.size();
    }

    @NonNull
    @Override
    public String toString() {
        String outPut = "";
        for (TypeModel model : models) {
            outPut += model + "\n";
        }
        return "models=" + outPut +
                '}';
    }
}
