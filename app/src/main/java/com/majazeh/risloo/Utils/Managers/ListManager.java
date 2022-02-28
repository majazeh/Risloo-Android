package com.majazeh.risloo.utils.managers;

import android.app.Activity;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.entities.Permissoon;
import com.majazeh.risloo.utils.entities.Singleton;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListManager {

    /*
    ---------- Funcs ----------
    */

    public static ArrayList<TypeModel> getDrawer(Activity activity, Permissoon permissoon, Singleton singleton) {
        ArrayList<TypeModel> items = new ArrayList<>();

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> descs = new ArrayList<>();
        ArrayList<Integer> images = new ArrayList<>();

        // Dashboard
        titles.add(activity.getResources().getString(R.string.MainTitleDashboard));
        descs.add(activity.getResources().getString(R.string.MainDescDashboard));
        images.add(R.drawable.ic_tachometer_alt_light);

        // Centers
        titles.add(activity.getResources().getString(R.string.MainTitleCenters));
        descs.add(activity.getResources().getString(R.string.MainDescCenters));
        images.add(R.drawable.ic_building_light);

        // Sessions
        titles.add(activity.getResources().getString(R.string.MainTitleSessions));
        descs.add(activity.getResources().getString(R.string.MainDescSessions));
        images.add(R.drawable.ic_user_friends_light);

        // Users
        if (permissoon.showUsers(singleton.getUserModel())) {
            titles.add(activity.getResources().getString(R.string.MainTitleUsers));
            descs.add(activity.getResources().getString(R.string.MainDescUsers));
            images.add(R.drawable.ic_users_light);
        }

        // Samples
        titles.add(activity.getResources().getString(R.string.MainTitleSamples));
        descs.add(activity.getResources().getString(R.string.MainDescSamples));
        images.add(R.drawable.ic_vial_light);

        // BulkSamples
        if (permissoon.showBulkSamples(singleton.getUserModel())) {
            titles.add(activity.getResources().getString(R.string.MainTitleBulkSamples));
            descs.add(activity.getResources().getString(R.string.MainDescBulkSamples));
            images.add(R.drawable.ic_users_medical_light);
        }

        // Scales
        if (permissoon.showScales(singleton.getUserModel())) {
            titles.add(activity.getResources().getString(R.string.MainTitleScales));
            descs.add(activity.getResources().getString(R.string.MainDescScales));
            images.add(R.drawable.ic_balance_scale_light);
        }

        // Downloads
        titles.add(activity.getResources().getString(R.string.MainTitleDownloads));
        descs.add(activity.getResources().getString(R.string.MainDescDownloads));
        images.add(R.drawable.ic_arrow_to_bottom_light);

        for (int i = 0; i < titles.size(); i++) {
            try {
                JSONObject object = new JSONObject();
                object.put("title", titles.get(i));
                object.put("desc", descs.get(i));
                object.put("image", images.get(i));

                TypeModel model = new TypeModel(object);

                items.add(model);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return items;
    }

    public static ArrayList<String> getToolbar(Activity activity) {
        ArrayList<String> items = new ArrayList<>();

        items.add(activity.getResources().getString(R.string.MainToolbarMe));
        items.add(activity.getResources().getString(R.string.MainToolbarAccounting));
        items.add(activity.getResources().getString(R.string.MainToolbarPayments));
        items.add(activity.getResources().getString(R.string.MainToolbarLogout));
        items.add("");

        return items;
    }

}