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
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentAuthPasswordRecoverBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.TypeModel.AuthModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class AuthPasswordRecoverFragment extends Fragment {

    // Binding
    private FragmentAuthPasswordRecoverBinding binding;

    // Vars
    private String mobile = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthPasswordRecoverBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        return binding.getRoot();
    }

    private void initializer() {
        binding.titleTextView.getRoot().setText(getResources().getString(R.string.PasswordRecoverFragmentTitle));

        binding.mobileEditText.getRoot().setHint(getResources().getString(R.string.PasswordRecoverFragmentInput));

        binding.guideIncludeLayout.guideTextView.setHint(getResources().getString(R.string.PasswordRecoverFragmentGuide));

        binding.buttonTextView.getRoot().setText(getResources().getString(R.string.PasswordRecoverFragmentButton));

        binding.loginHelperTextView.getRoot().setText(getResources().getString(R.string.AuthLoginHelper));
        binding.registerHelperTextView.getRoot().setText(getResources().getString(R.string.AuthRegisterHelper));

        binding.loginLinkTextView.getRoot().setText(getResources().getString(R.string.AuthLoginLink));
        binding.registerLinkTextView.getRoot().setText(getResources().getString(R.string.AuthRegisterLink));

        binding.illuImageView.getRoot().setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.illu_006, null));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.buttonTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.mobileEditText.getRoot().setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.mobileEditText.getRoot().hasFocus()) {
                    ((AuthActivity) requireActivity()).controlEditText.select(requireActivity(), binding.mobileEditText.getRoot());
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            if (binding.mobileEditText.getRoot().length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(requireActivity(), binding.mobileEditText.getRoot(), binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(requireActivity(), binding.mobileEditText.getRoot(), binding.errorIncludeLayout.getRoot(), binding.errorIncludeLayout.errorTextView);
                doWork();
            }
        }).widget(binding.buttonTextView.getRoot());

        ClickManager.onClickListener(() -> ((AuthActivity) requireActivity()).navigator(R.id.authLoginFragment, null)).widget(binding.loginLinkTextView.getRoot());
        ClickManager.onClickListener(() -> ((AuthActivity) requireActivity()).navigator(R.id.authRegisterFragment, null)).widget(binding.registerLinkTextView.getRoot());
    }

    private void doWork() {
        mobile = binding.mobileEditText.getRoot().getText().toString().trim();

        ((AuthActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        HashMap data = new HashMap();
        data.put("mobile", mobile);

        Auth.recovery(data, new HashMap<>(), new Response() {
            @Override
            public void onOK(Object object) {
                AuthModel model = (AuthModel) object;

                Bundle extras = new Bundle();

                extras.putString("mobile", mobile);
                extras.putString("key", model.getKey());
                extras.putString("callback", model.getCallback());

                switch (model.getTheory()) {
                    case "password":
                        requireActivity().runOnUiThread(() -> {
                            ((AuthActivity) requireActivity()).loadingDialog.dismiss();
                            ((AuthActivity) requireActivity()).navigator(R.id.authPasswordFragment, extras);
                        });
                        break;
                    case "mobileCode":
                        requireActivity().runOnUiThread(() -> {
                            ((AuthActivity) requireActivity()).loadingDialog.dismiss();
                            ((AuthActivity) requireActivity()).navigator(R.id.authPinFragment, extras);
                        });
                        break;
                    case "recovery":
                        requireActivity().runOnUiThread(() -> {
                            ((AuthActivity) requireActivity()).loadingDialog.dismiss();
                            ((AuthActivity) requireActivity()).navigator(R.id.authPasswordChangeFragment, extras);
                        });
                        break;
                }
            }

            @Override
            public void onFailure(String response) {
                requireActivity().runOnUiThread(() -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (!jsonObject.isNull("errors")) {
                            Iterator<String> keys = (jsonObject.getJSONObject("errors").keys());
                            while (keys.hasNext()) {
                                String key = keys.next();
                                for (int i = 0; i < jsonObject.getJSONObject("errors").getJSONArray(key).length(); i++)
                                    Toast.makeText(requireContext(), (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}