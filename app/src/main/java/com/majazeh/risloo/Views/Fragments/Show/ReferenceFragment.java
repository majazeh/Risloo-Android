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
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Index.IndexCaseAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Index.IndexSampleAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.RoomsAdapter;
import com.majazeh.risloo.databinding.FragmentReferenceBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ReferenceFragment extends Fragment {

    // Binding
    private FragmentReferenceBinding binding;

    // Adapters
    private RoomsAdapter roomsAdapter;
    private IndexCaseAdapter indexCaseAdapter;
    private IndexSampleAdapter indexSampleAdapter;

    // Models
    private UserModel userModel;

    // Objects
    private HashMap data, header;

    // Vars
    private String type = "";

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
        indexCaseAdapter = new IndexCaseAdapter(requireActivity());
        indexSampleAdapter = new IndexSampleAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        InitManager.imgResTint(requireActivity(), binding.editImageView.getRoot(), R.drawable.ic_edit_light, R.color.Gray500);

        binding.roomsHeaderLayout.titleTextView.setText(getResources().getString(R.string.RoomsAdapterHeader));
        binding.casesHeaderLayout.titleTextView.setText(getResources().getString(R.string.CasesFragmentTitle));
        binding.samplesHeaderLayout.titleTextView.setText(getResources().getString(R.string.SamplesFragmentTitle));

        binding.casesShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);
        binding.samplesShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);

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
        CustomClickView.onDelayedListener(() -> {
            if (binding.avatarIncludeLayout.charTextView.getVisibility() == View.GONE)
                IntentManager.display(requireActivity(), binding.nameTextView.getText().toString(), userModel.getAvatar().getMedium().getUrl());
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);

        CustomClickView.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.editImageView.getRoot());
    }

    private void setArgs() {
        TypeModel centerModel = ReferenceFragmentArgs.fromBundle(getArguments()).getCenterModel();

        if (StringManager.substring(centerModel.getClass().getName(), '.').equals("CenterModel")) {
            setData((CenterModel) centerModel);
        } else if (StringManager.substring(centerModel.getClass().getName(), '.').equals("RoomModel"))
            setData((RoomModel) centerModel);

        TypeModel typeModel = ReferenceFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (StringManager.substring(typeModel.getClass().getName(), '.').equals("UserModel")) {
                userModel = (UserModel) typeModel;
                setData(userModel);
            }
        } else {
            userModel = ((MainActivity) requireActivity()).singleton.getUserModel();
            setData(userModel);
        }
    }

    private void setData(CenterModel model) {
        if (model.getCenterId() != null && !model.getCenterId().equals("")) {
            data.put("id", model.getCenterId());
        }

        if (model.getCenterType() != null && !model.getCenterType().equals("")) {
            type = model.getCenterType();
        }
    }

    private void setData(RoomModel model) {
        if (model.getRoomId() != null && !model.getRoomId().equals("")) {
            data.put("id", model.getRoomId());
        }

        if (model.getRoomType() != null && !model.getRoomType().equals("")) {
            type = model.getRoomType();
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
            binding.statusTextView.setText(SelectionManager.getReferencePosition(requireActivity(), "fa", model.getPosition()));
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
                                indexCaseAdapter.setItems(userModel.getCaseList().data());
                                binding.casesSingleLayout.recyclerView.setAdapter(indexCaseAdapter);

                                binding.casesSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (indexCaseAdapter.getItemCount() == 0) {
                                binding.casesSingleLayout.emptyView.setVisibility(View.VISIBLE);

                                binding.casesSingleLayout.emptyView.setText(getResources().getString(R.string.CasesFragmentEmpty));
                            }

                            // Samples Data
                            if (!userModel.getSampleList().data().isEmpty()) {
                                indexSampleAdapter.setItems(userModel.getSampleList().data());
                                binding.samplesSingleLayout.recyclerView.setAdapter(indexSampleAdapter);

                                binding.samplesSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (indexSampleAdapter.getItemCount() == 0) {
                                binding.samplesSingleLayout.emptyView.setVisibility(View.VISIBLE);

                                binding.samplesSingleLayout.emptyView.setText(getResources().getString(R.string.SamplesFragmentEmpty));
                            }

                            binding.roomsHeaderLayout.countTextView.setText(StringManager.bracing(roomsAdapter.getItemCount()));
                            binding.casesHeaderLayout.countTextView.setText(StringManager.bracing(indexCaseAdapter.itemsCount()));
                            binding.samplesHeaderLayout.countTextView.setText(StringManager.bracing(indexSampleAdapter.itemsCount()));

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
                                indexCaseAdapter.setItems(userModel.getCaseList().data());
                                binding.casesSingleLayout.recyclerView.setAdapter(indexCaseAdapter);

                                binding.casesSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (indexCaseAdapter.getItemCount() == 0) {
                                binding.casesSingleLayout.emptyView.setVisibility(View.VISIBLE);

                                binding.casesSingleLayout.emptyView.setText(getResources().getString(R.string.CasesFragmentEmpty));
                            }

                            // Samples Data
                            if (!userModel.getSampleList().data().isEmpty()) {
                                indexSampleAdapter.setItems(userModel.getSampleList().data());
                                binding.samplesSingleLayout.recyclerView.setAdapter(indexSampleAdapter);

                                binding.samplesSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (indexSampleAdapter.getItemCount() == 0) {
                                binding.samplesSingleLayout.emptyView.setVisibility(View.VISIBLE);

                                binding.samplesSingleLayout.emptyView.setText(getResources().getString(R.string.SamplesFragmentEmpty));
                            }

                            binding.roomsHeaderLayout.countTextView.setText(StringManager.bracing(roomsAdapter.getItemCount()));
                            binding.casesHeaderLayout.countTextView.setText(StringManager.bracing(indexCaseAdapter.itemsCount()));
                            binding.samplesHeaderLayout.countTextView.setText(StringManager.bracing(indexSampleAdapter.itemsCount()));

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