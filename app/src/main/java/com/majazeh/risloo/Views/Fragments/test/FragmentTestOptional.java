package com.majazeh.risloo.views.fragments.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.AnimateManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.views.activities.ActivityTest;
import com.majazeh.risloo.views.adapters.recycler.test.TestOptionalAdapter;
import com.majazeh.risloo.databinding.FragmentTestOptionalBinding;
import com.mre.ligheh.Model.TypeModel.FormModel;
import com.mre.ligheh.Model.TypeModel.ItemModel;

import org.json.JSONException;

import java.util.ArrayList;

public class FragmentTestOptional extends Fragment {

    // Binding
    private FragmentTestOptionalBinding binding;

    // Adapters
    private TestOptionalAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTestOptionalBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        setAnimation();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new TestOptionalAdapter(requireActivity());

        InitManager.rvVerticalFixedUnnested(requireActivity(), binding.listRecyclerView.getRoot(), getResources().getDimension(R.dimen._16sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void setArgs() {
        FormModel formModel = ((ActivityTest) requireActivity()).formModel;
        setData(formModel);
    }

    private void setData(FormModel model) {
        try {
            ItemModel item = (ItemModel) model.getObject();

            if (!item.getCategory().equals("")) {
                binding.entityTextView.getRoot().setText(item.getCategory());
                binding.entityConstraintLayout.setVisibility(View.VISIBLE);
            } else {
                binding.entityConstraintLayout.setVisibility(View.GONE);
            }

            if (!item.getText().equals("")) {
                binding.titleTextView.getRoot().setText(item.getText());
            } else {
                binding.titleTextView.getRoot().setText("نامعلوم");
            }

            if (!item.getDescription().equals("")) {
                binding.descriptionTextView.getRoot().setText(item.getDescription());
                binding.descriptionTextView.getRoot().setVisibility(View.VISIBLE);
            } else {
                binding.descriptionTextView.getRoot().setVisibility(View.GONE);
            }

            ArrayList<String> options = new ArrayList<>();
            for (int i = 0; i < item.getAnswer().getOptions().length(); i++) {
                options.add(item.getAnswer().getOptions().get(i).toString());
            }

            if (options.size() != 0) {
                adapter.setItems(options, item.getUserAnswered(), item.getIndex());
                binding.listRecyclerView.getRoot().setAdapter(adapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAnimation() {
        AnimateManager.animateViewAlpha(binding.entityTextView.getRoot(), 500, 0f, 1f);
        AnimateManager.animateViewAlpha(binding.titleTextView.getRoot(), 500, 0f, 1f);
        AnimateManager.animateViewAlpha(binding.descriptionTextView.getRoot(), 500, 0f, 1f);
        AnimateManager.animateViewAlpha(binding.listRecyclerView.getRoot(), 500, 0f, 1f);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}