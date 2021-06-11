package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Fragments.Create.CreateRoomUserFragment;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
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
        if (this.items != null)
            return items.size();
        else
            return 0;
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

    public void clearItems() {
        if (this.items != null) {
            this.items.clear();
            notifyDataSetChanged();
        }
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
            Fragment fragment = ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
            if (fragment != null) {
                if (fragment instanceof CreateCaseFragment)
                    ((CreateCaseFragment) fragment).responseDialog(method, item);

                else if (fragment instanceof CreateCaseUserFragment)
                    ((CreateCaseUserFragment) fragment).responseDialog(method, item);

                else if (fragment instanceof CreateCenterFragment)
                    ((CreateCenterFragment) fragment).responseDialog(method, item);

                else if (fragment instanceof CreateCenterUserFragment)
                    ((CreateCenterUserFragment) fragment).responseDialog(method, item);

                else if (fragment instanceof CreateRoomFragment)
                    ((CreateRoomFragment) fragment).responseDialog(method, item);

                else if (fragment instanceof CreateRoomUserFragment)
                    ((CreateRoomUserFragment) fragment).responseDialog(method, item);

                else if (fragment instanceof CreateSampleFragment)
                    ((CreateSampleFragment) fragment).responseDialog(method, item);

                else if (fragment instanceof CreateScheduleFragment) {
                    Fragment childFragment = ((CreateScheduleFragment) fragment).adapter.hashMap.get(((CreateScheduleFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                    if (childFragment != null)
                        if (childFragment instanceof CreateScheduleReferenceFragment)
                            ((CreateScheduleReferenceFragment) childFragment).responseDialog(method, item);
                        else if (childFragment instanceof CreateScheduleTimeFragment)
                            ((CreateScheduleTimeFragment) childFragment).responseDialog(method, item);
                }

                else if (fragment instanceof CreateSessionFragment) {
                    Fragment childFragment = ((CreateSessionFragment) fragment).adapter.hashMap.get(((CreateSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                    if (childFragment != null)
                        if (childFragment instanceof CreateSessionTimeFragment)
                            ((CreateSessionTimeFragment) childFragment).responseDialog(method, item);
                }

                else if (fragment instanceof EditCenterFragment) {
                    Fragment childFragment = ((EditCenterFragment) fragment).adapter.hashMap.get(((EditCenterFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                    if (childFragment != null)
                        if (childFragment instanceof EditCenterDetailFragment)
                            ((EditCenterDetailFragment) childFragment).responseDialog(method, item);
                }

                else if (fragment instanceof EditSessionFragment) {
                    Fragment childFragment = ((EditSessionFragment) fragment).adapter.hashMap.get(((EditSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                    if (childFragment != null)
                        if (childFragment instanceof EditSessionTimeFragment)
                            ((EditSessionTimeFragment) childFragment).responseDialog(method, item);
                }
            }
            notifyDataSetChanged();
        }).widget(holder.binding.getRoot());
    }

    private void setData(SearchableHolder holder, TypeModel item) {
        try {
            switch (method) {
                case "scales": {
                    SampleModel model = (SampleModel) item;

                    holder.binding.titleTextView.setText(model.getSampleScaleTitle());

                    holder.binding.subTextView.setVisibility(View.VISIBLE);
                    if (!model.getSampleEdition().equals(""))
                        holder.binding.subTextView.setText(model.getSampleEdition() + " - نسخه " + model.getSampleVersion());
                    else
                        holder.binding.subTextView.setText("نسخه " + model.getSampleVersion());
                }
                break;
                case "rooms": {
                    RoomModel model = (RoomModel) item;

                    holder.binding.titleTextView.setText(model.getRoomManager().getName());

                    holder.binding.subTextView.setVisibility(View.VISIBLE);
                    holder.binding.subTextView.setText(model.getRoomCenter().getDetail().getString("title"));
                }
                break;
                case "references":
                case "psychologies":
                case "managers": {
                    UserModel model = (UserModel) item;

                    holder.binding.titleTextView.setText(model.getName());

                    holder.binding.subTextView.setVisibility(View.GONE);
                    holder.binding.subTextView.setText("");
                }
                break;
                case "cases":
                case "sessions":
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
            Fragment fragment = ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
            if (fragment != null) {
                if (fragment instanceof CreateCaseFragment)
                    switch (method) {
                        case "references": {
                            UserModel model = (UserModel) item;

                            detector(holder, ((CreateCaseFragment) fragment).referencesAdapter.getIds().contains(model.getId()));
                            calculateCount(((CreateCaseFragment) fragment).referencesAdapter.getIds().size());
                        }
                        break;
                        case "rooms": {
                            RoomModel model = (RoomModel) item;

                            detector(holder, ((CreateCaseFragment) fragment).roomId.equals(model.getRoomId()));
                        }
                        break;
                    }

                else if (fragment instanceof CreateCaseUserFragment)
                    switch (method) {
                        case "references":
                            UserModel model = (UserModel) item;

                            detector(holder, ((CreateCaseUserFragment) fragment).referencesAdapter.getIds().contains(model.getId()));
                            calculateCount(((CreateCaseUserFragment) fragment).referencesAdapter.getIds().size());
                            break;
                    }

                else if (fragment instanceof CreateCenterFragment)
                    switch (method) {
                        case "managers":
                            UserModel model = (UserModel) item;

                            detector(holder, ((CreateCenterFragment) fragment).managerId.equals(model.getId()));
                            break;
                    }

                else if (fragment instanceof CreateCenterUserFragment)
                    switch (method) {
                        case "rooms":
                            RoomModel model = (RoomModel) item;

                            detector(holder, ((CreateCenterUserFragment) fragment).roomId.equals(model.getRoomId()));
                            break;
                    }

                else if (fragment instanceof CreateRoomFragment)
                    switch (method) {
                        case "psychologies":
                            UserModel model = (UserModel) item;

                            detector(holder, ((CreateRoomFragment) fragment).psychologyId.equals(model.getId()));
                            break;
                    }

                else if (fragment instanceof CreateRoomUserFragment)
                    switch (method) {
                        case "references":
                            UserModel model = (UserModel) item;

                            detector(holder, ((CreateRoomUserFragment) fragment).referencesAdapter.getIds().contains(model.getId()));
                            calculateCount(((CreateRoomUserFragment) fragment).referencesAdapter.getIds().size());
                            break;
                    }

                else if (fragment instanceof CreateSampleFragment)
                    switch (method) {
                        case "scales": {
                            SampleModel model = (SampleModel) item;

                            detector(holder, ((CreateSampleFragment) fragment).scalesAdapter.getIds().contains(model.getSampleId()));
                            calculateCount(((CreateSampleFragment) fragment).scalesAdapter.getIds().size());
                        }
                        break;
                        case "references": {
                            UserModel model = (UserModel) item;

                            detector(holder, ((CreateSampleFragment) fragment).referencesAdapter.getIds().contains(model.getId()));
                            calculateCount(((CreateSampleFragment) fragment).referencesAdapter.getIds().size());
                        }
                        break;
                        case "rooms": {
                            RoomModel model = (RoomModel) item;

                            detector(holder, ((CreateSampleFragment) fragment).roomId.equals(model.getRoomId()));
                        }
                        break;
                        case "cases": {
                            CaseModel model = (CaseModel) item;

                            detector(holder, ((CreateSampleFragment) fragment).caseId.equals(model.getCaseId()));
                        }
                        break;
                        case "sessions": {
                            SessionModel model = (SessionModel) item;

                            detector(holder, ((CreateSampleFragment) fragment).sessionId.equals(model.getId()));
                        }
                        break;
                    }

                else if (fragment instanceof CreateScheduleFragment) {
                    Fragment childFragment = ((CreateScheduleFragment) fragment).adapter.hashMap.get(((CreateScheduleFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                    if (childFragment != null) {
                        if (method.equals("cases")) {
                            if (childFragment instanceof CreateScheduleReferenceFragment)
                                detector(holder, ((CreateScheduleReferenceFragment) childFragment).caseId.equals(item.object.get("id").toString()));
                        } else if (method.equals("patternDays")) {
                            if (childFragment instanceof CreateScheduleTimeFragment) {
                                detector(holder, ((CreateScheduleTimeFragment) childFragment).patternDaysAdapter.getIds().contains(item.object.get("id").toString()));
                                calculateCount(((CreateScheduleTimeFragment) childFragment).patternDaysAdapter.getIds().size());
                            }
                        }
                    }
                }

                else if (fragment instanceof CreateSessionFragment) {
                    Fragment childFragment = ((CreateSessionFragment) fragment).adapter.hashMap.get(((CreateSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                    if (childFragment != null)
                        if (method.equals("patternDays"))
                            if (childFragment instanceof CreateSessionTimeFragment) {
                                detector(holder, ((CreateSessionTimeFragment) childFragment).patternDaysAdapter.getIds().contains(item.object.get("id").toString()));
                                calculateCount(((CreateSessionTimeFragment) childFragment).patternDaysAdapter.getIds().size());
                            }
                }

                else if (fragment instanceof EditCenterFragment) {
                    Fragment childFragment = ((EditCenterFragment) fragment).adapter.hashMap.get(((EditCenterFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                    if (childFragment != null)
                        if (method.equals("managers"))
                            if (childFragment instanceof EditCenterDetailFragment) {
                                UserModel model = (UserModel) item;

                                detector(holder, ((EditCenterDetailFragment) childFragment).managerId.equals(model.getId()));
                            }
                }

                else if (fragment instanceof EditSessionFragment) {
                    Fragment childFragment = ((EditSessionFragment) fragment).adapter.hashMap.get(((EditSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                    if (childFragment != null)
                        if (method.equals("patternDays"))
                            if (childFragment instanceof EditSessionTimeFragment) {
                                detector(holder, ((EditSessionTimeFragment) childFragment).patternDaysAdapter.getIds().contains(item.object.get("id").toString()));
                                calculateCount(((EditSessionTimeFragment) childFragment).patternDaysAdapter.getIds().size());
                            }
                }
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