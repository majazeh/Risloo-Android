package com.majazeh.risloo.Views.Adapters.Recycler.Test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.Utils.Interfaces.MyDiffUtilAdapter;
import com.majazeh.risloo.Utils.Interfaces.DiffUtilTypeModelCallback;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.TestActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Test.TestPreMultiHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Test.TestPreSelectHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Test.TestPreTextHolder;
import com.majazeh.risloo.databinding.SingleItemTestPreMultiBinding;
import com.majazeh.risloo.databinding.SingleItemTestPreSelectBinding;
import com.majazeh.risloo.databinding.SingleItemTestPreTextBinding;
import com.mre.ligheh.Model.TypeModel.PrerequisitesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class TestPrerequisiteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MyDiffUtilAdapter {

    // Activity
    private final Activity activity;

    // Objects
    private final AsyncListDiffer<TypeModel> differ;

    // Vars
    private boolean userSelect = false;

    public TestPrerequisiteAdapter(@NonNull Activity activity) {
        this.activity = activity;

        differ = new AsyncListDiffer<>(this, new DiffUtilTypeModelCallback(this));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 1)
            return new TestPreTextHolder(SingleItemTestPreTextBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
        else if (viewType == 2)
            return new TestPreMultiHolder(SingleItemTestPreMultiBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
        else if (viewType == 3)
            return new TestPreSelectHolder(SingleItemTestPreSelectBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof TestPreTextHolder) {
            PrerequisitesModel model = (PrerequisitesModel) differ.getCurrentList().get(i);

            listener((TestPreTextHolder) holder, i);

            setData((TestPreTextHolder) holder, model);
        } else if (holder instanceof TestPreMultiHolder) {
            PrerequisitesModel model = (PrerequisitesModel) differ.getCurrentList().get(i);

            listener((TestPreMultiHolder) holder, i);

            setData((TestPreMultiHolder) holder, model);
        } else if (holder instanceof TestPreSelectHolder) {
            PrerequisitesModel model = (PrerequisitesModel) differ.getCurrentList().get(i);

            listener((TestPreSelectHolder) holder, i);

            setData((TestPreSelectHolder) holder, model, i);
        }
    }

    @Override
    public int getItemViewType(int position) {
        PrerequisitesModel model = (PrerequisitesModel) differ.getCurrentList().get(position);

        try {
            switch (model.getAnswer().getString("type")) {
                case "text":
                case "number":
                    return 1;
                case "descriptive":
                    return 2;
                case "select":
                    return 3;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int getItemCount() {
        if (this.differ.getCurrentList() != null)
            return differ.getCurrentList().size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items) {
        differ.submitList(items);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TestPreTextHolder holder, int item) {
        holder.binding.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !holder.binding.inputEditText.hasFocus())
                ((TestActivity) activity).inputon.select(holder.binding.inputEditText);
            return false;
        });

        holder.binding.inputEditText.setOnEditorActionListener((v, actionId, event) -> {
            ((TestActivity) activity).sendPre(item + 1, holder.binding.inputEditText.getText().toString().trim());
            ((TestActivity) activity).inputon.clear(((TestActivity) activity).inputon.editText);
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TestPreMultiHolder holder, int item) {
        holder.binding.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !holder.binding.inputEditText.hasFocus())
                ((TestActivity) activity).inputon.select(holder.binding.inputEditText);
            return false;
        });

        holder.binding.inputEditText.setOnEditorActionListener((v, actionId, event) -> {
            ((TestActivity) activity).sendPre(item + 1, holder.binding.inputEditText.getText().toString().trim());
            ((TestActivity) activity).inputon.clear(((TestActivity) activity).inputon.editText);
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TestPreSelectHolder holder, int item) {
        holder.binding.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

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

    private void setData(TestPreTextHolder holder, PrerequisitesModel model) {
        if (!model.getText().equals(""))
            holder.binding.headerTextView.setText(model.getText());

        setType(holder, model);
    }

    private void setData(TestPreMultiHolder holder, PrerequisitesModel model) {
        if (!model.getText().equals(""))
            holder.binding.headerTextView.setText(model.getText());

        setType(holder, model);
    }

    private void setData(TestPreSelectHolder holder, PrerequisitesModel model, int position) {
        if (!model.getText().equals(""))
            holder.binding.headerTextView.setText(model.getText());

        setType(holder, model, position);
    }

    private void setType(TestPreTextHolder holder, PrerequisitesModel model) {
        try {
            if (model.getAnswer().getString("type").equals("text"))
                holder.binding.inputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
            else if (model.getAnswer().getString("type").equals("number"))
                holder.binding.inputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

            if (!model.getUser_answered().equals(""))
                holder.binding.inputEditText.setText(model.getUser_answered());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setType(TestPreMultiHolder holder, PrerequisitesModel model) {
        if (!model.getUser_answered().equals(""))
            holder.binding.inputEditText.setText(model.getUser_answered());
    }

    private void setType(TestPreSelectHolder holder, PrerequisitesModel model, int position) {
        setSpinner(holder, model);

        if (!model.getUser_answered().equals("")) {
            holder.binding.selectSpinner.setSelection(Integer.parseInt(model.getUser_answered()) - 1);
            ((TestActivity) activity).sampleAnswers.addToPrerequisites(position + 1, String.valueOf(holder.binding.selectSpinner.getSelectedItemPosition() + 1));
        } else {
            holder.binding.selectSpinner.setSelection(0);
            ((TestActivity) activity).sampleAnswers.addToPrerequisites(position + 1, String.valueOf(1));
        }
    }

    private void setSpinner(TestPreSelectHolder holder, PrerequisitesModel model) {
        try {
            ArrayList<String> options = new ArrayList<>();
            for (int i = 0; i < model.getAnswer().getJSONArray("options").length(); i++) {
                options.add(model.getAnswer().getJSONArray("options").get(i).toString());
            }

            options.add("");

            InitManager.input10sspSpinner(activity, holder.binding.selectSpinner, options);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean areItemsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel) {
        PrerequisitesModel oldModel = (PrerequisitesModel) oldTypeModel;
        PrerequisitesModel newModel = (PrerequisitesModel) newTypeModel;

        return newModel.getIndex().equals(oldModel.getIndex());
    }

    @Override
    public boolean areContentsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel) {
        PrerequisitesModel oldModel = (PrerequisitesModel) oldTypeModel;
        PrerequisitesModel newModel = (PrerequisitesModel) newTypeModel;

        return newModel.compareTo(oldModel);
    }

}