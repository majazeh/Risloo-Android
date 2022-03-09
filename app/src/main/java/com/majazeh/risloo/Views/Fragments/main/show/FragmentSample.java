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
import com.majazeh.risloo.databinding.FragmentSampleBinding;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.GadgetManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.IntentManager;
import com.majazeh.risloo.utils.managers.SelectionManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.main.Index.IndexProfileAdapter;
import com.majazeh.risloo.views.adapters.recycler.main.Table.TableGenAdapter;
import com.majazeh.risloo.views.adapters.recycler.main.Table.TableItemAdapter;
import com.majazeh.risloo.views.adapters.recycler.main.Table.TablePreAdapter;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.Madule.SampleAnswers;
import com.mre.ligheh.Model.TypeModel.FormModel;
import com.mre.ligheh.Model.TypeModel.ProfileModel;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FragmentSample extends Fragment {

    // Binding
    public FragmentSampleBinding binding;

    // Adapters
    private IndexProfileAdapter indexHalfAdapter, indexExtraAdapter;
    private TableGenAdapter tableGenAdapter;
    private TablePreAdapter tablePreAdapter;
    private TableItemAdapter tableItemAdapter;

    // Models
    public SampleAnswers sampleAnswers;
    private SampleModel sampleModel;

    // Objects
    private HashMap data, header;

    // Vars
    private ArrayList<String> profileUrls;
    private boolean isLoading = true, userSelect = false;
    public String selectedProfileUrl = "";

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
        indexHalfAdapter = new IndexProfileAdapter(requireActivity());
        indexExtraAdapter = new IndexProfileAdapter(requireActivity());
        tableGenAdapter = new TableGenAdapter(requireActivity());
        tablePreAdapter = new TablePreAdapter(requireActivity());
        tableItemAdapter = new TableItemAdapter(requireActivity());

        sampleAnswers = new SampleAnswers();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.halfsHeaderLayout.titleTextView.setText(getResources().getString(R.string.SampleFragmentProfileHalfHeader));
        binding.extrasHeaderLayout.titleTextView.setText(getResources().getString(R.string.SampleFragmentProfileExtraHeader));
        binding.fieldsHeaderLayout.titleTextView.setText(getResources().getString(R.string.SampleFragmentFieldHeader));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.halfsRecyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.extrasRecyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.generalRecyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._6sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.prerequisiteRecyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._6sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.itemRecyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._6sdp), getResources().getDimension(R.dimen._12sdp));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            String status = SelectionManager.getSampleStatus(requireActivity(), "en", binding.statusTextView.getText().toString());

            switch (status) {
                case "seald":
                case "open":
                    doWork("fillSample");
                    break;
                case "closed":
                    doWork("openSample");
                    break;
            }
        }).widget(binding.primaryTextView.getRoot());

        CustomClickView.onDelayedListener(() -> {
            String status = SelectionManager.getSampleStatus(requireActivity(), "en", binding.statusTextView.getText().toString());

            switch (status) {
                case "seald":
                case "open":
                    doWork("closeSample");
                    break;
                case "closed":
                case "done":
                    doWork("scoreSample");
                    break;
            }
        }).widget(binding.secondaryTextView.getRoot());

        binding.profilesTextView.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        binding.profilesTextView.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        binding.profilesTextView.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    selectedProfileUrl = profileUrls.get(position);

                    if (GadgetManager.permissionSendTo(requireActivity()))
                        IntentManager.download(requireActivity(), binding.scaleTextView.getText().toString(), selectedProfileUrl);

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
                binding.fieldsCheckBox.setTextColor(getResources().getColor(R.color.coolGray900));

                tableGenAdapter.setEditable(true);
                tablePreAdapter.setEditable(true);
                tableItemAdapter.setEditable(true);
            } else {
                binding.fieldsCheckBox.setTextColor(getResources().getColor(R.color.coolGray600));

                tableGenAdapter.setEditable(false);
                tablePreAdapter.setEditable(false);
                tableItemAdapter.setEditable(false);
            }
        });
    }

    private void setArgs() {
        sampleModel = (SampleModel) FragmentSampleArgs.fromBundle(getArguments()).getTypeModel();
        setData(sampleModel);
    }

    private void setData(SampleModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            binding.serialTextView.setText(model.getId());

            data.put("id", model.getId());
            sampleAnswers.id = model.getId();
        }

        if (model.getScaleTitle() != null && !model.getScaleTitle().equals("")) {
            binding.scaleTextView.setText(model.getScaleTitle());
        }

        if (model.getScaleTitle() != null && !model.getEdition().equals("") && model.getVersion() != 0) {
            binding.editionTextView.setText(model.getEdition() + " - نسخه " + model.getVersion());
        } else if (model.getVersion() != 0) {
            binding.editionTextView.setText("نسخه " + model.getVersion());
        } else if (model.getScaleTitle() != null && !model.getEdition().equals("")) {
            binding.editionTextView.setText(model.getEdition());
        } else {
            binding.editionTextView.setText("-");
        }

        if (model.getClient() != null) {
            binding.referenceTextView.setText(model.getClient().getName());
        }

        setStatus(model.getStatus());
    }

    private void setStatus(String status) {
        setText(status);

        setButtons(status);

        setScoring(status);

        if (!isLoading) {
//            setEditable(status);

            setProfiles(status);
        }
    }

    private void setText(String status) {
        binding.statusTextView.setText(SelectionManager.getSampleStatus(requireActivity(), "fa", status));

        switch (status) {
            case "scoring":
            case "creating_files":
                binding.statusTextView.setTextColor(getResources().getColor(R.color.amber500));
                break;
            default:
                binding.statusTextView.setTextColor(getResources().getColor(R.color.coolGray500));
                break;
        }
    }

    private void setButtons(String status) {
        switch (status) {
            case "seald":
            case "open":
                binding.primaryTextView.getRoot().setVisibility(View.GONE);
                binding.secondaryTextView.getRoot().setVisibility(View.VISIBLE);

                InitManager.txtTextColorBackground(binding.primaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentFill), getResources().getColor(R.color.coolGray500), R.drawable.draw_16sdp_solid_white_border_1sdp_coolgray500_ripple_coolgray300);
                InitManager.txtTextColorBackground(binding.secondaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentClose), getResources().getColor(R.color.coolGray500), R.drawable.draw_16sdp_solid_white_border_1sdp_coolgray500_ripple_coolgray300);

                break;
            case "closed":
                binding.primaryTextView.getRoot().setVisibility(View.VISIBLE);
                binding.secondaryTextView.getRoot().setVisibility(View.VISIBLE);

                InitManager.txtTextColorBackground(binding.primaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentOpen), getResources().getColor(R.color.risloo500), R.drawable.draw_16sdp_solid_white_border_1sdp_risloo500_ripple_risloo50);
                InitManager.txtTextColorBackground(binding.secondaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentScore), getResources().getColor(R.color.white), R.drawable.draw_16sdp_solid_risloo500_ripple_risloo50);

                break;
            case "scoring":
            case "creating_files":
                binding.primaryTextView.getRoot().setVisibility(View.GONE);
                binding.secondaryTextView.getRoot().setVisibility(View.GONE);
                break;
            case "done":
                binding.primaryTextView.getRoot().setVisibility(View.GONE);
                binding.secondaryTextView.getRoot().setVisibility(View.GONE);

                InitManager.txtTextColorBackground(binding.secondaryTextView.getRoot(), getResources().getString(R.string.SampleFragmentScore), getResources().getColor(R.color.white), R.drawable.draw_16sdp_solid_risloo500_ripple_risloo50);
                InitManager.spinnerRect(requireActivity(), binding.profilesTextView.selectSpinner, binding.profilesTextView.selectTextView, binding.profilesTextView.angleImageView, getResources().getString(R.string.SampleFragmentProfiles), getResources().getColor(R.color.risloo500), R.color.risloo500, R.drawable.draw_16sdp_solid_white_border_1sdp_risloo500_ripple_risloo50);

                break;
        }
    }

    private void setScoring(String status) {
        switch (status) {
            case "scoring":
            case "creating_files":
                binding.scoringConstraintLayout.setVisibility(View.VISIBLE);

                doWork("getScore");
                break;
            default:
                binding.scoringConstraintLayout.setVisibility(View.GONE);
                break;
        }
    }

    private void setEditable(String status) {
        switch (status) {
            case "seald":
            case "open":
                binding.fieldsCheckBox.setVisibility(View.VISIBLE);
                break;
            default:
                binding.fieldsCheckBox.setVisibility(View.GONE);

                if (binding.fieldsCheckBox.isChecked()) {
                    binding.fieldsCheckBox.setChecked(false);

                    tableGenAdapter.setEditable(false);
                    tablePreAdapter.setEditable(false);
                    tableItemAdapter.setEditable(false);
                }
                break;
        }
    }

    private void setProfiles(String status) {
        switch (status) {
            case "done":
                binding.profilesTextView.getRoot().setVisibility(View.VISIBLE);

                setDropdown();

                // Profile Half
                if (sampleModel.getProfilesHalf() != null && !sampleModel.getProfilesHalf().data().isEmpty()) {
                    indexHalfAdapter.setItems(sampleModel.getProfilesHalf().data(), false);
                    binding.halfsRecyclerView.setAdapter(indexHalfAdapter);

                    binding.halfsGroup.setVisibility(View.VISIBLE);
                } else if (indexHalfAdapter.getItemCount() == 0) {
                    binding.halfsRecyclerView.setAdapter(null);

                    binding.halfsGroup.setVisibility(View.GONE);
                }

                // Profile Extras
                if (sampleModel.getProfilesExtra() != null && !sampleModel.getProfilesExtra().data().isEmpty()) {
                    indexExtraAdapter.setItems(sampleModel.getProfilesExtra().data(), true);
                    binding.extrasRecyclerView.setAdapter(indexExtraAdapter);

                    binding.extrasGroup.setVisibility(View.VISIBLE);
                } else if (indexExtraAdapter.getItemCount() == 0) {
                    binding.extrasRecyclerView.setAdapter(null);

                    binding.extrasGroup.setVisibility(View.GONE);
                }

                binding.halfsHeaderLayout.countTextView.setText(StringManager.bracing(indexHalfAdapter.getItemCount()));
                binding.extrasHeaderLayout.countTextView.setText(StringManager.bracing(indexExtraAdapter.getItemCount()));

                break;
            default:
                binding.profilesTextView.getRoot().setVisibility(View.GONE);

                binding.halfsGroup.setVisibility(View.GONE);
                binding.extrasGroup.setVisibility(View.GONE);
                break;
        }
    }

    private void setDropdown() {
        ArrayList<String> items = new ArrayList<>();
        profileUrls = new ArrayList<>();

        for (int i = 0; i < sampleModel.getProfiles().size(); i++) {
            ProfileModel profile = (ProfileModel) sampleModel.getProfiles().data().get(i);

            items.add(StringManager.adjustProfile(profile.getMode()));
            profileUrls.add(profile.getUrl());
        }

        items.add("");
        profileUrls.add("");

        if (items.size() > 2) {
            InitManager.selectProfileSpinner(requireActivity(), binding.profilesTextView.selectSpinner, items);
        } else {
            binding.profilesTextView.getRoot().setVisibility(View.GONE);
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
                                tableGenAdapter.setItems(generals);
                                binding.generalRecyclerView.setAdapter(tableGenAdapter);
                            } else if (tableGenAdapter.getItemCount() == 0) {
                                binding.generalRecyclerView.setAdapter(null);

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
                                tablePreAdapter.setItems(prerequisites.data());
                                binding.prerequisiteRecyclerView.setAdapter(tablePreAdapter);
                            } else if (tablePreAdapter.getItemCount() == 0) {
                                binding.prerequisiteRecyclerView.setAdapter(null);

                                binding.prerequisiteRecyclerView.setVisibility(View.GONE);
                            }

                            // Items Data
                            if (!items.data().isEmpty()) {
                                tableItemAdapter.setItems(items.data());
                                binding.itemRecyclerView.setAdapter(tableItemAdapter);
                            } else if (tableItemAdapter.getItemCount() == 0) {
                                binding.itemRecyclerView.setAdapter(null);

                                binding.itemRecyclerView.setVisibility(View.GONE);
                            }

                            hideShimmer();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                isLoading = false;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        hideShimmer();
                    });
                }
            }
        });
    }

    private void doWork(String method) {
        switch (method) {
            case "getScore":
                Sample.getScore(data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackSampleScored));

                                setStatus("done");
                            });
                        }
                    }

                    @Override
                    public void onFailure(String response) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                try {
                                    JSONObject responseObject = new JSONObject(response);
                                    if (!responseObject.isNull("errors")) {
                                        JSONObject errorsObject = responseObject.getJSONObject("errors");

                                        Iterator<String> keys = (errorsObject.keys());
                                        StringBuilder allErrors = new StringBuilder();

                                        while (keys.hasNext()) {
                                            String key = keys.next();

                                            for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                                String error = errorsObject.getJSONArray(key).getString(i);

                                                allErrors.append(error);
                                                allErrors.append("\n");
                                            }
                                        }

                                        SnackManager.showSnackError(requireActivity(), allErrors.substring(0, allErrors.length() - 1));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    }
                });
                break;
            case "fillSample":
                DialogManager.showDialogLoading(requireActivity(), "");

                Sample.fill(data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                DialogManager.dismissDialogLoading();
                                SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackSampleFilled));

                                // TODO : Place Code If Needed
                            });
                        }
                    }

                    @Override
                    public void onFailure(String response) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                try {
                                    JSONObject responseObject = new JSONObject(response);
                                    if (!responseObject.isNull("errors")) {
                                        JSONObject errorsObject = responseObject.getJSONObject("errors");

                                        Iterator<String> keys = (errorsObject.keys());
                                        StringBuilder allErrors = new StringBuilder();

                                        while (keys.hasNext()) {
                                            String key = keys.next();

                                            for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                                String error = errorsObject.getJSONArray(key).getString(i);

                                                allErrors.append(error);
                                                allErrors.append("\n");
                                            }
                                        }

                                        SnackManager.showSnackError(requireActivity(), allErrors.substring(0, allErrors.length() - 1));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    }
                });
                break;
            case "closeSample":
                DialogManager.showDialogLoading(requireActivity(), "");

                Sample.close(sampleAnswers, data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                DialogManager.dismissDialogLoading();
                                SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackSampleClosed));

                                setStatus("closed");
                            });
                        }
                    }

                    @Override
                    public void onFailure(String response) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                try {
                                    JSONObject responseObject = new JSONObject(response);
                                    if (!responseObject.isNull("errors")) {
                                        JSONObject errorsObject = responseObject.getJSONObject("errors");

                                        Iterator<String> keys = (errorsObject.keys());
                                        StringBuilder allErrors = new StringBuilder();

                                        while (keys.hasNext()) {
                                            String key = keys.next();

                                            for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                                String error = errorsObject.getJSONArray(key).getString(i);

                                                allErrors.append(error);
                                                allErrors.append("\n");
                                            }
                                        }

                                        SnackManager.showSnackError(requireActivity(), allErrors.substring(0, allErrors.length() - 1));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    }
                });
                break;
            case "openSample":
                DialogManager.showDialogLoading(requireActivity(), "");

                Sample.open(data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                DialogManager.dismissDialogLoading();
                                SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackSampleOpened));

                                setStatus("open");
                            });
                        }
                    }

                    @Override
                    public void onFailure(String response) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                try {
                                    JSONObject responseObject = new JSONObject(response);
                                    if (!responseObject.isNull("errors")) {
                                        JSONObject errorsObject = responseObject.getJSONObject("errors");

                                        Iterator<String> keys = (errorsObject.keys());
                                        StringBuilder allErrors = new StringBuilder();

                                        while (keys.hasNext()) {
                                            String key = keys.next();

                                            for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                                String error = errorsObject.getJSONArray(key).getString(i);

                                                allErrors.append(error);
                                                allErrors.append("\n");
                                            }
                                        }

                                        SnackManager.showSnackError(requireActivity(), allErrors.substring(0, allErrors.length() - 1));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    }
                });
                break;
            case "scoreSample":
                DialogManager.showDialogLoading(requireActivity(), "");

                Sample.score(data, header, new Response() {
                    @Override
                    public void onOK(Object object) {
                        sampleModel = (SampleModel) object;

                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                DialogManager.dismissDialogLoading();

                                setStatus(sampleModel.getStatus());
                            });
                        }
                    }

                    @Override
                    public void onFailure(String response) {
                        if (isAdded()) {
                            requireActivity().runOnUiThread(() -> {
                                try {
                                    JSONObject responseObject = new JSONObject(response);
                                    if (!responseObject.isNull("errors")) {
                                        JSONObject errorsObject = responseObject.getJSONObject("errors");

                                        Iterator<String> keys = (errorsObject.keys());
                                        StringBuilder allErrors = new StringBuilder();

                                        while (keys.hasNext()) {
                                            String key = keys.next();

                                            for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                                String error = errorsObject.getJSONArray(key).getString(i);

                                                allErrors.append(error);
                                                allErrors.append("\n");
                                            }
                                        }

                                        SnackManager.showSnackError(requireActivity(), allErrors.substring(0, allErrors.length() - 1));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    }
                });
                break;
        }
    }

    private void hideShimmer() {

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

    }

    public void sendGen(String key, String value) {
        HashMap newData = new HashMap<>();

        newData.put("id", sampleAnswers.id);
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
        sampleAnswers.sendPrerequisites(((ActivityMain) requireActivity()).singleton.getToken(), new Response() {
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
        sampleAnswers.add(key, value);
        sampleAnswers.sendRequest(((ActivityMain) requireActivity()).singleton.getToken(), new Response() {
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