package com.majazeh.risloo.Views.Adapters.Recycler.Sheet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Sheet.SheetFilterHolder;
import com.majazeh.risloo.Views.Fragments.Index.CenterSchedulesFragment;
import com.majazeh.risloo.Views.Fragments.Index.RoomSchedulesFragment;
import com.majazeh.risloo.databinding.SingleItemSheetFilterBinding;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class SheetFilterAdapter extends RecyclerView.Adapter<SheetFilterHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;
    private String method;

    public SheetFilterAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public SheetFilterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SheetFilterHolder(SingleItemSheetFilterBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SheetFilterHolder holder, int i) {
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

    public void setItems(ArrayList<TypeModel> items, String method) {
        this.method = method;

        if (this.items == null)
            this.items = items;
        else
            this.items.addAll(items);
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
    }

    private void detector(SheetFilterHolder holder, boolean selected) {
        if (selected)
            holder.binding.getRoot().setTextColor(activity.getResources().getColor(R.color.Blue700));
        else
            holder.binding.getRoot().setTextColor(activity.getResources().getColor(R.color.Gray600));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(SheetFilterHolder holder, TypeModel item) {
        CustomClickView.onClickListener(() -> {
            responseSheet(item);
        }).widget(holder.binding.getRoot());
    }

    private void setData(SheetFilterHolder holder, TypeModel item) {
        try {
            switch (method) {
                case "rooms": {
                    RoomModel model = (RoomModel) item;

                    if (model.getRoomManager() != null && !model.getRoomManager().getName().equals(""))
                        holder.binding.getRoot().setText(model.getRoomManager().getName());
                    else if (model.getRoomManager() != null)
                        holder.binding.getRoot().setText(activity.getResources().getString(R.string.BottomSheetScheduleFilterHint) + " " + model.getRoomManager().getId());
                    else
                        holder.binding.getRoot().setText(activity.getResources().getString(R.string.BottomSheetScheduleFilterHint) + " " + "نامعلوم");
                } break;
                case "status": {
                    String status = item.object.get("id").toString();

                    holder.binding.getRoot().setText(status);
                } break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setActive(SheetFilterHolder holder, TypeModel item) {
        try {
            if (current instanceof CenterSchedulesFragment) {
                switch (method) {
                    case "rooms": {
                        RoomModel model = (RoomModel) item;

                        detector(holder, ((CenterSchedulesFragment) current).filterRoom.equals(model.getRoomId()));
                    } break;
                    case "status": {
                        String status = item.object.get("id").toString();

                        detector(holder, ((CenterSchedulesFragment) current).filterStatus.equals(status));
                    } break;
                }
            }

            if (current instanceof RoomSchedulesFragment) {
                switch (method) {
                    case "status": {
                        String status = item.object.get("id").toString();

                        detector(holder, ((RoomSchedulesFragment) current).filterStatus.equals(status));
                    } break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void responseSheet(TypeModel item) {
        if (current instanceof CenterSchedulesFragment)
            ((CenterSchedulesFragment) current).responseSheet(method, item);

        if (current instanceof RoomSchedulesFragment)
            ((RoomSchedulesFragment) current).responseSheet(method, item);
    }

}