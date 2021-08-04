package com.majazeh.risloo.Views.Fragments.Tab;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.TabPlatformsAdapter;
import com.majazeh.risloo.Views.Fragments.Create.CreateSessionFragment;
import com.majazeh.risloo.databinding.FragmentCreateSessionTabPlatformBinding;

public class CreateSessionTabPlatformFragment extends Fragment {

    // Binding
    public FragmentCreateSessionTabPlatformBinding binding;

    // Adapters
    public TabPlatformsAdapter adapter;

    // Fragments
    private Fragment current;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateSessionTabPlatformBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new TabPlatformsAdapter(requireActivity());

        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.indexSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateSessionTabPlatformButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            if (current instanceof CreateSessionFragment)
                ((CreateSessionFragment) current).checkRequire();
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (current instanceof CreateSessionFragment) {
//            SessionModel model = ((CreateSessionFragment) current).sessionModel;
//
//            if (!model.getSession_platforms().data().isEmpty()) {
//                adapter.setPlatforms(model.getSession_platforms().data());
//                binding.indexSingleLayout.recyclerView.setAdapter(adapter);
//
//                binding.indexSingleLayout.textView.setVisibility(View.GONE);
//            } else if (adapter.getItemCount() == 0) {
//                binding.indexSingleLayout.textView.setVisibility(View.VISIBLE);
//            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}