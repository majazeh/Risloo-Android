package com.majazeh.risloo.views.adapters.recycler.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.views.adapters.holder.dialog.DialogSelectedHolder;
import com.mre.ligheh.Model.TypeModel.ScaleModel;
import com.mre.ligheh.Model.TypeModel.TagModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabPayment;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateSessionTabPayment;
import com.majazeh.risloo.databinding.SingleItemDialogSelectedBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.ArrayList;

public class DialogSelectedAdapter extends RecyclerView.Adapter<DialogSelectedHolder> {

    // Fragments
    private Fragment payment;

    // Objects
    private Activity activity;

    // Widget
    private TextView countTextView;

    // Vars
    private ArrayList<TypeModel> items;
    private ArrayList<String> ids = new ArrayList<>();
    private String method;

    public DialogSelectedAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public DialogSelectedHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DialogSelectedHolder(SingleItemDialogSelectedBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DialogSelectedHolder holder, int i) {
        TypeModel model = items.get(i);

        intializer();

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
        payment = ((ActivityMain) activity).fragmont.getPayment();
    }

    private void listener(DialogSelectedHolder holder, int position) {
        CustomClickView.onDelayedListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        CustomClickView.onDelayedListener(() -> {
            removePayment(position);
            removeItem(position);
            refreshCount();
        }).widget(holder.binding.removeImageView);
    }

    private void setData(DialogSelectedHolder holder, TypeModel item) {
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
        if (payment instanceof FragmentCreateScheduleTabPayment)
            ((FragmentCreateScheduleTabPayment) payment).axisAdapter.removeItem(position);

        if (payment instanceof FragmentCreateSessionTabPayment)
            ((FragmentCreateSessionTabPayment) payment).axisAdapter.removeItem(position);
    }

    private void refreshCount() {
        if (DialogManager.getDialogSelected() != null)
            DialogManager.getDialogSelected().calculateCount();
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

}