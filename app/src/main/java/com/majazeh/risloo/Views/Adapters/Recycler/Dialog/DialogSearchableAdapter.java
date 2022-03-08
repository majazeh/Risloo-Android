package com.majazeh.risloo.views.adapters.recycler.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.DateManager;
import com.majazeh.risloo.utils.managers.SelectionManager;
import com.majazeh.risloo.utils.managers.SpannableManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.views.adapters.holder.dialog.DialogSearchableHolder;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateRoomUser;
import com.majazeh.risloo.views.fragments.main.create.FragmentReserveSchedule;
import com.majazeh.risloo.views.fragments.main.index.FragmentCenterTags;
import com.majazeh.risloo.views.fragments.main.index.FragmentRoomTags;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.ScaleModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TagModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateCase;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateCaseUser;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateCenter;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateCenterUser;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateRoom;
import com.majazeh.risloo.views.fragments.main.create.FragmentCreateSample;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabReference;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateScheduleTabTime;
import com.majazeh.risloo.views.fragments.main.tab.FragmentCreateSessionTabTime;
import com.majazeh.risloo.views.fragments.main.tab.FragmentEditCenterTabDetail;
import com.majazeh.risloo.databinding.SingleItemDialogSearchableBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;

import java.util.ArrayList;

public class DialogSearchableAdapter extends RecyclerView.Adapter<DialogSearchableHolder> {

    // Fragments
    private Fragment current, child;

    // Objects
    private Activity activity;

    // Widget
    private TextView countTextView;

    // Vars
    private ArrayList<TypeModel> items;
    private String method;

