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
import com.majazeh.risloo.Utils.Managers.ClickManager;
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
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.referenceIncludeLayout.selectRecyclerView);

        ClickManager.onDelayedClickListener(() -> {
            if (binding.referenceIncludeLayout.selectRecyclerView.getChildCount() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, binding.referenceIncludeLayout.errorImageView, binding.referenceIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (binding.referenceIncludeLayout.selectRecyclerView.getChildCount() != 0) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, binding.referenceIncludeLayout.errorImageView, binding.referenceIncludeLayout.errorTextView);

                doWork();
            }
        }).widget(binding.createTextView.getRoot());
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
    }

}