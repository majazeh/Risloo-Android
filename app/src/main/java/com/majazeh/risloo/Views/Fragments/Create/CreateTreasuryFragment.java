package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentCreateTreasuryBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.HashMap;

public class CreateTreasuryFragment extends Fragment {

    // Binding
    private FragmentCreateTreasuryBinding binding;

    // Objecs
    private HashMap data, header;

    // Vars
    private String title = "", regionId = "";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateTreasuryBinding.inflate(inflater, viewGroup, false);

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

        binding.titleIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateTreasuryFragmentNameHeader));
        binding.regionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateTreasuryFragmentRegionHeader));

        binding.titleGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateTreasuryFragmentNameGuide));

        InitManager.normal12sspSpinner(requireActivity(), binding.regionIncludeLayout.selectSpinner, R.array.TreasuryRegion);

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateTreasuryFragmentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.titleIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.titleIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).validatoon.select(requireActivity(), binding.titleIncludeLayout.inputEditText);
            return false;
        });

        binding.titleIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            title = binding.titleIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.regionIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.regionIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    switch(pos) {
                        case "شخصی":
                            regionId = "";
                            break;
                        default:
                            regionId = "RS96666W6";
                            break;
                    }

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ClickManager.onDelayedClickListener(() -> {
            if (binding.titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.check(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView);
            if (binding.regionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.check(binding.regionErrorLayout.getRoot(), binding.regionErrorLayout.errorTextView);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        String type = CreateTreasuryFragmentArgs.fromBundle(getArguments()).getType();
        TypeModel typeModel = CreateTreasuryFragmentArgs.fromBundle(getArguments()).getTypeModel();

        // TODO : Place Code When Needed
    }

    private void doWork() {
//        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");
//
//        data.put("title", title);
//        data.put("region_id", regionId);
//
//        Treasury.create(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
//                        ToastManager.showToast(requireActivity(), getResources().getString(R.string.ToastNewTreasuryAdded));
//
//                        ((MainActivity) requireActivity()).navController.navigateUp();
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
//                                                ((MainActivity) requireActivity()).controlEditText.error(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView, validation);
//                                                break;
//                                            case "region_id":
//                                                ((MainActivity) requireActivity()).controlEditText.error(binding.regionErrorLayout.getRoot(), binding.regionErrorLayout.errorTextView, validation);
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