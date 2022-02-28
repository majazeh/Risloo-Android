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
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.holder.main.Index.IndexTagHolder;
import com.majazeh.risloo.views.fragments.main.index.FragmentCenterTags;
import com.majazeh.risloo.views.fragments.main.index.FragmentRoomTags;
import com.majazeh.risloo.databinding.SingleItemIndexTagBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.TagModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class IndexTagAdapter extends RecyclerView.Adapter<IndexTagHolder> {

    // Holders
    public IndexTagHolder selectedHolder;

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> items;

    public IndexTagAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public IndexTagHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IndexTagHolder(SingleItemIndexTagBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IndexTagHolder holder, int i) {
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
        current = ((ActivityMain) activity).fragmont.getCurrent();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) activity).singleton.getAuthorization());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(IndexTagHolder holder) {
        CustomClickView.onDelayedListener(() -> {
            // TODO : Place Code When Needed
        }).widget(holder.binding.indexTextView);

        CustomClickView.onDelayedListener(() -> {
            selectedHolder = holder;
            DialogManager.showDialogSearchable(activity, "tags");
        }).widget(holder.binding.titleTextView);
    }

    private void setData(IndexTagHolder holder) {
        holder.binding.indexTextView.setText(String.valueOf(holder.getBindingAdapterPosition() + 1));

        setTitle(holder);
    }

    private void setTitle(IndexTagHolder holder) {
        holder.binding.titleTextView.setText("");

        for (TypeModel item : items) {
            TagModel model = (TagModel) item;

            if (model.getOrder() == holder.getBindingAdapterPosition() + 1)
                holder.binding.titleTextView.setText(model.getTitle());

            break;
        }
    }

    public void responseDialog(String method, TypeModel item) {
        switch (method) {
            case "tags": {
                TagModel model = (TagModel) item;

                if (!selectedHolder.binding.titleTextView.getText().equals(model.getTitle())) {
                    selectedHolder.binding.titleTextView.setText(model.getTitle());
                } else if (selectedHolder.binding.titleTextView.getText().equals(model.getTitle())) {
                    selectedHolder.binding.titleTextView.setText("");
                }

                DialogManager.dismissDialogSearchable();

                doWork(model);
            } break;
        }
    }

    private void setHashmap(TagModel model) {
        if (current instanceof FragmentCenterTags)
            data.put("roomId", ((FragmentCenterTags) current).centerModel.getId());
        else if (current instanceof FragmentRoomTags)
            data.put("roomId", ((FragmentRoomTags) current).roomModel.getId());

        data.put("id", model.getId());
        data.put("order", selectedHolder.getBindingAdapterPosition() + 1);
    }

    private void setProgress(boolean show) {
        if (show && selectedHolder.binding.orderProgressBar.getVisibility() == View.GONE)
            selectedHolder.binding.orderProgressBar.setVisibility(View.VISIBLE);
        else if (!show && selectedHolder.binding.orderProgressBar.getVisibility() == View.VISIBLE)
            selectedHolder.binding.orderProgressBar.setVisibility(View.GONE);
    }

    private void doWork(TagModel model) {
        setProgress(true);

        setHashmap(model);

        if (current instanceof FragmentCenterTags) {
            Center.orderTags(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    List tags = (List) object;

                    activity.runOnUiThread(() -> {
                        SnackManager.showSnackSucces(activity, activity.getResources().getString(R.string.SnackChangesSaved));

                        items.clear();
                        if (!tags.data().isEmpty())
                            setItems(tags.data());
                        else
                            setItems(new ArrayList<>());

                        setProgress(false);
                    });
                }

                @Override
                public void onFailure(String response) {
                    activity.runOnUiThread(() -> {
                        setProgress(false);
                    });
                }
            });
        } else if (current instanceof FragmentRoomTags) {
            Room.orderTags(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    List tags = (List) object;

                    activity.runOnUiThread(() -> {
                        SnackManager.showSnackSucces(activity, activity.getResources().getString(R.string.SnackChangesSaved));

                        items.clear();
                        if (!tags.data().isEmpty())
                            setItems(tags.data());
                        else
                            setItems(new ArrayList<>());

                        setProgress(false);
                    });
                }

                @Override
                public void onFailure(String response) {
                    activity.runOnUiThread(() -> {
                        setProgress(false);
                    });
                }
            });
        }
    }

}