package com.majazeh.risloo.views.adapters.recycler.main.Create;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.SelectionManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.MainActivity;
import com.majazeh.risloo.views.adapters.holder.main.Create.CreatePlatformHolder;
import com.majazeh.risloo.databinding.SingleItemCreatePlatformBinding;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class CreatePlatformAdapter extends RecyclerView.Adapter<CreatePlatformHolder> {

    // Objects
    private Activity activity;
    public HashMap platforms = new HashMap<>(), pinPlatform = new HashMap<>(), identifierPlatform = new HashMap<>();

    // Vars
    private ArrayList<TypeModel> items = new ArrayList<>();
    private boolean userSelect = false;

    public CreatePlatformAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CreatePlatformHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CreatePlatformHolder(SingleItemCreatePlatformBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CreatePlatformHolder holder, int i) {
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
    private void listener(CreatePlatformHolder holder, SessionPlatformModel model) {
        CustomClickView.onClickListener(() -> {
            // TODO : Place Code When Needed
        }).widget(holder.binding.getRoot());

        holder.binding.identifierEditText.setOnTouchListener((v, event) -> {
            if (holder.binding.selectedSwitchCompat.isChecked() && holder.binding.roomCheckBox.isChecked())
                if (MotionEvent.ACTION_UP == event.getAction() && !holder.binding.identifierEditText.hasFocus())
                    ((MainActivity) activity).inputon.select(holder.binding.identifierEditText);
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

        holder.binding.selectedSwitchCompat.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.roomCheckBox.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        holder.binding.selectedSwitchCompat.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        holder.binding.roomCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (userSelect) {
                setPinned(holder, model.getId(), isChecked);

                userSelect = false;
            }
        });

        holder.binding.selectedSwitchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (userSelect) {
                setSelected(holder, model.getId(), isChecked);

                userSelect = false;
            }
        });
    }

    private void setData(CreatePlatformHolder holder, SessionPlatformModel model) {
        String title = model.getTitle() + " " + StringManager.bracing(SelectionManager.getPlatformSession(activity, "fa", model.getType())) ;
        holder.binding.titleTextView.setText(StringManager.foregroundSize(title, model.getTitle().length() + 1, title.length(), activity.getResources().getColor(R.color.coolGray400), (int) activity.getResources().getDimension(R.dimen._7ssp)));

        setIdentifier(holder, model.getId(), model.getIdentifier());

        setPinned(holder, model.getId(), model.isPin());

        setSelected(holder, model.getId(), model.isSelected());
    }

    private void setIdentifier(CreatePlatformHolder holder, String id, String identifier) {
        if (identifier != null) {
            identifierPlatform.put(id, identifier);

            holder.binding.identifierEditText.setText(identifier);
        } else {
            identifierPlatform.remove(id);

            holder.binding.identifierEditText.setText("");
        }
    }

    private void setPinned(CreatePlatformHolder holder, String id , boolean isPinned) {
        if (isPinned) {
            pinPlatform.put(id, "on");

            holder.binding.roomCheckBox.setChecked(true);

            holder.binding.identifierEditText.setFocusableInTouchMode(true);
            holder.binding.identifierEditText.setAlpha((float) 1);
        } else {
            pinPlatform.remove(id);

            holder.binding.roomCheckBox.setChecked(false);

            holder.binding.identifierEditText.setFocusableInTouchMode(false);
            holder.binding.identifierEditText.setAlpha((float) 0.6);
        }
    }

    private void setSelected(CreatePlatformHolder holder, String id, boolean isSelected) {
        if (isSelected) {
            platforms.put(id, "on");

            holder.binding.selectedSwitchCompat.setChecked(true);

            holder.binding.selectedSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOn));
            holder.binding.selectedSwitchCompat.setTextColor(activity.getResources().getColor(R.color.emerald700));

            holder.binding.roomCheckBox.setEnabled(true);
            holder.binding.roomCheckBox.setAlpha((float) 1);
        } else {
            platforms.remove(id);

            holder.binding.selectedSwitchCompat.setChecked(false);

            holder.binding.selectedSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOff));
            holder.binding.selectedSwitchCompat.setTextColor(activity.getResources().getColor(R.color.coolGray600));

            holder.binding.roomCheckBox.setEnabled(false);
            holder.binding.roomCheckBox.setAlpha((float) 0.6);

            holder.binding.identifierEditText.setFocusableInTouchMode(false);
            holder.binding.identifierEditText.setAlpha((float) 0.6);
        }
    }

}