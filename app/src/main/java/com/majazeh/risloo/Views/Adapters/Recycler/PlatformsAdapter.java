package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.databinding.SingleItemPlatformBinding;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class PlatformsAdapter extends RecyclerView.Adapter<PlatformsAdapter.PlatformsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public PlatformsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public PlatformsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PlatformsHolder(SingleItemPlatformBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlatformsHolder holder, int i) {
        SessionPlatformModel model = (SessionPlatformModel) items.get(i);

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

    private void detector(PlatformsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(PlatformsHolder holder, SessionPlatformModel model) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(PlatformsHolder holder, SessionPlatformModel model) {
        String title = model.getTitle() + " " + StringManager.bracing(SelectionManager.getPlatformSession(activity, "fa", model.getType())) + " :";

        holder.binding.titleTextView.setText(StringManager.foregroundSize(title, model.getTitle().length() + 1, title.length(), activity.getResources().getColor(R.color.Gray500), (int) activity.getResources().getDimension(R.dimen._8ssp)));
        holder.binding.valueTextView.setText(model.getIdentifier());
    }

    public class PlatformsHolder extends RecyclerView.ViewHolder {

        private SingleItemPlatformBinding binding;

        public PlatformsHolder(SingleItemPlatformBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}