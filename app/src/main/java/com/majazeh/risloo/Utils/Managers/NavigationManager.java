package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;

import androidx.navigation.NavController;

import com.majazeh.risloo.R;

public class NavigationManager {

    public static String currentLocation(Activity activity, NavController navController) {
        String currentLocation = navController.getCurrentDestination().getLabel().toString();

        switch (currentLocation) {
            case "fragment_dashboard":
                return activity.getResources().getString(R.string.DashboardFragment);
            case "fragment_centers":
                return activity.getResources().getString(R.string.DashboardFragment) + " " + activity.getResources().getString(R.string.CentersFragment);
            case "fragment_users":
                return activity.getResources().getString(R.string.DashboardFragment) + " " + activity.getResources().getString(R.string.UsersFragment);
            case "fragment_scales":
                return activity.getResources().getString(R.string.DashboardFragment) + " " + activity.getResources().getString(R.string.ScalesFragment);
            case "fragment_samples":
                return activity.getResources().getString(R.string.DashboardFragment) + " " + activity.getResources().getString(R.string.SamplesFragment);
            case "fragment_documents":
                return activity.getResources().getString(R.string.DashboardFragment) + " " + activity.getResources().getString(R.string.DocumentsFragment);
            case "fragment_settings":
                return activity.getResources().getString(R.string.DashboardFragment) + " " + activity.getResources().getString(R.string.SettingsFragment);
            case "fragment_account":
                return activity.getResources().getString(R.string.DashboardFragment) + " " + activity.getResources().getString(R.string.AccountFragment);
            case "fragment_edit_account":
                return activity.getResources().getString(R.string.DashboardFragment) + " " + activity.getResources().getString(R.string.AccountFragment) + " " + activity.getResources().getString(R.string.EditAccountFragment);
            default:
                return "";
        }
    }

}
