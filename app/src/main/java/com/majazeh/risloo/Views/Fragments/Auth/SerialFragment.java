package com.majazeh.risloo.Views.Fragments.Auth;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
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
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.squareup.picasso.Picasso;

public class SerialFragment extends Fragment {

    // Vars
    private String serial = "";

    // Widgets
    private ImageView avatarImageView;
    private TextView charTextView;
    private EditText serialEditText;
    private ImageView serialErrorImageView;
    private TextView serialErrorTextView;
    private TextView serialTextView;
    private TextView dashboardTextView, logoutTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_serial, viewGroup, false);

        initializer(view);

        detector();

        listener();

        setData();

        return view;
    }

    private void initializer(View view) {
        avatarImageView = view.findViewById(R.id.component_auth_avatar_imageView);

        charTextView = view.findViewById(R.id.component_auth_avatar_textView);

        serialEditText = view.findViewById(R.id.component_auth_input_text_editText);
        serialEditText.setHint(getResources().getString(R.string.SerialFragmentInput));

        serialErrorImageView = view.findViewById(R.id.component_auth_input_text_error_imageView);

        serialErrorTextView = view.findViewById(R.id.component_auth_input_text_error_textView);

        serialTextView = view.findViewById(R.id.fragment_serial_button_textView);
        serialTextView.setText(getResources().getString(R.string.SerialFragmentButton));

        dashboardTextView = view.findViewById(R.id.fragment_serial_dashboard_textView);
        dashboardTextView.setText(StringManager.foregroundStyle(getResources().getString(R.string.AuthDashboard), 0, 8, getResources().getColor(R.color.Gray900), Typeface.BOLD));
        logoutTextView = view.findViewById(R.id.fragment_serial_logout_textView);
        logoutTextView.setText(getResources().getString(R.string.AuthLogout));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            serialTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        serialEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!serialEditText.hasFocus()) {
                    ((AuthActivity) getActivity()).controlEditText.focus(serialEditText);
                    ((AuthActivity) getActivity()).controlEditText.select(serialEditText);
                }
            }
            return false;
        });

        serialTextView.setOnClickListener(v -> {
            serialTextView.setClickable(false);
            ((AuthActivity) getActivity()).handler.postDelayed(() -> serialTextView.setClickable(true), 300);

            if (serialEditText.length() == 0) {
                ((AuthActivity) getActivity()).controlEditText.error(getActivity(), serialEditText, serialErrorImageView, serialErrorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) getActivity()).controlEditText.check(getActivity(), serialEditText, serialErrorImageView, serialErrorTextView);
                doWork();
            }
        });

        dashboardTextView.setOnClickListener(v -> {
            dashboardTextView.setClickable(false);
            ((AuthActivity) getActivity()).handler.postDelayed(() -> dashboardTextView.setClickable(true), 300);

            IntentManager.main(getActivity());
        });

        logoutTextView.setOnClickListener(v -> {
            logoutTextView.setClickable(false);
            ((AuthActivity) getActivity()).handler.postDelayed(() -> logoutTextView.setClickable(true), 300);

            // TODO : Place Code Here
        });
    }

    private void setData() {
        if (((AuthActivity) getActivity()).singleton.getAvatar().equals("")) {
            charTextView.setVisibility(View.VISIBLE);
            if (((AuthActivity) getActivity()).singleton.getName().equals(""))
                charTextView.setText(StringManager.firstChars(getResources().getString(R.string.AuthToolbar)));
            else
                charTextView.setText(StringManager.firstChars(((AuthActivity) getActivity()).singleton.getName()));
        } else {
            charTextView.setVisibility(View.GONE);

            Picasso.get().load(((AuthActivity) getActivity()).singleton.getAvatar()).placeholder(R.color.Blue500).into(avatarImageView);
        }
    }

    private void doWork() {
        serial = serialEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

}