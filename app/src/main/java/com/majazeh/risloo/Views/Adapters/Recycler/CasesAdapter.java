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
import com.majazeh.risloo.databinding.SingleItemCaseBinding;

public class CasesAdapter extends RecyclerView.Adapter<CasesAdapter.CasesHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Case> cases;

    public CasesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CasesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CasesHolder(SingleItemCaseBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
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
            holder.binding.singleItemCase.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
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
            holder.binding.singleItemCaseTopView.setVisibility(View.GONE);
        } else {
            holder.binding.singleItemCaseTopView.setVisibility(View.VISIBLE);
        }

        holder.binding.singleItemCaseSerialTextView.setText("SE9666669");
        holder.binding.singleItemCaseRoomTextView.setText("اتاق درمان محمدعلی نخلی");
        holder.binding.singleItemCaseCenterTextView.setText("مرکز مشاوره ریسلو");
        holder.binding.singleItemCaseReferenceTextView.setText("محمد نخلی");
    }

    public class CasesHolder extends RecyclerView.ViewHolder {

        private SingleItemCaseBinding binding;

        public CasesHolder(SingleItemCaseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}