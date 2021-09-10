package com.majazeh.risloo.Views.Fragments.Show;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.FragmentBillBinding;
import com.mre.ligheh.Model.TypeModel.BillingModel;

import java.util.HashMap;

public class BillFragment extends Fragment {

    // Binding
    private FragmentBillBinding binding;

    // Adapters
//    private IndexTimeAdapter adapter;

    // Models
    private BillingModel billingModel;

    // Objects
    private HashMap data, header;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentBillBinding.inflate(inflater, viewGroup, false);

        initializer();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
//        adapter = new IndexTimeAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.timesHeaderLayout.titleTextView.setText(getResources().getString(R.string.TimesAdapterHeader));

        binding.timesShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);

        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.timesSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    private void setArgs() {
        billingModel = (BillingModel) BillFragmentArgs.fromBundle(getArguments()).getTypeModel();
        setData(billingModel);
    }

    private void setData(BillingModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            binding.serialTextView.setText(model.getId());
            data.put("id", model.getId());
        }

        if (model.getTitle() != null && !model.getTitle().equals("")) {
            binding.titleTextView.setText(model.getTitle());
        }

        if (model.getAmount() != 0) {
            String amount = StringManager.separate(String.valueOf(model.getAmount())) + " " + getResources().getString(R.string.MainToman);
            binding.amountTextView.setText(amount);
        } else {
            String amount = "0" + " " + getResources().getString(R.string.MainToman);
            binding.amountTextView.setText(amount);
        }

//        if (model.getType() != null && !model.getType().equals("")) {
//            binding.statusTextView.setText(model.getType());
//        }

        if (model.getCreated_at() != 0) {
            binding.dateTextView.setText(DateManager.jalYYYYsNMMsDDsNDDsHHsMM(String.valueOf(model.getCreated_at()), " "));
        }
    }

    private void getData() {
//        Bill.showDashborad(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                billingModel = (BillingModel) object;
//
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        setData(billingModel);
//
//                        // Times Data
//                        if (!billingModel.getTimes().data().isEmpty()) {
//                            adapter.setItems(billingModel.getTimes().data());
//                            binding.timesSingleLayout.recyclerView.setAdapter(adapter);
//
//                            binding.timesSingleLayout.emptyView.setVisibility(View.GONE);
//                        } else if (adapter.getItemCount() == 0) {
//                            binding.timesSingleLayout.emptyView.setVisibility(View.VISIBLE);
//                            binding.timesSingleLayout.emptyView.setText(getResources().getString(R.string.TimesAdapterEmpty));
//                        }
//
//                        binding.timesHeaderLayout.countTextView.setText(StringManager.bracing(adapter.itemsCount()));
//
//                        // Times Data
//                        binding.timesSingleLayout.getRoot().setVisibility(View.VISIBLE);
//                        binding.timesShimmerLayout.getRoot().setVisibility(View.GONE);
//                        binding.timesShimmerLayout.getRoot().stopShimmer();
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
//                        // Times Data
//                        binding.timesSingleLayout.getRoot().setVisibility(View.VISIBLE);
//                        binding.timesShimmerLayout.getRoot().setVisibility(View.GONE);
//                        binding.timesShimmerLayout.getRoot().stopShimmer();
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