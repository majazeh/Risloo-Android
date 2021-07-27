package com.majazeh.risloo.Views.Adapters.Recycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.databinding.SingleItemDocumentBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.DocumentsHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

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
//        DocumentModel model = (DocumentModel) items.get(i);

        detector(holder);

        listener(holder);

        setData(holder);
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

    private void detector(DocumentsHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.draw_rec_solid_white_ripple_gray300);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener(DocumentsHolder holder) {
        ClickManager.onDelayedClickListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());

        holder.binding.menuSpinner.setOnTouchListener((v, event) -> {
            userSelect = true;
            return false;
        });

        holder.binding.menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userSelect) {
                    String pos = parent.getItemAtPosition(position).toString();

                    switch (pos) {
                        case "تأیید":
                            // TODO : Place Code Here
                            break;
                        case "رد کردن":
                            // TODO : Place Code Here
                            break;
                        case "دانلود فایل":
                            // TODO : Place Code Here
                            break;
                    }

                    parent.setSelection(parent.getAdapter().getCount());

                    userSelect = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(DocumentsHolder holder) {
        if (holder.getBindingAdapterPosition() == 0)
            holder.binding.topView.setVisibility(View.GONE);
        else
            holder.binding.topView.setVisibility(View.VISIBLE);

        holder.binding.serialTextView.setText("P-96666DD");
        holder.binding.nameTextView.setText("مجوز مرکز مشاوره طلیعه سلامت");
        holder.binding.statusTextView.setText("تأیید شده");

        setMenu(holder);
    }

    private void setMenu(DocumentsHolder holder) {
        ArrayList<String> items = new ArrayList<>();

        items.add(activity.getResources().getString(R.string.DocumentsFragmentAccept));
        items.add(activity.getResources().getString(R.string.DocumentsFragmentKick));
        items.add(activity.getResources().getString(R.string.DocumentsFragmentDownload));

        items.add("");

        InitManager.actionCustomSpinner(activity, holder.binding.menuSpinner, items);
    }

    public class DocumentsHolder extends RecyclerView.ViewHolder {

        private SingleItemDocumentBinding binding;

        public DocumentsHolder(SingleItemDocumentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}