package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentUserBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class UserFragment extends Fragment {

    // Binding
    private FragmentUserBinding binding;

    // Models
    private UserModel userModel;

    // Objects
    private HashMap data, header;

    // Vars
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            if (binding.avatarIncludeLayout.charTextView.getVisibility() == View.GONE)
                IntentManager.display(requireActivity(), binding.nameTextView.getText().toString(), userModel.getAvatar().getMedium().getUrl());
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);

        CustomClickView.onClickListener(() -> {
            switch (binding.menuSpinner.selectImageView.getTag().toString()) {
                case "ویرایش": {
                    NavDirections action = NavigationMainDirections.actionGlobalEditUserFragment(userModel);
                    ((MainActivity) requireActivity()).navController.navigate(action);
                } break;
                case "ورود به کاربری": {
                    // TODO : Place Code Here
                } break;
            }
        }).widget(binding.menuSpinner.selectImageView);

        binding.menuSpinner.selectSpinner.setOnTouchListener((v, event) -> {
            binding.menuSpinner.selectSpinner.setSelection(binding.menuSpinner.selectSpinner.getAdapter().getCount());
            userSelect = true;
            return false;
        });

        binding.menuSpinner.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    switch (pos) {
                        case "ویرایش": {
                            NavDirections action = NavigationMainDirections.actionGlobalEditUserFragment(userModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } break;
                        case "ورود به کاربری": {
                            // TODO : Place Code Here
                        } break;
                    }

                    parent.setSelection(parent.getAdapter().getCount());

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setArgs() {
        userModel = (UserModel) UserFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(userModel);
    }

    private void setData(UserModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }

        if (model.getName() != null && !model.getName().equals("")) {
            binding.nameTextView.setText(model.getName());
        } else {
            binding.nameTextView.setText(getResources().getString(R.string.AppDefaultName));
        }

        if (model.getMobile() != null && !model.getMobile().equals("")) {
            binding.mobileTextView.setText(model.getMobile());
            binding.mobileGroup.setVisibility(View.VISIBLE);
        } else {
            binding.mobileGroup.setVisibility(View.GONE);
        }

        if (model.getEmail() != null && !model.getEmail().equals("")) {
            binding.emailTextView.setText(model.getEmail());
            binding.emailGroup.setVisibility(View.VISIBLE);
        } else {
            binding.emailGroup.setVisibility(View.GONE);
        }

        if (model.getAvatar() != null && model.getAvatar().getMedium() != null && model.getAvatar().getMedium().getUrl() != null && !model.getAvatar().getMedium().getUrl().equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(model.getAvatar().getMedium().getUrl()).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }

        setDropdown(model);
    }

    private void setDropdown(UserModel model) {
        ArrayList<String> items = new ArrayList<>();

        items.add(requireActivity().getResources().getString(R.string.UserFragmentEdit));
//        items.add(requireActivity().getResources().getString(R.string.UserFragmentEnter));
        items.add("");

        if (items.size() > 2) {
            InitManager.imgResTintBackground(requireActivity(), binding.menuSpinner.selectImageView, R.drawable.ic_ellipsis_v_light, R.color.Gray500, R.drawable.draw_oval_solid_transparent_border_1sdp_gray300);
            InitManager.actionCustomSpinner(requireActivity(), binding.menuSpinner.selectSpinner, items);
        } else if (items.size() == 2) {
            switch (items.get(0)) {
                case "ویرایش":
                    InitManager.imgResTintBackgroundTag(requireActivity(), binding.menuSpinner.selectImageView, R.drawable.ic_edit_light, R.color.Gray500, R.drawable.draw_oval_solid_white_border_1sdp_gray300_ripple_gray300, items.get(0));
                    break;
                case "ورود به کاربری":
                    InitManager.imgResTintBackgroundTag(requireActivity(), binding.menuSpinner.selectImageView, R.drawable.ic_user_cog_light, R.color.Gray500, R.drawable.draw_oval_solid_white_border_1sdp_gray300_ripple_gray300, items.get(0));
                    break;
            }

            binding.menuSpinner.selectImageView.setPadding((int) getResources().getDimension(R.dimen._8sdp), (int) getResources().getDimension(R.dimen._8sdp), (int) getResources().getDimension(R.dimen._8sdp), (int) getResources().getDimension(R.dimen._8sdp));
            binding.menuSpinner.selectSpinner.setVisibility(View.GONE);
        } else {
            binding.menuSpinner.getRoot().setVisibility(View.GONE);
        }
    }

    private void getData() {
        User.show(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                userModel = (UserModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        setData(userModel);
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
        userSelect = false;
    }

}