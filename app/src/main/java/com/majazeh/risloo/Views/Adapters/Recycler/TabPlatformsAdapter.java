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
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemTabPlatformBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class TabPlatformsAdapter extends RecyclerView.Adapter<TabPlatformsAdapter.TabPlatformsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> platforms;
    private HashMap data, header;
    private boolean editable = false;

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

    private void initializer(TabPlatformsHolder holder) {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());
    }

    private void detector(TabPlatformsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_gray50_border_1sdp_gray200_ripple_gray300);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TabPlatformsHolder holder) {
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
    }

    private void setData(TabPlatformsHolder holder) {
        holder.binding.titleTextView.setText("تماس صوتی آنلاین");

        setClickable(holder);
    }

    private void setClickable(TabPlatformsHolder holder) {
        if (editable) {
            holder.binding.inputEditText.setFocusableInTouchMode(true);
            holder.binding.centerCheckBox.setEnabled(true);

            holder.binding.inputEditText.setAlpha((float) 1);
            holder.binding.centerCheckBox.setAlpha((float) 1);
        } else {
            holder.binding.inputEditText.setFocusableInTouchMode(false);
            holder.binding.centerCheckBox.setEnabled(false);

            holder.binding.inputEditText.setAlpha((float) 0.6);
            holder.binding.centerCheckBox.setAlpha((float) 0.6);
        }
    }

    private void doWork(String value, String method) {
        // TODO : Place Code When Needed
    }

    public class TabPlatformsHolder extends RecyclerView.ViewHolder {

        private SingleItemTabPlatformBinding binding;

        public TabPlatformsHolder(SingleItemTabPlatformBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}