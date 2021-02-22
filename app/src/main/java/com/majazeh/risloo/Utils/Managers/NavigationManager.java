package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;

import androidx.navigation.NavController;

import com.majazeh.risloo.R;

public class NavigationManager {

    public static String previousLocation(Activity activity, NavController navController) {
        String destination = navController.getCurrentDestination().getLabel().toString();

        switch (destination) {
            case "fragment_dashboard":
                return "";
            case "fragment_centers":
            case "fragment_users":
            case "fragment_scales":
            case "fragment_samples":
            case "fragment_documents":
            case "fragment_settings":
            case "fragment_account":
                return activity.getResources().getString(R.string.DashboardFragment) + "  >  ";
            case "fragment_edit_account":
                return activity.getResources().getString(R.string.DashboardFragment) + "  >  " + activity.getResources().getString(R.string.AccountFragment) + "  >  ";
            default:
                return "";
        }
    }

    public static String currentLocation(Activity activity, NavController navController) {
        String destination = navController.getCurrentDestination().getLabel().toString();

        switch (destination) {
            case "fragment_dashboard":
                return activity.getResources().getString(R.string.DashboardFragment);
            case "fragment_centers":
                return activity.getResources().getString(R.string.CentersFragment);
            case "fragment_users":
                return activity.getResources().getString(R.string.UsersFragment);
            case "fragment_scales":
                return activity.getResources().getString(R.string.ScalesFragment);
            case "fragment_samples":
                return activity.getResources().getString(R.string.SamplesFragment);
            case "fragment_documents":
                return activity.getResources().getString(R.string.DocumentsFragment);
            case "fragment_settings":
                return activity.getResources().getString(R.string.SettingsFragment);
            case "fragment_account":
                return activity.getResources().getString(R.string.AccountFragment);
            case "fragment_edit_account":
                return activity.getResources().getString(R.string.EditAccountFragment);
            default:
                return "";
        }
    }

}
