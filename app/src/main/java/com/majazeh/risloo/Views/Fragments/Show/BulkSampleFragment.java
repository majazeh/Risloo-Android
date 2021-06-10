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
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.ReferencesAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Samples4Adapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Scales2Adapter;
import com.majazeh.risloo.databinding.FragmentBulkSampleBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class BulkSampleFragment extends Fragment {

    // Binding
    private FragmentBulkSampleBinding binding;

    // Adapters
    private ReferencesAdapter referencesAdapter;
    private Scales2Adapter scales2Adapter;
    private Samples4Adapter samples4Adapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration, itemDecoration2;
    private LinearLayoutManager referencesLayoutManager, scales2LayoutManager, samples4LayoutManager;
    private Bundle extras;

    // Vars
    private HashMap data, header;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentBulkSampleBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setExtra();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        referencesAdapter = new ReferencesAdapter(requireActivity());
        scales2Adapter = new Scales2Adapter(requireActivity());
        samples4Adapter = new Samples4Adapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));
        itemDecoration2 = new ItemDecorateRecyclerView("verticalLayout", 0, 0, 0, 0);

        referencesLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        scales2LayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        samples4LayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        extras = new Bundle();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        InitManager.imgResTint(requireActivity(), binding.editTextView.getRoot(), R.drawable.ic_edit_light, R.color.Gray500);
        InitManager.imgResTint(requireActivity(), binding.linkTextView.buttonImageView, R.drawable.ic_copy_light, R.color.Gray500);

        InitManager.txtTextColor(binding.linkTextView.buttonTextView, getResources().getString(R.string.BulkSampleFragmentLink), getResources().getColor(R.color.Gray500));

        binding.referencesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.ReferencesAdapterHeader));
        binding.scalesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Scales2AdapterHeader));
        binding.samplesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Samples4AdapterHeader));

        InitManager.recyclerView(binding.referencesSingleLayout.recyclerView, itemDecoration, referencesLayoutManager);
        InitManager.recyclerView(binding.scalesSingleLayout.recyclerView, itemDecoration2, scales2LayoutManager);
        InitManager.recyclerView(binding.samplesSingleLayout.recyclerView, itemDecoration2, samples4LayoutManager);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);
        } else {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
                IntentManager.display(requireActivity(), "", "", ((MainActivity) requireActivity()).singleton.getAvatar());
            }
        }).widget(binding.avatarsIncludeLayout.avatarCircleImageView);

        ClickManager.onDelayedClickListener(() -> {
            if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
                IntentManager.display(requireActivity(), "", "", ((MainActivity) requireActivity()).singleton.getAvatar());
            }
        }).widget(binding.avatarsIncludeLayout.avatarSubCircleImageView);

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.editTextView.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.linkTextView.buttonImageView);

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.linkTextView.buttonTextView);
    }

    private void setExtra() {
        if (getArguments() != null) {
            if (getArguments().getString("id") != null && !getArguments().getString("id").equals("")) {
                extras.putString("id", getArguments().getString("id"));
                data.put("id", getArguments().getString("id"));
                binding.serialTextView.setText(getArguments().getString("id"));
            }

            if (getArguments().getString("center_id") != null && !getArguments().getString("center_id").equals("") && getArguments().getString("center_name") != null && !getArguments().getString("center_name").equals("")) {
                extras.putString("center_id", getArguments().getString("center_id"));
                extras.putString("center_name", getArguments().getString("center_name"));
                binding.centerTextView.setText(getArguments().getString("center_name"));
            }

            if (getArguments().getString("room_id") != null && !getArguments().getString("room_id").equals("") && getArguments().getString("room_name") != null && !getArguments().getString("room_name").equals("")) {
                extras.putString("room_id", getArguments().getString("room_id"));
                extras.putString("room_name", getArguments().getString("room_name"));
                binding.psychologyTextView.setText(getArguments().getString("room_name"));
            }

            if (getArguments().getString("case_status") != null && !getArguments().getString("case_status").equals("")) {
                extras.putString("case_status", getArguments().getString("case_status"));
                binding.caseTextView.setText(getArguments().getString("case_status"));
            }

            if (getArguments().getString("status") != null && !getArguments().getString("status").equals("")) {
                extras.putString("status", getArguments().getString("status"));
                binding.statusTextView.setText(SelectionManager.getSampleStatus(requireActivity(), "fa", getArguments().getString("status")));
            }

            if (getArguments().getString("center_avatar") != null && !getArguments().getString("center_avatar").equals("")) {
                extras.putString("center_avatar", getArguments().getString("center_avatar"));
                binding.avatarsIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(getArguments().getString("center_avatar")).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarsIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarsIncludeLayout.charTextView.setText(StringManager.firstChars(binding.centerTextView.getText().toString()));

                Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarCircleImageView);
            }

            if (getArguments().getString("room_avater") != null && !getArguments().getString("room_avater").equals("")) {
                extras.putString("room_avater", getArguments().getString("room_avater"));
                binding.avatarsIncludeLayout.charSubTextView.setVisibility(View.GONE);
                Picasso.get().load(getArguments().getString("room_avater")).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarSubCircleImageView);
            } else {
                binding.avatarsIncludeLayout.charSubTextView.setVisibility(View.VISIBLE);
                binding.avatarsIncludeLayout.charSubTextView.setText(StringManager.firstChars(binding.psychologyTextView.getText().toString()));

                Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarSubCircleImageView);
            }
        }
    }

    private void setData(SampleModel model) {
        if (model.getSampleRoom() != null && model.getSampleRoom().getRoomCenter() != null && model.getSampleRoom().getRoomCenter().getManager() != null && model.getSampleRoom().getRoomCenter().getManager().getName() != null) {
            extras.putString("center_id", model.getSampleRoom().getRoomCenter().getCenterId());
            extras.putString("center_name", model.getSampleRoom().getRoomCenter().getManager().getName());
            binding.centerTextView.setText(model.getSampleRoom().getRoomCenter().getManager().getName());
        }

        if (model.getSampleRoom() != null && model.getSampleRoom().getRoomManager() != null && model.getSampleRoom().getRoomManager().getName() != null) {
            extras.putString("room_id", model.getSampleRoom().getRoomId());
            extras.putString("room_name", model.getSampleRoom().getRoomManager().getName());
            binding.psychologyTextView.setText(model.getSampleRoom().getRoomManager().getName());
        }

        if (model.getCaseStatus() != null && !model.getCaseStatus().equals("")) {
            extras.putString("case_status", model.getCaseStatus());
            binding.caseTextView.setText(model.getCaseStatus());
        }

        if (model.getSampleStatus() != null && !model.getSampleStatus().equals("")) {
            extras.putString("status", model.getSampleStatus());
            binding.statusTextView.setText(SelectionManager.getSampleStatus(requireActivity(), "fa", model.getSampleStatus()));
        }

        if (model.getSampleRoom() != null && model.getSampleRoom().getRoomCenter() != null && model.getSampleRoom().getRoomCenter().getManager() != null && model.getSampleRoom().getRoomCenter().getManager().getAvatar() != null && model.getSampleRoom().getRoomCenter().getManager().getAvatar().getMedium() != null && model.getSampleRoom().getRoomCenter().getManager().getAvatar().getMedium().getUrl() != null) {
            extras.putString("center_avatar", model.getSampleRoom().getRoomCenter().getManager().getAvatar().getMedium().getUrl());
            binding.avatarsIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(model.getSampleRoom().getRoomCenter().getManager().getAvatar().getMedium().getUrl()).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarsIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.avatarsIncludeLayout.charTextView.setText(StringManager.firstChars(binding.centerTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarCircleImageView);
        }

        if (model.getSampleRoom() != null && model.getSampleRoom().getRoomManager() != null && model.getSampleRoom().getRoomManager().getAvatar() != null && model.getSampleRoom().getRoomManager().getAvatar().getMedium() != null && model.getSampleRoom().getRoomManager().getAvatar().getMedium().getUrl() != null) {
            extras.putString("room_avater", model.getSampleRoom().getRoomManager().getAvatar().getMedium().getUrl());
            binding.avatarsIncludeLayout.charSubTextView.setVisibility(View.GONE);
            Picasso.get().load(model.getSampleRoom().getRoomManager().getAvatar().getMedium().getUrl()).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarSubCircleImageView);
        } else {
            binding.avatarsIncludeLayout.charSubTextView.setVisibility(View.VISIBLE);
            binding.avatarsIncludeLayout.charSubTextView.setText(StringManager.firstChars(binding.psychologyTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarSubCircleImageView);
        }
    }

    private void getData() {
        Sample.bulkShow(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                SampleModel model = (SampleModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        setData(model);

//                        // References Data
//                        if (model.getClients() != null && model.getClients().data().size() != 0) {
//                            referencesAdapter.setReferences(model.getClients().data());
//                            binding.referencesSingleLayout.recyclerView.setAdapter(referencesAdapter);
//                        }
//
//                        // Scales Data
//                        if (!model.getScales().data().isEmpty()) {
//                            scales2Adapter.setScales(model.getScales().data());
//                            binding.scalesSingleLayout.recyclerView.setAdapter(scales2Adapter);
//                        }
//
//                        // Samples Data
//                        if (!model.getSamples().data().isEmpty()) {
//                            samples4Adapter.setSamples(model.getSamples().data());
//                            binding.samplesSingleLayout.recyclerView.setAdapter(samples4Adapter);
//                        }

                        binding.referencesHeaderIncludeLayout.countTextView.setText("(" + referencesAdapter.getItemCount() + ")");
                        binding.scalesHeaderIncludeLayout.countTextView.setText("(" + scales2Adapter.getItemCount() + ")");
                        binding.samplesHeaderIncludeLayout.countTextView.setText("(" + samples4Adapter.getItemCount() + ")");

                        // References Data
                        binding.referencesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.referencesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.referencesShimmerLayout.getRoot().stopShimmer();

                        // Scales Data
                        binding.scalesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.scalesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.scalesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.scalesShimmerLayout.getRoot().stopShimmer();

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
                        // References Data
                        binding.referencesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.referencesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.referencesShimmerLayout.getRoot().stopShimmer();

                        // Scales Data
                        binding.scalesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.scalesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.scalesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.scalesShimmerLayout.getRoot().stopShimmer();

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