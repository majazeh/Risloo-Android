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

    // Models
    private UserModel userModel;

    // Vars
    private HashMap data, header;
    private String type = "", centerId = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentReferenceBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

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

        InitManager.imgResTint(requireActivity(), binding.editImageView.getRoot(), R.drawable.ic_edit_light, R.color.Gray500);

        binding.roomsHeaderLayout.titleTextView.setText(getResources().getString(R.string.RoomsAdapterHeader));
        binding.casesHeaderLayout.titleTextView.setText(getResources().getString(R.string.Cases3AdapterHeader));
        binding.samplesHeaderLayout.titleTextView.setText(getResources().getString(R.string.Samples3AdapterHeader));

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.roomsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.casesSingleLayout.recyclerView, 0, 0, 0, 0);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.samplesSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.editImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_gray500_ripple_gray300);
        } else {
            binding.editImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_gray500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            if (binding.avatarIncludeLayout.charTextView.getVisibility() == View.GONE)
                IntentManager.display(requireActivity(), binding.nameTextView.getText().toString(), userModel.getAvatar().getMedium().getUrl());
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);

        ClickManager.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.editImageView.getRoot());
    }

    private void setArgs() {
        type = ReferenceFragmentArgs.fromBundle(getArguments()).getType();

        if (ReferenceFragmentArgs.fromBundle(getArguments()).getCenterId() != null) {
            centerId = ReferenceFragmentArgs.fromBundle(getArguments()).getCenterId();
            data.put("id", centerId);

            userModel = (UserModel) ReferenceFragmentArgs.fromBundle(getArguments()).getTypeModel();
            setData(userModel);
        } else {
            CenterModel centerModel = (CenterModel) ReferenceFragmentArgs.fromBundle(getArguments()).getTypeModel();

            if (centerModel.getCenterId() != null && !centerModel.getCenterId().equals("")) {
                data.put("id", centerModel.getCenterId());
            }

            userModel = ((MainActivity) requireActivity()).singleton.getUserModel();
            setData(centerModel);
        }
    }

    private void setData(UserModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("userId", model.getId());
        }

        if (model.getName() != null && !model.getName().equals("")) {
            binding.nameTextView.setText(model.getName());
        } else {
            binding.nameTextView.setText(getResources().getString(R.string.AppDefaultName));
        }

        if (model.getMobile() != null && !model.getMobile().equals("")) {
            binding.mobileTextView.setText(model.getMobile());
            binding.mobileGroup.setVisibility(View.VISIBLE);
        } else {
            binding.mobileGroup.setVisibility(View.GONE);
        }

        if (model.getAvatar() != null && model.getAvatar().getMedium() != null && model.getAvatar().getMedium().getUrl() != null) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(model.getAvatar().getMedium().getUrl()).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }

        if (model.getPosition() != null && !model.getPosition().equals("")) {
            binding.statusTextView.setText(SelectionManager.getPosition2(requireActivity(), "fa", model.getPosition()));
            binding.statusTextView.setVisibility(View.VISIBLE);
        } else {
            binding.statusTextView.setVisibility(View.GONE);
        }
    }

    private void setData(CenterModel model) {
        if (model.getAcceptation() != null && model.getAcceptation().getId() != null && !model.getAcceptation().getId().equals("")) {
            data.put("userId", model.getAcceptation().getId());
        }

        if (model.getAcceptation() != null && model.getAcceptation().getName() != null && !model.getAcceptation().getName().equals("")) {
            binding.nameTextView.setText(model.getAcceptation().getName());
        } else {
            binding.nameTextView.setText(getResources().getString(R.string.AppDefaultName));
        }

        if (userModel.getMobile() != null && !userModel.getMobile().equals("")) {
            binding.mobileTextView.setText(userModel.getMobile());
            binding.mobileGroup.setVisibility(View.VISIBLE);
        } else {
            binding.mobileGroup.setVisibility(View.GONE);
        }

        if (userModel.getAvatar() != null && userModel.getAvatar().getMedium() != null && userModel.getAvatar().getMedium().getUrl() != null) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(userModel.getAvatar().getMedium().getUrl()).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }

        if (model.getAcceptation() != null && model.getAcceptation().getPosition() != null && !model.getAcceptation().getPosition().equals("")) {
            binding.statusTextView.setText(SelectionManager.getPosition2(requireActivity(), "fa", model.getAcceptation().getPosition()));
            binding.statusTextView.setVisibility(View.VISIBLE);
        } else {
            binding.statusTextView.setVisibility(View.GONE);
        }
    }

    private void getData() {
        if (!type.equals("room")) {
            Center.user(data, header, new Response() {
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
                                binding.roomsSingleLayout.emptyView.setText(getResources().getString(R.string.RoomsAdapterEmpty));
                            }

                            // Cases Data
                            if (!userModel.getCaseList().data().isEmpty()) {
                                cases3Adapter.setItems(userModel.getCaseList().data());
                                binding.casesSingleLayout.recyclerView.setAdapter(cases3Adapter);

                                binding.casesSingleLayout.headerView.getRoot().setVisibility(View.VISIBLE);
                                binding.casesSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (cases3Adapter.getItemCount() == 0) {
                                binding.casesSingleLayout.headerView.getRoot().setVisibility(View.GONE);
                                binding.casesSingleLayout.emptyView.setVisibility(View.VISIBLE);

                                binding.casesSingleLayout.emptyView.setText(getResources().getString(R.string.Cases3AdapterEmpty));
                            }

                            // Samples Data
                            if (!userModel.getSampleList().data().isEmpty()) {
                                samples3Adapter.setItems(userModel.getSampleList().data());
                                binding.samplesSingleLayout.recyclerView.setAdapter(samples3Adapter);

                                binding.samplesSingleLayout.headerView.getRoot().setVisibility(View.VISIBLE);
                                binding.samplesSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (samples3Adapter.getItemCount() == 0) {
                                binding.samplesSingleLayout.headerView.getRoot().setVisibility(View.GONE);
                                binding.samplesSingleLayout.emptyView.setVisibility(View.VISIBLE);

                                binding.samplesSingleLayout.emptyView.setText(getResources().getString(R.string.Samples3AdapterEmpty));
                            }

                            binding.roomsHeaderLayout.countTextView.setText(StringManager.bracing(roomsAdapter.getItemCount()));
                            binding.casesHeaderLayout.countTextView.setText(StringManager.bracing(cases3Adapter.getItemCount()));
                            binding.samplesHeaderLayout.countTextView.setText(StringManager.bracing(samples3Adapter.getItemCount()));

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
                                binding.roomsSingleLayout.emptyView.setText(getResources().getString(R.string.RoomsAdapterEmpty));
                            }

                            // Cases Data
                            if (!userModel.getCaseList().data().isEmpty()) {
                                cases3Adapter.setItems(userModel.getCaseList().data());
                                binding.casesSingleLayout.recyclerView.setAdapter(cases3Adapter);

                                binding.casesSingleLayout.headerView.getRoot().setVisibility(View.VISIBLE);
                                binding.casesSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (cases3Adapter.getItemCount() == 0) {
                                binding.casesSingleLayout.headerView.getRoot().setVisibility(View.GONE);
                                binding.casesSingleLayout.emptyView.setVisibility(View.VISIBLE);

                                binding.casesSingleLayout.emptyView.setText(getResources().getString(R.string.Cases3AdapterEmpty));
                            }

                            // Samples Data
                            if (!userModel.getSampleList().data().isEmpty()) {
                                samples3Adapter.setItems(userModel.getSampleList().data());
                                binding.samplesSingleLayout.recyclerView.setAdapter(samples3Adapter);

                                binding.samplesSingleLayout.headerView.getRoot().setVisibility(View.VISIBLE);
                                binding.samplesSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (samples3Adapter.getItemCount() == 0) {
                                binding.samplesSingleLayout.headerView.getRoot().setVisibility(View.GONE);
                                binding.samplesSingleLayout.emptyView.setVisibility(View.VISIBLE);

                                binding.samplesSingleLayout.emptyView.setText(getResources().getString(R.string.Samples3AdapterEmpty));
                            }

                            binding.roomsHeaderLayout.countTextView.setText(StringManager.bracing(roomsAdapter.getItemCount()));
                            binding.casesHeaderLayout.countTextView.setText(StringManager.bracing(cases3Adapter.getItemCount()));
                            binding.samplesHeaderLayout.countTextView.setText(StringManager.bracing(samples3Adapter.getItemCount()));

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
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}