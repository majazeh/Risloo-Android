package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemRoomPlatformBinding;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomPlatformsAdapter extends RecyclerView.Adapter<RoomPlatformsAdapter.RoomPlatformsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> platforms;
    private HashMap data, header;
    private boolean userSelect = false, editable = false;

    public RoomPlatformsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RoomPlatformsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RoomPlatformsHolder(SingleItemRoomPlatformBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RoomPlatformsHolder holder, int i) {
        SessionPlatformModel platform = (SessionPlatformModel) platforms.get(i);

        initializer(holder);

        detector(holder);

        listener(holder, platform);

        setData(holder, platform);
    }

    @Override
    public int getItemCount() {
        if (this.platforms != null)
            return platforms.size();
        else
            return 0;
    }

    public void setPlatforms(ArrayList<TypeModel> platforms) {
        if (this.platforms == null)
            this.platforms = platforms;
        else
            this.platforms.addAll(platforms);
        notifyDataSetChanged();
    }

    public void clearPlatforms() {
        if (this.platforms != null) {
            this.platforms.clear();
            notifyDataSetChanged();
        }
    }

    private void setEditable(boolean editable) {
        this.editable = editable;
        notifyDataSetChanged();
    }

    private void initializer(RoomPlatformsHolder holder) {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());

        InitManager.fixedSpinner(activity, holder.binding.levelSpinner, R.array.PlatformLevels, "adapter");
    }

    private void detector(RoomPlatformsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(RoomPlatformsHolder holder, SessionPlatformModel model) {
        ClickManager.onClickListener(() -> {
            // TODO : Place Code When Needed
        }).widget(holder.binding.containerConstraintLayout);

        holder.binding.identifierEditText.setOnTouchListener((v, event) -> {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    if (!holder.binding.identifierEditText.hasFocus()) {
                        ((MainActivity) activity).controlEditText.select(activity, holder.binding.identifierEditText);
                    }
                }
            return false;
        });

        holder.binding.identifierEditText.setOnFocusChangeListener((v, hasFocus) -> {
            String identifier = holder.binding.identifierEditText.getText().toString().trim();

            if (!identifier.equals(""))
                doWork(identifier, "editText");
        });

        holder.binding.centerCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                doWork("1", "checkbox");
            else
                doWork("", "checkbox");
        });

        holder.binding.availableSwitchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                doWork("on", "switch");

                holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOn));
                holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Green700));
                holder.binding.availableSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_green50_border_1sdp_gray200);
            } else {
                doWork("", "switch");

                holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOff));
                holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Gray600));
                holder.binding.availableSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
            }
        });

        holder.binding.levelSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String level = parent.getItemAtPosition(position).toString();

                    doWork(SelectionManager.getPlatformLevel(activity, "en", level), "level");

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(RoomPlatformsHolder holder, SessionPlatformModel model) {
        String title = model.getTitle() + " " + StringManager.bracing(SelectionManager.getPlatformSession(activity, "fa", model.getType())) ;

        holder.binding.titleTextView.setText(StringManager.foregroundSize(title, model.getTitle().length() + 1, title.length(), activity.getResources().getColor(R.color.Gray400), (int) activity.getResources().getDimension(R.dimen._7ssp)));
        holder.binding.identifierEditText.setText(model.getIdentifier());

        setAvailable(holder, model);

        setPinned(holder, model);

        setLevel(holder, model);
    }

    private void setAvailable(RoomPlatformsHolder holder, SessionPlatformModel model) {
        if (model.isAvailable()) {
            holder.binding.availableSwitchCompat.setChecked(true);

            holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOn));
            holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Green700));
            holder.binding.availableSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_green50_border_1sdp_gray200);
        } else {
            holder.binding.availableSwitchCompat.setChecked(false);

            holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOff));
            holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Gray600));
            holder.binding.availableSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);
        }
    }

    private void setPinned(RoomPlatformsHolder holder, SessionPlatformModel model) {
        if (model.isPin())
            holder.binding.centerCheckBox.setChecked(true);
        else
            holder.binding.centerCheckBox.setChecked(false);
    }

    private void setLevel(RoomPlatformsHolder holder, SessionPlatformModel model) {
        String level = SelectionManager.getPlatformLevel(activity, "fa", String.valueOf(model.getSelected_level()));
        for (int i = 0; i < holder.binding.levelSpinner.getCount(); i++) {
            if (holder.binding.levelSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(level)) {
                holder.binding.levelSpinner.setSelection(i);
            }
        }
    }

    private void setClickable(RoomPlatformsHolder holder) {
        if (editable) {
            holder.binding.identifierEditText.setFocusableInTouchMode(true);
            holder.binding.centerCheckBox.setEnabled(true);
            holder.binding.levelSpinner.setEnabled(true);

            holder.binding.identifierEditText.setAlpha((float) 1);
            holder.binding.centerCheckBox.setAlpha((float) 1);
            holder.binding.levelSpinner.setAlpha((float) 1);
            holder.binding.angleImageView.setAlpha((float) 1);
        } else {
            holder.binding.identifierEditText.setFocusableInTouchMode(false);
            holder.binding.centerCheckBox.setEnabled(false);
            holder.binding.levelSpinner.setEnabled(false);

            holder.binding.identifierEditText.setAlpha((float) 0.6);
            holder.binding.centerCheckBox.setAlpha((float) 0.6);
            holder.binding.levelSpinner.setAlpha((float) 0.6);
            holder.binding.angleImageView.setAlpha((float) 0.6);
        }
    }

    private void doWork(String value, String method) {
        // TODO : Place Code When Needed
    }

    public class RoomPlatformsHolder extends RecyclerView.ViewHolder {

        private SingleItemRoomPlatformBinding binding;

        public RoomPlatformsHolder(SingleItemRoomPlatformBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}