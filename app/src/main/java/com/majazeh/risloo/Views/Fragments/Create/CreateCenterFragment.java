package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Main;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Managers.BitmapManager;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.FileManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.ResultManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SelectedAdapter;
import com.majazeh.risloo.Views.BottomSheets.ImageBottomSheet;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;
import com.majazeh.risloo.Views.Dialogs.SelectedDialog;
import com.majazeh.risloo.databinding.FragmentCreateCenterBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateCenterFragment extends Fragment {

    // Binding
    private FragmentCreateCenterBinding binding;

    // Adapters
    public SelectedAdapter phonesAdapter;

    // Dialogs
    private SearchableDialog managersDialog;
    public SelectedDialog phonesDialog;

    // BottomSheets
    private ImageBottomSheet imageBottomSheet;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager phoneLayoutManager;
    private Bitmap avatarBitmap = null;

    // Vars
    public String center = "personal_clinic", managerId = "", managerName = "", name = "", address = "", description = "";
    public String avatarPath = "";

    HashMap data = new HashMap();
    HashMap header = new HashMap();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateCenterBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        phonesAdapter = new SelectedAdapter(requireActivity());

        managersDialog = new SearchableDialog();
        phonesDialog = new SelectedDialog();

        imageBottomSheet = new ImageBottomSheet();

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", 0, 0, (int) getResources().getDimension(R.dimen._2sdp), 0);

        phoneLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        binding.centerIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentCenterHeader));
        binding.managerIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentManagerHeader));
        binding.nameIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentNameHeader));
        binding.avatarIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentAvatarHeader));
        binding.addressIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentAddressHeader));
        binding.phonesIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentPhonesHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCenterFragmentDescriptionHeader));

        binding.avatarGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateCenterFragmentAvatarGuide));

        binding.addressIncludeLayout.inputEditText.setHint(getResources().getString(R.string.CreateCenterFragmentAddressHint));
        binding.descriptionIncludeLayout.inputEditText.setHint(getResources().getString(R.string.CreateCenterFragmentDescriptionHint));

        binding.centerIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.CreateCenterFragmentCenterPersonal));
        binding.centerIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.CreateCenterFragmentCenterClinic));

        InitManager.unfixedRecyclerView(binding.phonesIncludeLayout.selectRecyclerView, itemDecoration, phoneLayoutManager);

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateCenterFragmentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.centerIncludeLayout.getRoot().setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.first_radioButton:
                    center = "personal_clinic";

                    binding.clinicGroup.setVisibility(View.GONE);
                    break;
                case R.id.second_radioButton:
                    center = "counseling_center";

                    binding.clinicGroup.setVisibility(View.VISIBLE);
                    break;
            }
        });

        ClickManager.onDelayedClickListener(() -> {
            managersDialog.show(requireActivity().getSupportFragmentManager(), "managersDialog");
            managersDialog.setData("managers");
        }).widget(binding.managerIncludeLayout.selectTextView);

        binding.nameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.nameIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.nameIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            imageBottomSheet.show(requireActivity().getSupportFragmentManager(), "imageBottomSheet");
        }).widget(binding.avatarIncludeLayout.selectCircleImageView);

        binding.addressIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.addressIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.addressIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.phonesIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                phonesDialog.show(requireActivity().getSupportFragmentManager(), "phonesDialog");
                phonesDialog.setData("phones");
            }
            return false;
        });

        binding.descriptionIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.descriptionIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.descriptionIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            if (center.equals("personal_clinic")) {
                if (managerId.equals("")) {
                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.managerIncludeLayout.selectTextView, binding.managerErrorLayout.errorImageView, binding.managerErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                }
                if (binding.phonesIncludeLayout.selectRecyclerView.getChildCount() == 0) {
                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, binding.phonesErrorLayout.errorImageView, binding.phonesErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                }

                if (!managerId.equals("") && binding.phonesIncludeLayout.selectRecyclerView.getChildCount() != 0) {
                    ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.managerIncludeLayout.selectTextView, binding.managerErrorLayout.errorImageView, binding.managerErrorLayout.errorTextView);
                    ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, binding.phonesErrorLayout.errorImageView, binding.phonesErrorLayout.errorTextView);

                    doWork();
                }
            } else {
                if (managerId.equals("")) {
                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.managerIncludeLayout.selectTextView, binding.managerErrorLayout.errorImageView, binding.managerErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                }
                if (binding.nameIncludeLayout.inputEditText.length() == 0) {
                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameErrorLayout.errorImageView, binding.nameErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                }
                if (binding.phonesIncludeLayout.selectRecyclerView.getChildCount() == 0) {
                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, binding.phonesErrorLayout.errorImageView, binding.phonesErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                }

                if (!managerId.equals("") && binding.nameIncludeLayout.inputEditText.length() != 0 && binding.phonesIncludeLayout.selectRecyclerView.getChildCount() != 0) {
                    ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.managerIncludeLayout.selectTextView, binding.managerErrorLayout.errorImageView, binding.managerErrorLayout.errorTextView);
                    ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.nameIncludeLayout.inputEditText, binding.nameErrorLayout.errorImageView, binding.nameErrorLayout.errorTextView);
                    ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, binding.phonesErrorLayout.errorImageView, binding.phonesErrorLayout.errorTextView);

                    doWork();
                }
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getName().equals("")) {
//            center = ((MainActivity) requireActivity()).singleton.getType();
            switch (center) {
                case "personal_clinic":
                    binding.centerIncludeLayout.firstRadioButton.setChecked(true);

                    binding.clinicGroup.setVisibility(View.GONE);
                    break;
                case "counseling_center":
                    binding.centerIncludeLayout.secondRadioButton.setChecked(true);

                    binding.clinicGroup.setVisibility(View.VISIBLE);
                    break;
            }
        }

        if (!((MainActivity) requireActivity()).singleton.getManager().equals("")) {
            managerId = ((MainActivity) requireActivity()).singleton.getManager();
            managerName = ((MainActivity) requireActivity()).singleton.getManager();
            binding.managerIncludeLayout.selectTextView.setText(managerName);
        }
        if (!((MainActivity) requireActivity()).singleton.getName().equals("")) {
            name = ((MainActivity) requireActivity()).singleton.getName();
            binding.nameIncludeLayout.inputEditText.setText(name);
        }
        if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
            avatarPath = ((MainActivity) requireActivity()).singleton.getAvatar();
            Picasso.get().load(avatarPath).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.selectCircleImageView);
        }
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            address = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.addressIncludeLayout.inputEditText.setText(address);
        }

