package com.majazeh.risloo.Views.Fragments.Auth;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
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
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.databinding.FragmentSerialBinding;
import com.squareup.picasso.Picasso;

public class SerialFragment extends Fragment {

    // Binding
    private FragmentSerialBinding binding;

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
        binding.serialIncludeLayout.inputEditText.setHint(getResources().getString(R.string.SerialFragmentInput));

        binding.serialTextView.getRoot().setText(getResources().getString(R.string.SerialFragmentButton));

        binding.dashboardTextView.getRoot().setText(StringManager.foregroundStyle(getResources().getString(R.string.AuthDashboard), 0, 8, getResources().getColor(R.color.Gray900), Typeface.BOLD));
        binding.logoutTextView.getRoot().setText(getResources().getString(R.string.AuthLogout));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.serialTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.serialIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.serialIncludeLayout.inputEditText.hasFocus()) {
                    ((AuthActivity) requireActivity()).controlEditText.select(requireActivity(), binding.serialIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ((AuthActivity) requireActivity()).onClickListener(() -> {
            if (binding.serialIncludeLayout.inputEditText.length() == 0) {
                ((AuthActivity) requireActivity()).controlEditText.error(requireActivity(), binding.serialIncludeLayout.inputEditText, binding.serialIncludeLayout.errorImageView, binding.serialIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((AuthActivity) requireActivity()).controlEditText.check(requireActivity(), binding.serialIncludeLayout.inputEditText, binding.serialIncludeLayout.errorImageView, binding.serialIncludeLayout.errorTextView);
                doWork();
            }
        }).widget(binding.serialTextView.getRoot());

        ((AuthActivity) requireActivity()).onClickListener(() -> IntentManager.main(requireActivity())).widget(binding.dashboardTextView.getRoot());

        ((AuthActivity) requireActivity()).onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.logoutTextView.getRoot());
    }

    private void setData() {
        if (((AuthActivity) requireActivity()).singleton.getAvatar().equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            if (((AuthActivity) requireActivity()).singleton.getName().equals(""))
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(getResources().getString(R.string.AuthToolbar)));
            else
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(((AuthActivity) requireActivity()).singleton.getName()));
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);

            Picasso.get().load(((AuthActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Blue500).into(binding.avatarIncludeLayout.avatarImageView);
        }
    }

    private void doWork() {
        serial = binding.serialIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}