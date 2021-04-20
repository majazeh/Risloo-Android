package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Entities.Model;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.CreateCaseFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCaseUserFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterUserFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateRoomFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSampleFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterDetailFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterFragment;
import com.majazeh.risloo.databinding.SingleItemSearchableBinding;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

public class SearchableAdapter extends RecyclerView.Adapter<SearchableAdapter.SearchableHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<Model> items;
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
        Model item = items.get(i);

        listener(holder, item);

        setData(holder, item);

        setActive(holder, item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<Model> getItems() {
        return items;
    }

    public void setItems(ArrayList<Model> items, String method) {
        this.items = items;
        this.method = method;
        notifyDataSetChanged();
    }

    private void detector(SearchableHolder holder, boolean selected) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (selected)
                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray200_ripple_gray300);
            else
                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        } else {
            if (selected)
                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray200);
            else
                holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
        }
    }

    private void listener(SearchableHolder holder, Model item) {
        ClickManager.onDelayedClickListener(() -> {
            switch (Objects.requireNonNull(((MainActivity) activity).navController.getCurrentDestination()).getId()) {
                case R.id.createCaseFragment:
                    CreateCaseFragment createCaseFragment = (CreateCaseFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createCaseFragment != null) {
                        createCaseFragment.responseDialog(method, item);
                    }
                    break;
                case R.id.createCaseUserFragment:
                    CreateCaseUserFragment createCaseUserFragment = (CreateCaseUserFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createCaseUserFragment != null) {
                        createCaseUserFragment.responseDialog(method, item);
                    }
                    break;
                case R.id.createCenterFragment:
                    CreateCenterFragment createCenterFragment = (CreateCenterFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createCenterFragment != null) {
                        createCenterFragment.responseDialog(method, item);
                    }
                    break;
                case R.id.createCenterUserFragment:
                    CreateCenterUserFragment createCenterUserFragment = (CreateCenterUserFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createCenterUserFragment != null) {
                        createCenterUserFragment.responseDialog(method, item);
                    }
                    break;
                case R.id.createRoomFragment:
                    CreateRoomFragment createRoomFragment = (CreateRoomFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createRoomFragment != null) {
                        createRoomFragment.responseDialog(method, item);
                    }
                    break;
                case R.id.createSampleFragment:
                    CreateSampleFragment createSampleFragment = (CreateSampleFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createSampleFragment != null) {
                        createSampleFragment.responseDialog(method, item);
                    }
                    break;
                case R.id.editCenterFragment:
                    EditCenterFragment editCenterFragment = (EditCenterFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (editCenterFragment != null) {
                        EditCenterDetailFragment editCenterDetailFragment = (EditCenterDetailFragment) editCenterFragment.adapter.getRegisteredFragment(0);

                        editCenterDetailFragment.responseDialog(method, item);
                    }
                    break;
            }
            notifyDataSetChanged();
        }).widget(holder.binding.getRoot());
    }

    private void setData(SearchableHolder holder, Model item) {
        try {
            switch (method) {
                case "scales":
                case "rooms":
                    holder.binding.titleTextView.setText(item.get("title").toString());

                    holder.binding.subTextView.setVisibility(View.VISIBLE);
                    holder.binding.subTextView.setText(item.get("subtitle").toString());
                    break;
                case "references":
                case "managers":
                case "cases":
                case "sessions":
                case "psychologies":
                    holder.binding.titleTextView.setText(item.get("title").toString());

                    holder.binding.subTextView.setVisibility(View.GONE);
                    holder.binding.subTextView.setText("");
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setActive(SearchableHolder holder, Model item) {
        try {
            switch (Objects.requireNonNull(((MainActivity) activity).navController.getCurrentDestination()).getId()) {
                case R.id.createCaseFragment:
                    CreateCaseFragment createCaseFragment = (CreateCaseFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createCaseFragment != null) {
                        switch (method) {
                            case "references":
                                detector(holder, createCaseFragment.referencesAdapter.getIds().contains(item.get("id").toString()));
                                break;
                            case "rooms":
                                detector(holder, createCaseFragment.roomId.equals(item.get("id").toString()));
                                break;
                        }
                    }
                    break;
                case R.id.createCaseUserFragment:
                    CreateCaseUserFragment createCaseUserFragment = (CreateCaseUserFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createCaseUserFragment != null) {
                        if (method.equals("references")) {
                            detector(holder, createCaseUserFragment.referencesAdapter.getIds().contains(item.get("id").toString()));
                        }
                    }
                    break;
                case R.id.createCenterFragment:
                    CreateCenterFragment createCenterFragment = (CreateCenterFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createCenterFragment != null) {
                        if (method.equals("managers")) {
                            detector(holder, createCenterFragment.managerId.equals(item.get("id").toString()));
                        }
                    }
                    break;
                case R.id.createCenterUserFragment:
                    CreateCenterUserFragment createCenterUserFragment = (CreateCenterUserFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createCenterUserFragment != null) {
                        if (method.equals("rooms")) {
                            detector(holder, createCenterUserFragment.roomId.equals(item.get("id").toString()));
                        }
                    }
                    break;
                case R.id.createRoomFragment:
                    CreateRoomFragment createRoomFragment = (CreateRoomFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createRoomFragment != null) {
                        if (method.equals("psychologies")) {
                            detector(holder, createRoomFragment.psychologyId.equals(item.get("id").toString()));
                        }
                    }
                    break;
                case R.id.createSampleFragment:
                    CreateSampleFragment createSampleFragment = (CreateSampleFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createSampleFragment != null) {
                        switch (method) {
                            case "scales":
                                detector(holder, createSampleFragment.scalesAdapter.getIds().contains(item.get("id").toString()));
                                break;
                            case "references":
                                detector(holder, createSampleFragment.referencesAdapter.getIds().contains(item.get("id").toString()));
                                break;
                            case "rooms":
                                detector(holder, createSampleFragment.roomId.equals(item.get("id").toString()));
                                break;
                            case "cases":
                                detector(holder, createSampleFragment.caseId.equals(item.get("id").toString()));
                                break;
                            case "sessions":
                                detector(holder, createSampleFragment.sessionId.equals(item.get("id").toString()));
                                break;
                        }
                    }
                    break;
                case R.id.editCenterFragment:
                    EditCenterFragment editCenterFragment = (EditCenterFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (editCenterFragment != null) {
                        EditCenterDetailFragment editCenterDetailFragment = (EditCenterDetailFragment) editCenterFragment.adapter.getRegisteredFragment(0);

                        if (method.equals("managers")) {
                            detector(holder, editCenterDetailFragment.managerId.equals(item.get("id").toString()));
                        }
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
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