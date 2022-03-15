package com.majazeh.risloo.views.sheets;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.majazeh.risloo.R;
import com.majazeh.risloo.databinding.SheetBulkSampleBinding;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.IntentManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.majazeh.risloo.views.adapters.recycler.sheet.SheetScaleAdapter;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.BulkSampleModel;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.HashMap;

public class SheetBulkSample extends BottomSheetDialogFragment {

    // Binding
    private SheetBulkSampleBinding binding;

    // Adapters
    private SheetScaleAdapter scaleAdapter;

    // Models
    private UserModel userModel;
    private BulkSampleModel bulkSampleModel;

    // Objects
    private HashMap data, header;

    // Vars
    private String key = "", nickname = "";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = SheetBulkSampleBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setDialog();

        return binding.getRoot();
    }

    private void initializer() {
        scaleAdapter = new SheetScaleAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.scaleIncludeLayout.headerTextView.setText(getResources().getString(R.string.BottomSheetBulkSampleScaleHeader));
        binding.nicknameIncludeLayout.headerTextView.setText(getResources().getString(R.string.BottomSheetBulkSampleNicknameHeader));

        InitManager.rcvVerticalUnfixed(requireActivity(), binding.scaleIncludeLayout.listRecyclerView, 0, 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColorBackground(binding.entryTextView.getRoot(), getResources().getString(R.string.BottomSheetBulkSampleEntry), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.nicknameIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.nicknameIncludeLayout.inputEditText.hasFocus())
                ((ActivityMain) requireActivity()).inputon.select(binding.nicknameIncludeLayout.inputEditText);
            return false;
        });

        binding.nicknameIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            nickname = binding.nicknameIncludeLayout.inputEditText.getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.nicknameErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((ActivityMain) requireActivity()).validatoon.hideValid(binding.nicknameErrorLayout.getRoot(), binding.nicknameErrorLayout.errorTextView);

            doWork();
        }).widget(binding.entryTextView.getRoot());
    }

    private void setDialog() {
        try {
            if (!userModel.getName().equals("")) {
                binding.nicknameIncludeLayout.inputEditText.setText(userModel.getName());
            } else if (!userModel.getId().equals("")) {
                binding.nicknameIncludeLayout.inputEditText.setText(userModel.getId());
            } else {
                binding.nicknameIncludeLayout.inputEditText.setText("");
            }

            if (userModel.getAvatar() != null && userModel.getAvatar().getMedium() != null && !userModel.getAvatar().getMedium().getUrl().equals("")) {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(userModel.getAvatar().getMedium().getUrl()).placeholder(R.color.coolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarIncludeLayout.charTextView.setText(StringManager.charsFirst(binding.nicknameIncludeLayout.inputEditText.getText().toString()));

                Picasso.get().load(R.color.coolGray100).placeholder(R.color.coolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getCenter() != null && bulkSampleModel.getRoom().getCenter().getDetail() != null && bulkSampleModel.getRoom().getCenter().getDetail().has("title") && !bulkSampleModel.getRoom().getCenter().getDetail().getString("title").equals("")) {
                binding.centerIncludeLayout.nameTextView.setText(bulkSampleModel.getRoom().getCenter().getDetail().getString("title"));
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getCenter() != null && bulkSampleModel.getRoom().getCenter().getDetail() != null && bulkSampleModel.getRoom().getCenter().getDetail().has("avatar") && !bulkSampleModel.getRoom().getCenter().getDetail().getString("avatar").equals("") && bulkSampleModel.getRoom().getCenter().getDetail().getJSONArray("avatar").length() != 0) {
                binding.centerIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(bulkSampleModel.getRoom().getCenter().getDetail().getJSONArray("avatar").getJSONObject(2).getString("url")).placeholder(R.color.coolGray100).into(binding.centerIncludeLayout.avatarCircleImageView);
            } else {
                binding.centerIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.centerIncludeLayout.charTextView.setText(StringManager.charsFirst(binding.centerIncludeLayout.nameTextView.getText().toString()));

                Picasso.get().load(R.color.coolGray100).placeholder(R.color.coolGray100).into(binding.centerIncludeLayout.avatarCircleImageView);
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getManager() != null && !bulkSampleModel.getRoom().getManager().getName().equals("")) {
                binding.centerIncludeLayout.nameSubTextView.setText(bulkSampleModel.getRoom().getManager().getName());
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getManager() != null && bulkSampleModel.getRoom().getManager().getAvatar() != null && bulkSampleModel.getRoom().getManager().getAvatar().getMedium() != null && !bulkSampleModel.getRoom().getManager().getAvatar().getMedium().getUrl().equals("")) {
                binding.centerIncludeLayout.charSubTextView.setVisibility(View.GONE);
                Picasso.get().load(bulkSampleModel.getRoom().getManager().getAvatar().getMedium().getUrl()).placeholder(R.color.coolGray100).into(binding.centerIncludeLayout.avatarSubCircleImageView);
            } else {
                binding.centerIncludeLayout.charSubTextView.setVisibility(View.VISIBLE);
                binding.centerIncludeLayout.charSubTextView.setText(StringManager.charsFirst(binding.centerIncludeLayout.nameSubTextView.getText().toString()));

                Picasso.get().load(R.color.coolGray100).placeholder(R.color.coolGray100).into(binding.centerIncludeLayout.avatarSubCircleImageView);
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getType().equals("personal_clinic")) {
                binding.centerIncludeLayout.subGroup.setVisibility(View.GONE);
            }

            if (bulkSampleModel.getScales().size() != 0) {
                scaleAdapter.setItems(bulkSampleModel.getScales().data());
                binding.scaleIncludeLayout.listRecyclerView.setAdapter(scaleAdapter);
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getCenter() != null && bulkSampleModel.getRoom().getCenter().getAcceptation() != null) {
                binding.descTextView.getRoot().setText(getResources().getString(R.string.BottomSheetBulkSampleDescription1));
                binding.nicknameIncludeLayout.getRoot().setVisibility(View.GONE);
            } else {
                String desc = getResources().getString(R.string.BottomSheetBulkSampleDescription1) + "\n" + getResources().getString(R.string.BottomSheetBulkSampleDescription2);

                binding.descTextView.getRoot().setText(desc);
                binding.nicknameIncludeLayout.getRoot().setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setData(String key, UserModel userModel, BulkSampleModel bulkSampleModel) {
        this.key = key;
        this.userModel = userModel;
        this.bulkSampleModel = bulkSampleModel;
    }

    private void setHashmap() {
        data.put("key", key);

        if (binding.nicknameIncludeLayout.getRoot().getVisibility() == View.VISIBLE)
            data.put("nickname", nickname);
        else
            data.remove("nickname");
    }

    private void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap();

        Sample.theory(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                AuthModel model = (AuthModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissDialogLoading();

                        IntentManager.test(requireActivity(), model.getKey());

                        dismiss();
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> ((ActivityMain) requireActivity()).validatoon.requestValid(response, binding));
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}