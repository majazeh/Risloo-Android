package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.databinding.SingleItemTestBinding;
import com.mre.ligheh.Model.TypeModel.ScaleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class TestsAdapter extends RecyclerView.Adapter<TestsAdapter.TestsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> tests;

    public TestsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public TestsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TestsHolder(SingleItemTestBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TestsHolder holder, int i) {
        ScaleModel scale = (ScaleModel) tests.get(i);

        setData(holder, scale);
    }

    @Override
    public int getItemCount() {
        if (this.tests != null)
            return tests.size();
        else
            return 0;
    }

    public void setTests(ArrayList<TypeModel> tests) {
        if (this.tests == null)
            this.tests = tests;
        else
            this.tests.addAll(tests);
        notifyDataSetChanged();
    }

    public void clearTests() {
        if (this.tests != null) {
            this.tests.clear();
            notifyDataSetChanged();
        }
    }

    private void setData(TestsHolder holder, ScaleModel model) {
        holder.binding.getRoot().setText(model.getTitle());
    }

    public class TestsHolder extends RecyclerView.ViewHolder {

        private SingleItemTestBinding binding;

        public TestsHolder(SingleItemTestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}