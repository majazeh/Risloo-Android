package com.majazeh.risloo.Views.adapters.recycler.main.Table;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.Views.adapters.holder.main.Header.HeaderSessionHolder;
import com.majazeh.risloo.Views.adapters.holder.main.Table.TableSessionHolder;
import com.majazeh.risloo.databinding.HeaderItemTableSessionBinding;
import com.majazeh.risloo.databinding.SingleItemTableSessionBinding;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class TableSessionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public TableSessionAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderSessionHolder(HeaderItemTableSessionBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TableSessionHolder(SingleItemTableSessionBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof TableSessionHolder) {
            SessionModel model = (SessionModel) items.get(i - 1);

            listener((TableSessionHolder) holder, model);

            setData((TableSessionHolder) holder, model);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;

        return 1;
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size() + 1;
        else
            return 0;
    }

    public int itemsCount() {
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

    private void listener(TableSessionHolder holder, SessionModel model) {
        CustomClickView.onClickListener(() -> {
            ((MainActivity) activity).navigatoon.navigateToSessionFragment(model);
        }).widget(holder.binding.getRoot());

        CustomClickView.onClickListener(() -> {
            ((MainActivity) activity).navigatoon.navigateToEditSessionFragment(model);
        }).widget(holder.binding.editImageView);
    }

    private void setData(TableSessionHolder holder, SessionModel model) {
        try {
            holder.binding.serialTextView.setText(model.getId());

            if (model.getRoom() != null && model.getRoom().getManager() != null)
                holder.binding.roomTextView.setText(model.getRoom().getManager().getName());

            if (model.getRoom() != null && model.getRoom().getCenter() != null && model.getRoom().getCenter().getDetail() != null && model.getRoom().getCenter().getDetail().has("title") && !model.getRoom().getCenter().getDetail().getString("title").equals(""))
                holder.binding.centerTextView.setText(model.getRoom().getCenter().getDetail().getString("title"));

            holder.binding.startTimeTextView.setText(DateManager.jalYYYYsNMMsDDsNDDnlHHsMM(String.valueOf(model.getStartedAt()), " "));
            holder.binding.durationTextView.setText(model.getDuration() + " " + "دقیقه");
            holder.binding.statusTextView.setText(SelectionManager.getSessionStatus(activity, "fa", model.getStatus()));

            if (model.getCasse() != null)
                holder.binding.caseTextView.setText(model.getCasse().getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}