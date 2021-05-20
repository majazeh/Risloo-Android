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
import com.majazeh.risloo.databinding.SingleItemSessionReportBinding;

public class SessionReportsAdapter extends RecyclerView.Adapter<SessionReportsAdapter.SessionReportsHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Report> reports;

    public SessionReportsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public SessionReportsAdapter.SessionReportsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SessionReportsAdapter.SessionReportsHolder(SingleItemSessionReportBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SessionReportsHolder holder, int i) {
//        Reports report = reports.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return reports.size();
        return 4;
    }

//    public void setReports(ArrayList<Report> reports) {
//        this.reports = reports;
//        notifyDataSetChanged();
//    }

    private void detector(SessionReportsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(SessionReportsHolder holder) {
        ClickManager.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());
    }

    private void setData(SessionReportsHolder holder) {
        if (holder.getBindingAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.nameTextView.setText("مشکلی موجود نمی باشد.");
        holder.binding.timeTextView.setText("1400/01/01\n20/00");
        holder.binding.referenceTextView.setText("محمد حسن صالحی");
        holder.binding.readersTextView.setText("محمد علی نخلی");
    }

    public class SessionReportsHolder extends RecyclerView.ViewHolder {

        private SingleItemSessionReportBinding binding;

        public SessionReportsHolder(SingleItemSessionReportBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}