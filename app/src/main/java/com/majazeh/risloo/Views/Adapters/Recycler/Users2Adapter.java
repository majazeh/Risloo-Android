package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.databinding.SingleItemUser2Binding;

public class Users2Adapter extends RecyclerView.Adapter<Users2Adapter.Users2Holder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<User> users;

    public Users2Adapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Users2Adapter.Users2Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Users2Adapter.Users2Holder(SingleItemUser2Binding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Users2Holder holder, int i) {
//        Users user = users.get(i);

        initializer(holder);

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

    private void initializer(Users2Holder holder) {
        InitManager.spinner(activity, holder.binding.statusSpinner, R.array.UserStatus, "adapter");
    }

    private void detector(Users2Holder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.statusSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(Users2Holder holder) {
        ClickManager.onClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        holder.binding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String status = parent.getItemAtPosition(position).toString();

                doWork(status);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(Users2Holder holder) {
        if (holder.getBindingAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.nameTextView.setText("GH96666DY");
        holder.binding.mobileTextView.setText("+989905511926");
        holder.binding.situationTextView.setText("");
        holder.binding.descriptionTextView.setText("");
        holder.binding.fieldTextView.setText("-");
        holder.binding.typeTextView.setText("- ");
        holder.binding.caseTextView.setText("-");

        for (int i=0; i<holder.binding.statusSpinner.getCount(); i++) {
            if (holder.binding.statusSpinner.getItemAtPosition(i).toString().equalsIgnoreCase("مراجع")) {
                holder.binding.statusSpinner.setSelection(i);
            }
        }
    }

    private void doWork(String status) {

    }

    public class Users2Holder extends RecyclerView.ViewHolder {

        private SingleItemUser2Binding binding;

        public Users2Holder(SingleItemUser2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}