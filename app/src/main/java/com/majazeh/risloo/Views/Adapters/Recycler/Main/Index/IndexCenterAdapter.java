package com.majazeh.risloo.views.adapters.recycler.main.Index;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.widgets.interfaces.DiffUtilTypeModelAdapter;
import com.majazeh.risloo.utils.widgets.classes.DiffUtilTypeModelCallback;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.holder.main.Index.IndexCenterHolder;
import com.majazeh.risloo.databinding.SingleItemIndexCenterBinding;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

public class IndexCenterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DiffUtilTypeModelAdapter {

    // Activity
    private final Activity activity;

    // Differ
    private final AsyncListDiffer<TypeModel> differ;

    public IndexCenterAdapter(@NonNull Activity activity) {
        this.activity = activity;

        differ = new AsyncListDiffer<>(this, new DiffUtilTypeModelCallback(this));
    }

    @NonNull
    @Override
    public IndexCenterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IndexCenterHolder(SingleItemIndexCenterBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        CenterModel model = (CenterModel) differ.getCurrentList().get(i);

        listener((IndexCenterHolder) holder, model);

        setData((IndexCenterHolder) holder, model);
    }

    @Override
    public int getItemCount() {
        if (this.differ.getCurrentList() != null)
            return differ.getCurrentList().size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items) {
        differ.submitList(items);
    }

    public void clearItems() {
        differ.submitList(null);
    }

    private void listener(IndexCenterHolder holder, CenterModel model) {
        CustomClickView.onClickListener(() -> {
            if (model.getType().equals("counseling_center"))
                ((ActivityMain) activity).navigatoon.navigateToFragmentCenter(model);
            else
                ((ActivityMain) activity).navigatoon.navigateToFragmentRoom(model);

        }).widget(holder.binding.getRoot());
    }

    private void setData(IndexCenterHolder holder, CenterModel model) {
        try {
            if (model.getType().equals("counseling_center")) {

                if (model.getDetail().has("title") && !model.getDetail().isNull("title") && !model.getDetail().getString("title").equals(""))
                    holder.binding.nameTextView.setText(model.getDetail().getString("title"));
                else if (!model.getId().equals(""))
                    holder.binding.nameTextView.setText(model.getId());
                else
                    holder.binding.nameTextView.setText(activity.getResources().getString(R.string.AppDefaultUnknown));

                if (model.getManager() != null && !model.getManager().getName().equals(""))
                    holder.binding.typeTextView.setText(model.getManager().getName());
                else if (model.getManager() != null && !model.getManager().getId().equals(""))
                    holder.binding.typeTextView.setText(model.getManager().getId());
                else
                    holder.binding.typeTextView.setText(activity.getResources().getString(R.string.AppDefaultUnknown));

            } else {

                if (model.getManager() != null && !model.getManager().getName().equals(""))
                    holder.binding.nameTextView.setText(model.getManager().getName());
                else if (model.getManager() != null && !model.getManager().getId().equals(""))
                    holder.binding.nameTextView.setText(model.getManager().getId());
                else
                    holder.binding.nameTextView.setText(activity.getResources().getString(R.string.AppDefaultUnknown));

                holder.binding.typeTextView.setText(activity.getResources().getString(R.string.CenterAdapterPersonalClinic));

            }

            if (model.getDetail().has("avatar") && !model.getDetail().isNull("avatar") && model.getDetail().getJSONArray("avatar").length() != 0)
                setAvatar(holder, model.getDetail().getJSONArray("avatar").getJSONObject(2).getString("url"));
            else
                setAvatar(holder, "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAvatar(IndexCenterHolder holder, String url) {
        if (!url.equals("")) {
            holder.binding.charTextView.setVisibility(View.GONE);
            Picasso.get().load(url).placeholder(R.color.coolGray100).into(holder.binding.avatarCircleImageView);
        } else {
            holder.binding.charTextView.setVisibility(View.VISIBLE);
            holder.binding.charTextView.setText(StringManager.charsFirst(holder.binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.coolGray100).placeholder(R.color.coolGray100).into(holder.binding.avatarCircleImageView);
        }
    }

    @Override
    public boolean areItemsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel) {
        CenterModel oldModel = (CenterModel) oldTypeModel;
        CenterModel newModel = (CenterModel) newTypeModel;

        return newModel.getId().equals(oldModel.getId());
    }

    @Override
    public boolean areContentsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel) {
        CenterModel oldModel = (CenterModel) oldTypeModel;
        CenterModel newModel = (CenterModel) newTypeModel;

        return newModel.compareTo(oldModel);
    }

}