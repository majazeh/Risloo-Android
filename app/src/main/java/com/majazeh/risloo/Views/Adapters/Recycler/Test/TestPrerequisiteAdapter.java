package com.majazeh.risloo.Views.Adapters.Recycler.Test;

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
import com.majazeh.risloo.Views.Adapters.Holder.Test.TestPreInputHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Test.TestPreSelectHolder;
import com.majazeh.risloo.databinding.SingleItemTestPreInputBinding;
import com.majazeh.risloo.databinding.SingleItemTestPreSelectBinding;
import com.mre.ligheh.Model.TypeModel.PrerequisitesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class TestPrerequisiteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;
    private Handler handler;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

    public TestPrerequisiteAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 1)
            return new TestPreInputHolder(SingleItemTestPreInputBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
        else if (viewType == 2)
            return new TestPreSelectHolder(SingleItemTestPreSelectBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof TestPreInputHolder) {
            PrerequisitesModel model = (PrerequisitesModel) items.get(i);

            initializer();

            listener((TestPreInputHolder) holder, i);

            setData((TestPreInputHolder) holder, model);
        } else if (holder instanceof TestPreSelectHolder) {
            PrerequisitesModel model = (PrerequisitesModel) items.get(i);

            initializer();

            listener((TestPreSelectHolder) holder, i);

            setData((TestPreSelectHolder) holder, model, i);
        }
    }

    @Override
    public int getItemViewType(int position) {
        PrerequisitesModel model = (PrerequisitesModel) items.get(position);

        try {
            if (model.getAnswer().getString("type").equals("text") || model.getAnswer().getString("type").equals("number"))
                return 1;
            else if (model.getAnswer().getString("type").equals("select"))
                return 2;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
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
    private void listener(TestPreInputHolder holder, int item) {
        holder.binding.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !holder.binding.inputEditText.hasFocus())
                ((TestActivity) activity).inputon.select(activity, holder.binding.inputEditText);
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

    private void setData(TestPreInputHolder holder, PrerequisitesModel model) {
        if (!model.getText().equals(""))
            holder.binding.headerTextView.setText(model.getText());

        setType(holder, model);
    }

    private void setData(TestPreSelectHolder holder, PrerequisitesModel model, int position) {
        if (!model.getText().equals(""))
            holder.binding.headerTextView.setText(model.getText());

        setType(holder, model, position);
    }

    private void setType(TestPreInputHolder holder, PrerequisitesModel model) {
        try {
            switch (model.getAnswer().getString("type")) {
                case "text":
                    holder.binding.inputEditText.setInputType(InputType.TYPE_CLASS_TEXT);

                    if (!model.getUser_answered().equals(""))
                        holder.binding.inputEditText.setText(model.getUser_answered());

                    break;
                case "number":
                    holder.binding.inputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

                    if (!model.getUser_answered().equals(""))
                        holder.binding.inputEditText.setText(model.getUser_answered());

                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

}