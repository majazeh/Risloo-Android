package com.majazeh.risloo.views.adapters.recycler.main.Table;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.databinding.HeaderItemTableUserBinding;
import com.majazeh.risloo.databinding.SingleItemTableUserBinding;
import com.majazeh.risloo.utils.managers.IntentManager;
import com.majazeh.risloo.utils.managers.JsonManager;
import com.majazeh.risloo.utils.managers.DropdownManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.holder.main.Header.HeaderUserHolder;
import com.majazeh.risloo.views.adapters.holder.main.Table.TableUserHolder;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;

public class TableUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

    public TableUserAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderUserHolder(HeaderItemTableUserBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TableUserHolder(SingleItemTableUserBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof TableUserHolder) {
            UserModel model = (UserModel) items.get(i - 1);

            listener((TableUserHolder) holder, model);

            setData((TableUserHolder) holder, model);
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

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TableUserHolder holder, UserModel model) {
        CustomClickView.onClickListener(() -> {
            ((ActivityMain) activity).navigatoon.navigateToFragmentUser(model);
        }).widget(holder.binding.getRoot());

        holder.binding.menuSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.menuSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        holder.binding.menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    if (pos.contains("989"))
                        IntentManager.tel(activity, pos);
                    else if (pos.contains("@"))
                        IntentManager.email(activity, new String[]{pos}, "", "");
                    else if (pos.equals("ورود به کاربری"))
                        ((ActivityMain) activity).setUser("loginOtherUser", model.getId());
                    else if (pos.equals("ویرایش"))
                        ((ActivityMain) activity).navigatoon.navigateToFragmentEditUser(model);

                    parent.setSelection(parent.getAdapter().getCount());

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(TableUserHolder holder, UserModel model) {
        holder.binding.serialTextView.setText(model.getId());
        holder.binding.nameTextView.setText(model.getName());
        holder.binding.typeTextView.setText(JsonManager.getUserType(activity, "fa", model.getType()));
        holder.binding.statusTextView.setText(JsonManager.getUserStatus(activity, "fa", model.getStatus()));

        setMenu(holder, model);
    }

    private void setMenu(TableUserHolder holder, UserModel model) {
        ArrayList<String> items = new ArrayList<>();

        if (!model.getMobile().equals(""))
            items.add(model.getMobile());

        if (!model.getEmail().equals(""))
            items.add(model.getEmail());

        items.add(activity.getResources().getString(R.string.UserAdapterLogin));
        items.add(activity.getResources().getString(R.string.UserAdapterEdit));
        items.add("");

        if (items.size() > 1) {
            holder.binding.menuGroup.setVisibility(View.VISIBLE);
            DropdownManager.spinnerSelectAction(activity, holder.binding.menuSpinner, items);
        } else {
            holder.binding.menuGroup.setVisibility(View.INVISIBLE);
        }
    }

}