package com.majazeh.risloo.Views.Adapters.Recycler.Main.Index;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Header.HeaderFieldHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Index.IndexFieldInputHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Index.IndexFieldSelectHolder;
import com.majazeh.risloo.Views.Fragments.Main.Show.SampleFragment;
import com.majazeh.risloo.databinding.HeaderItemIndexFieldBinding;
import com.majazeh.risloo.databinding.SingleItemIndexFieldInputBinding;
import com.majazeh.risloo.databinding.SingleItemIndexFieldSelectBinding;
import com.mre.ligheh.Model.TypeModel.ItemModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class IndexItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;
    private Handler handler;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false, editable = false;

    public IndexItemAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 1)
            return new IndexFieldInputHolder(SingleItemIndexFieldInputBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
        else if (viewType == 2)
            return new IndexFieldSelectHolder(SingleItemIndexFieldSelectBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new HeaderFieldHolder(HeaderItemIndexFieldBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof HeaderFieldHolder) {
            setData((HeaderFieldHolder) holder);
        } else if (holder instanceof IndexFieldInputHolder) {
            ItemModel model = (ItemModel) items.get(i - 1);

            intializer();

            listener((IndexFieldInputHolder) holder, i);

            setData((IndexFieldInputHolder) holder, model);
        } else if (holder instanceof IndexFieldSelectHolder) {
            ItemModel model = (ItemModel) items.get(i - 1);

            intializer();

            listener((IndexFieldSelectHolder) holder, i);

            setData((IndexFieldSelectHolder) holder, model);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;

        ItemModel model = (ItemModel) items.get(position - 1);

        if (model.getAnswer().getType().equals("text") || model.getAnswer().getType().equals("number"))
            return 1;
        else if (model.getAnswer().getType().equals("select"))
            return 2;

        return 0;
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

    private void intializer() {
        current = ((MainActivity) activity).fragmont.getCurrent();

        handler = new Handler();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(IndexFieldInputHolder holder, int item) {
        holder.binding.inputEditText.setOnTouchListener((v, event) -> {
            if (editable)
                if (MotionEvent.ACTION_UP == event.getAction() && !holder.binding.inputEditText.hasFocus())
                    ((MainActivity) activity).inputor.select(activity, holder.binding.inputEditText);
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
                    handler.postDelayed(() -> {
                        if (current instanceof SampleFragment)
                            ((SampleFragment) current).sendItem(item + 1, holder.binding.inputEditText.getText().toString());
                    }, 750);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(IndexFieldSelectHolder holder, int item) {
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

    private void setData(IndexFieldInputHolder holder, ItemModel model) {
        holder.binding.headerTextView.setText((holder.getBindingAdapterPosition()) + " - " + model.getText());

        setType(holder, model);

        setClickable(holder);
    }

    private void setData(IndexFieldSelectHolder holder, ItemModel model) {
        holder.binding.headerTextView.setText((holder.getBindingAdapterPosition()) + " - " + model.getText());

        setType(holder, model);

        setClickable(holder);
    }

    private void setType(IndexFieldInputHolder holder, ItemModel model) {
        switch (model.getAnswer().getType()) {
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
    }

    private void setType(IndexFieldSelectHolder holder, ItemModel model) {
        setSpinner(holder, model);

        if (!model.getUser_answered().equals(""))
            holder.binding.selectSpinner.setSelection(Integer.parseInt(model.getUser_answered()) - 1);
        else
            holder.binding.selectSpinner.setSelection(holder.binding.selectSpinner.getCount());
    }

    private void setClickable(IndexFieldInputHolder holder) {
        if (editable) {
            holder.binding.inputEditText.setFocusableInTouchMode(true);
            holder.binding.getRoot().setAlpha((float) 1);
        } else {
            holder.binding.inputEditText.setFocusableInTouchMode(false);
            holder.binding.getRoot().setAlpha((float) 0.5);
        }
    }

    private void setClickable(IndexFieldSelectHolder holder) {
        if (editable) {
            holder.binding.selectSpinner.setEnabled(true);
            holder.binding.getRoot().setAlpha((float) 1);
        } else {
            holder.binding.selectSpinner.setEnabled(false);
            holder.binding.getRoot().setAlpha((float) 0.5);
        }
    }

    private void setSpinner(IndexFieldSelectHolder holder, ItemModel model) {
        try {
            ArrayList<String> options = new ArrayList<>();
            for (int i = 0; i < model.getAnswer().getAnswer().length(); i++) {
                options.add(model.getAnswer().getAnswer().get(i).toString());
            }

            options.add("");

            InitManager.normal10sspSpinner(activity, holder.binding.selectSpinner, options);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}