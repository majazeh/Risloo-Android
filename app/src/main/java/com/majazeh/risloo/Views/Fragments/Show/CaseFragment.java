package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.PsychologistsAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.ReferencesAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Samples2Adapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Sessions2Adapter;
import com.majazeh.risloo.databinding.FragmentCaseBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Case;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.CaseModel;

import org.json.JSONException;

import java.util.HashMap;

public class CaseFragment extends Fragment {

    // Binding
    private FragmentCaseBinding binding;

    // Adapters
    private PsychologistsAdapter psychologistsAdapter;
    private ReferencesAdapter referencesAdapter;
    private Sessions2Adapter sessions2Adapter;
    private Samples2Adapter samples2Adapter;

    // Vars
    private HashMap data, header;
    private CaseModel caseModel;

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
        sessions2Adapter = new Sessions2Adapter(requireActivity());
        samples2Adapter = new Samples2Adapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        InitManager.txtTextColor(binding.reportsTextView.getRoot(), getResources().getString(R.string.CaseFragmentReports), getResources().getColor(R.color.Blue600));

        binding.psychologistsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.PsychologistsAdapterHeader));
        binding.referencesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.ReferencesAdapterHeader));
        binding.sessionsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Sessions2AdapterHeader));
        binding.samplesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Samples2AdapterHeader));

        InitManager.imgResTint(requireActivity(), binding.referencesAddImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);
        InitManager.imgResTint(requireActivity(), binding.sessionsAddImageView.getRoot(), R.drawable.ic_plus_light, R.color.White);
        InitManager.imgResTint(requireActivity(), binding.samplesAddImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.psychologistsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.referencesSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.sessionsSingleLayout.recyclerView, 0, 0, 0, 0);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.samplesSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.reportsTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_blue600_ripple_blue300);

            binding.referencesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_green700_ripple_green300);
            binding.sessionsAddImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600_ripple_white);
            binding.samplesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            binding.reportsTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_blue600);

            binding.referencesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_green700);
            binding.sessionsAddImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600);
            binding.samplesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_green700);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onClickListener(() -> {
            NavDirections action = CaseFragmentDirections.actionCaseFragmentToClientReportsFragment(caseModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.reportsTextView.getRoot());

        ClickManager.onClickListener(() -> {
            NavDirections action = CaseFragmentDirections.actionCaseFragmentToCreateCaseUserFragment(caseModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.referencesAddImageView.getRoot());

        ClickManager.onClickListener(() -> {
            NavDirections action = CaseFragmentDirections.actionCaseFragmentToCreateSessionFragment(caseModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.sessionsAddImageView.getRoot());

        ClickManager.onClickListener(() -> {
            NavDirections action = CaseFragmentDirections.actionCaseFragmentToCreateSampleFragment(null, caseModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.samplesAddImageView.getRoot());
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
            }

            if (model.getDetail() != null && model.getDetail().has("problem") && !model.getDetail().isNull("problem")) {
                binding.descriptionTextView.setText(model.getDetail().getString("problem"));
            }

            if (model.getCaseCreated_at() != 0) {
                binding.dateTextView.setText(DateManager.jalYYYYsNMMsDDsNDDsHHsMM(String.valueOf(model.getCaseCreated_at()), " "));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                            psychologistsAdapter.setPsychologists(psychologists.data());
                            binding.psychologistsSingleLayout.recyclerView.setAdapter(psychologistsAdapter);
                        }

                        // References Data
                        if (caseModel.getClients() != null && caseModel.getClients().data().size() != 0) {
                            referencesAdapter.setReferences(caseModel.getClients().data());
                            binding.referencesSingleLayout.recyclerView.setAdapter(referencesAdapter);
                        }

                        // Sessions Data
                        if (!caseModel.getSessions().data().isEmpty()) {
                            sessions2Adapter.setSessions(caseModel.getSessions().data());
                            binding.sessionsSingleLayout.recyclerView.setAdapter(sessions2Adapter);
                        }

                        // Samples Data
                        if (!caseModel.getSamples().data().isEmpty()) {
                            samples2Adapter.setSamples(caseModel.getSamples().data());
                            binding.samplesSingleLayout.recyclerView.setAdapter(samples2Adapter);
                        }

                        binding.psychologistsHeaderIncludeLayout.countTextView.setText(StringManager.bracing(psychologistsAdapter.getItemCount()));
                        binding.referencesHeaderIncludeLayout.countTextView.setText(StringManager.bracing(referencesAdapter.getItemCount()));
                        binding.sessionsHeaderIncludeLayout.countTextView.setText(StringManager.bracing(sessions2Adapter.getItemCount()));
                        binding.samplesHeaderIncludeLayout.countTextView.setText(StringManager.bracing(samples2Adapter.getItemCount()));

                        // Psychologists Data
                        binding.psychologistsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.psychologistsShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.psychologistsShimmerLayout.getRoot().stopShimmer();

                        // References Data
                        binding.referencesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.referencesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.referencesShimmerLayout.getRoot().stopShimmer();

                        // Sessions Data
                        binding.sessionsHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.sessionsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.sessionsShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.sessionsShimmerLayout.getRoot().stopShimmer();

                        // Samples Data
                        binding.samplesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
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
                        binding.sessionsHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.sessionsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.sessionsShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.sessionsShimmerLayout.getRoot().stopShimmer();

                        // Samples Data
                        binding.samplesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
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
    }

}