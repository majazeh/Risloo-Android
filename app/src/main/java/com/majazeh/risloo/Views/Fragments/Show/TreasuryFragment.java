package com.majazeh.risloo.Views.Fragments.Show;

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
import com.majazeh.risloo.databinding.FragmentTreasuryBinding;
import com.mre.ligheh.Model.TypeModel.TreasuriesModel;

import java.util.HashMap;

public class TreasuryFragment extends Fragment {

    // Binding
    private FragmentTreasuryBinding binding;

    // Adapters
//    private IndexTransactionsAdapter adapter;

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
//        adapter = new IndexTransactionsAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.transactionsHeaderLayout.titleTextView.setText(getResources().getString(R.string.TransactionsAdapterHeader));

        binding.transactionsShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.transactionsSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    private void setArgs() {
        treasuriesModel = (TreasuriesModel) TreasuryFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(treasuriesModel);
    }

    private void setData(TreasuriesModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            binding.serialTextView.setText(model.getId());
            data.put("id", model.getId());
        }

        if (model.getTitle() != null && !model.getTitle().equals("")) {
            binding.titleTextView.setText(model.getTitle());
        }

        // TODO : Place Center Code Here

        if (model.getBalance() != 0) {
            String amount = StringManager.separate(String.valueOf(model.getBalance())) + " " + getResources().getString(R.string.MainToman);
            binding.amountTextView.setText(amount);
        } else {
            String amount = "0" + " " + getResources().getString(R.string.MainToman);
            binding.amountTextView.setText(amount);
        }
    }

    private void getData() {
//        Treasury.showDashborad(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                treasuriesModel = (TreasuriesModel) object;
//
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        setData(treasuriesModel);
//
//                        // Transactions Data
//                        if (!treasuriesModel.getTransactions().data().isEmpty()) {
//                            adapter.setItems(treasuriesModel.getTransactions().data());
//                            binding.transactionsSingleLayout.recyclerView.setAdapter(adapter);
//
//                            binding.transactionsSingleLayout.emptyView.setVisibility(View.GONE);
//                        } else if (adapter.getItemCount() == 0) {
//                            binding.transactionsSingleLayout.emptyView.setVisibility(View.VISIBLE);
//                            binding.transactionsSingleLayout.emptyView.setText(getResources().getString(R.string.TransactionsAdapterEmpty));
//                        }
//
//                        binding.transactionsHeaderLayout.countTextView.setText(StringManager.bracing(adapter.itemsCount()));
//
//                        // Transactions Data
//                        binding.transactionsSingleLayout.getRoot().setVisibility(View.VISIBLE);
//                        binding.transactionsShimmerLayout.getRoot().setVisibility(View.GONE);
//                        binding.transactionsShimmerLayout.getRoot().stopShimmer();
//
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(String response) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//
//                        // Transactions Data
//                        binding.transactionsSingleLayout.getRoot().setVisibility(View.VISIBLE);
//                        binding.transactionsShimmerLayout.getRoot().setVisibility(View.GONE);
//                        binding.transactionsShimmerLayout.getRoot().stopShimmer();
//
//                    });
//                }
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}