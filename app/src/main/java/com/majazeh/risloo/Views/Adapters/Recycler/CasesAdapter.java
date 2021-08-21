package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.CasesHolder;
import com.majazeh.risloo.databinding.SingleItemCaseBinding;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.ArrayList;

public class CasesAdapter extends RecyclerView.Adapter<CasesHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

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
        CaseModel model = (CaseModel) items.get(i);

        detector(holder);

        listener(holder, model);

        setData(holder, model);
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items) {
        if (this.items == null)
            this.items = items;
        else
            this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        if (this.items != null) {
            this.items.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(CasesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(CasesHolder holder, CaseModel model) {
        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalCaseFragment(model);
            ((MainActivity) activity).navController.navigate(action);
        }).widget(holder.binding.getRoot());
    }

    private void setData(CasesHolder holder, CaseModel model) {
        try {
            if (holder.getBindingAdapterPosition() == 0)
                holder.binding.topView.setVisibility(View.GONE);
            else
                holder.binding.topView.setVisibility(View.VISIBLE);

            holder.binding.serialTextView.setText(model.getCaseId());

            if (model.getCaseManager() != null) {
                holder.binding.roomTextView.setText(model.getCaseManager().getName());
            }

            if (model.getCaseRoom() != null && model.getCaseRoom().getRoomCenter() != null && model.getCaseRoom().getRoomCenter().getDetail().has("title") && !model.getCaseRoom().getRoomCenter().getDetail().isNull("title")) {
                holder.binding.centerTextView.setText(model.getCaseRoom().getRoomCenter().getDetail().getString("title"));
            }

            setClients(holder, model.getClients());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setClients(CasesHolder holder, List clients) {
        if (clients != null && clients.data().size() != 0) {
            holder.binding.referenceTextView.setText("");
            for (int i = 0; i < clients.data().size(); i++) {
                UserModel user = (UserModel) clients.data().get(i);
                if (user != null) {
                    holder.binding.referenceTextView.append(user.getName());
                    if (i != clients.data().size() - 1) {
                        holder.binding.referenceTextView.append("\n");
                    }
                }
            }
        }
    }

}