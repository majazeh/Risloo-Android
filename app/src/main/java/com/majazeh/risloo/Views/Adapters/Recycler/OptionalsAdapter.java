package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemOptionalBinding;

public class OptionalsAdapter extends RecyclerView.Adapter<OptionalsAdapter.OptionalsHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Optional> optionals;

    public OptionalsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public OptionalsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OptionalsHolder(SingleItemOptionalBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OptionalsHolder holder, int i) {
//        Optionals optional = optionals.get(i);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return optionals.size();
        return 5;
    }

//    public void setOptional(ArrayList<Optional> optionals) {
//        this.optionals = optionals;
//        notifyDataSetChanged();
//    }

    private void setData(OptionalsHolder holder) {

    }

    public class OptionalsHolder extends RecyclerView.ViewHolder {

        private SingleItemOptionalBinding binding;

        public OptionalsHolder(SingleItemOptionalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}