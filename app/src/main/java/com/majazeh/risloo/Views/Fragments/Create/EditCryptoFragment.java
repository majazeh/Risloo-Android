package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentEditCryptoBinding;

public class EditCryptoFragment extends Fragment {

    // Binding
    private FragmentEditCryptoBinding binding;

    // Widgets
    private TextView publicKeyHeaderTextView, privateKeyHeaderTextView;
    private EditText publicKeyEditText, privateKeyEditText;
    private ImageView publicKeyErrorImageView, privateKeyErrorImageView;
    private TextView publicKeyErrorTextView, privateKeyErrorTextView;
    private TextView editPublicKeyTextView, editPrivateKeyTextView;

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
        publicKeyHeaderTextView = binding.fragmentEditCryptoPublicEditText.componentInputMultiHeaderTextView;
        publicKeyHeaderTextView.setText(getResources().getString(R.string.EditCryptoFragmentPublicHeader));
        privateKeyHeaderTextView = binding.fragmentEditCryptoPrivateEditText.componentInputMultiHeaderTextView;
        privateKeyHeaderTextView.setText(getResources().getString(R.string.EditCryptoFragmentPrivateHeader));

        publicKeyEditText = binding.fragmentEditCryptoPublicEditText.componentInputMultiEditText;
        privateKeyEditText = binding.fragmentEditCryptoPrivateEditText.componentInputMultiEditText;

        publicKeyErrorImageView = binding.fragmentEditCryptoPublicEditText.componentInputMultiErrorImageView;
        privateKeyErrorImageView = binding.fragmentEditCryptoPrivateEditText.componentInputMultiErrorImageView;
        publicKeyErrorTextView = binding.fragmentEditCryptoPublicEditText.componentInputMultiErrorTextView;
        privateKeyErrorTextView = binding.fragmentEditCryptoPrivateEditText.componentInputMultiErrorTextView;

        editPublicKeyTextView = binding.fragmentEditCryptoPublicButtonTextView.componentButtonRectangle32sdp;
        editPublicKeyTextView.setText(getResources().getString(R.string.EditCryptoFragmentPublicButton));
        editPublicKeyTextView.setTextColor(getResources().getColor(R.color.White));
        editPrivateKeyTextView = binding.fragmentEditCryptoPrivateButtonTextView.componentButtonRectangle32sdp;
        editPrivateKeyTextView.setText(getResources().getString(R.string.EditCryptoFragmentPrivateButton));
        editPrivateKeyTextView.setTextColor(getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            editPublicKeyTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
            editPrivateKeyTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            editPublicKeyTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
            editPrivateKeyTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        publicKeyEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!publicKeyEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(getActivity(), publicKeyEditText);
                }
            }
            return false;
        });

        privateKeyEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!privateKeyEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(getActivity(), privateKeyEditText);
                }
            }
            return false;
        });

        editPublicKeyTextView.setOnClickListener(v -> {
            editPublicKeyTextView.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> editPublicKeyTextView.setClickable(true), 300);

            if (publicKeyEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(getActivity(), publicKeyEditText, publicKeyErrorImageView, publicKeyErrorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((MainActivity) requireActivity()).controlEditText.check(getActivity(), publicKeyEditText, publicKeyErrorImageView, publicKeyErrorTextView);
                doWork();
            }
        });

        editPrivateKeyTextView.setOnClickListener(v -> {
            editPrivateKeyTextView.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> editPrivateKeyTextView.setClickable(true), 300);

            if (privateKeyEditText.length() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(getActivity(), privateKeyEditText, privateKeyErrorImageView, privateKeyErrorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((MainActivity) requireActivity()).controlEditText.check(getActivity(), privateKeyEditText, privateKeyErrorImageView, privateKeyErrorTextView);
                doWork();
            }
        });
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getPublicKey().equals("")) {
            publicKey = ((MainActivity) requireActivity()).singleton.getPublicKey();
            publicKeyEditText.setText(publicKey);
        }
        if (!((MainActivity) requireActivity()).singleton.getPrivateKey().equals("")) {
            privateKey = ((MainActivity) requireActivity()).singleton.getPrivateKey();
            privateKeyEditText.setText(privateKey);
        }
    }

    private void doWork() {
        publicKey = publicKeyEditText.getText().toString().trim();
        privateKey = privateKeyEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}