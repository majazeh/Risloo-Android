package com.majazeh.risloo.Views.Fragments.Tab;

import android.annotation.SuppressLint;
import android.os.Build;
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
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.databinding.FragmentEditCenterTabDetailBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SelectedAdapter;
import com.majazeh.risloo.Views.Dialogs.SelectedDialog;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterFragment;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class EditCenterTabDetailFragment extends Fragment {

    // Binding
    private FragmentEditCenterTabDetailBinding binding;

    // Dialogs
    public SelectedDialog phonesDialog;

    // Adapters
    public SelectedAdapter phonesAdapter;

    // Fragments
    private Fragment current;

    // Objects
    private HashMap data, header;

    // Vars
    public String type = "", managerId = "", title = "", address = "", description = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditCenterTabDetailBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        phonesDialog = new SelectedDialog();

        phonesAdapter = new SelectedAdapter(requireActivity());

        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.managerIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterTabDetailManagerHeader));
        binding.titleIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterTabDetailTitleHeader));
        binding.addressIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterTabDetailAddressHeader));
        binding.phonesIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterTabDetailPhonesHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterTabDetailDescriptionHeader));

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditCenterTabDetailButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
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

        binding.addressIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.addressIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.addressIncludeLayout.inputEditText);
            return false;
        });

        binding.addressIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            address = binding.addressIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.phonesIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                phonesDialog.show(requireActivity().getSupportFragmentManager(), "phonesDialog");
                phonesDialog.setData("phones");
            }
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
            if (binding.managerErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.managerErrorLayout.getRoot(), binding.managerErrorLayout.errorTextView);
            if (binding.titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView);
            if (binding.addressErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.addressErrorLayout.getRoot(), binding.addressErrorLayout.errorTextView);
            if (binding.descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView);
            if (binding.phonesErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.phonesErrorLayout.getRoot(), binding.phonesErrorLayout.errorTextView);

            doWork();
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (current instanceof EditCenterFragment) {
            CenterModel model = ((EditCenterFragment) current).centerModel;

            try {
                if (model.getCenterId() != null && !model.getCenterId().equals("")) {
                    data.put("id", model.getCenterId());
                }

                if (model.getCenterType() != null && !model.getCenterType().equals("")) {
                    type = model.getCenterType();
                    switch (type) {
                        case "personal_clinic":
                            binding.counselingCenterGroup.setVisibility(View.GONE);
                            break;
                        case "counseling_center":
                            binding.counselingCenterGroup.setVisibility(View.VISIBLE);
                            break;
                    }
                }

                if (model.getManager().getId() != null && !model.getManager().getId().equals("")) {
                    managerId = model.getManager().getUserId();
                }

                if (model.getManager().getName() != null && !model.getManager().getName().equals("")) {
                    binding.managerIncludeLayout.selectTextView.setText(model.getManager().getName());
                }

                if (model.getDetail().has("title") && !model.getDetail().isNull("title") && !model.getDetail().getString("title").equals("")) {
                    title = model.getDetail().getString("title");
                    binding.titleIncludeLayout.inputEditText.setText(title);
                }

                if (model.getDetail().has("address") && !model.getDetail().isNull("address") && !model.getDetail().getString("address").equals("")) {
                    address = model.getDetail().getString("address");
                    binding.addressIncludeLayout.inputEditText.setText(address);
                }

                if (model.getDetail().has("description") && !model.getDetail().isNull("description") && !model.getDetail().getString("description").equals("")) {
                    description = model.getDetail().getString("description");
                    binding.descriptionIncludeLayout.inputEditText.setText(description);
                }

                if (model.getDetail().has("phone_numbers") && !model.getDetail().isNull("phone_numbers") && model.getDetail().getJSONArray("phone_numbers").length() != 0) {
                    JSONArray phones = model.getDetail().getJSONArray("phone_numbers");

                    ArrayList<TypeModel> models = new ArrayList<>();
                    ArrayList<String> ids = new ArrayList<>();

                    for (int i = 0; i < phones.length(); i++) {
                        TypeModel typeModel = new TypeModel(new JSONObject().put("id", phones.getString(i)));

                        models.add(typeModel);
                        ids.add(typeModel.object.getString("id"));
                    }

                    setRecyclerView(models, ids, "phones");
                } else {
                    setRecyclerView(new ArrayList<>(), new ArrayList<>(), "phones");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity());

        data.put("manager_id", managerId);
        data.put("address", address);
        data.put("description", description);
        data.put("phone_numbers", phonesAdapter.getIds());

        if (type.equals("counseling_center"))
            data.put("title", title);

        Center.edit(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.ToastChangesSaved));
                    });
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