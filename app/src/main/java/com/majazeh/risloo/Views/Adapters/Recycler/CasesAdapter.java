package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;

public class CasesAdapter extends RecyclerView.Adapter<CasesAdapter.CasesHolder> {

    // Vars
//    private ArrayList<Case> cases;

    // Objects
    private Activity activity;

    public CasesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CasesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.single_item_case, viewGroup, false);

        return new CasesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CasesHolder holder, int i) {
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

    private void detector(CasesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.itemView.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(CasesHolder holder) {
        holder.itemView.setOnClickListener(v -> {
            holder.itemView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.itemView.setClickable(true), 300);

            ((MainActivity) activity).navigator(R.id.caseFragment);
        });
    }

    private void setData(CasesHolder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.topView.setVisibility(View.GONE);
        } else {
            holder.topView.setVisibility(View.VISIBLE);
        }

        holder.serialTextView.setText("SE9666669");
        holder.roomTextView.setText("اتاق درمان محمدعلی نخلی");
        holder.centerTextView.setText("مرکز مشاوره ریسلو");
        holder.referenceTextView.setText("محمد نخلی");
    }

    public class CasesHolder extends RecyclerView.ViewHolder {

        private View topView;
        private TextView serialTextView, roomTextView, centerTextView, referenceTextView;

        public CasesHolder(View view) {
            super(view);
            topView = view.findViewById(R.id.single_item_case_top_view);
            serialTextView = view.findViewById(R.id.single_item_case_serial_textView);
            roomTextView = view.findViewById(R.id.single_item_case_room_textView);
            centerTextView = view.findViewById(R.id.single_item_case_center_textView);
            referenceTextView = view.findViewById(R.id.single_item_case_reference_textView);
        }
    }

}