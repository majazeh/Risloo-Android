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
import com.mre.ligheh.Model.Madule.SampleAnswers;
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
    private ProfilesAdapter profileHalfsAdapter, profileExtrasAdapter;
    private SaGensAdapter saGensAdapter;
    private SaPresAdapter saPresAdapter;
    private SaItemsAdapter saItemsAdapter;

    // Vars
    private HashMap data, header;
    private SampleAnswers sampleAnswers;
    private SampleModel sampleModel;
    private boolean isLoading = true, userSelect = false;
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
        profileHalfsAdapter = new ProfilesAdapter(requireActivity());
        profileExtrasAdapter = new ProfilesAdapter(requireActivity());
        saGensAdapter = new SaGensAdapter(requireActivity());
        saPresAdapter = new SaPresAdapter(requireActivity());
        saItemsAdapter = new SaItemsAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        sampleAnswers = new SampleAnswers();

        binding.profileHalfsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.SampleFragmentProfileHalfHeader));
        binding.profileExtrasHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.SampleFragmentProfileExtraHeader));
        binding.fieldsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.SampleFragmentFieldHeader));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.profileHalfsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.profileExtrasSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
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

                    binding.profilesTextView.selectSpinner.setSelection(parent.getAdapter().getCount());

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
            sampleAnswers.id = model.getSampleId();
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

        setButtons(status);

        setScoring(status);

        if (!isLoading) {
            setEditable(status);

            setProfiles(status);
        }
    }

    private void setButtons(String status) {
        switch (status) {
            case "seald":
            case "open":
                binding.primaryTextView.getRoot().setVisibility(View.VISIBLE);
                binding.secondaryTextView.getRoot().setVisibility(View.VISIBLE);

                InitManager.txtTextColor(binding.primaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentFill), getResources().getColor(R.color.Gray500));
                InitManager.txtTextColor(binding.secondaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentClose), getResources().getColor(R.color.Gray500));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    binding.primaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);
                    binding.secondaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);
                } else {
                    binding.primaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);
                    binding.secondaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);
                }

                break;
            case "closed":
                binding.primaryTextView.getRoot().setVisibility(View.VISIBLE);
                binding.secondaryTextView.getRoot().setVisibility(View.VISIBLE);

                InitManager.txtTextColor(binding.primaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentOpen), getResources().getColor(R.color.Blue600));
                InitManager.txtTextColor(binding.secondaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentScore), getResources().getColor(R.color.White));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    binding.primaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_blue600_ripple_blue300);
                    binding.secondaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
                } else {
                    binding.primaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_blue600);
                    binding.secondaryTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
                }

                break;
            case "scoring":
            case "creating_files":
                binding.primaryTextView.getRoot().setVisibility(View.GONE);
                binding.secondaryTextView.getRoot().setVisibility(View.GONE);
                break;
            case "done":
                binding.primaryTextView.getRoot().setVisibility(View.GONE);
                binding.secondaryTextView.getRoot().setVisibility(View.VISIBLE);

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

                break;
        }
    }

    private void setScoring(String status) {
        if (status.equals("scoring") || status.equals("creating_files")) {
            binding.scoringConstraintLayout.setVisibility(View.VISIBLE);
            doWork("getScore");
        } else {
            binding.scoringConstraintLayout.setVisibility(View.GONE);
        }
    }

    private void setEditable(String status) {
        if (status.equals("seald") || status.equals("open")) {
            binding.fieldsEditableCheckBox.setVisibility(View.VISIBLE);
        } else {
            binding.fieldsEditableCheckBox.setVisibility(View.GONE);

            if (binding.fieldsEditableCheckBox.isChecked()) {
                binding.fieldsEditableCheckBox.setChecked(false);

                saGensAdapter.setEditable(false);
                saPresAdapter.setEditable(false);
                saItemsAdapter.setEditable(false);
            }
        }
    }

    private void setProfiles(String status) {
        if (status.equals("done")) {
            binding.profilesTextView.getRoot().setVisibility(View.VISIBLE);

            ArrayList<String> options = new ArrayList<>();
            profileUrls = new ArrayList<>();

            for (int i = 0; i < sampleModel.getProfiles().size(); i++) {
                ProfileModel profile = (ProfileModel) sampleModel.getProfiles().data().get(i);

                options.add(profile.getFile_name());
                profileUrls.add(profile.getUrl());
            }

            options.add("");
            profileUrls.add("");

            InitManager.profileCustomSpinner(requireActivity(), binding.profilesTextView.selectSpinner, options);

            // Profile Half
            if (sampleModel.getProfilesHalf() != null && !sampleModel.getProfilesHalf().data().isEmpty()) {
                profileHalfsAdapter.setProfiles(sampleModel.getProfilesHalf().data(), false);
                binding.profileHalfsSingleLayout.recyclerView.setAdapter(profileHalfsAdapter);

                binding.profileHalfsSingleLayout.emptyView.setVisibility(View.GONE);
            } else if (profileHalfsAdapter.getItemCount() == 0) {
                binding.profileHalfsSingleLayout.emptyView.setVisibility(View.VISIBLE);
            }

            binding.profileHalfsHeaderIncludeLayout.countTextView.setText(StringManager.bracing(profileHalfsAdapter.getItemCount()));
            binding.profileHalfsGroup.setVisibility(View.VISIBLE);

            // Profile Extra
            if (sampleModel.getProfilesExtra() != null && !sampleModel.getProfilesExtra().data().isEmpty()) {
                profileExtrasAdapter.setProfiles(sampleModel.getProfilesExtra().data(), true);
                binding.profileExtrasSingleLayout.recyclerView.setAdapter(profileExtrasAdapter);

                binding.profileExtrasGroup.setVisibility(View.VISIBLE);
            } else if (profileExtrasAdapter.getItemCount() == 0) {
                binding.profileExtrasGroup.setVisibility(View.GONE);
            }

            binding.profileExtrasHeaderIncludeLayout.countTextView.setText(StringManager.bracing(profileExtrasAdapter.getItemCount()));
        } else {
            binding.profilesTextView.getRoot().setVisibility(View.GONE);

            binding.profileHalfsGroup.setVisibility(View.GONE);
            binding.profileExtrasGroup.setVisibility(View.GONE);
        }
    }

    private void getData() {
        Sample.show(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                sampleModel = (SampleModel) object;

                isLoading = false;

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
                            } else if (saGensAdapter.getItemCount() == 0) {
                                binding.generalConstraintLayout.setVisibility(View.GONE);
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
                            } else if (saPresAdapter.getItemCount() == 0) {
                                binding.prerequisiteConstraintLayout.setVisibility(View.GONE);
                            }

                            // Items Data
                            if (!items.data().isEmpty()) {
                                saItemsAdapter.setItems(items.data());
                                binding.itemRecyclerView.setAdapter(saItemsAdapter);
                            } else if (saItemsAdapter.getItemCount() == 0) {
                                binding.itemConstraintLayout.setVisibility(View.GONE);
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
                        isLoading = false;

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
        if (!method.equals("getScore"))
            ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        switch (method) {
            case "fill":
                Sample.fill(data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                ((MainActivity) requireActivity()).loadingDialog.dismiss();
                                // TODO : Place Code If Needed
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
                break;
            case "close":
                Sample.close(sampleAnswers, data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                ((MainActivity) requireActivity()).loadingDialog.dismiss();
                                setStatus("closed");
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
                break;
            case "open":
                Sample.open(data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                ((MainActivity) requireActivity()).loadingDialog.dismiss();
                                setStatus("open");
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
                break;
            case "score":
                Sample.score(data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        sampleModel = (SampleModel) object;

                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                ((MainActivity) requireActivity()).loadingDialog.dismiss();
                                setStatus(sampleModel.getSampleStatus());
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
                break;
            case "getScore":
                Sample.getScore(data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                setStatus("done");
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
                break;
        }
    }

    public void sendGen(String key, String value) {
        HashMap newData = new HashMap<>();

        newData.put("id", data.get("id"));
        newData.put(key, value);

        Sample.publicItems(newData, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        // TODO : Place Code If Needed
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

    public void sendPre(int key, String value) {
        sampleAnswers.addToPrerequisites(key, value);
        sampleAnswers.sendPrerequisites(((MainActivity) requireActivity()).singleton.getToken(), new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        // TODO : Place Code If Needed
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

    public void sendItem(int key, String value) {
        sampleAnswers.addToRemote(key, value);
        sampleAnswers.sendRequest(((MainActivity) requireActivity()).singleton.getToken(), new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        // TODO : Place Code If Needed
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}