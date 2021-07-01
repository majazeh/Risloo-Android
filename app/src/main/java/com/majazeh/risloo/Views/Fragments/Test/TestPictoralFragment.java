package com.majazeh.risloo.Views.Fragments.Test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.TestActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.PictoralsAdapter;
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
    private PictoralsAdapter adapter;

    // Vars
    public FormModel formModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentTestPictoralBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new PictoralsAdapter(requireActivity());

        InitManager.fixedGridRecyclerView(requireActivity(), binding.listRecyclerView, getResources().getDimension(R.dimen._16sdp), getResources().getDimension(R.dimen._12sdp), getResources().getDimension(R.dimen._4sdp), getResources().getDimension(R.dimen._12sdp));
    }

    private void setArgs() {
        formModel = ((TestActivity) requireActivity()).formModel;

        setData(formModel);
    }

    private void setData(FormModel model) {
        try {
            ItemModel item = (ItemModel) model.getObject();

            Picasso.get().load(item.getImage_url()).placeholder(R.color.Gray100).into(binding.questionImageView);

            ArrayList<String> images = new ArrayList<>();
            for (int i = 0; i < item.getAnswer().getAnswer().length(); i++) {
                images.add(item.getAnswer().getAnswer().get(i).toString());
            }

            if (images.size() != 0) {
                adapter.setItems(images, item.getUser_answered());
                binding.listRecyclerView.setAdapter(adapter);
            } else if (adapter.getItemCount() == 0) {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}