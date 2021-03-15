package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemDocumentBinding;

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.DocumentsHolder> {

    // Objects
    private Activity activity;

    // Vars
//    private ArrayList<Document> documents;

    public DocumentsAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public DocumentsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DocumentsHolder(SingleItemDocumentBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
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
            holder.binding.singleItemDocument.setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.attachmentImageView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_ripple_gray300);

            holder.binding.actionTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
        } else {
            holder.binding.actionTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
        }
    }

    private void listener(DocumentsHolder holder) {
        holder.binding.singleItemDocument.setOnClickListener(v -> {
            holder.binding.singleItemDocument.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.singleItemDocument.setClickable(true), 300);

            // TODO : Place Code Here
        });

        holder.binding.attachmentImageView.setOnClickListener(v -> {
            holder.binding.attachmentImageView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.attachmentImageView.setClickable(true), 300);

            // TODO : Place Code Here
        });

        holder.binding.actionTextView.setOnClickListener(v -> {
            holder.binding.actionTextView.setClickable(false);
            ((MainActivity) activity).handler.postDelayed(() -> holder.binding.actionTextView.setClickable(true), 300);

            // TODO : Place Code Here
        });
    }

    private void setData(DocumentsHolder holder) {
        if (holder.getAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText("P-96666DD");
        holder.binding.nameTextView.setText("مجوز مرکز مشاوره طلیعه سلامت");
        holder.binding.statusTextView.setText("تأیید شده");

        holder.binding.actionTextView.setText(activity.getResources().getString(R.string.DocumentsFragmentAccept));
        holder.binding.actionTextView.setTextColor(activity.getResources().getColor(R.color.Green600));
    }

    public class DocumentsHolder extends RecyclerView.ViewHolder {

        private SingleItemDocumentBinding binding;

        public DocumentsHolder(SingleItemDocumentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}