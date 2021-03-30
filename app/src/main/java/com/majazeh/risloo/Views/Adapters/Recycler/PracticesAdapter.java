package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemPracticeBinding;

public class PracticesAdapter extends RecyclerView.Adapter<PracticesAdapter.PracticesHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Practice> practices;

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
//        Practices practice = practices.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return practices.size();
        return 5;
    }

//    public void setPractice(ArrayList<Practice> practices) {
//        this.practices = practices;
//        notifyDataSetChanged();
//    }

    private void detector(PracticesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.attachmentImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_gray300);
            holder.binding.practiceImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_gray300);
        }
    }

    private void listener(PracticesHolder holder) {
        holder.binding.getRoot().setOnClickListener(v -> {
            holder.binding.getRoot().setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.getRoot().setClickable(true), 300);

            // TODO : Place Code Here
        });

        holder.binding.attachmentImageView.setOnClickListener(v -> {
            holder.binding.attachmentImageView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.attachmentImageView.setClickable(true), 300);

            // TODO : Place Code Here
        });

        holder.binding.practiceImageView.setOnClickListener(v -> {
            holder.binding.practiceImageView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.practiceImageView.setClickable(true), 300);

            // TODO : Place Code Here
        });
    }

    private void setData(PracticesHolder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText("P966663D");
        holder.binding.nameTextView.setText("تمرین abc");
        holder.binding.descriptionTextView.setText("چرا عاقل کند کاری که بازآید به کنعان غم مخور");
    }

    public class PracticesHolder extends RecyclerView.ViewHolder {

        private SingleItemPracticeBinding binding;

        public PracticesHolder(SingleItemPracticeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}