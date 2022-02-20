package com.majazeh.risloo.Views.adapters.recycler.main.Index;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.DateManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.IntentManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.Views.activities.MainActivity;
import com.majazeh.risloo.Views.adapters.holder.main.Index.IndexDownloadHolder;
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
            if (file.getName().startsWith("X1")) {
                IntentManager.file(activity, file);
            } else if (file.getName().contains("X1")) {
                if (current instanceof DownloadsFragment)
                    ((MainActivity) activity).navigatoon.navigateToFolderFragment(file.getName());

            } else {
                IntentManager.file(activity, file);
            }
        }).widget(holder.binding.getRoot());
    }

    private void setData(IndexDownloadHolder holder, File file) {
        holder.binding.nameTextView.setText(file.getName());
        holder.binding.dateTextView.setText(DateManager.jalNMMsDDsMMsDD(file.lastModified(), " "));

        setAvatar(holder, file);
    }

    private void setAvatar(IndexDownloadHolder holder, File file) {
        if (file.getName().startsWith("X1")) {
            String suffix = StringManager.suffix(file.getName(), '.');

            switch (suffix) {
                case "html":
                    holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                    holder.binding.avatarIncludeLayout.iconImageView.setVisibility(View.VISIBLE);

                    InitManager.imgResTint(activity, holder.binding.avatarIncludeLayout.iconImageView, R.drawable.ic_file_code_light, R.color.risloo500);

                    holder.binding.avatarIncludeLayout.avatarImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_risloo50);
                    break;
                case "xlsx":
                    holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                    holder.binding.avatarIncludeLayout.iconImageView.setVisibility(View.VISIBLE);

                    InitManager.imgResTint(activity, holder.binding.avatarIncludeLayout.iconImageView, R.drawable.ic_file_excel_light, R.color.emerald500);

                    holder.binding.avatarIncludeLayout.avatarImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_emerald50);
                    break;
                case "pdf":
                    holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                    holder.binding.avatarIncludeLayout.iconImageView.setVisibility(View.VISIBLE);

                    InitManager.imgResTint(activity, holder.binding.avatarIncludeLayout.iconImageView, R.drawable.ic_file_pdf_light, R.color.red500);

                    holder.binding.avatarIncludeLayout.avatarImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_red50);
                    break;
                case "png":
                    holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                    holder.binding.avatarIncludeLayout.iconImageView.setVisibility(View.VISIBLE);

                    InitManager.imgResTint(activity, holder.binding.avatarIncludeLayout.iconImageView, R.drawable.ic_file_image_light, R.color.pink500);

                    holder.binding.avatarIncludeLayout.avatarImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_pink50);
                    break;
                case "svg":
                    holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                    holder.binding.avatarIncludeLayout.iconImageView.setVisibility(View.GONE);

                    InitManager.txtTextColor(holder.binding.avatarIncludeLayout.charTextView, suffix, activity.getResources().getColor(R.color.amber500));

                    holder.binding.avatarIncludeLayout.avatarImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_amber50);
                    break;
                case "json":
                    holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                    holder.binding.avatarIncludeLayout.iconImageView.setVisibility(View.GONE);

                    InitManager.txtTextColor(holder.binding.avatarIncludeLayout.charTextView, suffix, activity.getResources().getColor(R.color.violet500));

                    holder.binding.avatarIncludeLayout.avatarImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_violet50);
                    break;
                default:
                    holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                    holder.binding.avatarIncludeLayout.iconImageView.setVisibility(View.GONE);

                    InitManager.txtTextColor(holder.binding.avatarIncludeLayout.charTextView, suffix, activity.getResources().getColor(R.color.coolGray500));

                    holder.binding.avatarIncludeLayout.avatarImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_coolgray100);
                    break;
            }

        } else if (file.getName().contains("X1")) {
            holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            holder.binding.avatarIncludeLayout.iconImageView.setVisibility(View.VISIBLE);

            InitManager.imgResTint(activity, holder.binding.avatarIncludeLayout.iconImageView, R.drawable.ic_folder_open_light, R.color.risloo500);

            holder.binding.avatarIncludeLayout.avatarImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_coolgray100);
        } else {
            holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            holder.binding.avatarIncludeLayout.iconImageView.setVisibility(View.GONE);

            Picasso.get().load(Uri.fromFile(file)).placeholder(R.color.coolGray100).into(holder.binding.avatarIncludeLayout.avatarImageView);

            holder.binding.avatarIncludeLayout.avatarImageView.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_coolgray200);
        }
    }

}