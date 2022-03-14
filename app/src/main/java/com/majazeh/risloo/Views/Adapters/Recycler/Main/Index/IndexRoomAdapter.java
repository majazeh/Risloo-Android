package com.majazeh.risloo.views.adapters.recycler.main.Index;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.interfaces.ItemTouchHelperAdapter;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.interfaces.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.holder.main.Index.IndexRoomHolder;
import com.majazeh.risloo.views.fragments.main.index.FragmentRooms;
import com.majazeh.risloo.views.fragments.main.show.FragmentDashboard;
import com.majazeh.risloo.databinding.SingleItemIndexRoomBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class IndexRoomAdapter extends RecyclerView.Adapter<IndexRoomHolder> implements ItemTouchHelperAdapter {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> items;
    private int dragFromPosition, dragToPosition;
    private boolean userSelect = false;

    public IndexRoomAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public IndexRoomHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IndexRoomHolder(SingleItemIndexRoomBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IndexRoomHolder holder, int i) {
        RoomModel model = (RoomModel) items.get(i);

        initializer();

        listener(holder, model);

        setData(holder, model);
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size();
        else {
            return 0;
        }
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
        current = ((ActivityMain) activity).fragmont.getCurrent();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) activity).singleton.getAuthorization());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(IndexRoomHolder holder, RoomModel model) {
        CustomClickView.onClickListener(() -> {
            ((ActivityMain) activity).navigatoon.navigateToFragmentRoom(model);
        }).widget(holder.binding.getRoot());

        holder.binding.availableSwitchCompat.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.availableSwitchCompat.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        holder.binding.availableSwitchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (userSelect) {
                if (isChecked)
                    doWork(holder, model, "1", "available");
                else
                    doWork(holder, model, "0", "available");

                userSelect = false;
            }
        });
    }

    private void setData(IndexRoomHolder holder, RoomModel model) {
        if (current instanceof FragmentDashboard) {
            try {

                if (model.getCenter() != null && model.getCenter().getDetail() != null && model.getCenter().getDetail().has("title") && !model.getCenter().getDetail().isNull("title") && !model.getCenter().getDetail().getString("title").equals(""))
                    holder.binding.nameTextView.setText(model.getCenter().getDetail().getString("title"));
                else if (model.getCenter() != null && model.getCenter().getId() != null && !model.getCenter().getId().equals(""))
                    holder.binding.nameTextView.setText(model.getCenter().getId());
                else
                    holder.binding.nameTextView.setText(activity.getResources().getString(R.string.AppDefaultUnknown));

                if (model.getCenter() != null && model.getCenter().getDetail() != null && model.getCenter().getDetail().has("avatar") && !model.getCenter().getDetail().isNull("avatar") && model.getCenter().getDetail().getJSONArray("avatar").length() != 0)
                    setAvatar(holder, model.getCenter().getDetail().getJSONArray("avatar").getJSONObject(2).getString("url"));
                else
                    setAvatar(holder, "");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

            if (model.getManager() != null && model.getManager().getName() != null && !model.getManager().getName().equals(""))
                holder.binding.nameTextView.setText(model.getManager().getName());
            else if (model.getManager() != null && model.getManager().getId() != null && !model.getManager().getId().equals(""))
                holder.binding.nameTextView.setText(model.getManager().getId());
            else
                holder.binding.nameTextView.setText(activity.getResources().getString(R.string.AppDefaultUnknown));

            if (model.getManager() != null && model.getManager().getAvatar() != null && model.getManager().getAvatar().getMedium() != null)
                setAvatar(holder, model.getManager().getAvatar().getMedium().getUrl());
            else
                setAvatar(holder, "");

        }

        if (current instanceof FragmentRooms) {
            setAvailable(holder, true);
        }
    }

    private void setAvatar(IndexRoomHolder holder, String url) {
        if (!url.equals("")) {
            holder.binding.charTextView.setVisibility(View.GONE);
            Picasso.get().load(url).placeholder(R.color.coolGray100).into(holder.binding.avatarCircleImageView);
        } else {
            holder.binding.charTextView.setVisibility(View.VISIBLE);
            holder.binding.charTextView.setText(StringManager.charsFirst(holder.binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.coolGray100).placeholder(R.color.coolGray100).into(holder.binding.avatarCircleImageView);
        }
    }

    private void setAvailable(IndexRoomHolder holder, boolean isAvailable) {
        holder.binding.availableSwitchCompat.setVisibility(View.VISIBLE);
        holder.binding.availableSwitchCompat.setChecked(isAvailable);
    }

    private void setHashmap(RoomModel model, String value, String method) {
        data.put("id", model.getId());
        data.put(method, value);

        if (method.equals("available"))
            data.remove("order");
        else
            data.remove("available");
    }

    private void doWork(IndexRoomHolder holder, RoomModel model, String value, String method) {
        setHashmap(model, value, method);

        if (method.equals("available")) {
//            DialogManager.showLoadingDialog(activity,"");
//
//            Room.edit(data, header, new Response() {
//                @Override
//                public void onOK(Object object) {
//                    activity.runOnUiThread(() -> {
//                        DialogManager.dismissLoadingDialog();
//                        SnackManager.showSuccesSnack(activity, activity.getResources().getString(R.string.SnackChangesSaved));
//                    });
//                }
//
//                @Override
//                public void onFailure(String response) {
//                    activity.runOnUiThread(() -> {
//                        resetWidget(holder, value, method);
//                    });
//                }
//            });
        } else {
            Room.changeOrder(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    activity.runOnUiThread(() -> {
                        // TODO : Place Code When Needed
                    });
                }

                @Override
                public void onFailure(String response) {
                    activity.runOnUiThread(() -> {
                        // TODO : Place Code When Needed
                    });
                }
            });
        }
    }

    private void resetWidget(IndexRoomHolder holder, String value, String method) {
        if (method.equals("available")) {
            if (value.equals("1"))
                setAvailable(holder, false);
            else if (value.equals("0"))
                setAvailable(holder, true);
        }
    }

    @Override
    public void onItemMove(RecyclerView.ViewHolder viewHolder, int fromPosition, int toPosition) {
        this.dragFromPosition = fromPosition;
        this.dragToPosition = toPosition;

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++)
                Collections.swap(items, i, i + 1);
        } else {
            for (int i = fromPosition; i > toPosition; i--)
                Collections.swap(items, i, i - 1);
        }

        if (viewHolder instanceof IndexRoomHolder) {
            ((IndexRoomHolder) viewHolder).binding.positionTextView.setText(String.valueOf(toPosition + 1));
        }

        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int position) {
        // TODO : Place Code If Needed

        notifyItemRemoved(position);
    }

    @Override
    public void onItemSelect(RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setBackgroundResource(R.drawable.draw_2sdp_solid_lightblue50_border_1sdp_lightblue300);

        if (viewHolder instanceof IndexRoomHolder) {
            ((IndexRoomHolder) viewHolder).binding.positionTextView.setVisibility(View.VISIBLE);
            ((IndexRoomHolder) viewHolder).binding.positionTextView.setText(String.valueOf(viewHolder.getBindingAdapterPosition() + 1));
        }
    }

    @Override
    public void onItemClear(RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_coolgray300);

        if (viewHolder instanceof IndexRoomHolder) {
            ((IndexRoomHolder) viewHolder).binding.positionTextView.setVisibility(View.GONE);
            ((IndexRoomHolder) viewHolder).binding.positionTextView.setText("");
        }

        RoomModel model = (RoomModel) items.get(dragToPosition);
        doWork(null, model, String.valueOf(dragToPosition + 1), "order");
    }

}