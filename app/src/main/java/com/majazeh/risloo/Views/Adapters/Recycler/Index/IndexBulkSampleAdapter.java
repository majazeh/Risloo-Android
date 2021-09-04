package com.majazeh.risloo.Views.Adapters.Recycler.Index;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.SheetManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.ToastManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Header.HeaderBulkSampleHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Index.IndexBulkSampleHolder;
import com.majazeh.risloo.databinding.HeaderItemIndexBulkSampleBinding;
import com.majazeh.risloo.databinding.SingleItemIndexBulkSampleBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.TypeModel.BulkSampleModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class IndexBulkSampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

    public IndexBulkSampleAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderBulkSampleHolder(HeaderItemIndexBulkSampleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new IndexBulkSampleHolder(SingleItemIndexBulkSampleBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof  IndexBulkSampleHolder) {
            BulkSampleModel model = (BulkSampleModel) items.get(i - 1);

            initializer();

            detector((IndexBulkSampleHolder) holder);

            listener((IndexBulkSampleHolder) holder, model);

            setData((IndexBulkSampleHolder) holder, model);
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
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());
    }

    private void detector(IndexBulkSampleHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(IndexBulkSampleHolder holder, BulkSampleModel model) {
        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalBulkSampleFragment(model);
            ((MainActivity) activity).navController.navigate(action);
        }).widget(holder.binding.getRoot());

        holder.binding.menuSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

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
                            ToastManager.showSuccesToast(activity, activity.getResources().getString(R.string.ToastLinkSaved));
                            break;
                        case "ویرایش":
                            // TODO : Place Code If Needed
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

    private void setData(IndexBulkSampleHolder holder, BulkSampleModel model) {
        try {
            holder.binding.serialTextView.setText(model.getId());
            holder.binding.nameTextView.setText(model.getTitle());
            holder.binding.caseTextView.setText(SelectionManager.getCaseStatus(activity, "fa", model.getCase_status()));

            if (model.getRoom() != null && model.getRoom().getRoomManager() != null) {
                holder.binding.roomTextView.setText(model.getRoom().getRoomManager().getName());
            }

            if (model.getRoom() != null && model.getRoom().getRoomCenter() != null && model.getRoom().getRoomCenter().getDetail() != null && model.getRoom().getRoomCenter().getDetail().has("title") && !model.getRoom().getRoomCenter().getDetail().isNull("title")) {
                holder.binding.centerTextView.setText(model.getRoom().getRoomCenter().getDetail().getString("title"));
            }

            holder.binding.statusTextView.setText(SelectionManager.getSampleStatus(activity, "fa", model.getStatus()));
            holder.binding.referenceTextView.setText(model.getMembers_count() + " / " + model.getJoined());

            setMenu(holder, model);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setMenu(IndexBulkSampleHolder holder, BulkSampleModel model) {
        ArrayList<String> items = new ArrayList<>();

        if (!model.getLink().equals("")) {
            items.add(activity.getResources().getString(R.string.BulkSamplesFragmentLink));
            items.add(activity.getResources().getString(R.string.BulkSamplesFragmentCopy));
        }

        items.add(activity.getResources().getString(R.string.BulkSamplesFragmentEdit));
        items.add("");

        InitManager.actionCustomSpinner(activity, holder.binding.menuSpinner, items);
    }

    private void doWork(BulkSampleModel model) {
        DialogManager.showLoadingDialog(activity, "loading");

        data.put("authorized_key", model.getLink());

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
                            SheetManager.showBulkSampleBottomSheet(activity, key, ((MainActivity) activity).singleton.getName(), ((MainActivity) activity).singleton.getAvatar(), bulkSampleModel);
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