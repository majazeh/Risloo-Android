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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.RoomsAdapter;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class CenterFragment extends Fragment {

    // Adapters
    private RoomsAdapter roomsAdapter;

    // Objects
    private LinearLayoutManager layoutManager;

    // Widgets
    private CircleImageView avatarCircleImageView;
    private TextView charTextView;
    private TextView nameTextView;
    private ImageView badgeImageView;
    private TextView ownerTextView, mobileTextView, descriptionTextView;
    private ImageView ownerImageView, mobileImageView;
    private TextView statusTextView, profileTextView;
    private ImageView editImageView, usersImageView;
    private TextView roomsTitleTextView, roomsCountTextView;
    private EditText roomsSearchEditText;
    private ProgressBar roomsSearchProgressBar;
    private ImageView roomsAddImageView;
    private ShimmerFrameLayout roomsShimmerLayout;
    private ConstraintLayout roomsConstraintLayout;
    private RecyclerView roomsRecyclerView;
    private TextView roomsEmptyTextView;
    private ProgressBar roomsProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_center, viewGroup, false);

        initializer(view);

        detector();

        listener();

        setData();

        ((MainActivity) getActivity()).handler.postDelayed(() -> {
            roomsShimmerLayout.setVisibility(View.GONE);
            roomsConstraintLayout.setVisibility(View.VISIBLE);
        }, 2000);

        return view;
    }

    private void initializer(View view) {
        roomsAdapter = new RoomsAdapter(getActivity());

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        avatarCircleImageView = view.findViewById(R.id.component_avatar_86sdp_border_white_circleImageView);

        charTextView = view.findViewById(R.id.component_avatar_86sdp_border_white_textView);

        nameTextView = view.findViewById(R.id.fragment_center_name_textView);

        badgeImageView = view.findViewById(R.id.fragment_center_badge_imageView);

        ownerTextView = view.findViewById(R.id.fragment_center_owner_textView);
        mobileTextView = view.findViewById(R.id.fragment_center_mobile_textView);
        descriptionTextView = view.findViewById(R.id.fragment_center_description_textView);

        ownerImageView = view.findViewById(R.id.fragment_center_owner_imageView);
        mobileImageView = view.findViewById(R.id.fragment_center_mobile_imageView);

        profileTextView = view.findViewById(R.id.fragment_center_profile_textView);
        profileTextView.setText(getResources().getString(R.string.CenterFragmentProfile));
        profileTextView.setTextColor(getResources().getColor(R.color.Gray500));
        statusTextView = view.findViewById(R.id.fragment_center_status_textView);
        statusTextView.setText(getResources().getString(R.string.CenterFragmentRequest));
        statusTextView.setTextColor(getResources().getColor(R.color.White));

        editImageView = view.findViewById(R.id.fragment_center_edit_imageView);
        editImageView.setImageResource(R.drawable.ic_edit_light);
        ImageViewCompat.setImageTintList(editImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Gray500));
        usersImageView = view.findViewById(R.id.fragment_center_users_imageView);
        usersImageView.setImageResource(R.drawable.ic_users_light);
        ImageViewCompat.setImageTintList(usersImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Blue600));

        roomsTitleTextView = view.findViewById(R.id.component_index_header_title_textView);
        roomsTitleTextView.setText(getResources().getString(R.string.CenterFragmentRoomsHeader));
        roomsCountTextView = view.findViewById(R.id.component_index_header_count_textView);

        roomsSearchEditText = view.findViewById(R.id.component_index_search_editText);

        roomsSearchProgressBar = view.findViewById(R.id.component_index_search_progressBar);

        roomsAddImageView = view.findViewById(R.id.fragment_center_rooms_add_imageView);
        roomsAddImageView.setImageResource(R.drawable.ic_plus_light);
        ImageViewCompat.setImageTintList(roomsAddImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Green700));

        roomsShimmerLayout = view.findViewById(R.id.fragment_center_rooms_index_shimmerLayout);

        roomsConstraintLayout = view.findViewById(R.id.fragment_center_rooms_index_constraintLayout);

        roomsRecyclerView = view.findViewById(R.id.component_single_room_recyclerView);
        roomsRecyclerView.addItemDecoration(new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._6sdp), (int) getResources().getDimension(R.dimen._12sdp)));
        roomsRecyclerView.setLayoutManager(layoutManager);
        roomsRecyclerView.setNestedScrollingEnabled(false);
        roomsRecyclerView.setHasFixedSize(true);

        roomsEmptyTextView = view.findViewById(R.id.component_single_room_textView);

        roomsProgressBar = view.findViewById(R.id.component_single_room_progressBar);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            profileTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);
            statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_green600_ripple_green800);
            editImageView.setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_gray500_ripple_gray300);
            usersImageView.setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_blue600_ripple_blue300);

            roomsAddImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            profileTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);
            statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_green600);
            editImageView.setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_gray500);
            usersImageView.setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_blue600_ripple_blue300);

            roomsAddImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
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

        statusTextView.setOnClickListener(v -> {
            statusTextView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> statusTextView.setClickable(true), 300);

            // TODO : Call Work Method
        });

        profileTextView.setOnClickListener(v -> {
            profileTextView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> profileTextView.setClickable(true), 300);

            // TODO : Call Work Method
        });

        editImageView.setOnClickListener(v -> {
            editImageView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> editImageView.setClickable(true), 300);

            // TODO : Call Work Method
        });

        usersImageView.setOnClickListener(v -> {
            usersImageView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> usersImageView.setClickable(true), 300);

            // TODO : Call Work Method
        });

        roomsSearchEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!roomsSearchEditText.hasFocus()) {
                    ((MainActivity) getActivity()).controlEditText.select(getActivity(), roomsSearchEditText);
                }
            }
            return false;
        });

        roomsSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((MainActivity) getActivity()).handler.removeCallbacksAndMessages(null);
                ((MainActivity) getActivity()).handler.postDelayed(() -> {
//                    if (roomsSearchEditText.length() != 0) {
//                        getData("getRooms", "", roomsSearchEditText.getText().toString().trim());
//                    } else {
//                        roomsRecyclerView.setAdapter(null);
//
//                        if (roomsEmptyTextView.getVisibility() == View.VISIBLE) {
//                            roomsEmptyTextView.setVisibility(View.GONE);
//                        }
//                    }
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        roomsAddImageView.setOnClickListener(v -> {
            roomsAddImageView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> roomsAddImageView.setClickable(true), 300);

//            ((MainActivity) getActivity()).navigator(R.id.createRoomFragment);
        });
    }

    private void setData() {
        if (((MainActivity) getActivity()).singleton.getName().equals("")) {
            nameTextView.setText(getResources().getString(R.string.MainToolbar));
        } else {
            nameTextView.setText(((MainActivity) getActivity()).singleton.getName());
        }

        if (((MainActivity) getActivity()).singleton.getOwner().equals("")) {
            ownerTextView.setVisibility(View.GONE);
            ownerImageView.setVisibility(View.GONE);
        } else {
            ownerTextView.setText(((MainActivity) getActivity()).singleton.getOwner());
        }

        if (((MainActivity) getActivity()).singleton.getMobile().equals("")) {
            mobileTextView.setVisibility(View.GONE);
            mobileImageView.setVisibility(View.GONE);
        } else {
            mobileTextView.setText(((MainActivity) getActivity()).singleton.getMobile());
        }

        if (((MainActivity) getActivity()).singleton.getDescription().equals("")) {
            descriptionTextView.setVisibility(View.GONE);
        } else {
            descriptionTextView.setText(((MainActivity) getActivity()).singleton.getDescription());
        }

        if (((MainActivity) getActivity()).singleton.getAvatar().equals("")) {
            charTextView.setVisibility(View.VISIBLE);
            charTextView.setText(StringManager.firstChars(nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(avatarCircleImageView);
        } else {
            charTextView.setVisibility(View.GONE);

            Picasso.get().load(((MainActivity) getActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(avatarCircleImageView);
        }

        //        roomsAdapter.setRoom(null);
        roomsRecyclerView.setAdapter(roomsAdapter);

        String dataSize = "15";
        roomsCountTextView.setText("(" + dataSize + ")");
    }

}