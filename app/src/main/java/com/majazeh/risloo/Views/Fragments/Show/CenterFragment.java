package com.majazeh.risloo.Views.Fragments.Show;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Cases2Adapter;
import com.majazeh.risloo.Views.Adapters.Recycler.RoomsAdapter;
import com.majazeh.risloo.databinding.FragmentCenterBinding;
import com.squareup.picasso.Picasso;

public class CenterFragment extends Fragment {

    // Binding
    private FragmentCenterBinding binding;

    // Adapters
    private RoomsAdapter roomsAdapter;
    private Cases2Adapter cases2Adapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager roomsLayoutManager, cases2LayoutManager;
    private Handler handler;

    // Vars
    private String status = "request", center = "clinic";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCenterBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        roomsAdapter = new RoomsAdapter(requireActivity());
        cases2Adapter = new Cases2Adapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._4sdp), (int) getResources().getDimension(R.dimen._12sdp));

        roomsLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        cases2LayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        handler = new Handler();

        InitManager.imgResTint(requireActivity(), binding.editImageView.getRoot(), R.drawable.ic_edit_light, R.color.Gray500);
        InitManager.imgResTint(requireActivity(), binding.profileImageView.getRoot(), R.drawable.ic_user_crown_light, R.color.Blue600);
        InitManager.imgResTint(requireActivity(), binding.schedulesImageView.getRoot(), R.drawable.ic_user_clock_light, R.color.Blue600);
        InitManager.imgResTint(requireActivity(), binding.usersImageView.getRoot(), R.drawable.ic_users_light, R.color.Blue600);

        binding.roomsHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.RoomsAdapterHeader));
        binding.casesHeaderIncludeLayout.titleTextView.setText(getResources().getString(R.string.Cases2AdapterHeader));

        InitManager.imgResTint(requireActivity(), binding.addRoomImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);
        InitManager.imgResTint(requireActivity(), binding.addCaseImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);
        InitManager.imgResTint(requireActivity(), binding.addRoomScheduleImageView.getRoot(), R.drawable.ic_calendar_plus_light, R.color.Green700);
        InitManager.imgResTint(requireActivity(), binding.addCaseScheduleImageView.getRoot(), R.drawable.ic_calendar_plus_light, R.color.Green700);
        InitManager.recyclerView(binding.roomsSingleLayout.recyclerView, itemDecoration, roomsLayoutManager);
        InitManager.recyclerView(binding.casesSingleLayout.recyclerView, itemDecoration, cases2LayoutManager);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.editImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_gray500_ripple_gray300);
            binding.profileImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_blue600_ripple_blue300);
            binding.schedulesImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_blue600_ripple_blue300);
            binding.usersImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_blue600_ripple_blue300);

            binding.addRoomImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_green700_ripple_green300);
            binding.addCaseImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_green700_ripple_green300);
            binding.addRoomScheduleImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_green700_ripple_green300);
            binding.addCaseScheduleImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            binding.editImageView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);
            binding.profileImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_blue600);
            binding.schedulesImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_blue600);
            binding.usersImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_blue600);

            binding.addRoomImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_green700);
            binding.addCaseImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_green700);
            binding.addRoomScheduleImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_green700);
            binding.addCaseScheduleImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_green700);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
                IntentManager.display(requireActivity(), "", "", ((MainActivity) requireActivity()).singleton.getAvatar());
            }
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.editCenterFragment)).widget(binding.editImageView.getRoot());

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.userFragment)).widget(binding.profileImageView.getRoot());

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.centerSchedulesFragment)).widget(binding.schedulesImageView.getRoot());

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.centerUsersFragment)).widget(binding.usersImageView.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(binding.statusTextView.getRoot());

        binding.roomsSearchIncludeLayout.editText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.roomsSearchIncludeLayout.editText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.roomsSearchIncludeLayout.editText);
                }
            }
            return false;
        });

        binding.casesSearchIncludeLayout.editText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.casesSearchIncludeLayout.editText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.casesSearchIncludeLayout.editText);
                }
            }
            return false;
        });

        binding.roomsSearchIncludeLayout.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(() -> {
                    // TODO : Place Code Here
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.casesSearchIncludeLayout.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(() -> {
                    // TODO : Place Code Here
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createRoomFragment)).widget(binding.addRoomImageView.getRoot());
        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createCaseFragment)).widget(binding.addCaseImageView.getRoot());

        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createScheduleFragment)).widget(binding.addRoomScheduleImageView.getRoot());
        ClickManager.onClickListener(() -> ((MainActivity) requireActivity()).navigator(R.id.createScheduleFragment)).widget(binding.addCaseScheduleImageView.getRoot());
    }

    private void setData() {
        // Todo : Place Code Here And set them to the below conditions

        if (((MainActivity) requireActivity()).singleton.getName().equals("")) {
            binding.nameTextView.setText(getResources().getString(R.string.AppDefaultName));
        } else {
            binding.nameTextView.setText(((MainActivity) requireActivity()).singleton.getName());
        }

        if (((MainActivity) requireActivity()).singleton.getDescription().equals("")) {
            binding.descriptionTextView.setVisibility(View.GONE);
        } else {
            binding.descriptionTextView.setText(((MainActivity) requireActivity()).singleton.getDescription());
        }

        if (((MainActivity) requireActivity()).singleton.getOwner().equals("")) {
            binding.ownerGroup.setVisibility(View.GONE);
        } else {
            binding.ownerGroup.setVisibility(View.VISIBLE);
            binding.ownerTextView.setText(((MainActivity) requireActivity()).singleton.getOwner());
        }

        if (((MainActivity) requireActivity()).singleton.getMobile().equals("")) {
            binding.mobileGroup.setVisibility(View.GONE);
        } else {
            binding.mobileGroup.setVisibility(View.VISIBLE);
            binding.mobileTextView.setText(((MainActivity) requireActivity()).singleton.getMobile());
        }

        if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(((MainActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
        }

        if (!((MainActivity) requireActivity()).singleton.getEnter()) {
            binding.editImageView.getRoot().setVisibility(View.GONE);
            binding.profileImageView.getRoot().setVisibility(View.GONE);
            binding.schedulesImageView.getRoot().setVisibility(View.GONE);
            binding.usersImageView.getRoot().setVisibility(View.GONE);
        } else {
            binding.editImageView.getRoot().setVisibility(View.VISIBLE);
            binding.profileImageView.getRoot().setVisibility(View.VISIBLE);
            binding.schedulesImageView.getRoot().setVisibility(View.VISIBLE);
            binding.usersImageView.getRoot().setVisibility(View.VISIBLE);
        }

        switch (status) {
            case "request":
                binding.statusTextView.getRoot().setEnabled(true);

                binding.statusTextView.getRoot().setText(getResources().getString(R.string.CenterFragmentStatusRequest));
                binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.White));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                    binding.statusTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_green600_ripple_green800);
                else
                    binding.statusTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_green600);
                break;
            case "owner":
                binding.statusTextView.getRoot().setEnabled(false);

                binding.statusTextView.getRoot().setText(getResources().getString(R.string.CenterFragmentStatusOwner));
                binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.Gray500));

                binding.statusTextView.getRoot().setBackgroundResource(android.R.color.transparent);
                break;
            case "reference":
                binding.statusTextView.getRoot().setEnabled(false);

                binding.statusTextView.getRoot().setText(getResources().getString(R.string.CenterFragmentStatusReference));
                binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.Gray500));

                binding.statusTextView.getRoot().setBackgroundResource(android.R.color.transparent);
                break;
            case "accepted":
                binding.statusTextView.getRoot().setEnabled(false);

                binding.statusTextView.getRoot().setText(getResources().getString(R.string.CenterFragmentStatusAccepted));
                binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.Gray500));

                binding.statusTextView.getRoot().setBackgroundResource(android.R.color.transparent);
                break;
            case "awaiting":
                binding.statusTextView.getRoot().setEnabled(false);

                binding.statusTextView.getRoot().setText(getResources().getString(R.string.CenterFragmentStatusAwaiting));
                binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.Gray500));

                binding.statusTextView.getRoot().setBackgroundResource(android.R.color.transparent);
                break;
            case "kicked":
                binding.statusTextView.getRoot().setEnabled(false);

                binding.statusTextView.getRoot().setText(getResources().getString(R.string.CenterFragmentStatusKicked));
                binding.statusTextView.getRoot().setTextColor(getResources().getColor(R.color.Gray500));

                binding.statusTextView.getRoot().setBackgroundResource(android.R.color.transparent);
                break;
        }

        switch (center) {
            case "personal":
                binding.casesGroup.setVisibility(View.VISIBLE);
                binding.roomsGroup.setVisibility(View.GONE);
                binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);

                //        cases2Adapter.setCases(null);
                binding.casesSingleLayout.recyclerView.setAdapter(cases2Adapter);
                binding.casesHeaderIncludeLayout.countTextView.setText("(" + cases2Adapter.getItemCount() + ")");

                new Handler().postDelayed(() -> {
                    binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);
                    binding.casesSingleLayout.getRoot().setVisibility(View.VISIBLE);
                }, 1000);
                break;
            case "clinic":
                binding.roomsGroup.setVisibility(View.VISIBLE);
                binding.casesGroup.setVisibility(View.GONE);
                binding.casesShimmerLayout.getRoot().setVisibility(View.GONE);

                //        roomsAdapter.setRooms(null);
                binding.roomsSingleLayout.recyclerView.setAdapter(roomsAdapter);
                binding.roomsHeaderIncludeLayout.countTextView.setText("(" + roomsAdapter.getItemCount() + ")");

                new Handler().postDelayed(() -> {
                    binding.roomsShimmerLayout.getRoot().setVisibility(View.GONE);
                    binding.roomsSingleLayout.getRoot().setVisibility(View.VISIBLE);
                }, 1000);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}