package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemPictoralBinding;

public class PictoralsAdapter extends RecyclerView.Adapter<PictoralsAdapter.PictoralsHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Pictoral> pictorals;

    public PictoralsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public PictoralsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PictoralsHolder(SingleItemPictoralBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PictoralsHolder holder, int i) {
//        Pictorals pictoral = pictorals.get(i);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return pictorals.size();
        return 5;
    }

//    public void setPictoral(ArrayList<Pictoral> pictorals) {
//        this.pictorals = pictorals;
//        notifyDataSetChanged();
//    }

    private void setData(PictoralsHolder holder) {

    }

    public class PictoralsHolder extends RecyclerView.ViewHolder {

        private SingleItemPictoralBinding binding;

        public PictoralsHolder(SingleItemPictoralBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}