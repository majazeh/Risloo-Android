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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.DocumentsAdapter;

public class DocumentsFragment extends Fragment {

    // Adapters
    private DocumentsAdapter documentsAdapter;

    // Objects
    private LinearLayoutManager layoutManager;

    // Widgets
    private TextView documentsTitleTextView, documentsCountTextView;
    private EditText documentsSearchEditText;
    private ProgressBar documentsSearchProgressBar;
    private ImageView documentsAddImageView;
    private ShimmerFrameLayout documentsShimmerLayout;
    private View documentsShimmerTopView;
    private ConstraintLayout documentsHeaderLayout, documentsConstraintLayout;
    private RecyclerView documentsRecyclerView;
    private TextView documentsEmptyTextView;
    private ProgressBar documentsProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_documents, viewGroup, false);

        initializer(view);

        detector();

        listener();

        setData();

        ((MainActivity) getActivity()).handler.postDelayed(() -> {
            documentsShimmerLayout.setVisibility(View.GONE);
            documentsHeaderLayout.setVisibility(View.VISIBLE);
            documentsConstraintLayout.setVisibility(View.VISIBLE);
        }, 5000);

        return view;
    }

    private void initializer(View view) {
        documentsAdapter = new DocumentsAdapter(getActivity());

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        documentsTitleTextView = view.findViewById(R.id.component_index_header_title_textView);
        documentsTitleTextView.setText(getResources().getString(R.string.DocumentsFragmentTitle));
        documentsCountTextView = view.findViewById(R.id.component_index_header_count_textView);

        documentsSearchEditText = view.findViewById(R.id.component_input_search_editText);

        documentsSearchProgressBar = view.findViewById(R.id.component_input_search_progressBar);

        documentsAddImageView = view.findViewById(R.id.fragment_documents_add_imageView);
        documentsAddImageView.setImageResource(R.drawable.ic_plus_light);
        ImageViewCompat.setImageTintList(documentsAddImageView, AppCompatResources.getColorStateList(getActivity(), R.color.Green700));

        documentsShimmerLayout = view.findViewById(R.id.fragment_documents_index_shimmerLayout);
        documentsShimmerTopView = view.findViewById(R.id.shimmer_item_document_top_view);
        documentsShimmerTopView.setVisibility(View.GONE);

        documentsHeaderLayout = view.findViewById(R.id.fragment_documents_index_headerLayout);
        documentsConstraintLayout = view.findViewById(R.id.fragment_documents_index_constraintLayout);

        documentsRecyclerView = view.findViewById(R.id.component_index_document_recyclerView);
        documentsRecyclerView.addItemDecoration(new ItemDecorateRecyclerView("verticalLayout", 0,0, 0, 0));
        documentsRecyclerView.setLayoutManager(layoutManager);
        documentsRecyclerView.setNestedScrollingEnabled(false);
        documentsRecyclerView.setHasFixedSize(true);

        documentsEmptyTextView = view.findViewById(R.id.component_index_document_textView);

        documentsProgressBar = view.findViewById(R.id.component_index_document_progressBar);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            documentsAddImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            documentsAddImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
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
    }

    private void setData() {
//        documentsAdapter.setDocument(null);
        documentsRecyclerView.setAdapter(documentsAdapter);

        String dataSize = "15";
        documentsCountTextView.setText("(" + dataSize + ")");
    }

}