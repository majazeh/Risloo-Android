package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemUserBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> users;

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
        UserModel user = (UserModel) users.get(i);

        detector(holder);

        listener(holder, user);

        setData(holder, user);
    }

    @Override
    public int getItemCount() {
        if (this.users != null)
            return users.size();
        else
            return 0;
    }

    public void setUsers(ArrayList<TypeModel> users) {
        if (this.users == null)
            this.users = users;
        else
            this.users.addAll(users);
        notifyDataSetChanged();
    }

    public void clearUsers() {
        if (this.users != null) {
            this.users.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(UsersHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(UsersHolder holder, UserModel model) {
        ClickManager.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalUserFragment(model);
            ((MainActivity) activity).navController.navigate(action);
        }).widget(holder.binding.getRoot());

        holder.binding.menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = parent.getItemAtPosition(position).toString();

                if (pos.contains("989")) {
                    IntentManager.phone(activity, pos);
                } else if (pos.contains("@")) {
                    IntentManager.email(activity, new String[]{pos}, "", "", "");
                } else if (pos.equals("ورود به کاربری")) {
                    Log.e("method", "enter");
                } else if (pos.equals("ویرایش عضو")) {
                    NavDirections action = NavigationMainDirections.actionGlobalEditUserFragment(model);
                    ((MainActivity) activity).navController.navigate(action);
                }

                holder.binding.menuSpinner.setSelection(parent.getAdapter().getCount());
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
        holder.binding.usernameTextView.setText(model.getUsername());
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

        if (!model.getUserType().equals("admin"))
//            menu.add(activity.getResources().getString(R.string.UsersFragmentEnter));

        items.add(activity.getResources().getString(R.string.UsersFragmentEdit));
        items.add("");

        if (items.size() != 1)
            holder.binding.menuImageView.setVisibility(View.VISIBLE);
        else
            holder.binding.menuImageView.setVisibility(View.INVISIBLE);

        InitManager.actionCustomSpinner(activity, holder.binding.menuSpinner, items);
    }

    public class UsersHolder extends RecyclerView.ViewHolder {

        private SingleItemUserBinding binding;

        public UsersHolder(SingleItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}