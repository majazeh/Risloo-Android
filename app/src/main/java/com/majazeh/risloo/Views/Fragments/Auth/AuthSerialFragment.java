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
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.majazeh.risloo.NavigationAuthDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentAuthSerialBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AuthSerialFragment extends Fragment {

    // Binding
    private FragmentAuthSerialBinding binding;

    // Objects
    private HashMap data, header;

    // Vars
    private String serial = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthSerialBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((AuthActivity) requireActivity()).singleton.getAuthorization());

        binding.titleTextView.getRoot().setText(getResources().getString(R.string.SerialFragmentTitle));
        binding.serialEditText.getRoot().setHint(getResources().getString(R.string.SerialFragmentInput));
        binding.guideIncludeLayout.guideTextView.setText(getResources().getString(R.string.SerialFragmentGuide));
        binding.buttonTextView.getRoot().setText(getResources().getString(R.string.SerialFragmentButton));

        binding.dashboardLinkTextView.getRoot().setText(StringManager.foreground(getResources().getString(R.string.AuthDashboardLink), 0, 8, getResources().getColor(R.color.Gray800)));
        binding.dashboardLinkTextView.getRoot().setTextAppearance(requireActivity(), R.style.danaDemiBoldTextStyle);
        binding.logoutLinkTextView.getRoot().setText(getResources().getString(R.string.AuthLogoutLink));

        binding.illuImageView.getRoot().setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.illu_003, null));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.buttonTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            if (binding.avatarIncludeLayout.charTextView.getVisibility() == View.GONE)
                IntentManager.display(requireActivity(), ((AuthActivity) requireActivity()).singleton.getName(), ((AuthActivity) requireActivity()).singleton.getAvatar());
        }).widget(binding.avatarIncludeLayout.avatarImageView);

        binding.serialEditText.getRoot().setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.serialEditText.getRoot().hasFocus())
                ((AuthActivity) requireActivity()).inputor.select(requireActivity(), binding.serialEditText.getRoot());
            return false;
        });

        binding.serialEditText.getRoot().setOnFocusChangeListener((v, hasFocus) -> {
            serial = binding.serialEditText.getRoot().getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.serialEditText.getRoot().length() == 0) {
                ((AuthActivity) requireActivity()).validatoon.showValid(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).validatoon.hideValid(binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView);
                doWork();
            }
        }).widget(binding.buttonTextView.getRoot());

        CustomClickView.onClickListener(() -> IntentManager.main(requireActivity())).widget(binding.dashboardLinkTextView.getRoot());

        CustomClickView.onClickListener(() -> {
            DialogManager.showLoadingDialog(requireActivity(), "loading");

            Auth.logout(new HashMap<>(), header, new Response() {
                @Override
                public void onOK(Object object) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            NavDirections action = NavigationAuthDirections.actionGlobalAuthLoginFragment();

                            ((AuthActivity) requireActivity()).singleton.logout();
                            ((AuthActivity) requireActivity()).navController.navigate(action);

                            DialogManager.dismissLoadingDialog();
                        });
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            // Place Code if Needed
                        });
                    }
                }
            });
        }).widget(binding.logoutLinkTextView.getRoot());
    }

    private void setData() {
        if (!((AuthActivity) requireActivity()).singleton.getAvatar().equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(((AuthActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Blue500).into(binding.avatarIncludeLayout.avatarImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            if (!((AuthActivity) requireActivity()).singleton.getName().equals(""))
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(((AuthActivity) requireActivity()).singleton.getName()));
            else
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(getResources().getString(R.string.AppDefaultName)));
        }
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "loading");

        data.put("authorized_key", serial);

        // Todo : Place Code Here
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}