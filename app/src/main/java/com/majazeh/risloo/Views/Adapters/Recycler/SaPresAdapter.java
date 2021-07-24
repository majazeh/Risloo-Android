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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Show.SampleFragment;
import com.majazeh.risloo.databinding.SingleItemSaBinding;
import com.mre.ligheh.Model.TypeModel.PrerequisitesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class SaPresAdapter extends RecyclerView.Adapter<SaPresAdapter.SaPresHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> prerequisites;
    private boolean userSelect = false, editable = false;

    public SaPresAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public SaPresHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SaPresHolder(SingleItemSaBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SaPresHolder holder, int i) {
        PrerequisitesModel prerequisite = (PrerequisitesModel) prerequisites.get(i);

        listener(holder, i);

        setData(holder, prerequisite);
    }

    @Override
    public int getItemCount() {
        if (this.prerequisites != null)
            return prerequisites.size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> prerequisites) {
        if (this.prerequisites == null)
            this.prerequisites = prerequisites;
        else
            this.prerequisites.addAll(prerequisites);
        notifyDataSetChanged();
    }

    public void clearItems() {
        if (this.prerequisites != null) {
            this.prerequisites.clear();
            notifyDataSetChanged();
        }
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        notifyDataSetChanged();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(SaPresHolder holder, int item) {
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

        holder.binding.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            Fragment current = ((MainActivity) activity).fragmont.getCurrent();

            if (current instanceof SampleFragment)
                ((SampleFragment) current).sendPre(item + 1, holder.binding.inputEditText.getText().toString());
        });

        holder.binding.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    Fragment current = ((MainActivity) activity).fragmont.getCurrent();

                    if (current instanceof SampleFragment)
                        ((SampleFragment) current).sendPre(item + 1, String.valueOf(position + 1));

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(SaPresHolder holder, PrerequisitesModel model) {
        holder.binding.headerTextView.setText((holder.getBindingAdapterPosition() + 1) + " - " + model.getText());

        setType(holder, model);

        setClickable(holder);
    }

    private void setType(SaPresHolder holder, PrerequisitesModel model) {
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
                        holder.binding.selectSpinner.setSelection(holder.binding.selectSpinner.getCount());

                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setClickable(SaPresHolder holder) {
        if (editable) {
            holder.binding.inputEditText.setFocusableInTouchMode(true);
            holder.binding.selectSpinner.setEnabled(true);

            holder.binding.getRoot().setAlpha((float) 1);
        } else {
            holder.binding.inputEditText.setFocusableInTouchMode(false);
            holder.binding.selectSpinner.setEnabled(false);

            holder.binding.getRoot().setAlpha((float) 0.5);
        }
    }

    private void setSpinner(SaPresHolder holder, PrerequisitesModel model) {
        try {
            ArrayList<String> options = new ArrayList<>();

            for (int i = 0; i < model.getAnswer().getJSONArray("options").length(); i++) {
                options.add(model.getAnswer().getJSONArray("options").get(i).toString());
            }

            options.add("");

            InitManager.unfixedSpinner(activity, holder.binding.selectSpinner, options, "pre");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class SaPresHolder extends RecyclerView.ViewHolder {

        private SingleItemSaBinding binding;

        public SaPresHolder(SingleItemSaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}