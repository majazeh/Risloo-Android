package com.majazeh.risloo.Views.Fragments.Test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.AnimateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.TestActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Test.TestPictoralAdapter;
import com.majazeh.risloo.databinding.FragmentTestPictoralBinding;
import com.mre.ligheh.Model.TypeModel.FormModel;
import com.mre.ligheh.Model.TypeModel.ItemModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

public class TestPictoralFragment extends Fragment {

    // Binding
    private FragmentTestPictoralBinding binding;

    // Adapters
    private TestPictoralAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTestPictoralBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        setAnimation();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new TestPictoralAdapter(requireActivity());

        InitManager.fixedGridRecyclerView(requireActivity(), binding.listRecyclerView.getRoot(), getResources().getDimension(R.dimen._16sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void setArgs() {
        FormModel formModel = ((TestActivity) requireActivity()).formModel;
        setData(formModel);
    }

    private void setData(FormModel model) {
        try {
            ItemModel item = (ItemModel) model.getObject();

            if (item.getCategory() != null && !item.getCategory().equals("")) {
                binding.entityTextView.getRoot().setText(item.getCategory());
                binding.entityConstraintLayout.setVisibility(View.VISIBLE);
            } else {
                binding.entityConstraintLayout.setVisibility(View.GONE);
            }

            if (item.getImageUrl() != null && !item.getImageUrl().equals("")) {
                Picasso.get().load(item.getImageUrl()).placeholder(R.color.CoolGray100).into(binding.questionImageView.getRoot());
            } else {
                Picasso.get().load(R.color.CoolGray100).placeholder(R.color.CoolGray100).into(binding.questionImageView.getRoot());
            }

            ArrayList<String> pics = new ArrayList<>();
            for (int i = 0; i < item.getAnswer().getOptions().length(); i++) {
                pics.add(item.getAnswer().getOptions().get(i).toString());
            }

            if (pics.size() != 0) {
                adapter.setItems(pics, item.getUserAnswered(), item.getIndex());
                binding.listRecyclerView.getRoot().setAdapter(adapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAnimation() {
        AnimateManager.animateViewAlpha(binding.entityTextView.getRoot(), 500, 0f, 1f);
        AnimateManager.animateViewAlpha(binding.questionImageView.getRoot(), 500, 0f, 1f);
        AnimateManager.animateViewAlpha(binding.listRecyclerView.getRoot(), 500, 0f, 1f);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}