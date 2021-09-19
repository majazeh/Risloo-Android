package com.majazeh.risloo.Views.Adapters.Recycler.Dialog;

import android.app.Activity;
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
import com.majazeh.risloo.Views.Adapters.Holder.Dialog.DialogSearchableHolder;
import com.majazeh.risloo.Views.Fragments.Create.CreateRoomUserFragment;
import com.majazeh.risloo.Views.Fragments.Create.ReserveScheduleFragment;
import com.majazeh.risloo.Views.Fragments.Index.CenterTagsFragment;
import com.majazeh.risloo.Views.Fragments.Index.RoomTagsFragment;
import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.ScaleModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TagModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.CreateCaseFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCaseUserFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterUserFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateRoomFragment;
import com.majazeh.risloo.Views.Fragments.Create.CreateSampleFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabReferenceFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateScheduleTabTimeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.CreateSessionTabTimeFragment;
import com.majazeh.risloo.Views.Fragments.Tab.EditCenterTabDetailFragment;
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
        current = ((MainActivity) activity).fragmont.getCurrent();
        child = ((MainActivity) activity).fragmont.getChild();
    }

    private void detector(DialogSearchableHolder holder, boolean selected) {
        if (selected)
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_gray100_border_1sdp_gray200_ripple_gray300);
        else
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
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
            if (current instanceof CreateCaseFragment) {
                switch (method) {
                    case "references": {
                        UserModel model = (UserModel) item;

                        detector(holder, ((CreateCaseFragment) current).referencesAdapter.getIds().contains(model.getId()));
                        calculateCount(((CreateCaseFragment) current).referencesAdapter.getIds().size());
                    } break;
                    case "tags": {
                        TagModel model = (TagModel) item;

                        detector(holder, ((CreateCaseFragment) current).tagsAdapter.getIds().contains(model.getId()));
                        calculateCount(((CreateCaseFragment) current).tagsAdapter.getIds().size());
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

            if (child instanceof CreateScheduleTabReferenceFragment){
                if (method.equals("cases")) {
                    CaseModel model = (CaseModel) item;

                    detector(holder, ((CreateScheduleTabReferenceFragment) child).caseId.equals(model.getCaseId()));
                }
            }

            if (child instanceof CreateScheduleTabTimeFragment) {
                if (method.equals("patternDays")) {
                    detector(holder, ((CreateScheduleTabTimeFragment) child).patternDaysAdapter.getIds().contains(item.object.get("id").toString()));
                    calculateCount(((CreateScheduleTabTimeFragment) child).patternDaysAdapter.getIds().size());
                }
            }

            if (child instanceof CreateSessionTabTimeFragment) {
                if (method.equals("patternDays")) {
                    detector(holder, ((CreateSessionTabTimeFragment) child).patternDaysAdapter.getIds().contains(item.object.get("id").toString()));
                    calculateCount(((CreateSessionTabTimeFragment) child).patternDaysAdapter.getIds().size());
                }
            }

            if (current instanceof ReserveScheduleFragment) {
                switch (method) {
                    case "references": {
                        UserModel model = (UserModel) item;

                        detector(holder, ((ReserveScheduleFragment) current).referenceId.equals(model.getId()));
                    } break;
                    case "cases": {
                        CaseModel model = (CaseModel) item;

                        detector(holder, ((ReserveScheduleFragment) current).caseId.equals(model.getCaseId()));
                    } break;
                }
            }

            if (child instanceof EditCenterTabDetailFragment) {
                if (method.equals("managers")) {
                    UserModel model = (UserModel) item;

                    detector(holder, ((EditCenterTabDetailFragment) child).managerId.equals(model.getId()));
                }
            }

            if (current instanceof CenterTagsFragment) {
                if (method.equals("tags")) {
                    TagModel model = (TagModel) item;

                    detector(holder, ((CenterTagsFragment) current).adapter.selectedHolder.binding.valueTextView.getText().equals(model.getTitle()));
                }
            }

            if (current instanceof RoomTagsFragment) {
                if (method.equals("tags")) {
                    TagModel model = (TagModel) item;

                    detector(holder, ((RoomTagsFragment) current).adapter.selectedHolder.binding.valueTextView.getText().equals(model.getTitle()));
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

        if (child instanceof CreateScheduleTabReferenceFragment)
            ((CreateScheduleTabReferenceFragment) child).responseDialog(method, item);

        if (child instanceof CreateScheduleTabTimeFragment)
            ((CreateScheduleTabTimeFragment) child).responseDialog(method, item);

        if (child instanceof CreateSessionTabTimeFragment)
            ((CreateSessionTabTimeFragment) child).responseDialog(method, item);

        if (current instanceof ReserveScheduleFragment)
            ((ReserveScheduleFragment) current).responseDialog(method, item);

        if (child instanceof EditCenterTabDetailFragment)
            ((EditCenterTabDetailFragment) child).responseDialog(method, item);

        if (current instanceof CenterTagsFragment)
            ((CenterTagsFragment) current).adapter.responseDialog(method, item);

        if (current instanceof RoomTagsFragment)
            ((RoomTagsFragment) current).adapter.responseDialog(method, item);
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