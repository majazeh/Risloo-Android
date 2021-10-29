package com.majazeh.risloo.Views.Adapters.Recycler.Main;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Main.CentersHolder;
import com.majazeh.risloo.databinding.SingleItemCenterBinding;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

public class CentersAdapter extends RecyclerView.Adapter<CentersHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public CentersAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CentersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CentersHolder(SingleItemCenterBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CentersHolder holder, int i) {
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

    private void listener(CentersHolder holder, CenterModel model) {
        CustomClickView.onClickListener(() -> {
            if (model.getCenterType().equals("counseling_center")) {
                NavDirections action = NavigationMainDirections.actionGlobalCenterFragment(model);
                ((MainActivity) activity).navController.navigate(action);
            } else {
                NavDirections action = NavigationMainDirections.actionGlobalRoomFragment(model);
                ((MainActivity) activity).navController.navigate(action);
            }
        }).widget(holder.binding.getRoot());
    }

    private void setData(CentersHolder holder, CenterModel model) {
        try {
            if (model.getCenterType().equals("counseling_center")) {
                if (model.getDetail() != null && model.getDetail().has("title") && !model.getDetail().isNull("title") && !model.getDetail().getString("title").equals(""))
                    holder.binding.nameTextView.setText(model.getDetail().getString("title"));
                else
                    holder.binding.nameTextView.setText(model.getCenterId());

                if (model.getManager() != null && !model.getManager().getName().equals(""))
                    holder.binding.typeTextView.setText(model.getManager().getName());
                else if (model.getManager() != null)
                    holder.binding.typeTextView.setText(model.getManager().getId());
                else
                    holder.binding.typeTextView.setText("نامعلوم");

            } else {
                if (model.getManager() != null && !model.getManager().getName().equals(""))
                    holder.binding.nameTextView.setText(model.getManager().getName());
                else if (model.getManager() != null)
                    holder.binding.nameTextView.setText(model.getManager().getId());
                else
                    holder.binding.nameTextView.setText("نامعلوم");

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

    private void setAvatar(CentersHolder holder, String url) {
        if (!url.equals("")) {
            holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(url).placeholder(R.color.CoolGray50).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            holder.binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(holder.binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.CoolGray50).placeholder(R.color.CoolGray50).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
        }
    }

}