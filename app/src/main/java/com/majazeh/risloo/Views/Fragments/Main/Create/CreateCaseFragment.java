package com.majazeh.risloo.Views.Fragments.Main.Create;

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
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Case;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TagModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Dialog.DialogSelectedAdapter;
import com.majazeh.risloo.databinding.FragmentCreateCaseBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CreateCaseFragment extends Fragment {

    // Binding
    private FragmentCreateCaseBinding binding;

    // Adapters
    public DialogSelectedAdapter referencesAdapter, tagsAdapter;

    // Objects
    private HashMap data, header;

    // Vars
    public String roomId = "", centerId = "", title = "", problem = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateCaseBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        referencesAdapter = new DialogSelectedAdapter(requireActivity());
        tagsAdapter = new DialogSelectedAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.titleIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCaseFragmentTitleHeader));
        binding.referenceIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCaseFragmentReferenceHeader));
        binding.problemIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCaseFragmentProblemHeader));
        binding.tagsIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCaseFragmentTagsHeader));

        binding.referenceGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateCaseFragmentReferenceGuide));

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);
        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.tagsIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateCenterFragmentButton), getResources().getColor(R.color.White), R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.titleIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.titleIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.titleIncludeLayout.inputEditText);
            return false;
        });

        binding.titleIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            title = binding.titleIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.referenceIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction())
                DialogManager.showSearchableDialog(requireActivity(), "references");
            return false;
        });

        binding.problemIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.problemIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.problemIncludeLayout.inputEditText);
            return false;
        });

        binding.problemIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            problem = binding.problemIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.tagsIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction())
                DialogManager.showSearchableDialog(requireActivity(), "tags");
            return false;
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.titleErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView);
            if (binding.referenceErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView);
            if (binding.problemErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.problemErrorLayout.getRoot(), binding.problemErrorLayout.errorTextView);
            if (binding.tagsErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.tagsErrorLayout.getRoot(), binding.tagsErrorLayout.errorTextView);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        TypeModel typeModel = CreateCaseFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (StringManager.substring(typeModel.getClass().getName(), '.').equals("CenterModel"))
                setData((CenterModel) typeModel);
            else if (StringManager.substring(typeModel.getClass().getName(), '.').equals("RoomModel"))
                setData((RoomModel) typeModel);

        } else {
            setRecyclerView(new ArrayList<>(), new ArrayList<>(), "references");
            setRecyclerView(new ArrayList<>(), new ArrayList<>(), "tags");
        }
    }

    private void setData(CenterModel model) {
        if (model.getCenterId() != null && !model.getCenterId().equals("")) {
            roomId = model.getCenterId();
            data.put("id", roomId);

            centerId = roomId;
        }

        setRecyclerView(new ArrayList<>(), new ArrayList<>(), "references");
        setRecyclerView(new ArrayList<>(), new ArrayList<>(), "tags");
    }

    private void setData(RoomModel model) {
        if (model.getRoomId() != null && !model.getRoomId().equals("")) {
            roomId = model.getRoomId();
            data.put("id", roomId);
        }

        if (model.getRoomCenter() != null && model.getRoomCenter().getCenterId() != null && !model.getRoomCenter().getCenterId().equals("")) {
            centerId = model.getRoomCenter().getCenterId();
        }

        setRecyclerView(new ArrayList<>(), new ArrayList<>(), "references");
        setRecyclerView(new ArrayList<>(), new ArrayList<>(), "tags");
    }

    private void setRecyclerView(ArrayList<TypeModel> items, ArrayList<String> ids, String method) {
        switch (method) {
            case "references":
                referencesAdapter.setItems(items, ids, method, binding.referenceIncludeLayout.countTextView);
                binding.referenceIncludeLayout.selectRecyclerView.setAdapter(referencesAdapter);
                break;
            case "tags":
                tagsAdapter.setItems(items, ids, method, binding.tagsIncludeLayout.countTextView);
                binding.tagsIncludeLayout.selectRecyclerView.setAdapter(tagsAdapter);
                break;
        }
    }

    public void responseDialog(String method, TypeModel item) {
        switch (method) {
            case "references": {
                UserModel model = (UserModel) item;

                int position = referencesAdapter.getIds().indexOf(model.getId());

                if (position == -1)
                    referencesAdapter.addItem(item);
                else
                    referencesAdapter.removeItem(position);

                if (referencesAdapter.getIds().size() != 0) {
                    binding.referenceIncludeLayout.countTextView.setVisibility(View.VISIBLE);
                    binding.referenceIncludeLayout.countTextView.setText(StringManager.bracing(referencesAdapter.getIds().size()));
                } else {
                    binding.referenceIncludeLayout.countTextView.setVisibility(View.GONE);
                    binding.referenceIncludeLayout.countTextView.setText("");
                }
            } break;
            case "tags": {
                TagModel model = (TagModel) item;

                int position = tagsAdapter.getIds().indexOf(model.getId());

                if (position == -1)
                    tagsAdapter.addItem(item);
                else
                    tagsAdapter.removeItem(position);

                if (tagsAdapter.getIds().size() != 0) {
                    binding.tagsIncludeLayout.countTextView.setVisibility(View.VISIBLE);
                    binding.tagsIncludeLayout.countTextView.setText(StringManager.bracing(tagsAdapter.getIds().size()));
                } else {
                    binding.tagsIncludeLayout.countTextView.setVisibility(View.GONE);
                    binding.tagsIncludeLayout.countTextView.setText("");
                }
            } break;
        }
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        data.put("title", title);
        data.put("client_id", referencesAdapter.getIds());
        data.put("problem", problem);
        data.put("tags", tagsAdapter.getIds());

        Case.create(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.ToastNewCaseAdded));

                        ((MainActivity) requireActivity()).navController.navigateUp();
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
                                            case "title":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.titleErrorLayout.getRoot(), binding.titleErrorLayout.errorTextView, validation);
                                                break;
                                            case "client_id":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.referenceErrorLayout.getRoot(), binding.referenceErrorLayout.errorTextView, validation);
                                                break;
                                            case "problem":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.problemErrorLayout.getRoot(), binding.problemErrorLayout.errorTextView, validation);
                                                break;
                                            case "tags":
                                                ((MainActivity) requireActivity()).validatoon.showValid(binding.tagsErrorLayout.getRoot(), binding.tagsErrorLayout.errorTextView, validation);
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