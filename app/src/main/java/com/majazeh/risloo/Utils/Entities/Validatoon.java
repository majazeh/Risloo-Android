package com.majazeh.risloo.utils.entities;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.majazeh.risloo.R;
import com.majazeh.risloo.databinding.FragmentAuthLoginBinding;
import com.majazeh.risloo.databinding.FragmentAuthPasswordBinding;
import com.majazeh.risloo.databinding.FragmentAuthPasswordChangeBinding;
import com.majazeh.risloo.databinding.FragmentAuthPasswordRecoverBinding;
import com.majazeh.risloo.databinding.FragmentAuthPinBinding;
import com.majazeh.risloo.databinding.FragmentAuthRegisterBinding;
import com.majazeh.risloo.databinding.FragmentBanksBinding;
import com.majazeh.risloo.databinding.FragmentCreateBillBinding;
import com.majazeh.risloo.databinding.FragmentCreateCaseBinding;
import com.majazeh.risloo.databinding.FragmentCreateCaseUserBinding;
import com.majazeh.risloo.databinding.FragmentCreateCenterBinding;
import com.majazeh.risloo.databinding.FragmentCreateCenterUserBinding;
import com.majazeh.risloo.databinding.FragmentCreateClientReportBinding;
import com.majazeh.risloo.databinding.FragmentCreateDocumentBinding;
import com.majazeh.risloo.databinding.FragmentCreatePlatformBinding;
import com.majazeh.risloo.databinding.FragmentCreatePracticeBinding;
import com.majazeh.risloo.databinding.FragmentCreateRoomBinding;
import com.majazeh.risloo.databinding.FragmentCreateRoomUserBinding;
import com.majazeh.risloo.databinding.FragmentCreateSampleBinding;
import com.majazeh.risloo.databinding.FragmentCreateSessionUserBinding;
import com.majazeh.risloo.databinding.FragmentCreateTreasuryBinding;
import com.majazeh.risloo.databinding.FragmentCreateUserBinding;
import com.majazeh.risloo.databinding.FragmentEditCenterUserBinding;
import com.majazeh.risloo.databinding.FragmentEditPlatformBinding;
import com.majazeh.risloo.databinding.FragmentEditTreasuryBinding;
import com.majazeh.risloo.databinding.FragmentPaymentsBinding;
import com.majazeh.risloo.databinding.FragmentReserveScheduleBinding;
import com.majazeh.risloo.databinding.SheetBulkSampleBinding;
import com.majazeh.risloo.utils.managers.SnackManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class Validatoon {

    // Objects
    private final Activity activity;

    /*
    ---------- Intialize ----------
    */

    public Validatoon(@NonNull Activity activity) {
        this.activity = activity;
    }

    /*
    ---------- Func's ----------
    */

    public void emptyValid(LinearLayout errorLayout, TextView errorTextView) {
        errorLayout.setVisibility(View.VISIBLE);
        errorTextView.setText(activity.getResources().getString(R.string.AppInputEmpty));
    }

    public void showValid(LinearLayout errorLayout, TextView errorTextView, String value) {
        errorLayout.setVisibility(View.VISIBLE);
        errorTextView.setText(value);
    }

    public void hideValid(LinearLayout errorLayout, TextView errorTextView) {
        errorLayout.setVisibility(View.GONE);
        errorTextView.setText("");
    }

    /*
    ---------- New's ----------
    */

    public void requestValid(String response, ViewBinding binding) {
        try {
            JSONObject responseObject = new JSONObject(response);
            if (!responseObject.isNull("errors")) {
                JSONObject errorsObject = responseObject.getJSONObject("errors");

                Iterator<String> keys = (errorsObject.keys());
                StringBuilder allErrors = new StringBuilder();

                while (keys.hasNext()) {
                    String key = keys.next();
                    StringBuilder keyErrors = new StringBuilder();

                    for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                        String error = errorsObject.getJSONArray(key).getString(i);

                        keyErrors.append(error);
                        keyErrors.append("\n");

                        allErrors.append(error);
                        allErrors.append("\n");
                    }

                    String value = keyErrors.substring(0, keyErrors.length() - 1);

                    // -------------------- Auth

                    if (binding instanceof FragmentAuthLoginBinding) {
                        if (key.equals("authorized_key")) {
                            showValid(((FragmentAuthLoginBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthLoginBinding) binding).errorIncludeLayout.errorTextView, value);
                        }

                    } else if (binding instanceof FragmentAuthPasswordBinding) {
                        if (key.equals("password")) {
                            showValid(((FragmentAuthPasswordBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPasswordBinding) binding).errorIncludeLayout.errorTextView, value);
                        }

                    } else if (binding instanceof FragmentAuthPasswordChangeBinding) {
                        if (key.equals("password")) {
                            showValid(((FragmentAuthPasswordChangeBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPasswordChangeBinding) binding).errorIncludeLayout.errorTextView, value);
                        }

                    } else if (binding instanceof FragmentAuthPasswordRecoverBinding) {
                        if (key.equals("mobile")) {
                            showValid(((FragmentAuthPasswordRecoverBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPasswordRecoverBinding) binding).errorIncludeLayout.errorTextView, value);
                        }

                    } else if (binding instanceof FragmentAuthPinBinding) {
                        if (key.equals("code")) {
                            showValid(((FragmentAuthPinBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPinBinding) binding).errorIncludeLayout.errorTextView, value);
                        }

                    } else if (binding instanceof FragmentAuthRegisterBinding) {
                        if (key.equals("mobile")) {
                            showValid(((FragmentAuthRegisterBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthRegisterBinding) binding).errorIncludeLayout.errorTextView, value);
                        }

                    // -------------------- Create

                    } else if (binding instanceof FragmentCreateBillBinding) {
                        switch (key) {
                            case "title":
                                showValid(((FragmentCreateBillBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).titleErrorLayout.errorTextView, value);
                                break;
                            case "user_id":
                                showValid(((FragmentCreateBillBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).referenceErrorLayout.errorTextView, value);
                                break;
                            case "type":
                                showValid(((FragmentCreateBillBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).typeErrorLayout.errorTextView, value);
                                break;
                            case "treasury":
                                showValid(((FragmentCreateBillBinding) binding).treasuryErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).treasuryErrorLayout.errorTextView, value);
                                break;
                            case "amount":
                                showValid(((FragmentCreateBillBinding) binding).amountErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).amountErrorLayout.errorTextView, value);
                                break;
                        }

                    } else if (binding instanceof FragmentCreateCaseBinding) {
                        switch (key) {
                            case "title":
                                showValid(((FragmentCreateCaseBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateCaseBinding) binding).titleErrorLayout.errorTextView, value);
                                break;
                            case "client_id":
                                showValid(((FragmentCreateCaseBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateCaseBinding) binding).referenceErrorLayout.errorTextView, value);
                                break;
                            case "problem":
                                showValid(((FragmentCreateCaseBinding) binding).problemErrorLayout.getRoot(), ((FragmentCreateCaseBinding) binding).problemErrorLayout.errorTextView, value);
                                break;
                            case "tags":
                                showValid(((FragmentCreateCaseBinding) binding).tagsErrorLayout.getRoot(), ((FragmentCreateCaseBinding) binding).tagsErrorLayout.errorTextView, value);
                                break;
                        }

                    } else if (binding instanceof FragmentCreateCaseUserBinding) {
                        if (key.equals("client_id")) {
                            showValid(((FragmentCreateCaseUserBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateCaseUserBinding) binding).referenceErrorLayout.errorTextView, value);
                        }

                    } else if (binding instanceof FragmentCreateCenterBinding) {
                        switch (key) {
                            case "type":
                                showValid(((FragmentCreateCenterBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).typeErrorLayout.errorTextView, value);
                                break;
                            case "manager_id":
                                showValid(((FragmentCreateCenterBinding) binding).managerErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).managerErrorLayout.errorTextView, value);
                                break;
                            case "title":
                                showValid(((FragmentCreateCenterBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).titleErrorLayout.errorTextView, value);
                                break;
                            case "address":
                                showValid(((FragmentCreateCenterBinding) binding).addressErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).addressErrorLayout.errorTextView, value);
                                break;
                            case "description":
                                showValid(((FragmentCreateCenterBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).descriptionErrorLayout.errorTextView, value);
                                break;
                            case "avatar":
                                showValid(((FragmentCreateCenterBinding) binding).avatarErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).avatarErrorLayout.errorTextView, value);
                                break;
                            case "phone_numbers":
                                showValid(((FragmentCreateCenterBinding) binding).phonesErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).phonesErrorLayout.errorTextView, value);
                                break;
                        }

                    } else if (binding instanceof FragmentCreateCenterUserBinding) {
                        switch (key) {
                            case "mobile":
                                showValid(((FragmentCreateCenterUserBinding) binding).mobileErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).mobileErrorLayout.errorTextView, value);
                                break;
                            case "position":
                                showValid(((FragmentCreateCenterUserBinding) binding).positionErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).positionErrorLayout.errorTextView, value);
                                break;
                            case "room_id":
                                showValid(((FragmentCreateCenterUserBinding) binding).roomErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).roomErrorLayout.errorTextView, value);
                                break;
                            case "nickname":
                                showValid(((FragmentCreateCenterUserBinding) binding).nicknameErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).nicknameErrorLayout.errorTextView, value);
                                break;
                            case "create_case":
                                showValid(((FragmentCreateCenterUserBinding) binding).caseErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).caseErrorLayout.errorTextView, value);
                                break;
                        }

                    } else if (binding instanceof FragmentCreateClientReportBinding) {
                        switch (key) {
                            case "encryption":
                                showValid(((FragmentCreateClientReportBinding) binding).encryptionErrorLayout.getRoot(), ((FragmentCreateClientReportBinding) binding).encryptionErrorLayout.errorTextView, value);
                                break;
                            case "description":
                                showValid(((FragmentCreateClientReportBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateClientReportBinding) binding).descriptionErrorLayout.errorTextView, value);
                                break;
                        }

                    } else if (binding instanceof FragmentCreateDocumentBinding) {
                        switch (key) {
                            case "name":
                                showValid(((FragmentCreateDocumentBinding) binding).nameErrorLayout.getRoot(), ((FragmentCreateDocumentBinding) binding).nameErrorLayout.errorTextView, value);
                                break;
                            case "description":
                                showValid(((FragmentCreateDocumentBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateDocumentBinding) binding).descriptionErrorLayout.errorTextView, value);
                                break;
                            case "file":
                                showValid(((FragmentCreateDocumentBinding) binding).fileErrorLayout.getRoot(), ((FragmentCreateDocumentBinding) binding).fileErrorLayout.errorTextView, value);
                                break;
                        }

                    } else if (binding instanceof FragmentCreatePlatformBinding) {
                        switch (key) {
                            case "title":
                                showValid(((FragmentCreatePlatformBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).titleErrorLayout.errorTextView, value);
                                break;
                            case "type":
                                showValid(((FragmentCreatePlatformBinding) binding).sessionTypeErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).sessionTypeErrorLayout.errorTextView, value);
                                break;
                            case "identifier_type":
                                showValid(((FragmentCreatePlatformBinding) binding).indentifierTypeErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).indentifierTypeErrorLayout.errorTextView, value);
                                break;
                            case "identifier":
                                showValid(((FragmentCreatePlatformBinding) binding).indentifierErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).indentifierErrorLayout.errorTextView, value);
                                break;
                            case "selected":
                                showValid(((FragmentCreatePlatformBinding) binding).sessionErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).sessionErrorLayout.errorTextView, value);
                                break;
                            case "available":
                                showValid(((FragmentCreatePlatformBinding) binding).availableErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).availableErrorLayout.errorTextView, value);
                                break;
                        }

                    } else if (binding instanceof FragmentCreatePracticeBinding) {
                        switch (key) {
                            case "name":
                                showValid(((FragmentCreatePracticeBinding) binding).nameErrorLayout.getRoot(), ((FragmentCreatePracticeBinding) binding).nameErrorLayout.errorTextView, value);
                                break;
                            case "description":
                                showValid(((FragmentCreatePracticeBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreatePracticeBinding) binding).descriptionErrorLayout.errorTextView, value);
                                break;
                            case "file":
                                showValid(((FragmentCreatePracticeBinding) binding).fileErrorLayout.getRoot(), ((FragmentCreatePracticeBinding) binding).fileErrorLayout.errorTextView, value);
                                break;
                        }

                    } else if (binding instanceof FragmentCreateRoomBinding) {
                        if (key.equals("psychologist_id")) {
                            showValid(((FragmentCreateRoomBinding) binding).psychologyErrorLayout.getRoot(), ((FragmentCreateRoomBinding) binding).psychologyErrorLayout.errorTextView, value);
                        }

                    } else if (binding instanceof FragmentCreateRoomUserBinding) {
                        if (key.equals("user_id")) {
                            showValid(((FragmentCreateRoomUserBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateRoomUserBinding) binding).referenceErrorLayout.errorTextView, value);
                        }

                    } else if (binding instanceof FragmentCreateSampleBinding) {
                        // TODO : Place Code Here

                    } else if (binding instanceof FragmentCreateSessionUserBinding) {
                        switch (key) {
                            case "field":
                                showValid(((FragmentCreateSessionUserBinding) binding).axisErrorLayout.getRoot(), ((FragmentCreateSessionUserBinding) binding).axisErrorLayout.errorTextView, value);
                                break;
                            case "session_platform":
                                showValid(((FragmentCreateSessionUserBinding) binding).platformErrorLayout.getRoot(), ((FragmentCreateSessionUserBinding) binding).platformErrorLayout.errorTextView, value);
                                break;
                            case "client_id":
                                showValid(((FragmentCreateSessionUserBinding) binding).clientErrorLayout.getRoot(), ((FragmentCreateSessionUserBinding) binding).clientErrorLayout.errorTextView, value);
                                break;
                            case "description":
                                showValid(((FragmentCreateSessionUserBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateSessionUserBinding) binding).descriptionErrorLayout.errorTextView, value);
                                break;
                        }

                    } else if (binding instanceof FragmentCreateTreasuryBinding) {
                        switch (key) {
                            case "title":
                                showValid(((FragmentCreateTreasuryBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateTreasuryBinding) binding).titleErrorLayout.errorTextView, value);
                                break;
                            case "region_id":
                                showValid(((FragmentCreateTreasuryBinding) binding).regionErrorLayout.getRoot(), ((FragmentCreateTreasuryBinding) binding).regionErrorLayout.errorTextView, value);
                                break;
                        }

                    } else if (binding instanceof FragmentCreateUserBinding) {
                        switch (key) {
                            case "name":
                                showValid(((FragmentCreateUserBinding) binding).nameErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).nameErrorLayout.errorTextView, value);
                                break;
                            case "mobile":
                                showValid(((FragmentCreateUserBinding) binding).mobileErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).mobileErrorLayout.errorTextView, value);
                                break;
                            case "email":
                                showValid(((FragmentCreateUserBinding) binding).emailErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).emailErrorLayout.errorTextView, value);
                                break;
                            case "password":
                                showValid(((FragmentCreateUserBinding) binding).passwordErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).passwordErrorLayout.errorTextView, value);
                                break;
                            case "birthday":
                                showValid(((FragmentCreateUserBinding) binding).birthdayErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).birthdayErrorLayout.errorTextView, value);
                                break;
                            case "status":
                                showValid(((FragmentCreateUserBinding) binding).statusErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).statusErrorLayout.errorTextView, value);
                                break;
                            case "type":
                                showValid(((FragmentCreateUserBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).typeErrorLayout.errorTextView, value);
                                break;
                            case "gender":
                                showValid(((FragmentCreateUserBinding) binding).genderErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).genderErrorLayout.errorTextView, value);
                                break;
                        }

                    } else if (binding instanceof FragmentReserveScheduleBinding) {
                        // TODO : Place Code Here

                    // -------------------- Edit

                    } else if (binding instanceof FragmentEditCenterUserBinding) {
                        switch (key) {
                            case "position":
                                showValid(((FragmentEditCenterUserBinding) binding).positionErrorLayout.getRoot(), ((FragmentEditCenterUserBinding) binding).positionErrorLayout.errorTextView, value);
                                break;
                            case "name":
                                showValid(((FragmentEditCenterUserBinding) binding).nicknameErrorLayout.getRoot(), ((FragmentEditCenterUserBinding) binding).nicknameErrorLayout.errorTextView, value);
                                break;
                            case "status":
                                showValid(((FragmentEditCenterUserBinding) binding).statusErrorLayout.getRoot(), ((FragmentEditCenterUserBinding) binding).statusErrorLayout.errorTextView, value);
                                break;
                        }

                    } else if (binding instanceof FragmentEditPlatformBinding) {
                        switch (key) {
                            case "title":
                                showValid(((FragmentEditPlatformBinding) binding).titleErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).titleErrorLayout.errorTextView, value);
                                break;
                            case "type":
                                showValid(((FragmentEditPlatformBinding) binding).sessionTypeErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).sessionTypeErrorLayout.errorTextView, value);
                                break;
                            case "identifier_type":
                                showValid(((FragmentEditPlatformBinding) binding).indentifierTypeErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).indentifierTypeErrorLayout.errorTextView, value);
                                break;
                            case "identifier":
                                showValid(((FragmentEditPlatformBinding) binding).indentifierErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).indentifierErrorLayout.errorTextView, value);
                                break;
                            case "selected":
                                showValid(((FragmentEditPlatformBinding) binding).sessionErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).sessionErrorLayout.errorTextView, value);
                                break;
                            case "available":
                                showValid(((FragmentEditPlatformBinding) binding).availableErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).availableErrorLayout.errorTextView, value);
                                break;
                        }

                    } else if (binding instanceof FragmentEditTreasuryBinding) {
                        if (key.equals("title")) {
                            showValid(((FragmentEditTreasuryBinding) binding).titleErrorLayout.getRoot(), ((FragmentEditTreasuryBinding) binding).titleErrorLayout.errorTextView, value);
                        }

                    // -------------------- Index

                    } else if (binding instanceof FragmentBanksBinding) {
                        switch (key) {
                            case "iban":
                                showValid(((FragmentBanksBinding) binding).ibanErrorLayout.getRoot(), ((FragmentBanksBinding) binding).ibanErrorLayout.errorTextView, value);
                                break;
                            case "iban_id":
                                showValid(((FragmentBanksBinding) binding).accountErrorLayout.getRoot(), ((FragmentBanksBinding) binding).accountErrorLayout.errorTextView, value);
                                break;
                            case "type":
                                showValid(((FragmentBanksBinding) binding).typeErrorLayout.getRoot(), ((FragmentBanksBinding) binding).typeErrorLayout.errorTextView, value);
                                break;
                            case "amount":
                                showValid(((FragmentBanksBinding) binding).amountErrorLayout.getRoot(), ((FragmentBanksBinding) binding).amountErrorLayout.errorTextView, value);
                                break;
                            case "weekday":
                                showValid(((FragmentBanksBinding) binding).weekdayErrorLayout.getRoot(), ((FragmentBanksBinding) binding).weekdayErrorLayout.errorTextView, value);
                                break;
                            case "day":
                                showValid(((FragmentBanksBinding) binding).monthdayErrorLayout.getRoot(), ((FragmentBanksBinding) binding).monthdayErrorLayout.errorTextView, value);
                                break;
                        }

                    } else if (binding instanceof FragmentPaymentsBinding) {
                        switch (key) {
                            case "treasury_id":
                                showValid(((FragmentPaymentsBinding) binding).treasuryErrorLayout.getRoot(), ((FragmentPaymentsBinding) binding).treasuryErrorLayout.errorTextView, value);
                                break;
                            case "amount":
                                showValid(((FragmentPaymentsBinding) binding).amountErrorLayout.getRoot(), ((FragmentPaymentsBinding) binding).amountErrorLayout.errorTextView, value);
                                break;
                        }

                    // -------------------- Sheet

                    } else if (binding instanceof SheetBulkSampleBinding) {
                        if (key.equals("nickname")) {
                            showValid(((SheetBulkSampleBinding) binding).nicknameErrorLayout.getRoot(), ((SheetBulkSampleBinding) binding).nicknameErrorLayout.errorTextView, value);
                        }

                    }
                }

                // -------------------- End

                SnackManager.showSnackError(activity, allErrors.substring(0, allErrors.length() - 1));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}