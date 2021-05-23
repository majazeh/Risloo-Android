package com.majazeh.risloo.Views.Fragments.Index;

import android.annotation.SuppressLint;
import android.os.Build;
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
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.CentersAdapter;
import com.majazeh.risloo.databinding.FragmentCentersBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.List;

import java.util.HashMap;

public class CentersFragment extends Fragment {

    // Binding
    private FragmentCentersBinding binding;

    // Adapters
    private CentersAdapter adapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager layoutManager;
    private Handler handler;

    // Vars
    private HashMap data, header;
    private boolean loading = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCentersBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new CentersAdapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));

        layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        handler = new Handler();

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.CentersFragmentTitle));

        InitManager.imgResTint(requireActivity(), binding.addImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);
        InitManager.recyclerView(binding.indexSingleLayout.recyclerView, itemDecoration, layoutManager);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.addImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            binding.addImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_green700);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.searchIncludeLayout.editText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.searchIncludeLayout.editText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.searchIncludeLayout.editText);
                }
            }
            return false;
        });

        binding.searchIncludeLayout.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(() -> {
                    binding.searchIncludeLayout.progressBar.setVisibility(View.VISIBLE);
                    data.put("page", 1);
                    data.put("q", String.valueOf(s));
                    setData();
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.getRoot().setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > 0) {
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                if (!loading) {
                    if (pastVisiblesItems + visibleItemCount >= totalItemCount) {
                        binding.indexSingleLayout.progressBar.setVisibility(View.VISIBLE);
                        if (data.containsKey("page")) {
                            int page = (int) data.get("page");
                            page++;

                            data.put("page", page);
                        } else {
                            data.put("page", 1);
                        }
                        setData();
                    }
                }
            }
        });

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createCenterFragment)).widget(binding.addImageView.getRoot());
    }

    private void setData() {
        if (!((MainActivity) requireActivity()).singleton.getType().equals("admin")) {
            binding.addImageView.getRoot().setVisibility(View.GONE);
        }

        loading = true;

        if (data.containsKey("page")) {
            if (data.get("page").equals(1)) {
                adapter.clearCenters();
            }
        }

        Center.list(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                List centers = (List) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        if (!centers.data().isEmpty()) {
                            adapter.setCenters(centers.data());

                            binding.indexSingleLayout.recyclerView.setAdapter(adapter);
                            binding.headerIncludeLayout.countTextView.setText("(" + adapter.getItemCount() + ")");

                            binding.indexSingleLayout.textView.setVisibility(View.GONE);
                        } else {
                            binding.indexSingleLayout.textView.setVisibility(View.VISIBLE);
                        }
                        binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.indexShimmerLayout.getRoot().stopShimmer();

                        if (binding.indexSingleLayout.progressBar.getVisibility() == View.VISIBLE) {
                            binding.indexSingleLayout.progressBar.setVisibility(View.GONE);
                        }
                        if (binding.searchIncludeLayout.progressBar.getVisibility() == View.VISIBLE) {
                            binding.searchIncludeLayout.progressBar.setVisibility(View.GONE);
                        }
                    });
                    loading = false;
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.indexShimmerLayout.getRoot().stopShimmer();

                        if (binding.indexSingleLayout.progressBar.getVisibility() == View.VISIBLE) {
                            binding.indexSingleLayout.progressBar.setVisibility(View.GONE);
                        }
                        if (binding.searchIncludeLayout.progressBar.getVisibility() == View.VISIBLE) {
                            binding.searchIncludeLayout.progressBar.setVisibility(View.GONE);
                        }
                    });
                    loading = false;
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        handler.removeCallbacksAndMessages(null);
    }

}