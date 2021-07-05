package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Views.Activities.TestActivity;
import com.majazeh.risloo.Views.Fragments.Test.TestPictoralFragment;
import com.majazeh.risloo.databinding.SingleItemPictoralBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PictoralsAdapter extends RecyclerView.Adapter<PictoralsAdapter.PictoralsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<String> urls;
    private int answer = -1;
    private boolean userSelect = false;

    public PictoralsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public PictoralsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PictoralsHolder(SingleItemPictoralBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PictoralsHolder holder, int i) {
        String url = urls.get(i);

        listener(holder, i);

        setData(holder, url, i);
    }

    @Override
    public int getItemCount() {
        if (this.urls != null)
            return urls.size();
        else
            return 0;
    }

    public void setItems(ArrayList<String> urls, String answer) {
        if (this.urls == null)
            this.urls = urls;
        else
            this.urls.addAll(urls);

        if (!answer.equals(""))
            this.answer = Integer.parseInt(answer) - 1;

        notifyDataSetChanged();
    }

    public void clearItems() {
        if (this.urls != null) {
            this.urls.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(PictoralsHolder holder, boolean selected) {
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

    private void listener(PictoralsHolder holder, int position) {
        ClickManager.onDelayedClickListener(() -> {
            answer = position;
            userSelect = true;
            notifyDataSetChanged();

            ((TestActivity) activity).sendItem(Integer.parseInt(getParent().formModel.getTitle()), String.valueOf(answer + 1));
        }).widget(holder.itemView);
    }

    private void setData(PictoralsHolder holder, String url, int position) {
        holder.binding.numberTextView.setText(String.valueOf(holder.getBindingAdapterPosition() + 1));
        Picasso.get().load(url).placeholder(R.color.Gray100).into(holder.binding.answerImageView);

        setActive(holder, position);

        setClickable(holder);
    }

    private void setActive(PictoralsHolder holder, int position) {
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

    private void setClickable(PictoralsHolder holder) {
        if (userSelect) {
            holder.itemView.setEnabled(false);
            holder.itemView.setClickable(false);
        } else {
            holder.itemView.setEnabled(true);
            holder.itemView.setClickable(true);
        }
    }

    private TestPictoralFragment getParent() {
        Fragment fragment = ((TestActivity) activity).navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (fragment != null)
            if (fragment instanceof TestPictoralFragment)
                return (TestPictoralFragment) fragment;

        return null;
    }

    public class PictoralsHolder extends RecyclerView.ViewHolder {

        private SingleItemPictoralBinding binding;

        public PictoralsHolder(SingleItemPictoralBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}