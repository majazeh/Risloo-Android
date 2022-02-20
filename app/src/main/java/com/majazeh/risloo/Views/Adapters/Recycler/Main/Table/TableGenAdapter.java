package com.majazeh.risloo.Views.adapters.recycler.main.Table;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.interfaces.DiffUtilStringAdapter;
import com.majazeh.risloo.Utils.interfaces.DiffUtilStringCallback;
import com.majazeh.risloo.Utils.managers.StringManager;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.Views.adapters.holder.main.Header.HeaderFieldHolder;
import com.majazeh.risloo.Views.adapters.holder.main.Table.TableFieldTextHolder;
import com.majazeh.risloo.Views.Fragments.Main.Show.SampleFragment;
import com.majazeh.risloo.databinding.HeaderItemTableFieldBinding;
import com.majazeh.risloo.databinding.SingleItemTableFieldTextBinding;

import java.util.ArrayList;

public class TableGenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DiffUtilStringAdapter {

    // Activity
    private final Activity activity;

    // Differ
    private final AsyncListDiffer<String> differ;

    // Fragments
    private Fragment current;

    // Vars
    private ArrayList<String> items = new ArrayList<>();
    private boolean editable = false;

    public TableGenAdapter(@NonNull Activity activity) {
        this.activity = activity;

        differ = new AsyncListDiffer<>(this, new DiffUtilStringCallback(this));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderFieldHolder(HeaderItemTableFieldBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new TableFieldTextHolder(SingleItemTableFieldTextBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof HeaderFieldHolder) {
            setData((HeaderFieldHolder) holder);
        } else if (holder instanceof TableFieldTextHolder) {
            String item =differ.getCurrentList().get(i - 1);

            intializer();

            listener((TableFieldTextHolder) holder);

            setData((TableFieldTextHolder) holder, item);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;

        return 1;
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

    public void setItems(ArrayList<String> items) {
        this.items = items;

        differ.submitList(items);
    }

    private void resetItems() {
        differ.submitList(null);
        differ.submitList(items);
    }

    private void intializer() {
        current = ((MainActivity) activity).fragmont.getCurrent();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(TableFieldTextHolder holder) {
        holder.binding.inputEditText.setOnTouchListener((v, event) -> {
            if (editable && MotionEvent.ACTION_UP == event.getAction() && !holder.binding.inputEditText.hasFocus())
                ((MainActivity) activity).inputon.select(holder.binding.inputEditText);
            return false;
        });

        holder.binding.inputEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (current instanceof SampleFragment)
                ((SampleFragment) current).sendGen("cornometer", holder.binding.inputEditText.getText().toString().trim());

            ((MainActivity) activity).inputon.clear(((MainActivity) activity).inputon.editText);
            return false;
        });
    }

    private void setData(HeaderFieldHolder holder) {
        holder.binding.titleTextView.setText(activity.getResources().getString(R.string.SampleFragmentFieldGen));
        holder.binding.countTextView.setText(StringManager.bracing(itemsCount()));
    }

    private void setData(TableFieldTextHolder holder, String item) {
        holder.binding.headerTextView.setText(activity.getResources().getString(R.string.SampleFragmentFieldTime));

        setType(holder, item);

        setClickable(holder);
    }

    private void setType(TableFieldTextHolder holder, String item) {
        holder.binding.inputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

        if (!item.equals(""))
            holder.binding.inputEditText.setText(item);
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

    public void setEditable(boolean editable) {
        this.editable = editable;

        resetItems();
    }

    @Override
    public boolean areItemsTheSame(String oldString, String newString) {
        return newString.equals(oldString);
    }

    @Override
    public boolean areContentsTheSame(String oldString, String newString) {
        return newString.equals(oldString);
    }

}