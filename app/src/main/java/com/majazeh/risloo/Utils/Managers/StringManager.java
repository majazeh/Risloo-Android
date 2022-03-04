package com.majazeh.risloo.utils.managers;

import java.text.DecimalFormat;

public class StringManager {

    /*
    ---------- Bracing ----------
    */

    public static String bracing(int value) {
        return "(" + value + ")";
    }

    public static String bracing(String value) {
        return "(" + value + ")";
    }

    /*
    ---------- Fix ----------
    */

    public static String prefix(String value, char target) {
        return value.substring(0, value.indexOf(target));
    }

    public static String suffix(String value, char target) {
        return value.substring(value.lastIndexOf(target) + 1);
    }

    public static String suffix(String value, int target) {
        return value.substring(value.length() - target);
    }

    public static String replace(String value, String oldText, String newText) {
        return value.replace(oldText, newText);
    }

    /*
    ---------- Seperate ----------
    */

    public static String seperatePlus(String value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        return decimalFormat.format(Double.parseDouble(value));
    }

    public static String seperateMinus(String value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        return "(" + decimalFormat.format(Double.parseDouble(value.substring(1))) + ")";
    }

    /*
    ---------- Adjust ----------
    */

    public static String adjustProfile(String value) {
        if (!value.equals("")) {
            if (value.startsWith("profile_"))
                return value.substring(8).replaceAll("_", " ").toUpperCase();
        }

        return value;
    }

    public static String adjustMobile(String value) {
        if (!value.equals("")) {
            if (value.startsWith("989"))
                return "0" + value.substring(2);
        }

        return value;
    }

    /*
    ---------- Char ----------
    */

    public static String fisrtChar(String value) {
        return String.valueOf(value.charAt(0));
    }

    public static String lastChar(String value) {
        return String.valueOf(value.charAt(value.length() - 1));
    }

    public static String firstChars(String value) {
        if (!value.equals("")) {
            if (value.contains(" "))
                return String.valueOf(value.charAt(0)) + value.substring(value.lastIndexOf(" ") + 1).charAt(0);
            else if (value.contains("-"))
                return String.valueOf(value.charAt(0)) + value.substring(value.lastIndexOf("-") + 1).charAt(0);
            else if (value.contains("/"))
                return String.valueOf(value.charAt(0)) + value.substring(value.lastIndexOf("/") + 1).charAt(0);
            else
                return String.valueOf(value.charAt(0)) + value.charAt(1);
        }

        return value;
    }

    /*
    ---------- Compare ----------
    */

    public static int compareVersionNames(String oldVersionName, String newVersionName) {
        int res = 0;

        String[] oldNumbers = oldVersionName.split("\\.");
        String[] newNumbers = newVersionName.split("\\.");

        int maxIndex = Math.min(oldNumbers.length, newNumbers.length);

        for (int i = 0; i < maxIndex; i ++) {
            int oldVersionNumber = Integer.parseInt(oldNumbers[i]);
            int newVersionNumber = Integer.parseInt(newNumbers[i]);

            if (oldVersionNumber < newVersionNumber) {
                res = 1;
                break;
            } else if (oldVersionNumber > newVersionNumber) {
                res = -1;
                break;
            }
        }

        return res;
    }

}