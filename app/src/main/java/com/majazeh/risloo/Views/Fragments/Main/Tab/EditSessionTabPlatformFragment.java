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
import com.majazeh.risloo.Views.Fragments.Main.Edit.EditSessionFragment;
import com.majazeh.risloo.databinding.FragmentEditSessionTabPlatformBinding;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class EditSessionTabPlatformFragment extends Fragment {

    // Binding
    public FragmentEditSessionTabPlatformBinding binding;

    // Adapters
    public TabPlatformsAdapter adapter;

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
        adapter = new TabPlatformsAdapter(requireActivity());

        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.platformsSingleLayout.recyclerView, getResources().getDimension(R.dimen._12sdp), 0, getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));

        InitManager.txtTextColorBackground(binding.editTextView.getRoot(), getResources().getString(R.string.EditSessionTabPlatformButton), getResources().getColor(R.color.White), R.drawable.draw_16sdp_solid_lightblue500_ripple_lightblue800);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            if (current instanceof EditSessionFragment)
                ((EditSessionFragment) current).checkRequire();
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (current instanceof EditSessionFragment) {
            SessionModel model = ((EditSessionFragment) current).sessionModel;

            if (!model.getSession_platforms().data().isEmpty()) {
                ArrayList<TypeModel> sessionPlatforms = model.getSession_platforms().data();

                for (int i = 0; i < sessionPlatforms.size(); i++) {
                    SessionPlatformModel spm = (SessionPlatformModel) sessionPlatforms.get(i);
                    spm.setSelected(true);
                    adapter.addItem(spm);
                }

                if (model.getRoom() != null && !model.getRoom().getSession_platforms().data().isEmpty()) {
                    ArrayList<TypeModel> roomPlatforms = model.getRoom().getSession_platforms().data();

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
            } else if (model.getRoom() != null && !model.getRoom().getSession_platforms().data().isEmpty()) {
                ArrayList<TypeModel> roomPlatforms = model.getRoom().getSession_platforms().data();

                for (int i = 0; i < roomPlatforms.size(); i++) {
                    SessionPlatformModel rpm = (SessionPlatformModel) roomPlatforms.get(i);
                    rpm.setSelected(false);
                    adapter.addItem(rpm);
                }

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