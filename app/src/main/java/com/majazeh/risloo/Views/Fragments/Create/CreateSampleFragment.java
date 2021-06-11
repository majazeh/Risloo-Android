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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.majazeh.risloo.R;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SelectedAdapter;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;
import com.majazeh.risloo.databinding.FragmentCreateSampleBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CreateSampleFragment extends Fragment {

    // Binding
    private FragmentCreateSampleBinding binding;

    // Adapters
    public SelectedAdapter scalesAdapter, referencesAdapter;

    // Dialogs
    private SearchableDialog scalesDialog, roomsDialog, referencesDialog, casesDialog, sessionsDialog;

    // Objects
    private ClickableSpan assessmentLinkSpan;

    // Vars
    private HashMap data, header;
    public String roomId = "", roomName = "", centerName = "", type = "case", name = "", userCount = "", caseType = "", situation = "", caseId = "", caseName = "",  sessionId = "", sessionName = "", psychologyDescription = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateSampleBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setClickableSpan();

        setExtra();

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

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.scaleIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentScaleHeader));
        binding.roomIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentRoomHeader));
        binding.nameIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentNameHeader));
        binding.userCountIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentUserCountHeader));
        binding.caseTypeIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentCaseTypeHeader));
        binding.situationIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentSituationHeader));
        binding.caseIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentCaseHeader));
        binding.sessionIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentSessionHeader));
        binding.referenceIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentReferenceHeader));
        binding.psychologyIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateSampleFragmentPsychologyDescriptionHeader));

        binding.nameIncludeLayout.inputEditText.setHint(getResources().getString(R.string.CreateSampleFragmentNameHint));

        binding.scaleGuideLayout.guideTextView.setMovementMethod(LinkMovementMethod.getInstance());

        binding.nameGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateSampleFragmentNameGuide));
        binding.userCountGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateSampleFragmentUserCountGuide));
        binding.psychologyGuideLayout.guideTextView.setText(getResources().getString(R.string.CreateSampleFragmentPsychologyDescriptionGuide));

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.scaleIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);
        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.referenceIncludeLayout.selectRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.fixedSpinner(requireActivity(), binding.caseTypeIncludeLayout.selectSpinner, R.array.CaseTypes, "main");

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

        binding.psychologyIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.psychologyIncludeLayout.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.psychologyIncludeLayout.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            if (binding.scaleIncludeLayout.selectRecyclerView.getChildCount() == 0)
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.scaleIncludeLayout.selectRecyclerView, binding.scaleErrorLayout.getRoot(), binding.scaleErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.scaleIncludeLayout.selectRecyclerView, binding.scaleErrorLayout.getRoot(), binding.scaleErrorLayout.errorTextView);

            if (roomId.equals(""))
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.roomIncludeLayout.selectContainer, binding.roomErrorLayout.getRoot(), binding.roomErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            else
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.roomIncludeLayout.selectContainer, binding.roomErrorLayout.getRoot(), binding.roomErrorLayout.errorTextView);

            if (binding.scaleIncludeLayout.selectRecyclerView.getChildCount() != 0 && !roomId.equals(""))
                doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setClickableSpan() {
        binding.scaleGuideLayout.guideTextView.setText(StringManager.clickable(requireActivity().getResources().getString(R.string.CreateSampleFragmentScaleGuide), 220, 228, assessmentLinkSpan));
    }

    ///////////////////////////////////////////////////

    private void setExtra() {
        if (getArguments() != null) {






            //        if (extras.getString("scales") != null) {
//            try {
//                JSONArray jsonArray = new JSONArray(extras.getString("phones"));
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                    TypeModel TypeModel = new TypeModel(jsonObject);
//
//                    scales.add(TypeModel);
//                }
//
//                setRecyclerView(scales, "scales");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
            setRecyclerView(new ArrayList<>(), new ArrayList<>(), "scales");
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
                        } else if (i == 1 || i == 2) {
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
//                    TypeModel TypeModel = new TypeModel(jsonObject);
//
//                    references.add(TypeModel);
//                }
//
//                setRecyclerView(references, "references");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
            setRecyclerView(new ArrayList<>(), new ArrayList<>(), "references");
//        }

            if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
                psychologyDescription = ((MainActivity) requireActivity()).singleton.getAddress();
                binding.psychologyIncludeLayout.inputEditText.setText(psychologyDescription);
            }







        }
    }

    ///////////////////////////////////////////////////

    private void setRecyclerView(ArrayList<TypeModel> items, ArrayList<String> ids, String method) {
        if (method.equals("scales")) {
            scalesAdapter.setItems(items, ids, method, binding.scaleIncludeLayout.countTextView);
            binding.scaleIncludeLayout.selectRecyclerView.setAdapter(scalesAdapter);
        } else if (method.equals("references")) {
            referencesAdapter.setItems(items, ids, method, binding.referenceIncludeLayout.countTextView);
            binding.referenceIncludeLayout.selectRecyclerView.setAdapter(referencesAdapter);
        }
    }

    public void responseDialog(String method, TypeModel item) {
        try {
            switch (method) {
                case "scales": {
                    SampleModel model = (SampleModel) item;

                    int position = scalesAdapter.getIds().indexOf(model.getSampleId());

                    if (position == -1)
                        scalesAdapter.addItem(item);
                    else
                        scalesAdapter.removeItem(position);

                    if (scalesAdapter.getIds().size() != 0) {
                        binding.scaleIncludeLayout.countTextView.setVisibility(View.VISIBLE);
                        binding.scaleIncludeLayout.countTextView.setText("(" + scalesAdapter.getIds().size() + ")");
                    } else {
                        binding.scaleIncludeLayout.countTextView.setVisibility(View.GONE);
                        binding.scaleIncludeLayout.countTextView.setText("");
                    }
                }
                break;
                case "rooms": {
                    RoomModel model = (RoomModel) item;

                    if (!roomId.equals(model.getRoomId())) {
                        roomId = model.getRoomId();
                        roomName = model.getRoomManager().getName();
                        centerName = model.getRoomCenter().getDetail().getString("title");

                        binding.roomIncludeLayout.primaryTextView.setText(roomName);
                        binding.roomIncludeLayout.secondaryTextView.setText(centerName);
                    } else if (roomId.equals(model.getRoomId())) {
                        roomId = "";
                        roomName = "";
                        centerName = "";

                        binding.roomIncludeLayout.primaryTextView.setText("");
                        binding.roomIncludeLayout.secondaryTextView.setText("");
                    }

                    roomsDialog.dismiss();
                }
                break;
                case "references": {
                    UserModel model = (UserModel) item;

                    int position = referencesAdapter.getIds().indexOf(model.getId());

                    if (position == -1)
                        referencesAdapter.addItem(item);
                    else
                        referencesAdapter.removeItem(position);

                    if (referencesAdapter.getIds().size() != 0) {
                        binding.referenceIncludeLayout.countTextView.setVisibility(View.VISIBLE);
                        binding.referenceIncludeLayout.countTextView.setText("(" + referencesAdapter.getIds().size() + ")");
                    } else {
                        binding.referenceIncludeLayout.countTextView.setVisibility(View.GONE);
                        binding.referenceIncludeLayout.countTextView.setText("");
                    }
                }
                break;
                case "cases": {
                    CaseModel model = (CaseModel) item;

                    if (!caseId.equals(model.getCaseId())) {
                        caseId = model.getCaseId();
                        caseName = model.getCaseId();

                        binding.caseIncludeLayout.selectTextView.setText(caseName);
                    } else if (caseId.equals(model.getCaseId())) {
                        caseId = "";
                        caseName = "";

                        binding.caseIncludeLayout.selectTextView.setText("");
                    }

                    casesDialog.dismiss();
                }
                break;
                case "sessions": {
                    SessionModel model = (SessionModel) item;

                    if (!sessionId.equals(model.getId())) {
                        sessionId = model.getId();
                        sessionName = model.getId();

                        binding.sessionIncludeLayout.selectTextView.setText(sessionName);
                    } else if (sessionId.equals(model.getId())) {
                        sessionId = "";
                        sessionName = "";

                        binding.sessionIncludeLayout.selectTextView.setText("");
                    }

                    sessionsDialog.dismiss();
                }
                break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        name = binding.nameIncludeLayout.inputEditText.getText().toString().trim();
        userCount = binding.userCountIncludeLayout.inputEditText.getText().toString().trim();
        situation = binding.situationIncludeLayout.inputEditText.getText().toString().trim();
        psychologyDescription = binding.psychologyIncludeLayout.inputEditText.getText().toString().trim();

        data.put("scale_id", scalesAdapter.getIds());
        data.put("room_id", roomId);

        Sample.create(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
                        Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppAdded), Toast.LENGTH_SHORT).show();
                        ((MainActivity) requireActivity()).navigator(R.id.samplesFragment);
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
                                            case "scale_id":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.scaleIncludeLayout.selectRecyclerView, binding.scaleErrorLayout.getRoot(), binding.scaleErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                                break;
                                            case "room_id":
                                                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.roomIncludeLayout.selectContainer, binding.roomErrorLayout.getRoot(), binding.roomErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
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