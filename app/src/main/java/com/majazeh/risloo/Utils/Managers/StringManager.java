package com.majazeh.risloo.utils.managers;

import java.text.DecimalFormat;

public class StringManager {

    /*
    ---------- Logics ----------
    */

    public static String bracing(int value) {
        return "(" + value + ")";
    }

    public static String bracing(String value) {
        return "(" + value + ")";
    }

    public static String sub(String value, char character) {
        return value.substring(0, value.indexOf(character));
    }

    public static String suffix(String value, char character) {
        return value.substring(value.lastIndexOf(character) + 1);
    }

    public static String substring(String value, int index) {
        return value.substring(value.length() - index);
    }

    public static String substring(String value, char character) {
        int position = 0;

        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == character)
                position = i;
        }

        return value.substring(position + 1);
    }

    public static String lastChar(String value) {
        return String.valueOf(value.charAt(value.length() - 1));
    }

    public static String firstChars(String value) {
        if (!value.equals("")) {
            if (value.contains(" "))
                return value.charAt(0) + String.valueOf(value.substring(value.lastIndexOf(" ") + 1).charAt(0));
            else if (value.contains("-"))
                return value.charAt(0) + String.valueOf(value.substring(value.lastIndexOf("-") + 1).charAt(0));
            else if (value.contains("/"))
                return value.charAt(0) + String.valueOf(value.substring(value.lastIndexOf("/") + 1).charAt(0));
            else
                return value.charAt(0) + String.valueOf(value.charAt(1));
        } return value;
    }

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

    public static String profileMode(String value) {
        if (!value.equals("")) {
            if (value.startsWith("profile_"))
                return value.substring(8).replaceAll("_", " ").toUpperCase();
        } return value;
    }

    public static String mobileConvert(String value) {
        if (!value.equals("")) {
            if (value.startsWith("989"))
                return "0" + value.substring(2);
        } return value;
    }

    public static String separate(String value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        return decimalFormat.format(Double.parseDouble(value));
    }

    public static String minusSeparate(String value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        return "(" + decimalFormat.format(Double.parseDouble(value.substring(1))) + ")";
    }

    public static String persian(String value) {
        String[] persianNumbers = new String[]{"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "'۸", "۹"};
        StringBuilder output = new StringBuilder();
        if (value.length() == 0) {
            return "";
        } else {
            for (char ch: value.toCharArray()) {
                if ('0' <= ch && ch <= '9')
                    output.append(persianNumbers[Integer.parseInt(String.valueOf(ch))]);
                else if (ch == '.' || ch == ',' || ch == 'و')
                    output.append(",");
                else
                    output.append(ch);
            } return output.toString();
        }
    }

    public static String replace(String value, String oldText, String newText) {
        return value.replace(oldText, newText);
    }

}