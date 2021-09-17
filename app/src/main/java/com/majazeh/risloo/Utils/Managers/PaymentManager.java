package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.net.Uri;

import androidx.navigation.NavDirections;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Paymont;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Payment;
import com.mre.ligheh.Model.TypeModel.PaymentModel;

import java.util.HashMap;

public class PaymentManager {

    /*
    ---------- Voids ----------
    */

    public static void request(Activity activity, PaymentModel model) {
        DialogManager.showPaymentDialog(activity, "request", model);

        HashMap data = new HashMap<>();
        HashMap header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());

        data.put("authorized_key", model.getAuthorized_key());

        Payment.auth(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                PaymentModel model = (PaymentModel) object;

                activity.runOnUiThread(() -> {
                    DialogManager.dismissPaymentDialog();
                    IntentManager.browser(activity, model.getRedirect());
                });
            }

            @Override
            public void onFailure(String response) {
                activity.runOnUiThread(() -> {
                    Paymont.getInstance().clearPayment();
                });
            }
        });
    }

    public static void callback(Activity activity) {
        Uri uri = activity.getIntent().getData();

        if (uri != null) {
            if (Paymont.getInstance().getDestination() == R.id.reserveScheduleFragment) {
                NavDirections action = NavigationMainDirections.actionGlobalReserveScheduleFragment(Paymont.getInstance().getTypeModel());
                ((MainActivity) activity).navController.navigate(action);
            } else if (Paymont.getInstance().getDestination() == R.id.paymentsFragment) {
                NavDirections action = NavigationMainDirections.actionGlobalPaymentsFragment();
                ((MainActivity) activity).navController.navigate(action);
            }

            // TODO : Call finalize() method and pass PaymentModel that was getted form uri
        }
    }

    public static void finalize(Activity activity, PaymentModel model) {
        DialogManager.showPaymentDialog(activity, "finalize", model);

        HashMap data = new HashMap<>();
        HashMap header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());

        data.put("authorized_key", model.getAuthorized_key());

        Payment.auth(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                PaymentModel model = (PaymentModel) object;

                activity.runOnUiThread(() -> {
                    DialogManager.dismissPaymentDialog();

                    if (Paymont.getInstance().getDestination() == R.id.reserveScheduleFragment) {

                    } else if (Paymont.getInstance().getDestination() == R.id.paymentsFragment) {

                    }
                });
            }

            @Override
            public void onFailure(String response) {
                activity.runOnUiThread(() -> {
                    Paymont.getInstance().clearPayment();
                });
            }
        });
    }

}