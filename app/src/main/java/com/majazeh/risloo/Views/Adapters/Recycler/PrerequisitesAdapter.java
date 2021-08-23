package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.TestActivity;
import com.majazeh.risloo.Views.Adapters.Holder.PrerequisitesHolder;
import com.majazeh.risloo.databinding.SingleItemPrerequisiteBinding;
import com.mre.ligheh.Model.TypeModel.PrerequisitesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class PrerequisitesAdapter extends RecyclerView.Adapter<PrerequisitesHolder> {

    // Objects
    private Activity activity;
    private Handler handler;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

    public PrerequisitesAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public PrerequisitesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PrerequisitesHolder(SingleItemPrerequisiteBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PrerequisitesHolder holder, int i) {
        PrerequisitesModel model = (PrerequisitesModel) items.get(i);

        initializer();

        listener(holder, i);

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

    private void initializer() {
        handler = new Handler();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(PrerequisitesHolder holder, int item) {
        holder.binding.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !holder.binding.inputEditText.hasFocus())
                ((TestActivity) activity).inputor.select(activity, holder.binding.inputEditText);
            return false;
        });

        holder.binding.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (holder.binding.inputEditText.hasFocus()) {
                    handler.removeCallbacksAndMessages(null);
                    handler.postDelayed(() -> ((TestActivity) activity).sendPre(item + 1, holder.binding.inputEditText.getText().toString().trim()), 750);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.binding.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    ((TestActivity) activity).sendPre(item + 1, String.valueOf(position + 1));

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(PrerequisitesHolder holder, PrerequisitesModel model) {
        holder.binding.headerTextView.setText(model.getText());

        setType(holder, model);
    }

    private void setType(PrerequisitesHolder holder, PrerequisitesModel model) {
        try {
            switch (model.getAnswer().getString("type")) {
                case "text":
                    holder.binding.selectGroup.setVisibility(View.GONE);

                    holder.binding.inputEditText.setVisibility(View.VISIBLE);
                    holder.binding.inputEditText.setInputType(InputType.TYPE_CLASS_TEXT);

                    if (!model.getUser_answered().equals(""))
                        holder.binding.inputEditText.setText(model.getUser_answered());

                    break;
                case "number":
                    holder.binding.selectGroup.setVisibility(View.GONE);

                    holder.binding.inputEditText.setVisibility(View.VISIBLE);
                    holder.binding.inputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

                    if (!model.getUser_answered().equals(""))
                        holder.binding.inputEditText.setText(model.getUser_answered());

                    break;
                case "select":
                    holder.binding.inputEditText.setVisibility(View.GONE);

                    holder.binding.selectGroup.setVisibility(View.VISIBLE);
                    setSpinner(holder, model);

                    if (!model.getUser_answered().equals(""))
                        holder.binding.selectSpinner.setSelection(Integer.parseInt(model.getUser_answered()) - 1);
                    else
                        holder.binding.selectSpinner.setSelection(0);

                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setSpinner(PrerequisitesHolder holder, PrerequisitesModel model) {
        try {
            ArrayList<String> options = new ArrayList<>();

            for (int i = 0; i < model.getAnswer().getJSONArray("options").length(); i++) {
                options.add(model.getAnswer().getJSONArray("options").get(i).toString());
            }

            options.add("");

            InitManager.normal10sspSpinner(activity, holder.binding.selectSpinner, options);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}