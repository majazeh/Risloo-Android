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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
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

    // Objects
    private RecyclerView.ItemDecoration itemDecoration, itemDecoration2;
    private LinearLayoutManager psychologistsLayoutManager, referencesLayoutManager, sessions2LayoutManager, samples2LayoutManager;
    private Bundle extras;

    // Vars
    private HashMap data, header;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCaseBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setExtra();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        psychologistsAdapter = new PsychologistsAdapter(requireActivity());
        referencesAdapter = new ReferencesAdapter(requireActivity());
        sessions2Adapter = new Sessions2Adapter(requireActivity());
        samples2Adapter = new Samples2Adapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));
        itemDecoration2 = new ItemDecorateRecyclerView("verticalLayout", 0, 0, 0, 0);

        psychologistsLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        referencesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        sessions2LayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        samples2LayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        extras = new Bundle();

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

        InitManager.recyclerView(binding.psychologistsSingleLayout.recyclerView, itemDecoration, psychologistsLayoutManager);
        InitManager.recyclerView(binding.referencesSingleLayout.recyclerView, itemDecoration, referencesLayoutManager);
        InitManager.recyclerView(binding.sessionsSingleLayout.recyclerView, itemDecoration2, sessions2LayoutManager);
        InitManager.recyclerView(binding.samplesSingleLayout.recyclerView, itemDecoration2, samples2LayoutManager);
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
        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.clientReportsFragment, extras)).widget(binding.reportsTextView.getRoot());

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createCaseUserFragment, extras)).widget(binding.referencesAddImageView.getRoot());

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createSessionFragment, extras)).widget(binding.sessionsAddImageView.getRoot());

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createSampleFragment, extras)).widget(binding.samplesAddImageView.getRoot());
    }

    private void setExtra() {
        if (getArguments() != null) {
            if (getArguments().getString("id") != null && !getArguments().getString("id").equals("")) {
                extras.putString("id", getArguments().getString("id"));
                data.put("id", getArguments().getString("id"));
                binding.serialTextView.setText(getArguments().getString("id"));
            }

            if (getArguments().getString("manager_id") != null && !getArguments().getString("manager_id").equals("") && getArguments().getString("manager_name") != null && !getArguments().getString("manager_name").equals("")) {
                extras.putString("manager_id", getArguments().getString("manager_id"));
                extras.putString("manager_name", getArguments().getString("manager_name"));
            }

            if (getArguments().getString("room_id") != null && !getArguments().getString("room_id").equals("")) {
                extras.putString("room_id", getArguments().getString("room_id"));
            }

            if (getArguments().getString("clients") != null && !getArguments().getString("clients").equals("")) {
                extras.putString("clients", getArguments().getString("clients"));
            }

            if (getArguments().getString("problem") != null && !getArguments().getString("problem").equals("")) {
                extras.putString("problem", getArguments().getString("problem"));
                binding.descriptionTextView.setText(getArguments().getString("problem"));
            }

            if (getArguments().getString("session_count") != null && !getArguments().getString("session_count").equals("")) {
                extras.putString("session_count", getArguments().getString("session_count"));
            }

            if (getArguments().getInt("created_at") != 0) {
                extras.putInt("created_at", getArguments().getInt("created_at"));
                binding.dateTextView.setText(DateManager.gregorianToJalali6(DateManager.dateToString("yyyy-MM-dd HH:mm:ss", DateManager.timestampToDate(getArguments().getInt("created_at")))));
            }
        }
    }

    private void setData(CaseModel model) {
        try {
            if (model.getCaseManager().getUserId() != null && model.getCaseManager().getName() != null) {
                extras.putString("manager_id", model.getCaseManager().getUserId());
                extras.putString("manager_name", model.getCaseManager().getName());
            }

            if (model.getCaseRoom() != null && model.getCaseRoom().getRoomId() != null) {
                extras.putString("room_id", model.getCaseRoom().getRoomId());
            }

            if (model.getClients() != null && model.getClients().data().size() != 0) {
                extras.putString("clients", String.valueOf(model.getClients()));
            }

            if (model.getDetail() != null && model.getDetail().has("problem") && !model.getDetail().isNull("problem")) {
                extras.putString("problem", model.getDetail().getString("problem"));
                binding.descriptionTextView.setText(model.getDetail().getString("problem"));
            }

            if (model.getSessions_count() != 0) {
                extras.putString("session_count", String.valueOf(model.getSessions_count()));
            }

            if (model.getCaseCreated_at() != 0) {
                extras.putInt("created_at", model.getCaseCreated_at());
                binding.dateTextView.setText(DateManager.gregorianToJalali6(DateManager.dateToString("yyyy-MM-dd HH:mm:ss", DateManager.timestampToDate(model.getCaseCreated_at()))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getData() {
        Case.showDashborad(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                CaseModel model = (CaseModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        setData(model);

                        List psychologists = new List();
                        if (model.getCaseManager() != null)
                            psychologists.add(model.getCaseManager());

                        // Psychologists Data
                        if (!psychologists.data().isEmpty()) {
                            psychologistsAdapter.setPsychologists(psychologists.data());
                            binding.psychologistsSingleLayout.recyclerView.setAdapter(psychologistsAdapter);
                        }

                        // References Data
                        if (model.getClients() != null && model.getClients().data().size() != 0) {
                            referencesAdapter.setReferences(model.getClients().data());
                            binding.referencesSingleLayout.recyclerView.setAdapter(referencesAdapter);
                        }

                        // Sessions Data
                        if (!model.getSessions().data().isEmpty()) {
                            sessions2Adapter.setSessions(model.getSessions().data());
                            binding.sessionsSingleLayout.recyclerView.setAdapter(sessions2Adapter);
                        }

                        // Samples Data
                        if (!model.getSamples().data().isEmpty()) {
                            samples2Adapter.setSamples(model.getSamples().data());
                            binding.samplesSingleLayout.recyclerView.setAdapter(samples2Adapter);
                        }

                        binding.psychologistsHeaderIncludeLayout.countTextView.setText("(" + psychologistsAdapter.getItemCount() + ")");
                        binding.referencesHeaderIncludeLayout.countTextView.setText("(" + referencesAdapter.getItemCount() + ")");
                        binding.sessionsHeaderIncludeLayout.countTextView.setText("(" + sessions2Adapter.getItemCount() + ")");
                        binding.samplesHeaderIncludeLayout.countTextView.setText("(" + samples2Adapter.getItemCount() + ")");

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