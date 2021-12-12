package com.majazeh.risloo.Views.Adapters.Recycler.Main.Index;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.SelectionManager;
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Index.IndexRoomPlatformHolder;
import com.majazeh.risloo.Views.Fragments.Main.Index.RoomPlatformsFragment;
import com.majazeh.risloo.databinding.SingleItemIndexRoomPlatformBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.SessionPlatformModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class IndexRoomPlatformAdapter extends RecyclerView.Adapter<IndexRoomPlatformHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;
    private Handler handler;
    private HashMap data, header;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

    public IndexRoomPlatformAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public IndexRoomPlatformHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IndexRoomPlatformHolder(SingleItemIndexRoomPlatformBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IndexRoomPlatformHolder holder, int i) {
        SessionPlatformModel model = (SessionPlatformModel) items.get(i);

        initializer(holder);

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

    private void initializer(IndexRoomPlatformHolder holder) {
        current = ((MainActivity) activity).fragmont.getCurrent();

        handler = new Handler();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) activity).singleton.getAuthorization());

        InitManager.input8sspSpinner(activity, holder.binding.levelSpinner, R.array.PlatformLevels);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(IndexRoomPlatformHolder holder, SessionPlatformModel model) {
        CustomClickView.onClickListener(() -> {
            // TODO : Place Code When Needed
        }).widget(holder.binding.getRoot());

        holder.binding.identifierEditText.setOnTouchListener((v, event) -> {
            if (holder.binding.availableSwitchCompat.isChecked() && holder.binding.centerCheckBox.isChecked())
                if (MotionEvent.ACTION_UP == event.getAction() && !holder.binding.identifierEditText.hasFocus())
                    ((MainActivity) activity).inputon.select(holder.binding.identifierEditText);
            return false;
        });

        holder.binding.identifierEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (holder.binding.identifierEditText.hasFocus()) {
                    handler.removeCallbacksAndMessages(null);
                    handler.postDelayed(() -> {
                        if (holder.binding.availableSwitchCompat.isChecked() && holder.binding.centerCheckBox.isChecked()) {
                            String identifier = holder.binding.identifierEditText.getText().toString().trim();

                            if (!identifier.equals(""))
                                doWork(holder, model, identifier, "identifier");
                        }
                    },750);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.binding.centerCheckBox.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.availableSwitchCompat.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.levelSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.centerCheckBox.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        holder.binding.availableSwitchCompat.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        holder.binding.levelSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        holder.binding.centerCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (userSelect) {
                if (isChecked)
                    doWork(holder, model, "1", "pin");
                else
                    doWork(holder, model, "0", "pin");

                setPinned(holder, isChecked);

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

        holder.binding.levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    doWork(holder, model, SelectionManager.getPlatformLevel(activity, "en", pos), "selected_level");

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(IndexRoomPlatformHolder holder, SessionPlatformModel model) {
        String title = model.getTitle() + " " + StringManager.bracing(SelectionManager.getPlatformSession(activity, "fa", model.getType()));
        holder.binding.titleTextView.setText(StringManager.foregroundSize(title, model.getTitle().length() + 1, title.length(), activity.getResources().getColor(R.color.CoolGray400), (int) activity.getResources().getDimension(R.dimen._7ssp)));

        holder.binding.identifierEditText.setText(model.getIdentifier());

        setLevel(holder, model);

        setPinned(holder, model.isPin());

        setAvailable(holder, model.isAvailable());
    }

    private void setLevel(IndexRoomPlatformHolder holder, SessionPlatformModel model) {
        String level = SelectionManager.getPlatformLevel(activity, "fa", String.valueOf(model.getSelectedLevel()));
        for (int i = 0; i < holder.binding.levelSpinner.getCount(); i++) {
            if (holder.binding.levelSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(level)) {
                holder.binding.levelSpinner.setSelection(i);
            }
        }
    }

    private void setPinned(IndexRoomPlatformHolder holder, boolean isPinned) {
        if (isPinned) {
            holder.binding.centerCheckBox.setChecked(true);

            holder.binding.identifierEditText.setFocusableInTouchMode(true);
            holder.binding.identifierEditText.setAlpha((float) 1);
        } else {
            holder.binding.centerCheckBox.setChecked(false);

            holder.binding.identifierEditText.setFocusableInTouchMode(false);
            holder.binding.identifierEditText.setAlpha((float) 0.6);
        }
    }

    private void setAvailable(IndexRoomPlatformHolder holder, boolean isAvailable) {
        if (isAvailable) {
            holder.binding.availableSwitchCompat.setChecked(true);

            holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOn));
            holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.Emerald700));

            holder.binding.centerCheckBox.setEnabled(true);
            holder.binding.centerCheckBox.setAlpha((float) 1);

            holder.binding.levelSpinner.setEnabled(true);
            holder.binding.levelSpinner.setAlpha((float) 1);
            holder.binding.angleImageView.setAlpha((float) 1);
        } else {
            holder.binding.availableSwitchCompat.setChecked(false);

            holder.binding.availableSwitchCompat.setText(activity.getResources().getString(R.string.AppSwicthOff));
            holder.binding.availableSwitchCompat.setTextColor(activity.getResources().getColor(R.color.CoolGray600));

            holder.binding.centerCheckBox.setEnabled(false);
            holder.binding.centerCheckBox.setAlpha((float) 0.6);

            holder.binding.levelSpinner.setEnabled(false);
            holder.binding.levelSpinner.setAlpha((float) 0.6);
            holder.binding.angleImageView.setAlpha((float) 0.6);

            holder.binding.identifierEditText.setFocusableInTouchMode(false);
            holder.binding.identifierEditText.setAlpha((float) 0.6);
        }
    }

    private void setHashmap(SessionPlatformModel model, String value, String method) {
        if (current instanceof RoomPlatformsFragment)
            data.put("id", ((RoomPlatformsFragment) current).roomModel.getId());

        data.put("platformId", model.getId());
        data.put(method, value);
    }

    private void doWork(IndexRoomPlatformHolder holder, SessionPlatformModel model, String value, String method) {
        DialogManager.showLoadingDialog(activity,"");

        setHashmap(model, value, method);

        Room.editRoomSessionPlatform(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                activity.runOnUiThread(() -> {
                    DialogManager.dismissLoadingDialog();
                    SnackManager.showSuccesSnack(activity, activity.getResources().getString(R.string.SnackChangesSaved));
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

    private void resetWidget(IndexRoomPlatformHolder holder, String value, String method) {
        if (method.equals("available")) {
            if (value.equals("1"))
                setAvailable(holder, false);
            else if (value.equals("0"))
                setAvailable(holder, true);

        } else if (method.equals("pin")) {
            if (value.equals("1"))
                setPinned(holder, false);
            else if (value.equals("0"))
                setPinned(holder, true);
        }
    }

}