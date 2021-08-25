package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.majazeh.risloo.Views.BottomSheets.AuthBottomSheet;
import com.majazeh.risloo.Views.BottomSheets.ChainBottomSheet;
import com.majazeh.risloo.Views.BottomSheets.DateBottomSheet;
import com.majazeh.risloo.Views.BottomSheets.ImageBottomSheet;
import com.majazeh.risloo.Views.BottomSheets.LogoutBottomSheet;
import com.majazeh.risloo.Views.BottomSheets.TimeBottomSheet;
import com.mre.ligheh.Model.TypeModel.BulkSampleModel;

public class SheetManager {

    /*
    ---------- Funcs ----------
    */

    public static void showAuthBottomSheet(Activity activity, String key, String name, String avatar) {
        AuthBottomSheet bottomSheet = new AuthBottomSheet();
        bottomSheet.show(((FragmentActivity) activity).getSupportFragmentManager(), "authBottomSheet");
        bottomSheet.setData(key, name, avatar);
    }

    public static void showChainBottomSheet(Activity activity, String key, String name, String avatar, BulkSampleModel model) {
        ChainBottomSheet bottomSheet = new ChainBottomSheet();
        bottomSheet.show(((FragmentActivity) activity).getSupportFragmentManager(), "chainBottomSheet");
        bottomSheet.setData(key, name, avatar, model);
    }

    public static void showDateBottomSheet(Activity activity, String timestamp, String method) {
        DateBottomSheet bottomSheet = new DateBottomSheet();
        bottomSheet.show(((FragmentActivity) activity).getSupportFragmentManager(), "dateBottomSheet");
        bottomSheet.setDate(timestamp, method);
    }

    public static void showImageBottomSheet(Activity activity) {
        ImageBottomSheet bottomSheet = new ImageBottomSheet();
        bottomSheet.show(((FragmentActivity) activity).getSupportFragmentManager(), "imageBottomSheet");
    }

    public static void showLogoutBottomSheet(Activity activity, String name, String avatar) {
        LogoutBottomSheet bottomSheet = new LogoutBottomSheet();
        bottomSheet.show(((AppCompatActivity) activity).getSupportFragmentManager(), "logoutBottomSheet");
        bottomSheet.setData(name, avatar);
    }

    public static void showTimeBottomSheet(Activity activity, String timestamp, String method) {
        TimeBottomSheet bottomSheet = new TimeBottomSheet();
        bottomSheet.show(((FragmentActivity) activity).getSupportFragmentManager(), "timeBottomSheet");
        bottomSheet.setTime(timestamp, method);
    }

}