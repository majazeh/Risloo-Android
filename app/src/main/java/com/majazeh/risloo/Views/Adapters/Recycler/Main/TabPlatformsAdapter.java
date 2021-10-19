package com.majazeh.risloo.Views.Adapters.Recycler.Main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Main.TabPlatformsHolder;
import com.majazeh.risloo.databinding.SingleItemTabPlatformBinding;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class TabPlatformsAdapter extends RecyclerView.Adapter<TabPlatformsHolder> {

    // Objects
    private Activity activity;
    public HashMap platforms = new HashMap(), pinPlatform = new HashMap<>(), identifierPlatform = new HashMap<>();

    // Vars
    private ArrayList<TypeModel> items = new ArrayList<>();
    private boolean userSelect = false;

    public TabPlatformsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public TabPlatformsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TabPlatformsHolder(SingleItemTabPlatformBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TabPlatformsHolder holder, int i) {
        SessionPlatformModel model = (SessionPlatformModel) items.get(i);

        listener(holder, model);

        setData(holder, model);
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items) {
        userSelect = false;

        if (this.items == null)
            this.items = items;
        else
            this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(TypeModel item){
        if (item != null) {
            items.add(item);
            notifyDataSetChanged();
        }
    }

    public void clearItems() {
        if (this.items != null) {
            this.items.clear();
            notifyDataSetChanged();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TabPlatformsHolder holder, SessionPlatformModel model) {
        CustomClickView.onClickListener(() -> {
            // TODO : Place Code When Needed
        }).widget(holder.binding.getRoot());

        holder.binding.identifierEditText.setOnTouchListener((v, event) -> {
            if (holder.binding.selectedSwitchCompat.isChecked() && holder.binding.roomCheckBox.isChecked())
                if (MotionEvent.ACTION_UP == event.getAction() && !holder.binding.identifierEditText.hasFocus())
                    ((MainActivity) activity).inputor.select(activity, holder.binding.identifierEditText);
            return false;
        });

        holder.binding.identifierEditText.setOnFocusChangeListener((v, hasFocus) -> {
            String identifier = holder.binding.identifierEditText.getText().toString().trim();

            setIdentifier(holder, model.getId(), identifier);
        });

        holder.binding.roomCheckBox.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.roomCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (userSelect) {
                setPinned(holder, model.getId(), isChecked);

                userSelect = false;
            }
        });

        holder.binding.selectedSwitchCompat.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.selectedSwitchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (userSelect) {
                setSelected(holder, model.getId(), isChecked);

                userSelect = false;
            }
        });
    }

    private void setData(TabPlatformsHolder holder, SessionPlatformModel model) {
        String title = model.getTitle() + " " + StringManager.bracing(SelectionManager.getPlatformSession(activity, "fa", model.getType())) ;

        holder.binding.titleTextView.setText(StringManager.foregroundSize(title, model.getTitle().length() + 1, title.length(), activity.getResources().getColor(R.color.CoolGray400), (int) activity.getResources().getDimension(R.dimen._7ssp)));

        setIdentifier(holder, model.getId(), model.getIdentifier());

        setPinned(holder, model.getId(), model.isPin());

        setSelected(holder, model.getId(), model.isSelected());
    }

    private void setIdentifier(TabPlatformsHolder holder, String id, String identifier) {
        if (identifier != null) {
            identifierPlatform.put(id, identifier);
            holder.binding.identifierEditText.setText(identifier);
        } else {
            holder.binding.identifierEditText.setText("");
        }
    }

    private void setPinned(TabPlatformsHolder holder, String id , boolean isPinned) {
        if (isPinned) {
            pinPlatform.put(id, "on");
            holder.binding.roomCheckBox.setChecked(true);

            holder.binding.identifierEditText.setFocusableInTouchMode(true);
            holder.binding.identifierEditText.setAlpha((float) 1);
        } else {
            holder.binding.roomCheckBox.setChecked(false);

            holder.binding.identifierEditText.setFocusableInTouchMode(false);
            holder.binding.identifierEditText.setAlpha((float) 0.6);
        }
    }

    private void setSelected(TabPlatformsHolder holder, String id, boolean isSelected) {
        if (isSelected) {
            platforms.put(id, "on");
            holder.binding.selectedSwitchCompat.setChecked(true);

            holder.binding.selectedSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOn));
            holder.binding.selectedSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Emerald700));
            holder.binding.selectedSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_emerald50_border_1sdp_coolgray200);

            holder.binding.roomCheckBox.setEnabled(true);
            holder.binding.roomCheckBox.setAlpha((float) 1);
        } else {
            holder.binding.selectedSwitchCompat.setChecked(false);

            holder.binding.selectedSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOff));
            holder.binding.selectedSwitchCompat.setTextColor(activity.getResources().getColor(R.color.CoolGray600));
            holder.binding.selectedSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200);

            holder.binding.roomCheckBox.setEnabled(false);
            holder.binding.roomCheckBox.setAlpha((float) 0.6);

            holder.binding.identifierEditText.setFocusableInTouchMode(false);
            holder.binding.identifierEditText.setAlpha((float) 0.6);
        }
    }

}