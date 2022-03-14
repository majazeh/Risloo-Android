package com.majazeh.risloo.views.fragments.main.show;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.IntentManager;
import com.majazeh.risloo.utils.managers.JsonManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.interfaces.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.main.Index.IndexRoomAdapter;
import com.majazeh.risloo.views.adapters.recycler.main.Table.TableCaseAdapter;
import com.majazeh.risloo.views.adapters.recycler.main.Table.TableSampleAdapter;
import com.majazeh.risloo.databinding.FragmentReferenceBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.AcceptationModel;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class FragmentReference extends Fragment {

    // Binding
    private FragmentReferenceBinding binding;

    // Adapters
    private IndexRoomAdapter indexRoomAdapter;
    private TableCaseAdapter tableCaseAdapter;
    private TableSampleAdapter tableSampleAdapter;

    // Models
    private UserModel userModel;
    private CenterModel centerModel;
    private RoomModel roomModel;

    // Objects
    private HashMap data, header;

    // Vars
    private String type = "";

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
        indexRoomAdapter = new IndexRoomAdapter(requireActivity());
        tableCaseAdapter = new TableCaseAdapter(requireActivity());
        tableSampleAdapter = new TableSampleAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        InitManager.imgResTintBackground(requireActivity(), binding.editImageView.getRoot(), R.drawable.ic_edit_light, R.color.coolGray500, R.drawable.draw_oval_solid_white_border_1sdp_coolgray300_ripple_coolgray300);

        binding.roomsHeaderLayout.titleTextView.setText(getResources().getString(R.string.RoomAdapterHeader));
        binding.casesHeaderLayout.titleTextView.setText(getResources().getString(R.string.CasesFragmentTitle));
        binding.samplesHeaderLayout.titleTextView.setText(getResources().getString(R.string.SamplesFragmentTitle));

        binding.casesShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);
        binding.samplesShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);

        InitManager.rcvVerticalFixedUnnested(requireActivity(), binding.roomsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
        InitManager.rcvVerticalFixedUnnested(requireActivity(), binding.casesSingleLayout.recyclerView, 0, 0, 0, 0);
        InitManager.rcvVerticalFixedUnnested(requireActivity(), binding.samplesSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            if (binding.avatarIncludeLayout.charTextView.getVisibility() == View.GONE)
                IntentManager.display(requireActivity(), binding.nameTextView.getText().toString(), userModel.getAvatar().getMedium().getUrl());

        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);

        CustomClickView.onClickListener(() -> {
            if (!type.equals("room"))
                ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentEditCenterUser(centerModel, userModel);
            else
                ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentEditCenterUser(roomModel, userModel);

        }).widget(binding.editImageView.getRoot());

        CustomClickView.onDelayedListener(() -> {
            IntentManager.tel(requireActivity(), binding.mobileTextView.getText().toString());
        }).widget(binding.mobileTextView);

        CustomClickView.onDelayedListener(() -> {
            IntentManager.email(requireActivity(), new String[]{binding.emailTextView.getText().toString()}, "", "");
        }).widget(binding.emailTextView);
    }

    private void setArgs() {
        TypeModel centerModel = FragmentReferenceArgs.fromBundle(getArguments()).getCenterModel();

        if (StringManager.suffix(centerModel.getClass().getName(), '.').equals("CenterModel")) {
            this.centerModel = (CenterModel) centerModel;
            setData(this.centerModel);
        } else if (StringManager.suffix(centerModel.getClass().getName(), '.').equals("RoomModel")) {
            this.roomModel = (RoomModel) centerModel;
            setData(this.roomModel);
        }

        userModel = (UserModel) FragmentReferenceArgs.fromBundle(getArguments()).getTypeModel();

        if (((ActivityMain) requireActivity()).singleton.getUserModel().getId().equals(userModel.getId())) {
            if (!type.equals("room"))
                setData(this.centerModel.getAcceptation());
            else
                setData(this.roomModel.getAcceptation());
        } else {
            setData(userModel);
        }
    }

    private void setData(CenterModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }

        if (model.getType() != null && !model.getType().equals("")) {
            type = model.getType();
        }
    }

    private void setData(RoomModel model) {
        if (model.getCenter() != null && model.getCenter().getId() != null && !model.getCenter().getId().equals("")) {
            data.put("id", model.getCenter().getId());
        }

        if (model.getType() != null && !model.getType().equals("")) {
            type = model.getType();
        }
    }

    private void setData(UserModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("userId", model.getId());
        }

        if (model.getName() != null && !model.getName().equals("")) {
            binding.nameTextView.setText(model.getName());
        } else if (model.getId() != null && !model.getId().equals("")) {
            binding.nameTextView.setText(model.getId());
        } else {
            binding.nameTextView.setText(getResources().getString(R.string.AppDefaultUnknown));
        }

        if (model.getMobile() != null && !model.getMobile().equals("")) {
            binding.mobileTextView.setText(model.getMobile());
            binding.mobileGroup.setVisibility(View.VISIBLE);
        } else {
            binding.mobileGroup.setVisibility(View.GONE);
        }

        if (model.getEmail() != null && !model.getEmail().equals("")) {
            binding.emailTextView.setText(model.getEmail());
            binding.emailGroup.setVisibility(View.VISIBLE);
        } else {
            binding.emailGroup.setVisibility(View.GONE);
        }

        if (model.getAvatar() != null && model.getAvatar().getMedium() != null && model.getAvatar().getMedium().getUrl() != null && !model.getAvatar().getMedium().getUrl().equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(model.getAvatar().getMedium().getUrl()).placeholder(R.color.coolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.avatarIncludeLayout.charTextView.setText(StringManager.charsFirst(binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.coolGray100).placeholder(R.color.coolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }

        if (model.getPosition() != null && !model.getPosition().equals("")) {
            binding.statusTextView.setText(JsonManager.getReferencePosition(requireActivity(), "fa", model.getPosition()));
            binding.statusTextView.setVisibility(View.VISIBLE);
        } else {
            binding.statusTextView.setVisibility(View.GONE);
        }
    }

    private void setData(AcceptationModel model) {
        if (model != null && model.getId() != null && !model.getId().equals("")) {
            data.put("userId", model.getId());
        }

        if (model != null && model.getName() != null && !model.getName().equals("")) {
            binding.nameTextView.setText(model.getName());
        } else if (model != null && model.getId() != null && !model.getId().equals("")) {
            binding.nameTextView.setText(model.getId());
        } else {
            binding.nameTextView.setText(getResources().getString(R.string.AppDefaultUnknown));
        }

        if (userModel.getMobile() != null && !userModel.getMobile().equals("")) {
            binding.mobileTextView.setText(userModel.getMobile());
            binding.mobileGroup.setVisibility(View.VISIBLE);
        } else {
            binding.mobileGroup.setVisibility(View.GONE);
        }

        if (userModel.getEmail() != null && !userModel.getEmail().equals("")) {
            binding.emailTextView.setText(userModel.getEmail());
            binding.emailGroup.setVisibility(View.VISIBLE);
        } else {
            binding.emailGroup.setVisibility(View.GONE);
        }

        if (userModel.getAvatar() != null && userModel.getAvatar().getMedium() != null && userModel.getAvatar().getMedium().getUrl() != null && !userModel.getAvatar().getMedium().getUrl().equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(userModel.getAvatar().getMedium().getUrl()).placeholder(R.color.coolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.avatarIncludeLayout.charTextView.setText(StringManager.charsFirst(binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.coolGray100).placeholder(R.color.coolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }

        if (model != null && model.getPosition() != null && !model.getPosition().equals("")) {
            binding.statusTextView.setText(JsonManager.getReferencePosition(requireActivity(), "fa", model.getPosition()));
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
                            if (!userModel.getRooms().data().isEmpty()) {
                                indexRoomAdapter.setItems(userModel.getRooms().data());
                                binding.roomsSingleLayout.recyclerView.setAdapter(indexRoomAdapter);

                                binding.roomsSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (indexRoomAdapter.getItemCount() == 0) {
                                binding.roomsSingleLayout.recyclerView.setAdapter(null);

                                binding.roomsSingleLayout.emptyView.setVisibility(View.VISIBLE);
                                binding.roomsSingleLayout.emptyView.setText(getResources().getString(R.string.RoomAdapterEmpty));
                            }

                            // Cases Data
                            if (!userModel.getCases().data().isEmpty()) {
                                tableCaseAdapter.setItems(userModel.getCases().data());
                                binding.casesSingleLayout.recyclerView.setAdapter(tableCaseAdapter);

                                binding.casesSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (tableCaseAdapter.getItemCount() == 0) {
                                binding.casesSingleLayout.recyclerView.setAdapter(null);

                                binding.casesSingleLayout.emptyView.setVisibility(View.VISIBLE);
                                binding.casesSingleLayout.emptyView.setText(getResources().getString(R.string.CasesFragmentEmpty));
                            }

                            // Samples Data
                            if (!userModel.getSamples().data().isEmpty()) {
                                tableSampleAdapter.setItems(userModel.getSamples().data());
                                binding.samplesSingleLayout.recyclerView.setAdapter(tableSampleAdapter);

                                binding.samplesSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (tableSampleAdapter.getItemCount() == 0) {
                                binding.samplesSingleLayout.recyclerView.setAdapter(null);

                                binding.samplesSingleLayout.emptyView.setVisibility(View.VISIBLE);
                                binding.samplesSingleLayout.emptyView.setText(getResources().getString(R.string.SamplesFragmentEmpty));
                            }

                            binding.roomsHeaderLayout.countTextView.setText(StringManager.bracing(indexRoomAdapter.getItemCount()));
                            binding.casesHeaderLayout.countTextView.setText(StringManager.bracing(tableCaseAdapter.itemsCount()));
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
        } else {
            Room.user(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    userModel = (UserModel) object;

                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            setData(userModel);

                            // Rooms Data
                            if (!userModel.getRooms().data().isEmpty()) {
                                indexRoomAdapter.setItems(userModel.getRooms().data());
                                binding.roomsSingleLayout.recyclerView.setAdapter(indexRoomAdapter);

                                binding.roomsSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (indexRoomAdapter.getItemCount() == 0) {
                                binding.roomsSingleLayout.recyclerView.setAdapter(null);

                                binding.roomsSingleLayout.emptyView.setVisibility(View.VISIBLE);
                                binding.roomsSingleLayout.emptyView.setText(getResources().getString(R.string.RoomAdapterEmpty));
                            }

                            // Cases Data
                            if (!userModel.getCases().data().isEmpty()) {
                                tableCaseAdapter.setItems(userModel.getCases().data());
                                binding.casesSingleLayout.recyclerView.setAdapter(tableCaseAdapter);

                                binding.casesSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (tableCaseAdapter.getItemCount() == 0) {
                                binding.casesSingleLayout.recyclerView.setAdapter(null);

                                binding.casesSingleLayout.emptyView.setVisibility(View.VISIBLE);
                                binding.casesSingleLayout.emptyView.setText(getResources().getString(R.string.CasesFragmentEmpty));
                            }

                            // Samples Data
                            if (!userModel.getSamples().data().isEmpty()) {
                                tableSampleAdapter.setItems(userModel.getSamples().data());
                                binding.samplesSingleLayout.recyclerView.setAdapter(tableSampleAdapter);

                                binding.samplesSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (tableSampleAdapter.getItemCount() == 0) {
                                binding.samplesSingleLayout.recyclerView.setAdapter(null);

                                binding.samplesSingleLayout.emptyView.setVisibility(View.VISIBLE);
                                binding.samplesSingleLayout.emptyView.setText(getResources().getString(R.string.SamplesFragmentEmpty));
                            }

                            binding.roomsHeaderLayout.countTextView.setText(StringManager.bracing(indexRoomAdapter.getItemCount()));
                            binding.casesHeaderLayout.countTextView.setText(StringManager.bracing(tableCaseAdapter.itemsCount()));
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
    }

    private void hideShimmer() {

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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}