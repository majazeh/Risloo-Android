package com.majazeh.risloo.views.fragments.main.index;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.MainActivity;
import com.majazeh.risloo.views.adapters.recycler.main.Table.TableClientReportAdapter;
import com.majazeh.risloo.databinding.FragmentClientReportsBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Case;
import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.Madule.Session;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.HashMap;
import java.util.Objects;

public class ClientReportsFragment extends Fragment {

    // Binding
    private FragmentClientReportsBinding binding;

    // Adapters
    private TableClientReportAdapter adapter;

    // Models
    private CaseModel caseModel;
    private SessionModel sessionModel;

    // Objects
    private Handler handler;
    private HashMap data, header;

    // Vars
    private String type = "";
    private boolean isLoading = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentClientReportsBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        getData();

        return binding.getRoot();
    }

    private void initializer() {
        adapter = new TableClientReportAdapter(requireActivity());

        handler = new Handler();

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.tableShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);

        InitManager.imgResTintBackground(requireActivity(), binding.addImageView.getRoot(), R.drawable.ic_plus_light, R.color.white, R.drawable.draw_oval_solid_emerald600_ripple_white);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.tableSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.searchIncludeLayout.searchEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.searchIncludeLayout.searchEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(binding.searchIncludeLayout.searchEditText);
            return false;
        });

        binding.searchIncludeLayout.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(() -> {
                    data.put("page", 1);
                    data.put("q", String.valueOf(s));

                    if (binding.searchIncludeLayout.searchProgressBar.getVisibility() == View.GONE)
                        binding.searchIncludeLayout.searchProgressBar.setVisibility(View.VISIBLE);

                    getData();
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
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

        CustomClickView.onClickListener(() -> {
            if (type.equals("case"))
                ((MainActivity) requireActivity()).navigatoon.navigateToCreateClientReportFragment(caseModel);
            else
                ((MainActivity) requireActivity()).navigatoon.navigateToCreateClientReportFragment(sessionModel);

        }).widget(binding.addImageView.getRoot());
    }

    private void setArgs() {
        TypeModel typeModel = ClientReportsFragmentArgs.fromBundle(getArguments()).getTypeModel();

        if (StringManager.substring(typeModel.getClass().getName(), '.').equals("CaseModel")) {
            type = "case";

            caseModel = (CaseModel) typeModel;
            setData(caseModel);
        } else if (StringManager.substring(typeModel.getClass().getName(), '.').equals("SessionModel")) {
            type = "session";

            sessionModel = (SessionModel) typeModel;
            setData(sessionModel);
        }
    }

    private void setData(CaseModel model) {
        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.ClientReportsFragmentCaseTitle));

        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }
    }

    private void setData(SessionModel model) {
        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.ClientReportsFragmentSessionTitle));

        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }
    }

    private void getData() {
        if (type.equals("case")) {
            Case.reports(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    List items = (List) object;

                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            if (Objects.equals(data.get("page"), 1))
                                adapter.clearItems();

                            if (!items.data().isEmpty()) {
                                adapter.setItems(items.data());
                                binding.tableSingleLayout.recyclerView.setAdapter(adapter);

                                binding.tableSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (adapter.itemsCount() == 0) {
                                binding.tableSingleLayout.recyclerView.setAdapter(null);

                                binding.tableSingleLayout.emptyView.setVisibility(View.VISIBLE);
                                if (binding.searchIncludeLayout.searchProgressBar.getVisibility() == View.VISIBLE)
                                    binding.tableSingleLayout.emptyView.setText(getResources().getString(R.string.AppSearchEmpty));
                                else
                                    binding.tableSingleLayout.emptyView.setText(getResources().getString(R.string.ClientReportsFragmentCaseEmpty));
                            }

                            binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(items.getTotal()));

                            hideShimmer();
                        });

                        isLoading = false;
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            hideShimmer();
                        });

                        isLoading = false;
                    }
                }
            });
        } else {
            Session.reports(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    List items = (List) object;

                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            if (Objects.equals(data.get("page"), 1))
                                adapter.clearItems();

                            if (!items.data().isEmpty()) {
                                adapter.setItems(items.data());
                                binding.tableSingleLayout.recyclerView.setAdapter(adapter);

                                binding.tableSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (adapter.getItemCount() == 0) {
                                binding.tableSingleLayout.emptyView.setVisibility(View.VISIBLE);

                                if (binding.searchIncludeLayout.searchProgressBar.getVisibility() == View.VISIBLE)
                                    binding.tableSingleLayout.emptyView.setText(getResources().getString(R.string.AppSearchEmpty));
                                else
                                    binding.tableSingleLayout.emptyView.setText(getResources().getString(R.string.ClientReportsFragmentSessionEmpty));
                            }

                            binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(items.getTotal()));

                            hideShimmer();
                        });

                        isLoading = false;
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            hideShimmer();
                        });

                        isLoading = false;
                    }
                }
            });
        }
    }

    private void hideShimmer() {
        binding.tableSingleLayout.getRoot().setVisibility(View.VISIBLE);
        binding.tableShimmerLayout.getRoot().setVisibility(View.GONE);
        binding.tableShimmerLayout.getRoot().stopShimmer();

        if (binding.tableSingleLayout.progressBar.getVisibility() == View.VISIBLE)
            binding.tableSingleLayout.progressBar.setVisibility(View.GONE);
        if (binding.searchIncludeLayout.searchProgressBar.getVisibility() == View.VISIBLE)
            binding.searchIncludeLayout.searchProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        isLoading = true;
        handler.removeCallbacksAndMessages(null);
    }

}