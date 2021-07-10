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
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Fragments.Create.CreateRoomUserFragment;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.ScaleModel;
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
        if (countTextView != null)
            this.countTextView = countTextView;
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
            responseDialog(item);
            notifyDataSetChanged();
        }).widget(holder.binding.getRoot());
    }

    private void setData(SearchableHolder holder, TypeModel item) {
        try {
            switch (method) {
                case "scales": {
                    ScaleModel model = (ScaleModel) item;

                    holder.binding.titleTextView.setText(model.getTitle());

                    holder.binding.subTextView.setVisibility(View.VISIBLE);
                    if (!model.getEdition().equals(""))
                        holder.binding.subTextView.setText(model.getEdition() + " - نسخه " + model.getVersion());
                    else
                        holder.binding.subTextView.setText("نسخه " + model.getVersion());
                } break;
                case "rooms": {
                    RoomModel model = (RoomModel) item;

                    holder.binding.titleTextView.setText(model.getRoomManager().getName());

                    holder.binding.subTextView.setVisibility(View.VISIBLE);
                    holder.binding.subTextView.setText(model.getRoomCenter().getDetail().getString("title"));
                } break;
                case "cases": {
                    CaseModel model = (CaseModel) item;

                    holder.binding.titleTextView.setText(model.getCaseId());

                    if (model.getClients() != null && !model.getClients().data().isEmpty()) {
                        holder.binding.subTextView.setVisibility(View.VISIBLE);

                        holder.binding.subTextView.setText("");
                        for (int i = 0; i < model.getClients().data().size(); i++) {
                            UserModel user = (UserModel) model.getClients().data().get(i);
                            if (user != null) {
                                holder.binding.subTextView.append(user.getName());
                                if (i != model.getClients().size() -1) {
                                    holder.binding.subTextView.append("  -  ");
                                }
                            }
                        }
                    } else
                        holder.binding.subTextView.setVisibility(View.GONE);

                } break;
                case "sessions": {
                    SessionModel model = (SessionModel) item;

                    String primaryText = model.getId() + " " + "(" + SelectionManager.getSessionStatus(activity, "fa", model.getStatus()) + ")";
                    String secondaryText = DateManager.jalDayName(String.valueOf(model.getStarted_at())) + " " + DateManager.jalYYYYsMMsDD(String.valueOf(model.getStarted_at()), ".") + " / " + "ساعت" + " " + DateManager.jalHHsMM(String.valueOf(model.getStarted_at())) + " / " + model.getDuration() + " " + "دقیقه";

                    holder.binding.titleTextView.setText(StringManager.foregroundSize(primaryText, 10, primaryText.length(), activity.getResources().getColor(R.color.Gray600), (int) activity.getResources().getDimension(R.dimen._8ssp)));

                    holder.binding.subTextView.setVisibility(View.VISIBLE);
                    holder.binding.subTextView.setText(secondaryText);
                } break;
                case "references":
                case "psychologies":
                case "managers": {
                    UserModel model = (UserModel) item;

                    holder.binding.titleTextView.setText(model.getName());

                    holder.binding.subTextView.setVisibility(View.GONE);
                    holder.binding.subTextView.setText("");
                } break;
                case "patternDays": {
                    holder.binding.titleTextView.setText(item.object.getString("title"));

                    holder.binding.subTextView.setVisibility(View.GONE);
                    holder.binding.subTextView.setText("");
                } break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setActive(SearchableHolder holder, TypeModel item) {
        try {
            if (getCurrent() != null) {
                if (getCurrent() instanceof CreateCaseFragment) {
                    switch (method) {
                        case "rooms": {
                            RoomModel model = (RoomModel) item;

                            detector(holder, ((CreateCaseFragment) getCurrent()).roomId.equals(model.getRoomId()));
                        } break;
                        case "references": {
                            UserModel model = (UserModel) item;

                            detector(holder, ((CreateCaseFragment) getCurrent()).referencesAdapter.getIds().contains(model.getId()));
                            calculateCount(((CreateCaseFragment) getCurrent()).referencesAdapter.getIds().size());
                        } break;
                    }

                } else if (getCurrent() instanceof CreateCaseUserFragment) {
                    if (method.equals("references")) {
                        UserModel model = (UserModel) item;

                        detector(holder, ((CreateCaseUserFragment) getCurrent()).referencesAdapter.getIds().contains(model.getId()));
                        calculateCount(((CreateCaseUserFragment) getCurrent()).referencesAdapter.getIds().size());
                    }

                } else if (getCurrent() instanceof CreateCenterFragment) {
                    if (method.equals("managers")) {
                        UserModel model = (UserModel) item;

                        detector(holder, ((CreateCenterFragment) getCurrent()).managerId.equals(model.getId()));
                    }

                } else if (getCurrent() instanceof CreateCenterUserFragment) {
                    if (method.equals("rooms")) {
                        RoomModel model = (RoomModel) item;

                        detector(holder, ((CreateCenterUserFragment) getCurrent()).roomId.equals(model.getRoomId()));
                    }

                } else if (getCurrent() instanceof CreateRoomFragment){
                    if (method.equals("psychologies")) {
                        UserModel model = (UserModel) item;

                        detector(holder, ((CreateRoomFragment) getCurrent()).psychologyId.equals(model.getId()));
                    }

                } else if (getCurrent() instanceof CreateRoomUserFragment){
                    if (method.equals("references")) {
                        UserModel model = (UserModel) item;

                        detector(holder, ((CreateRoomUserFragment) getCurrent()).referencesAdapter.getIds().contains(model.getId()));
                        calculateCount(((CreateRoomUserFragment) getCurrent()).referencesAdapter.getIds().size());
                    }

                } else if (getCurrent() instanceof CreateSampleFragment){
                    switch (method) {
                        case "scales": {
                            SampleModel model = (SampleModel) item;

                            detector(holder, ((CreateSampleFragment) getCurrent()).scalesAdapter.getIds().contains(model.getSampleId()));
                            calculateCount(((CreateSampleFragment) getCurrent()).scalesAdapter.getIds().size());
                        } break;
                        case "references": {
                            UserModel model = (UserModel) item;

                            detector(holder, ((CreateSampleFragment) getCurrent()).referencesAdapter.getIds().contains(model.getId()));
                            calculateCount(((CreateSampleFragment) getCurrent()).referencesAdapter.getIds().size());
                        } break;
                        case "rooms": {
                            RoomModel model = (RoomModel) item;

                            detector(holder, ((CreateSampleFragment) getCurrent()).roomId.equals(model.getRoomId()));
                        } break;
                        case "cases": {
                            CaseModel model = (CaseModel) item;

                            detector(holder, ((CreateSampleFragment) getCurrent()).caseId.equals(model.getCaseId()));
                        } break;
                        case "sessions": {
                            SessionModel model = (SessionModel) item;

                            detector(holder, ((CreateSampleFragment) getCurrent()).sessionId.equals(model.getId()));
                        } break;
                    }

                } else if (getCurrent() instanceof CreateScheduleReferenceFragment){
                    if (method.equals("cases")) {
                        CaseModel model = (CaseModel) item;

                        detector(holder, ((CreateScheduleReferenceFragment) getCurrent()).caseId.equals(model.getCaseId()));
                    }

                } else if (getCurrent() instanceof CreateScheduleTimeFragment) {
                    if (method.equals("patternDays")) {
                        detector(holder, ((CreateScheduleTimeFragment) getCurrent()).patternDaysAdapter.getIds().contains(item.object.get("id").toString()));
                        calculateCount(((CreateScheduleTimeFragment) getCurrent()).patternDaysAdapter.getIds().size());
                    }

                } else if (getCurrent() instanceof CreateSessionTimeFragment) {
                    if (method.equals("patternDays")) {
                        detector(holder, ((CreateSessionTimeFragment) getCurrent()).patternDaysAdapter.getIds().contains(item.object.get("id").toString()));
                        calculateCount(((CreateSessionTimeFragment) getCurrent()).patternDaysAdapter.getIds().size());
                    }

                } else if (getCurrent() instanceof EditCenterDetailFragment) {
                    if (method.equals("managers")) {
                        UserModel model = (UserModel) item;

                        detector(holder, ((EditCenterDetailFragment) getCurrent()).managerId.equals(model.getId()));
                    }

                } else if (getCurrent() instanceof EditSessionTimeFragment) {
                    if (method.equals("patternDays")) {
                        detector(holder, ((EditSessionTimeFragment) getCurrent()).patternDaysAdapter.getIds().contains(item.object.get("id").toString()));
                        calculateCount(((EditSessionTimeFragment) getCurrent()).patternDaysAdapter.getIds().size());
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void responseDialog(TypeModel item) {
        if (getCurrent() != null) {
            if (getCurrent() instanceof CreateCaseFragment)
                ((CreateCaseFragment) getCurrent()).responseDialog(method, item);

            else if (getCurrent() instanceof CreateCaseUserFragment)
                ((CreateCaseUserFragment) getCurrent()).responseDialog(method, item);

            else if (getCurrent() instanceof CreateCenterFragment)
                ((CreateCenterFragment) getCurrent()).responseDialog(method, item);

            else if (getCurrent() instanceof CreateCenterUserFragment)
                ((CreateCenterUserFragment) getCurrent()).responseDialog(method, item);

            else if (getCurrent() instanceof CreateRoomFragment)
                ((CreateRoomFragment) getCurrent()).responseDialog(method, item);

            else if (getCurrent() instanceof CreateRoomUserFragment)
                ((CreateRoomUserFragment) getCurrent()).responseDialog(method, item);

            else if (getCurrent() instanceof CreateSampleFragment)
                ((CreateSampleFragment) getCurrent()).responseDialog(method, item);

            else if (getCurrent() instanceof CreateScheduleReferenceFragment)
                ((CreateScheduleReferenceFragment) getCurrent()).responseDialog(method, item);

            else if (getCurrent() instanceof CreateScheduleTimeFragment)
                ((CreateScheduleTimeFragment) getCurrent()).responseDialog(method, item);

            else if (getCurrent() instanceof CreateSessionTimeFragment)
                ((CreateSessionTimeFragment) getCurrent()).responseDialog(method, item);

            else if (getCurrent() instanceof EditCenterDetailFragment)
                ((EditCenterDetailFragment) getCurrent()).responseDialog(method, item);

            else if (getCurrent() instanceof EditSessionTimeFragment)
                ((EditSessionTimeFragment) getCurrent()).responseDialog(method, item);
        }
    }

    private void calculateCount(int count) {
        if (count != 0) {
            countTextView.setVisibility(View.VISIBLE);
            countTextView.setText(StringManager.bracing(count));
        } else {
            countTextView.setVisibility(View.GONE);
            countTextView.setText("");
        }
    }

    private Fragment getCurrent() {
        Fragment fragment = ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (fragment != null)
            if (fragment instanceof CreateCaseFragment)
                return fragment;

            else if (fragment instanceof CreateCaseUserFragment)
                return fragment;

            else if (fragment instanceof CreateCenterFragment)
                return fragment;

            else if (fragment instanceof CreateCenterUserFragment)
                return fragment;

            else if (fragment instanceof CreateRoomFragment)
                return fragment;

            else if (fragment instanceof CreateRoomUserFragment)
                return fragment;

            else if (fragment instanceof CreateSampleFragment)
                return fragment;

            else if (fragment instanceof CreateScheduleFragment) {
                Fragment childFragment = ((CreateScheduleFragment) fragment).adapter.hashMap.get(((CreateScheduleFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof CreateScheduleReferenceFragment)
                        return childFragment;
                    else if (childFragment instanceof CreateScheduleTimeFragment)
                        return childFragment;

            } else if (fragment instanceof CreateSessionFragment) {
                Fragment childFragment = ((CreateSessionFragment) fragment).adapter.hashMap.get(((CreateSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof CreateSessionTimeFragment)
                        return childFragment;

            } else if (fragment instanceof EditCenterFragment) {
                Fragment childFragment = ((EditCenterFragment) fragment).adapter.hashMap.get(((EditCenterFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof EditCenterDetailFragment)
                        return childFragment;

            } else if (fragment instanceof EditSessionFragment) {
                Fragment childFragment = ((EditSessionFragment) fragment).adapter.hashMap.get(((EditSessionFragment) fragment).binding.viewPager.getRoot().getCurrentItem());
                if (childFragment != null)
                    if (childFragment instanceof EditSessionTimeFragment)
                        return childFragment;
            }

        return null;
    }

    public class SearchableHolder extends RecyclerView.ViewHolder {

        private SingleItemSearchableBinding binding;

        public SearchableHolder(SingleItemSearchableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}