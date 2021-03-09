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
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;

public class SamplesFragment extends Fragment {

    // Objects
    private LinearLayoutManager layoutManager;

    // Widgets
    private TextView samplesTitleTextView, samplesCountTextView;
    private EditText samplesSearchEditText;
    private ProgressBar samplesSearchProgressBar;
    private ImageView samplesAddImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_samples, viewGroup, false);

        initializer(view);

        detector();

        listener();

        setData();

        return view;
    }

    private void initializer(View view) {
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        samplesTitleTextView = view.findViewById(R.id.component_index_header_title_textView);
        samplesTitleTextView.setText(getResources().getString(R.string.SamplesFragmentTitle));
        samplesCountTextView = view.findViewById(R.id.component_index_header_count_textView);

        samplesSearchEditText = view.findViewById(R.id.component_input_search_editText);

        samplesSearchProgressBar = view.findViewById(R.id.component_input_search_progressBar);

        samplesAddImageView = view.findViewById(R.id.fragment_samples_add_imageView);
        samplesAddImageView.setImageResource(R.drawable.ic_plus_light);
        ImageViewCompat.setImageTintList(samplesAddImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Green700));
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
        String dataSize = "5";
        samplesCountTextView.setText("(" + dataSize + ")");
    }

}