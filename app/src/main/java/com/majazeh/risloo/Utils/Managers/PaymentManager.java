package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.net.Uri;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Instances.Paymont;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Payment;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.PaymentModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

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

        data.put("authorized_key", model.getAuthorizedKey());

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
            String authorizedKey = uri.getQueryParameter("authorized_key");

            if (authorizedKey != null && !authorizedKey.equals("")) {
                if (Paymont.getInstance().getPaymentModel() != null && Paymont.getInstance().getPaymentModel().getAuthorizedKey().equals(authorizedKey)) {
                    finalize(activity);
                }
            }

            if (Paymont.getInstance().getPaymentModel() != null && Paymont.getInstance().getDestination() != R.id.reserveScheduleFragment) {
                Paymont.getInstance().clearPayment();
            }
        }
    }

    private static void finalize(Activity activity) {
        DialogManager.showPaymentDialog(activity, "finalize", Paymont.getInstance().getPaymentModel());

        HashMap data = new HashMap<>();
        data.put("user", ((MainActivity) activity).singleton.getUserModel().getId());
        HashMap header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());

        User.dashboard(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                UserModel userModel = (UserModel) object;

                activity.runOnUiThread(() -> {
                    ((MainActivity) activity).singleton.update(userModel);
                    ((MainActivity) activity).setData();

                    DialogManager.dismissPaymentDialog();
                    navigate(activity);
                });
            }

            @Override
            public void onFailure(String response) {
                activity.runOnUiThread(() -> {
                    DialogManager.dismissPaymentDialog();
                    navigate(activity);
                });
            }
        });
    }

    private static void navigate(Activity activity) {
        switch (Paymont.getInstance().getDestination()) {
            case R.id.billingsFragment:
            case R.id.sessionFragment:
                ((MainActivity) activity).navigatoon.navigateToBillFragment(Paymont.getInstance().getTypeModel());
                break;
            case R.id.paymentsFragment:
                ((MainActivity) activity).navigatoon.navigateToPaymentsFragment(null);
                break;
            case R.id.reserveScheduleFragment:
                ((MainActivity) activity).navigatoon.navigateToReserveScheduleFragment(Paymont.getInstance().getTypeModel());
                break;
        }
    }

}