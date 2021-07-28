package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.majazeh.risloo.Views.Adapters.Recycler.Cases3Adapter;
import com.majazeh.risloo.Views.Adapters.Recycler.RoomsAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Samples3Adapter;
import com.majazeh.risloo.databinding.FragmentReferenceBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ReferenceFragment extends Fragment {

    // Binding
    private FragmentReferenceBinding binding;

    // Adapters
    private RoomsAdapter roomsAdapter;
    private Cases3Adapter cases3Adapter;
    private Samples3Adapter samples3Adapter;

    // Vars
    private HashMap data, header;
    private UserModel userModel;
    private String type = "", centerId = "", userId = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentReferenceBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        roomsAdapter = new RoomsAdapter(requireActivity());
        cases3Adapter = new Cases3Adapter(requireActivity());
        samples3Adapter = new Samples3Adapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.roomsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.RoomsAdapterHeader));
        binding.casesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Cases3AdapterHeader));
        binding.samplesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Samples3AdapterHeader));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.roomsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.casesSingleLayout.recyclerView, 0, 0, 0, 0);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.samplesSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            if (binding.avatarIncludeLayout.charTextView.getVisibility() == View.GONE)
                IntentManager.display(requireActivity(), binding.nameTextView.getText().toString(), userModel.getAvatar().getMedium().getUrl());
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);
    }

    private void setArgs() {
        type = ReferenceFragmentArgs.fromBundle(getArguments()).getType();

        if (ReferenceFragmentArgs.fromBundle(getArguments()).getCenterId() != null) {
            centerId = ReferenceFragmentArgs.fromBundle(getArguments()).getCenterId();
            data.put("id", centerId);

            userModel = (UserModel) ReferenceFragmentArgs.fromBundle(getArguments()).getTypeModel();

            if (userModel.getId() != null && !userModel.getId().equals("")) {
                userId = userModel.getId();
                data.put("userId", userId);
            }

            setData(userModel);
        }

        if (ReferenceFragmentArgs.fromBundle(getArguments()).getUserId() != null) {
            userId = ReferenceFragmentArgs.fromBundle(getArguments()).getUserId();
            data.put("userId", userId);

            CenterModel centerModel = (CenterModel) ReferenceFragmentArgs.fromBundle(getArguments()).getTypeModel();

            if (centerModel.getCenterId() != null && !centerModel.getCenterId().equals("")) {
                centerId = centerModel.getCenterId();
                data.put("id", centerId);
            }

            setData(((MainActivity) requireActivity()).singleton.getUserModel());
        }
    }

    private void setData(UserModel model) {
        if (model.getPosition() != null && !model.getPosition().equals("")) {
            binding.statusTextView.setText(SelectionManager.getPosition2(requireActivity(), "fa", model.getPosition()));
            binding.statusTextView.setVisibility(View.VISIBLE);
        } else {
            binding.statusTextView.setVisibility(View.GONE);
        }

        if (model.getName() != null && !model.getName().equals("")) {
            binding.nameTextView.setText(model.getName());
            binding.nameTextView.setVisibility(View.VISIBLE);
        } else {
            binding.nameTextView.setVisibility(View.GONE);
        }

        if (model.getMobile() != null && !model.getMobile().equals("")) {
            binding.mobileTextView.setText(model.getMobile());
            binding.mobileGroup.setVisibility(View.VISIBLE);
        } else {
            binding.mobileGroup.setVisibility(View.GONE);
        }

        if (model.getAvatar() != null && model.getAvatar().getMedium() != null && model.getAvatar().getMedium().getUrl() != null && !model.getAvatar().getMedium().getUrl().equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(model.getAvatar().getMedium().getUrl()).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }
    }

    private void getData() {
        if (!type.equals("room")) {
            Center.user(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    userModel = (UserModel) object;

                    if (isAdded())
                        requireActivity().runOnUiThread(() -> {
                            setData(userModel);

                            // Rooms Data
                            if (!userModel.getRoomList().data().isEmpty()) {
                                roomsAdapter.setItems(userModel.getRoomList().data());
                                binding.roomsSingleLayout.recyclerView.setAdapter(roomsAdapter);

                                binding.roomsSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (roomsAdapter.getItemCount() == 0) {
                                binding.roomsSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            }

                            // Cases Data
                            if (!userModel.getCaseList().data().isEmpty()) {
                                cases3Adapter.setCases(userModel.getCaseList().data());
                                binding.casesSingleLayout.recyclerView.setAdapter(cases3Adapter);

                                binding.casesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                                binding.casesSingleLayout.textView.setVisibility(View.GONE);
                            } else if (cases3Adapter.getItemCount() == 0) {
                                binding.casesHeaderLayout.getRoot().setVisibility(View.GONE);
                                binding.casesSingleLayout.textView.setVisibility(View.VISIBLE);
                            }

                            // Samples Data
                            if (!userModel.getSampleList().data().isEmpty()) {
                                samples3Adapter.setSamples(userModel.getSampleList().data());
                                binding.samplesSingleLayout.recyclerView.setAdapter(samples3Adapter);

                                binding.samplesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                                binding.samplesSingleLayout.textView.setVisibility(View.GONE);
                            } else if (samples3Adapter.getItemCount() == 0) {
                                binding.samplesHeaderLayout.getRoot().setVisibility(View.GONE);
                                binding.samplesSingleLayout.textView.setVisibility(View.VISIBLE);
                            }

                            binding.roomsHeaderIncludeLayout.countTextView.setText(StringManager.bracing(roomsAdapter.getItemCount()));
                            binding.casesHeaderIncludeLayout.countTextView.setText(StringManager.bracing(cases3Adapter.getItemCount()));
                            binding.samplesHeaderIncludeLayout.countTextView.setText(StringManager.bracing(samples3Adapter.getItemCount()));

                            // Rooms Data
                            binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.roomsShimmerLayout.getRoot().stopShimmer();

                            // Cases Data
                            binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.casesShimmerLayout.getRoot().stopShimmer();

                            // Samples Data
                            binding.samplesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.samplesShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.samplesShimmerLayout.getRoot().stopShimmer();
                        });
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {

                            // Rooms Data
                            binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.roomsShimmerLayout.getRoot().stopShimmer();

                            // Cases Data
                            binding.casesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.casesShimmerLayout.getRoot().stopShimmer();

                            // Samples Data
                            binding.samplesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.samplesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.samplesShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.samplesShimmerLayout.getRoot().stopShimmer();
                        });
                    }
                }
            });
        } else {
            Room.user(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    userModel = (UserModel) object;

                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            setData(userModel);

                            // Rooms Data
                            if (!userModel.getRoomList().data().isEmpty()) {
                                roomsAdapter.setItems(userModel.getRoomList().data());
                                binding.roomsSingleLayout.recyclerView.setAdapter(roomsAdapter);

                                binding.roomsSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (roomsAdapter.getItemCount() == 0) {
                                binding.roomsSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            }

                            // Cases Data
                            if (!userModel.getCaseList().data().isEmpty()) {
                                cases3Adapter.setCases(userModel.getCaseList().data());
                                binding.casesSingleLayout.recyclerView.setAdapter(cases3Adapter);

                                binding.casesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                                binding.casesSingleLayout.textView.setVisibility(View.GONE);
                            } else if (cases3Adapter.getItemCount() == 0) {
                                binding.casesHeaderLayout.getRoot().setVisibility(View.GONE);
                                binding.casesSingleLayout.textView.setVisibility(View.VISIBLE);
                            }

                            // Samples Data
                            if (!userModel.getSampleList().data().isEmpty()) {
                                samples3Adapter.setSamples(userModel.getSampleList().data());
                                binding.samplesSingleLayout.recyclerView.setAdapter(samples3Adapter);

                                binding.samplesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                                binding.samplesSingleLayout.textView.setVisibility(View.GONE);
                            } else if (samples3Adapter.getItemCount() == 0) {
                                binding.samplesHeaderLayout.getRoot().setVisibility(View.GONE);
                                binding.samplesSingleLayout.textView.setVisibility(View.VISIBLE);
                            }

                            binding.roomsHeaderIncludeLayout.countTextView.setText(StringManager.bracing(roomsAdapter.getItemCount()));
                            binding.casesHeaderIncludeLayout.countTextView.setText(StringManager.bracing(cases3Adapter.getItemCount()));
                            binding.samplesHeaderIncludeLayout.countTextView.setText(StringManager.bracing(samples3Adapter.getItemCount()));

                            // Rooms Data
                            binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.roomsShimmerLayout.getRoot().stopShimmer();

                            // Cases Data
                            binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.casesShimmerLayout.getRoot().stopShimmer();

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

                            // Rooms Data
                            binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.roomsShimmerLayout.getRoot().stopShimmer();

                            // Cases Data
                            binding.casesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.casesShimmerLayout.getRoot().stopShimmer();

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}