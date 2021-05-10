package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Model;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSessionFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSchedulePaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionSessionFragment;
import com.majazeh.risloo.databinding.SingleItemSelectedBinding;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

public class SelectedAdapter extends RecyclerView.Adapter<SelectedAdapter.SelectedHolder> {

    // Objects
    private Activity activity;

    // Widget
    private TextView countTextView;

    // Vars
    private ArrayList<Model> items;
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

    public void setItems(ArrayList<Model> items, ArrayList<String> ids, String method, TextView countTextView) {
        this.items = items;
        this.ids = ids;
        this.method = method;
        this.countTextView = countTextView;
        notifyDataSetChanged();

        calculateCount(this.countTextView);
    }

    public void clearItems() {
        items.clear();
        ids.clear();
        notifyDataSetChanged();

        calculateCount(countTextView);
    }

    public void addItem(Model item) {
        try {
            items.add(item);
            ids.add(item.get("id").toString());
            notifyDataSetChanged();

            calculateCount(countTextView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void replaceItem(int position, Model item) {
        try {
            items.set(position, item);
            ids.set(position, item.get("id").toString());
            notifyItemChanged(position);
            notifyItemRangeChanged(position, getItemCount());

            calculateCount(countTextView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void removeItem(int position) {
        items.remove(position);
        ids.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());

        calculateCount(countTextView);
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
            switch (Objects.requireNonNull(((MainActivity) activity).navController.getCurrentDestination()).getId()) {
                case R.id.createScheduleFragment:
                    CreateScheduleFragment createScheduleFragment = (CreateScheduleFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createScheduleFragment != null) {
                        if (method.equals("axises")) {
                            CreateScheduleSessionFragment createScheduleSessionFragment = (CreateScheduleSessionFragment) createScheduleFragment.adapter.hashMap.get(createScheduleFragment.binding.viewPager.getRoot().getCurrentItem());
                            if (createScheduleSessionFragment != null) {
//                                createScheduleSessionFragment.axisesDialog.calculateCount();

                                CreateSchedulePaymentFragment createSchedulePaymentFragment = (CreateSchedulePaymentFragment) createScheduleFragment.adapter.hashMap.get(3);
                                if (createSchedulePaymentFragment != null) {
                                    createSchedulePaymentFragment.axisAdapter.removeItem(position);
                                }
                            }
                        }
                    }
                    break;
                case R.id.createSessionFragment:
                    CreateSessionFragment createSessionFragment = (CreateSessionFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createSessionFragment != null) {
                        if (method.equals("axises")) {
                            CreateSessionSessionFragment createSessionSessionFragment = (CreateSessionSessionFragment) createSessionFragment.adapter.hashMap.get(createSessionFragment.binding.viewPager.getRoot().getCurrentItem());
                            if (createSessionSessionFragment != null) {
//                                createSessionSessionFragment.axisesDialog.calculateCount();

                                CreateSessionPaymentFragment createSessionPaymentFragment = (CreateSessionPaymentFragment) createSessionFragment.adapter.hashMap.get(2);
                                if (createSessionPaymentFragment != null) {
                                    createSessionPaymentFragment.axisAdapter.removeItem(position);
                                }
                            }
                        }
                    }
                    break;
                case R.id.editSessionFragment:
                    EditSessionFragment editSessionFragment = (EditSessionFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (editSessionFragment != null) {
                        if (method.equals("axises")) {
                            EditSessionSessionFragment editSessionSessionFragment = (EditSessionSessionFragment) editSessionFragment.adapter.hashMap.get(editSessionFragment.binding.viewPager.getRoot().getCurrentItem());
                            if (editSessionSessionFragment != null) {
//                                editSessionSessionFragment.axisesDialog.calculateCount();

                                EditSessionPaymentFragment editSessionPaymentFragment = (EditSessionPaymentFragment) editSessionFragment.adapter.hashMap.get(2);
                                if (editSessionPaymentFragment != null) {
                                    editSessionPaymentFragment.axisAdapter.removeItem(position);
                                }
                            }
                        }
                    }
                    break;
            }

            removeItem(position);
        }).widget(holder.binding.removeImageView);
    }

    private void setData(SelectedHolder holder, Model item) {
        try {
            switch (method) {
                case "scales":
                    holder.binding.titleTextView.setText(item.get("title").toString());

                    holder.binding.subTextView.setVisibility(View.VISIBLE);
                    holder.binding.subTextView.setText(item.get("subtitle").toString());
                    break;
                case "references":
                case "phones":
                case "axises":
                case "patternDays":
                    holder.binding.titleTextView.setText(item.get("title").toString());

                    holder.binding.subTextView.setVisibility(View.GONE);
                    holder.binding.subTextView.setText("");
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void calculateCount(TextView widget) {
        if (getItemCount() != 0) {
            String count = "(" + getItemCount() + ")";

            widget.setVisibility(View.VISIBLE);
            widget.setText(count);
        } else {
            widget.setVisibility(View.GONE);
            widget.setText("");
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