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
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemCenterUserBinding;

public class CenterUsersAdapter extends RecyclerView.Adapter<CenterUsersAdapter.CenterUsersHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<User> users;
    private String type = "";

    public CenterUsersAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CenterUsersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CenterUsersHolder(SingleItemCenterUserBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CenterUsersHolder holder, int i) {
//        Users user = users.get(i);

        initializer(holder);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return users.size();
        return 5;
    }

//    public void setUser(ArrayList<User> users) {
//        this.users = users;
//        notifyDataSetChanged();
//    }

    private void initializer(CenterUsersHolder holder) {
        InitManager.spinner(activity, holder.binding.typeSpinner, R.array.UserTypes, "adapter");
    }

    private void detector(CenterUsersHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.typeSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);

            holder.binding.taskImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_gray300);
        } else {
            holder.binding.typeSpinner.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray200);
        }
    }

    private void listener(CenterUsersHolder holder) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.referenceFragment)).widget(holder.binding.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.taskImageView);

        holder.binding.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();

                doWork();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(CenterUsersHolder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText("GH96666DY");
        holder.binding.nameTextView.setText("محمد نخلی");
        holder.binding.mobileTextView.setText("+989905511926");
        holder.binding.statusTexView.setText("پذیرش شده");
        holder.binding.acceptedTextView.setText("99-12-26 10:55 ");
        holder.binding.kickedTextView.setText("99-12-26 10:55 ");

//        type = ((MainActivity) activity).singleton.getType();
//        for (int i=0; i<holder.binding.typeSpinner.getCount(); i++) {
//            if (holder.binding.typeSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(type)) {
//                holder.binding.typeSpinner.setSelection(i);
//            }
//        }
    }

    private void doWork() {

    }

    public class CenterUsersHolder extends RecyclerView.ViewHolder {

        private SingleItemCenterUserBinding binding;

        public CenterUsersHolder(SingleItemCenterUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}