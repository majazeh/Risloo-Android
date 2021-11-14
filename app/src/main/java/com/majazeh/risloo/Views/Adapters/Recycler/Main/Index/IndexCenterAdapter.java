package com.majazeh.risloo.Views.Adapters.Recycler.Main.Index;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
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

public class IndexCenterAdapter extends RecyclerView.Adapter<IndexCenterHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

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
        if (this.items == null)
            this.items = items;
        else
            this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        if (this.items != null) {
            this.items.clear();
            notifyDataSetChanged();
        }
    }

    private void listener(IndexCenterHolder holder, CenterModel model) {
        CustomClickView.onClickListener(() -> {
            NavDirections action;

            if (model.getCenterType().equals("counseling_center"))
                action = NavigationMainDirections.actionGlobalCenterFragment(model);
            else
                action = NavigationMainDirections.actionGlobalRoomFragment(model);

            ((MainActivity) activity).navController.navigate(action);
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

}