package com.majazeh.risloo.Views.Fragments.Index;

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
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.DocumentsAdapter;
import com.majazeh.risloo.databinding.FragmentDocumentsBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;

import java.util.HashMap;
import java.util.Objects;

public class DocumentsFragment extends Fragment {

    // Binding
    private FragmentDocumentsBinding binding;

    // Adapters
    private DocumentsAdapter adapter;

    // Objects
    private Handler handler;

    // Vars
    private HashMap data, header;
    private boolean isLoading = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentDocumentsBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new DocumentsAdapter(requireActivity());

        handler = new Handler();

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.DocumentsFragmentTitle));

        binding.indexShimmerLayout.shimmerItem1.topView.setVisibility(View.GONE);

        InitManager.imgResTint(requireActivity(), binding.addImageView.getRoot(), R.drawable.ic_plus_light, R.color.White);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.indexSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.addImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600_ripple_white);
        } else {
            binding.addImageView.getRoot().setBackgroundResource(R.drawable.draw_oval_solid_green600);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.searchIncludeLayout.editText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.searchIncludeLayout.editText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.searchIncludeLayout.editText);
                }
            }
            return false;
        });

        binding.searchIncludeLayout.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(() -> {
                    data.put("page", 1);
                    data.put("q", String.valueOf(s));

                    if (binding.searchIncludeLayout.progressBar.getVisibility() == View.GONE)
                        binding.searchIncludeLayout.progressBar.setVisibility(View.VISIBLE);

                    getData();
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.getRoot().setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (!isLoading) {
                if (!binding.getRoot().canScrollVertically(1)) {
                    isLoading = true;

                    if (data.containsKey("page"))
                        data.put("page", ((int) data.get("page")) + 1);
                    else
                        data.put("page", 1);

                    if (binding.indexSingleLayout.progressBar.getVisibility() == View.GONE)
                        binding.indexSingleLayout.progressBar.setVisibility(View.VISIBLE);

                    getData();
                }
            }
        });

        ClickManager.onClickListener(() -> {
            NavDirections action = DocumentsFragmentDirections.actionDocumentsFragmentToCreateDocumentFragment();
            ((MainActivity) requireActivity()).navController.navigate(action);
        }).widget(binding.addImageView.getRoot());
    }

    private void getData() {
//        Document.list(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                List documents = (List) object;
//
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        if (Objects.equals(data.get("page"), 1))
//                            adapter.clearDocuments();
//
//                        if (!documents.data().isEmpty()) {
//                            adapter.setDocuments(documents.data());
//                            binding.indexSingleLayout.recyclerView.setAdapter(adapter);
//
//                            binding.indexHeaderLayout.getRoot().setVisibility(View.VISIBLE);
//                            binding.indexSingleLayout.textView.setVisibility(View.GONE);
//                        } else if (adapter.getItemCount() == 0) {
                            binding.indexHeaderLayout.getRoot().setVisibility(View.GONE);
                            binding.indexSingleLayout.textView.setVisibility(View.VISIBLE);
//                        }
//                        binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(adapter.getItemCount()));

                        binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.indexShimmerLayout.getRoot().stopShimmer();

//                        if (binding.indexSingleLayout.progressBar.getVisibility() == View.VISIBLE)
//                            binding.indexSingleLayout.progressBar.setVisibility(View.GONE);
//                        if (binding.searchIncludeLayout.progressBar.getVisibility() == View.VISIBLE)
//                            binding.searchIncludeLayout.progressBar.setVisibility(View.GONE);
//                    });
//                    isLoading = false;
//                }
//            }
//
//            @Override
//            public void onFailure(String response) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        binding.indexHeaderLayout.getRoot().setVisibility(View.VISIBLE);
//                        binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
//                        binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
//                        binding.indexShimmerLayout.getRoot().stopShimmer();
//
//                        if (binding.indexSingleLayout.progressBar.getVisibility() == View.VISIBLE)
//                            binding.indexSingleLayout.progressBar.setVisibility(View.GONE);
//                        if (binding.searchIncludeLayout.progressBar.getVisibility() == View.VISIBLE)
//                            binding.searchIncludeLayout.progressBar.setVisibility(View.GONE);
//                    });
//                    isLoading = false;
//                }
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        handler.removeCallbacksAndMessages(null);
    }

}