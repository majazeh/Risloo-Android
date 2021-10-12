package com.majazeh.risloo.Views.Fragments.Main.Show;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Index.IndexTransactionAdapter;
import com.majazeh.risloo.databinding.FragmentTreasuryBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Treasury;
import com.mre.ligheh.Model.TypeModel.TransactionModel;
import com.mre.ligheh.Model.TypeModel.TreasuriesModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TreasuryFragment extends Fragment {

    // Binding
    private FragmentTreasuryBinding binding;

    // Adapters
    private IndexTransactionAdapter indexTransactionAdapter;

    // Models
    private TreasuriesModel treasuriesModel;

    // Objects
    private HashMap data, header;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentTreasuryBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        indexTransactionAdapter = new IndexTransactionAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.transactionsHeaderLayout.titleTextView.setText(getResources().getString(R.string.TransactionAdapterHeader));

        binding.transactionsShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.transactionsSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    private void setArgs() {
        treasuriesModel = (TreasuriesModel) TreasuryFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(treasuriesModel);
    }

    private void setData(TreasuriesModel model) {
        try {
            if (model.getId() != null && !model.getId().equals("")) {
                binding.serialTextView.setText(model.getId());
                data.put("id", model.getId());
            }

            if (model.getTitle() != null && !model.getTitle().equals("")) {
                binding.titleTextView.setText(model.getTitle());
            }

            if (model.getCenterModel() != null && model.getCenterModel().getDetail() != null && model.getCenterModel().getDetail().has("title") && !model.getCenterModel().getDetail().isNull("title") && !model.getCenterModel().getDetail().getString("title").equals("")) {
                binding.centerTextView.setText(model.getCenterModel().getDetail().getString("title"));
                binding.centerTextView.setVisibility(View.VISIBLE);
            } else {
                binding.centerTextView.setVisibility(View.GONE);
            }

            if (model.getBalance() == 0) {
                binding.amountTextView.setText(model.getBalance() + " " + getResources().getString(R.string.MainToman));
                binding.amountTextView.setTextColor(getResources().getColor(R.color.CoolGray700));
            } else if (String.valueOf(model.getBalance()).contains("-")) {
                binding.amountTextView.setText(StringManager.minusSeparate(String.valueOf(model.getBalance())) + " " + getResources().getString(R.string.MainToman));
                binding.amountTextView.setTextColor(getResources().getColor(R.color.Red600));
            } else {
                binding.amountTextView.setText(StringManager.separate(String.valueOf(model.getBalance())) + " " + getResources().getString(R.string.MainToman));
                binding.amountTextView.setTextColor(getResources().getColor(R.color.Emerald600));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getData() {
        Treasury.transaction(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            treasuriesModel = new TreasuriesModel(((JSONObject) object).getJSONObject("treasury"));
                            setData(treasuriesModel);

                            List transactions = new List();
                            for (int i = 0; i < ((JSONObject) object).getJSONArray("data").length(); i++)
                                transactions.add(new TransactionModel(((JSONObject) object).getJSONArray("data").getJSONObject(i)));

                            // Transactions Data
                            if (!transactions.data().isEmpty()) {
                                indexTransactionAdapter.setItems(transactions.data());
                                binding.transactionsSingleLayout.recyclerView.setAdapter(indexTransactionAdapter);

                                binding.transactionsSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (indexTransactionAdapter.getItemCount() == 0) {
                                binding.transactionsSingleLayout.recyclerView.setAdapter(null);

                                binding.transactionsSingleLayout.emptyView.setVisibility(View.VISIBLE);
                                binding.transactionsSingleLayout.emptyView.setText(getResources().getString(R.string.TransactionAdapterEmpty));
                            }

                            binding.transactionsHeaderLayout.countTextView.setText(StringManager.bracing(indexTransactionAdapter.itemsCount()));

                            hideShimmer();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        hideShimmer();
                    });
                }
            }
        });
    }

    private void hideShimmer() {

        // Transactions Data
        binding.transactionsSingleLayout.getRoot().setVisibility(View.VISIBLE);
        binding.transactionsShimmerLayout.getRoot().setVisibility(View.GONE);
        binding.transactionsShimmerLayout.getRoot().stopShimmer();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}