package com.majazeh.risloo.views.adapters.recycler.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.interfaces.DiffUtilTypeModelAdapter;
import com.majazeh.risloo.utils.interfaces.DiffUtilTypeModelCallback;
import com.majazeh.risloo.utils.managers.JsonManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.holder.dialog.DialogFilterHolder;
import com.majazeh.risloo.views.fragments.main.index.FragmentCenterSchedules;
import com.majazeh.risloo.views.fragments.main.index.FragmentRoomSchedules;
import com.majazeh.risloo.databinding.SingleItemDialogFilterBinding;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class DialogFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DiffUtilTypeModelAdapter {

    // Activity
    private final Activity activity;

    // Differ
    private final AsyncListDiffer<TypeModel> differ;

    // Fragments
    private Fragment current;

    // Vars
    private String method = "";

    public DialogFilterAdapter(@NonNull Activity activity) {
        this.activity = activity;

        differ = new AsyncListDiffer<>(this, new DiffUtilTypeModelCallback(this));
    }

    @NonNull
    @Override
    public DialogFilterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DialogFilterHolder(SingleItemDialogFilterBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        TypeModel model = differ.getCurrentList().get(i);

        intializer();

        listener((DialogFilterHolder) holder, model);

        setData((DialogFilterHolder) holder, model);

        setActive((DialogFilterHolder) holder, model);
    }

    @Override
    public int getItemCount() {
        if (this.differ.getCurrentList() != null)
            return differ.getCurrentList().size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items, String method) {
        this.method = method;

        differ.submitList(items);
    }

    private void intializer() {
        current = ((ActivityMain) activity).fragmont.getCurrent();
    }

    private void detector(DialogFilterHolder holder, boolean selected) {
        if (selected)
            holder.binding.getRoot().setTextColor(activity.getResources().getColor(R.color.lightBlue700));
        else
            holder.binding.getRoot().setTextColor(activity.getResources().getColor(R.color.coolGray600));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(DialogFilterHolder holder, TypeModel item) {
        CustomClickView.onClickListener(() -> responseDialog(item)).widget(holder.binding.getRoot());
    }

    private void setData(DialogFilterHolder holder, TypeModel item) {
        try {
            switch (method) {
                case "rooms": {
                    RoomModel model = (RoomModel) item;

                    if (model.getManager() != null && !model.getManager().getName().equals(""))
                        holder.binding.getRoot().setText(model.getManager().getName());
                    else
                        holder.binding.getRoot().setText(activity.getResources().getString(R.string.DialogScheduleFilterHint) + " " + model.getId());
                } break;
                case "status": {
                    String status = item.object.get("id").toString();

                    holder.binding.getRoot().setText(JsonManager.getSessionStatus2(activity, "fa", status));
                } break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setActive(DialogFilterHolder holder, TypeModel item) {
        try {
            if (current instanceof FragmentCenterSchedules) {
                switch (method) {
                    case "rooms": {
                        RoomModel model = (RoomModel) item;

                        detector(holder, ((FragmentCenterSchedules) current).filterRoom.equals(model.getId()));
                    } break;
                    case "status": {
                        String status = item.object.get("id").toString();

                        detector(holder, ((FragmentCenterSchedules) current).filterStatus.equals(status));
                    } break;
                }
            }

            if (current instanceof FragmentRoomSchedules) {
                if (method.equals("status")) {
                    String status = item.object.get("id").toString();

                    detector(holder, ((FragmentRoomSchedules) current).filterStatus.equals(status));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void responseDialog(TypeModel item) {
        if (current instanceof FragmentCenterSchedules)
            ((FragmentCenterSchedules) current).responseDialog(method, item);

        if (current instanceof FragmentRoomSchedules)
            ((FragmentRoomSchedules) current).responseDialog(method, item);
    }

    @Override
    public boolean areItemsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel) {
        switch (method) {
            case "rooms":
                RoomModel oldModel = (RoomModel) oldTypeModel;
                RoomModel newModel = (RoomModel) newTypeModel;

                return newModel.getId().equals(oldModel.getId());

            case "status":
                try {
                    return newTypeModel.object.getString("id").equals(oldTypeModel.object.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                } return false;

        } return false;
    }

    @Override
    public boolean areContentsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel) {
        switch (method) {
            case "rooms":
                RoomModel oldModel = (RoomModel) oldTypeModel;
                RoomModel newModel = (RoomModel) newTypeModel;

                return newModel.compareTo(oldModel);

            case "status":
                try {
                    return newTypeModel.object.getString("id").equals(oldTypeModel.object.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                } return false;

        } return false;
    }

}