package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.CreateCaseFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCaseUserFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterUserFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateRoomFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSampleFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateScheduleFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSessionFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditSessionFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTimeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTimeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterDetailFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditSessionTimeFragment;
import com.majazeh.risloo.databinding.SingleItemSearchableBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

public class SearchableAdapter extends RecyclerView.Adapter<SearchableAdapter.SearchableHolder> {

    // Objects
    private Activity activity;

    // Widget
    private TextView countTextView;

    // Vars
    private ArrayList<TypeModel> items;
    private String method;

    public SearchableAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public SearchableHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SearchableHolder(SingleItemSearchableBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchableHolder holder, int i) {
        TypeModel item = items.get(i);

        listener(holder, item);

        setData(holder, item);

        setActive(holder, item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<TypeModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<TypeModel> items, String method, TextView countTextView) {
        this.items = items;
        this.method = method;
        if (countTextView != null) {
            this.countTextView = countTextView;
        }
        notifyDataSetChanged();
    }

    private void detector(SearchableHolder holder, boolean selected) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (selected)
                holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray200_ripple_gray300);
            else
                holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        } else {
            if (selected)
                holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray200);
            else
                holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
        }
    }

    private void listener(SearchableHolder holder, TypeModel item) {
        ClickManager.onDelayedClickListener(() -> {
            switch (Objects.requireNonNull(((MainActivity) activity).navController.getCurrentDestination()).getId()) {
                case R.id.createCaseFragment:
                    CreateCaseFragment createCaseFragment = (CreateCaseFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (createCaseFragment != null) {
                        createCaseFragment.responseDialog(method, item);
                    }
                    break;
                case R.id.createCaseUserFragment:
                    CreateCaseUserFragment createCaseUserFragment = (CreateCaseUserFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (createCaseUserFragment != null) {
                        createCaseUserFragment.responseDialog(method, item);
                    }
                    break;
                case R.id.createCenterFragment:
                    CreateCenterFragment createCenterFragment = (CreateCenterFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (createCenterFragment != null) {
                        createCenterFragment.responseDialog(method, item);
                    }
                    break;
                case R.id.createCenterUserFragment:
                    CreateCenterUserFragment createCenterUserFragment = (CreateCenterUserFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (createCenterUserFragment != null) {
                        createCenterUserFragment.responseDialog(method, item);
                    }
                    break;
                case R.id.createRoomFragment:
                    CreateRoomFragment createRoomFragment = (CreateRoomFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (createRoomFragment != null) {
                        createRoomFragment.responseDialog(method, item);
                    }
                    break;
                case R.id.createSampleFragment:
                    CreateSampleFragment createSampleFragment = (CreateSampleFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (createSampleFragment != null) {
                        createSampleFragment.responseDialog(method, item);
                    }
                    break;
                case R.id.createScheduleFragment:
                    CreateScheduleFragment createScheduleFragment = (CreateScheduleFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (createScheduleFragment != null) {
                        switch (method) {
                            case "cases":
                                CreateScheduleReferenceFragment createScheduleReferenceFragment = (CreateScheduleReferenceFragment) createScheduleFragment.adapter.hashMap.get(createScheduleFragment.binding.viewPager.getRoot().getCurrentItem());
                                if (createScheduleReferenceFragment != null) {
                                    createScheduleReferenceFragment.responseDialog(method, item);
                                }
                                break;
                            case "patternDays":
                                CreateScheduleTimeFragment createScheduleTimeFragment = (CreateScheduleTimeFragment) createScheduleFragment.adapter.hashMap.get(createScheduleFragment.binding.viewPager.getRoot().getCurrentItem());
                                if (createScheduleTimeFragment != null) {
                                    createScheduleTimeFragment.responseDialog(method, item);
                                }
                                break;
                        }
                    }
                    break;
                case R.id.createSessionFragment:
                    CreateSessionFragment createSessionFragment = (CreateSessionFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (createSessionFragment != null) {
                        if (method.equals("patternDays")) {
                            CreateSessionTimeFragment createSessionTimeFragment = (CreateSessionTimeFragment) createSessionFragment.adapter.hashMap.get(createSessionFragment.binding.viewPager.getRoot().getCurrentItem());
                            if (createSessionTimeFragment != null) {
                                createSessionTimeFragment.responseDialog(method, item);
                            }
                        }
                    }
                    break;
                case R.id.editCenterFragment:
                    EditCenterFragment editCenterFragment = (EditCenterFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (editCenterFragment != null) {
                        EditCenterDetailFragment editCenterDetailFragment = (EditCenterDetailFragment) editCenterFragment.adapter.hashMap.get(editCenterFragment.binding.viewPager.getRoot().getCurrentItem());
                        if (editCenterDetailFragment != null) {
                            editCenterDetailFragment.responseDialog(method, item);
                        }
                    }
                    break;
                case R.id.editSessionFragment:
                    EditSessionFragment editSessionFragment = (EditSessionFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (editSessionFragment != null) {
                        if (method.equals("patternDays")) {
                            EditSessionTimeFragment editSessionTimeFragment = (EditSessionTimeFragment) editSessionFragment.adapter.hashMap.get(editSessionFragment.binding.viewPager.getRoot().getCurrentItem());
                            if (editSessionTimeFragment != null) {
                                editSessionTimeFragment.responseDialog(method, item);
                            }
                        }
                    }
                    break;
            }
            notifyDataSetChanged();
        }).widget(holder.binding.getRoot());
    }

    private void setData(SearchableHolder holder, TypeModel item) {
        try {
            switch (method) {
                case "scales":
                case "rooms":
                    holder.binding.titleTextView.setText(item.object.get("title").toString());

                    holder.binding.subTextView.setVisibility(View.VISIBLE);
                    holder.binding.subTextView.setText(item.object.get("subtitle").toString());
                    break;
                case "references":
                case "managers":
                    // TODO: write title
                    holder.binding.titleTextView.setText(((UserModel)item).getName());
                    break;
                case "cases":
                case "sessions":
                case "psychologies":
                case "patternDays":
                    holder.binding.titleTextView.setText(item.object.get("title").toString());

                    holder.binding.subTextView.setVisibility(View.GONE);
                    holder.binding.subTextView.setText("");
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setActive(SearchableHolder holder, TypeModel item) {
        try {
            switch (Objects.requireNonNull(((MainActivity) activity).navController.getCurrentDestination()).getId()) {
                case R.id.createCaseFragment:
                    CreateCaseFragment createCaseFragment = (CreateCaseFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (createCaseFragment != null) {
                        switch (method) {
                            case "references":
                                detector(holder, createCaseFragment.referencesAdapter.getIds().contains(item.object.get("id").toString()));
                                calculateCount(createCaseFragment.referencesAdapter.getIds().size());
                                break;
                            case "rooms":
                                detector(holder, createCaseFragment.roomId.equals(item.object.get("id").toString()));
                                break;
                        }
                    }
                    break;
                case R.id.createCaseUserFragment:
                    CreateCaseUserFragment createCaseUserFragment = (CreateCaseUserFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (createCaseUserFragment != null) {
                        if (method.equals("references")) {
                            detector(holder, createCaseUserFragment.referencesAdapter.getIds().contains(item.object.get("id").toString()));
                            calculateCount(createCaseUserFragment.referencesAdapter.getIds().size());
                        }
                    }
                    break;
                case R.id.createCenterFragment:
                    CreateCenterFragment createCenterFragment = (CreateCenterFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (createCenterFragment != null) {
                        if (method.equals("managers")) {
                            detector(holder, createCenterFragment.managerId.equals(item.object.get("id").toString()));
                        }
                    }
                    break;
                case R.id.createCenterUserFragment:
                    CreateCenterUserFragment createCenterUserFragment = (CreateCenterUserFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (createCenterUserFragment != null) {
                        if (method.equals("rooms")) {
                            detector(holder, createCenterUserFragment.roomId.equals(item.object.get("id").toString()));
                        }
                    }
                    break;
                case R.id.createRoomFragment:
                    CreateRoomFragment createRoomFragment = (CreateRoomFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (createRoomFragment != null) {
                        if (method.equals("psychologies")) {
                            detector(holder, createRoomFragment.psychologyId.equals(item.object.get("id").toString()));
                        }
                    }
                    break;
                case R.id.createSampleFragment:
                    CreateSampleFragment createSampleFragment = (CreateSampleFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (createSampleFragment != null) {
                        switch (method) {
                            case "scales":
                                detector(holder, createSampleFragment.scalesAdapter.getIds().contains(item.object.get("id").toString()));
                                calculateCount(createSampleFragment.scalesAdapter.getIds().size());
                                break;
                            case "references":
                                detector(holder, createSampleFragment.referencesAdapter.getIds().contains(item.object.get("id").toString()));
                                calculateCount(createSampleFragment.referencesAdapter.getIds().size());
                                break;
                            case "rooms":
                                detector(holder, createSampleFragment.roomId.equals(item.object.get("id").toString()));
                                break;
                            case "cases":
                                detector(holder, createSampleFragment.caseId.equals(item.object.get("id").toString()));
                                break;
                            case "sessions":
                                detector(holder, createSampleFragment.sessionId.equals(item.object.get("id").toString()));
                                break;
                        }
                    }
                    break;
                case R.id.createScheduleFragment:
                    CreateScheduleFragment createScheduleFragment = (CreateScheduleFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (createScheduleFragment != null) {
                        switch (method) {
                            case "cases":
                                CreateScheduleReferenceFragment createScheduleReferenceFragment = (CreateScheduleReferenceFragment) createScheduleFragment.adapter.hashMap.get(createScheduleFragment.binding.viewPager.getRoot().getCurrentItem());
                                if (createScheduleReferenceFragment != null) {
                                    detector(holder, createScheduleReferenceFragment.caseId.equals(item.object.get("id").toString()));
                                }
                                break;
                            case "patternDays":
                                CreateScheduleTimeFragment createScheduleTimeFragment = (CreateScheduleTimeFragment) createScheduleFragment.adapter.hashMap.get(createScheduleFragment.binding.viewPager.getRoot().getCurrentItem());
                                if (createScheduleTimeFragment != null) {
                                    detector(holder, createScheduleTimeFragment.patternDaysAdapter.getIds().contains(item.object.get("id").toString()));
                                    calculateCount(createScheduleTimeFragment.patternDaysAdapter.getIds().size());
                                }
                                break;
                        }
                    }
                    break;
                case R.id.createSessionFragment:
                    CreateSessionFragment createSessionFragment = (CreateSessionFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (createSessionFragment != null) {
                        if (method.equals("patternDays")) {
                            CreateSessionTimeFragment createSessionTimeFragment = (CreateSessionTimeFragment) createSessionFragment.adapter.hashMap.get(createSessionFragment.binding.viewPager.getRoot().getCurrentItem());
                            if (createSessionTimeFragment != null) {
                                detector(holder, createSessionTimeFragment.patternDaysAdapter.getIds().contains(item.object.get("id").toString()));
                                calculateCount(createSessionTimeFragment.patternDaysAdapter.getIds().size());
                            }
                        }
                    }
                    break;
                case R.id.editCenterFragment:
                    EditCenterFragment editCenterFragment = (EditCenterFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (editCenterFragment != null) {
                        if (method.equals("managers")) {
                            EditCenterDetailFragment editCenterDetailFragment = (EditCenterDetailFragment) editCenterFragment.adapter.hashMap.get(editCenterFragment.binding.viewPager.getRoot().getCurrentItem());
                            if (editCenterDetailFragment != null) {
                                detector(holder, editCenterDetailFragment.managerId.equals(item.object.get("id").toString()));
                            }
                        }
                    }
                    break;
                case R.id.editSessionFragment:
                    EditSessionFragment editSessionFragment = (EditSessionFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (editSessionFragment != null) {
                        if (method.equals("patternDays")) {
                            EditSessionTimeFragment editSessionTimeFragment = (EditSessionTimeFragment) editSessionFragment.adapter.hashMap.get(editSessionFragment.binding.viewPager.getRoot().getCurrentItem());
                            if (editSessionTimeFragment != null) {
                                detector(holder, editSessionTimeFragment.patternDaysAdapter.getIds().contains(item.object.get("id").toString()));
                                calculateCount(editSessionTimeFragment.patternDaysAdapter.getIds().size());
                            }
                        }
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void calculateCount(int listSize) {
        if (listSize != 0) {
            String count = "(" + listSize + ")";

            countTextView.setVisibility(View.VISIBLE);
            countTextView.setText(count);
        } else {
            countTextView.setVisibility(View.GONE);
            countTextView.setText("");
        }
    }

    public class SearchableHolder extends RecyclerView.ViewHolder {

        private SingleItemSearchableBinding binding;

        public SearchableHolder(SingleItemSearchableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}