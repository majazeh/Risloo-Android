package com.majazeh.risloo.Views.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.majazeh.risloo.R;
import com.majazeh.risloo.Utils.Widgets.CustomizeDialog;
import com.majazeh.risloo.Utils.Widgets.SingleNumberPicker;
import com.majazeh.risloo.Views.Activities.MainActivity;
import com.majazeh.risloo.Views.Fragments.Create.EditAccountFragment;

public class DateDialog extends BottomSheetDialogFragment {

    // Vars
    private int year, month, day;

    // Widgets
    private SingleNumberPicker yearNumberPicker, monthNumberPicker, dayNumberPicker;
    private TextView entryTextView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return CustomizeDialog.bottomSheet(getActivity(), dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_date, viewGroup, false);

        initializer(view);

        listener();

        detector();

        setNumberPicker();

        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        clearNumberPicker();
    }

    private void initializer(View view) {
        yearNumberPicker = view.findViewById(R.id.dialog_date_year_NumberPicker);
        monthNumberPicker = view.findViewById(R.id.dialog_date_month_NumberPicker);
        dayNumberPicker = view.findViewById(R.id.dialog_date_day_NumberPicker);

        entryTextView = view.findViewById(R.id.dialog_date_entry_button);
    }

    private void detector() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            entryTextView.setBackgroundResource(R.drawable.draw_16sdp_solid_blue500_ripple_blue800);
        }
    }

    private void listener() {
        monthNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (picker == monthNumberPicker) {
                if (newVal <= 6) {
                    dayNumberPicker.setMaxValue(31);
                } else {
                    dayNumberPicker.setMaxValue(30);
                }
            }
        });

        entryTextView.setOnClickListener(v -> {
            entryTextView.setClickable(false);
            ((MainActivity) getActivity()).handler.postDelayed(() -> entryTextView.setClickable(true), 300);
            dismiss();

            switch (((MainActivity) getActivity()).navController.getCurrentDestination().getId()) {
                case R.id.editAccountFragment:
                    EditAccountFragment editAccountFragment = (EditAccountFragment) ((MainActivity) getActivity()).navHostFragment.getChildFragmentManager().getFragments().get(0);;
                    if (editAccountFragment != null) {
                        // TODO : Set Fragment TextView String
                    }
                    break;
            }
        });
    }

    private void setNumberPicker() {
        yearNumberPicker.setMinValue(1300);
        yearNumberPicker.setMaxValue(2100);
        yearNumberPicker.setValue(year);

        monthNumberPicker.setMinValue(1);
        monthNumberPicker.setMaxValue(12);
        monthNumberPicker.setValue(month);
        monthNumberPicker.setDisplayedValues(getActivity().getResources().getStringArray(R.array.JalaliMonths));

        dayNumberPicker.setMinValue(1);
        dayNumberPicker.setMaxValue(31);
        dayNumberPicker.setValue(day);
    }

    private void clearNumberPicker() {
        yearNumberPicker.setValue(year);
        monthNumberPicker.setValue(month);
        dayNumberPicker.setValue(day);
    }

    private String getDate() {
        year = yearNumberPicker.getValue();
        month = monthNumberPicker.getValue();
        day = dayNumberPicker.getValue();

        if (month < 10) {
            if (day < 10)
                return year + "-" + "0" + month + "-" + "0" + day;
            else
                return year + "-" + "0" + month + "-" + day;
        } else {
            if (day < 10)
                return year + "-" + month + "-" + "0" + day;
            else
                return year + "-" + month + "-" + day;
        }
    }

    public void setDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

}