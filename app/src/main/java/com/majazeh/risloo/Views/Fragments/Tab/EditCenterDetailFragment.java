package com.majazeh.risloo.Views.Fragments.Tab;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SelectedAdapter;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;
import com.majazeh.risloo.Views.Dialogs.SelectedDialog;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterFragment;
import com.majazeh.risloo.databinding.FragmentEditCenterDetailBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class EditCenterDetailFragment extends Fragment {

    // Binding
    private FragmentEditCenterDetailBinding binding;

    // Adapters
    public SelectedAdapter phonesAdapter;

    // Dialogs
    private SearchableDialog managersDialog;
    public SelectedDialog phonesDialog;

    // Vars
    private HashMap data, header;
    public String type = "", managerId = "", managerName = "", title = "", address = "", description = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditCenterDetailBinding.inflate(inflater, viewGroup, false);

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

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.managerIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterDetailTabManagerHeader));
        binding.titleIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterDetailTabTitleHeader));
        binding.addressIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterDetailTabAddressHeader));
        binding.phonesIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterDetailTabPhonesHeader));
        binding.descriptionIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditCenterDetailTabDescriptionHeader));

        binding.addressIncludeLayout.inputEditText.setHint(getResources().getString(R.string.EditCenterDetailTabAddressHint));
        binding.descriptionIncludeLayout.inputEditText.setHint(getResources().getString(R.string.EditCenterDetailTabDescriptionHint));

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.phonesIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditCenterDetailTabButton), getResources().getColor(R.color.White));
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

            if (!managerId.equals("") && binding.phonesIncludeLayout.selectRecyclerView.getChildCount() != 0) {
                doWork();
            }
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        Fragment current = ((MainActivity) requireActivity()).fragmont.getCurrent();

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
                    managerName = model.getManager().getName();
                    binding.managerIncludeLayout.selectTextView.setText(managerName);
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
                    JSONArray phoneNumbers = model.getDetail().getJSONArray("phone_numbers");

                    ArrayList<TypeModel> phones = new ArrayList<>();
                    ArrayList<String> ids = new ArrayList<>();

                    for (int i = 0; i < phoneNumbers.length(); i++) {
                        TypeModel typeModel = new TypeModel(new JSONObject().put("id", phoneNumbers.getString(i)).put("title", phoneNumbers.getString(i)));

                        phones.add(typeModel);
                        ids.add(typeModel.object.getString("id"));
                    }

                    setRecyclerView(phones, ids, "phones");
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

    private void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        title = binding.titleIncludeLayout.inputEditText.getText().toString().trim();
        address = binding.addressIncludeLayout.inputEditText.getText().toString().trim();
        description = binding.descriptionIncludeLayout.inputEditText.getText().toString().trim();

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
                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
                        Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppChanged), Toast.LENGTH_SHORT).show();
                    });
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