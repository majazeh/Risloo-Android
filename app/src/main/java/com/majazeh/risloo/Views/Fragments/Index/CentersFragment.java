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

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;

public class CentersFragment extends Fragment {

    // Widgets
    private TextView centersTitleTextView, centersCountTextView;
    private EditText centersSearchEditText;
    private ProgressBar centersSearchProgressBar;
    private ImageView centersAddImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_centers, viewGroup, false);

        initializer(view);

        detector();

        listener();

        setData();

        return view;
    }

    private void initializer(View view) {
        centersTitleTextView = view.findViewById(R.id.component_index_header_title_textView);
        centersTitleTextView.setText(getResources().getString(R.string.CentersFragmentHeader));
        centersCountTextView = view.findViewById(R.id.component_index_header_count_textView);

        centersSearchEditText = view.findViewById(R.id.component_input_search_editText);

        centersSearchProgressBar = view.findViewById(R.id.component_input_search_progressBar);

        centersAddImageView = view.findViewById(R.id.fragment_centers_add_imageView);
        centersAddImageView.setImageResource(R.drawable.ic_plus_light);
        ImageViewCompat.setImageTintList(centersAddImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Green700));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            centersAddImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            centersAddImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        centersSearchEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!centersSearchEditText.hasFocus()) {
                    ((MainActivity) getActivity()).controlEditText.select(getActivity(), centersSearchEditText);
                }
            }
            return false;
        });

        centersSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((MainActivity) getActivity()).handler.removeCallbacksAndMessages(null);
                ((MainActivity) getActivity()).handler.postDelayed(() -> {
//                    if (centersSearchEditText.length() != 0) {
//                        getData("getCenters", "", centersSearchEditText.getText().toString().trim());
//                    } else {
//                        centersRecyclerView.setAdapter(null);
//
//                        if (centersEmptyTextView.getVisibility() == View.VISIBLE) {
//                            centersEmptyTextView.setVisibility(View.GONE);
//                        }
//                    }
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        centersAddImageView.setOnClickListener(v -> {
            centersAddImageView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> centersAddImageView.setClickable(true), 300);

            ((MainActivity) getActivity()).navigator(R.id.createCenterFragment);
        });
    }

    private void setData() {
        String dataSize = "5";
        centersCountTextView.setText("(" + dataSize + ")");
    }

}