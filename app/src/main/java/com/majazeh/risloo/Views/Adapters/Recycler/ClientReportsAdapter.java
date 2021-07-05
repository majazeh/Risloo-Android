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
import com.majazeh.risloo.databinding.SingleItemClientReportBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class ClientReportsAdapter extends RecyclerView.Adapter<ClientReportsAdapter.ClientReportsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> reports;

    public ClientReportsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ClientReportsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ClientReportsHolder(SingleItemClientReportBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClientReportsHolder holder, int i) {
//        ReportModel report = (ReportModel) reports.get(i);
//
//        detector(holder);
//
//        listener(holder, report);
//
//        setData(holder, report);
    }

    @Override
    public int getItemCount() {
        if (this.reports != null)
            return reports.size();
        else
            return 0;
    }

    public void setReports(ArrayList<TypeModel> reports) {
        if (this.reports == null)
            this.reports = reports;
        else
            this.reports.addAll(reports);
        notifyDataSetChanged();
    }

    public void clearReports() {
        if (this.reports != null) {
            this.reports.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(ClientReportsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(ClientReportsHolder holder) {
        ClickManager.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());
    }

    private void setData(ClientReportsHolder holder) {
        if (holder.getBindingAdapterPosition() == 0)
            holder.binding.topView.setVisibility(View.GONE);
        else
            holder.binding.topView.setVisibility(View.VISIBLE);

        holder.binding.nameTextView.setText("مشکلی موجود نمی باشد.");
        holder.binding.timeTextView.setText("1400/01/01\n20/00");
        holder.binding.referenceTextView.setText("محمد حسن صالحی");
        holder.binding.readersTextView.setText("محمد علی نخلی");
    }

    public class ClientReportsHolder extends RecyclerView.ViewHolder {

        private SingleItemClientReportBinding binding;

        public ClientReportsHolder(SingleItemClientReportBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}