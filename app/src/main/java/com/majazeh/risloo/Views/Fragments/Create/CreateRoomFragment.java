package com.majazeh.risloo.Views.Fragments.Create;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Dialogs.SearchableDialog;
import com.majazeh.risloo.databinding.FragmentCreateRoomBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class CreateRoomFragment extends Fragment {

    // Binding
    private FragmentCreateRoomBinding binding;

    // Dialogs
    private SearchableDialog psychologiesDialog;

    // Vars
    private String centerId = "";
    public String psychologyId = "", psychologyName = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateRoomBinding.inflate(inflater, viewGroup, false);

        initializer();

        detector();

        listener();

        setData();

        return binding.getRoot();
    }

    private void initializer() {
        psychologiesDialog = new SearchableDialog();

        binding.psychologyIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateRoomFragmentPsychologyHeader));

        binding.psychologyIncludeLayout.selectTextView.setHint(getResources().getString(R.string.CreateRoomFragmentPsychologyHint));

        InitManager.txtTextColor(binding.createTextView.getRoot(), getResources().getString(R.string.CreateRoomFragmentButton), getResources().getColor(R.color.White));
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        } else {
            binding.createTextView.getRoot().setBackgroundResource(R.drawable.draw_16sdp_solid_blue500);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        ClickManager.onDelayedClickListener(() -> {
            psychologiesDialog.show(requireActivity().getSupportFragmentManager(), "psychologiesDialog");
            psychologiesDialog.setData("psychologies");
        }).widget(binding.psychologyIncludeLayout.selectTextView);

        ClickManager.onDelayedClickListener(() -> {
            if (psychologyId.equals("")) {
                ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.psychologyIncludeLayout.selectTextView, binding.psychologyErrorLayout.getRoot(), binding.psychologyErrorLayout.errorTextView, getResources().getString(R.string.AppInputEmpty));
            } else {
                ((MainActivity) requireActivity()).controlEditText.check(requireActivity(), binding.psychologyIncludeLayout.selectTextView, binding.psychologyErrorLayout.getRoot(), binding.psychologyErrorLayout.errorTextView);
                doWork();
            }
        }).widget(binding.createTextView.getRoot());
    }

    private void setData() {
        if (getArguments() != null) {
            if (getArguments().getString("id") != null) {
                centerId = getArguments().getString("id");
            }

            if (getArguments().getString("psychology_id") != null && !getArguments().getString("psychology_id").equals("") && getArguments().getString("psychology_name") != null && !getArguments().getString("psychology_name").equals("")) {
                psychologyId = getArguments().getString("psychology_id");
                psychologyName = getArguments().getString("psychology_name");
                binding.psychologyIncludeLayout.selectTextView.setText(psychologyName);
            }
        }
    }

    public void responseDialog(String method, TypeModel item) {
        switch (method) {
            case "psychologies":
                UserModel model = (UserModel) item;

                if (!psychologyId.equals(model.getUserId())) {
                    psychologyId = model.getUserId();
                    psychologyName = model.getName();

                    binding.psychologyIncludeLayout.selectTextView.setText(psychologyName);
                } else if (psychologyId.equals(model.getUserId())) {
                    psychologyId = "";
                    psychologyName = "";

                    binding.psychologyIncludeLayout.selectTextView.setText("");
                }

                psychologiesDialog.dismiss();
                break;
        }
    }

    private void doWork() {
        ((MainActivity) requireActivity()).loadingDialog.show(requireActivity().getSupportFragmentManager(), "loadingDialog");

        HashMap data = new HashMap<>();
        data.put("id", centerId);
        data.put("psychology_id", psychologyId);

        HashMap header = new HashMap<>();
        header.put("Authorization", ((MainActivity) requireActivity()).singleton.getAuthorization());

        Room.create(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        ((MainActivity) requireActivity()).loadingDialog.dismiss();
                        Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.AppAdded), Toast.LENGTH_SHORT).show();
                        ((MainActivity) requireActivity()).navigator(R.id.centerFragment);
                    });
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
                                        if (key.equals("psychology_id")) {
                                            ((MainActivity) requireActivity()).controlEditText.error(requireActivity(), binding.psychologyIncludeLayout.selectTextView, binding.psychologyErrorLayout.getRoot(), binding.psychologyErrorLayout.errorTextView, (String) jsonObject.getJSONObject("errors").getJSONArray(key).get(i));
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