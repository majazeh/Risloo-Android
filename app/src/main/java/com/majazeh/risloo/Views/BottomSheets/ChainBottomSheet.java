package com.majazeh.risloo.Views.BottomSheets;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.TestsAdapter;
import com.majazeh.risloo.databinding.BottomSheetChainBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.BulkSampleModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.HashMap;

public class ChainBottomSheet extends BottomSheetDialogFragment {

    // Binding
    private BottomSheetChainBinding binding;

    // Adapters
    private TestsAdapter testAdapter;

    // Models
    private BulkSampleModel bulkSampleModel;

    // Objects
    private HashMap data, header;

    // Vars
    private String key, name, avatar;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetChainBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        detector();

        setDialog();

        return binding.getRoot();
    }

    private void initializer() {
        testAdapter = new TestsAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.listRecyclerView, getResources().getDimension(R.dimen._6sdp), 0, getResources().getDimension(R.dimen._2sdp), 0);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.entryButton.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.nicknameEditText.getRoot().setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.nicknameEditText.getRoot().hasFocus())
                ((MainActivity) requireActivity()).inputManager.select(requireActivity(), binding.nicknameEditText.getRoot());
            return false;
        });

        binding.nicknameEditText.getRoot().setOnFocusChangeListener((v, hasFocus) -> {
            name = binding.nicknameEditText.getRoot().getText().toString().trim();
        });

        ClickManager.onDelayedClickListener(() -> {
            ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

            data.put("key", key);

            if (binding.nicknameGroup.getVisibility() == View.VISIBLE)
                data.put("nickname", name);

            Sample.theory(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    AuthModel model = (AuthModel) object;

                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            key = model.getKey();
                            ((MainActivity) requireActivity()).loadingDialog.dismiss();

                            IntentManager.test(requireActivity(), key);

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
        }).widget(binding.entryButton);
    }

    private void setDialog() {
        try {
            if (!name.equals("")) {
                binding.nicknameEditText.getRoot().setText(name);
            } else {
                binding.nicknameEditText.getRoot().setText(getResources().getString(R.string.AppDefaultName));
            }

            if (!avatar.equals("")) {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(avatar).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nicknameEditText.getRoot().getText().toString()));

                Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getRoomCenter() != null && bulkSampleModel.getRoom().getRoomCenter().getDetail() != null && bulkSampleModel.getRoom().getRoomCenter().getDetail().has("title") && !bulkSampleModel.getRoom().getRoomCenter().getDetail().getString("title").equals("")) {
                binding.centerTextView.setText(bulkSampleModel.getRoom().getRoomCenter().getDetail().getString("title"));
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getRoomManager() != null && bulkSampleModel.getRoom().getRoomManager().getName() != null) {
                binding.psychologyTextView.setText(bulkSampleModel.getRoom().getRoomManager().getName());
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getRoomCenter() != null && bulkSampleModel.getRoom().getRoomCenter().getDetail() != null && bulkSampleModel.getRoom().getRoomCenter().getDetail().has("avatar") && !bulkSampleModel.getRoom().getRoomCenter().getDetail().getString("avatar").equals("") && bulkSampleModel.getRoom().getRoomCenter().getDetail().getJSONArray("avatar").length() != 0) {
                binding.avatarsIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(bulkSampleModel.getRoom().getRoomCenter().getDetail().getJSONArray("avatar").getJSONObject(2).getString("url")).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarsIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarsIncludeLayout.charTextView.setText(StringManager.firstChars(binding.centerTextView.getText().toString()));

                Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarCircleImageView);
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getRoomManager() != null && bulkSampleModel.getRoom().getRoomManager().getAvatar() != null && bulkSampleModel.getRoom().getRoomManager().getAvatar().getMedium() != null && bulkSampleModel.getRoom().getRoomManager().getAvatar().getMedium().getUrl() != null) {
                binding.avatarsIncludeLayout.charSubTextView.setVisibility(View.GONE);
                Picasso.get().load(bulkSampleModel.getRoom().getRoomManager().getAvatar().getMedium().getUrl()).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarSubCircleImageView);
            } else {
                binding.avatarsIncludeLayout.charSubTextView.setVisibility(View.VISIBLE);
                binding.avatarsIncludeLayout.charSubTextView.setText(StringManager.firstChars(binding.psychologyTextView.getText().toString()));

                Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarsIncludeLayout.avatarSubCircleImageView);
            }

            if (bulkSampleModel.getScales() != null) {
                testAdapter.setItems(bulkSampleModel.getScales().data());
                binding.listRecyclerView.setAdapter(testAdapter);
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getRoomCenter() != null && bulkSampleModel.getRoom().getRoomCenter().getAcceptation() != null) {
                binding.descriptionTextView.setText(getResources().getString(R.string.BottomSheetChainDescription1));
                binding.nicknameGroup.setVisibility(View.GONE);
            } else {
                binding.descriptionTextView.setText(getResources().getString(R.string.BottomSheetChainDescription1) + "\n" + getResources().getString(R.string.BottomSheetChainDescription2));
                binding.nicknameGroup.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setData(String key, String name, String avatar, BulkSampleModel model) {
        this.key = key;
        this.name = name;
        this.avatar = avatar;
        this.bulkSampleModel = model;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}