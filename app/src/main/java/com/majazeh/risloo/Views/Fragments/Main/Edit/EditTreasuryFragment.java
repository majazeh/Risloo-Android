package com.majazeh.risloo.Views.Fragments.Main.Edit;

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
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentEditTreasuryBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Treasury;
import com.mre.ligheh.Model.TypeModel.TreasuriesModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class EditTreasuryFragment extends Fragment {

    // Binding
    private FragmentEditTreasuryBinding binding;

    // Models
    private TreasuriesModel treasuryModel;

    // Objecs
    private HashMap data, header;

    // Vars
    private String title = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditTreasuryBinding.inflate(inflater, viewGroup, false);

        initializer();

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

        InitManager.txtTextColorBackground(binding.editTextView.getRoot(), getResources().getString(R.string.EditTreasuryFragmentButton), getResources().getColor(R.color.White), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.titleIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.titleIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(requireActivity(), binding.titleIncludeLayout.inputEditText);
            return false;
        });

        binding.titleIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            title = binding.titleIncludeLayout.inputEditText.getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView);

            doWork();
        }).widget(binding.editTextView.getRoot());
    }

    private void setArgs() {
        treasuryModel = (TreasuriesModel) EditTreasuryFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(treasuryModel);
    }

    private void setData(TreasuriesModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }

        if (model.getTitle() != null && !model.getTitle().equals("")) {
            title = model.getTitle();
            binding.titleIncludeLayout.inputEditText.setText(title);
        }
    }

    private void setHashmap() {
        if (!title.equals(""))
            data.put("title", title);
        else
            data.remove("title");
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        setHashmap();

        Treasury.edit(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.SnackChangesSaved));

                        ((MainActivity) requireActivity()).navController.navigateUp();
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            JSONObject responseObject = new JSONObject(response);
                            if (!responseObject.isNull("errors")) {
                                JSONObject errorsObject = responseObject.getJSONObject("errors");

                                Iterator<String> keys = (errorsObject.keys());
                                StringBuilder allErrors = new StringBuilder();

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    StringBuilder keyErrors = new StringBuilder();

                                    for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                        String error = errorsObject.getJSONArray(key).getString(i);

                                        keyErrors.append(error);
                                        keyErrors.append("\n");

                                        allErrors.append(error);
                                        allErrors.append("\n");
                                    }

                                    switch (key) {
                                        case "title":
                                            ((MainActivity) requireActivity()).validatoon.showValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                    }
                                }

                                SnackManager.showErrorSnack(requireActivity(), allErrors.substring(0, allErrors.length() - 1));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}