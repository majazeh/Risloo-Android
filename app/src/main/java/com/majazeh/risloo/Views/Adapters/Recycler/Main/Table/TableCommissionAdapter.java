package com.majazeh.risloo.Views.adapters.recycler.main.Table;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.Utils.widgets.CustomClickView;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.Views.adapters.holder.main.Header.HeaderCommissionHolder;
import com.majazeh.risloo.Views.adapters.holder.main.Table.TableCommissionHolder;
import com.majazeh.risloo.databinding.HeaderItemTableCommissionBinding;
import com.majazeh.risloo.databinding.SingleItemTableCommissionBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class TableCommissionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;
    private Handler handler;
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

    public TableCommissionAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderCommissionHolder(HeaderItemTableCommissionBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TableCommissionHolder(SingleItemTableCommissionBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
         if (holder instanceof TableCommissionHolder) {
//            CommissionModel model = (CommissionModel) items.get(i - 1);

            initializer();

            listener((TableCommissionHolder) holder);

            setData((TableCommissionHolder) holder);
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
            return 5;
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

    private void initializer() {
        current = ((MainActivity) activity).fragmont.getCurrent();

        handler = new Handler();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TableCommissionHolder holder) {
        CustomClickView.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        holder.binding.contributionCenterEditText.setOnTouchListener((v, event) -> {
            if (!holder.binding.pinnedCheckbox.isChecked())
                if (MotionEvent.ACTION_UP == event.getAction() && !holder.binding.contributionCenterEditText.hasFocus())
                    ((MainActivity) activity).inputon.select(holder.binding.contributionCenterEditText);
            return false;
        });

        holder.binding.contributionCenterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (holder.binding.contributionCenterEditText.hasFocus()) {
                    handler.removeCallbacksAndMessages(null);
                    handler.postDelayed(() -> {
                        if (!holder.binding.pinnedCheckbox.isChecked()) {
                            String contribution = holder.binding.contributionCenterEditText.getText().toString().trim();

                            if (!contribution.equals(""))
                                doWork(holder, contribution, "commission");
                        }
                    },750);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.binding.pinnedCheckbox.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.pinnedCheckbox.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        holder.binding.pinnedCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (userSelect) {
                if (isChecked)
                    doWork(holder, "1", "pinned");
                else
                    doWork(holder, "0", "pinned");

                setPinned(holder, isChecked);

                userSelect = false;
            }
        });
    }

    private void setData(TableCommissionHolder holder) {
        holder.binding.roomTextView.setText("محمد رضا سالاري فر");

        holder.binding.contributionRoomTextView.setText("50%");
        holder.binding.contributionCenterEditText.setText("50");

        setPinned(holder, true);
    }

    private void setPinned(TableCommissionHolder holder, boolean isPinned) {
        if (isPinned) {
            holder.binding.pinnedCheckbox.setChecked(true);

            holder.binding.contributionCenterEditText.setFocusableInTouchMode(true);
            holder.binding.contributionCenterEditText.setAlpha((float) 1);
        } else {
            holder.binding.pinnedCheckbox.setChecked(false);

            holder.binding.contributionCenterEditText.setFocusableInTouchMode(false);
            holder.binding.contributionCenterEditText.setAlpha((float) 0.6);
        }
    }

    private void setHashmap(String value, String method) {
        // TODO : Place Code Here

        data.put("room_id", "?????");
        data.put(method, value);
    }

    private void doWork(TableCommissionHolder holder, String value, String method) {
//        DialogManager.showLoadingDialog(activity, "");
//
//        setHashmap(value, method);
//
//        Commission.edit(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                activity.runOnUiThread(() -> {
//                    DialogManager.dismissLoadingDialog();
//                    SnackManager.showSuccesSnack(activity, activity.getResources().getString(R.string.SnackChangesSaved));
//                });
//            }
//
//            @Override
//            public void onFailure(String response) {
//                activity.runOnUiThread(() -> {
//                    resetWidget(holder, value, method);
//                });
//            }
//        });
    }

    private void resetWidget(TableCommissionHolder holder, String value, String method) {
        if (method.equals("pinned")) {
            if (value.equals("1"))
                setPinned(holder, false);
            else if (value.equals("0"))
                setPinned(holder, true);
        }
    }

}