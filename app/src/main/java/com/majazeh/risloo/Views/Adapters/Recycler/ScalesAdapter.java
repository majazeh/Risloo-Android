package com.majazeh.risloo.Views.Adapters.Recycler;

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
import com.majazeh.risloo.Views.Adapters.Holder.ScalesHolder;
import com.majazeh.risloo.Views.Fragments.Index.ScalesFragment;
import com.majazeh.risloo.databinding.SingleItemScaleBinding;
import com.mre.ligheh.Model.TypeModel.ScaleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;

public class ScalesAdapter extends RecyclerView.Adapter<ScalesHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public ScalesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ScalesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ScalesHolder(SingleItemScaleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScalesHolder holder, int i) {
        ScaleModel model = (ScaleModel) items.get(i);

        initializer(holder);

        detector(holder);

        listener(holder, model);

        setPermission(holder);

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

    private void initializer(ScalesHolder holder) {
        current = ((MainActivity) activity).fragmont.getCurrent();
    }

    private void detector(ScalesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.createTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        }
    }

    private void listener(ScalesHolder holder, ScaleModel model) {
        CustomClickView.onDelayedListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCreateSampleFragment("scale", model);
            ((MainActivity) activity).navController.navigate(action);
        }).widget(holder.binding.createTextView);
    }

    private void setPermission(ScalesHolder holder) {
        UserModel model = ((MainActivity) activity).singleton.getUserModel();

        if (current instanceof ScalesFragment && ((MainActivity) activity).permissoon.showScalesCreateSample(model))
            holder.binding.createTextView.setVisibility(View.VISIBLE);
        else
            holder.binding.createTextView.setVisibility(View.GONE);
    }

    private void setData(ScalesHolder holder, ScaleModel model) {
        if (holder.getBindingAdapterPosition() == 0)
            holder.binding.topView.setVisibility(View.GONE);
        else
            holder.binding.topView.setVisibility(View.VISIBLE);

        holder.binding.serialTextView.setText(model.getId());
        holder.binding.nameTextView.setText(model.getTitle());

        if (!model.getEdition().equals(""))
            holder.binding.editionTextView.setText(model.getEdition() + " - نسخه " + model.getVersion());
        else
            holder.binding.editionTextView.setText("نسخه " + model.getVersion());
    }

}