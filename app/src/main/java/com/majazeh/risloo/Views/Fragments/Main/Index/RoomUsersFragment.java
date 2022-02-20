package com.majazeh.risloo.Views.Fragments.Main.Index;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.Views.adapters.recycler.main.Table.TableRoomUserAdapter;
import com.majazeh.risloo.databinding.FragmentRoomUsersBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.RoomModel;

import java.util.HashMap;
import java.util.Objects;

public class RoomUsersFragment extends Fragment {

    // Binding
    private FragmentRoomUsersBinding binding;

    // Adapters
    private TableRoomUserAdapter adapter;

    // Models
    public RoomModel roomModel;

    // Objects
    private Handler handler;
    private HashMap data, header;

    // Vars
    private boolean isLoading = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomUsersBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new TableRoomUserAdapter(requireActivity());

        handler = new Handler();

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.RoomUsersFragmentTitle));

        binding.tableShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);

        InitManager.imgResTintBackground(requireActivity(), binding.addImageView.getRoot(), R.drawable.ic_plus_light, R.color.white, R.drawable.draw_oval_solid_emerald600_ripple_white);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.tableSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.searchIncludeLayout.searchEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.searchIncludeLayout.searchEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(binding.searchIncludeLayout.searchEditText);
            return false;
        });

        binding.searchIncludeLayout.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(() -> {
                    data.put("page", 1);
                    data.put("q", String.valueOf(s));

                    if (binding.searchIncludeLayout.searchProgressBar.getVisibility() == View.GONE)
                        binding.searchIncludeLayout.searchProgressBar.setVisibility(View.VISIBLE);

                    getData();
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.getRoot().setMOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (!isLoading && !Objects.requireNonNull(v).canScrollVertically(1)) {
                isLoading = true;

                if (data.containsKey("page"))
                    data.put("page", ((int) data.get("page")) + 1);
                else
                    data.put("page", 1);

                if (binding.tableSingleLayout.progressBar.getVisibility() == View.GONE)
                    binding.tableSingleLayout.progressBar.setVisibility(View.VISIBLE);

                getData();
            }
        });

        CustomClickView.onClickListener(() -> {
            ((MainActivity) requireActivity()).navigatoon.navigateToCreateRoomUserFragment(roomModel);
        }).widget(binding.addImageView.getRoot());
    }

    private void setArgs() {
        roomModel = (RoomModel) RoomUsersFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(roomModel);
    }

    private void setData(RoomModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }
    }

    private void getData() {
        Room.users(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                List items = (List) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        if (Objects.equals(data.get("page"), 1))
                            adapter.clearItems();

                        if (!items.data().isEmpty()) {
                            adapter.setItems(items.data());
                            binding.tableSingleLayout.recyclerView.setAdapter(adapter);

                            binding.tableSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (adapter.itemsCount() == 0) {
                            binding.tableSingleLayout.recyclerView.setAdapter(null);

                            binding.tableSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            if (binding.searchIncludeLayout.searchProgressBar.getVisibility() == View.VISIBLE)
                                binding.tableSingleLayout.emptyView.setText(getResources().getString(R.string.AppSearchEmpty));
                            else
                                binding.tableSingleLayout.emptyView.setText(getResources().getString(R.string.RoomUsersFragmentEmpty));
                        }

                        binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(items.getTotal()));

                        hideShimmer();
                    });

                    isLoading = false;
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        hideShimmer();
                    });

                    isLoading = false;
                }
            }
        });
    }

    private void hideShimmer() {
        binding.tableSingleLayout.getRoot().setVisibility(View.VISIBLE);
        binding.tableShimmerLayout.getRoot().setVisibility(View.GONE);
        binding.tableShimmerLayout.getRoot().stopShimmer();

        if (binding.tableSingleLayout.progressBar.getVisibility() == View.VISIBLE)
            binding.tableSingleLayout.progressBar.setVisibility(View.GONE);
        if (binding.searchIncludeLayout.searchProgressBar.getVisibility() == View.VISIBLE)
            binding.searchIncludeLayout.searchProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        isLoading = true;
        handler.removeCallbacksAndMessages(null);
    }

}