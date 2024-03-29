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
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.SheetManager;
import com.majazeh.risloo.utils.managers.DropdownManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.IntentManager;
import com.majazeh.risloo.utils.managers.JsonManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.managers.ToastManager;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.main.Table.TableSampleAdapter;
import com.majazeh.risloo.views.adapters.recycler.main.Index.IndexReferenceAdapter;
import com.majazeh.risloo.views.adapters.recycler.main.Table.TableScaleAdapter;
import com.majazeh.risloo.databinding.FragmentBulkSampleBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.TypeModel.BulkSampleModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentBulkSample extends Fragment {

    // Binding
    private FragmentBulkSampleBinding binding;

    // Adapters
    private IndexReferenceAdapter indexReferenceAdapter;
    private TableScaleAdapter tableScaleAdapter;
    private TableSampleAdapter tableSampleAdapter;

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

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        indexReferenceAdapter = new IndexReferenceAdapter(requireActivity());
        tableScaleAdapter = new TableScaleAdapter(requireActivity());
        tableSampleAdapter = new TableSampleAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.referencesHeaderLayout.titleTextView.setText(getResources().getString(R.string.ReferenceAdapterHeader));
        binding.scalesHeaderLayout.titleTextView.setText(getResources().getString(R.string.ScalesFragmentTitle));
        binding.samplesHeaderLayout.titleTextView.setText(getResources().getString(R.string.SamplesFragmentTitle));

        binding.scalesShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);
        binding.samplesShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);

        InitManager.rcvVerticalFixedUnnested(requireActivity(), binding.referencesSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.rcvVerticalFixedUnnested(requireActivity(), binding.scalesSingleLayout.recyclerView, 0, 0, 0, 0);
        InitManager.rcvVerticalFixedUnnested(requireActivity(), binding.samplesSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            if (binding.avatarsIncludeLayout.charTextView.getVisibility() == View.GONE) {
                try {
                    IntentManager.display(requireActivity(), binding.centerTextView.getText().toString(), bulkSampleModel.getRoom().getCenter().getDetail().getJSONArray("avatar").getJSONObject(2).getString("url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).widget(binding.avatarsIncludeLayout.avatarCircleImageView);

        CustomClickView.onDelayedListener(() -> {
            if (binding.avatarsIncludeLayout.charSubTextView.getVisibility() == View.GONE)
                IntentManager.display(requireActivity(), binding.psychologyTextView.getText().toString(), bulkSampleModel.getRoom().getManager().getAvatar().getMedium().getUrl());
        }).widget(binding.avatarsIncludeLayout.avatarSubCircleImageView);

        CustomClickView.onClickListener(() -> {
            switch (binding.menuSpinner.selectImageView.getTag().toString()) {
                case "لینک ثبت نام":
                    doWork();
                    break;
                case "کپی کردن لینک":
                    IntentManager.clipboard(requireActivity(), bulkSampleModel.getLink());
                    ToastManager.showToastSucces(requireActivity(), requireActivity().getResources().getString(R.string.ToastSampleLinkSaved));
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
                        case "لینک ثبت نام":
                            doWork();
                            break;
                        case "کپی کردن لینک":
                            IntentManager.clipboard(requireActivity(), bulkSampleModel.getLink());
                            ToastManager.showToastSucces(requireActivity(), requireActivity().getResources().getString(R.string.ToastSampleLinkSaved));
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
    }

    private void setArgs() {
        bulkSampleModel = (BulkSampleModel) FragmentBulkSampleArgs.fromBundle(getArguments()).getTypeModel();
        setData(bulkSampleModel);
    }

    private void setData(BulkSampleModel model) {
        try {
            if (model.getId() != null && !model.getId().equals("")) {
                binding.serialTextView.setText(model.getId());
                data.put("id", model.getId());
            }

            if (model.getRoom() != null && model.getRoom().getCenter() != null && model.getRoom().getCenter().getDetail() != null && model.getRoom().getCenter().getDetail().has("title") && !model.getRoom().getCenter().getDetail().getString("title").equals("")) {
                binding.centerTextView.setText(model.getRoom().getCenter().getDetail().getString("title"));
            }

            if (model.getRoom() != null && model.getRoom().getManager() != null && model.getRoom().getManager().getName() != null) {
                binding.psychologyTextView.setText(model.getRoom().getManager().getName());
            }

            if (model.getRoom() != null && model.getRoom().getCenter() != null && model.getRoom().getCenter().getDetail() != null && model.getRoom().getCenter().getDetail().has("avatar") && !model.getRoom().getCenter().getDetail().getString("avatar").equals("") && model.getRoom().getCenter().getDetail().getJSONArray("avatar").length() != 0) {
                binding.avatarsIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(model.getRoom().getCenter().getDetail().getJSONArray("avatar").getJSONObject(2).getString("url")).placeholder(R.color.coolGray100).into(binding.avatarsIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarsIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarsIncludeLayout.charTextView.setText(StringManager.charsFirst(binding.centerTextView.getText().toString()));

                Picasso.get().load(R.color.coolGray100).placeholder(R.color.coolGray100).into(binding.avatarsIncludeLayout.avatarCircleImageView);
            }

            if (model.getRoom() != null && model.getRoom().getManager() != null && model.getRoom().getManager().getAvatar() != null && model.getRoom().getManager().getAvatar().getMedium() != null && model.getRoom().getManager().getAvatar().getMedium().getUrl() != null) {
                binding.avatarsIncludeLayout.charSubTextView.setVisibility(View.GONE);
                Picasso.get().load(model.getRoom().getManager().getAvatar().getMedium().getUrl()).placeholder(R.color.coolGray100).into(binding.avatarsIncludeLayout.avatarSubCircleImageView);
            } else {
                binding.avatarsIncludeLayout.charSubTextView.setVisibility(View.VISIBLE);
                binding.avatarsIncludeLayout.charSubTextView.setText(StringManager.charsFirst(binding.psychologyTextView.getText().toString()));

                Picasso.get().load(R.color.coolGray100).placeholder(R.color.coolGray100).into(binding.avatarsIncludeLayout.avatarSubCircleImageView);
            }

            if (model.getRoom() != null && model.getRoom().getType() != null && model.getRoom().getType().equals("personal_clinic")) {
                binding.psychologyTextView.setVisibility(View.GONE);
                binding.avatarsIncludeLayout.subGroup.setVisibility(View.GONE);
            }

            if (model.getCaseStatus() != null && !model.getCaseStatus().equals("")) {
                binding.caseTextView.setText(JsonManager.getCaseStatus(requireActivity(), "fa", model.getCaseStatus()));
            }

            if (model.getStatus() != null && !model.getStatus().equals("")) {
                binding.statusTextView.setText(JsonManager.getSampleStatus(requireActivity(), "fa", model.getStatus()));
            }

            if (model.getMembersCount() != -1 && model.getJoined() != -1) {
                binding.referencesHeaderLayout.countTextView.setText("(" + model.getMembersCount() + " / " + model.getJoined() + ")");
            }

            setDropdown(model);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDropdown(BulkSampleModel model) {
        ArrayList<String> items = new ArrayList<>();

        if (model.getLink() != null && !model.getLink().equals("") && model.getStatus() != null && !model.getStatus().equals("closed")) {
            items.add(requireActivity().getResources().getString(R.string.BulkSampleFragmentLink));
            items.add(requireActivity().getResources().getString(R.string.BulkSampleFragmentCopy));
        }

        items.add("");

        if (items.size() > 2) {
            InitManager.spOvalDropdown(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_ellipsis_v_light, R.color.coolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300);
            DropdownManager.spinnerSelectAction(requireActivity(), binding.menuSpinner.selectSpinner, items);
        } else if (items.size() == 2) {
            switch (items.get(0)) {
                case "لینک ثبت نام":
                    InitManager.spOvalSingle(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_link_light, R.color.coolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
                case "کپی کردن لینک":
                    InitManager.spOvalSingle(requireActivity(), binding.menuSpinner.selectSpinner, binding.menuSpinner.selectImageView, R.drawable.ic_copy_light, R.color.coolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300, items.get(0));
                    break;
            }
        } else {
            binding.menuSpinner.getRoot().setVisibility(View.GONE);
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
                            indexReferenceAdapter.setItems(bulkSampleModel.getMembers().data());
                            binding.referencesSingleLayout.recyclerView.setAdapter(indexReferenceAdapter);

                            binding.referencesSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (indexReferenceAdapter.getItemCount() == 0) {
                            binding.referencesSingleLayout.recyclerView.setAdapter(null);

                            binding.referencesSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.referencesSingleLayout.emptyView.setText(getResources().getString(R.string.ReferenceAdapterEmpty));
                        }

                        // Scales Data
                        if (!bulkSampleModel.getScales().data().isEmpty()) {
                            tableScaleAdapter.setItems(bulkSampleModel.getScales().data());
                            binding.scalesSingleLayout.recyclerView.setAdapter(tableScaleAdapter);

                            binding.scalesSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (tableScaleAdapter.getItemCount() == 0) {
                            binding.scalesSingleLayout.recyclerView.setAdapter(null);

                            binding.scalesSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.scalesSingleLayout.emptyView.setText(getResources().getString(R.string.ScalesFragmentEmpty));
                        }

                        // Samples Data
                        if (!bulkSampleModel.getSamples().data().isEmpty()) {
                            tableSampleAdapter.setItems(bulkSampleModel.getSamples().data());
                            binding.samplesSingleLayout.recyclerView.setAdapter(tableSampleAdapter);

                            binding.samplesSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (tableSampleAdapter.getItemCount() == 0) {
                            binding.samplesSingleLayout.recyclerView.setAdapter(null);

                            binding.samplesSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            binding.samplesSingleLayout.emptyView.setText(getResources().getString(R.string.SamplesFragmentEmpty));
                        }

                        binding.scalesHeaderLayout.countTextView.setText(StringManager.bracing(tableScaleAdapter.itemsCount()));
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

    private void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

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
                                DialogManager.dismissDialogLoading();
                                IntentManager.test(requireActivity(), key);
                            } else {
                                DialogManager.dismissDialogLoading();
                                SheetManager.showSheetBulkSample(requireActivity(), key, ((ActivityMain) requireActivity()).singleton.getUserModel(), bulkSampleModel);
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
    }

    private void hideShimmer() {

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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        userSelect = false;
    }

}