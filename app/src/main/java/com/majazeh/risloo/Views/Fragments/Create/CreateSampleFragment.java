package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Model;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SelectedAdapter;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;
import com.majazeh.risloo.databinding.FragmentCreateSampleBinding;

import org.json.JSONException;

import java.util.ArrayList;

public class CreateSampleFragment extends Fragment {

    // Binding
    private FragmentCreateSampleBinding binding;

    // Adapters
    public SelectedAdapter scalesAdapter, referencesAdapter;

    // Dialogs
    private SearchableDialog scalesDialog, roomsDialog, referencesDialog, casesDialog, sessionsDialog;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager scalesLayoutManager, referencesLayoutManager;
    private ClickableSpan assessmentLinkSpan;

    // Vars
    private ArrayList<Model> scales = new ArrayList<>(), references = new ArrayList<>();
    public String roomId = "", roomName = "", centerName = "", type = "case", name = "", userCount = "", caseType = "", situation = "", caseId = "", caseName = "",  sessionId = "", sessionName = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateSampleBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        scalesAdapter = new SelectedAdapter(requireActivity());
        referencesAdapter = new SelectedAdapter(requireActivity());

        scalesDialog = new SearchableDialog();
        roomsDialog = new SearchableDialog();
        referencesDialog = new SearchableDialog();
        casesDialog = new SearchableDialog();
        sessionsDialog = new SearchableDialog();

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", 0, 0, (int) getResources().getDimension(R.dimen._2sdp), 0);

        scalesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        referencesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        binding.scaleIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentScaleHeader));
        binding.roomIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentRoomHeader));
        binding.nameIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentNameHeader));
        binding.userCountIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentUserCountHeader));
        binding.caseTypeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentCaseTypeHeader));
        binding.situationIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentSituationHeader));
        binding.caseIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentCaseHeader));
        binding.sessionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentSessionHeader));
        binding.referenceIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentReferenceHeader));

        binding.nameIncludeLayout.inputEditText.setHint(getResources().getString(R.string.CreateSampleFragmentNameHint));

        binding.scaleGuideLayout.guideTextView.setMovementMethod(LinkMovementMethod.getInstance());

        binding.nameGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateSampleFragmentNameGuide));
        binding.userCountGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateSampleFragmentUserCountGuide));

        InitManager.unfixedRecyclerView(binding.scaleIncludeLayout.selectRecyclerView, itemDecoration, scalesLayoutManager);
        InitManager.unfixedRecyclerView(binding.referenceIncludeLayout.selectRecyclerView, itemDecoration, referencesLayoutManager);

        InitManager.spinner(requireActivity(), binding.caseTypeIncludeLayout.selectSpinner, R.array.CaseTypes, "main");

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateCenterFragmentButton), getResources().getColor(R.color.White));
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
        binding.scaleIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                scalesDialog.show(requireActivity().getSupportFragmentManager(), "scalesDialog");
                scalesDialog.setData("scales");
            }
            return false;
        });

        assessmentLinkSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                ((MainActivity) requireActivity()).navigator(R.id.scalesFragment);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint textPaint) {
                textPaint.setColor(getResources().getColor(R.color.Blue600));
                textPaint.setUnderlineText(false);
            }
        };

        ClickManager.onDelayedClickListener(() -> {
            roomsDialog.show(requireActivity().getSupportFragmentManager(), "roomsDialog");
            roomsDialog.setData("rooms");
        }).widget(binding.roomIncludeLayout.selectContainer);

        binding.typeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    type = "case";

                    binding.caseGroup.setVisibility(View.VISIBLE);
                    binding.roomGroup.setVisibility(View.GONE);
                    binding.bulkSampleGroup.setVisibility(View.GONE);

                    binding.caseIncludeLayout.selectTextView.setEnabled(true);
                    binding.caseIncludeLayout.selectTextView.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);
                } else if (tab.getPosition() == 1) {
                    type = "room";

                    binding.caseGroup.setVisibility(View.GONE);
                    binding.roomGroup.setVisibility(View.VISIBLE);
                    binding.bulkSampleGroup.setVisibility(View.GONE);
                } else {
                    type = "bulk";

                    binding.caseGroup.setVisibility(View.GONE);
                    binding.roomGroup.setVisibility(View.GONE);
                    binding.bulkSampleGroup.setVisibility(View.VISIBLE);

                    binding.caseIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    binding.caseIncludeLayout.selectTextView.setEnabled(false);
                    binding.caseIncludeLayout.selectTextView.setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray500);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.nameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.nameIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.nameIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.userCountIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.userCountIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.userCountIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        binding.caseTypeIncludeLayout.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                caseType = parent.getItemAtPosition(position).toString();

                if (position == 0) {
                    binding.caseIncludeLayout.selectTextView.setEnabled(false);
                    binding.caseIncludeLayout.selectTextView.setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray500);

                    binding.situationIncludeLayout.getRoot().setVisibility(View.GONE);
                } else if (position == 1 || position == 2) {
                    binding.caseIncludeLayout.selectTextView.setEnabled(false);
                    binding.caseIncludeLayout.selectTextView.setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray500);

                    binding.situationIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                } else {
                    binding.caseIncludeLayout.selectTextView.setEnabled(true);
                    binding.caseIncludeLayout.selectTextView.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);

                    binding.situationIncludeLayout.getRoot().setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.situationIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.situationIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.situationIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            casesDialog.show(requireActivity().getSupportFragmentManager(), "casesDialog");
            casesDialog.setData("cases");
        }).widget(binding.caseIncludeLayout.selectTextView);

        ClickManager.onDelayedClickListener(() -> {
            sessionsDialog.show(requireActivity().getSupportFragmentManager(), "sessionsDialog");
            sessionsDialog.setData("sessions");
        }).widget(binding.sessionIncludeLayout.selectTextView);

        binding.referenceIncludeLayout.selectRecyclerView.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                referencesDialog.show(requireActivity().getSupportFragmentManager(), "referencesDialog");
                referencesDialog.setData("references");
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            if (binding.scaleIncludeLayout.selectRecyclerView.getChildCount() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.scaleIncludeLayout.selectRecyclerView, binding.scaleIncludeLayout.errorImageView, binding.scaleIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (roomId.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.roomIncludeLayout.selectContainer, binding.roomIncludeLayout.errorImageView, binding.roomIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (binding.scaleIncludeLayout.selectRecyclerView.getChildCount() != 0 && !roomId.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.scaleIncludeLayout.selectRecyclerView, binding.scaleIncludeLayout.errorImageView, binding.scaleIncludeLayout.errorTextView);
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.roomIncludeLayout.selectContainer, binding.roomIncludeLayout.errorImageView, binding.roomIncludeLayout.errorTextView);

                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        binding.scaleGuideLayout.guideTextView.setText(StringManager.clickable(requireActivity().getResources().getString(R.string.CreateSampleFragmentScaleGuide), 220, 228, assessmentLinkSpan));

//        if (extras.getString("scales") != null) {
//            try {
//                JSONArray jsonArray = new JSONArray(extras.getString("phones"));
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                    Model model = new Model(jsonObject);
//
//                    scales.add(model);
//                }
//
//                setRecyclerView(scales, "scales");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
            setRecyclerView(scales, new ArrayList<>(), "scales");
//        }

        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            roomId = ((MainActivity) requireActivity()).singleton.getAddress();
            roomName = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.roomIncludeLayout.primaryTextView.setText(roomName);
        }
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            centerName = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.roomIncludeLayout.secondaryTextView.setText(centerName);
        }

        if (!((MainActivity) requireActivity()).singleton.getType().equals("")) {
            type = ((MainActivity) requireActivity()).singleton.getType();
            switch (type) {
                case "case":
                    binding.typeTabLayout.getTabAt(0);

                    binding.caseGroup.setVisibility(View.VISIBLE);
                    binding.roomGroup.setVisibility(View.GONE);
                    binding.bulkSampleGroup.setVisibility(View.GONE);

                    binding.caseIncludeLayout.selectTextView.setEnabled(true);
                    binding.caseIncludeLayout.selectTextView.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);
                    break;
                case "room":
                    binding.typeTabLayout.getTabAt(1);

                    binding.caseGroup.setVisibility(View.GONE);
                    binding.roomGroup.setVisibility(View.VISIBLE);
                    binding.bulkSampleGroup.setVisibility(View.GONE);
                    break;
                case "scale":
                    binding.typeTabLayout.getTabAt(2);

                    binding.caseGroup.setVisibility(View.GONE);
                    binding.roomGroup.setVisibility(View.GONE);
                    binding.bulkSampleGroup.setVisibility(View.VISIBLE);

                    binding.caseIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    binding.caseIncludeLayout.selectTextView.setEnabled(false);
                    binding.caseIncludeLayout.selectTextView.setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray500);
                    break;
            }
        }

        if (!((MainActivity) requireActivity()).singleton.getName().equals("")) {
            name = ((MainActivity) requireActivity()).singleton.getName();
            binding.nameIncludeLayout.inputEditText.setText(name);
        }
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            userCount = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.userCountIncludeLayout.inputEditText.setText(userCount);
        }
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            caseType = ((MainActivity) requireActivity()).singleton.getAddress();
            for (int i=0; i<binding.caseTypeIncludeLayout.selectSpinner.getCount(); i++) {
                if (binding.caseTypeIncludeLayout.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(caseType)) {
                    binding.caseTypeIncludeLayout.selectSpinner.setSelection(i);

                    if (i == 0) {
                        binding.caseIncludeLayout.selectTextView.setEnabled(false);
                        binding.caseIncludeLayout.selectTextView.setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray500);

                        binding.situationIncludeLayout.getRoot().setVisibility(View.GONE);
                    } else if (i == 1 && i == 2) {
                        binding.caseIncludeLayout.selectTextView.setEnabled(false);
                        binding.caseIncludeLayout.selectTextView.setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray500);

                        binding.situationIncludeLayout.getRoot().setVisibility(View.VISIBLE);
                    } else {
                        binding.caseIncludeLayout.selectTextView.setEnabled(true);
                        binding.caseIncludeLayout.selectTextView.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray500);

                        binding.situationIncludeLayout.getRoot().setVisibility(View.GONE);
                    }
                }
            }
        }
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            situation = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.situationIncludeLayout.inputEditText.setText(situation);
        }
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            caseId = ((MainActivity) requireActivity()).singleton.getAddress();
            caseName = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.caseIncludeLayout.selectTextView.setText(caseName);
        }
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            sessionId = ((MainActivity) requireActivity()).singleton.getAddress();
            sessionName = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.sessionIncludeLayout.selectTextView.setText(sessionName);
        }

