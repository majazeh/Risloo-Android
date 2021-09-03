package com.majazeh.risloo.Views.Adapters.Recycler.Index;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
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
import com.majazeh.risloo.Views.Adapters.Holder.Header.HeaderSession2Holder;
import com.majazeh.risloo.Views.Adapters.Holder.Index.IndexSession2Holder;
import com.majazeh.risloo.databinding.HeaderItemIndexSession2Binding;
import com.majazeh.risloo.databinding.SingleItemIndexSession2Binding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Session;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class IndexSession2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

    public IndexSession2Adapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderSession2Holder(HeaderItemIndexSession2Binding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new IndexSession2Holder(SingleItemIndexSession2Binding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof  IndexSession2Holder) {
            SessionModel model = (SessionModel) items.get(i - 1);

            initializer((IndexSession2Holder) holder);

            detector((IndexSession2Holder) holder);

            listener((IndexSession2Holder) holder, model);

            setData((IndexSession2Holder) holder, model);
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

    private void initializer(IndexSession2Holder holder) {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());

        InitManager.normalAdapterSpinner(activity, holder.binding.statusSpinner, R.array.SessionStatus);
    }

    private void detector(IndexSession2Holder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.editImageView.setBackgroundResource(R.drawable.draw_oval_solid_white_ripple_gray300);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(IndexSession2Holder holder, SessionModel model) {
        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalSessionFragment("session", model);
            ((MainActivity) activity).navController.navigate(action);
        }).widget(holder.binding.getRoot());

        holder.binding.statusSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

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
            NavDirections action = NavigationMainDirections.actionGlobalEditSessionFragment(model);
            ((MainActivity) activity).navController.navigate(action);
        }).widget(holder.binding.editImageView);
    }

    private void setData(IndexSession2Holder holder, SessionModel model) {
        holder.binding.serialTextView.setText(model.getId());
        holder.binding.startTimeTextView.setText(DateManager.jalYYYYsNMMsDDsNDDnlHHsMM(String.valueOf(model.getStarted_at()), " "));
        holder.binding.durationTextView.setText(model.getDuration() + " " + "دقیقه");

        setStatus(holder, model);
    }

    private void setStatus(IndexSession2Holder holder, SessionModel model) {
        String status = SelectionManager.getSessionStatus(activity, "fa", model.getStatus());
        for (int i=0; i<holder.binding.statusSpinner.getCount(); i++) {
            if (holder.binding.statusSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(status)) {
                holder.binding.statusSpinner.setSelection(i);
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

    private void doWork(SessionModel model, String status) {
        DialogManager.showLoadingDialog(activity);

        data.put("id", model.getId());
        data.put("status", status);

        Session.edit(data, header, new Response() {
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
    }

}