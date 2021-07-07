package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Index.CenterUsersFragment;
import com.majazeh.risloo.databinding.SingleItemCenterUserBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;
import java.util.HashMap;

public class CenterUsersAdapter extends RecyclerView.Adapter<CenterUsersAdapter.CenterUsersHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> users;
    private HashMap data, header;
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
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());

        InitManager.fixedSpinner(activity, holder.binding.positionSpinner, R.array.UserTypes, "adapter2");
    }

    private void detector(CenterUsersHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(CenterUsersHolder holder, UserModel model) {
        ClickManager.onClickListener(() -> {
            if (getParent() != null) {
                NavDirections action = NavigationMainDirections.actionGlobalReferenceFragment(getParent().type, getParent().centerId, null, model);
                ((MainActivity) activity).navController.navigate(action);
            }
        }).widget(holder.binding.getRoot());

        holder.binding.positionSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.positionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    doWork(holder, model, SelectionManager.getPosition(activity, "en", pos), "position");

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
                String item = parent.getItemAtPosition(position).toString();

                switch (item) {
                    case "پذیرفتن":
                        doWork(holder, model, "accept", "status");
                        break;
                    case "تعلیق":
                        doWork(holder, model, "kick", "status");
                        break;
                    case "ساختن اتاق درمان": {
                        NavDirections action = NavigationMainDirections.actionGlobalCreateRoomFragment("user", model);
                        ((MainActivity) activity).navController.navigate(action);
                    } break;
                    case "اتاق درمان": {
                        if (getParent() != null) {
                            NavDirections action = NavigationMainDirections.actionGlobalRoomFragment(getParent().type, model);
                            ((MainActivity) activity).navController.navigate(action);
                        }
                    } break;
                    case "ویرایش کاربر": {
                        if (getParent() != null) {
                            NavDirections action = NavigationMainDirections.actionGlobalEditCenterUserFragment(getParent().centerId, model);
                            ((MainActivity) activity).navController.navigate(action);
                        }
                    } break;
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
        if (holder.getBindingAdapterPosition() == 0)
            holder.binding.topView.setVisibility(View.GONE);
        else
            holder.binding.topView.setVisibility(View.VISIBLE);

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
            holder.binding.acceptedTextView.setText(DateManager.jalHHoMMoYYoMMoDD(String.valueOf(model.getUserAccepted_at())));
        }

        if (model.getUserKicked_at() == 0) {
            menu.add(activity.getResources().getString(R.string.CenterUsersFragmentKick));
        } else {
            holder.binding.statusTexView.setText(activity.getResources().getString(R.string.CenterUsersFragmentStatusKicked));
            holder.binding.kickedTextView.setText(DateManager.jalHHoMMoYYoMMoDD(String.valueOf(model.getUserKicked_at())));
        }

        if (model.getUserAccepted_at() == 0 && model.getUserKicked_at() == 0) {
            holder.binding.statusTexView.setText(activity.getResources().getString(R.string.CenterUsersFragmentStatusWaiting));
        }

        menu.add(activity.getResources().getString(R.string.CenterUsersFragmentCreateRoom));
        menu.add(activity.getResources().getString(R.string.CenterUsersFragmentRoom));
        menu.add(activity.getResources().getString(R.string.CenterUsersFragmentEdit));
        menu.add(activity.getResources().getString(R.string.CenterUsersFragmentEnter));
        menu.add("");

        InitManager.unfixedCustomSpinner(activity, holder.binding.menuSpinner, menu, "centerUsers");
    }

    private void doWork(CenterUsersHolder holder, UserModel model, String value, String method) {
        ((MainActivity) activity).loadingDialog.show(((MainActivity) activity).getSupportFragmentManager(), "loadingDialog");

        if (method.equals("position")) {
            if (getParent() != null)
                data.put("id", getParent().centerId);

            data.put("userId", model.getId());
            data.put("position", value);

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
                    activity.runOnUiThread(() -> {
                        // Place Code if Needed
                    });
                }
            });
        } else {
            if (getParent() != null)
                data.put("id", getParent().centerId);

            data.put("userId", model.getId());
            data.put("status", value);

            Center.changeStatus(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    UserModel userModel = (UserModel) object;

                    activity.runOnUiThread(() -> {
                        setAcceptation(holder, userModel);

                        ((MainActivity) activity).loadingDialog.dismiss();
                        Toast.makeText(activity, activity.getResources().getString(R.string.AppChanged), Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onFailure(String response) {
                    activity.runOnUiThread(() -> {
                        // Place Code if Needed
                    });
                }
            });
        }
    }

    private CenterUsersFragment getParent() {
        Fragment fragment = ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (fragment != null)
            if (fragment instanceof CenterUsersFragment)
                return (CenterUsersFragment) fragment;

        return null;
    }

    public class CenterUsersHolder extends RecyclerView.ViewHolder {

        private SingleItemCenterUserBinding binding;

        public CenterUsersHolder(SingleItemCenterUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}