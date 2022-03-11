package com.majazeh.risloo.views.fragments.main.tab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.DropdownManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.JsonManager;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditSession;
import com.majazeh.risloo.databinding.FragmentEditSessionTabReferenceBinding;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentEditSessionTabReference extends Fragment {

    // Binding
    public FragmentEditSessionTabReferenceBinding binding;

    // Fragments
    private Fragment current;

    // Vars
    public String type = "", roomId = "", caseId = "", problem = "", count = "", selection = "", groupSession = "";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditSessionTabReferenceBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        current = ((ActivityMain) requireActivity()).fragmont.getCurrent();

        binding.typeIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTabReferenceTypeHeader));
        binding.caseIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTabReferenceCaseHeader));
        binding.problemIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTabReferenceProblemHeader));
        binding.countIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTabReferenceCountHeader));
        binding.selectionIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTabReferenceSelectionHeader));

        binding.bulkSessionCheckBox.getRoot().setText(getResources().getString(R.string.EditSessionTabReferenceCheckbox));

        DropdownManager.spinner12ssp(requireActivity(), binding.selectionIncludeLayout.selectSpinner, R.array.SelectionTypes);

        InitManager.txtTextColorBackground(binding.editTextView.getRoot(), getResources().getString(R.string.EditSessionTabReferenceButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.typeIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.selectionIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.typeIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.selectionIncludeLayout.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.typeIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    type = parent.getItemAtPosition(position).toString();

                    if (type.equals("اعضاء پرونده درمانی …")) {
                        binding.caseIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.problemIncludeLayout.getRoot().setVisibility(View.GONE);
                    } else if (type.equals("ساخت پرونده جدید")) {
                        binding.caseIncludeLayout.getRoot().setVisibility(View.GONE);
                        binding.problemIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    } else {
                        binding.caseIncludeLayout.getRoot().setVisibility(View.GONE);
                        binding.problemIncludeLayout.getRoot().setVisibility(View.GONE);
                    }

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.selectionIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    selection = parent.getItemAtPosition(position).toString();

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.problemIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.problemIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.problemIncludeLayout.inputEditText);
            return false;
        });

        binding.countIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.countIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.countIncludeLayout.inputEditText);
            return false;
        });

        binding.problemIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            problem = binding.problemIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.countIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            count = binding.countIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.bulkSessionCheckBox.getRoot().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                groupSession = "on";

                binding.countIncludeLayout.getRoot().setVisibility(View.VISIBLE);
            } else {
                groupSession = "";

                binding.countIncludeLayout.getRoot().setVisibility(View.GONE);
            }
        });

        CustomClickView.onDelayedListener(() -> {
            DialogManager.showDialogSearchable(requireActivity(), "cases");
        }).widget(binding.caseIncludeLayout.selectContainer);

        CustomClickView.onDelayedListener(() -> {
            if (current instanceof FragmentEditSession)
                ((FragmentEditSession) current).checkRequire();
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (current instanceof FragmentEditSession) {
            SessionModel model = ((FragmentEditSession) current).sessionModel;

            if (model.getRoom() != null && model.getRoom().getId() != null) {
                roomId = model.getRoom().getId();
            }

            if (model.getRoom() != null) {
                setTypes(model.getRoom());
            }

            if (model.getClientsType() != null && !model.getClientsType().equals("")) {
                switch (model.getClientsType()) {
                    case "risloo":
                        type = binding.typeIncludeLayout.selectSpinner.getItemAtPosition(0).toString();
                        binding.typeIncludeLayout.selectSpinner.setSelection(0);
                        break;
                    case "center":
                        type = binding.typeIncludeLayout.selectSpinner.getItemAtPosition(1).toString();
                        binding.typeIncludeLayout.selectSpinner.setSelection(1);
                        break;
                    case "room":
                        type = binding.typeIncludeLayout.selectSpinner.getItemAtPosition(2).toString();
                        binding.typeIncludeLayout.selectSpinner.setSelection(2);
                        break;
                    case "case":
                        type = binding.typeIncludeLayout.selectSpinner.getItemAtPosition(3).toString();
                        binding.typeIncludeLayout.selectSpinner.setSelection(3);
                        break;
                    case "new_case":
                        type = binding.typeIncludeLayout.selectSpinner.getItemAtPosition(4).toString();
                        binding.typeIncludeLayout.selectSpinner.setSelection(4);
                        break;
                }

                if (type.equals("اعضاء پرونده درمانی …")) {
                    binding.caseIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    binding.problemIncludeLayout.getRoot().setVisibility(View.GONE);

                    if (model.getCasse().getId() != null && !model.getCasse().getId().equals("")) {
                        caseId = model.getCasse().getId();

                        binding.caseIncludeLayout.primaryTextView.setText(caseId);
                        setClients(model.getClients());
                    }

                } else if (type.equals("ساخت پرونده جدید")) {
                    binding.caseIncludeLayout.getRoot().setVisibility(View.GONE);
                    binding.problemIncludeLayout.getRoot().setVisibility(View.VISIBLE);

                    if (model.getReport() != null && !model.getReport().equals("")) {
                        problem = model.getReport();
                        binding.problemIncludeLayout.inputEditText.setText(problem);
                    }

                } else {
                    binding.caseIncludeLayout.getRoot().setVisibility(View.GONE);
                    binding.problemIncludeLayout.getRoot().setVisibility(View.GONE);
                }
            }

            if (model.isGroupSession()) {
                binding.bulkSessionCheckBox.getRoot().setChecked(true);
                groupSession = "on";

                binding.countIncludeLayout.getRoot().setVisibility(View.VISIBLE);
            }

            if (model.isGroupSession() && model.getClientsNumber() != 0) {
                count = String.valueOf(model.getClientsNumber());
                binding.countIncludeLayout.inputEditText.setText(count);
            }

            if (model.getSelectionType() != null && !model.getSelectionType().equals("")) {
                selection = JsonManager.getSelectionType(requireActivity(), "fa", model.getSelectionType());
                for (int i = 0; i < binding.selectionIncludeLayout.selectSpinner.getCount(); i++) {
                    if (binding.selectionIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(selection)) {
                        binding.selectionIncludeLayout.selectSpinner.setSelection(i);
                    }
                }
            }
        }
    }

    private void setTypes(RoomModel model) {
        ArrayList<String> options = new ArrayList<>();

        options.add(requireActivity().getResources().getString(R.string.EditSessionTabReferenceTypeRisloo));

        try {
            if (model.getCenter() != null && model.getCenter().getDetail() != null && model.getCenter().getDetail().has("title") && !model.getCenter().getDetail().isNull("title") && !model.getCenter().getDetail().getString("title").equals(""))
                options.add(model.getCenter().getDetail().getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (model.getManager().getName() != null && !model.getManager().getName().equals("")) {
            String name = requireActivity().getResources().getString(R.string.EditSessionTabReferenceRoom) + " " + model.getManager().getName();
            options.add(name);
        }

        options.add(requireActivity().getResources().getString(R.string.EditSessionTabReferenceTypeCase));
        options.add(requireActivity().getResources().getString(R.string.EditSessionTabReferenceTypeNew));

        options.add("");

        DropdownManager.spinner12ssp(requireActivity(), binding.typeIncludeLayout.selectSpinner, options);
    }

    private void setClients(List clients) {
        if (clients != null && clients.data().size() != 0) {
            binding.caseIncludeLayout.secondaryTextView.setVisibility(View.VISIBLE);
            binding.caseIncludeLayout.secondaryTextView.setText("");

            for (int i = 0; i < clients.data().size(); i++) {
                UserModel user = (UserModel) clients.data().get(i);

                if (user != null) {
                    binding.caseIncludeLayout.secondaryTextView.append(user.getName());
                    if (i != clients.data().size() - 1)
                        binding.caseIncludeLayout.secondaryTextView.append("  -  ");

                }
            }
        } else {
            binding.caseIncludeLayout.secondaryTextView.setVisibility(View.GONE);
            binding.caseIncludeLayout.secondaryTextView.setText("");
        }
    }

    public void responseDialog(String method, TypeModel item) {
        switch (method) {
            case "cases": {
                CaseModel model = (CaseModel) item;

                if (!caseId.equals(model.getId())) {
                    caseId = model.getId();

                    binding.caseIncludeLayout.primaryTextView.setText(caseId);
                    setClients(model.getClients());
                } else if (caseId.equals(model.getId())) {
                    caseId = "";

                    binding.caseIncludeLayout.primaryTextView.setText("");
                    binding.caseIncludeLayout.secondaryTextView.setText("");

                    setClients(null);
                }

                DialogManager.dismissDialogSearchable();
            } break;
        }
    }

    public void setHashmap(HashMap data) {
        if (!selection.equals(""))
            data.put("selection_type", JsonManager.getSelectionType(requireActivity(), "en", selection));
        else
            data.remove("selection_type");

        if (type.equals("اعضاء ریسلو")) {
            data.put("clients_type", "risloo");
        } else if (type.contains("مرکز")) {
            data.put("clients_type", "center");
        } else if (type.contains("اتاق درمان")) {
            data.put("clients_type", "room");
        } else if (type.equals("اعضاء پرونده درمانی …")) {
            data.put("clients_type", "case");

            if (!caseId.equals(""))
                data.put("case_id", caseId);
            else
                data.remove("case_id");

        } else if (type.equals("ساخت پرونده جدید")) {
            data.put("clients_type", "new_case");

            if (!problem.equals(""))
                data.put("problem", problem);
            else
                data.remove("problem");
        }

        if (!groupSession.equals(""))
            data.put("group_session", groupSession);
        else
            data.remove("group_session");

        if (groupSession.equals("on"))
            data.put("clients_number", count);
        else
            data.remove("clients_number");

    }

    public void hideValid() {
        if (binding.selectionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((ActivityMain) requireActivity()).validatoon.hideValid(binding.selectionErrorLayout.getRoot(), binding.selectionErrorLayout.errorTextView);

        if (binding.typeErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((ActivityMain) requireActivity()).validatoon.hideValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView);

        if (binding.caseErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((ActivityMain) requireActivity()).validatoon.hideValid(binding.caseErrorLayout.getRoot(), binding.caseErrorLayout.errorTextView);

        if (binding.problemErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((ActivityMain) requireActivity()).validatoon.hideValid(binding.problemErrorLayout.getRoot(), binding.problemErrorLayout.errorTextView);

        if (binding.bulkSessionErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((ActivityMain) requireActivity()).validatoon.hideValid(binding.bulkSessionErrorLayout.getRoot(), binding.bulkSessionErrorLayout.errorTextView);

        if (binding.countErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((ActivityMain) requireActivity()).validatoon.hideValid(binding.countErrorLayout.getRoot(), binding.countErrorLayout.errorTextView);
    }

    public void showValid(String key, String validation) {
        switch (key) {
            case "selection_type":
                ((ActivityMain) requireActivity()).validatoon.showValid(binding.selectionErrorLayout.getRoot(), binding.selectionErrorLayout.errorTextView, validation);
                break;
            case "clients_type":
                ((ActivityMain) requireActivity()).validatoon.showValid(binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView, validation);
                break;
            case "case_id":
                ((ActivityMain) requireActivity()).validatoon.showValid(binding.caseErrorLayout.getRoot(), binding.caseErrorLayout.errorTextView, validation);
                break;
            case "problem":
                ((ActivityMain) requireActivity()).validatoon.showValid(binding.problemErrorLayout.getRoot(), binding.problemErrorLayout.errorTextView, validation);
                break;
            case "group_session":
                ((ActivityMain) requireActivity()).validatoon.showValid(binding.bulkSessionErrorLayout.getRoot(), binding.bulkSessionErrorLayout.errorTextView, validation);
                break;
            case "clients_number":
                ((ActivityMain) requireActivity()).validatoon.showValid(binding.countErrorLayout.getRoot(), binding.countErrorLayout.errorTextView, validation);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        userSelect = false;
    }

}