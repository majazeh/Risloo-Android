package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.databinding.SingleItemPhoneBinding;

import java.util.ArrayList;

public class PhonesAdapter extends RecyclerView.Adapter<PhonesAdapter.PhonesHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<String> phones;

    public PhonesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public PhonesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PhonesHolder(SingleItemPhoneBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhonesHolder holder, int i) {
        String phone = phones.get(i);

        detector(holder);

        listener(holder, i);

        setData(holder, phone);
    }

    @Override
    public int getItemCount() {
        return phones.size();
    }

    public ArrayList<String> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
        notifyDataSetChanged();
    }

    public void clearPhones() {
        phones.clear();
        notifyDataSetChanged();
    }

    public void addPhone(String phone) {
        phones.add(phone);
        notifyDataSetChanged();
    }

    public void replacePhone(int position, String phone) {
        phones.set(position, phone);
        notifyItemChanged(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    private void removePhone(int position) {
        phones.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    private void detector(PhonesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200_ripple_gray300);

            holder.binding.removeImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_gray50_ripple_red300);
        }
    }

    private void listener(PhonesHolder holder, int position) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            removePhone(position);
        }).widget(holder.binding.removeImageView);
    }

    private void setData(PhonesHolder holder, String phone) {
        holder.binding.phoneTextView.setText(phone);
    }

    public class PhonesHolder extends RecyclerView.ViewHolder {

        private SingleItemPhoneBinding binding;

        public PhonesHolder(SingleItemPhoneBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}