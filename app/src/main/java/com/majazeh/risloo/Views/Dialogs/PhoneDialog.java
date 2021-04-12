package com.majazeh.risloo.Views.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Managers.ClickManager;
import com.majazeh.risloo.Utils.Managers.InitManager;
import com.majazeh.risloo.Utils.Managers.ParamsManager;
import com.majazeh.risloo.Utils.Widgets.ItemDecorateRecyclerView;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.CreateCenterFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterDetailFragment;
import com.majazeh.risloo.Views.Fragments.Edit.EditCenterFragment;
import com.majazeh.risloo.databinding.DialogPhoneBinding;

import java.util.Objects;

public class PhoneDialog extends AppCompatDialogFragment {

    // Binding
    private DialogPhoneBinding binding;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager layoutManager;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireActivity(), R.style.DialogTheme);

        DialogPhoneBinding binding = DialogPhoneBinding.inflate(LayoutInflater.from(requireActivity()));

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(binding.getRoot());
        dialog.setCancelable(true);
        dialog.getWindow().setAttributes(ParamsManager.apply(dialog));

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        binding = DialogPhoneBinding.inflate(inflater, viewGroup, false);

        initializer();

        listener();

        detector();

        setRecyclerView();

        return binding.getRoot();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        clearEditText();
    }

    private void initializer() {
        itemDecoration = new ItemDecorateRecyclerView("verticalLayout", 0, 0, (int) getResources().getDimension(R.dimen._2sdp), 0);

        layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        InitManager.unfixedRecyclerView(binding.listRecyclerView, itemDecoration, layoutManager);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.entryButton.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listener() {
        binding.inputEditText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (!binding.inputEditText.hasFocus()) {
                    ((MainActivity) requireActivity()).controlEditText.select(requireActivity(), binding.inputEditText);
                }
            }
            return false;
        });

        ClickManager.onDelayedClickListener(() -> {
            if (binding.inputEditText.length() != 0) {
                String phone = binding.inputEditText.getText().toString().trim();

                switch (Objects.requireNonNull(((MainActivity) requireActivity()).navController.getCurrentDestination()).getId()) {
                    case R.id.createCenterFragment:
                        CreateCenterFragment createCenterFragment = (CreateCenterFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                        if (createCenterFragment != null) {
                            if (!createCenterFragment.phoneDialogAdapter.getPhones().contains(phone)) {
                                createCenterFragment.phoneDialogAdapter.addPhone(phone);
                            } else {
                                Toast.makeText(requireActivity(), "exception", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    case R.id.editCenterFragment:
                        EditCenterFragment editCenterFragment = (EditCenterFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                        if (editCenterFragment != null) {
                            EditCenterDetailFragment editCenterDetailFragment = (EditCenterDetailFragment) editCenterFragment.adapter.getRegisteredFragment(0);

                            if (!editCenterDetailFragment.phoneDialogAdapter.getPhones().contains(phone)) {
                                editCenterDetailFragment.phoneDialogAdapter.addPhone(phone);
                            } else {
                                Toast.makeText(requireActivity(), "exception", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                }

                ((MainActivity) requireActivity()).controlEditText.clear(requireActivity(), ((MainActivity) requireActivity()).controlEditText.input());
                clearEditText();
            } else {
                dismiss();
            }
        }).widget(binding.entryButton);
    }

    private void setRecyclerView() {
        switch (Objects.requireNonNull(((MainActivity) requireActivity()).navController.getCurrentDestination()).getId()) {
            case R.id.createCenterFragment:
                CreateCenterFragment createCenterFragment = (CreateCenterFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                if (createCenterFragment != null) {
                    binding.listRecyclerView.setAdapter(createCenterFragment.phoneDialogAdapter);
                }
                break;
            case R.id.editCenterFragment:
                EditCenterFragment editCenterFragment = (EditCenterFragment) ((MainActivity) requireActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                if (editCenterFragment != null) {
                    EditCenterDetailFragment editCenterDetailFragment = (EditCenterDetailFragment) editCenterFragment.adapter.getRegisteredFragment(0);

                    binding.listRecyclerView.setAdapter(editCenterDetailFragment.phoneDialogAdapter);
                }
                break;
        }
    }

    private void clearEditText() {
        binding.inputEditText.getText().clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}