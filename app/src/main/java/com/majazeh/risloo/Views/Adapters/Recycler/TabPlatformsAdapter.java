package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemTabPlatformBinding;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class TabPlatformsAdapter extends RecyclerView.Adapter<TabPlatformsAdapter.TabPlatformsHolder> {

    // Objects
    private Activity activity;
    public HashMap platforms = new HashMap(), pinPlatform = new HashMap<>(), identifierPlatform = new HashMap<>();

    // Vars
    private ArrayList<TypeModel> items;
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

        detector(holder);

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

    private void detector(TabPlatformsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TabPlatformsHolder holder, SessionPlatformModel model) {
        ClickManager.onClickListener(() -> {
            // TODO : Place Code When Needed
        }).widget(holder.binding.containerConstraintLayout);

        holder.binding.identifierEditText.setOnTouchListener((v, event) -> {
            if (holder.binding.selectedSwitchCompat.isChecked() && holder.binding.roomCheckBox.isChecked())
                if (MotionEvent.ACTION_UP == event.getAction() && !holder.binding.identifierEditText.hasFocus())
                    ((MainActivity) activity).controlEditText.select(activity, holder.binding.identifierEditText);
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

        holder.binding.titleTextView.setText(StringManager.foregroundSize(title, model.getTitle().length() + 1, title.length(), activity.getResources().getColor(R.color.Gray400), (int) activity.getResources().getDimension(R.dimen._7ssp)));

        setIdentifier(holder, model.getId(), model.getIdentifier());

        setPinned(holder, model.getId(), model.isPin());

        setSelected(holder, model.getId(), model.isSelected());
    }

    private void setIdentifier(TabPlatformsHolder holder, String id, String identifier) {
        if (identifier == null) {
            identifierPlatform.put(id, "");
            holder.binding.identifierEditText.setText("");
        } else {
            identifierPlatform.put(id, identifier);
            holder.binding.identifierEditText.setText(identifier);
        }
    }

    private void setPinned(TabPlatformsHolder holder, String id , boolean isPinned) {
        if (isPinned) {
            pinPlatform.put(id, "on");
            holder.binding.roomCheckBox.setChecked(true);

            holder.binding.identifierEditText.setFocusableInTouchMode(true);
            holder.binding.identifierEditText.setAlpha((float) 1);
        } else {
            pinPlatform.put(id, "");
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
            holder.binding.selectedSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Green700));
            holder.binding.selectedSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_green50_border_1sdp_gray200);

            holder.binding.roomCheckBox.setEnabled(true);
            holder.binding.roomCheckBox.setAlpha((float) 1);
        } else {
            platforms.put(id, "");
            holder.binding.selectedSwitchCompat.setChecked(false);

            holder.binding.selectedSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOff));
            holder.binding.selectedSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Gray600));
            holder.binding.selectedSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);

            holder.binding.roomCheckBox.setEnabled(false);
            holder.binding.roomCheckBox.setAlpha((float) 0.6);

            holder.binding.identifierEditText.setFocusableInTouchMode(false);
            holder.binding.identifierEditText.setAlpha((float) 0.6);
        }
    }

    public class TabPlatformsHolder extends RecyclerView.ViewHolder {

        private SingleItemTabPlatformBinding binding;

        public TabPlatformsHolder(SingleItemTabPlatformBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}