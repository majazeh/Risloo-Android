package com.majazeh.risloo.views.fragments.main.create;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.SelectionManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.SpannableManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.databinding.FragmentCreateBillBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Billing;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.BillingModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TreasuriesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FragmentCreateBill extends Fragment {

    // Binding
    private FragmentCreateBillBinding binding;

    // Objects
    private HashMap data, header;

    // Vars
    private ArrayList<String> referenceIds = new ArrayList<>(), treasuryIds = new ArrayList<>();
    private String title = "", reference = "", type = "", treasury = "", amount = "";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateBillBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.titleIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateBillFragmentTitleHeader));
        binding.referenceIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateBillFragmentReferenceHeader));
        binding.typeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateBillFragmentTypeHeader));
        binding.treasuryIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateBillFragmentTreasuryHeader));
        binding.amountIncludeLayout.headerTextView.setText(SpannableManager.foregroundColorSize(getResources().getString(R.string.CreateBillFragmentAmountHeader), 5, 12, getResources().getColor(R.color.coolGray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.amountIncludeLayout.footerTextView.setText("0" + " " + getResources().getString(R.string.MainToman));

        InitManager.input12sspSpinner(requireActivity(), binding.typeIncludeLayout.selectSpinner, R.array.BillStatus);

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateBillFragmentButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.titleIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.titleIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.titleIncludeLayout.inputEditText);
            return false;
        });

        binding.amountIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.amountIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.amountIncludeLayout.inputEditText);
            return false;
        });

        binding.titleIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            title = binding.titleIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.amountIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            amount = binding.amountIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.amountIncludeLayout.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    String money = StringManager.seperatePlus(String.valueOf(s)) + " " + getResources().getString(R.string.MainToman);
                    binding.amountIncludeLayout.footerTextView.setText(money);
                } else {
                    String money = "0" + " " + getResources().getString(R.string.MainToman);
                    binding.amountIncludeLayout.footerTextView.setText(money);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.referenceIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.typeIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.treasuryIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.referenceIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.typeIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.treasuryIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.referenceIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    reference = referenceIds.get(position);

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.typeIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    type = parent.getItemAtPosition(position).toString();

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.treasuryIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    treasury = treasuryIds.get(position);

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView);
            if (binding.referenceErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView);
            if (binding.typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView);
            if (binding.treasuryErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.treasuryErrorLayout.getRoot(), binding.treasuryErrorLayout.errorTextView);
            if (binding.amountErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.amountErrorLayout.getRoot(), binding.amountErrorLayout.errorTextView);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        TypeModel typeModel = FragmentCreateBillArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (StringManager.suffix(typeModel.getClass().getName(), '.').equals("SessionModel"))
                setData((SessionModel) typeModel);
        }
    }

    private void setData(SessionModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }

        if (model.getClients() != null && model.getClients().size() != 0) {
            setClients(model.getClients());
        }

        if (model.getRoom() != null && model.getRoom().getCenter() != null && model.getRoom().getCenter().getTreasuries() != null && model.getRoom().getCenter().getTreasuries().size() != 0) {
            setTreasury(model.getRoom().getCenter().getTreasuries());
        }
    }

    private void setClients(List clients) {
        ArrayList<String> options = new ArrayList<>();

        for (TypeModel typeModel : clients.data()) {
            UserModel model = (UserModel) typeModel;

            if (model != null) {
                options.add(model.getName());
                referenceIds.add(model.getId());
            }
        }

        options.add("");
        referenceIds.add("");

        InitManager.input12sspSpinner(requireActivity(), binding.referenceIncludeLayout.selectSpinner, options);
    }

    private void setTreasury(List treasuries) {
        ArrayList<String> options = new ArrayList<>();

        for (TypeModel typeModel : treasuries.data()) {
            TreasuriesModel model = (TreasuriesModel) typeModel;

            if (model != null) {
                options.add(model.getTitle());
                treasuryIds.add(model.getId());
            }
        }

        options.add("");
        treasuryIds.add("");

        InitManager.input12sspSpinner(requireActivity(), binding.treasuryIncludeLayout.selectSpinner, options);
    }

    private void setHashmap() {
        if (!title.equals(""))
            data.put("title", title);
        else
            data.remove("title");

        if (!reference.equals(""))
            data.put("user_id", reference);
        else
            data.remove("user_id");

        if (!type.equals(""))
            data.put("type", SelectionManager.getBillType(requireActivity(), "en", type));
        else
            data.remove("type");

        if (!treasury.equals(""))
            data.put("treasury", treasury);
        else
            data.remove("treasury");

        if (!amount.equals(""))
            data.put("amount", amount);
        else
            data.remove("amount");
    }

    private void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap();

        Billing.addBill(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                BillingModel billingModel = (BillingModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissDialogLoading();
                        SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackCreatedNewBill));

                        ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentBill(billingModel);
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
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

                                    switch (key) {
                                        case "title":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "user_id":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "type":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "treasury":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.treasuryErrorLayout.getRoot(), binding.treasuryErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "amount":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.amountErrorLayout.getRoot(), binding.amountErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                    }
                                }

                                SnackManager.showSnackError(requireActivity(), allErrors.substring(0, allErrors.length() - 1));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        userSelect = false;
    }

}