package com.majazeh.risloo.Views.Adapters.Recycler.Main.Index;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Interfaces.MyDiffUtilAdapter;
import com.majazeh.risloo.Utils.Interfaces.MyDiffUtilCallback;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Index.IndexCenterHolder;
import com.majazeh.risloo.databinding.SingleItemIndexCenterBinding;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

public class IndexCenterAdapter extends RecyclerView.Adapter<IndexCenterHolder> implements MyDiffUtilAdapter {

    // Instance
    private final Activity activity;

    // Vars
    private final ArrayList<TypeModel> items = new ArrayList<>();

    public IndexCenterAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public IndexCenterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IndexCenterHolder(SingleItemIndexCenterBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IndexCenterHolder holder, int i) {
        CenterModel model = (CenterModel) items.get(i);

        listener(holder, model);

        setData(holder, model);
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffUtilCallback(this, this.items, items));
        this.items.addAll(items);

        diffResult.dispatchUpdatesTo(this);
    }

    private void listener(IndexCenterHolder holder, CenterModel model) {
        CustomClickView.onClickListener(() -> {
            if (model.getCenterType().equals("counseling_center"))
                ((MainActivity) activity).navigatoon.navigateToCenterFragment(model);
            else
                ((MainActivity) activity).navigatoon.navigateToRoomFragment(model);

        }).widget(holder.binding.getRoot());
    }

    private void setData(IndexCenterHolder holder, CenterModel model) {
        try {
            if (model.getCenterType().equals("counseling_center")) {

                if (model.getDetail() != null && model.getDetail().has("title") && !model.getDetail().isNull("title") && !model.getDetail().getString("title").equals(""))
                    holder.binding.nameTextView.setText(model.getDetail().getString("title"));
                else if (model.getCenterId() != null && !model.getCenterId().equals(""))
                    holder.binding.nameTextView.setText(model.getCenterId());
                else
                    holder.binding.nameTextView.setText(activity.getResources().getString(R.string.AppDefaultUnknown));

                if (model.getManager() != null && model.getManager().getName() != null && !model.getManager().getName().equals(""))
                    holder.binding.typeTextView.setText(model.getManager().getName());
                else if (model.getManager() != null && model.getManager().getId() != null && !model.getManager().getId().equals(""))
                    holder.binding.typeTextView.setText(model.getManager().getId());
                else
                    holder.binding.typeTextView.setText(activity.getResources().getString(R.string.AppDefaultUnknown));

            } else {

                if (model.getManager() != null && model.getManager().getName() != null && !model.getManager().getName().equals(""))
                    holder.binding.nameTextView.setText(model.getManager().getName());
                else if (model.getManager() != null && model.getManager().getId() != null && !model.getManager().getId().equals(""))
                    holder.binding.nameTextView.setText(model.getManager().getId());
                else
                    holder.binding.nameTextView.setText(activity.getResources().getString(R.string.AppDefaultUnknown));

                holder.binding.typeTextView.setText(activity.getResources().getString(R.string.CenterAdapterPersonalClinic));

            }

            if (model.getDetail() != null && model.getDetail().has("avatar") && !model.getDetail().isNull("avatar") && model.getDetail().getJSONArray("avatar").length() != 0)
                setAvatar(holder, model.getDetail().getJSONArray("avatar").getJSONObject(2).getString("url"));
            else
                setAvatar(holder, "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAvatar(IndexCenterHolder holder, String url) {
        if (!url.equals("")) {
            holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(url).placeholder(R.color.CoolGray100).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            holder.binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(holder.binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.CoolGray100).placeholder(R.color.CoolGray100).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
        }
    }

    @Override
    public boolean isItemsTheSame(ArrayList<TypeModel> oldList, ArrayList<TypeModel> newList, int oldItemPosition, int newItemPosition) {
        CenterModel oldCenterModel = (CenterModel) oldList.get(oldItemPosition);
        CenterModel newCenterModel = (CenterModel) newList.get(newItemPosition);

        return newCenterModel.getCenterId().equals(oldCenterModel.getCenterId());
    }

    @Override
    public boolean isContentsTheSame(ArrayList<TypeModel> oldList, ArrayList<TypeModel> newList, int oldItemPosition, int newItemPosition) {
        CenterModel oldCenterModel = (CenterModel) oldList.get(oldItemPosition);
        CenterModel newCenterModel = (CenterModel) newList.get(newItemPosition);

        return newCenterModel.compareTo(oldCenterModel);
    }

}