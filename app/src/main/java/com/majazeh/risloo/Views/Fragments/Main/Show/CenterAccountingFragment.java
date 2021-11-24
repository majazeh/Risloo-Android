package com.majazeh.risloo.Views.Fragments.Main.Show;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.databinding.FragmentCenterAccountingBinding;

public class CenterAccountingFragment extends Fragment {

    // Binding
    private FragmentCenterAccountingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCenterAccountingBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        return binding.getRoot();
    }

    private void initializer() {
        InitManager.layoutTextColorResTintBackground(requireActivity(), binding.commissionsIncludeLayout.getRoot(), binding.commissionsIncludeLayout.selectTextView, binding.commissionsIncludeLayout.selectImageView, getResources().getString(R.string.CenterAccountingFragmentCommissions), getResources().getColor(R.color.CoolGray500), R.drawable.ic_abacus_light, R.color.Red400, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_red200);
        InitManager.layoutTextColorResTintBackground(requireActivity(), binding.balancesIncludeLayout.getRoot(), binding.balancesIncludeLayout.selectTextView, binding.balancesIncludeLayout.selectImageView, getResources().getString(R.string.CenterAccountingFragmentBalances), getResources().getColor(R.color.CoolGray500), R.drawable.ic_balance_scale_light, R.color.CoolGray500, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_coolgray200);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onClickListener(() -> {
            // TODO : Place Code When Needed
        }).widget(binding.commissionsIncludeLayout.getRoot());

        CustomClickView.onClickListener(() -> {
            // TODO : Place Code When Needed
        }).widget(binding.balancesIncludeLayout.getRoot());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
