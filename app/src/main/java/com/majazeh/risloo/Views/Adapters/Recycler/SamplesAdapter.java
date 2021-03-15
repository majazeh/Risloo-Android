package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemSampleBinding;

public class SamplesAdapter extends RecyclerView.Adapter<SamplesAdapter.SamplesHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Sample> samples;

    public SamplesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public SamplesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SamplesHolder(SingleItemSampleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SamplesHolder holder, int i) {
//        Samples sample = samples.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return samples.size();
        return 15;
    }

//    public void setSample(ArrayList<Sample> samples) {
//        this.samples = samples;
//        notifyDataSetChanged();
//    }

    private void detector(SamplesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.singleItemSample.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.singleItemSampleStatusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            holder.binding.singleItemSampleStatusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
        }
    }

    private void listener(SamplesHolder holder) {
        holder.binding.singleItemSample.setOnClickListener(v -> {
            holder.binding.singleItemSample.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.singleItemSample.setClickable(true), 300);

            ((MainActivity) activity).navigator(R.id.sampleFragment);
        });

        holder.binding.singleItemSampleStatusTextView.setOnClickListener(v -> {
            holder.binding.singleItemSampleStatusTextView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.singleItemSampleStatusTextView.setClickable(true), 300);

            // TODO : Place Code Here
        });
    }

    private void setData(SamplesHolder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.binding.singleItemSampleTopView.setVisibility(View.GONE);
        } else {
            holder.binding.singleItemSampleTopView.setVisibility(View.VISIBLE);
        }

        holder.binding.singleItemSampleSerialTextView.setText("$X1HQUQ71U");
        holder.binding.singleItemSampleNameTextView.setText("پرسشنامه 16 عاملی شخصیت کتل");
        holder.binding.singleItemSampleEditionTextView.setText("ویرایش طلیعه سلامت - نسخه 4");
        holder.binding.singleItemSampleRoomTextView.setText("اتاق درمان فاطمه عبدالملکی");
        holder.binding.singleItemSampleCaseTextView.setText("RS96666DT");
        holder.binding.singleItemSampleReferenceTextView.setText("مرضیه آشتیانی");

        holder.binding.singleItemSampleStatusTextView.setText(activity.getResources().getString(R.string.SamplesFragmentSeald));
        holder.binding.singleItemSampleStatusTextView.setTextColor(activity.getResources().getColor(R.color.Green600));
    }

    public class SamplesHolder extends RecyclerView.ViewHolder {

        private SingleItemSampleBinding binding;

        public SamplesHolder(SingleItemSampleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}