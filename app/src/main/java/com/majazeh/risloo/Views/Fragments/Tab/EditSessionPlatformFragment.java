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
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragment;
import com.majazeh.risloo.databinding.FragmentEditSessionPlatformBinding;

public class EditSessionPlatformFragment extends Fragment {

    // Binding
    public FragmentEditSessionPlatformBinding binding;

    // Adapters
    public TabPlatformsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditSessionPlatformBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new TabPlatformsAdapter(requireActivity());

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.platformRecyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), 0);

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditSessionPlatformTabButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            doWork();
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        adapter.setPlatforms(null);
        binding.platformRecyclerView.setAdapter(adapter);
    }

    private void setRecyclerView() {
        // TODO : Place Code When Needed
    }

    private void doWork() {
        Fragment current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        if (current instanceof EditSessionFragment)
            ((EditSessionFragment) current).doWork();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}