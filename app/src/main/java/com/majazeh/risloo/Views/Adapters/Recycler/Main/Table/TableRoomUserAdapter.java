package com.majazeh.risloo.views.adapters.recycler.main.Table;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.utils.managers.DateManager;
import com.majazeh.risloo.utils.managers.JsonManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.holder.main.Header.HeaderRoomUserHolder;
import com.majazeh.risloo.views.adapters.holder.main.Table.TableRoomUserHolder;
import com.majazeh.risloo.views.fragments.main.index.FragmentRoomUsers;
import com.majazeh.risloo.databinding.HeaderItemTableRoomUserBinding;
import com.majazeh.risloo.databinding.SingleItemTableRoomUserBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;

public class TableRoomUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public TableRoomUserAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderRoomUserHolder(HeaderItemTableRoomUserBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TableRoomUserHolder(SingleItemTableRoomUserBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof TableRoomUserHolder) {
            UserModel model = (UserModel) items.get(i - 1);

            initializer((TableRoomUserHolder) holder);

            listener((TableRoomUserHolder) holder, model);

            setData((TableRoomUserHolder) holder, model);
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

    private void initializer(TableRoomUserHolder holder) {
        current = ((ActivityMain) activity).fragmont.getCurrent();
    }

    private void listener(TableRoomUserHolder holder, UserModel model) {
        CustomClickView.onClickListener(() -> {
            if (current instanceof FragmentRoomUsers)
                ((ActivityMain) activity).navigatoon.navigateToFragmentReference(((FragmentRoomUsers) current).roomModel, model);

        }).widget(holder.binding.getRoot());
    }

    private void setData(TableRoomUserHolder holder, UserModel model) {
        holder.binding.serialTextView.setText(model.getId());
        holder.binding.nameTextView.setText(model.getName());
        holder.binding.mobileTextView.setText(model.getMobile());
        holder.binding.positionTextView.setText(JsonManager.getUserType(activity, "fa", model.getPosition()));

        setAcceptation(holder, model);
    }

    private void setAcceptation(TableRoomUserHolder holder, UserModel model) {
        if (model.getKickedAt() != 0 && model.getAcceptedAt() != 0) {
            holder.binding.statusTexView.setText(JsonManager.getAcceptation(activity, "fa", "kicked"));
            holder.binding.acceptedTextView.setText(DateManager.jalHHcMMsYYsMMsDD(String.valueOf(model.getKickedAt()), "-"));
        } else if (model.getKickedAt() != 0) {
            holder.binding.statusTexView.setText(JsonManager.getAcceptation(activity, "fa", "kicked"));
            holder.binding.acceptedTextView.setText(DateManager.jalHHcMMsYYsMMsDD(String.valueOf(model.getKickedAt()), "-"));
        } else if (model.getAcceptedAt() != 0) {
            holder.binding.statusTexView.setText(JsonManager.getAcceptation(activity, "fa", "accepted"));
            holder.binding.acceptedTextView.setText(DateManager.jalHHcMMsYYsMMsDD(String.valueOf(model.getAcceptedAt()), "-"));
        } else {
            holder.binding.statusTexView.setText(JsonManager.getAcceptation(activity, "fa", "waiting"));
            holder.binding.acceptedTextView.setText("");
        }
    }

}