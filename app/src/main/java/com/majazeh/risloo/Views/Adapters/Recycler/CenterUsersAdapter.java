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
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterUserFragment;
import com.majazeh.risloo.Views.Fragments.Index.CenterUsersFragment;
import com.majazeh.risloo.databinding.SingleItemCenterUserBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.CenterModel;
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

    public void clear() {
        if (this.users != null) {
            this.users.clear();
            notifyDataSetChanged();
        }
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

        holder.binding.menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String task = parent.getItemAtPosition(position).toString();
                if (Objects.requireNonNull(((MainActivity) activity).navController.getCurrentDestination()).getId() == R.id.centerUsersFragment) {
                    CenterUsersFragment centerUsersFragment = (CenterUsersFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (centerUsersFragment != null) {
                        switch (task) {
                            case "پذیرفتن":
                                HashMap data = new HashMap();
                                data.put("id", centerUsersFragment.id);
                                data.put("userId", holder.binding.serialTextView.getText().toString());
                                data.put("status", "accept");
                                HashMap header = new HashMap();
                                header.put("Authorization", "Bearer " + ((MainActivity) activity).singleton.getToken());
                                Center.changeStatus(data, header, new Response() {
                                    @Override
                                    public void onOK(Object object) {
                                        activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(activity, "DONE!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(String response) {

                                    }
                                });
                                Log.e("method", "accept");
                                break;
                            case "تعلیق":
                                HashMap data1 = new HashMap();
                                data1.put("id", centerUsersFragment.id);
                                data1.put("userId", holder.binding.serialTextView.getText().toString());
                                data1.put("status", "kick");
                                HashMap header1 = new HashMap();
                                header1.put("Authorization", "Bearer " + ((MainActivity) activity).singleton.getToken());
                                Center.changeStatus(data1, header1, new Response() {
                                    @Override
                                    public void onOK(Object object) {
                                        activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(activity, "DONE!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(String response) {

                                    }
                                });
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.binding.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = parent.getItemAtPosition(position).toString();
                if (Objects.requireNonNull(((MainActivity) activity).navController.getCurrentDestination()).getId() == R.id.centerUsersFragment) {
                    CenterUsersFragment centerUsersFragment = (CenterUsersFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (centerUsersFragment != null) {
                        String enType = "";
                        switch (type) {
                            case "مدیر":
                                enType = "manager";
                                break;
                            case "مراجع":
                                enType = "client";
                                break;
                            case "اپراتور":
                                enType = "operator";
                                break;
                            case "روانشناس":
                                enType = "psychologist";
                                break;
                        }
//                        TODO: do it
//                        doWork(enType, centerUsersFragment.id, holder.binding.serialTextView.getText().toString());
                    }
                }
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

        if (model.getUserKicked_at() != 0 || model.getUserAccepted_at() == 0)
            menuValues.add(activity.getResources().getString(R.string.CenterUsersFragmentAccept));
        if (model.getUserKicked_at() == 0)
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

    private void doWork(String position, String id, String userId) {
        HashMap data = new HashMap();
        data.put("position", position);
        data.put("id", id);
        data.put("userId", userId);
        HashMap header = new HashMap();
        header.put("Authorization", "Bearer " + ((MainActivity) activity).singleton.getToken());
        Center.changePosition(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                activity.runOnUiThread(() -> Toast.makeText(activity, "done", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onFailure(String response) {
                System.out.println(response);
            }
        });
    }

    public class CenterUsersHolder extends RecyclerView.ViewHolder {

        private SingleItemCenterUserBinding binding;

        public CenterUsersHolder(SingleItemCenterUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}