package com.majazeh.risloo.Views.Adapters.Recycler;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
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

        listener(holder, center);

        setData(holder, center);
    }

    @Override
    public int getItemCount() {
        if (this.centers != null)
            return centers.size();
        else
            return 0;
    }

    public void setCenters(ArrayList<TypeModel> centers) {
        if (this.centers == null)
            this.centers = centers;
        else
            this.centers.addAll(centers);
        notifyDataSetChanged();
    }

    public void clearCenters() {
        if (this.centers != null) {
            this.centers.clear();
            notifyDataSetChanged();
        }
    }

    private void detector(CentersHolder holder) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            holder.binding.containerConstraintLayout.setBackgroundResource(R.drawable.draw_2sdp_solid_white_border_1sdp_gray200_ripple_gray300);
        }
    }

    private void listener(CentersHolder holder, CenterModel model) {
        ClickManager.onClickListener(() -> {
            if (model.getCenterType().equals("counseling_center"))
                ((MainActivity) activity).navigator(R.id.centerFragment, getExtras(model));
            else
                ((MainActivity) activity).navigator(R.id.roomFragment, getExtras(model));
        }).widget(holder.binding.containerConstraintLayout);
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

            if (model.getDetail().has("avatar") && !model.getDetail().isNull("avatar") && model.getDetail().getJSONArray("avatar").length() != 0) {
                setAvatar(holder, model.getDetail().getJSONArray("avatar").getJSONObject(2).getString("url"));
            } else {
                setAvatar(holder, "");
            }
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

    private void setAcceptation(CenterModel model, Bundle extras) {
        if (model.getAcceptation() != null) {
            switch (model.getAcceptation().getPosition()) {
                case "manager":
                case "operator":
                case "psychologist":
                case "client":
                    extras.putString("status", model.getAcceptation().getPosition());
                    break;
                default:
                    if (!model.getAcceptation().getKicked_at().equals("")) {
                        extras.putString("status", "kicked");
                    } else {
                        if (model.getAcceptation().getAccepted_at() != 0) {
                            extras.putString("status", "accepted");
                        } else {
                            extras.putString("status", "awaiting");
                        }
                    }
                    break;
            }
        } else {
            extras.putString("status", "request");
        }
    }

    private Bundle getExtras(CenterModel model) {
        Bundle extras = new Bundle();
        try {
            extras.putString("id", model.getCenterId());
            extras.putString("type", model.getCenterType());

            extras.putString("manager_id", model.getManager().getUserId());
            extras.putString("manager_name", model.getManager().getName());

            setAcceptation(model, extras);

            if (model.getDetail().has("title") && !model.getDetail().isNull("title"))
                extras.putString("title", model.getDetail().getString("title"));

            if (model.getDetail().has("address") && !model.getDetail().isNull("address"))
                extras.putString("address", model.getDetail().getString("address"));

            if (model.getDetail().has("description") && !model.getDetail().isNull("description"))
                extras.putString("description", model.getDetail().getString("description"));

            if (model.getDetail().has("avatar") && !model.getDetail().isNull("avatar") && model.getDetail().getJSONArray("avatar").length() != 0)
                extras.putString("avatar", model.getDetail().getJSONArray("avatar").getJSONObject(2).getString("url"));

            if (model.getDetail().has("phone_numbers") && !model.getDetail().isNull("phone_numbers") && model.getDetail().getJSONArray("phone_numbers").length() != 0)
                extras.putString("phone_numbers", model.getDetail().getJSONArray("phone_numbers").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return extras;
    }

    public class CentersHolder extends RecyclerView.ViewHolder {

        private SingleItemCenterBinding binding;

        public CentersHolder(SingleItemCenterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}