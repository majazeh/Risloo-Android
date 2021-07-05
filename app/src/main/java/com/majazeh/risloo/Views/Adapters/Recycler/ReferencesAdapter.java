package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.databinding.SingleItemReferenceBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;

public class ReferencesAdapter extends RecyclerView.Adapter<ReferencesAdapter.ReferencesHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> references;

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
        UserModel reference = (UserModel) references.get(i);

        detector(holder);

        listener(holder, reference);

        setData(holder, reference);
    }

    @Override
    public int getItemCount() {
        if (this.references != null)
            return references.size();
        else
            return 0;
    }

    public void setReferences(ArrayList<TypeModel> references) {
        if (this.references == null)
            this.references = references;
        else
            this.references.addAll(references);
        notifyDataSetChanged();
    }

    public void clearReferences() {
        if (this.references != null) {
            this.references.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(ReferencesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(ReferencesHolder holder, UserModel model) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(ReferencesHolder holder, UserModel model) {
        holder.binding.nameTextView.setText(model.getName());
    }

    public class ReferencesHolder extends RecyclerView.ViewHolder {

        private SingleItemReferenceBinding binding;

        public ReferencesHolder(SingleItemReferenceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}