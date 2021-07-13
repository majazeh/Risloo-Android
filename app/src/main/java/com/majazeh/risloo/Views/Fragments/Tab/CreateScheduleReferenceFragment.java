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
import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleFragment;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;
import com.majazeh.risloo.databinding.FragmentCreateScheduleReferenceBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.ArrayList;

public class CreateScheduleReferenceFragment extends Fragment {

    // Binding
    private FragmentCreateScheduleReferenceBinding binding;

    // Dialogs
    private SearchableDialog casesDialog;

    // Vars
    public String type = "", roomId = "", caseId = "", count = "", selection = "";
    public boolean bulkSession = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateScheduleReferenceBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        casesDialog = new SearchableDialog();

        binding.typeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleReferenceTabTypeHeader));
        binding.caseIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleReferenceTabCaseHeader));
        binding.countIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleReferenceTabCountHeader));
        binding.selectionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleReferenceTabSelectionHeader));

        binding.bulkSessionCheckBox.getRoot().setText(getResources().getString(R.string.CreateScheduleReferenceTabCheckbox));

        InitManager.fixedSpinner(requireActivity(), binding.selectionIncludeLayout.selectSpinner, R.array.SelectionTypes, "main");

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateScheduleReferenceTabButton), getResources().getColor(R.color.White));
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
        binding.typeIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();

                if (type.equals("اعضاء پرونده درمانی …"))
                    binding.caseIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                else
                    binding.caseIncludeLayout.getRoot().setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ClickManager.onDelayedClickListener(() -> {
            casesDialog.show(requireActivity().getSupportFragmentManager(), "casesDialog");
            casesDialog.setData("cases");
        }).widget(binding.caseIncludeLayout.selectContainer);

        binding.bulkSessionCheckBox.getRoot().setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bulkSession = true;

                binding.countIncludeLayout.getRoot().setVisibility(View.VISIBLE);
            } else {
                bulkSession = false;

                binding.countIncludeLayout.getRoot().setVisibility(View.GONE);
            }
        });

        binding.countIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.countIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.countIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.selectionIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selection = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ClickManager.onDelayedClickListener(() -> {
            if (type.equals(""))
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.typeIncludeLayout.selectSpinner, binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.typeIncludeLayout.selectSpinner, binding.typeErrorLayout.getRoot(), binding.typeErrorLayout.errorTextView);

            if (selection.equals(""))
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.selectionIncludeLayout.selectSpinner, binding.selectionErrorLayout.getRoot(), binding.selectionErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.selectionIncludeLayout.selectSpinner, binding.selectionErrorLayout.getRoot(), binding.selectionErrorLayout.errorTextView);

            if (!type.equals("") && !selection.equals(""))
                doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        Fragment current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        if (current instanceof CreateScheduleFragment) {
            RoomModel model = ((CreateScheduleFragment) current).roomModel;

            if (model.getRoomId() != null && !model.getRoomId().equals("")) {
                roomId = model.getRoomId();
            }

            setTypes(model);
        }
    }

    private void setTypes(RoomModel model) {
        ArrayList<String> options = new ArrayList<>();

        options.add(requireActivity().getResources().getString(R.string.CreateScheduleReferenceTabTypeRisloo));

        try {
            if (model.getRoomCenter() != null && model.getRoomCenter().getDetail() != null && model.getRoomCenter().getDetail().has("title") && !model.getRoomCenter().getDetail().isNull("title") && !model.getRoomCenter().getDetail().getString("title").equals(""))
                options.add(model.getRoomCenter().getDetail().getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (model.getRoomManager().getName() != null && !model.getRoomManager().getName().equals("")) {
            String name = requireActivity().getResources().getString(R.string.CreateScheduleReferenceTabRoom) + " " + model.getRoomManager().getName();
            options.add(name);
        }

        options.add(requireActivity().getResources().getString(R.string.CreateScheduleReferenceTabTypeCase));

        options.add("");

        InitManager.unfixedSpinner(requireActivity(), binding.typeIncludeLayout.selectSpinner, options, "main");
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

    private void doWork() {
        count = binding.countIncludeLayout.inputEditText.getText().toString().trim();

        Fragment current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        if (current instanceof CreateScheduleFragment)
            ((CreateScheduleFragment) current).doWork();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}