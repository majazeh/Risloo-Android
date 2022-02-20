package com.majazeh.risloo.Views.adapters.recycler.main.Table;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.Utils.widgets.CustomClickView;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.Views.adapters.holder.main.Header.HeaderCaseHolder;
import com.majazeh.risloo.Views.adapters.holder.main.Table.TableCaseHolder;
import com.majazeh.risloo.databinding.HeaderItemTableCaseBinding;
import com.majazeh.risloo.databinding.SingleItemTableCaseBinding;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.ArrayList;

public class TableCaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public TableCaseAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderCaseHolder(HeaderItemTableCaseBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TableCaseHolder(SingleItemTableCaseBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof TableCaseHolder) {
            CaseModel model = (CaseModel) items.get(i - 1);

            listener((TableCaseHolder) holder, model);

            setData((TableCaseHolder) holder, model);
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

    private void listener(TableCaseHolder holder, CaseModel model) {
        CustomClickView.onClickListener(() -> {
            ((MainActivity) activity).navigatoon.navigateToCaseFragment(model);
        }).widget(holder.binding.getRoot());
    }

    private void setData(TableCaseHolder holder, CaseModel model) {
        try {
            holder.binding.serialTextView.setText(model.getId());

            if (model.getManager() != null)
                holder.binding.roomTextView.setText(model.getManager().getName());

            if (model.getRoom() != null && model.getRoom().getCenter() != null && model.getRoom().getCenter().getDetail() != null && model.getRoom().getCenter().getDetail().has("title") && !model.getRoom().getCenter().getDetail().getString("title").equals(""))
                holder.binding.centerTextView.setText(model.getRoom().getCenter().getDetail().getString("title"));

            setClients(holder, model.getClients());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setClients(TableCaseHolder holder, List clients) {
        if (clients != null && clients.data().size() != 0) {
            holder.binding.referenceTextView.setText("");
            for (int i = 0; i < clients.data().size(); i++) {
                UserModel model = (UserModel) clients.data().get(i);
                if (model != null) {
                    holder.binding.referenceTextView.append(model.getName());
                    if (i != clients.data().size() - 1) {
                        holder.binding.referenceTextView.append("\n");
                    }
                }
            }
        }
    }

}