//        if (extras.getString("references") != null) {
//            try {
//                JSONArray jsonArray = new JSONArray(extras.getString("references"));
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                    Model model = new Model(jsonObject);
//
//                    references.add(model);
//                }
//
//                setRecyclerView(references, "references");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
            setRecyclerView(references, new ArrayList<>(), "references");
//        }

    }

    private void setRecyclerView(ArrayList<Model> items, ArrayList<String> ids, String method) {
        if (method.equals("scales")) {
            scalesAdapter.setItems(items, ids, method);
            binding.scaleIncludeLayout.selectRecyclerView.setAdapter(scalesAdapter);
        } else if (method.equals("references")) {
            referencesAdapter.setItems(items, ids, method);
            binding.referenceIncludeLayout.selectRecyclerView.setAdapter(referencesAdapter);
        }
    }

    public void responseDialog(String method, Model item) {
        try {
            switch (method) {
                case "scales":
                    int scalePosition = scalesAdapter.getIds().indexOf(item.get("id").toString());

                    if (scalePosition == -1)
                        scalesAdapter.addItem(item);
                    else
                        scalesAdapter.removeItem(scalePosition);
                    break;
                case "rooms":
                    if (!roomId.equals(item.get("id").toString())) {
                        roomId = item.get("id").toString();
                        roomName = item.get("title").toString();
                        centerName = item.get("subtitle").toString();

                        binding.roomIncludeLayout.primaryTextView.setText(roomName);
                        binding.roomIncludeLayout.secondaryTextView.setText(centerName);
                    } else if (roomId.equals(item.get("id").toString())) {
                        roomId = "";
                        roomName = "";
                        centerName = "";

                        binding.roomIncludeLayout.primaryTextView.setText("");
                        binding.roomIncludeLayout.secondaryTextView.setText("");
                    }

                    roomsDialog.dismiss();
                    break;
                case "references":
                    int referencePosition = referencesAdapter.getIds().indexOf(item.get("id").toString());

                    if (referencePosition == -1)
                        referencesAdapter.addItem(item);
                    else
                        referencesAdapter.removeItem(referencePosition);
                    break;
                case "cases":
                    if (!caseId.equals(item.get("id").toString())) {
                        caseId = item.get("id").toString();
                        caseName = item.get("title").toString();

                        binding.caseIncludeLayout.selectTextView.setText(caseName);
                    } else if (caseId.equals(item.get("id").toString())) {
                        caseId = "";
                        caseName = "";

                        binding.caseIncludeLayout.selectTextView.setText("");
                    }

                    casesDialog.dismiss();
                    break;
                case "sessions":
                    if (!sessionId.equals(item.get("id").toString())) {
                        sessionId = item.get("id").toString();
                        sessionName = item.get("title").toString();

                        binding.sessionIncludeLayout.selectTextView.setText(sessionName);
                    } else if (sessionId.equals(item.get("id").toString())) {
                        sessionId = "";
                        sessionName = "";

                        binding.sessionIncludeLayout.selectTextView.setText("");
                    }

                    sessionsDialog.dismiss();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void doWork() {
        name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();
        userCount = binding.userCountIncludeLayout.inputEditText.getText().toString().trim();
        situation = binding.situationIncludeLayout.inputEditText.getText().toString().trim();

        // TODO : Call Work Method
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}