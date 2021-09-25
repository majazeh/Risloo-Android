package com.majazeh.risloo.Views.Adapters.Recycler.Create;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Adapters.Holder.Create.CreateCheckHolder;
import com.majazeh.risloo.databinding.SingleItemCreateCheckBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class CreateCheckAdapter extends RecyclerView.Adapter<CreateCheckHolder> {

    // Objects
    private Activity activity;

    // Widget
    private TextView countTextView;

    // Vars
    private ArrayList<TypeModel> items;
    private ArrayList<String> ids = new ArrayList<>();

    public CreateCheckAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CreateCheckHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CreateCheckHolder(SingleItemCreateCheckBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CreateCheckHolder holder, int i) {
        TypeModel model = items.get(i);

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

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setItems(ArrayList<TypeModel> items, ArrayList<String> ids, TextView countTextView) {
        this.items = items;
        this.ids = ids;
        this.countTextView = countTextView;
        notifyDataSetChanged();
    }

    public void clearItems() {
        if (this.items != null) {
            items.clear();
            ids.clear();
            notifyDataSetChanged();
        }
    }

    private void listener(CreateCheckHolder holder, TypeModel model) {
        holder.binding.getRoot().setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                if (isChecked)
                    ids.add(model.object.getString("id"));
                else
                    ids.remove(model.object.getString("id"));

                calculateCount();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void setData(CreateCheckHolder holder, TypeModel model) {
        try {
            holder.binding.getRoot().setText(model.object.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void calculateCount() {
        if (ids.size() != 0) {
            countTextView.setVisibility(View.VISIBLE);
            countTextView.setText(StringManager.bracing(ids.size()));
        } else {
            countTextView.setVisibility(View.GONE);
            countTextView.setText("");
        }
    }

}