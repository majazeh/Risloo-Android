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
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;

public class ScalesFragment extends Fragment {

    // Widgets
    private TextView scalesTitleTextView, scalesCountTextView;
    private EditText scalesSearchEditText;
    private ProgressBar scalesSearchProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scales, viewGroup, false);

        initializer(view);

        detector();

        listener();

        setData();

        return view;
    }

    private void initializer(View view) {
        scalesTitleTextView = view.findViewById(R.id.component_index_header_title_textView);
        scalesTitleTextView.setText(getResources().getString(R.string.ScalesFragmentTitle));
        scalesCountTextView = view.findViewById(R.id.component_index_header_count_textView);

        scalesSearchEditText = view.findViewById(R.id.component_input_search_editText);

        scalesSearchProgressBar = view.findViewById(R.id.component_input_search_progressBar);
    }

    private void detector() {
        // TODO : Place Code Here
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
        String dataSize = "5";
        scalesCountTextView.setText("(" + dataSize + ")");
    }

}