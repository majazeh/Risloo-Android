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
import com.majazeh.risloo.databinding.HeaderItemTableUser2Binding;
import com.majazeh.risloo.databinding.SingleItemTableUser2Binding;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.JsonManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.DropdownManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.holder.main.Header.HeaderUser2Holder;
import com.majazeh.risloo.views.adapters.holder.main.Table.TableUser2Holder;
import com.majazeh.risloo.views.fragments.main.show.FragmentSession;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Session;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;
import java.util.HashMap;

public class TableUser2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> items;
    private ArrayList<String> platformIds = new ArrayList<>();
    private boolean userSelect = false;

    public TableUser2Adapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderUser2Holder(HeaderItemTableUser2Binding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TableUser2Holder(SingleItemTableUser2Binding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof TableUser2Holder) {
            UserModel model = (UserModel) items.get(i - 1);

            initializer((TableUser2Holder) holder);

            listener((TableUser2Holder) holder, model);

            setData((TableUser2Holder) holder, model);
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

    private void initializer(TableUser2Holder holder) {
        current = ((ActivityMain) activity).fragmont.getCurrent();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) activity).singleton.getAuthorization());

        DropdownManager.spinner8ssp(activity, holder.binding.positionSpinner, R.array.UserPosition);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TableUser2Holder holder, UserModel model) {
        CustomClickView.onClickListener(() -> {
            if (current instanceof FragmentSession) {
                if (((FragmentSession) current).sessionModel != null && ((FragmentSession) current).sessionModel.getRoom() != null)
                    ((ActivityMain) activity).navigatoon.navigateToFragmentReference(((FragmentSession) current).sessionModel.getRoom(), model);

            }
        }).widget(holder.binding.getRoot());

        holder.binding.platformSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.positionSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.platformSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        holder.binding.positionSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        holder.binding.platformSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    doWork(model, platformIds.get(position), "platform");

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.binding.positionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    doWork(model, JsonManager.getUserPosition(activity, "en", pos), "position");

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(TableUser2Holder holder, UserModel model) {
        holder.binding.nameTextView.setText(model.getName());

        if (model.getField() != null)
            holder.binding.fieldTextView.setText(model.getField().getTitle());

        if (current instanceof FragmentSession)
            if (((FragmentSession) current).sessionModel != null && ((FragmentSession) current).sessionModel.getAvailableSessionPlatforms() != null)
                setPlatform(holder, ((FragmentSession) current).sessionModel.getAvailableSessionPlatforms(), model);

        setPosition(holder, model);
    }

    private void setPlatform(TableUser2Holder holder, List platforms, UserModel model) {
        // Set Platform Spinner
        ArrayList<String> options = new ArrayList<>();

        for (TypeModel typeModel : platforms.data()) {
            SessionPlatformModel sessionPlatformModel = (SessionPlatformModel) typeModel;

            if (sessionPlatformModel != null) {
                options.add(sessionPlatformModel.getTitle());
                platformIds.add(sessionPlatformModel.getId());
            }
        }

        options.add("");
        platformIds.add("");

        DropdownManager.spinner8ssp(activity, holder.binding.platformSpinner, options);

        // Set Selected Platform
        if (model.getSessionPlatform() != null) {
            String platform = model.getSessionPlatform().getTitle();
            for (int i=0; i<holder.binding.platformSpinner.getCount(); i++) {
                if (holder.binding.platformSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(platform)) {
                    holder.binding.platformSpinner.setSelection(i);
                }
            }
        }

        boolean enable = true;

        if (enable) {
            holder.binding.platformSpinner.setEnabled(true);
            holder.binding.platformSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_coolgray300);

            holder.binding.platformAngleImageView.setVisibility(View.VISIBLE);
        } else {
            holder.binding.platformSpinner.setEnabled(false);
            holder.binding.platformSpinner.setBackgroundResource(android.R.color.transparent);

            holder.binding.platformAngleImageView.setVisibility(View.GONE);
        }
    }

    private void setPosition(TableUser2Holder holder, UserModel model) {
        String position = JsonManager.getUserPosition(activity, "fa", model.getPosition());
        for (int i=0; i<holder.binding.positionSpinner.getCount(); i++) {
            if (holder.binding.positionSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(position)) {
                holder.binding.positionSpinner.setSelection(i);
            }
        }

        boolean enable = true;

        if (enable) {
            holder.binding.positionSpinner.setEnabled(true);
            holder.binding.positionSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_coolgray300);

            holder.binding.positionAngleImageView.setVisibility(View.VISIBLE);
        } else {
            holder.binding.positionSpinner.setEnabled(false);
            holder.binding.positionSpinner.setBackgroundResource(android.R.color.transparent);

            holder.binding.positionAngleImageView.setVisibility(View.GONE);
        }
    }

    private void setHashmap(UserModel model, String value, String method) {
        if (current instanceof FragmentSession)
            data.put("id", ((FragmentSession) current).sessionModel.getId());

        if (method.equals("position")) {
            data.put("userId", model.getId());
            data.put("position", value);

            data.remove("session_platform");
        } else {
            data.put("userId", model.getId());
            data.put("session_platform", value);

            data.remove("position");
        }
    }

    private void doWork(UserModel model, String value, String method) {
        DialogManager.showDialogLoading(activity, "");

        setHashmap(model, value, method);

        Session.editUser(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                activity.runOnUiThread(() -> {
                    DialogManager.dismissDialogLoading();
                    SnackManager.showSnackSucces(activity, activity.getResources().getString(R.string.SnackChangesSaved));
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