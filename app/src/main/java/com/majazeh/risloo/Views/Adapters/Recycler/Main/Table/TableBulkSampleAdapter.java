package com.majazeh.risloo.Views.adapters.recycler.main.Table;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.managers.DialogManager;
import com.majazeh.risloo.Utils.managers.InitManager;
import com.majazeh.risloo.Utils.managers.IntentManager;
import com.majazeh.risloo.Utils.managers.SelectionManager;
import com.majazeh.risloo.Utils.managers.SheetManager;
import com.majazeh.risloo.Utils.managers.ToastManager;
import com.majazeh.risloo.Utils.widgets.CustomClickView;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.Views.adapters.holder.main.Header.HeaderBulkSampleHolder;
import com.majazeh.risloo.Views.adapters.holder.main.Table.TableBulkSampleHolder;
import com.majazeh.risloo.databinding.HeaderItemTableBulkSampleBinding;
import com.majazeh.risloo.databinding.SingleItemTableBulkSampleBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.TypeModel.BulkSampleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TableBulkSampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

    public TableBulkSampleAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderBulkSampleHolder(HeaderItemTableBulkSampleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TableBulkSampleHolder(SingleItemTableBulkSampleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof TableBulkSampleHolder) {
            BulkSampleModel model = (BulkSampleModel) items.get(i - 1);

            initializer();

            listener((TableBulkSampleHolder) holder, model);

            setData((TableBulkSampleHolder) holder, model);
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
        userSelect = false;

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
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TableBulkSampleHolder holder, BulkSampleModel model) {
        CustomClickView.onClickListener(() -> {
            ((MainActivity) activity).navigatoon.navigateToBulkSampleFragment(model);
        }).widget(holder.binding.getRoot());

        holder.binding.menuSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.menuSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        holder.binding.menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    switch (pos) {
                        case "لینک ثبت نام":
                            doWork(model);
                            break;
                        case "کپی کردن لینک":
                            IntentManager.clipboard(activity, model.getLink());
                            ToastManager.showSuccesToast(activity, activity.getResources().getString(R.string.ToastSampleLinkSaved));
                            break;
                    }

                    parent.setSelection(parent.getAdapter().getCount());

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(TableBulkSampleHolder holder, BulkSampleModel model) {
        try {
            holder.binding.serialTextView.setText(model.getId());
            holder.binding.nameTextView.setText(model.getTitle());
            holder.binding.caseTextView.setText(SelectionManager.getCaseStatus(activity, "fa", model.getCaseStatus()));

            if (model.getRoom() != null && model.getRoom().getManager() != null)
                holder.binding.roomTextView.setText(model.getRoom().getManager().getName());

            if (model.getRoom() != null && model.getRoom().getCenter() != null && model.getRoom().getCenter().getDetail() != null && model.getRoom().getCenter().getDetail().has("title") && !model.getRoom().getCenter().getDetail().getString("title").equals(""))
                holder.binding.centerTextView.setText(model.getRoom().getCenter().getDetail().getString("title"));

            if (model.getRoom() != null && model.getRoom().getType() != null && model.getRoom().getType().equals("personal_clinic"))
                holder.binding.centerTextView.setVisibility(View.GONE);

            holder.binding.statusTextView.setText(SelectionManager.getSampleStatus(activity, "fa", model.getStatus()));
            holder.binding.referenceTextView.setText(model.getMembersCount() + " / " + model.getJoined());

            setMenu(holder, model);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setMenu(TableBulkSampleHolder holder, BulkSampleModel model) {
        ArrayList<String> items = new ArrayList<>();

        if (model.getLink() != null && !model.getLink().equals("") && model.getStatus() != null && !model.getStatus().equals("closed")) {
            items.add(activity.getResources().getString(R.string.BulkSampleAdapterLink));
            items.add(activity.getResources().getString(R.string.BulkSampleAdapterCopy));
        }

        items.add("");

        if (items.size() > 1) {
            holder.binding.menuGroup.setVisibility(View.VISIBLE);
            InitManager.selectCustomActionSpinner(activity, holder.binding.menuSpinner, items);
        } else {
            holder.binding.menuGroup.setVisibility(View.INVISIBLE);
        }
    }

    private void setHashmap(BulkSampleModel model) {
        data.put("authorized_key", model.getLink());
    }

    private void doWork(BulkSampleModel model) {
        DialogManager.showLoadingDialog(activity, "");

        setHashmap(model);

        Sample.auth(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                activity.runOnUiThread(() -> {
                    try {
                        JSONObject jsonObject = (JSONObject) object;
                        BulkSampleModel bulkSampleModel = null;

                        if (!jsonObject.getString("theory").equals("sample"))
                            bulkSampleModel = new BulkSampleModel(jsonObject.getJSONObject("bulk_sample"));

                        String key = jsonObject.getString("key");

                        if (key.startsWith("$")) {
                            DialogManager.dismissLoadingDialog();
                            IntentManager.test(activity, key);
                        } else {
                            DialogManager.dismissLoadingDialog();
                            SheetManager.showBulkSampleBottomSheet(activity, key, ((MainActivity) activity).singleton.getUserModel(), bulkSampleModel);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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