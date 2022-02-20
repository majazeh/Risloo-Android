package com.majazeh.risloo.Views.Adapters.Recycler.Main.Index;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Index.IndexReferenceHolder;
import com.majazeh.risloo.databinding.SingleItemIndexReferenceBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class IndexReferenceAdapter extends RecyclerView.Adapter<IndexReferenceHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public IndexReferenceAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public IndexReferenceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IndexReferenceHolder(SingleItemIndexReferenceBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IndexReferenceHolder holder, int i) {
        UserModel model = (UserModel) items.get(i);

        initializer();

        listener(holder, model);

        setData(holder, model);
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

    private void initializer() {
        current = ((MainActivity) activity).fragmont.getCurrent();
    }

    private void listener(IndexReferenceHolder holder, UserModel model) {
        CustomClickView.onDelayedListener(() -> {
            // TODO : Place Code When Needed
        }).widget(holder.binding.getRoot());
    }

    private void setData(IndexReferenceHolder holder, UserModel model) {
        if (!model.getName().equals(""))
            holder.binding.nameTextView.setText(model.getName());
        else
            holder.binding.nameTextView.setText(model.getId());

        if (model.getAvatar() != null && model.getAvatar().getMedium() != null && !model.getAvatar().getMedium().getUrl().equals(""))
            setAvatar(holder, model.getAvatar().getMedium().getUrl());
        else
            setAvatar(holder, "");
    }

    private void setAvatar(IndexReferenceHolder holder, String url) {
        if (!url.equals("")) {
            holder.binding.avatarIncludeLayout.iconImageView.setVisibility(View.GONE);
            Picasso.get().load(url).placeholder(R.color.coolGray100).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            holder.binding.avatarIncludeLayout.iconImageView.setVisibility(View.VISIBLE);
            holder.binding.avatarIncludeLayout.iconImageView.setImageResource(R.drawable.ic_user_light);

            holder.binding.avatarIncludeLayout.avatarCircleImageView.setBackgroundResource(R.drawable.draw_oval_solid_white_border_1sdp_coolgray200);
        }
    }

}