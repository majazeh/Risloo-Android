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
import com.majazeh.risloo.Utils.Entities.Paymont;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.PaymentManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Index.IndexPaymentAdapter;
import com.majazeh.risloo.databinding.FragmentPaymentsBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Payment;
import com.mre.ligheh.Model.TypeModel.PaymentModel;
import com.mre.ligheh.Model.TypeModel.TreasuriesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class PaymentsFragment extends Fragment {

    // Binding
    private FragmentPaymentsBinding binding;

    // Adapters
    private IndexPaymentAdapter indexPaymentAdapter;

    // Objects
    private HashMap data, header;

    // Vars
    private ArrayList<String> treasuryIds = new ArrayList<>();
    private String treasury = "", amount = "";
    private boolean userSelect = false, isLoading = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentPaymentsBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        indexPaymentAdapter = new IndexPaymentAdapter(requireActivity());

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.chargeHeaderLayout.titleTextView.setText(getResources().getString(R.string.PaymentsFragmentChargeHeader));
        binding.chargeHeaderLayout.titleTextView.setTextColor(requireActivity().getResources().getColor(R.color.Emerald600));

        binding.treasuryIncludeLayout.headerTextView.setText(getResources().getString(R.string.PaymentsFragmentChargeTreasuryHeader));
        binding.amountIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.PaymentsFragmentChargeAmountHeader), 5, 12, getResources().getColor(R.color.CoolGray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.amountIncludeLayout.footerTextView.setText("0" + " " + getResources().getString(R.string.MainToman));

        InitManager.txtTextColorBackground(binding.chargeTextView.getRoot(), getResources().getString(R.string.PaymentsFragmentChargeButton), getResources().getColor(R.color.White), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);

        binding.paymentsHeaderLayout.titleTextView.setText(getResources().getString(R.string.PaymentAdapterHeader));

        binding.paymentsShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.paymentsSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.treasuryIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.treasuryIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

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

        binding.amountIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.amountIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.amountIncludeLayout.inputEditText);
            return false;
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

        binding.getRoot().setMOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (!isLoading && !Objects.requireNonNull(v).canScrollVertically(1)) {
                isLoading = true;

                if (data.containsKey("page"))
                    data.put("page", ((int) data.get("page")) + 1);
                else
                    data.put("page", 1);

                if (binding.paymentsSingleLayout.progressBar.getVisibility() == View.GONE)
                    binding.paymentsSingleLayout.progressBar.setVisibility(View.VISIBLE);

                getData();
            }
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.treasuryErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.treasuryErrorLayout.getRoot(), binding.treasuryErrorLayout.errorTextView);
            if (binding.amountErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.amountErrorLayout.getRoot(), binding.amountErrorLayout.errorTextView);

            doWork();
        }).widget(binding.chargeTextView.getRoot());
    }

    private void setArgs() {
        UserModel userModel = ((MainActivity) requireActivity()).singleton.getUserModel();
        setData(userModel);

        TypeModel typeModel = PaymentsFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (StringManager.substring(typeModel.getClass().getName(), '.').equals("PaymentModel"))
                setData((PaymentModel) typeModel);
        }
    }

    private void setData(PaymentModel model) {
        if (model.getAmount() != 0 ) {
            amount = String.valueOf(model.getAmount());
            binding.amountIncludeLayout.inputEditText.setText(amount);

            String money = StringManager.separate(amount) + " " + getResources().getString(R.string.MainToman);
            binding.amountIncludeLayout.footerTextView.setText(money);
        }

        if (model.getTreasury() != null && model.getTreasury().getId() != null && !model.getTreasury().getId().equals("")) {
            treasury = model.getTreasury().getId();
            for (int i = 0; i < treasuryIds.size(); i++) {
                if (treasuryIds.get(i).equals(treasury)) {
                    binding.treasuryIncludeLayout.selectSpinner.setSelection(i);
                }
            }
        }
    }

    private void setData(UserModel model) {
        if (model.getTreasuries() != null) {
            setTreasury(model.getTreasuries());
        }
    }

    private void setTreasury(List treasuries) {
        ArrayList<String> options = new ArrayList<>();

        for (TypeModel typeModel : treasuries.data()) {
            TreasuriesModel model = (TreasuriesModel) typeModel;

            if (model.isCreditable() && model.isMy_treasury() && !model.getSymbol().equals("gift")) {
                options.add(model.getTitle());
                treasuryIds.add(model.getId());
            }
        }

        options.add("");
        treasuryIds.add("");

        InitManager.normal12sspSpinner(requireActivity(), binding.treasuryIncludeLayout.selectSpinner, options);

        treasury = treasuryIds.get(0);
        binding.treasuryIncludeLayout.selectSpinner.setSelection(0);
    }

    private void getData() {
        Payment.list(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                List items = (List) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        if (Objects.equals(data.get("page"), 1))
                            indexPaymentAdapter.clearItems();

                        if (!items.data().isEmpty()) {
                            indexPaymentAdapter.setItems(items.data());
                            binding.paymentsSingleLayout.recyclerView.setAdapter(indexPaymentAdapter);

                            binding.paymentsSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (indexPaymentAdapter.itemsCount() == 0) {
                            binding.paymentsSingleLayout.recyclerView.setAdapter(null);

                            binding.paymentsSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.paymentsSingleLayout.emptyView.setText(getResources().getString(R.string.PaymentAdapterEmpty));
                        }

                        binding.paymentsHeaderLayout.countTextView.setText(StringManager.bracing(items.getTotal()));

                        hideShimmer();
                    });

                    isLoading = false;
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        hideShimmer();
                    });

                    isLoading = false;
                }
            }
        });
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        data.put("treasury_id", treasury);
        data.put("amount", amount);

        Payment.post(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                PaymentModel paymentModel = (PaymentModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();

                        Paymont.getInstance().insertPayment(null, paymentModel, data, R.id.paymentsFragment);
                        PaymentManager.request(requireActivity(), paymentModel);
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
                                StringBuilder errors = new StringBuilder();

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                        String validation = errorsObject.getJSONArray(key).get(i).toString();

                                        switch (key) {
                                            case "treasury_id":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.treasuryErrorLayout.getRoot(), binding.treasuryErrorLayout.errorTextView, validation);
                                                break;
                                            case "amount":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.amountErrorLayout.getRoot(), binding.amountErrorLayout.errorTextView, validation);
                                                break;
                                        }

                                        errors.append(validation);
                                        errors.append("\n");
                                    }
                                }

                                SnackManager.showErrorSnack(requireActivity(), errors.substring(0, errors.length() - 1));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    private void hideShimmer() {

        // Payments Data
        binding.paymentsSingleLayout.getRoot().setVisibility(View.VISIBLE);
        binding.paymentsShimmerLayout.getRoot().setVisibility(View.GONE);
        binding.paymentsShimmerLayout.getRoot().stopShimmer();

        if (binding.paymentsSingleLayout.progressBar.getVisibility() == View.VISIBLE)
            binding.paymentsSingleLayout.progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        userSelect = false;
        isLoading = true;
    }

}