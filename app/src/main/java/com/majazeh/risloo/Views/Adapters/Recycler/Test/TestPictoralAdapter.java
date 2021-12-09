package com.majazeh.risloo.Views.Adapters.Recycler.Test;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Interfaces.MyDiffUtilAdapter2;
import com.majazeh.risloo.Utils.Interfaces.DiffUtilStringCallback;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.TestActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Test.TestPictoralHolder;
import com.majazeh.risloo.databinding.SingleItemTestPictoralBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TestPictoralAdapter extends RecyclerView.Adapter<TestPictoralHolder> implements MyDiffUtilAdapter2 {

    // Activity
    private final Activity activity;

    // Objects
    private final AsyncListDiffer<String> differ;

    // Vars
    private int answer = -1, key = -1;
    private boolean userSelect = false;

    public TestPictoralAdapter(@NonNull Activity activity) {
        this.activity = activity;

        differ = new AsyncListDiffer<>(this, new DiffUtilStringCallback(this));
    }

    @NonNull
    @Override
    public TestPictoralHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TestPictoralHolder(SingleItemTestPictoralBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TestPictoralHolder holder, int i) {
        String item = differ.getCurrentList().get(i);

        listener(holder, i);

        setData(holder, item, i);
    }

    @Override
    public int getItemCount() {
        if (this.differ.getCurrentList() != null)
            return differ.getCurrentList().size();
        else
            return 0;
    }

    public void setItems(ArrayList<String> items, String answer, String key) {
        if (!answer.equals(""))
            this.answer = Integer.parseInt(answer) - 1;

        if (!key.equals(""))
            this.key = Integer.parseInt(key);

        differ.submitList(items);
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
        Picasso.get().load(url).placeholder(R.color.CoolGray100).into(holder.binding.answerImageView);

        setActive(holder, position);

        setClickable(holder);
    }

    private void setActive(TestPictoralHolder holder, int position) {
        if (position == answer) {
            holder.itemView.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_risloo500_ripple_coolgray300);
            InitManager.txtColorBackground(holder.binding.numberTextView, activity.getResources().getColor(R.color.White), R.drawable.draw_oval_solid_risloo500);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200_ripple_coolgray300);
            InitManager.txtColorBackground(holder.binding.numberTextView, activity.getResources().getColor(R.color.CoolGray700), R.drawable.draw_oval_solid_transparent_border_1sdp_coolgray200);

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

    @Override
    public boolean areItemsTheSame(String oldString, String newString) {
        return newString.equals(oldString);
    }

    @Override
    public boolean areContentsTheSame(String oldString, String newString) {
        return newString.equals(oldString);
    }

}