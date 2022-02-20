package com.majazeh.risloo.views.fragments.main.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.ResultManager;
import com.majazeh.risloo.utils.managers.SheetManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.managers.ToastManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.MainActivity;
import com.majazeh.risloo.views.fragments.main.edit.EditUserFragment;
import com.majazeh.risloo.databinding.FragmentEditUserTabAvatarBinding;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Auth;
import com.mre.ligheh.Model.Madule.User;
import com.mre.ligheh.Model.TypeModel.AuthModel;
import com.mre.ligheh.Model.TypeModel.UserModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class EditUserTabAvatarFragment extends Fragment {

    // Binding
    private FragmentEditUserTabAvatarBinding binding;

    // Fragments
    private Fragment current;

    // Objects
    private HashMap data, header;

    // Vars
    private File avatarFile = null;
    public String avatarPath = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditUserTabAvatarBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        current = ((MainActivity) requireActivity()).fragmont.getCurrent();

        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        binding.avatarGuideLayout.guideTextView.setText(getResources().getString(R.string.EditUserTabAvatarAvatarGuide));

        InitManager.txtTextColorBackground(binding.editTextView.getRoot(), getResources().getString(R.string.EditUserTabAvatarButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            SheetManager.showImageBottomSheet(requireActivity());
        }).widget(binding.avatarIncludeLayout.avatarCircleImageView);

        CustomClickView.onDelayedListener(() -> {
            if (avatarFile == null) {
                if (!avatarPath.equals(""))
                    ToastManager.showErrorToast(requireActivity(), getResources().getString(R.string.ToastImageNewNotSelected));
                else
                    ToastManager.showErrorToast(requireActivity(), getResources().getString(R.string.ToastImageNotSelected));
            } else {
                doWork();
            }
        }).widget(binding.editTextView.getRoot());
    }

    private void setData() {
        if (current instanceof EditUserFragment) {
            UserModel model = ((EditUserFragment) current).userModel;

            if (model.getId() != null && !model.getId().equals("")) {
                data.put("id", model.getId());
            }

            if (model.getAvatar() != null && model.getAvatar().getMedium() != null && model.getAvatar() .getMedium().getUrl() != null && !model.getAvatar().getMedium().getUrl().equals("")) {
                avatarPath = model.getAvatar().getMedium().getUrl();

                binding.avatarIncludeLayout.charTextView.setVisibility(View.GONE);
                Picasso.get().load(avatarPath).placeholder(R.color.coolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
            } else {
                binding.avatarIncludeLayout.charTextView.setVisibility(View.VISIBLE);
                if (model.getName() != null && !model.getName().equals(""))
                    binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(model.getName()));
                else if (model.getId() != null && !model.getId().equals(""))
                    binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(model.getId()));
                else
                    binding.avatarIncludeLayout.charTextView.setText(StringManager.firstChars(getResources().getString(R.string.AppDefaultUnknown)));

                Picasso.get().load(R.color.coolGray100).placeholder(R.color.coolGray100).into(binding.avatarIncludeLayout.avatarCircleImageView);
            }
        }
    }

    public void responseAction(String method, Intent data) {
        switch (method) {
            case "gallery":
                ResultManager.galleryResult(requireActivity(), data);
                break;
            case "camera":
                ResultManager.cameraResult(requireActivity(), avatarPath);
                break;
            case "crop":
                ResultManager.cropResult(requireActivity(), data, binding.avatarIncludeLayout.avatarCircleImageView, binding.avatarIncludeLayout.charTextView);

                avatarFile = ResultManager.file;
                avatarPath = ResultManager.path;
                break;
        }
    }

    private void setHashmap() {
        if (avatarFile != null)
            data.put("avatar", avatarFile);
        else
            data.remove("avatar");
    }

    private void doWork() {
        DialogManager.showLoadingDialog(requireActivity(), "");

        setHashmap();

        if (Objects.equals(data.get("id"), ((MainActivity) requireActivity()).singleton.getUserModel().getId())) {
            Auth.changeAvatar(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    AuthModel authModel = (AuthModel) object;
                    UserModel userModel = authModel.getUser();

                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            ((MainActivity) requireActivity()).singleton.params(userModel);
                            ((MainActivity) requireActivity()).setData();

                            DialogManager.dismissLoadingDialog();
                            SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.SnackChangesSaved));
                        });

                        if (avatarFile != null)
                            avatarFile.delete();
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

                                        if (key.equals("avatar"))
                                            ((MainActivity) requireActivity()).validatoon.showValid(binding.avatarErrorLayout.getRoot(), binding.avatarErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
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
        } else {
            User.changeAvatar(data, header, new Response() {
                @Override
                public void onOK(Object object) {
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> {
                            DialogManager.dismissLoadingDialog();
                            SnackManager.showSuccesSnack(requireActivity(), getResources().getString(R.string.SnackChangesSaved));
                        });

                        if (avatarFile != null)
                            avatarFile.delete();
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

                                        if (key.equals("avatar"))
                                            ((MainActivity) requireActivity()).validatoon.showValid(binding.avatarErrorLayout.getRoot(), binding.avatarErrorLayout.errorTextView, keyErrors.substring(0, keyErrors.length() - 1));
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

        if (avatarFile != null)
            avatarFile.delete();
    }

}