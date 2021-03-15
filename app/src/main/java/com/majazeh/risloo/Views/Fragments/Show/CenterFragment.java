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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.RoomsAdapter;
import com.majazeh.risloo.databinding.FragmentCenterBinding;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class CenterFragment extends Fragment {

    // Binding
    private FragmentCenterBinding binding;

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
        binding = FragmentCenterBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        ((MainActivity) requireActivity()).handler.postDelayed(() -> {
            roomsShimmerLayout.setVisibility(View.GONE);
            roomsConstraintLayout.setVisibility(View.VISIBLE);
        }, 2000);

        return binding.getRoot();
    }

    private void initializer() {
        roomsAdapter = new RoomsAdapter(getActivity());

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        avatarCircleImageView = binding.fragmentCenterAvatarCircleImageView.componentAvatar86sdpBorderWhiteCircleImageView;

        charTextView = binding.fragmentCenterAvatarCircleImageView.componentAvatar86sdpBorderWhiteTextView;

        nameTextView = binding.fragmentCenterNameTextView;

        badgeImageView = binding.fragmentCenterBadgeImageView;

        ownerTextView = binding.fragmentCenterOwnerTextView;
        mobileTextView = binding.fragmentCenterMobileTextView;
        descriptionTextView = binding.fragmentCenterDescriptionTextView;

        ownerImageView = binding.fragmentCenterOwnerImageView;
        mobileImageView = binding.fragmentCenterMobileImageView;

        profileTextView = binding.fragmentCenterProfileTextView.componentButtonRectangle28sdp;
        profileTextView.setText(getResources().getString(R.string.CenterFragmentProfile));
        profileTextView.setTextColor(getResources().getColor(R.color.Gray500));
        statusTextView = binding.fragmentCenterStatusTextView.componentButtonRectangle28sdp;
        statusTextView.setText(getResources().getString(R.string.CenterFragmentRequest));
        statusTextView.setTextColor(getResources().getColor(R.color.White));

        editImageView = binding.fragmentCenterEditImageView.componentButtonOval28sdp;
        InitManager.imageView(getActivity(), editImageView, R.drawable.ic_edit_light, R.color.Gray500);
        usersImageView = binding.fragmentCenterUsersImageView.componentButtonOval28sdp;
        InitManager.imageView(getActivity(), usersImageView, R.drawable.ic_users_light, R.color.Blue600);

        roomsTitleTextView = binding.fragmentCenterRoomsHeaderConstraintLayout.componentIndexHeaderTitleTextView;
        roomsTitleTextView.setText(getResources().getString(R.string.CenterFragmentRoomsHeader));
        roomsCountTextView = binding.fragmentCenterRoomsHeaderConstraintLayout.componentIndexHeaderCountTextView;

        roomsSearchEditText = binding.fragmentCenterRoomsSearchConstraintLayout.componentIndexSearchEditText;
        roomsSearchProgressBar = binding.fragmentCenterRoomsSearchConstraintLayout.componentIndexSearchProgressBar;

        roomsAddImageView = binding.fragmentCenterRoomsAddImageView.componentButtonOval28sdp;
        InitManager.imageView(getActivity(), roomsAddImageView, R.drawable.ic_plus_light, R.color.Green700);

        roomsShimmerLayout = binding.fragmentCenterRoomsIndexShimmerLayout.componentShimmerRoom;
        roomsConstraintLayout = binding.fragmentCenterRoomsIndexConstraintLayout.componentSingleRoom;

        roomsRecyclerView = binding.fragmentCenterRoomsIndexConstraintLayout.componentSingleRoomRecyclerView;
        roomsRecyclerView.addItemDecoration(new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._6sdp), (int) getResources().getDimension(R.dimen._12sdp)));
        roomsRecyclerView.setLayoutManager(layoutManager);
        roomsRecyclerView.setNestedScrollingEnabled(false);
        roomsRecyclerView.setHasFixedSize(true);

        roomsEmptyTextView = binding.fragmentCenterRoomsIndexConstraintLayout.componentSingleRoomTextView;

        roomsProgressBar = binding.fragmentCenterRoomsIndexConstraintLayout.componentSingleRoomProgressBar;
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
            ((MainActivity) requireActivity()).handler.postDelayed(() -> avatarCircleImageView.setClickable(true), 300);

            if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
                IntentManager.display(getActivity(), "", "", ((MainActivity) requireActivity()).singleton.getAvatar());
            }
        });

        statusTextView.setOnClickListener(v -> {
            statusTextView.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> statusTextView.setClickable(true), 300);

            // TODO : Call Work Method
        });

        profileTextView.setOnClickListener(v -> {
            profileTextView.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> profileTextView.setClickable(true), 300);

            // TODO : Call Work Method
        });

        editImageView.setOnClickListener(v -> {
            editImageView.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> editImageView.setClickable(true), 300);

            // TODO : Call Work Method
        });

        usersImageView.setOnClickListener(v -> {
            usersImageView.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> usersImageView.setClickable(true), 300);

            // TODO : Call Work Method
        });

        roomsSearchEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!roomsSearchEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(getActivity(), roomsSearchEditText);
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
                ((MainActivity) requireActivity()).handler.removeCallbacksAndMessages(null);
                ((MainActivity) requireActivity()).handler.postDelayed(() -> {
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
            ((MainActivity) requireActivity()).handler.postDelayed(() -> roomsAddImageView.setClickable(true), 300);

//            ((MainActivity) requireActivity()).navigator(R.id.createRoomFragment);
        });
    }

    private void setData() {
        if (((MainActivity) requireActivity()).singleton.getName().equals("")) {
            nameTextView.setText(getResources().getString(R.string.MainToolbar));
        } else {
            nameTextView.setText(((MainActivity) requireActivity()).singleton.getName());
        }

        if (((MainActivity) requireActivity()).singleton.getOwner().equals("")) {
            ownerTextView.setVisibility(View.GONE);
            ownerImageView.setVisibility(View.GONE);
        } else {
            ownerTextView.setText(((MainActivity) requireActivity()).singleton.getOwner());
        }

        if (((MainActivity) requireActivity()).singleton.getMobile().equals("")) {
            mobileTextView.setVisibility(View.GONE);
            mobileImageView.setVisibility(View.GONE);
        } else {
            mobileTextView.setText(((MainActivity) requireActivity()).singleton.getMobile());
        }

        if (((MainActivity) requireActivity()).singleton.getDescription().equals("")) {
            descriptionTextView.setVisibility(View.GONE);
        } else {
            descriptionTextView.setText(((MainActivity) requireActivity()).singleton.getDescription());
        }

        if (((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
            charTextView.setVisibility(View.VISIBLE);
            charTextView.setText(StringManager.firstChars(nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(avatarCircleImageView);
        } else {
            charTextView.setVisibility(View.GONE);

            Picasso.get().load(((MainActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(avatarCircleImageView);
        }

        //        roomsAdapter.setRoom(null);
        roomsRecyclerView.setAdapter(roomsAdapter);

        String dataSize = "15";
        roomsCountTextView.setText("(" + dataSize + ")");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}