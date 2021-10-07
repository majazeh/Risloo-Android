package com.majazeh.risloo.Views.Fragments.Main.Tab;

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
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateScheduleFragment;
import com.majazeh.risloo.databinding.FragmentCreateScheduleTabReferenceBinding;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.ArrayList;

public class CreateScheduleTabReferenceFragment extends Fragment {

    // Binding
    public FragmentCreateScheduleTabReferenceBinding binding;

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
        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        binding.typeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTabReferenceTypeHeader));
        binding.caseIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTabReferenceCaseHeader));
        binding.countIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTabReferenceCountHeader));
        binding.selectionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateScheduleTabReferenceSelectionHeader));

        binding.bulkSessionCheckBox.getRoot().setText(getResources().getString(R.string.CreateScheduleTabReferenceCheckbox));

        InitManager.normal12sspSpinner(requireActivity(), binding.selectionIncludeLayout.selectSpinner, R.array.SelectionTypes);

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateScheduleTabReferenceButton), getResources().getColor(R.color.White), R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
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

        CustomClickView.onDelayedListener(() -> {
            DialogManager.showSearchableDialog(requireActivity(), "cases");
        }).widget(binding.caseIncludeLayout.selectContainer);

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
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.countIncludeLayout.inputEditText);
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

        CustomClickView.onDelayedListener(() -> {
            if (current instanceof CreateScheduleFragment)
                ((CreateScheduleFragment) current).checkRequire();
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
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

        options.add(requireActivity().getResources().getString(R.string.CreateScheduleTabReferenceTypeRisloo));

        try {
            if (model.getRoomCenter() != null && model.getRoomCenter().getDetail() != null && model.getRoomCenter().getDetail().has("title") && !model.getRoomCenter().getDetail().isNull("title") && !model.getRoomCenter().getDetail().getString("title").equals(""))
                options.add(model.getRoomCenter().getDetail().getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (model.getRoomManager().getName() != null && !model.getRoomManager().getName().equals("")) {
            String name = requireActivity().getResources().getString(R.string.CreateScheduleTabReferenceRoom) + " " + model.getRoomManager().getName();
            options.add(name);
        }

        options.add(requireActivity().getResources().getString(R.string.CreateScheduleTabReferenceTypeCase));

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

                DialogManager.dismissSearchableDialog();
            } break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}