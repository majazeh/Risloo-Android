package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemSample3Binding;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class Samples3Adapter extends RecyclerView.Adapter<Samples3Adapter.Samples3Holder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> samples;

    public Samples3Adapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Samples3Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Samples3Holder(SingleItemSample3Binding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Samples3Holder holder, int i) {
        SampleModel sample = (SampleModel) samples.get(i);

        detector(holder);

        listener(holder, sample);

        setData(holder, sample);
    }

    @Override
    public int getItemCount() {
        if (this.samples != null)
            return samples.size();
        else
            return 0;
    }

    public void setSamples(ArrayList<TypeModel> samples) {
        if (this.samples == null)
            this.samples = samples;
        else
            this.samples.addAll(samples);
        notifyDataSetChanged();
    }

    public void clearSamples() {
        if (this.samples != null) {
            this.samples.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(Samples3Holder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(Samples3Holder holder, SampleModel model) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.sampleFragment, getExtras(model))).widget(holder.binding.getRoot());

        ClickManager.onClickListener(() -> IntentManager.test(activity, null)).widget(holder.binding.statusTextView);
    }

    private void setData(Samples3Holder holder, SampleModel model) {
        if (holder.getBindingAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText(model.getSampleId());
        holder.binding.nameTextView.setText(model.getSampleScaleTitle());

        if (!model.getSampleEdition().equals(""))
            holder.binding.editionTextView.setText(model.getSampleEdition() + " - نسخه " + model.getSampleVersion());
        else
            holder.binding.editionTextView.setText("نسخه " + model.getSampleVersion());

        if (model.getSampleRoom() != null && model.getSampleRoom().getRoomManager() != null) {
            holder.binding.roomTextView.setText(model.getSampleRoom().getRoomManager().getName());
        }

        if (model.getSampleCase() != null && model.getSampleCase().getCaseId() != null) {
            holder.binding.caseTextView.setText(model.getSampleCase().getCaseId());
        }

        setAction(holder, model.getSampleStatus());
    }

    private void setAction(Samples3Holder holder, String action) {
        switch (action) {
            case "seald":
                holder.binding.statusTextView.setEnabled(true);

                holder.binding.statusTextView.setText(activity.getResources().getString(R.string.SamplesFragmentStatusSeald));
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Green600));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                    holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
                else
                    holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
                break;
            case "open":
                holder.binding.statusTextView.setEnabled(true);

                holder.binding.statusTextView.setText(activity.getResources().getString(R.string.SamplesFragmentStatusOpen));
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Green600));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                    holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
                else
                    holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
                break;
            case "closed":
                holder.binding.statusTextView.setEnabled(false);

                holder.binding.statusTextView.setText(activity.getResources().getString(R.string.SamplesFragmentStatusClosed));
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));

                holder.binding.statusTextView.setBackgroundResource(android.R.color.transparent);
                break;
            case "scoring":
                holder.binding.statusTextView.setEnabled(false);

                holder.binding.statusTextView.setText(activity.getResources().getString(R.string.SamplesFragmentStatusScoring));
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));

                holder.binding.statusTextView.setBackgroundResource(android.R.color.transparent);
                break;
            case "creating_files":
                holder.binding.statusTextView.setEnabled(false);

                holder.binding.statusTextView.setText(activity.getResources().getString(R.string.SamplesFragmentStatusCreatingFiles));
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));

                holder.binding.statusTextView.setBackgroundResource(android.R.color.transparent);
                break;
            case "done":
                holder.binding.statusTextView.setEnabled(false);

                holder.binding.statusTextView.setText(activity.getResources().getString(R.string.SamplesFragmentStatusDone));
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Gray600));

                holder.binding.statusTextView.setBackgroundResource(android.R.color.transparent);
                break;
        }
    }

    private Bundle getExtras(SampleModel model) {
        Bundle extras = new Bundle();
//        try {
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return extras;
    }

    public class Samples3Holder extends RecyclerView.ViewHolder {

        private SingleItemSample3Binding binding;

        public Samples3Holder(SingleItemSample3Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}