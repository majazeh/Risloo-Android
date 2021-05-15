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
import com.majazeh.risloo.Utils.Managers.ClickManager;
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
        InitManager.imgResTint(requireActivity(), binding.editImageView.getRoot(), R.drawable.ic_edit_light, R.color.Gray500);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.editImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_gray500_ripple_gray300);
        } else {
            binding.editImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_gray500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
                IntentManager.display(requireActivity(), "", "", ((MainActivity) requireActivity()).singleton.getAvatar());
            }
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.editUserFragment)).widget(binding.editImageView.getRoot());
    }

    private void setData() {
        if (((MainActivity) requireActivity()).singleton.getName().equals("")) {
            binding.nameTextView.setText(getResources().getString(R.string.AppDefaultName));
        } else {
            binding.nameTextView.setText(((MainActivity) requireActivity()).singleton.getName());
        }

        if (((MainActivity) requireActivity()).singleton.getUsername().equals("")) {
            binding.usernameTextView.setVisibility(View.GONE);
        } else {
            binding.usernameTextView.setText(((MainActivity) requireActivity()).singleton.getUsername());
        }

        if (((MainActivity) requireActivity()).singleton.getEducation().equals("")) {
            binding.educationGroup.setVisibility(View.GONE);
        } else {
            binding.educationGroup.setVisibility(View.VISIBLE);
            binding.educationTextView.setText(((MainActivity) requireActivity()).singleton.getEducation());
        }

        if (((MainActivity) requireActivity()).singleton.getBirthday().equals("")) {
            binding.birthdayGroup.setVisibility(View.GONE);
        } else {
            binding.birthdayGroup.setVisibility(View.VISIBLE);
            binding.birthdayTextView.setText(((MainActivity) requireActivity()).singleton.getBirthday());
        }

        if (((MainActivity) requireActivity()).singleton.getEmail().equals("")) {
            binding.emailGroup.setVisibility(View.GONE);
        } else {
            binding.emailGroup.setVisibility(View.VISIBLE);
            binding.emailTextView.setText(((MainActivity) requireActivity()).singleton.getEmail());
        }

        if (((MainActivity) requireActivity()).singleton.getMobile().equals("")) {
            binding.mobileGroup.setVisibility(View.GONE);
        } else {
            binding.mobileGroup.setVisibility(View.VISIBLE);
            binding.mobileTextView.setText(((MainActivity) requireActivity()).singleton.getMobile());
        }

        if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(((MainActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}