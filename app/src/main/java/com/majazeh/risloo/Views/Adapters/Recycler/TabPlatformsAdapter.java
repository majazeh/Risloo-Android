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

    private void initializer(TabPlatformsHolder holder) {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());
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

        holder.binding.roomCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
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
    }

    private void setData(TabPlatformsHolder holder, SessionPlatformModel model) {
        String title = model.getTitle() + " " + StringManager.bracing(SelectionManager.getPlatformSession(activity, "fa", model.getType())) ;

        holder.binding.titleTextView.setText(StringManager.foregroundSize(title, model.getTitle().length() + 1, title.length(), activity.getResources().getColor(R.color.Gray400), (int) activity.getResources().getDimension(R.dimen._7ssp)));
        holder.binding.identifierEditText.setText(model.getIdentifier());

        setAvailable(holder, model);

        setPinned(holder, model);
    }

    private void setAvailable(TabPlatformsHolder holder, SessionPlatformModel model) {
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

    private void setPinned(TabPlatformsHolder holder, SessionPlatformModel model) {
        if (model.isPin())
            holder.binding.roomCheckBox.setChecked(true);
        else
            holder.binding.roomCheckBox.setChecked(false);
    }

    private void setClickable(TabPlatformsHolder holder) {
        if (editable) {
            holder.binding.identifierEditText.setFocusableInTouchMode(true);
            holder.binding.roomCheckBox.setEnabled(true);

            holder.binding.identifierEditText.setAlpha((float) 1);
            holder.binding.roomCheckBox.setAlpha((float) 1);
        } else {
            holder.binding.identifierEditText.setFocusableInTouchMode(false);
            holder.binding.roomCheckBox.setEnabled(false);

            holder.binding.identifierEditText.setAlpha((float) 0.6);
            holder.binding.roomCheckBox.setAlpha((float) 0.6);
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