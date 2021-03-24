package com.majazeh.risloo.Views.Fragments.Index;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SamplesAdapter;
import com.majazeh.risloo.databinding.FragmentSamplesBinding;

public class SamplesFragment extends Fragment {

    // Binding
    private FragmentSamplesBinding binding;

    // Adapters
    private SamplesAdapter adapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentSamplesBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        ((MainActivity) requireActivity()).handler.postDelayed(() -> {
            binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.indexHeaderLayout.getRoot().setVisibility(View.VISIBLE);
            binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
        }, 2000);

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new SamplesAdapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", 0, 0, 0, 0);

        layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.SamplesFragmentTitle));

        binding.indexShimmerLayout.shimmerItem1.topView.setVisibility(View.GONE);

        InitManager.imgResTint(requireActivity(), binding.addImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);
        InitManager.recyclerView(binding.indexSingleLayout.recyclerView, itemDecoration, layoutManager);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.addImageView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            binding.addImageView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
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
                ((MainActivity) requireActivity()).handler.removeCallbacksAndMessages(null);
                ((MainActivity) requireActivity()).handler.postDelayed(() -> {
                    // TODO : Place Code Here
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.addImageView.getRoot().setOnClickListener(v -> {
            binding.addImageView.getRoot().setClickable(false);

            ((MainActivity) requireActivity()).navigator(R.id.createSampleFragment);
        });
    }

    private void setData() {
//        adapter.setSample(null);
        binding.indexSingleLayout.recyclerView.setAdapter(adapter);

        String dataSize = "15";
        binding.headerIncludeLayout.countTextView.setText("(" + dataSize + ")");
    }

}