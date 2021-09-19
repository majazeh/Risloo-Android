package com.majazeh.risloo.Views.Adapters.Recycler.Index;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Adapters.Holder.Header.HeaderDocumentHolder;
import com.majazeh.risloo.Views.Adapters.Holder.Index.IndexDocumentHolder;
import com.majazeh.risloo.databinding.HeaderItemIndexDocumentBinding;
import com.majazeh.risloo.databinding.SingleItemIndexDocumentBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class IndexDocumentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;
    private boolean userSelect = false;

    public IndexDocumentAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0)
            return new HeaderDocumentHolder(HeaderItemIndexDocumentBinding.inflate(LayoutInflater.from(activity), viewGroup, false));

        return new IndexDocumentHolder(SingleItemIndexDocumentBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof  IndexDocumentHolder) {
//            DocumentModel model = (DocumentModel) items.get(i - 1);

            listener((IndexDocumentHolder) holder);

            setData((IndexDocumentHolder) holder);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;

        return 1;
    }

    @Override
    public int getItemCount() {
        if (this.items != null)
            return items.size() + 1;
        else
            return 0;
    }

    public int itemsCount() {
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

    @SuppressLint("ClickableViewAccessibility")
    private void listener(IndexDocumentHolder holder) {
        CustomClickView.onDelayedListener(() -> {
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

    private void setData(IndexDocumentHolder holder) {
        holder.binding.serialTextView.setText("P-96666DD");
        holder.binding.nameTextView.setText("مجوز مرکز مشاوره طلیعه سلامت");
        holder.binding.statusTextView.setText("تأیید شده");

        setMenu(holder);
    }

    private void setMenu(IndexDocumentHolder holder) {
        ArrayList<String> items = new ArrayList<>();

        items.add(activity.getResources().getString(R.string.DocumentsFragmentAccept));
        items.add(activity.getResources().getString(R.string.DocumentsFragmentKick));
        items.add(activity.getResources().getString(R.string.DocumentsFragmentDownload));

        items.add("");

        InitManager.actionCustomSpinner(activity, holder.binding.menuSpinner, items);
    }

}