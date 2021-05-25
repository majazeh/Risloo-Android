package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Index.RoomUsersFragment;
import com.majazeh.risloo.databinding.SingleItemRoomUserBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.ArrayList;
import java.util.Objects;

public class RoomUsersAdapter extends RecyclerView.Adapter<RoomUsersAdapter.RoomUsersHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> users;

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
        UserModel user = (UserModel) users.get(i);

        detector(holder);

        listener(holder, user);

        setData(holder, user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(ArrayList<TypeModel> users) {
        if (this.users == null)
            this.users = users;
        else
            this.users.addAll(users);
        notifyDataSetChanged();
    }

    public void clearUsers() {
        if (this.users != null) {
            this.users.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(RoomUsersHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(RoomUsersHolder holder, UserModel model) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.referenceFragment, getExtras(holder, model))).widget(holder.binding.getRoot());
    }

    private void setData(RoomUsersHolder holder, UserModel model) {
        if (holder.getBindingAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText(model.getUserId());
        holder.binding.nameTextView.setText(model.getName());
        holder.binding.mobileTextView.setText(model.getMobile());

        setPosition(holder, model);

        setAcceptation(holder, model);
    }

    private void setPosition(RoomUsersHolder holder, UserModel model) {
        switch (model.getPosition()) {
            case "مدیر":
            case "manager":
                holder.binding.positionTextView.setText(activity.getResources().getString(R.string.RoomUsersFragmentPositionManager));
                break;
            case "اپراتور":
            case "operator":
                holder.binding.positionTextView.setText(activity.getResources().getString(R.string.RoomUsersFragmentPositionOperator));
                break;
            case "روان\u200Cشناس":
            case "psychologist":
                holder.binding.positionTextView.setText(activity.getResources().getString(R.string.RoomUsersFragmentPositionPsychologist));
                break;
            case "مراجع":
            case "client":
                holder.binding.positionTextView.setText(activity.getResources().getString(R.string.RoomUsersFragmentPositionClient));
                break;
        }
    }

    private void setAcceptation(RoomUsersHolder holder, UserModel model) {
        if (model.getUserAccepted_at() != 0) {
            holder.binding.statusTexView.setText(activity.getResources().getString(R.string.RoomUsersFragmentStatusAccepted));
            holder.binding.acceptedTextView.setText(DateManager.gregorianToJalali4(DateManager.dateToString("yyyy-MM-dd HH:mm:ss", DateManager.timestampToDate(model.getUserAccepted_at()))));
        }
    }

    private Bundle getExtras(RoomUsersHolder holder, UserModel model) {
        Bundle extras = new Bundle();

        if (Objects.requireNonNull(((MainActivity) activity).navController.getCurrentDestination()).getId() == R.id.roomUsersFragment) {
            RoomUsersFragment roomUsersFragment = (RoomUsersFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
            if (roomUsersFragment != null)
                extras.putString("id", roomUsersFragment.roomId);
        }

        extras.putString("user_id", model.getUserId());
        extras.putString("position", model.getPosition());
        extras.putString("nickname", model.getName());
        extras.putString("mobile", model.getMobile());

        if (holder.binding.statusTexView.getText().toString().equals("پذیرش شده"))
            extras.putString("status", "accept");
        else if (holder.binding.statusTexView.getText().toString().equals("تعلیق شده"))
            extras.putString("status", "kick");
        else
            extras.putString("status", "none");

        return extras;
    }

    public class RoomUsersHolder extends RecyclerView.ViewHolder {

        private SingleItemRoomUserBinding binding;

        public RoomUsersHolder(SingleItemRoomUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}