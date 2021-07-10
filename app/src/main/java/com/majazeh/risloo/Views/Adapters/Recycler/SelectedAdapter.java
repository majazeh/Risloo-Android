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
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSessionFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSchedulePaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterDetailFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionPaymentFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionSessionFragment;
import com.majazeh.risloo.databinding.SingleItemSelectedBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.ArrayList;

public class SelectedAdapter extends RecyclerView.Adapter<SelectedAdapter.SelectedHolder> {

    // Objects
    private Activity activity;

    // Widget
    private TextView countTextView;

    // Vars
    private ArrayList<TypeModel> items = new ArrayList<>();
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
        TypeModel item = items.get(i);

        detector(holder);

        listener(holder, i);

        setData(holder, item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<TypeModel> getItems() {
        return items;
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
                case "phones":
                case "axises":
                case "patternDays": {
                    holder.binding.titleTextView.setText(item.object.getString("title"));

                    holder.binding.subTextView.setVisibility(View.GONE);
                    holder.binding.subTextView.setText("");
                } break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void removePayment(int position) {
        if (getPayment() != null) {
            if (getPayment() instanceof CreateSchedulePaymentFragment)
                ((CreateSchedulePaymentFragment) getPayment()).axisAdapter.removeItem(position);

            else if (getPayment() instanceof CreateSessionPaymentFragment)
                ((CreateSessionPaymentFragment) getPayment()).axisAdapter.removeItem(position);

            else if (getPayment() instanceof EditSessionPaymentFragment)
                ((EditSessionPaymentFragment) getPayment()).axisAdapter.removeItem(position);
        }
    }

    private void refreshCount() {
        if (getCurrent() != null) {
            if (getCurrent() instanceof CreateCenterFragment) {
                if (method.equals("phones"))
                    if (((CreateCenterFragment) getCurrent()).phonesDialog.isVisible())
                        ((CreateCenterFragment) getCurrent()).phonesDialog.calculateCount();

            } else if (getCurrent() instanceof CreateScheduleSessionFragment) {
                if (method.equals("axises"))
                    if (((CreateScheduleSessionFragment) getCurrent()).axisesDialog.isVisible())
                        ((CreateScheduleSessionFragment) getCurrent()).axisesDialog.calculateCount();

            } else if (getCurrent() instanceof CreateSessionFragment) {
                if (method.equals("axises"))
                    if (((CreateSessionSessionFragment) getCurrent()).axisesDialog.isVisible())
                        ((CreateSessionSessionFragment) getCurrent()).axisesDialog.calculateCount();

            } else if (getCurrent() instanceof EditCenterFragment) {
                if (method.equals("phones"))
                    if (((EditCenterDetailFragment) getCurrent()).phonesDialog.isVisible())
                        ((EditCenterDetailFragment) getCurrent()).phonesDialog.calculateCount();

            } else if (getCurrent() instanceof EditSessionFragment) {
                if (method.equals("axises"))
                    if (((EditSessionSessionFragment) getCurrent()).axisesDialog.isVisible())
                        ((EditSessionSessionFragment) getCurrent()).axisesDialog.calculateCount();

            }
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

    private Fragment getCurrent() {
        Fragment fragment = ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (fragment != null)
            if (fragment instanceof CreateCenterFragment)
                return fragment;

            else if (fragment instanceof CreateScheduleFragment) {
                Fragment childFragment = ((CreateScheduleFragment) fragment).adapter.hashMap.get(((CreateScheduleFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof CreateScheduleSessionFragment)
                        return childFragment;

            } else if (fragment instanceof CreateSessionFragment) {
                Fragment childFragment = ((CreateSessionFragment) fragment).adapter.hashMap.get(((CreateSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof CreateSessionSessionFragment)
                        return childFragment;

            } else if (fragment instanceof EditCenterFragment) {
                Fragment childFragment = ((EditCenterFragment) fragment).adapter.hashMap.get(((EditCenterFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof EditCenterDetailFragment)
                        return childFragment;

            } else if (fragment instanceof EditSessionFragment) {
                Fragment childFragment = ((EditSessionFragment) fragment).adapter.hashMap.get(((EditSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof EditSessionSessionFragment)
                        return childFragment;
            }

        return null;
    }

    private Fragment getParent() {
        Fragment fragment = ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (fragment != null)
            if (fragment instanceof CreateScheduleFragment)
                return fragment;

            else if (fragment instanceof CreateSessionFragment)
                return fragment;

            else if (fragment instanceof EditCenterFragment)
                return fragment;

            else if (fragment instanceof EditSessionFragment)
                return fragment;

        return null;
    }

    private Fragment getPayment() {
        Fragment fragment = getParent();
        if (fragment != null)
            if (fragment instanceof CreateScheduleFragment) {
                Fragment paymentFragment = ((CreateScheduleFragment) fragment).adapter.hashMap.get(3);
                if (paymentFragment != null)
                    if (paymentFragment instanceof CreateSchedulePaymentFragment)
                        return paymentFragment;

            } else if (fragment instanceof CreateSessionFragment) {
                Fragment paymentFragment = ((CreateSessionFragment) fragment).adapter.hashMap.get(2);
                if (paymentFragment != null)
                    if (paymentFragment instanceof CreateSessionPaymentFragment)
                        return paymentFragment;

            } else if (fragment instanceof EditSessionFragment) {
                Fragment paymentFragment = ((EditSessionFragment) fragment).adapter.hashMap.get(2);
                if (paymentFragment != null)
                    if (paymentFragment instanceof EditSessionPaymentFragment)
                        return paymentFragment;
            }

        return null;
    }

    public class SelectedHolder extends RecyclerView.ViewHolder {

        private SingleItemSelectedBinding binding;

        public SelectedHolder(SingleItemSelectedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}