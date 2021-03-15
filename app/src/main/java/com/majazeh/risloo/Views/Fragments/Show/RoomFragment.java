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
import com.majazeh.risloo.Views.Adapters.Recycler.Cases2Adapter;
import com.majazeh.risloo.databinding.FragmentRoomBinding;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoomFragment extends Fragment {

    // Binding
    private FragmentRoomBinding binding;

    // Adapters
    private Cases2Adapter cases2Adapter;

    // Objects
    private LinearLayoutManager layoutManager;

    // Widgets
    private CircleImageView avatarCircleImageView;
    private TextView charTextView;
    private TextView nameTextView;
    private ImageView badgeImageView;
    private ImageView usersImageView;
    private TextView casesTitleTextView, casesCountTextView;
    private EditText casesSearchEditText;
    private ProgressBar casesSearchProgressBar;
    private ImageView casesAddImageView;
    private ShimmerFrameLayout casesShimmerLayout;
    private ConstraintLayout casesConstraintLayout;
    private RecyclerView casesRecyclerView;
    private TextView casesEmptyTextView;
    private ProgressBar casesProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        ((MainActivity) requireActivity()).handler.postDelayed(() -> {
            casesShimmerLayout.setVisibility(View.GONE);
            casesConstraintLayout.setVisibility(View.VISIBLE);
        }, 2000);

        return binding.getRoot();
    }

    private void initializer() {
        cases2Adapter = new Cases2Adapter(getActivity());

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        avatarCircleImageView = binding.fragmentRoomAvatarCircleImageView.componentAvatar86sdpBorderWhiteCircleImageView;

        charTextView = binding.fragmentRoomAvatarCircleImageView.componentAvatar86sdpBorderWhiteTextView;

        nameTextView = binding.fragmentRoomNameTextView;

        badgeImageView = binding.fragmentRoomBadgeImageView;

        usersImageView = binding.fragmentRoomUsersImageView.componentButtonOval28sdp;
        InitManager.imgResTint(getActivity(), usersImageView, R.drawable.ic_users_light, R.color.Blue600);

        casesTitleTextView = binding.fragmentRoomCasesHeaderConstraintLayout.componentIndexHeaderTitleTextView;
        casesTitleTextView.setText(getResources().getString(R.string.RoomFragmentCasesHeader));
        casesCountTextView = binding.fragmentRoomCasesHeaderConstraintLayout.componentIndexHeaderCountTextView;

        casesSearchEditText = binding.fragmentRoomCasesSearchConstraintLayout.componentIndexSearchEditText;
        casesSearchProgressBar = binding.fragmentRoomCasesSearchConstraintLayout.componentIndexSearchProgressBar;

        casesAddImageView = binding.fragmentRoomCasesAddImageView.componentButtonOval28sdp;
        InitManager.imgResTint(getActivity(), casesAddImageView, R.drawable.ic_plus_light, R.color.Green700);

        casesShimmerLayout = binding.fragmentRoomCasesIndexShimmerLayout.componentShimmerCase2;
        casesConstraintLayout = binding.fragmentRoomCasesIndexConstraintLayout.componentSingleCase2;

        casesRecyclerView = binding.fragmentRoomCasesIndexConstraintLayout.componentSingleCase2RecyclerView;
        casesRecyclerView.addItemDecoration(new ItemDecorateRecyclerView("verticalLayout", (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._12sdp), (int) getResources().getDimension(R.dimen._6sdp), (int) getResources().getDimension(R.dimen._12sdp)));
        casesRecyclerView.setLayoutManager(layoutManager);
        casesRecyclerView.setNestedScrollingEnabled(false);
        casesRecyclerView.setHasFixedSize(true);

        casesEmptyTextView = binding.fragmentRoomCasesIndexConstraintLayout.componentSingleCase2TextView;
        casesProgressBar = binding.fragmentRoomCasesIndexConstraintLayout.componentSingleCase2ProgressBar;
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
            ((MainActivity) requireActivity()).handler.postDelayed(() -> avatarCircleImageView.setClickable(true), 300);

            if (!((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
                IntentManager.display(getActivity(), "", "", ((MainActivity) requireActivity()).singleton.getAvatar());
            }
        });

        usersImageView.setOnClickListener(v -> {
            usersImageView.setClickable(false);
            ((MainActivity) requireActivity()).handler.postDelayed(() -> usersImageView.setClickable(true), 300);

            // TODO : Call Work Method
        });

        casesSearchEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!casesSearchEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(getActivity(), casesSearchEditText);
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
                ((MainActivity) requireActivity()).handler.removeCallbacksAndMessages(null);
                ((MainActivity) requireActivity()).handler.postDelayed(() -> {
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
            ((MainActivity) requireActivity()).handler.postDelayed(() -> casesAddImageView.setClickable(true), 300);

            ((MainActivity) requireActivity()).navigator(R.id.createCaseFragment);
        });
    }

    private void setData() {
        if (((MainActivity) requireActivity()).singleton.getName().equals("")) {
            nameTextView.setText(getResources().getString(R.string.MainToolbar));
        } else {
            nameTextView.setText(((MainActivity) requireActivity()).singleton.getName());
        }

        if (((MainActivity) requireActivity()).singleton.getAvatar().equals("")) {
            charTextView.setVisibility(View.VISIBLE);
            charTextView.setText(StringManager.firstChars(nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(avatarCircleImageView);
        } else {
            charTextView.setVisibility(View.GONE);

            Picasso.get().load(((MainActivity) requireActivity()).singleton.getAvatar()).placeholder(R.color.Gray50).into(avatarCircleImageView);
        }

        //        cases2Adapter.setCase(null);
        casesRecyclerView.setAdapter(cases2Adapter);

        String dataSize = "15";
        casesCountTextView.setText("(" + dataSize + ")");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}