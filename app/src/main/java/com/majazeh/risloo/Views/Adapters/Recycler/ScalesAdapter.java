package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemScaleBinding;
import com.mre.ligheh.Model.TypeModel.ScaleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class ScalesAdapter extends RecyclerView.Adapter<ScalesAdapter.ScalesHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> scales;

    public ScalesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ScalesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ScalesHolder(SingleItemScaleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScalesHolder holder, int i) {
        ScaleModel scale = (ScaleModel) scales.get(i);

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

    private void detector(ScalesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.createTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        }
    }

    private void listener(ScalesHolder holder, ScaleModel model) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        ClickManager.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCreateSampleFragment("scale", model);
            ((MainActivity) activity).navController.navigate(action);
        }).widget(holder.binding.createTextView);
    }

    private void setData(ScalesHolder holder, ScaleModel model) {
        if (holder.getBindingAdapterPosition() == 0)
            holder.binding.topView.setVisibility(View.GONE);
        else
            holder.binding.topView.setVisibility(View.VISIBLE);

        holder.binding.serialTextView.setText(model.getId());
        holder.binding.nameTextView.setText(model.getTitle());

        if (!model.getEdition().equals(""))
            holder.binding.editionTextView.setText(model.getEdition() + " - نسخه " + model.getVersion());
        else
            holder.binding.editionTextView.setText("نسخه " + model.getVersion());
    }

    public class ScalesHolder extends RecyclerView.ViewHolder {

        private SingleItemScaleBinding binding;

        public ScalesHolder(SingleItemScaleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}