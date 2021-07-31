package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Index.RoomUsersFragment;
import com.majazeh.risloo.databinding.SingleItemRoomUserBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;

public class RoomUsersAdapter extends RecyclerView.Adapter<RoomUsersAdapter.RoomUsersHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public RoomUsersAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RoomUsersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RoomUsersHolder(SingleItemRoomUserBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RoomUsersHolder holder, int i) {
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

    private void initializer(RoomUsersHolder holder) {
        current = ((MainActivity) activity).fragmont.getCurrent();
    }

    private void detector(RoomUsersHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(RoomUsersHolder holder, UserModel model) {
        ClickManager.onClickListener(() -> {
            if (current instanceof RoomUsersFragment) {
                NavDirections action = NavigationMainDirections.actionGlobalReferenceFragment(((RoomUsersFragment) current).type, ((RoomUsersFragment) current).centerId, model);
                ((MainActivity) activity).navController.navigate(action);
            }
        }).widget(holder.binding.getRoot());
    }

    private void setData(RoomUsersHolder holder, UserModel model) {
        if (holder.getBindingAdapterPosition() == 0)
            holder.binding.topView.setVisibility(View.GONE);
        else
            holder.binding.topView.setVisibility(View.VISIBLE);

        holder.binding.serialTextView.setText(model.getId());
        holder.binding.nameTextView.setText(model.getName());
        holder.binding.mobileTextView.setText(model.getMobile());

        holder.binding.positionTextView.setText(SelectionManager.getPosition(activity, "fa", model.getPosition()));

        setAcceptation(holder, model);
    }

    private void setAcceptation(RoomUsersHolder holder, UserModel model) {
        if (model.getUserAccepted_at() != 0) {
            holder.binding.statusTexView.setText(activity.getResources().getString(R.string.RoomUsersFragmentStatusAccepted));
            holder.binding.acceptedTextView.setText(DateManager.jalHHoMMoYYoMMoDD(String.valueOf(model.getUserAccepted_at())));
        }
    }

    public class RoomUsersHolder extends RecyclerView.ViewHolder {

        private SingleItemRoomUserBinding binding;

        public RoomUsersHolder(SingleItemRoomUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}