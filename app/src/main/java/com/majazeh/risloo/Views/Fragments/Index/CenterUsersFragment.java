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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.CenterUsersAdapter;
import com.majazeh.risloo.databinding.FragmentCenterUsersBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.List;

import java.util.HashMap;

public class CenterUsersFragment extends Fragment {

    // Binding
    private FragmentCenterUsersBinding binding;

    // Adapters
    private CenterUsersAdapter adapter;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager layoutManager;
    private Handler handler;

    private HashMap data = new HashMap();
    private HashMap header = new HashMap();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCenterUsersBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new CenterUsersAdapter(requireActivity());

        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", 0, 0, 0, 0);

        layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        handler = new Handler();

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.CenterUsersFragmentTitle));

        binding.indexShimmerLayout.shimmerItem1.topView.setVisibility(View.GONE);

        InitManager.imgResTint(requireActivity(), binding.addImageView.getRoot(), R.drawable.ic_plus_light, R.color.Green700);
        InitManager.recyclerView(binding.indexSingleLayout.recyclerView, itemDecoration, layoutManager);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.addImageView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            binding.addImageView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
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
                    data.put("q", String.valueOf(s));
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ClickManager.onClickListener(() -> {
            Bundle extras = new Bundle();
            extras.putString("id", requireArguments().getString("id"));
            ((MainActivity) requireActivity()).navigator(R.id.createCenterUserFragment,extras);
        }).widget(binding.addImageView.getRoot());
    }

    private void setData() {
        data.put("id", requireArguments().getString("id"));
        header.put("Authorization", "Bearer " + ((MainActivity) requireActivity()).singleton.getToken());
        Center.users(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded())
                    requireActivity().runOnUiThread(() -> {
                        List users = (List) object;
                        adapter.setUsers(users.data());
                        binding.indexSingleLayout.recyclerView.setAdapter(adapter);
                        binding.headerIncludeLayout.countTextView.setText("(" + adapter.getItemCount() + ")");

                        binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
                        binding.indexHeaderLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);

                    });

            }

            @Override
            public void onFailure(String response) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}