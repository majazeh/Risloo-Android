package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.SheetManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Managers.BitmapManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.FileManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.ResultManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Dialog.DialogSelectedAdapter;
import com.majazeh.risloo.databinding.FragmentCreateCenterBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CreateCenterFragment extends Fragment {

    // Binding
    private FragmentCreateCenterBinding binding;

    // Adapters
    public DialogSelectedAdapter phonesAdapter;

    // Objects
    private Bitmap avatarBitmap = null;
    private HashMap data, header;

    // Vars
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
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

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

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateCenterFragmentButton), getResources().getColor(R.color.White), R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
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

        CustomClickView.onDelayedListener(() -> {
            DialogManager.showSearchableDialog(requireActivity(), "managers");
        }).widget(binding.managerIncludeLayout.selectTextView);

        binding.titleIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.titleIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.titleIncludeLayout.inputEditText);
            return false;
        });

        binding.titleIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            title = binding.titleIncludeLayout.inputEditText.getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            SheetManager.showImageBottomSheet(requireActivity());
        }).widget(binding.avatarIncludeLayout.selectCircleImageView);

        binding.addressIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.addressIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.addressIncludeLayout.inputEditText);
            return false;
        });

        binding.addressIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            address = binding.addressIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.phonesIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction())
                DialogManager.showSelectedDialog(requireActivity(), "phones");
            return false;
        });

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.descriptionIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.descriptionIncludeLayout.inputEditText);
            return false;
        });

        binding.descriptionIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView);
            if (binding.managerErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.managerErrorLayout.getRoot(), binding.managerErrorLayout.errorTextView);
            if (binding.titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView);
            if (binding.addressErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.addressErrorLayout.getRoot(), binding.addressErrorLayout.errorTextView);
            if (binding.descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView);
            if (binding.avatarErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.avatarErrorLayout.getRoot(), binding.avatarErrorLayout.errorTextView);
            if (binding.phonesErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.phonesErrorLayout.getRoot(), binding.phonesErrorLayout.errorTextView);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        TypeModel typeModel = CreateCenterFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            // TODO : Place Code When Needed
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

                DialogManager.dismissSearchableDialog();
            } break;
        }
    }

    public void responseAction(String method, Intent data) {
        switch (method) {
            case "gallery":
                ResultManager.galleryResult(requireActivity(), data, binding.avatarIncludeLayout.selectCircleImageView, null);

                avatarPath = ResultManager.filePath;
                avatarBitmap = ResultManager.bitmap;
                break;
            case "camera":
                ResultManager.cameraResult(requireActivity(), avatarPath, binding.avatarIncludeLayout.selectCircleImageView, null);

                avatarPath = ResultManager.filePath;
                avatarBitmap = ResultManager.bitmap;
                break;
        }
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        data.put("type", type);
        data.put("manager_id", managerId);
        data.put("address", address);
        data.put("description", description);
        data.put("phone_numbers", phonesAdapter.getIds());

        if (type.equals("counseling_center")) {
            data.put("title", title);

            if (avatarBitmap != null) {
                FileManager.writeBitmapToCache(requireActivity(), BitmapManager.modifyOrientation(avatarBitmap, avatarPath), "image");
                if (FileManager.readFileFromCache(requireActivity(), "image") != null)
                    data.put("avatar", FileManager.readFileFromCache(requireActivity(), "image"));
            }
        }

        Center.create(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.ToastNewCenterAdded));

                        ((MainActivity) requireActivity()).navController.navigateUp();
                    });

                    if (FileManager.readFileFromCache(requireActivity(), "image") != null)
                        FileManager.deleteFileFromCache(requireActivity(), "image");
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            JSONObject responseObject = new JSONObject(response);
                            if (!responseObject.isNull("errors")) {
                                JSONObject errorsObject = responseObject.getJSONObject("errors");

                                Iterator<String> keys = (errorsObject.keys());
                                StringBuilder errors = new StringBuilder();

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                        String validation = errorsObject.getJSONArray(key).get(i).toString();

                                        switch (key) {
                                            case "type":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView,validation);
                                                break;
                                            case "manager_id":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.managerErrorLayout.getRoot(), binding.managerErrorLayout.errorTextView,validation);
                                                break;
                                            case "title":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView,validation);
                                                break;
                                            case "address":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.addressErrorLayout.getRoot(), binding.addressErrorLayout.errorTextView,validation);
                                                break;
                                            case "description":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView,validation);
                                                break;
                                            case "avatar":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.avatarErrorLayout.getRoot(), binding.avatarErrorLayout.errorTextView,validation);
                                                break;
                                            case "phone_numbers":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.phonesErrorLayout.getRoot(), binding.phonesErrorLayout.errorTextView,validation);
                                                break;
                                        }

                                        errors.append(validation);
                                        errors.append("\n");
                                    }
                                }

                                SnackManager.showErrorSnack(requireActivity(), errors.substring(0, errors.length() - 1));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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