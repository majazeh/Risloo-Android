package com.majazeh.risloo.Views.Fragments.Main.Show;

import android.annotation.SuppressLint;
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
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.ProfilesAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Index.IndexGenAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Index.IndexItemAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Index.IndexPreAdapter;
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
    private IndexGenAdapter indexGenAdapter;
    private IndexPreAdapter indexPreAdapter;
    private IndexItemAdapter indexItemAdapter;

    // Models
    private SampleAnswers sampleAnswers;
    private SampleModel sampleModel;

    // Objects
    private HashMap data, header;

    // Vars
    private ArrayList<String> profileUrls;
    private boolean isLoading = true, userSelect = false;

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
        indexGenAdapter = new IndexGenAdapter(requireActivity());
        indexPreAdapter = new IndexPreAdapter(requireActivity());
        indexItemAdapter = new IndexItemAdapter(requireActivity());

        sampleAnswers = new SampleAnswers();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.profileHalfsHeaderLayout.titleTextView.setText(getResources().getString(R.string.SampleFragmentProfileHalfHeader));
        binding.profileExtrasHeaderLayout.titleTextView.setText(getResources().getString(R.string.SampleFragmentProfileExtraHeader));
        binding.fieldsHeaderLayout.titleTextView.setText(getResources().getString(R.string.SampleFragmentFieldHeader));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.profileHalfsRecyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.profileExtrasRecyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.generalRecyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.prerequisiteRecyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.itemRecyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            String status = SelectionManager.getSampleStatus(requireActivity(), "en", binding.statusTextView.getText().toString());

            switch (status) {
                case "seald":
                case "open":
                    fillSample();
                    break;
                case "closed":
                    openSample();
                    break;
            }
        }).widget(binding.primaryTextView.getRoot());

        CustomClickView.onDelayedListener(() -> {
            String status = SelectionManager.getSampleStatus(requireActivity(), "en", binding.statusTextView.getText().toString());

            switch (status) {
                case "seald":
                case "open":
                    closeSample();
                    break;
                case "closed":
                case "done":
                    scoreSample();
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

                    parent.setSelection(parent.getAdapter().getCount());

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.fieldsCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.fieldsCheckBox.setTextColor(getResources().getColor(R.color.CoolGray900));

                indexGenAdapter.setEditable(true);
                indexPreAdapter.setEditable(true);
                indexItemAdapter.setEditable(true);
            } else {
                binding.fieldsCheckBox.setTextColor(getResources().getColor(R.color.CoolGray600));

                indexGenAdapter.setEditable(false);
                indexPreAdapter.setEditable(false);
                indexItemAdapter.setEditable(false);
            }
        });
    }

    private void setArgs() {
        sampleModel = (SampleModel) SampleFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(sampleModel);
    }

    private void setData(SampleModel model) {
        if (model.getSampleId() != null && !model.getSampleId().equals("")) {
            binding.serialTextView.setText(model.getSampleId());
            data.put("id", model.getSampleId());
            sampleAnswers.id = model.getSampleId();
        }

        if (model.getSampleScaleTitle() != null && !model.getSampleScaleTitle().equals("")) {
            binding.scaleTextView.setText(model.getSampleScaleTitle());
        }

        if (model.getSampleEdition() != null && !model.getSampleEdition().equals("")) {
            binding.editionTextView.setText(model.getSampleEdition() + " - نسخه " + model.getSampleVersion());
        } else {
            binding.editionTextView.setText("نسخه " + model.getSampleVersion());
        }

        if (model.getClient() != null) {
            binding.referenceTextView.setText(model.getClient().getName());
        }

        setStatus(model.getSampleStatus());
    }

    private void setStatus(String status) {
        setText(status);

        setButtons(status);

        setScoring(status);

        if (!isLoading) {
            setEditable(status);

            setProfiles(status);
        }
    }

    private void setText(String status) {
        binding.statusTextView.setText(SelectionManager.getSampleStatus(requireActivity(), "fa", status));

        switch (status) {
            case "scoring":
            case "creating_files":
                binding.statusTextView.setTextColor(getResources().getColor(R.color.Amber500));
                break;
            default:
                binding.statusTextView.setTextColor(getResources().getColor(R.color.CoolGray500));
                break;
        }
    }

    private void setButtons(String status) {
        switch (status) {
            case "seald":
            case "open":
                binding.primaryTextView.getRoot().setVisibility(View.VISIBLE);
                binding.secondaryTextView.getRoot().setVisibility(View.VISIBLE);

                InitManager.txtTextColorBackground(binding.primaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentFill), getResources().getColor(R.color.CoolGray500), R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);
                InitManager.txtTextColorBackground(binding.secondaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentClose), getResources().getColor(R.color.CoolGray500), R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);

                break;
            case "closed":
                binding.primaryTextView.getRoot().setVisibility(View.VISIBLE);
                binding.secondaryTextView.getRoot().setVisibility(View.VISIBLE);

                InitManager.txtTextColorBackground(binding.primaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentOpen), getResources().getColor(R.color.LightBlue600), R.drawable.draw_16sdp_solid_white_border_1sdp_blue600_ripple_blue300);
                InitManager.txtTextColorBackground(binding.secondaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentScore), getResources().getColor(R.color.White), R.drawable.draw_16sdp_solid_blue500_ripple_blue800);

                break;
            case "scoring":
            case "creating_files":
                binding.primaryTextView.getRoot().setVisibility(View.GONE);
                binding.secondaryTextView.getRoot().setVisibility(View.GONE);
                break;
            case "done":
                binding.primaryTextView.getRoot().setVisibility(View.GONE);
                binding.secondaryTextView.getRoot().setVisibility(View.VISIBLE);

                InitManager.txtTextColorBackground(binding.profilesTextView.selectTextView, getResources().getString(R.string.SampleFragmentProfiles), getResources().getColor(R.color.LightBlue600), R.drawable.draw_16sdp_solid_transparent_border_1sdp_blue600);
                InitManager.txtTextColorBackground(binding.secondaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentScore), getResources().getColor(R.color.White), R.drawable.draw_16sdp_solid_blue500_ripple_blue800);

                ImageViewCompat.setImageTintList(binding.profilesTextView.angleImageView, AppCompatResources.getColorStateList(requireActivity(), R.color.LightBlue600));

                break;
        }
    }

    private void setScoring(String status) {
        if (status.equals("scoring") || status.equals("creating_files")) {
            binding.scoringConstraintLayout.setVisibility(View.VISIBLE);
            getScore();
        } else {
            binding.scoringConstraintLayout.setVisibility(View.GONE);
        }
    }

    private void setEditable(String status) {
        if (status.equals("seald") || status.equals("open")) {
            binding.fieldsCheckBox.setVisibility(View.VISIBLE);
        } else {
            binding.fieldsCheckBox.setVisibility(View.GONE);

            if (binding.fieldsCheckBox.isChecked()) {
                binding.fieldsCheckBox.setChecked(false);

                indexGenAdapter.setEditable(false);
                indexPreAdapter.setEditable(false);
                indexItemAdapter.setEditable(false);
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
                profileHalfsAdapter.setItems(sampleModel.getProfilesHalf().data(), false);
                binding.profileHalfsRecyclerView.setAdapter(profileHalfsAdapter);

                binding.profileHalfsGroup.setVisibility(View.VISIBLE);
            } else if (profileHalfsAdapter.getItemCount() == 0) {
                binding.profileHalfsGroup.setVisibility(View.GONE);
            }

            // Profile Extra
            if (sampleModel.getProfilesExtra() != null && !sampleModel.getProfilesExtra().data().isEmpty()) {
                profileExtrasAdapter.setItems(sampleModel.getProfilesExtra().data(), true);
                binding.profileExtrasRecyclerView.setAdapter(profileExtrasAdapter);

                binding.profileExtrasGroup.setVisibility(View.VISIBLE);
            } else if (profileExtrasAdapter.getItemCount() == 0) {
                binding.profileExtrasGroup.setVisibility(View.GONE);
            }

            binding.profileHalfsHeaderLayout.countTextView.setText(StringManager.bracing(profileHalfsAdapter.getItemCount()));
            binding.profileExtrasHeaderLayout.countTextView.setText(StringManager.bracing(profileExtrasAdapter.getItemCount()));

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
                                indexGenAdapter.setItems(generals);
                                binding.generalRecyclerView.setAdapter(indexGenAdapter);
                            } else if (indexGenAdapter.getItemCount() == 0) {
                                binding.generalRecyclerView.setVisibility(View.GONE);
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
                                indexPreAdapter.setItems(prerequisites.data());
                                binding.prerequisiteRecyclerView.setAdapter(indexPreAdapter);
                            } else if (indexPreAdapter.getItemCount() == 0) {
                                binding.prerequisiteRecyclerView.setVisibility(View.GONE);
                            }

                            // Items Data
                            if (!items.data().isEmpty()) {
                                indexItemAdapter.setItems(items.data());
                                binding.itemRecyclerView.setAdapter(indexItemAdapter);
                            } else if (indexItemAdapter.getItemCount() == 0) {
                                binding.itemRecyclerView.setVisibility(View.GONE);
                            }

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

    private void getScore() {
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
    }

    private void fillSample() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        Sample.fill(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
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

    private void closeSample() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        Sample.close(sampleAnswers, data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
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
    }

    private void openSample() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        Sample.open(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
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
    }

    private void scoreSample() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        Sample.score(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                sampleModel = (SampleModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
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
        isLoading = true;
        userSelect = false;
    }

}