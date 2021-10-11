package com.majazeh.risloo.Views.BottomSheets;

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
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Sheet.SheetSampleAdapter;
import com.majazeh.risloo.databinding.BottomSheetBulkSampleBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.BulkSampleModel;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.HashMap;

public class BulkSampleBottomSheet extends BottomSheetDialogFragment {

    // Binding
    private BottomSheetBulkSampleBinding binding;

    // Adapters
    private SheetSampleAdapter sampleAdapter;

    // Models
    private UserModel userModel;
    private BulkSampleModel bulkSampleModel;

    // Objects
    private HashMap data, header;

    // Vars
    private String key, nickname;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetBulkSampleBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setDialog();

        return binding.getRoot();
    }

    private void initializer() {
        sampleAdapter = new SheetSampleAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.listRecyclerView, getResources().getDimension(R.dimen._6sdp), 0, getResources().getDimension(R.dimen._2sdp), 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.nicknameEditText.getRoot().setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.nicknameEditText.getRoot().hasFocus())
                ((MainActivity) requireActivity()).inputor.select(requireActivity(), binding.nicknameEditText.getRoot());
            return false;
        });

        binding.nicknameEditText.getRoot().setOnFocusChangeListener((v, hasFocus) -> {
            nickname = binding.nicknameEditText.getRoot().getText().toString().trim();
        });

        CustomClickView.onDelayedListener(this::doWork).widget(binding.entryButton);
    }

    private void setDialog() {
        try {
            if (userModel.getName() != null && !userModel.getName().equals("")) {
                binding.nicknameEditText.getRoot().setText(userModel.getName());
            } else if (userModel.getId() != null && !userModel.getId().equals("")) {
                binding.nicknameEditText.getRoot().setText(userModel.getId());
            } else {
                binding.nicknameEditText.getRoot().setText("");
            }

            if (userModel.getAvatar() != null && userModel.getAvatar().getMedium() != null && userModel.getAvatar().getMedium().getUrl() != null && !userModel.getAvatar().getMedium().getUrl().equals("")) {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(userModel.getAvatar().getMedium().getUrl()).placeholder(R.color.CoolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nicknameEditText.getRoot().getText().toString()));

                Picasso.get().load(R.color.CoolGray100).placeholder(R.color.CoolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getRoomCenter() != null && bulkSampleModel.getRoom().getRoomCenter().getDetail() != null && bulkSampleModel.getRoom().getRoomCenter().getDetail().has("title") && !bulkSampleModel.getRoom().getRoomCenter().getDetail().getString("title").equals("")) {
                binding.centerTextView.setText(bulkSampleModel.getRoom().getRoomCenter().getDetail().getString("title"));
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getRoomManager() != null && bulkSampleModel.getRoom().getRoomManager().getName() != null) {
                binding.psychologyTextView.setText(bulkSampleModel.getRoom().getRoomManager().getName());
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getRoomCenter() != null && bulkSampleModel.getRoom().getRoomCenter().getDetail() != null && bulkSampleModel.getRoom().getRoomCenter().getDetail().has("avatar") && !bulkSampleModel.getRoom().getRoomCenter().getDetail().getString("avatar").equals("") && bulkSampleModel.getRoom().getRoomCenter().getDetail().getJSONArray("avatar").length() != 0) {
                binding.avatarsIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(bulkSampleModel.getRoom().getRoomCenter().getDetail().getJSONArray("avatar").getJSONObject(2).getString("url")).placeholder(R.color.CoolGray100).into(binding.avatarsIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarsIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarsIncludeLayout.charTextView.setText(StringManager.firstChars(binding.centerTextView.getText().toString()));

                Picasso.get().load(R.color.CoolGray100).placeholder(R.color.CoolGray100).into(binding.avatarsIncludeLayout.avatarCircleImageView);
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getRoomManager() != null && bulkSampleModel.getRoom().getRoomManager().getAvatar() != null && bulkSampleModel.getRoom().getRoomManager().getAvatar().getMedium() != null && bulkSampleModel.getRoom().getRoomManager().getAvatar().getMedium().getUrl() != null) {
                binding.avatarsIncludeLayout.charSubTextView.setVisibility(View.GONE);
                Picasso.get().load(bulkSampleModel.getRoom().getRoomManager().getAvatar().getMedium().getUrl()).placeholder(R.color.CoolGray100).into(binding.avatarsIncludeLayout.avatarSubCircleImageView);
            } else {
                binding.avatarsIncludeLayout.charSubTextView.setVisibility(View.VISIBLE);
                binding.avatarsIncludeLayout.charSubTextView.setText(StringManager.firstChars(binding.psychologyTextView.getText().toString()));

                Picasso.get().load(R.color.CoolGray100).placeholder(R.color.CoolGray100).into(binding.avatarsIncludeLayout.avatarSubCircleImageView);
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getRoomType() != null && bulkSampleModel.getRoom().getRoomType().equals("personal_clinic")) {
                binding.psychologyTextView.setVisibility(View.GONE);
                binding.avatarsIncludeLayout.subGroup.setVisibility(View.GONE);
            }

            if (bulkSampleModel.getScales() != null) {
                sampleAdapter.setItems(bulkSampleModel.getScales().data());
                binding.listRecyclerView.setAdapter(sampleAdapter);
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getRoomCenter() != null && bulkSampleModel.getRoom().getRoomCenter().getAcceptation() != null) {
                binding.descriptionTextView.setText(getResources().getString(R.string.BottomSheetBulkSampleDescription1));
                binding.nicknameGroup.setVisibility(View.GONE);
            } else {
                binding.descriptionTextView.setText(getResources().getString(R.string.BottomSheetBulkSampleDescription1) + "\n" + getResources().getString(R.string.BottomSheetBulkSampleDescription2));
                binding.nicknameGroup.setVisibility(View.VISIBLE);
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

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        data.put("key", key);

        if (binding.nicknameGroup.getVisibility() == View.VISIBLE)
            data.put("nickname", nickname);

        Sample.theory(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                AuthModel model = (AuthModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissLoadingDialog();

                        IntentManager.test(requireActivity(), model.getKey());

                        dismiss();
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        // TODO : Place Code If Needed
                    });
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