package com.majazeh.risloo.Views.Fragments.Edit;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentEditTreasuryBinding;

import java.util.HashMap;

public class EditTreasuryFragment extends Fragment {

    // Binding
    private FragmentEditTreasuryBinding binding;

    // Models
//    private TreasuryModel treasuryModel;

    // Objecs
    private HashMap data, header;

    // Vars
    private String title = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditTreasuryBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.titleIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditTreasuryFragmentNameHeader));

        binding.titleGuideLayout.guideTextView.setText(getResources().getString(R.string.EditTreasuryFragmentNameGuide));

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditTreasuryFragmentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.titleIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.titleIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.titleIncludeLayout.inputEditText);
            return false;
        });

        binding.titleIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            title = binding.titleIncludeLayout.inputEditText.getText().toString().trim();
        });

        ClickManager.onDelayedClickListener(() -> {
            if (binding.titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.titleIncludeLayout.inputEditText, binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView);

            doWork();
        }).widget(binding.editTextView.getRoot());
    }

    private void setArgs() {
//        treasuryModel = (TreasuryModel) EditTreasuryFragmentArgs.fromBundle(getArguments()).getTypeModel();
//        setData(treasuryModel);
    }

//    private void setData(TreasuryModel model) {
//        if (model.getId() != null && !model.getId().equals("")) {
//            data.put("id", model.getId());
//        }
//
//        if (model.getTitle() != null && !model.getTitle().equals("")) {
//            title = model.getTitle();
//            binding.titleIncludeLayout.inputEditText.setText(title);
//        }
//    }

    private void doWork() {
//        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");
//
//        data.put("title", title);
//
//        Treasury.edit(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
//                        ToastManager.showToast(requireActivity(), getResources().getString(R.string.ToastChangesSaved));
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
//                                StringBuilder errors = new StringBuilder();
//
//                                while (keys.hasNext()) {
//                                    String key = keys.next();
//                                    for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
//                                        String validation = errorsObject.getJSONArray(key).get(i).toString();
//
//                                        switch (key) {
//                                            case "title":
//                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.titleIncludeLayout.inputEditText, binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView, validation);
//                                                break;
//                                        }
//
//                                        errors.append(validation);
//                                        errors.append("\n");
//                                    }
//                                }
//
//                                ToastManager.showToast(requireActivity(), errors.substring(0, errors.length() - 1));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    });
//                }
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}