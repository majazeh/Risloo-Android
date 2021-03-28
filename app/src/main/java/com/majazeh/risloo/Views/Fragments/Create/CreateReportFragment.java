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
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentCreateReportBinding;

public class CreateReportFragment extends Fragment {

    // Binding
    private FragmentCreateReportBinding binding;

    // Vars
    private String encryption = "", description = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateReportBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        binding.encryptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateReportFragmentEncryptionHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateReportFragmentDescriptionHeader));

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
        binding.encryptionIncludeLayout.selectTextView.setOnClickListener(v -> {
            binding.encryptionIncludeLayout.selectTextView.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> binding.encryptionIncludeLayout.selectTextView.setClickable(true), 300);

            // TODO : Place Code Here
        });

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.descriptionIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.descriptionIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.cryptoTextView.getRoot().setOnClickListener(v -> {
            binding.cryptoTextView.getRoot().setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> binding.cryptoTextView.getRoot().setClickable(true), 300);

            // TODO : Place Code Here
        });

        binding.createTextView.getRoot().setOnClickListener(v -> {
            binding.createTextView.getRoot().setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> binding.createTextView.getRoot().setClickable(true), 300);

            if (binding.descriptionIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionIncludeLayout.errorImageView, binding.descriptionIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (binding.descriptionIncludeLayout.inputEditText.length() != 0) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionIncludeLayout.errorImageView, binding.descriptionIncludeLayout.errorTextView);

                doWork();
            }
        });
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            encryption = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.encryptionIncludeLayout.selectTextView.setText(encryption);
        }
        if (!((MainActivity) requireActivity()).singleton.getDescription().equals("")) {
            description = ((MainActivity) requireActivity()).singleton.getDescription();
            binding.descriptionIncludeLayout.inputEditText.setText(description);
        }
    }

    private void doWork() {
        description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}