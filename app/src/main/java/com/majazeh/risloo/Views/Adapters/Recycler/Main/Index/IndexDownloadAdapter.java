package com.majazeh.risloo.Views.Adapters.Recycler.Main.Index;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DateManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Holder.Main.Index.IndexDownloadHolder;
import com.majazeh.risloo.Views.Fragments.Main.Index.DownloadsFragment;
import com.majazeh.risloo.databinding.SingleItemIndexDownloadBinding;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class IndexDownloadAdapter extends RecyclerView.Adapter<IndexDownloadHolder> {

    // Fragments
    private Fragment current;

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<File> items;

    public IndexDownloadAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public IndexDownloadHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IndexDownloadHolder(SingleItemIndexDownloadBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IndexDownloadHolder holder, int i) {
        File file = items.get(i);

        initializer();

        listener(holder, file);

        setData(holder, file);
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size();
        else
            return 0;
    }

    public void setItems(ArrayList<File> items) {
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

    private void listener(IndexDownloadHolder holder, File file) {
        CustomClickView.onDelayedListener(() -> {
            if (file.getName().contains(".")) {
                IntentManager.file(activity, file);
            } else {
                if (current instanceof DownloadsFragment)
                    ((MainActivity) activity).navigatoon.navigateToFolderFragment(file.getName());
            }
        }).widget(holder.binding.getRoot());
    }

    private void setData(IndexDownloadHolder holder, File file) {
        holder.binding.nameTextView.setText(file.getName());
        holder.binding.dateTextView.setText(DateManager.jalNMMsDDsMMsDD(file.lastModified(), " "));

        setAvatar(holder, file);
    }

    private void setAvatar(IndexDownloadHolder holder, File file) {
        if (file.getName().contains(".")) {
            String suffix = StringManager.suffix(file.getName(), '.');

            if (suffix.equals("png") || suffix.equals("jpg")) {
                holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                holder.binding.avatarIncludeLayout.iconImageView.setVisibility(View.GONE);

                Picasso.get().load(Uri.fromFile(file)).placeholder(R.color.CoolGray100).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
            } else {
                holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                holder.binding.avatarIncludeLayout.charTextView.setText(suffix);

                holder.binding.avatarIncludeLayout.iconImageView.setVisibility(View.GONE);

                switch (suffix) {
                    case "svg":
                        holder.binding.avatarIncludeLayout.charTextView.setTextColor(activity.getResources().getColor(R.color.Amber500));
                        holder.binding.avatarIncludeLayout.avatarCircleImageView.setBackgroundResource(R.drawable.draw_oval_solid_amber50);
                        break;
                    case "html":
                        holder.binding.avatarIncludeLayout.charTextView.setTextColor(activity.getResources().getColor(R.color.Risloo500));
                        holder.binding.avatarIncludeLayout.avatarCircleImageView.setBackgroundResource(R.drawable.draw_oval_solid_risloo50);
                        break;
                    case "xlsx":
                        holder.binding.avatarIncludeLayout.charTextView.setTextColor(activity.getResources().getColor(R.color.Emerald500));
                        holder.binding.avatarIncludeLayout.avatarCircleImageView.setBackgroundResource(R.drawable.draw_oval_solid_emerald50);
                        break;
                    case "pdf":
                        holder.binding.avatarIncludeLayout.charTextView.setTextColor(activity.getResources().getColor(R.color.Red500));
                        holder.binding.avatarIncludeLayout.avatarCircleImageView.setBackgroundResource(R.drawable.draw_oval_solid_red50);
                        break;
                    case "json":
                        holder.binding.avatarIncludeLayout.charTextView.setTextColor(activity.getResources().getColor(R.color.Violet500));
                        holder.binding.avatarIncludeLayout.avatarCircleImageView.setBackgroundResource(R.drawable.draw_oval_solid_violet50);
                        break;
                    default:
                        holder.binding.avatarIncludeLayout.charTextView.setTextColor(activity.getResources().getColor(R.color.CoolGray500));
                        holder.binding.avatarIncludeLayout.avatarCircleImageView.setBackgroundResource(R.drawable.draw_oval_solid_coolgray50);
                        break;
                }
            }

        } else {
            holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);

            holder.binding.avatarIncludeLayout.iconImageView.setVisibility(View.VISIBLE);
            InitManager.imgResTint(activity, holder.binding.avatarIncludeLayout.iconImageView, R.drawable.ic_vial_light, R.color.Risloo500);

            holder.binding.avatarIncludeLayout.avatarCircleImageView.setBackgroundResource(R.drawable.draw_oval_solid_coolgray50);
        }
    }

}