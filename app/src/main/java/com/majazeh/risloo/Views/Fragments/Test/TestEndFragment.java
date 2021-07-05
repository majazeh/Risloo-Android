package com.majazeh.risloo.Views.Fragments.Test;

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
import com.majazeh.risloo.Views.Activities.TestActivity;
import com.majazeh.risloo.databinding.FragmentTestEndBinding;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.ChainModel;
import com.mre.ligheh.Model.TypeModel.FormModel;
import com.mre.ligheh.Model.TypeModel.SampleModel;

public class TestEndFragment extends Fragment {

    // Binding
    private FragmentTestEndBinding binding;

    // Vars
    private SampleModel sampleModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTestEndBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        binding.titleTextView.getRoot().setText(getResources().getString(R.string.EndFragmentTitle));
        binding.descriptionTextView.getRoot().setText(getResources().getString(R.string.EndFragmentDescription));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.endTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> ((TestActivity) requireActivity()).closeSample()).widget(binding.endTextView);
    }

    private void setArgs() {
        sampleModel = ((TestActivity) requireActivity()).sampleModel;

        setData(sampleModel);
    }

    private void setData(SampleModel model) {
        FormModel form = model.getSampleForm().getModel("زنجیره");

        if (form == null)
            binding.endTextView.setText(getResources().getString(R.string.EndFragmentButtonSample));
        else {
            List chains = (List) form.getObject();

            for (int i = 0; i < chains.data().size(); i++) {
                ChainModel chainModel = (ChainModel) chains.data().get(i);

                if ((chainModel.getStatus().equals("seald") || chainModel.getStatus().equals("open")) && i != chains.data().size()) {
                    binding.endTextView.setText(getResources().getString(R.string.EndFragmentButtonNext));
                    break;
                } else {
                    binding.endTextView.setText(getResources().getString(R.string.EndFragmentButtonChain));
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}