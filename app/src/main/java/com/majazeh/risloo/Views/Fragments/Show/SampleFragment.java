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
import com.majazeh.risloo.Views.Adapters.Recycler.FieldsAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.ProfilesAdapter;
import com.majazeh.risloo.databinding.FragmentSampleBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.TypeModel.SampleModel;

import java.util.ArrayList;
import java.util.HashMap;

public class SampleFragment extends Fragment {

    // Binding
    private FragmentSampleBinding binding;

    // Adapters
    private ProfilesAdapter profilesAdapter;
    private FieldsAdapter fieldsGeneralAdapter, fieldsPrerequisiteAdapter, fieldsItemAdapter;

    // Vars
    private HashMap data, header;
    private SampleModel sampleModel;
    private boolean userSelect = false;

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
        fieldsGeneralAdapter = new FieldsAdapter(requireActivity());
        fieldsPrerequisiteAdapter = new FieldsAdapter(requireActivity());
        fieldsItemAdapter = new FieldsAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.profilesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.SampleFragmentProfileHeader));
        binding.fieldsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.SampleFragmentFieldHeader));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.profilesRecyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.fieldsGeneralRecyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.fieldsPrerequisiteRecyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.fieldsAnswerRecyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
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
                    String profile = parent.getItemAtPosition(position).toString();

                    IntentManager.download(requireContext(), profile);

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

                fieldsGeneralAdapter.setEditable(true);
                fieldsPrerequisiteAdapter.setEditable(true);
                fieldsItemAdapter.setEditable(true);
            } else {
                binding.fieldsEditableCheckBox.setTextColor(getResources().getColor(R.color.Gray600));

                fieldsGeneralAdapter.setEditable(false);
                fieldsPrerequisiteAdapter.setEditable(false);
                fieldsItemAdapter.setEditable(false);
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
                break;
            case "scoring":
            case "creating_files":
                binding.primaryTextView.getRoot().setVisibility(View.GONE);
                binding.secondaryTextView.getRoot().setVisibility(View.GONE);
                binding.profilesTextView.getRoot().setVisibility(View.GONE);

                doWork("getScore");

                binding.scoringConstraintLayout.setVisibility(View.VISIBLE);
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
                break;
        }
    }

    private void setProfiles(SampleModel model) {
        ArrayList<String> profiles = new ArrayList<>();

        profiles.add("");
        profiles.add("");
        profiles.add("");
        profiles.add("");

        InitManager.unfixedCustomSpinner(requireActivity(), binding.profilesTextView.selectSpinner, profiles, "profiles");
    }

    private void getData() {
        Sample.show(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                sampleModel = (SampleModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        setData(sampleModel);

//                        // Profiles Data
//                        if (!model.getProfiles().data().isEmpty()) {
//                            profilesAdapter.setProfiles(model.getProfiles().data());
//                            binding.profilesRecyclerView.setAdapter(profilesAdapter);
//                        }
//
//                        // Fields General Data
//                        if (!model.getGenerals().data().isEmpty()) {
//                            fieldsGeneralAdapter.setFields(model.getGenerals().data());
//                            binding.fieldsGeneralRecyclerView.setAdapter(fieldsGeneralAdapter);
//                        }
//
//                        // Fields Prerequisite Data
//                        if (!model.getPrerequisites().data().isEmpty()) {
//                            fieldsPrerequisiteAdapter.setFields(model.getPrerequisites().data());
//                            binding.fieldsPrerequisiteRecyclerView.setAdapter(fieldsPrerequisiteAdapter);
//                        }
//
//                        // Fields Answer Data
//                        if (!model.getAnswers().data().isEmpty()) {
//                            fieldsAnswerAdapter.setFields(model.getAnswers().data());
//                            binding.fieldsAnswerRecyclerView.setAdapter(fieldsAnswerAdapter);
//                        }

                        binding.profilesHeaderIncludeLayout.countTextView.setText(StringManager.bracing(profilesAdapter.getItemCount()));
                        binding.fieldsHeaderIncludeLayout.countTextView.setText(StringManager.bracing(fieldsGeneralAdapter.getItemCount() + fieldsPrerequisiteAdapter.getItemCount() + fieldsItemAdapter.getItemCount()));

                        // Profiles Data
                        binding.profilesRecyclerView.setVisibility(View.VISIBLE);
                        binding.profilesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.profilesShimmerLayout.getRoot().stopShimmer();

                        // Fields General Data
                        binding.fieldsGeneralRecyclerView.setVisibility(View.VISIBLE);
                        binding.fieldsGeneralShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.fieldsGeneralShimmerLayout.getRoot().stopShimmer();

                        // Fields Prerequisite Data
                        binding.fieldsPrerequisiteRecyclerView.setVisibility(View.VISIBLE);
                        binding.fieldsPrerequisiteShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.fieldsPrerequisiteShimmerLayout.getRoot().stopShimmer();

                        // Fields Answer Data
                        binding.fieldsAnswerRecyclerView.setVisibility(View.VISIBLE);
                        binding.fieldsAnswerShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.fieldsAnswerShimmerLayout.getRoot().stopShimmer();
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {

                        // Profiles Data
                        binding.profilesRecyclerView.setVisibility(View.VISIBLE);
                        binding.profilesShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.profilesShimmerLayout.getRoot().stopShimmer();

                        // Fields General Data
                        binding.fieldsGeneralRecyclerView.setVisibility(View.VISIBLE);
                        binding.fieldsGeneralShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.fieldsGeneralShimmerLayout.getRoot().stopShimmer();

                        // Fields Prerequisite Data
                        binding.fieldsPrerequisiteRecyclerView.setVisibility(View.VISIBLE);
                        binding.fieldsPrerequisiteShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.fieldsPrerequisiteShimmerLayout.getRoot().stopShimmer();

                        // Fields Answer Data
                        binding.fieldsAnswerRecyclerView.setVisibility(View.VISIBLE);
                        binding.fieldsAnswerShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.fieldsAnswerShimmerLayout.getRoot().stopShimmer();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}