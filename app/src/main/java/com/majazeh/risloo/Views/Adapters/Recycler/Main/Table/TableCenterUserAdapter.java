package com.majazeh.risloo.views.adapters.recycler.main.Table;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.DateManager;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.SelectionManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.holder.main.Header.HeaderCenterUserHolder;
import com.majazeh.risloo.views.adapters.holder.main.Table.TableCenterUserHolder;
import com.majazeh.risloo.views.fragments.main.index.FragmentCenterUsers;
import com.majazeh.risloo.databinding.HeaderItemTableCenterUserBinding;
import com.majazeh.risloo.databinding.SingleItemTableCenterUserBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TableCenterUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

    public TableCenterUserAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderCenterUserHolder(HeaderItemTableCenterUserBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TableCenterUserHolder(SingleItemTableCenterUserBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof TableCenterUserHolder) {
            UserModel model = (UserModel) items.get(i - 1);

            initializer((TableCenterUserHolder) holder);

            listener((TableCenterUserHolder) holder, model);

            setData((TableCenterUserHolder) holder, model);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;

        return 1;
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size() + 1;
        else
            return 0;
    }

    public int itemsCount() {
        if (this.items != null)
            return items.size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items) {
        userSelect = false;

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

    private void initializer(TableCenterUserHolder holder) {
        current = ((ActivityMain) activity).fragmont.getCurrent();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) activity).singleton.getAuthorization());

        InitManager.input8sspSpinner(activity, holder.binding.positionSpinner, R.array.UserTypes);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TableCenterUserHolder holder, UserModel model) {
        CustomClickView.onClickListener(() -> {
            if (current instanceof FragmentCenterUsers)
                ((ActivityMain) activity).navigatoon.navigateToFragmentReference(((FragmentCenterUsers) current).centerModel, model);

        }).widget(holder.binding.getRoot());

        holder.binding.positionSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.menuSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.positionSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        holder.binding.menuSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

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
                        case "ساختن اتاق درمان":
                            if (current instanceof FragmentCenterUsers)
                                ((ActivityMain) activity).navigatoon.navigateToFragmentCreateRoom(((FragmentCenterUsers) current).centerModel, model);

                            break;
                        case "اتاق درمان":
                            try {
                                RoomModel roomModel = new RoomModel(new JSONObject().put("id", model.getMeta().getString("room_id")).put("type", "room").put("manager", model.object));

                                ((ActivityMain) activity).navigatoon.navigateToFragmentRoom(roomModel);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "ویرایش":
                            if (current instanceof FragmentCenterUsers)
                                ((ActivityMain) activity).navigatoon.navigateToFragmentEditCenterUser(((FragmentCenterUsers) current).centerModel, model);

                            break;
                        case "ورود به کاربری":
                            ((ActivityMain) activity).setUser("loginOtherUser", model.getId());
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

    private void setData(TableCenterUserHolder holder, UserModel model) {
        holder.binding.serialTextView.setText(model.getId());
        holder.binding.nameTextView.setText(model.getName());
        holder.binding.mobileTextView.setText(model.getMobile());

        setPosition(holder, model);

        setAcceptation(holder, model);
    }

    private void setPosition(TableCenterUserHolder holder, UserModel model) {
        String position = SelectionManager.getUserType(activity, "fa", model.getPosition());
        for (int i=0; i<holder.binding.positionSpinner.getCount(); i++) {
            if (holder.binding.positionSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(position)) {
                holder.binding.positionSpinner.setSelection(i);
            }
        }

        if (current instanceof FragmentCenterUsers && !((FragmentCenterUsers) current).centerModel.getManager().getUserId().equals(model.getUserId())) {
            holder.binding.positionSpinner.setEnabled(true);
            holder.binding.positionSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_coolgray300);

            holder.binding.positionAngleImageView.setVisibility(View.VISIBLE);
        } else {
            holder.binding.positionSpinner.setEnabled(false);
            holder.binding.positionSpinner.setBackgroundResource(android.R.color.transparent);

            holder.binding.positionAngleImageView.setVisibility(View.GONE);
        }
    }

    private void setAcceptation(TableCenterUserHolder holder, UserModel model) {
        ArrayList<String> items = new ArrayList<>();

        if (model.getMeta() != null) {
            items.add(activity.getResources().getString(R.string.CenterUserAdapterRoom));
        } else {
            if (!model.getPosition().equals("client"))
                items.add(activity.getResources().getString(R.string.CenterUserAdapterCreateRoom));
        }

        if (model.getKickedAt() != 0 && model.getAcceptedAt() != 0) {
            holder.binding.statusTexView.setText(SelectionManager.getAcceptation(activity, "fa", "kicked"));

            holder.binding.acceptedTextView.setText(DateManager.jalHHoMMoYYoMMoDD(String.valueOf(model.getAcceptedAt())));
            holder.binding.kickedTextView.setText(DateManager.jalHHoMMoYYoMMoDD(String.valueOf(model.getKickedAt())));

            items.add(activity.getResources().getString(R.string.CenterUserAdapterAccept));
        } else if (model.getKickedAt() != 0) {
            holder.binding.statusTexView.setText(SelectionManager.getAcceptation(activity, "fa", "kicked"));

            holder.binding.acceptedTextView.setText("");
            holder.binding.kickedTextView.setText(DateManager.jalHHoMMoYYoMMoDD(String.valueOf(model.getKickedAt())));

            items.add(activity.getResources().getString(R.string.CenterUserAdapterAccept));
        } else if (model.getAcceptedAt() != 0) {
            holder.binding.statusTexView.setText(SelectionManager.getAcceptation(activity, "fa", "accepted"));

            holder.binding.acceptedTextView.setText(DateManager.jalHHoMMoYYoMMoDD(String.valueOf(model.getAcceptedAt())));
            holder.binding.kickedTextView.setText("");

            items.add(activity.getResources().getString(R.string.CenterUserAdapterKick));
        } else {
            holder.binding.statusTexView.setText(SelectionManager.getAcceptation(activity, "fa", "waiting"));

            holder.binding.acceptedTextView.setText("");
            holder.binding.kickedTextView.setText("");

            items.add(activity.getResources().getString(R.string.CenterUserAdapterAccept));
        }

        items.add(activity.getResources().getString(R.string.CenterUserAdapterEdit));
        items.add(activity.getResources().getString(R.string.CenterUserAdapterEnter));
        items.add("");

        InitManager.selectCustomActionSpinner(activity, holder.binding.menuSpinner, items);
    }

    private void setHashmap(UserModel model, String value, String method) {
        if (current instanceof FragmentCenterUsers)
            data.put("id", ((FragmentCenterUsers) current).centerModel.getId());

        if (method.equals("position")) {
            data.put("userId", model.getId());
            data.put("position", value);

            data.remove("status");
        } else {
            data.put("userId", model.getId());
            data.put("status", value);

            data.remove("position");
        }
    }

    private void doWork(TableCenterUserHolder holder, UserModel model, String value, String method) {
        DialogManager.showLoadingDialog(activity, "");

        setHashmap(model, value, method);

        if (method.equals("position")) {
            Center.changePosition(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    activity.runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(activity, activity.getResources().getString(R.string.SnackChangesSaved));
                    });
                }

                @Override
                public void onFailure(String response) {
                    activity.runOnUiThread(() -> {
                        // TODO : Place Code If Needed
                    });
                }
            });
        } else {
            Center.changeStatus(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    UserModel userModel = (UserModel) object;

                    activity.runOnUiThread(() -> {
                        setAcceptation(holder, userModel);

                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(activity, activity.getResources().getString(R.string.SnackChangesSaved));
                    });
                }

                @Override
                public void onFailure(String response) {
                    activity.runOnUiThread(() -> {
                        // TODO : Place Code If Needed
                    });
                }
            });
        }
    }

}