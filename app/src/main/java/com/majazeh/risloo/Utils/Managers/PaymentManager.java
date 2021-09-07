package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

import androidx.navigation.NavDirections;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;

public class PaymentManager {

    public static void request(Activity activity, String url) {
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://dev.risloo.ir/a.html"));
        } catch (ActivityNotFoundException e) {
            ToastManager.showDefaultToast(activity, activity.getResources().getString(R.string.ToastActivityException));
        }
        activity.startActivity(intent);
    }

    public static void callback(Activity activity) {
        Uri uri = activity.getIntent().getData();

        if (uri != null) {
            NavDirections action = NavigationMainDirections.actionGlobalReserveScheduleFragment(((MainActivity) activity).singleton.getPaymentAuthScheduleModel());
            ((MainActivity) activity).navController.navigate(action);
        }
    }

}
