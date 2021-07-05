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
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.ProfilesAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.SaGensAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.SaItemsAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.SaPresAdapter;
import com.majazeh.risloo.databinding.FragmentSampleBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.TypeModel.FormModel;
import com.mre.ligheh.Model.TypeModel.ProfileModel;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class SampleFragment extends Fragment {

    // Binding
    private FragmentSampleBinding binding;

    // Adapters
    private ProfilesAdapter profilesAdapter;
    private SaGensAdapter saGensAdapter;
    private SaPresAdapter saPresAdapter;
    private SaItemsAdapter saItemsAdapter;

    // Vars
    private HashMap data, header;
    private SampleModel sampleModel;
    private boolean userSelect = false;
    private ArrayList<String> profileUrls;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentSampleBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        profilesAdapter = new ProfilesAdapter(requireActivity());
        saGensAdapter = new SaGensAdapter(requireActivity());
        saPresAdapter = new SaPresAdapter(requireActivity());
        saItemsAdapter = new SaItemsAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.profilesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.SampleFragmentProfileHeader));
        binding.fieldsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.SampleFragmentFieldHeader));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.profilesSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.generalRecyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.prerequisiteRecyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.itemRecyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            switch (SelectionManager.getSampleStatus(requireActivity(), "en", binding.statusTextView.getText().toString())) {
                case "seald":
                case "open":
                    doWork("fill");
                    break;
                case "closed":
                    doWork("open");
                    break;
            }
        }).widget(binding.primaryTextView.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            switch (SelectionManager.getSampleStatus(requireActivity(), "en", binding.statusTextView.getText().toString())) {
                case "seald":
                case "open":
                    doWork("close");
                    break;
                case "closed":
                case "done":
                    doWork("score");
                    break;
            }
        }).widget(binding.secondaryTextView.getRoot());

        binding.profilesTextView.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.profilesTextView.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    IntentManager.download(requireContext(), profileUrls.get(position));

                    binding.profilesTextView.selectSpinner.setSelection(binding.profilesTextView.selectSpinner.getAdapter().getCount());

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.fieldsEditableCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.fieldsEditableCheckBox.setTextColor(getResources().getColor(R.color.Gray900));

                saGensAdapter.setEditable(true);
                saPresAdapter.setEditable(true);
                saItemsAdapter.setEditable(true);
            } else {
                binding.fieldsEditableCheckBox.setTextColor(getResources().getColor(R.color.Gray600));

                saGensAdapter.setEditable(false);
                saPresAdapter.setEditable(false);
                saItemsAdapter.setEditable(false);
            }
        });
    }

    private void setArgs() {
        sampleModel = (SampleModel) SampleFragmentArgs.fromBundle(getArguments()).getTypeModel();

        setData(sampleModel);
    }

    private void setData(SampleModel model) {
        if (model.getSampleId() != null && !model.getSampleId().equals("")) {
            data.put("id", model.getSampleId());
        }

        if (model.getSampleScaleTitle() != null && !model.getSampleScaleTitle().equals("")) {
            binding.nameTextView.setText(model.getSampleScaleTitle());
        }

        if (model.getClient() != null && model.getClient().getName() != null) {
            binding.referenceTextView.setText(model.getClient().getName());
        }

        setStatus(model.getSampleStatus());
    }

    private void setStatus(String status) {
        binding.statusTextView.setText(SelectionManager.getSampleStatus(requireActivity(), "fa", status));

        switch (status) {
            case "seald":
            case "open":
                binding.primaryTextView.getRoot().setVisibility(View.VISIBLE);
                binding.secondaryTextView.getRoot().setVisibility(View.VISIBLE);
                binding.profilesTextView.getRoot().setVisibility(View.GONE);

                InitManager.txtTextColor(binding.primaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentFill), getResources().getColor(R.color.Gray500));
                InitManager.txtTextColor(binding.secondaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentClose), getResources().getColor(R.color.Gray500));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    binding.primaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);
                    binding.secondaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);
                } else {
                    binding.primaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);
                    binding.secondaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);
                }

                binding.scoringConstraintLayout.setVisibility(View.GONE);

                binding.profilesGroup.setVisibility(View.GONE);

                binding.fieldsEditableCheckBox.setVisibility(View.VISIBLE);
                break;
            case "closed":
                binding.primaryTextView.getRoot().setVisibility(View.VISIBLE);
                binding.secondaryTextView.getRoot().setVisibility(View.VISIBLE);
                binding.profilesTextView.getRoot().setVisibility(View.GONE);

                InitManager.txtTextColor(binding.primaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentOpen), getResources().getColor(R.color.Blue600));
                InitManager.txtTextColor(binding.secondaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentScore), getResources().getColor(R.color.White));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    binding.primaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_blue600_ripple_blue300);
                    binding.secondaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
                } else {
                    binding.primaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_blue600);
                    binding.secondaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
                }

                binding.scoringConstraintLayout.setVisibility(View.GONE);

                binding.profilesGroup.setVisibility(View.GONE);

                binding.fieldsEditableCheckBox.setVisibility(View.GONE);
                break;
            case "scoring":
            case "creating_files":
                binding.primaryTextView.getRoot().setVisibility(View.GONE);
                binding.secondaryTextView.getRoot().setVisibility(View.GONE);
                binding.profilesTextView.getRoot().setVisibility(View.GONE);

                doWork("getScore");

                binding.scoringConstraintLayout.setVisibility(View.VISIBLE);

                binding.profilesGroup.setVisibility(View.GONE);

                binding.fieldsEditableCheckBox.setVisibility(View.GONE);
                break;
            case "done":
                binding.primaryTextView.getRoot().setVisibility(View.GONE);
                binding.secondaryTextView.getRoot().setVisibility(View.VISIBLE);
                binding.profilesTextView.getRoot().setVisibility(View.VISIBLE);

                InitManager.txtTextColor(binding.profilesTextView.selectTextView, getResources().getString(R.string.SampleFragmentProfiles), getResources().getColor(R.color.Blue600));
                InitManager.txtTextColor(binding.secondaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentScore), getResources().getColor(R.color.White));

                ImageViewCompat.setImageTintList(binding.profilesTextView.angleImageView, AppCompatResources.getColorStateList(requireActivity(), R.color.Blue600));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    binding.profilesTextView.selectTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_blue600);
                    binding.secondaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
                } else {
                    binding.profilesTextView.selectTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_blue600);
                    binding.secondaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
                }

                binding.scoringConstraintLayout.setVisibility(View.GONE);

                setProfiles();

                binding.fieldsEditableCheckBox.setVisibility(View.GONE);
                break;
        }
    }

    private void setProfiles() {
        List profilesPngs = new List();
        ArrayList<String> profilesExecs = new ArrayList<>();
        profileUrls = new ArrayList<>();

        for (int i = 0; i < sampleModel.getProfiles().size(); i++) {
            ProfileModel profile = (ProfileModel) sampleModel.getProfiles().data().get(i);

            if (profile.getExec().equals("png"))
                profilesPngs.add(profile);

            profilesExecs.add(profile.getExec());
            profileUrls.add(profile.getUrl());
        }

        profilesExecs.add("");

        InitManager.unfixedCustomSpinner(requireActivity(), binding.profilesTextView.selectSpinner, profilesExecs, "profiles");

        if (!profilesPngs.data().isEmpty()) {
            profilesAdapter.setProfiles(profilesPngs.data());
            binding.profilesSingleLayout.recyclerView.setAdapter(profilesAdapter);

            binding.profilesSingleLayout.textView.setVisibility(View.GONE);
        } else {
            binding.profilesSingleLayout.textView.setVisibility(View.VISIBLE);
        }
        binding.profilesHeaderIncludeLayout.countTextView.setText(StringManager.bracing(profilesAdapter.getItemCount()));

        binding.profilesGroup.setVisibility(View.VISIBLE);
    }

    private void getData() {
        Sample.show(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                sampleModel = (SampleModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            setData(sampleModel);

                            ArrayList<String> generals = new ArrayList<>();
                            generals.add(String.valueOf(sampleModel.getCornometer()));

                            // Gens Data
                            if (generals.size() != 0) {
                                saGensAdapter.setItems(generals);
                                binding.generalRecyclerView.setAdapter(saGensAdapter);
                            }

                            List prerequisites = new List();
                            List items = new List();

                            for (int i = 0; i < sampleModel.getSampleForm().getSampleForm().length(); i++) {
                                FormModel model = (FormModel) sampleModel.getSampleForm().getSampleForm().get(i);

                                if (model.getType().equals("prerequisites"))
                                    prerequisites = (List) model.getObject();
                                else if (model.getType().equals("item"))
                                    items.add((TypeModel) model.getObject());
                            }

                            // Prerequisite Data
                            if (!prerequisites.data().isEmpty()) {
                                saPresAdapter.setItems(prerequisites.data());
                                binding.prerequisiteRecyclerView.setAdapter(saPresAdapter);
                            }

                            // Items Data
                            if (!items.data().isEmpty()) {
                                saItemsAdapter.setItems(items.data());
                                binding.itemRecyclerView.setAdapter(saItemsAdapter);
                            }

                            binding.fieldsHeaderIncludeLayout.countTextView.setText(StringManager.bracing(saGensAdapter.getItemCount() + saPresAdapter.getItemCount() + saItemsAdapter.getItemCount()));

                            // Generals Data
                            binding.generalRecyclerView.setVisibility(View.VISIBLE);
                            binding.generalShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.generalShimmerLayout.getRoot().stopShimmer();

                            // Prerequisites Data
                            binding.prerequisiteRecyclerView.setVisibility(View.VISIBLE);
                            binding.prerequisiteShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.prerequisiteShimmerLayout.getRoot().stopShimmer();

                            // Items Data
                            binding.itemRecyclerView.setVisibility(View.VISIBLE);
                            binding.itemShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.itemShimmerLayout.getRoot().stopShimmer();

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

                        // Generals Data
                        binding.generalRecyclerView.setVisibility(View.VISIBLE);
                        binding.generalShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.generalShimmerLayout.getRoot().stopShimmer();

                        // Prerequisites Data
                        binding.prerequisiteRecyclerView.setVisibility(View.VISIBLE);
                        binding.prerequisiteShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.prerequisiteShimmerLayout.getRoot().stopShimmer();

                        // Items Data
                        binding.itemRecyclerView.setVisibility(View.VISIBLE);
                        binding.itemShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.itemShimmerLayout.getRoot().stopShimmer();
                    });
                }
            }
        });
    }

    private void doWork(String method) {
//        switch (method) {
//            case "fill":
//                Sample.fill(data, header, new Response() {
//                    @Override
//                    public void onOK(Object object) {
//                        if (isAdded()) {
//                            requireActivity().runOnUiThread(() -> {
//
//                            });
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(String response) {
//                        if (isAdded()) {
//                            requireActivity().runOnUiThread(() -> {
//                                // TODO : Place Code If Needed
//                            });
//                        }
//                    }
//                });
//                break;
//            case "close":
//                Sample.close(data, header, new Response() {
//                    @Override
//                    public void onOK(Object object) {
//                        if (isAdded()) {
//                            requireActivity().runOnUiThread(() -> {
//
//                            });
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(String response) {
//                        if (isAdded()) {
//                            requireActivity().runOnUiThread(() -> {
//                                // TODO : Place Code If Needed
//                            });
//                        }
//                    }
//                });
//                break;
//            case "open":
//                Sample.open(data, header, new Response() {
//                    @Override
//                    public void onOK(Object object) {
//                        if (isAdded()) {
//                            requireActivity().runOnUiThread(() -> {
//
//                            });
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(String response) {
//                        if (isAdded()) {
//                            requireActivity().runOnUiThread(() -> {
//                                // TODO : Place Code If Needed
//                            });
//                        }
//                    }
//                });
//                break;
//            case "score":
//                Sample.score(data, header, new Response() {
//                    @Override
//                    public void onOK(Object object) {
//                        if (isAdded()) {
//                            requireActivity().runOnUiThread(() -> {
//
//                            });
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(String response) {
//                        if (isAdded()) {
//                            requireActivity().runOnUiThread(() -> {
//                                // TODO : Place Code If Needed
//                            });
//                        }
//                    }
//                });
//                break;
//            case "getScore":
//                Sample.getScore(data, header, new Response() {
//                    @Override
//                    public void onOK(Object object) {
//                        if (isAdded()) {
//                            requireActivity().runOnUiThread(() -> {
//
//                            });
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(String response) {
//                        if (isAdded()) {
//                            requireActivity().runOnUiThread(() -> {
//                                // TODO : Place Code If Needed
//                            });
//                        }
//                    }
//                });
//                break;
//        }
    }

    public void sendGen(String key, String value) {

    }

    public void sendPre(int key, String value) {

    }

    public void sendItem(int key, String value) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}