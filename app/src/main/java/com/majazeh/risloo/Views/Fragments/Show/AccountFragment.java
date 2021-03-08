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
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    // Objects
    private LinearLayoutManager layoutManager;

    // Widgets
    private CircleImageView avatarCircleImageView;
    private TextView charTextView;
    private TextView nameTextView, usernameTextView;
    private ImageView badgeImageView;
    private TextView educationTextView, birthdayTextView, emailTextView, mobileTextView;
    private ImageView educationImageView, birthdayImageView, emailImageView, mobileImageView;
    private TextView editTextView;
    private ImageView enterImageView;
    private TextView documentsTitleTextView, documentsCountTextView;
    private EditText documentsSearchEditText;
    private ProgressBar documentsSearchProgressBar;
    private ImageView documentsAddImageView;
    private SwipeRefreshLayout documentsSwipeRefreshLayout;
    private ShimmerFrameLayout documentsShimmerLayout;
    private RecyclerView documentsRecyclerView;
    private TextView documentsEmptyTextView;
    private ProgressBar documentsProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, viewGroup, false);

        initializer(view);

        detector();

        listener();

        setData();

        ((MainActivity) getActivity()).handler.postDelayed(() -> {
            documentsShimmerLayout.setVisibility(View.GONE);
            documentsRecyclerView.setVisibility(View.VISIBLE);
            documentsEmptyTextView.setVisibility(View.VISIBLE);
        }, 5000);

        return view;
    }

    private void initializer(View view) {
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        avatarCircleImageView = view.findViewById(R.id.component_main_avatar_circleImageView);

        charTextView = view.findViewById(R.id.component_main_avatar_textView);

        nameTextView = view.findViewById(R.id.fragment_account_name_textView);
        usernameTextView = view.findViewById(R.id.fragment_account_username_textView);

        badgeImageView = view.findViewById(R.id.fragment_account_badge_imageView);

        educationTextView = view.findViewById(R.id.fragment_account_education_textView);
        birthdayTextView = view.findViewById(R.id.fragment_account_birthday_textView);
        emailTextView = view.findViewById(R.id.fragment_account_email_textView);
        mobileTextView = view.findViewById(R.id.fragment_account_mobile_textView);

        educationImageView = view.findViewById(R.id.fragment_account_education_imageView);
        birthdayImageView = view.findViewById(R.id.fragment_account_birthday_imageView);
        emailImageView = view.findViewById(R.id.fragment_account_email_imageView);
        mobileImageView = view.findViewById(R.id.fragment_account_mobile_imageView);

        editTextView = view.findViewById(R.id.fragment_account_edit_textView);
        editTextView.setText(getResources().getString(R.string.AccountFragmentEdit));
        editTextView.setTextColor(getResources().getColor(R.color.Gray500));

        enterImageView = view.findViewById(R.id.fragment_account_enter_imageView);
        enterImageView.setImageResource(R.drawable.ic_user_cog_light);
        ImageViewCompat.setImageTintList(enterImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Blue600));

        documentsTitleTextView = view.findViewById(R.id.component_index_header_title_textView);
        documentsTitleTextView.setText(getResources().getString(R.string.AccountFragmentDocumentsHeader));
        documentsCountTextView = view.findViewById(R.id.component_index_header_count_textView);

        documentsSearchEditText = view.findViewById(R.id.component_input_search_editText);

        documentsSearchProgressBar = view.findViewById(R.id.component_input_search_progressBar);

        documentsAddImageView = view.findViewById(R.id.fragment_account_documents_add_imageView);
        documentsAddImageView.setImageResource(R.drawable.ic_plus_light);
        ImageViewCompat.setImageTintList(documentsAddImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Green700));

        documentsSwipeRefreshLayout = view.findViewById(R.id.component_index_list_swipeRefreshLayout);
        documentsSwipeRefreshLayout.setColorSchemeResources(R.color.Blue600);
        documentsSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.Gray100));

        documentsShimmerLayout = view.findViewById(R.id.component_index_list_shimmerFrameLayout);

        documentsRecyclerView = view.findViewById(R.id.component_index_list_recyclerView);
        documentsRecyclerView.addItemDecoration(new ItemDecorateRecyclerView("verticalLayout", 0, 0, 0, 0));
        documentsRecyclerView.setLayoutManager(layoutManager);
        documentsRecyclerView.setNestedScrollingEnabled(false);
        documentsRecyclerView.setHasFixedSize(true);

        documentsEmptyTextView = view.findViewById(R.id.component_index_list_textView);

        documentsProgressBar = view.findViewById(R.id.component_index_list_progressBar);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            editTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_gray500_ripple_gray300);

            enterImageView.setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_blue600_ripple_blue300);
            documentsAddImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            editTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_gray500);

            enterImageView.setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_blue600);
            documentsAddImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
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

        editTextView.setOnClickListener(v -> {
            editTextView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> editTextView.setClickable(true), 300);

            ((MainActivity) getActivity()).navigator(R.id.editAccountFragment);
        });

        enterImageView.setOnClickListener(v -> {
            enterImageView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> enterImageView.setClickable(true), 300);

            // TODO : Call Work Method
        });

        documentsSearchEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!documentsSearchEditText.hasFocus()) {
                    ((MainActivity) getActivity()).controlEditText.select(getActivity(), documentsSearchEditText);
                }
            }
            return false;
        });

        documentsSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((MainActivity) getActivity()).handler.removeCallbacksAndMessages(null);
                ((MainActivity) getActivity()).handler.postDelayed(() -> {
//                    if (documentsSearchEditText.length() != 0) {
//                        getData("getDocuments", "", documentsSearchEditText.getText().toString().trim());
//                    } else {
//                        documentsRecyclerView.setAdapter(null);
//
//                        if (documentsEmptyTextView.getVisibility() == View.VISIBLE) {
//                            documentsEmptyTextView.setVisibility(View.GONE);
//                        }
//                    }
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        documentsAddImageView.setOnClickListener(v -> {
            documentsAddImageView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> documentsAddImageView.setClickable(true), 300);

            ((MainActivity) getActivity()).navigator(R.id.createDocumentFragment);
        });

        documentsSwipeRefreshLayout.setOnRefreshListener(() -> {
            documentsSwipeRefreshLayout.setRefreshing(false);

            // TODO : Call Work Method
        });
    }

    private void setData() {
        if (((MainActivity) getActivity()).singleton.getName().equals("")) {
            nameTextView.setText(getResources().getString(R.string.MainToolbar));
        } else {
            nameTextView.setText(((MainActivity) getActivity()).singleton.getName());
        }

        if (((MainActivity) getActivity()).singleton.getUsername().equals("")) {
            usernameTextView.setVisibility(View.GONE);
        } else {
            usernameTextView.setText(((MainActivity) getActivity()).singleton.getUsername());
        }

        if (((MainActivity) getActivity()).singleton.getEducation().equals("")) {
            educationTextView.setVisibility(View.GONE);
            educationImageView.setVisibility(View.GONE);
        } else {
            educationTextView.setText(((MainActivity) getActivity()).singleton.getEducation());
        }

        if (((MainActivity) getActivity()).singleton.getBirthday().equals("")) {
            birthdayTextView.setVisibility(View.GONE);
            birthdayImageView.setVisibility(View.GONE);
        } else {
            birthdayTextView.setText(((MainActivity) getActivity()).singleton.getBirthday());
        }

        if (((MainActivity) getActivity()).singleton.getEmail().equals("")) {
            emailTextView.setVisibility(View.GONE);
            emailImageView.setVisibility(View.GONE);
        } else {
            emailTextView.setText(((MainActivity) getActivity()).singleton.getEmail());
        }

        if (((MainActivity) getActivity()).singleton.getMobile().equals("")) {
            mobileTextView.setVisibility(View.GONE);
            mobileImageView.setVisibility(View.GONE);
        } else {
            mobileTextView.setText(((MainActivity) getActivity()).singleton.getMobile());
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
        documentsCountTextView.setText("(" + dataSize + ")");
    }

}