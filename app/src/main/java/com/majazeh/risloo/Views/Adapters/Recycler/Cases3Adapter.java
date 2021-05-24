package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemCase3Binding;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class Cases3Adapter extends RecyclerView.Adapter<Cases3Adapter.Cases3Holder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> cases;

    public Cases3Adapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Cases3Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Cases3Holder(SingleItemCase3Binding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Cases3Holder holder, int i) {
        CaseModel model = (CaseModel) cases.get(i);

        detector(holder);

        listener(holder);

        setData(holder,model);
    }

    @Override
    public int getItemCount() {
        return cases.size();
    }

    public void setCases(ArrayList<TypeModel> cases) {
        this.cases = cases;
        notifyDataSetChanged();
    }

    private void detector(Cases3Holder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(Cases3Holder holder) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.caseFragment)).widget(holder.binding.getRoot());
    }

    private void setData(Cases3Holder holder,CaseModel model) {
        if (holder.getBindingAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText(model.getCaseId());
        if (model.getCaseRoom() != null)
        holder.binding.roomTextView.setText(model.getCaseRoom().getRoomManager().getName());
        holder.binding.sessionCountTextView.setText(String.valueOf(model.getSessions_count()));
    }

    public class Cases3Holder extends RecyclerView.ViewHolder {

        private SingleItemCase3Binding binding;

        public Cases3Holder(SingleItemCase3Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}