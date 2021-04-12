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
import com.majazeh.risloo.databinding.PopItemDialogMultiBinding;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

public class DialogMultiAdapter extends RecyclerView.Adapter<DialogMultiAdapter.DialogMultiHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<Model> items;
    private ArrayList<String> ids = new ArrayList<>();
    private String method;

    public DialogMultiAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public DialogMultiHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DialogMultiHolder(PopItemDialogMultiBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DialogMultiHolder holder, int i) {
        Model item = items.get(i);

        listener(holder, item, i);

        setData(holder, item);

        detector(holder, i);
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

    private void listener(DialogMultiHolder holder, Model item, int position) {
        ClickManager.onDelayedClickListener(() -> {
            switch (Objects.requireNonNull(((MainActivity) activity).navController.getCurrentDestination()).getId()) {
                case R.id.createSampleFragment:
                    CreateSampleFragment createSampleFragment = (CreateSampleFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createSampleFragment != null) {
                        if (method.equals("scales")) {
                            if (!createSampleFragment.scalesAdapter.getIds().contains(ids.get(position)))
                                createSampleFragment.scalesAdapter.addItem(item);
                            else
                                createSampleFragment.scalesAdapter.removeItem(position);
                        }

                        notifyDataSetChanged();
                    }
                    break;
            }
        }).widget(holder.binding.getRoot());
    }

    private void setData(DialogMultiHolder holder, Model item) {
        try {
            if (method.equals("scales")) {
                ids.add(item.get("id").toString());

                holder.binding.titleTextView.setText(item.get("title").toString());
                holder.binding.subTextView.setText(item.get("subtitle").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void detector(DialogMultiHolder holder, int position) {
        switch (Objects.requireNonNull(((MainActivity) activity).navController.getCurrentDestination()).getId()) {
            case R.id.createSampleFragment:
                CreateSampleFragment createSampleFragment = (CreateSampleFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                if (createSampleFragment != null) {
                    if (method.equals("scales")) {
                        if (!createSampleFragment.scalesAdapter.getIds().contains(ids.get(position))) {
                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200_ripple_gray300);
                            else
                                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200);
                        } else {
                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
                            else
                                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
                        }
                    }
                }
                break;
        }
    }

    public class DialogMultiHolder extends RecyclerView.ViewHolder {

        private PopItemDialogMultiBinding binding;

        public DialogMultiHolder(PopItemDialogMultiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}