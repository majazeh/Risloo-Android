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
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemRoomPlatformBinding;
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
//        PlatformModel platform = (PlatformModel) platforms.get(i);

        initializer(holder);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
        if (this.platforms != null)
            return platforms.size();
        else
            return 4;
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
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200_ripple_gray300);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(RoomPlatformsHolder holder) {
        ClickManager.onClickListener(() -> {
            // TODO : Place Code When Needed
        }).widget(holder.binding.containerConstraintLayout);

        holder.binding.inputEditText.setOnTouchListener((v, event) -> {
            if (editable) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    if (!holder.binding.inputEditText.hasFocus()) {
                        ((MainActivity) activity).controlEditText.select(activity, holder.binding.inputEditText);
                    }
                }
            }
            return false;
        });

        holder.binding.centerCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                doWork("1", "checkbox");
            else
                doWork("", "checkbox");
        });

        holder.binding.availableSwitchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                setEditable(true);

                holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOn));
                holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Green700));
                holder.binding.availableSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_green50_border_1sdp_gray200);

                doWork("on", "switch");
            } else {
                setEditable(false);

                holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOff));
                holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Gray600));
                holder.binding.availableSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);

                doWork("", "switch");
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
                    switch (position) {
                        case 0:
                            doWork("1", "level");
                            break;
                        case 1:
                            doWork("2", "level");
                            break;
                        case 2:
                            doWork("0", "level");
                            break;
                    }

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(RoomPlatformsHolder holder) {
        holder.binding.titleTextView.setText("تماس صوتی آنلاین");

        setClickable(holder);
    }

    private void setClickable(RoomPlatformsHolder holder) {
        if (editable) {
            holder.binding.inputEditText.setFocusableInTouchMode(true);
            holder.binding.centerCheckBox.setEnabled(true);
            holder.binding.levelSpinner.setEnabled(true);

            holder.binding.inputEditText.setAlpha((float) 1);
            holder.binding.centerCheckBox.setAlpha((float) 1);
            holder.binding.levelSpinner.setAlpha((float) 1);
            holder.binding.angleImageView.setAlpha((float) 1);
        } else {
            holder.binding.inputEditText.setFocusableInTouchMode(false);
            holder.binding.centerCheckBox.setEnabled(false);
            holder.binding.levelSpinner.setEnabled(false);

            holder.binding.inputEditText.setAlpha((float) 0.6);
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