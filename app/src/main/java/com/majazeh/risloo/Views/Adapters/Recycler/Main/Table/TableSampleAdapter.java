package com.majazeh.risloo.Views.adapters.recycler.main.Table;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.managers.DialogManager;
import com.majazeh.risloo.Utils.managers.IntentManager;
import com.majazeh.risloo.Utils.managers.SelectionManager;
import com.majazeh.risloo.Utils.widgets.CustomClickView;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.Views.adapters.holder.main.Header.HeaderSampleHolder;
import com.majazeh.risloo.Views.adapters.holder.main.Table.TableSampleHolder;
import com.majazeh.risloo.Views.Fragments.Main.Index.SamplesFragment;
import com.majazeh.risloo.Views.Fragments.Main.Show.BulkSampleFragment;
import com.majazeh.risloo.Views.Fragments.Main.Show.CaseFragment;
import com.majazeh.risloo.Views.Fragments.Main.Show.DashboardFragment;
import com.majazeh.risloo.Views.Fragments.Main.Show.ReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Main.Show.SessionFragment;
import com.majazeh.risloo.databinding.HeaderItemTableSampleBinding;
import com.majazeh.risloo.databinding.SingleItemTableSampleBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class TableSampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> items;

    public TableSampleAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderSampleHolder(HeaderItemTableSampleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TableSampleHolder(SingleItemTableSampleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof HeaderSampleHolder) {
            initializer();

            setWidget((HeaderSampleHolder) holder);
        } else if (holder instanceof TableSampleHolder) {
            SampleModel model = (SampleModel) items.get(i - 1);

            initializer();

            listener((TableSampleHolder) holder, model, i);

            setWidget((TableSampleHolder) holder);

            setData((TableSampleHolder) holder, model);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;

        return 1;
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size() + 1;
        else
            return 0;
    }

    public int itemsCount() {
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

    private void initializer() {
        current = ((MainActivity) activity).fragmont.getCurrent();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());
    }

    private void listener(TableSampleHolder holder, SampleModel model, int position) {
        CustomClickView.onClickListener(() -> {
            if (((MainActivity) activity).permissoon.showSamplesFragmentSample(((MainActivity) activity).singleton.getUserModel(), model))
                ((MainActivity) activity).navigatoon.navigateToSampleFragment(model);

        }).widget(holder.binding.getRoot());

        CustomClickView.onClickListener(() -> {
            if (model.getStatus().equals("closed"))
                doWork(holder, model, position);
            else
                IntentManager.test(activity, model.getId());

        }).widget(holder.binding.statusTextView);

        CustomClickView.onClickListener(() -> {
            ((MainActivity) activity).navigatoon.navigateToSamplesFragment(model.getChainId(), null);
        }).widget(holder.binding.bulkTextView);
    }

    private void setWidget(HeaderSampleHolder holder) {
        if (current instanceof SamplesFragment || current instanceof DashboardFragment) {
            holder.binding.roomTextView.setVisibility(View.VISIBLE);
            holder.binding.caseTextView.setVisibility(View.VISIBLE);

            holder.binding.referenceTextView.setVisibility(View.VISIBLE);
        } else if (current instanceof ReferenceFragment) {
            holder.binding.roomTextView.setVisibility(View.VISIBLE);
            holder.binding.caseTextView.setVisibility(View.VISIBLE);

            holder.binding.referenceTextView.setVisibility(View.GONE);
        } else if (current instanceof BulkSampleFragment || current instanceof CaseFragment || current instanceof SessionFragment) {
            holder.binding.roomTextView.setVisibility(View.GONE);
            holder.binding.caseTextView.setVisibility(View.GONE);

            holder.binding.referenceTextView.setVisibility(View.VISIBLE);
        }
    }

    private void setWidget(TableSampleHolder holder) {
        if (current instanceof SamplesFragment || current instanceof DashboardFragment) {
            holder.binding.roomTextView.setVisibility(View.VISIBLE);
            holder.binding.caseTextView.setVisibility(View.VISIBLE);

            holder.binding.referenceTextView.setVisibility(View.VISIBLE);
        } else if (current instanceof ReferenceFragment) {
            holder.binding.roomTextView.setVisibility(View.VISIBLE);
            holder.binding.caseTextView.setVisibility(View.VISIBLE);

            holder.binding.referenceTextView.setVisibility(View.GONE);
        } else if (current instanceof BulkSampleFragment || current instanceof CaseFragment || current instanceof SessionFragment) {
            holder.binding.roomTextView.setVisibility(View.GONE);
            holder.binding.caseTextView.setVisibility(View.GONE);

            holder.binding.referenceTextView.setVisibility(View.VISIBLE);
        }
    }

    private void setData(TableSampleHolder holder, SampleModel model) {
        holder.binding.serialTextView.setText(model.getId());

        if (!model.getScaleTitle().equals(""))
            holder.binding.nameTextView.setText(model.getScaleTitle());
        else
            holder.binding.nameTextView.setText(model.getTitle());

        if (!model.getEdition().equals("") && model.getVersion() != 0)
            holder.binding.editionTextView.setText(model.getEdition() + " - نسخه " + model.getVersion());
        else if (model.getVersion() != 0)
            holder.binding.editionTextView.setText("نسخه " + model.getVersion());
        else if (!model.getEdition().equals(""))
            holder.binding.editionTextView.setText(model.getEdition());
        else
            holder.binding.editionTextView.setText("-");

        if (!model.getChainId().equals(""))
            holder.binding.bulkTextView.setVisibility(View.VISIBLE);
        else
            holder.binding.bulkTextView.setVisibility(View.GONE);

        if (model.getRoom() != null && model.getRoom().getManager() != null)
            holder.binding.roomTextView.setText(model.getRoom().getManager().getName());

        if (model.getCasse() != null)
            holder.binding.caseTextView.setText("پرونده " + model.getCasse().getId());

        if (model.getClient() != null)
            holder.binding.referenceTextView.setText(model.getClient().getName());

        setStatus(holder, model.getStatus());
    }

    private void setStatus(TableSampleHolder holder, String status) {
        holder.binding.statusTextView.setText(SelectionManager.getSampleStatus2(activity, "fa", status));

        switch (status) {
            case "seald":
            case "open":
            case "closed":
                holder.binding.statusTextView.setEnabled(true);
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.risloo500));

                holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_risloo500_ripple_risloo50);
                break;
            case "scoring":
            case "creating_files":
                holder.binding.statusTextView.setEnabled(false);
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.amber500));

                holder.binding.statusTextView.setBackgroundResource(android.R.color.transparent);
                break;
            default:
                holder.binding.statusTextView.setEnabled(false);
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.coolGray700));

                holder.binding.statusTextView.setBackgroundResource(android.R.color.transparent);
                break;
        }
    }

    private void setHashmap(SampleModel model) {
        data.put("id", model.getId());
    }

    private void doWork(TableSampleHolder holder, SampleModel model, int position) {
        DialogManager.showLoadingDialog(activity, "");

        setHashmap(model);

        Sample.score(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                SampleModel sampleModel = (SampleModel) object;
                items.add(position, sampleModel);

                activity.runOnUiThread(() -> {
                    DialogManager.dismissLoadingDialog();
                    setStatus(holder, sampleModel.getStatus());
                });
            }

            @Override
            public void onFailure(String response) {
                activity.runOnUiThread(() -> {
                    // TODO : Place Code If Needed
                });
            }
        });
    }

}