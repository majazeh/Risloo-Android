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
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Managers.BitmapManager;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.FileManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.ResultManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SelectedAdapter;
import com.majazeh.risloo.Views.BottomSheets.ImageBottomSheet;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;
import com.majazeh.risloo.Views.Dialogs.SelectedDialog;
import com.majazeh.risloo.databinding.FragmentCreateCenterBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
    private Bitmap avatarBitmap = null;

    // Vars
    private HashMap data, header;
    public String type = "personal_clinic", managerId = "", managerName = "", title = "", address = "", description = "";
    public String avatarPath = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateCenterBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setExtra();

        return binding.getRoot();
    }

    private void initializer() {
        phonesAdapter = new SelectedAdapter(requireActivity());

        managersDialog = new SearchableDialog();
        phonesDialog = new SelectedDialog();

        imageBottomSheet = new ImageBottomSheet();

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

        binding.addressIncludeLayout.inputEditText.setHint(getResources().getString(R.string.CreateCenterFragmentAddressHint));
        binding.descriptionIncludeLayout.inputEditText.setHint(getResources().getString(R.string.CreateCenterFragmentDescriptionHint));

        binding.typeIncludeLayout.firstRadioButton.setText(getResources().getString(R.string.CreateCenterFragmentPersonalClinic));
        binding.typeIncludeLayout.secondRadioButton.setText(getResources().getString(R.string.CreateCenterFragmentCounselingCenter));

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

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

        ClickManager.onDelayedClickListener(() -> {
            managersDialog.show(requireActivity().getSupportFragmentManager(), "managersDialog");
            managersDialog.setData("managers");
        }).widget(binding.managerIncludeLayout.selectTextView);

        binding.titleIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.titleIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.titleIncludeLayout.inputEditText);
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
            if (managerId.equals(""))
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.managerIncludeLayout.selectTextView, binding.managerErrorLayout.getRoot(), binding.managerErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.managerIncludeLayout.selectTextView, binding.managerErrorLayout.getRoot(), binding.managerErrorLayout.errorTextView);
            if (binding.phonesIncludeLayout.selectRecyclerView.getChildCount() == 0)
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, binding.phonesErrorLayout.getRoot(), binding.phonesErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, binding.phonesErrorLayout.getRoot(), binding.phonesErrorLayout.errorTextView);

            if (type.equals("counseling_center")) {
                if (binding.titleIncludeLayout.inputEditText.length() == 0)
                    ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.titleIncludeLayout.inputEditText, binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
                else
                    ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.titleIncludeLayout.inputEditText, binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView);
            }

            if (type.equals("personal_clinic")) {
                if (!managerId.equals("") && binding.phonesIncludeLayout.selectRecyclerView.getChildCount() != 0)
                    doWork();
            } else {
                if (!managerId.equals("") && binding.titleIncludeLayout.inputEditText.length() != 0 && binding.phonesIncludeLayout.selectRecyclerView.getChildCount() != 0)
                    doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setExtra() {
        if (getArguments() != null) {
            if (getArguments().getString("type") != null && !getArguments().getString("type").equals("")) {
                type = getArguments().getString("type");
                switch (type) {
                    case "personal_clinic":
                        binding.typeIncludeLayout.firstRadioButton.setChecked(true);

                        binding.counselingCenterGroup.setVisibility(View.GONE);
                        break;
                    case "counseling_center":
                        binding.typeIncludeLayout.secondRadioButton.setChecked(true);

                        binding.counselingCenterGroup.setVisibility(View.VISIBLE);
                        break;
                }
            }

            if (getArguments().getString("manager_id") != null && !getArguments().getString("manager_id").equals("") && getArguments().getString("manager_name") != null && !getArguments().getString("manager_name").equals("")) {
                managerId = getArguments().getString("manager_id");
                managerName = getArguments().getString("manager_name");
                binding.managerIncludeLayout.selectTextView.setText(managerName);
            }

            if (getArguments().getString("title") != null && !getArguments().getString("title").equals("")) {
                title = getArguments().getString("title");
                binding.titleIncludeLayout.inputEditText.setText(title);
            }

            if (getArguments().getString("address") != null && !getArguments().getString("address").equals("")) {
                address = getArguments().getString("address");
                binding.addressIncludeLayout.inputEditText.setText(address);
            }

            if (getArguments().getString("description") != null && !getArguments().getString("description").equals("")) {
                description = getArguments().getString("description");
                binding.descriptionIncludeLayout.inputEditText.setText(description);
            }

            if (getArguments().getString("avatar") != null && !getArguments().getString("avatar").equals("")) {
                avatarPath = getArguments().getString("avatar");
                Picasso.get().load(avatarPath).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.selectCircleImageView);
            }

            if (getArguments().getString("phone_numbers") != null && !getArguments().getString("phone_numbers").equals("")) {
                try {
                    JSONArray phoneNumbers = new JSONArray(getArguments().getString("phone_numbers"));

                    ArrayList<TypeModel> phones = new ArrayList<>();
                    ArrayList<String> ids = new ArrayList<>();

                    for (int i = 0; i < phoneNumbers.length(); i++) {
                        TypeModel model = new TypeModel(new JSONObject().put("id", phoneNumbers.getString(i)).put("title", phoneNumbers.getString(i)));

                        phones.add(model);
                        ids.add(model.object.getString("id"));
                    }

                    setRecyclerView(phones, ids, "phones");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                setRecyclerView(new ArrayList<>(), new ArrayList<>(), "phones");
            }
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
            case "managers":
                UserModel model = (UserModel) item;

                if (!managerId.equals(model.getId())) {
                    managerId = model.getId();
                    managerName = model.getName();

                    binding.managerIncludeLayout.selectTextView.setText(managerName);
                } else if (managerId.equals(model.getId())) {
                    managerId = "";
                    managerName = "";

                    binding.managerIncludeLayout.selectTextView.setText("");
                }

                managersDialog.dismiss();
                break;
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

        title = binding.titleIncludeLayout.inputEditText.getText().toString().trim();
        address = binding.addressIncludeLayout.inputEditText.getText().toString().trim();
        description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();

        data.put("type", type);
        data.put("manager_id", managerId);
        data.put("address", address);
        data.put("description", description);
        data.put("phone_numbers", phonesAdapter.getIds());

        if (type.equals("counseling_center")) {
            data.put("title", title);

            if (avatarBitmap != null) {
                FileManager.writeBitmapToCache(requireActivity(), BitmapManager.modifyOrientation(avatarBitmap, avatarPath), "image");
                if (FileManager.readFileFromCache(requireActivity(), "image") != null) {
                    data.put("avatar", FileManager.readFileFromCache(requireActivity(), "image"));
                }
            }
        }

        Center.create(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
                        Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppAdded), Toast.LENGTH_SHORT).show();
                        ((MainActivity) requireActivity()).navigator(R.id.centersFragment);
                    });

                    if (FileManager.readFileFromCache(requireActivity(), "image") != null) {
                        FileManager.deleteFileFromCache(requireActivity(), "image");
                    }
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.isNull("errors")) {
                                Iterator<String> keys = (jsonObject.getJSONObject("errors").keys());

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    for (int i = 0; i < jsonObject.getJSONObject("errors").getJSONArray(key).length(); i++) {
                                        switch (key) {
                                            case "manager_id":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.managerIncludeLayout.selectTextView, binding.managerErrorLayout.getRoot(), binding.managerErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "title":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.titleIncludeLayout.inputEditText, binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "address":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.addressIncludeLayout.inputEditText, binding.addressErrorLayout.getRoot(), binding.addressErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "description":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.descriptionIncludeLayout.inputEditText, binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "avatar":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), (LinearLayout) null, binding.avatarErrorLayout.getRoot(), binding.avatarErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "phone_numbers":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, binding.phonesErrorLayout.getRoot(), binding.phonesErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                        }
                                    }
                                }
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