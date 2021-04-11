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
import com.majazeh.risloo.Views.Adapters.Recycler.ScalesDialogAdapter;
import com.majazeh.risloo.Views.Dialogs.ScaleDialog;
import com.majazeh.risloo.databinding.FragmentCreateSampleBinding;

import java.util.ArrayList;

public class CreateSampleFragment extends Fragment {

    // Binding
    public FragmentCreateSampleBinding binding;

    // Adapters
    public ScalesDialogAdapter scalesDialogAdapter;

    // Dialogs
    private ScaleDialog scaleDialog;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager scalesLayoutManager;
    private ClickableSpan assessmentLinkSpan;

    // Vars
    private ArrayList<Model> scales = new ArrayList<>();
    private String room = "", center = "", type = "case", name = "", userCount = "", caseType = "", situation = "", casse = "",  session = "";

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
        scaleDialog = new ScaleDialog();

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", 0, 0, (int) getResources().getDimension(R.dimen._2sdp), 0);

        scalesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

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

        InitManager.spinner(requireActivity(), binding.caseTypeIncludeLayout.selectSpinner, R.array.CasesTypes);

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
        ClickManager.onDelayedClickListener(() -> {
            scaleDialog.show(requireActivity().getSupportFragmentManager(), "scaleDialog");
        }).widget(binding.scaleIncludeLayout.selectRecyclerView);

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
            // TODO : Place Code Here
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
            // TODO : Place Code Here
        }).widget(binding.caseIncludeLayout.selectTextView);

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.sessionIncludeLayout.selectTextView);

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.referenceIncludeLayout.selectRecyclerView);

        ClickManager.onDelayedClickListener(() -> {
            if (binding.scaleIncludeLayout.selectRecyclerView.getChildCount() == 0) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.scaleIncludeLayout.selectRecyclerView, binding.scaleIncludeLayout.errorImageView, binding.scaleIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }
            if (room.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.roomIncludeLayout.selectContainer, binding.roomIncludeLayout.errorImageView, binding.roomIncludeLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            }

            if (binding.scaleIncludeLayout.selectRecyclerView.getChildCount() != 0 && !room.equals("")) {
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
//                JSONArray scales = new JSONArray(extras.getString("phones"));
//
//                for (int i = 0; i < scales.length(); i++) {
//                    JSONObject scale = (JSONObject) scales.get(i);
//                    Model model = new Model(scale);
//
//                    this.scales.add(model);
//                }
//
//                setRecyclerView();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
            setRecyclerView();
//        }

        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            room = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.roomIncludeLayout.primaryTextView.setText(room);
        }
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            center = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.roomIncludeLayout.secondaryTextView.setText(center);
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
            casse = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.caseIncludeLayout.selectTextView.setText(casse);
        }
        if (!((MainActivity) requireActivity()).singleton.getAddress().equals("")) {
            session = ((MainActivity) requireActivity()).singleton.getAddress();
            binding.sessionIncludeLayout.selectTextView.setText(session);
        }

        // TODO : Set References Here

    }

    private void setRecyclerView() {
        scalesDialogAdapter.setScales(scales);
        binding.scaleIncludeLayout.selectRecyclerView.setAdapter(scalesDialogAdapter);
    }

    private void doWork() {
        scales = scalesDialogAdapter.getScales();

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