    public DialogSearchableAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public DialogSearchableHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DialogSearchableHolder(SingleItemDialogSearchableBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DialogSearchableHolder holder, int i) {
        TypeModel model = items.get(i);

        intializer();

        listener(holder, model);

        setData(holder, model);

        setActive(holder, model);
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

    public void addItem(TypeModel item) {
        if (this.items == null)
            this.items = new ArrayList<>();
        else
            this.items.add(item);

        notifyDataSetChanged();
    }

    public void clearItems() {
        if (this.items != null) {
            this.items.clear();
            notifyDataSetChanged();
        }
    }

    private void intializer() {
        current = ((ActivityMain) activity).fragmont.getCurrent();
        child = ((ActivityMain) activity).fragmont.getChild();
    }

    private void detector(DialogSearchableHolder holder, boolean selected) {
        if (selected)
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_coolgray100_border_1sdp_coolgray200_ripple_coolgray300);
        else
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_coolgray300);
    }

    private void listener(DialogSearchableHolder holder, TypeModel item) {
        CustomClickView.onDelayedListener(() -> {
            responseDialog(item);
            notifyDataSetChanged();
        }).widget(holder.binding.getRoot());
    }

    private void setData(DialogSearchableHolder holder, TypeModel item) {
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

                    holder.binding.titleTextView.setText(model.getManager().getName());

                    holder.binding.subTextView.setVisibility(View.VISIBLE);
                    holder.binding.subTextView.setText(model.getCenter().getDetail().getString("title"));
                } break;
                case "cases": {
                    CaseModel model = (CaseModel) item;

                    holder.binding.titleTextView.setText(model.getId());

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
                    String secondaryText = DateManager.jalND(String.valueOf(model.getStartedAt())) + " " + DateManager.jalYYYYsMMsDD(String.valueOf(model.getStartedAt()), ".") + " / " + "ساعت" + " " + DateManager.jalHHcMM(String.valueOf(model.getStartedAt())) + " / " + model.getDuration() + " " + "دقیقه";

                    holder.binding.titleTextView.setText(SpannableManager.foregroundColorSize(primaryText, 10, primaryText.length(), activity.getResources().getColor(R.color.coolGray600), (int) activity.getResources().getDimension(R.dimen._8ssp)));

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
                    TagModel model = (TagModel) item;

                    holder.binding.titleTextView.setText(model.getTitle());

                    holder.binding.subTextView.setVisibility(View.GONE);
                    holder.binding.subTextView.setText("");
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

    private void setActive(DialogSearchableHolder holder, TypeModel item) {
        try {
            if (current instanceof FragmentCreateCase) {
                switch (method) {
                    case "references": {
                        UserModel model = (UserModel) item;

                        detector(holder, ((FragmentCreateCase) current).referencesAdapter.getIds().contains(model.getId()));
                        calculateCount(((FragmentCreateCase) current).referencesAdapter.getIds().size());
                    } break;
                    case "tags": {
                        TagModel model = (TagModel) item;

                        detector(holder, ((FragmentCreateCase) current).tagsAdapter.getIds().contains(model.getId()));
                        calculateCount(((FragmentCreateCase) current).tagsAdapter.getIds().size());
                    } break;
                }
            }

            if (current instanceof FragmentCreateCaseUser) {
                if (method.equals("references")) {
                    UserModel model = (UserModel) item;

                    detector(holder, ((FragmentCreateCaseUser) current).referencesAdapter.getIds().contains(model.getId()));
                    calculateCount(((FragmentCreateCaseUser) current).referencesAdapter.getIds().size());
                }
            }

            if (current instanceof FragmentCreateCenter) {
                if (method.equals("managers")) {
                    UserModel model = (UserModel) item;

                    detector(holder, ((FragmentCreateCenter) current).managerId.equals(model.getId()));
                }
            }

            if (current instanceof FragmentCreateCenterUser) {
                if (method.equals("rooms")) {
                    RoomModel model = (RoomModel) item;

                    detector(holder, ((FragmentCreateCenterUser) current).centerModel.getId().equals(model.getId()));
                }
            }

            if (current instanceof FragmentCreateRoom){
                if (method.equals("psychologies")) {
                    UserModel model = (UserModel) item;

                    detector(holder, ((FragmentCreateRoom) current).psychologyId.equals(model.getId()));
                }
            }

            if (current instanceof FragmentCreateRoomUser){
                if (method.equals("references")) {
                    UserModel model = (UserModel) item;

                    detector(holder, ((FragmentCreateRoomUser) current).referencesAdapter.getIds().contains(model.getId()));
                    calculateCount(((FragmentCreateRoomUser) current).referencesAdapter.getIds().size());
                }
            }

            if (current instanceof FragmentCreateSample){
                switch (method) {
                    case "scales": {
                        ScaleModel model = (ScaleModel) item;

                        detector(holder, ((FragmentCreateSample) current).scalesAdapter.getIds().contains(model.getId()));
                        calculateCount(((FragmentCreateSample) current).scalesAdapter.getIds().size());
                    } break;
                    case "references": {
                        UserModel model = (UserModel) item;

                        detector(holder, ((FragmentCreateSample) current).referencesAdapter.getIds().contains(model.getId()));
                        calculateCount(((FragmentCreateSample) current).referencesAdapter.getIds().size());
                    } break;
                    case "rooms": {
                        RoomModel model = (RoomModel) item;

                        detector(holder, ((FragmentCreateSample) current).roomId.equals(model.getId()));
                    } break;
                    case "cases": {
                        CaseModel model = (CaseModel) item;

                        detector(holder, ((FragmentCreateSample) current).caseId.equals(model.getId()));
                    } break;
                    case "sessions": {
                        SessionModel model = (SessionModel) item;

                        detector(holder, ((FragmentCreateSample) current).sessionId.equals(model.getId()));
                    } break;
                }
            }

            if (child instanceof FragmentCreateScheduleTabReference){
                if (method.equals("cases")) {
                    CaseModel model = (CaseModel) item;

                    detector(holder, ((FragmentCreateScheduleTabReference) child).caseId.equals(model.getId()));
                }
            }

            if (child instanceof FragmentCreateScheduleTabTime) {
                if (method.equals("patternDays")) {
                    detector(holder, ((FragmentCreateScheduleTabTime) child).patternDaysAdapter.getIds().contains(item.object.get("id").toString()));
                    calculateCount(((FragmentCreateScheduleTabTime) child).patternDaysAdapter.getIds().size());
                }
            }

            if (child instanceof FragmentCreateSessionTabTime) {
                if (method.equals("patternDays")) {
                    detector(holder, ((FragmentCreateSessionTabTime) child).patternDaysAdapter.getIds().contains(item.object.get("id").toString()));
                    calculateCount(((FragmentCreateSessionTabTime) child).patternDaysAdapter.getIds().size());
                }
            }

            if (current instanceof FragmentReserveSchedule) {
                switch (method) {
                    case "references": {
                        UserModel model = (UserModel) item;

                        detector(holder, ((FragmentReserveSchedule) current).referenceId.equals(model.getId()));
                    } break;
                    case "cases": {
                        CaseModel model = (CaseModel) item;

                        detector(holder, ((FragmentReserveSchedule) current).caseId.equals(model.getId()));
                    } break;
                }
            }

            if (child instanceof FragmentEditCenterTabDetail) {
                if (method.equals("managers")) {
                    UserModel model = (UserModel) item;

                    detector(holder, ((FragmentEditCenterTabDetail) child).managerId.equals(model.getId()));
                }
            }

            if (current instanceof FragmentCenterTags) {
                if (method.equals("tags")) {
                    TagModel model = (TagModel) item;

                    detector(holder, ((FragmentCenterTags) current).adapter.selectedHolder.binding.titleTextView.getText().equals(model.getTitle()));
                }
            }

            if (current instanceof FragmentRoomTags) {
                if (method.equals("tags")) {
                    TagModel model = (TagModel) item;

                    detector(holder, ((FragmentRoomTags) current).adapter.selectedHolder.binding.titleTextView.getText().equals(model.getTitle()));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void responseDialog(TypeModel item) {
        if (current instanceof FragmentCreateCase)
            ((FragmentCreateCase) current).responseDialog(method, item);

        if (current instanceof FragmentCreateCaseUser)
            ((FragmentCreateCaseUser) current).responseDialog(method, item);

        if (current instanceof FragmentCreateCenter)
            ((FragmentCreateCenter) current).responseDialog(method, item);

        if (current instanceof FragmentCreateCenterUser)
            ((FragmentCreateCenterUser) current).responseDialog(method, item);

        if (current instanceof FragmentCreateRoom)
            ((FragmentCreateRoom) current).responseDialog(method, item);

        if (current instanceof FragmentCreateRoomUser)
            ((FragmentCreateRoomUser) current).responseDialog(method, item);

        if (current instanceof FragmentCreateSample)
            ((FragmentCreateSample) current).responseDialog(method, item);

        if (child instanceof FragmentCreateScheduleTabReference)
            ((FragmentCreateScheduleTabReference) child).responseDialog(method, item);

        if (child instanceof FragmentCreateScheduleTabTime)
            ((FragmentCreateScheduleTabTime) child).responseDialog(method, item);

        if (child instanceof FragmentCreateSessionTabTime)
            ((FragmentCreateSessionTabTime) child).responseDialog(method, item);

        if (current instanceof FragmentReserveSchedule)
            ((FragmentReserveSchedule) current).responseDialog(method, item);

        if (child instanceof FragmentEditCenterTabDetail)
            ((FragmentEditCenterTabDetail) child).responseDialog(method, item);

        if (current instanceof FragmentCenterTags)
            ((FragmentCenterTags) current).adapter.responseDialog(method, item);

        if (current instanceof FragmentRoomTags)
            ((FragmentRoomTags) current).adapter.responseDialog(method, item);
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

}