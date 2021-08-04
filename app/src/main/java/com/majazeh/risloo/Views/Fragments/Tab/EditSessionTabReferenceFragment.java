package com.majazeh.risloo.Views.Fragments.Tab;

import android.annotation.SuppressLint;
import android.os.Build;
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
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragment;
import com.majazeh.risloo.databinding.FragmentEditSessionTabReferenceBinding;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.ArrayList;

public class EditSessionTabReferenceFragment extends Fragment {

    // Binding
    public FragmentEditSessionTabReferenceBinding binding;

    // Dialogs
    private SearchableDialog casesDialog;

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

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        casesDialog = new SearchableDialog();

        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        binding.typeIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTabReferenceTypeHeader));
        binding.caseIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTabReferenceCaseHeader));
        binding.problemIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTabReferenceProblemHeader));
        binding.countIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTabReferenceCountHeader));
        binding.selectionIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditSessionTabReferenceSelectionHeader));

        binding.bulkSessionCheckBox.getRoot().setText(getResources().getString(R.string.EditSessionTabReferenceCheckbox));

        InitManager.normal12sspSpinner(requireActivity(), binding.selectionIncludeLayout.selectSpinner, R.array.SelectionTypes);

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.EditSessionTabReferenceButton), getResources().getColor(R.color.White));
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
        binding.typeIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

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

        ClickManager.onDelayedClickListener(() -> {
            casesDialog.show(requireActivity().getSupportFragmentManager(), "casesDialog");
            casesDialog.setData("cases");
        }).widget(binding.caseIncludeLayout.selectContainer);

        binding.problemIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.problemIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.problemIncludeLayout.inputEditText);
            return false;
        });

        binding.problemIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            problem = binding.problemIncludeLayout.inputEditText.getText().toString().trim();
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

        binding.countIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.countIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.countIncludeLayout.inputEditText);
            return false;
        });

        binding.countIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            count = binding.countIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.selectionIncludeLayout.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
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

        ClickManager.onDelayedClickListener(() -> {
            if (current instanceof EditSessionFragment)
                ((EditSessionFragment) current).checkRequire();
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (current instanceof EditSessionFragment) {
            SessionModel model = ((EditSessionFragment) current).sessionModel;

            if (model.getRoom() != null && model.getRoom().getRoomId() != null) {
                roomId = model.getRoom().getRoomId();
            }

            if (model.getRoom() != null) {
                setTypes(model.getRoom());
            }

            if (model.getClients_type() != null && !model.getClients_type().equals("")) {
                switch (model.getClients_type()) {
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

                    if (model.getCaseModel().getCaseId() != null && !model.getCaseModel().getCaseId().equals("")) {
                        caseId = model.getCaseModel().getCaseId();

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

            if (model.isGroup_session()) {
                binding.bulkSessionCheckBox.getRoot().setChecked(true);
                groupSession = "on";

                binding.countIncludeLayout.getRoot().setVisibility(View.VISIBLE);
            }

            if (model.isGroup_session() && model.getClients_number() != 0) {
                count = String.valueOf(model.getClients_number());
                binding.countIncludeLayout.inputEditText.setText(count);
            }

            if (model.getSelection_type() != null && !model.getSelection_type().equals("")) {
                selection = SelectionManager.getSelectionType(requireActivity(), "fa", model.getSelection_type());
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
            if (model.getRoomCenter() != null && model.getRoomCenter().getDetail() != null && model.getRoomCenter().getDetail().has("title") && !model.getRoomCenter().getDetail().isNull("title") && !model.getRoomCenter().getDetail().getString("title").equals(""))
                options.add(model.getRoomCenter().getDetail().getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (model.getRoomManager().getName() != null && !model.getRoomManager().getName().equals("")) {
            String name = requireActivity().getResources().getString(R.string.EditSessionTabReferenceRoom) + " " + model.getRoomManager().getName();
            options.add(name);
        }

        options.add(requireActivity().getResources().getString(R.string.EditSessionTabReferenceTypeCase));
        options.add(requireActivity().getResources().getString(R.string.EditSessionTabReferenceTypeNew));

        options.add("");

        InitManager.normal12sspSpinner(requireActivity(), binding.typeIncludeLayout.selectSpinner, options);
    }

    private void setClients(com.mre.ligheh.Model.Madule.List clients) {
        if (clients != null && clients.data().size() != 0) {
            binding.caseIncludeLayout.secondaryTextView.setVisibility(View.VISIBLE);

            binding.caseIncludeLayout.secondaryTextView.setText("");
            for (int i = 0; i < clients.data().size(); i++) {
                UserModel user = (UserModel) clients.data().get(i);
                if (user != null) {
                    binding.caseIncludeLayout.secondaryTextView.append(user.getName());
                    if (i != clients.data().size() - 1) {
                        binding.caseIncludeLayout.secondaryTextView.append("  -  ");
                    }
                }
            }
        } else {
            binding.caseIncludeLayout.secondaryTextView.setVisibility(View.GONE);
        }
    }

    public void responseDialog(String method, TypeModel item) {
        switch (method) {
            case "cases": {
                CaseModel model = (CaseModel) item;

                if (!caseId.equals(model.getCaseId())) {
                    caseId = model.getCaseId();

                    binding.caseIncludeLayout.primaryTextView.setText(caseId);
                    setClients(model.getClients());
                } else if (caseId.equals(model.getCaseId())) {
                    caseId = "";

                    binding.caseIncludeLayout.primaryTextView.setText("");
                    binding.caseIncludeLayout.secondaryTextView.setText("");

                    setClients(null);
                }

                casesDialog.dismiss();
            } break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}