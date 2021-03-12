package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;

public class Cases2Adapter extends RecyclerView.Adapter<Cases2Adapter.Cases2Holder> {

    // Vars
//    private ArrayList<Case> cases;

    // Objects
    private Activity activity;

    public Cases2Adapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Cases2Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.single_item_case2, viewGroup, false);

        return new Cases2Holder(view);
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
        return 15;
    }

//    public void setCase(ArrayList<Case> cases) {
//        this.cases = cases;
//        notifyDataSetChanged();
//    }

    private void detector(Cases2Holder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.itemConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(Cases2Holder holder) {
        holder.itemConstraintLayout.setOnClickListener(v -> {
            holder.itemConstraintLayout.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.itemConstraintLayout.setClickable(true), 300);

//            ((MainActivity) activity).navigator(R.id.caseFragment);
        });
    }

    private void setData(Cases2Holder holder) {
        holder.serialTextView.setText("RS96666DQ");
        holder.referenceTextView.setText("حسن حسینی");
        holder.dateTextView.setText("سه شنبه 19 اسفند 99");
        holder.sessionCountTextView.setText("1 جلسه");
    }

    public class Cases2Holder extends RecyclerView.ViewHolder {

        private ConstraintLayout itemConstraintLayout;
        private TextView serialTextView, referenceTextView, dateTextView, sessionCountTextView;

        public Cases2Holder(View view) {
            super(view);
            itemConstraintLayout = view.findViewById(R.id.single_item_case2_constraintLayout);
            serialTextView = view.findViewById(R.id.single_item_case2_serial_textView);
            referenceTextView = view.findViewById(R.id.single_item_case2_reference_textView);
            dateTextView = view.findViewById(R.id.single_item_case2_date_textView);
            sessionCountTextView = view.findViewById(R.id.single_item_case2_session_count_textView);
        }
    }

}