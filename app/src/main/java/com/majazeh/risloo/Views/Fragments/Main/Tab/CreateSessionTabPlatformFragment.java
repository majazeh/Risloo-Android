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
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Create.CreatePlatformAdapter;
import com.majazeh.risloo.Views.Fragments.Main.Create.CreateSessionFragment;
import com.majazeh.risloo.databinding.FragmentCreateSessionTabPlatformBinding;
import com.mre.ligheh.Model.TypeModel.CaseModel;

import java.util.HashMap;

public class CreateSessionTabPlatformFragment extends Fragment {

    // Binding
    private FragmentCreateSessionTabPlatformBinding binding;

    // Adapters
    private CreatePlatformAdapter adapter;

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
        adapter = new CreatePlatformAdapter(requireActivity());

        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.platformsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateSessionTabPlatformButton), getResources().getColor(R.color.White), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
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

            if (model.getCaseRoom() != null && !model.getCaseRoom().getSessionPlatforms().data().isEmpty()) {
                adapter.setItems(model.getCaseRoom().getSessionPlatforms().data());
                binding.platformsSingleLayout.recyclerView.setAdapter(adapter);

                binding.platformsSingleLayout.emptyView.setVisibility(View.GONE);
            } else if (adapter.getItemCount() == 0) {
                binding.platformsSingleLayout.recyclerView.setAdapter(null);

                binding.platformsSingleLayout.emptyView.setVisibility(View.VISIBLE);
                binding.platformsSingleLayout.emptyView.setText(getResources().getString(R.string.CreatePlatformAdapterEmpty));
            }
        }
    }

    public void setHashmap(HashMap data) {
        if (!adapter.platforms.isEmpty())
            data.put("platforms", adapter.platforms);
        else
            data.remove("platforms");

        if (!adapter.pinPlatform.isEmpty())
            data.put("pin_platform", adapter.pinPlatform);
        else
            data.remove("pin_platform");

        if (!adapter.identifierPlatform.isEmpty())
            data.put("identifier_platform", adapter.identifierPlatform);
        else
            data.remove("identifier_platform");
    }

    public void hideValid() {
        if (binding.platformsErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((MainActivity) requireActivity()).validatoon.hideValid(binding.platformsErrorLayout.getRoot(), binding.platformsErrorLayout.errorTextView);

        if (binding.pinPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((MainActivity) requireActivity()).validatoon.hideValid(binding.pinPlatformErrorLayout.getRoot(), binding.pinPlatformErrorLayout.errorTextView);

        if (binding.identifierPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((MainActivity) requireActivity()).validatoon.hideValid(binding.identifierPlatformErrorLayout.getRoot(), binding.identifierPlatformErrorLayout.errorTextView);
    }

    public void showValid(String key, String validation) {
        switch (key) {
            case "platforms":
                ((MainActivity) requireActivity()).validatoon.showValid(binding.platformsErrorLayout.getRoot(), binding.platformsErrorLayout.errorTextView, validation);
                break;
            case "pin_platform":
                ((MainActivity) requireActivity()).validatoon.showValid(binding.pinPlatformErrorLayout.getRoot(), binding.pinPlatformErrorLayout.errorTextView, validation);
                break;
            case "identifier_platform":
                ((MainActivity) requireActivity()).validatoon.showValid(binding.identifierPlatformErrorLayout.getRoot(), binding.identifierPlatformErrorLayout.errorTextView, validation);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}