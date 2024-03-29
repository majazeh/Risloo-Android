package com.majazeh.risloo.utils.managers;

import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.TreasuriesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

public class BalanceManager {

    /*
    ---------- Func's ----------
    */

    public static String balanceWallet(List treasuries) {
        int balance = 0;

        if (treasuries.size() != 0) {
            for (TypeModel typeModel : treasuries.data()) {
                TreasuriesModel treasuriesModel = (TreasuriesModel) typeModel;

                if (treasuriesModel.getSymbol().equals("wallet")) {
                    balance += treasuriesModel.getBalance();
                }
            }
        }

        return String.valueOf(balance);
    }

    public static String balanceGift(List treasuries) {
        int balance = 0;

        if (treasuries.size() != 0) {
            for (TypeModel typeModel : treasuries.data()) {
                TreasuriesModel treasuriesModel = (TreasuriesModel) typeModel;

                if (treasuriesModel.getSymbol().equals("gift")) {
                    balance += treasuriesModel.getBalance();
                }
            }
        }

        return String.valueOf(balance);
    }

    public static String balanceWalletAndGift(List treasuries) {
        int balance = 0;

        if (treasuries.size() != 0) {
            for (TypeModel typeModel : treasuries.data()) {
                TreasuriesModel treasuriesModel = (TreasuriesModel) typeModel;

                if (treasuriesModel.getSymbol().equals("wallet") || treasuriesModel.getSymbol().equals("gift")) {
                    balance += treasuriesModel.getBalance();
                }
            }
        }

        return String.valueOf(balance);
    }

}