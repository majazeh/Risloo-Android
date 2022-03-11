package com.majazeh.risloo.views.fragments.main.tab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.main.Create.CreatePlatformAdapter;
import com.majazeh.risloo.views.fragments.main.edit.FragmentEditSession;
import com.majazeh.risloo.databinding.FragmentEditSessionTabPlatformBinding;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentEditSessionTabPlatform extends Fragment {

    // Binding
    private FragmentEditSessionTabPlatformBinding binding;

    // Adapters
    private CreatePlatformAdapter adapter;

    // Fragments
    private Fragment current;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditSessionTabPlatformBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new CreatePlatformAdapter(requireActivity());

        current = ((ActivityMain) requireActivity()).fragmont.getCurrent();

        InitManager.rcvVerticalFixedUnnested(requireActivity(), binding.platformsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));

        InitManager.txtTextColorBackground(binding.editTextView.getRoot(), getResources().getString(R.string.EditSessionTabPlatformButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            if (current instanceof FragmentEditSession)
                ((FragmentEditSession) current).checkRequire();
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (current instanceof FragmentEditSession) {
            SessionModel model = ((FragmentEditSession) current).sessionModel;

            if (!model.getSessionPlatforms().data().isEmpty()) {
                ArrayList<TypeModel> sessionPlatforms = model.getSessionPlatforms().data();

                for (int i = 0; i < sessionPlatforms.size(); i++) {
                    SessionPlatformModel spm = (SessionPlatformModel) sessionPlatforms.get(i);
                    spm.setSelected(true);
                    adapter.addItem(spm);
                }

                if (model.getRoom() != null && !model.getRoom().getSessionPlatforms().data().isEmpty()) {
                    ArrayList<TypeModel> roomPlatforms = model.getRoom().getSessionPlatforms().data();

                    for (int i = 0; i < roomPlatforms.size(); i++) {
                        SessionPlatformModel rpm = (SessionPlatformModel) roomPlatforms.get(i);
                        for (int j = 0; j < sessionPlatforms.size(); j++) {
                            SessionPlatformModel spm = (SessionPlatformModel) sessionPlatforms.get(j);
                            if (j == sessionPlatforms.size() - 1 && !rpm.getId().equals(spm.getId())) {
                                rpm.setSelected(false);
                                adapter.addItem(rpm);
                            } else if (rpm.getId().equals(spm.getId())) {
                                break;
                            }
                        }
                    }
                }

                binding.platformsSingleLayout.recyclerView.setAdapter(adapter);
                binding.platformsSingleLayout.emptyView.setVisibility(View.GONE);
            } else if (model.getRoom() != null && !model.getRoom().getSessionPlatforms().data().isEmpty()) {
                ArrayList<TypeModel> roomPlatforms = model.getRoom().getSessionPlatforms().data();

                for (int i = 0; i < roomPlatforms.size(); i++) {
                    SessionPlatformModel rpm = (SessionPlatformModel) roomPlatforms.get(i);
                    rpm.setSelected(false);
                    adapter.addItem(rpm);
                }

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
            ((ActivityMain) requireActivity()).validatoon.hideValid(binding.platformsErrorLayout.getRoot(), binding.platformsErrorLayout.errorTextView);

        if (binding.pinPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((ActivityMain) requireActivity()).validatoon.hideValid(binding.pinPlatformErrorLayout.getRoot(), binding.pinPlatformErrorLayout.errorTextView);

        if (binding.identifierPlatformErrorLayout.getRoot().getVisibility() == View.VISIBLE)
            ((ActivityMain) requireActivity()).validatoon.hideValid(binding.identifierPlatformErrorLayout.getRoot(), binding.identifierPlatformErrorLayout.errorTextView);
    }

    public void showValid(String key, String validation) {
        switch (key) {
            case "platforms":
                ((ActivityMain) requireActivity()).validatoon.showValid(binding.platformsErrorLayout.getRoot(), binding.platformsErrorLayout.errorTextView, validation);
                break;
            case "pin_platform":
                ((ActivityMain) requireActivity()).validatoon.showValid(binding.pinPlatformErrorLayout.getRoot(), binding.pinPlatformErrorLayout.errorTextView, validation);
                break;
            case "identifier_platform":
                ((ActivityMain) requireActivity()).validatoon.showValid(binding.identifierPlatformErrorLayout.getRoot(), binding.identifierPlatformErrorLayout.errorTextView, validation);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}