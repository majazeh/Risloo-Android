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
import com.majazeh.risloo.databinding.FragmentCreateCaseBinding;

public class CreateCaseFragment extends Fragment {

    // Binding
    private FragmentCreateCaseBinding binding;

    // Vars
    private String room = "", center = "", situation = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateCaseBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        binding.roomIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCaseFragmentRoomHeader));
        binding.referenceIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCaseFragmentReferenceHeader));
        binding.situationIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCaseFragmentSituationHeader));

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateCenterFragmentButton), getResources().getColor(R.color.White));
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
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.roomIncludeLayout.selectContainer);

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.referenceIncludeLayout.selectRecyclerView);

        binding.situationIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.situationIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.situationIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            if (room.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.roomIncludeLayout.selectContainer, binding.roomIncludeLayout.errorImageView, binding.roomIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (binding.referenceIncludeLayout.selectRecyclerView.getChildCount() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, binding.referenceIncludeLayout.errorImageView, binding.referenceIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (binding.situationIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.situationIncludeLayout.inputEditText, binding.situationIncludeLayout.errorImageView, binding.situationIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (!room.equals("") && binding.referenceIncludeLayout.selectRecyclerView.getChildCount() != 0 && binding.situationIncludeLayout.inputEditText.length() != 0) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.roomIncludeLayout.selectContainer, binding.roomIncludeLayout.errorImageView, binding.roomIncludeLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, binding.referenceIncludeLayout.errorImageView, binding.referenceIncludeLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.situationIncludeLayout.inputEditText, binding.situationIncludeLayout.errorImageView, binding.situationIncludeLayout.errorTextView);

                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            room = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.roomIncludeLayout.primaryTextView.setText(room);
        }
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            center = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.roomIncludeLayout.secondaryTextView.setText(center);
        }

        // TODO : Set References Here

        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            situation = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.situationIncludeLayout.inputEditText.setText(situation);
        }
    }

    private void doWork() {
        situation = binding.situationIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}