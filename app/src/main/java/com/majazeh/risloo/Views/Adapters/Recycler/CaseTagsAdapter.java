package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Adapters.Holder.CaseTagsHolder;
import com.majazeh.risloo.databinding.SingleItemCaseTagBinding;
import com.mre.ligheh.Model.TypeModel.TagModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class CaseTagsAdapter extends RecyclerView.Adapter<CaseTagsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public CaseTagsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CaseTagsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CaseTagsHolder(SingleItemCaseTagBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CaseTagsHolder holder, int i) {
        TagModel model = (TagModel) items.get(i);

        detector(holder);

        listener(holder, model);

        setData(holder, model);
    }

    @Override
    public int getItemCount() {
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

    private void detector(CaseTagsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(CaseTagsHolder holder, TagModel model) {
        CustomClickView.onDelayedListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(CaseTagsHolder holder, TagModel model) {
        holder.binding.titleTextView.setText(model.getTitle());
    }

}