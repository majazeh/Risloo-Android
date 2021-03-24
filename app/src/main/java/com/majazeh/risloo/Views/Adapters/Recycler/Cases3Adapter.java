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
import com.majazeh.risloo.databinding.SingleItemCase3Binding;

public class Cases3Adapter extends RecyclerView.Adapter<Cases3Adapter.Cases3Holder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Case> cases;

    public Cases3Adapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Cases3Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Cases3Holder(SingleItemCase3Binding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Cases3Holder holder, int i) {
//        Cases case = cases.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return cases.size();
        return 12;
    }

//    public void setCase(ArrayList<Case> cases) {
//        this.cases = cases;
//        notifyDataSetChanged();
//    }

    private void detector(Cases3Holder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(Cases3Holder holder) {
        holder.binding.getRoot().setOnClickListener(v -> {
            holder.binding.getRoot().setClickable(false);

            ((MainActivity) activity).navigator(R.id.caseFragment);
        });
    }

    private void setData(Cases3Holder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText("SE9666669");
        holder.binding.roomTextView.setText("اتاق درمان محمدعلی نخلی");
        holder.binding.sessionCountTextView.setText("4");
    }

    public class Cases3Holder extends RecyclerView.ViewHolder {

        private SingleItemCase3Binding binding;

        public Cases3Holder(SingleItemCase3Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}