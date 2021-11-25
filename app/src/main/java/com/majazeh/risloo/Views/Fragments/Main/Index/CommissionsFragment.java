package com.majazeh.risloo.Views.Fragments.Main.Index;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.majazeh.risloo.databinding.FragmentCommissionsBinding;

import java.util.HashMap;

public class CommissionsFragment extends Fragment {

    // Binding
    private FragmentCommissionsBinding binding;

    // Objects
    private HashMap data, header;

    // Vars
    private String share = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCommissionsBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.contributionHeaderLayout.titleTextView.setText(getResources().getString(R.string.CommissionsFragmentContributionHeader));
        binding.contributionHeaderLayout.titleTextView.setTextColor(requireActivity().getResources().getColor(R.color.Emerald600));

        binding.shareIncludeLayout.headerTextView.setText(StringManager.foregroundSize(getResources().getString(R.string.CommissionsFragmentContributionShareHeader), 10, 16, getResources().getColor(R.color.CoolGray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.shareIncludeLayout.inputEditText.setHint(getResources().getString(R.string.CommissionsFragmentContributionShareHint));

        InitManager.txtTextColorBackground(binding.contributionTextView.getRoot(), getResources().getString(R.string.CommissionsFragmentContributionButton), getResources().getColor(R.color.White), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.shareIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.shareIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(binding.shareIncludeLayout.inputEditText);
            return false;
        });

        binding.shareIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            share = binding.shareIncludeLayout.inputEditText.getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.shareErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.shareErrorLayout.getRoot(), binding.shareErrorLayout.errorTextView);

            doWork();
        }).widget(binding.contributionTextView.getRoot());
    }

    private void setArgs() {
        // TODO : Place Code When Needed
    }

    private void setData() {
        // TODO : Place Code When Needed
    }

    private void getData() {
        // TODO : Place Code When Needed
    }

    private void setHashmap() {
        if (!share.equals(""))
            data.put("?????", share);
        else
            data.remove("?????");
    }

    private void doWork() {
//        DialogManager.showLoadingDialog(requireActivity(), "");
//
//        setHashmap();
//
//        Commission.post(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                CommissionModel commissionModel = (CommissionModel) object;
//
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        DialogManager.dismissLoadingDialog();
//
//                        // TODO : Place Code When Needed
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(String response) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        try {
//                            JSONObject responseObject = new JSONObject(response);
//                            if (!responseObject.isNull("errors")) {
//                                JSONObject errorsObject = responseObject.getJSONObject("errors");
//
//                                Iterator<String> keys = (errorsObject.keys());
//                                StringBuilder allErrors = new StringBuilder();
//
//                                while (keys.hasNext()) {
//                                    String key = keys.next();
//                                    StringBuilder keyErrors = new StringBuilder();
//
//                                    for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
//                                        String error = errorsObject.getJSONArray(key).getString(i);
//
//                                        keyErrors.append(error);
//                                        keyErrors.append("\n");
//
//                                        allErrors.append(error);
//                                        allErrors.append("\n");
//                                    }
//
//                                    switch (key) {
//                                        case "?????":
//                                            ((MainActivity) requireActivity()).validatoon.showValid(binding.shareErrorLayout.getRoot(), binding.shareErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
//                                            break;
//                                    }
//                                }
//
//                                SnackManager.showErrorSnack(requireActivity(), allErrors.substring(0, allErrors.length() - 1));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    });
//                }
//            }
//        });
    }

    private void hideShimmer() {
        // TODO : Place Code When Needed
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}