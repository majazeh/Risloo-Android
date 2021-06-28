package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.TestActivity;
import com.majazeh.risloo.databinding.SingleItemPrerequisiteBinding;
import com.mre.ligheh.Model.TypeModel.PrerequisitesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class PrerequisitesAdapter extends RecyclerView.Adapter<PrerequisitesAdapter.PrerequisitesHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> prerequisites;
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
        PrerequisitesModel prerequisite = (PrerequisitesModel) prerequisites.get(i);

        listener(holder, prerequisite);

        setData(holder, prerequisite);
    }

    @Override
    public int getItemCount() {
        return prerequisites.size();
    }

    public void setItems(ArrayList<TypeModel> prerequisites) {
        this.prerequisites = prerequisites;
        notifyDataSetChanged();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(PrerequisitesHolder holder, PrerequisitesModel model) {
        holder.binding.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!holder.binding.inputEditText.hasFocus()) {
                    ((TestActivity) activity).controlEditText.select(activity, holder.binding.inputEditText);
                }
            }
            return false;
        });

        holder.binding.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    // Do Work Here

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
                    holder.binding.inputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

                    if (!model.getUser_answered().equals(""))
                        holder.binding.inputEditText.setText(model.getUser_answered());

                    break;
                case "number":
                    holder.binding.selectGroup.setVisibility(View.GONE);

                    holder.binding.inputEditText.setVisibility(View.VISIBLE);
                    holder.binding.inputEditText.setInputType(InputType.TYPE_CLASS_TEXT);

                    if (!model.getUser_answered().equals(""))
                        holder.binding.inputEditText.setText(model.getUser_answered());

                    break;
                case "select":
                    holder.binding.inputEditText.setVisibility(View.GONE);

                    holder.binding.selectGroup.setVisibility(View.VISIBLE);
                    setSpinner(holder, model);

                    if (!model.getUser_answered().equals("")) {
                        for (int i = 0; i < holder.binding.selectSpinner.getCount(); i++) {
                            if (holder.binding.selectSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(model.getUser_answered()))
                                holder.binding.selectSpinner.setSelection(i);
                            else
                                holder.binding.selectSpinner.setSelection(0);
                        }
                    }

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

            InitManager.unfixedSpinner(activity, holder.binding.selectSpinner, options, "prerequisite");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class PrerequisitesHolder extends RecyclerView.ViewHolder {

        private SingleItemPrerequisiteBinding binding;

        public PrerequisitesHolder(SingleItemPrerequisiteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}