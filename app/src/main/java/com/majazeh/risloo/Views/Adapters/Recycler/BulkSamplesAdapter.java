package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Index.BulkSamplesFragmentDirections;
import com.majazeh.risloo.databinding.SingleItemBulkSampleBinding;
import com.mre.ligheh.Model.TypeModel.BulkSampleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class BulkSamplesAdapter extends RecyclerView.Adapter<BulkSamplesAdapter.BulkSamplesHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> bulkSamples;

    public BulkSamplesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public BulkSamplesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BulkSamplesHolder(SingleItemBulkSampleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BulkSamplesHolder holder, int i) {
        BulkSampleModel bulkSample = (BulkSampleModel) bulkSamples.get(i);

        detector(holder);

        listener(holder, bulkSample);

        setData(holder, bulkSample);
    }

    @Override
    public int getItemCount() {
        if (this.bulkSamples != null)
            return bulkSamples.size();
        else
            return 0;
    }

    public void setBulkSamples(ArrayList<TypeModel> bulkSamples) {
        if (this.bulkSamples == null)
            this.bulkSamples = bulkSamples;
        else
            this.bulkSamples.addAll(bulkSamples);
        notifyDataSetChanged();
    }

    public void clearBulkSamples() {
        if (this.bulkSamples != null) {
            this.bulkSamples.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(BulkSamplesHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    private void listener(BulkSamplesHolder holder, BulkSampleModel model) {
        ClickManager.onClickListener(() -> {
            NavDirections action = BulkSamplesFragmentDirections.actionBulkSamplesFragmentToBulkSampleFragment(model);
            ((MainActivity) activity).navController.navigate(action);
        }).widget(holder.binding.getRoot());

        holder.binding.menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                switch (item) {
                    case "لینک ثبت نام":
                        // TODO : Place Code If Needed
                        break;
                    case "کپی کردن لینک":
                        IntentManager.clipboard(activity, model.getLink());
                        Toast.makeText(activity, activity.getResources().getString(R.string.AppLinkSaved), Toast.LENGTH_SHORT).show();
                        break;
                    case "ویرایش نمونه":
                        // TODO : Place Code If Needed
                        break;
                }

                holder.binding.menuSpinner.setSelection(holder.binding.menuSpinner.getAdapter().getCount());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(BulkSamplesHolder holder, BulkSampleModel model) {
        try {
            if (holder.getBindingAdapterPosition() == 0)
                holder.binding.topView.setVisibility(View.GONE);
            else
                holder.binding.topView.setVisibility(View.VISIBLE);

            holder.binding.serialTextView.setText(model.getId());
            holder.binding.nameTextView.setText(model.getTitle());
            holder.binding.caseTextView.setText(SelectionManager.getCaseStatus(activity, "fa", model.getCase_status()));

            if (model.getRoom() != null && model.getRoom().getRoomManager() != null && model.getRoom().getRoomManager().getName() != null) {
                holder.binding.roomTextView.setText(model.getRoom().getRoomManager().getName());
            }

            if (model.getRoom() != null && model.getRoom().getRoomCenter() != null && model.getRoom().getRoomCenter().getDetail() != null && !model.getRoom().getRoomCenter().getDetail().getString("title").equals("")) {
                holder.binding.centerTextView.setText(model.getRoom().getRoomCenter().getDetail().getString("title"));
            }

            holder.binding.statusTextView.setText(SelectionManager.getSampleStatus(activity, "fa", model.getStatus()));
            holder.binding.referenceTextView.setText(model.getMembers_count() + " / " + model.getJoined());

            InitManager.fixedCustomSpinner(activity, holder.binding.menuSpinner, R.array.BulkSamplesTasks, "bulkSamples");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class BulkSamplesHolder extends RecyclerView.ViewHolder {

        private SingleItemBulkSampleBinding binding;

        public BulkSamplesHolder(SingleItemBulkSampleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}