package com.majazeh.risloo.Views.Adapters.Recycler.Main.Index;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Index.IndexCaseHolder;
import com.majazeh.risloo.databinding.SingleItemIndexCaseBinding;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;

public class IndexCaseAdapter extends RecyclerView.Adapter<IndexCaseHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public IndexCaseAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public IndexCaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IndexCaseHolder(SingleItemIndexCaseBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IndexCaseHolder holder, int i) {
        CaseModel model = (CaseModel) items.get(i);

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

    private void listener(IndexCaseHolder holder, CaseModel model) {
        CustomClickView.onClickListener(() -> {
            ((MainActivity) activity).navigatoon.navigateToCaseFragment(model);
        }).widget(holder.binding.getRoot());
    }

    private void setData(IndexCaseHolder holder, CaseModel model) {
        holder.binding.serialTextView.setText(model.getId());
        holder.binding.dateTextView.setText(DateManager.jalYYYYsNMMsDDsNDD(String.valueOf(model.getCreatedAt()), " "));
        holder.binding.sessionCountTextView.setText(model.getSessionsCount() + " " + activity.getResources().getString(R.string.CaseAdapterSession));

        setClients(holder, model.getClients());
    }

    private void setClients(IndexCaseHolder holder, List clients) {
        if (clients != null && clients.data().size() != 0) {
            holder.binding.referenceTextView.setText("");
            for (int i = 0; i < clients.data().size(); i++) {
                UserModel model = (UserModel) clients.data().get(i);
                if (model != null) {
                    holder.binding.referenceTextView.append(model.getName());
                    if (i != clients.data().size() - 1) {
                        holder.binding.referenceTextView.append("  -  ");
                    }
                }
            }
        }
    }

}