package com.majazeh.risloo.Views.Adapters.Recycler.Index;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Paymont;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.PaymentManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Header.HeaderBillHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Index.IndexBillHolder;
import com.majazeh.risloo.Views.Fragments.Index.BillingsFragment;
import com.majazeh.risloo.Views.Fragments.Show.SessionFragment;
import com.majazeh.risloo.databinding.HeaderItemIndexBillBinding;
import com.majazeh.risloo.databinding.SingleItemIndexBillBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Billing;
import com.mre.ligheh.Model.TypeModel.BillingModel;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.PaymentModel;
import com.mre.ligheh.Model.TypeModel.TreasuriesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class IndexBillAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> items;
    private ArrayList<String> treasuryIds = new ArrayList<>();
    private boolean userSelect = false;

    public IndexBillAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderBillHolder(HeaderItemIndexBillBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new IndexBillHolder(SingleItemIndexBillBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof HeaderBillHolder) {
            setWidget((HeaderBillHolder) holder);
        } else if (holder instanceof  IndexBillHolder) {
            BillingModel model = (BillingModel) items.get(i - 1);

            initializer();

            listener((IndexBillHolder) holder, model);

            setData((IndexBillHolder) holder, model);
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
        current = ((MainActivity) activity).fragmont.getCurrent();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());
    }

    private void setWidget(HeaderBillHolder holder) {
        holder.binding.amountTextView.setText(StringManager.foregroundSize(activity.getResources().getString(R.string.BillingsFragmentAmount), 5, 8, activity.getResources().getColor(R.color.Gray500), (int) activity.getResources().getDimension(R.dimen._7ssp)));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(IndexBillHolder holder, BillingModel model) {
        CustomClickView.onClickListener(() -> {
            NavDirections action = NavigationMainDirections.actionGlobalBillFragment(model);
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

                    if (pos.equals("پرداخت"))
                        doWork(holder, model, position, "settled");
                    else
                        doWork(holder, model, position, "finall");

                    parent.setSelection(parent.getAdapter().getCount());

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(IndexBillHolder holder, BillingModel model) {
        holder.binding.serialTextView.setText(model.getId());
        holder.binding.titleTextView.setText(model.getTitle());
        holder.binding.dateTextView.setText(DateManager.jalYYYYsNMMsDDsNDDnlHHsMM(String.valueOf(model.getCreated_at()), " "));

        if (model.getCreditor() != null)
            holder.binding.creditorTextView.setText(model.getCreditor().getTitle());

        if (model.getDebtor() != null && model.getDebtor().getUserModel() != null)
            holder.binding.debtorTextView.setText(model.getDebtor().getUserModel().getName() + " - " + model.getDebtor().getTitle());
        else if (model.getDebtor() != null)
            holder.binding.debtorTextView.setText(model.getDebtor().getTitle());

        holder.binding.amountTextView.setText(StringManager.separate(String.valueOf(model.getAmount())));
        holder.binding.statusTextView.setText(SelectionManager.getBillType(activity, "fa", model.getType()));

        setMenu(holder, model);
    }

    private void setMenu(IndexBillHolder holder, BillingModel model) {
        ArrayList<String> items = new ArrayList<>();

        // Settled
        if (model.getType().equals("creditor")) {
            String debtorId = model.getDebtor().getId();
            UserModel userModel = ((MainActivity) activity).singleton.getUserModel();

            if (userModel.getTreasuries() != null) {
                for (TypeModel typeModel : userModel.getTreasuries().data()) {
                    TreasuriesModel treasuriesModel = (TreasuriesModel) typeModel;

                    if (treasuriesModel != null && treasuriesModel.getId() != null && treasuriesModel.getId().equals(debtorId)) {
                        items.add(activity.getResources().getString(R.string.BillingsFragmentPay));
                        treasuryIds.add("");
                        break;
                    }
                }
            }
        }

        // Finall
        if (current instanceof SessionFragment) {
            String centerId = ((SessionFragment) current).sessionModel.getRoom().getRoomCenter().getCenterId();

            if (model.getType().equals("creditor")) {
                UserModel userModel = ((MainActivity) activity).singleton.getUserModel();

                if (userModel.getCenterList() != null) {
                    for (TypeModel typeModel : userModel.getCenterList().data()) {
                        CenterModel centerModel = (CenterModel) typeModel;

                        if (centerModel != null && centerModel.getCenterId() != null && centerModel.getCenterId().equals(centerId)) {
                            if (centerModel.getTreasuries() != null) {
                                for (TypeModel typeModel2 : centerModel.getTreasuries().data()) {
                                    TreasuriesModel treasuriesModel = (TreasuriesModel) typeModel2;

                                    if (treasuriesModel.isCreditable() && treasuriesModel.getSymbol().contains(centerId.toLowerCase()))
                                        treasuriesModel.setTitle(activity.getResources().getString(R.string.BillingsFragmentTreasuryOnline));

                                    items.add(treasuriesModel.getTitle());
                                    treasuryIds.add(treasuriesModel.getId());
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }

        items.add("");
        treasuryIds.add("");

        if (items.size() > 1) {
            holder.binding.menuGroup.setVisibility(View.VISIBLE);
            InitManager.billCustomSpinner(activity, holder.binding.menuSpinner, items);
        } else {
            holder.binding.menuGroup.setVisibility(View.INVISIBLE);
        }
    }

    private void doWork(IndexBillHolder holder, BillingModel model, int position, String method) {
        DialogManager.showLoadingDialog(activity, "");

        if (method.equals("settled")) {
            data.put("id", model.getId());

            Billing.settled(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    activity.runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();

                        // TODO : Place Code If Needed
                    });
                }

                @Override
                public void onFailure(String response) {
                    activity.runOnUiThread(() -> {
                        try {
                            JSONObject responseObject = new JSONObject(response);

                            if (responseObject.getString("message").equals("POVERTY")) {
                                JSONObject paymentObject = responseObject.getJSONObject("payment");
                                PaymentModel paymentModel = new PaymentModel(paymentObject);

                                if (current instanceof BillingsFragment)
                                    Paymont.getInstance().insertPayment(model, paymentModel, null, R.id.billingsFragment);
                                else if (current instanceof SessionFragment)
                                    Paymont.getInstance().insertPayment(model, paymentModel, null, R.id.sessionFragment);

                                PaymentManager.request(activity, paymentModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            });
        } else {
            data.put("billingId", model.getId());
            data.put("id", treasuryIds.get(position));

            Billing.finall(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    activity.runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();
                        SnackManager.showSuccesSnack(activity, activity.getResources().getString(R.string.ToastChangesSaved));

                        holder.binding.menuGroup.setVisibility(View.INVISIBLE);
                    });
                }

                @Override
                public void onFailure(String response) {
                    activity.runOnUiThread(() -> {
                        // Place Code if Needed
                    });
                }
            });
        }
    }

}