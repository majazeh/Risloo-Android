package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemCase2Binding;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

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

    private void detector(Cases2Holder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(Cases2Holder holder, CaseModel model) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.caseFragment, getExtras(model))).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(Cases2Holder holder, CaseModel model) {
        holder.binding.serialTextView.setText(model.getCaseId());

        if (model.getClients() != null && !model.getClients().data().isEmpty()) {
            for (int i = 0; i < model.getClients().data().size(); i++) {
                UserModel user = (UserModel) model.getClients().data().get(i);
                if (user != null) {
                    holder.binding.referenceTextView.setText(user.getName());
                }
            }
        }

        holder.binding.dateTextView.setText(DateManager.gregorianToJalali2(DateManager.dateToString("yyyy-MM-dd", DateManager.timestampToDate(model.getCaseCreated_at()))));
        holder.binding.sessionCountTextView.setText(model.getSessions_count() + " جلسه ");
    }

    private Bundle getExtras(CaseModel model) {
        Bundle extras = new Bundle();
        try {
            extras.putString("id", model.getCaseId());

            extras.putString("manager_id", model.getCaseManager().getUserId());
            extras.putString("manager_name", model.getCaseManager().getName());

            extras.putString("clients", String.valueOf(model.getClients()));

            if (model.getDetail() != null && model.getDetail().has("problem") && !model.getDetail().isNull("problem"))
                extras.putString("problem", model.getDetail().getString("problem"));

            extras.putString("session_count", String.valueOf(model.getSessions_count()));

            extras.putInt("created_at", model.getCaseCreated_at());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return extras;
    }

    public class Cases2Holder extends RecyclerView.ViewHolder {

        private SingleItemCase2Binding binding;

        public Cases2Holder(SingleItemCase2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}