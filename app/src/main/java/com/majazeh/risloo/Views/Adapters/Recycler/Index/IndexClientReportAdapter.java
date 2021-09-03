package com.majazeh.risloo.Views.Adapters.Recycler.Index;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Views.Adapters.Holder.Header.HeaderClientReportHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Index.IndexClientReportHolder;
import com.majazeh.risloo.databinding.HeaderItemIndexClientReportBinding;
import com.majazeh.risloo.databinding.SingleItemIndexClientReportBinding;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.ReportModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;

public class IndexClientReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public IndexClientReportAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderClientReportHolder(HeaderItemIndexClientReportBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new IndexClientReportHolder(SingleItemIndexClientReportBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof  IndexClientReportHolder) {
            ReportModel model = (ReportModel) items.get(i - 1);

            detector((IndexClientReportHolder) holder);

            listener((IndexClientReportHolder) holder, model);

            setData((IndexClientReportHolder) holder, model);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;

        return 1;
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size() + 1;
        else
            return 0;
    }

    public int itemsCount() {
        if (this.items != null)
            return items.size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items) {
        if (this.items == null)
            this.items = items;
        else
            this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        if (this.items != null) {
            this.items.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(IndexClientReportHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(IndexClientReportHolder holder, ReportModel model) {
        CustomClickView.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());
    }

    private void setData(IndexClientReportHolder holder, ReportModel model) {
        holder.binding.nameTextView.setText(model.getTitle());
        holder.binding.timeTextView.setText(DateManager.jalYYYYsMMsDD(String.valueOf(model.getReported_at()), "-"));

        setClients(holder, model.getClients());
        setViewers(holder, model.getViewers());
    }

    private void setClients(IndexClientReportHolder holder, List clients) {
        if (clients != null && clients.data().size() != 0) {
            holder.binding.referenceTextView.setText("");
            for (int i = 0; i < clients.data().size(); i++) {
                UserModel user = (UserModel) clients.data().get(i);
                if (user != null) {
                    holder.binding.referenceTextView.append(user.getName());
                    if (i != clients.data().size() - 1) {
                        holder.binding.referenceTextView.append("\n");
                    }
                }
            }
        }
    }

    private void setViewers(IndexClientReportHolder holder, List viewers) {
        if (viewers != null && viewers.data().size() != 0) {
            holder.binding.readersTextView.setText("");
            for (int i = 0; i < viewers.data().size(); i++) {
                UserModel user = (UserModel) viewers.data().get(i);
                if (user != null) {
                    holder.binding.readersTextView.append(user.getName());
                    if (i != viewers.data().size() - 1) {
                        holder.binding.readersTextView.append("\n");
                    }
                }
            }
        }
    }

}