package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.DocumentsHolder> {

    // Vars
//    private ArrayList<Document> documents;

    // Objects
    private Activity activity;

    public DocumentsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public DocumentsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.single_item_document, viewGroup, false);

        return new DocumentsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentsHolder holder, int i) {
//        Documents document = documents.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
    }

    @Override
    public int getItemCount() {
//        return documents.size();
        return 15;
    }

//    public void setDocument(ArrayList<Document> documents) {
//        this.documents = documents;
//        notifyDataSetChanged();
//    }

    private void detector(DocumentsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.itemView.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.attachmentImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_gray300);
            holder.cancelImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_gray300);
            holder.acceptImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_gray300);
        }
    }

    private void listener(DocumentsHolder holder) {
        holder.itemView.setOnClickListener(v -> {
            holder.itemView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.itemView.setClickable(true), 300);

//            ((MainActivity) activity).navigator(R.id.userFragment);
        });

        holder.attachmentImageView.setOnClickListener(v -> {
            holder.attachmentImageView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.attachmentImageView.setClickable(true), 300);

            // TODO : Place Code Here
        });

        holder.cancelImageView.setOnClickListener(v -> {
            holder.cancelImageView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.cancelImageView.setClickable(true), 300);

            // TODO : Place Code Here
        });

        holder.acceptImageView.setOnClickListener(v -> {
            holder.acceptImageView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.acceptImageView.setClickable(true), 300);

            // TODO : Place Code Here
        });
    }

    private void setData(DocumentsHolder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.topView.setVisibility(View.GONE);
        } else {
            holder.topView.setVisibility(View.VISIBLE);
        }

        holder.serialTextView.setText("P-96666DD");
        holder.nameTextView.setText("مجوز مرکز مشاوره طلیعه سلامت");
        holder.statusTextView.setText("تأیید شده");

        holder.cancelImageView.setImageResource(R.drawable.ic_times_light);
        ImageViewCompat.setImageTintList(holder.cancelImageView, AppCompatResources.getColorStateList(activity, R.color.Red700));
        holder.acceptImageView.setImageResource(R.drawable.ic_check_light);
        ImageViewCompat.setImageTintList(holder.acceptImageView, AppCompatResources.getColorStateList(activity, R.color.Green700));
    }

    public class DocumentsHolder extends RecyclerView.ViewHolder {

        private View topView;
        private TextView serialTextView, nameTextView, statusTextView;
        private ImageView attachmentImageView, cancelImageView, acceptImageView;

        public DocumentsHolder(View view) {
            super(view);
            topView = view.findViewById(R.id.single_item_document_top_view);
            serialTextView = view.findViewById(R.id.single_item_document_serial_textView);
            nameTextView = view.findViewById(R.id.single_item_document_name_textView);
            attachmentImageView = view.findViewById(R.id.single_item_document_attachment_imageView);
            statusTextView = view.findViewById(R.id.single_item_document_status_textView);
            cancelImageView = view.findViewById(R.id.single_item_document_cancel_imageView);
            acceptImageView = view.findViewById(R.id.single_item_document_accept_imageView);
        }
    }

}