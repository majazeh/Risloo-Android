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
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.PracticesAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.PsychologistsAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Samples2Adapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Users2Adapter;
import com.majazeh.risloo.databinding.FragmentSessionBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Session;
import com.mre.ligheh.Model.TypeModel.SessionModel;

import java.util.HashMap;

public class SessionFragment extends Fragment {

    // Binding
    private FragmentSessionBinding binding;

    // Adapters
    private PsychologistsAdapter psychologistsAdapter;
    private Users2Adapter users2Adapter;
    private PracticesAdapter practicesAdapter;
    private Samples2Adapter samples2Adapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration, itemDecoration2;
    private LinearLayoutManager psychologistsLayoutManager, users2LayoutManager, practicesLayoutManager, samples2LayoutManager;
    private Bundle extras;

    // Vars
    private HashMap data, header;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentSessionBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setExtra();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        psychologistsAdapter = new PsychologistsAdapter(requireActivity());
        users2Adapter = new Users2Adapter(requireActivity());
        practicesAdapter = new PracticesAdapter(requireActivity());
        samples2Adapter = new Samples2Adapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));
        itemDecoration2 = new ItemDecorateRecyclerView("verticalLayout", 0, 0, 0, 0);

        psychologistsLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        users2LayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        practicesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        samples2LayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        extras = new Bundle();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        InitManager.txtTextColor(binding.reportsTextView.getRoot(), getResources().getString(R.string.SessionFragmentReports), getResources().getColor(R.color.Blue600));
        InitManager.imgResTint(requireActivity(), binding.editImageView.getRoot(), R.drawable.ic_edit_light, R.color.Gray500);

        binding.reportHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.SessionFragmentReportHeader));
        binding.psychologistsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.PsychologistsAdapterHeader));
        binding.usersHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Users2AdapterHeader));
        binding.practicesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.PracticesAdapterHeader));
        binding.samplesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Samples2AdapterHeader));

        InitManager.imgResTint(requireActivity(), binding.usersAddImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);
        InitManager.imgResTint(requireActivity(), binding.practicesAddImageView.getRoot(), R.drawable.ic_plus_light, R.color.White);
        InitManager.imgResTint(requireActivity(), binding.samplesAddImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);

        InitManager.recyclerView(binding.psychologistsSingleLayout.recyclerView, itemDecoration, psychologistsLayoutManager);
        InitManager.recyclerView(binding.usersSingleLayout.recyclerView, itemDecoration2, users2LayoutManager);
        InitManager.recyclerView(binding.practicesSingleLayout.recyclerView, itemDecoration2, practicesLayoutManager);
        InitManager.recyclerView(binding.samplesSingleLayout.recyclerView, itemDecoration2, samples2LayoutManager);
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
        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.sessionReportsFragment, extras)).widget(binding.reportsTextView.getRoot());

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.editSessionFragment, extras)).widget(binding.editImageView.getRoot());

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createReportFragment, extras)).widget(binding.reportActionTextView.getRoot());

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createCenterUserFragment, extras)).widget(binding.usersAddImageView.getRoot());

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createPracticeFragment, extras)).widget(binding.practicesAddImageView.getRoot());

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createSampleFragment, extras)).widget(binding.samplesAddImageView.getRoot());
    }

    private void setExtra() {
        if (getArguments() != null) {
            if (getArguments().getString("id") != null && !getArguments().getString("id").equals("")) {
                extras.putString("id", getArguments().getString("id"));
                data.put("id", getArguments().getString("id"));
                binding.serialTextView.setText(getArguments().getString("id"));
            }

            if (getArguments().getInt("started_at") != 0) {
                extras.putInt("started_at", getArguments().getInt("started_at"));
                binding.dateTextView.setText(DateManager.gregorianToJalali6(DateManager.dateToString("yyyy-MM-dd HH:mm:ss", DateManager.timestampToDate(getArguments().getInt("started_at")))));
            }

            if (getArguments().getString("duration") != null && !getArguments().getString("duration").equals("")) {
                extras.putString("duration", getArguments().getString("duration"));
                binding.durationTextView.setText(getArguments().getString("duration") + " " + "دقیقه");
            }

            if (getArguments().getString("status") != null && !getArguments().getString("status").equals("")) {
                extras.putString("status", getArguments().getString("status"));
                binding.statusTextView.setText(SelectionManager.getSessionStatus(requireActivity(), "fa", getArguments().getString("status")));
            }

            if (getArguments().getString("type") != null && !getArguments().getString("type").equals("")) {
                extras.putString("type", getArguments().getString("type"));
                binding.sessionTypeTextView.setText(SelectionManager.getSessionType(requireActivity(), "fa", getArguments().getString("type")));
            }

            if (getArguments().getString("clients_number") != null && !getArguments().getString("clients_number").equals("")) {
                extras.putString("clients_number", getArguments().getString("clients_number"));
                binding.referenceCountTextView.setText(getArguments().getString("clients_number"));
            }

            if (getArguments().getString("payment_status") != null && !getArguments().getString("payment_status").equals("")) {
                extras.putString("payment_status", getArguments().getString("payment_status"));
                binding.paymentTypeTextView.setText(SelectionManager.getPaymentStatus(requireActivity(), "fa", getArguments().getString("payment_status")));
            }

            if (getArguments().getString("selection_type") != null && !getArguments().getString("selection_type").equals("")) {
                extras.putString("selection_type", getArguments().getString("selection_type"));
                binding.selectionTypeTextView.setText(SelectionManager.getSelectionType(requireActivity(), "fa", getArguments().getString("selection_type")));
            }

            if (getArguments().getString("clients_type") != null && !getArguments().getString("clients_type").equals("")) {
                extras.putString("clients_type", getArguments().getString("clients_type"));
                binding.referenceTypeTextView.setText(SelectionManager.getClientType(requireActivity(), "fa", getArguments().getString("clients_type")));
            }

            if (getArguments().getInt("opens_at") != 0) {
                extras.putInt("opens_at", getArguments().getInt("opens_at"));
                binding.startTimeTextView.setText(DateManager.gregorianToJalali6(DateManager.dateToString("yyyy-MM-dd HH:mm:ss", DateManager.timestampToDate(getArguments().getInt("opens_at")))));
            }

            if (getArguments().getInt("closed_at") != 0) {
                extras.putInt("closed_at", getArguments().getInt("closed_at"));
                binding.endTimeTextView.setText(DateManager.gregorianToJalali6(DateManager.dateToString("yyyy-MM-dd HH:mm:ss", DateManager.timestampToDate(getArguments().getInt("closed_at")))));
            }

            if (getArguments().getString("report") != null && !getArguments().getString("report").equals("")) {
                extras.putString("report", getArguments().getString("report"));
                setReport(getArguments().getString("report"));
            }

            if (getArguments().getString("clients") != null && !getArguments().getString("clients").equals("")) {
                extras.putString("clients", getArguments().getString("clients"));
            }

            if (getArguments().getString("practices") != null && !getArguments().getString("practices").equals("")) {
                extras.putString("practices", getArguments().getString("practices"));
            }
        }
    }

    private void setData(SessionModel model) {
        if (model.getStarted_at() != 0) {
            extras.putInt("started_at", model.getStarted_at());
            binding.dateTextView.setText(DateManager.gregorianToJalali6(DateManager.dateToString("yyyy-MM-dd HH:mm:ss", DateManager.timestampToDate(model.getStarted_at()))));
        }

        if (model.getDuration() != 0) {
            extras.putString("duration", String.valueOf(model.getDuration()));
            binding.durationTextView.setText(model.getDuration() + " " + "دقیقه");
        }

        if (model.getStatus() != null && !model.getStatus().equals("")) {
            extras.putString("status", model.getStatus());
            binding.statusTextView.setText(SelectionManager.getSessionStatus(requireActivity(), "fa", model.getStatus()));
        }

        if (model.getType() != null && !model.getType().equals("")) {
            extras.putString("type", model.getType());
            binding.sessionTypeTextView.setText(SelectionManager.getSessionType(requireActivity(), "fa", model.getType()));
        }

        if (model.getClients_number() != 0) {
            extras.putString("clients_number", String.valueOf(model.getClients_number()));
            binding.referenceCountTextView.setText(String.valueOf(model.getClients_number()));
        }

        if (model.getPayment_status() != null && !model.getPayment_status().equals("")) {
            extras.putString("payment_status", model.getPayment_status());
            binding.paymentTypeTextView.setText(SelectionManager.getPaymentStatus(requireActivity(), "fa", model.getPayment_status()));
        }

        if (model.getSelection_type() != null && !model.getSelection_type().equals("")) {
            extras.putString("selection_type", model.getSelection_type());
            binding.selectionTypeTextView.setText(SelectionManager.getSelectionType(requireActivity(), "fa", model.getSelection_type()));
        }

        if (model.getClients_type() != null && !model.getClients_type().equals("")) {
            extras.putString("clients_type", model.getClients_type());
            binding.referenceTypeTextView.setText(SelectionManager.getClientType(requireActivity(), "fa", model.getClients_type()));
        }

        if (model.getOpens_at() != 0) {
            extras.putInt("opens_at", model.getOpens_at());
            binding.startTimeTextView.setText(DateManager.gregorianToJalali6(DateManager.dateToString("yyyy-MM-dd HH:mm:ss", DateManager.timestampToDate(model.getOpens_at()))));
        }

        if (model.getClosed_at() != 0) {
            extras.putInt("closed_at", model.getClosed_at());
            binding.endTimeTextView.setText(DateManager.gregorianToJalali6(DateManager.dateToString("yyyy-MM-dd HH:mm:ss", DateManager.timestampToDate(model.getClosed_at()))));
        }

        if (model.getReport() != null && !model.getReport().equals("")) {
            extras.putString("report", model.getReport());
            setReport(model.getReport());
        }

        if (model.getClients() != null && model.getClients().data().size() != 0) {
            extras.putString("clients", String.valueOf(model.getClients()));
        }

        if (model.getPractices() != null && model.getPractices().length() != 0) {
            extras.putString("practices", String.valueOf(model.getPractices()));
        }
    }

    private void getData() {
        Session.show(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                SessionModel model = (SessionModel) object;

                if (isAdded())
                    requireActivity().runOnUiThread(() -> {
                        setData(model);

//                        // Psychologists Data
//                        if (!model.getPsychologistList().data().isEmpty()) {
//                            psychologistsAdapter.setPsychologists(model.getPsychologistList().data());
//                            binding.psychologistsSingleLayout.recyclerView.setAdapter(psychologistsAdapter);
//                        }
//
//                        // Users Data
//                        if (!model.getUserList().data().isEmpty()) {
//                            users2Adapter.setUsers(model.getUserList().data());
//                            binding.usersSingleLayout.recyclerView.setAdapter(users2Adapter);
//                        }
//
//                        // Practices Data
//                        if (!model.getPracticeList().data().isEmpty()) {
//                            practicesAdapter.setPractices(model.getSessionList().data());
//                            binding.practicesSingleLayout.recyclerView.setAdapter(practicesAdapter);
//                        }
//
//                        // Samples Data
//                        if (!model.getSampleList().data().isEmpty()) {
//                            samples2Adapter.setSamples(model.getSampleList().data());
//                            binding.samplesSingleLayout.recyclerView.setAdapter(samples2Adapter);
//                        }

                        binding.psychologistsHeaderIncludeLayout.countTextView.setText("(" + psychologistsAdapter.getItemCount() + ")");
                        binding.usersHeaderIncludeLayout.countTextView.setText("(" + users2Adapter.getItemCount() + ")");
                        binding.practicesHeaderIncludeLayout.countTextView.setText("(" + practicesAdapter.getItemCount() + ")");
                        binding.samplesHeaderIncludeLayout.countTextView.setText("(" + samples2Adapter.getItemCount() + ")");

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

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
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

    private void setReport(String report) {
        switch (report) {
            case "add":
                binding.reportTextView.setText(getResources().getString(R.string.SessionFragmentReportBodyEmpty));

                binding.reportActionTextView.getRoot().setEnabled(true);
                binding.reportActionTextView.getRoot().setText(getResources().getString(R.string.SessionFragmentReportAdd));
                binding.reportActionTextView.getRoot().setTextColor(getResources().getColor(R.color.White));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                    binding.reportActionTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_green600_ripple_green800);
                else
                    binding.reportActionTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_green600);
                break;
            case "edit":
                binding.reportTextView.setText("ﻟﻮرم اﯾﭙﺴﻮم متن ﺳﺎﺧﺘﮕﯽ ﺑﺎ ﺗﻮﻟﯿﺪ ﺳﺎدﮔﯽ ﻧﺎﻣﻔﻬﻮم از ﺻﻨﻌﺖ ﭼﺎپ، و ﺑﺎ اﺳﺘﻔﺎده از ﻃﺮاﺣﺎن ﮔﺮاﻓﯿﮏ اﺳﺖ، ﭼﺎﭘﮕﺮﻫﺎ و ﻣﺘﻮن ﺑﻠﮑﻪ روزنامه و مجله در ستون و سطر آنچنان که لازم است، و برای شرایط فعلی تکنولوژی مورد نیاز، و کاربرهای متنوع با هدف بهبود ابزراهای کاربردی می\u200Cباشد، کتابهای زیادی در شصت و سه درصد گذشته حال و آینده، شناخت فراوان جامعه و متخصصان را می\u200Cطلبد.");

                binding.reportActionTextView.getRoot().setEnabled(true);
                binding.reportActionTextView.getRoot().setText(getResources().getString(R.string.SessionFragmentReportEdit));
                binding.reportActionTextView.getRoot().setTextColor(getResources().getColor(R.color.Green600));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                    binding.reportActionTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
                else
                    binding.reportActionTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
                break;
            case "privacy":
                binding.reportTextView.setText(getResources().getString(R.string.SessionFragmentReportBodyPrivacy));

                binding.reportActionTextView.getRoot().setEnabled(false);
                binding.reportActionTextView.getRoot().setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}