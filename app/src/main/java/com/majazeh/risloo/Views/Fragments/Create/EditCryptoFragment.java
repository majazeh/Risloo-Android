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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;

public class EditCryptoFragment extends Fragment {

    // Vars
    private String publicKey = "", privateKey = "";

    // Widgets
    private ConstraintLayout publicKeyConstraintLayout, privateKeyConstraintLayout;
    private TextView publicKeyHeaderTextView, privateKeyHeaderTextView;
    private EditText publicKeyEditText, privateKeyEditText;
    private ImageView publicKeyErrorImageView, privateKeyErrorImageView;
    private TextView publicKeyErrorTextView, privateKeyErrorTextView;
    private TextView editPublicKeyTextView, editPrivateKeyTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_crypto, viewGroup, false);

        initializer(view);

        detector();

        listener();

        setData();

        return view;
    }

    private void initializer(View view) {
        publicKeyConstraintLayout = view.findViewById(R.id.fragment_edit_crypto_public_editText);
        privateKeyConstraintLayout = view.findViewById(R.id.fragment_edit_crypto_private_editText);

        publicKeyHeaderTextView = publicKeyConstraintLayout.findViewById(R.id.component_input_multi_header_textView);
        publicKeyHeaderTextView.setText(getResources().getString(R.string.EditCryptoFragmentPublicHeader));
        privateKeyHeaderTextView = privateKeyConstraintLayout.findViewById(R.id.component_input_multi_header_textView);
        privateKeyHeaderTextView.setText(getResources().getString(R.string.EditCryptoFragmentPrivateHeader));

        publicKeyEditText = publicKeyConstraintLayout.findViewById(R.id.component_input_multi_editText);
        privateKeyEditText = privateKeyConstraintLayout.findViewById(R.id.component_input_multi_editText);

        publicKeyErrorImageView = publicKeyConstraintLayout.findViewById(R.id.component_input_multi_error_imageView);
        privateKeyErrorImageView = privateKeyConstraintLayout.findViewById(R.id.component_input_multi_error_imageView);

        publicKeyErrorTextView = publicKeyConstraintLayout.findViewById(R.id.component_input_multi_error_textView);
        privateKeyErrorTextView = privateKeyConstraintLayout.findViewById(R.id.component_input_multi_error_textView);

        editPublicKeyTextView = view.findViewById(R.id.fragment_edit_crypto_public_button_textView);
        editPublicKeyTextView.setText(getResources().getString(R.string.EditCryptoFragmentPublicButton));
        editPublicKeyTextView.setTextColor(getResources().getColor(R.color.White));
        editPrivateKeyTextView = view.findViewById(R.id.fragment_edit_crypto_private_button_textView);
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
                    ((MainActivity) getActivity()).controlEditText.focus(publicKeyEditText);
                    ((MainActivity) getActivity()).controlEditText.select(publicKeyEditText);
                }
            }
            return false;
        });

        privateKeyEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!privateKeyEditText.hasFocus()) {
                    ((MainActivity) getActivity()).controlEditText.focus(privateKeyEditText);
                    ((MainActivity) getActivity()).controlEditText.select(privateKeyEditText);
                }
            }
            return false;
        });

        editPublicKeyTextView.setOnClickListener(v -> {
            editPublicKeyTextView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> editPublicKeyTextView.setClickable(true), 300);

            if (publicKeyEditText.length() == 0) {
                ((MainActivity) getActivity()).controlEditText.error(getActivity(), publicKeyEditText, publicKeyErrorImageView, publicKeyErrorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((MainActivity) getActivity()).controlEditText.check(getActivity(), publicKeyEditText, publicKeyErrorImageView, publicKeyErrorTextView);
                doWork();
            }
        });

        editPrivateKeyTextView.setOnClickListener(v -> {
            editPrivateKeyTextView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> editPrivateKeyTextView.setClickable(true), 300);

            if (privateKeyEditText.length() == 0) {
                ((MainActivity) getActivity()).controlEditText.error(getActivity(), privateKeyEditText, privateKeyErrorImageView, privateKeyErrorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((MainActivity) getActivity()).controlEditText.check(getActivity(), privateKeyEditText, privateKeyErrorImageView, privateKeyErrorTextView);
                doWork();
            }
        });
    }

    private void setData() {
        if (!((MainActivity) getActivity()).singleton.getPublicKey().equals("")) {
            publicKey = ((MainActivity) getActivity()).singleton.getPublicKey();
            publicKeyEditText.setText(publicKey);
        }
        if (!((MainActivity) getActivity()).singleton.getPrivateKey().equals("")) {
            privateKey = ((MainActivity) getActivity()).singleton.getPrivateKey();
            privateKeyEditText.setText(privateKey);
        }
    }

    private void doWork() {
        publicKey = publicKeyEditText.getText().toString().trim();
        privateKey = privateKeyEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

}