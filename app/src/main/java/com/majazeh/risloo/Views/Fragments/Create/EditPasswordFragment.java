package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CutCopyPasteEditText;
import com.majazeh.risloo.Views.Activities.MainActivity;

public class EditPasswordFragment extends Fragment {

    // Vars
    private String password = "";
    private boolean passwordVisibility = false;

    // Widgets
    private TextView passwordHeaderTextView;
    private CutCopyPasteEditText passwordEditText;
    private ImageView passwordVisibilityImageView;
    private ImageView passwordErrorImageView;
    private TextView passwordErrorTextView;
    private TextView passwordGuideTextView;
    private TextView editPasswordTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_password, viewGroup, false);

        initializer(view);

        detector();

        listener();

        setData();

        return view;
    }

    private void initializer(View view) {
        passwordHeaderTextView = view.findViewById(R.id.component_input_password_header_textView);
        passwordHeaderTextView.setText(getResources().getString(R.string.EditPasswordFragmentHeader));

        passwordEditText = view.findViewById(R.id.component_input_password_editText);

        passwordVisibilityImageView = view.findViewById(R.id.component_input_password_visibility_imageView);

        passwordErrorImageView = view.findViewById(R.id.component_input_password_error_imageView);

        passwordErrorTextView = view.findViewById(R.id.component_input_password_error_textView);

        passwordGuideTextView = view.findViewById(R.id.component_main_guide_error_textView);
        passwordGuideTextView.setText(getResources().getString(R.string.EditPasswordFragmentHint));

        editPasswordTextView = view.findViewById(R.id.fragment_edit_password_button_textView);
        editPasswordTextView.setText(getResources().getString(R.string.EditPasswordFragmentButton));
        editPasswordTextView.setTextColor(getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            editPasswordTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            editPasswordTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        passwordEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!passwordEditText.hasFocus()) {
                    ((MainActivity) getActivity()).controlEditText.select(getActivity(), passwordEditText);
                }
            }
            return false;
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (passwordEditText.length() == 0) {
                    passwordVisibilityImageView.setVisibility(View.INVISIBLE);
                } else if (passwordEditText.length() == 1) {
                    passwordVisibilityImageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordEditText.setOnCutCopyPasteListener(new CutCopyPasteEditText.OnCutCopyPasteListener() {
            @Override
            public void onCut() {

            }

            @Override
            public void onCopy() {

            }

            @Override
            public void onPaste() {
                if (passwordEditText.length() != 0) {
                    passwordVisibilityImageView.setVisibility(View.VISIBLE);
                }
            }
        });

        passwordVisibilityImageView.setOnClickListener(v -> {
            if (!passwordVisibility) {
                passwordVisibility = true;
                passwordVisibilityImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_light, null));

                ImageViewCompat.setImageTintList(passwordVisibilityImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Blue800));
                passwordEditText.setTransformationMethod(null);
            } else {
                passwordVisibility = false;
                passwordVisibilityImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_eye_slash_light, null));

                ImageViewCompat.setImageTintList(passwordVisibilityImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Gray600));
                passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        editPasswordTextView.setOnClickListener(v -> {
            editPasswordTextView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> editPasswordTextView.setClickable(true), 300);

            if (passwordEditText.length() == 0) {
                ((MainActivity) getActivity()).controlEditText.error(getActivity(), passwordEditText, passwordErrorImageView, passwordErrorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((MainActivity) getActivity()).controlEditText.check(getActivity(), passwordEditText, passwordErrorImageView, passwordErrorTextView);
                doWork();
            }
        });
    }

    private void setData() {
        if (!((MainActivity) getActivity()).singleton.getPassword().equals("")) {
            password = ((MainActivity) getActivity()).singleton.getPassword();
            passwordEditText.setText(password);
        }
    }

    private void doWork() {
        password = passwordEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

}