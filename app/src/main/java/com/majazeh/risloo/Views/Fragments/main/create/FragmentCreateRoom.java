package com.majazeh.risloo.views.fragments.main.create;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.databinding.FragmentCreateRoomBinding;
import com.majazeh.risloo.utils.managers.DialogManager;
import com.majazeh.risloo.utils.managers.InitManager;
import com.majazeh.risloo.utils.managers.SnackManager;
import com.majazeh.risloo.utils.managers.StringManager;
import com.majazeh.risloo.utils.widgets.CustomClickView;
import com.majazeh.risloo.views.activities.ActivityMain;
import com.mre.ligheh.API.Response;
import com.mre.ligheh.Model.Madule.Room;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.RoomModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.HashMap;

public class FragmentCreateRoom extends Fragment {

    // Binding
    private FragmentCreateRoomBinding binding;

    // Models
    public CenterModel centerModel;

    // Objects
    private HashMap data, header;

    // Vars
    public String psychologyId = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateRoomBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        setArgs();

        return binding.getRoot();
    }

    private void initializer() {
        data = new HashMap<>();
        header = new HashMap<>();
        header.put("Authorization", ((ActivityMain) requireActivity()).singleton.getAuthorization());

        binding.psychologyIncludeLayout.headerTextView.setText(getResources().getString(R.string.CreateRoomFragmentPsychologyHeader));

        InitManager.txtTextColorBackground(binding.createTextView.getRoot(), getResources().getString(R.string.CreateRoomFragmentButton), getResources().getColor(R.color.white), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        CustomClickView.onDelayedListener(() -> {
            DialogManager.showDialogSearchable(requireActivity(), "psychologies");
        }).widget(binding.psychologyIncludeLayout.selectTextView);

        CustomClickView.onDelayedListener(() -> {
            ((ActivityMain) requireActivity()).validatoon.hideValid(binding);

            doWork();
        }).widget(binding.createTextView.getRoot());
    }

    private void setArgs() {
        centerModel = (CenterModel) FragmentCreateRoomArgs.fromBundle(getArguments()).getCenterModel();
        setData(centerModel);

        TypeModel typeModel = FragmentCreateRoomArgs.fromBundle(getArguments()).getTypeModel();

        if (typeModel != null) {
            if (StringManager.suffix(typeModel.getClass().getName(), '.').equals("UserModel"))
                setData((UserModel) typeModel);
        }
    }

    private void setData(CenterModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            data.put("id", model.getId());
        }
    }

    private void setData(UserModel model) {
        if (model.getId() != null && !model.getId().equals("")) {
            psychologyId = model.getId();
        }

        if (model.getName() != null && !model.getName().equals("")) {
            binding.psychologyIncludeLayout.selectTextView.setText(model.getName());
        }
    }

    public void responseDialog(String method, TypeModel item) {
        switch (method) {
            case "psychologies": {
                UserModel model = (UserModel) item;

                if (!psychologyId.equals(model.getId())) {
                    psychologyId = model.getId();

                    binding.psychologyIncludeLayout.selectTextView.setText(model.getName());
                } else if (psychologyId.equals(model.getId())) {
                    psychologyId = "";

                    binding.psychologyIncludeLayout.selectTextView.setText("");
                }

                DialogManager.dismissDialogSearchable();
            } break;
        }
    }

    private void setHashmap() {
        if (!psychologyId.equals(""))
            data.put("psychologist_id", psychologyId);
        else
            data.remove("psychologist_id");
    }

    private void doWork() {
        DialogManager.showDialogLoading(requireActivity(), "");

        setHashmap();

        Room.create(data, header, new Response() {
            @Override
            public void onOK(Object object) {
                RoomModel roomModel = (RoomModel) object;

                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> {
                        DialogManager.dismissDialogLoading();
                        SnackManager.showSnackSucces(requireActivity(), getResources().getString(R.string.SnackCreatedNewRoom));

                        ((ActivityMain) requireActivity()).navigatoon.navigateToFragmentRoom(roomModel);
                    });
                }
            }

            @Override
            public void onFailure(String response) {
                if (isAdded()) {
                    requireActivity().runOnUiThread(() -> ((ActivityMain) requireActivity()).validatoon.showValid(response, binding));
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