package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.RoomsHolder;
import com.majazeh.risloo.Views.Fragments.Show.DashboardFragment;
import com.majazeh.risloo.databinding.SingleItemRoomBinding;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public RoomsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RoomsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RoomsHolder(SingleItemRoomBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsHolder holder, int i) {
        RoomModel model = (RoomModel) items.get(i);

        initializer();

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

    private void initializer() {
        current = ((MainActivity) activity).fragmont.getCurrent();
    }

    private void listener(RoomsHolder holder, RoomModel model) {
        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalRoomFragment(model);
            ((MainActivity) activity).navController.navigate(action);
        }).widget(holder.binding.getRoot());
    }

    private void setData(RoomsHolder holder, RoomModel model) {
        if (current instanceof DashboardFragment) {
            try {
                if (model.getRoomCenter() != null && model.getRoomCenter().getDetail() != null && model.getRoomCenter().getDetail().has("title") && !model.getRoomCenter().getDetail().isNull("title") && !model.getRoomCenter().getDetail().getString("title").equals(""))
                    holder.binding.nameTextView.setText(model.getRoomCenter().getDetail().getString("title"));
                else if (model.getRoomCenter() != null)
                    holder.binding.nameTextView.setText(model.getRoomCenter().getCenterId());
                else
                    holder.binding.nameTextView.setText("نامعلوم");

                if (model.getRoomCenter() != null && model.getRoomCenter().getDetail().has("avatar") && !model.getRoomCenter().getDetail().isNull("avatar") && model.getRoomCenter().getDetail().getJSONArray("avatar").length() != 0)
                    setAvatar(holder, model.getRoomCenter().getDetail().getJSONArray("avatar").getJSONObject(2).getString("url"));
                else
                    setAvatar(holder, "");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            if (model.getRoomManager() != null && !model.getRoomManager().getName().equals(""))
                holder.binding.nameTextView.setText(model.getRoomManager().getName());
            else if (model.getRoomManager() != null)
                holder.binding.nameTextView.setText(model.getRoomManager().getId());
            else
                holder.binding.nameTextView.setText("نامعلوم");

            if (model.getRoomManager() != null && model.getRoomManager().getAvatar() != null && model.getRoomManager().getAvatar().getMedium() != null)
                setAvatar(holder, model.getRoomManager().getAvatar().getMedium().getUrl());
            else
                setAvatar(holder, "");
        }
    }

    private void setAvatar(RoomsHolder holder, String url) {
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