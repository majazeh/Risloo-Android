package com.majazeh.risloo.Views.Adapters.Recycler.Index;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Adapters.Holder.Header.HeaderPaymentHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Index.IndexPaymentHolder;
import com.majazeh.risloo.databinding.HeaderItemIndexPaymentBinding;
import com.majazeh.risloo.databinding.SingleItemIndexPaymentBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class IndexPaymentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public IndexPaymentAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderPaymentHolder(HeaderItemIndexPaymentBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new IndexPaymentHolder(SingleItemIndexPaymentBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof  IndexPaymentHolder) {
//            PaymentModel model = (PaymentModel) items.get(i - 1);

            detector((IndexPaymentHolder) holder);

            listener((IndexPaymentHolder) holder);

            setData((IndexPaymentHolder) holder);
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

    private void detector(IndexPaymentHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(IndexPaymentHolder holder) {
        CustomClickView.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        CustomClickView.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.treasuryImageView);
    }

    private void setData(IndexPaymentHolder holder) {
        // TODO : Place Code Here
    }

}