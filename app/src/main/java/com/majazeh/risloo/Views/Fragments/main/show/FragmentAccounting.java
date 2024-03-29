package com.majazeh.risloo.views.fragments.main.show;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.databinding.FragmentAccountingBinding;

public class FragmentAccounting extends Fragment {

    // Binding
    private FragmentAccountingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountingBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        return binding.getRoot();
    }

    private void initializer() {
        InitManager.lytTextColorResTintBackground(requireActivity(), binding.treasuriesIncludeLayout.getRoot(), binding.treasuriesIncludeLayout.selectTextView, binding.treasuriesIncludeLayout.selectImageView, getResources().getString(R.string.AccountingFragmentTreasuries), getResources().getColor(R.color.coolGray500), R.drawable.ic_wallet_light, R.color.risloo500, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_risloo50);
        InitManager.lytTextColorResTintBackground(requireActivity(), binding.billingsIncludeLayout.getRoot(), binding.billingsIncludeLayout.selectTextView, binding.billingsIncludeLayout.selectImageView, getResources().getString(R.string.AccountingFragmentBillings), getResources().getColor(R.color.coolGray500), R.drawable.ic_file_invoice_light, R.color.amber500, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_amber200);
        InitManager.lytTextColorResTintBackground(requireActivity(), binding.banksIncludeLayout.getRoot(), binding.banksIncludeLayout.selectTextView, binding.banksIncludeLayout.selectImageView, getResources().getString(R.string.AccountingFragmentBanks), getResources().getColor(R.color.coolGray500), R.drawable.ic_cash_register_light, R.color.violet500, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_violet200);
        InitManager.lytTextColorResTintBackground(requireActivity(), binding.paymentsIncludeLayout.getRoot(), binding.paymentsIncludeLayout.selectTextView, binding.paymentsIncludeLayout.selectImageView, getResources().getString(R.string.AccountingFragmentPayments), getResources().getColor(R.color.coolGray500), R.drawable.ic_plus_light, R.color.emerald600, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_emerald200);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onClickListener(() -> {
            ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentTreasuries();
        }).widget(binding.treasuriesIncludeLayout.getRoot());

        CustomClickView.onClickListener(() -> {
            ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentBillings();
        }).widget(binding.billingsIncludeLayout.getRoot());

        CustomClickView.onClickListener(() -> {
            ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentBanks(null);
        }).widget(binding.banksIncludeLayout.getRoot());

        CustomClickView.onClickListener(() -> {
            ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentPayments(null);
        }).widget(binding.paymentsIncludeLayout.getRoot());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
