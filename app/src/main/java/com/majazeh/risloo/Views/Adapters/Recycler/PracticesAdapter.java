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
import com.majazeh.risloo.databinding.SingleItemPracticeBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class PracticesAdapter extends RecyclerView.Adapter<PracticesAdapter.PracticesHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> practices;

    public PracticesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public PracticesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PracticesHolder(SingleItemPracticeBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PracticesHolder holder, int i) {
//        PracticeModel practice = (PracticeModel) practices.get(i);
//
//        detector(holder);
//
//        listener(holder, practice);
//
//        setData(holder, practice);
    }

    @Override
    public int getItemCount() {
        if (this.practices != null)
            return practices.size();
        else
            return 0;
    }

    public void setPractices(ArrayList<TypeModel> practices) {
        if (this.practices == null)
            this.practices = practices;
        else
            this.practices.addAll(practices);
        notifyDataSetChanged();
    }

    public void clearPractices() {
        if (this.practices != null) {
            this.practices.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(PracticesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.attachmentImageView.setBackgroundResource(R.drawable.draw_oval_solid_white_ripple_gray300);
            holder.binding.workImageView.setBackgroundResource(R.drawable.draw_oval_solid_white_ripple_gray300);
        }
    }

    private void listener(PracticesHolder holder) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.attachmentImageView);

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.workImageView);
    }

    private void setData(PracticesHolder holder) {
        if (holder.getAbsoluteAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText("P966663D");
        holder.binding.nameTextView.setText("تمرین abc");
        holder.binding.descriptionTextView.setText("چرا عاقل کند کاری که بازآید به کنعان غم مخور");
    }

//    private Bundle getExtras(PracticeModel model) {
//        Bundle extras = new Bundle();
//
//        return extras;
//    }

    public class PracticesHolder extends RecyclerView.ViewHolder {

        private SingleItemPracticeBinding binding;

        public PracticesHolder(SingleItemPracticeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}