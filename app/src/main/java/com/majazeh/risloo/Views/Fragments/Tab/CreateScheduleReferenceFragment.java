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
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;
import com.majazeh.risloo.databinding.FragmentCreateScheduleReferenceBinding;

import org.json.JSONException;

public class CreateScheduleReferenceFragment extends Fragment {

    // Binding
    private FragmentCreateScheduleReferenceBinding binding;

    // Dialogs
    private SearchableDialog casesDialog;

    // Vars
    public String type = "", caseId = "", caseName = "", count = "", selection = "";
    private boolean bulkSession = false;

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

        InitManager.spinner(requireActivity(), binding.typeIncludeLayout.selectSpinner, R.array.RequestTypes, "main");
        InitManager.spinner(requireActivity(), binding.selectionIncludeLayout.selectSpinner, R.array.SelectionTypes, "main");

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

                if (type.contains("پرونده درمانی")) {
                    binding.caseIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                } else {
                    binding.caseIncludeLayout.getRoot().setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ClickManager.onDelayedClickListener(() -> {
            casesDialog.show(requireActivity().getSupportFragmentManager(), "casesDialog");
            casesDialog.setData("cases");
        }).widget(binding.caseIncludeLayout.selectTextView);

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
            if (type.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.typeIncludeLayout.selectSpinner, binding.typeErrorLayout.errorImageView, binding.typeErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (selection.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.selectionIncludeLayout.selectSpinner, binding.selectionErrorLayout.errorImageView, binding.selectionErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (!type.equals("") && !selection.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.typeIncludeLayout.selectSpinner, binding.typeErrorLayout.errorImageView, binding.typeErrorLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.selectionIncludeLayout.selectSpinner, binding.selectionErrorLayout.errorImageView, binding.selectionErrorLayout.errorTextView);

                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getType().equals("")) {
            type = ((MainActivity) requireActivity()).singleton.getType();
            for (int i=0; i<binding.typeIncludeLayout.selectSpinner.getCount(); i++) {
                if (binding.typeIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(type)) {
                    binding.typeIncludeLayout.selectSpinner.setSelection(i);

                    if (type.contains("پرونده درمانی")) {
                        binding.caseIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    } else {
                        binding.caseIncludeLayout.getRoot().setVisibility(View.GONE);
                    }
                }
            }
        }
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            caseId = ((MainActivity) requireActivity()).singleton.getAddress();
            caseName = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.caseIncludeLayout.selectTextView.setText(caseName);
        }
        if (((MainActivity) requireActivity()).singleton.getBulkSession()) {
            bulkSession = true;
            binding.bulkSessionCheckBox.getRoot().setChecked(true);
            binding.countIncludeLayout.getRoot().setVisibility(View.VISIBLE);
        }
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            count = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.countIncludeLayout.inputEditText.setText(count);
        }
        if (!((MainActivity) requireActivity()).singleton.getType().equals("")) {
            selection = ((MainActivity) requireActivity()).singleton.getType();
            for (int i=0; i<binding.selectionIncludeLayout.selectSpinner.getCount(); i++) {
                if (binding.selectionIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(selection)) {
                    binding.selectionIncludeLayout.selectSpinner.setSelection(i);
                }
            }
        }
    }

    public void responseDialog(String method, TypeModel item) {
        try {
            switch (method) {
                case "cases":
                    if (!caseId.equals(item.object.get("id").toString())) {
                        caseId = item.object.get("id").toString();
                        caseName = item.object.get("title").toString();

                        binding.caseIncludeLayout.selectTextView.setText(caseName);
                    } else if (caseId.equals(item.object.get("id").toString())) {
                        caseId = "";
                        caseName = "";

                        binding.caseIncludeLayout.selectTextView.setText("");
                    }

                    casesDialog.dismiss();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void doWork() {
        count = binding.countIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}