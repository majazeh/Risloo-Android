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
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.databinding.SingleItemCenterBinding;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

public class CentersAdapter extends RecyclerView.Adapter<CentersAdapter.CentersHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> centers;

    public CentersAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CentersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CentersHolder(SingleItemCenterBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CentersHolder holder, int i) {
        CenterModel center = (CenterModel) centers.get(i);

        detector(holder);

        listener(holder);

        setData(holder, center);
    }

    @Override
    public int getItemCount() {
        return centers.size();
    }

    public void setCenters(ArrayList<TypeModel> centers) {
        this.centers = centers;
        notifyDataSetChanged();
    }

    private void detector(CentersHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(CentersHolder holder) {
        ClickManager.onClickListener(() -> ((MainActivity) activity).navigator(R.id.centerFragment)).widget(holder.binding.containerConstraintLayout);
    }

    private void setData(CentersHolder holder, CenterModel model) {
        try {
            if (model.getCenterType().equals("counseling_center")) {
                holder.binding.nameTextView.setText(model.getDetail().getString("title"));
                holder.binding.typeTextView.setText(model.getManager().getName());
            } else {
                holder.binding.nameTextView.setText(model.getManager().getName());
                holder.binding.typeTextView.setText(activity.getResources().getString(R.string.CentersFragmentTypePersonalClinic));
            }

            if (model.getDetail().getJSONArray("avatar") != null) {
                if (model.getDetail().getJSONArray("avatar").length() != 0) {
                    setAvatar(holder, model.getDetail().getJSONArray("avatar").getJSONObject(2).getString("url"));
                    return;
                }
            }
            setAvatar(holder, "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAvatar(CentersHolder holder, String url) {
        if (!url.equals("")) {
            holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
            Picasso.get().load(url).placeholder(R.color.Gray50).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
        } else {
            holder.binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
            holder.binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(holder.binding.nameTextView.getText().toString()));

            Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(holder.binding.avatarIncludeLayout.avatarCircleImageView);
        }
    }

    public class CentersHolder extends RecyclerView.ViewHolder {

        private SingleItemCenterBinding binding;

        public CentersHolder(SingleItemCenterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}