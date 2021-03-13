package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;

public class SamplesAdapter extends RecyclerView.Adapter<SamplesAdapter.SamplesHolder> {

    // Vars
//    private ArrayList<Sample> samples;

    // Objects
    private Activity activity;

    public SamplesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public SamplesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.single_item_sample, viewGroup, false);

        return new SamplesHolder(view);
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
            holder.itemView.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            holder.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
        }
    }

    private void listener(SamplesHolder holder) {
        holder.itemView.setOnClickListener(v -> {
            holder.itemView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.itemView.setClickable(true), 300);

//            ((MainActivity) activity).navigator(R.id.sampleFragment);
        });

        holder.statusTextView.setOnClickListener(v -> {
            holder.statusTextView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.statusTextView.setClickable(true), 300);

            // TODO : Place Code Here
        });
    }

    private void setData(SamplesHolder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.topView.setVisibility(View.GONE);
        } else {
            holder.topView.setVisibility(View.VISIBLE);
        }

        holder.serialTextView.setText("$X1HQUQ71U");
        holder.nameTextView.setText("پرسشنامه 16 عاملی شخصیت کتل");
        holder.editionTextView.setText("ویرایش طلیعه سلامت - نسخه 4");
        holder.roomTextView.setText("اتاق درمان فاطمه عبدالملکی");
        holder.caseTextView.setText("RS96666DT");
        holder.referenceTextView.setText("مرضیه آشتیانی");

        holder.statusTextView.setText(activity.getResources().getString(R.string.SamplesFragmentSeald));
        holder.statusTextView.setTextColor(activity.getResources().getColor(R.color.Green600));
    }

    public class SamplesHolder extends RecyclerView.ViewHolder {

        private View topView;
        private TextView serialTextView, nameTextView, editionTextView, roomTextView, caseTextView, referenceTextView, statusTextView;

        public SamplesHolder(View view) {
            super(view);
            topView = view.findViewById(R.id.single_item_sample_top_view);
            serialTextView = view.findViewById(R.id.single_item_sample_serial_textView);
            nameTextView = view.findViewById(R.id.single_item_sample_name_textView);
            editionTextView = view.findViewById(R.id.single_item_sample_edition_textView);
            roomTextView = view.findViewById(R.id.single_item_sample_room_textView);
            caseTextView = view.findViewById(R.id.single_item_sample_case_textView);
            referenceTextView = view.findViewById(R.id.single_item_sample_reference_textView);
            statusTextView = view.findViewById(R.id.single_item_sample_status_textView);
        }
    }

}