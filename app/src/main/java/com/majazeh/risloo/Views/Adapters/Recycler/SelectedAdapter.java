package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.mre.ligheh.Model.TypeModel.ScaleModel;
import com.mre.ligheh.Model.TypeModel.TagModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTabPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTabSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterTabDetailFragment;
import com.majazeh.risloo.databinding.SingleItemSelectedBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.ArrayList;

public class SelectedAdapter extends RecyclerView.Adapter<SelectedAdapter.SelectedHolder> {

    // Fragments
    private Fragment current, child, payment;

    // Objects
    private Activity activity;

    // Widget
    private TextView countTextView;

    // Vars
    private ArrayList<TypeModel> items;
    private ArrayList<String> ids;
    private String method;

    public SelectedAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public SelectedHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SelectedHolder(SingleItemSelectedBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedHolder holder, int i) {
        TypeModel model = items.get(i);

        intializer();

        detector(holder);

        listener(holder, i);

        setData(holder, model);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setItems(ArrayList<TypeModel> items, ArrayList<String> ids, String method, TextView countTextView) {
        this.items = items;
        this.ids = ids;
        this.method = method;
        this.countTextView = countTextView;
        notifyDataSetChanged();

        calculateCount();
    }

    public void clearItems() {
        items.clear();
        ids.clear();
        notifyDataSetChanged();

        calculateCount();
    }

    public void addItem(TypeModel item) {
        try {
            items.add(item);
            ids.add(item.object.getString("id"));
            notifyDataSetChanged();

            calculateCount();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void replaceItem(int position, TypeModel item) {
        try {
            items.set(position, item);
            ids.set(position, item.object.getString("id"));
            notifyItemChanged(position);
            notifyItemRangeChanged(position, getItemCount());

            calculateCount();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void removeItem(int position) {
        items.remove(position);
        ids.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());

        calculateCount();
    }

    private void intializer() {
        current = ((MainActivity) activity).fragmont.getCurrent();
        child = ((MainActivity) activity).fragmont.getChild();
        payment = ((MainActivity) activity).fragmont.getPayment();
    }

    private void detector(SelectedHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200_ripple_gray300);

            holder.binding.removeImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_gray50_ripple_red300);
        }
    }

    private void listener(SelectedHolder holder, int position) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            removePayment(position);
            removeItem(position);
            refreshCount();
        }).widget(holder.binding.removeImageView);
    }

    private void setData(SelectedHolder holder, TypeModel item) {
        try {
            switch (method) {
                case "scales": {
                    ScaleModel model = (ScaleModel) item;

                    holder.binding.titleTextView.setText(model.getTitle());

                    holder.binding.subTextView.setVisibility(View.VISIBLE);
                    if (!model.getEdition().equals(""))
                        holder.binding.subTextView.setText(model.getEdition() + " - نسخه " + model.getVersion());
                    else
                        holder.binding.subTextView.setText("نسخه " + model.getVersion());
                } break;
                case "references": {
                    UserModel model = (UserModel) item;

                    holder.binding.titleTextView.setText(model.getName());

                    holder.binding.subTextView.setVisibility(View.GONE);
                    holder.binding.subTextView.setText("");
                } break;
                case "tags": {
                    TagModel model = (TagModel) item;

                    holder.binding.titleTextView.setText(model.getTitle());

                    holder.binding.subTextView.setVisibility(View.GONE);
                    holder.binding.subTextView.setText("");
                } break;
                case "phones":
                case "axises":
                case "patternDays": {
                    holder.binding.titleTextView.setText(item.object.getString("id"));

                    holder.binding.subTextView.setVisibility(View.GONE);
                    holder.binding.subTextView.setText("");
                } break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void removePayment(int position) {
        if (payment instanceof CreateScheduleTabPaymentFragment)
            ((CreateScheduleTabPaymentFragment) payment).axisAdapter.removeItem(position);

        if (payment instanceof CreateSessionTabPaymentFragment)
            ((CreateSessionTabPaymentFragment) payment).axisAdapter.removeItem(position);
    }

    private void refreshCount() {
        if (current instanceof CreateCenterFragment) {
            if (method.equals("phones"))
                if (((CreateCenterFragment) current).phonesDialog.isVisible())
                    ((CreateCenterFragment) current).phonesDialog.calculateCount();
        }

        if (child instanceof CreateScheduleTabSessionFragment) {
            if (method.equals("axises"))
                if (((CreateScheduleTabSessionFragment) child).axisesDialog.isVisible())
                    ((CreateScheduleTabSessionFragment) child).axisesDialog.calculateCount();
        }

        if (child instanceof CreateSessionTabSessionFragment) {
            if (method.equals("axises"))
                if (((CreateSessionTabSessionFragment) child).axisesDialog.isVisible())
                    ((CreateSessionTabSessionFragment) child).axisesDialog.calculateCount();
        }

        if (child instanceof EditCenterTabDetailFragment) {
            if (method.equals("phones"))
                if (((EditCenterTabDetailFragment) child).phonesDialog.isVisible())
                    ((EditCenterTabDetailFragment) child).phonesDialog.calculateCount();
        }
    }

    private void calculateCount() {
        if (getItemCount() != 0) {
            countTextView.setVisibility(View.VISIBLE);
            countTextView.setText(StringManager.bracing(getItemCount()));
        } else {
            countTextView.setVisibility(View.GONE);
            countTextView.setText("");
        }
    }

    public class SelectedHolder extends RecyclerView.ViewHolder {

        private SingleItemSelectedBinding binding;

        public SelectedHolder(SingleItemSelectedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}