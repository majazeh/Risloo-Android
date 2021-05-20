package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemCenterUserBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;

public class CenterUsersAdapter extends RecyclerView.Adapter<CenterUsersAdapter.CenterUsersHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> users;

    public CenterUsersAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CenterUsersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CenterUsersHolder(SingleItemCenterUserBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CenterUsersHolder holder, int i) {
        UserModel user = (UserModel) users.get(i);

        initializer(holder);

        detector(holder);

        listener(holder);

        setData(holder, user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(ArrayList<TypeModel> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    private void initializer(CenterUsersHolder holder) {
        InitManager.spinner(activity, holder.binding.typeSpinner, R.array.UserTypes, "adapter2");
    }

    private void detector(CenterUsersHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(CenterUsersHolder holder) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.referenceFragment)).widget(holder.binding.getRoot());

        holder.binding.menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String task = parent.getItemAtPosition(position).toString();

                switch (task) {
                    case "پذیرفتن":
                        Log.e("method", "accept");
                        break;
                    case "تعلیق":
                        Log.e("method", "kick");
                        break;
                    case "ساختن اتاق درمان":
                        Log.e("method", "create_room");
                        break;
                    case "ویرایش کاربر":
                        Log.e("method", "edit");
                        break;
                    case "ورود به کاربری":
                        Log.e("method", "enter");
                        break;
                }

                holder.binding.menuSpinner.setSelection(holder.binding.menuSpinner.getAdapter().getCount());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.binding.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = parent.getItemAtPosition(position).toString();

                doWork(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(CenterUsersHolder holder, UserModel model) {
        if (holder.getBindingAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText(model.getUserId());
        holder.binding.nameTextView.setText(model.getName());
        holder.binding.mobileTextView.setText(model.getMobile());
        holder.binding.statusTexView.setText(model.getUserStatus());
        holder.binding.acceptedTextView.setText(String.valueOf(model.getUserAccepted_at()));
        holder.binding.kickedTextView.setText(String.valueOf(model.getUserKicked_at()));

        for (int i = 0; i < holder.binding.typeSpinner.getCount(); i++) {
            if (holder.binding.typeSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(model.getPosition())) {
                holder.binding.typeSpinner.setSelection(i);
            }
        }

        if (holder.getBindingAdapterPosition() == 0) {
            setType(holder, true);
        } else {
            setType(holder, false);
        }

        ArrayList<String> menuValues = new ArrayList<>();

        menuValues.add(activity.getResources().getString(R.string.CenterUsersFragmentAccept));
        menuValues.add(activity.getResources().getString(R.string.CenterUsersFragmentKick));
        menuValues.add(activity.getResources().getString(R.string.CenterUsersFragmentCreateRoom));
        menuValues.add(activity.getResources().getString(R.string.CenterUsersFragmentEdit));
        menuValues.add(activity.getResources().getString(R.string.CenterUsersFragmentEnter));
        menuValues.add("");

        InitManager.customizedSpinner(activity, holder.binding.menuSpinner, menuValues, "centerUsers");
    }

    private void setType(CenterUsersHolder holder, boolean enable) {
        if (enable) {
            holder.binding.typeSpinner.setEnabled(true);

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                holder.binding.typeSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
            else
                holder.binding.typeSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray200);

            holder.binding.typeAngleImageView.setVisibility(View.VISIBLE);
        } else {
            holder.binding.typeSpinner.setEnabled(false);
            holder.binding.typeSpinner.setBackgroundResource(android.R.color.transparent);

            holder.binding.typeAngleImageView.setVisibility(View.GONE);
        }
    }

    private void doWork(String status) {

    }

    public class CenterUsersHolder extends RecyclerView.ViewHolder {

        private SingleItemCenterUserBinding binding;

        public CenterUsersHolder(SingleItemCenterUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}