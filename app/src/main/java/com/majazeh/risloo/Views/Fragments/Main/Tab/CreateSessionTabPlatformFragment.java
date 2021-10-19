package com.majazeh.risloo.Views.Fragments.Main.Tab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.TabPlatformsAdapter;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateSessionFragment;
import com.majazeh.risloo.databinding.FragmentCreateSessionTabPlatformBinding;
import com.mre.ligheh.Model.TypeModel.CaseModel;

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

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new TabPlatformsAdapter(requireActivity());

        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.platformsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateSessionTabPlatformButton), getResources().getColor(R.color.White), R.drawable.draw_16sdp_solid_lightblue500_ripple_lightblue800);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            if (current instanceof CreateSessionFragment)
                ((CreateSessionFragment) current).checkRequire();
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (current instanceof CreateSessionFragment) {
            CaseModel model = ((CreateSessionFragment) current).caseModel;

            if (model.getCaseRoom() != null && !model.getCaseRoom().getSession_platforms().data().isEmpty()) {
                adapter.setItems(model.getCaseRoom().getSession_platforms().data());
                binding.platformsSingleLayout.recyclerView.setAdapter(adapter);

                binding.platformsSingleLayout.emptyView.setVisibility(View.GONE);
            } else if (adapter.getItemCount() == 0) {
                binding.platformsSingleLayout.emptyView.setVisibility(View.VISIBLE);
                binding.platformsSingleLayout.emptyView.setText(getResources().getString(R.string.TabPlatformsAdapterEmpty));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}