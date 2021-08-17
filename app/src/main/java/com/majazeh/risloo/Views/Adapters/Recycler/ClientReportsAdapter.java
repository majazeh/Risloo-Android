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
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Views.Adapters.Holder.ClientReportsHolder;
import com.majazeh.risloo.databinding.SingleItemClientReportBinding;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.ReportModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;

public class ClientReportsAdapter extends RecyclerView.Adapter<ClientReportsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

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
        ReportModel model = (ReportModel) items.get(i);

        detector(holder);

        listener(holder, model);

        setData(holder, model);
    }

    @Override
    public int getItemCount() {
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

    private void detector(ClientReportsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(ClientReportsHolder holder, ReportModel model) {
        ClickManager.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());
    }

    private void setData(ClientReportsHolder holder, ReportModel model) {
        if (holder.getBindingAdapterPosition() == 0)
            holder.binding.topView.setVisibility(View.GONE);
        else
            holder.binding.topView.setVisibility(View.VISIBLE);

        holder.binding.nameTextView.setText(model.getTitle());
        holder.binding.timeTextView.setText(DateManager.jalYYYYsMMsDD(String.valueOf(model.getReported_at()), "-"));

        setClients(holder, model.getClients());
        setViewers(holder, model.getViewers());
    }

    private void setClients(ClientReportsHolder holder, List clients) {
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

    private void setViewers(ClientReportsHolder holder, List viewers) {
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