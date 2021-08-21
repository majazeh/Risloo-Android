package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.UsersHolder;
import com.majazeh.risloo.databinding.SingleItemUserBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

    public UsersAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public UsersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UsersHolder(SingleItemUserBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UsersHolder holder, int i) {
        UserModel model = (UserModel) items.get(i);

        detector(holder);

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

    private void detector(UsersHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(UsersHolder holder, UserModel model) {
        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalUserFragment(model);
            ((MainActivity) activity).navController.navigate(action);
        }).widget(holder.binding.getRoot());

        holder.binding.menuSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    if (pos.contains("989")) {
                        IntentManager.phone(activity, pos);
                    } else if (pos.contains("@")) {
                        IntentManager.email(activity, new String[]{pos}, "", "", "");
                    } else if (pos.equals("ورود به کاربری")) {
                        // TODO : Place Code When Needed
                    } else if (pos.equals("ویرایش")) {
                        NavDirections action = NavigationMainDirections.actionGlobalEditUserFragment(model);
                        ((MainActivity) activity).navController.navigate(action);
                    }

                    parent.setSelection(parent.getAdapter().getCount());

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(UsersHolder holder, UserModel model) {
        if (holder.getBindingAdapterPosition() == 0)
            holder.binding.topView.setVisibility(View.GONE);
         else
            holder.binding.topView.setVisibility(View.VISIBLE);

        holder.binding.serialTextView.setText(model.getId());
        holder.binding.nameTextView.setText(model.getName());
        holder.binding.typeTextView.setText(SelectionManager.getUserType(activity, "fa", model.getUserType()));
        holder.binding.statusTextView.setText(SelectionManager.getUserStatus(activity, "fa", model.getUserStatus()));

        setMenu(holder, model);
    }

    private void setMenu(UsersHolder holder, UserModel model) {
        ArrayList<String> items = new ArrayList<>();

        if (!model.getMobile().equals(""))
            items.add(model.getMobile());

        if (!model.getEmail().equals(""))
            items.add(model.getEmail());

        items.add(activity.getResources().getString(R.string.UsersFragmentEdit));
        items.add("");

        InitManager.actionCustomSpinner(activity, holder.binding.menuSpinner, items);
    }

}