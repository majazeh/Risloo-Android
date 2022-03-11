package com.majazeh.risloo.views.fragments.main.index;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.databinding.FragmentCommissionsBinding;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.SpannableManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.main.Table.TableCommissionAdapter;

import java.util.HashMap;
import java.util.Objects;

public class FragmentCommissions extends Fragment {

    // Binding
    private FragmentCommissionsBinding binding;

    // Adapters
    private TableCommissionAdapter adapter;

    // Objects
    private HashMap data, header;

    // Vars
    private String share = "";
    private boolean isLoading = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCommissionsBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new TableCommissionAdapter(requireActivity());

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.contributionHeaderLayout.titleTextView.setText(getResources().getString(R.string.CommissionsFragmentContributionHeader));
        binding.contributionHeaderLayout.titleTextView.setTextColor(requireActivity().getResources().getColor(R.color.emerald600));

        binding.shareIncludeLayout.headerTextView.setText(SpannableManager.spanForegroundColorSize(getResources().getString(R.string.CommissionsFragmentContributionShareHeader), 10, 16, getResources().getColor(R.color.coolGray500), (int) getResources().getDimension(R.dimen._9ssp)));
        binding.shareIncludeLayout.inputEditText.setHint(getResources().getString(R.string.CommissionsFragmentContributionShareHint));

        InitManager.txtTextColorBackground(binding.contributionTextView.getRoot(), getResources().getString(R.string.CommissionsFragmentContributionButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);

        binding.tableHeaderLayout.titleTextView.setText(getResources().getString(R.string.CommissionAdapterHeader));

        binding.tableShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);

        InitManager.rcvVerticalFixedUnnested(requireActivity(), binding.tableSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.shareIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.shareIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.shareIncludeLayout.inputEditText);
            return false;
        });

        binding.shareIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            share = binding.shareIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.getRoot().setMOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (!isLoading && !Objects.requireNonNull(v).canScrollVertically(1)) {
                isLoading = true;

                if (data.containsKey("page"))
                    data.put("page", ((int) data.get("page")) + 1);
                else
                    data.put("page", 1);

                if (binding.tableSingleLayout.progressBar.getVisibility() == View.GONE)
                    binding.tableSingleLayout.progressBar.setVisibility(View.VISIBLE);

                getData();
            }
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.shareErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.shareErrorLayout.getRoot(), binding.shareErrorLayout.errorTextView);

            doWork();
        }).widget(binding.contributionTextView.getRoot());
    }

    private void setArgs() {
        // TODO : Place Code When Needed
    }

    private void setData() {
        // TODO : Place Code When Needed
    }

    private void getData() {
//        Commission.list(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                List items = (List) object;
//
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        if (Objects.equals(data.get("page"), 1))
//                            adapter.clearItems();
//
//                        if (!items.data().isEmpty()) {
//                            adapter.setItems(items.data());
                            binding.tableSingleLayout.recyclerView.setAdapter(adapter);
//
//                            binding.tableSingleLayout.emptyView.setVisibility(View.GONE);
//                        } else if (adapter.itemsCount() == 0) {
//                            binding.tableSingleLayout.recyclerView.setAdapter(null);
//
//                            binding.tableSingleLayout.emptyView.setVisibility(View.VISIBLE);
//                            binding.tableSingleLayout.emptyView.setText(getResources().getString(R.string.CommissionAdapterEmpty));
//                        }
//
//                        binding.tableHeaderLayout.countTextView.setText(StringManager.bracing(items.getTotal()));
//
                        hideShimmer();
//                    });
//
//                    isLoading = false;
//                }
//            }
//
//            @Override
//            public void onFailure(String response) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        hideShimmer();
//                    });
//
//                    isLoading = false;
//                }
//            }
//        });
    }

    private void setHashmap() {
        if (!share.equals(""))
            data.put("commission", share);
        else
            data.remove("commission");
    }

    private void doWork() {
//        DialogManager.showLoadingDialog(requireActivity(), "");
//
//        setHashmap();
//
//        Commission.post(data, header, new Response() {
//            @Override
//            public void onOK(Object object) {
//                CommissionModel commissionModel = (CommissionModel) object;
//
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        DialogManager.dismissLoadingDialog();
//
//                        // TODO : Place Code When Needed
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(String response) {
//                if (isAdded()) {
//                    requireActivity().runOnUiThread(() -> {
//                        try {
//                            JSONObject responseObject = new JSONObject(response);
//                            if (!responseObject.isNull("errors")) {
//                                JSONObject errorsObject = responseObject.getJSONObject("errors");
//
//                                Iterator<String> keys = (errorsObject.keys());
//                                StringBuilder allErrors = new StringBuilder();
//
//                                while (keys.hasNext()) {
//                                    String key = keys.next();
//                                    StringBuilder keyErrors = new StringBuilder();
//
//                                    for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
//                                        String error = errorsObject.getJSONArray(key).getString(i);
//
//                                        keyErrors.append(error);
//                                        keyErrors.append("\n");
//
//                                        allErrors.append(error);
//                                        allErrors.append("\n");
//                                    }
//
//                                    switch (key) {
//                                        case "commission":
//                                            ((MainActivity) requireActivity()).validatoon.showValid(binding.shareErrorLayout.getRoot(), binding.shareErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
//                                            break;
//                                    }
//                                }
//
//                                SnackManager.showErrorSnack(requireActivity(), allErrors.substring(0, allErrors.length() - 1));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    });
//                }
//            }
//        });
    }

    private void hideShimmer() {
        binding.tableSingleLayout.getRoot().setVisibility(View.VISIBLE);
        binding.tableShimmerLayout.getRoot().setVisibility(View.GONE);
        binding.tableShimmerLayout.getRoot().stopShimmer();

        if (binding.tableSingleLayout.progressBar.getVisibility() == View.VISIBLE)
            binding.tableSingleLayout.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        isLoading = true;
    }

}