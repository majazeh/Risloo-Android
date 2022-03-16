package com.majazeh.risloo.views.fragments.main.create;

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
import com.majazeh.risloo.databinding.FragmentCreateCaseBinding;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.dialog.DialogSelectedAdapter;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Case;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TagModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentCreateCase extends Fragment {

    // Binding
    private FragmentCreateCaseBinding binding;

    // Adapters
    public DialogSelectedAdapter referencesAdapter, tagsAdapter;

    // Models
    public TypeModel typeModel;

    // Objects
    private HashMap data, header;

    // Vars
    private String title = "", problem = "";

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
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.titleIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCaseFragmentTitleHeader));
        binding.referenceIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCaseFragmentReferenceHeader));
        binding.problemIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCaseFragmentProblemHeader));
        binding.tagsIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateCaseFragmentTagsHeader));

        binding.referenceGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateCaseFragmentReferenceGuide));

        InitManager.rcvVerticalUnfixed(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);
        InitManager.rcvVerticalUnfixed(requireActivity(), binding.tagsIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateCenterFragmentButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.referenceIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction())
                DialogManager.showDialogSearchable(requireActivity(), "references");
            return false;
        });

        binding.tagsIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction())
                DialogManager.showDialogSearchable(requireActivity(), "tags");
            return false;
        });

        binding.titleIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.titleIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.titleIncludeLayout.inputEditText);
            return false;
        });

        binding.problemIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.problemIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.problemIncludeLayout.inputEditText);
            return false;
        });

        binding.titleIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            title = binding.titleIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.problemIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            problem = binding.problemIncludeLayout.inputEditText.getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            ((ActivityMain) requireActivity()).validatoon.resetValid(binding);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        typeModel = FragmentCreateCaseArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (StringManager.suffix(typeModel.getClass().getName(), '.').equals("CenterModel"))
                setData((CenterModel) typeModel);
            else if (StringManager.suffix(typeModel.getClass().getName(), '.').equals("RoomModel"))
                setData((RoomModel) typeModel);

        } else {
            setRecyclerView(new ArrayList<>(), new ArrayList<>(), "references");
            setRecyclerView(new ArrayList<>(), new ArrayList<>(), "tags");
        }
    }

    private void setData(CenterModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }

        setRecyclerView(new ArrayList<>(), new ArrayList<>(), "references");
        setRecyclerView(new ArrayList<>(), new ArrayList<>(), "tags");
    }

    private void setData(RoomModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
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

    private void setHashmap() {
        if (!title.equals(""))
            data.put("title", title);
        else
            data.remove("title");

        if (!referencesAdapter.getIds().isEmpty())
            data.put("client_id", referencesAdapter.getIds());
        else
            data.remove("client_id");

        if (!problem.equals(""))
            data.put("problem", problem);
        else
            data.remove("problem");

        if (!tagsAdapter.getIds().isEmpty())
            data.put("tags", tagsAdapter.getIds());
        else
            data.remove("tags");
    }

    private void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap();

        Case.create(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                CaseModel caseModel = (CaseModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissDialogLoading();
                        SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackCreatedNewCase));

                        ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentCase(caseModel);
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> ((ActivityMain) requireActivity()).validatoon.requestValid(response, binding));
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