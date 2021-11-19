package com.majazeh.risloo.Views.Fragments.Main.Tab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.DialogManager;
import com.majazeh.risloo.Utils.Widgets.CustomClickView;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Main.Edit.EditUserFragment;
import com.majazeh.risloo.databinding.FragmentEditUserTabCryptoBinding;
import com.mre.ligheh.Model.TypeModel.UserModel;

import java.util.HashMap;

public class EditUserTabCryptoFragment extends Fragment {

    // Binding
    private FragmentEditUserTabCryptoBinding binding;

    // Fragments
    private Fragment current;

    // Objects
    private HashMap data, header;

    // Vars
    private String publicKey = "", privateKey = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup,  @Nullable Bundle savedInstanceState) {
        binding = FragmentEditUserTabCryptoBinding.inflate(inflater, viewGroup, false);

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

        binding.publicIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserTabCryptoPublicHeader));
        binding.privateIncludeLayout.headerTextView.setText(getResources().getString(R.string.EditUserTabCryptoPrivateHeader));

        InitManager.txtTextColorBackground(binding.publicEditTextView.getRoot(), getResources().getString(R.string.EditUserTabCryptoFragmentButton), getResources().getColor(R.color.White), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
        InitManager.txtTextColorBackground(binding.privateEditTextView.getRoot(), getResources().getString(R.string.EditUserTabCryptoFragmentButton), getResources().getColor(R.color.White), R.drawable.draw_24sdp_solid_risloo500_ripple_risloo700);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.publicIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.publicIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(binding.publicIncludeLayout.inputEditText);
            return false;
        });

        binding.privateIncludeLayout.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction() && !binding.privateIncludeLayout.inputEditText.hasFocus())
                ((MainActivity) requireActivity()).inputon.select(binding.privateIncludeLayout.inputEditText);
            return false;
        });

        binding.publicIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            publicKey = binding.publicIncludeLayout.inputEditText.getText().toString().trim();
        });

        binding.privateIncludeLayout.inputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            privateKey = binding.privateIncludeLayout.inputEditText.getText().toString().trim();
        });

        CustomClickView.onDelayedListener(() -> {
            if (binding.publicErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.publicErrorLayout.getRoot(), binding.publicErrorLayout.errorTextView);

            doWork("public");
        }).widget(binding.publicEditTextView.getRoot());

        CustomClickView.onDelayedListener(() -> {
            if (binding.privateErrorLayout.getRoot().getVisibility() == View.VISIBLE)
                ((MainActivity) requireActivity()).validatoon.hideValid(binding.privateErrorLayout.getRoot(), binding.privateErrorLayout.errorTextView);

            doWork("private");
        }).widget(binding.privateEditTextView.getRoot());
    }

    private void setData() {
        if (current instanceof EditUserFragment) {
            UserModel model = ((EditUserFragment) current).userModel;

            if (model.getId() != null && !model.getId().equals("")) {
                data.put("id", model.getId());
            }

            if (model.getPublic_key() != null && !model.getPublic_key().equals("")) {
                publicKey = model.getPublic_key();
                binding.publicIncludeLayout.inputEditText.setText(publicKey);
            }

//            if (model.getPrivate_key() != null && !model.getPrivate_key().equals("")) {
//                privateKey = model.getPrivate_key();
//                binding.privateIncludeLayout.inputEditText.setText(privateKey);
//            }
        }
    }

    private void doWork(String key) {
        DialogManager.showLoadingDialog(requireActivity(), "");

        if (key.equals("public")) {
            // TODO : Place Code Here
        } else {
            // TODO : Place Code Here
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}