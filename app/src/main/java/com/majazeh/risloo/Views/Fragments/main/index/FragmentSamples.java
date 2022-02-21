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
import com.majazeh.risloo.views.adapters.recycler.main.Table.TableSampleAdapter;
import com.majazeh.risloo.databinding.FragmentSamplesBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.HashMap;
import java.util.Objects;

public class FragmentSamples extends Fragment {

    // Binding
    private FragmentSamplesBinding binding;

    // Adapters
    private TableSampleAdapter adapter;

    // Objects
    private Handler handler;
    private HashMap data, header;

    // Vars
    private boolean isLoading = true, isScrollable = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentSamplesBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        setPermission();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new TableSampleAdapter(requireActivity());

        handler = new Handler();

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.SamplesFragmentTitle));

        binding.tableShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);

        InitManager.imgResTintBackground(requireActivity(), binding.addImageView.getRoot(), R.drawable.ic_plus_light, R.color.white, R.drawable.draw_oval_solid_emerald600_ripple_white);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.tableSingleLayout.recyclerView, 0, 0, 0, 0);
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
                    if (isScrollable)
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
            if (!isLoading && isScrollable && !Objects.requireNonNull(v).canScrollVertically(1)) {
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
            ((ActivityMain) requireActivity()).navigatoon.navigateToCreateSampleFragment(null);
        }).widget(binding.addImageView.getRoot());
    }

    private void setArgs() {
        String chainId = FragmentSamplesArgs.fromBundle(getArguments()).getChainId();
        String[] sampleIds = FragmentSamplesArgs.fromBundle(getArguments()).getSampleIds();

        if (chainId != null) {
            data.put("chain", chainId);
        }

        if (sampleIds != null) {
            StringBuilder ids = new StringBuilder();
            for (String id : sampleIds) {
                ids.append(id + ",");
            }

            data.put("ids", ids);
        }

        if (chainId != null || sampleIds != null) {
            data.remove("page");
            isScrollable = false;
        }
    }

    private void setPermission() {
        UserModel model = ((ActivityMain) requireActivity()).singleton.getUserModel();

        if (((ActivityMain) requireActivity()).permissoon.showSamplesCreateSample(model))
            binding.addImageView.getRoot().setVisibility(View.VISIBLE);
        else
            binding.addImageView.getRoot().setVisibility(View.GONE);
    }

    private void getData() {
        Sample.sampleList(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                List items = (List) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        if (Objects.equals(data.get("page"), 1) || !isScrollable)
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
                                binding.tableSingleLayout.emptyView.setText(getResources().getString(R.string.SamplesFragmentEmpty));
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
        isScrollable = true;
        handler.removeCallbacksAndMessages(null);
    }

}