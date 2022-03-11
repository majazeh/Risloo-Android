package com.majazeh.risloo.views.adapters.recycler.main.Table;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.instances.Paymont;
import com.majazeh.risloo.utils.managers.DateManager;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.PaymentManager;
import com.majazeh.risloo.utils.managers.JsonManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.SpannableManager;
import com.majazeh.risloo.utils.managers.SpinnerManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.holder.main.Header.HeaderBillHolder;
import com.majazeh.risloo.views.adapters.holder.main.Table.TableBillHolder;
import com.majazeh.risloo.views.fragments.main.index.FragmentBillings;
import com.majazeh.risloo.views.fragments.main.show.FragmentSession;
import com.majazeh.risloo.databinding.HeaderItemTableBillBinding;
import com.majazeh.risloo.databinding.SingleItemTableBillBinding;
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

public class TableBillAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> items;
    private ArrayList<String> treasuryIds = new ArrayList<>();
    private boolean userSelect = false;

    public TableBillAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderBillHolder(HeaderItemTableBillBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TableBillHolder(SingleItemTableBillBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof HeaderBillHolder) {
            setWidget((HeaderBillHolder) holder);
        } else if (holder instanceof TableBillHolder) {
            BillingModel model = (BillingModel) items.get(i - 1);

            initializer();

            listener((TableBillHolder) holder, model);

            setData((TableBillHolder) holder, model);
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
        current = ((ActivityMain) activity).fragmont.getCurrent();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) activity).singleton.getAuthorization());
    }

    private void setWidget(HeaderBillHolder holder) {
        holder.binding.amountTextView.setText(SpannableManager.spanForegroundColorSize(activity.getResources().getString(R.string.BillAdapterAmount), 5, 12, activity.getResources().getColor(R.color.coolGray500), (int) activity.getResources().getDimension(R.dimen._7ssp)));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TableBillHolder holder, BillingModel model) {
        CustomClickView.onClickListener(() -> {
            ((ActivityMain) activity).navigatoon.navigateToFragmentBill(model);
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

    private void setData(TableBillHolder holder, BillingModel model) {
        holder.binding.serialTextView.setText(model.getId());
        holder.binding.titleTextView.setText(model.getTitle());
        holder.binding.dateTextView.setText(DateManager.jalNDsDDsNMsYYYYnHHcMM(String.valueOf(model.getCreatedAt()), " "));

        if (model.getCreditor() != null)
            holder.binding.creditorTextView.setText(model.getCreditor().getTitle());

        if (model.getDebtor() != null && model.getDebtor().getUser() != null)
            holder.binding.debtorTextView.setText(model.getDebtor().getUser().getName() + " - " + model.getDebtor().getTitle());
        else if (model.getDebtor() != null)
            holder.binding.debtorTextView.setText(model.getDebtor().getTitle());

        holder.binding.amountTextView.setText(StringManager.seperatePlus(String.valueOf(model.getAmount())));
        holder.binding.statusTextView.setText(JsonManager.getBillType(activity, "fa", model.getType()));

        setMenu(holder, model);
    }

    private void setMenu(TableBillHolder holder, BillingModel model) {
        ArrayList<String> items = new ArrayList<>();

        // Settled
        if (model.getType().equals("creditor")) {
            String debtorId = model.getDebtor().getId();
            UserModel userModel = ((ActivityMain) activity).singleton.getUserModel();

            if (userModel.getTreasuries() != null) {
                for (TypeModel typeModel : userModel.getTreasuries().data()) {
                    TreasuriesModel treasuriesModel = (TreasuriesModel) typeModel;

                    if (treasuriesModel != null && treasuriesModel.getId() != null && treasuriesModel.getId().equals(debtorId)) {
                        items.add(activity.getResources().getString(R.string.BillAdapterPay));
                        treasuryIds.add("");
                        break;
                    }
                }
            }
        }

        // Finall
        if (current instanceof FragmentSession) {
            String centerId = ((FragmentSession) current).sessionModel.getRoom().getCenter().getId();

            if (model.getType().equals("creditor")) {
                UserModel userModel = ((ActivityMain) activity).singleton.getUserModel();

                if (userModel.getCenters() != null) {
                    for (TypeModel typeModel : userModel.getCenters().data()) {
                        CenterModel centerModel = (CenterModel) typeModel;

                        if (centerModel != null && centerModel.getId() != null && centerModel.getId().equals(centerId)) {
                            if (centerModel.getTreasuries() != null) {
                                for (TypeModel typeModel2 : centerModel.getTreasuries().data()) {
                                    TreasuriesModel treasuriesModel = (TreasuriesModel) typeModel2;

                                    if (treasuriesModel.isCreditable() && treasuriesModel.getSymbol().contains(centerId.toLowerCase()))
                                        treasuriesModel.setTitle(activity.getResources().getString(R.string.BillAdapterTreasury));

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
            SpinnerManager.selectBillSpinner(activity, holder.binding.menuSpinner, items);
        } else {
            holder.binding.menuGroup.setVisibility(View.INVISIBLE);
        }
    }

    private void setHashmap(BillingModel model, int position, String method) {
        if (method.equals("settled")) {
            data.put("id", model.getId());

            data.remove("billingId");
        } else {
            data.put("id", treasuryIds.get(position));
            data.put("billingId", model.getId());
        }
    }

    private void doWork(TableBillHolder holder, BillingModel model, int position, String method) {
        DialogManager.showDialogLoading(activity, "");

        setHashmap(model, position, method);

        if (method.equals("settled")) {
            Billing.settled(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    activity.runOnUiThread(() -> {
                        DialogManager.dismissDialogLoading();

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

                                if (current instanceof FragmentBillings)
                                    Paymont.getInstance().insertPayment(model, paymentModel, null, R.id.fragmentBillings);
                                else if (current instanceof FragmentSession)
                                    Paymont.getInstance().insertPayment(model, paymentModel, null, R.id.fragmentSession);

                                PaymentManager.request(activity, paymentModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            });
        } else {
            Billing.finall(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    activity.runOnUiThread(() -> {
                        DialogManager.dismissDialogLoading();
                        SnackManager.showSnackSucces(activity, activity.getResources().getString(R.string.SnackChangesSaved));

                        holder.binding.menuGroup.setVisibility(View.INVISIBLE);
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

}