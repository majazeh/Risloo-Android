package com.majazeh.risloo.Views.Fragments.Test;

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
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Views.Activities.TestActivity;
import com.majazeh.risloo.databinding.FragmentTestEndBinding;

public class TestEndFragment extends Fragment {

    // Binding
    private FragmentTestEndBinding binding;

    // Vars
    private String status = "sample";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTestEndBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        InitManager.txtTextColor(binding.endTextView.getRoot(), getResources().getString(R.string.TestEndFragmentButtonSample), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.endTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.endTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(this::doWork).widget(binding.endTextView.getRoot());
    }

    private void setData() {
        if (!((TestActivity) requireActivity()).singleton.getStatus().equals("")) {
            status = ((TestActivity) requireActivity()).singleton.getStatus();

            switch (status) {
                case "sample":
                    InitManager.txtTextColor(binding.endTextView.getRoot(), getResources().getString(R.string.TestEndFragmentButtonSample), getResources().getColor(R.color.White));
                    break;
                case "next":
                    InitManager.txtTextColor(binding.endTextView.getRoot(), getResources().getString(R.string.TestEndFragmentButtonNext), getResources().getColor(R.color.White));
                    break;
                case "bulk":
                    InitManager.txtTextColor(binding.endTextView.getRoot(), getResources().getString(R.string.TestEndFragmentButtonBulk), getResources().getColor(R.color.White));
                    break;
            }
        }
    }

    private void doWork() {
        IntentManager.main(requireActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}