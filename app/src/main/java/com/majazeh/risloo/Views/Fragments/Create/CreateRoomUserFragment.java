package com.majazeh.risloo.Views.Fragments.Create;

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
import com.majazeh.risloo.Views.BottomSheets.AuthBottomSheet;
import com.majazeh.risloo.databinding.FragmentCreateRoomUserBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.AuthModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class CreateRoomUserFragment extends Fragment {

    // Binding
    private FragmentCreateRoomUserBinding binding;

    // BottomSheets
    private AuthBottomSheet authBottomSheet;

    // Vars
    public String roomId = "";
    private String mobile = "", nickname = "", createCase = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateRoomUserBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setExtra();

        return binding.getRoot();
    }

    private void initializer() {
        authBottomSheet = new AuthBottomSheet();

        binding.mobileIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateRoomUserFragmentMobileHeader));
        binding.nicknameIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateRoomUserFragmentNicknameHeader));

        binding.nicknameGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateRoomUserFragmentNicknameGuide));

        binding.caseCheckBox.getRoot().setText(getResources().getString(R.string.CreateRoomUserFragmentCheckbox));

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateRoomUserFragmentButton), getResources().getColor(R.color.White));
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
        binding.mobileIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.mobileIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.mobileIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.nicknameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.nicknameIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.nicknameIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.caseCheckBox.getRoot().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                createCase = "1";
            } else {
                createCase = "";
            }
        });

        ClickManager.onDelayedClickListener(() -> {
            if (binding.mobileIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.mobileIncludeLayout.inputEditText, binding.mobileErrorLayout.getRoot(), binding.mobileErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.mobileIncludeLayout.inputEditText, binding.mobileErrorLayout.getRoot(), binding.mobileErrorLayout.errorTextView);
                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setExtra() {
        if (getArguments() != null) {
            if (getArguments().getString("id") != null && !getArguments().getString("id").equals("")) {
                roomId = getArguments().getString("id");
            }

            if (getArguments().getString("mobile") != null && !getArguments().getString("mobile").equals("")) {
                mobile = getArguments().getString("mobile");
                binding.mobileIncludeLayout.inputEditText.setText(mobile);
            }

            if (getArguments().getString("nickname") != null && !getArguments().getString("nickname").equals("")) {
                nickname = getArguments().getString("nickname");
                binding.nicknameIncludeLayout.inputEditText.setText(nickname);
            }

            if (getArguments().getString("create_case") != null && !getArguments().getString("create_case").equals("")) {
                createCase = getArguments().getString("create_case");

                if (createCase.equals("1")) {
                    binding.caseCheckBox.getRoot().setChecked(true);
                }
            }
        }
    }

    private void doWork() {
        mobile = binding.mobileIncludeLayout.inputEditText.getText().toString().trim();
        nickname = binding.nicknameIncludeLayout.inputEditText.getText().toString().trim();

        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        HashMap data = new HashMap<>();
        data.put("id", roomId);
        data.put("mobile", mobile);
        data.put("nickname", nickname);
        data.put("create_case", createCase);

        HashMap header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        Room.createUser(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            AuthModel model = new AuthModel((JSONObject) object);

                            ((MainActivity) requireActivity()).loadingDialog.dismiss();
                            authBottomSheet.show(requireActivity().getSupportFragmentManager(), "authBottomSheet");
                            authBottomSheet.setData(roomId, model.getKey(), ((MainActivity) requireActivity()).singleton.getName(), ((MainActivity) requireActivity()).singleton.getAvatar());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                                            case "mobile":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.mobileIncludeLayout.inputEditText, binding.mobileErrorLayout.getRoot(), binding.mobileErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "nickname":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.nicknameIncludeLayout.inputEditText, binding.nicknameErrorLayout.getRoot(), binding.nicknameErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
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