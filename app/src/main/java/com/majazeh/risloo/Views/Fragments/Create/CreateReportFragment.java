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
import com.majazeh.risloo.databinding.FragmentCreateReportBinding;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.HashMap;

public class CreateReportFragment extends Fragment {

    // Binding
    private FragmentCreateReportBinding binding;

    // Vars
    private HashMap data, header;
    private String encryption = "", description = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateReportBinding.inflate(inflater, viewGroup, false);

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

        binding.encryptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateReportFragmentEncryptionHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateReportFragmentDescriptionHeader));

        InitManager.normal12sspSpinner(requireActivity(), binding.encryptionIncludeLayout.selectSpinner, R.array.EncryptionStates);

        InitManager.txtTextColor(binding.cryptoTextView.getRoot(), getResources().getString(R.string.CreateReportFragmentCryptoButton), getResources().getColor(R.color.Blue600));
        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateReportFragmentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.cryptoTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_blue600_ripple_blue300);
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.cryptoTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_blue600);
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.encryptionIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                encryption = parent.getItemAtPosition(position).toString();

                if (position == 0)
                    binding.cryptoConstraintLayout.setVisibility(View.GONE);
                else
                    binding.cryptoConstraintLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.descriptionIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.descriptionIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.cryptoTextView.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            if (binding.descriptionIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView);
                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        String type = CreateReportFragmentArgs.fromBundle(getArguments()).getType();
        TypeModel typeModel = CreateReportFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (type.equals("case")) {
                CaseModel caseModel = (CaseModel) CreateReportFragmentArgs.fromBundle(getArguments()).getTypeModel();
                setData(caseModel);

            } else if (type.equals("session")) {
                SessionModel sessionModel = (SessionModel) CreateReportFragmentArgs.fromBundle(getArguments()).getTypeModel();
                setData(sessionModel);
            }
        }
    }

    private void setData(CaseModel model) {
        if (model.getCaseId() != null && !model.getCaseId().equals("")) {
            data.put("id", model.getCaseId());
        }
    }

    private void setData(SessionModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }
    }

    private void doWork() {
//        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");
//
//        description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();
//
//        data.put("encryption", SelectionManager.getEncryption(requireActivity(), "en", encryption));
//        data.put("description", description);
//
//        Session.addReport(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
//                        Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppAdded), Toast.LENGTH_SHORT).show();
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(String response) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if (!jsonObject.isNull("errors")) {
//                                Iterator<String> keys = (jsonObject.getJSONObject("errors").keys());
//
//                                while (keys.hasNext()) {
//                                    String key = keys.next();
//                                    for (int i = 0; i < jsonObject.getJSONObject("errors").getJSONArray(key).length(); i++) {
//                                        switch (key) {
//                                            case "encryption":
//                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.encryptionIncludeLayout.selectSpinner, binding.encryptionErrorLayout.getRoot(), binding.encryptionErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
//                                                break;
//                                            case "description":
//                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionIncludeLayout.getRoot(), binding.descriptionErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
//                                                break;
//                                        }
//                                    }
//                                }
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