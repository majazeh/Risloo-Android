package com.majazeh.risloo.Views.Adapters.Recycler.Main.Table;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Header.HeaderBalanceHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Table.TableBalanceHolder;
import com.majazeh.risloo.databinding.HeaderItemTableBalanceBinding;
import com.majazeh.risloo.databinding.SingleItemTableBalanceBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class TableBalanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

    public TableBalanceAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderBalanceHolder(HeaderItemTableBalanceBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TableBalanceHolder(SingleItemTableBalanceBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof HeaderBalanceHolder) {
            setWidget((HeaderBalanceHolder) holder);
        } else if (holder instanceof TableBalanceHolder) {
//            BalanceModel model = (BalanceModel) items.get(i - 1);

            listener((TableBalanceHolder) holder);

            setData((TableBalanceHolder) holder);
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
            return 5;
    }

    public int itemsCount() {
        if (this.items != null)
            return items.size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items) {
        userSelect = false;

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

    private void setWidget(HeaderBalanceHolder holder) {
        holder.binding.amountTextView.setText(StringManager.foregroundSize(activity.getResources().getString(R.string.BalanceAdapterAmount), 10, 17, activity.getResources().getColor(R.color.CoolGray500), (int) activity.getResources().getDimension(R.dimen._7ssp)));
    }

    private void listener(TableBalanceHolder holder) {
        CustomClickView.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());
    }

    private void setData(TableBalanceHolder holder) {
        // TODO : Place Code Here
    }

    private void setMenu() {

    }

}