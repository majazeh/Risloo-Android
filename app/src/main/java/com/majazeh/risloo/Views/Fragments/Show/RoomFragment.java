package com.majazeh.risloo.Views.Fragments.Show;

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
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoomFragment extends Fragment {

    // Objects
    private LinearLayoutManager layoutManager;

    // Widgets
    private CircleImageView avatarCircleImageView;
    private TextView charTextView;
    private TextView nameTextView;
    private ImageView usersImageView;
    private TextView casesTitleTextView, casesCountTextView;
    private EditText casesSearchEditText;
    private ProgressBar casesSearchProgressBar;
    private ImageView casesAddImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room, viewGroup, false);

        initializer(view);

        detector();

        listener();

        setData();

        return view;
    }

    private void initializer(View view) {
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        avatarCircleImageView = view.findViewById(R.id.component_avatar_86sdp_border_white_circleImageView);

        charTextView = view.findViewById(R.id.component_avatar_86sdp_border_white_textView);

        nameTextView = view.findViewById(R.id.fragment_room_name_textView);

        usersImageView = view.findViewById(R.id.fragment_room_users_imageView);
        usersImageView.setImageResource(R.drawable.ic_users_light);
        ImageViewCompat.setImageTintList(usersImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Blue600));

        casesTitleTextView = view.findViewById(R.id.component_index_header_title_textView);
        casesTitleTextView.setText(getResources().getString(R.string.RoomFragmentCasesHeader));
        casesCountTextView = view.findViewById(R.id.component_index_header_count_textView);

        casesSearchEditText = view.findViewById(R.id.component_input_search_editText);

        casesSearchProgressBar = view.findViewById(R.id.component_input_search_progressBar);

        casesAddImageView = view.findViewById(R.id.fragment_room_cases_add_imageView);
        casesAddImageView.setImageResource(R.drawable.ic_plus_light);
        ImageViewCompat.setImageTintList(casesAddImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Green700));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            usersImageView.setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_blue600_ripple_blue300);

            casesAddImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            usersImageView.setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_blue600);

            casesAddImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        avatarCircleImageView.setOnClickListener(v -> {
            avatarCircleImageView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> avatarCircleImageView.setClickable(true), 300);

            if (!((MainActivity) getActivity()).singleton.getAvatar().equals("")) {
                IntentManager.display(getActivity(), "", "", ((MainActivity) getActivity()).singleton.getAvatar());
            }
        });

        usersImageView.setOnClickListener(v -> {
            usersImageView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> usersImageView.setClickable(true), 300);

            // TODO : Call Work Method
        });

        casesSearchEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!casesSearchEditText.hasFocus()) {
                    ((MainActivity) getActivity()).controlEditText.select(getActivity(), casesSearchEditText);
                }
            }
            return false;
        });

        casesSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((MainActivity) getActivity()).handler.removeCallbacksAndMessages(null);
                ((MainActivity) getActivity()).handler.postDelayed(() -> {
//                    if (casesSearchEditText.length() != 0) {
//                        getData("getCases", "", casesSearchEditText.getText().toString().trim());
//                    } else {
//                        casesRecyclerView.setAdapter(null);
//
//                        if (casesEmptyTextView.getVisibility() == View.VISIBLE) {
//                            casesEmptyTextView.setVisibility(View.GONE);
//                        }
//                    }
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        casesAddImageView.setOnClickListener(v -> {
            casesAddImageView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> casesAddImageView.setClickable(true), 300);

            ((MainActivity) getActivity()).navigator(R.id.createCaseFragment);
        });
    }

    private void setData() {
        if (((MainActivity) getActivity()).singleton.getName().equals("")) {
            nameTextView.setText(getResources().getString(R.string.MainToolbar));
        } else {
            nameTextView.setText(((MainActivity) getActivity()).singleton.getName());
        }

        if (((MainActivity) getActivity()).singleton.getAvatar().equals("")) {
            charTextView.setVisibility(View.VISIBLE);
            charTextView.setText(StringManager.firstChars(nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(avatarCircleImageView);
        } else {
            charTextView.setVisibility(View.GONE);

            Picasso.get().load(((MainActivity) getActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(avatarCircleImageView);
        }

        String dataSize = "5";
        casesCountTextView.setText("(" + dataSize + ")");
    }

}