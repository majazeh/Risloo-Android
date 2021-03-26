package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentMeBinding;
import com.squareup.picasso.Picasso;

public class MeFragment extends Fragment {

    // Binding
    private FragmentMeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentMeBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.AccountFragmentEdit), getResources().getColor(R.color.Gray500));

        InitManager.imgResTint(requireActivity(), binding.enterImageView.getRoot(), R.drawable.ic_user_cog_light, R.color.Blue600);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);
            binding.enterImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_blue600_ripple_blue300);
        } else {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);
            binding.enterImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_blue600);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.avatarIncludeLayout.avatarCircleImageView.setOnClickListener(v -> {
            binding.avatarIncludeLayout.avatarCircleImageView.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> binding.avatarIncludeLayout.avatarCircleImageView.setClickable(true), 300);

            if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
                IntentManager.display(requireActivity(), "", "", ((MainActivity) requireActivity()).singleton.getAvatar());
            }
        });

        binding.editTextView.getRoot().setOnClickListener(v -> {
            binding.editTextView.getRoot().setClickable(false);

            ((MainActivity) requireActivity()).navigator(R.id.editAccountFragment);
        });

        binding.enterImageView.getRoot().setOnClickListener(v -> {
            binding.enterImageView.getRoot().setClickable(false);

            // TODO : Call Work Method
        });
    }

    private void setData() {
        if (((MainActivity) requireActivity()).singleton.getName().equals("")) {
            binding.nameTextView.setText(getResources().getString(R.string.MainToolbar));
        } else {
            binding.nameTextView.setText(((MainActivity) requireActivity()).singleton.getName());
        }

        if (((MainActivity) requireActivity()).singleton.getUsername().equals("")) {
            binding.usernameTextView.setVisibility(View.GONE);
        } else {
            binding.usernameTextView.setText(((MainActivity) requireActivity()).singleton.getUsername());
        }

        if (((MainActivity) requireActivity()).singleton.getEducation().equals("")) {
            binding.educationTextView.setVisibility(View.GONE);
            binding.educationImageView.setVisibility(View.GONE);
        } else {
            binding.educationTextView.setText(((MainActivity) requireActivity()).singleton.getEducation());
        }

        if (((MainActivity) requireActivity()).singleton.getBirthday().equals("")) {
            binding.birthdayTextView.setVisibility(View.GONE);
            binding.birthdayImageView.setVisibility(View.GONE);
        } else {
            binding.birthdayTextView.setText(((MainActivity) requireActivity()).singleton.getBirthday());
        }

        if (((MainActivity) requireActivity()).singleton.getEmail().equals("")) {
            binding.emailTextView.setVisibility(View.GONE);
            binding.emailImageView.setVisibility(View.GONE);
        } else {
            binding.emailTextView.setText(((MainActivity) requireActivity()).singleton.getEmail());
        }

        if (((MainActivity) requireActivity()).singleton.getMobile().equals("")) {
            binding.mobileTextView.setVisibility(View.GONE);
            binding.mobileImageView.setVisibility(View.GONE);
        } else {
            binding.mobileTextView.setText(((MainActivity) requireActivity()).singleton.getMobile());
        }

        if (((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);

            Picasso.get().load(((MainActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}