package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentUserBinding;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserFragment extends Fragment {

    // Binding
    private FragmentUserBinding binding;

    // Widgets
    private CircleImageView avatarCircleImageView;
    private TextView charTextView;
    private TextView nameTextView, usernameTextView;
    private ImageView badgeImageView;
    private TextView educationTextView, birthdayTextView, emailTextView, mobileTextView;
    private ImageView educationImageView, birthdayImageView, emailImageView, mobileImageView;
    private TextView editTextView;
    private ImageView enterImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        avatarCircleImageView = binding.fragmentUserAvatarCircleImageView.componentAvatar86sdpBorderWhiteCircleImageView;

        charTextView = binding.fragmentUserAvatarCircleImageView.componentAvatar86sdpBorderWhiteTextView;

        nameTextView = binding.fragmentUserNameTextView;
        usernameTextView = binding.fragmentUserUsernameTextView;

        badgeImageView = binding.fragmentUserBadgeImageView;

        educationTextView = binding.fragmentUserEducationTextView;
        birthdayTextView = binding.fragmentUserBirthdayTextView;
        emailTextView = binding.fragmentUserEmailTextView;
        mobileTextView = binding.fragmentUserMobileTextView;

        educationImageView = binding.fragmentUserEducationImageView;
        birthdayImageView = binding.fragmentUserBirthdayImageView;
        emailImageView = binding.fragmentUserEmailImageView;
        mobileImageView = binding.fragmentUserMobileImageView;

        editTextView = binding.fragmentUserEditTextView.componentButtonRectangle28sdp;
        editTextView.setText(getResources().getString(R.string.UserFragmentEdit));
        editTextView.setTextColor(getResources().getColor(R.color.Gray500));

        enterImageView = binding.fragmentUserEnterImageView.componentButtonOval28sdp;
        InitManager.imgResTint(getActivity(), enterImageView, R.drawable.ic_user_cog_light, R.color.Blue600);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            editTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);
            enterImageView.setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_blue600_ripple_blue300);
        } else {
            editTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);
            enterImageView.setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_blue600);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        avatarCircleImageView.setOnClickListener(v -> {
            avatarCircleImageView.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> avatarCircleImageView.setClickable(true), 300);

            if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
                IntentManager.display(getActivity(), "", "", ((MainActivity) requireActivity()).singleton.getAvatar());
            }
        });

        editTextView.setOnClickListener(v -> {
            editTextView.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> editTextView.setClickable(true), 300);

//            ((MainActivity) requireActivity()).navigator(R.id.editUserFragment);
        });

        enterImageView.setOnClickListener(v -> {
            enterImageView.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> enterImageView.setClickable(true), 300);

            // TODO : Call Work Method
        });
    }

    private void setData() {
        if (((MainActivity) requireActivity()).singleton.getName().equals("")) {
            nameTextView.setText(getResources().getString(R.string.MainToolbar));
        } else {
            nameTextView.setText(((MainActivity) requireActivity()).singleton.getName());
        }

        if (((MainActivity) requireActivity()).singleton.getUsername().equals("")) {
            usernameTextView.setVisibility(View.GONE);
        } else {
            usernameTextView.setText(((MainActivity) requireActivity()).singleton.getUsername());
        }

        if (((MainActivity) requireActivity()).singleton.getEducation().equals("")) {
            educationTextView.setVisibility(View.GONE);
            educationImageView.setVisibility(View.GONE);
        } else {
            educationTextView.setText(((MainActivity) requireActivity()).singleton.getEducation());
        }

        if (((MainActivity) requireActivity()).singleton.getBirthday().equals("")) {
            birthdayTextView.setVisibility(View.GONE);
            birthdayImageView.setVisibility(View.GONE);
        } else {
            birthdayTextView.setText(((MainActivity) requireActivity()).singleton.getBirthday());
        }

        if (((MainActivity) requireActivity()).singleton.getEmail().equals("")) {
            emailTextView.setVisibility(View.GONE);
            emailImageView.setVisibility(View.GONE);
        } else {
            emailTextView.setText(((MainActivity) requireActivity()).singleton.getEmail());
        }

        if (((MainActivity) requireActivity()).singleton.getMobile().equals("")) {
            mobileTextView.setVisibility(View.GONE);
            mobileImageView.setVisibility(View.GONE);
        } else {
            mobileTextView.setText(((MainActivity) requireActivity()).singleton.getMobile());
        }

        if (((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
            charTextView.setVisibility(View.VISIBLE);
            charTextView.setText(StringManager.firstChars(nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(avatarCircleImageView);
        } else {
            charTextView.setVisibility(View.GONE);

            Picasso.get().load(((MainActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(avatarCircleImageView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}