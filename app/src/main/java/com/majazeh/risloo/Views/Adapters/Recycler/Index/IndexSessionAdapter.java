package com.majazeh.risloo.Views.Adapters.Recycler.Index;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Header.HeaderSessionHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Index.IndexSessionHolder;
import com.majazeh.risloo.databinding.HeaderItemIndexSessionBinding;
import com.majazeh.risloo.databinding.SingleItemIndexSessionBinding;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class IndexSessionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public IndexSessionAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderSessionHolder(HeaderItemIndexSessionBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new IndexSessionHolder(SingleItemIndexSessionBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof  IndexSessionHolder) {
            SessionModel model = (SessionModel) items.get(i - 1);

            detector((IndexSessionHolder) holder);

            listener((IndexSessionHolder) holder, model);

            setData((IndexSessionHolder) holder, model);
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

    private void detector(IndexSessionHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.editImageView.setBackgroundResource(R.drawable.draw_oval_solid_white_ripple_gray300);
        }
    }

    private void listener(IndexSessionHolder holder, SessionModel model) {
        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalSessionFragment("session", model);
            ((MainActivity) activity).navController.navigate(action);
        }).widget(holder.binding.getRoot());

        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalEditSessionFragment(model);
            ((MainActivity) activity).navController.navigate(action);
        }).widget(holder.binding.editImageView);
    }

    private void setData(IndexSessionHolder holder, SessionModel model) {
        try {
            holder.binding.serialTextView.setText(model.getId());

            if (model.getRoom() != null && model.getRoom().getRoomManager() != null) {
                holder.binding.roomTextView.setText(model.getRoom().getRoomManager().getName());
            }

            if (model.getRoom() != null && model.getRoom().getRoomCenter() != null && model.getRoom().getRoomCenter().getDetail().has("title") && !model.getRoom().getRoomCenter().getDetail().isNull("title")) {
                holder.binding.centerTextView.setText(model.getRoom().getRoomCenter().getDetail().getString("title"));
            }

            holder.binding.startTimeTextView.setText(DateManager.jalYYYYsNMMsDDsNDDnlHHsMM(String.valueOf(model.getStarted_at()), " "));
            holder.binding.durationTextView.setText(model.getDuration() + " " + "دقیقه");
            holder.binding.statusTextView.setText(SelectionManager.getSessionStatus(activity, "fa", model.getStatus()));

            if (model.getCaseModel() != null) {
                holder.binding.caseTextView.setText(model.getCaseModel().getCaseId());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}