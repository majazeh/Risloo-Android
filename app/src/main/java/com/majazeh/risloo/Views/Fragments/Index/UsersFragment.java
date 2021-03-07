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

public class UsersFragment extends Fragment {

    // Widgets
    private TextView usersTitleTextView, usersCountTextView;
    private EditText usersSearchEditText;
    private ProgressBar usersSearchProgressBar;
    private ImageView usersAddImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, viewGroup, false);

        initializer(view);

        detector();

        listener();

        setData();

        return view;
    }

    private void initializer(View view) {
        usersTitleTextView = view.findViewById(R.id.component_index_header_title_textView);
        usersTitleTextView.setText(getResources().getString(R.string.UsersFragmentHeader));
        usersCountTextView = view.findViewById(R.id.component_index_header_count_textView);

        usersSearchEditText = view.findViewById(R.id.component_input_search_editText);

        usersSearchProgressBar = view.findViewById(R.id.component_input_search_progressBar);

        usersAddImageView = view.findViewById(R.id.fragment_users_add_imageView);
        usersAddImageView.setImageResource(R.drawable.ic_plus_light);
        ImageViewCompat.setImageTintList(usersAddImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Green700));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            usersAddImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            usersAddImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        usersSearchEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!usersSearchEditText.hasFocus()) {
                    ((MainActivity) getActivity()).controlEditText.select(getActivity(), usersSearchEditText);
                }
            }
            return false;
        });

        usersSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((MainActivity) getActivity()).handler.removeCallbacksAndMessages(null);
                ((MainActivity) getActivity()).handler.postDelayed(() -> {
//                    if (usersSearchEditText.length() != 0) {
//                        getData("getUsers", "", usersSearchEditText.getText().toString().trim());
//                    } else {
//                        usersRecyclerView.setAdapter(null);
//
//                        if (usersEmptyTextView.getVisibility() == View.VISIBLE) {
//                            usersEmptyTextView.setVisibility(View.GONE);
//                        }
//                    }
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        usersAddImageView.setOnClickListener(v -> {
            usersAddImageView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> usersAddImageView.setClickable(true), 300);

            ((MainActivity) getActivity()).navigator(R.id.createUserFragment);
        });
    }

    private void setData() {
        String dataSize = "5";
        usersCountTextView.setText("(" + dataSize + ")");
    }

}