package com.majazeh.risloo.views.fragments.test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.AnimateManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityTest;
import com.majazeh.risloo.databinding.FragmentTestEndBinding;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.ChainModel;
import com.mre.ligheh.Model.TypeModel.FormModel;
import com.mre.ligheh.Model.TypeModel.SampleModel;

public class FragmentTestEnd extends Fragment {

    // Binding
    private FragmentTestEndBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTestEndBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        setAnimation();

        return binding.getRoot();
    }

    private void initializer() {
        binding.titleTextView.getRoot().setText(getResources().getString(R.string.EndFragmentTitle));
        binding.descriptionTextView.getRoot().setText(getResources().getString(R.string.EndFragmentDescription));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> ((ActivityTest) requireActivity()).closeSample()).widget(binding.endTextView.getRoot());
    }

    private void setArgs() {
        SampleModel sampleModel = ((ActivityTest) requireActivity()).sampleModel;
        setData(sampleModel);
    }

    private void setData(SampleModel model) {
        FormModel formModel = model.getSampleForm().getModel("زنجیره");

        if (formModel == null) {
            binding.endTextView.getRoot().setText(getResources().getString(R.string.EndFragmentButtonDone));
        } else {
            List items = (List) formModel.getObject();

            for (int i = 0; i < items.data().size(); i++) {
                ChainModel chainModel = (ChainModel) items.data().get(i);

                if ((chainModel.getStatus().equals("seald") || chainModel.getStatus().equals("open")) && i != items.data().size()) {
                    binding.endTextView.getRoot().setText(getResources().getString(R.string.EndFragmentButtonNext));
                    break;
                } else {
                    binding.endTextView.getRoot().setText(getResources().getString(R.string.EndFragmentButtonChain));
                }
            }
        }
    }

    private void setAnimation() {
        AnimateManager.animateViewAlpha(binding.titleTextView.getRoot(), 500, 0f, 1f);
        AnimateManager.animateViewAlpha(binding.descriptionTextView.getRoot(), 500, 0f, 1f);
        AnimateManager.animateViewAlpha(binding.endTextView.getRoot(), 500, 0f, 1f);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}