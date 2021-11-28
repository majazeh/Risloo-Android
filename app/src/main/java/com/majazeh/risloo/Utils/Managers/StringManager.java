package com.majazeh.risloo.Utils.Managers;

import android.app.Activity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.view.View;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;

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

    /*
    ---------- Customize One ----------
    */

    public static SpannableStringBuilder phones(Activity activity, JSONArray list) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        try {
            for (int i = 0; i < list.length(); i++) {
                String label = list.getString(i);

                builder.append(label);
                if (i != list.length() - 1) {
                    builder.append("  -  ");
                }

                builder.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        IntentManager.phone(activity, label);
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint textPaint) {
                        textPaint.setUnderlineText(false);
                    }

                }, builder.toString().indexOf(label), builder.toString().indexOf(label) + label.length(), 0);
            }

            return builder;
        } catch (JSONException e) {
            e.printStackTrace();
        } return SpannableStringBuilder.valueOf("");
    }

    public static SpannableString strikethrough(String value, int startIndex, int endIndex) {
        SpannableString spannableString = new SpannableString(value);
        spannableString.setSpan(new StrikethroughSpan(), startIndex, endIndex, Spanned.SPAN_MARK_MARK);
        return spannableString;
    }

    public static SpannableString clickable(String value, int startIndex, int endIndex, ClickableSpan clickableSpan) {
        SpannableString spannableString = new SpannableString(value);
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString foreground(String value, int startIndex, int endIndex, int color) {
        SpannableString spannableString = new SpannableString(value);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(foregroundColorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString background(String value, int startIndex, int endIndex, int color) {
        SpannableString spannableString = new SpannableString(value);
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(color);
        spannableString.setSpan(backgroundColorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString size(String value, int startIndex, int endIndex, int size) {
        SpannableString spannableString = new SpannableString(value);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(size);
        spannableString.setSpan(sizeSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString style(String value, int startIndex, int endIndex, int typeface) {
        SpannableString spannableString = new SpannableString(value);
        StyleSpan styleSpan = new StyleSpan(typeface);
        spannableString.setSpan(styleSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /*
    ---------- Customize Two ----------
    */

    public static SpannableString separateStrike(String value, int startIndex, int endIndex) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        String decimalFormatString = decimalFormat.format(Double.parseDouble(value));
        SpannableString spannableString = new SpannableString(decimalFormatString);
        spannableString.setSpan(new StrikethroughSpan(), startIndex, endIndex, Spanned.SPAN_MARK_MARK);
        return spannableString;
    }

    public static SpannableString foregroundBackground(String value, int startIndex, int endIndex, int foregroundColor, int backgroundColor) {
        SpannableString spannableString = new SpannableString(value);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(foregroundColor);
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(backgroundColor);
        spannableString.setSpan(foregroundColorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(backgroundColorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString backgroundSize(String value, int startIndex, int endIndex, int backgroundColor, int size) {
        SpannableString spannableString = new SpannableString(value);
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(backgroundColor);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(size);
        spannableString.setSpan(backgroundColorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString foregroundSize(String value, int startIndex, int endIndex, int foregroundColor, int size) {
        SpannableString spannableString = new SpannableString(value);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(foregroundColor);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(size);
        spannableString.setSpan(foregroundColorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString foregroundStyle(String value, int startIndex, int endIndex, int foregroundColor, int typeface) {
        SpannableString spannableString = new SpannableString(value);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(foregroundColor);
        StyleSpan styleSpan = new StyleSpan(typeface);
        spannableString.setSpan(foregroundColorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /*
    ---------- Customize Three ----------
    */

    public static SpannableString foregroundSizeStyle(String value, int startIndex, int endIndex, int foregroundColor, int size, int typeface) {
        SpannableString spannableString = new SpannableString(value);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(foregroundColor);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(size);
        StyleSpan styleSpan = new StyleSpan(typeface);
        spannableString.setSpan(foregroundColorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString backgroundSizeStyle(String value, int startIndex, int endIndex, int backgroundColor, int size, int typeface) {
        SpannableString spannableString = new SpannableString(value);
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(backgroundColor);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(size);
        StyleSpan styleSpan = new StyleSpan(typeface);
        spannableString.setSpan(backgroundColorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

}