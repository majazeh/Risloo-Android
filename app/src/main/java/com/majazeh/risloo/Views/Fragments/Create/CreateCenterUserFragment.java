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
import com.majazeh.risloo.Utils.Entities.Model;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;
import com.majazeh.risloo.databinding.FragmentCreateCenterUserBinding;

import org.json.JSONException;

public class CreateCenterUserFragment extends Fragment {

    // Binding
    private FragmentCreateCenterUserBinding binding;

    // Dialogs
    private SearchableDialog roomsDialog;

    // Vars
    public String mobile = "", type = "", roomId = "", roomName = "", centerName = "", name = "";
    private boolean createCase = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateCenterUserBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        roomsDialog = new SearchableDialog();

        binding.mobileIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterUserFragmentMobileHeader));
        binding.typeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterUserFragmentTypeHeader));
        binding.roomIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterUserFragmentRoomHeader));
        binding.nameIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterUserFragmentNameHeader));

        binding.nameGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateCenterUserFragmentNameGuide));

        binding.caseCheckbox.getRoot().setText(getResources().getString(R.string.CreateCenterUserFragmentCheckbox));

        InitManager.spinner(requireActivity(), binding.typeIncludeLayout.selectSpinner, R.array.UsersTypes);

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

        binding.typeIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();

                if (position == 3) {
                    binding.clientGroup.setVisibility(View.VISIBLE);
                } else {
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

        binding.nameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.nameIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.nameIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.caseCheckbox.getRoot().setOnCheckedChangeListener((buttonView, isChecked) -> createCase = isChecked);

        ClickManager.onDelayedClickListener(() -> {
            if (binding.mobileIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.mobileIncludeLayout.inputEditText, binding.mobileIncludeLayout.errorImageView, binding.mobileIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (roomId.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.roomIncludeLayout.selectContainer, binding.roomIncludeLayout.errorImageView, binding.roomIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (binding.nameIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameIncludeLayout.errorImageView, binding.nameIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (binding.mobileIncludeLayout.inputEditText.length() != 0 && !roomId.equals("") && binding.nameIncludeLayout.inputEditText.length() != 0) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.mobileIncludeLayout.inputEditText, binding.mobileIncludeLayout.errorImageView, binding.mobileIncludeLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.roomIncludeLayout.selectContainer, binding.roomIncludeLayout.errorImageView, binding.roomIncludeLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameIncludeLayout.errorImageView, binding.nameIncludeLayout.errorTextView);

                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getMobile().equals("")) {
            mobile = ((MainActivity) requireActivity()).singleton.getMobile();
            binding.mobileIncludeLayout.inputEditText.setText(mobile);
        }
        if (!((MainActivity) requireActivity()).singleton.getType().equals("")) {
            type = ((MainActivity) requireActivity()).singleton.getType();
            for (int i=0; i<binding.typeIncludeLayout.selectSpinner.getCount(); i++) {
                if (binding.typeIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(type)) {
                    binding.typeIncludeLayout.selectSpinner.setSelection(i);

                    if (i == 3) {
                        binding.clientGroup.setVisibility(View.VISIBLE);
                    } else {
                        binding.clientGroup.setVisibility(View.GONE);
                    }
                }
            }
        }
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            roomId = ((MainActivity) requireActivity()).singleton.getAddress();
            roomName = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.roomIncludeLayout.primaryTextView.setText(roomName);
        }
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            centerName = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.roomIncludeLayout.secondaryTextView.setText(centerName);
        }
        if (!((MainActivity) requireActivity()).singleton.getName().equals("")) {
            name = ((MainActivity) requireActivity()).singleton.getName();
            binding.nameIncludeLayout.inputEditText.setText(name);
        }
        if (((MainActivity) requireActivity()).singleton.getCreateCase()) {
            createCase = true;
            binding.caseCheckbox.getRoot().setChecked(true);
        }
    }

    public void responseDialog(String method, Model item) {
        try {
            switch (method) {
                case "rooms":
                    if (!roomId.equals(item.get("id").toString())) {
                        roomId = item.get("id").toString();
                        roomName = item.get("title").toString();
                        centerName = item.get("subtitle").toString();

                        binding.roomIncludeLayout.primaryTextView.setText(roomName);
                        binding.roomIncludeLayout.secondaryTextView.setText(centerName);
                    } else if (roomId.equals(item.get("id").toString())) {
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
        mobile = binding.mobileIncludeLayout.inputEditText.getText().toString().trim();
        name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}