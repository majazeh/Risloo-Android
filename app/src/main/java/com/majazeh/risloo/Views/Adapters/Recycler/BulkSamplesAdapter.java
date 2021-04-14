package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemBulkSampleBinding;

public class BulkSamplesAdapter extends RecyclerView.Adapter<BulkSamplesAdapter.BulkSamplesHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<BulkSample> bulkSamples;

    public BulkSamplesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public BulkSamplesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BulkSamplesHolder(SingleItemBulkSampleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BulkSamplesHolder holder, int i) {
//        BulkSamples bulkSample = bulkSamples.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return bulkSamples.size();
        return 5;
    }

//    public void setBulkSample(ArrayList<BulkSample> bulkSamples) {
//        this.bulkSamples = bulkSamples;
//        notifyDataSetChanged();
//    }

    private void detector(BulkSamplesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.linkImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_gray300);
        }
    }

    private void listener(BulkSamplesHolder holder) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.bulkSampleFragment)).widget(holder.binding.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.linkImageView);
    }

    private void setData(BulkSamplesHolder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText("BS966666W");
        holder.binding.nameTextView.setText("تست چند نمونه");
        holder.binding.roomTextView.setText("محمد رضا سالاری فر");
        holder.binding.centerTextView.setText("کلینیک شخصی محمد رضا سالاری فر");
        holder.binding.caseTextView.setText("بدون پرونده");
        holder.binding.referenceTextView.setText("10 / 1");
        holder.binding.statusTextView.setText("بازنشده");
    }

    public class BulkSamplesHolder extends RecyclerView.ViewHolder {

        private SingleItemBulkSampleBinding binding;

        public BulkSamplesHolder(SingleItemBulkSampleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}