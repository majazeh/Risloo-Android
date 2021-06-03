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
import com.majazeh.risloo.databinding.FragmentUserBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class UserFragment extends Fragment {

    // Binding
    private FragmentUserBinding binding;

    // Objects
    private Bundle extras;

    // Vars
    private HashMap data, header;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setExtra();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        extras = new Bundle();

        data = new HashMap<>();
        data.put("id", "");
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        InitManager.imgResTint(requireActivity(), binding.editImageView.getRoot(), R.drawable.ic_edit_light, R.color.Gray500);
        InitManager.imgResTint(requireActivity(), binding.enterImageView.getRoot(), R.drawable.ic_user_cog_light, R.color.Blue600);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.editImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_gray500_ripple_gray300);
            binding.enterImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_blue600_ripple_blue300);
        } else {
            binding.editImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_gray500);
            binding.enterImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_blue600);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
                IntentManager.display(requireActivity(), "", "", ((MainActivity) requireActivity()).singleton.getAvatar());
            }
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.editUserFragment, extras)).widget(binding.editImageView.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.enterImageView.getRoot());
    }

    private void setExtra() {
        if (getArguments() != null) {
            if (getArguments().getString("id") != null && !getArguments().getString("id").equals("")) {
                extras.putString("id", getArguments().getString("id"));
                data.put("id", getArguments().getString("id"));
            }

            if (getArguments().getString("name") != null && !getArguments().getString("name").equals("")) {
                extras.putString("name", getArguments().getString("name"));
                binding.nameTextView.setText(getArguments().getString("name"));
            } else {
                binding.nameTextView.setText(getResources().getString(R.string.AppDefaultName));
            }

            if (getArguments().getString("username") != null && !getArguments().getString("username").equals("")) {
                extras.putString("username", getArguments().getString("username"));
                binding.usernameTextView.setText(getArguments().getString("username"));
            } else {
                binding.usernameTextView.setVisibility(View.GONE);
            }

            if (getArguments().getString("education") != null && !getArguments().getString("education").equals("")) {
                extras.putString("education", getArguments().getString("education"));
                binding.educationTextView.setText(getArguments().getString("education"));
                binding.educationGroup.setVisibility(View.VISIBLE);
            } else {
                binding.educationGroup.setVisibility(View.GONE);
            }

            if (getArguments().getString("birthday") != null && !getArguments().getString("birthday").equals("")) {
                extras.putString("birthday", getArguments().getString("birthday"));
                binding.birthdayTextView.setText(getArguments().getString("birthday"));
                binding.birthdayGroup.setVisibility(View.VISIBLE);
            } else {
                binding.birthdayGroup.setVisibility(View.GONE);
            }

            if (getArguments().getString("email") != null && !getArguments().getString("email").equals("")) {
                extras.putString("email", getArguments().getString("email"));
                binding.emailTextView.setText(getArguments().getString("email"));
                binding.emailGroup.setVisibility(View.VISIBLE);
            } else {
                binding.emailGroup.setVisibility(View.GONE);
            }

            if (getArguments().getString("mobile") != null && !getArguments().getString("mobile").equals("")) {
                extras.putString("mobile", getArguments().getString("mobile"));
                binding.mobileTextView.setText(getArguments().getString("mobile"));
                binding.mobileGroup.setVisibility(View.VISIBLE);
            } else {
                binding.mobileGroup.setVisibility(View.GONE);
            }

            if (getArguments().getString("status") != null && !getArguments().getString("status").equals("")) {
                extras.putString("status", getArguments().getString("status"));
            }

            if (getArguments().getString("type") != null && !getArguments().getString("type").equals("")) {
                extras.putString("type", getArguments().getString("type"));
            }

            if (getArguments().getString("gender") != null && !getArguments().getString("gender").equals("")) {
                extras.putString("gender", getArguments().getString("gender"));
            }

            if (getArguments().getString("public_key") != null && !getArguments().getString("public_key").equals("")) {
                extras.putString("public_key", getArguments().getString("public_key"));
            }

            if (getArguments().getString("avatar") != null && !getArguments().getString("avatar").equals("")) {
                extras.putString("avatar", getArguments().getString("avatar"));
                binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(getArguments().getString("avatar")).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

                Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
            }
        }
    }

    private void setData(UserModel model) {
        if (model.getName() != null && !model.getName().equals("")) {
            extras.putString("name", model.getName());
            binding.nameTextView.setText(model.getName());
        } else {
            binding.nameTextView.setText(getResources().getString(R.string.AppDefaultName));
        }

        if (model.getUsername() != null && !model.getUsername().equals("")) {
            extras.putString("username", model.getUsername());
            binding.usernameTextView.setText(model.getUsername());
        } else {
            binding.usernameTextView.setVisibility(View.GONE);
        }

//        if (model.getEducation() != null && !model.getEducation().equals("")) {
//            extras.putString("education", model.getEducation());
//            binding.educationTextView.setText(model.getEducation());
//            binding.educationGroup.setVisibility(View.VISIBLE);
//        } else {
            binding.educationGroup.setVisibility(View.GONE);
//        }

        if (model.getBirthday() != null && !model.getBirthday().equals("")) {
            extras.putString("birthday", model.getBirthday());
            binding.birthdayTextView.setText(model.getBirthday());
            binding.birthdayGroup.setVisibility(View.VISIBLE);
        } else {
            binding.birthdayGroup.setVisibility(View.GONE);
        }

        if (model.getEmail() != null && !model.getEmail().equals("")) {
            extras.putString("email", model.getEmail());
            binding.emailTextView.setText(model.getEmail());
            binding.emailGroup.setVisibility(View.VISIBLE);
        } else {
            binding.emailGroup.setVisibility(View.GONE);
        }

        if (model.getMobile() != null && !model.getMobile().equals("")) {
            extras.putString("mobile", model.getMobile());
            binding.mobileTextView.setText(model.getMobile());
            binding.mobileGroup.setVisibility(View.VISIBLE);
        } else {
            binding.mobileGroup.setVisibility(View.GONE);
        }

        if (model.getUserStatus() != null && !model.getUserStatus().equals("")) {
            extras.putString("status", model.getUserStatus());
        }

        if (model.getUserType() != null && !model.getUserType().equals("")) {
            extras.putString("type", model.getUserType());
        }

        if (model.getGender() != null && !model.getGender().equals("")) {
            extras.putString("gender", model.getGender());
        }

        if (model.getPublic_key() != null && !model.getPublic_key().equals("")) {
            extras.putString("public_key", model.getPublic_key());
        }

        if (model.getAvatar() != null && model.getAvatar().getMedium() != null && model.getAvatar().getMedium().getUrl() != null && !model.getAvatar().getMedium().getUrl().equals("")) {
            extras.putString("avatar", model.getAvatar().getMedium().getUrl());
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(model.getAvatar().getMedium().getUrl()).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }
    }

    private void getData() {
        User.show(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        UserModel model = (UserModel) object;

                        setData(model);
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        // TODO : Place Code If Needed
                    });
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}