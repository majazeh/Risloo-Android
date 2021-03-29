package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentCreateRoomBinding;

public class CreateRoomFragment extends Fragment {

    // Binding
    private FragmentCreateRoomBinding binding;

    // Vars
    private String psychology = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateRoomBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        binding.psychologyIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateRoomFragmentPsychologyHeader));

        binding.psychologyIncludeLayout.selectTextView.setHint(getResources().getString(R.string.CreateRoomFragmentPsychologyHint));

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateRoomFragmentButton), getResources().getColor(R.color.White));
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
        binding.psychologyIncludeLayout.selectTextView.setOnClickListener(v -> {
            binding.psychologyIncludeLayout.selectTextView.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> binding.psychologyIncludeLayout.selectTextView.setClickable(true), 300);

            // TODO : Place Code Here
        });

        binding.createTextView.getRoot().setOnClickListener(v -> {
            binding.createTextView.getRoot().setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> binding.createTextView.getRoot().setClickable(true), 300);

            if (psychology.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.psychologyIncludeLayout.selectTextView, binding.psychologyIncludeLayout.errorImageView, binding.psychologyIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (!psychology.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.psychologyIncludeLayout.selectTextView, binding.psychologyIncludeLayout.errorImageView, binding.psychologyIncludeLayout.errorTextView);
                doWork();
            }
        });
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getPsychology().equals("")) {
            psychology = ((MainActivity) requireActivity()).singleton.getPsychology();
            binding.psychologyIncludeLayout.selectTextView.setText(psychology);
        }
    }

    private void doWork() {
        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        ((MainActivity) requireActivity()).handler.removeCallbacksAndMessages(null);
    }

}