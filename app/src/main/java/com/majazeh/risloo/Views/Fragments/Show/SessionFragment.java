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
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.PlatformsAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.PracticesAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.PsychologistsAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Samples2Adapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Users2Adapter;
import com.majazeh.risloo.databinding.FragmentSessionBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Session;
import com.mre.ligheh.Model.TypeModel.SessionModel;

import java.util.ArrayList;
import java.util.HashMap;

public class SessionFragment extends Fragment {

    // Binding
    private FragmentSessionBinding binding;

    // Adapters
    private PlatformsAdapter platformsAdapter;
    private PsychologistsAdapter psychologistsAdapter;
    private Users2Adapter users2Adapter;
    private PracticesAdapter practicesAdapter;
    private Samples2Adapter samples2Adapter;

    // Models
    public SessionModel sessionModel;

    // Objects
    private HashMap data, header;

    // Vars
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        platformsAdapter = new PlatformsAdapter(requireActivity());
        psychologistsAdapter = new PsychologistsAdapter(requireActivity());
        users2Adapter = new Users2Adapter(requireActivity());
        practicesAdapter = new PracticesAdapter(requireActivity());
        samples2Adapter = new Samples2Adapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.platformsHeaderLayout.titleTextView.setText(getResources().getString(R.string.PlatformsAdapterHeader));
        binding.psychologistsHeaderLayout.titleTextView.setText(getResources().getString(R.string.PsychologistsAdapterHeader));
        binding.usersHeaderLayout.titleTextView.setText(getResources().getString(R.string.Users2AdapterHeader));
        binding.practicesHeaderLayout.titleTextView.setText(getResources().getString(R.string.PracticesAdapterHeader));
        binding.samplesHeaderLayout.titleTextView.setText(getResources().getString(R.string.Samples2AdapterHeader));

