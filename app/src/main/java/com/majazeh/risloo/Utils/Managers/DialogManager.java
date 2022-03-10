package com.majazeh.risloo.utils.managers;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.majazeh.risloo.views.dialogs.DialogLoading;
import com.majazeh.risloo.views.dialogs.DialogPayment;
import com.majazeh.risloo.views.dialogs.DialogScheduleFilter;
import com.majazeh.risloo.views.dialogs.DialogSearchable;
import com.majazeh.risloo.views.dialogs.DialogSelected;
import com.mre.ligheh.Model.TypeModel.PaymentModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

import java.util.ArrayList;

public class DialogManager {

    // Widgets
    private static DialogLoading dialogLoading = null;
    private static DialogPayment dialogPayment = null;
    private static DialogScheduleFilter dialogScheduleFilter = null;
    private static DialogSearchable dialogSearchable = null;
    private static DialogSelected dialogSelected = null;

    /*
    ---------- Show's ----------
    */

    public static void showDialogLoading(Activity activity, String title) {
        dialogLoading = new DialogLoading();

        if (activity instanceof AppCompatActivity)
            dialogLoading.show(((AppCompatActivity) activity).getSupportFragmentManager(), "dialogLoading");
        else if (activity instanceof FragmentActivity)
            dialogLoading.show(((FragmentActivity) activity).getSupportFragmentManager(), "dialogLoading");

        dialogLoading.setData(title);
    }

    public static void showDialogPayment(Activity activity, String method, PaymentModel paymentModel) {
        dialogPayment = new DialogPayment();

        if (activity instanceof AppCompatActivity)
            dialogPayment.show(((AppCompatActivity) activity).getSupportFragmentManager(), "dialogPayment");
        else if (activity instanceof FragmentActivity)
            dialogPayment.show(((FragmentActivity) activity).getSupportFragmentManager(), "dialogPayment");

        dialogPayment.setData(method, paymentModel);
    }

    public static void showDialogScheduleFilter(Activity activity, String method, ArrayList<TypeModel> rooms, ArrayList<TypeModel> status) {
        dialogScheduleFilter = new DialogScheduleFilter();

        if (activity instanceof AppCompatActivity)
            dialogScheduleFilter.show(((AppCompatActivity) activity).getSupportFragmentManager(), "dialogScheduleFilter");
        else if (activity instanceof FragmentActivity)
            dialogScheduleFilter.show(((FragmentActivity) activity).getSupportFragmentManager(), "dialogScheduleFilter");

        dialogScheduleFilter.setData(method, rooms, status);
    }

    public static void showDialogSearchable(Activity activity, String method) {
        dialogSearchable = new DialogSearchable();

        if (activity instanceof AppCompatActivity)
            dialogSearchable.show(((AppCompatActivity) activity).getSupportFragmentManager(), "dialogSearchable");
        else if (activity instanceof FragmentActivity)
            dialogSearchable.show(((FragmentActivity) activity).getSupportFragmentManager(), "dialogSearchable");

        dialogSearchable.setData(method);
    }

    public static void showDialogSelected(Activity activity, String method) {
        dialogSelected = new DialogSelected();

        if (activity instanceof AppCompatActivity)
            dialogSelected.show(((AppCompatActivity) activity).getSupportFragmentManager(), "dialogSelected");
        else if (activity instanceof FragmentActivity)
            dialogSelected.show(((FragmentActivity) activity).getSupportFragmentManager(), "dialogSelected");

        dialogSelected.setData(method);
    }

    /*
    ---------- Dismiss's ----------
    */

    public static void dismissDialogLoading() {
        if (dialogLoading != null) {
            dialogLoading.dismiss();
            dialogLoading = null;
        }
    }

    public static void dismissDialogPayment() {
        if (dialogPayment != null) {
            dialogPayment.dismiss();
            dialogPayment = null;
        }
    }

    public static void dismissDialogScheduleFilter() {
        if (dialogScheduleFilter != null) {
            dialogScheduleFilter.dismiss();
            dialogScheduleFilter = null;
        }
    }

    public static void dismissDialogSearchable() {
        if (dialogSearchable != null) {
            dialogSearchable.dismiss();
            dialogSearchable = null;
        }
    }

    public static void dismissDialogSelected() {
        if (dialogSelected != null) {
            dialogSelected.dismiss();
            dialogSelected = null;
        }
    }

    /*
    ---------- Getter's ----------
    */

    public static DialogLoading getDialogLoading() {
        if (dialogLoading != null && dialogLoading.isVisible())
            return dialogLoading;

        return null;
    }

    public static DialogPayment getDialogPayment() {
        if (dialogPayment != null && dialogPayment.isVisible())
            return dialogPayment;

        return null;
    }

    public static DialogScheduleFilter getDialogScheduleFilter() {
        if (dialogScheduleFilter != null && dialogScheduleFilter.isVisible())
            return dialogScheduleFilter;

        return null;
    }

    public static DialogSearchable getDialogSearchable() {
        if (dialogSearchable != null && dialogSearchable.isVisible())
            return dialogSearchable;

        return null;
    }

    public static DialogSelected getDialogSelected() {
        if (dialogSelected != null && dialogSelected.isVisible())
            return dialogSelected;

        return null;
    }

}