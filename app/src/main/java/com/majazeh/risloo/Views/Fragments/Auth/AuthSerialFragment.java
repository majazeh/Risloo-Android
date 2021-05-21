package com.majazeh.risloo.Views.Fragments.Auth;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentAuthSerialBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AuthSerialFragment extends Fragment {

    // Binding
    private FragmentAuthSerialBinding binding;

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
        binding.titleTextView.getRoot().setText(getResources().getString(R.string.SerialFragmentTitle));

        binding.serialEditText.getRoot().setHint(getResources().getString(R.string.SerialFragmentInput));

        binding.guideIncludeLayout.guideTextView.setHint(getResources().getString(R.string.SerialFragmentGuide));

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
                ((AuthActivity) requireActivity()).controlEditText.error(requireActivity(), binding.serialEditText.getRoot(), binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(requireActivity(), binding.serialEditText.getRoot(), binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView);
                doWork();
            }
        }).widget(binding.buttonTextView.getRoot());

        ClickManager.onClickListener(() -> IntentManager.main(requireActivity())).widget(binding.dashboardLinkTextView.getRoot());

        ClickManager.onClickListener(() -> {
            ((AuthActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

            HashMap header = new HashMap<>();
            header.put("Authorization", ((AuthActivity) requireActivity()).singleton.getAuthorization());

            Auth.logout(new HashMap<>(), header, new Response() {
                @Override
                public void onOK(Object object) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            ((AuthActivity) requireActivity()).singleton.logOut();

                            ((AuthActivity) requireActivity()).loadingDialog.dismiss();
                            ((AuthActivity) requireActivity()).navigator(R.id.authLoginFragment, null);
                        });
                    }
                }

                @Override
                public void onFailure(String response) {
                    // Place Code if Needed
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
            if (((AuthActivity) requireActivity()).singleton.getName().equals("")) {
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(getResources().getString(R.string.AppDefaultName)));
            } else {
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(((AuthActivity) requireActivity()).singleton.getName()));
            }
        }
    }

    private void doWork() {
//        serial = binding.serialEditText.getRoot().getText().toString().trim();
//
//        ((AuthActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");
//
//        HashMap data = new HashMap();
//        data.put("authorized_key", serial);
//
//        Auth.auth(data, new HashMap<>(), new Response() {
//            @Override
//            public void onOK(Object object) {
//                AuthModel model = (AuthModel) object;
//                if (((AuthModel) object).getUser() == null) {
//                    Bundle extras = new Bundle();
//
//                    extras.putString("key", model.getKey());
//                    extras.putString("callback", model.getCallback());
//
//                    switch (model.getTheory()) {
//                        case "password":
//                            requireActivity().runOnUiThread(() -> {
//                                ((AuthActivity) requireActivity()).loadingDialog.dismiss();
//                                ((AuthActivity) requireActivity()).navigator(R.id.authPasswordFragment, extras);
//                            });
//                            break;
//                    }
//                } else {
//                    requireActivity().runOnUiThread(() -> {
//                        ((AuthActivity) requireActivity()).loadingDialog.dismiss();
//                        Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppAuthenticated), Toast.LENGTH_SHORT).show();
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(String response) {
//                // Place Code if Needed
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}