//        if (extras.getString("phones") != null) {
//            try {
//                JSONArray jsonArray = new JSONArray(extras.getString("phones"));
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                    TypeModel TypeModel = new TypeModel(jsonObject);
//
//                    phones.add(TypeModel);
//                }
//
//                setRecyclerView(phones, "phones");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
        setRecyclerView(new ArrayList<>(), new ArrayList<>(), "phones");
//        }

        if (!((MainActivity) requireActivity()).singleton.getDescription().equals("")) {
            description = ((MainActivity) requireActivity()).singleton.getDescription();
            binding.descriptionIncludeLayout.inputEditText.setText(description);
        }
    }

    private void setRecyclerView(ArrayList<TypeModel> items, ArrayList<String> ids, String method) {
        if (method.equals("phones")) {
            phonesAdapter.setItems(items, ids, method, binding.phonesIncludeLayout.countTextView);
            binding.phonesIncludeLayout.selectRecyclerView.setAdapter(phonesAdapter);
        }
    }

    public void responseDialog(String method, TypeModel item) {
        try {
            switch (method) {
                case "managers":
                    if (!managerId.equals(((UserModel) item).getUserId())) {
                        managerId = ((UserModel) item).getUserId();
                        managerName = ((UserModel) item).getName();

                        binding.managerIncludeLayout.selectTextView.setText(managerName);
                    } else if (managerId.equals(item.object.get("id").toString())) {
                        managerId = "";
                        managerName = "";

                        binding.managerIncludeLayout.selectTextView.setText("");
                    }

                    managersDialog.dismiss();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void responseAction(String method, Intent data) {
        ResultManager resultManager = new ResultManager();

        switch (method) {
            case "gallery":
                resultManager.galleryResult(requireActivity(), data, binding.avatarIncludeLayout.selectCircleImageView, null);

                avatarPath = resultManager.path;
                avatarBitmap = resultManager.bitmap;
                break;
            case "camera":
                resultManager.cameraResult(requireActivity(), avatarPath, binding.avatarIncludeLayout.selectCircleImageView, null);

                avatarPath = resultManager.path;
                avatarBitmap = resultManager.bitmap;
                break;
        }
    }

    private void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");
        if (center.equals("personal_clinic")) {
            address = binding.addressIncludeLayout.inputEditText.getText().toString().trim();
            description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();
            data.put("type", "personal_clinic");
        } else {
            FileManager.writeBitmapToCache(requireActivity(), BitmapManager.modifyOrientation(avatarBitmap, avatarPath), "image");

            name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();
            address = binding.addressIncludeLayout.inputEditText.getText().toString().trim();
            description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();
            if (FileManager.readFileFromCache(requireActivity(), "image") != null)
                data.put("avatar", FileManager.readFileFromCache(requireActivity(), "image"));
            data.put("type", "counseling_center");
            data.put("title", name);
        }
        data.put("manager_id", managerId);
        data.put("address", address);
        data.put("description", description);
        data.put("phone_numbers", phonesAdapter.getIds());
        header.put("Authorization", "Bearer " + ((MainActivity) requireActivity()).singleton.getToken());
        Center.create(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded())
                    requireActivity().runOnUiThread(() -> {
                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
                        ((MainActivity) requireActivity()).navigator(R.id.centersFragment);
                    });
            }

            @Override
            public void onFailure(String response) {
                System.out.println(response);
            }
        });
        FileManager.deleteFileFromCache(requireActivity(), "image");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}