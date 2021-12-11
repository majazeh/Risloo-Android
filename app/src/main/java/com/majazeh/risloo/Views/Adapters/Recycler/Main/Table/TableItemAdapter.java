package com.majazeh.risloo.Views.Adapters.Recycler.Main.Table;

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
import com.majazeh.risloo.Utils.Interfaces.DiffUtilTypeModelAdapter;
import com.majazeh.risloo.Utils.Interfaces.DiffUtilTypeModelCallback;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Header.HeaderFieldHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Table.TableFieldMultiHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Table.TableFieldSelectHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Table.TableFieldTextHolder;
import com.majazeh.risloo.Views.Fragments.Main.Show.SampleFragment;
import com.majazeh.risloo.databinding.HeaderItemTableFieldBinding;
import com.majazeh.risloo.databinding.SingleItemTableFieldMultiBinding;
import com.majazeh.risloo.databinding.SingleItemTableFieldSelectBinding;
import com.majazeh.risloo.databinding.SingleItemTableFieldTextBinding;
import com.mre.ligheh.Model.TypeModel.ItemModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class TableItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DiffUtilTypeModelAdapter {

    // Activity
    private final Activity activity;

    // Objects
    private final AsyncListDiffer<TypeModel> differ;

    // Fragments
    private Fragment current;

    // Vars
    private boolean userSelect = false, editable = false;

    public TableItemAdapter(@NonNull Activity activity) {
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
            ItemModel model = (ItemModel) differ.getCurrentList().get(i - 1);

            intializer();

            listener((TableFieldTextHolder) holder, i);

            setData((TableFieldTextHolder) holder, model);
        } else if (holder instanceof TableFieldMultiHolder) {
            ItemModel model = (ItemModel) differ.getCurrentList().get(i - 1);

            intializer();

            listener((TableFieldMultiHolder) holder, i);

            setData((TableFieldMultiHolder) holder, model);
        } else if (holder instanceof TableFieldSelectHolder) {
            ItemModel model = (ItemModel) differ.getCurrentList().get(i - 1);

            intializer();

            listener((TableFieldSelectHolder) holder, i);

            setData((TableFieldSelectHolder) holder, model);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;

        ItemModel model = (ItemModel) differ.getCurrentList().get(position - 1);

        switch (model.getAnswer().getType()) {
            case "text":
            case "number":
                return 1;
            case "descriptive":
                return 2;
            case "optional":
                return 3;
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
        differ.submitList(items);
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        notifyDataSetChanged();
    }

    private void intializer() {
        current = ((MainActivity) activity).fragmont.getCurrent();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TableFieldTextHolder holder, int item) {
        holder.binding.inputEditText.setOnTouchListener((v, event) -> {
            if (editable && MotionEvent.ACTION_UP == event.getAction() && !holder.binding.inputEditText.hasFocus())
                ((MainActivity) activity).inputon.select(holder.binding.inputEditText);
            return false;
        });

        holder.binding.inputEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (current instanceof SampleFragment)
                ((SampleFragment) current).sendItem(item + 1, holder.binding.inputEditText.getText().toString().trim());

            ((MainActivity) activity).inputon.clear(((MainActivity) activity).inputon.editText);
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TableFieldMultiHolder holder, int item) {
        holder.binding.inputEditText.setOnTouchListener((v, event) -> {
            if (editable && MotionEvent.ACTION_UP == event.getAction() && !holder.binding.inputEditText.hasFocus())
                ((MainActivity) activity).inputon.select(holder.binding.inputEditText);
            return false;
        });

        holder.binding.inputEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (current instanceof SampleFragment)
                ((SampleFragment) current).sendItem(item + 1, holder.binding.inputEditText.getText().toString().trim());

            ((MainActivity) activity).inputon.clear(((MainActivity) activity).inputon.editText);
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
                    if (current instanceof SampleFragment)
                        ((SampleFragment) current).sendItem(item + 1, String.valueOf(position + 1));

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(HeaderFieldHolder holder) {
        holder.binding.titleTextView.setText(activity.getResources().getString(R.string.SampleFragmentFieldItem));
        holder.binding.countTextView.setText(StringManager.bracing(itemsCount()));
    }

    private void setData(TableFieldTextHolder holder, ItemModel model) {
        String header = holder.getBindingAdapterPosition() + " - " + model.getText();
        holder.binding.headerTextView.setText(header);

        setType(holder, model);

        setClickable(holder);
    }

    private void setData(TableFieldMultiHolder holder, ItemModel model) {
        String header = holder.getBindingAdapterPosition() + " - " + model.getText();
        holder.binding.headerTextView.setText(header);

        setType(holder, model);

        setClickable(holder);
    }

    private void setData(TableFieldSelectHolder holder, ItemModel model) {
        String header = holder.getBindingAdapterPosition() + " - " + model.getText();
        holder.binding.headerTextView.setText(header);

        setType(holder, model);

        setClickable(holder);
    }

    private void setType(TableFieldTextHolder holder, ItemModel model) {
        if (model.getAnswer().getType().equals("text"))
            holder.binding.inputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        else if (model.getAnswer().getType().equals("number"))
            holder.binding.inputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

        if (!model.getUserAnswered().equals(""))
            holder.binding.inputEditText.setText(model.getUserAnswered());
    }

    private void setType(TableFieldMultiHolder holder, ItemModel model) {
        if (!model.getUserAnswered().equals(""))
            holder.binding.inputEditText.setText(model.getUserAnswered());
    }

    private void setType(TableFieldSelectHolder holder, ItemModel model) {
        setSpinner(holder, model);

        if (!model.getUserAnswered().equals(""))
            holder.binding.selectSpinner.setSelection(Integer.parseInt(model.getUserAnswered()) - 1);
        else
            holder.binding.selectSpinner.setSelection(holder.binding.selectSpinner.getCount());
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

    private void setSpinner(TableFieldSelectHolder holder, ItemModel model) {
        try {
            ArrayList<String> options = new ArrayList<>();
            for (int i = 0; i < model.getAnswer().getOptions().length(); i++) {
                options.add(model.getAnswer().getOptions().get(i).toString());
            }

            options.add("");

            InitManager.input10sspSpinner(activity, holder.binding.selectSpinner, options);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean areItemsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel) {
        ItemModel oldModel = (ItemModel) oldTypeModel;
        ItemModel newModel = (ItemModel) newTypeModel;

        return newModel.getIndex().equals(oldModel.getIndex());
    }

    @Override
    public boolean areContentsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel) {
        ItemModel oldModel = (ItemModel) oldTypeModel;
        ItemModel newModel = (ItemModel) newTypeModel;

        return newModel.compareTo(oldModel);
    }

}