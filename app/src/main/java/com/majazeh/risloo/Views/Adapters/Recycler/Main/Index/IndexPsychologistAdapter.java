package com.majazeh.risloo.Views.Adapters.Recycler.Main.Index;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Index.IndexPsychologistHolder;
import com.majazeh.risloo.databinding.SingleItemIndexPsychologistBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class IndexPsychologistAdapter extends RecyclerView.Adapter<IndexPsychologistHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public IndexPsychologistAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public IndexPsychologistHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IndexPsychologistHolder(SingleItemIndexPsychologistBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IndexPsychologistHolder holder, int i) {
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

    private void listener(IndexPsychologistHolder holder, UserModel model) {
        CustomClickView.onDelayedListener(() -> {
            // TODO : Place Code When Needed
        }).widget(holder.binding.getRoot());
    }

    private void setData(IndexPsychologistHolder holder, UserModel model) {
        if (!model.getName().equals(""))
            holder.binding.nameTextView.setText(model.getName());
        else
            holder.binding.nameTextView.setText(model.getId());

        if (model.getAvatar() != null && model.getAvatar().getMedium() != null && !model.getAvatar().getMedium().getUrl().equals(""))
            setAvatar(holder, model.getAvatar().getMedium().getUrl());
        else
            setAvatar(holder, "");
    }

    private void setAvatar(IndexPsychologistHolder holder, String url) {
        if (!url.equals("")) {
            holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(url).placeholder(R.color.coolGray100).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            holder.binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(holder.binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.coolGray100).placeholder(R.color.coolGray100).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
        }
    }

}