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

    // Vars
    private HashMap data, header;
    public SessionModel sessionModel;

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

        InitManager.txtTextColor(binding.reportsTextView.getRoot(), getResources().getString(R.string.SessionFragmentReports), getResources().getColor(R.color.Blue600));
        InitManager.imgResTint(requireActivity(), binding.editImageView.getRoot(), R.drawable.ic_edit_light, R.color.Gray500);

        binding.platformsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.PlatformsAdapterHeader));
        binding.psychologistsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.PsychologistsAdapterHeader));
        binding.usersHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Users2AdapterHeader));
        binding.practicesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.PracticesAdapterHeader));
        binding.samplesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Samples2AdapterHeader));

        InitManager.imgResTint(requireActivity(), binding.usersAddImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);
        InitManager.imgResTint(requireActivity(), binding.practicesAddImageView.getRoot(), R.drawable.ic_plus_light, R.color.White);
        InitManager.imgResTint(requireActivity(), binding.samplesAddImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.platformsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.psychologistsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.usersSingleLayout.recyclerView, 0, 0, 0, 0);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.practicesSingleLayout.recyclerView, 0, 0, 0, 0);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.samplesSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.reportsTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_blue600_ripple_blue300);
            binding.editImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_gray500_ripple_gray300);

            binding.usersAddImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_green700_ripple_green300);
            binding.practicesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600_ripple_white);
            binding.samplesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            binding.reportsTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_blue600);
            binding.editImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_gray500);

            binding.usersAddImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_green700);
            binding.practicesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600);
            binding.samplesAddImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_green700);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalClientReportsFragment("session", sessionModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.reportsTextView.getRoot());

        ClickManager.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalEditSessionFragment(sessionModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.editImageView.getRoot());

        ClickManager.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCreateSessionUserFragment("session", sessionModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.usersAddImageView.getRoot());

        ClickManager.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCreatePracticeFragment("session", sessionModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.practicesAddImageView.getRoot());

        ClickManager.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCreateSampleFragment("session", sessionModel);
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.samplesAddImageView.getRoot());
    }

    private void setArgs() {
        sessionModel = (SessionModel) SessionFragmentArgs.fromBundle(getArguments()).getTypeModel();

        setData(sessionModel);
    }

    private void setData(SessionModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            binding.serialTextView.setText(model.getId());
            data.put("id", model.getId());
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

        if (model.getType() != null && !model.getType().equals("")) {
            binding.sessionTypeTextView.setText(SelectionManager.getSessionType(requireActivity(), "fa", model.getType()));
        }

        if (model.getClients_number() != 0) {
            binding.referenceCountTextView.setText(String.valueOf(model.getClients_number()));
        }

        if (model.getPayment_status() != null && !model.getPayment_status().equals("")) {
            binding.paymentTypeTextView.setText(SelectionManager.getPaymentStatus(requireActivity(), "fa", model.getPayment_status()));
        }

        if (model.getSelection_type() != null && !model.getSelection_type().equals("")) {
            binding.selectionTypeTextView.setText(SelectionManager.getSelectionType(requireActivity(), "fa", model.getSelection_type()));
        }

        if (model.getClients_type() != null && !model.getClients_type().equals("")) {
            binding.referenceTypeTextView.setText(SelectionManager.getClientType(requireActivity(), "fa", model.getClients_type()));
        }

        if (model.getOpens_at() != 0) {
            binding.startTimeTextView.setText(DateManager.jalYYYYsNMMsDDsNDDsHHsMM(String.valueOf(model.getOpens_at()), " "));
        }

        if (model.getClosed_at() != 0) {
            binding.endTimeTextView.setText(DateManager.jalYYYYsNMMsDDsNDDsHHsMM(String.valueOf(model.getClosed_at()), " "));
        }
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
                            platformsAdapter.setPlatforms(sessionModel.getSession_platforms().data());
                            binding.platformsSingleLayout.recyclerView.setAdapter(platformsAdapter);

                            binding.psychologistsSingleLayout.textView.setVisibility(View.GONE);
                        } else if (platformsAdapter.getItemCount() == 0) {
                            binding.platformsSingleLayout.textView.setVisibility(View.VISIBLE);
                        }

                        List psychologists = new List();
                        if (sessionModel.getRoom() != null && sessionModel.getRoom().getRoomManager() != null)
                            psychologists.add(sessionModel.getRoom().getRoomManager());

                        // Psychologists Data
                        if (!psychologists.data().isEmpty()) {
                            psychologistsAdapter.setPsychologists(psychologists.data());
                            binding.psychologistsSingleLayout.recyclerView.setAdapter(psychologistsAdapter);

                            binding.psychologistsSingleLayout.textView.setVisibility(View.GONE);
                        } else if (psychologistsAdapter.getItemCount() == 0) {
                            binding.psychologistsSingleLayout.textView.setVisibility(View.VISIBLE);
                        }

                        // Users Data
                        if (sessionModel.getClients() != null && sessionModel.getClients().data().size() != 0) {
                            users2Adapter.setUsers(sessionModel.getClients().data());
                            binding.usersSingleLayout.recyclerView.setAdapter(users2Adapter);

                            binding.usersHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.usersSingleLayout.textView.setVisibility(View.GONE);
                        } else if (users2Adapter.getItemCount() == 0) {
                            binding.usersHeaderLayout.getRoot().setVisibility(View.GONE);
                            binding.usersSingleLayout.textView.setVisibility(View.VISIBLE);
                        }

//                        // Practices Data
//                        if (!sessionModel.getPractices().data().isEmpty()) {
//                            practicesAdapter.setPractices(sessionModel.getPractices().data());
//                            binding.practicesSingleLayout.recyclerView.setAdapter(practicesAdapter);
//
//                            binding.practicesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
//                            binding.practicesSingleLayout.textView.setVisibility(View.GONE);
//                        } else if (practicesAdapter.getItemCount() == 0) {
                            binding.practicesHeaderLayout.getRoot().setVisibility(View.GONE);
                            binding.practicesSingleLayout.textView.setVisibility(View.VISIBLE);
//                        }

                        // Samples Data
                        if (!sessionModel.getSamples().data().isEmpty()) {
                            samples2Adapter.setSamples(sessionModel.getSamples().data());
                            binding.samplesSingleLayout.recyclerView.setAdapter(samples2Adapter);

                            binding.samplesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.samplesSingleLayout.textView.setVisibility(View.GONE);
                        } else if (samples2Adapter.getItemCount() == 0) {
                            binding.samplesHeaderLayout.getRoot().setVisibility(View.GONE);
                            binding.samplesSingleLayout.textView.setVisibility(View.VISIBLE);
                        }

                        binding.platformsHeaderIncludeLayout.countTextView.setText(StringManager.bracing(platformsAdapter.getItemCount()));
                        binding.psychologistsHeaderIncludeLayout.countTextView.setText(StringManager.bracing(psychologistsAdapter.getItemCount()));
                        binding.usersHeaderIncludeLayout.countTextView.setText(StringManager.bracing(users2Adapter.getItemCount()));
                        binding.practicesHeaderIncludeLayout.countTextView.setText(StringManager.bracing(practicesAdapter.getItemCount()));
                        binding.samplesHeaderIncludeLayout.countTextView.setText(StringManager.bracing(samples2Adapter.getItemCount()));

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
                        binding.usersHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.usersSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.usersShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.usersShimmerLayout.getRoot().stopShimmer();

                        // Practices Data
                        binding.practicesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.practicesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.practicesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.practicesShimmerLayout.getRoot().stopShimmer();

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