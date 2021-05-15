package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemCase2Binding;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class Cases2Adapter extends RecyclerView.Adapter<Cases2Adapter.Cases2Holder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> cases;

    public Cases2Adapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Cases2Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Cases2Holder(SingleItemCase2Binding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Cases2Holder holder, int i) {
        CaseModel caseModel = (CaseModel) cases.get(i);

        detector(holder);

        listener(holder);

        setData(holder,caseModel);
    }

    @Override
    public int getItemCount() {
        return cases.size();
    }

    public void setCases(ArrayList<TypeModel> cases) {
        this.cases = cases;
        notifyDataSetChanged();
    }

    private void detector(Cases2Holder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(Cases2Holder holder) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.caseFragment)).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(Cases2Holder holder,CaseModel caseModel) {
        holder.binding.serialTextView.setText(caseModel.getCaseId());
        holder.binding.referenceTextView.setText(caseModel.getCaseManager().getName());
        holder.binding.dateTextView.setText(String.valueOf(caseModel.getCaseCreated_at()));
        holder.binding.sessionCountTextView.setText("1 جلسه");
    }

    public class Cases2Holder extends RecyclerView.ViewHolder {

        private SingleItemCase2Binding binding;

        public Cases2Holder(SingleItemCase2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}