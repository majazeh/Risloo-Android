package com.majazeh.risloo.Views.Fragments.Main.Index;

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
import androidx.navigation.NavDirections;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Main.Index.IndexClientReportAdapter;
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
    private IndexClientReportAdapter adapter;

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
        adapter = new IndexClientReportAdapter(requireActivity());

        handler = new Handler();

        data = new HashMap<>();
        data.put("page", 1);
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.headerIncludeLayout.titleTextView.setText(getResources().getString(R.string.ClientReportsFragmentTitle));

        binding.indexShimmerLayout.shimmerItem1.borderView.setVisibility(View.GONE);

        InitManager.imgResTintBackground(requireActivity(), binding.addImageView.getRoot(), R.drawable.ic_plus_light, R.color.White, R.drawable.draw_oval_solid_green600_ripple_white);
        InitManager.fixedVerticalRecyclerView(requireActivity(), binding.indexSingleLayout.recyclerView, 0, 0, 0, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.searchIncludeLayout.searchEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.searchIncludeLayout.searchEditText.hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.searchIncludeLayout.searchEditText);
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

                if (binding.indexSingleLayout.progressBar.getVisibility() == View.GONE)
                    binding.indexSingleLayout.progressBar.setVisibility(View.VISIBLE);

                getData();
            }
        });

        CustomClickView.onClickListener(() -> {
            if (type.equals("case")) {
                NavDirections action = NavigationMainDirections.actionGlobalCreateReportFragment(caseModel);
                ((MainActivity) requireActivity()).navController.navigate(action);
            } else {
                NavDirections action = NavigationMainDirections.actionGlobalCreateReportFragment(sessionModel);
                ((MainActivity) requireActivity()).navController.navigate(action);
            }
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
        if (model.getCaseId() != null && !model.getCaseId().equals("")) {
            data.put("id", model.getCaseId());
        }
    }

    private void setData(SessionModel model) {
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
                                binding.indexSingleLayout.recyclerView.setAdapter(adapter);

                                binding.indexSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (adapter.itemsCount() == 0) {
                                binding.indexSingleLayout.recyclerView.setAdapter(null);
                                
                                binding.indexSingleLayout.emptyView.setVisibility(View.VISIBLE);
                                if (binding.searchIncludeLayout.searchProgressBar.getVisibility() == View.VISIBLE)
                                    binding.indexSingleLayout.emptyView.setText(getResources().getString(R.string.AppSearchEmpty));
                                else
                                    binding.indexSingleLayout.emptyView.setText(getResources().getString(R.string.ClientReportsFragmentEmpty));
                            }

                            binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(adapter.itemsCount()));

                            binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.indexShimmerLayout.getRoot().stopShimmer();

                            if (binding.indexSingleLayout.progressBar.getVisibility() == View.VISIBLE)
                                binding.indexSingleLayout.progressBar.setVisibility(View.GONE);
                            if (binding.searchIncludeLayout.searchProgressBar.getVisibility() == View.VISIBLE)
                                binding.searchIncludeLayout.searchProgressBar.setVisibility(View.GONE);

                        });

                        isLoading = false;
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.indexShimmerLayout.getRoot().stopShimmer();

                            if (binding.indexSingleLayout.progressBar.getVisibility() == View.VISIBLE)
                                binding.indexSingleLayout.progressBar.setVisibility(View.GONE);
                            if (binding.searchIncludeLayout.searchProgressBar.getVisibility() == View.VISIBLE)
                                binding.searchIncludeLayout.searchProgressBar.setVisibility(View.GONE);

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
                                binding.indexSingleLayout.recyclerView.setAdapter(adapter);

                                binding.indexSingleLayout.emptyView.setVisibility(View.GONE);
                            } else if (adapter.getItemCount() == 0) {
                                binding.indexSingleLayout.emptyView.setVisibility(View.VISIBLE);

                                if (binding.searchIncludeLayout.searchProgressBar.getVisibility() == View.VISIBLE)
                                    binding.indexSingleLayout.emptyView.setText(getResources().getString(R.string.AppSearchEmpty));
                                else
                                    binding.indexSingleLayout.emptyView.setText(getResources().getString(R.string.ClientReportsFragmentEmpty));
                            }

                            binding.headerIncludeLayout.countTextView.setText(StringManager.bracing(adapter.itemsCount()));

                            binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.indexShimmerLayout.getRoot().stopShimmer();

                            if (binding.indexSingleLayout.progressBar.getVisibility() == View.VISIBLE)
                                binding.indexSingleLayout.progressBar.setVisibility(View.GONE);
                            if (binding.searchIncludeLayout.searchProgressBar.getVisibility() == View.VISIBLE)
                                binding.searchIncludeLayout.searchProgressBar.setVisibility(View.GONE);

                        });

                        isLoading = false;
                    }
                }

                @Override
                public void onFailure(String response) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            binding.indexSingleLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.indexShimmerLayout.getRoot().setVisibility(View.GONE);
                            binding.indexShimmerLayout.getRoot().stopShimmer();

                            if (binding.indexSingleLayout.progressBar.getVisibility() == View.VISIBLE)
                                binding.indexSingleLayout.progressBar.setVisibility(View.GONE);
                            if (binding.searchIncludeLayout.searchProgressBar.getVisibility() == View.VISIBLE)
                                binding.searchIncludeLayout.searchProgressBar.setVisibility(View.GONE);

                        });

                        isLoading = false;
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        isLoading = true;
        handler.removeCallbacksAndMessages(null);
    }

}