package com.majazeh.risloo.Views.Fragments.Auth;

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
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentAuthSerialBinding;
import com.squareup.picasso.Picasso;

public class AuthSerialFragment extends Fragment {

    // Binding
    private FragmentAuthSerialBinding binding;

    // Vars
    private String serial = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthSerialBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        binding.titleTextView.getRoot().setText(getResources().getString(R.string.SerialFragmentTitle));

        binding.serialEditText.getRoot().setHint(getResources().getString(R.string.SerialFragmentInput));

        binding.guideIncludeLayout.guideTextView.setHint(getResources().getString(R.string.SerialFragmentGuide));

        binding.buttonTextView.getRoot().setText(getResources().getString(R.string.SerialFragmentButton));

        binding.dashboardLinkTextView.getRoot().setText(StringManager.foreground(getResources().getString(R.string.AuthDashboardLink), 0, 8, getResources().getColor(R.color.Gray800)));
        binding.dashboardLinkTextView.getRoot().setTextAppearance(requireActivity(), R.style.danaDemiBoldTextStyle);
        binding.logoutLinkTextView.getRoot().setText(getResources().getString(R.string.AuthLogoutLink));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.buttonTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.serialEditText.getRoot().setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.serialEditText.getRoot().hasFocus()) {
                    ((AuthActivity) requireActivity()).controlEditText.select(requireActivity(), binding.serialEditText.getRoot());
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            if (binding.serialEditText.getRoot().length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(requireActivity(), binding.serialEditText.getRoot(), binding.errorIncludeLayout.errorImageView, binding.errorIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(requireActivity(), binding.serialEditText.getRoot(), binding.errorIncludeLayout.errorImageView, binding.errorIncludeLayout.errorTextView);
                doWork();
            }
        }).widget(binding.buttonTextView.getRoot());

        ClickManager.onClickListener(() -> IntentManager.main(requireActivity())).widget(binding.dashboardLinkTextView.getRoot());

        ClickManager.onClickListener(() -> {
            // TODO : call logout method

            ((AuthActivity) requireActivity()).navigator(R.id.authLoginFragment);
        }).widget(binding.logoutLinkTextView.getRoot());
    }

    private void setData() {
        if (!((AuthActivity) requireActivity()).singleton.getAvatar().equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(((AuthActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Blue500).into(binding.avatarIncludeLayout.avatarImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            if (((AuthActivity) requireActivity()).singleton.getName().equals("")) {
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(getResources().getString(R.string.AppDefaultName)));
            } else {
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(((AuthActivity) requireActivity()).singleton.getName()));
            }
        }
    }

    private void doWork() {
        serial = binding.serialEditText.getRoot().getText().toString().trim();

        // TODO : call work method and place mobile as it's input
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}