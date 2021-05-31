package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Cases3Adapter;
import com.majazeh.risloo.Views.Adapters.Recycler.RoomsAdapter;
import com.majazeh.risloo.Views.Adapters.Recycler.Samples3Adapter;
import com.majazeh.risloo.databinding.FragmentReferenceBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.Room;
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

    // Objects
    private RecyclerView.ItemDecoration itemDecoration, itemDecoration2;
    private LinearLayoutManager roomsLayoutManager, cases3LayoutManager, samples3LayoutManager;
    private Bundle extras;

    // Vars
    private HashMap data, header;
    private String type = "personal_clinic";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentReferenceBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setExtra();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        roomsAdapter = new RoomsAdapter(requireActivity());
        cases3Adapter = new Cases3Adapter(requireActivity());
        samples3Adapter = new Samples3Adapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));
        itemDecoration2 = new ItemDecorateRecyclerView("verticalLayout", 0, 0, 0, 0);

        roomsLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        cases3LayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        samples3LayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        extras = new Bundle();

        data = new HashMap<>();
        data.put("id", "");
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.roomsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.RoomsAdapterHeader));
        binding.casesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Cases3AdapterHeader));
        binding.samplesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Samples3AdapterHeader));

        InitManager.recyclerView(binding.roomsSingleLayout.recyclerView, itemDecoration, roomsLayoutManager);
        InitManager.recyclerView(binding.casesSingleLayout.recyclerView, itemDecoration2, cases3LayoutManager);
        InitManager.recyclerView(binding.samplesSingleLayout.recyclerView, itemDecoration2, samples3LayoutManager);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
                IntentManager.display(requireActivity(), "", "", ((MainActivity) requireActivity()).singleton.getAvatar());
            }
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);
    }

    private void setExtra() {
        if (getArguments() != null) {
            if (getArguments().getString("id") != null && !getArguments().getString("id").equals("")) {
                extras.putString("id", getArguments().getString("id"));
                data.put("id", getArguments().getString("id"));
            }

            if (getArguments().getString("type") != null && !getArguments().getString("type").equals("")) {
                type = getArguments().getString("type");
                extras.putString("type", type);

                if (getArguments().getString("type").equals("room")) {
                    if (getArguments().getString("center_id") != null && !getArguments().getString("center_id").equals("")) {
                        data.put("id", getArguments().getString("center_id"));
                    }
                }
            }

            if (getArguments().getString("user_id") != null && !getArguments().getString("user_id").equals("")) {
                extras.putString("user_id", getArguments().getString("user_id"));
                data.put("userId", getArguments().getString("user_id"));
            }

            if (getArguments().getString("position") != null && !getArguments().getString("position").equals("")) {
                extras.putString("position", getArguments().getString("position"));
                binding.statusTextView.setText(SelectionManager.getPosition2(requireActivity(), "fa", getArguments().getString("position")));
                binding.statusTextView.setVisibility(View.VISIBLE);
            } else if (getArguments().getString("status") != null && !getArguments().getString("status").equals("")) {
                extras.putString("status", getArguments().getString("status"));
                binding.statusTextView.setText(SelectionManager.getPosition2(requireActivity(), "fa", getArguments().getString("status")));
                binding.statusTextView.setVisibility(View.VISIBLE);
            } else {
                binding.statusTextView.setVisibility(View.GONE);
            }

            if (getArguments().getString("nickname") != null && !getArguments().getString("nickname").equals("")) {
                extras.putString("nickname", getArguments().getString("nickname"));
                binding.nameTextView.setText(getArguments().getString("nickname"));
                binding.nameTextView.setVisibility(View.VISIBLE);
            } else if (getArguments().getString("user_name") != null && !getArguments().getString("user_name").equals("")) {
                extras.putString("user_name", getArguments().getString("user_name"));
                binding.nameTextView.setText(getArguments().getString("user_name"));
                binding.nameTextView.setVisibility(View.VISIBLE);
            } else {
                binding.nameTextView.setVisibility(View.GONE);
            }

            if (getArguments().getString("user_avatar") != null && !getArguments().getString("user_avatar").equals("")) {
                extras.putString("user_avatar", getArguments().getString("user_avatar"));
                binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(getArguments().getString("user_avatar")).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

                Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
            }

            if (getArguments().getString("mobile") != null && !getArguments().getString("mobile").equals("")) {
                extras.putString("mobile", getArguments().getString("mobile"));
                binding.mobileTextView.setText(getArguments().getString("mobile"));
                binding.mobileGroup.setVisibility(View.VISIBLE);
            } else {
                binding.mobileGroup.setVisibility(View.GONE);
            }
        }
    }

    private void setData(UserModel model) {
        if (!model.getPosition().equals("")) {
            binding.statusTextView.setText(SelectionManager.getPosition2(requireActivity(), "fa", model.getPosition()));
            binding.statusTextView.setVisibility(View.VISIBLE);
        } else {
            binding.statusTextView.setVisibility(View.GONE);
        }

        if (!model.getName().equals("")) {
            binding.nameTextView.setText(model.getName());
            binding.nameTextView.setVisibility(View.VISIBLE);
        } else {
            binding.nameTextView.setVisibility(View.GONE);
        }

        if (model.getAvatar() != null && model.getAvatar().getMedium() != null && !model.getAvatar().getMedium().getUrl().equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(model.getAvatar().getMedium().getUrl()).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }

        if (!model.getMobile().equals("")) {
            binding.mobileTextView.setText(model.getMobile());
            binding.mobileGroup.setVisibility(View.VISIBLE);
        } else {
            binding.mobileGroup.setVisibility(View.GONE);
        }
    }

    private void getData() {
        if (!type.equals("room")) {
            Center.user(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    UserModel model = (UserModel) object;

                    if (isAdded())
                        requireActivity().runOnUiThread(() -> {
                            setData(model);

                            if (!model.getRoomList().data().isEmpty()) {
                                roomsAdapter.setRooms(model.getRoomList().data());
                                binding.roomsSingleLayout.recyclerView.setAdapter(roomsAdapter);
                            }

                            if (!model.getCaseList().data().isEmpty()) {
                                cases3Adapter.setCases(model.getCaseList().data());
                                binding.casesSingleLayout.recyclerView.setAdapter(cases3Adapter);
                            }

                            if (!model.getSampleList().data().isEmpty()) {
                                samples3Adapter.setSamples(model.getSampleList().data());
                                binding.samplesSingleLayout.recyclerView.setAdapter(samples3Adapter);
                            }

                            binding.roomsHeaderIncludeLayout.countTextView.setText("(" + roomsAdapter.getItemCount() + ")");
                            binding.casesHeaderIncludeLayout.countTextView.setText("(" + cases3Adapter.getItemCount() + ")");
                            binding.samplesHeaderIncludeLayout.countTextView.setText("(" + samples3Adapter.getItemCount() + ")");

                            binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.roomsShimmerLayout.getRoot().stopShimmer();

                            binding.casesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.casesShimmerLayout.getRoot().stopShimmer();

                            binding.samplesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.samplesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.samplesShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.samplesShimmerLayout.getRoot().stopShimmer();
                        });
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.roomsShimmerLayout.getRoot().stopShimmer();

                            binding.casesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.casesShimmerLayout.getRoot().stopShimmer();

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
                    UserModel model = (UserModel) object;

                    if (isAdded())
                        requireActivity().runOnUiThread(() -> {
                            setData(model);

                            if (!model.getRoomList().data().isEmpty()) {
                                roomsAdapter.setRooms(model.getRoomList().data());
                                binding.roomsSingleLayout.recyclerView.setAdapter(roomsAdapter);
                            }

                            if (!model.getCaseList().data().isEmpty()) {
                                cases3Adapter.setCases(model.getCaseList().data());
                                binding.casesSingleLayout.recyclerView.setAdapter(cases3Adapter);
                            }

                            if (!model.getSampleList().data().isEmpty()) {
                                samples3Adapter.setSamples(model.getSampleList().data());
                                binding.samplesSingleLayout.recyclerView.setAdapter(samples3Adapter);
                            }

                            binding.roomsHeaderIncludeLayout.countTextView.setText("(" + roomsAdapter.getItemCount() + ")");
                            binding.casesHeaderIncludeLayout.countTextView.setText("(" + cases3Adapter.getItemCount() + ")");
                            binding.samplesHeaderIncludeLayout.countTextView.setText("(" + samples3Adapter.getItemCount() + ")");

                            binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.roomsShimmerLayout.getRoot().stopShimmer();

                            binding.casesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.casesShimmerLayout.getRoot().stopShimmer();

                            binding.samplesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.samplesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.samplesShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.samplesShimmerLayout.getRoot().stopShimmer();
                        });
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.roomsShimmerLayout.getRoot().stopShimmer();

                            binding.casesHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.casesShimmerLayout.getRoot().stopShimmer();

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