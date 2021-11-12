package com.majazeh.risloo.Views.Fragments.Main.Index;

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
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentBanksBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class BanksFragment extends Fragment {

    // Binding
    private FragmentBanksBinding binding;

    // Objects
    private HashMap data, header;

    // Vars
    private ArrayList<String> accountIds = new ArrayList<>();
    private String iban = "", account = "", type = "", amount = "", weekday = "", monthday = "";
    private boolean userSelect = false, isLoading = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentBanksBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.createHeaderLayout.titleTextView.setText(getResources().getString(R.string.BankAdapterTitle));

        binding.ibanIncludeLayout.headerTextView.setText(getResources().getString(R.string.BanksFragmentCreateIbanHeader));
        binding.ibanIncludeLayout.footerTextView.setText(getResources().getString(R.string.MainIban) + " - " + "0");

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.BanksFragmentCreateButton), getResources().getColor(R.color.White), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);

        binding.settleHeaderLayout.titleTextView.setText(getResources().getString(R.string.BanksFragmentTitle));

        binding.accountIncludeLayout.headerTextView.setText(getResources().getString(R.string.BanksFragmentSettleAccountHeader));
        binding.typeIncludeLayout.headerTextView.setText(getResources().getString(R.string.BanksFragmentSettleTypeHeader));
        binding.weekdayIncludeLayout.headerTextView.setText(getResources().getString(R.string.BanksFragmentSettleWeekdayHeader));
        binding.monthdayIncludeLayout.headerTextView.setText(getResources().getString(R.string.BanksFragmentSettleMonthDayHeader));

        binding.amountIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.BanksFragmentSettleAmountHeader), 5, 12, getResources().getColor(R.color.CoolGray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.amountIncludeLayout.footerTextView.setText("0" + " " + getResources().getString(R.string.MainToman));

        binding.scheduleGuideLayout.guideTextView.setText(getResources().getString(R.string.BanksFragmentSettleScheduleGuide));

        InitManager.input12sspSpinner(requireActivity(), binding.typeIncludeLayout.selectSpinner, R.array.IbanTypes);
        InitManager.input12sspSpinner(requireActivity(), binding.weekdayIncludeLayout.selectSpinner, R.array.SettleWeekDays);
        InitManager.input12sspSpinner(requireActivity(), binding.monthdayIncludeLayout.selectSpinner, R.array.SettleMonthDays);

        InitManager.txtTextColorBackground(binding.settleTextView.getRoot(), getResources().getString(R.string.BanksFragmentSettleImmediateButton), getResources().getColor(R.color.White), R.drawable.draw_24sdp_solid_emerald600_ripple_emerald800);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.ibanIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.ibanIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(requireActivity(), binding.ibanIncludeLayout.inputEditText);
            return false;
        });

        binding.amountIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.amountIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(requireActivity(), binding.amountIncludeLayout.inputEditText);
            return false;
        });

        binding.ibanIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            iban = binding.ibanIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.amountIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            amount = binding.amountIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.ibanIncludeLayout.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    String iban = getResources().getString(R.string.MainIban) + " - " + s;
                    binding.ibanIncludeLayout.footerTextView.setText(iban);
                } else {
                    String iban = getResources().getString(R.string.MainIban) + " - " + "0";
                    binding.ibanIncludeLayout.footerTextView.setText(iban);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.amountIncludeLayout.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    String money = StringManager.separate(String.valueOf(s)) + " " + getResources().getString(R.string.MainToman);
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

        binding.accountIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.typeIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.weekdayIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.monthdayIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.accountIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.typeIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.weekdayIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.monthdayIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.accountIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    account = accountIds.get(position);

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

        binding.weekdayIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    weekday = String.valueOf(position);

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.monthdayIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    switch(pos) {
                        case "یک روز مانده به روز آخر ماه":
                            monthday = "before_last";
                            break;
                        case "روز آخر ماه":
                            monthday = "last_day";
                            break;
                        default:
                            monthday = pos;
                            break;
                    }

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.ibanErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.ibanErrorLayout.getRoot(), binding.ibanErrorLayout.errorTextView);
            if (binding.amountErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.amountErrorLayout.getRoot(), binding.amountErrorLayout.errorTextView);
            if (binding.accountErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.accountErrorLayout.getRoot(), binding.accountErrorLayout.errorTextView);
            if (binding.typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView);
            if (binding.weekdayErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.weekdayErrorLayout.getRoot(), binding.weekdayErrorLayout.errorTextView);
            if (binding.monthdayErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.monthdayErrorLayout.getRoot(), binding.monthdayErrorLayout.errorTextView);

            doWork("create");
        }).widget(binding.createTextView.getRoot());

        CustomClickView.onDelayedListener(() -> {
            if (binding.ibanErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.ibanErrorLayout.getRoot(), binding.ibanErrorLayout.errorTextView);
            if (binding.amountErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.amountErrorLayout.getRoot(), binding.amountErrorLayout.errorTextView);
            if (binding.accountErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.accountErrorLayout.getRoot(), binding.accountErrorLayout.errorTextView);
            if (binding.typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView);
            if (binding.weekdayErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.weekdayErrorLayout.getRoot(), binding.weekdayErrorLayout.errorTextView);
            if (binding.monthdayErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.monthdayErrorLayout.getRoot(), binding.monthdayErrorLayout.errorTextView);

            doWork("settle");
        }).widget(binding.settleTextView.getRoot());
    }

    private void setArgs() {
        TypeModel typeModel = BanksFragmentArgs.fromBundle(getArguments()).getTypeModel();

        // TODO : Place Code When Needed
    }

    private void setHashmap(String method) {
        if (method.equals("create")) {
            if (!iban.equals(""))
                data.put("iban", iban);
            else
                data.remove("iban");

            // Clear "Settle" Section
            data.remove("iban_id");
            data.remove("type");
            data.remove("amount");
            data.remove("weekday");
            data.remove("day");

        } else {
            if (!account.equals(""))
                data.put("iban_id", account);
            else
                data.remove("iban_id");

            if (!type.equals(""))
                data.put("type", SelectionManager.getIbanType(requireActivity(), "en", type));
            else
                data.remove("type");

            if (!amount.equals(""))
                data.put("amount", amount);
            else
                data.remove("amount");

            if (!weekday.equals(""))
                data.put("weekday", weekday);
            else
                data.remove("weekday");

            if (!monthday.equals(""))
                data.put("day", monthday);
            else
                data.remove("day");

            // Clear "Create" Section
            data.remove("iban");
        }
    }

    private void doWork(String method) {
        DialogManager.showLoadingDialog(requireActivity(), "");

        setHashmap(method);

        if (method.equals("create")) {
            // TODO : Place Code Here
        } else {
            // TODO : Place Code Here
        }
    }

    private void hideShimmer() {
        binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
        binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
        binding.indexShimmerLayout.getRoot().stopShimmer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        userSelect = false;
        isLoading = true;
    }

}