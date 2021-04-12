package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Model;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.CreateSampleFragment;
import com.majazeh.risloo.databinding.PopItemRecyclerSingleBinding;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

public class RecyclerSingleAdapter extends RecyclerView.Adapter<RecyclerSingleAdapter.RecyclerSingleHandler> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<Model> items;
    private ArrayList<String> ids = new ArrayList<>();
    private String method;

    public RecyclerSingleAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerSingleHandler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyclerSingleHandler(PopItemRecyclerSingleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerSingleHandler holder, int i) {
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

    private void detector(RecyclerSingleHandler holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200_ripple_gray300);

            holder.binding.removeImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_gray50_ripple_red300);
        }
    }

    private void listener(RecyclerSingleHandler holder, int position) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            removeItem(position);

            switch (Objects.requireNonNull(((MainActivity) activity).navController.getCurrentDestination()).getId()) {
                case R.id.createSampleFragment:
                    CreateSampleFragment createSampleFragment = (CreateSampleFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createSampleFragment != null) {
                        if (method.equals("references")) {
                            createSampleFragment.referenceDialog.singleAdapter.notifyDataSetChanged();
                        }
                    }
                    break;
            }
        }).widget(holder.binding.removeImageView);
    }

    private void setData(RecyclerSingleHandler holder, Model item) {
        try {
            if (method.equals("phones")) {
                ids.add(item.get("id").toString());

                holder.binding.titleTextView.setText(item.get("phone").toString());
            } else if (method.equals("references")) {
                ids.add(item.get("id").toString());

                holder.binding.titleTextView.setText(item.get("title").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class RecyclerSingleHandler extends RecyclerView.ViewHolder {

        private PopItemRecyclerSingleBinding binding;

        public RecyclerSingleHandler(PopItemRecyclerSingleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}