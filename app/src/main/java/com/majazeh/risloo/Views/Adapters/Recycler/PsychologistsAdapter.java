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
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.databinding.SingleItemPsychologyBinding;
import com.squareup.picasso.Picasso;

public class PsychologistsAdapter extends RecyclerView.Adapter<PsychologistsAdapter.PsychologistsHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Psychology> psychologists;

    public PsychologistsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public PsychologistsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PsychologistsHolder(SingleItemPsychologyBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PsychologistsHolder holder, int i) {
//        Psychologists psychology = psychologists.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return psychologists.size();
        return 5;
    }

//    public void setPsychology(ArrayList<Psychology> psychologists) {
//        this.psychologists = psychologists;
//        notifyDataSetChanged();
//    }

    private void detector(PsychologistsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(PsychologistsHolder holder) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(PsychologistsHolder holder) {
        holder.binding.nameTextView.setText("ریلسو");

        holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
        holder.binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(holder.binding.nameTextView.getText().toString()));

        Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
    }

    public class PsychologistsHolder extends RecyclerView.ViewHolder {

        private SingleItemPsychologyBinding binding;

        public PsychologistsHolder(SingleItemPsychologyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}