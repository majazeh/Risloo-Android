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

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.SheetManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Managers.ToastManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Index.IndexSampleAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.ReferencesAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Index.IndexScalesAdapter;
import com.majazeh.risloo.databinding.FragmentBulkSampleBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.TypeModel.BulkSampleModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class BulkSampleFragment extends Fragment {

    // Binding
    private FragmentBulkSampleBinding binding;

    // Adapters
    private ReferencesAdapter referencesAdapter;
    private IndexScalesAdapter indexScalesAdapter;
    private IndexSampleAdapter indexSampleAdapter;

    // Models
    private BulkSampleModel bulkSampleModel;

    // Objects
    private HashMap data, header;

    // Vars
    private boolean userSelect = false;

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
        indexScalesAdapter = new IndexScalesAdapter(requireActivity());
        indexSampleAdapter = new IndexSampleAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.referencesHeaderLayout.titleTextView.setText(getResources().getString(R.string.ReferencesAdapterHeader));
        binding.scalesHeaderLayout.titleTextView.setText(getResources().getString(R.string.ScalesFragmentTitle));
        binding.samplesHeaderLayout.titleTextView.setText(getResources().getString(R.string.SamplesFragmentTitle));

        InitManager.imgResTint(requireActivity(), binding.menuSpinner.selectImageView, R.drawable.ic_ellipsis_v_light, R.color.Gray500);

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.referencesSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.scalesSingleLayout.recyclerView, 0, 0, 0, 0);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.samplesSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.menuSpinner.selectImageView.setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_gray300);
        } else {
            binding.menuSpinner.selectImageView.setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_gray300);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            if (binding.avatarsIncludeLayout.charTextView.getVisibility() == View.GONE) {
                try {
                    IntentManager.display(requireActivity(), binding.centerTextView.getText().toString(), bulkSampleModel.getRoom().getRoomCenter().getDetail().getJSONArray("avatar").getJSONObject(2).getString("url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).widget(binding.avatarsIncludeLayout.avatarCircleImageView);

        CustomClickView.onDelayedListener(() -> {
            if (binding.avatarsIncludeLayout.charSubTextView.getVisibility() == View.GONE) {
                IntentManager.display(requireActivity(), binding.psychologyTextView.getText().toString(), bulkSampleModel.getRoom().getRoomManager().getAvatar().getMedium().getUrl());
            }
        }).widget(binding.avatarsIncludeLayout.avatarSubCircleImageView);

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
                        case "لینک ثبت نام": {
                            DialogManager.showLoadingDialog(requireActivity());

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
                                                    DialogManager.dismissLoadingDialog();
                                                    IntentManager.test(requireActivity(), key);
                                                } else {
                                                    DialogManager.dismissLoadingDialog();
                                                    SheetManager.showBulkSampleBottomSheet(requireActivity(), key, ((MainActivity) requireActivity()).singleton.getName(), ((MainActivity) requireActivity()).singleton.getAvatar(), bulkSampleModel);
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
                        } break;
                        case "کپی کردن لینک": {
                            IntentManager.clipboard(requireActivity(), bulkSampleModel.getLink());
                            ToastManager.showSuccesToast(requireActivity(), requireActivity().getResources().getString(R.string.ToastLinkSaved));
                        } break;
                        case "ویرایش": {
                            // TODO : Place Code If Needed
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
                binding.referencesHeaderLayout.countTextView.setText("(" + model.getMembers_count() + " / " + model.getJoined() + ")");
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

            setDropdown(model);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDropdown(BulkSampleModel model) {
        ArrayList<String> items = new ArrayList<>();

        if (!model.getLink().equals("")) {
            items.add(requireActivity().getResources().getString(R.string.BulkSampleFragmentLink));
            items.add(requireActivity().getResources().getString(R.string.BulkSampleFragmentCopy));
        }

        items.add(requireActivity().getResources().getString(R.string.BulkSampleFragmentEdit));
        items.add("");

        InitManager.actionCustomSpinner(requireActivity(), binding.menuSpinner.selectSpinner, items);
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
                            referencesAdapter.setItems(bulkSampleModel.getMembers().data());
                            binding.referencesSingleLayout.recyclerView.setAdapter(referencesAdapter);

                            binding.referencesSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (referencesAdapter.getItemCount() == 0) {
                            binding.referencesSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.referencesSingleLayout.emptyView.setText(getResources().getString(R.string.ReferencesAdapterEmpty));
                        }

                        // Scales Data
                        if (!bulkSampleModel.getScales().data().isEmpty()) {
                            indexScalesAdapter.setItems(bulkSampleModel.getScales().data());
                            binding.scalesSingleLayout.recyclerView.setAdapter(indexScalesAdapter);

                            binding.scalesSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (indexScalesAdapter.getItemCount() == 0) {
                            binding.scalesSingleLayout.emptyView.setVisibility(View.VISIBLE);

                            binding.scalesSingleLayout.emptyView.setText(getResources().getString(R.string.ScalesFragmentEmpty));
                        }

                        // Samples Data
                        if (!bulkSampleModel.getSamples().data().isEmpty()) {
                            indexSampleAdapter.setItems(bulkSampleModel.getSamples().data());
                            binding.samplesSingleLayout.recyclerView.setAdapter(indexSampleAdapter);

                            binding.samplesSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (indexSampleAdapter.getItemCount() == 0) {
                            binding.samplesSingleLayout.emptyView.setVisibility(View.VISIBLE);

                            binding.samplesSingleLayout.emptyView.setText(getResources().getString(R.string.SamplesFragmentEmpty));
                        }

                        binding.scalesHeaderLayout.countTextView.setText(StringManager.bracing(indexScalesAdapter.itemsCount()));
                        binding.samplesHeaderLayout.countTextView.setText(StringManager.bracing(indexSampleAdapter.itemsCount()));

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
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}