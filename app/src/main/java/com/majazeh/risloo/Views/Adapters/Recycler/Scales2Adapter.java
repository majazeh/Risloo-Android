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
import com.majazeh.risloo.databinding.SingleItemScale2Binding;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class Scales2Adapter extends RecyclerView.Adapter<Scales2Adapter.Scales2Holder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> scales;

    public Scales2Adapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Scales2Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Scales2Holder(SingleItemScale2Binding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Scales2Holder holder, int i) {
        SampleModel scale = (SampleModel) scales.get(i);

        detector(holder);

        listener(holder, scale);

        setData(holder, scale);
    }

    @Override
    public int getItemCount() {
        if (this.scales != null)
            return scales.size();
        else
            return 0;
    }

    public void setScales(ArrayList<TypeModel> scales) {
        if (this.scales == null)
            this.scales = scales;
        else
            this.scales.addAll(scales);
        notifyDataSetChanged();
    }

    public void clearScales() {
        if (this.scales != null) {
            this.scales.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(Scales2Holder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(Scales2Holder holder, SampleModel model) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());
    }

    private void setData(Scales2Holder holder, SampleModel model) {
        if (holder.getBindingAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText(model.getSampleId());
        holder.binding.nameTextView.setText(model.getSampleTitle());

        if (!model.getSampleEdition().equals(""))
            holder.binding.editionTextView.setText(model.getSampleEdition() + " - نسخه " + model.getSampleVersion());
        else
            holder.binding.editionTextView.setText("نسخه " + model.getSampleVersion());
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

    public class Scales2Holder extends RecyclerView.ViewHolder {

        private SingleItemScale2Binding binding;

        public Scales2Holder(SingleItemScale2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}