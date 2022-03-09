package com.majazeh.risloo.utils.managers;

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

public class SpannableManager {

    /*
    ---------- OneSpan's ----------
    */

    public static SpannableString setClickable(String value, int startIndex, int endIndex, ClickableSpan clickableSpan) {
        SpannableString spannableString = new SpannableString(value);
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static SpannableString setStrikethrough(String value, int startIndex, int endIndex) {
        SpannableString spannableString = new SpannableString(value);

        StrikethroughSpan strikeSpan = new StrikethroughSpan();
        spannableString.setSpan(strikeSpan, startIndex, endIndex, Spanned.SPAN_MARK_MARK);

        return spannableString;
    }

    public static SpannableString setForegroundColor(String value, int startIndex, int endIndex, int color) {
        SpannableString spannableString = new SpannableString(value);

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static SpannableString setBackgroundColor(String value, int startIndex, int endIndex, int color) {
        SpannableString spannableString = new SpannableString(value);

        BackgroundColorSpan colorSpan = new BackgroundColorSpan(color);
        spannableString.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static SpannableString setSize(String value, int startIndex, int endIndex, int size) {
        SpannableString spannableString = new SpannableString(value);

        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(size);
        spannableString.setSpan(sizeSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static SpannableString setStyle(String value, int startIndex, int endIndex, int style) {
        SpannableString spannableString = new SpannableString(value);

        StyleSpan styleSpan = new StyleSpan(style);
        spannableString.setSpan(styleSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /*
    ---------- TwoSpan's ----------
    */

    public static SpannableString setForegroundColorSize(String value, int startIndex, int endIndex, int color, int size) {
        SpannableString spannableString = new SpannableString(value);

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(size);

        spannableString.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static SpannableString setForegroundColorStyle(String value, int startIndex, int endIndex, int color, int style) {
        SpannableString spannableString = new SpannableString(value);

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        StyleSpan styleSpan = new StyleSpan(style);

        spannableString.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static SpannableString setBackgroundColorSize(String value, int startIndex, int endIndex, int color, int size) {
        SpannableString spannableString = new SpannableString(value);

        BackgroundColorSpan colorSpan = new BackgroundColorSpan(color);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(size);

        spannableString.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static SpannableString setBackgroundColorStyle(String value, int startIndex, int endIndex, int color, int style) {
        SpannableString spannableString = new SpannableString(value);

        BackgroundColorSpan colorSpan = new BackgroundColorSpan(color);
        StyleSpan styleSpan = new StyleSpan(style);

        spannableString.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /*
    ---------- ThreeSpan's ----------
    */

    public static SpannableString setForegroundColorSizeStyle(String value, int startIndex, int endIndex, int color, int size, int style) {
        SpannableString spannableString = new SpannableString(value);

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(size);
        StyleSpan styleSpan = new StyleSpan(style);

        spannableString.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static SpannableString setBackgroundColorSizeStyle(String value, int startIndex, int endIndex, int color, int size, int style) {
        SpannableString spannableString = new SpannableString(value);

        BackgroundColorSpan colorSpan = new BackgroundColorSpan(color);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(size);
        StyleSpan styleSpan = new StyleSpan(style);

        spannableString.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /*
    ---------- Builder's ----------
    */

    public static SpannableStringBuilder setClicakblePhoneNumbers(Activity activity, JSONArray list) {
        SpannableStringBuilder spannableBuilder = new SpannableStringBuilder();

        try {
            for (int i = 0; i < list.length(); i++) {
                String label = list.getString(i);

                spannableBuilder.append(label);
                if (i != list.length() - 1) {
                    spannableBuilder.append("  -  ");
                }

                int start = spannableBuilder.toString().indexOf(label);
                int end = start + label.length();

                spannableBuilder.setSpan(new ClickableSpan() {

                    @Override
                    public void onClick(@NonNull View widget) {
                        IntentManager.phone(activity, label);
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint textPaint) {
                        textPaint.setUnderlineText(false);
                    }

                }, start, end, 0);
            }

            return spannableBuilder;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return SpannableStringBuilder.valueOf("");
    }

}