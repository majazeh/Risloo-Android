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
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.SelectionManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateSchedule;
import com.majazeh.risloo.databinding.FragmentCreateScheduleTabReferenceBinding;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentCreateScheduleTabReference extends Fragment {

    // Binding
    private FragmentCreateScheduleTabReferenceBinding binding;

    // Fragments
    private Fragment current;

    // Vars
    public String type = "", roomId = "", caseId = "", count = "", selection = "", groupSession = "";
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateScheduleTabReferenceBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        current = ((ActivityMain) requireActivity()).fragmont.getCurrent();

        binding.typeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTabReferenceTypeHeader));
        binding.caseIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTabReferenceCaseHeader));
        binding.countIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTabReferenceCountHeader));
        binding.selectionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTabReferenceSelectionHeader));

        binding.bulkSessionCheckBox.getRoot().setText(getResources().getString(R.string.CreateScheduleTabReferenceCheckbox));

        InitManager.input12sspSpinner(requireActivity(), binding.selectionIncludeLayout.selectSpinner, R.array.SelectionTypes);

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateScheduleTabReferenceButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
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

                    if (type.equals("اعضاء پرونده درمانی …"))
                        binding.caseIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    else
                        binding.caseIncludeLayout.getRoot().setVisibility(View.GONE);

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

        binding.countIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.countIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.countIncludeLayout.inputEditText);
            return false;
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
            DialogManager.showSearchableDialog(requireActivity(), "cases");
        }).widget(binding.caseIncludeLayout.selectContainer);

        CustomClickView.onDelayedListener(() -> {
            if (current instanceof FragmentCreateSchedule)
                ((FragmentCreateSchedule) current).checkRequire();
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (current instanceof FragmentCreateSchedule) {
            RoomModel model = ((FragmentCreateSchedule) current).roomModel;

            if (model.getId() != null && !model.getId().equals("")) {
                roomId = model.getId();
            }

            setTypes(model);
        }
    }

    private void setTypes(RoomModel model) {
        ArrayList<String> options = new ArrayList<>();

        options.add(requireActivity().getResources().getString(R.string.CreateScheduleTabReferenceTypeRisloo));

        try {
            if (model.getCenter() != null && model.getCenter().getDetail() != null && model.getCenter().getDetail().has("title") && !model.getCenter().getDetail().isNull("title") && !model.getCenter().getDetail().getString("title").equals(""))
                options.add(model.getCenter().getDetail().getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (model.getManager().getName() != null && !model.getManager().getName().equals("")) {
            String name = requireActivity().getResources().getString(R.string.CreateScheduleTabReferenceRoom) + " " + model.getManager().getName();
            options.add(name);
        }

        options.add(requireActivity().getResources().getString(R.string.CreateScheduleTabReferenceTypeCase));

        options.add("");

        InitManager.input12sspSpinner(requireActivity(), binding.typeIncludeLayout.selectSpinner, options);
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

                DialogManager.dismissSearchableDialog();
            } break;
        }
    }

    public void setHashmap(HashMap data) {
        if (!selection.equals(""))
            data.put("selection_type", SelectionManager.getSelectionType(requireActivity(), "en", selection));
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