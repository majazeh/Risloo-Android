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
import com.majazeh.risloo.Views.Fragments.Create.CreateSampleFragment;
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
            try {
                switch (Objects.requireNonNull(((MainActivity) activity).navController.getCurrentDestination()).getId()) {
                    case R.id.createSampleFragment:
                        CreateSampleFragment createSampleFragment = (CreateSampleFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                        if (createSampleFragment != null) {
                            if (method.equals("scales")) {
                                int position = createSampleFragment.scalesAdapter.getIds().indexOf(item.get("id").toString());

                                if (position == -1)
                                    createSampleFragment.scalesAdapter.addItem(item);
                                else
                                    createSampleFragment.scalesAdapter.removeItem(position);

                            } else if (method.equals("references")) {
                                int position = createSampleFragment.referencesAdapter.getIds().indexOf(item.get("id").toString());

                                if (position == -1)
                                    createSampleFragment.referencesAdapter.addItem(item);
                                else
                                    createSampleFragment.referencesAdapter.removeItem(position);
                            }
                        }
                        break;
                    case R.id.createCaseFragment:
                        CreateCaseFragment createCaseFragment = (CreateCaseFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                        if (createCaseFragment != null) {
                            if (method.equals("references")) {
                                int position = createCaseFragment.referencesAdapter.getIds().indexOf(item.get("id").toString());

                                if (position == -1)
                                    createCaseFragment.referencesAdapter.addItem(item);
                                else
                                    createCaseFragment.referencesAdapter.removeItem(position);
                            }
                        }
                        break;
                    case R.id.createCaseUserFragment:
                        CreateCaseUserFragment createCaseUserFragment = (CreateCaseUserFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                        if (createCaseUserFragment != null) {
                            if (method.equals("references")) {
                                int position = createCaseUserFragment.referencesAdapter.getIds().indexOf(item.get("id").toString());

                                if (position == -1)
                                    createCaseUserFragment.referencesAdapter.addItem(item);
                                else
                                    createCaseUserFragment.referencesAdapter.removeItem(position);
                            }
                        }
                        break;
                }

                notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).widget(holder.binding.getRoot());
    }

    private void setData(SearchableHolder holder, Model item) {
        try {
            switch (method) {
                case "scales":
                    holder.binding.titleTextView.setText(item.get("title").toString());

                    holder.binding.subTextView.setVisibility(View.VISIBLE);
                    holder.binding.subTextView.setText(item.get("subtitle").toString());
                    break;
                case "references":
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
                case R.id.createSampleFragment:
                    CreateSampleFragment createSampleFragment = (CreateSampleFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createSampleFragment != null) {
                        if (method.equals("scales")) {
                            detector(holder, createSampleFragment.scalesAdapter.getIds().contains(item.get("id").toString()));
                        } else if (method.equals("references")) {
                            detector(holder, createSampleFragment.referencesAdapter.getIds().contains(item.get("id").toString()));
                        }
                    }
                    break;
                case R.id.createCaseFragment:
                    CreateCaseFragment createCaseFragment = (CreateCaseFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createCaseFragment != null) {
                        if (method.equals("references")) {
                            detector(holder, createCaseFragment.referencesAdapter.getIds().contains(item.get("id").toString()));
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