        InitManager.imgResTint(requireActivity(), binding.usersAddView.getRoot(), R.drawable.ic_plus_light, R.color.White);
        InitManager.imgResTint(requireActivity(), binding.practicesAddView.getRoot(), R.drawable.ic_plus_light, R.color.White);
        InitManager.imgResTint(requireActivity(), binding.samplesAddView.getRoot(), R.drawable.ic_plus_light, R.color.White);

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.platformsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.psychologistsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.usersSingleLayout.recyclerView, 0, 0, 0, 0);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.practicesSingleLayout.recyclerView, 0, 0, 0, 0);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.samplesSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.usersAddView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600_ripple_white);
            binding.practicesAddView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600_ripple_white);
            binding.samplesAddView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600_ripple_white);
        } else {
            binding.usersAddView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600);
            binding.practicesAddView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600);
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
                            NavDirections action = NavigationMainDirections.actionGlobalClientReportsFragment("session", sessionModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
                        } break;
                        case "ویرایش": {
                            NavDirections action = NavigationMainDirections.actionGlobalEditSessionFragment(sessionModel);
                            ((MainActivity) requireActivity()).navController.navigate(action);
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

        ClickManager.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCreateSessionUserFragment("session", sessionModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.usersAddView.getRoot());

        ClickManager.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCreatePracticeFragment("session", sessionModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.practicesAddView.getRoot());

        ClickManager.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCreateSampleFragment("session", sessionModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.samplesAddView.getRoot());
    }

    private void setArgs() {
        sessionModel = (SessionModel) SessionFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(sessionModel);
    }

    private void setData(SessionModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            binding.serialTextView.setText(model.getId());
            data.put("id", model.getId());
            data.put("session_platforms", 1);
        }

        if (model.getStarted_at() != 0) {
            binding.dateTextView.setText(DateManager.jalYYYYsNMMsDDsNDDsHHsMM(String.valueOf(model.getStarted_at()), " "));
        }

        if (model.getDuration() != 0) {
            binding.durationTextView.setText(model.getDuration() + " " + "دقیقه");
        }

        if (model.getStatus() != null && !model.getStatus().equals("")) {
            binding.statusTextView.setText(SelectionManager.getSessionStatus(requireActivity(), "fa", model.getStatus()));
        }

        if (model.getPayment_status() != null && !model.getPayment_status().equals("")) {
            binding.paymentTextView.setText(SelectionManager.getPaymentStatus(requireActivity(), "fa", model.getPayment_status()));
        }

        if (model.getClients_type() != null && !model.getClients_type().equals("")) {
            binding.clientTextView.setText(SelectionManager.getClientType(requireActivity(), "fa", model.getClients_type()));
        }

        if (model.getOpens_at() != 0) {
            binding.startTimeTextView.setText(DateManager.jalYYYYsNMMsDDsNDDsHHsMM(String.valueOf(model.getOpens_at()), " "));
        }

        if (model.getClosed_at() != 0) {
            binding.endTimeTextView.setText(DateManager.jalYYYYsNMMsDDsNDDsHHsMM(String.valueOf(model.getClosed_at()), " "));
        }

        setDropdown();
    }

    private void setDropdown() {
        ArrayList<String> items = new ArrayList<>();

        items.add(requireActivity().getResources().getString(R.string.SessionFragmentReports));
        items.add(requireActivity().getResources().getString(R.string.SessionFragmentEdit));

        items.add("");

        InitManager.actionCustomSpinner(requireActivity(), binding.menuSpinner.selectSpinner, items);
    }

    private void getData() {
        Session.showDashborad(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                sessionModel = (SessionModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        setData(sessionModel);

                        // Platforms Data
                        if (!sessionModel.getSession_platforms().data().isEmpty()) {
                            platformsAdapter.setItems(sessionModel.getSession_platforms().data());
                            binding.platformsSingleLayout.recyclerView.setAdapter(platformsAdapter);

                            binding.platformsSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (platformsAdapter.getItemCount() == 0) {
                            binding.platformsSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.platformsSingleLayout.emptyView.setText(getResources().getString(R.string.PlatformsAdapterEmpty));
                        }

                        List psychologists = new List();
                        if (sessionModel.getRoom() != null && sessionModel.getRoom().getRoomManager() != null)
                            psychologists.add(sessionModel.getRoom().getRoomManager());

                        // Psychologists Data
                        if (!psychologists.data().isEmpty()) {
                            psychologistsAdapter.setItems(psychologists.data());
                            binding.psychologistsSingleLayout.recyclerView.setAdapter(psychologistsAdapter);

                            binding.psychologistsSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (psychologistsAdapter.getItemCount() == 0) {
                            binding.psychologistsSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.psychologistsSingleLayout.emptyView.setText(getResources().getString(R.string.PsychologistsAdapterEmpty));
                        }

                        // Users Data
                        if (sessionModel.getClients() != null && sessionModel.getClients().data().size() != 0) {
                            users2Adapter.setItems(sessionModel.getClients().data());
                            binding.usersSingleLayout.recyclerView.setAdapter(users2Adapter);

                            binding.usersSingleLayout.headerView.getRoot().setVisibility(View.VISIBLE);
                            binding.usersSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (users2Adapter.getItemCount() == 0) {
                            binding.usersSingleLayout.headerView.getRoot().setVisibility(View.GONE);
                            binding.usersSingleLayout.emptyView.setVisibility(View.VISIBLE);

                            binding.usersSingleLayout.emptyView.setText(getResources().getString(R.string.Users2AdapterEmpty));
                        }

//                        // Practices Data
//                        if (!sessionModel.getPractices().data().isEmpty()) {
//                            practicesAdapter.setItems(sessionModel.getPractices().data());
//                            binding.practicesSingleLayout.recyclerView.setAdapter(practicesAdapter);
//
//                            binding.practicesSingleLayout.headerView.getRoot().setVisibility(View.VISIBLE);
//                            binding.practicesSingleLayout.emptyView.setVisibility(View.GONE);
//                        } else if (practicesAdapter.getItemCount() == 0) {
                            binding.practicesSingleLayout.headerView.getRoot().setVisibility(View.GONE);
                            binding.practicesSingleLayout.emptyView.setVisibility(View.VISIBLE);

                            binding.practicesSingleLayout.emptyView.setText(getResources().getString(R.string.PracticesAdapterEmpty));
//                        }

                        // Samples Data
                        if (!sessionModel.getSamples().data().isEmpty()) {
                            samples2Adapter.setItems(sessionModel.getSamples().data());
                            binding.samplesSingleLayout.recyclerView.setAdapter(samples2Adapter);

                            binding.samplesSingleLayout.headerView.getRoot().setVisibility(View.VISIBLE);
                            binding.samplesSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (samples2Adapter.getItemCount() == 0) {
                            binding.samplesSingleLayout.headerView.getRoot().setVisibility(View.GONE);
                            binding.samplesSingleLayout.emptyView.setVisibility(View.VISIBLE);

                            binding.samplesSingleLayout.emptyView.setText(getResources().getString(R.string.Samples2AdapterEmpty));
                        }

                        binding.platformsHeaderLayout.countTextView.setText(StringManager.bracing(platformsAdapter.getItemCount()));
                        binding.psychologistsHeaderLayout.countTextView.setText(StringManager.bracing(psychologistsAdapter.getItemCount()));
                        binding.usersHeaderLayout.countTextView.setText(StringManager.bracing(users2Adapter.getItemCount()));
                        binding.practicesHeaderLayout.countTextView.setText(StringManager.bracing(practicesAdapter.getItemCount()));
                        binding.samplesHeaderLayout.countTextView.setText(StringManager.bracing(samples2Adapter.getItemCount()));

                        // Platforms Data
                        binding.platformsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.platformsShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.platformsShimmerLayout.getRoot().stopShimmer();

                        // Psychologists Data
                        binding.psychologistsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.psychologistsShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.psychologistsShimmerLayout.getRoot().stopShimmer();

                        // Users Data
                        binding.usersSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.usersShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.usersShimmerLayout.getRoot().stopShimmer();

                        // Practices Data
                        binding.practicesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.practicesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.practicesShimmerLayout.getRoot().stopShimmer();

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

                        // Platforms Data
                        binding.platformsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.platformsShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.platformsShimmerLayout.getRoot().stopShimmer();

                        // Psychologists Data
                        binding.psychologistsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.psychologistsShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.psychologistsShimmerLayout.getRoot().stopShimmer();

                        // Users Data
                        binding.usersSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.usersShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.usersShimmerLayout.getRoot().stopShimmer();

                        // Practices Data
                        binding.practicesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.practicesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.practicesShimmerLayout.getRoot().stopShimmer();

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
    }

}