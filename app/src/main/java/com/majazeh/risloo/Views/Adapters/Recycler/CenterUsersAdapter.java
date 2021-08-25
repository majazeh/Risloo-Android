package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.CenterUsersHolder;
import com.majazeh.risloo.Views.Fragments.Index.CenterUsersFragment;
import com.majazeh.risloo.databinding.SingleItemCenterUserBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;
import java.util.HashMap;

public class CenterUsersAdapter extends RecyclerView.Adapter<CenterUsersHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> items;
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
        UserModel model = (UserModel) items.get(i);

        initializer(holder);

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

    private void initializer(CenterUsersHolder holder) {
        current = ((MainActivity) activity).fragmont.getCurrent();

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
        CustomClickView.onClickListener(() -> {
            if (current instanceof CenterUsersFragment) {
                NavDirections action = NavigationMainDirections.actionGlobalReferenceFragment(((CenterUsersFragment) current).type, ((CenterUsersFragment) current).centerId, model);
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

                    doWork(holder, model, SelectionManager.getUserType(activity, "en", pos), "position");

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
                        case "ویرایش": {
                            if (current instanceof CenterUsersFragment) {
                                NavDirections action = NavigationMainDirections.actionGlobalEditCenterUserFragment(((CenterUsersFragment) current).centerId, model);
                                ((MainActivity) activity).navController.navigate(action);
                            }
                        } break;
                        case "ورود به کاربری":
                            // TODO : Place Code If Needed
                            break;
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
        String position = SelectionManager.getUserType(activity, "fa", model.getPosition());
        for (int i=0; i<holder.binding.positionSpinner.getCount(); i++) {
            if (holder.binding.positionSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(position)) {
                holder.binding.positionSpinner.setSelection(i);
            }
        }

        if (current instanceof CenterUsersFragment && !((CenterUsersFragment) current).centerModel.getManager().getUserId().equals(model.getUserId())) {
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

        if (model.getUserKicked_at() != 0 && model.getUserAccepted_at() != 0) {
            holder.binding.statusTexView.setText(activity.getResources().getString(R.string.CenterUsersFragmentStatusKicked));

            holder.binding.acceptedTextView.setText(DateManager.jalHHoMMoYYoMMoDD(String.valueOf(model.getUserAccepted_at())));
            holder.binding.kickedTextView.setText(DateManager.jalHHoMMoYYoMMoDD(String.valueOf(model.getUserKicked_at())));

            items.add(activity.getResources().getString(R.string.CenterUsersFragmentAccept));
        } else if (model.getUserKicked_at() != 0) {
            holder.binding.statusTexView.setText(activity.getResources().getString(R.string.CenterUsersFragmentStatusKicked));

            holder.binding.acceptedTextView.setText("");
            holder.binding.kickedTextView.setText(DateManager.jalHHoMMoYYoMMoDD(String.valueOf(model.getUserKicked_at())));

            items.add(activity.getResources().getString(R.string.CenterUsersFragmentAccept));
        } else if (model.getUserAccepted_at() != 0) {
            holder.binding.statusTexView.setText(activity.getResources().getString(R.string.RoomUsersFragmentStatusAccepted));

            holder.binding.acceptedTextView.setText(DateManager.jalHHoMMoYYoMMoDD(String.valueOf(model.getUserAccepted_at())));
            holder.binding.kickedTextView.setText("");

            items.add(activity.getResources().getString(R.string.CenterUsersFragmentKick));
        } else {
            holder.binding.statusTexView.setText(activity.getResources().getString(R.string.RoomUsersFragmentStatusWaiting));

            holder.binding.acceptedTextView.setText("");
            holder.binding.kickedTextView.setText("");

            items.add(activity.getResources().getString(R.string.CenterUsersFragmentAccept));
        }

        items.add(activity.getResources().getString(R.string.CenterUsersFragmentCreateRoom));
        items.add(activity.getResources().getString(R.string.CenterUsersFragmentRoom));
        items.add(activity.getResources().getString(R.string.CenterUsersFragmentEdit));
        items.add(activity.getResources().getString(R.string.CenterUsersFragmentEnter));
        items.add("");

        InitManager.actionCustomSpinner(activity, holder.binding.menuSpinner, items);
    }

    private void doWork(CenterUsersHolder holder, UserModel model, String value, String method) {
        DialogManager.showLoadingDialog(activity);

        if (current instanceof CenterUsersFragment)
            data.put("id", ((CenterUsersFragment) current).centerId);

        if (method.equals("position")) {
            data.put("userId", model.getId());
            data.put("position", value);

            Center.changePosition(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    activity.runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(activity, activity.getResources().getString(R.string.ToastChangesSaved));
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
            data.put("userId", model.getId());
            data.put("status", value);

            Center.changeStatus(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    UserModel userModel = (UserModel) object;

                    activity.runOnUiThread(() -> {
                        setAcceptation(holder, userModel);

                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(activity, activity.getResources().getString(R.string.ToastChangesSaved));
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

}