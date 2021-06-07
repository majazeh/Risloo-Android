package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemUser2Binding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;
import java.util.HashMap;

public class Users2Adapter extends RecyclerView.Adapter<Users2Adapter.Users2Holder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> users;
    private boolean userSelect = false;

    public Users2Adapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Users2Adapter.Users2Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Users2Adapter.Users2Holder(SingleItemUser2Binding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Users2Holder holder, int i) {
        UserModel user = (UserModel) users.get(i);

        initializer(holder);

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

    private void initializer(Users2Holder holder) {
        InitManager.spinner(activity, holder.binding.statusSpinner, R.array.UserStatus, "adapter");
    }

    private void detector(Users2Holder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(Users2Holder holder, UserModel model) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.userFragment, getExtras(model))).widget(holder.binding.getRoot());

        holder.binding.statusSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String status = parent.getItemAtPosition(position).toString();

                    doWork(holder, model.getId(), SelectionManager.getUserStatus2(activity, "en", status));

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(Users2Holder holder, UserModel model) {
        if (holder.getBindingAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.nameTextView.setText(model.getName());
        holder.binding.mobileTextView.setText(model.getMobile());
        holder.binding.situationTextView.setText("");
        holder.binding.descriptionTextView.setText("");
        holder.binding.fieldTextView.setText("-");
        holder.binding.typeTextView.setText(SelectionManager.getUserType(activity, "fa", model.getUserType()));
        holder.binding.caseTextView.setText("-");

        setStatus(holder, model);
    }

    private void setStatus(Users2Holder holder, UserModel model) {
        for (int i=0; i<holder.binding.statusSpinner.getCount(); i++) {
            switch (model.getUserStatus()) {
                case "client":
                    holder.binding.statusSpinner.setSelection(0);
                    break;
                case "request":
                    holder.binding.statusSpinner.setSelection(1);
                    break;
                case "delete":
                    holder.binding.statusSpinner.setSelection(2);
                    break;
            }
        }

        boolean enable = true;

        if (enable) {
            holder.binding.statusSpinner.setEnabled(true);

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                holder.binding.statusSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
            else
                holder.binding.statusSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray200);

            holder.binding.statusAngleImageView.setVisibility(View.VISIBLE);
        } else {
            holder.binding.statusSpinner.setEnabled(false);
            holder.binding.statusSpinner.setBackgroundResource(android.R.color.transparent);

            holder.binding.statusAngleImageView.setVisibility(View.GONE);
        }
    }

    private void doWork(Users2Holder holder, String id, String status) {
        ((MainActivity) activity).loadingDialog.show(((MainActivity) activity).getSupportFragmentManager(), "loadingDialog");

        HashMap data = new HashMap<>();
        data.put("id", id);
        data.put("status", status);

        HashMap header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());

//        User.changeStatus(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                UserModel model = (UserModel) object;
//
//                activity.runOnUiThread(() -> {
//                    setStatus(holder, model);
//
//                    ((MainActivity) activity).loadingDialog.dismiss();
//                    Toast.makeText(activity, activity.getResources().getString(R.string.AppChanged), Toast.LENGTH_SHORT).show();
//                });
//            }
//
//            @Override
//            public void onFailure(String response) {
//                // Place Code if Needed
//            }
//        });
    }

    private Bundle getExtras(UserModel model) {
        Bundle extras = new Bundle();

        extras.putString("id", model.getId());
        extras.putString("name", model.getName());
        extras.putString("username", model.getUsername());
        extras.putString("birthday", model.getBirthday());
        extras.putString("email", model.getEmail());
        extras.putString("mobile", model.getMobile());
        extras.putString("status", model.getUserStatus());
        extras.putString("type", model.getUserType());
        extras.putString("gender", model.getGender());
        extras.putString("public_key", model.getPublic_key());

        if (model.getAvatar() != null && model.getAvatar().getMedium() != null && model.getAvatar().getMedium().getUrl() != null)
            extras.putString("avatar", model.getAvatar().getMedium().getUrl());

        return extras;
    }

    public class Users2Holder extends RecyclerView.ViewHolder {

        private SingleItemUser2Binding binding;

        public Users2Holder(SingleItemUser2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}