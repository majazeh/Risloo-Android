package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentReferenceBinding;
import com.squareup.picasso.Picasso;

public class ReferenceFragment extends Fragment {

    // Binding
    private FragmentReferenceBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentReferenceBinding.inflate(inflater, viewGroup, false);

        listener();

        setData();

        return binding.getRoot();
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
    }

    private void setData() {
        if (((MainActivity) requireActivity()).singleton.getName().equals("")) {
            binding.nameTextView.setText(getResources().getString(R.string.MainToolbar));
        } else {
            binding.nameTextView.setText(((MainActivity) requireActivity()).singleton.getName());
        }

        if (((MainActivity) requireActivity()).singleton.getStatus().equals("")) {
            binding.statusTextView.setVisibility(View.GONE);
        } else {
            binding.statusTextView.setText(((MainActivity) requireActivity()).singleton.getStatus());
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