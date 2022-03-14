package com.majazeh.risloo.views.adapters.recycler.main;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.interfaces.DiffUtilTypeModelAdapter;
import com.majazeh.risloo.utils.interfaces.DiffUtilTypeModelCallback;
import com.majazeh.risloo.utils.interfaces.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.holder.main.MainNavHolder;
import com.majazeh.risloo.databinding.SingleItemMainNavBinding;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import org.json.JSONException;

import java.util.ArrayList;

public class MainNavAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DiffUtilTypeModelAdapter {

    // Activity
    private final Activity activity;

    // Differ
    private final AsyncListDiffer<TypeModel> differ;

    // Vars
    private ArrayList<TypeModel> items = new ArrayList<>();
    private int selectedPosition = 0;

    public MainNavAdapter(@NonNull Activity activity) {
        this.activity = activity;

        differ = new AsyncListDiffer<>(this, new DiffUtilTypeModelCallback(this));
    }

    @NonNull
    @Override
    public MainNavHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MainNavHolder(SingleItemMainNavBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        TypeModel model = differ.getCurrentList().get(i);

        listener((MainNavHolder) holder);

        setData((MainNavHolder) holder, model, i);
    }

    @Override
    public int getItemCount() {
        if (this.differ.getCurrentList() != null)
            return differ.getCurrentList().size();
        else
            return 0;
    }

    public void setItems(ArrayList<TypeModel> items) {
        this.items = items;

        differ.submitList(items);
    }

    private void resetItems() {
        differ.submitList(null);
        differ.submitList(items);
    }

    private void listener(MainNavHolder holder) {
        CustomClickView.onDelayedListener(() -> {
            String title = holder.binding.nameTextView.getText().toString();

            ((ActivityMain) activity).setFragment(title);
            ((ActivityMain) activity).setDrawer("closeDrawer");
        }).widget(holder.itemView);
    }

    private void setData(MainNavHolder holder, TypeModel model, int position) {
        try {
            holder.binding.nameTextView.setText(model.object.getString("title"));
            holder.binding.descriptionTextView.setText(model.object.getString("desc"));

            holder.binding.iconImageView.setImageResource(model.object.getInt("image"));

            setActive(holder, position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setActive(MainNavHolder holder, int position) {
        if (selectedPosition == position) {
            holder.itemView.setBackgroundResource(R.drawable.draw_4sdp_solid_risloo500_ripple_risloo700);

            holder.binding.nameTextView.setTextColor(activity.getResources().getColor(R.color.white));
            holder.binding.descriptionTextView.setTextColor(activity.getResources().getColor(R.color.white));

            ImageViewCompat.setImageTintList(holder.binding.iconImageView, AppCompatResources.getColorStateList(activity, R.color.white));
        } else {
            holder.itemView.setBackgroundResource(R.drawable.draw_4sdp_solid_coolgray50_ripple_coolgray300);

            holder.binding.nameTextView.setTextColor(activity.getResources().getColor(R.color.coolGray900));
            holder.binding.descriptionTextView.setTextColor(activity.getResources().getColor(R.color.coolGray600));

            ImageViewCompat.setImageTintList(holder.binding.iconImageView, AppCompatResources.getColorStateList(activity, R.color.coolGray900));
        }
    }

    public void setFocused(String value) {
        if (value.contains("نمونه\u200Cهای گروهی")) {
            if (((ActivityMain) activity).permissoon.showUsers(((ActivityMain) activity).singleton.getUserModel()) && ((ActivityMain) activity).permissoon.showBulkSamples(((ActivityMain) activity).singleton.getUserModel()))
                selectedPosition = 5;
            else if (((ActivityMain) activity).permissoon.showBulkSamples(((ActivityMain) activity).singleton.getUserModel()))
                selectedPosition = 4;
            else
                selectedPosition = 10;
        }

        else if (value.contains("نمونه") || value.contains("نمونه\u200Cها")) {
            if (((ActivityMain) activity).permissoon.showUsers(((ActivityMain) activity).singleton.getUserModel()))
                selectedPosition = 4;
            else
                selectedPosition = 3;
        }

        else if (value.contains("جلسه") || value.contains("جلسات"))
            selectedPosition = 2;
        else if (value.contains("مراکز درمانی"))
            selectedPosition = 1;
        else if (value.contains("اعضاء"))
            selectedPosition = 3;

        else if (value.contains("ارزیابی\u200Cها")) {
            if (((ActivityMain) activity).permissoon.showUsers(((ActivityMain) activity).singleton.getUserModel()) && ((ActivityMain) activity).permissoon.showBulkSamples(((ActivityMain) activity).singleton.getUserModel()))
                selectedPosition = 6;
            else if (((ActivityMain) activity).permissoon.showUsers(((ActivityMain) activity).singleton.getUserModel()) || ((ActivityMain) activity).permissoon.showBulkSamples(((ActivityMain) activity).singleton.getUserModel()))
                selectedPosition = 5;
            else
                selectedPosition = 10;
        }

        else if (value.contains("دانلودها")) {
            if (((ActivityMain) activity).permissoon.showUsers(((ActivityMain) activity).singleton.getUserModel()) && ((ActivityMain) activity).permissoon.showBulkSamples(((ActivityMain) activity).singleton.getUserModel()))
                selectedPosition = 7;
            else if (((ActivityMain) activity).permissoon.showUsers(((ActivityMain) activity).singleton.getUserModel()) && ((ActivityMain) activity).permissoon.showBulkSamples(((ActivityMain) activity).singleton.getUserModel()))
                selectedPosition = 6;
            else
                selectedPosition = 10;
        }

        else
            selectedPosition = 0;

        resetItems();
    }

    @Override
    public boolean areItemsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel) {
        try {
            return newTypeModel.object.getString("title").equals(oldTypeModel.object.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        } return false;
    }

    @Override
    public boolean areContentsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel) {
        try {
            return newTypeModel.object.getString("title").equals(oldTypeModel.object.getString("title"))
                    && newTypeModel.object.getString("desc").equals(oldTypeModel.object.getString("desc"))
                    && newTypeModel.object.getInt("image") == oldTypeModel.object.getInt("image");
        } catch (JSONException e) {
            e.printStackTrace();
        } return false;
    }

}