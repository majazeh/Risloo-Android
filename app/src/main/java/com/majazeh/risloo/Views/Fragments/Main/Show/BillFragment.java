package com.majazeh.risloo.Views.Fragments.Main.Show;

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
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Index.IndexTimeAdapter;
import com.majazeh.risloo.databinding.FragmentBillBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Billing;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.BillingModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class BillFragment extends Fragment {

    // Binding
    private FragmentBillBinding binding;

    // Adapters
    private IndexTimeAdapter indexTimeAdapter;

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
        indexTimeAdapter = new IndexTimeAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.timesHeaderLayout.titleTextView.setText(getResources().getString(R.string.TimeAdapterHeader));

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

        if (model.getCreated_at() != 0) {
            binding.dateTextView.setText(DateManager.jalYYYYsNMMsDDsNDDsHHsMM(String.valueOf(model.getCreated_at()), " "));
        }

        if (model.getType() != null && !model.getType().equals("")) {
            binding.statusTextView.setText(SelectionManager.getBillType(requireActivity(), "fa", model.getType()));
        }

        if (model.getAmount() != 0) {
            binding.amountTextView.setText(StringManager.separate(String.valueOf(model.getAmount())) + " " + getResources().getString(R.string.MainToman));
        } else {
            binding.amountTextView.setText("0" + " " + getResources().getString(R.string.MainToman));
        }
    }

    private void getData() {
        Billing.show(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            billingModel = new BillingModel(((JSONObject) object).getJSONObject("billing"));
                            setData(billingModel);

                            List times = new List();
                            for (int i = 0; i < ((JSONObject) object).getJSONArray("data").length(); i++)
                                times.add(new BillingModel(((JSONObject) object).getJSONArray("data").getJSONObject(i)));

                            // Times Data
                            if (!times.data().isEmpty()) {
                                indexTimeAdapter.setItems(times.data());
                                binding.timesSingleLayout.recyclerView.setAdapter(indexTimeAdapter);

                                binding.timesSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (indexTimeAdapter.getItemCount() == 0) {
                                binding.timesSingleLayout.recyclerView.setAdapter(null);

                                binding.timesSingleLayout.emptyView.setVisibility(View.VISIBLE);
                                binding.timesSingleLayout.emptyView.setText(getResources().getString(R.string.TimeAdapterEmpty));
                            }

                            binding.timesHeaderLayout.countTextView.setText(StringManager.bracing(indexTimeAdapter.itemsCount()));

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

        // Times Data
        binding.timesSingleLayout.getRoot().setVisibility(View.VISIBLE);
        binding.timesShimmerLayout.getRoot().setVisibility(View.GONE);
        binding.timesShimmerLayout.getRoot().stopShimmer();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}