package com.majazeh.risloo.Utils.managers;

import android.app.Activity;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.entities.Permissoon;
import com.majazeh.risloo.Utils.entities.Singleton;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListManager {

    /*
    ---------- Funcs ----------
    */

    public static ArrayList<TypeModel> getDrawer(Activity activity, Permissoon permissoon, Singleton singleton) {
        ArrayList<TypeModel> values = new ArrayList<>();

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> description = new ArrayList<>();
        ArrayList<Integer> images = new ArrayList<>();

        // Dashboard
        titles.add(activity.getResources().getString(R.string.MainTitleDashboard));
        description.add(activity.getResources().getString(R.string.MainDescDashboard));
        images.add(R.drawable.ic_tachometer_alt_light);

        // Centers
        titles.add(activity.getResources().getString(R.string.MainTitleCenters));
        description.add(activity.getResources().getString(R.string.MainDescCenters));
        images.add(R.drawable.ic_building_light);

        // Sessions
        titles.add(activity.getResources().getString(R.string.MainTitleSessions));
        description.add(activity.getResources().getString(R.string.MainDescSessions));
        images.add(R.drawable.ic_user_friends_light);

        // Users
        if (permissoon.showUsers(singleton.getUserModel())) {
            titles.add(activity.getResources().getString(R.string.MainTitleUsers));
            description.add(activity.getResources().getString(R.string.MainDescUsers));
            images.add(R.drawable.ic_users_light);
        }

        // Samples
        titles.add(activity.getResources().getString(R.string.MainTitleSamples));
        description.add(activity.getResources().getString(R.string.MainDescSamples));
        images.add(R.drawable.ic_vial_light);

        // BulkSamples
        if (permissoon.showBulkSamples(singleton.getUserModel())) {
            titles.add(activity.getResources().getString(R.string.MainTitleBulkSamples));
            description.add(activity.getResources().getString(R.string.MainDescBulkSamples));
            images.add(R.drawable.ic_users_medical_light);
        }

        // Scales
        if (permissoon.showScales(singleton.getUserModel())) {
            titles.add(activity.getResources().getString(R.string.MainTitleScales));
            description.add(activity.getResources().getString(R.string.MainDescScales));
            images.add(R.drawable.ic_balance_scale_light);
        }

        // Downloads
        titles.add(activity.getResources().getString(R.string.MainTitleDownloads));
        description.add(activity.getResources().getString(R.string.MainDescDownloads));
        images.add(R.drawable.ic_arrow_to_bottom_light);

        for (int i = 0; i < titles.size(); i++) {
            try {
                JSONObject object = new JSONObject();
                object.put("title", titles.get(i));
                object.put("description", description.get(i));
                object.put("image", images.get(i));

                TypeModel model = new TypeModel(object);

                values.add(model);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return values;
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