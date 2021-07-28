package com.majazeh.risloo.Views.Fragments.Tab;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.BitmapManager;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.FileManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.ResultManager;
import com.majazeh.risloo.Utils.Managers.StringManager;
import com.majazeh.risloo.Utils.Managers.ToastManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.BottomSheets.ImageBottomSheet;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterFragment;
import com.majazeh.risloo.databinding.FragmentEditCenterAvatarBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Center;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class EditCenterAvatarFragment extends Fragment {

    // Binding
    private FragmentEditCenterAvatarBinding binding;

    // BottomSheets
    private ImageBottomSheet imageBottomSheet;

    // Fragments
    private Fragment current;

    // Objects
    private Bitmap avatarBitmap = null;
    private HashMap data, header;

    // Vars
    public String avatarPath = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditCenterAvatarBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        imageBottomSheet = new ImageBottomSheet();

        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.avatarGuideLayout.guideTextView.setText(getResources().getString(R.string.EditCenterAvatarTabAvatarGuide));

        InitManager.txtTextColor(binding.editTextView.getRoot(), getResources().getString(R.string.EditCenterAvatarTabButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.editTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            imageBottomSheet.show(requireActivity().getSupportFragmentManager(), "imageBottomSheet");
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);

        ClickManager.onDelayedClickListener(() -> {
            if (avatarBitmap == null) {
                if (!avatarPath.equals(""))
                    ToastManager.showToast(requireActivity(), getResources().getString(R.string.ToastNewImageNotSelected));
                else
                    ToastManager.showToast(requireActivity(), getResources().getString(R.string.ToastImageIsEmpty));
            } else {
                doWork();
            }
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (current instanceof EditCenterFragment) {
            CenterModel model = ((EditCenterFragment) current).centerModel;

            try {
                if (model.getCenterId() != null && !model.getCenterId().equals("")) {
                    data.put("id", model.getCenterId());
                }

                if (model.getDetail().has("avatar") && !model.getDetail().isNull("avatar") && model.getDetail().getJSONArray("avatar").length() != 0) {
                    avatarPath = model.getDetail().getJSONArray("avatar").getJSONObject(2).getString("url");

                    binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                    Picasso.get().load(avatarPath).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
                } else {
                    binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                    if (model.getDetail().has("title") && !model.getDetail().isNull("title") && !model.getDetail().getString("title").equals(""))
                        binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(model.getDetail().getString("title")));
                    else
                        binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(getResources().getString(R.string.AppDefaultCenter)));

                    Picasso.get().load(R.color.Gray50).placeholder(R.color.Gray50).into(binding.avatarIncludeLayout.avatarCircleImageView);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void responseAction(String method, Intent data) {
        ResultManager resultManager = new ResultManager();

        switch (method) {
            case "gallery":
                resultManager.galleryResult(requireActivity(), data, binding.avatarIncludeLayout.avatarCircleImageView, binding.avatarIncludeLayout.charTextView);

                avatarPath = resultManager.path;
                avatarBitmap = resultManager.bitmap;
                break;
            case "camera":
                resultManager.cameraResult(requireActivity(), avatarPath, binding.avatarIncludeLayout.avatarCircleImageView, binding.avatarIncludeLayout.charTextView);

                avatarPath = resultManager.path;
                avatarBitmap = resultManager.bitmap;
                break;
        }
    }

    private void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        FileManager.writeBitmapToCache(requireActivity(), BitmapManager.modifyOrientation(avatarBitmap, avatarPath), "image");
        if (FileManager.readFileFromCache(requireActivity(), "image") != null)
            data.put("avatar", FileManager.readFileFromCache(requireActivity(), "image"));

        Center.editAvatar(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
                        ToastManager.showToast(requireActivity(), getResources().getString(R.string.ToastChangesSaved));
                    });

                    FileManager.deleteFileFromCache(requireActivity(), "image");
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.isNull("errors")) {
                                Iterator<String> keys = (jsonObject.getJSONObject("errors").keys());

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    for (int i = 0; i < jsonObject.getJSONObject("errors").getJSONArray(key).length(); i++) {
                                        if (key.equals("avatar")) {
                                            ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), (LinearLayout) null, binding.avatarErrorLayout.getRoot(), binding.avatarErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
                                        }
                                    }
                                }
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