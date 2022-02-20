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
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentCenterAccountingBinding;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.HashMap;

public class CenterAccountingFragment extends Fragment {

    // Binding
    private FragmentCenterAccountingBinding binding;

    // Models
    private CenterModel centerModel;
    private RoomModel roomModel;

    // Objects
    private HashMap data, header;

    // Vars
    private String type = "counseling_center";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCenterAccountingBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        InitManager.layoutTextColorResTintBackground(requireActivity(), binding.treasuriesIncludeLayout.getRoot(), binding.treasuriesIncludeLayout.selectTextView, binding.treasuriesIncludeLayout.selectImageView, getResources().getString(R.string.CenterAccountingFragmentTreasuries), getResources().getColor(R.color.coolGray500), R.drawable.ic_wallet_light, R.color.risloo500, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_risloo50);
        InitManager.layoutTextColorResTintBackground(requireActivity(), binding.billingsIncludeLayout.getRoot(), binding.billingsIncludeLayout.selectTextView, binding.billingsIncludeLayout.selectImageView, getResources().getString(R.string.CenterAccountingFragmentBillings), getResources().getColor(R.color.coolGray500), R.drawable.ic_file_invoice_light, R.color.amber500, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_amber200);
        InitManager.layoutTextColorResTintBackground(requireActivity(), binding.banksIncludeLayout.getRoot(), binding.banksIncludeLayout.selectTextView, binding.banksIncludeLayout.selectImageView, getResources().getString(R.string.CenterAccountingFragmentBanks), getResources().getColor(R.color.coolGray500), R.drawable.ic_cash_register_light, R.color.violet500, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_violet200);
        InitManager.layoutTextColorResTintBackground(requireActivity(), binding.paymentsIncludeLayout.getRoot(), binding.paymentsIncludeLayout.selectTextView, binding.paymentsIncludeLayout.selectImageView, getResources().getString(R.string.CenterAccountingFragmentPayments), getResources().getColor(R.color.coolGray500), R.drawable.ic_plus_light, R.color.emerald600, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_emerald200);
        InitManager.layoutTextColorResTintBackground(requireActivity(), binding.commissionsIncludeLayout.getRoot(), binding.commissionsIncludeLayout.selectTextView, binding.commissionsIncludeLayout.selectImageView, getResources().getString(R.string.CenterAccountingFragmentCommissions), getResources().getColor(R.color.coolGray500), R.drawable.ic_abacus_light, R.color.red400, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_red200);
        InitManager.layoutTextColorResTintBackground(requireActivity(), binding.balancesIncludeLayout.getRoot(), binding.balancesIncludeLayout.selectTextView, binding.balancesIncludeLayout.selectImageView, getResources().getString(R.string.CenterAccountingFragmentBalances), getResources().getColor(R.color.coolGray500), R.drawable.ic_balance_scale_light, R.color.coolGray500, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_coolgray200);
        InitManager.layoutTextColorResTintBackground(requireActivity(), binding.discountsIncludeLayout.getRoot(), binding.discountsIncludeLayout.selectTextView, binding.discountsIncludeLayout.selectImageView, getResources().getString(R.string.CenterAccountingFragmentDiscount), getResources().getColor(R.color.coolGray500), R.drawable.ic_badge_percent_light, R.color.pink500, R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_pink200);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onClickListener(() -> {
            // TODO : Place Code When Needed
        }).widget(binding.treasuriesIncludeLayout.getRoot());

        CustomClickView.onClickListener(() -> {
            // TODO : Place Code When Needed
        }).widget(binding.billingsIncludeLayout.getRoot());

        CustomClickView.onClickListener(() -> {
            // TODO : Place Code When Needed
        }).widget(binding.banksIncludeLayout.getRoot());

        CustomClickView.onClickListener(() -> {
            // TODO : Place Code When Needed
        }).widget(binding.paymentsIncludeLayout.getRoot());

        CustomClickView.onClickListener(() -> {
            if (type.equals("counseling_center"))
                ((MainActivity) requireActivity()).navigatoon.navigateToCommissionsFragment(centerModel);
            else
                ((MainActivity) requireActivity()).navigatoon.navigateToCommissionsFragment(roomModel);

        }).widget(binding.commissionsIncludeLayout.getRoot());

        CustomClickView.onClickListener(() -> {
            if (type.equals("counseling_center"))
                ((MainActivity) requireActivity()).navigatoon.navigateToBalancesFragment(centerModel);
            else
                ((MainActivity) requireActivity()).navigatoon.navigateToBalancesFragment(roomModel);

        }).widget(binding.balancesIncludeLayout.getRoot());

        CustomClickView.onClickListener(() -> {
            // TODO : Place Code When Needed
        }).widget(binding.discountsIncludeLayout.getRoot());
    }

    private void setArgs() {
        TypeModel typeModel = CenterAccountingFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (StringManager.substring(typeModel.getClass().getName(), '.').equals("CenterModel")) {
            centerModel = (CenterModel) typeModel;
            setData(centerModel);
        } else if (StringManager.substring(typeModel.getClass().getName(), '.').equals("RoomModel")) {
            roomModel = (RoomModel) typeModel;
            setData(roomModel);
        }
    }

    private void setData(CenterModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }

        if (model.getType() != null && !model.getType().equals("")) {
            type = model.getType();
        }
    }

    private void setData(RoomModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }

        if (model.getType() != null && !model.getType().equals("")) {
            type = model.getType();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}