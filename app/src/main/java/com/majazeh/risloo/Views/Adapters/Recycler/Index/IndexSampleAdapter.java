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
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Header.HeaderSampleHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Index.IndexSampleHolder;
import com.majazeh.risloo.databinding.HeaderItemIndexSampleBinding;
import com.majazeh.risloo.databinding.SingleItemIndexSampleBinding;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class IndexSampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public IndexSampleAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderSampleHolder(HeaderItemIndexSampleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new IndexSampleHolder(SingleItemIndexSampleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof  IndexSampleHolder) {
            SampleModel model = (SampleModel) items.get(i - 1);

            detector((IndexSampleHolder) holder);

            listener((IndexSampleHolder) holder, model);

            setData((IndexSampleHolder) holder, model);
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

    private void detector(IndexSampleHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(IndexSampleHolder holder, SampleModel model) {
        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalSampleFragment(model);
            ((MainActivity) activity).navController.navigate(action);
        }).widget(holder.binding.getRoot());

        CustomClickView.onClickListener(() -> {
            IntentManager.test(activity, model.getSampleId());
        }).widget(holder.binding.statusTextView);
    }

    private void setData(IndexSampleHolder holder, SampleModel model) {
        holder.binding.serialTextView.setText(model.getSampleId());
        holder.binding.nameTextView.setText(model.getSampleScaleTitle());

        if (!model.getSampleEdition().equals(""))
            holder.binding.editionTextView.setText(model.getSampleEdition() + " - نسخه " + model.getSampleVersion());
        else
            holder.binding.editionTextView.setText("نسخه " + model.getSampleVersion());

        if (model.getSampleRoom() != null && model.getSampleRoom().getRoomManager() != null) {
            holder.binding.roomTextView.setText(model.getSampleRoom().getRoomManager().getName());
        }

        if (model.getSampleCase() != null) {
            holder.binding.caseTextView.setText(model.getSampleCase().getCaseId());
        }

        if (model.getClient() != null) {
            holder.binding.referenceTextView.setText(model.getClient().getName());
        }

        setStatus(holder, model.getSampleStatus());
    }

    private void setStatus(IndexSampleHolder holder, String status) {
        holder.binding.statusTextView.setText(SelectionManager.getSampleStatus2(activity, "fa", status));

        switch (status) {
            case "seald":
            case "open":
                holder.binding.statusTextView.setEnabled(true);
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Green600));

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                    holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
                else
                    holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
                break;
            default:
                holder.binding.statusTextView.setEnabled(false);
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Gray700));

                holder.binding.statusTextView.setBackgroundResource(android.R.color.transparent);
                break;
        }
    }

}