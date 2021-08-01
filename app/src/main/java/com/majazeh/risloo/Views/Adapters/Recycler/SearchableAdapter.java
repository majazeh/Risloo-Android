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
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTimeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTimeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterTabDetailFragment;
import com.majazeh.risloo.databinding.SingleItemSearchableBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.ArrayList;

public class SearchableAdapter extends RecyclerView.Adapter<SearchableAdapter.SearchableHolder> {

    // Objects
    private Activity activity;

    // Fragments
    private Fragment current, child;

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

        intializer();

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

    private void intializer() {
        current = ((MainActivity) activity).fragmont.getCurrent();
        child = ((MainActivity) activity).fragmont.getChild();
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
                case "tags": {
                    // TODO : Place Code When Needed
                } break;
                case "patternDays": {
                    holder.binding.titleTextView.setText(item.object.getString("id"));

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
            if (current instanceof CreateCaseFragment) {
                switch (method) {
                    case "references": {
                        UserModel model = (UserModel) item;

                        detector(holder, ((CreateCaseFragment) current).referencesAdapter.getIds().contains(model.getId()));
                        calculateCount(((CreateCaseFragment) current).referencesAdapter.getIds().size());
                    } break;
                    case "tags": {
                        // TODO : Place Code When Needed
                    } break;
                }
            }

            if (current instanceof CreateCaseUserFragment) {
                if (method.equals("references")) {
                    UserModel model = (UserModel) item;

                    detector(holder, ((CreateCaseUserFragment) current).referencesAdapter.getIds().contains(model.getId()));
                    calculateCount(((CreateCaseUserFragment) current).referencesAdapter.getIds().size());
                }
            }

            if (current instanceof CreateCenterFragment) {
                if (method.equals("managers")) {
                    UserModel model = (UserModel) item;

                    detector(holder, ((CreateCenterFragment) current).managerId.equals(model.getId()));
                }
            }

            if (current instanceof CreateCenterUserFragment) {
                if (method.equals("rooms")) {
                    RoomModel model = (RoomModel) item;

                    detector(holder, ((CreateCenterUserFragment) current).roomId.equals(model.getRoomId()));
                }
            }

            if (current instanceof CreateRoomFragment){
                if (method.equals("psychologies")) {
                    UserModel model = (UserModel) item;

                    detector(holder, ((CreateRoomFragment) current).psychologyId.equals(model.getId()));
                }
            }

            if (current instanceof CreateRoomUserFragment){
                if (method.equals("references")) {
                    UserModel model = (UserModel) item;

                    detector(holder, ((CreateRoomUserFragment) current).referencesAdapter.getIds().contains(model.getId()));
                    calculateCount(((CreateRoomUserFragment) current).referencesAdapter.getIds().size());
                }
            }

            if (current instanceof CreateSampleFragment){
                switch (method) {
                    case "scales": {
                        ScaleModel model = (ScaleModel) item;

                        detector(holder, ((CreateSampleFragment) current).scalesAdapter.getIds().contains(model.getId()));
                        calculateCount(((CreateSampleFragment) current).scalesAdapter.getIds().size());
                    } break;
                    case "references": {
                        UserModel model = (UserModel) item;

                        detector(holder, ((CreateSampleFragment) current).referencesAdapter.getIds().contains(model.getId()));
                        calculateCount(((CreateSampleFragment) current).referencesAdapter.getIds().size());
                    } break;
                    case "rooms": {
                        RoomModel model = (RoomModel) item;

                        detector(holder, ((CreateSampleFragment) current).roomId.equals(model.getRoomId()));
                    } break;
                    case "cases": {
                        CaseModel model = (CaseModel) item;

                        detector(holder, ((CreateSampleFragment) current).caseId.equals(model.getCaseId()));
                    } break;
                    case "sessions": {
                        SessionModel model = (SessionModel) item;

                        detector(holder, ((CreateSampleFragment) current).sessionId.equals(model.getId()));
                    } break;
                }
            }

            if (child instanceof CreateScheduleReferenceFragment){
                if (method.equals("cases")) {
                    CaseModel model = (CaseModel) item;

                    detector(holder, ((CreateScheduleReferenceFragment) child).caseId.equals(model.getCaseId()));
                }
            }

            if (child instanceof CreateScheduleTimeFragment) {
                if (method.equals("patternDays")) {
                    detector(holder, ((CreateScheduleTimeFragment) child).patternDaysAdapter.getIds().contains(item.object.get("id").toString()));
                    calculateCount(((CreateScheduleTimeFragment) child).patternDaysAdapter.getIds().size());
                }
            }

            if (child instanceof CreateSessionTimeFragment) {
                if (method.equals("patternDays")) {
                    detector(holder, ((CreateSessionTimeFragment) child).patternDaysAdapter.getIds().contains(item.object.get("id").toString()));
                    calculateCount(((CreateSessionTimeFragment) child).patternDaysAdapter.getIds().size());
                }
            }

            if (child instanceof EditCenterTabDetailFragment) {
                if (method.equals("managers")) {
                    UserModel model = (UserModel) item;

                    detector(holder, ((EditCenterTabDetailFragment) child).managerId.equals(model.getId()));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void responseDialog(TypeModel item) {
        if (current instanceof CreateCaseFragment)
            ((CreateCaseFragment) current).responseDialog(method, item);

        if (current instanceof CreateCaseUserFragment)
            ((CreateCaseUserFragment) current).responseDialog(method, item);

        if (current instanceof CreateCenterFragment)
            ((CreateCenterFragment) current).responseDialog(method, item);

        if (current instanceof CreateCenterUserFragment)
            ((CreateCenterUserFragment) current).responseDialog(method, item);

        if (current instanceof CreateRoomFragment)
            ((CreateRoomFragment) current).responseDialog(method, item);

        if (current instanceof CreateRoomUserFragment)
            ((CreateRoomUserFragment) current).responseDialog(method, item);

        if (current instanceof CreateSampleFragment)
            ((CreateSampleFragment) current).responseDialog(method, item);

        if (child instanceof CreateScheduleReferenceFragment)
            ((CreateScheduleReferenceFragment) child).responseDialog(method, item);

        if (child instanceof CreateScheduleTimeFragment)
            ((CreateScheduleTimeFragment) child).responseDialog(method, item);

        if (child instanceof CreateSessionTimeFragment)
            ((CreateSessionTimeFragment) child).responseDialog(method, item);

        if (child instanceof EditCenterTabDetailFragment)
            ((EditCenterTabDetailFragment) child).responseDialog(method, item);
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

    public class SearchableHolder extends RecyclerView.ViewHolder {

        private SingleItemSearchableBinding binding;

        public SearchableHolder(SingleItemSearchableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}