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
import com.majazeh.risloo.databinding.FragmentAuthSerialBinding;
import com.majazeh.risloo.databinding.FragmentBanksBinding;
import com.majazeh.risloo.databinding.FragmentCommissionsBinding;
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
import com.majazeh.risloo.databinding.FragmentCreateScheduleTabPaymentBinding;
import com.majazeh.risloo.databinding.FragmentCreateScheduleTabPlatformBinding;
import com.majazeh.risloo.databinding.FragmentCreateScheduleTabReferenceBinding;
import com.majazeh.risloo.databinding.FragmentCreateScheduleTabSessionBinding;
import com.majazeh.risloo.databinding.FragmentCreateScheduleTabTimeBinding;
import com.majazeh.risloo.databinding.FragmentCreateSessionTabPaymentBinding;
import com.majazeh.risloo.databinding.FragmentCreateSessionTabPlatformBinding;
import com.majazeh.risloo.databinding.FragmentCreateSessionTabSessionBinding;
import com.majazeh.risloo.databinding.FragmentCreateSessionTabTimeBinding;
import com.majazeh.risloo.databinding.FragmentCreateSessionUserBinding;
import com.majazeh.risloo.databinding.FragmentCreateTreasuryBinding;
import com.majazeh.risloo.databinding.FragmentCreateUserBinding;
import com.majazeh.risloo.databinding.FragmentEditCenterTabAvatarBinding;
import com.majazeh.risloo.databinding.FragmentEditCenterTabDetailBinding;
import com.majazeh.risloo.databinding.FragmentEditCenterUserBinding;
import com.majazeh.risloo.databinding.FragmentEditPlatformBinding;
import com.majazeh.risloo.databinding.FragmentEditSessionTabPaymentBinding;
import com.majazeh.risloo.databinding.FragmentEditSessionTabPlatformBinding;
import com.majazeh.risloo.databinding.FragmentEditSessionTabReferenceBinding;
import com.majazeh.risloo.databinding.FragmentEditSessionTabSessionBinding;
import com.majazeh.risloo.databinding.FragmentEditSessionTabTimeBinding;
import com.majazeh.risloo.databinding.FragmentEditTreasuryBinding;
import com.majazeh.risloo.databinding.FragmentEditUserTabAvatarBinding;
import com.majazeh.risloo.databinding.FragmentEditUserTabCryptoBinding;
import com.majazeh.risloo.databinding.FragmentEditUserTabPasswordBinding;
import com.majazeh.risloo.databinding.FragmentEditUserTabPersonalBinding;
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

    private void hideValid(LinearLayout errorLayout, TextView errorTextView) {
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
                        switch (key) {
                            case "scale_id":
                                showValid(((FragmentCreateSampleBinding) binding).scaleErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).scaleErrorLayout.errorTextView, value);
                                break;
                            case "room_id":
                                showValid(((FragmentCreateSampleBinding) binding).roomErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).roomErrorLayout.errorTextView, value);
                                break;
                            case "type":
                                showValid(((FragmentCreateSampleBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).typeErrorLayout.errorTextView, value);
                                break;
                            case "title":
                                showValid(((FragmentCreateSampleBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).titleErrorLayout.errorTextView, value);
                                break;
                            case "members_count":
                                showValid(((FragmentCreateSampleBinding) binding).membersCountErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).membersCountErrorLayout.errorTextView, value);
                                break;
                            case "case_status":
                                showValid(((FragmentCreateSampleBinding) binding).caseStatusErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).caseStatusErrorLayout.errorTextView, value);
                                break;
                            case "problem":
                                showValid(((FragmentCreateSampleBinding) binding).problemErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).problemErrorLayout.errorTextView, value);
                                break;
                            case "case_id":
                                showValid(((FragmentCreateSampleBinding) binding).caseErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).caseErrorLayout.errorTextView, value);
                                break;
                            case "session_id":
                                showValid(((FragmentCreateSampleBinding) binding).sessionErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).sessionErrorLayout.errorTextView, value);
                                break;
                            case "client_id":
                                if (((FragmentCreateSampleBinding) binding).typeTabLayout.getSelectedTabPosition() == 0)
                                    showValid(((FragmentCreateSampleBinding) binding).clientErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).clientErrorLayout.errorTextView, value);
                                else if (((FragmentCreateSampleBinding) binding).typeTabLayout.getSelectedTabPosition() == 1)
                                    showValid(((FragmentCreateSampleBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).referenceErrorLayout.errorTextView, value);

                                break;
                            case "psychologist_description":
                                showValid(((FragmentCreateSampleBinding) binding).psychologyDescriptionErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).psychologyDescriptionErrorLayout.errorTextView, value);
                                break;
                        }

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
                        switch (key) {
                            case "field":
                                showValid(((FragmentReserveScheduleBinding) binding).fieldErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).fieldErrorLayout.errorTextView, value);
                                break;
                            case "session_platform":
                                showValid(((FragmentReserveScheduleBinding) binding).platformErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).platformErrorLayout.errorTextView, value);
                                break;
                            case "client_typ":
                                showValid(((FragmentReserveScheduleBinding) binding).typeErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).typeErrorLayout.errorTextView, value);
                                break;
                            case "case_id":
                                showValid(((FragmentReserveScheduleBinding) binding).caseErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).caseErrorLayout.errorTextView, value);
                                break;
                            case "client_id":
                                if (((FragmentReserveScheduleBinding) binding).typeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.second_radioButton && ((FragmentReserveScheduleBinding) binding).clientIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                                    showValid(((FragmentReserveScheduleBinding) binding).clientErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).clientErrorLayout.errorTextView, value);
                                else if (((FragmentReserveScheduleBinding) binding).typeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.first_radioButton)
                                    showValid(((FragmentReserveScheduleBinding) binding).referenceErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).referenceErrorLayout.errorTextView, value);

                                break;
                            case "problem":
                                showValid(((FragmentReserveScheduleBinding) binding).problemErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).problemErrorLayout.errorTextView, value);
                                break;
                            case "nickname":
                                showValid(((FragmentReserveScheduleBinding) binding).nameErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).nameErrorLayout.errorTextView, value);
                                break;
                            case "description":
                                showValid(((FragmentReserveScheduleBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).descriptionErrorLayout.errorTextView, value);
                                break;
                            case "treasurie_id":
                                showValid(((FragmentReserveScheduleBinding) binding).treasuryErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).treasuryErrorLayout.errorTextView, value);
                                break;
                        }

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

                    } else if (binding instanceof FragmentCommissionsBinding) {
                        if (key.equals("commission")) {
                            showValid(((FragmentCommissionsBinding) binding).shareErrorLayout.getRoot(), ((FragmentCommissionsBinding) binding).shareErrorLayout.errorTextView, value);
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

                    // -------------------- Tab

                    } else if (binding instanceof FragmentEditCenterTabAvatarBinding) {
                        if (key.equals("avatar")) {
                            showValid(((FragmentEditCenterTabAvatarBinding) binding).avatarErrorLayout.getRoot(), ((FragmentEditCenterTabAvatarBinding) binding).avatarErrorLayout.errorTextView, value);
                        }

                    } else if (binding instanceof FragmentEditCenterTabDetailBinding) {
                        switch (key) {
                            case "manager_id":
                                showValid(((FragmentEditCenterTabDetailBinding) binding).managerErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).managerErrorLayout.errorTextView, value);
                                break;
                            case "title":
                                showValid(((FragmentEditCenterTabDetailBinding) binding).titleErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).titleErrorLayout.errorTextView, value);
                                break;
                            case "address":
                                showValid(((FragmentEditCenterTabDetailBinding) binding).addressErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).addressErrorLayout.errorTextView, value);
                                break;
                            case "description":
                                showValid(((FragmentEditCenterTabDetailBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).descriptionErrorLayout.errorTextView, value);
                                break;
                            case "phone_numbers":
                                showValid(((FragmentEditCenterTabDetailBinding) binding).phonesErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).phonesErrorLayout.errorTextView, value);
                                break;
                        }

                    } else if (binding instanceof FragmentEditUserTabAvatarBinding) {
                        if (key.equals("avatar")) {
                            showValid(((FragmentEditUserTabAvatarBinding) binding).avatarErrorLayout.getRoot(), ((FragmentEditUserTabAvatarBinding) binding).avatarErrorLayout.errorTextView, value);
                        }

                    } else if (binding instanceof FragmentEditUserTabPasswordBinding) {
                        switch (key) {
                            case "password":
                                showValid(((FragmentEditUserTabPasswordBinding) binding).currentPasswordErrorLayout.getRoot(), ((FragmentEditUserTabPasswordBinding) binding).currentPasswordErrorLayout.errorTextView, value);
                                break;
                            case "new_password":
                                showValid(((FragmentEditUserTabPasswordBinding) binding).newPasswordErrorLayout.getRoot(), ((FragmentEditUserTabPasswordBinding) binding).newPasswordErrorLayout.errorTextView, value);
                                break;
                        }

                    } else if (binding instanceof FragmentEditUserTabPersonalBinding) {
                        switch (key) {
                            case "name":
                                showValid(((FragmentEditUserTabPersonalBinding) binding).nameErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).nameErrorLayout.errorTextView, value);
                                break;
                            case "mobile":
                                showValid(((FragmentEditUserTabPersonalBinding) binding).mobileErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).mobileErrorLayout.errorTextView, value);
                                break;
                            case "email":
                                showValid(((FragmentEditUserTabPersonalBinding) binding).emailErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).emailErrorLayout.errorTextView, value);
                                break;
                            case "birthday":
                                showValid(((FragmentEditUserTabPersonalBinding) binding).birthdayErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).birthdayErrorLayout.errorTextView, value);
                                break;
                            case "status":
                                showValid(((FragmentEditUserTabPersonalBinding) binding).statusErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).statusErrorLayout.errorTextView, value);
                                break;
                            case "type":
                                showValid(((FragmentEditUserTabPersonalBinding) binding).typeErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).typeErrorLayout.errorTextView, value);
                                break;
                            case "gender":
                                showValid(((FragmentEditUserTabPersonalBinding) binding).genderErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).genderErrorLayout.errorTextView, value);
                                break;
                        }

                    // -------------------- Sheet

                    } else if (binding instanceof SheetBulkSampleBinding) {
                        if (key.equals("nickname")) {
                            showValid(((SheetBulkSampleBinding) binding).nicknameErrorLayout.getRoot(), ((SheetBulkSampleBinding) binding).nicknameErrorLayout.errorTextView, value);
                        }

                    }

                    // -------------------- End

                }

                SnackManager.showSnackError(activity, allErrors.substring(0, allErrors.length() - 1));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void requestValid2(String key, String value, ViewBinding binding) {

        // -------------------- Tab

        if (binding instanceof FragmentCreateScheduleTabPaymentBinding) {
            if (key.equals("payment_status")) {
                showValid(((FragmentCreateScheduleTabPaymentBinding) binding).paymentErrorLayout.getRoot(), ((FragmentCreateScheduleTabPaymentBinding) binding).paymentErrorLayout.errorTextView, value);
            }

        } else if (binding instanceof FragmentCreateScheduleTabPlatformBinding) {
            switch (key) {
                case "platforms":
                    showValid(((FragmentCreateScheduleTabPlatformBinding) binding).platformsErrorLayout.getRoot(), ((FragmentCreateScheduleTabPlatformBinding) binding).platformsErrorLayout.errorTextView, value);
                    break;
                case "pin_platform":
                    showValid(((FragmentCreateScheduleTabPlatformBinding) binding).pinPlatformErrorLayout.getRoot(), ((FragmentCreateScheduleTabPlatformBinding) binding).pinPlatformErrorLayout.errorTextView, value);
                    break;
                case "identifier_platform":
                    showValid(((FragmentCreateScheduleTabPlatformBinding) binding).identifierPlatformErrorLayout.getRoot(), ((FragmentCreateScheduleTabPlatformBinding) binding).identifierPlatformErrorLayout.errorTextView, value);
                    break;
            }

        } else if (binding instanceof FragmentCreateScheduleTabReferenceBinding) {
            switch (key) {
                case "selection_type":
                    showValid(((FragmentCreateScheduleTabReferenceBinding) binding).selectionErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).selectionErrorLayout.errorTextView, value);
                    break;
                case "clients_type":
                    showValid(((FragmentCreateScheduleTabReferenceBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).typeErrorLayout.errorTextView, value);
                    break;
                case "case_id":
                    showValid(((FragmentCreateScheduleTabReferenceBinding) binding).caseErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).caseErrorLayout.errorTextView, value);
                    break;
                case "group_session":
                    showValid(((FragmentCreateScheduleTabReferenceBinding) binding).bulkSessionErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).bulkSessionErrorLayout.errorTextView, value);
                    break;
                case "clients_number":
                    showValid(((FragmentCreateScheduleTabReferenceBinding) binding).countErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).countErrorLayout.errorTextView, value);
                    break;
            }

        } else if (binding instanceof FragmentCreateScheduleTabSessionBinding) {


        } else if (binding instanceof FragmentCreateScheduleTabTimeBinding) {
            switch (key) {
                case "time":
                    showValid(((FragmentCreateScheduleTabTimeBinding) binding).startTimeErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).startTimeErrorLayout.errorTextView, value);
                    break;
                case "duration":
                    showValid(((FragmentCreateScheduleTabTimeBinding) binding).durationErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).durationErrorLayout.errorTextView, value);
                    break;
                case "date_type":
                    showValid(((FragmentCreateScheduleTabTimeBinding) binding).dateTypeErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).dateTypeErrorLayout.errorTextView, value);
                    break;
                case "date":
                    showValid(((FragmentCreateScheduleTabTimeBinding) binding).specifiedDateErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).specifiedDateErrorLayout.errorTextView, value);
                    break;
                case "week_days":
                    showValid(((FragmentCreateScheduleTabTimeBinding) binding).patternDaysErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).patternDaysErrorLayout.errorTextView, value);
                    break;
                case "repeat_status":
                    showValid(((FragmentCreateScheduleTabTimeBinding) binding).patternTypeErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).patternTypeErrorLayout.errorTextView, value);
                    break;
                case "repeat":
                    showValid(((FragmentCreateScheduleTabTimeBinding) binding).repeatWeeksErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).repeatWeeksErrorLayout.errorTextView, value);
                    break;
                case "repeat_from":
                    showValid(((FragmentCreateScheduleTabTimeBinding) binding).periodStartDateErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).periodStartDateErrorLayout.errorTextView, value);
                    break;
                case "repeat_to":
                    showValid(((FragmentCreateScheduleTabTimeBinding) binding).periodEndDateErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).periodEndDateErrorLayout.errorTextView, value);
                    break;
            }

        } else if (binding instanceof FragmentCreateSessionTabPaymentBinding) {
            if (key.equals("payment_status")) {
                showValid(((FragmentCreateSessionTabPaymentBinding) binding).paymentErrorLayout.getRoot(), ((FragmentCreateSessionTabPaymentBinding) binding).paymentErrorLayout.errorTextView, value);
            }

        } else if (binding instanceof FragmentCreateSessionTabPlatformBinding) {
            switch (key) {
                case "platforms":
                    showValid(((FragmentCreateSessionTabPlatformBinding) binding).platformsErrorLayout.getRoot(), ((FragmentCreateSessionTabPlatformBinding) binding).platformsErrorLayout.errorTextView, value);
                    break;
                case "pin_platform":
                    showValid(((FragmentCreateSessionTabPlatformBinding) binding).pinPlatformErrorLayout.getRoot(), ((FragmentCreateSessionTabPlatformBinding) binding).pinPlatformErrorLayout.errorTextView, value);
                    break;
                case "identifier_platform":
                    showValid(((FragmentCreateSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.getRoot(), ((FragmentCreateSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.errorTextView, value);
                    break;
            }

        } else if (binding instanceof FragmentCreateSessionTabSessionBinding) {


        } else if (binding instanceof FragmentCreateSessionTabTimeBinding) {
            switch (key) {
                case "time":
                    showValid(((FragmentCreateSessionTabTimeBinding) binding).startTimeErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).startTimeErrorLayout.errorTextView, value);
                    break;
                case "duration":
                    showValid(((FragmentCreateSessionTabTimeBinding) binding).durationErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).durationErrorLayout.errorTextView, value);
                    break;
                case "date_type":
                    showValid(((FragmentCreateSessionTabTimeBinding) binding).dateTypeErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).dateTypeErrorLayout.errorTextView, value);
                    break;
                case "date":
                    showValid(((FragmentCreateSessionTabTimeBinding) binding).specifiedDateErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).specifiedDateErrorLayout.errorTextView, value);
                    break;
                case "week_days":
                    showValid(((FragmentCreateSessionTabTimeBinding) binding).patternDaysErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).patternDaysErrorLayout.errorTextView, value);
                    break;
                case "repeat_status":
                    showValid(((FragmentCreateSessionTabTimeBinding) binding).patternTypeErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).patternTypeErrorLayout.errorTextView, value);
                    break;
                case "repeat":
                    showValid(((FragmentCreateSessionTabTimeBinding) binding).repeatWeeksErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).repeatWeeksErrorLayout.errorTextView, value);
                    break;
                case "repeat_from":
                    showValid(((FragmentCreateSessionTabTimeBinding) binding).periodStartDateErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).periodStartDateErrorLayout.errorTextView, value);
                    break;
                case "repeat_to":
                    showValid(((FragmentCreateSessionTabTimeBinding) binding).periodEndDateErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).periodEndDateErrorLayout.errorTextView, value);
                    break;
            }

        } else if (binding instanceof FragmentEditSessionTabPaymentBinding) {
            if (key.equals("payment_status")) {
                showValid(((FragmentEditSessionTabPaymentBinding) binding).paymentErrorLayout.getRoot(), ((FragmentEditSessionTabPaymentBinding) binding).paymentErrorLayout.errorTextView, value);
            }

        } else if (binding instanceof FragmentEditSessionTabPlatformBinding) {
            switch (key) {
                case "platforms":
                    showValid(((FragmentEditSessionTabPlatformBinding) binding).platformsErrorLayout.getRoot(), ((FragmentEditSessionTabPlatformBinding) binding).platformsErrorLayout.errorTextView, value);
                    break;
                case "pin_platform":
                    showValid(((FragmentEditSessionTabPlatformBinding) binding).pinPlatformErrorLayout.getRoot(), ((FragmentEditSessionTabPlatformBinding) binding).pinPlatformErrorLayout.errorTextView, value);
                    break;
                case "identifier_platform":
                    showValid(((FragmentEditSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.getRoot(), ((FragmentEditSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.errorTextView, value);
                    break;
            }

        } else if (binding instanceof FragmentEditSessionTabReferenceBinding) {
            switch (key) {
                case "selection_type":
                    showValid(((FragmentEditSessionTabReferenceBinding) binding).selectionErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).selectionErrorLayout.errorTextView, value);
                    break;
                case "clients_type":
                    showValid(((FragmentEditSessionTabReferenceBinding) binding).typeErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).typeErrorLayout.errorTextView, value);
                    break;
                case "case_id":
                    showValid(((FragmentEditSessionTabReferenceBinding) binding).caseErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).caseErrorLayout.errorTextView, value);
                    break;
                case "problem":
                    showValid(((FragmentEditSessionTabReferenceBinding) binding).problemErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).problemErrorLayout.errorTextView, value);
                    break;
                case "group_session":
                    showValid(((FragmentEditSessionTabReferenceBinding) binding).bulkSessionErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).bulkSessionErrorLayout.errorTextView, value);
                    break;
                case "clients_number":
                    showValid(((FragmentEditSessionTabReferenceBinding) binding).countErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).countErrorLayout.errorTextView, value);
                    break;
            }

        } else if (binding instanceof FragmentEditSessionTabSessionBinding) {


        } else if (binding instanceof FragmentEditSessionTabTimeBinding) {
            switch (key) {
                case "time":
                    showValid(((FragmentEditSessionTabTimeBinding) binding).startTimeErrorLayout.getRoot(), ((FragmentEditSessionTabTimeBinding) binding).startTimeErrorLayout.errorTextView, value);
                    break;
                case "duration":
                    showValid(((FragmentEditSessionTabTimeBinding) binding).durationErrorLayout.getRoot(), ((FragmentEditSessionTabTimeBinding) binding).durationErrorLayout.errorTextView, value);
                    break;
                case "date":
                    showValid(((FragmentEditSessionTabTimeBinding) binding).startDateErrorLayout.getRoot(), ((FragmentEditSessionTabTimeBinding) binding).startDateErrorLayout.errorTextView, value);
                    break;
            }

        }

        // -------------------- End

    }

    public void resetValid(ViewBinding binding) {

        // -------------------- Auth

        if (binding instanceof FragmentAuthLoginBinding) {
            if (((FragmentAuthLoginBinding) binding).errorIncludeLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentAuthLoginBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthLoginBinding) binding).errorIncludeLayout.errorTextView);
            }

        } else if (binding instanceof FragmentAuthPasswordBinding) {
            if (((FragmentAuthPasswordBinding) binding).errorIncludeLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentAuthPasswordBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPasswordBinding) binding).errorIncludeLayout.errorTextView);
            }

        } else if (binding instanceof FragmentAuthPasswordChangeBinding) {
            if (((FragmentAuthPasswordChangeBinding) binding).errorIncludeLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentAuthPasswordChangeBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPasswordChangeBinding) binding).errorIncludeLayout.errorTextView);
            }

        } else if (binding instanceof FragmentAuthPasswordRecoverBinding) {
            if (((FragmentAuthPasswordRecoverBinding) binding).errorIncludeLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentAuthPasswordRecoverBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPasswordRecoverBinding) binding).errorIncludeLayout.errorTextView);
            }

        } else if (binding instanceof FragmentAuthPinBinding) {
            if (((FragmentAuthPinBinding) binding).errorIncludeLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentAuthPinBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPinBinding) binding).errorIncludeLayout.errorTextView);
            }

        } else if (binding instanceof FragmentAuthRegisterBinding) {
            if (((FragmentAuthRegisterBinding) binding).errorIncludeLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentAuthRegisterBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthRegisterBinding) binding).errorIncludeLayout.errorTextView);
            }

        } else if (binding instanceof FragmentAuthSerialBinding) {
            if (((FragmentAuthSerialBinding) binding).errorIncludeLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentAuthSerialBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthSerialBinding) binding).errorIncludeLayout.errorTextView);
            }

        // -------------------- Create

        } else if (binding instanceof FragmentCreateBillBinding) {
            if (((FragmentCreateBillBinding) binding).titleErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateBillBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).titleErrorLayout.errorTextView);
            }
            if (((FragmentCreateBillBinding) binding).referenceErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateBillBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).referenceErrorLayout.errorTextView);
            }
            if (((FragmentCreateBillBinding) binding).typeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateBillBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).typeErrorLayout.errorTextView);
            }
            if (((FragmentCreateBillBinding) binding).treasuryErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateBillBinding) binding).treasuryErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).treasuryErrorLayout.errorTextView);
            }
            if (((FragmentCreateBillBinding) binding).amountErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateBillBinding) binding).amountErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).amountErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateCaseBinding) {
            if (((FragmentCreateCaseBinding) binding).titleErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateCaseBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateCaseBinding) binding).titleErrorLayout.errorTextView);
            }
            if (((FragmentCreateCaseBinding) binding).referenceErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateCaseBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateCaseBinding) binding).referenceErrorLayout.errorTextView);
            }
            if (((FragmentCreateCaseBinding) binding).problemErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateCaseBinding) binding).problemErrorLayout.getRoot(), ((FragmentCreateCaseBinding) binding).problemErrorLayout.errorTextView);
            }
            if (((FragmentCreateCaseBinding) binding).tagsErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateCaseBinding) binding).tagsErrorLayout.getRoot(), ((FragmentCreateCaseBinding) binding).tagsErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateCaseUserBinding) {
            if (((FragmentCreateCaseUserBinding) binding).referenceErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateCaseUserBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateCaseUserBinding) binding).referenceErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateCenterBinding) {
            if (((FragmentCreateCenterBinding) binding).typeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateCenterBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).typeErrorLayout.errorTextView);
            }
            if (((FragmentCreateCenterBinding) binding).managerErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateCenterBinding) binding).managerErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).managerErrorLayout.errorTextView);
            }
            if (((FragmentCreateCenterBinding) binding).titleErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateCenterBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).titleErrorLayout.errorTextView);
            }
            if (((FragmentCreateCenterBinding) binding).addressErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateCenterBinding) binding).addressErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).addressErrorLayout.errorTextView);
            }
            if (((FragmentCreateCenterBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateCenterBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).descriptionErrorLayout.errorTextView);
            }
            if (((FragmentCreateCenterBinding) binding).avatarErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateCenterBinding) binding).avatarErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).avatarErrorLayout.errorTextView);
            }
            if (((FragmentCreateCenterBinding) binding).phonesErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateCenterBinding) binding).phonesErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).phonesErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateCenterUserBinding) {
            if (((FragmentCreateCenterUserBinding) binding).mobileErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateCenterUserBinding) binding).mobileErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).mobileErrorLayout.errorTextView);
            }
            if (((FragmentCreateCenterUserBinding) binding).positionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateCenterUserBinding) binding).positionErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).positionErrorLayout.errorTextView);
            }
            if (((FragmentCreateCenterUserBinding) binding).roomErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateCenterUserBinding) binding).roomErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).roomErrorLayout.errorTextView);
            }
            if (((FragmentCreateCenterUserBinding) binding).nicknameErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateCenterUserBinding) binding).nicknameErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).nicknameErrorLayout.errorTextView);
            }
            if (((FragmentCreateCenterUserBinding) binding).caseErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateCenterUserBinding) binding).caseErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).caseErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateClientReportBinding) {
            if (((FragmentCreateClientReportBinding) binding).encryptionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateClientReportBinding) binding).encryptionErrorLayout.getRoot(), ((FragmentCreateClientReportBinding) binding).encryptionErrorLayout.errorTextView);
            }
            if (((FragmentCreateClientReportBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateClientReportBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateClientReportBinding) binding).descriptionErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateDocumentBinding) {
            if (((FragmentCreateDocumentBinding) binding).nameErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateDocumentBinding) binding).nameErrorLayout.getRoot(), ((FragmentCreateDocumentBinding) binding).nameErrorLayout.errorTextView);
            }
            if (((FragmentCreateDocumentBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateDocumentBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateDocumentBinding) binding).descriptionErrorLayout.errorTextView);
            }
            if (((FragmentCreateDocumentBinding) binding).fileErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateDocumentBinding) binding).fileErrorLayout.getRoot(), ((FragmentCreateDocumentBinding) binding).fileErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreatePlatformBinding) {
            if (((FragmentCreatePlatformBinding) binding).titleErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreatePlatformBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).titleErrorLayout.errorTextView);
            }
            if (((FragmentCreatePlatformBinding) binding).sessionTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreatePlatformBinding) binding).sessionTypeErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).sessionTypeErrorLayout.errorTextView);
            }
            if (((FragmentCreatePlatformBinding) binding).indentifierTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreatePlatformBinding) binding).indentifierTypeErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).indentifierTypeErrorLayout.errorTextView);
            }
            if (((FragmentCreatePlatformBinding) binding).indentifierErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreatePlatformBinding) binding).indentifierErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).indentifierErrorLayout.errorTextView);
            }
            if (((FragmentCreatePlatformBinding) binding).sessionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreatePlatformBinding) binding).sessionErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).sessionErrorLayout.errorTextView);
            }
            if (((FragmentCreatePlatformBinding) binding).availableErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreatePlatformBinding) binding).availableErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).availableErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreatePracticeBinding) {
            if (((FragmentCreatePracticeBinding) binding).nameErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreatePracticeBinding) binding).nameErrorLayout.getRoot(), ((FragmentCreatePracticeBinding) binding).nameErrorLayout.errorTextView);
            }
            if (((FragmentCreatePracticeBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreatePracticeBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreatePracticeBinding) binding).descriptionErrorLayout.errorTextView);
            }
            if (((FragmentCreatePracticeBinding) binding).fileErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreatePracticeBinding) binding).fileErrorLayout.getRoot(), ((FragmentCreatePracticeBinding) binding).fileErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateRoomBinding) {
            if (((FragmentCreateRoomBinding) binding).psychologyErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateRoomBinding) binding).psychologyErrorLayout.getRoot(), ((FragmentCreateRoomBinding) binding).psychologyErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateRoomUserBinding) {
            if (((FragmentCreateRoomUserBinding) binding).referenceErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateRoomUserBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateRoomUserBinding) binding).referenceErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateSampleBinding) {
            if (((FragmentCreateSampleBinding) binding).scaleErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSampleBinding) binding).scaleErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).scaleErrorLayout.errorTextView);
            }
            if (((FragmentCreateSampleBinding) binding).roomErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSampleBinding) binding).roomErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).roomErrorLayout.errorTextView);
            }
            if (((FragmentCreateSampleBinding) binding).typeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSampleBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).typeErrorLayout.errorTextView);
            }
            if (((FragmentCreateSampleBinding) binding).titleErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSampleBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).titleErrorLayout.errorTextView);
            }
            if (((FragmentCreateSampleBinding) binding).membersCountErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSampleBinding) binding).membersCountErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).membersCountErrorLayout.errorTextView);
            }
            if (((FragmentCreateSampleBinding) binding).caseStatusErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSampleBinding) binding).caseStatusErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).caseStatusErrorLayout.errorTextView);
            }
            if (((FragmentCreateSampleBinding) binding).problemErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSampleBinding) binding).problemErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).problemErrorLayout.errorTextView);
            }
            if (((FragmentCreateSampleBinding) binding).caseErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSampleBinding) binding).caseErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).caseErrorLayout.errorTextView);
            }
            if (((FragmentCreateSampleBinding) binding).sessionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSampleBinding) binding).sessionErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).sessionErrorLayout.errorTextView);
            }
            if (((FragmentCreateSampleBinding) binding).clientErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSampleBinding) binding).clientErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).clientErrorLayout.errorTextView);
            }
            if (((FragmentCreateSampleBinding) binding).referenceErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSampleBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).referenceErrorLayout.errorTextView);
            }
            if (((FragmentCreateSampleBinding) binding).psychologyDescriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSampleBinding) binding).psychologyDescriptionErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).psychologyDescriptionErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateSessionUserBinding) {
            if (((FragmentCreateSessionUserBinding) binding).axisErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionUserBinding) binding).axisErrorLayout.getRoot(), ((FragmentCreateSessionUserBinding) binding).axisErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionUserBinding) binding).platformErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionUserBinding) binding).platformErrorLayout.getRoot(), ((FragmentCreateSessionUserBinding) binding).platformErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionUserBinding) binding).clientErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionUserBinding) binding).clientErrorLayout.getRoot(), ((FragmentCreateSessionUserBinding) binding).clientErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionUserBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionUserBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateSessionUserBinding) binding).descriptionErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateTreasuryBinding) {
            if (((FragmentCreateTreasuryBinding) binding).titleErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateTreasuryBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateTreasuryBinding) binding).titleErrorLayout.errorTextView);
            }
            if (((FragmentCreateTreasuryBinding) binding).regionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateTreasuryBinding) binding).regionErrorLayout.getRoot(), ((FragmentCreateTreasuryBinding) binding).regionErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateUserBinding) {
            if (((FragmentCreateUserBinding) binding).nameErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateUserBinding) binding).nameErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).nameErrorLayout.errorTextView);
            }
            if (((FragmentCreateUserBinding) binding).mobileErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateUserBinding) binding).mobileErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).mobileErrorLayout.errorTextView);
            }
            if (((FragmentCreateUserBinding) binding).emailErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateUserBinding) binding).emailErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).emailErrorLayout.errorTextView);
            }
            if (((FragmentCreateUserBinding) binding).passwordErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateUserBinding) binding).passwordErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).passwordErrorLayout.errorTextView);
            }
            if (((FragmentCreateUserBinding) binding).birthdayErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateUserBinding) binding).birthdayErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).birthdayErrorLayout.errorTextView);
            }
            if (((FragmentCreateUserBinding) binding).statusErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateUserBinding) binding).statusErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).statusErrorLayout.errorTextView);
            }
            if (((FragmentCreateUserBinding) binding).typeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateUserBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).typeErrorLayout.errorTextView);
            }
            if (((FragmentCreateUserBinding) binding).genderErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateUserBinding) binding).genderErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).genderErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentReserveScheduleBinding) {
            if (((FragmentReserveScheduleBinding) binding).fieldErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentReserveScheduleBinding) binding).fieldErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).fieldErrorLayout.errorTextView);
            }
            if (((FragmentReserveScheduleBinding) binding).platformErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentReserveScheduleBinding) binding).platformErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).platformErrorLayout.errorTextView);
            }
            if (((FragmentReserveScheduleBinding) binding).typeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentReserveScheduleBinding) binding).typeErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).typeErrorLayout.errorTextView);
            }
            if (((FragmentReserveScheduleBinding) binding).caseErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentReserveScheduleBinding) binding).caseErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).caseErrorLayout.errorTextView);
            }
            if (((FragmentReserveScheduleBinding) binding).clientErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentReserveScheduleBinding) binding).clientErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).clientErrorLayout.errorTextView);
            }
            if (((FragmentReserveScheduleBinding) binding).referenceErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentReserveScheduleBinding) binding).referenceErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).referenceErrorLayout.errorTextView);
            }
            if (((FragmentReserveScheduleBinding) binding).problemErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentReserveScheduleBinding) binding).problemErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).problemErrorLayout.errorTextView);
            }
            if (((FragmentReserveScheduleBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentReserveScheduleBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).descriptionErrorLayout.errorTextView);
            }

        // -------------------- Edit

        } else if (binding instanceof FragmentEditCenterUserBinding) {
            if (((FragmentEditCenterUserBinding) binding).positionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditCenterUserBinding) binding).positionErrorLayout.getRoot(), ((FragmentEditCenterUserBinding) binding).positionErrorLayout.errorTextView);
            }
            if (((FragmentEditCenterUserBinding) binding).nicknameErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditCenterUserBinding) binding).nicknameErrorLayout.getRoot(), ((FragmentEditCenterUserBinding) binding).nicknameErrorLayout.errorTextView);
            }
            if (((FragmentEditCenterUserBinding) binding).statusErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditCenterUserBinding) binding).statusErrorLayout.getRoot(), ((FragmentEditCenterUserBinding) binding).statusErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentEditPlatformBinding) {
            if (((FragmentEditPlatformBinding) binding).titleErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditPlatformBinding) binding).titleErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).titleErrorLayout.errorTextView);
            }
            if (((FragmentEditPlatformBinding) binding).sessionTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditPlatformBinding) binding).sessionTypeErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).sessionTypeErrorLayout.errorTextView);
            }
            if (((FragmentEditPlatformBinding) binding).indentifierTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditPlatformBinding) binding).indentifierTypeErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).indentifierTypeErrorLayout.errorTextView);
            }
            if (((FragmentEditPlatformBinding) binding).indentifierErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditPlatformBinding) binding).indentifierErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).indentifierErrorLayout.errorTextView);
            }
            if (((FragmentEditPlatformBinding) binding).sessionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditPlatformBinding) binding).sessionErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).sessionErrorLayout.errorTextView);
            }
            if (((FragmentEditPlatformBinding) binding).availableErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditPlatformBinding) binding).availableErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).availableErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentEditTreasuryBinding) {
            if (((FragmentEditTreasuryBinding) binding).titleErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditTreasuryBinding) binding).titleErrorLayout.getRoot(), ((FragmentEditTreasuryBinding) binding).titleErrorLayout.errorTextView);
            }

        // -------------------- Index

        } else if (binding instanceof FragmentBanksBinding) {
            if (((FragmentBanksBinding) binding).ibanErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentBanksBinding) binding).ibanErrorLayout.getRoot(), ((FragmentBanksBinding) binding).ibanErrorLayout.errorTextView);
            }
            if (((FragmentBanksBinding) binding).amountErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentBanksBinding) binding).amountErrorLayout.getRoot(), ((FragmentBanksBinding) binding).amountErrorLayout.errorTextView);
            }
            if (((FragmentBanksBinding) binding).accountErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentBanksBinding) binding).accountErrorLayout.getRoot(), ((FragmentBanksBinding) binding).accountErrorLayout.errorTextView);
            }
            if (((FragmentBanksBinding) binding).typeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentBanksBinding) binding).typeErrorLayout.getRoot(), ((FragmentBanksBinding) binding).typeErrorLayout.errorTextView);
            }
            if (((FragmentBanksBinding) binding).weekdayErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentBanksBinding) binding).weekdayErrorLayout.getRoot(), ((FragmentBanksBinding) binding).weekdayErrorLayout.errorTextView);
            }
            if (((FragmentBanksBinding) binding).monthdayErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentBanksBinding) binding).monthdayErrorLayout.getRoot(), ((FragmentBanksBinding) binding).monthdayErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCommissionsBinding) {
            if (((FragmentCommissionsBinding) binding).shareErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCommissionsBinding) binding).shareErrorLayout.getRoot(), ((FragmentCommissionsBinding) binding).shareErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentPaymentsBinding) {
            if (((FragmentPaymentsBinding) binding).treasuryErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentPaymentsBinding) binding).treasuryErrorLayout.getRoot(), ((FragmentPaymentsBinding) binding).treasuryErrorLayout.errorTextView);
            }
            if (((FragmentPaymentsBinding) binding).amountErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentPaymentsBinding) binding).amountErrorLayout.getRoot(), ((FragmentPaymentsBinding) binding).amountErrorLayout.errorTextView);
            }

        // -------------------- Tab

        } else if (binding instanceof FragmentCreateScheduleTabPaymentBinding) {
            if (((FragmentCreateScheduleTabPaymentBinding) binding).paymentErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabPaymentBinding) binding).paymentErrorLayout.getRoot(), ((FragmentCreateScheduleTabPaymentBinding) binding).paymentErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateScheduleTabPlatformBinding) {
            if (((FragmentCreateScheduleTabPlatformBinding) binding).platformsErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabPlatformBinding) binding).platformsErrorLayout.getRoot(), ((FragmentCreateScheduleTabPlatformBinding) binding).platformsErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabPlatformBinding) binding).pinPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabPlatformBinding) binding).pinPlatformErrorLayout.getRoot(), ((FragmentCreateScheduleTabPlatformBinding) binding).pinPlatformErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabPlatformBinding) binding).identifierPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabPlatformBinding) binding).identifierPlatformErrorLayout.getRoot(), ((FragmentCreateScheduleTabPlatformBinding) binding).identifierPlatformErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateScheduleTabReferenceBinding) {
            if (((FragmentCreateScheduleTabReferenceBinding) binding).selectionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabReferenceBinding) binding).selectionErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).selectionErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabReferenceBinding) binding).typeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabReferenceBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).typeErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabReferenceBinding) binding).caseErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabReferenceBinding) binding).caseErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).caseErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabReferenceBinding) binding).bulkSessionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabReferenceBinding) binding).bulkSessionErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).bulkSessionErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabReferenceBinding) binding).countErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabReferenceBinding) binding).countErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).countErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateScheduleTabSessionBinding) {
            if (((FragmentCreateScheduleTabSessionBinding) binding).statusErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabSessionBinding) binding).statusErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).statusErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabSessionBinding) binding).startTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabSessionBinding) binding).startTypeErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).startTypeErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabSessionBinding) binding).startRelativeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabSessionBinding) binding).startRelativeErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).startRelativeErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabSessionBinding) binding).startAccurateErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabSessionBinding) binding).startAccurateErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).startAccurateErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabSessionBinding) binding).endTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabSessionBinding) binding).endTypeErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).endTypeErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabSessionBinding) binding).endRelativeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabSessionBinding) binding).endRelativeErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).endRelativeErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabSessionBinding) binding).endAccurateErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabSessionBinding) binding).endAccurateErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).endAccurateErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabSessionBinding) binding).axisErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabSessionBinding) binding).axisErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).axisErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabSessionBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabSessionBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).descriptionErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabSessionBinding) binding).coordinationErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabSessionBinding) binding).coordinationErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).coordinationErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateScheduleTabTimeBinding) {
            if (((FragmentCreateScheduleTabTimeBinding) binding).startTimeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabTimeBinding) binding).startTimeErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).startTimeErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabTimeBinding) binding).durationErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabTimeBinding) binding).durationErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).durationErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabTimeBinding) binding).dateTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabTimeBinding) binding).dateTypeErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).dateTypeErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabTimeBinding) binding).specifiedDateErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabTimeBinding) binding).specifiedDateErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).specifiedDateErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabTimeBinding) binding).patternDaysErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabTimeBinding) binding).patternDaysErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).patternDaysErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabTimeBinding) binding).patternTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabTimeBinding) binding).patternTypeErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).patternTypeErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabTimeBinding) binding).repeatWeeksErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabTimeBinding) binding).repeatWeeksErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).repeatWeeksErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabTimeBinding) binding).periodStartDateErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabTimeBinding) binding).periodStartDateErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).periodStartDateErrorLayout.errorTextView);
            }
            if (((FragmentCreateScheduleTabTimeBinding) binding).periodEndDateErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateScheduleTabTimeBinding) binding).periodEndDateErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).periodEndDateErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateSessionTabPaymentBinding) {
            if (((FragmentCreateSessionTabPaymentBinding) binding).paymentErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabPaymentBinding) binding).paymentErrorLayout.getRoot(), ((FragmentCreateSessionTabPaymentBinding) binding).paymentErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateSessionTabPlatformBinding) {
            if (((FragmentCreateSessionTabPlatformBinding) binding).platformsErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabPlatformBinding) binding).platformsErrorLayout.getRoot(), ((FragmentCreateSessionTabPlatformBinding) binding).platformsErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionTabPlatformBinding) binding).pinPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabPlatformBinding) binding).pinPlatformErrorLayout.getRoot(), ((FragmentCreateSessionTabPlatformBinding) binding).pinPlatformErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.getRoot(), ((FragmentCreateSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateSessionTabSessionBinding) {
            if (((FragmentCreateSessionTabSessionBinding) binding).statusErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabSessionBinding) binding).statusErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).statusErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionTabSessionBinding) binding).startTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabSessionBinding) binding).startTypeErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).startTypeErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionTabSessionBinding) binding).startRelativeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabSessionBinding) binding).startRelativeErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).startRelativeErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionTabSessionBinding) binding).startAccurateErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabSessionBinding) binding).startAccurateErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).startAccurateErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionTabSessionBinding) binding).endTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabSessionBinding) binding).endTypeErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).endTypeErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionTabSessionBinding) binding).endRelativeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabSessionBinding) binding).endRelativeErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).endRelativeErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionTabSessionBinding) binding).endAccurateErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabSessionBinding) binding).endAccurateErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).endAccurateErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionTabSessionBinding) binding).axisErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabSessionBinding) binding).axisErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).axisErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionTabSessionBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabSessionBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).descriptionErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionTabSessionBinding) binding).coordinationErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabSessionBinding) binding).coordinationErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).coordinationErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentCreateSessionTabTimeBinding) {
            if (((FragmentCreateSessionTabTimeBinding) binding).startTimeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabTimeBinding) binding).startTimeErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).startTimeErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionTabTimeBinding) binding).durationErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabTimeBinding) binding).durationErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).durationErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionTabTimeBinding) binding).dateTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabTimeBinding) binding).dateTypeErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).dateTypeErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionTabTimeBinding) binding).specifiedDateErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabTimeBinding) binding).specifiedDateErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).specifiedDateErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionTabTimeBinding) binding).patternDaysErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabTimeBinding) binding).patternDaysErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).patternDaysErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionTabTimeBinding) binding).patternTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabTimeBinding) binding).patternTypeErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).patternTypeErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionTabTimeBinding) binding).repeatWeeksErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabTimeBinding) binding).repeatWeeksErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).repeatWeeksErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionTabTimeBinding) binding).periodStartDateErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabTimeBinding) binding).periodStartDateErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).periodStartDateErrorLayout.errorTextView);
            }
            if (((FragmentCreateSessionTabTimeBinding) binding).periodEndDateErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentCreateSessionTabTimeBinding) binding).periodEndDateErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).periodEndDateErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentEditSessionTabPaymentBinding) {
            if (((FragmentEditSessionTabPaymentBinding) binding).paymentErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabPaymentBinding) binding).paymentErrorLayout.getRoot(), ((FragmentEditSessionTabPaymentBinding) binding).paymentErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentEditSessionTabPlatformBinding) {
            if (((FragmentEditSessionTabPlatformBinding) binding).platformsErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabPlatformBinding) binding).platformsErrorLayout.getRoot(), ((FragmentEditSessionTabPlatformBinding) binding).platformsErrorLayout.errorTextView);
            }
            if (((FragmentEditSessionTabPlatformBinding) binding).pinPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabPlatformBinding) binding).pinPlatformErrorLayout.getRoot(), ((FragmentEditSessionTabPlatformBinding) binding).pinPlatformErrorLayout.errorTextView);
            }
            if (((FragmentEditSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.getRoot(), ((FragmentEditSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentEditSessionTabReferenceBinding) {
            if (((FragmentEditSessionTabReferenceBinding) binding).selectionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabReferenceBinding) binding).selectionErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).selectionErrorLayout.errorTextView);
            }
            if (((FragmentEditSessionTabReferenceBinding) binding).typeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabReferenceBinding) binding).typeErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).typeErrorLayout.errorTextView);
            }
            if (((FragmentEditSessionTabReferenceBinding) binding).caseErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabReferenceBinding) binding).caseErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).caseErrorLayout.errorTextView);
            }
            if (((FragmentEditSessionTabReferenceBinding) binding).problemErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabReferenceBinding) binding).problemErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).problemErrorLayout.errorTextView);
            }
            if (((FragmentEditSessionTabReferenceBinding) binding).bulkSessionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabReferenceBinding) binding).bulkSessionErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).bulkSessionErrorLayout.errorTextView);
            }
            if (((FragmentEditSessionTabReferenceBinding) binding).countErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabReferenceBinding) binding).countErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).countErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentEditSessionTabSessionBinding) {
            if (((FragmentEditSessionTabSessionBinding) binding).statusErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabSessionBinding) binding).statusErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).statusErrorLayout.errorTextView);
            }
            if (((FragmentEditSessionTabSessionBinding) binding).startTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabSessionBinding) binding).startTypeErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).startTypeErrorLayout.errorTextView);
            }
            if (((FragmentEditSessionTabSessionBinding) binding).startRelativeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabSessionBinding) binding).startRelativeErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).startRelativeErrorLayout.errorTextView);
            }
            if (((FragmentEditSessionTabSessionBinding) binding).startAccurateErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabSessionBinding) binding).startAccurateErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).startAccurateErrorLayout.errorTextView);
            }
            if (((FragmentEditSessionTabSessionBinding) binding).endTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabSessionBinding) binding).endTypeErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).endTypeErrorLayout.errorTextView);
            }
            if (((FragmentEditSessionTabSessionBinding) binding).endRelativeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabSessionBinding) binding).endRelativeErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).endRelativeErrorLayout.errorTextView);
            }
            if (((FragmentEditSessionTabSessionBinding) binding).endAccurateErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabSessionBinding) binding).endAccurateErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).endAccurateErrorLayout.errorTextView);
            }
            if (((FragmentEditSessionTabSessionBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabSessionBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).descriptionErrorLayout.errorTextView);
            }
            if (((FragmentEditSessionTabSessionBinding) binding).coordinationErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabSessionBinding) binding).coordinationErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).coordinationErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentEditSessionTabTimeBinding) {
            if (((FragmentEditSessionTabTimeBinding) binding).startTimeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabTimeBinding) binding).startTimeErrorLayout.getRoot(), ((FragmentEditSessionTabTimeBinding) binding).startTimeErrorLayout.errorTextView);
            }
            if (((FragmentEditSessionTabTimeBinding) binding).durationErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabTimeBinding) binding).durationErrorLayout.getRoot(), ((FragmentEditSessionTabTimeBinding) binding).durationErrorLayout.errorTextView);
            }
            if (((FragmentEditSessionTabTimeBinding) binding).startDateErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditSessionTabTimeBinding) binding).startDateErrorLayout.getRoot(), ((FragmentEditSessionTabTimeBinding) binding).startDateErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentEditCenterTabDetailBinding) {
            if (((FragmentEditCenterTabDetailBinding) binding).managerErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditCenterTabDetailBinding) binding).managerErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).managerErrorLayout.errorTextView);
            }
            if (((FragmentEditCenterTabDetailBinding) binding).titleErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditCenterTabDetailBinding) binding).titleErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).titleErrorLayout.errorTextView);
            }
            if (((FragmentEditCenterTabDetailBinding) binding).addressErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditCenterTabDetailBinding) binding).addressErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).addressErrorLayout.errorTextView);
            }
            if (((FragmentEditCenterTabDetailBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditCenterTabDetailBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).descriptionErrorLayout.errorTextView);
            }
            if (((FragmentEditCenterTabDetailBinding) binding).phonesErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditCenterTabDetailBinding) binding).phonesErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).phonesErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentEditUserTabCryptoBinding) {
            if (((FragmentEditUserTabCryptoBinding) binding).publicErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditUserTabCryptoBinding) binding).publicErrorLayout.getRoot(), ((FragmentEditUserTabCryptoBinding) binding).publicErrorLayout.errorTextView);
            }
            if (((FragmentEditUserTabCryptoBinding) binding).privateErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditUserTabCryptoBinding) binding).privateErrorLayout.getRoot(), ((FragmentEditUserTabCryptoBinding) binding).privateErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentEditUserTabPasswordBinding) {
            if (((FragmentEditUserTabPasswordBinding) binding).currentPasswordErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditUserTabPasswordBinding) binding).currentPasswordErrorLayout.getRoot(), ((FragmentEditUserTabPasswordBinding) binding).currentPasswordErrorLayout.errorTextView);
            }
            if (((FragmentEditUserTabPasswordBinding) binding).newPasswordErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditUserTabPasswordBinding) binding).newPasswordErrorLayout.getRoot(), ((FragmentEditUserTabPasswordBinding) binding).newPasswordErrorLayout.errorTextView);
            }

        } else if (binding instanceof FragmentEditUserTabPersonalBinding) {
            if (((FragmentEditUserTabPersonalBinding) binding).nameErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditUserTabPersonalBinding) binding).nameErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).nameErrorLayout.errorTextView);
            }
            if (((FragmentEditUserTabPersonalBinding) binding).mobileErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditUserTabPersonalBinding) binding).mobileErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).mobileErrorLayout.errorTextView);
            }
            if (((FragmentEditUserTabPersonalBinding) binding).emailErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditUserTabPersonalBinding) binding).emailErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).emailErrorLayout.errorTextView);
            }
            if (((FragmentEditUserTabPersonalBinding) binding).birthdayErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditUserTabPersonalBinding) binding).birthdayErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).birthdayErrorLayout.errorTextView);
            }
            if (((FragmentEditUserTabPersonalBinding) binding).statusErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditUserTabPersonalBinding) binding).statusErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).statusErrorLayout.errorTextView);
            }
            if (((FragmentEditUserTabPersonalBinding) binding).typeErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditUserTabPersonalBinding) binding).typeErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).typeErrorLayout.errorTextView);
            }
            if (((FragmentEditUserTabPersonalBinding) binding).genderErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((FragmentEditUserTabPersonalBinding) binding).genderErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).genderErrorLayout.errorTextView);
            }

        // -------------------- Sheet

        } else if (binding instanceof SheetBulkSampleBinding) {
            if (((SheetBulkSampleBinding) binding).nicknameErrorLayout.getRoot().getVisibility() == View.VISIBLE) {
                hideValid(((SheetBulkSampleBinding) binding).nicknameErrorLayout.getRoot(), ((SheetBulkSampleBinding) binding).nicknameErrorLayout.errorTextView);
            }

        }

        // -------------------- End

    }

}