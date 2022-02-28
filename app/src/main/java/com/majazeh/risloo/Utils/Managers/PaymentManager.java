package com.majazeh.risloo.utils.managers;

import android.app.Activity;
import android.net.Uri;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.instances.Paymont;
import com.majazeh.risloo.views.activities.ActivityMain;
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
        DialogManager.showDialogPayment(activity, "request", model);

        HashMap data = new HashMap<>();
        HashMap header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) activity).singleton.getAuthorization());

        data.put("authorized_key", model.getAuthorizedKey());

        Payment.auth(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                PaymentModel model = (PaymentModel) object;

                activity.runOnUiThread(() -> {
                    DialogManager.dismissDialogPayment();
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

            if (Paymont.getInstance().getPaymentModel() != null && Paymont.getInstance().getDestination() != R.id.fragmentReserveSchedule) {
                Paymont.getInstance().clearPayment();
            }
        }
    }

    private static void finalize(Activity activity) {
        DialogManager.showDialogPayment(activity, "finalize", Paymont.getInstance().getPaymentModel());

        HashMap data = new HashMap<>();
        data.put("user", ((ActivityMain) activity).singleton.getUserModel().getId());
        HashMap header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) activity).singleton.getAuthorization());

        User.dashboard(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                UserModel userModel = (UserModel) object;

                activity.runOnUiThread(() -> {
                    ((ActivityMain) activity).singleton.update(userModel);
                    ((ActivityMain) activity).setData();

                    DialogManager.dismissDialogPayment();
                    navigate(activity);
                });
            }

            @Override
            public void onFailure(String response) {
                activity.runOnUiThread(() -> {
                    DialogManager.dismissDialogPayment();
                    navigate(activity);
                });
            }
        });
    }

    private static void navigate(Activity activity) {
        switch (Paymont.getInstance().getDestination()) {
            case R.id.fragmentBillings:
            case R.id.fragmentSession:
                ((ActivityMain) activity).navigatoon.navigateToFragmentBill(Paymont.getInstance().getTypeModel());
                break;
            case R.id.fragmentPayments:
                ((ActivityMain) activity).navigatoon.navigateToFragmentPayments(null);
                break;
            case R.id.fragmentReserveSchedule:
                ((ActivityMain) activity).navigatoon.navigateToFragmentReserveSchedule(Paymont.getInstance().getTypeModel());
                break;
        }
    }

}