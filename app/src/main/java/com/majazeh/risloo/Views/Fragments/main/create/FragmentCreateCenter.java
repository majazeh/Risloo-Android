package com.majazeh.risloo.views.fragments.main.create;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.databinding.FragmentCreateCenterBinding;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.ResultManager;
import com.majazeh.risloo.utils.managers.SheetManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.dialog.DialogSelectedAdapter;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class FragmentCreateCenter extends Fragment {

    // Binding
    private FragmentCreateCenterBinding binding;

    // Adapters
    public DialogSelectedAdapter phonesAdapter;

    // Objects
    private HashMap data, header;

    // Vars
    private File avatarFile = null;
    public String type = "personal_clinic", managerId = "", title = "", address = "", description = "", avatarPath = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateCenterBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        phonesAdapter = new DialogSelectedAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.typeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentTypeHeader));
        binding.managerIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentManagerHeader));
        binding.titleIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentTitleHeader));
        binding.avatarIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentAvatarHeader));
        binding.addressIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentAddressHeader));
        binding.phonesIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentPhonesHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentDescriptionHeader));

        binding.avatarGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateCenterFragmentAvatarGuide));

        binding.typeIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.CreateCenterFragmentPersonalClinic));
        binding.typeIncludeLayout.firstRadioButton.setChecked(true);
        binding.typeIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.CreateCenterFragmentCounselingCenter));

        InitManager.rcvVerticalUnfixed(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateCenterFragmentButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.typeIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    type = "personal_clinic";

                    binding.counselingCenterGroup.setVisibility(View.GONE);
                    break;
                case R.id.second_radioButton:
                    type = "counseling_center";

                    binding.counselingCenterGroup.setVisibility(View.VISIBLE);
                    break;
            }
        });

        binding.phonesIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction())
                DialogManager.showDialogSelected(requireActivity(), "phones");
            return false;
        });

        CustomClickView.onDelayedListener(() -> {
            DialogManager.showDialogSearchable(requireActivity(), "managers");
        }).widget(binding.managerIncludeLayout.selectTextView);

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showSheetImage(requireActivity());
        }).widget(binding.avatarIncludeLayout.selectCircleImageView);

        binding.titleIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.titleIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.titleIncludeLayout.inputEditText);
            return false;
        });

        binding.addressIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.addressIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.addressIncludeLayout.inputEditText);
            return false;
        });

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.descriptionIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.descriptionIncludeLayout.inputEditText);
            return false;
        });

        binding.titleIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            title = binding.titleIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.addressIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            address = binding.addressIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.descriptionIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            ((ActivityMain) requireActivity()).validatoon.hideValid(binding);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        TypeModel typeModel = FragmentCreateCenterArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            // TODO : Place Code If Needed
        } else {
            setRecyclerView(new ArrayList<>(), new ArrayList<>(), "phones");
        }
    }

    private void setRecyclerView(ArrayList<TypeModel> items, ArrayList<String> ids, String method) {
        if (method.equals("phones")) {
            phonesAdapter.setItems(items, ids, method, binding.phonesIncludeLayout.countTextView);
            binding.phonesIncludeLayout.selectRecyclerView.setAdapter(phonesAdapter);
        }
    }

    public void responseDialog(String method, TypeModel item) {
        switch (method) {
            case "managers": {
                UserModel model = (UserModel) item;

                if (!managerId.equals(model.getId())) {
                    managerId = model.getId();

                    binding.managerIncludeLayout.selectTextView.setText(model.getName());
                } else if (managerId.equals(model.getId())) {
                    managerId = "";

                    binding.managerIncludeLayout.selectTextView.setText("");
                }

                DialogManager.dismissDialogSearchable();
            } break;
        }
    }

    public void responseAction(String method, Intent data) {
        switch (method) {
            case "gallery":
                ResultManager.resultGallery(requireActivity(), data);
                break;
            case "camera":
                ResultManager.resultCamera(requireActivity(), avatarPath);
                break;
            case "crop":
                ResultManager.resultCrop(requireActivity(), data, binding.avatarIncludeLayout.selectCircleImageView, null);

                avatarFile = ResultManager.file;
                avatarPath = ResultManager.path;
                break;
        }
    }

    private void setHashmap() {
        if (!type.equals(""))
            data.put("type", type);
        else
            data.remove("type");

        if (!managerId.equals(""))
            data.put("manager_id", managerId);
        else
            data.remove("manager_id");

        if (!address.equals(""))
            data.put("address", address);
        else
            data.remove("address");

        if (!description.equals(""))
            data.put("description", description);
        else
            data.remove("description");

        if (!phonesAdapter.getIds().isEmpty())
            data.put("phone_numbers", phonesAdapter.getIds());
        else
            data.remove("phone_numbers");

        if (type.equals("counseling_center")) {
            if (!title.equals(""))
                data.put("title", title);
            else
                data.remove("title");

            if (avatarFile != null)
                data.put("avatar", avatarFile);
            else
                data.remove("avatar");
        }

    }

    private void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap();

        Center.create(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                CenterModel centerModel = (CenterModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissDialogLoading();
                        SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackCreatedNewCenter));

                        ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentCenter(centerModel);
                    });

                    if (avatarFile != null)
                        avatarFile.delete();
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> ((ActivityMain) requireActivity()).validatoon.showValid(response, binding));
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

        if (avatarFile != null)
            avatarFile.delete();
    }

}