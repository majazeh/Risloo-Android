package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemRoomUserBinding;

import java.util.ArrayList;

public class RoomUsersAdapter extends RecyclerView.Adapter<RoomUsersAdapter.RoomUsersHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<User> users;

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
//        Users user = users.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return users.size();
        return 4;
    }

//    public void setUsers(ArrayList<User> users) {
//        this.users = users;
//        notifyDataSetChanged();
//    }

    private void detector(RoomUsersHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(RoomUsersHolder holder) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.roomUsersFragment)).widget(holder.binding.getRoot());

        holder.binding.menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String task = parent.getItemAtPosition(position).toString();

                switch (task) {
                    case "پذیرفتن":
                        Log.e("method", "accept");
                        break;
                    case "تعلیق":
                        Log.e("method", "kick");
                        break;
                    case "ساختن اتاق درمان":
                        Log.e("method", "create_room");
                        break;
                    case "ویرایش کاربر":
                        Log.e("method", "edit");
                        break;
                    case "ورود به کاربری":
                        Log.e("method", "enter");
                        break;
                }

                holder.binding.menuSpinner.setSelection(holder.binding.menuSpinner.getAdapter().getCount());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(RoomUsersHolder holder) {
        if (holder.getBindingAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText("GH96666DY");
        holder.binding.nameTextView.setText("محمد نخلی");
        holder.binding.mobileTextView.setText("+989905511926");
        holder.binding.typeTextView.setText("مراجع");
        holder.binding.statusTexView.setText("پذیرش شده");
        holder.binding.acceptedTextView.setText("99-12-26 10:55 ");
        holder.binding.kickedTextView.setText("99-12-26 10:55 ");

        ArrayList<String> menuValues = new ArrayList<>();

        menuValues.add(activity.getResources().getString(R.string.RoomUsersFragmentAccept));
        menuValues.add(activity.getResources().getString(R.string.RoomUsersFragmentKick));
        menuValues.add(activity.getResources().getString(R.string.RoomUsersFragmentCreateRoom));
        menuValues.add(activity.getResources().getString(R.string.RoomUsersFragmentEdit));
        menuValues.add(activity.getResources().getString(R.string.RoomUsersFragmentEnter));
        menuValues.add("");

        InitManager.customizedSpinner(activity, holder.binding.menuSpinner, menuValues, "roomUsers");
    }

    public class RoomUsersHolder extends RecyclerView.ViewHolder {

        private SingleItemRoomUserBinding binding;

        public RoomUsersHolder(SingleItemRoomUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}