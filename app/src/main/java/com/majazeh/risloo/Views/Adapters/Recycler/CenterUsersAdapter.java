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

        InitManager.normalAdapterSpinner(activity, holder.binding.positionSpinner, R.array.UserTypes);
    }

    private void detector(CenterUsersHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(CenterUsersHolder holder, UserModel model) {
        ClickManager.onClickListener(() -> {
            Fragment current = ((MainActivity) activity).fragmont.getCurrent();

            if (current instanceof CenterUsersFragment) {
                NavDirections action = NavigationMainDirections.actionGlobalReferenceFragment(((CenterUsersFragment) current).type, ((CenterUsersFragment) current).centerId, null, model);
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

        holder.binding.menuSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                String pos = parent.getItemAtPosition(position).toString();

                Fragment current = ((MainActivity) activity).fragmont.getCurrent();

                switch (pos) {
                    case "پذیرفتن":
                        doWork(holder, model, "accept", "status");
                        break;
                    case "تعلیق":
                        doWork(holder, model, "kick", "status");
                        break;
                    case "ساختن اتاق درمان": {
                        if (current instanceof CenterUsersFragment) {
                            NavDirections action = NavigationMainDirections.actionGlobalCreateRoomFragment("user", ((CenterUsersFragment) current).centerId, model);
                            ((MainActivity) activity).navController.navigate(action);
                        }
                    } break;
                    case "اتاق درمان":
                        // TODO : Place Code If Needed
                        break;
                    case "ویرایش عضو": {
                        if (current instanceof CenterUsersFragment) {
                            NavDirections action = NavigationMainDirections.actionGlobalEditCenterUserFragment(((CenterUsersFragment) current).centerId, model);
                            ((MainActivity) activity).navController.navigate(action);
                        }
                    } break;
                    case "ورود به کاربری":
                        Log.e("method", "enter");
                        break;
                }

                    userSelect = false;
                }
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
        ArrayList<String> items = new ArrayList<>();

        if (model.getUserAccepted_at() == 0) {
            items.add(activity.getResources().getString(R.string.CenterUsersFragmentAccept));
            holder.binding.acceptedTextView.setText("");
        } else {
            holder.binding.statusTexView.setText(activity.getResources().getString(R.string.CenterUsersFragmentStatusAccepted));
            holder.binding.acceptedTextView.setText(DateManager.jalHHoMMoYYoMMoDD(String.valueOf(model.getUserAccepted_at())));
        }

        if (model.getUserKicked_at() == 0) {
            items.add(activity.getResources().getString(R.string.CenterUsersFragmentKick));
            holder.binding.kickedTextView.setText("");
        } else {
            holder.binding.statusTexView.setText(activity.getResources().getString(R.string.CenterUsersFragmentStatusKicked));
            holder.binding.kickedTextView.setText(DateManager.jalHHoMMoYYoMMoDD(String.valueOf(model.getUserKicked_at())));
        }

        if (model.getUserAccepted_at() == 0 && model.getUserKicked_at() == 0) {
            holder.binding.statusTexView.setText(activity.getResources().getString(R.string.CenterUsersFragmentStatusWaiting));
        }

        if (model.getUserAccepted_at() != 0 && model.getUserKicked_at() != 0) {
            items.add(activity.getResources().getString(R.string.CenterUsersFragmentAccept));
        }

        items.add(activity.getResources().getString(R.string.CenterUsersFragmentCreateRoom));
        items.add(activity.getResources().getString(R.string.CenterUsersFragmentRoom));
        items.add(activity.getResources().getString(R.string.CenterUsersFragmentEdit));
//        menu.add(activity.getResources().getString(R.string.CenterUsersFragmentEnter));
        items.add("");

        InitManager.actionCustomSpinner(activity, holder.binding.menuSpinner, items);
    }

    private void doWork(CenterUsersHolder holder, UserModel model, String value, String method) {
        ((MainActivity) activity).loadingDialog.show(((MainActivity) activity).getSupportFragmentManager(), "loadingDialog");

        Fragment current = ((MainActivity) activity).fragmont.getCurrent();

        if (method.equals("position")) {
            if (current instanceof CenterUsersFragment)
                data.put("id", ((CenterUsersFragment) current).centerId);

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
            if (current instanceof CenterUsersFragment)
                data.put("id", ((CenterUsersFragment) current).centerId);

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

    public class CenterUsersHolder extends RecyclerView.ViewHolder {

        private SingleItemCenterUserBinding binding;

        public CenterUsersHolder(SingleItemCenterUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}