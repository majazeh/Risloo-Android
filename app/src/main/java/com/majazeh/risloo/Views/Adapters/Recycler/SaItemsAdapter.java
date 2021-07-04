package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.mre.ligheh.Model.TypeModel.ItemModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class SaItemsAdapter extends RecyclerView.Adapter<SaItemsAdapter.SaItemsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false, editable = false;

    public SaItemsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public SaItemsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SaItemsHolder(SingleItemSaBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SaItemsHolder holder, int i) {
        ItemModel item = (ItemModel) items.get(i);

        listener(holder, i);

        setData(holder, item);
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

    public void setEditable(boolean editable) {
        this.editable = editable;
        notifyDataSetChanged();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(SaItemsHolder holder, int item) {
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

        holder.binding.inputEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (getParent() != null) {
                getParent().sendPre(item + 1, holder.binding.inputEditText.getText().toString());
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

                    if (getParent() != null) {
                        getParent().sendPre(item + 1, String.valueOf(position + 1));
                    }

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(SaItemsHolder holder, ItemModel model) {
        holder.binding.headerTextView.setText((holder.getBindingAdapterPosition() + 1) + " - " + model.getText());

        setType(holder, model);

        setClickable(holder);
    }

    private void setType(SaItemsHolder holder, ItemModel model) {
//        switch (model.getAnswer().getType()) {
//            case "text":
//                holder.binding.selectGroup.setVisibility(View.GONE);
//
//                holder.binding.inputEditText.setVisibility(View.VISIBLE);
//                holder.binding.inputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
//
//                if (!model.getUser_answered().equals(""))
//                    holder.binding.inputEditText.setText(model.getUser_answered());
//
//                break;
//            case "number":
//                holder.binding.selectGroup.setVisibility(View.GONE);
//
//                holder.binding.inputEditText.setVisibility(View.VISIBLE);
//                holder.binding.inputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
//
//                if (!model.getUser_answered().equals(""))
//                    holder.binding.inputEditText.setText(model.getUser_answered());
//
//                break;
//            case "select":
                holder.binding.inputEditText.setVisibility(View.GONE);

                holder.binding.selectGroup.setVisibility(View.VISIBLE);
                setSpinner(holder, model);

                if (!model.getUser_answered().equals(""))
                    holder.binding.selectSpinner.setSelection(Integer.parseInt(model.getUser_answered()) - 1);
                else
                    holder.binding.selectSpinner.setSelection(0);
//
//                break;
//        }
    }

    private void setClickable(SaItemsHolder holder) {
        if (editable) {
            holder.binding.inputEditText.setFocusableInTouchMode(true);
            holder.binding.selectSpinner.setEnabled(true);
        } else {
            holder.binding.inputEditText.setFocusableInTouchMode(false);
            holder.binding.selectSpinner.setEnabled(false);
        }
    }

    private void setSpinner(SaItemsHolder holder, ItemModel model) {
        try {
            ArrayList<String> options = new ArrayList<>();

            for (int i = 0; i < model.getAnswer().getAnswer().length(); i++) {
                options.add(model.getAnswer().getAnswer().get(i).toString());
            }

            options.add("");

            InitManager.unfixedSpinner(activity, holder.binding.selectSpinner, options, "item");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private SampleFragment getParent() {
        Fragment fragment = ((MainActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (fragment != null)
            if (fragment instanceof SampleFragment)
                return (SampleFragment) fragment;

        return null;
    }

    public class SaItemsHolder extends RecyclerView.ViewHolder {

        private SingleItemSaBinding binding;

        public SaItemsHolder(SingleItemSaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}