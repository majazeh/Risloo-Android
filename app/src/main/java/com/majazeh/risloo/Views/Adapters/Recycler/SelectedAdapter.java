package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Model;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.CreateSampleFragment;
import com.majazeh.risloo.databinding.SingleItemSelectedBinding;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

public class SelectedAdapter extends RecyclerView.Adapter<SelectedAdapter.SelectedHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<Model> items;
    private ArrayList<String> ids = new ArrayList<>();
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
        Model item = items.get(i);

        detector(holder);

        listener(holder, i);

        setData(holder, item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<Model> getItems() {
        return items;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setItems(ArrayList<Model> items, String method) {
        this.items = items;
        this.method = method;
        notifyDataSetChanged();
    }

    public void clearItems() {
        items.clear();
        ids.clear();
        notifyDataSetChanged();
    }

    public void addItem(Model item) {
        items.add(item);

        try {
            ids.add(item.get("id").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    public void replaceItem(int position, Model item) {
        items.set(position, item);

        try {
            ids.set(position, item.get("id").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        notifyItemChanged(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void removeItem(int position) {
        items.remove(position);
        ids.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    private void detector(SelectedHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200_ripple_gray300);

            holder.binding.removeImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_gray50_ripple_red300);
        }
    }

    private void listener(SelectedHolder holder, int position) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            removeItem(position);

            switch (Objects.requireNonNull(((MainActivity) activity).navController.getCurrentDestination()).getId()) {
                case R.id.createSampleFragment:
                    CreateSampleFragment createSampleFragment = (CreateSampleFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createSampleFragment != null) {
                        if (method.equals("scales")) {
                            createSampleFragment.scalesDialog.searchableAdapter.notifyDataSetChanged();
                        } else if (method.equals("references")) {
                            createSampleFragment.referencesDialog.searchableAdapter.notifyDataSetChanged();
                        }
                    }
                    break;
            }
        }).widget(holder.binding.removeImageView);
    }

    private void setData(SelectedHolder holder, Model item) {
        try {
            switch (method) {
                case "scales":
                    ids.add(item.get("id").toString());

                    holder.binding.titleTextView.setText(item.get("title").toString());

                    holder.binding.subTextView.setVisibility(View.VISIBLE);
                    holder.binding.subTextView.setText(item.get("subtitle").toString());
                    break;
                case "references":
                    ids.add(item.get("id").toString());

                    holder.binding.titleTextView.setText(item.get("title").toString());

                    holder.binding.subTextView.setVisibility(View.GONE);
                    holder.binding.subTextView.setText("");
                    break;
                case "phones":
                    ids.add(item.get("id").toString());

                    holder.binding.titleTextView.setText(item.get("phone").toString());

                    holder.binding.subTextView.setVisibility(View.GONE);
                    holder.binding.subTextView.setText("");
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
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