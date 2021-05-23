package com.majazeh.risloo.Views.Adapters.Recycler;

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

    private void listener(CenterUsersHolder holder) {
        ClickManager.onClickListener(() -> {
            if (Objects.requireNonNull(((MainActivity) activity).navController.getCurrentDestination()).getId() == R.id.centerUsersFragment) {
                CenterUsersFragment centerUsersFragment = (CenterUsersFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                if (centerUsersFragment != null) {
                    Bundle extras = new Bundle();
                    extras.putString("id", centerUsersFragment.id);
                    extras.putString("userId", holder.binding.serialTextView.getText().toString());

                    ((MainActivity) activity).navigator(R.id.referenceFragment);
                }
            }
        }).widget(holder.binding.getRoot());

        holder.binding.positionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pos = parent.getItemAtPosition(position).toString();

                if (Objects.requireNonNull(((MainActivity) activity).navController.getCurrentDestination()).getId() == R.id.centerUsersFragment) {
                    CenterUsersFragment centerUsersFragment = (CenterUsersFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (centerUsersFragment != null) {
                        switch (pos) {
                            case "مدیر":
//                                doWork(centerUsersFragment.id, holder.binding.serialTextView.getText().toString(), "manager", "position");
                                break;
                            case "مراجع":
//                                doWork(centerUsersFragment.id, holder.binding.serialTextView.getText().toString(), "client", "position");
                                break;
                            case "اپراتور":
//                                doWork(centerUsersFragment.id, holder.binding.serialTextView.getText().toString(), "operator", "position");
                                break;
                            case "روانشناس":
//                                doWork(centerUsersFragment.id, holder.binding.serialTextView.getText().toString(), "psychologist", "position");
                                break;
                        }
                    }
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

                if (Objects.requireNonNull(((MainActivity) activity).navController.getCurrentDestination()).getId() == R.id.centerUsersFragment) {
                    CenterUsersFragment centerUsersFragment = (CenterUsersFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (centerUsersFragment != null) {
                        switch (task) {
                            case "پذیرفتن":
                                doWork(centerUsersFragment.id, holder.binding.serialTextView.getText().toString(), "accept", "status");
                                break;
                            case "تعلیق":
                                doWork(centerUsersFragment.id, holder.binding.serialTextView.getText().toString(), "kick", "status");
                                break;
                            case "ساختن اتاق درمان":
                                Log.e("method", "create_room");
                                break;
                            case "اتاق درمان":
                                Log.e("method", "room");
                                break;
                            case "ویرایش کاربر":
                                Log.e("method", "edit");
                                break;
                            case "ورود به کاربری":
                                Log.e("method", "enter");
                                break;
                        }
                    }
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

        holder.binding.serialTextView.setText(model.getUserId());
        holder.binding.nameTextView.setText(model.getName());
        holder.binding.mobileTextView.setText(model.getMobile());
        holder.binding.statusTexView.setText(model.getUserStatus());

        holder.binding.acceptedTextView.setText(String.valueOf(model.getUserAccepted_at()));
        holder.binding.kickedTextView.setText(String.valueOf(model.getUserKicked_at()));

        for (int i = 0; i < holder.binding.positionSpinner.getCount(); i++) {
            if (holder.binding.positionSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(model.getPosition())) {
                holder.binding.positionSpinner.setSelection(i);
            }
        }

        setPosition(holder, true);

        ArrayList<String> list = new ArrayList<>();

        if (model.getUserAccepted_at() == 0)
            list.add(activity.getResources().getString(R.string.CenterUsersFragmentAccept));
        if (model.getUserKicked_at() == 0)
            list.add(activity.getResources().getString(R.string.CenterUsersFragmentKick));
        list.add(activity.getResources().getString(R.string.CenterUsersFragmentCreateRoom));
        list.add(activity.getResources().getString(R.string.CenterUsersFragmentRoom));
        list.add(activity.getResources().getString(R.string.CenterUsersFragmentEdit));
        list.add(activity.getResources().getString(R.string.CenterUsersFragmentEnter));
        list.add("");

        InitManager.customizedSpinner(activity, holder.binding.menuSpinner, list, "centerUsers");
    }

    private void setPosition(CenterUsersHolder holder, boolean enable) {
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

    private void doWork(String id, String userId, String value, String method) {
        if (method.equals("position")) {
            HashMap data = new HashMap();
            data.put("id", id);
            data.put("userId", userId);
            data.put("position", value);

            HashMap header = new HashMap();
            header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());

            Center.changePosition(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    activity.runOnUiThread(() -> {
                        // sdfnslkdafnksnfklasklfnlksfn

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
            HashMap data = new HashMap();
            data.put("id", id);
            data.put("userId", userId);
            data.put("status", value);

            HashMap header = new HashMap();
            header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());

            Center.changeStatus(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    activity.runOnUiThread(() -> {
                        // sdfnslkdafnksnfklasklfnlksfn

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

    public class CenterUsersHolder extends RecyclerView.ViewHolder {

        private SingleItemCenterUserBinding binding;

        public CenterUsersHolder(SingleItemCenterUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}