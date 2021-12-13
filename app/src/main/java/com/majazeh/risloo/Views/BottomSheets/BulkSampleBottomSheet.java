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
import com.majazeh.risloo.Utils.Managers.SnackManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.IntentManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Views.Activities.AuthActivity;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Adapters.Recycler.Sheet.SheetScaleAdapter;
import com.majazeh.risloo.databinding.BottomSheetBulkSampleBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Sample;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.BulkSampleModel;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class BulkSampleBottomSheet extends BottomSheetDialogFragment {

    // Binding
    private BottomSheetBulkSampleBinding binding;

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
        binding = BottomSheetBulkSampleBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setDialog();

        return binding.getRoot();
    }

    private void initializer() {
        scaleAdapter = new SheetScaleAdapter(requireActivity());

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.listTextView.getRoot().setText(getResources().getString(R.string.BottomSheetBulkSampleList));
        binding.nicknameTextView.getRoot().setText(getResources().getString(R.string.BottomSheetBulkSampleNickname));

        InitManager.unfixedVerticalRecyclerView(requireActivity(), binding.listRecyclerView.getRoot(), getResources().getDimension(R.dimen._6sdp), 0, getResources().getDimension(R.dimen._2sdp), 0);

        InitManager.txtTextColorBackground(binding.entryTextView.getRoot(), getResources().getString(R.string.BottomSheetBulkSampleEntry), getResources().getColor(R.color.White), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.nicknameEditText.getRoot().setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.nicknameEditText.getRoot().hasFocus())
                ((MainActivity) requireActivity()).inputon.select(binding.nicknameEditText.getRoot());
            return false;
        });

        binding.nicknameEditText.getRoot().setOnFocusChangeListener((v, hasFocus) -> {
            nickname = binding.nicknameEditText.getRoot().getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.nicknameErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.nicknameErrorLayout.getRoot(), binding.nicknameErrorLayout.errorTextView);

            doWork();
        }).widget(binding.entryTextView.getRoot());
    }

    private void setDialog() {
        try {
            if (!userModel.getName().equals("")) {
                binding.nicknameEditText.getRoot().setText(userModel.getName());
            } else if (!userModel.getId().equals("")) {
                binding.nicknameEditText.getRoot().setText(userModel.getId());
            } else {
                binding.nicknameEditText.getRoot().setText("");
            }

            if (userModel.getAvatar() != null && userModel.getAvatar().getMedium() != null && !userModel.getAvatar().getMedium().getUrl().equals("")) {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(userModel.getAvatar().getMedium().getUrl()).placeholder(R.color.CoolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(binding.nicknameEditText.getRoot().getText().toString()));

                Picasso.get().load(R.color.CoolGray100).placeholder(R.color.CoolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getCenter() != null && bulkSampleModel.getRoom().getCenter().getDetail() != null && bulkSampleModel.getRoom().getCenter().getDetail().has("title") && !bulkSampleModel.getRoom().getCenter().getDetail().getString("title").equals("")) {
                binding.centerIncludeLayout.nameTextView.setText(bulkSampleModel.getRoom().getCenter().getDetail().getString("title"));
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getCenter() != null && bulkSampleModel.getRoom().getCenter().getDetail() != null && bulkSampleModel.getRoom().getCenter().getDetail().has("avatar") && !bulkSampleModel.getRoom().getCenter().getDetail().getString("avatar").equals("") && bulkSampleModel.getRoom().getCenter().getDetail().getJSONArray("avatar").length() != 0) {
                binding.centerIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(bulkSampleModel.getRoom().getCenter().getDetail().getJSONArray("avatar").getJSONObject(2).getString("url")).placeholder(R.color.CoolGray100).into(binding.centerIncludeLayout.avatarCircleImageView);
            } else {
                binding.centerIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                binding.centerIncludeLayout.charTextView.setText(StringManager.firstChars(binding.centerIncludeLayout.nameTextView.getText().toString()));

                Picasso.get().load(R.color.CoolGray100).placeholder(R.color.CoolGray100).into(binding.centerIncludeLayout.avatarCircleImageView);
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getManager() != null && !bulkSampleModel.getRoom().getManager().getName().equals("")) {
                binding.centerIncludeLayout.nameSubTextView.setText(bulkSampleModel.getRoom().getManager().getName());
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getManager() != null && bulkSampleModel.getRoom().getManager().getAvatar() != null && bulkSampleModel.getRoom().getManager().getAvatar().getMedium() != null && !bulkSampleModel.getRoom().getManager().getAvatar().getMedium().getUrl().equals("")) {
                binding.centerIncludeLayout.charSubTextView.setVisibility(View.GONE);
                Picasso.get().load(bulkSampleModel.getRoom().getManager().getAvatar().getMedium().getUrl()).placeholder(R.color.CoolGray100).into(binding.centerIncludeLayout.avatarSubCircleImageView);
            } else {
                binding.centerIncludeLayout.charSubTextView.setVisibility(View.VISIBLE);
                binding.centerIncludeLayout.charSubTextView.setText(StringManager.firstChars(binding.centerIncludeLayout.nameSubTextView.getText().toString()));

                Picasso.get().load(R.color.CoolGray100).placeholder(R.color.CoolGray100).into(binding.centerIncludeLayout.avatarSubCircleImageView);
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getType().equals("personal_clinic")) {
                binding.centerIncludeLayout.subGroup.setVisibility(View.GONE);
            }

            if (bulkSampleModel.getScales().size() != 0) {
                scaleAdapter.setItems(bulkSampleModel.getScales().data());
                binding.listRecyclerView.getRoot().setAdapter(scaleAdapter);
            }

            if (bulkSampleModel.getRoom() != null && bulkSampleModel.getRoom().getCenter() != null && bulkSampleModel.getRoom().getCenter().getAcceptation() != null) {
                binding.descTextView.getRoot().setText(getResources().getString(R.string.BottomSheetBulkSampleDescription1));
                binding.nicknameGroup.setVisibility(View.GONE);
            } else {
                binding.descTextView.getRoot().setText(getResources().getString(R.string.BottomSheetBulkSampleDescription1) + "\n" + getResources().getString(R.string.BottomSheetBulkSampleDescription2));
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

    private void setHashmap() {
        data.put("key", key);

        if (binding.nicknameGroup.getVisibility() == View.VISIBLE)
            data.put("nickname", nickname);
        else
            data.remove("nickname");
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        setHashmap();

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
                        try {
                            JSONObject responseObject = new JSONObject(response);
                            if (!responseObject.isNull("errors")) {
                                JSONObject errorsObject = responseObject.getJSONObject("errors");

                                Iterator<String> keys = (errorsObject.keys());
                                StringBuilder allErrors = new StringBuilder();

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    StringBuilder keyErrors = new StringBuilder();

                                    for (int i = 0; i < errorsObject.getJSONArray(key).length(); i++) {
                                        String error = errorsObject.getJSONArray(key).getString(i);

                                        keyErrors.append(error);
                                        keyErrors.append("\n");

                                        allErrors.append(error);
                                        allErrors.append("\n");
                                    }

                                    if (key.equals("nickname"))
                                        ((AuthActivity) requireActivity()).validatoon.showValid(binding.nicknameErrorLayout.getRoot(), binding.nicknameErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
                                }

                                SnackManager.showErrorSnack(requireActivity(), allErrors.substring(0, allErrors.length() - 1));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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