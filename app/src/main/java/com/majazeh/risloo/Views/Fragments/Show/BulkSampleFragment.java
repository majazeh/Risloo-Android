package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.ReferencesAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Samples4Adapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Scales2Adapter;
import com.majazeh.risloo.Views.BottomSheets.ChainBottomSheet;
import com.majazeh.risloo.databinding.FragmentBulkSampleBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.TypeModel.BulkSampleModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class BulkSampleFragment extends Fragment {

    // Binding
    private FragmentBulkSampleBinding binding;

    // Adapters
    private ReferencesAdapter referencesAdapter;
    private Scales2Adapter scales2Adapter;
    private Samples4Adapter samples4Adapter;

    // BottomSheets
    private ChainBottomSheet chainBottomSheet;

    // Vars
    private HashMap data, header;
    private BulkSampleModel bulkSampleModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentBulkSampleBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        referencesAdapter = new ReferencesAdapter(requireActivity());
        scales2Adapter = new Scales2Adapter(requireActivity());
        samples4Adapter = new Samples4Adapter(requireActivity());

        chainBottomSheet = new ChainBottomSheet();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        InitManager.imgResTint(requireActivity(), binding.editTextView.getRoot(), R.drawable.ic_edit_light, R.color.Gray500);
        InitManager.imgResTint(requireActivity(), binding.linkTextView.buttonImageView, R.drawable.ic_copy_light, R.color.Gray500);

        InitManager.txtTextColor(binding.linkTextView.buttonTextView, getResources().getString(R.string.BulkSampleFragmentLink), getResources().getColor(R.color.Gray500));

        binding.referencesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.ReferencesAdapterHeader));
        binding.scalesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Scales2AdapterHeader));
        binding.samplesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Samples4AdapterHeader));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.referencesSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.scalesSingleLayout.recyclerView, 0, 0, 0, 0);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.samplesSingleLayout.recyclerView, 0, 0, 0, 0);
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
            if (binding.avatarsIncludeLayout.charTextView.getVisibility() == View.GONE) {
                try {
                    IntentManager.display(requireActivity(), binding.centerTextView.getText().toString(), bulkSampleModel.getRoom().getRoomCenter().getDetail().getJSONArray("avatar").getJSONObject(2).getString("url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).widget(binding.avatarsIncludeLayout.avatarCircleImageView);

        ClickManager.onDelayedClickListener(() -> {
            if (binding.avatarsIncludeLayout.charSubTextView.getVisibility() == View.GONE) {
                IntentManager.display(requireActivity(), binding.psychologyTextView.getText().toString(), bulkSampleModel.getRoom().getRoomManager().getAvatar().getMedium().getUrl());
            }
        }).widget(binding.avatarsIncludeLayout.avatarSubCircleImageView);

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code If Needed
        }).widget(binding.editTextView.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            IntentManager.clipboard(requireActivity(), bulkSampleModel.getLink());
            Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppLinkSaved), Toast.LENGTH_SHORT).show();
        }).widget(binding.linkTextView.buttonImageView);

        ClickManager.onDelayedClickListener(() -> {
            ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

            HashMap authData = new HashMap<>();
            authData.put("authorized_key", bulkSampleModel.getLink());

            Sample.auth(authData, header, new Response() {
                @Override
                public void onOK(Object object) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            try {
                                JSONObject jsonObject = (JSONObject) object;

                                if (!jsonObject.getString("theory").equals("sample"))
                                    bulkSampleModel = new BulkSampleModel(jsonObject.getJSONObject("bulk_sample"));

                                String key = jsonObject.getString("key");

                                if (key.startsWith("$")) {
                                    ((MainActivity) requireActivity()).loadingDialog.dismiss();
                                    IntentManager.test(requireActivity(), key);
                                } else {
                                    ((MainActivity) requireActivity()).loadingDialog.dismiss();

                                    chainBottomSheet.show(requireActivity().getSupportFragmentManager(), "chainBottomSheet");
                                    chainBottomSheet.setData(key, ((MainActivity) requireActivity()).singleton.getId(), ((MainActivity) requireActivity()).singleton.getName(), ((MainActivity) requireActivity()).singleton.getAvatar(), bulkSampleModel);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            // TODO : Place Code If Needed
                        });
                    }
                }
            });
        }).widget(binding.linkTextView.buttonTextView);
    }

    private void setArgs() {
        bulkSampleModel = (BulkSampleModel) BulkSampleFragmentArgs.fromBundle(getArguments()).getTypeModel();

        setData(bulkSampleModel);
    }

    private void setData(BulkSampleModel model) {
        try {
            if (model.getId() != null && !model.getId().equals("")) {
                binding.serialTextView.setText(model.getId());
                data.put("id", model.getId());
            }

            if (model.getRoom() != null && model.getRoom().getRoomCenter() != null && model.getRoom().getRoomCenter().getDetail() != null && model.getRoom().getRoomCenter().getDetail().has("title") && !model.getRoom().getRoomCenter().getDetail().getString("title").equals("")) {
                binding.centerTextView.setText(model.getRoom().getRoomCenter().getDetail().getString("title"));
            }

            if (model.getRoom() != null && model.getRoom().getRoomManager() != null && model.getRoom().getRoomManager().getName() != null) {
                binding.psychologyTextView.setText(model.getRoom().getRoomManager().getName());
            }

            if (model.getMembers_count() != -1 && model.getJoined() != -1) {
                binding.referencesHeaderIncludeLayout.countTextView.setText("(" + model.getMembers_count() + " / " + model.getJoined() + ")");
            }

            if (model.getCase_status() != null && !model.getCase_status().equals("")) {
                binding.caseTextView.setText(SelectionManager.getCaseStatus(requireActivity(), "fa", model.getCase_status()));
            }

            if (model.getStatus() != null && !model.getStatus().equals("")) {
                binding.statusTextView.setText(SelectionManager.getSampleStatus(requireActivity(), "fa", model.getStatus()));
            }

            if (model.getRoom() != null && model.getRoom().getRoomCenter() != null && model.getRoom().getRoomCenter().getDetail() != null && model.getRoom().getRoomCenter().getDetail().has("avatar") && !model.getRoom().getRoomCenter().getDetail().getString("avatar").equals("") && model.getRoom().getRoomCenter().getDetail().getJSONArray("avatar").length() != 0) {
                binding.avatarsIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(model.getRoom().getRoomCenter().getDetail().getJSONArray("avatar").getJSONObject(2).getString("url")).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarsIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarsIncludeLayout.charTextView.setText(StringManager.firstChars(binding.centerTextView.getText().toString()));

                Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarCircleImageView);
            }

            if (model.getRoom() != null && model.getRoom().getRoomManager() != null && model.getRoom().getRoomManager().getAvatar() != null && model.getRoom().getRoomManager().getAvatar().getMedium() != null && model.getRoom().getRoomManager().getAvatar().getMedium().getUrl() != null) {
                binding.avatarsIncludeLayout.charSubTextView.setVisibility(View.GONE);
                Picasso.get().load(model.getRoom().getRoomManager().getAvatar().getMedium().getUrl()).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarSubCircleImageView);
            } else {
                binding.avatarsIncludeLayout.charSubTextView.setVisibility(View.VISIBLE);
                binding.avatarsIncludeLayout.charSubTextView.setText(StringManager.firstChars(binding.psychologyTextView.getText().toString()));

                Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarSubCircleImageView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getData() {
        Sample.bulkShow(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                bulkSampleModel = (BulkSampleModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        setData(bulkSampleModel);

                        // References Data
                        if (bulkSampleModel.getMembers() != null && bulkSampleModel.getMembers().data().size() != 0) {
                            referencesAdapter.setReferences(bulkSampleModel.getMembers().data());
                            binding.referencesSingleLayout.recyclerView.setAdapter(referencesAdapter);

                            binding.referencesSingleLayout.textView.setVisibility(View.GONE);
                        } else if (referencesAdapter.getItemCount() == 0) {
                            binding.referencesSingleLayout.textView.setVisibility(View.VISIBLE);
                        }

                        // Scales Data
                        if (!bulkSampleModel.getScales().data().isEmpty()) {
                            scales2Adapter.setScales(bulkSampleModel.getScales().data());
                            binding.scalesSingleLayout.recyclerView.setAdapter(scales2Adapter);

                            binding.scalesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.scalesSingleLayout.textView.setVisibility(View.GONE);
                        } else if (scales2Adapter.getItemCount() == 0) {
                            binding.scalesHeaderLayout.getRoot().setVisibility(View.GONE);
                            binding.scalesSingleLayout.textView.setVisibility(View.VISIBLE);
                        }

                        // Samples Data
                        if (!bulkSampleModel.getSamples().data().isEmpty()) {
                            samples4Adapter.setSamples(bulkSampleModel.getSamples().data());
                            binding.samplesSingleLayout.recyclerView.setAdapter(samples4Adapter);

                            binding.samplesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.samplesSingleLayout.textView.setVisibility(View.GONE);
                        } else if (samples4Adapter.getItemCount() == 0) {
                            binding.samplesHeaderLayout.getRoot().setVisibility(View.GONE);
                            binding.samplesSingleLayout.textView.setVisibility(View.VISIBLE);
                        }

                        binding.scalesHeaderIncludeLayout.countTextView.setText(StringManager.bracing(scales2Adapter.getItemCount()));
                        binding.samplesHeaderIncludeLayout.countTextView.setText(StringManager.bracing(samples4Adapter.getItemCount()));

                        // References Data
                        binding.referencesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.referencesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.referencesShimmerLayout.getRoot().stopShimmer();

                        // Scales Data
                        binding.scalesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.scalesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.scalesShimmerLayout.getRoot().stopShimmer();

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