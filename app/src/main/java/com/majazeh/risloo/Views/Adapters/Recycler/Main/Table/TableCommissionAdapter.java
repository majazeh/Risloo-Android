package com.majazeh.risloo.Views.Adapters.Recycler.Main.Table;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Header.HeaderCommissionHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Table.TableCommissionHolder;
import com.majazeh.risloo.databinding.HeaderItemTableCommissionBinding;
import com.majazeh.risloo.databinding.SingleItemTableCommissionBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class TableCommissionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> items;

    public TableCommissionAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderCommissionHolder(HeaderItemTableCommissionBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TableCommissionHolder(SingleItemTableCommissionBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
         if (holder instanceof TableCommissionHolder) {
//            CommissionModel model = (CommissionModel) items.get(i - 1);

            initializer();

            listener((TableCommissionHolder) holder);

            setData((TableCommissionHolder) holder);
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

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());
    }

    private void listener(TableCommissionHolder holder) {
        CustomClickView.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());
    }

    private void setData(TableCommissionHolder holder) {
        // TODO : Place Code Here
    }

    private void setHashmap() {

    }

    private void doWork() {

    }

}