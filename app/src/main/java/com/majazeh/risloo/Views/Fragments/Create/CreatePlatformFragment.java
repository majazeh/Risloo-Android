package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentCreatePlatformBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class CreatePlatformFragment extends Fragment {

    // Binding
    private FragmentCreatePlatformBinding binding;

    // Vars
    private HashMap data, header;
    private String title = "", sessionType = "", indentifierType = "", indentifier = "", createSession = "", available = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreatePlatformBinding.inflate(inflater, viewGroup, false);

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

        binding.titleIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreatePlatformFragmentTitleHeader));
        binding.sessionTypeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreatePlatformFragmentSessionTypeHeader));
        binding.indentifierTypeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreatePlatformFragmentIndetifierTypeHeader));
        binding.indentifierIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreatePlatformFragmentIdentifierHeader));

        binding.indentifierGuideLayout.guideTextView.setText(getResources().getString(R.string.CreatePlatformFragmentIdentifierHint));

        binding.sessionCheckBox.getRoot().setText(getResources().getString(R.string.CreatePlatformFragmentSessionCheckbox));

        InitManager.fixedSpinner(requireActivity(), binding.sessionTypeIncludeLayout.selectSpinner, R.array.PlatformSessions, "main");
        InitManager.fixedSpinner(requireActivity(), binding.indentifierTypeIncludeLayout.selectSpinner, R.array.PlatformIndentifiers, "main");

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreatePlatformFragmentButton), getResources().getColor(R.color.White));
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
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.titleIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.titleIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.titleIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            title = binding.titleIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.sessionTypeIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sessionType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.indentifierTypeIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indentifierType = parent.getItemAtPosition(position).toString();

                switch (indentifierType) {
                    case "آدرس اینترنتی":
                        binding.indentifierIncludeLayout.inputEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        break;
                    case "شماره تماس":
                        binding.indentifierIncludeLayout.inputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        break;
                    case "متن":
                        binding.indentifierIncludeLayout.inputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.indentifierIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.indentifierIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.indentifierIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.indentifierIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            indentifier = binding.indentifierIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.sessionCheckBox.getRoot().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                createSession = "1";
            else
                createSession = "";
        });

        binding.availableSwitchCompat.getRoot().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                available = "on";

                binding.availableSwitchCompat.getRoot().setText(getResources().getString(R.string.AppSwicthOn));
                binding.availableSwitchCompat.getRoot().setTextColor(getResources().getColor(R.color.Green700));
                binding.availableSwitchCompat.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_green50_border_1sdp_gray300);
            } else {
                available = "";

                binding.availableSwitchCompat.getRoot().setText(getResources().getString(R.string.AppSwicthOff));
                binding.availableSwitchCompat.getRoot().setTextColor(getResources().getColor(R.color.Gray600));
                binding.availableSwitchCompat.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray300);
            }
        });

        ClickManager.onDelayedClickListener(() -> {
            if (binding.titleIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.titleIncludeLayout.inputEditText, binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.titleIncludeLayout.inputEditText, binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView);
                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        String type = CreatePlatformFragmentArgs.fromBundle(getArguments()).getType();
        TypeModel typeModel = CreatePlatformFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (type.equals("center")) {
                CenterModel centerModel = (CenterModel) CreatePlatformFragmentArgs.fromBundle(getArguments()).getTypeModel();
                setData(centerModel);
            }
        }
    }

    private void setData(CenterModel model) {
        if (model.getCenterId() != null && !model.getCenterId().equals("")) {
            data.put("id", model.getCenterId());
        }
    }

    private void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        data.put("title", title);
        data.put("type", SelectionManager.getPlatformSession(requireActivity(), "en", sessionType));
        data.put("identifier_type", SelectionManager.getPlatformIdentifier(requireActivity(), "en", indentifierType));
        data.put("identifier", indentifier);
        data.put("selected", createSession);
        data.put("available", available);

        Center.createCenterSessionPlatform(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
                        Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppAdded), Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.isNull("errors")) {
                                Iterator<String> keys = (jsonObject.getJSONObject("errors").keys());

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    for (int i = 0; i < jsonObject.getJSONObject("errors").getJSONArray(key).length(); i++) {
                                        switch (key) {
                                            case "title":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.titleIncludeLayout.inputEditText, binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "type":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.sessionTypeIncludeLayout.selectSpinner, binding.sessionTypeErrorLayout.getRoot(), binding.sessionTypeErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "identifier_type":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.indentifierTypeIncludeLayout.selectSpinner, binding.indentifierTypeErrorLayout.getRoot(), binding.indentifierTypeErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "identifier":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.indentifierIncludeLayout.inputEditText, binding.indentifierErrorLayout.getRoot(), binding.indentifierErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                        }
                                    }
                                }
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