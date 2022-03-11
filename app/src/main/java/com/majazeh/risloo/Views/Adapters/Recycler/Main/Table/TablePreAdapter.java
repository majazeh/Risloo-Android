package com.majazeh.risloo.views.adapters.recycler.main.Table;

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
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.interfaces.DiffUtilTypeModelAdapter;
import com.majazeh.risloo.utils.interfaces.DiffUtilTypeModelCallback;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.SpinnerManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.holder.main.Header.HeaderFieldHolder;
import com.majazeh.risloo.views.adapters.holder.main.Table.TableFieldMultiHolder;
import com.majazeh.risloo.views.adapters.holder.main.Table.TableFieldSelectHolder;
import com.majazeh.risloo.views.adapters.holder.main.Table.TableFieldTextHolder;
import com.majazeh.risloo.views.fragments.main.show.FragmentSample;
import com.majazeh.risloo.databinding.HeaderItemTableFieldBinding;
import com.majazeh.risloo.databinding.SingleItemTableFieldMultiBinding;
import com.majazeh.risloo.databinding.SingleItemTableFieldSelectBinding;
import com.majazeh.risloo.databinding.SingleItemTableFieldTextBinding;
import com.mre.ligheh.Model.TypeModel.PrerequisitesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class TablePreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DiffUtilTypeModelAdapter {

    // Activity
    private final Activity activity;

    // Differ
    private final AsyncListDiffer<TypeModel> differ;

    // Fragments
    private Fragment current;

    // Vars
    private ArrayList<TypeModel> items = new ArrayList<>();
    private boolean userSelect = false, editable = false;

    public TablePreAdapter(@NonNull Activity activity) {
        this.activity = activity;

        differ = new AsyncListDiffer<>(this, new DiffUtilTypeModelCallback(this));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 1)
            return new TableFieldTextHolder(SingleItemTableFieldTextBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
        else if (viewType == 2)
            return new TableFieldMultiHolder(SingleItemTableFieldMultiBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
        else if (viewType == 3)
            return new TableFieldSelectHolder(SingleItemTableFieldSelectBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new HeaderFieldHolder(HeaderItemTableFieldBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof HeaderFieldHolder) {
            setData((HeaderFieldHolder) holder);
        } else if (holder instanceof TableFieldTextHolder) {
            PrerequisitesModel model = (PrerequisitesModel) differ.getCurrentList().get(i - 1);

            intializer();

            listener((TableFieldTextHolder) holder, i);

            setData((TableFieldTextHolder) holder, model);
        } else if (holder instanceof TableFieldMultiHolder) {
            PrerequisitesModel model = (PrerequisitesModel) differ.getCurrentList().get(i - 1);

            intializer();

            listener((TableFieldMultiHolder) holder, i);

            setData((TableFieldMultiHolder) holder, model);
        } else if (holder instanceof TableFieldSelectHolder) {
            PrerequisitesModel model = (PrerequisitesModel) differ.getCurrentList().get(i - 1);

            intializer();

            listener((TableFieldSelectHolder) holder, i);

            setData((TableFieldSelectHolder) holder, model, i);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;

        PrerequisitesModel model = (PrerequisitesModel) differ.getCurrentList().get(position - 1);

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
            return differ.getCurrentList().size() + 1;
        else
            return 0;
    }

    public int itemsCount() {
        if (this.differ.getCurrentList() != null)
            return differ.getCurrentList().size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items) {
        this.items = items;

        differ.submitList(items);
    }

    private void resetItems() {
        differ.submitList(null);
        differ.submitList(items);
    }

    private void intializer() {
        current = ((ActivityMain) activity).fragmont.getCurrent();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TableFieldTextHolder holder, int item) {
        holder.binding.inputEditText.setOnTouchListener((v, event) -> {
            if (editable && MotionEvent.ACTION_UP == event.getAction() && !holder.binding.inputEditText.hasFocus())
                ((ActivityMain) activity).inputon.select(holder.binding.inputEditText);
            return false;
        });

        holder.binding.inputEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (current instanceof FragmentSample)
                ((FragmentSample) current).sendPre(item + 1, holder.binding.inputEditText.getText().toString().trim());

            ((ActivityMain) activity).inputon.clear(((ActivityMain) activity).inputon.editText);
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TableFieldMultiHolder holder, int item) {
        holder.binding.inputEditText.setOnTouchListener((v, event) -> {
            if (editable && MotionEvent.ACTION_UP == event.getAction() && !holder.binding.inputEditText.hasFocus())
                ((ActivityMain) activity).inputon.select(holder.binding.inputEditText);
            return false;
        });

        holder.binding.inputEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (current instanceof FragmentSample)
                ((FragmentSample) current).sendPre(item + 1, holder.binding.inputEditText.getText().toString().trim());

            ((ActivityMain) activity).inputon.clear(((ActivityMain) activity).inputon.editText);
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TableFieldSelectHolder holder, int item) {
        holder.binding.selectSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.selectSpinner.setOnFocusChangeListener((v, hasFocus) -> userSelect = false);

        holder.binding.selectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    if (current instanceof FragmentSample)
                        ((FragmentSample) current).sendPre(item + 1, String.valueOf(position + 1));

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(HeaderFieldHolder holder) {
        holder.binding.titleTextView.setText(activity.getResources().getString(R.string.SampleFragmentFieldPre));
        holder.binding.countTextView.setText(StringManager.bracing(itemsCount()));
    }

    private void setData(TableFieldTextHolder holder, PrerequisitesModel model) {
        String header = holder.getBindingAdapterPosition() + " - " + model.getText();
        holder.binding.headerTextView.setText(header);

        setType(holder, model);

        setClickable(holder);
    }

    private void setData(TableFieldMultiHolder holder, PrerequisitesModel model) {
        String header = holder.getBindingAdapterPosition() + " - " + model.getText();
        holder.binding.headerTextView.setText(header);

        setType(holder, model);

        setClickable(holder);
    }

    private void setData(TableFieldSelectHolder holder, PrerequisitesModel model, int position) {
        String header = holder.getBindingAdapterPosition() + " - " + model.getText();
        holder.binding.headerTextView.setText(header);

        setType(holder, model, position);

        setClickable(holder);
    }

    private void setType(TableFieldTextHolder holder, PrerequisitesModel model) {
        try {
            if (model.getAnswer().getString("type").equals("text"))
                holder.binding.inputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
            else if (model.getAnswer().getString("type").equals("number"))
                holder.binding.inputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

            if (!model.getUserAnswered().equals(""))
                holder.binding.inputEditText.setText(model.getUserAnswered());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setType(TableFieldMultiHolder holder, PrerequisitesModel model) {
        if (!model.getUserAnswered().equals(""))
            holder.binding.inputEditText.setText(model.getUserAnswered());
    }

    private void setType(TableFieldSelectHolder holder, PrerequisitesModel model, int position) {
        setSpinner(holder, model);

        if (!model.getUserAnswered().equals("")) {
            holder.binding.selectSpinner.setSelection(Integer.parseInt(model.getUserAnswered()) - 1);

            if (current instanceof FragmentSample)
                ((FragmentSample) current).sampleAnswers.addToPrerequisites(position + 1, String.valueOf(holder.binding.selectSpinner.getSelectedItemPosition() + 1));
        } else {
            holder.binding.selectSpinner.setSelection(holder.binding.selectSpinner.getCount());

            if (current instanceof FragmentSample)
                ((FragmentSample) current).sampleAnswers.addToPrerequisites(position + 1, String.valueOf(1));
        }
    }

    private void setClickable(TableFieldTextHolder holder) {
        if (editable) {
            holder.binding.inputEditText.setFocusableInTouchMode(true);
            holder.binding.getRoot().setAlpha((float) 1);
        } else {
            holder.binding.inputEditText.setFocusableInTouchMode(false);
            holder.binding.getRoot().setAlpha((float) 0.6);
        }
    }

    private void setClickable(TableFieldMultiHolder holder) {
        if (editable) {
            holder.binding.inputEditText.setFocusableInTouchMode(true);
            holder.binding.getRoot().setAlpha((float) 1);
        } else {
            holder.binding.inputEditText.setFocusableInTouchMode(false);
            holder.binding.getRoot().setAlpha((float) 0.6);
        }
    }

    private void setClickable(TableFieldSelectHolder holder) {
        if (editable) {
            holder.binding.selectSpinner.setEnabled(true);
            holder.binding.getRoot().setAlpha((float) 1);
        } else {
            holder.binding.selectSpinner.setEnabled(false);
            holder.binding.getRoot().setAlpha((float) 0.6);
        }
    }

    private void setSpinner(TableFieldSelectHolder holder, PrerequisitesModel model) {
        try {
            ArrayList<String> options = new ArrayList<>();
            for (int i = 0; i < model.getAnswer().getJSONArray("options").length(); i++) {
                options.add(model.getAnswer().getJSONArray("options").get(i).toString());
            }

            options.add("");

            SpinnerManager.input10sspSpinner(activity, holder.binding.selectSpinner, options);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setEditable(boolean editable) {
        this.editable = editable;

        resetItems();
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