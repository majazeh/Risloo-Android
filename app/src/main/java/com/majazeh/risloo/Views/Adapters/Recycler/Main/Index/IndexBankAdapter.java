package com.majazeh.risloo.Views.Adapters.Recycler.Main.Index;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Index.IndexBankHolder;
import com.majazeh.risloo.databinding.SingleItemIndexBankBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class IndexBankAdapter extends RecyclerView.Adapter<IndexBankHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public IndexBankAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public IndexBankHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IndexBankHolder(SingleItemIndexBankBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IndexBankHolder holder, int i) {
//        BankModel model = (BankModel) items.get(i);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size();
        else
            return 4;
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

    private void listener(IndexBankHolder holder) {
        CustomClickView.onDelayedListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());
    }

    private void setData(IndexBankHolder holder) {
        holder.binding.ibanTextView.setText("IR122223545487987987454544");
        holder.binding.nameTextView.setText("حسن صالحی");

        setAvatar(holder, "");

        setStatus(holder);
    }

    private void setAvatar(IndexBankHolder holder, String url) {
        if (!url.equals("")) {
            holder.binding.avatarIncludeLayout.iconImageView.setVisibility(View.GONE);
            Picasso.get().load(url).placeholder(R.color.CoolGray100).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            holder.binding.avatarIncludeLayout.iconImageView.setVisibility(View.VISIBLE);
            holder.binding.avatarIncludeLayout.iconImageView.setImageResource(R.drawable.ic_university_light);

            Picasso.get().load(R.color.CoolGray50).placeholder(R.color.CoolGray100).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
        }
    }

    private void setStatus(IndexBankHolder holder) {
        holder.binding.statusTextView.setText("در انتظار");

        switch (holder.binding.statusTextView.getText().toString()) {
            case "در انتظار":
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Amber500));
                holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_amber50);
                break;
            case "در حال انجام":
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Risloo500));
                holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_risloo50);
                break;
            case "پایان یافته":
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Emerald500));
                holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_emerald50);
                break;
            case "لغو شده":
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.Red500));
                holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_red50);
                break;
            default:
                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.CoolGray500));
                holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_coolgray50);
                break;
        }
    }

}