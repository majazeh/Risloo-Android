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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.SamplesAdapter;

public class SamplesFragment extends Fragment {

    // Adapters
    private SamplesAdapter samplesAdapter;

    // Objects
    private LinearLayoutManager layoutManager;

    // Widgets
    private TextView samplesTitleTextView, samplesCountTextView;
    private EditText samplesSearchEditText;
    private ProgressBar samplesSearchProgressBar;
    private ImageView samplesAddImageView;
    private ShimmerFrameLayout samplesShimmerLayout;
    private View samplesShimmerTopView;
    private ConstraintLayout samplesHeaderLayout, samplesConstraintLayout;
    private RecyclerView samplesRecyclerView;
    private TextView samplesEmptyTextView;
    private ProgressBar samplesProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_samples, viewGroup, false);

        initializer(view);

        detector();

        listener();

        setData();

        ((MainActivity) getActivity()).handler.postDelayed(() -> {
            samplesShimmerLayout.setVisibility(View.GONE);
            samplesHeaderLayout.setVisibility(View.VISIBLE);
            samplesConstraintLayout.setVisibility(View.VISIBLE);
        }, 2000);

        return view;
    }

    private void initializer(View view) {
        samplesAdapter = new SamplesAdapter(getActivity());

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        samplesTitleTextView = view.findViewById(R.id.component_index_header_title_textView);
        samplesTitleTextView.setText(getResources().getString(R.string.SamplesFragmentTitle));
        samplesCountTextView = view.findViewById(R.id.component_index_header_count_textView);

        samplesSearchEditText = view.findViewById(R.id.component_index_search_editText);

        samplesSearchProgressBar = view.findViewById(R.id.component_index_search_progressBar);

        samplesAddImageView = view.findViewById(R.id.fragment_samples_add_imageView);
        samplesAddImageView.setImageResource(R.drawable.ic_plus_light);
        ImageViewCompat.setImageTintList(samplesAddImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Green700));

        samplesShimmerLayout = view.findViewById(R.id.fragment_samples_index_shimmerLayout);
        samplesShimmerTopView = view.findViewById(R.id.shimmer_item_sample_top_view);
        samplesShimmerTopView.setVisibility(View.GONE);

        samplesHeaderLayout = view.findViewById(R.id.fragment_samples_index_headerLayout);
        samplesConstraintLayout = view.findViewById(R.id.fragment_samples_index_constraintLayout);

        samplesRecyclerView = view.findViewById(R.id.component_single_sample_recyclerView);
        samplesRecyclerView.addItemDecoration(new ItemDecorateRecyclerView("verticalLayout", 0,0, 0, 0));
        samplesRecyclerView.setLayoutManager(layoutManager);
        samplesRecyclerView.setNestedScrollingEnabled(false);
        samplesRecyclerView.setHasFixedSize(true);

        samplesEmptyTextView = view.findViewById(R.id.component_single_sample_textView);

        samplesProgressBar = view.findViewById(R.id.component_single_sample_progressBar);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            samplesAddImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            samplesAddImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        samplesSearchEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!samplesSearchEditText.hasFocus()) {
                    ((MainActivity) getActivity()).controlEditText.select(getActivity(), samplesSearchEditText);
                }
            }
            return false;
        });

        samplesSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((MainActivity) getActivity()).handler.removeCallbacksAndMessages(null);
                ((MainActivity) getActivity()).handler.postDelayed(() -> {
//                    if (samplesSearchEditText.length() != 0) {
//                        getData("getSamples", "", samplesSearchEditText.getText().toString().trim());
//                    } else {
//                        samplesRecyclerView.setAdapter(null);
//
//                        if (samplesEmptyTextView.getVisibility() == View.VISIBLE) {
//                            samplesEmptyTextView.setVisibility(View.GONE);
//                        }
//                    }
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        samplesAddImageView.setOnClickListener(v -> {
            samplesAddImageView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> samplesAddImageView.setClickable(true), 300);

            ((MainActivity) getActivity()).navigator(R.id.createSampleFragment);
        });
    }

    private void setData() {
//        samplesAdapter.setSample(null);
        samplesRecyclerView.setAdapter(samplesAdapter);

        String dataSize = "15";
        samplesCountTextView.setText("(" + dataSize + ")");
    }

}