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
import com.majazeh.risloo.Views.Adapters.Recycler.Platforms2Adapter;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragment;
import com.majazeh.risloo.databinding.FragmentEditSessionPlatformBinding;
import com.mre.ligheh.Model.TypeModel.SessionModel;

public class EditSessionPlatformFragment extends Fragment {

    // Binding
    public FragmentEditSessionPlatformBinding binding;

    // Adapters
    public Platforms2Adapter adapter;

    // Fragments
    private Fragment current;

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
        adapter = new Platforms2Adapter(requireActivity());

        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.indexSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));

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
            if (current instanceof EditSessionFragment)
                ((EditSessionFragment) current).checkRequire();
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (current instanceof EditSessionFragment) {
            SessionModel model = ((EditSessionFragment) current).sessionModel;

            if (!model.getSession_platforms().data().isEmpty()) {
                adapter.setPlatforms(model.getSession_platforms().data());
                binding.indexSingleLayout.recyclerView.setAdapter(adapter);

                binding.indexSingleLayout.emptyView.setVisibility(View.GONE);
            } else if (adapter.getItemCount() == 0) {
                binding.indexSingleLayout.emptyView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}