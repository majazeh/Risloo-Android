package com.majazeh.risloo.Views.Fragments.Edit;

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
import com.majazeh.risloo.databinding.FragmentEditCryptoBinding;

public class EditCryptoFragment extends Fragment {

    // Binding
    private FragmentEditCryptoBinding binding;

    // Vars
    private String publicKey = "", privateKey = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditCryptoBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        binding.publicIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCryptoFragmentPublicHeader));
        binding.privateIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCryptoFragmentPrivateHeader));

        InitManager.txtTextColor(binding.publicEditTextView.getRoot(), getResources().getString(R.string.EditCryptoFragmentButton), getResources().getColor(R.color.White));
        InitManager.txtTextColor(binding.privateEditTextView.getRoot(), getResources().getString(R.string.EditCryptoFragmentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.publicEditTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
            binding.privateEditTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.publicEditTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
            binding.privateEditTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.publicIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.publicIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.publicIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.privateIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.privateIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.privateIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            if (binding.publicIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.publicIncludeLayout.inputEditText, binding.publicIncludeLayout.errorImageView, binding.publicIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.publicIncludeLayout.inputEditText, binding.publicIncludeLayout.errorImageView, binding.publicIncludeLayout.errorTextView);

                doWork();
            }
        }).widget(binding.publicEditTextView.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            if (binding.privateIncludeLayout.inputEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.privateIncludeLayout.inputEditText, binding.privateIncludeLayout.errorImageView, binding.privateIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.privateIncludeLayout.inputEditText, binding.privateIncludeLayout.errorImageView, binding.privateIncludeLayout.errorTextView);

                doWork();
            }
        }).widget(binding.privateEditTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getPublicKey().equals("")) {
            publicKey = ((MainActivity) requireActivity()).singleton.getPublicKey();
            binding.publicIncludeLayout.inputEditText.setText(publicKey);
        }
        if (!((MainActivity) requireActivity()).singleton.getPrivateKey().equals("")) {
            privateKey = ((MainActivity) requireActivity()).singleton.getPrivateKey();
            binding.privateIncludeLayout.inputEditText.setText(privateKey);
        }
    }

    private void doWork() {
        publicKey = binding.publicIncludeLayout.inputEditText.getText().toString().trim();
        privateKey = binding.privateIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}