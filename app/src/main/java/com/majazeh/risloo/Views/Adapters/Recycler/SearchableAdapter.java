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
            try {
                switch (Objects.requireNonNull(((MainActivity) activity).navController.getCurrentDestination()).getId()) {
                    case R.id.createSampleFragment:
                        CreateSampleFragment createSampleFragment = (CreateSampleFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                        if (createSampleFragment != null) {
                            switch (method) {
                                case "scales": {
                                    int position = createSampleFragment.scalesAdapter.getIds().indexOf(item.get("id").toString());

                                    if (position == -1)
                                        createSampleFragment.scalesAdapter.addItem(item);
                                    else
                                        createSampleFragment.scalesAdapter.removeItem(position);
                                    break;
                                }
                                case "references": {
                                    int position = createSampleFragment.referencesAdapter.getIds().indexOf(item.get("id").toString());

                                    if (position == -1)
                                        createSampleFragment.referencesAdapter.addItem(item);
                                    else
                                        createSampleFragment.referencesAdapter.removeItem(position);
                                    break;
                                }
                                case "cases":
                                    if (!createSampleFragment.caseId.equals(item.get("id").toString())) {
                                        createSampleFragment.caseId = item.get("id").toString();
                                        createSampleFragment.caseName = item.get("title").toString();

                                        createSampleFragment.binding.caseIncludeLayout.selectTextView.setText(createSampleFragment.caseName);
                                    } else if (createSampleFragment.caseId.equals(item.get("id").toString())) {
                                        createSampleFragment.caseId = "";
                                        createSampleFragment.caseName = "";

                                        createSampleFragment.binding.caseIncludeLayout.selectTextView.setText("");
                                    }

                                    createSampleFragment.casesDialog.dismiss();
                                    break;
                                case "sessions":
                                    if (!createSampleFragment.sessionId.equals(item.get("id").toString())) {
                                        createSampleFragment.sessionId = item.get("id").toString();
                                        createSampleFragment.sessionName = item.get("title").toString();

                                        createSampleFragment.binding.sessionIncludeLayout.selectTextView.setText(createSampleFragment.sessionName);
                                    } else if (createSampleFragment.sessionId.equals(item.get("id").toString())) {
                                        createSampleFragment.sessionId = "";
                                        createSampleFragment.sessionName = "";

                                        createSampleFragment.binding.sessionIncludeLayout.selectTextView.setText("");
                                    }

                                    createSampleFragment.sessionsDialog.dismiss();
                                    break;
                                case "rooms":
                                    if (!createSampleFragment.roomId.equals(item.get("id").toString())) {
                                        createSampleFragment.roomId = item.get("id").toString();
                                        createSampleFragment.roomName = item.get("title").toString();
                                        createSampleFragment.centerName = item.get("subtitle").toString();

                                        createSampleFragment.binding.roomIncludeLayout.primaryTextView.setText(createSampleFragment.roomName);
                                        createSampleFragment.binding.roomIncludeLayout.secondaryTextView.setText(createSampleFragment.centerName);
                                    } else if (createSampleFragment.roomId.equals(item.get("id").toString())) {
                                        createSampleFragment.roomId = "";
                                        createSampleFragment.roomName = "";
                                        createSampleFragment.centerName = "";

                                        createSampleFragment.binding.roomIncludeLayout.primaryTextView.setText("");
                                        createSampleFragment.binding.roomIncludeLayout.secondaryTextView.setText("");
                                    }

                                    createSampleFragment.roomsDialog.dismiss();
                                    break;
                            }
                        }
                        break;
                    case R.id.createCaseFragment:
                        CreateCaseFragment createCaseFragment = (CreateCaseFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                        if (createCaseFragment != null) {
                            switch (method) {
                                case "references":
                                    int position = createCaseFragment.referencesAdapter.getIds().indexOf(item.get("id").toString());

                                    if (position == -1)
                                        createCaseFragment.referencesAdapter.addItem(item);
                                    else
                                        createCaseFragment.referencesAdapter.removeItem(position);
                                    break;
                                case "rooms":
                                    if (!createCaseFragment.roomId.equals(item.get("id").toString())) {
                                        createCaseFragment.roomId = item.get("id").toString();
                                        createCaseFragment.roomName = item.get("title").toString();
                                        createCaseFragment.centerName = item.get("subtitle").toString();

                                        createCaseFragment.binding.roomIncludeLayout.primaryTextView.setText(createCaseFragment.roomName);
                                        createCaseFragment.binding.roomIncludeLayout.secondaryTextView.setText(createCaseFragment.centerName);
                                    } else if (createCaseFragment.roomId.equals(item.get("id").toString())) {
                                        createCaseFragment.roomId = "";
                                        createCaseFragment.roomName = "";
                                        createCaseFragment.centerName = "";

                                        createCaseFragment.binding.roomIncludeLayout.primaryTextView.setText("");
                                        createCaseFragment.binding.roomIncludeLayout.secondaryTextView.setText("");
                                    }

                                    createCaseFragment.roomsDialog.dismiss();
                                    break;
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
                    case R.id.createCenterFragment:
                        CreateCenterFragment createCenterFragment = (CreateCenterFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                        if (createCenterFragment != null) {
                            if (method.equals("managers")) {
                                if (!createCenterFragment.managerId.equals(item.get("id").toString())) {
                                    createCenterFragment.managerId = item.get("id").toString();
                                    createCenterFragment.managerName = item.get("title").toString();

                                    createCenterFragment.binding.managerIncludeLayout.selectTextView.setText(createCenterFragment.managerName);
                                } else if (createCenterFragment.managerId.equals(item.get("id").toString())) {
                                    createCenterFragment.managerId = "";
                                    createCenterFragment.managerName = "";

                                    createCenterFragment.binding.managerIncludeLayout.selectTextView.setText("");
                                }

                                createCenterFragment.managersDialog.dismiss();
                            }
                        }
                        break;
                    case R.id.createRoomFragment:
                        CreateRoomFragment createRoomFragment = (CreateRoomFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                        if (createRoomFragment != null) {
                            if (method.equals("psychologies")) {
                                if (!createRoomFragment.psychologyId.equals(item.get("id").toString())) {
                                    createRoomFragment.psychologyId = item.get("id").toString();
                                    createRoomFragment.psychologyName = item.get("title").toString();

                                    createRoomFragment.binding.psychologyIncludeLayout.selectTextView.setText(createRoomFragment.psychologyName);
                                } else if (createRoomFragment.psychologyId.equals(item.get("id").toString())) {
                                    createRoomFragment.psychologyId = "";
                                    createRoomFragment.psychologyName = "";

                                    createRoomFragment.binding.psychologyIncludeLayout.selectTextView.setText("");
                                }

                                createRoomFragment.psychologiesDialog.dismiss();
                            }
                        }
                        break;
                    case R.id.editCenterFragment:
                        EditCenterFragment editCenterFragment = (EditCenterFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                        if (editCenterFragment != null) {
                            EditCenterDetailFragment editCenterDetailFragment = (EditCenterDetailFragment) editCenterFragment.adapter.getRegisteredFragment(0);

                            if (method.equals("managers")) {
                                if (!editCenterDetailFragment.managerId.equals(item.get("id").toString())) {
                                    editCenterDetailFragment.managerId = item.get("id").toString();
                                    editCenterDetailFragment.managerName = item.get("title").toString();

                                    editCenterDetailFragment.binding.managerIncludeLayout.selectTextView.setText(editCenterDetailFragment.managerName);
                                } else if (editCenterDetailFragment.managerId.equals(item.get("id").toString())) {
                                    editCenterDetailFragment.managerId = "";
                                    editCenterDetailFragment.managerName = "";

                                    editCenterDetailFragment.binding.managerIncludeLayout.selectTextView.setText("");
                                }

                                editCenterDetailFragment.managersDialog.dismiss();
                            }
                        }
                        break;
                    case R.id.createCenterUserFragment:
                        CreateCenterUserFragment createCenterUserFragment = (CreateCenterUserFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                        if (createCenterUserFragment != null) {
                            if (method.equals("rooms")) {
                                if (!createCenterUserFragment.roomId.equals(item.get("id").toString())) {
                                    createCenterUserFragment.roomId = item.get("id").toString();
                                    createCenterUserFragment.roomName = item.get("title").toString();
                                    createCenterUserFragment.centerName = item.get("subtitle").toString();

                                    createCenterUserFragment.binding.roomIncludeLayout.primaryTextView.setText(createCenterUserFragment.roomName);
                                    createCenterUserFragment.binding.roomIncludeLayout.secondaryTextView.setText(createCenterUserFragment.centerName);
                                } else if (createCenterUserFragment.roomId.equals(item.get("id").toString())) {
                                    createCenterUserFragment.roomId = "";
                                    createCenterUserFragment.roomName = "";
                                    createCenterUserFragment.centerName = "";

                                    createCenterUserFragment.binding.roomIncludeLayout.primaryTextView.setText("");
                                    createCenterUserFragment.binding.roomIncludeLayout.secondaryTextView.setText("");
                                }

                                createCenterUserFragment.roomsDialog.dismiss();
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
                case "rooms":
                    holder.binding.titleTextView.setText(item.get("title").toString());

                    holder.binding.subTextView.setVisibility(View.VISIBLE);
                    holder.binding.subTextView.setText(item.get("subtitle").toString());
                    break;
                case "references":
                case "managers":
                case "cases":
                case "sessions":
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
                        switch (method) {
                            case "scales":
                                detector(holder, createSampleFragment.scalesAdapter.getIds().contains(item.get("id").toString()));
                                break;
                            case "references":
                                detector(holder, createSampleFragment.referencesAdapter.getIds().contains(item.get("id").toString()));
                                break;
                            case "cases":
                                detector(holder, createSampleFragment.caseId.equals(item.get("id").toString()));
                                break;
                            case "sessions":
                                detector(holder, createSampleFragment.sessionId.equals(item.get("id").toString()));
                                break;
                            case "rooms":
                                detector(holder, createSampleFragment.roomId.equals(item.get("id").toString()));
                                break;
                        }
                    }
                    break;
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
                case R.id.createRoomFragment:
                    CreateRoomFragment createRoomFragment = (CreateRoomFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createRoomFragment != null) {
                        if (method.equals("psychologies")) {
                            detector(holder, createRoomFragment.psychologyId.equals(item.get("id").toString()));
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
                case R.id.createCenterUserFragment:
                    CreateCenterUserFragment createCenterUserFragment = (CreateCenterUserFragment) ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (createCenterUserFragment != null) {
                        if (method.equals("rooms")) {
                            detector(holder, createCenterUserFragment.roomId.equals(item.get("id").toString()));
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