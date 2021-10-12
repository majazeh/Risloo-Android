package com.majazeh.risloo.Views.Adapters.Recycler.Main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Main.TagsHolder;
import com.majazeh.risloo.Views.Fragments.Main.Index.CenterTagsFragment;
import com.majazeh.risloo.Views.Fragments.Main.Index.RoomTagsFragment;
import com.majazeh.risloo.databinding.SingleItemTagBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.TagModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class TagsAdapter extends RecyclerView.Adapter<TagsHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;
    private HashMap data, header;
    public TagsHolder selectedHolder;

    // Vars
    private ArrayList<TypeModel> items;

    public TagsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public TagsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TagsHolder(SingleItemTagBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TagsHolder holder, int i) {
        intializer();

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
        return 9;
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

    private void intializer() {
        current = ((MainActivity) activity).fragmont.getCurrent();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TagsHolder holder) {
        CustomClickView.onDelayedListener(() -> {
            selectedHolder = holder;
            DialogManager.showSearchableDialog(activity, "tags");
        }).widget(holder.binding.valueTextView);
    }

    private void setData(TagsHolder holder) {
        holder.binding.indexTextView.setText(String.valueOf(holder.getBindingAdapterPosition() + 1));
        holder.binding.valueTextView.setText("");

        for (TypeModel item : items) {
            TagModel model = (TagModel) item;

            if (model.getOrder() == holder.getBindingAdapterPosition() + 1)
                holder.binding.valueTextView.setText(model.getTitle());
        }
    }

    public void responseDialog(String method, TypeModel item) {
        switch (method) {
            case "tags": {
                TagModel model = (TagModel) item;

                if (!selectedHolder.binding.valueTextView.getText().equals(model.getTitle())) {
                    selectedHolder.binding.valueTextView.setText(model.getTitle());
                } else if (selectedHolder.binding.valueTextView.getText().equals(model.getTitle())) {
                    selectedHolder.binding.valueTextView.setText("");
                }

                DialogManager.dismissSearchableDialog();

                doWork(model);
            } break;
        }
    }

    private void doWork(TagModel model) {
        if (selectedHolder.binding.orderProgressBar.getVisibility() == View.GONE)
            selectedHolder.binding.orderProgressBar.setVisibility(View.VISIBLE);

        if (current instanceof CenterTagsFragment)
            data.put("roomId", ((CenterTagsFragment) current).centerModel.getCenterId());
        else if (current instanceof RoomTagsFragment)
            data.put("roomId", ((RoomTagsFragment) current).roomModel.getRoomId());

        data.put("id", model.getId());
        data.put("order", selectedHolder.getBindingAdapterPosition() + 1);

        if (current instanceof CenterTagsFragment)
            Center.orderTags(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    List tags = (List) object;

                    activity.runOnUiThread(() -> {
                        SnackManager.showSuccesSnack(activity, activity.getResources().getString(R.string.SnackChangesSaved));

                        items.clear();
                        if (!tags.data().isEmpty())
                            setItems(tags.data());
                        else
                            setItems(new ArrayList<>());

                        if (selectedHolder.binding.orderProgressBar.getVisibility() == View.VISIBLE)
                            selectedHolder.binding.orderProgressBar.setVisibility(View.GONE);
                    });
                }

                @Override
                public void onFailure(String response) {
                    activity.runOnUiThread(() -> {
                        if (selectedHolder.binding.orderProgressBar.getVisibility() == View.VISIBLE)
                            selectedHolder.binding.orderProgressBar.setVisibility(View.GONE);
                    });
                }
            });
        else if (current instanceof RoomTagsFragment)
            Room.orderTags(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    List tags = (List) object;

                    activity.runOnUiThread(() -> {
                        SnackManager.showSuccesSnack(activity, activity.getResources().getString(R.string.SnackChangesSaved));

                        items.clear();
                        if (!tags.data().isEmpty())
                            setItems(tags.data());
                        else
                            setItems(new ArrayList<>());

                        if (selectedHolder.binding.orderProgressBar.getVisibility() == View.VISIBLE)
                            selectedHolder.binding.orderProgressBar.setVisibility(View.GONE);
                    });
                }

                @Override
                public void onFailure(String response) {
                    activity.runOnUiThread(() -> {
                        if (selectedHolder.binding.orderProgressBar.getVisibility() == View.VISIBLE)
                            selectedHolder.binding.orderProgressBar.setVisibility(View.GONE);
                    });
                }
            });
    }

}