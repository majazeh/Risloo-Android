package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.net.Uri;

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

//        if (uri != null) {
//            if (authorizedKey != null) {
//                System.out.println(authorizedKey);
//                if (authorizedKey.equals(Paymont.getInstance().getPaymentModel().getAuthorized_key())) {
//            switch (Paymont.getInstance().getDestination()) {
//                case R.id.billingsFragment: {
//                    NavDirections action = NavigationMainDirections.actionGlobalBillFragment(Paymont.getInstance().getTypeModel());
//                    ((MainActivity) activity).navController.navigate(action);
//                }
//                break;
//                case R.id.sessionFragment: {
//                    NavDirections action = NavigationMainDirections.actionGlobalSessionFragment(Paymont.getInstance().getTypeModel());
//                    ((MainActivity) activity).navController.navigate(action);
//                }
//                break;
//                default: {
//                    NavDirections action = NavigationMainDirections.actionGlobalPaymentsFragment(null);
//                    ((MainActivity) activity).navController.navigate(action);
//                }
//                break;
//            }
//                }
//
//            } else {
//                System.out.println("auth is null");
//            }
//        }
    }

}