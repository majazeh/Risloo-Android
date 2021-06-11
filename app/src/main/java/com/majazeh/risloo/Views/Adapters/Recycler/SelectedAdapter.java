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
import com.mre.ligheh.Model.TypeModel.SampleModel;
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
            Fragment fragment = ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
            if (fragment != null) {

                if (fragment instanceof CreateScheduleFragment) {
                    Fragment childFragment = ((CreateScheduleFragment) fragment).adapter.hashMap.get(((CreateScheduleFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                    if (childFragment != null) {
                        if (method.equals("axises"))
                            if (childFragment instanceof CreateScheduleSessionFragment) {
                                CreateSchedulePaymentFragment paymentFragment = (CreateSchedulePaymentFragment) ((CreateScheduleFragment) fragment).adapter.hashMap.get(3);
                                if (paymentFragment != null)
                                    paymentFragment.axisAdapter.removeItem(position);
                            }
                    }

                } else if (fragment instanceof CreateSessionFragment) {
                    Fragment childFragment = ((CreateSessionFragment) fragment).adapter.hashMap.get(((CreateSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                    if (childFragment != null) {
                        if (method.equals("axises"))
                            if (childFragment instanceof CreateSessionSessionFragment) {
                                CreateSessionPaymentFragment paymentFragment = (CreateSessionPaymentFragment) ((CreateSessionFragment) fragment).adapter.hashMap.get(2);
                                if (paymentFragment != null)
                                    paymentFragment.axisAdapter.removeItem(position);
                            }
                    }
                } else if (fragment instanceof EditSessionFragment) {
                    Fragment childFragment = ((EditSessionFragment) fragment).adapter.hashMap.get(((EditSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                    if (childFragment != null) {
                        if (method.equals("axises"))
                            if (childFragment instanceof EditSessionSessionFragment) {
                                EditSessionPaymentFragment paymentFragment = (EditSessionPaymentFragment) ((EditSessionFragment) fragment).adapter.hashMap.get(2);
                                if (paymentFragment != null)
                                    paymentFragment.axisAdapter.removeItem(position);
                            }
                    }
                }
            }

            removeItem(position);

            Fragment fragment2 = ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
            if (fragment2 != null) {

                if (fragment2 instanceof CreateCenterFragment) {
                    if (method.equals("phones"))
                        if (((CreateCenterFragment) fragment2).phonesDialog.isVisible())
                            ((CreateCenterFragment) fragment2).phonesDialog.calculateCount();

                } else if (fragment2 instanceof CreateScheduleFragment) {
                    Fragment childFragment = ((CreateScheduleFragment) fragment2).adapter.hashMap.get(((CreateScheduleFragment) fragment2).binding.viewPager.getRoot().getCurrentItem());
                    if (childFragment != null) {
                        if (method.equals("axises"))
                            if (childFragment instanceof CreateScheduleSessionFragment)
                                if (((CreateScheduleSessionFragment) childFragment).axisesDialog.isVisible())
                                    ((CreateScheduleSessionFragment) childFragment).axisesDialog.calculateCount();
                    }

                } else if (fragment2 instanceof CreateSessionFragment) {
                    Fragment childFragment = ((CreateSessionFragment) fragment2).adapter.hashMap.get(((CreateSessionFragment) fragment2).binding.viewPager.getRoot().getCurrentItem());
                    if (childFragment != null) {
                        if (method.equals("axises"))
                            if (childFragment instanceof CreateSessionSessionFragment)
                                if (((CreateSessionSessionFragment) childFragment).axisesDialog.isVisible())
                                    ((CreateSessionSessionFragment) childFragment).axisesDialog.calculateCount();
                    }

                } else if (fragment2 instanceof EditCenterFragment) {
                    Fragment childFragment = ((EditCenterFragment) fragment2).adapter.hashMap.get(((EditCenterFragment) fragment2).binding.viewPager.getRoot().getCurrentItem());
                    if (childFragment != null) {
                        if (method.equals("phones"))
                            if (childFragment instanceof EditCenterDetailFragment)
                                if (((EditCenterDetailFragment) childFragment).phonesDialog.isVisible())
                                    ((EditCenterDetailFragment) childFragment).phonesDialog.calculateCount();
                    }

                } else if (fragment2 instanceof EditSessionFragment) {
                    Fragment childFragment = ((EditSessionFragment) fragment2).adapter.hashMap.get(((EditSessionFragment) fragment2).binding.viewPager.getRoot().getCurrentItem());
                    if (childFragment != null) {
                        if (method.equals("axises"))
                            if (childFragment instanceof EditSessionSessionFragment)
                                if (((EditSessionSessionFragment) childFragment).axisesDialog.isVisible())
                                    ((EditSessionSessionFragment) childFragment).axisesDialog.calculateCount();
                    }
                }
            }
        }).widget(holder.binding.removeImageView);
    }

    private void setData(SelectedHolder holder, TypeModel item) {
        try {
            switch (method) {
                case "scales": {
                    SampleModel model = (SampleModel) item;

                    holder.binding.titleTextView.setText(model.getSampleScaleTitle());

                    holder.binding.subTextView.setVisibility(View.VISIBLE);
                    if (!model.getSampleEdition().equals(""))
                        holder.binding.subTextView.setText(model.getSampleEdition() + " - نسخه " + model.getSampleVersion());
                    else
                        holder.binding.subTextView.setText("نسخه " + model.getSampleVersion());
                }
                break;
                case "references": {
                    UserModel model = (UserModel) item;

                    holder.binding.titleTextView.setText(model.getName());

                    holder.binding.subTextView.setVisibility(View.GONE);
                    holder.binding.subTextView.setText("");
                }
                break;
                case "phones":
                case "axises":
                case "patternDays":
                    holder.binding.titleTextView.setText(item.object.getString("title"));

                    holder.binding.subTextView.setVisibility(View.GONE);
                    holder.binding.subTextView.setText("");
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void calculateCount() {
        if (getItemCount() != 0) {
            String count = "(" + getItemCount() + ")";

            countTextView.setVisibility(View.VISIBLE);
            countTextView.setText(count);
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