package com.majazeh.risloo.views.adapters.recycler.main.Index;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.JsonManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.SpannableManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.holder.main.Index.IndexCenterPlatformHolder;
import com.majazeh.risloo.views.fragments.main.index.FragmentCenterPlatforms;
import com.majazeh.risloo.databinding.SingleItemIndexCenterPlatformBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class IndexCenterPlatformAdapter extends RecyclerView.Adapter<IndexCenterPlatformHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

    public IndexCenterPlatformAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public IndexCenterPlatformHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IndexCenterPlatformHolder(SingleItemIndexCenterPlatformBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IndexCenterPlatformHolder holder, int i) {
        SessionPlatformModel model = (SessionPlatformModel) items.get(i);

        initializer();

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

    public void clearItems() {
        if (this.items != null) {
            this.items.clear();
            notifyDataSetChanged();
        }
    }

    private void initializer() {
        current = ((ActivityMain) activity).fragmont.getCurrent();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) activity).singleton.getAuthorization());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(IndexCenterPlatformHolder holder, SessionPlatformModel model) {
        CustomClickView.onClickListener(() -> {
            // TODO : Place Code When Needed
        }).widget(holder.binding.getRoot());

        CustomClickView.onClickListener(() -> {
            if (current instanceof FragmentCenterPlatforms)
                ((ActivityMain) activity).navigatoon.navigateToFragmentEditPlatform(((FragmentCenterPlatforms) current).centerModel, model);

        }).widget(holder.binding.editImageView);

        holder.binding.sessionCheckBox.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.availableSwitchCompat.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.sessionCheckBox.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        holder.binding.availableSwitchCompat.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        holder.binding.sessionCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (userSelect) {
                if (isChecked)
                    doWork(holder, model, "1", "selected");
                else
                    doWork(holder, model, "0", "selected");

                userSelect = false;
            }
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

    private void setData(IndexCenterPlatformHolder holder, SessionPlatformModel model) {
        String title = model.getTitle() + " " + StringManager.bracing(JsonManager.getPlatformSession(activity, "fa", model.getType()));
        holder.binding.titleTextView.setText(SpannableManager.spanForegroundColorSize(title, model.getTitle().length() + 1, title.length(), activity.getResources().getColor(R.color.coolGray400), (int) activity.getResources().getDimension(R.dimen._7ssp)));

        setIdentifier(holder, model);

        setSelected(holder, model.isSelected());

        setAvailable(holder, model.isAvailable());
    }

    private void setIdentifier(IndexCenterPlatformHolder holder, SessionPlatformModel model) {
        if (model.getIdentifier() != null && !model.getIdentifier().equals("")) {
            holder.binding.identifierGroup.setVisibility(View.VISIBLE);

            switch (model.getIdentifierType()) {
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

    private void setSelected(IndexCenterPlatformHolder holder, boolean isSelected) {
        holder.binding.sessionCheckBox.setChecked(isSelected);
    }

    private void setAvailable(IndexCenterPlatformHolder holder, boolean isAvailable) {
        if (isAvailable) {
            holder.binding.availableSwitchCompat.setChecked(true);

            holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOn));
            holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.emerald700));

            holder.binding.sessionCheckBox.setEnabled(true);
            holder.binding.sessionCheckBox.setAlpha((float) 1);
        } else {
            holder.binding.availableSwitchCompat.setChecked(false);

            holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOff));
            holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.coolGray600));

            holder.binding.sessionCheckBox.setEnabled(false);
            holder.binding.sessionCheckBox.setAlpha((float) 0.6);
        }
    }

    private void setHashmap(SessionPlatformModel model, String value, String method) {
        if (current instanceof FragmentCenterPlatforms)
            data.put("id", ((FragmentCenterPlatforms) current).centerModel.getId());

        data.put("platformId", model.getId());
        data.put(method, value);
    }

    private void doWork(IndexCenterPlatformHolder holder, SessionPlatformModel model, String value, String method) {
        DialogManager.showDialogLoading(activity, "");

        setHashmap(model, value, method);

        Center.editCenterSessionPlatform(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                activity.runOnUiThread(() -> {
                    DialogManager.dismissDialogLoading();
                    SnackManager.showSnackSucces(activity, activity.getResources().getString(R.string.SnackChangesSaved));
                });
            }

            @Override
            public void onFailure(String response) {
                activity.runOnUiThread(() -> {
                    resetWidget(holder, value, method);
                });
            }
        });
    }

    private void resetWidget(IndexCenterPlatformHolder holder, String value, String method) {
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
    }

}