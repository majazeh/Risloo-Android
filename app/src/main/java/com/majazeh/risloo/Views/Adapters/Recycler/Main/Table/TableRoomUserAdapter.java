package com.majazeh.risloo.Views.Adapters.Recycler.Main.Table;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Header.HeaderRoomUserHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Table.TableRoomUserHolder;
import com.majazeh.risloo.Views.Fragments.Main.Index.RoomUsersFragment;
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
        current = ((MainActivity) activity).fragmont.getCurrent();
    }

    private void listener(TableRoomUserHolder holder, UserModel model) {
        CustomClickView.onClickListener(() -> {
            if (current instanceof RoomUsersFragment) {
                NavDirections action = NavigationMainDirections.actionGlobalReferenceFragment(((RoomUsersFragment) current).roomModel, model);
                ((MainActivity) activity).navController.navigate(action);
            }
        }).widget(holder.binding.getRoot());
    }

    private void setData(TableRoomUserHolder holder, UserModel model) {
        holder.binding.serialTextView.setText(model.getId());
        holder.binding.nameTextView.setText(model.getName());
        holder.binding.mobileTextView.setText(model.getMobile());
        holder.binding.positionTextView.setText(SelectionManager.getUserType(activity, "fa", model.getPosition()));

        setAcceptation(holder, model);
    }

    private void setAcceptation(TableRoomUserHolder holder, UserModel model) {
        if (model.getUserKicked_at() != 0 && model.getUserAccepted_at() != 0) {
            holder.binding.statusTexView.setText(activity.getResources().getString(R.string.RoomUsersFragmentStatusKicked));
            holder.binding.acceptedTextView.setText(DateManager.jalHHoMMoYYoMMoDD(String.valueOf(model.getUserKicked_at())));
        } else if (model.getUserKicked_at() != 0) {
            holder.binding.statusTexView.setText(activity.getResources().getString(R.string.RoomUsersFragmentStatusKicked));
            holder.binding.acceptedTextView.setText(DateManager.jalHHoMMoYYoMMoDD(String.valueOf(model.getUserKicked_at())));
        } else if (model.getUserAccepted_at() != 0) {
            holder.binding.statusTexView.setText(activity.getResources().getString(R.string.RoomUsersFragmentStatusAccepted));
            holder.binding.acceptedTextView.setText(DateManager.jalHHoMMoYYoMMoDD(String.valueOf(model.getUserAccepted_at())));
        } else {
            holder.binding.statusTexView.setText(activity.getResources().getString(R.string.RoomUsersFragmentStatusWaiting));
            holder.binding.acceptedTextView.setText("");
        }
    }

}