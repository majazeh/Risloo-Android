package com.majazeh.risloo.Views.Fragments.Index;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.ScalesAdapter;

public class ScalesFragment extends Fragment {

    // Adapters
    private ScalesAdapter scalesAdapter;

    // Objects
    private LinearLayoutManager layoutManager;

    // Widgets
    private TextView scalesTitleTextView, scalesCountTextView;
    private EditText scalesSearchEditText;
    private ProgressBar scalesSearchProgressBar;
    private ShimmerFrameLayout scalesShimmerLayout;
    private View scalesShimmerTopView;
    private ConstraintLayout scalesHeaderLayout, scalesConstraintLayout;
    private RecyclerView scalesRecyclerView;
    private TextView scalesEmptyTextView;
    private ProgressBar scalesProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scales, viewGroup, false);

        initializer(view);

        detector();

        listener();

        setData();

        ((MainActivity) getActivity()).handler.postDelayed(() -> {
            scalesShimmerLayout.setVisibility(View.GONE);
            scalesHeaderLayout.setVisibility(View.VISIBLE);
            scalesConstraintLayout.setVisibility(View.VISIBLE);
        }, 2000);

        return view;
    }

    private void initializer(View view) {
        scalesAdapter = new ScalesAdapter(getActivity());

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        scalesTitleTextView = view.findViewById(R.id.component_index_header_title_textView);
        scalesTitleTextView.setText(getResources().getString(R.string.ScalesFragmentTitle));
        scalesCountTextView = view.findViewById(R.id.component_index_header_count_textView);

        scalesSearchEditText = view.findViewById(R.id.component_input_search_editText);

        scalesSearchProgressBar = view.findViewById(R.id.component_input_search_progressBar);

        scalesShimmerLayout = view.findViewById(R.id.fragment_scales_index_shimmerLayout);
        scalesShimmerTopView = view.findViewById(R.id.shimmer_item_scale_top_view);
        scalesShimmerTopView.setVisibility(View.GONE);

        scalesHeaderLayout = view.findViewById(R.id.fragment_scales_index_headerLayout);
        scalesConstraintLayout = view.findViewById(R.id.fragment_scales_index_constraintLayout);

        scalesRecyclerView = view.findViewById(R.id.component_index_scale_recyclerView);
        scalesRecyclerView.addItemDecoration(new ItemDecorateRecyclerView("verticalLayout", 0,0, 0, 0));
        scalesRecyclerView.setLayoutManager(layoutManager);
        scalesRecyclerView.setNestedScrollingEnabled(false);
        scalesRecyclerView.setHasFixedSize(true);

        scalesEmptyTextView = view.findViewById(R.id.component_index_scale_textView);

        scalesProgressBar = view.findViewById(R.id.component_index_scale_progressBar);
    }

    private void detector() {
        // TODO : Place Work Here
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        scalesSearchEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!scalesSearchEditText.hasFocus()) {
                    ((MainActivity) getActivity()).controlEditText.select(getActivity(), scalesSearchEditText);
                }
            }
            return false;
        });

        scalesSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((MainActivity) getActivity()).handler.removeCallbacksAndMessages(null);
                ((MainActivity) getActivity()).handler.postDelayed(() -> {
//                    if (scalesSearchEditText.length() != 0) {
//                        getData("getScales", "", scalesSearchEditText.getText().toString().trim());
//                    } else {
//                        scalesRecyclerView.setAdapter(null);
//
//                        if (scalesEmptyTextView.getVisibility() == View.VISIBLE) {
//                            scalesEmptyTextView.setVisibility(View.GONE);
//                        }
//                    }
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setData() {
//        scalesAdapter.setScale(null);
        scalesRecyclerView.setAdapter(scalesAdapter);

        String dataSize = "15";
        scalesCountTextView.setText("(" + dataSize + ")");
    }

}