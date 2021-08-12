package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.databinding.SingleItemRoomTagBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class RoomTagsAdapter extends RecyclerView.Adapter<RoomTagsAdapter.RoomTagsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public RoomTagsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RoomTagsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RoomTagsHolder(SingleItemRoomTagBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RoomTagsHolder holder, int i) {
//        TagModel model = (TagModel) items.get(i);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
        return 8;
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

    private void listener(RoomTagsHolder holder) {
        // TODO Place Code When Needed
    }

    private void setData(RoomTagsHolder holder) {
        holder.binding.indexTextView.setText(String.valueOf(holder.getBindingAdapterPosition() + 1));

        setTag(holder);
    }

    private void setTag(RoomTagsHolder holder) {
        // TODO :Place Code Here
    }

    public class RoomTagsHolder extends RecyclerView.ViewHolder {

        private SingleItemRoomTagBinding binding;

        public RoomTagsHolder(SingleItemRoomTagBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}