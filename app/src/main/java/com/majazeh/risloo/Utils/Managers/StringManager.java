package com.majazeh.risloo.Utils.Managers;

import android.annotation.SuppressLint;
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
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;

import com.majazeh.risloo.R;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Queue;

public class StringManager {

    public static String substring(String value, char character) {
        int position = 0;

        for(int i=0; i<value.length(); i++) {
            if (value.charAt(i) == character) {
                position = i;
            }
        }

        return value.substring(position + 1);
    }

    public static String firstChars(String value) {
        String cc = "";

        if (value.contains(" ")) {
            cc = value.charAt(0) + String.valueOf(value.substring(value.lastIndexOf(" ") + 1).charAt(0));
        } else if (value.contains("-")) {
            cc = value.charAt(0) + String.valueOf(value.substring(value.lastIndexOf("-") + 1).charAt(0));
        } else if (value.contains("/")) {
            cc = value.charAt(0) + String.valueOf(value.substring(value.lastIndexOf("/") + 1).charAt(0));
        } else {
            cc = value.charAt(0) + String.valueOf(value.charAt(1));
        }

        return cc;
    }

    public static String separate(String value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        return decimalFormat.format(Double.parseDouble(value));
    }

    public static SpannableString lining(String value) {
        SpannableString spannableString = new SpannableString(value);
        spannableString.setSpan(new StrikethroughSpan(), 0, value.length(), Spanned.SPAN_MARK_MARK);
        return spannableString;
    }

    public static String persian(String value) {
        String[] persianNumbers = new String[] {"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "'۸", "۹"};
        StringBuilder output = new StringBuilder();
        if (value.length() == 0) {
            return "";
        } else {
            for (int i = 0; i < value.length(); i++) {
                char c = value.charAt(i);
                if ('0' <= c && c <= '9') {
                    output.append(persianNumbers[Integer.parseInt(String.valueOf(c))]);
                } else if (c == '.' || c == ',' || c == 'و') {
                    output.append(",");
                } else {
                    output.append(c);
                }
            }
            return output.toString();
        }
    }

    public static SpannableString separateLining(String value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        String decimalFormatString = decimalFormat.format(Double.parseDouble(value));
        SpannableString spannableString = new SpannableString(decimalFormatString);
        spannableString.setSpan(new StrikethroughSpan(), 0, decimalFormatString.length(), Spanned.SPAN_MARK_MARK);
        return spannableString;
    }

    public static String separatePersian(String value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        String decimalFormatString = decimalFormat.format(Double.parseDouble(value));
        String[] persianNumbers = new String[] {"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "'۸", "۹"};
        StringBuilder output = new StringBuilder();
        if (decimalFormatString.length() == 0) {
            return "";
        } else {
            for (int i = 0; i < decimalFormatString.length(); i++) {
                char c = decimalFormatString.charAt(i);
                if ('0' <= c && c <= '9') {
                    output.append(persianNumbers[Integer.parseInt(String.valueOf(c))]);
                } else if (c == '.' || c == ',' || c == 'و') {
                    output.append(",");
                } else {
                    output.append(c);
                }
            }
            return output.toString();
        }
    }

    public static SpannableString persianLining(String value) {
        String[] persianNumbers = new String[] {"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "'۸", "۹"};
        StringBuilder output = new StringBuilder();
        if (value.length() == 0) {
            return null;
        } else {
            for (int i = 0; i < value.length(); i++) {
                char c = value.charAt(i);
                if ('0' <= c && c <= '9') {
                    output.append(persianNumbers[Integer.parseInt(String.valueOf(c))]);
                } else if (c == '.' || c == ',' || c == 'و') {
                    output.append(",");
                } else {
                    output.append(c);
                }
            }
            SpannableString spannableString = new SpannableString(output.toString());
            spannableString.setSpan(new StrikethroughSpan(), 0, output.length(), Spanned.SPAN_MARK_MARK);
            return spannableString;
        }
    }

    public static SpannableString separatePersianLining(String value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        String decimalFormatString = decimalFormat.format(Double.parseDouble(value));
        String[] persianNumbers = new String[] {"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "'۸", "۹"};
        StringBuilder output = new StringBuilder();
        if (decimalFormatString.length() == 0) {
            return null;
        } else {
            for (int i = 0; i < decimalFormatString.length(); i++) {
                char c = decimalFormatString.charAt(i);
                if ('0' <= c && c <= '9') {
                    output.append(persianNumbers[Integer.parseInt(String.valueOf(c))]);
                } else if (c == '.' || c == ',' || c == 'و') {
                    output.append(",");
                } else {
                    output.append(c);
                }
            }
            SpannableString spannableString = new SpannableString(output.toString());
            spannableString.setSpan(new StrikethroughSpan(), 0, output.length(), Spanned.SPAN_MARK_MARK);
            return spannableString;
        }
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

    public static SpannableString foregroundBackground(String value, int startIndex, int endIndex, int foregroundColor, int backgroundColor) {
        SpannableString spannableString = new SpannableString(value);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(foregroundColor);
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(backgroundColor);
        spannableString.setSpan(foregroundColorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(backgroundColorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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

    public static SpannableString backgroundStyle(String value, int startIndex, int endIndex, int backgroundColor, int typeface) {
        SpannableString spannableString = new SpannableString(value);
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(backgroundColor);
        StyleSpan styleSpan = new StyleSpan(typeface);
        spannableString.setSpan(backgroundColorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString foregroundBackgroundStyle(String value, int startIndex, int endIndex, int foregroundColor, int backgroundColor, int typeface) {
        SpannableString spannableString = new SpannableString(value);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(foregroundColor);
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(backgroundColor);
        StyleSpan styleSpan = new StyleSpan(typeface);
        spannableString.setSpan(foregroundColorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(backgroundColorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @SuppressLint("RestrictedApi")
    public static SpannableStringBuilder clickableNavBackStack(Activity activity, NavController navController) {
        Queue<Integer> enQueue = new LinkedList();
        Queue<String> faQueue = new LinkedList();

        SpannableStringBuilder destination = new SpannableStringBuilder();

        for (NavBackStackEntry navBackStackEntry : navController.getBackStack()) {
            if (navBackStackEntry.getDestination().getLabel() != null) {
                if (!enQueue.isEmpty()) {
                    destination.append("  >  ");
                }
                enQueue.add(navBackStackEntry.getDestination().getId());
                faQueue.add(navBackStackEntry.getDestination().getLabel().toString());

                destination.append(navBackStackEntry.getDestination().getLabel().toString());
            }
        }

        for (int i = 0; i < faQueue.size(); i++) {
            int j = i;
            String s = (String) faQueue.toArray()[i];
            if ( j+1!=faQueue.size()) {

                destination.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        for (int k = j+1; k < navController.getBackStack().size(); k++) {
                            navController.popBackStack();
                            enQueue.remove();
                            faQueue.remove();
                        }
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint textPaint) {
                        textPaint.setColor(activity.getResources().getColor(R.color.Gray500));
                        textPaint.setUnderlineText(false);
                    }

                },destination.toString().indexOf(s),destination.toString().indexOf(s)+s.length(),0);
            }
        }

        return destination;
    }

}