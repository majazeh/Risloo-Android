package com.majazeh.risloo.Views.adapters.recycler.main.Create;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.managers.StringManager;
import com.majazeh.risloo.Views.adapters.holder.main.Create.CreateAxisHolder;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemCreateAxisBinding;

import org.json.JSONException;

import java.util.ArrayList;

public class CreateAxisAdapter extends RecyclerView.Adapter<CreateAxisHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;
    private ArrayList<String> ids = new ArrayList<>(), amounts = new ArrayList<>();

    public CreateAxisAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CreateAxisHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CreateAxisHolder(SingleItemCreateAxisBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CreateAxisHolder holder, int i) {
        TypeModel model = items.get(i);

        listener(holder, i);

        setData(holder, i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public ArrayList<String> getAmounts() {
        return amounts;
    }

    public void setItems(ArrayList<TypeModel> items, ArrayList<String> ids, ArrayList<String> amounts) {
        this.items = items;
        this.ids = ids;
        this.amounts = amounts;
        notifyDataSetChanged();
    }

    public void clearItems() {
        items.clear();
        ids.clear();
        amounts.clear();
        notifyDataSetChanged();
    }

    public void addItem(TypeModel item) {
        try {
            items.add(item);
            ids.add(item.object.get("id").toString());
            amounts.add(item.object.get("amount").toString());
            notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void replaceItem(int position, TypeModel item) {
        try {
            items.set(position, item);
            ids.set(position, item.object.get("id").toString());
            amounts.set(position, item.object.get("amount").toString());
            notifyItemChanged(position);
            notifyItemRangeChanged(position, getItemCount());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void removeItem(int position) {
        items.remove(position);
        ids.remove(position);
        amounts.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(CreateAxisHolder holder, int position) {
        holder.binding.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !holder.binding.inputEditText.hasFocus())
                ((MainActivity) activity).inputon.select(holder.binding.inputEditText);
            return false;
        });

        holder.binding.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            String value = holder.binding.inputEditText.getText().toString().trim();

            amounts.set(position, value);
        });

        holder.binding.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    String money = StringManager.separate(String.valueOf(s)) + " " + activity.getResources().getString(R.string.MainToman);
                    holder.binding.footerTextView.setText(money);
                } else {
                    String money = "0" + " " + activity.getResources().getString(R.string.MainToman);
                    holder.binding.footerTextView.setText(money);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setData(CreateAxisHolder holder, int position) {
        holder.binding.headerTextView.setText(activity.getResources().getString(R.string.AppSessionPrice) + " " + ids.get(position));
        holder.binding.inputEditText.setText(amounts.get(position));
        holder.binding.footerTextView.setText(StringManager.separate(amounts.get(position)) + " " + activity.getResources().getString(R.string.MainToman));
    }

}