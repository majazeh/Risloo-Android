package com.majazeh.risloo.Views.Adapters.Recycler.Test;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.TestActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Test.TestPictoralHolder;
import com.majazeh.risloo.databinding.SingleItemTestPictoralBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TestPictoralAdapter extends RecyclerView.Adapter<TestPictoralHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<String> items;
    private int answer = -1, key = -1;
    private boolean userSelect = false;

    public TestPictoralAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public TestPictoralHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TestPictoralHolder(SingleItemTestPictoralBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TestPictoralHolder holder, int i) {
        String item = items.get(i);

        listener(holder, i);

        setData(holder, item, i);
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size();
        else
            return 0;
    }

    public void setItems(ArrayList<String> items, String answer, String key) {
        if (this.items == null)
            this.items = items;
        else
            this.items.addAll(items);

        if (!answer.equals(""))
            this.answer = Integer.parseInt(answer) - 1;

        if (!key.equals(""))
            this.key = Integer.parseInt(key);

        notifyDataSetChanged();
    }

    public void clearItems() {
        if (this.items != null) {
            this.items.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(TestPictoralHolder holder, boolean selected) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (selected)
                holder.itemView.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_blue600_ripple_gray300);
            else
                holder.itemView.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        } else {
            if (selected)
                holder.itemView.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_blue600);
            else
                holder.itemView.setBackgroundResource(R.drawable.draw_2sdp_solid_transparent_border_1sdp_gray200);
        }
    }

    private void listener(TestPictoralHolder holder, int position) {
        CustomClickView.onDelayedListener(() -> {
            answer = position;
            userSelect = true;
            notifyDataSetChanged();

            ((TestActivity) activity).sendItem(key, String.valueOf(answer + 1));
        }).widget(holder.itemView);
    }

    private void setData(TestPictoralHolder holder, String url, int position) {
        holder.binding.numberTextView.setText(String.valueOf(holder.getBindingAdapterPosition() + 1));
        Picasso.get().load(url).placeholder(R.color.Gray100).into(holder.binding.answerImageView);

        setActive(holder, position);

        setClickable(holder);
    }

    private void setActive(TestPictoralHolder holder, int position) {
        if (position == answer) {
            detector(holder, true);

            holder.binding.numberTextView.setTextColor(activity.getResources().getColor(R.color.White));
            holder.binding.numberTextView.setBackgroundResource(R.drawable.draw_oval_solid_blue600);
        } else {
            detector(holder, false);

            holder.binding.numberTextView.setTextColor(activity.getResources().getColor(R.color.Gray800));
            holder.binding.numberTextView.setBackgroundResource(R.drawable.draw_oval_solid_transparent_border_1sdp_gray200);

            if (userSelect)
                holder.binding.getRoot().setAlpha((float) 0.4);
        }
    }

    private void setClickable(TestPictoralHolder holder) {
        if (userSelect) {
            holder.itemView.setEnabled(false);
            holder.itemView.setClickable(false);
        } else {
            holder.itemView.setEnabled(true);
            holder.itemView.setClickable(true);
        }
    }

}