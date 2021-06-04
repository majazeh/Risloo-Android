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
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemCaseBinding;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class CasesAdapter extends RecyclerView.Adapter<CasesAdapter.CasesHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> cases;

    public CasesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CasesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CasesHolder(SingleItemCaseBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CasesHolder holder, int i) {
        CaseModel casse = (CaseModel) cases.get(i);

        detector(holder);

        listener(holder, casse);

        setData(holder, casse);
    }

    @Override
    public int getItemCount() {
        if (this.cases != null)
            return cases.size();
        else
            return 0;
    }

    public void setCases(ArrayList<TypeModel> cases) {
        if (this.cases == null)
            this.cases = cases;
        else
            this.cases.addAll(cases);
        notifyDataSetChanged();
    }

    public void clearCases() {
        if (this.cases != null) {
            this.cases.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(CasesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(CasesHolder holder, CaseModel model) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.caseFragment, getExtras(model))).widget(holder.binding.getRoot());
    }

    private void setData(CasesHolder holder, CaseModel model) {
        try {
            if (holder.getBindingAdapterPosition() == 0) {
                holder.binding.topView.setVisibility(View.GONE);
            } else {
                holder.binding.topView.setVisibility(View.VISIBLE);
            }

            holder.binding.serialTextView.setText(model.getCaseId());
            holder.binding.roomTextView.setText(model.getCaseManager().getName());

            if (model.getCaseRoom() != null && model.getCaseRoom().getRoomCenter() != null && model.getCaseRoom().getRoomCenter().getDetail().has("title") && !model.getCaseRoom().getRoomCenter().getDetail().isNull("title")) {
                holder.binding.centerTextView.setText(model.getCaseRoom().getRoomCenter().getDetail().getString("title"));
            }

            if (model.getClients() != null && !model.getClients().data().isEmpty()) {
                holder.binding.referenceTextView.setText(model.getClients().data().get(0).object.getString("name"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Bundle getExtras(CaseModel model) {
        Bundle extras = new Bundle();
//        try {
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return extras;
    }

    public class CasesHolder extends RecyclerView.ViewHolder {

        private SingleItemCaseBinding binding;

        public CasesHolder(SingleItemCaseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}