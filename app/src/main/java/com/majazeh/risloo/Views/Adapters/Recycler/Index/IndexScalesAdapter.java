package com.majazeh.risloo.Views.Adapters.Recycler.Index;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Header.HeaderScaleHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Index.IndexScaleHolder;
import com.majazeh.risloo.Views.Fragments.Index.ScalesFragment;
import com.majazeh.risloo.databinding.HeaderItemIndexScaleBinding;
import com.majazeh.risloo.databinding.SingleItemIndexScaleBinding;
import com.mre.ligheh.Model.TypeModel.ScaleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;

public class IndexScalesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public IndexScalesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderScaleHolder(HeaderItemIndexScaleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new IndexScaleHolder(SingleItemIndexScaleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof  IndexScaleHolder) {
            ScaleModel model = (ScaleModel) items.get(i - 1);

            initializer((IndexScaleHolder) holder);

            detector((IndexScaleHolder) holder);

            listener((IndexScaleHolder) holder, model);

            setPermission((IndexScaleHolder) holder);

            setData((IndexScaleHolder) holder, model);
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

    private void initializer(IndexScaleHolder holder) {
        current = ((MainActivity) activity).fragmont.getCurrent();
    }

    private void detector(IndexScaleHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.createTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        }
    }

    private void listener(IndexScaleHolder holder, ScaleModel model) {
        CustomClickView.onDelayedListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCreateSampleFragment("scale", model);
            ((MainActivity) activity).navController.navigate(action);
        }).widget(holder.binding.createTextView);
    }

    private void setPermission(IndexScaleHolder holder) {
        UserModel model = ((MainActivity) activity).singleton.getUserModel();

        if (current instanceof ScalesFragment && ((MainActivity) activity).permissoon.showScalesCreateSample(model))
            holder.binding.createTextView.setVisibility(View.VISIBLE);
        else
            holder.binding.createTextView.setVisibility(View.GONE);
    }

    private void setData(IndexScaleHolder holder, ScaleModel model) {
        holder.binding.serialTextView.setText(model.getId());
        holder.binding.nameTextView.setText(model.getTitle());

        if (!model.getEdition().equals(""))
            holder.binding.editionTextView.setText(model.getEdition() + " - نسخه " + model.getVersion());
        else
            holder.binding.editionTextView.setText("نسخه " + model.getVersion());
    }

}