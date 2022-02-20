package com.majazeh.risloo.Views.Adapters.Recycler.Main.Table;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Header.HeaderSession2Holder;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Table.TableSession2Holder;
import com.majazeh.risloo.databinding.HeaderItemTableSession2Binding;
import com.majazeh.risloo.databinding.SingleItemTableSession2Binding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Session;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class TableSession2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

    public TableSession2Adapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderSession2Holder(HeaderItemTableSession2Binding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TableSession2Holder(SingleItemTableSession2Binding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof TableSession2Holder) {
            SessionModel model = (SessionModel) items.get(i - 1);

            initializer((TableSession2Holder) holder);

            listener((TableSession2Holder) holder, model);

            setData((TableSession2Holder) holder, model);
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

    private void initializer(TableSession2Holder holder) {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());

        InitManager.input8sspSpinner(activity, holder.binding.statusSpinner, R.array.SessionStatus);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TableSession2Holder holder, SessionModel model) {
        CustomClickView.onClickListener(() -> {
            ((MainActivity) activity).navigatoon.navigateToSessionFragment(model);
        }).widget(holder.binding.getRoot());

        holder.binding.statusSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.statusSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        holder.binding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    doWork(model, SelectionManager.getSessionStatus(activity, "en", pos));

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CustomClickView.onClickListener(() -> {
            ((MainActivity) activity).navigatoon.navigateToEditSessionFragment(model);
        }).widget(holder.binding.editImageView);
    }

    private void setData(TableSession2Holder holder, SessionModel model) {
        holder.binding.serialTextView.setText(model.getId());
        holder.binding.startTimeTextView.setText(DateManager.jalYYYYsNMMsDDsNDDnlHHsMM(String.valueOf(model.getStartedAt()), " "));
        holder.binding.durationTextView.setText(model.getDuration() + " " + "دقیقه");

        setStatus(holder, model);
    }

    private void setStatus(TableSession2Holder holder, SessionModel model) {
        String status = SelectionManager.getSessionStatus(activity, "fa", model.getStatus());
        for (int i=0; i<holder.binding.statusSpinner.getCount(); i++) {
            if (holder.binding.statusSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(status)) {
                holder.binding.statusSpinner.setSelection(i);
            }
        }

        boolean enable = true;

        if (enable) {
            holder.binding.statusSpinner.setEnabled(true);
            holder.binding.statusSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_coolgray300);

            holder.binding.statusAngleImageView.setVisibility(View.VISIBLE);
        } else {
            holder.binding.statusSpinner.setEnabled(false);
            holder.binding.statusSpinner.setBackgroundResource(android.R.color.transparent);

            holder.binding.statusAngleImageView.setVisibility(View.GONE);
        }
    }

    private void setHashmap(SessionModel model, String status) {
        data.put("id", model.getId());
        data.put("status", status);
    }

    private void doWork(SessionModel model, String status) {
        DialogManager.showLoadingDialog(activity, "");

        setHashmap(model, status);

        Session.edit(data, header, new Response() {
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
    }

}