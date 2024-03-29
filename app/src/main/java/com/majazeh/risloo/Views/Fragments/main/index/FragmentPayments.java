package com.majazeh.risloo.views.fragments.main.index;

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
import com.majazeh.risloo.databinding.FragmentPaymentsBinding;
import com.majazeh.risloo.utils.instances.Paymont;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.DropdownManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.PaymentManager;
import com.majazeh.risloo.utils.managers.SpannableManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.main.Table.TablePaymentAdapter;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Payment;
import com.mre.ligheh.Model.TypeModel.PaymentModel;
import com.mre.ligheh.Model.TypeModel.TreasuriesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class FragmentPayments extends Fragment {

    // Binding
    private FragmentPaymentsBinding binding;

    // Adapters
    private TablePaymentAdapter adapter;

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
        adapter = new TablePaymentAdapter(requireActivity());

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.chargeHeaderLayout.titleTextView.setText(getResources().getString(R.string.PaymentsFragmentChargeHeader));
        binding.chargeHeaderLayout.titleTextView.setTextColor(requireActivity().getResources().getColor(R.color.emerald600));

        binding.treasuryIncludeLayout.headerTextView.setText(getResources().getString(R.string.PaymentsFragmentChargeTreasuryHeader));

        binding.amountIncludeLayout.headerTextView.setText(SpannableManager.spanForegroundColorSize(getResources().getString(R.string.PaymentsFragmentChargeAmountHeader), 5, 12, getResources().getColor(R.color.coolGray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.amountIncludeLayout.footerTextView.setText("0" + " " + getResources().getString(R.string.MainToman));

        InitManager.txtTextColorBackground(binding.chargeTextView.getRoot(), getResources().getString(R.string.PaymentsFragmentChargeButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);

        binding.tableHeaderLayout.titleTextView.setText(getResources().getString(R.string.PaymentAdapterHeader));

        binding.tableShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);

        InitManager.rcvVerticalFixedUnnested(requireActivity(), binding.tableSingleLayout.recyclerView, 0, 0, 0, 0);
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
                ((ActivityMain) requireActivity()).inputon.select(binding.amountIncludeLayout.inputEditText);
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

        binding.getRoot().setMOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (!isLoading && !Objects.requireNonNull(v).canScrollVertically(1)) {
                isLoading = true;

                if (data.containsKey("page"))
                    data.put("page", ((int) data.get("page")) + 1);
                else
                    data.put("page", 1);

                if (binding.tableSingleLayout.progressBar.getVisibility() == View.GONE)
                    binding.tableSingleLayout.progressBar.setVisibility(View.VISIBLE);

                getData();
            }
        });

        CustomClickView.onDelayedListener(() -> {
            ((ActivityMain) requireActivity()).validatoon.hideValid(binding);

            doWork();
        }).widget(binding.chargeTextView.getRoot());
    }

    private void setArgs() {
        UserModel userModel = ((ActivityMain) requireActivity()).singleton.getUserModel();
        setData(userModel);

        TypeModel typeModel = FragmentPaymentsArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (StringManager.suffix(typeModel.getClass().getName(), '.').equals("PaymentModel"))
                setData((PaymentModel) typeModel);
        }
    }

    private void setData(PaymentModel model) {
        if (model.getAmount() != 0 ) {
            amount = String.valueOf(model.getAmount());
            binding.amountIncludeLayout.inputEditText.setText(amount);

            String money = StringManager.seperatePlus(amount) + " " + getResources().getString(R.string.MainToman);
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
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> balances = new ArrayList<>();

        for (TypeModel typeModel : treasuries.data()) {
            TreasuriesModel model = (TreasuriesModel) typeModel;

            if (model != null && model.isCreditable() && model.isMyTreasury() && !model.getSymbol().equals("gift")) {
                titles.add(model.getTitle());
                balances.add(getResources().getString(R.string.PaymentsFragmentChargeBalanceHint) + " " + StringManager.seperatePlus(String.valueOf(model.getBalance())) + " " + getResources().getString(R.string.MainToman));
                treasuryIds.add(model.getId());
            }
        }

        titles.add("");
        balances.add("");
        treasuryIds.add("");

        DropdownManager.spinnerCustomTreasury(requireActivity(), binding.treasuryIncludeLayout.selectSpinner, titles, balances);

        // Select First Treasury Item
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
                            adapter.clearItems();

                        if (!items.data().isEmpty()) {
                            adapter.setItems(items.data());
                            binding.tableSingleLayout.recyclerView.setAdapter(adapter);

                            binding.tableSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (adapter.itemsCount() == 0) {
                            binding.tableSingleLayout.recyclerView.setAdapter(null);

                            binding.tableSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.tableSingleLayout.emptyView.setText(getResources().getString(R.string.PaymentAdapterEmpty));
                        }

                        binding.tableHeaderLayout.countTextView.setText(StringManager.bracing(items.getTotal()));

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

    private void setHashmap() {
        data.put("treasury_id", treasury);

        if (!amount.equals(""))
            data.put("amount", amount);
        else
            data.remove("amount");
    }

    private void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap();

        Payment.post(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                PaymentModel paymentModel = (PaymentModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissDialogLoading();

                        Paymont.getInstance().insertPayment(null, paymentModel, data, R.id.fragmentPayments);
                        PaymentManager.request(requireActivity(), paymentModel);
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> ((ActivityMain) requireActivity()).validatoon.showValid(response, binding));
                }
            }
        });
    }

    private void hideShimmer() {
        binding.tableSingleLayout.getRoot().setVisibility(View.VISIBLE);
        binding.tableShimmerLayout.getRoot().setVisibility(View.GONE);
        binding.tableShimmerLayout.getRoot().stopShimmer();

        if (binding.tableSingleLayout.progressBar.getVisibility() == View.VISIBLE)
            binding.tableSingleLayout.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        userSelect = false;
        isLoading = true;
    }

}