package com.majazeh.risloo.views.fragments.main.tab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.dialog.DialogSelectedAdapter;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditCenter;
import com.majazeh.risloo.databinding.FragmentEditCenterTabDetailBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FragmentEditCenterTabDetail extends Fragment {

    // Binding
    private FragmentEditCenterTabDetailBinding binding;

    // Adapters
    public DialogSelectedAdapter phonesAdapter;

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

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        phonesAdapter = new DialogSelectedAdapter(requireActivity());

        current = ((ActivityMain) requireActivity()).fragmont.getCurrent();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.managerIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterTabDetailManagerHeader));
        binding.titleIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterTabDetailTitleHeader));
        binding.addressIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterTabDetailAddressHeader));
        binding.phonesIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterTabDetailPhonesHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterTabDetailDescriptionHeader));

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColorBackground(binding.editTextView.getRoot(), getResources().getString(R.string.EditCenterTabDetailButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.phonesIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction())
                DialogManager.showDialogSelected(requireActivity(), "phones");
            return false;
        });

        CustomClickView.onDelayedListener(() -> {
            DialogManager.showDialogSearchable(requireActivity(), "managers");
        }).widget(binding.managerIncludeLayout.selectTextView);

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
            if (binding.managerErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.managerErrorLayout.getRoot(), binding.managerErrorLayout.errorTextView);
            if (binding.titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView);
            if (binding.addressErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.addressErrorLayout.getRoot(), binding.addressErrorLayout.errorTextView);
            if (binding.descriptionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView);
            if (binding.phonesErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.phonesErrorLayout.getRoot(), binding.phonesErrorLayout.errorTextView);

            doWork();
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (current instanceof FragmentEditCenter) {
            CenterModel model;

            if (((FragmentEditCenter) current).centerModel != null)
                model = ((FragmentEditCenter) current).centerModel;
            else
                model = ((FragmentEditCenter) current).roomModel.getCenter();

            try {
                if (model.getId() != null && !model.getId().equals("")) {
                    data.put("id", model.getId());
                }

                if (model.getType() != null && !model.getType().equals("")) {
                    type = model.getType();
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

                DialogManager.dismissDialogSearchable();
            } break;
        }
    }

    private void setHashmap() {
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
        }

    }

    private void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap();

        Center.edit(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissDialogLoading();
                        SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackChangesSaved));
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
                                StringBuilder allErrors = new StringBuilder();

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    StringBuilder keyErrors = new StringBuilder();

                                    for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                        String error = errorsObject.getJSONArray(key).getString(i);

                                        keyErrors.append(error);
                                        keyErrors.append("\n");

                                        allErrors.append(error);
                                        allErrors.append("\n");
                                    }

                                    switch (key) {
                                        case "manager_id":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.managerErrorLayout.getRoot(), binding.managerErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "title":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "address":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.addressErrorLayout.getRoot(), binding.addressErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "description":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.descriptionErrorLayout.getRoot(), binding.descriptionErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                        case "phone_numbers":
                                            ((ActivityMain) requireActivity()).validatoon.showValid(binding.phonesErrorLayout.getRoot(), binding.phonesErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                            break;
                                    }
                                }

                                SnackManager.showSnackError(requireActivity(), allErrors.substring(0, allErrors.length() - 1));
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