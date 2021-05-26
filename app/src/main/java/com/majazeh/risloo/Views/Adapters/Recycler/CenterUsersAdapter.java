package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Index.CenterUsersFragment;
import com.majazeh.risloo.databinding.SingleItemCenterUserBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CenterUsersAdapter extends RecyclerView.Adapter<CenterUsersAdapter.CenterUsersHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> users;
    private boolean userSelect = false;

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

    private void initializer(CenterUsersHolder holder) {
        InitManager.spinner(activity, holder.binding.positionSpinner, R.array.UserTypes, "adapter2");
    }

    private void detector(CenterUsersHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(CenterUsersHolder holder, UserModel model) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.referenceFragment, getExtras(holder, model))).widget(holder.binding.getRoot());

        holder.binding.positionSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.positionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    switch (pos) {
                        case "مدیر":
                            doWork(holder, getExtras(holder, model).getString("id"), getExtras(holder, model).getString("user_id"), "manager", "position");
                            break;
                        case "مراجع":
                            doWork(holder, getExtras(holder, model).getString("id"), getExtras(holder, model).getString("user_id"), "client", "position");
                            break;
                        case "اپراتور":
                            doWork(holder, getExtras(holder, model).getString("id"), getExtras(holder, model).getString("user_id"), "operator", "position");
                            break;
                        case "روان\u200Cشناس":
                            doWork(holder, getExtras(holder, model).getString("id"), getExtras(holder, model).getString("user_id"), "psychologist", "position");
                            break;
                    }

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.binding.menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String task = parent.getItemAtPosition(position).toString();

                switch (task) {
                    case "پذیرفتن":
                        doWork(holder, getExtras(holder, model).getString("id"), getExtras(holder, model).getString("user_id"), "accept", "status");
                        break;
                    case "تعلیق":
                        doWork(holder, getExtras(holder, model).getString("id"), getExtras(holder, model).getString("user_id"), "kick", "status");
                        break;
                    case "ساختن اتاق درمان":
                        ((MainActivity) activity).navigator(R.id.createRoomFragment, getExtras(holder, model));
                        break;
                    case "اتاق درمان":
                        ((MainActivity) activity).navigator(R.id.roomFragment, getExtras(holder, model));
                        break;
                    case "ویرایش کاربر":
                        ((MainActivity) activity).navigator(R.id.editCenterUserFragment, getExtras(holder, model));
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
    }

    private void setData(CenterUsersHolder holder, UserModel model) {
        if (holder.getBindingAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText(model.getId());
        holder.binding.nameTextView.setText(model.getName());
        holder.binding.mobileTextView.setText(model.getMobile());

        setPosition(holder, model);

        setAcceptation(holder, model);
    }

    private void setPosition(CenterUsersHolder holder, UserModel model) {
        for (int i = 0; i < holder.binding.positionSpinner.getCount(); i++) {
            switch (model.getPosition()) {
                case "مدیر":
                case "manager":
                    holder.binding.positionSpinner.setSelection(0);
                    break;
                case "اپراتور":
                case "operator":
                    holder.binding.positionSpinner.setSelection(1);
                    break;
                case "روان\u200Cشناس":
                case "psychologist":
                    holder.binding.positionSpinner.setSelection(2);
                    break;
                case "مراجع":
                case "client":
                    holder.binding.positionSpinner.setSelection(3);
                    break;
            }
        }

        boolean enable = true;

        if (enable) {
            holder.binding.positionSpinner.setEnabled(true);

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                holder.binding.positionSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
            else
                holder.binding.positionSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray200);

            holder.binding.positionAngleImageView.setVisibility(View.VISIBLE);
        } else {
            holder.binding.positionSpinner.setEnabled(false);
            holder.binding.positionSpinner.setBackgroundResource(android.R.color.transparent);

            holder.binding.positionAngleImageView.setVisibility(View.GONE);
        }
    }

    private void setAcceptation(CenterUsersHolder holder, UserModel model) {
        ArrayList<String> menu = new ArrayList<>();

        if (model.getUserAccepted_at() == 0) {
            menu.add(activity.getResources().getString(R.string.CenterUsersFragmentAccept));
        } else {
            holder.binding.statusTexView.setText(activity.getResources().getString(R.string.CenterUsersFragmentStatusAccepted));
            holder.binding.acceptedTextView.setText(DateManager.gregorianToJalali3(DateManager.dateToString("yyyy-MM-dd HH:mm:ss", DateManager.timestampToDate(model.getUserAccepted_at()))));
        }

        if (model.getUserKicked_at() == 0) {
            menu.add(activity.getResources().getString(R.string.CenterUsersFragmentKick));
        } else {
            holder.binding.statusTexView.setText(activity.getResources().getString(R.string.CenterUsersFragmentStatusKicked));
            holder.binding.kickedTextView.setText(DateManager.gregorianToJalali3(DateManager.dateToString("yyyy-MM-dd HH:mm:ss", DateManager.timestampToDate(model.getUserKicked_at()))));
        }

        if (model.getUserAccepted_at() == 0 && model.getUserKicked_at() == 0) {
            holder.binding.statusTexView.setText(activity.getResources().getString(R.string.CenterUsersFragmentStatusWaiting));
        }

        menu.add(activity.getResources().getString(R.string.CenterUsersFragmentCreateRoom));
        menu.add(activity.getResources().getString(R.string.CenterUsersFragmentRoom));
        menu.add(activity.getResources().getString(R.string.CenterUsersFragmentEdit));
        menu.add(activity.getResources().getString(R.string.CenterUsersFragmentEnter));
        menu.add("");

        InitManager.customizedSpinner(activity, holder.binding.menuSpinner, menu, "centerUsers");
    }

    private void doWork(CenterUsersHolder holder, String id, String userId, String value, String method) {
        ((MainActivity) activity).loadingDialog.show(((MainActivity) activity).getSupportFragmentManager(), "loadingDialog");

        if (method.equals("position")) {
            HashMap data = new HashMap<>();
            data.put("id", id);
            data.put("userId", userId);
            data.put("position", value);

            HashMap header = new HashMap<>();
            header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());

            Center.changePosition(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    activity.runOnUiThread(() -> {
                        ((MainActivity) activity).loadingDialog.dismiss();
                        Toast.makeText(activity, activity.getResources().getString(R.string.AppChanged), Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onFailure(String response) {
                    // Place Code if Needed
                }
            });
        } else {
            HashMap data = new HashMap<>();
            data.put("id", id);
            data.put("userId", userId);
            data.put("status", value);

            HashMap header = new HashMap<>();
            header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());

            Center.changeStatus(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    UserModel model = (UserModel) object;

                    activity.runOnUiThread(() -> {
                        setAcceptation(holder, model);

                        ((MainActivity) activity).loadingDialog.dismiss();
                        Toast.makeText(activity, activity.getResources().getString(R.string.AppChanged), Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onFailure(String response) {
                    // Place Code if Needed
                }
            });
        }
    }

    private Bundle getExtras(CenterUsersHolder holder, UserModel model) {
        Bundle extras = new Bundle();

        if (Objects.requireNonNull(((MainActivity) activity).navController.getCurrentDestination()).getId() == R.id.centerUsersFragment) {
            CenterUsersFragment centerUsersFragment = (CenterUsersFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
            if (centerUsersFragment != null)
                extras.putString("id", centerUsersFragment.centerId);
        }

        extras.putString("user_id", model.getId());
        extras.putString("position", holder.binding.positionSpinner.getSelectedItem().toString());
        extras.putString("nickname", model.getName());
        extras.putString("mobile", model.getMobile());

        if (holder.binding.statusTexView.getText().toString().equals("پذیرش شده"))
            extras.putString("status", "accept");
        else if (holder.binding.statusTexView.getText().toString().equals("تعلیق شده"))
            extras.putString("status", "kick");
        else
            extras.putString("status", "none");

        return extras;
    }

    public class CenterUsersHolder extends RecyclerView.ViewHolder {

        private SingleItemCenterUserBinding binding;

        public CenterUsersHolder(SingleItemCenterUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}