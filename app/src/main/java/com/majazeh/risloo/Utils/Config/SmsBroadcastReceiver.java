package com.majazeh.risloo.Utils.Config;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.Views.Fragments.Auth.AuthPinFragment;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

            switch(status.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:
                    String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);

                    if (context instanceof AuthActivity) {
                        Fragment current = ((AuthActivity) context).fragmont.getCurrent();

                        if (current instanceof AuthPinFragment) {
                            // TODO : Extract the code from the message and pass to the input here
                        }
                    }
                    break;
                case CommonStatusCodes.TIMEOUT:
                    System.out.println("onReceiveFail: " + status.getStatusMessage());
                    SnackManager.showDefaultSnack((Activity) context, status.getStatusMessage());
                    break;
            }
        }
    }

}