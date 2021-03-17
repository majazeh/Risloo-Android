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
import com.majazeh.risloo.Views.Adapters.Recycler.CentersAdapter;
import com.majazeh.risloo.databinding.FragmentCentersBinding;

public class CentersFragment extends Fragment {

    // Binding
    private FragmentCentersBinding binding;

    // Adapters
    private CentersAdapter centersAdapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCentersBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        ((MainActivity) getActivity()).handler.postDelayed(() -> {
            binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
            binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
        }, 2000);

        return binding.getRoot();
    }

    private void initializer() {
        centersAdapter = new CentersAdapter(getActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._6sdp), (int) getResources().getDimension(R.dimen._12sdp));
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.CentersFragmentTitle));

        InitManager.imgResTint(getActivity(), binding.addImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);
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
                    ((MainActivity) getActivity()).controlEditText.select(getActivity(), binding.searchIncludeLayout.editText);
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
                ((MainActivity) getActivity()).handler.removeCallbacksAndMessages(null);
                ((MainActivity) getActivity()).handler.postDelayed(() -> {
                    // TODO : Place Code Here
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.addImageView.getRoot().setOnClickListener(v -> {
            binding.addImageView.getRoot().setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> binding.addImageView.getRoot().setClickable(true), 300);

            ((MainActivity) getActivity()).navigator(R.id.createCenterFragment);
        });
    }

    private void setData() {
//        centersAdapter.setCenter(null);
        binding.indexSingleLayout.recyclerView.setAdapter(centersAdapter);

        String dataSize = "15";
        binding.headerIncludeLayout.countTextView.setText("(" + dataSize + ")");
    }

}