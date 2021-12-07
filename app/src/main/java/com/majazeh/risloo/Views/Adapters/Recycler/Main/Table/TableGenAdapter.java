package com.majazeh.risloo.Views.Adapters.Recycler.Main.Table;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Header.HeaderFieldHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Table.TableFieldTextHolder;
import com.majazeh.risloo.Views.Fragments.Main.Show.SampleFragment;
import com.majazeh.risloo.databinding.HeaderItemTableFieldBinding;
import com.majazeh.risloo.databinding.SingleItemTableFieldTextBinding;

import java.util.ArrayList;

public class TableGenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Instance
    private Activity activity;

    // Fragments
    private Fragment current;

    // Vars
    private ArrayList<String> items;
    private boolean editable = false;

    public TableGenAdapter(@NonNull Activity activity) {
        this.activity = activity;
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
            String item = items.get(i - 1);

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
        if (this.items != null)
            return items.size() + 1;
        else
            return 0;
    }

    public int itemsCount() {
        if (this.items != null)
            return items.size();
        else
            return 0;
    }

    public void setItems(ArrayList<String> items) {
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

    public void setEditable(boolean editable) {
        this.editable = editable;
        notifyDataSetChanged();
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

}