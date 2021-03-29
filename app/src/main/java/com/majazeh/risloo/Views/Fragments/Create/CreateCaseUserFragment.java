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
import com.majazeh.risloo.databinding.FragmentCreateCaseUserBinding;

public class CreateCaseUserFragment extends Fragment {

    // Binding
    private FragmentCreateCaseUserBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateCaseUserBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        binding.referenceIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCaseUserFragmentReferenceHeader));

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateCaseUserFragmentButton), getResources().getColor(R.color.White));
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
        binding.referenceIncludeLayout.selectRecyclerView.setOnClickListener(v -> {
            binding.referenceIncludeLayout.selectRecyclerView.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> binding.referenceIncludeLayout.selectRecyclerView.setClickable(true), 300);

            // TODO : Place Code Here
        });

        binding.createTextView.getRoot().setOnClickListener(v -> {
            binding.createTextView.getRoot().setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> binding.createTextView.getRoot().setClickable(true), 300);

            if (binding.referenceIncludeLayout.selectRecyclerView.getChildCount() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, binding.referenceIncludeLayout.errorImageView, binding.referenceIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (binding.referenceIncludeLayout.selectRecyclerView.getChildCount() != 0) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, binding.referenceIncludeLayout.errorImageView, binding.referenceIncludeLayout.errorTextView);

                doWork();
            }
        });
    }

    private void setData() {
        // TODO : Set References Here
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