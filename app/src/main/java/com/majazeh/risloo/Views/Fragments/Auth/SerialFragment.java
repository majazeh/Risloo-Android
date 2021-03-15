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
import com.majazeh.risloo.databinding.FragmentSerialBinding;
import com.squareup.picasso.Picasso;

public class SerialFragment extends Fragment {

    // Binding
    private FragmentSerialBinding binding;

    // Widgets
    private ImageView avatarImageView;
    private TextView charTextView;
    private EditText serialEditText;
    private ImageView serialErrorImageView;
    private TextView serialErrorTextView;
    private TextView serialTextView, dashboardTextView, logoutTextView;

    // Vars
    private String serial = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentSerialBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        avatarImageView = binding.fragmentSerialAvatarImageView.componentAuthAvatarImageView;
        charTextView = binding.fragmentSerialAvatarImageView.componentAuthAvatarTextView;

        serialEditText = binding.fragmentSerialInputEditText.componentAuthInputTextEditText;
        serialEditText.setHint(getResources().getString(R.string.SerialFragmentInput));

        serialErrorImageView = binding.fragmentSerialInputEditText.componentAuthInputTextErrorImageView;
        serialErrorTextView = binding.fragmentSerialInputEditText.componentAuthInputTextErrorTextView;

        serialTextView = binding.fragmentSerialButtonTextView.componentAuthButton;
        serialTextView.setText(getResources().getString(R.string.SerialFragmentButton));

        dashboardTextView = binding.fragmentSerialDashboardTextView.componentAuthLink;
        dashboardTextView.setText(StringManager.foregroundStyle(getResources().getString(R.string.AuthDashboard), 0, 8, getResources().getColor(R.color.Gray900), Typeface.BOLD));

        logoutTextView = binding.fragmentSerialLogoutTextView.componentAuthLink;
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
                    ((AuthActivity) requireActivity()).controlEditText.select(getActivity(), serialEditText);
                }
            }
            return false;
        });

        serialTextView.setOnClickListener(v -> {
            serialTextView.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> serialTextView.setClickable(true), 300);

            if (serialEditText.length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(getActivity(), serialEditText, serialErrorImageView, serialErrorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(getActivity(), serialEditText, serialErrorImageView, serialErrorTextView);
                doWork();
            }
        });

        dashboardTextView.setOnClickListener(v -> {
            dashboardTextView.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> dashboardTextView.setClickable(true), 300);

            IntentManager.main(getActivity());
        });

        logoutTextView.setOnClickListener(v -> {
            logoutTextView.setClickable(false);
            ((AuthActivity) requireActivity()).handler.postDelayed(() -> logoutTextView.setClickable(true), 300);

            // TODO : Place Code Here
        });
    }

    private void setData() {
        if (((AuthActivity) requireActivity()).singleton.getAvatar().equals("")) {
            charTextView.setVisibility(View.VISIBLE);
            if (((AuthActivity) requireActivity()).singleton.getName().equals(""))
                charTextView.setText(StringManager.firstChars(getResources().getString(R.string.AuthToolbar)));
            else
                charTextView.setText(StringManager.firstChars(((AuthActivity) requireActivity()).singleton.getName()));
        } else {
            charTextView.setVisibility(View.GONE);

            Picasso.get().load(((AuthActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Blue500).into(avatarImageView);
        }
    }

    private void doWork() {
        serial = serialEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}