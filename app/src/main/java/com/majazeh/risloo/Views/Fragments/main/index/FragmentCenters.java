package com.majazeh.risloo.views.fragments.main.index;

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
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.main.Index.IndexCenterAdapter;
import com.majazeh.risloo.databinding.FragmentCentersBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class FragmentCenters extends Fragment {

    // Binding
    private FragmentCentersBinding binding;

    // Adapters
    private IndexCenterAdapter adapter;

    // Objects
    private Handler handler;
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> items = new ArrayList<>();
    private boolean isLoading = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCentersBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setPermission();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new IndexCenterAdapter(requireActivity());

        handler = new Handler();

        data = new HashMap<>();
        data.put("page", 1);
        data.put("session_platforms", 1);
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.CentersFragmentTitle));

        InitManager.imgResTintBackground(requireActivity(), binding.addImageView.getRoot(), R.drawable.ic_plus_light, R.color.white, R.drawable.draw_oval_solid_emerald600_ripple_white);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.indexSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.searchIncludeLayout.searchEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.searchIncludeLayout.searchEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.searchIncludeLayout.searchEditText);
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

                if (binding.indexSingleLayout.progressBar.getVisibility() == View.GONE)
                    binding.indexSingleLayout.progressBar.setVisibility(View.VISIBLE);

                getData();
            }
        });

        CustomClickView.onClickListener(() -> ((ActivityMain) requireActivity()).navigatoon.navigateToCreateCenterFragment(null)).widget(binding.addImageView.getRoot());
    }

    private void setPermission() {
        UserModel model = ((ActivityMain) requireActivity()).singleton.getUserModel();

        if (((ActivityMain) requireActivity()).permissoon.showCentersCreateCenter(model))
            binding.addImageView.getRoot().setVisibility(View.VISIBLE);
        else
            binding.addImageView.getRoot().setVisibility(View.GONE);
    }

    private void getData() {
        Center.list(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                List list = (List) object;

                if (Objects.equals(data.get("page"), 1)) {
                    adapter.clearItems();
                    items = list.data();
                } else {
                    items.addAll(list.data());
                }

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        if (!items.isEmpty()) {
                            adapter.setItems(items);
                            binding.indexSingleLayout.recyclerView.setAdapter(adapter);

                            binding.indexSingleLayout.emptyView.setVisibility(View.GONE);
                        } else if (adapter.getItemCount() == 0) {
                            binding.indexSingleLayout.recyclerView.setAdapter(null);

                            binding.indexSingleLayout.emptyView.setVisibility(View.VISIBLE);
                            if (binding.searchIncludeLayout.searchProgressBar.getVisibility() == View.VISIBLE)
                                binding.indexSingleLayout.emptyView.setText(getResources().getString(R.string.AppSearchEmpty));
                            else
                                binding.indexSingleLayout.emptyView.setText(getResources().getString(R.string.CentersFragmentEmpty));
                        }

                        binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(list.getTotal()));

                        hideShimmer();
                    });

                    isLoading = false;
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> hideShimmer());

                    isLoading = false;
                }
            }
        });
    }

    private void hideShimmer() {
        binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
        binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
        binding.indexShimmerLayout.getRoot().stopShimmer();

        if (binding.indexSingleLayout.progressBar.getVisibility() == View.VISIBLE)
            binding.indexSingleLayout.progressBar.setVisibility(View.GONE);
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