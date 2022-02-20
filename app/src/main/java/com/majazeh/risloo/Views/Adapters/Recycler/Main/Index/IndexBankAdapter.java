package com.majazeh.risloo.Views.adapters.recycler.main.Index;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.managers.InitManager;
import com.majazeh.risloo.Utils.managers.SelectionManager;
import com.majazeh.risloo.Utils.widgets.CustomClickView;
import com.majazeh.risloo.Views.adapters.holder.main.Index.IndexBankHolder;
import com.majazeh.risloo.databinding.SingleItemIndexBankBinding;
import com.mre.ligheh.Model.TypeModel.IbanModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class IndexBankAdapter extends RecyclerView.Adapter<IndexBankHolder> {

    // Objects
    private Activity activity;

    // Vars
    private ArrayList<TypeModel> items;

    public IndexBankAdapter(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public IndexBankHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IndexBankHolder(SingleItemIndexBankBinding.inflate(LayoutInflater.from(activity), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IndexBankHolder holder, int i) {
        IbanModel model = (IbanModel) items.get(i);

        listener(holder, model);

        setData(holder, model);
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

    private void listener(IndexBankHolder holder, IbanModel model) {
        CustomClickView.onDelayedListener(() -> {
            // TODO : Place Code Here
        }).widget(holder.binding.getRoot());
    }

    private void setData(IndexBankHolder holder, IbanModel model) {
        holder.binding.ibanTextView.setText(model.getIban());

        if (!model.getOwner().equals(""))
            holder.binding.nameTextView.setText(model.getOwner());
        else
            holder.binding.nameTextView.setText(activity.getResources().getString(R.string.AppDefaultUnknown));

        if (model.getBank() != null && !model.getBank().getTitle().equals(""))
            setAvatar(holder, model.getBank().getId());
        else
            setAvatar(holder, "");

        setStatus(holder, model.getStatus());
    }

    private void setAvatar(IndexBankHolder holder, String id) {
        if (!id.equals("")) {
            holder.binding.avatarImageView.setPadding((int) activity.getResources().getDimension(R.dimen._12sdp), (int) activity.getResources().getDimension(R.dimen._12sdp), (int) activity.getResources().getDimension(R.dimen._12sdp), (int) activity.getResources().getDimension(R.dimen._12sdp));

            switch (id) {
                case "1":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_ansar);
                    break;
                case "2":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_ayandeh);
                    break;
                case "3":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_dey);
                    break;
                case "4":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_eghtesad_novin);
                    break;
                case "5":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_ghavamin);
                    break;
                case "6":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_hekmat_iranian);
                    break;
                case "7":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_iran_zamin);
                    break;
                case "8":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_karafarin);
                    break;
                case "9":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_keshavarzi);
                    break;
                case "10":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_khavarmianeh);
                    break;
                case "11":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_kosar);
                    break;
                case "12":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_markazi);
                    break;
                case "13":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_maskan);
                    break;
                case "14":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_mehr_eghtesad);
                    break;
                case "15":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_mehr_iran);
                    break;
                case "16":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_melal);
                    break;
                case "17":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_mellat);
                    break;
                case "18":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_melli_iran);
                    break;
                case "19":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_noor);
                    break;
                case "20":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_parsian);
                    break;
                case "21":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_pasargad);
                    break;
                case "22":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_post_bank);
                    break;
                case "23":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_refah);
                    break;
                case "24":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_resalat);
                    break;
                case "25":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_saderat);
                    break;
                case "26":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_saman);
                    break;
                case "27":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_sanat_madan);
                    break;
                case "28":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_sarmayeh);
                    break;
                case "29":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_sepah);
                    break;
                case "30":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_shahr);
                    break;
                case "31":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_sina);
                    break;
                case "32":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_taavon);
                    break;
                case "33":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_tejarat);
                    break;
                case "34":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_tosee);
                    break;
                case "35":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_tosee_saderat);
                    break;
                case "36":
                    holder.binding.avatarImageView.setImageResource(R.drawable.bank_tourism);
                    break;
            }
        } else {
            InitManager.imgResTint(activity, holder.binding.avatarImageView, R.drawable.ic_university_light, R.color.coolGray400);
        }
    }

    private void setStatus(IndexBankHolder holder, String status) {
        holder.binding.statusTextView.setText(SelectionManager.getIbanStatus(activity, "fa", status));

        switch (status) {
            case "verified":
                holder.binding.statusTextView.setVisibility(View.GONE);

                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.emerald500));
                holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_emerald50);
                break;
            case "awaiting":
                holder.binding.statusTextView.setVisibility(View.VISIBLE);

                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.amber500));
                holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_amber50);
                break;
            default:
                holder.binding.statusTextView.setVisibility(View.VISIBLE);

                holder.binding.statusTextView.setTextColor(activity.getResources().getColor(R.color.coolGray500));
                holder.binding.statusTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_coolgray50);
                break;
        }
    }

}