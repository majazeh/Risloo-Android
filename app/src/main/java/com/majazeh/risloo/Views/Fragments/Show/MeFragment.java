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
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class MeFragment extends Fragment {

    // Binding
    private FragmentMeBinding binding;

    // Objects
    private Bundle extras;

    // Vars
    private HashMap data, header;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentMeBinding.inflate(inflater, viewGroup, false);

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
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

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

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.editUserFragment, extras)).widget(binding.editImageView.getRoot());
    }

    private void setExtra() {
        if (((MainActivity) requireActivity()).singleton != null) {
            if (!((MainActivity) requireActivity()).singleton.getId().equals("")) {
                extras.putString("id", ((MainActivity) requireActivity()).singleton.getId());
                data.put("id", ((MainActivity) requireActivity()).singleton.getId());
            }

            if (!((MainActivity) requireActivity()).singleton.getName().equals("")) {
                extras.putString("name", ((MainActivity) requireActivity()).singleton.getName());
                binding.nameTextView.setText(((MainActivity) requireActivity()).singleton.getName());
            } else {
                binding.nameTextView.setText(getResources().getString(R.string.AppDefaultName));
            }

            if (!((MainActivity) requireActivity()).singleton.getUsername().equals("")) {
                extras.putString("username", ((MainActivity) requireActivity()).singleton.getUsername());
                binding.usernameTextView.setText(((MainActivity) requireActivity()).singleton.getUsername());
            } else {
                binding.usernameTextView.setVisibility(View.GONE);
            }

            if (!((MainActivity) requireActivity()).singleton.getEducation().equals("")) {
                extras.putString("education", ((MainActivity) requireActivity()).singleton.getEducation());
                binding.educationTextView.setText(((MainActivity) requireActivity()).singleton.getEducation());
                binding.educationGroup.setVisibility(View.VISIBLE);
            } else {
                binding.educationGroup.setVisibility(View.GONE);
            }

            if (!((MainActivity) requireActivity()).singleton.getBirthday().equals("")) {
                extras.putString("birthday", ((MainActivity) requireActivity()).singleton.getBirthday());
                binding.birthdayTextView.setText(((MainActivity) requireActivity()).singleton.getBirthday());
                binding.birthdayGroup.setVisibility(View.VISIBLE);
            } else {
                binding.birthdayGroup.setVisibility(View.GONE);
            }

            if (!((MainActivity) requireActivity()).singleton.getEmail().equals("")) {
                extras.putString("email", ((MainActivity) requireActivity()).singleton.getEmail());
                binding.emailTextView.setText(((MainActivity) requireActivity()).singleton.getEmail());
                binding.emailGroup.setVisibility(View.VISIBLE);
            } else {
                binding.emailGroup.setVisibility(View.GONE);
            }

            if (!((MainActivity) requireActivity()).singleton.getMobile().equals("")) {
                extras.putString("mobile", ((MainActivity) requireActivity()).singleton.getMobile());
                binding.mobileTextView.setText(((MainActivity) requireActivity()).singleton.getMobile());
                binding.mobileGroup.setVisibility(View.VISIBLE);
            } else {
                binding.mobileGroup.setVisibility(View.GONE);
            }

            if (!((MainActivity) requireActivity()).singleton.getStatus().equals("")) {
                extras.putString("status", ((MainActivity) requireActivity()).singleton.getStatus());
            }

            if (!((MainActivity) requireActivity()).singleton.getType().equals("")) {
                extras.putString("type", ((MainActivity) requireActivity()).singleton.getType());
            }

            if (!((MainActivity) requireActivity()).singleton.getGender().equals("")) {
                extras.putString("gender", ((MainActivity) requireActivity()).singleton.getGender());
            }

            if (!((MainActivity) requireActivity()).singleton.getPublicKey().equals("")) {
                extras.putString("public_key", ((MainActivity) requireActivity()).singleton.getPublicKey());
            }

            if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
                extras.putString("avatar", ((MainActivity) requireActivity()).singleton.getAvatar());
                binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(((MainActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
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