package com.majazeh.risloo.views.fragments.main.show;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.DateManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.main.Index.IndexPsychologistAdapter;
import com.majazeh.risloo.views.adapters.recycler.main.Index.IndexReferenceAdapter;
import com.majazeh.risloo.views.adapters.recycler.main.Table.TableSampleAdapter;
import com.majazeh.risloo.views.adapters.recycler.main.Table.TableSession2Adapter;
import com.majazeh.risloo.databinding.FragmentCaseBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Case;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.TagModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentCase extends Fragment {

    // Binding
    private FragmentCaseBinding binding;

    // Adapters
    private IndexPsychologistAdapter indexPsychologistAdapter;
    private IndexReferenceAdapter indexReferenceAdapter;
    private TableSession2Adapter tableSession2Adapter;
    private TableSampleAdapter tableSampleAdapter;

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

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        indexPsychologistAdapter = new IndexPsychologistAdapter(requireActivity());
        indexReferenceAdapter = new IndexReferenceAdapter(requireActivity());
        tableSession2Adapter = new TableSession2Adapter(requireActivity());
        tableSampleAdapter = new TableSampleAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.psychologistsHeaderLayout.titleTextView.setText(getResources().getString(R.string.PsychologistAdapterHeader));
        binding.referencesHeaderLayout.titleTextView.setText(getResources().getString(R.string.ReferenceAdapterHeader));
        binding.sessionsHeaderLayout.titleTextView.setText(getResources().getString(R.string.Session2AdapterHeader));
        binding.samplesHeaderLayout.titleTextView.setText(getResources().getString(R.string.SamplesFragmentTitle));

        binding.sessionsShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);
        binding.samplesShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);

        InitManager.imgResTintBackground(requireActivity(), binding.referencesAddView.getRoot(), R.drawable.ic_plus_light, R.color.white, R.drawable.draw_oval_solid_emerald600_ripple_white);
        InitManager.imgResTintBackground(requireActivity(), binding.sessionsAddView.getRoot(), R.drawable.ic_plus_light, R.color.white, R.drawable.draw_oval_solid_emerald600_ripple_white);
        InitManager.imgResTintBackground(requireActivity(), binding.samplesAddView.getRoot(), R.drawable.ic_plus_light, R.color.white, R.drawable.draw_oval_solid_emerald600_ripple_white);

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.psychologistsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.referencesSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.sessionsSingleLayout.recyclerView, 0, 0, 0, 0);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.samplesSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onClickListener(() -> {
            switch (binding.menuSpinner.selectImageView.getTag().toString()) {
                case "گزارشات":
                    ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentClientReports(caseModel);
                    break;
                case "ویرایش":
                    // TODO : Place Code Here
                    break;
            }
        }).widget(binding.menuSpinner.selectImageView);

        binding.menuSpinner.selectSpinner.setOnTouchListener((v, event) -> {
            binding.menuSpinner.selectSpinner.setSelection(binding.menuSpinner.selectSpinner.getAdapter().getCount());
            userSelect = true;
            return false;
        });

        binding.menuSpinner.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.menuSpinner.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    switch (pos) {
                        case "گزارشات":
                            ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentClientReports(caseModel);
                            break;
                        case "ویرایش":
                            // TODO : Place Code Here
                            break;
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
            ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentCreateCaseUser(caseModel);
        }).widget(binding.referencesAddView.getRoot());

        CustomClickView.onClickListener(() -> {
            ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentCreateSession(caseModel);
        }).widget(binding.sessionsAddView.getRoot());

        CustomClickView.onClickListener(() -> {
            ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentCreateSample(caseModel);
        }).widget(binding.samplesAddView.getRoot());
    }

    private void setArgs() {
        caseModel = (CaseModel) FragmentCaseArgs.fromBundle(getArguments()).getTypeModel();
        setData(caseModel);
    }

    private void setData(CaseModel model) {
        try {
            if (model.getId() != null && !model.getId().equals("")) {
                binding.serialTextView.setText(model.getId());
                data.put("id", model.getId());
                data.put("session_platforms", 1);
            }

            if (model.getDetail() != null && model.getDetail().has("problem") && !model.getDetail().getString("problem").equals("") && !model.getDetail().getString("problem").equals("")) {
                binding.problemTextView.setText(model.getDetail().getString("problem"));
            }

            if (model.getCreatedAt() != 0) {
                binding.dateTextView.setText(DateManager.jalYYYYsNMMsDDsNDDsHHsMM(String.valueOf(model.getCreatedAt()), " "));
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

//        if (((MainActivity) requireActivity()).permissoon.showCaseDropdownReports(((MainActivity) requireActivity()).singleton.getUserModel(), caseModel))
//            items.add(requireActivity().getResources().getString(R.string.CaseFragmentReports));

//        if (((MainActivity) requireActivity()).permissoon.showCaseDropdownEdit(((MainActivity) requireActivity()).singleton.getUserModel(), caseModel))
//            items.add(requireActivity().getResources().getString(R.string.CaseFragmentEdit));

        items.add("");

        if (items.size() > 2) {
            InitManager.spinnerOvalEnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_ellipsis_v_light, R.color.coolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300);
            InitManager.selectCustomActionSpinner(requireActivity(), binding.menuSpinner.selectSpinner, items);
        } else if (items.size() == 2) {
            switch (items.get(0)) {
                case "گزارشات":
                    InitManager.spinnerOvalUnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_clipboard_light, R.color.coolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "ویرایش":
                    InitManager.spinnerOvalUnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_edit_light, R.color.coolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
            }
        } else {
            binding.menuSpinner.getRoot().setVisibility(View.GONE);
        }
    }

    private void setPermission() {
        if (((ActivityMain) requireActivity()).permissoon.showCaseCreateReference(((ActivityMain) requireActivity()).singleton.getUserModel(), caseModel))
            binding.referencesAddView.getRoot().setVisibility(View.VISIBLE);
        else
            binding.referencesAddView.getRoot().setVisibility(View.GONE);

        if (((ActivityMain) requireActivity()).permissoon.showCaseCreateSession(((ActivityMain) requireActivity()).singleton.getUserModel(), caseModel))
            binding.sessionsAddView.getRoot().setVisibility(View.VISIBLE);
        else
            binding.sessionsAddView.getRoot().setVisibility(View.GONE);

        if (((ActivityMain) requireActivity()).permissoon.showCaseCreateSample(((ActivityMain) requireActivity()).singleton.getUserModel(), caseModel))
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
                        if (caseModel.getManager() != null)
                            psychologists.add(caseModel.getManager());

                        // Psychologists Data
                        if (!psychologists.data().isEmpty()) {
                            indexPsychologistAdapter.setItems(psychologists.data());
                            binding.psychologistsSingleLayout.recyclerView.setAdapter(indexPsychologistAdapter);

                            binding.psychologistsSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (indexPsychologistAdapter.getItemCount() == 0) {
                            binding.psychologistsSingleLayout.recyclerView.setAdapter(null);

                            binding.psychologistsSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.psychologistsSingleLayout.emptyView.setText(getResources().getString(R.string.PsychologistAdapterEmpty));
                        }

                        // References Data
                        if (caseModel.getClients() != null && caseModel.getClients().data().size() != 0) {
                            indexReferenceAdapter.setItems(caseModel.getClients().data());
                            binding.referencesSingleLayout.recyclerView.setAdapter(indexReferenceAdapter);

                            binding.referencesSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (indexReferenceAdapter.getItemCount() == 0) {
                            binding.referencesSingleLayout.recyclerView.setAdapter(null);

                            binding.referencesSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.referencesSingleLayout.emptyView.setText(getResources().getString(R.string.ReferenceAdapterEmpty));
                        }

                        // Sessions Data
                        if (!caseModel.getSessions().data().isEmpty()) {
                            tableSession2Adapter.setItems(caseModel.getSessions().data());
                            binding.sessionsSingleLayout.recyclerView.setAdapter(tableSession2Adapter);

                            binding.sessionsSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (tableSession2Adapter.getItemCount() == 0) {
                            binding.sessionsSingleLayout.recyclerView.setAdapter(null);

                            binding.sessionsSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.sessionsSingleLayout.emptyView.setText(getResources().getString(R.string.Sessions2AdapterEmpty));
                        }

                        // Samples Data
                        if (!caseModel.getSamples().data().isEmpty()) {
                            tableSampleAdapter.setItems(caseModel.getSamples().data());
                            binding.samplesSingleLayout.recyclerView.setAdapter(tableSampleAdapter);

                            binding.samplesSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (tableSampleAdapter.getItemCount() == 0) {
                            binding.samplesSingleLayout.recyclerView.setAdapter(null);

                            binding.samplesSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.samplesSingleLayout.emptyView.setText(getResources().getString(R.string.SamplesFragmentEmpty));
                        }

                        binding.psychologistsHeaderLayout.countTextView.setText(StringManager.bracing(indexPsychologistAdapter.getItemCount()));
                        binding.referencesHeaderLayout.countTextView.setText(StringManager.bracing(indexReferenceAdapter.getItemCount()));
                        binding.sessionsHeaderLayout.countTextView.setText(StringManager.bracing(tableSession2Adapter.itemsCount()));
                        binding.samplesHeaderLayout.countTextView.setText(StringManager.bracing(tableSampleAdapter.itemsCount()));

                        hideShimmer();
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        hideShimmer();
                    });
                }
            }
        });
    }

    private void hideShimmer() {

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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        userSelect = false;
    }

}