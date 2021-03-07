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

public class SessionsFragment extends Fragment {

    // Widgets
    private TextView sessionsTitleTextView, sessionsCountTextView;
    private EditText sessionsSearchEditText;
    private ProgressBar sessionsSearchProgressBar;
    private ImageView sessionsAddImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sessions, viewGroup, false);

        initializer(view);

        detector();

        listener();

        setData();

        return view;
    }

    private void initializer(View view) {
        sessionsTitleTextView = view.findViewById(R.id.component_index_header_title_textView);
        sessionsTitleTextView.setText(getResources().getString(R.string.SessionsFragmentTitle));
        sessionsCountTextView = view.findViewById(R.id.component_index_header_count_textView);

        sessionsSearchEditText = view.findViewById(R.id.component_input_search_editText);

        sessionsSearchProgressBar = view.findViewById(R.id.component_input_search_progressBar);

        sessionsAddImageView = view.findViewById(R.id.fragment_sessions_add_imageView);
        sessionsAddImageView.setImageResource(R.drawable.ic_plus_light);
        ImageViewCompat.setImageTintList(sessionsAddImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Green700));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            sessionsAddImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            sessionsAddImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        sessionsSearchEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!sessionsSearchEditText.hasFocus()) {
                    ((MainActivity) getActivity()).controlEditText.select(getActivity(), sessionsSearchEditText);
                }
            }
            return false;
        });

        sessionsSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((MainActivity) getActivity()).handler.removeCallbacksAndMessages(null);
                ((MainActivity) getActivity()).handler.postDelayed(() -> {
//                    if (sessionsSearchEditText.length() != 0) {
//                        getData("getSessions", "", sessionsSearchEditText.getText().toString().trim());
//                    } else {
//                        sessionsRecyclerView.setAdapter(null);
//
//                        if (sessionsEmptyTextView.getVisibility() == View.VISIBLE) {
//                            sessionsEmptyTextView.setVisibility(View.GONE);
//                        }
//                    }
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sessionsAddImageView.setOnClickListener(v -> {
            sessionsAddImageView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> sessionsAddImageView.setClickable(true), 300);

            ((MainActivity) getActivity()).navigator(R.id.createSessionFragment);
        });
    }

    private void setData() {
        String dataSize = "5";
        sessionsCountTextView.setText("(" + dataSize + ")");
    }

}