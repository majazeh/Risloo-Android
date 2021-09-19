package com.majazeh.risloo.Views.Adapters.Recycler.Index;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Header.HeaderCaseHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Index.IndexCaseHolder;
import com.majazeh.risloo.databinding.HeaderItemIndexCaseBinding;
import com.majazeh.risloo.databinding.SingleItemIndexCaseBinding;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.ArrayList;

public class IndexCaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public IndexCaseAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderCaseHolder(HeaderItemIndexCaseBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new IndexCaseHolder(SingleItemIndexCaseBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof  IndexCaseHolder) {
            CaseModel model = (CaseModel) items.get(i - 1);

            listener((IndexCaseHolder) holder, model);

            setData((IndexCaseHolder) holder, model);
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

    private void listener(IndexCaseHolder holder, CaseModel model) {
        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCaseFragment(model);
            ((MainActivity) activity).navController.navigate(action);
        }).widget(holder.binding.getRoot());
    }

    private void setData(IndexCaseHolder holder, CaseModel model) {
        try {
            holder.binding.serialTextView.setText(model.getCaseId());

            if (model.getCaseManager() != null) {
                holder.binding.roomTextView.setText(model.getCaseManager().getName());
            }

            if (model.getCaseRoom() != null && model.getCaseRoom().getRoomCenter() != null && model.getCaseRoom().getRoomCenter().getDetail().has("title") && !model.getCaseRoom().getRoomCenter().getDetail().isNull("title")) {
                holder.binding.centerTextView.setText(model.getCaseRoom().getRoomCenter().getDetail().getString("title"));
            }

            setClients(holder, model.getClients());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setClients(IndexCaseHolder holder, List clients) {
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

}