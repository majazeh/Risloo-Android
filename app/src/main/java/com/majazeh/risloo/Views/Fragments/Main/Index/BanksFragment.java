package com.majazeh.risloo.Views.Fragments.Main.Index;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentBanksBinding;

import java.util.HashMap;

public class BanksFragment extends Fragment {

    // Binding
    private FragmentBanksBinding binding;

    // Objects
    private HashMap data, header;

    // Vars
    private String iban = "", amount = "";
    private boolean userSelect = false, isLoading = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentBanksBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

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
        binding.dayIncludeLayout.headerTextView.setText(getResources().getString(R.string.BanksFragmentSettleDayHeader));

        binding.amountIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.BanksFragmentSettleAmountHeader), 5, 12, getResources().getColor(R.color.CoolGray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.amountIncludeLayout.footerTextView.setText("0" + " " + getResources().getString(R.string.MainToman));

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

        CustomClickView.onDelayedListener(() -> {
            // TODO : Place Code When Needed
        }).widget(binding.createTextView.getRoot());

        CustomClickView.onDelayedListener(() -> {
            // TODO : Place Code When Needed
        }).widget(binding.settleTextView.getRoot());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        userSelect = false;
        isLoading = true;
    }

}