package com.majazeh.risloo.Views.Fragments.Main.Show;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentAccountingBinding;

public class AccountingFragment extends Fragment {

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
        InitManager.layoutTextColorResTintBackground(requireActivity(), binding.treasuriesIncludeLayout.getRoot(), binding.treasuriesIncludeLayout.selectTextView, binding.treasuriesIncludeLayout.selectImageView, getResources().getString(R.string.AccountingFragmentTreasuries), getResources().getColor(R.color.CoolGray500), R.drawable.ic_wallet_light, R.color.Risloo500, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_risloo50);
        InitManager.layoutTextColorResTintBackground(requireActivity(), binding.billingsIncludeLayout.getRoot(), binding.billingsIncludeLayout.selectTextView, binding.billingsIncludeLayout.selectImageView, getResources().getString(R.string.AccountingFragmentBillings), getResources().getColor(R.color.CoolGray500), R.drawable.ic_file_invoice_light, R.color.Amber500, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_amber200);
        InitManager.layoutTextColorResTintBackground(requireActivity(), binding.banksIncludeLayout.getRoot(), binding.banksIncludeLayout.selectTextView, binding.banksIncludeLayout.selectImageView, getResources().getString(R.string.AccountingFragmentBanks), getResources().getColor(R.color.CoolGray500), R.drawable.ic_cash_register_light, R.color.Violet500, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_violet200);
        InitManager.layoutTextColorResTintBackground(requireActivity(), binding.paymentsIncludeLayout.getRoot(), binding.paymentsIncludeLayout.selectTextView, binding.paymentsIncludeLayout.selectImageView, getResources().getString(R.string.AccountingFragmentPayments), getResources().getColor(R.color.CoolGray500), R.drawable.ic_plus_light, R.color.Emerald600, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_emerald200);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalTreasuriesFragment();
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.treasuriesIncludeLayout.getRoot());

        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalBillingsFragment();
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.billingsIncludeLayout.getRoot());

        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalBanksFragment();
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.banksIncludeLayout.getRoot());

        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalPaymentsFragment(null);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.paymentsIncludeLayout.getRoot());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
