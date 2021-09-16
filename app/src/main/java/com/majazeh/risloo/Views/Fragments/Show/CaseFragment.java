package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Index.IndexSampleAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.PsychologistsAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.ReferencesAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Index.IndexSession2Adapter;
import com.majazeh.risloo.databinding.FragmentCaseBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Case;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.TagModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class CaseFragment extends Fragment {

    // Binding
    private FragmentCaseBinding binding;

    // Adapters
    private PsychologistsAdapter psychologistsAdapter;
    private ReferencesAdapter referencesAdapter;
    private IndexSession2Adapter indexSession2Adapter;
    private IndexSampleAdapter indexSampleAdapter;

    // Models
    private CaseModel caseModel;

    // Objects
    private HashMap data, header;

    // Vars
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCaseBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        psychologistsAdapter = new PsychologistsAdapter(requireActivity());
        referencesAdapter = new ReferencesAdapter(requireActivity());
        indexSession2Adapter = new IndexSession2Adapter(requireActivity());
        indexSampleAdapter = new IndexSampleAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.psychologistsHeaderLayout.titleTextView.setText(getResources().getString(R.string.PsychologistsAdapterHeader));
        binding.referencesHeaderLayout.titleTextView.setText(getResources().getString(R.string.ReferencesAdapterHeader));
        binding.sessionsHeaderLayout.titleTextView.setText(getResources().getString(R.string.Sessions2AdapterHeader));
        binding.samplesHeaderLayout.titleTextView.setText(getResources().getString(R.string.SamplesFragmentTitle));

        binding.sessionsShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);
        binding.samplesShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);

        InitManager.imgResTint(requireActivity(), binding.menuSpinner.selectImageView, R.drawable.ic_ellipsis_v_light, R.color.Gray500);
        InitManager.imgResTint(requireActivity(), binding.referencesAddView.getRoot(), R.drawable.ic_plus_light, R.color.White);
        InitManager.imgResTint(requireActivity(), binding.sessionsAddView.getRoot(), R.drawable.ic_plus_light, R.color.White);
        InitManager.imgResTint(requireActivity(), binding.samplesAddView.getRoot(), R.drawable.ic_plus_light, R.color.White);

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.psychologistsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.referencesSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.sessionsSingleLayout.recyclerView, 0, 0, 0, 0);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.samplesSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.menuSpinner.selectImageView.setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_gray300);

            binding.referencesAddView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600_ripple_white);
            binding.sessionsAddView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600_ripple_white);
            binding.samplesAddView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600_ripple_white);
        } else {
            binding.menuSpinner.selectImageView.setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_gray300);

            binding.referencesAddView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600);
            binding.sessionsAddView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600);
            binding.samplesAddView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.menuSpinner.selectSpinner.setOnTouchListener((v, event) -> {
            binding.menuSpinner.selectSpinner.setSelection(binding.menuSpinner.selectSpinner.getAdapter().getCount());
            userSelect = true;
            return false;
        });

        binding.menuSpinner.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    switch (pos) {
                        case "گزارشات": {
                            NavDirections action = NavigationMainDirections.actionGlobalClientReportsFragment( caseModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } break;
                        case "ویرایش": {
                            // TODO : Place Code Here
                        } break;
                    }

                    parent.setSelection(parent.getAdapter().getCount());

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCreateCaseUserFragment(caseModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.referencesAddView.getRoot());

        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCreateSessionFragment(caseModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.sessionsAddView.getRoot());

        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCreateSampleFragment(caseModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.samplesAddView.getRoot());
    }

    private void setArgs() {
        caseModel = (CaseModel) CaseFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(caseModel);
    }

    private void setData(CaseModel model) {
        try {
            if (model.getCaseId() != null && !model.getCaseId().equals("")) {
                binding.serialTextView.setText(model.getCaseId());
                data.put("id", model.getCaseId());
                data.put("session_platforms", 1);
            }

            if (model.getDetail() != null && model.getDetail().has("problem") && !model.getDetail().isNull("problem")) {
                binding.problemTextView.setText(model.getDetail().getString("problem"));
            }

            if (model.getCaseCreated_at() != 0) {
                binding.dateTextView.setText(DateManager.jalYYYYsNMMsDDsNDDsHHsMM(String.valueOf(model.getCaseCreated_at()), " "));
            }

            if (model.getTags() != null && model.getTags().data().size() != 0) {
                binding.info2ConstraintLayout.setVisibility(View.VISIBLE);
                binding.tagTextView.setText("");
                for (int i = 0; i < model.getTags().data().size(); i++) {
                    TagModel tag = (TagModel) model.getTags().data().get(i);
                    if (tag != null) {
                        binding.tagTextView.append(tag.getTitle());
                        if (i != model.getTags().data().size() - 1) {
                            binding.tagTextView.append("  |  ");
                        }
                    }
                }
            } else {
                binding.info2ConstraintLayout.setVisibility(View.GONE);
            }

            setDropdown();
            setPermission();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDropdown() {
        ArrayList<String> items = new ArrayList<>();

        if (((MainActivity) requireActivity()).permissoon.showCaseDropdownReports(((MainActivity) requireActivity()).singleton.getUserModel(), caseModel))
            items.add(requireActivity().getResources().getString(R.string.CaseFragmentReports));

        if (((MainActivity) requireActivity()).permissoon.showCaseDropdownEdit(((MainActivity) requireActivity()).singleton.getUserModel(), caseModel))
            items.add(requireActivity().getResources().getString(R.string.CaseFragmentEdit));

        items.add("");

        InitManager.actionCustomSpinner(requireActivity(), binding.menuSpinner.selectSpinner, items);

        if (items.size() > 1)
            binding.menuSpinner.getRoot().setVisibility(View.VISIBLE);
        else
            binding.menuSpinner.getRoot().setVisibility(View.GONE);
    }

    private void setPermission() {
        if (((MainActivity) requireActivity()).permissoon.showCaseCreateSession(((MainActivity) requireActivity()).singleton.getUserModel(), caseModel))
            binding.sessionsAddView.getRoot().setVisibility(View.VISIBLE);
        else
            binding.sessionsAddView.getRoot().setVisibility(View.GONE);

        if (((MainActivity) requireActivity()).permissoon.showCaseCreateSample(((MainActivity) requireActivity()).singleton.getUserModel(), caseModel))
            binding.samplesAddView.getRoot().setVisibility(View.VISIBLE);
        else
            binding.samplesAddView.getRoot().setVisibility(View.GONE);
    }

    private void getData() {
        Case.showDashborad(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                caseModel = (CaseModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        setData(caseModel);

                        List psychologists = new List();
                        if (caseModel.getCaseManager() != null)
                            psychologists.add(caseModel.getCaseManager());

                        // Psychologists Data
                        if (!psychologists.data().isEmpty()) {
                            psychologistsAdapter.setItems(psychologists.data());
                            binding.psychologistsSingleLayout.recyclerView.setAdapter(psychologistsAdapter);

                            binding.psychologistsSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (psychologistsAdapter.getItemCount() == 0) {
                            binding.psychologistsSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.psychologistsSingleLayout.emptyView.setText(getResources().getString(R.string.PsychologistsAdapterEmpty));
                        }

                        // References Data
                        if (caseModel.getClients() != null && caseModel.getClients().data().size() != 0) {
                            referencesAdapter.setItems(caseModel.getClients().data());
                            binding.referencesSingleLayout.recyclerView.setAdapter(referencesAdapter);

                            binding.referencesSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (referencesAdapter.getItemCount() == 0) {
                            binding.referencesSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.referencesSingleLayout.emptyView.setText(getResources().getString(R.string.ReferencesAdapterEmpty));
                        }

                        // Sessions Data
                        if (!caseModel.getSessions().data().isEmpty()) {
                            indexSession2Adapter.setItems(caseModel.getSessions().data());
                            binding.sessionsSingleLayout.recyclerView.setAdapter(indexSession2Adapter);

                            binding.sessionsSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (indexSession2Adapter.getItemCount() == 0) {
                            binding.sessionsSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.sessionsSingleLayout.emptyView.setText(getResources().getString(R.string.Sessions2AdapterEmpty));
                        }

                        // Samples Data
                        if (!caseModel.getSamples().data().isEmpty()) {
                            indexSampleAdapter.setItems(caseModel.getSamples().data());
                            binding.samplesSingleLayout.recyclerView.setAdapter(indexSampleAdapter);

                            binding.samplesSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (indexSampleAdapter.getItemCount() == 0) {
                            binding.samplesSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.samplesSingleLayout.emptyView.setText(getResources().getString(R.string.SamplesFragmentEmpty));
                        }

                        binding.psychologistsHeaderLayout.countTextView.setText(StringManager.bracing(psychologistsAdapter.getItemCount()));
                        binding.referencesHeaderLayout.countTextView.setText(StringManager.bracing(referencesAdapter.getItemCount()));
                        binding.sessionsHeaderLayout.countTextView.setText(StringManager.bracing(indexSession2Adapter.itemsCount()));
                        binding.samplesHeaderLayout.countTextView.setText(StringManager.bracing(indexSampleAdapter.itemsCount()));

                        // Psychologists Data
                        binding.psychologistsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.psychologistsShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.psychologistsShimmerLayout.getRoot().stopShimmer();

                        // References Data
                        binding.referencesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.referencesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.referencesShimmerLayout.getRoot().stopShimmer();

                        // Sessions Data
                        binding.sessionsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.sessionsShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.sessionsShimmerLayout.getRoot().stopShimmer();

                        // Samples Data
                        binding.samplesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.samplesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.samplesShimmerLayout.getRoot().stopShimmer();

                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {

                        // Psychologists Data
                        binding.psychologistsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.psychologistsShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.psychologistsShimmerLayout.getRoot().stopShimmer();

                        // References Data
                        binding.referencesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.referencesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.referencesShimmerLayout.getRoot().stopShimmer();

                        // Sessions Data
                        binding.sessionsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.sessionsShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.sessionsShimmerLayout.getRoot().stopShimmer();

                        // Samples Data
                        binding.samplesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.samplesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.samplesShimmerLayout.getRoot().stopShimmer();

                    });
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        userSelect = false;
    }

}