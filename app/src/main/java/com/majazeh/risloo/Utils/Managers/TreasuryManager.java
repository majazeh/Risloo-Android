package com.majazeh.risloo.Utils.Managers;

import com.mre.ligheh.Model.Madule.List;
import com.mre.ligheh.Model.TypeModel.TreasuriesModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;

public class TreasuryManager {

    /*
    ---------- Funcs ----------
    */

    public static String getWallet(List treasuries) {
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

    public static String getGift(List treasuries) {
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

    public static String getWalletAndGift(List treasuries) {
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