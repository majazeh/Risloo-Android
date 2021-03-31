package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemReferenceBinding;

public class ReferencesAdapter extends RecyclerView.Adapter<ReferencesAdapter.ReferencesHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Reference> references;

    public ReferencesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ReferencesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ReferencesHolder(SingleItemReferenceBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReferencesHolder holder, int i) {
//        References Reference = references.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return references.size();
        return 5;
    }

//    public void setReference(ArrayList<Reference> references) {
//        this.references = references;
//        notifyDataSetChanged();
//    }

    private void detector(ReferencesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(ReferencesHolder holder) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.referenceFragment)).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(ReferencesHolder holder) {
        holder.binding.nameTextView.setText("ریلسو");
    }

    public class ReferencesHolder extends RecyclerView.ViewHolder {

        private SingleItemReferenceBinding binding;

        public ReferencesHolder(SingleItemReferenceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}