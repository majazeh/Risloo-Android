package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemCase2Binding;

public class Cases2Adapter extends RecyclerView.Adapter<Cases2Adapter.Cases2Holder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Case> cases;

    public Cases2Adapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Cases2Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Cases2Holder(SingleItemCase2Binding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Cases2Holder holder, int i) {
//        Cases case = cases.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return cases.size();
        return 5;
    }

//    public void setCase(ArrayList<Case> cases) {
//        this.cases = cases;
//        notifyDataSetChanged();
//    }

    private void detector(Cases2Holder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(Cases2Holder holder) {
        holder.binding.containerConstraintLayout.setOnClickListener(v -> {
            holder.binding.containerConstraintLayout.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.containerConstraintLayout.setClickable(true), 300);

            ((MainActivity) activity).navigator(R.id.caseFragment);
        });
    }

    private void setData(Cases2Holder holder) {
        holder.binding.serialTextView.setText("RS96666DQ");
        holder.binding.referenceTextView.setText("حسن حسینی");
        holder.binding.dateTextView.setText("سه شنبه 19 اسفند 99");
        holder.binding.sessionCountTextView.setText("1 جلسه");
    }

    public class Cases2Holder extends RecyclerView.ViewHolder {

        private SingleItemCase2Binding binding;

        public Cases2Holder(SingleItemCase2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}