package com.majazeh.risloo.Views.Fragments.Main.Show;

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
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Index.IndexPsychologistAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Table.TableBillAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Table.TablePracticeAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Table.TableSampleAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Table.TableUser2Adapter;
import com.majazeh.risloo.databinding.FragmentSessionBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Session;
import com.mre.ligheh.Model.TypeModel.ScheduleModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class SessionFragment extends Fragment {

    // Binding
    private FragmentSessionBinding binding;

    // Adapters
    private IndexPsychologistAdapter indexPsychologistAdapter;
    private TableUser2Adapter tableUser2Adapter;
    private TablePracticeAdapter tablePracticeAdapter;
    private TableSampleAdapter tableSampleAdapter;
    private TableBillAdapter tableBillAdapter;

    // Models
    public SessionModel sessionModel;
    public ScheduleModel scheduleModel;

    // Objects
    private HashMap data, header;

    // Vars
    private boolean userSelect = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        indexPsychologistAdapter = new IndexPsychologistAdapter(requireActivity());
        tableUser2Adapter = new TableUser2Adapter(requireActivity());
        tablePracticeAdapter = new TablePracticeAdapter(requireActivity());
        tableSampleAdapter = new TableSampleAdapter(requireActivity());
        tableBillAdapter = new TableBillAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.psychologistsHeaderLayout.titleTextView.setText(getResources().getString(R.string.PsychologistAdapterHeader));
        binding.usersHeaderLayout.titleTextView.setText(getResources().getString(R.string.User2AdapterHeader));
        binding.practicesHeaderLayout.titleTextView.setText(getResources().getString(R.string.PracticeAdapterHeader));
        binding.samplesHeaderLayout.titleTextView.setText(getResources().getString(R.string.SamplesFragmentTitle));
        binding.billsHeaderLayout.titleTextView.setText(getResources().getString(R.string.BillAdapterTitle));

        binding.usersShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);
        binding.practicesShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);
        binding.samplesShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);
        binding.billsShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);

        InitManager.imgResTintBackground(requireActivity(), binding.usersAddView.getRoot(), R.drawable.ic_plus_light, R.color.White, R.drawable.draw_oval_solid_emerald600_ripple_white);
        InitManager.imgResTintBackground(requireActivity(), binding.practicesAddView.getRoot(), R.drawable.ic_plus_light, R.color.White, R.drawable.draw_oval_solid_emerald600_ripple_white);
        InitManager.imgResTintBackground(requireActivity(), binding.samplesAddView.getRoot(), R.drawable.ic_plus_light, R.color.White, R.drawable.draw_oval_solid_emerald600_ripple_white);
        InitManager.imgResTintBackground(requireActivity(), binding.billsAddView.getRoot(), R.drawable.ic_plus_light, R.color.White, R.drawable.draw_oval_solid_emerald600_ripple_white);

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.psychologistsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.usersSingleLayout.recyclerView, 0, 0, 0, 0);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.practicesSingleLayout.recyclerView, 0, 0, 0, 0);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.samplesSingleLayout.recyclerView, 0, 0, 0, 0);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.billsSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onClickListener(() -> {
            switch (binding.menuSpinner.selectImageView.getTag().toString()) {
                case "گزارشات":
                    if (sessionModel != null)
                        ((MainActivity) requireActivity()).navigatoon.navigateToClientReportsFragment(sessionModel);

                    break;
                case "ویرایش":
                    if (sessionModel != null)
                        ((MainActivity) requireActivity()).navigatoon.navigateToEditSessionFragment(sessionModel);

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
                            if (sessionModel != null)
                                ((MainActivity) requireActivity()).navigatoon.navigateToClientReportsFragment(sessionModel);

                            break;
                        case "ویرایش":
                            if (sessionModel != null)
                                ((MainActivity) requireActivity()).navigatoon.navigateToEditSessionFragment(sessionModel);

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
            if (sessionModel != null)
                ((MainActivity) requireActivity()).navigatoon.navigateToCreateSessionUserFragment(sessionModel);

        }).widget(binding.usersAddView.getRoot());

        CustomClickView.onClickListener(() -> {
            if (sessionModel != null)
                ((MainActivity) requireActivity()).navigatoon.navigateToCreatePracticeFragment(sessionModel);

        }).widget(binding.practicesAddView.getRoot());

        CustomClickView.onClickListener(() -> {
            if (sessionModel != null)
                ((MainActivity) requireActivity()).navigatoon.navigateToCreateSampleFragment(sessionModel);

        }).widget(binding.samplesAddView.getRoot());

        CustomClickView.onClickListener(() -> {
            if (sessionModel != null)
                ((MainActivity) requireActivity()).navigatoon.navigateToCreateBillFragment(sessionModel);

        }).widget(binding.billsAddView.getRoot());
    }

    private void setArgs() {
        TypeModel typeModel = SessionFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (StringManager.substring(typeModel.getClass().getName(), '.').equals("ScheduleModel")) {
            scheduleModel = (ScheduleModel) typeModel;
            setData(scheduleModel);
        } else if (StringManager.substring(typeModel.getClass().getName(), '.').equals("SessionModel")) {
            sessionModel = (SessionModel) typeModel;
            setData(sessionModel);
        }
    }

    private void setData(ScheduleModel model) {
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
            setStatus(model.getStatus());
        }

        if (model.getClients() != null && model.getClients().data().size() != 0) {
            binding.referenceGroup.setVisibility(View.VISIBLE);
            binding.referenceTextView.setText("");
            for (int i = 0; i < model.getClients().data().size(); i++) {
                UserModel user = (UserModel) model.getClients().data().get(i);
                if (user != null) {
                    binding.referenceTextView.append(user.getName());
                    if (i != model.getClients().data().size() - 1) {
                        binding.referenceTextView.append("  -  ");
                    }
                }
            }
        } else {
            binding.referenceGroup.setVisibility(View.GONE);
        }

        try {
            if (model.getFields() != null && model.getFields().length() != 0) {
                binding.axisGroup.setVisibility(View.VISIBLE);
                binding.axisTextView.setText("");
                for (int i = 0; i < model.getFields().length(); i++) {
                    binding.axisTextView.append(model.getFields().getJSONObject(i).getString("title"));
                    if (i != model.getFields().length() - 1) {
                        binding.axisTextView.append("  |  ");
                    }
                }
            } else {
                binding.axisGroup.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (model.getSession_platforms() != null && model.getSession_platforms().data().size() != 0) {
            binding.platformGroup.setVisibility(View.VISIBLE);
            binding.platformTextView.setText("");
            for (int i = 0; i < model.getSession_platforms().data().size(); i++) {
                SessionPlatformModel platform = (SessionPlatformModel) model.getSession_platforms().data().get(i);
                if (platform != null) {
                    binding.platformTextView.append(platform.getTitle());
                    if (i != model.getSession_platforms().data().size() - 1) {
                        binding.platformTextView.append("  |  ");
                    }
                }
            }
        } else {
            binding.platformGroup.setVisibility(View.GONE);
        }

        if (binding.referenceGroup.getVisibility() != View.VISIBLE && binding.axisGroup.getVisibility() != View.VISIBLE && binding.platformGroup.getVisibility() != View.VISIBLE) {
            binding.info2ConstraintLayout.setVisibility(View.GONE);
        }

        if (model.getPayment_status() != null && !model.getPayment_status().equals("")) {
            binding.paymentGroup.setVisibility(View.VISIBLE);
            binding.paymentTextView.setText(SelectionManager.getPaymentStatus(requireActivity(), "fa", model.getPayment_status()));
        } else {
            binding.paymentGroup.setVisibility(View.GONE);
        }

        if (model.getClients_type() != null && !model.getClients_type().equals("")) {
            binding.clientGroup.setVisibility(View.VISIBLE);
            binding.clientTextView.setText(SelectionManager.getClientType(requireActivity(), "fa", model.getClients_type()));
        } else {
            binding.clientGroup.setVisibility(View.GONE);
        }

        if (model.getOpens_at() != 0) {
            binding.startTimeGroup.setVisibility(View.VISIBLE);
            binding.startTimeTextView.setText(DateManager.jalYYYYsNMMsDDsNDDsHHsMM(String.valueOf(model.getOpens_at()), " "));
        } else {
            binding.startTimeGroup.setVisibility(View.GONE);
        }

        if (model.getClosed_at() != 0) {
            binding.endTimeGroup.setVisibility(View.VISIBLE);
            binding.endTimeTextView.setText(DateManager.jalYYYYsNMMsDDsNDDsHHsMM(String.valueOf(model.getClosed_at()), " "));
        } else {
            binding.endTimeGroup.setVisibility(View.GONE);
        }

        if (binding.paymentGroup.getVisibility() != View.VISIBLE && binding.clientGroup.getVisibility() != View.VISIBLE && binding.startTimeGroup.getVisibility() != View.VISIBLE && binding.endTimeGroup.getVisibility() != View.VISIBLE) {
            binding.info3ConstraintLayout.setVisibility(View.GONE);
        }

        setDropdown();
        setPermission();
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
            setStatus(model.getStatus());
        }

        if (model.getClients() != null && model.getClients().data().size() != 0) {
            binding.referenceGroup.setVisibility(View.VISIBLE);
            binding.referenceTextView.setText("");
            for (int i = 0; i < model.getClients().data().size(); i++) {
                UserModel user = (UserModel) model.getClients().data().get(i);
                if (user != null) {
                    binding.referenceTextView.append(user.getName());
                    if (i != model.getClients().data().size() - 1) {
                        binding.referenceTextView.append("  -  ");
                    }
                }
            }
        } else {
            binding.referenceGroup.setVisibility(View.GONE);
        }

        try {
            if (model.getFields() != null && model.getFields().length() != 0) {
                binding.axisGroup.setVisibility(View.VISIBLE);
                binding.axisTextView.setText("");
                for (int i = 0; i < model.getFields().length(); i++) {
                    binding.axisTextView.append(model.getFields().getJSONObject(i).getString("title"));
                    if (i != model.getFields().length() - 1) {
                        binding.axisTextView.append("  |  ");
                    }
                }
            } else {
                binding.axisGroup.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (model.getSession_platforms() != null && model.getSession_platforms().data().size() != 0) {
            binding.platformGroup.setVisibility(View.VISIBLE);
            binding.platformTextView.setText("");
            for (int i = 0; i < model.getSession_platforms().data().size(); i++) {
                SessionPlatformModel platform = (SessionPlatformModel) model.getSession_platforms().data().get(i);
                if (platform != null) {
                    binding.platformTextView.append(platform.getTitle());
                    if (i != model.getSession_platforms().data().size() - 1) {
                        binding.platformTextView.append("  |  ");
                    }
                }
            }
        } else {
            binding.platformGroup.setVisibility(View.GONE);
        }

        if (binding.referenceGroup.getVisibility() != View.VISIBLE && binding.axisGroup.getVisibility() != View.VISIBLE && binding.platformGroup.getVisibility() != View.VISIBLE) {
            binding.info2ConstraintLayout.setVisibility(View.GONE);
        }

        if (model.getPayment_status() != null && !model.getPayment_status().equals("")) {
            binding.paymentGroup.setVisibility(View.VISIBLE);
            binding.paymentTextView.setText(SelectionManager.getPaymentStatus(requireActivity(), "fa", model.getPayment_status()));
        } else {
            binding.paymentGroup.setVisibility(View.GONE);
        }

        if (model.getClients_type() != null && !model.getClients_type().equals("")) {
            binding.clientGroup.setVisibility(View.VISIBLE);
            binding.clientTextView.setText(SelectionManager.getClientType(requireActivity(), "fa", model.getClients_type()));
        } else {
            binding.clientGroup.setVisibility(View.GONE);
        }

        if (model.getOpens_at() != 0) {
            binding.startTimeGroup.setVisibility(View.VISIBLE);
            binding.startTimeTextView.setText(DateManager.jalYYYYsNMMsDDsNDDsHHsMM(String.valueOf(model.getOpens_at()), " "));
        } else {
            binding.startTimeGroup.setVisibility(View.GONE);
        }

        if (model.getClosed_at() != 0) {
            binding.endTimeGroup.setVisibility(View.VISIBLE);
            binding.endTimeTextView.setText(DateManager.jalYYYYsNMMsDDsNDDsHHsMM(String.valueOf(model.getClosed_at()), " "));
        } else {
            binding.endTimeGroup.setVisibility(View.GONE);
        }

        if (binding.paymentGroup.getVisibility() != View.VISIBLE && binding.clientGroup.getVisibility() != View.VISIBLE && binding.startTimeGroup.getVisibility() != View.VISIBLE && binding.endTimeGroup.getVisibility() != View.VISIBLE) {
            binding.info3ConstraintLayout.setVisibility(View.GONE);
        }

        setDropdown();
        setPermission();
    }

    private void setStatus(String status) {
        binding.statusTextView.setText(SelectionManager.getSessionStatus2(requireActivity(), "fa", status));

        switch (status) {
            case "registration_awaiting":
            case "registration":
                binding.statusTextView.setTextColor(getResources().getColor(R.color.Amber500));
                binding.statusCircle.setBackgroundResource(R.drawable.draw_oval_solid_amber600);
                break;
            case "client_awaiting":
            case "session_awaiting":
                binding.statusTextView.setTextColor(getResources().getColor(R.color.Risloo500));
                binding.statusCircle.setBackgroundResource(R.drawable.draw_oval_solid_risloo500);
                break;
            case "in_session":
                binding.statusTextView.setTextColor(getResources().getColor(R.color.Emerald500));
                binding.statusCircle.setBackgroundResource(R.drawable.draw_oval_solid_emerald600);
                break;
            case "canceled_by_client":
            case "canceled_by_center":
                binding.statusTextView.setTextColor(getResources().getColor(R.color.Red500));
                binding.statusCircle.setBackgroundResource(R.drawable.draw_oval_solid_red600);
                break;
            default:
                binding.statusTextView.setTextColor(getResources().getColor(R.color.CoolGray500));
                binding.statusCircle.setBackgroundResource(R.drawable.draw_oval_solid_coolgray400);
                break;
        }
    }

    private void setDropdown() {
        ArrayList<String> items = new ArrayList<>();

//        if (((MainActivity) requireActivity()).permissoon.showSessionDropdownReports(((MainActivity) requireActivity()).singleton.getUserModel(), sessionModel))
//            items.add(requireActivity().getResources().getString(R.string.SessionFragmentReports));

        if (((MainActivity) requireActivity()).permissoon.showSessionDropdownEdit(((MainActivity) requireActivity()).singleton.getUserModel(), sessionModel))
            items.add(requireActivity().getResources().getString(R.string.SessionFragmentEdit));

        items.add("");

        if (items.size() > 2) {
            InitManager.spinnerOvalEnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_ellipsis_v_light, R.color.CoolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300);
            InitManager.selectCustomActionSpinner(requireActivity(), binding.menuSpinner.selectSpinner, items);
        } else if (items.size() == 2) {
            switch (items.get(0)) {
                case "گزارشات":
                    InitManager.spinnerOvalUnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_clipboard_light, R.color.CoolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "ویرایش":
                    InitManager.spinnerOvalUnable(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_edit_light, R.color.CoolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
            }
        } else {
            binding.menuSpinner.getRoot().setVisibility(View.GONE);
        }
    }

    private void setPermission() {
        if (((MainActivity) requireActivity()).permissoon.showSessionCreateUser(((MainActivity) requireActivity()).singleton.getUserModel(), sessionModel))
            binding.usersAddView.getRoot().setVisibility(View.VISIBLE);
        else
            binding.usersAddView.getRoot().setVisibility(View.GONE);

        if (((MainActivity) requireActivity()).permissoon.showSessionCreatePractice(((MainActivity) requireActivity()).singleton.getUserModel(), sessionModel))
            binding.practicesAddView.getRoot().setVisibility(View.VISIBLE);
        else
            binding.practicesAddView.getRoot().setVisibility(View.GONE);

        if (((MainActivity) requireActivity()).permissoon.showSessionCreateSample(((MainActivity) requireActivity()).singleton.getUserModel(), sessionModel))
            binding.samplesAddView.getRoot().setVisibility(View.VISIBLE);
        else
            binding.samplesAddView.getRoot().setVisibility(View.GONE);

        if (((MainActivity) requireActivity()).permissoon.showSessionCreateBill(((MainActivity) requireActivity()).singleton.getUserModel(), sessionModel))
            binding.billsAddView.getRoot().setVisibility(View.VISIBLE);
        else
            binding.billsAddView.getRoot().setVisibility(View.GONE);
    }

    private void getData() {
        Session.showDashboradWithFillAccount(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                sessionModel = (SessionModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        setData(sessionModel);

                        List psychologists = new List();
                        if (sessionModel.getRoom() != null && sessionModel.getRoom().getRoomManager() != null)
                            psychologists.add(sessionModel.getRoom().getRoomManager());

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

                        // Users Data
                        if (sessionModel.getClients() != null && sessionModel.getClients().data().size() != 0) {
                            tableUser2Adapter.setItems(sessionModel.getClients().data());
                            binding.usersSingleLayout.recyclerView.setAdapter(tableUser2Adapter);

                            binding.usersSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (tableUser2Adapter.getItemCount() == 0) {
                            binding.usersSingleLayout.recyclerView.setAdapter(null);

                            binding.usersSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.usersSingleLayout.emptyView.setText(getResources().getString(R.string.User2AdapterEmpty));
                        }

//                        // Practices Data
//                        if (!sessionModel.getPractices().data().isEmpty()) {
//                            indexPracticeAdapter.setItems(sessionModel.getPractices().data());
//                            binding.practicesSingleLayout.recyclerView.setAdapter(indexPracticeAdapter);
//
//                            binding.practicesSingleLayout.emptyView.setVisibility(View.GONE);
//                        } else if (indexPracticeAdapter.getItemCount() == 0) {
                            binding.practicesSingleLayout.recyclerView.setAdapter(null);

                            binding.practicesSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.practicesSingleLayout.emptyView.setText(getResources().getString(R.string.PracticeAdapterEmpty));
//                        }

                        // Samples Data
                        if (!sessionModel.getSamples().data().isEmpty()) {
                            tableSampleAdapter.setItems(sessionModel.getSamples().data());
                            binding.samplesSingleLayout.recyclerView.setAdapter(tableSampleAdapter);

                            binding.samplesSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (tableSampleAdapter.getItemCount() == 0) {
                            binding.samplesSingleLayout.recyclerView.setAdapter(null);

                            binding.samplesSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.samplesSingleLayout.emptyView.setText(getResources().getString(R.string.SamplesFragmentEmpty));
                        }

                        // Bills Data
                        if (!sessionModel.getTransactions().data().isEmpty()) {
                            tableBillAdapter.setItems(sessionModel.getTransactions().data());
                            binding.billsSingleLayout.recyclerView.setAdapter(tableBillAdapter);

                            binding.billsSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (tableBillAdapter.getItemCount() == 0) {
                            binding.billsSingleLayout.recyclerView.setAdapter(null);

                            binding.billsSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.billsSingleLayout.emptyView.setText(getResources().getString(R.string.BillAdapterEmpty));
                        }

                        binding.psychologistsHeaderLayout.countTextView.setText(StringManager.bracing(indexPsychologistAdapter.getItemCount()));
                        binding.usersHeaderLayout.countTextView.setText(StringManager.bracing(tableUser2Adapter.itemsCount()));
                        binding.practicesHeaderLayout.countTextView.setText(StringManager.bracing(tablePracticeAdapter.itemsCount()));
                        binding.samplesHeaderLayout.countTextView.setText(StringManager.bracing(tableSampleAdapter.itemsCount()));
                        binding.billsHeaderLayout.countTextView.setText(StringManager.bracing(tableBillAdapter.itemsCount()));

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

        // Bills Data
        binding.billsSingleLayout.getRoot().setVisibility(View.VISIBLE);
        binding.billsShimmerLayout.getRoot().setVisibility(View.GONE);
        binding.billsShimmerLayout.getRoot().stopShimmer();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        userSelect = false;
    }

}