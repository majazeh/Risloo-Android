package com.majazeh.risloo.Views.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

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
import com.majazeh.risloo.databinding.DialogSingleBinding;

import java.util.Objects;

public class SingleDialog extends AppCompatDialogFragment {

    // Binding
    private DialogSingleBinding binding;

    // Objects
    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager layoutManager;
    private Handler handler;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireActivity(), R.style.DialogTheme);

        DialogSingleBinding binding = DialogSingleBinding.inflate(LayoutInflater.from(requireActivity()));

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
        binding = DialogSingleBinding.inflate(inflater, viewGroup, false);

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

        handler = new Handler();

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

        binding.inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(() -> {
                    // TODO : Place Code Here
                }, 750);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ClickManager.onDelayedClickListener(() -> {
            dismiss();
        }).widget(binding.entryButton);
    }

    private void setRecyclerView() {
        switch (Objects.requireNonNull(((MainActivity) requireActivity()).navController.getCurrentDestination()).getId()) {
            // TODO : Place Code Here
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