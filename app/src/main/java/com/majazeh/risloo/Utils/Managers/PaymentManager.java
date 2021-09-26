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
            String authorizedKey = uri.getQueryParameter("authorized_key");

            if (authorizedKey != null && !authorizedKey.equals("")) {
                if (Paymont.getInstance().getPaymentModel() != null && Paymont.getInstance().getPaymentModel().getAuthorized_key().equals(authorizedKey)) {
                    switch (Paymont.getInstance().getDestination()) {
                        case R.id.billingsFragment:
                        case R.id.sessionFragment: {
                            NavDirections action = NavigationMainDirections.actionGlobalBillFragment(Paymont.getInstance().getTypeModel());
                            ((MainActivity) activity).navController.navigate(action);
                        } break;
                        case R.id.paymentsFragment: {
                            NavDirections action = NavigationMainDirections.actionGlobalPaymentsFragment(null);
                            ((MainActivity) activity).navController.navigate(action);
                        } break;
                        case R.id.reserveScheduleFragment: {
                            NavDirections action = NavigationMainDirections.actionGlobalReserveScheduleFragment(Paymont.getInstance().getTypeModel());
                            ((MainActivity) activity).navController.navigate(action);
                        } break;
                    }
                }
            }

            if (Paymont.getInstance().getPaymentModel() != null && Paymont.getInstance().getDestination() != R.id.reserveScheduleFragment) {
                Paymont.getInstance().clearPayment();
            }
        }
    }

}