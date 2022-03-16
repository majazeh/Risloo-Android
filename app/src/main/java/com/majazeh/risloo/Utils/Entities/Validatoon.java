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

    public void showValid(String response, ViewBinding binding) {
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

                    String validation = keyErrors.substring(0, keyErrors.length() - 1);

                    // -------------------- Auth

                    if (binding instanceof FragmentAuthLoginBinding) {
                        if (key.equals("authorized_key")) {
                            showHandle(((FragmentAuthLoginBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthLoginBinding) binding).errorIncludeLayout.errorTextView, validation);
                        }

                    } else if (binding instanceof FragmentAuthPasswordBinding) {
                        if (key.equals("password")) {
                            showHandle(((FragmentAuthPasswordBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPasswordBinding) binding).errorIncludeLayout.errorTextView, validation);
                        }

                    } else if (binding instanceof FragmentAuthPasswordChangeBinding) {
                        if (key.equals("password")) {
                            showHandle(((FragmentAuthPasswordChangeBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPasswordChangeBinding) binding).errorIncludeLayout.errorTextView, validation);
                        }

                    } else if (binding instanceof FragmentAuthPasswordRecoverBinding) {
                        if (key.equals("mobile")) {
                            showHandle(((FragmentAuthPasswordRecoverBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPasswordRecoverBinding) binding).errorIncludeLayout.errorTextView, validation);
                        }

                    } else if (binding instanceof FragmentAuthPinBinding) {
                        if (key.equals("code")) {
                            showHandle(((FragmentAuthPinBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPinBinding) binding).errorIncludeLayout.errorTextView, validation);
                        }

                    } else if (binding instanceof FragmentAuthRegisterBinding) {
                        if (key.equals("mobile")) {
                            showHandle(((FragmentAuthRegisterBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthRegisterBinding) binding).errorIncludeLayout.errorTextView, validation);
                        }

                    // -------------------- Create

                    } else if (binding instanceof FragmentCreateBillBinding) {
                        switch (key) {
                            case "title":
                                showHandle(((FragmentCreateBillBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).titleErrorLayout.errorTextView, validation);
                                break;
                            case "user_id":
                                showHandle(((FragmentCreateBillBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).referenceErrorLayout.errorTextView, validation);
                                break;
                            case "type":
                                showHandle(((FragmentCreateBillBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).typeErrorLayout.errorTextView, validation);
                                break;
                            case "treasury":
                                showHandle(((FragmentCreateBillBinding) binding).treasuryErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).treasuryErrorLayout.errorTextView, validation);
                                break;
                            case "amount":
                                showHandle(((FragmentCreateBillBinding) binding).amountErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).amountErrorLayout.errorTextView, validation);
                                break;
                        }

                    } else if (binding instanceof FragmentCreateCaseBinding) {
                        switch (key) {
                            case "title":
                                showHandle(((FragmentCreateCaseBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateCaseBinding) binding).titleErrorLayout.errorTextView, validation);
                                break;
                            case "client_id":
                                showHandle(((FragmentCreateCaseBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateCaseBinding) binding).referenceErrorLayout.errorTextView, validation);
                                break;
                            case "problem":
                                showHandle(((FragmentCreateCaseBinding) binding).problemErrorLayout.getRoot(), ((FragmentCreateCaseBinding) binding).problemErrorLayout.errorTextView, validation);
                                break;
                            case "tags":
                                showHandle(((FragmentCreateCaseBinding) binding).tagsErrorLayout.getRoot(), ((FragmentCreateCaseBinding) binding).tagsErrorLayout.errorTextView, validation);
                                break;
                        }

                    } else if (binding instanceof FragmentCreateCaseUserBinding) {
                        if (key.equals("client_id")) {
                            showHandle(((FragmentCreateCaseUserBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateCaseUserBinding) binding).referenceErrorLayout.errorTextView, validation);
                        }

                    } else if (binding instanceof FragmentCreateCenterBinding) {
                        switch (key) {
                            case "type":
                                showHandle(((FragmentCreateCenterBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).typeErrorLayout.errorTextView, validation);
                                break;
                            case "manager_id":
                                showHandle(((FragmentCreateCenterBinding) binding).managerErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).managerErrorLayout.errorTextView, validation);
                                break;
                            case "title":
                                showHandle(((FragmentCreateCenterBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).titleErrorLayout.errorTextView, validation);
                                break;
                            case "address":
                                showHandle(((FragmentCreateCenterBinding) binding).addressErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).addressErrorLayout.errorTextView, validation);
                                break;
                            case "description":
                                showHandle(((FragmentCreateCenterBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).descriptionErrorLayout.errorTextView, validation);
                                break;
                            case "avatar":
                                showHandle(((FragmentCreateCenterBinding) binding).avatarErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).avatarErrorLayout.errorTextView, validation);
                                break;
                            case "phone_numbers":
                                showHandle(((FragmentCreateCenterBinding) binding).phonesErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).phonesErrorLayout.errorTextView, validation);
                                break;
                        }

                    } else if (binding instanceof FragmentCreateCenterUserBinding) {
                        switch (key) {
                            case "mobile":
                                showHandle(((FragmentCreateCenterUserBinding) binding).mobileErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).mobileErrorLayout.errorTextView, validation);
                                break;
                            case "position":
                                showHandle(((FragmentCreateCenterUserBinding) binding).positionErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).positionErrorLayout.errorTextView, validation);
                                break;
                            case "room_id":
                                showHandle(((FragmentCreateCenterUserBinding) binding).roomErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).roomErrorLayout.errorTextView, validation);
                                break;
                            case "nickname":
                                showHandle(((FragmentCreateCenterUserBinding) binding).nicknameErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).nicknameErrorLayout.errorTextView, validation);
                                break;
                            case "create_case":
                                showHandle(((FragmentCreateCenterUserBinding) binding).caseErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).caseErrorLayout.errorTextView, validation);
                                break;
                        }

                    } else if (binding instanceof FragmentCreateClientReportBinding) {
                        switch (key) {
                            case "encryption":
                                showHandle(((FragmentCreateClientReportBinding) binding).encryptionErrorLayout.getRoot(), ((FragmentCreateClientReportBinding) binding).encryptionErrorLayout.errorTextView, validation);
                                break;
                            case "description":
                                showHandle(((FragmentCreateClientReportBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateClientReportBinding) binding).descriptionErrorLayout.errorTextView, validation);
                                break;
                        }

                    } else if (binding instanceof FragmentCreateDocumentBinding) {
                        switch (key) {
                            case "name":
                                showHandle(((FragmentCreateDocumentBinding) binding).nameErrorLayout.getRoot(), ((FragmentCreateDocumentBinding) binding).nameErrorLayout.errorTextView, validation);
                                break;
                            case "description":
                                showHandle(((FragmentCreateDocumentBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateDocumentBinding) binding).descriptionErrorLayout.errorTextView, validation);
                                break;
                            case "file":
                                showHandle(((FragmentCreateDocumentBinding) binding).fileErrorLayout.getRoot(), ((FragmentCreateDocumentBinding) binding).fileErrorLayout.errorTextView, validation);
                                break;
                        }

                    } else if (binding instanceof FragmentCreatePlatformBinding) {
                        switch (key) {
                            case "title":
                                showHandle(((FragmentCreatePlatformBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).titleErrorLayout.errorTextView, validation);
                                break;
                            case "type":
                                showHandle(((FragmentCreatePlatformBinding) binding).sessionTypeErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).sessionTypeErrorLayout.errorTextView, validation);
                                break;
                            case "identifier_type":
                                showHandle(((FragmentCreatePlatformBinding) binding).indentifierTypeErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).indentifierTypeErrorLayout.errorTextView, validation);
                                break;
                            case "identifier":
                                showHandle(((FragmentCreatePlatformBinding) binding).indentifierErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).indentifierErrorLayout.errorTextView, validation);
                                break;
                            case "selected":
                                showHandle(((FragmentCreatePlatformBinding) binding).sessionErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).sessionErrorLayout.errorTextView, validation);
                                break;
                            case "available":
                                showHandle(((FragmentCreatePlatformBinding) binding).availableErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).availableErrorLayout.errorTextView, validation);
                                break;
                        }

                    } else if (binding instanceof FragmentCreatePracticeBinding) {
                        switch (key) {
                            case "name":
                                showHandle(((FragmentCreatePracticeBinding) binding).nameErrorLayout.getRoot(), ((FragmentCreatePracticeBinding) binding).nameErrorLayout.errorTextView, validation);
                                break;
                            case "description":
                                showHandle(((FragmentCreatePracticeBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreatePracticeBinding) binding).descriptionErrorLayout.errorTextView, validation);
                                break;
                            case "file":
                                showHandle(((FragmentCreatePracticeBinding) binding).fileErrorLayout.getRoot(), ((FragmentCreatePracticeBinding) binding).fileErrorLayout.errorTextView, validation);
                                break;
                        }

                    } else if (binding instanceof FragmentCreateRoomBinding) {
                        if (key.equals("psychologist_id")) {
                            showHandle(((FragmentCreateRoomBinding) binding).psychologyErrorLayout.getRoot(), ((FragmentCreateRoomBinding) binding).psychologyErrorLayout.errorTextView, validation);
                        }

                    } else if (binding instanceof FragmentCreateRoomUserBinding) {
                        if (key.equals("user_id")) {
                            showHandle(((FragmentCreateRoomUserBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateRoomUserBinding) binding).referenceErrorLayout.errorTextView, validation);
                        }

                    } else if (binding instanceof FragmentCreateSampleBinding) {
                        switch (key) {
                            case "scale_id":
                                showHandle(((FragmentCreateSampleBinding) binding).scaleErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).scaleErrorLayout.errorTextView, validation);
                                break;
                            case "room_id":
                                showHandle(((FragmentCreateSampleBinding) binding).roomErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).roomErrorLayout.errorTextView, validation);
                                break;
                            case "type":
                                showHandle(((FragmentCreateSampleBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).typeErrorLayout.errorTextView, validation);
                                break;
                            case "title":
                                showHandle(((FragmentCreateSampleBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).titleErrorLayout.errorTextView, validation);
                                break;
                            case "members_count":
                                showHandle(((FragmentCreateSampleBinding) binding).membersCountErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).membersCountErrorLayout.errorTextView, validation);
                                break;
                            case "case_status":
                                showHandle(((FragmentCreateSampleBinding) binding).caseStatusErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).caseStatusErrorLayout.errorTextView, validation);
                                break;
                            case "problem":
                                showHandle(((FragmentCreateSampleBinding) binding).problemErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).problemErrorLayout.errorTextView, validation);
                                break;
                            case "case_id":
                                showHandle(((FragmentCreateSampleBinding) binding).caseErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).caseErrorLayout.errorTextView, validation);
                                break;
                            case "session_id":
                                showHandle(((FragmentCreateSampleBinding) binding).sessionErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).sessionErrorLayout.errorTextView, validation);
                                break;
                            case "client_id":
                                if (((FragmentCreateSampleBinding) binding).typeTabLayout.getSelectedTabPosition() == 0)
                                    showHandle(((FragmentCreateSampleBinding) binding).clientErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).clientErrorLayout.errorTextView, validation);
                                else if (((FragmentCreateSampleBinding) binding).typeTabLayout.getSelectedTabPosition() == 1)
                                    showHandle(((FragmentCreateSampleBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).referenceErrorLayout.errorTextView, validation);

                                break;
                            case "psychologist_description":
                                showHandle(((FragmentCreateSampleBinding) binding).psychologyDescriptionErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).psychologyDescriptionErrorLayout.errorTextView, validation);
                                break;
                        }

                    } else if (binding instanceof FragmentCreateSessionUserBinding) {
                        switch (key) {
                            case "field":
                                showHandle(((FragmentCreateSessionUserBinding) binding).axisErrorLayout.getRoot(), ((FragmentCreateSessionUserBinding) binding).axisErrorLayout.errorTextView, validation);
                                break;
                            case "session_platform":
                                showHandle(((FragmentCreateSessionUserBinding) binding).platformErrorLayout.getRoot(), ((FragmentCreateSessionUserBinding) binding).platformErrorLayout.errorTextView, validation);
                                break;
                            case "client_id":
                                showHandle(((FragmentCreateSessionUserBinding) binding).clientErrorLayout.getRoot(), ((FragmentCreateSessionUserBinding) binding).clientErrorLayout.errorTextView, validation);
                                break;
                            case "description":
                                showHandle(((FragmentCreateSessionUserBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateSessionUserBinding) binding).descriptionErrorLayout.errorTextView, validation);
                                break;
                        }

                    } else if (binding instanceof FragmentCreateTreasuryBinding) {
                        switch (key) {
                            case "title":
                                showHandle(((FragmentCreateTreasuryBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateTreasuryBinding) binding).titleErrorLayout.errorTextView, validation);
                                break;
                            case "region_id":
                                showHandle(((FragmentCreateTreasuryBinding) binding).regionErrorLayout.getRoot(), ((FragmentCreateTreasuryBinding) binding).regionErrorLayout.errorTextView, validation);
                                break;
                        }

                    } else if (binding instanceof FragmentCreateUserBinding) {
                        switch (key) {
                            case "name":
                                showHandle(((FragmentCreateUserBinding) binding).nameErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).nameErrorLayout.errorTextView, validation);
                                break;
                            case "mobile":
                                showHandle(((FragmentCreateUserBinding) binding).mobileErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).mobileErrorLayout.errorTextView, validation);
                                break;
                            case "email":
                                showHandle(((FragmentCreateUserBinding) binding).emailErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).emailErrorLayout.errorTextView, validation);
                                break;
                            case "password":
                                showHandle(((FragmentCreateUserBinding) binding).passwordErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).passwordErrorLayout.errorTextView, validation);
                                break;
                            case "birthday":
                                showHandle(((FragmentCreateUserBinding) binding).birthdayErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).birthdayErrorLayout.errorTextView, validation);
                                break;
                            case "status":
                                showHandle(((FragmentCreateUserBinding) binding).statusErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).statusErrorLayout.errorTextView, validation);
                                break;
                            case "type":
                                showHandle(((FragmentCreateUserBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).typeErrorLayout.errorTextView, validation);
                                break;
                            case "gender":
                                showHandle(((FragmentCreateUserBinding) binding).genderErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).genderErrorLayout.errorTextView, validation);
                                break;
                        }

                    } else if (binding instanceof FragmentReserveScheduleBinding) {
                        switch (key) {
                            case "field":
                                showHandle(((FragmentReserveScheduleBinding) binding).fieldErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).fieldErrorLayout.errorTextView, validation);
                                break;
                            case "session_platform":
                                showHandle(((FragmentReserveScheduleBinding) binding).platformErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).platformErrorLayout.errorTextView, validation);
                                break;
                            case "client_typ":
                                showHandle(((FragmentReserveScheduleBinding) binding).typeErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).typeErrorLayout.errorTextView, validation);
                                break;
                            case "case_id":
                                showHandle(((FragmentReserveScheduleBinding) binding).caseErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).caseErrorLayout.errorTextView, validation);
                                break;
                            case "client_id":
                                if (((FragmentReserveScheduleBinding) binding).typeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.second_radioButton && ((FragmentReserveScheduleBinding) binding).clientIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                                    showHandle(((FragmentReserveScheduleBinding) binding).clientErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).clientErrorLayout.errorTextView, validation);
                                else if (((FragmentReserveScheduleBinding) binding).typeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.first_radioButton)
                                    showHandle(((FragmentReserveScheduleBinding) binding).referenceErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).referenceErrorLayout.errorTextView, validation);

                                break;
                            case "problem":
                                showHandle(((FragmentReserveScheduleBinding) binding).problemErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).problemErrorLayout.errorTextView, validation);
                                break;
                            case "nickname":
                                showHandle(((FragmentReserveScheduleBinding) binding).nameErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).nameErrorLayout.errorTextView, validation);
                                break;
                            case "description":
                                showHandle(((FragmentReserveScheduleBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).descriptionErrorLayout.errorTextView, validation);
                                break;
                            case "treasurie_id":
                                showHandle(((FragmentReserveScheduleBinding) binding).treasuryErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).treasuryErrorLayout.errorTextView, validation);
                                break;
                        }

                    // -------------------- Edit

                    } else if (binding instanceof FragmentEditCenterUserBinding) {
                        switch (key) {
                            case "position":
                                showHandle(((FragmentEditCenterUserBinding) binding).positionErrorLayout.getRoot(), ((FragmentEditCenterUserBinding) binding).positionErrorLayout.errorTextView, validation);
                                break;
                            case "name":
                                showHandle(((FragmentEditCenterUserBinding) binding).nicknameErrorLayout.getRoot(), ((FragmentEditCenterUserBinding) binding).nicknameErrorLayout.errorTextView, validation);
                                break;
                            case "status":
                                showHandle(((FragmentEditCenterUserBinding) binding).statusErrorLayout.getRoot(), ((FragmentEditCenterUserBinding) binding).statusErrorLayout.errorTextView, validation);
                                break;
                        }

                    } else if (binding instanceof FragmentEditPlatformBinding) {
                        switch (key) {
                            case "title":
                                showHandle(((FragmentEditPlatformBinding) binding).titleErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).titleErrorLayout.errorTextView, validation);
                                break;
                            case "type":
                                showHandle(((FragmentEditPlatformBinding) binding).sessionTypeErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).sessionTypeErrorLayout.errorTextView, validation);
                                break;
                            case "identifier_type":
                                showHandle(((FragmentEditPlatformBinding) binding).indentifierTypeErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).indentifierTypeErrorLayout.errorTextView, validation);
                                break;
                            case "identifier":
                                showHandle(((FragmentEditPlatformBinding) binding).indentifierErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).indentifierErrorLayout.errorTextView, validation);
                                break;
                            case "selected":
                                showHandle(((FragmentEditPlatformBinding) binding).sessionErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).sessionErrorLayout.errorTextView, validation);
                                break;
                            case "available":
                                showHandle(((FragmentEditPlatformBinding) binding).availableErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).availableErrorLayout.errorTextView, validation);
                                break;
                        }

                    } else if (binding instanceof FragmentEditTreasuryBinding) {
                        if (key.equals("title")) {
                            showHandle(((FragmentEditTreasuryBinding) binding).titleErrorLayout.getRoot(), ((FragmentEditTreasuryBinding) binding).titleErrorLayout.errorTextView, validation);
                        }

                    // -------------------- Index

                    } else if (binding instanceof FragmentBanksBinding) {
                        switch (key) {
                            case "iban":
                                showHandle(((FragmentBanksBinding) binding).ibanErrorLayout.getRoot(), ((FragmentBanksBinding) binding).ibanErrorLayout.errorTextView, validation);
                                break;
                            case "iban_id":
                                showHandle(((FragmentBanksBinding) binding).accountErrorLayout.getRoot(), ((FragmentBanksBinding) binding).accountErrorLayout.errorTextView, validation);
                                break;
                            case "type":
                                showHandle(((FragmentBanksBinding) binding).typeErrorLayout.getRoot(), ((FragmentBanksBinding) binding).typeErrorLayout.errorTextView, validation);
                                break;
                            case "amount":
                                showHandle(((FragmentBanksBinding) binding).amountErrorLayout.getRoot(), ((FragmentBanksBinding) binding).amountErrorLayout.errorTextView, validation);
                                break;
                            case "weekday":
                                showHandle(((FragmentBanksBinding) binding).weekdayErrorLayout.getRoot(), ((FragmentBanksBinding) binding).weekdayErrorLayout.errorTextView, validation);
                                break;
                            case "day":
                                showHandle(((FragmentBanksBinding) binding).monthdayErrorLayout.getRoot(), ((FragmentBanksBinding) binding).monthdayErrorLayout.errorTextView, validation);
                                break;
                        }

                    } else if (binding instanceof FragmentCommissionsBinding) {
                        if (key.equals("commission")) {
                            showHandle(((FragmentCommissionsBinding) binding).shareErrorLayout.getRoot(), ((FragmentCommissionsBinding) binding).shareErrorLayout.errorTextView, validation);
                        }

                    } else if (binding instanceof FragmentPaymentsBinding) {
                        switch (key) {
                            case "treasury_id":
                                showHandle(((FragmentPaymentsBinding) binding).treasuryErrorLayout.getRoot(), ((FragmentPaymentsBinding) binding).treasuryErrorLayout.errorTextView, validation);
                                break;
                            case "amount":
                                showHandle(((FragmentPaymentsBinding) binding).amountErrorLayout.getRoot(), ((FragmentPaymentsBinding) binding).amountErrorLayout.errorTextView, validation);
                                break;
                        }

                    // -------------------- Tab

                    } else if (binding instanceof FragmentEditCenterTabAvatarBinding) {
                        if (key.equals("avatar")) {
                            showHandle(((FragmentEditCenterTabAvatarBinding) binding).avatarErrorLayout.getRoot(), ((FragmentEditCenterTabAvatarBinding) binding).avatarErrorLayout.errorTextView, validation);
                        }

                    } else if (binding instanceof FragmentEditCenterTabDetailBinding) {
                        switch (key) {
                            case "manager_id":
                                showHandle(((FragmentEditCenterTabDetailBinding) binding).managerErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).managerErrorLayout.errorTextView, validation);
                                break;
                            case "title":
                                showHandle(((FragmentEditCenterTabDetailBinding) binding).titleErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).titleErrorLayout.errorTextView, validation);
                                break;
                            case "address":
                                showHandle(((FragmentEditCenterTabDetailBinding) binding).addressErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).addressErrorLayout.errorTextView, validation);
                                break;
                            case "description":
                                showHandle(((FragmentEditCenterTabDetailBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).descriptionErrorLayout.errorTextView, validation);
                                break;
                            case "phone_numbers":
                                showHandle(((FragmentEditCenterTabDetailBinding) binding).phonesErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).phonesErrorLayout.errorTextView, validation);
                                break;
                        }

                    } else if (binding instanceof FragmentEditUserTabAvatarBinding) {
                        if (key.equals("avatar")) {
                            showHandle(((FragmentEditUserTabAvatarBinding) binding).avatarErrorLayout.getRoot(), ((FragmentEditUserTabAvatarBinding) binding).avatarErrorLayout.errorTextView, validation);
                        }

                    } else if (binding instanceof FragmentEditUserTabPasswordBinding) {
                        switch (key) {
                            case "password":
                                showHandle(((FragmentEditUserTabPasswordBinding) binding).currentPasswordErrorLayout.getRoot(), ((FragmentEditUserTabPasswordBinding) binding).currentPasswordErrorLayout.errorTextView, validation);
                                break;
                            case "new_password":
                                showHandle(((FragmentEditUserTabPasswordBinding) binding).newPasswordErrorLayout.getRoot(), ((FragmentEditUserTabPasswordBinding) binding).newPasswordErrorLayout.errorTextView, validation);
                                break;
                        }

                    } else if (binding instanceof FragmentEditUserTabPersonalBinding) {
                        switch (key) {
                            case "name":
                                showHandle(((FragmentEditUserTabPersonalBinding) binding).nameErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).nameErrorLayout.errorTextView, validation);
                                break;
                            case "mobile":
                                showHandle(((FragmentEditUserTabPersonalBinding) binding).mobileErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).mobileErrorLayout.errorTextView, validation);
                                break;
                            case "email":
                                showHandle(((FragmentEditUserTabPersonalBinding) binding).emailErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).emailErrorLayout.errorTextView, validation);
                                break;
                            case "birthday":
                                showHandle(((FragmentEditUserTabPersonalBinding) binding).birthdayErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).birthdayErrorLayout.errorTextView, validation);
                                break;
                            case "status":
                                showHandle(((FragmentEditUserTabPersonalBinding) binding).statusErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).statusErrorLayout.errorTextView, validation);
                                break;
                            case "type":
                                showHandle(((FragmentEditUserTabPersonalBinding) binding).typeErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).typeErrorLayout.errorTextView, validation);
                                break;
                            case "gender":
                                showHandle(((FragmentEditUserTabPersonalBinding) binding).genderErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).genderErrorLayout.errorTextView, validation);
                                break;
                        }

                    // -------------------- Sheet

                    } else if (binding instanceof SheetBulkSampleBinding) {
                        if (key.equals("nickname")) {
                            showHandle(((SheetBulkSampleBinding) binding).nicknameErrorLayout.getRoot(), ((SheetBulkSampleBinding) binding).nicknameErrorLayout.errorTextView, validation);
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

    public void showValid(String key, String validation, ViewBinding binding) {

        // -------------------- Tab

        if (binding instanceof FragmentCreateScheduleTabPaymentBinding) {
            if (key.equals("payment_status")) {
                showHandle(((FragmentCreateScheduleTabPaymentBinding) binding).paymentErrorLayout.getRoot(), ((FragmentCreateScheduleTabPaymentBinding) binding).paymentErrorLayout.errorTextView, validation);
            }

        } else if (binding instanceof FragmentCreateScheduleTabPlatformBinding) {
            switch (key) {
                case "platforms":
                    showHandle(((FragmentCreateScheduleTabPlatformBinding) binding).platformsErrorLayout.getRoot(), ((FragmentCreateScheduleTabPlatformBinding) binding).platformsErrorLayout.errorTextView, validation);
                    break;
                case "pin_platform":
                    showHandle(((FragmentCreateScheduleTabPlatformBinding) binding).pinPlatformErrorLayout.getRoot(), ((FragmentCreateScheduleTabPlatformBinding) binding).pinPlatformErrorLayout.errorTextView, validation);
                    break;
                case "identifier_platform":
                    showHandle(((FragmentCreateScheduleTabPlatformBinding) binding).identifierPlatformErrorLayout.getRoot(), ((FragmentCreateScheduleTabPlatformBinding) binding).identifierPlatformErrorLayout.errorTextView, validation);
                    break;
            }

        } else if (binding instanceof FragmentCreateScheduleTabReferenceBinding) {
            switch (key) {
                case "selection_type":
                    showHandle(((FragmentCreateScheduleTabReferenceBinding) binding).selectionErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).selectionErrorLayout.errorTextView, validation);
                    break;
                case "clients_type":
                    showHandle(((FragmentCreateScheduleTabReferenceBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).typeErrorLayout.errorTextView, validation);
                    break;
                case "case_id":
                    showHandle(((FragmentCreateScheduleTabReferenceBinding) binding).caseErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).caseErrorLayout.errorTextView, validation);
                    break;
                case "group_session":
                    showHandle(((FragmentCreateScheduleTabReferenceBinding) binding).bulkSessionErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).bulkSessionErrorLayout.errorTextView, validation);
                    break;
                case "clients_number":
                    showHandle(((FragmentCreateScheduleTabReferenceBinding) binding).countErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).countErrorLayout.errorTextView, validation);
                    break;
            }

        } else if (binding instanceof FragmentCreateScheduleTabSessionBinding) {
            switch (key) {
                case "status":
                    showHandle(((FragmentCreateScheduleTabSessionBinding) binding).statusErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).statusErrorLayout.errorTextView, validation);
                    break;
                case "opens_at_type":
                    showHandle(((FragmentCreateScheduleTabSessionBinding) binding).startTypeErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).startTypeErrorLayout.errorTextView, validation);
                    break;
                case "opens_at":
                    if (((FragmentCreateScheduleTabSessionBinding) binding).startTypeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.second_radioButton)
                        showHandle(((FragmentCreateScheduleTabSessionBinding) binding).startRelativeErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).startRelativeErrorLayout.errorTextView, validation);
                    else if (((FragmentCreateScheduleTabSessionBinding) binding).startTypeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.first_radioButton)
                        showHandle(((FragmentCreateScheduleTabSessionBinding) binding).startAccurateErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).startAccurateErrorLayout.errorTextView, validation);

                    break;
                case "closed_at_type":
                    showHandle(((FragmentCreateScheduleTabSessionBinding) binding).endTypeErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).endTypeErrorLayout.errorTextView, validation);
                    break;
                case "closed_at":
                    if (((FragmentCreateScheduleTabSessionBinding) binding).endTypeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.second_radioButton)
                        showHandle(((FragmentCreateScheduleTabSessionBinding) binding).endRelativeErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).endRelativeErrorLayout.errorTextView, validation);
                    else if (((FragmentCreateScheduleTabSessionBinding) binding).endTypeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.first_radioButton)
                        showHandle(((FragmentCreateScheduleTabSessionBinding) binding).endAccurateErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).endAccurateErrorLayout.errorTextView, validation);

                    break;
                case "fields":
                    showHandle(((FragmentCreateScheduleTabSessionBinding) binding).axisErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).axisErrorLayout.errorTextView, validation);
                    break;
                case "description":
                    showHandle(((FragmentCreateScheduleTabSessionBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).descriptionErrorLayout.errorTextView, validation);
                    break;
                case "client_reminder":
                    showHandle(((FragmentCreateScheduleTabSessionBinding) binding).coordinationErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).coordinationErrorLayout.errorTextView, validation);
                    break;
            }

        } else if (binding instanceof FragmentCreateScheduleTabTimeBinding) {
            switch (key) {
                case "time":
                    showHandle(((FragmentCreateScheduleTabTimeBinding) binding).startTimeErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).startTimeErrorLayout.errorTextView, validation);
                    break;
                case "duration":
                    showHandle(((FragmentCreateScheduleTabTimeBinding) binding).durationErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).durationErrorLayout.errorTextView, validation);
                    break;
                case "date_type":
                    showHandle(((FragmentCreateScheduleTabTimeBinding) binding).dateTypeErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).dateTypeErrorLayout.errorTextView, validation);
                    break;
                case "date":
                    showHandle(((FragmentCreateScheduleTabTimeBinding) binding).specifiedDateErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).specifiedDateErrorLayout.errorTextView, validation);
                    break;
                case "week_days":
                    showHandle(((FragmentCreateScheduleTabTimeBinding) binding).patternDaysErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).patternDaysErrorLayout.errorTextView, validation);
                    break;
                case "repeat_status":
                    showHandle(((FragmentCreateScheduleTabTimeBinding) binding).patternTypeErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).patternTypeErrorLayout.errorTextView, validation);
                    break;
                case "repeat":
                    showHandle(((FragmentCreateScheduleTabTimeBinding) binding).repeatWeeksErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).repeatWeeksErrorLayout.errorTextView, validation);
                    break;
                case "repeat_from":
                    showHandle(((FragmentCreateScheduleTabTimeBinding) binding).periodStartDateErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).periodStartDateErrorLayout.errorTextView, validation);
                    break;
                case "repeat_to":
                    showHandle(((FragmentCreateScheduleTabTimeBinding) binding).periodEndDateErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).periodEndDateErrorLayout.errorTextView, validation);
                    break;
            }

        } else if (binding instanceof FragmentCreateSessionTabPaymentBinding) {
            if (key.equals("payment_status")) {
                showHandle(((FragmentCreateSessionTabPaymentBinding) binding).paymentErrorLayout.getRoot(), ((FragmentCreateSessionTabPaymentBinding) binding).paymentErrorLayout.errorTextView, validation);
            }

        } else if (binding instanceof FragmentCreateSessionTabPlatformBinding) {
            switch (key) {
                case "platforms":
                    showHandle(((FragmentCreateSessionTabPlatformBinding) binding).platformsErrorLayout.getRoot(), ((FragmentCreateSessionTabPlatformBinding) binding).platformsErrorLayout.errorTextView, validation);
                    break;
                case "pin_platform":
                    showHandle(((FragmentCreateSessionTabPlatformBinding) binding).pinPlatformErrorLayout.getRoot(), ((FragmentCreateSessionTabPlatformBinding) binding).pinPlatformErrorLayout.errorTextView, validation);
                    break;
                case "identifier_platform":
                    showHandle(((FragmentCreateSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.getRoot(), ((FragmentCreateSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.errorTextView, validation);
                    break;
            }

        } else if (binding instanceof FragmentCreateSessionTabSessionBinding) {
            switch (key) {
                case "status":
                    showHandle(((FragmentCreateSessionTabSessionBinding) binding).statusErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).statusErrorLayout.errorTextView, validation);
                    break;
                case "opens_at_type":
                    showHandle(((FragmentCreateSessionTabSessionBinding) binding).startTypeErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).startTypeErrorLayout.errorTextView, validation);
                    break;
                case "opens_at":
                    if (((FragmentCreateSessionTabSessionBinding) binding).startTypeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.second_radioButton)
                        showHandle(((FragmentCreateSessionTabSessionBinding) binding).startRelativeErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).startRelativeErrorLayout.errorTextView, validation);
                    else if (((FragmentCreateSessionTabSessionBinding) binding).startTypeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.first_radioButton)
                        showHandle(((FragmentCreateSessionTabSessionBinding) binding).startAccurateErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).startAccurateErrorLayout.errorTextView, validation);

                    break;
                case "closed_at_type":
                    showHandle(((FragmentCreateSessionTabSessionBinding) binding).endTypeErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).endTypeErrorLayout.errorTextView, validation);
                    break;
                case "closed_at":
                    if (((FragmentCreateSessionTabSessionBinding) binding).endTypeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.second_radioButton)
                        showHandle(((FragmentCreateSessionTabSessionBinding) binding).endRelativeErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).endRelativeErrorLayout.errorTextView, validation);
                    else if (((FragmentCreateSessionTabSessionBinding) binding).endTypeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.first_radioButton)
                        showHandle(((FragmentCreateSessionTabSessionBinding) binding).endAccurateErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).endAccurateErrorLayout.errorTextView, validation);

                    break;
                case "fields":
                    showHandle(((FragmentCreateSessionTabSessionBinding) binding).axisErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).axisErrorLayout.errorTextView, validation);
                    break;
                case "description":
                    showHandle(((FragmentCreateSessionTabSessionBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).descriptionErrorLayout.errorTextView, validation);
                    break;
                case "client_reminder":
                    showHandle(((FragmentCreateSessionTabSessionBinding) binding).coordinationErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).coordinationErrorLayout.errorTextView, validation);
                    break;
            }

        } else if (binding instanceof FragmentCreateSessionTabTimeBinding) {
            switch (key) {
                case "time":
                    showHandle(((FragmentCreateSessionTabTimeBinding) binding).startTimeErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).startTimeErrorLayout.errorTextView, validation);
                    break;
                case "duration":
                    showHandle(((FragmentCreateSessionTabTimeBinding) binding).durationErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).durationErrorLayout.errorTextView, validation);
                    break;
                case "date_type":
                    showHandle(((FragmentCreateSessionTabTimeBinding) binding).dateTypeErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).dateTypeErrorLayout.errorTextView, validation);
                    break;
                case "date":
                    showHandle(((FragmentCreateSessionTabTimeBinding) binding).specifiedDateErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).specifiedDateErrorLayout.errorTextView, validation);
                    break;
                case "week_days":
                    showHandle(((FragmentCreateSessionTabTimeBinding) binding).patternDaysErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).patternDaysErrorLayout.errorTextView, validation);
                    break;
                case "repeat_status":
                    showHandle(((FragmentCreateSessionTabTimeBinding) binding).patternTypeErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).patternTypeErrorLayout.errorTextView, validation);
                    break;
                case "repeat":
                    showHandle(((FragmentCreateSessionTabTimeBinding) binding).repeatWeeksErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).repeatWeeksErrorLayout.errorTextView, validation);
                    break;
                case "repeat_from":
                    showHandle(((FragmentCreateSessionTabTimeBinding) binding).periodStartDateErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).periodStartDateErrorLayout.errorTextView, validation);
                    break;
                case "repeat_to":
                    showHandle(((FragmentCreateSessionTabTimeBinding) binding).periodEndDateErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).periodEndDateErrorLayout.errorTextView, validation);
                    break;
            }

        } else if (binding instanceof FragmentEditSessionTabPaymentBinding) {
            if (key.equals("payment_status")) {
                showHandle(((FragmentEditSessionTabPaymentBinding) binding).paymentErrorLayout.getRoot(), ((FragmentEditSessionTabPaymentBinding) binding).paymentErrorLayout.errorTextView, validation);
            }

        } else if (binding instanceof FragmentEditSessionTabPlatformBinding) {
            switch (key) {
                case "platforms":
                    showHandle(((FragmentEditSessionTabPlatformBinding) binding).platformsErrorLayout.getRoot(), ((FragmentEditSessionTabPlatformBinding) binding).platformsErrorLayout.errorTextView, validation);
                    break;
                case "pin_platform":
                    showHandle(((FragmentEditSessionTabPlatformBinding) binding).pinPlatformErrorLayout.getRoot(), ((FragmentEditSessionTabPlatformBinding) binding).pinPlatformErrorLayout.errorTextView, validation);
                    break;
                case "identifier_platform":
                    showHandle(((FragmentEditSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.getRoot(), ((FragmentEditSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.errorTextView, validation);
                    break;
            }

        } else if (binding instanceof FragmentEditSessionTabReferenceBinding) {
            switch (key) {
                case "selection_type":
                    showHandle(((FragmentEditSessionTabReferenceBinding) binding).selectionErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).selectionErrorLayout.errorTextView, validation);
                    break;
                case "clients_type":
                    showHandle(((FragmentEditSessionTabReferenceBinding) binding).typeErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).typeErrorLayout.errorTextView, validation);
                    break;
                case "case_id":
                    showHandle(((FragmentEditSessionTabReferenceBinding) binding).caseErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).caseErrorLayout.errorTextView, validation);
                    break;
                case "problem":
                    showHandle(((FragmentEditSessionTabReferenceBinding) binding).problemErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).problemErrorLayout.errorTextView, validation);
                    break;
                case "group_session":
                    showHandle(((FragmentEditSessionTabReferenceBinding) binding).bulkSessionErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).bulkSessionErrorLayout.errorTextView, validation);
                    break;
                case "clients_number":
                    showHandle(((FragmentEditSessionTabReferenceBinding) binding).countErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).countErrorLayout.errorTextView, validation);
                    break;
            }

        } else if (binding instanceof FragmentEditSessionTabSessionBinding) {
            switch (key) {
                case "status":
                    showHandle(((FragmentEditSessionTabSessionBinding) binding).statusErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).statusErrorLayout.errorTextView, validation);
                    break;
                case "opens_at_type":
                    showHandle(((FragmentEditSessionTabSessionBinding) binding).startTypeErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).startTypeErrorLayout.errorTextView, validation);
                    break;
                case "opens_at":
                    if (((FragmentEditSessionTabSessionBinding) binding).startTypeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.second_radioButton)
                        showHandle(((FragmentEditSessionTabSessionBinding) binding).startRelativeErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).startRelativeErrorLayout.errorTextView, validation);
                    else if (((FragmentEditSessionTabSessionBinding) binding).startTypeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.first_radioButton)
                        showHandle(((FragmentEditSessionTabSessionBinding) binding).startAccurateErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).startAccurateErrorLayout.errorTextView, validation);

                    break;
                case "closed_at_type":
                    showHandle(((FragmentEditSessionTabSessionBinding) binding).endTypeErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).endTypeErrorLayout.errorTextView, validation);
                    break;
                case "closed_at":
                    if (((FragmentEditSessionTabSessionBinding) binding).endTypeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.second_radioButton)
                        showHandle(((FragmentEditSessionTabSessionBinding) binding).endRelativeErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).endRelativeErrorLayout.errorTextView, validation);
                    else if (((FragmentEditSessionTabSessionBinding) binding).endTypeIncludeLayout.getRoot().getCheckedRadioButtonId() == R.id.first_radioButton)
                        showHandle(((FragmentEditSessionTabSessionBinding) binding).endAccurateErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).endAccurateErrorLayout.errorTextView, validation);

                    break;
                case "description":
                    showHandle(((FragmentEditSessionTabSessionBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).descriptionErrorLayout.errorTextView, validation);
                    break;
                case "client_reminder":
                    showHandle(((FragmentEditSessionTabSessionBinding) binding).coordinationErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).coordinationErrorLayout.errorTextView, validation);
                    break;
            }

        } else if (binding instanceof FragmentEditSessionTabTimeBinding) {
            switch (key) {
                case "time":
                    showHandle(((FragmentEditSessionTabTimeBinding) binding).startTimeErrorLayout.getRoot(), ((FragmentEditSessionTabTimeBinding) binding).startTimeErrorLayout.errorTextView, validation);
                    break;
                case "duration":
                    showHandle(((FragmentEditSessionTabTimeBinding) binding).durationErrorLayout.getRoot(), ((FragmentEditSessionTabTimeBinding) binding).durationErrorLayout.errorTextView, validation);
                    break;
                case "date":
                    showHandle(((FragmentEditSessionTabTimeBinding) binding).startDateErrorLayout.getRoot(), ((FragmentEditSessionTabTimeBinding) binding).startDateErrorLayout.errorTextView, validation);
                    break;
            }

        }

        // -------------------- End

    }

    public void hideValid(ViewBinding binding) {

        // -------------------- Auth

        if (binding instanceof FragmentAuthLoginBinding) {
            if (((FragmentAuthLoginBinding) binding).errorIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentAuthLoginBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthLoginBinding) binding).errorIncludeLayout.errorTextView);

        } else if (binding instanceof FragmentAuthPasswordBinding) {
            if (((FragmentAuthPasswordBinding) binding).errorIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentAuthPasswordBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPasswordBinding) binding).errorIncludeLayout.errorTextView);

        } else if (binding instanceof FragmentAuthPasswordChangeBinding) {
            if (((FragmentAuthPasswordChangeBinding) binding).errorIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentAuthPasswordChangeBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPasswordChangeBinding) binding).errorIncludeLayout.errorTextView);

        } else if (binding instanceof FragmentAuthPasswordRecoverBinding) {
            if (((FragmentAuthPasswordRecoverBinding) binding).errorIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentAuthPasswordRecoverBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPasswordRecoverBinding) binding).errorIncludeLayout.errorTextView);

        } else if (binding instanceof FragmentAuthPinBinding) {
            if (((FragmentAuthPinBinding) binding).errorIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentAuthPinBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPinBinding) binding).errorIncludeLayout.errorTextView);

        } else if (binding instanceof FragmentAuthRegisterBinding) {
            if (((FragmentAuthRegisterBinding) binding).errorIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentAuthRegisterBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthRegisterBinding) binding).errorIncludeLayout.errorTextView);

        } else if (binding instanceof FragmentAuthSerialBinding) {
            if (((FragmentAuthSerialBinding) binding).errorIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentAuthSerialBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthSerialBinding) binding).errorIncludeLayout.errorTextView);

        // -------------------- Create

        } else if (binding instanceof FragmentCreateBillBinding) {
            if (((FragmentCreateBillBinding) binding).titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateBillBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).titleErrorLayout.errorTextView);
            if (((FragmentCreateBillBinding) binding).referenceErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateBillBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).referenceErrorLayout.errorTextView);
            if (((FragmentCreateBillBinding) binding).typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateBillBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).typeErrorLayout.errorTextView);
            if (((FragmentCreateBillBinding) binding).treasuryErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateBillBinding) binding).treasuryErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).treasuryErrorLayout.errorTextView);
            if (((FragmentCreateBillBinding) binding).amountErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateBillBinding) binding).amountErrorLayout.getRoot(), ((FragmentCreateBillBinding) binding).amountErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateCaseBinding) {
            if (((FragmentCreateCaseBinding) binding).titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateCaseBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateCaseBinding) binding).titleErrorLayout.errorTextView);
            if (((FragmentCreateCaseBinding) binding).referenceErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateCaseBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateCaseBinding) binding).referenceErrorLayout.errorTextView);
            if (((FragmentCreateCaseBinding) binding).problemErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateCaseBinding) binding).problemErrorLayout.getRoot(), ((FragmentCreateCaseBinding) binding).problemErrorLayout.errorTextView);
            if (((FragmentCreateCaseBinding) binding).tagsErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateCaseBinding) binding).tagsErrorLayout.getRoot(), ((FragmentCreateCaseBinding) binding).tagsErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateCaseUserBinding) {
            if (((FragmentCreateCaseUserBinding) binding).referenceErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateCaseUserBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateCaseUserBinding) binding).referenceErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateCenterBinding) {
            if (((FragmentCreateCenterBinding) binding).typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateCenterBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).typeErrorLayout.errorTextView);
            if (((FragmentCreateCenterBinding) binding).managerErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateCenterBinding) binding).managerErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).managerErrorLayout.errorTextView);
            if (((FragmentCreateCenterBinding) binding).titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateCenterBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).titleErrorLayout.errorTextView);
            if (((FragmentCreateCenterBinding) binding).addressErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateCenterBinding) binding).addressErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).addressErrorLayout.errorTextView);
            if (((FragmentCreateCenterBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateCenterBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).descriptionErrorLayout.errorTextView);
            if (((FragmentCreateCenterBinding) binding).avatarErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateCenterBinding) binding).avatarErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).avatarErrorLayout.errorTextView);
            if (((FragmentCreateCenterBinding) binding).phonesErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateCenterBinding) binding).phonesErrorLayout.getRoot(), ((FragmentCreateCenterBinding) binding).phonesErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateCenterUserBinding) {
            if (((FragmentCreateCenterUserBinding) binding).mobileErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateCenterUserBinding) binding).mobileErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).mobileErrorLayout.errorTextView);
            if (((FragmentCreateCenterUserBinding) binding).positionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateCenterUserBinding) binding).positionErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).positionErrorLayout.errorTextView);
            if (((FragmentCreateCenterUserBinding) binding).roomErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateCenterUserBinding) binding).roomErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).roomErrorLayout.errorTextView);
            if (((FragmentCreateCenterUserBinding) binding).nicknameErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateCenterUserBinding) binding).nicknameErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).nicknameErrorLayout.errorTextView);
            if (((FragmentCreateCenterUserBinding) binding).caseErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateCenterUserBinding) binding).caseErrorLayout.getRoot(), ((FragmentCreateCenterUserBinding) binding).caseErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateClientReportBinding) {
            if (((FragmentCreateClientReportBinding) binding).encryptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateClientReportBinding) binding).encryptionErrorLayout.getRoot(), ((FragmentCreateClientReportBinding) binding).encryptionErrorLayout.errorTextView);
            if (((FragmentCreateClientReportBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateClientReportBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateClientReportBinding) binding).descriptionErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateDocumentBinding) {
            if (((FragmentCreateDocumentBinding) binding).nameErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateDocumentBinding) binding).nameErrorLayout.getRoot(), ((FragmentCreateDocumentBinding) binding).nameErrorLayout.errorTextView);
            if (((FragmentCreateDocumentBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateDocumentBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateDocumentBinding) binding).descriptionErrorLayout.errorTextView);
            if (((FragmentCreateDocumentBinding) binding).fileErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateDocumentBinding) binding).fileErrorLayout.getRoot(), ((FragmentCreateDocumentBinding) binding).fileErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreatePlatformBinding) {
            if (((FragmentCreatePlatformBinding) binding).titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreatePlatformBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).titleErrorLayout.errorTextView);
            if (((FragmentCreatePlatformBinding) binding).sessionTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreatePlatformBinding) binding).sessionTypeErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).sessionTypeErrorLayout.errorTextView);
            if (((FragmentCreatePlatformBinding) binding).indentifierTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreatePlatformBinding) binding).indentifierTypeErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).indentifierTypeErrorLayout.errorTextView);
            if (((FragmentCreatePlatformBinding) binding).indentifierErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreatePlatformBinding) binding).indentifierErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).indentifierErrorLayout.errorTextView);
            if (((FragmentCreatePlatformBinding) binding).sessionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreatePlatformBinding) binding).sessionErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).sessionErrorLayout.errorTextView);
            if (((FragmentCreatePlatformBinding) binding).availableErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreatePlatformBinding) binding).availableErrorLayout.getRoot(), ((FragmentCreatePlatformBinding) binding).availableErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreatePracticeBinding) {
            if (((FragmentCreatePracticeBinding) binding).nameErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreatePracticeBinding) binding).nameErrorLayout.getRoot(), ((FragmentCreatePracticeBinding) binding).nameErrorLayout.errorTextView);
            if (((FragmentCreatePracticeBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreatePracticeBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreatePracticeBinding) binding).descriptionErrorLayout.errorTextView);
            if (((FragmentCreatePracticeBinding) binding).fileErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreatePracticeBinding) binding).fileErrorLayout.getRoot(), ((FragmentCreatePracticeBinding) binding).fileErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateRoomBinding) {
            if (((FragmentCreateRoomBinding) binding).psychologyErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateRoomBinding) binding).psychologyErrorLayout.getRoot(), ((FragmentCreateRoomBinding) binding).psychologyErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateRoomUserBinding) {
            if (((FragmentCreateRoomUserBinding) binding).referenceErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateRoomUserBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateRoomUserBinding) binding).referenceErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateSampleBinding) {
            if (((FragmentCreateSampleBinding) binding).scaleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSampleBinding) binding).scaleErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).scaleErrorLayout.errorTextView);
            if (((FragmentCreateSampleBinding) binding).roomErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSampleBinding) binding).roomErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).roomErrorLayout.errorTextView);
            if (((FragmentCreateSampleBinding) binding).typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSampleBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).typeErrorLayout.errorTextView);
            if (((FragmentCreateSampleBinding) binding).titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSampleBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).titleErrorLayout.errorTextView);
            if (((FragmentCreateSampleBinding) binding).membersCountErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSampleBinding) binding).membersCountErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).membersCountErrorLayout.errorTextView);
            if (((FragmentCreateSampleBinding) binding).caseStatusErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSampleBinding) binding).caseStatusErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).caseStatusErrorLayout.errorTextView);
            if (((FragmentCreateSampleBinding) binding).problemErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSampleBinding) binding).problemErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).problemErrorLayout.errorTextView);
            if (((FragmentCreateSampleBinding) binding).caseErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSampleBinding) binding).caseErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).caseErrorLayout.errorTextView);
            if (((FragmentCreateSampleBinding) binding).sessionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSampleBinding) binding).sessionErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).sessionErrorLayout.errorTextView);
            if (((FragmentCreateSampleBinding) binding).clientErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSampleBinding) binding).clientErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).clientErrorLayout.errorTextView);
            if (((FragmentCreateSampleBinding) binding).referenceErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSampleBinding) binding).referenceErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).referenceErrorLayout.errorTextView);
            if (((FragmentCreateSampleBinding) binding).psychologyDescriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSampleBinding) binding).psychologyDescriptionErrorLayout.getRoot(), ((FragmentCreateSampleBinding) binding).psychologyDescriptionErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateSessionUserBinding) {
            if (((FragmentCreateSessionUserBinding) binding).axisErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionUserBinding) binding).axisErrorLayout.getRoot(), ((FragmentCreateSessionUserBinding) binding).axisErrorLayout.errorTextView);
            if (((FragmentCreateSessionUserBinding) binding).platformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionUserBinding) binding).platformErrorLayout.getRoot(), ((FragmentCreateSessionUserBinding) binding).platformErrorLayout.errorTextView);
            if (((FragmentCreateSessionUserBinding) binding).clientErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionUserBinding) binding).clientErrorLayout.getRoot(), ((FragmentCreateSessionUserBinding) binding).clientErrorLayout.errorTextView);
            if (((FragmentCreateSessionUserBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionUserBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateSessionUserBinding) binding).descriptionErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateTreasuryBinding) {
            if (((FragmentCreateTreasuryBinding) binding).titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateTreasuryBinding) binding).titleErrorLayout.getRoot(), ((FragmentCreateTreasuryBinding) binding).titleErrorLayout.errorTextView);
            if (((FragmentCreateTreasuryBinding) binding).regionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateTreasuryBinding) binding).regionErrorLayout.getRoot(), ((FragmentCreateTreasuryBinding) binding).regionErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateUserBinding) {
            if (((FragmentCreateUserBinding) binding).nameErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateUserBinding) binding).nameErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).nameErrorLayout.errorTextView);
            if (((FragmentCreateUserBinding) binding).mobileErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateUserBinding) binding).mobileErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).mobileErrorLayout.errorTextView);
            if (((FragmentCreateUserBinding) binding).emailErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateUserBinding) binding).emailErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).emailErrorLayout.errorTextView);
            if (((FragmentCreateUserBinding) binding).passwordErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateUserBinding) binding).passwordErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).passwordErrorLayout.errorTextView);
            if (((FragmentCreateUserBinding) binding).birthdayErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateUserBinding) binding).birthdayErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).birthdayErrorLayout.errorTextView);
            if (((FragmentCreateUserBinding) binding).statusErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateUserBinding) binding).statusErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).statusErrorLayout.errorTextView);
            if (((FragmentCreateUserBinding) binding).typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateUserBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).typeErrorLayout.errorTextView);
            if (((FragmentCreateUserBinding) binding).genderErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateUserBinding) binding).genderErrorLayout.getRoot(), ((FragmentCreateUserBinding) binding).genderErrorLayout.errorTextView);

        } else if (binding instanceof FragmentReserveScheduleBinding) {
            if (((FragmentReserveScheduleBinding) binding).fieldErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentReserveScheduleBinding) binding).fieldErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).fieldErrorLayout.errorTextView);
            if (((FragmentReserveScheduleBinding) binding).platformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentReserveScheduleBinding) binding).platformErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).platformErrorLayout.errorTextView);
            if (((FragmentReserveScheduleBinding) binding).typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentReserveScheduleBinding) binding).typeErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).typeErrorLayout.errorTextView);
            if (((FragmentReserveScheduleBinding) binding).caseErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentReserveScheduleBinding) binding).caseErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).caseErrorLayout.errorTextView);
            if (((FragmentReserveScheduleBinding) binding).clientErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentReserveScheduleBinding) binding).clientErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).clientErrorLayout.errorTextView);
            if (((FragmentReserveScheduleBinding) binding).referenceErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentReserveScheduleBinding) binding).referenceErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).referenceErrorLayout.errorTextView);
            if (((FragmentReserveScheduleBinding) binding).problemErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentReserveScheduleBinding) binding).problemErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).problemErrorLayout.errorTextView);
            if (((FragmentReserveScheduleBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentReserveScheduleBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentReserveScheduleBinding) binding).descriptionErrorLayout.errorTextView);

        // -------------------- Edit

        } else if (binding instanceof FragmentEditCenterUserBinding) {
            if (((FragmentEditCenterUserBinding) binding).positionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditCenterUserBinding) binding).positionErrorLayout.getRoot(), ((FragmentEditCenterUserBinding) binding).positionErrorLayout.errorTextView);
            if (((FragmentEditCenterUserBinding) binding).nicknameErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditCenterUserBinding) binding).nicknameErrorLayout.getRoot(), ((FragmentEditCenterUserBinding) binding).nicknameErrorLayout.errorTextView);
            if (((FragmentEditCenterUserBinding) binding).statusErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditCenterUserBinding) binding).statusErrorLayout.getRoot(), ((FragmentEditCenterUserBinding) binding).statusErrorLayout.errorTextView);

        } else if (binding instanceof FragmentEditPlatformBinding) {
            if (((FragmentEditPlatformBinding) binding).titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditPlatformBinding) binding).titleErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).titleErrorLayout.errorTextView);
            if (((FragmentEditPlatformBinding) binding).sessionTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditPlatformBinding) binding).sessionTypeErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).sessionTypeErrorLayout.errorTextView);
            if (((FragmentEditPlatformBinding) binding).indentifierTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditPlatformBinding) binding).indentifierTypeErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).indentifierTypeErrorLayout.errorTextView);
            if (((FragmentEditPlatformBinding) binding).indentifierErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditPlatformBinding) binding).indentifierErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).indentifierErrorLayout.errorTextView);
            if (((FragmentEditPlatformBinding) binding).sessionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditPlatformBinding) binding).sessionErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).sessionErrorLayout.errorTextView);
            if (((FragmentEditPlatformBinding) binding).availableErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditPlatformBinding) binding).availableErrorLayout.getRoot(), ((FragmentEditPlatformBinding) binding).availableErrorLayout.errorTextView);

        } else if (binding instanceof FragmentEditTreasuryBinding) {
            if (((FragmentEditTreasuryBinding) binding).titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditTreasuryBinding) binding).titleErrorLayout.getRoot(), ((FragmentEditTreasuryBinding) binding).titleErrorLayout.errorTextView);

        // -------------------- Index

        } else if (binding instanceof FragmentBanksBinding) {
            if (((FragmentBanksBinding) binding).ibanErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentBanksBinding) binding).ibanErrorLayout.getRoot(), ((FragmentBanksBinding) binding).ibanErrorLayout.errorTextView);
            if (((FragmentBanksBinding) binding).amountErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentBanksBinding) binding).amountErrorLayout.getRoot(), ((FragmentBanksBinding) binding).amountErrorLayout.errorTextView);
            if (((FragmentBanksBinding) binding).accountErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentBanksBinding) binding).accountErrorLayout.getRoot(), ((FragmentBanksBinding) binding).accountErrorLayout.errorTextView);
            if (((FragmentBanksBinding) binding).typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentBanksBinding) binding).typeErrorLayout.getRoot(), ((FragmentBanksBinding) binding).typeErrorLayout.errorTextView);
            if (((FragmentBanksBinding) binding).weekdayErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentBanksBinding) binding).weekdayErrorLayout.getRoot(), ((FragmentBanksBinding) binding).weekdayErrorLayout.errorTextView);
            if (((FragmentBanksBinding) binding).monthdayErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentBanksBinding) binding).monthdayErrorLayout.getRoot(), ((FragmentBanksBinding) binding).monthdayErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCommissionsBinding) {
            if (((FragmentCommissionsBinding) binding).shareErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCommissionsBinding) binding).shareErrorLayout.getRoot(), ((FragmentCommissionsBinding) binding).shareErrorLayout.errorTextView);

        } else if (binding instanceof FragmentPaymentsBinding) {
            if (((FragmentPaymentsBinding) binding).treasuryErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentPaymentsBinding) binding).treasuryErrorLayout.getRoot(), ((FragmentPaymentsBinding) binding).treasuryErrorLayout.errorTextView);
            if (((FragmentPaymentsBinding) binding).amountErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentPaymentsBinding) binding).amountErrorLayout.getRoot(), ((FragmentPaymentsBinding) binding).amountErrorLayout.errorTextView);

        // -------------------- Tab

        } else if (binding instanceof FragmentCreateScheduleTabPaymentBinding) {
            if (((FragmentCreateScheduleTabPaymentBinding) binding).paymentErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabPaymentBinding) binding).paymentErrorLayout.getRoot(), ((FragmentCreateScheduleTabPaymentBinding) binding).paymentErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateScheduleTabPlatformBinding) {
            if (((FragmentCreateScheduleTabPlatformBinding) binding).platformsErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabPlatformBinding) binding).platformsErrorLayout.getRoot(), ((FragmentCreateScheduleTabPlatformBinding) binding).platformsErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabPlatformBinding) binding).pinPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabPlatformBinding) binding).pinPlatformErrorLayout.getRoot(), ((FragmentCreateScheduleTabPlatformBinding) binding).pinPlatformErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabPlatformBinding) binding).identifierPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabPlatformBinding) binding).identifierPlatformErrorLayout.getRoot(), ((FragmentCreateScheduleTabPlatformBinding) binding).identifierPlatformErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateScheduleTabReferenceBinding) {
            if (((FragmentCreateScheduleTabReferenceBinding) binding).selectionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabReferenceBinding) binding).selectionErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).selectionErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabReferenceBinding) binding).typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabReferenceBinding) binding).typeErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).typeErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabReferenceBinding) binding).caseErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabReferenceBinding) binding).caseErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).caseErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabReferenceBinding) binding).bulkSessionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabReferenceBinding) binding).bulkSessionErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).bulkSessionErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabReferenceBinding) binding).countErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabReferenceBinding) binding).countErrorLayout.getRoot(), ((FragmentCreateScheduleTabReferenceBinding) binding).countErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateScheduleTabSessionBinding) {
            if (((FragmentCreateScheduleTabSessionBinding) binding).statusErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabSessionBinding) binding).statusErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).statusErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabSessionBinding) binding).startTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabSessionBinding) binding).startTypeErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).startTypeErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabSessionBinding) binding).startRelativeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabSessionBinding) binding).startRelativeErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).startRelativeErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabSessionBinding) binding).startAccurateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabSessionBinding) binding).startAccurateErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).startAccurateErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabSessionBinding) binding).endTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabSessionBinding) binding).endTypeErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).endTypeErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabSessionBinding) binding).endRelativeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabSessionBinding) binding).endRelativeErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).endRelativeErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabSessionBinding) binding).endAccurateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabSessionBinding) binding).endAccurateErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).endAccurateErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabSessionBinding) binding).axisErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabSessionBinding) binding).axisErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).axisErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabSessionBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabSessionBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).descriptionErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabSessionBinding) binding).coordinationErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabSessionBinding) binding).coordinationErrorLayout.getRoot(), ((FragmentCreateScheduleTabSessionBinding) binding).coordinationErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateScheduleTabTimeBinding) {
            if (((FragmentCreateScheduleTabTimeBinding) binding).startTimeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabTimeBinding) binding).startTimeErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).startTimeErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabTimeBinding) binding).durationErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabTimeBinding) binding).durationErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).durationErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabTimeBinding) binding).dateTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabTimeBinding) binding).dateTypeErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).dateTypeErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabTimeBinding) binding).specifiedDateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabTimeBinding) binding).specifiedDateErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).specifiedDateErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabTimeBinding) binding).patternDaysErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabTimeBinding) binding).patternDaysErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).patternDaysErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabTimeBinding) binding).patternTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabTimeBinding) binding).patternTypeErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).patternTypeErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabTimeBinding) binding).repeatWeeksErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabTimeBinding) binding).repeatWeeksErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).repeatWeeksErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabTimeBinding) binding).periodStartDateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabTimeBinding) binding).periodStartDateErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).periodStartDateErrorLayout.errorTextView);
            if (((FragmentCreateScheduleTabTimeBinding) binding).periodEndDateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateScheduleTabTimeBinding) binding).periodEndDateErrorLayout.getRoot(), ((FragmentCreateScheduleTabTimeBinding) binding).periodEndDateErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateSessionTabPaymentBinding) {
            if (((FragmentCreateSessionTabPaymentBinding) binding).paymentErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabPaymentBinding) binding).paymentErrorLayout.getRoot(), ((FragmentCreateSessionTabPaymentBinding) binding).paymentErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateSessionTabPlatformBinding) {
            if (((FragmentCreateSessionTabPlatformBinding) binding).platformsErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabPlatformBinding) binding).platformsErrorLayout.getRoot(), ((FragmentCreateSessionTabPlatformBinding) binding).platformsErrorLayout.errorTextView);
            if (((FragmentCreateSessionTabPlatformBinding) binding).pinPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabPlatformBinding) binding).pinPlatformErrorLayout.getRoot(), ((FragmentCreateSessionTabPlatformBinding) binding).pinPlatformErrorLayout.errorTextView);
            if (((FragmentCreateSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.getRoot(), ((FragmentCreateSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateSessionTabSessionBinding) {
            if (((FragmentCreateSessionTabSessionBinding) binding).statusErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabSessionBinding) binding).statusErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).statusErrorLayout.errorTextView);
            if (((FragmentCreateSessionTabSessionBinding) binding).startTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabSessionBinding) binding).startTypeErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).startTypeErrorLayout.errorTextView);
            if (((FragmentCreateSessionTabSessionBinding) binding).startRelativeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabSessionBinding) binding).startRelativeErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).startRelativeErrorLayout.errorTextView);
            if (((FragmentCreateSessionTabSessionBinding) binding).startAccurateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabSessionBinding) binding).startAccurateErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).startAccurateErrorLayout.errorTextView);
            if (((FragmentCreateSessionTabSessionBinding) binding).endTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabSessionBinding) binding).endTypeErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).endTypeErrorLayout.errorTextView);
            if (((FragmentCreateSessionTabSessionBinding) binding).endRelativeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabSessionBinding) binding).endRelativeErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).endRelativeErrorLayout.errorTextView);
            if (((FragmentCreateSessionTabSessionBinding) binding).endAccurateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabSessionBinding) binding).endAccurateErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).endAccurateErrorLayout.errorTextView);
            if (((FragmentCreateSessionTabSessionBinding) binding).axisErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabSessionBinding) binding).axisErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).axisErrorLayout.errorTextView);
            if (((FragmentCreateSessionTabSessionBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabSessionBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).descriptionErrorLayout.errorTextView);
            if (((FragmentCreateSessionTabSessionBinding) binding).coordinationErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabSessionBinding) binding).coordinationErrorLayout.getRoot(), ((FragmentCreateSessionTabSessionBinding) binding).coordinationErrorLayout.errorTextView);

        } else if (binding instanceof FragmentCreateSessionTabTimeBinding) {
            if (((FragmentCreateSessionTabTimeBinding) binding).startTimeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabTimeBinding) binding).startTimeErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).startTimeErrorLayout.errorTextView);
            if (((FragmentCreateSessionTabTimeBinding) binding).durationErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabTimeBinding) binding).durationErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).durationErrorLayout.errorTextView);
            if (((FragmentCreateSessionTabTimeBinding) binding).dateTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabTimeBinding) binding).dateTypeErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).dateTypeErrorLayout.errorTextView);
            if (((FragmentCreateSessionTabTimeBinding) binding).specifiedDateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabTimeBinding) binding).specifiedDateErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).specifiedDateErrorLayout.errorTextView);
            if (((FragmentCreateSessionTabTimeBinding) binding).patternDaysErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabTimeBinding) binding).patternDaysErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).patternDaysErrorLayout.errorTextView);
            if (((FragmentCreateSessionTabTimeBinding) binding).patternTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabTimeBinding) binding).patternTypeErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).patternTypeErrorLayout.errorTextView);
            if (((FragmentCreateSessionTabTimeBinding) binding).repeatWeeksErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabTimeBinding) binding).repeatWeeksErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).repeatWeeksErrorLayout.errorTextView);
            if (((FragmentCreateSessionTabTimeBinding) binding).periodStartDateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabTimeBinding) binding).periodStartDateErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).periodStartDateErrorLayout.errorTextView);
            if (((FragmentCreateSessionTabTimeBinding) binding).periodEndDateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentCreateSessionTabTimeBinding) binding).periodEndDateErrorLayout.getRoot(), ((FragmentCreateSessionTabTimeBinding) binding).periodEndDateErrorLayout.errorTextView);

        } else if (binding instanceof FragmentEditSessionTabPaymentBinding) {
            if (((FragmentEditSessionTabPaymentBinding) binding).paymentErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabPaymentBinding) binding).paymentErrorLayout.getRoot(), ((FragmentEditSessionTabPaymentBinding) binding).paymentErrorLayout.errorTextView);

        } else if (binding instanceof FragmentEditSessionTabPlatformBinding) {
            if (((FragmentEditSessionTabPlatformBinding) binding).platformsErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabPlatformBinding) binding).platformsErrorLayout.getRoot(), ((FragmentEditSessionTabPlatformBinding) binding).platformsErrorLayout.errorTextView);
            if (((FragmentEditSessionTabPlatformBinding) binding).pinPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabPlatformBinding) binding).pinPlatformErrorLayout.getRoot(), ((FragmentEditSessionTabPlatformBinding) binding).pinPlatformErrorLayout.errorTextView);
            if (((FragmentEditSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.getRoot(), ((FragmentEditSessionTabPlatformBinding) binding).identifierPlatformErrorLayout.errorTextView);

        } else if (binding instanceof FragmentEditSessionTabReferenceBinding) {
            if (((FragmentEditSessionTabReferenceBinding) binding).selectionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabReferenceBinding) binding).selectionErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).selectionErrorLayout.errorTextView);
            if (((FragmentEditSessionTabReferenceBinding) binding).typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabReferenceBinding) binding).typeErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).typeErrorLayout.errorTextView);
            if (((FragmentEditSessionTabReferenceBinding) binding).caseErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabReferenceBinding) binding).caseErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).caseErrorLayout.errorTextView);
            if (((FragmentEditSessionTabReferenceBinding) binding).problemErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabReferenceBinding) binding).problemErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).problemErrorLayout.errorTextView);
            if (((FragmentEditSessionTabReferenceBinding) binding).bulkSessionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabReferenceBinding) binding).bulkSessionErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).bulkSessionErrorLayout.errorTextView);
            if (((FragmentEditSessionTabReferenceBinding) binding).countErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabReferenceBinding) binding).countErrorLayout.getRoot(), ((FragmentEditSessionTabReferenceBinding) binding).countErrorLayout.errorTextView);

        } else if (binding instanceof FragmentEditSessionTabSessionBinding) {
            if (((FragmentEditSessionTabSessionBinding) binding).statusErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabSessionBinding) binding).statusErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).statusErrorLayout.errorTextView);
            if (((FragmentEditSessionTabSessionBinding) binding).startTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabSessionBinding) binding).startTypeErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).startTypeErrorLayout.errorTextView);
            if (((FragmentEditSessionTabSessionBinding) binding).startRelativeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabSessionBinding) binding).startRelativeErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).startRelativeErrorLayout.errorTextView);
            if (((FragmentEditSessionTabSessionBinding) binding).startAccurateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabSessionBinding) binding).startAccurateErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).startAccurateErrorLayout.errorTextView);
            if (((FragmentEditSessionTabSessionBinding) binding).endTypeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabSessionBinding) binding).endTypeErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).endTypeErrorLayout.errorTextView);
            if (((FragmentEditSessionTabSessionBinding) binding).endRelativeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabSessionBinding) binding).endRelativeErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).endRelativeErrorLayout.errorTextView);
            if (((FragmentEditSessionTabSessionBinding) binding).endAccurateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabSessionBinding) binding).endAccurateErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).endAccurateErrorLayout.errorTextView);
            if (((FragmentEditSessionTabSessionBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabSessionBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).descriptionErrorLayout.errorTextView);
            if (((FragmentEditSessionTabSessionBinding) binding).coordinationErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabSessionBinding) binding).coordinationErrorLayout.getRoot(), ((FragmentEditSessionTabSessionBinding) binding).coordinationErrorLayout.errorTextView);

        } else if (binding instanceof FragmentEditSessionTabTimeBinding) {
            if (((FragmentEditSessionTabTimeBinding) binding).startTimeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabTimeBinding) binding).startTimeErrorLayout.getRoot(), ((FragmentEditSessionTabTimeBinding) binding).startTimeErrorLayout.errorTextView);
            if (((FragmentEditSessionTabTimeBinding) binding).durationErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabTimeBinding) binding).durationErrorLayout.getRoot(), ((FragmentEditSessionTabTimeBinding) binding).durationErrorLayout.errorTextView);
            if (((FragmentEditSessionTabTimeBinding) binding).startDateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditSessionTabTimeBinding) binding).startDateErrorLayout.getRoot(), ((FragmentEditSessionTabTimeBinding) binding).startDateErrorLayout.errorTextView);

        } else if (binding instanceof FragmentEditCenterTabDetailBinding) {
            if (((FragmentEditCenterTabDetailBinding) binding).managerErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditCenterTabDetailBinding) binding).managerErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).managerErrorLayout.errorTextView);
            if (((FragmentEditCenterTabDetailBinding) binding).titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditCenterTabDetailBinding) binding).titleErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).titleErrorLayout.errorTextView);
            if (((FragmentEditCenterTabDetailBinding) binding).addressErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditCenterTabDetailBinding) binding).addressErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).addressErrorLayout.errorTextView);
            if (((FragmentEditCenterTabDetailBinding) binding).descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditCenterTabDetailBinding) binding).descriptionErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).descriptionErrorLayout.errorTextView);
            if (((FragmentEditCenterTabDetailBinding) binding).phonesErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditCenterTabDetailBinding) binding).phonesErrorLayout.getRoot(), ((FragmentEditCenterTabDetailBinding) binding).phonesErrorLayout.errorTextView);

        } else if (binding instanceof FragmentEditUserTabCryptoBinding) {
            if (((FragmentEditUserTabCryptoBinding) binding).publicErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditUserTabCryptoBinding) binding).publicErrorLayout.getRoot(), ((FragmentEditUserTabCryptoBinding) binding).publicErrorLayout.errorTextView);
            if (((FragmentEditUserTabCryptoBinding) binding).privateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditUserTabCryptoBinding) binding).privateErrorLayout.getRoot(), ((FragmentEditUserTabCryptoBinding) binding).privateErrorLayout.errorTextView);

        } else if (binding instanceof FragmentEditUserTabPasswordBinding) {
            if (((FragmentEditUserTabPasswordBinding) binding).currentPasswordErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditUserTabPasswordBinding) binding).currentPasswordErrorLayout.getRoot(), ((FragmentEditUserTabPasswordBinding) binding).currentPasswordErrorLayout.errorTextView);
            if (((FragmentEditUserTabPasswordBinding) binding).newPasswordErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditUserTabPasswordBinding) binding).newPasswordErrorLayout.getRoot(), ((FragmentEditUserTabPasswordBinding) binding).newPasswordErrorLayout.errorTextView);

        } else if (binding instanceof FragmentEditUserTabPersonalBinding) {
            if (((FragmentEditUserTabPersonalBinding) binding).nameErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditUserTabPersonalBinding) binding).nameErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).nameErrorLayout.errorTextView);
            if (((FragmentEditUserTabPersonalBinding) binding).mobileErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditUserTabPersonalBinding) binding).mobileErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).mobileErrorLayout.errorTextView);
            if (((FragmentEditUserTabPersonalBinding) binding).emailErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditUserTabPersonalBinding) binding).emailErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).emailErrorLayout.errorTextView);
            if (((FragmentEditUserTabPersonalBinding) binding).birthdayErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditUserTabPersonalBinding) binding).birthdayErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).birthdayErrorLayout.errorTextView);
            if (((FragmentEditUserTabPersonalBinding) binding).statusErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditUserTabPersonalBinding) binding).statusErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).statusErrorLayout.errorTextView);
            if (((FragmentEditUserTabPersonalBinding) binding).typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditUserTabPersonalBinding) binding).typeErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).typeErrorLayout.errorTextView);
            if (((FragmentEditUserTabPersonalBinding) binding).genderErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((FragmentEditUserTabPersonalBinding) binding).genderErrorLayout.getRoot(), ((FragmentEditUserTabPersonalBinding) binding).genderErrorLayout.errorTextView);

        // -------------------- Sheet

        } else if (binding instanceof SheetBulkSampleBinding) {
            if (((SheetBulkSampleBinding) binding).nicknameErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                hideHandle(((SheetBulkSampleBinding) binding).nicknameErrorLayout.getRoot(), ((SheetBulkSampleBinding) binding).nicknameErrorLayout.errorTextView);

        }

        // -------------------- End

    }

    public void emptyValid(ViewBinding binding) {

        // -------------------- Auth

        if (binding instanceof FragmentAuthLoginBinding)
            emptyHandle(((FragmentAuthLoginBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthLoginBinding) binding).errorIncludeLayout.errorTextView);

        else if (binding instanceof FragmentAuthPasswordBinding)
            emptyHandle(((FragmentAuthPasswordBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPasswordBinding) binding).errorIncludeLayout.errorTextView);

        else if (binding instanceof FragmentAuthPasswordChangeBinding)
            emptyHandle(((FragmentAuthPasswordChangeBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPasswordChangeBinding) binding).errorIncludeLayout.errorTextView);

        else if (binding instanceof FragmentAuthPasswordRecoverBinding)
            emptyHandle(((FragmentAuthPasswordRecoverBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPasswordRecoverBinding) binding).errorIncludeLayout.errorTextView);

        else if (binding instanceof FragmentAuthPinBinding)
            emptyHandle(((FragmentAuthPinBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthPinBinding) binding).errorIncludeLayout.errorTextView);

        else if (binding instanceof FragmentAuthRegisterBinding)
            emptyHandle(((FragmentAuthRegisterBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthRegisterBinding) binding).errorIncludeLayout.errorTextView);

        else if (binding instanceof FragmentAuthSerialBinding)
            emptyHandle(((FragmentAuthSerialBinding) binding).errorIncludeLayout.getRoot(), ((FragmentAuthSerialBinding) binding).errorIncludeLayout.errorTextView);

        // -------------------- End

    }

    /*
    ---------- Private's ----------
    */

    private void showHandle(LinearLayout errorLayout, TextView errorTextView, String validation) {
        errorLayout.setVisibility(View.VISIBLE);
        errorTextView.setText(validation);
    }

    private void hideHandle(LinearLayout errorLayout, TextView errorTextView) {
        errorLayout.setVisibility(View.GONE);
        errorTextView.setText("");
    }

    private void emptyHandle(LinearLayout errorLayout, TextView errorTextView) {
        errorLayout.setVisibility(View.VISIBLE);
        errorTextView.setText(activity.getResources().getString(R.string.AppInputEmpty));
    }

}