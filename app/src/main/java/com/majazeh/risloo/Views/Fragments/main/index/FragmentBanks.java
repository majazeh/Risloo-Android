package com.majazeh.risloo.views.fragments.main.index;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
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
import com.majazeh.risloo.utils.managers.JsonManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.SpannableManager;
import com.majazeh.risloo.utils.managers.DropdownManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.managers.BalanceManager;
import com.majazeh.risloo.utils.interfaces.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.main.Index.IndexBankAdapter;
import com.majazeh.risloo.databinding.FragmentBanksBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Bank;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.IbanModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FragmentBanks extends Fragment {

    // Binding
    private FragmentBanksBinding binding;

    // Adapters
    private IndexBankAdapter adapter;

    // Objects
    private HashMap data, header;

    // Vars
    private ArrayList<String> accountIds = new ArrayList<>();
    private String iban = "", account = "", type = "", amount = "", weekday = "", monthday = "";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentBanksBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new IndexBankAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.createHeaderLayout.titleTextView.setText(getResources().getString(R.string.BankAdapterTitle));

        binding.ibanIncludeLayout.headerTextView.setText(getResources().getString(R.string.BanksFragmentCreateIbanHeader));
        binding.ibanIncludeLayout.inputEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(24)});
        binding.ibanIncludeLayout.footerTextView.setText(getResources().getString(R.string.MainIban) + " - " + "0");

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.BanksFragmentCreateButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);

        InitManager.rcvVerticalFixedUnnested(requireActivity(), binding.indexSingleLayout.recyclerView,  getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));

        binding.settleHeaderLayout.titleTextView.setText(getResources().getString(R.string.BanksFragmentTitle));

        binding.totalIncludeLayout.headerTextView.setText(getResources().getString(R.string.BanksFragmentSettleTotalHeader));
        binding.accountIncludeLayout.headerTextView.setText(getResources().getString(R.string.BanksFragmentSettleAccountHeader));
        binding.typeIncludeLayout.headerTextView.setText(getResources().getString(R.string.BanksFragmentSettleTypeHeader));
        binding.weekdayIncludeLayout.headerTextView.setText(getResources().getString(R.string.BanksFragmentSettleWeekdayHeader));
        binding.monthdayIncludeLayout.headerTextView.setText(getResources().getString(R.string.BanksFragmentSettleMonthDayHeader));

        binding.amountIncludeLayout.headerTextView.setText(SpannableManager.spanForegroundColorSize(getResources().getString(R.string.BanksFragmentSettleAmountHeader), 5, 12, getResources().getColor(R.color.coolGray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.amountIncludeLayout.footerTextView.setText("0" + " " + getResources().getString(R.string.MainToman));

        binding.scheduleGuideLayout.guideTextView.setText(getResources().getString(R.string.BanksFragmentSettleScheduleGuide));

        DropdownManager.spinner12ssp(requireActivity(), binding.typeIncludeLayout.selectSpinner, R.array.IbanTypes);
        DropdownManager.spinner12ssp(requireActivity(), binding.weekdayIncludeLayout.selectSpinner, R.array.SettleWeekDays);
        DropdownManager.spinner12ssp(requireActivity(), binding.monthdayIncludeLayout.selectSpinner, R.array.SettleMonthDays);

        InitManager.txtTextColorBackground(binding.scheduleHelperView.getRoot(), getResources().getString(R.string.BanksFragmentSettleScheduleHelper), getResources().getColor(R.color.amber600), R.drawable.draw_2sdp_solid_amber50_border_right_2dp_amber400);
        InitManager.txtTextColorBackground(binding.settleTextView.getRoot(), getResources().getString(R.string.BanksFragmentSettleImmediateButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_emerald600_ripple_emerald800);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.ibanIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.ibanIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.ibanIncludeLayout.inputEditText);
            return false;
        });

        binding.amountIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.amountIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.amountIncludeLayout.inputEditText);
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

                    switch(type) {
                        case "تسویه آنی":
                            binding.amountIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.weekdayIncludeLayout.getRoot().setVisibility(View.GONE);
                            binding.monthdayIncludeLayout.getRoot().setVisibility(View.GONE);

                            binding.scheduleGuideLayout.getRoot().setVisibility(View.GONE);

                            binding.settleTextView.getRoot().setText(requireActivity().getResources().getString(R.string.BanksFragmentSettleImmediateButton));
                            break;
                        case "زمانبندی: روزانه":
                            binding.amountIncludeLayout.getRoot().setVisibility(View.GONE);
                            binding.weekdayIncludeLayout.getRoot().setVisibility(View.GONE);
                            binding.monthdayIncludeLayout.getRoot().setVisibility(View.GONE);

                            binding.scheduleGuideLayout.getRoot().setVisibility(View.VISIBLE);

                            binding.settleTextView.getRoot().setText(requireActivity().getResources().getString(R.string.BanksFragmentSettleScheduleButton));
                            break;
                        case "زمانبندی: هفتگی":
                            binding.amountIncludeLayout.getRoot().setVisibility(View.GONE);
                            binding.weekdayIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.monthdayIncludeLayout.getRoot().setVisibility(View.GONE);

                            binding.scheduleGuideLayout.getRoot().setVisibility(View.VISIBLE);

                            binding.settleTextView.getRoot().setText(requireActivity().getResources().getString(R.string.BanksFragmentSettleScheduleButton));
                            break;
                        case "زمانبندی: ماهانه":
                            binding.amountIncludeLayout.getRoot().setVisibility(View.GONE);
                            binding.weekdayIncludeLayout.getRoot().setVisibility(View.GONE);
                            binding.monthdayIncludeLayout.getRoot().setVisibility(View.VISIBLE);

                            binding.scheduleGuideLayout.getRoot().setVisibility(View.VISIBLE);

                            binding.settleTextView.getRoot().setText(requireActivity().getResources().getString(R.string.BanksFragmentSettleScheduleButton));
                            break;
                    }

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
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.ibanErrorLayout.getRoot(), binding.ibanErrorLayout.errorTextView);
            if (binding.amountErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.amountErrorLayout.getRoot(), binding.amountErrorLayout.errorTextView);
            if (binding.accountErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.accountErrorLayout.getRoot(), binding.accountErrorLayout.errorTextView);
            if (binding.typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView);
            if (binding.weekdayErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.weekdayErrorLayout.getRoot(), binding.weekdayErrorLayout.errorTextView);
            if (binding.monthdayErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.monthdayErrorLayout.getRoot(), binding.monthdayErrorLayout.errorTextView);

            doWork("create");
        }).widget(binding.createTextView.getRoot());

        CustomClickView.onDelayedListener(() -> {
            if (binding.ibanErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.ibanErrorLayout.getRoot(), binding.ibanErrorLayout.errorTextView);
            if (binding.amountErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.amountErrorLayout.getRoot(), binding.amountErrorLayout.errorTextView);
            if (binding.accountErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.accountErrorLayout.getRoot(), binding.accountErrorLayout.errorTextView);
            if (binding.typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView);
            if (binding.weekdayErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.weekdayErrorLayout.getRoot(), binding.weekdayErrorLayout.errorTextView);
            if (binding.monthdayErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.monthdayErrorLayout.getRoot(), binding.monthdayErrorLayout.errorTextView);

            doWork("settle");
        }).widget(binding.settleTextView.getRoot());
    }

    private void setArgs() {
        UserModel userModel = ((ActivityMain) requireActivity()).singleton.getUserModel();
        setData(userModel);
    }

    private void setData(UserModel model) {
        if (!BalanceManager.balanceWallet(model.getTreasuries()).equals("0")) {
            String value = StringManager.seperatePlus(BalanceManager.balanceWallet(model.getTreasuries())) + " " + getResources().getString(R.string.MainToman);
            binding.totalIncludeLayout.amountTextView.setText(value);
        } else {
            binding.totalIncludeLayout.amountTextView.setText("");
        }
    }

    private void setAccounts(List accounts) {
        ArrayList<String> options = new ArrayList<>();

        for (TypeModel typeModel : accounts.data()) {
            IbanModel model = (IbanModel) typeModel;

            if (model != null && model.getBank() != null && model.getStatus().equals("verified")) {
                options.add(model.getIban() + "\n" + model.getOwner() + " " + StringManager.bracing(model.getBank().getTitle()));
                accountIds.add(model.getId());
            }
        }

        options.add("");
        accountIds.add("");

        if (options.size() >= 2)
            binding.settleGroup.setVisibility(View.VISIBLE);
        else
            binding.settleGroup.setVisibility(View.GONE);

        DropdownManager.spinner12ssp(requireActivity(), binding.accountIncludeLayout.selectSpinner, options);

        // Select First Account Item
        account = accountIds.get(0);
        binding.accountIncludeLayout.selectSpinner.setSelection(0);
    }

    private void setScheduling(JSONObject object) {
        try {
            JSONObject scheduling = object.getJSONObject("scheduling");

            if (scheduling != null) {
                binding.scheduleHelperView.getRoot().setVisibility(View.GONE);

                if (scheduling.getString("type") != null && !scheduling.getString("type").equals("")) {
                    type = JsonManager.getIbanType(requireActivity(), "fa", scheduling.getString("type"));

                    switch (type) {
                        case "تسویه آنی":
                            binding.typeIncludeLayout.selectSpinner.setSelection(0);

                            binding.amountIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.weekdayIncludeLayout.getRoot().setVisibility(View.GONE);
                            binding.monthdayIncludeLayout.getRoot().setVisibility(View.GONE);

                            binding.scheduleGuideLayout.getRoot().setVisibility(View.GONE);

                            binding.settleTextView.getRoot().setText(requireActivity().getResources().getString(R.string.BanksFragmentSettleImmediateButton));

                            setAmount(scheduling.getString("value"));
                            break;
                        case "زمانبندی: روزانه":
                            binding.typeIncludeLayout.selectSpinner.setSelection(1);

                            binding.amountIncludeLayout.getRoot().setVisibility(View.GONE);
                            binding.weekdayIncludeLayout.getRoot().setVisibility(View.GONE);
                            binding.monthdayIncludeLayout.getRoot().setVisibility(View.GONE);

                            binding.scheduleGuideLayout.getRoot().setVisibility(View.VISIBLE);

                            binding.settleTextView.getRoot().setText(requireActivity().getResources().getString(R.string.BanksFragmentSettleScheduleButton));
                            break;
                        case "زمانبندی: هفتگی":
                            binding.typeIncludeLayout.selectSpinner.setSelection(2);

                            binding.amountIncludeLayout.getRoot().setVisibility(View.GONE);
                            binding.weekdayIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.monthdayIncludeLayout.getRoot().setVisibility(View.GONE);

                            binding.scheduleGuideLayout.getRoot().setVisibility(View.VISIBLE);

                            binding.settleTextView.getRoot().setText(requireActivity().getResources().getString(R.string.BanksFragmentSettleScheduleButton));

                            setWeekday(scheduling.getString("value"));
                            break;
                        case "زمانبندی: ماهانه":
                            binding.typeIncludeLayout.selectSpinner.setSelection(3);

                            binding.amountIncludeLayout.getRoot().setVisibility(View.GONE);
                            binding.weekdayIncludeLayout.getRoot().setVisibility(View.GONE);
                            binding.monthdayIncludeLayout.getRoot().setVisibility(View.VISIBLE);

                            binding.scheduleGuideLayout.getRoot().setVisibility(View.VISIBLE);

                            binding.settleTextView.getRoot().setText(requireActivity().getResources().getString(R.string.BanksFragmentSettleScheduleButton));

                            setMonthday(scheduling.getString("value"));
                            break;
                    }
                }
            } else {
                binding.scheduleHelperView.getRoot().setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAmount(String value) {
        if (value != null && !value.equals("")) {
            amount = value;
            binding.amountIncludeLayout.inputEditText.setText(amount);

            String money = StringManager.seperatePlus(amount) + " " + getResources().getString(R.string.MainToman);
            binding.amountIncludeLayout.footerTextView.setText(money);
        }
    }

    private void setWeekday(String value) {
        if (value != null && !value.equals("")) {
            weekday = value;
            binding.weekdayIncludeLayout.selectSpinner.setSelection(Integer.parseInt(weekday));
        }
    }

    private void setMonthday(String value) {
        if (value != null && !value.equals("")) {
            monthday = value;

            switch(monthday) {
                case "before_last":
                    binding.monthdayIncludeLayout.selectSpinner.setSelection(binding.monthdayIncludeLayout.selectSpinner.getAdapter().getCount() - 2);
                    break;
                case "last_day":
                    binding.monthdayIncludeLayout.selectSpinner.setSelection(binding.monthdayIncludeLayout.selectSpinner.getAdapter().getCount() - 1);
                    break;
                default:
                    binding.monthdayIncludeLayout.selectSpinner.setSelection(Integer.parseInt(monthday) - 1);
                    break;
            }
        }
    }

    private void getData() {
        Bank.getIbans(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            adapter.clearItems();

                            List items = new List();
                            for (int i = 0; i < ((JSONObject) object).getJSONObject("data").getJSONArray("items").length(); i++)
                                items.add(new IbanModel(((JSONObject) object).getJSONObject("data").getJSONArray("items").getJSONObject(i)));

                            if (!items.data().isEmpty()) {
                                adapter.setItems(items.data());
                                binding.indexSingleLayout.recyclerView.setAdapter(adapter);

                                binding.indexSingleLayout.emptyView.setVisibility(View.GONE);

                                setAccounts(items);
                            } else if (adapter.getItemCount() == 0) {
                                binding.indexSingleLayout.recyclerView.setAdapter(null);

                                binding.indexSingleLayout.emptyView.setVisibility(View.VISIBLE);
                                binding.indexSingleLayout.emptyView.setText(getResources().getString(R.string.BankAdapterEmpty));

                                binding.settleGroup.setVisibility(View.GONE);
                            }

                            binding.createHeaderLayout.countTextView.setText(StringManager.bracing(adapter.getItemCount()));

                            setScheduling(((JSONObject) object).getJSONObject("data"));

                            hideShimmer();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        hideShimmer();
                    });
                }
            }
        });
    }

    private void resetInputs() {
        iban = "";
        binding.ibanIncludeLayout.inputEditText.setText(iban);
        binding.ibanIncludeLayout.footerTextView.setText(getResources().getString(R.string.MainIban) + " - " + "0");
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

            if (!type.equals("")) {
                data.put("type", JsonManager.getIbanType(requireActivity(), "en", type));

                switch (type) {
                    case "تسویه آنی":
                        if (!amount.equals(""))
                            data.put("amount", amount);
                        else
                            data.remove("amount");

                        data.remove("weekday");
                        data.remove("day");
                        break;
                    case "زمانبندی: روزانه":
                        data.remove("amount");
                        data.remove("weekday");
                        data.remove("day");
                        break;
                    case "زمانبندی: هفتگی":
                        if (!weekday.equals(""))
                            data.put("weekday", weekday);
                        else
                            data.remove("weekday");

                        data.remove("amount");
                        data.remove("day");
                        break;
                    case "زمانبندی: ماهانه":
                        if (!monthday.equals(""))
                            data.put("day", monthday);
                        else
                            data.remove("day");

                        data.remove("amount");
                        data.remove("weekday");
                        break;
                }

            } else {
                data.remove("type");
            }

            // Clear "Create" Section
            data.remove("iban");
        }
    }

    private void doWork(String method) {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap(method);

        if (method.equals("create")) {
            Bank.addIban(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            DialogManager.dismissDialogLoading();
                            SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackCreatedNewIban));

                            resetInputs();
                            showShimmer();

                            getData();
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
                                            case "iban":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.ibanErrorLayout.getRoot(), binding.ibanErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
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
        } else {
            Bank.settleds(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            DialogManager.dismissDialogLoading();
                            SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackIbanSettled));
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
                                            case "iban_id":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.accountErrorLayout.getRoot(), binding.accountErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "type":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "amount":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.amountErrorLayout.getRoot(), binding.amountErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "weekday":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.weekdayErrorLayout.getRoot(), binding.weekdayErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                                break;
                                            case "day":
                                                ((ActivityMain) requireActivity()).validatoon.showValid(binding.monthdayErrorLayout.getRoot(), binding.monthdayErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
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
    }

    private void showShimmer() {
        binding.indexSingleLayout.getRoot().setVisibility(View.GONE);
        binding.indexShimmerLayout.getRoot().setVisibility(View.VISIBLE);
        binding.indexShimmerLayout.getRoot().startShimmer();
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
    }

}