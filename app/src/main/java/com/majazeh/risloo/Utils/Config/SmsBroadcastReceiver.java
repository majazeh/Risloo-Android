package com.majazeh.risloo.Utils.Config;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

            switch(status.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:
                    System.out.println("onReceiveSucces: " + status.getStatusMessage());
                    String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);

                    Pattern pattern = Pattern.compile("\\d+");
                    Matcher matcher = pattern.matcher(message);

                    if (matcher.find()) {
                        String code = matcher.group(0);

                        // TODO : Extract the code from the message and pass to the input here
                    }

                    break;
                default:
                    System.out.println("onReceiveFail: " + status.getStatusMessage());
                    break;
            }
        }
    }

}