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
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemSample4Binding;

public class Samples4Adapter extends RecyclerView.Adapter<Samples4Adapter.Samples4Holder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Sample> samples;

    public Samples4Adapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Samples4Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Samples4Holder(SingleItemSample4Binding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Samples4Holder holder, int i) {
//        Samples sample = samples.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return samples.size();
        return 5;
    }

//    public void setSample(ArrayList<Sample> samples) {
//        this.samples = samples;
//        notifyDataSetChanged();
//    }

    private void detector(Samples4Holder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
        }
    }

    private void listener(Samples4Holder holder) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.sampleFragment)).widget(holder.binding.getRoot());

        ClickManager.onClickListener(() -> IntentManager.test(activity, null)).widget(holder.binding.statusTextView);
    }

    private void setData(Samples4Holder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText("$X1HQUQ71U");
        holder.binding.nameTextView.setText("پرسشنامه 16 عاملی شخصیت کتل");
        holder.binding.editionTextView.setText("ویرایش طلیعه سلامت - نسخه 4");
        holder.binding.referenceTextView.setText("محمد حسین");

        holder.binding.statusTextView.setText(activity.getResources().getString(R.string.SamplesFragmentStatusSeald));
        holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Green600));
    }

    public class Samples4Holder extends RecyclerView.ViewHolder {

        private SingleItemSample4Binding binding;

        public Samples4Holder(SingleItemSample4Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}