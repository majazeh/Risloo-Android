package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
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
        return 4;
    }

//    public void setDocuments(ArrayList<Document> documents) {
//        this.documents = documents;
//        notifyDataSetChanged();
//    }

    private void detector(DocumentsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);

            holder.binding.attachmentImageView.setBackgroundResource(R.drawable.draw_oval_solid_white_ripple_gray300);
        }
    }

    private void listener(DocumentsHolder holder) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.attachmentImageView);

        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.actionTextView);
    }

    private void setData(DocumentsHolder holder) {
        if (holder.getBindingAdapterPosition() == 0) {
            holder.binding.topView.setVisibility(View.GONE);
        } else {
            holder.binding.topView.setVisibility(View.VISIBLE);
        }

        holder.binding.serialTextView.setText("P-96666DD");
        holder.binding.nameTextView.setText("مجوز مرکز مشاوره طلیعه سلامت");
        holder.binding.statusTextView.setText("تأیید شده");

        if (holder.getBindingAdapterPosition() == 0) {
            setAction(holder, "none");
        } else if (holder.getBindingAdapterPosition() == 2) {
            setAction(holder, "kick");
        } else {
            setAction(holder, "accept");
        }
    }

    private void setAction(DocumentsHolder holder, String action) {
        if (action.equals("accept")) {
            holder.binding.actionTextView.setVisibility(View.VISIBLE);
            holder.binding.actionTextView.setText(activity.getResources().getString(R.string.DocumentsFragmentAccept));
            holder.binding.actionTextView.setTextColor(activity.getResources().getColor(R.color.Green600));

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                holder.binding.actionTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_green700_ripple_green300);
            else
                holder.binding.actionTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_green700);
        } else if (action.equals("kick")) {
            holder.binding.actionTextView.setVisibility(View.VISIBLE);
            holder.binding.actionTextView.setText(activity.getResources().getString(R.string.DocumentsFragmentKick));
            holder.binding.actionTextView.setTextColor(activity.getResources().getColor(R.color.Red600));

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                holder.binding.actionTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_white_border_1sdp_red700_ripple_red300);
            else
                holder.binding.actionTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_transparent_border_1sdp_red700);
        } else {
            holder.binding.actionTextView.setVisibility(View.GONE);
            holder.binding.actionTextView.setText("");
        }
    }

    public class DocumentsHolder extends RecyclerView.ViewHolder {

        private SingleItemDocumentBinding binding;

        public DocumentsHolder(SingleItemDocumentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}