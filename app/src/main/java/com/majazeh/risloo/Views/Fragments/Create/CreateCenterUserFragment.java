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
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.BottomSheets.AuthBottomSheet;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;
import com.majazeh.risloo.databinding.FragmentCreateCenterUserBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class CreateCenterUserFragment extends Fragment {

    // Binding
    private FragmentCreateCenterUserBinding binding;

    // Dialogs
    private SearchableDialog roomsDialog;

    // BottomSheets
    private AuthBottomSheet authBottomSheet;

    // Vars
    private HashMap data, header;
    public String centerId = "", type = "personal_clinic", mobile = "", position = "", roomId = "", roomName = "", centerName = "", nickname = "", createCase = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateCenterUserBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setExtra();

        return binding.getRoot();
    }

    private void initializer() {
        roomsDialog = new SearchableDialog();

        authBottomSheet = new AuthBottomSheet();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.mobileIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterUserFragmentMobileHeader));
        binding.positionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterUserFragmentPositionHeader));
        binding.roomIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterUserFragmentRoomHeader));
        binding.nicknameIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterUserFragmentNicknameHeader));

        binding.nicknameGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateCenterUserFragmentNicknameGuide));

        binding.caseCheckBox.getRoot().setText(getResources().getString(R.string.CreateCenterUserFragmentCheckbox));

        InitManager.spinner(requireActivity(), binding.positionIncludeLayout.selectSpinner, R.array.UserTypes, "main");

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateCenterUserFragmentButton), getResources().getColor(R.color.White));
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

        binding.positionIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CreateCenterUserFragment.this.position = parent.getItemAtPosition(position).toString();

                if (position == 3) {
                    binding.roomIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    binding.clientGroup.setVisibility(View.VISIBLE);
                } else {
                    binding.roomIncludeLayout.getRoot().setVisibility(View.GONE);
                    binding.clientGroup.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ClickManager.onDelayedClickListener(() -> {
            roomsDialog.show(requireActivity().getSupportFragmentManager(), "roomsDialog");
            roomsDialog.setData("rooms");
        }).widget(binding.roomIncludeLayout.selectContainer);

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
                centerId = getArguments().getString("id");
                data.put("id", centerId);
            }

            if (getArguments().getString("type") != null && !getArguments().getString("type").equals("")) {
                type = getArguments().getString("type");
                switch (type) {
                    case "personal_clinic":
                        binding.positionIncludeLayout.getRoot().setVisibility(View.GONE);
                        binding.clientGroup.setVisibility(View.VISIBLE);
                        break;
                    case "counseling_center":
                        binding.positionIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.clientGroup.setVisibility(View.GONE);
                        break;
                }
            }

            if (getArguments().getString("mobile") != null && !getArguments().getString("mobile").equals("")) {
                mobile = getArguments().getString("mobile");
                binding.mobileIncludeLayout.inputEditText.setText(mobile);
            }

            if (getArguments().getString("position") != null && !getArguments().getString("position").equals("")) {
                position = SelectionManager.getPosition(requireActivity(), "fa", getArguments().getString("position"));
                for (int i = 0; i < binding.positionIncludeLayout.selectSpinner.getCount(); i++) {
                    if (binding.positionIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(position)) {
                        binding.positionIncludeLayout.selectSpinner.setSelection(i);

                        if (i == 3) {
                            binding.roomIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.clientGroup.setVisibility(View.VISIBLE);
                        } else {
                            binding.roomIncludeLayout.getRoot().setVisibility(View.GONE);
                            binding.clientGroup.setVisibility(View.GONE);
                        }
                    }
                }
            }

            if (getArguments().getString("room_id") != null && !getArguments().getString("room_id").equals("") && getArguments().getString("room_name") != null && !getArguments().getString("room_name").equals("")) {
                roomId = getArguments().getString("room_id");
                roomName = getArguments().getString("room_name");
                binding.roomIncludeLayout.primaryTextView.setText(roomName);
            }

            if (getArguments().getString("center_name") != null && !getArguments().getString("center_name").equals("")) {
                centerName = getArguments().getString("center_name");
                binding.roomIncludeLayout.secondaryTextView.setText(centerName);
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

    public void responseDialog(String method, TypeModel item) {
        try {
            switch (method) {
                case "rooms":
                    RoomModel model = (RoomModel) item;

                    if (!roomId.equals(model.getRoomId())) {
                        roomId = model.getRoomId();
                        roomName = model.getRoomManager().getName();
                        centerName = model.getRoomCenter().getDetail().getString("title");

                        binding.roomIncludeLayout.primaryTextView.setText(roomName);
                        binding.roomIncludeLayout.secondaryTextView.setText(centerName);
                    } else if (roomId.equals(model.getRoomId())) {
                        roomId = "";
                        roomName = "";
                        centerName = "";

                        binding.roomIncludeLayout.primaryTextView.setText("");
                        binding.roomIncludeLayout.secondaryTextView.setText("");
                    }

                    roomsDialog.dismiss();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        mobile = binding.mobileIncludeLayout.inputEditText.getText().toString().trim();
        nickname = binding.nicknameIncludeLayout.inputEditText.getText().toString().trim();

        data.put("mobile", mobile);

        if (type.equals("counseling_center")) {
            data.put("position", SelectionManager.getPosition(requireActivity(), "en", position));

            if (position.equals("مراجع")) {
                data.put("room_id", roomId);
                data.put("nickname", nickname);
                data.put("create_case", createCase);
            }
        } else {
            data.put("nickname", nickname);
            data.put("create_case", createCase);
        }

        Center.createUser(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            AuthModel model = new AuthModel((JSONObject) object);

                            ((MainActivity) requireActivity()).loadingDialog.dismiss();
                            authBottomSheet.show(requireActivity().getSupportFragmentManager(), "authBottomSheet");
                            authBottomSheet.setData(centerId, model.getKey(), ((MainActivity) requireActivity()).singleton.getName(), ((MainActivity) requireActivity()).singleton.getAvatar());
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
                                            case "position":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.positionIncludeLayout.selectSpinner, binding.positionErrorLayout.getRoot(), binding.positionErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "room_id":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.roomIncludeLayout.selectContainer, binding.roomErrorLayout.getRoot(), binding.roomErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
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