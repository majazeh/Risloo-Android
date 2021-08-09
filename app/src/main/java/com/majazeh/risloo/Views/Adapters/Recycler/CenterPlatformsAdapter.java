package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.NavigationMainDirections;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Managers.ToastManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Index.CenterPlatformsFragment;
import com.majazeh.risloo.databinding.SingleItemCenterPlatformBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class CenterPlatformsAdapter extends RecyclerView.Adapter<CenterPlatformsAdapter.CenterPlatformsHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

    public CenterPlatformsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CenterPlatformsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CenterPlatformsHolder(SingleItemCenterPlatformBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CenterPlatformsHolder holder, int i) {
        SessionPlatformModel model = (SessionPlatformModel) items.get(i);

        initializer(holder);

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

    private void initializer(CenterPlatformsHolder holder) {
        current = ((MainActivity) activity).fragmont.getCurrent();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());
    }

    private void detector(CenterPlatformsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);

            holder.binding.editImageView.setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(CenterPlatformsHolder holder, SessionPlatformModel model) {
        ClickManager.onClickListener(() -> {
            // TODO : Place Code When Needed
        }).widget(holder.binding.containerConstraintLayout);

        ClickManager.onClickListener(() -> {
            if (current instanceof CenterPlatformsFragment) {
                NavDirections action = NavigationMainDirections.actionGlobalEditPlatformFragment(((CenterPlatformsFragment) current).centerId, model);
                ((MainActivity) activity).navController.navigate(action);
            }
        }).widget(holder.binding.editImageView);

        holder.binding.sessionCheckBox.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.sessionCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (userSelect) {
                if (isChecked)
                    doWork(holder, model, "1", "selected");
                else
                    doWork(holder, model, "0", "selected");

                userSelect = false;
            }
        });

        holder.binding.availableSwitchCompat.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.availableSwitchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (userSelect) {
                if (isChecked)
                    doWork(holder, model, "1", "available");
                else
                    doWork(holder, model, "0", "available");

                setAvailable(holder, isChecked);

                userSelect = false;
            }
        });
    }

    private void setData(CenterPlatformsHolder holder, SessionPlatformModel model) {
        String title = model.getTitle() + " " + StringManager.bracing(SelectionManager.getPlatformSession(activity, "fa", model.getType()));

        holder.binding.titleTextView.setText(StringManager.foregroundSize(title, model.getTitle().length() + 1, title.length(), activity.getResources().getColor(R.color.Gray400), (int) activity.getResources().getDimension(R.dimen._7ssp)));

        setIdentifier(holder, model);

        setSelected(holder, model.isSelected());

        setAvailable(holder, model.isAvailable());
    }

    private void setIdentifier(CenterPlatformsHolder holder, SessionPlatformModel model) {
        if (model.getIdentifier() != null && !model.getIdentifier().equals("")) {
            holder.binding.identifierGroup.setVisibility(View.VISIBLE);

            switch (model.getIdentifier_type()) {
                case "uri":
                    holder.binding.identifierImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_link_light, null));
                    holder.binding.identifierTextView.setText(model.getIdentifier());
                    break;
                case "phone":
                    holder.binding.identifierImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_phone_light, null));
                    holder.binding.identifierTextView.setText(model.getIdentifier());
                    break;
                case "string":
                    holder.binding.identifierImageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), R.drawable.ic_clipboard_light, null));
                    holder.binding.identifierTextView.setText(model.getIdentifier());
                    break;
            }
        } else {
            holder.binding.identifierGroup.setVisibility(View.GONE);
        }
    }

    private void setSelected(CenterPlatformsHolder holder, boolean isSelected) {
        if (isSelected)
            holder.binding.sessionCheckBox.setChecked(true);
        else
            holder.binding.sessionCheckBox.setChecked(false);
    }

    private void setAvailable(CenterPlatformsHolder holder, boolean isAvailable) {
        if (isAvailable) {
            holder.binding.availableSwitchCompat.setChecked(true);

            holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOn));
            holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Green700));
            holder.binding.availableSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_green50_border_1sdp_gray200);

            holder.binding.sessionCheckBox.setEnabled(true);
            holder.binding.sessionCheckBox.setAlpha((float) 1);
        } else {
            holder.binding.availableSwitchCompat.setChecked(false);

            holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOff));
            holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Gray600));
            holder.binding.availableSwitchCompat.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200);

            holder.binding.sessionCheckBox.setEnabled(false);
            holder.binding.sessionCheckBox.setAlpha((float) 0.6);
        }
    }

    private void doWork(CenterPlatformsHolder holder, SessionPlatformModel model, String value, String method) {
        ((MainActivity) activity).loadingDialog.show(((MainActivity) activity).getSupportFragmentManager(), "loadingDialog");

        if (current instanceof CenterPlatformsFragment)
            data.put("id", ((CenterPlatformsFragment) current).centerId);

        data.put("platformId", model.getId());
        data.put(method, value);

        Center.editCenterSessionPlatform(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                activity.runOnUiThread(() -> {
                    ((MainActivity) activity).loadingDialog.dismiss();
                    ToastManager.showSuccesToast(activity, activity.getResources().getString(R.string.ToastChangesSaved));
                });
            }

            @Override
            public void onFailure(String response) {
                activity.runOnUiThread(() -> {
                    if (method.equals("available")) {
                        if (value.equals("1"))
                            setAvailable(holder, false);
                        else if (value.equals("0"))
                            setAvailable(holder, true);

                    } else if (method.equals("selected")) {
                        if (value.equals("1"))
                            setSelected(holder, false);
                        else if (value.equals("0"))
                            setSelected(holder, true);
                    }
                });
            }
        });
    }

    public class CenterPlatformsHolder extends RecyclerView.ViewHolder {

        private SingleItemCenterPlatformBinding binding;

        public CenterPlatformsHolder(SingleItemCenterPlatformBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}