package com.majazeh.risloo.views.fragments.main.create;

import android.annotation.SuppressLint;
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
import com.majazeh.risloo.databinding.FragmentCreateTreasuryBinding;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.DropdownManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Treasury;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentCreateTreasury extends Fragment {

    // Binding
    private FragmentCreateTreasuryBinding binding;

    // Objecs
    private HashMap data, header;

    // Vars
    private String title = "", regionId = "", personalClinicId = "";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateTreasuryBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.titleIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateTreasuryFragmentNameHeader));
        binding.regionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateTreasuryFragmentRegionHeader));

        binding.titleGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateTreasuryFragmentNameGuide));

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateTreasuryFragmentButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.titleIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.titleIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.titleIncludeLayout.inputEditText);
            return false;
        });

        binding.titleIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            title = binding.titleIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.regionIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.regionIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.regionIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    switch(pos) {
                        case "شخصی":
                            regionId = "";
                            break;
                        case "کلینیک شخصی":
                            regionId = personalClinicId;
                            break;
                    }

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CustomClickView.onDelayedListener(() -> {
            ((ActivityMain) requireActivity()).validatoon.hideValid(binding);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        TypeModel typeModel = FragmentCreateTreasuryArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (StringManager.suffix(typeModel.getClass().getName(), '.').equals("UserModel"))
                setData((UserModel) typeModel);
        }
    }

    private void setData(UserModel model) {
        setRegion(model.getCenters());
    }

    private void setRegion(List centers) {
        ArrayList<String> options = new ArrayList<>();

        options.add("شخصی");

        if (!centers.data().isEmpty()) {
            for (TypeModel typeModel : centers.data()) {
                CenterModel model = (CenterModel) typeModel;

                if (model != null && model.getAcceptation() != null && model.getAcceptation().getPosition().equals("manager") && model.getType().equals("personal_clinic")) {
                    personalClinicId = model.getId();
                    options.add("کلینیک شخصی");
                    break;
                }
            }
        }

        options.add("");

        DropdownManager.spinner12ssp(requireActivity(), binding.regionIncludeLayout.selectSpinner, options);

        // Select First Treasury Item
        regionId = "";
        binding.regionIncludeLayout.selectSpinner.setSelection(0);
    }

    private void setHashmap() {
        if (!title.equals(""))
            data.put("title", title);
        else
            data.remove("title");

        data.put("region_id", regionId);
    }

    private void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap();

        Treasury.create(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissDialogLoading();
                        SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackCreatedNewTreasury));

                        ((ActivityMain) requireActivity()).navigatoon.navigateUp();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        userSelect = false;
    }

}