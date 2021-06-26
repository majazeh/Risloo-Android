package com.majazeh.risloo.Utils.Managers;

import android.annotation.SuppressLint;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import saman.zamani.persiandate.PersianDate;

public class DateManager {

     /*
    ---------- String & Date ----------
    */

    @SuppressLint("SimpleDateFormat")
    public static Date stringToDate(String pattern, String value) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        } return null;
    }

    @SuppressLint("SimpleDateFormat")
    public static String dateToString(String pattern, Date value) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(value);
    }

    /*
    ---------- Timestamp & Date ----------
    */

    public static Date timestampToDate(long value) {
        Timestamp timestamp = new Timestamp(value * 1000);
        return new Date(timestamp.getTime());
    }

    public static long dateToTimestamp(Date value) {
        return value.getTime() / 1000;
    }

    /*
    ---------- PersianDate & Date ----------
    */

    public static Date persianToDate(PersianDate value) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(value.getGrgYear(), value.getGrgMonth() - 1, value.getGrgDay(), value.getHour(), value.getMinute(), value.getSecond());

        return gregorianCalendar.getTime();
    }

    public static PersianDate dateToPersian(Date value) {
        int year = Integer.parseInt(dateToString("yyyy", value));
        int month = Integer.parseInt(dateToString("MM", value));
        int day = Integer.parseInt(dateToString("dd", value));
        int hour = Integer.parseInt(dateToString("HH", value));
        int minute = Integer.parseInt(dateToString("mm", value));
        int second = Integer.parseInt(dateToString("ss", value));

        PersianDate persianDate = new PersianDate();
        persianDate.initGrgDate(year, month, day, hour, minute, second);

        return persianDate;
    }

    /*
    ---------- Current Time ----------
    */

    public static Date currentDate() {
        return Calendar.getInstance().getTime();
    }

    public static long currentTimestamp() {
        Date date = Calendar.getInstance().getTime();
        return date.getTime() / 1000;
    }

    /*
    ---------- Create PersianDate & Date ----------
    */

    public static Date createDate(int year, int month, int day, int hour, int minute, int second) {
        return new GregorianCalendar(year, month - 1, day, hour, minute, second).getTime();
    }

    public static PersianDate createPersianDate(int year, int month, int day, int hour, int minute, int second) {
        return new PersianDate().initJalaliDate(year, month, day, hour, minute, second);
    }

    /*
    ---------- Persian Date's ----------
    */

    public static String jalYYYYsMMsDD(String timestamp, String seperator) {
        long value = Long.parseLong(timestamp);
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        if (persianDate.getShMonth() < 10) {
            if (persianDate.getShDay() < 10)
                return persianDate.getShYear() + seperator + "0" + persianDate.getShMonth() + seperator + "0" + persianDate.getShDay();
            else
                return persianDate.getShYear() + seperator + "0" + persianDate.getShMonth() + seperator + persianDate.getShDay();
        } else {
            if (persianDate.getShDay() < 10)
                return persianDate.getShYear() + seperator + persianDate.getShMonth() + seperator + "0" + persianDate.getShDay();
            else
                return persianDate.getShYear() + seperator + persianDate.getShMonth() + seperator + persianDate.getShDay();
        }
    }

    public static String jalHHoMMoYYoMMoDD(String timestamp) {
        long value = Long.parseLong(timestamp);
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        if (persianDate.getShMonth() < 10) {
            if (persianDate.getShDay() < 10)
                return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + "0" + persianDate.getShMonth() + "-" + "0" + persianDate.getShDay() + " " + persianDate.getHour() + ":" + persianDate.getMinute();
            else
                return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + "0" + persianDate.getShMonth() + "-" + persianDate.getShDay() + " " + persianDate.getHour() + ":" + persianDate.getMinute();
        } else {
            if (persianDate.getShDay() < 10)
                return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + persianDate.getShMonth() + "-" + "0" + persianDate.getShDay() + " " + persianDate.getHour() + ":" + persianDate.getMinute();
            else
                return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + persianDate.getShMonth() + "-" + persianDate.getShDay() + " " + persianDate.getHour() + ":" + persianDate.getMinute();
        }
    }

    /*
    ---------- Calculate Timestamp's ----------
    */

    public static long preJalFridayTimestamp(long value) {
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        switch (persianDate.dayName()) {
            case "شنبه":
                return new Date(date.getTime() - (86400000)).getTime() / 1000;
            case "یکشنبه":
                return new Date(date.getTime() - (2*86400000)).getTime() / 1000;
            case "دوشنبه":
                return new Date(date.getTime() - (3*86400000)).getTime() / 1000;
            case "سه\u200Cشنبه":
                return new Date(date.getTime() - (4*86400000)).getTime() / 1000;
            case "چهارشنبه":
                return new Date(date.getTime() - (5*86400000)).getTime() / 1000;
            case "پنج\u200Cشنبه":
                return new Date(date.getTime() - (6*86400000)).getTime() / 1000;
            case "جمعه":
                return new Date(date.getTime() - (7*86400000)).getTime() / 1000;
            default:
                return value;
        }
    }

    public static long nxtJalSaturdayTimestamp(long value) {
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        switch (persianDate.dayName()) {
            case "شنبه":
                return new Date(date.getTime() + (7*86400000)).getTime() / 1000;
            case "یکشنبه":
                return new Date(date.getTime() + (6*86400000)).getTime() / 1000;
            case "دوشنبه":
                return new Date(date.getTime() + (5*86400000)).getTime() / 1000;
            case "سه\u200Cشنبه":
                return new Date(date.getTime() + (4*86400000)).getTime() / 1000;
            case "چهارشنبه":
                return new Date(date.getTime() + (3*86400000)).getTime() / 1000;
            case "پنج\u200Cشنبه":
                return new Date(date.getTime() + (2*86400000)).getTime() / 1000;
            case "جمعه":
                return new Date(date.getTime() + (86400000)).getTime() / 1000;
            default:
                return value;
        }
    }

    /*
    ---------- Current Week ----------
    */

    public static String currentJalWeekString(long value) {
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        switch (persianDate.dayName()) {
            case "شنبه": {
                Date lastDay = new Date(date.getTime() + (6*86400000));
                PersianDate lastPersianDate = dateToPersian(lastDay);

                return persianDate.getShMonth() + "/" + persianDate.getShDay()  + " تا " + lastPersianDate.getShMonth() + "/" + lastPersianDate.getShDay();
            }
            case "یکشنبه": {
                Date firstDay = new Date(date.getTime() - (86400000));
                Date lastDay = new Date(date.getTime() + (5*86400000));

                PersianDate firstPersianDate = dateToPersian(firstDay);
                PersianDate lastPersianDate = dateToPersian(lastDay);

                return firstPersianDate.getShMonth() + "/" + firstPersianDate.getShDay()  + " تا " + lastPersianDate.getShMonth() + "/" + lastPersianDate.getShDay();
            }
            case "دوشنبه": {
                Date firstDay = new Date(date.getTime() - (2*86400000));
                Date lastDay = new Date(date.getTime() + (4*86400000));

                PersianDate firstPersianDate = dateToPersian(firstDay);
                PersianDate lastPersianDate = dateToPersian(lastDay);

                return firstPersianDate.getShMonth() + "/" + firstPersianDate.getShDay()  + " تا " + lastPersianDate.getShMonth() + "/" + lastPersianDate.getShDay();
            }
            case "سه\u200Cشنبه": {
                Date firstDay = new Date(date.getTime() - (3*86400000));
                Date lastDay = new Date(date.getTime() + (3*86400000));

                PersianDate firstPersianDate = dateToPersian(firstDay);
                PersianDate lastPersianDate = dateToPersian(lastDay);

                return firstPersianDate.getShMonth() + "/" + firstPersianDate.getShDay()  + " تا " + lastPersianDate.getShMonth() + "/" + lastPersianDate.getShDay();
            }
            case "چهارشنبه": {
                Date firstDay = new Date(date.getTime() - (4*86400000));
                Date lastDay = new Date(date.getTime() + (2*86400000));

                PersianDate firstPersianDate = dateToPersian(firstDay);
                PersianDate lastPersianDate = dateToPersian(lastDay);

                return firstPersianDate.getShMonth() + "/" + firstPersianDate.getShDay()  + " تا " + lastPersianDate.getShMonth() + "/" + lastPersianDate.getShDay();
            }
            case "پنج\u200Cشنبه": {
                Date firstDay = new Date(date.getTime() - (5*86400000));
                Date lastDay = new Date(date.getTime() + (86400000));

                PersianDate firstPersianDate = dateToPersian(firstDay);
                PersianDate lastPersianDate = dateToPersian(lastDay);

                return firstPersianDate.getShMonth() + "/" + firstPersianDate.getShDay()  + " تا " + lastPersianDate.getShMonth() + "/" + lastPersianDate.getShDay();
            }
            case "جمعه": {
                Date firstDay = new Date(date.getTime() - (6*86400000));
                PersianDate firstPersianDate = dateToPersian(firstDay);

                return persianDate.getShMonth() + "/" + persianDate.getShDay()  + " تا " + firstPersianDate.getShMonth() + "/" + firstPersianDate.getShDay();
            }
            default:
                return "";
        }
    }

    public static ArrayList<Long> currentJalWeekTimestamps(long value) {
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        ArrayList<Long> timestamps = new ArrayList<>();

        switch (persianDate.dayName()) {
            case "شنبه":
                timestamps.add(new Date(date.getTime()).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (2*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (3*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (4*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (5*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (6*86400000)).getTime() / 1000);
                return timestamps;
            case "یکشنبه":
                timestamps.add(new Date(date.getTime() - (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime()).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (2*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (3*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (4*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (5*86400000)).getTime() / 1000);
                return timestamps;
            case "دوشنبه":
                timestamps.add(new Date(date.getTime() - (2*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime()).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (2*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (3*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (4*86400000)).getTime() / 1000);
                return timestamps;
            case "سه\u200Cشنبه":
                timestamps.add(new Date(date.getTime() - (3*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (2*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime()).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (2*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (3*86400000)).getTime() / 1000);
                return timestamps;
            case "چهارشنبه":
                timestamps.add(new Date(date.getTime() - (4*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (3*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (2*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime()).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (2*86400000)).getTime() / 1000);
                return timestamps;
            case "پنج\u200Cشنبه":
                timestamps.add(new Date(date.getTime() - (5*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (4*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (3*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (2*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime()).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (86400000)).getTime() / 1000);
                return timestamps;
            case "جمعه":
                timestamps.add(new Date(date.getTime() - (6*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (5*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (4*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (3*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (2*86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime()).getTime() / 1000);
                return timestamps;
            default:
                return timestamps;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////



    //////////////////////////////////////////////////////////////////////////////////////



    //////////////////////////////////////////////////////////////////////////////////////



    //////////////////////////////////////////////////////////////////////////////////////



    //////////////////////////////////////////////////////////////////////////////////////

    public static ArrayList<Long> previeosJalaliWeekTimestamps(long value) {
        Timestamp timestamp = new Timestamp(value * 1000);
        Date today = new Date(timestamp.getTime());

        int year = Integer.parseInt(dateToString("yyyy", today));
        int month = Integer.parseInt(dateToString("MM", today));
        int day = Integer.parseInt(dateToString("dd", today));

        PersianDate persianDate = new PersianDate();
        persianDate.initGrgDate(year, month, day);

        ArrayList<Long> timestamps = new ArrayList<>();

        switch (persianDate.dayName()) {
            case "شنبه":
                timestamps.add(new Date(today.getTime() - (7*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (6*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (5*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (4*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (3*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (2*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (86400000)).getTime() / 1000);
                return timestamps;
            case "یکشنبه":
                timestamps.add(new Date(today.getTime() - (8*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (7*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (6*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (5*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (4*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (3*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (2*86400000)).getTime() / 1000);
                return timestamps;
            case "دوشنبه":
                timestamps.add(new Date(today.getTime() - (9*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (8*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (7*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (6*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (5*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (4*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (3*86400000)).getTime() / 1000);
                return timestamps;
            case "سه\u200Cشنبه":
                timestamps.add(new Date(today.getTime() - (10*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (9*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (8*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (7*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (6*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (5*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (4*86400000)).getTime() / 1000);
                return timestamps;
            case "چهارشنبه":
                timestamps.add(new Date(today.getTime() - (11*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (10*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (9*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (8*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (7*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (6*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (5*86400000)).getTime() / 1000);
                return timestamps;
            case "پنج\u200Cشنبه":
                timestamps.add(new Date(today.getTime() - (12*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (11*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (10*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (9*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (8*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (7*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (6*86400000)).getTime() / 1000);
                return timestamps;
            case "جمعه":
                timestamps.add(new Date(today.getTime() - (13*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (12*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (11*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (10*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (9*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (8*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() - (7*86400000)).getTime() / 1000);
                return timestamps;
            default:
                return timestamps;
        }
    }

    public static ArrayList<Long> nextJalaliWeekTimestamps(long value) {
        Timestamp timestamp = new Timestamp(value * 1000);
        Date today = new Date(timestamp.getTime());

        int year = Integer.parseInt(dateToString("yyyy", today));
        int month = Integer.parseInt(dateToString("MM", today));
        int day = Integer.parseInt(dateToString("dd", today));

        PersianDate persianDate = new PersianDate();
        persianDate.initGrgDate(year, month, day);

        ArrayList<Long> timestamps = new ArrayList<>();

        switch (persianDate.dayName()) {
            case "شنبه":
                timestamps.add(new Date(today.getTime() + (7*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (8*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (9*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (10*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (11*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (12*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (13*86400000)).getTime() / 1000);
                return timestamps;
            case "یکشنبه":
                timestamps.add(new Date(today.getTime() + (6*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (7*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (8*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (9*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (10*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (11*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (12*86400000)).getTime() / 1000);
                return timestamps;
            case "دوشنبه":
                timestamps.add(new Date(today.getTime() + (5*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (6*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (7*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (8*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (9*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (10*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (11*86400000)).getTime() / 1000);
                return timestamps;
            case "سه\u200Cشنبه":
                timestamps.add(new Date(today.getTime() + (4*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (5*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (6*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (7*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (8*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (9*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (10*86400000)).getTime() / 1000);
                return timestamps;
            case "چهارشنبه":
                timestamps.add(new Date(today.getTime() + (3*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (4*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (5*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (6*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (7*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (8*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (9*86400000)).getTime() / 1000);
                return timestamps;
            case "پنج\u200Cشنبه":
                timestamps.add(new Date(today.getTime() + (2*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (3*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (4*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (5*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (6*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (7*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (8*86400000)).getTime() / 1000);
                return timestamps;
            case "جمعه":
                timestamps.add(new Date(today.getTime() + (86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (2*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (3*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (4*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (5*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (6*86400000)).getTime() / 1000);
                timestamps.add(new Date(today.getTime() + (7*86400000)).getTime() / 1000);
                return timestamps;
            default:
                return timestamps;
        }
    }

    public static String currentJalaliDate() {
        Date value = Calendar.getInstance().getTime();

        int year = Integer.parseInt(dateToString("yyyy", value));
        int month = Integer.parseInt(dateToString("MM", value));
        int day = Integer.parseInt(dateToString("dd", value));

        PersianDate persianDate = new PersianDate();
        persianDate.initGrgDate(year, month, day);

        if (persianDate.getShMonth() < 10) {
            if (persianDate.getShDay() < 10)
                return persianDate.getShYear() + "-" + "0" + persianDate.getShMonth() + "-" + "0" + persianDate.getShDay();
            else
                return persianDate.getShYear() + "-" + "0" + persianDate.getShMonth() + "-" + persianDate.getShDay();
        } else {
            if (persianDate.getShDay() < 10)
                return persianDate.getShYear() + "-" + persianDate.getShMonth() + "-" + "0" + persianDate.getShDay();
            else
                return persianDate.getShYear() + "-" + persianDate.getShMonth() + "-" + persianDate.getShDay();
        }
    }

    public static String gregorianToJalali0(String value) {
        int year = Integer.parseInt(dateToString("yyyy", stringToDate("yyyy-MM-dd", value)));
        int month = Integer.parseInt(dateToString("MM", stringToDate("yyyy-MM-dd", value)));
        int day = Integer.parseInt(dateToString("dd", stringToDate("yyyy-MM-dd", value)));

        PersianDate persianDate = new PersianDate();
        persianDate.initGrgDate(year, month, day);

        if (persianDate.getShMonth() < 10) {
            if (persianDate.getShDay() < 10)
                return persianDate.getShYear() + "-" + "0" + persianDate.getShMonth() + "-" + "0" + persianDate.getShDay();
            else
                return persianDate.getShYear() + "-" + "0" + persianDate.getShMonth() + "-" + persianDate.getShDay();
        } else {
            if (persianDate.getShDay() < 10)
                return persianDate.getShYear() + "-" + persianDate.getShMonth() + "-" + "0" + persianDate.getShDay();
            else
                return persianDate.getShYear() + "-" + persianDate.getShMonth() + "-" + persianDate.getShDay();
        }
    }

    public static String gregorianToJalali1(String value) {
        int year = Integer.parseInt(dateToString("yyyy", stringToDate("yyyy-MM-dd", value)));
        int month = Integer.parseInt(dateToString("MM", stringToDate("yyyy-MM-dd", value)));
        int day = Integer.parseInt(dateToString("dd", stringToDate("yyyy-MM-dd", value)));

        PersianDate persianDate = new PersianDate();
        persianDate.initGrgDate(year, month, day);

        if (persianDate.getShMonth() < 10) {
            if (persianDate.getShDay() < 10)
                return persianDate.getShYear() + "/" + "0" + persianDate.getShMonth() + "/" + "0" + persianDate.getShDay();
            else
                return persianDate.getShYear() + "/" + "0" + persianDate.getShMonth() + "/" + persianDate.getShDay();
        } else {
            if (persianDate.getShDay() < 10)
                return persianDate.getShYear() + "/" + persianDate.getShMonth() + "/" + "0" + persianDate.getShDay();
            else
                return persianDate.getShYear() + "/" + persianDate.getShMonth() + "/" + persianDate.getShDay();
        }
    }

    public static String gregorianToJalali11(String value) {
        int year = Integer.parseInt(dateToString("yyyy", stringToDate("yyyy-MM-dd", value)));
        int month = Integer.parseInt(dateToString("MM", stringToDate("yyyy-MM-dd", value)));
        int day = Integer.parseInt(dateToString("dd", stringToDate("yyyy-MM-dd", value)));

        PersianDate persianDate = new PersianDate();
        persianDate.initGrgDate(year, month, day);

        return persianDate.dayName();
    }

    public static String gregorianToJalali2(String value) {
        int year = Integer.parseInt(dateToString("yyyy", stringToDate("yyyy-MM-dd", value)));
        int month = Integer.parseInt(dateToString("MM", stringToDate("yyyy-MM-dd", value)));
        int day = Integer.parseInt(dateToString("dd", stringToDate("yyyy-MM-dd", value)));

        PersianDate persianDate = new PersianDate();
        persianDate.initGrgDate(year, month, day);

        return persianDate.dayName() + " " + persianDate.getShDay() + " " + persianDate.monthName() + " " + persianDate.getShYear();
    }

    public static String gregorianToJalali3(String value) {
        int year = Integer.parseInt(dateToString("yyyy", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int month = Integer.parseInt(dateToString("MM", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int day = Integer.parseInt(dateToString("dd", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int hour = Integer.parseInt(dateToString("HH", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int minute = Integer.parseInt(dateToString("mm", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int second = Integer.parseInt(dateToString("ss", stringToDate("yyyy-MM-dd HH:mm:ss", value)));

        PersianDate persianDate = new PersianDate();
        persianDate.initGrgDate(year, month, day, hour, minute, second);

        return StringManager.lastChar(String.valueOf(persianDate.getShYear()))+ "-" + StringManager.lastChar(String.valueOf(persianDate.getShMonth())) + "-" + StringManager.lastChar(String.valueOf(persianDate.getShDay())) + " " + persianDate.getHour() + ":" + persianDate.getMinute();
    }

    public static String gregorianToJalali4(String value) {
        int year = Integer.parseInt(dateToString("yyyy", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int month = Integer.parseInt(dateToString("MM", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int day = Integer.parseInt(dateToString("dd", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int hour = Integer.parseInt(dateToString("HH", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int minute = Integer.parseInt(dateToString("mm", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int second = Integer.parseInt(dateToString("ss", stringToDate("yyyy-MM-dd HH:mm:ss", value)));

        PersianDate persianDate = new PersianDate();
        persianDate.initGrgDate(year, month, day, hour, minute, second);

        return StringManager.substring(String.valueOf(persianDate.getShYear()), 2)+ "-" + persianDate.getShMonth() + "-" + persianDate.getShDay() + " " + persianDate.getHour() + ":" + persianDate.getMinute();
    }

    public static String gregorianToJalali5(String value) {
        int year = Integer.parseInt(dateToString("yyyy", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int month = Integer.parseInt(dateToString("MM", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int day = Integer.parseInt(dateToString("dd", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int hour = Integer.parseInt(dateToString("HH", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int minute = Integer.parseInt(dateToString("mm", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int second = Integer.parseInt(dateToString("ss", stringToDate("yyyy-MM-dd HH:mm:ss", value)));

        PersianDate persianDate = new PersianDate();
        persianDate.initGrgDate(year, month, day, hour, minute, second);

        if (persianDate.getMinute() < 10)
            return persianDate.dayName() + " " + persianDate.getShDay() + " " + persianDate.monthName() + " " + StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "\n" + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
        else
            return persianDate.dayName() + " " + persianDate.getShDay() + " " + persianDate.monthName() + " " + StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "\n" + persianDate.getHour() + ":" + persianDate.getMinute();
    }

    public static String gregorianToJalali6(String value) {
        int year = Integer.parseInt(dateToString("yyyy", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int month = Integer.parseInt(dateToString("MM", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int day = Integer.parseInt(dateToString("dd", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int hour = Integer.parseInt(dateToString("HH", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int minute = Integer.parseInt(dateToString("mm", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int second = Integer.parseInt(dateToString("ss", stringToDate("yyyy-MM-dd HH:mm:ss", value)));

        PersianDate persianDate = new PersianDate();
        persianDate.initGrgDate(year, month, day, hour, minute, second);

        if (persianDate.getHour() < 10) {
            if (persianDate.getMinute() < 10)
                return persianDate.dayName() + " " + persianDate.getShDay() + " " + persianDate.monthName() + " " + StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + " " + "ساعت" + " " + "0" + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
            else
                return persianDate.dayName() + " " + persianDate.getShDay() + " " + persianDate.monthName() + " " + StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + " " + "ساعت" + " " + "0" + persianDate.getHour() + ":" + persianDate.getMinute();
        } else {
            if (persianDate.getMinute() < 10)
                return persianDate.dayName() + " " + persianDate.getShDay() + " " + persianDate.monthName() + " " + StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + " " + "ساعت" + " " + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
            else
                return persianDate.dayName() + " " + persianDate.getShDay() + " " + persianDate.monthName() + " " + StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + " " + "ساعت" + " " + persianDate.getHour() + ":" + persianDate.getMinute();
        }
    }

    public static String gregorianToJalali7(String value) {
        int year = Integer.parseInt(dateToString("yyyy", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int month = Integer.parseInt(dateToString("MM", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int day = Integer.parseInt(dateToString("dd", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int hour = Integer.parseInt(dateToString("HH", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int minute = Integer.parseInt(dateToString("mm", stringToDate("yyyy-MM-dd HH:mm:ss", value)));
        int second = Integer.parseInt(dateToString("ss", stringToDate("yyyy-MM-dd HH:mm:ss", value)));

        PersianDate persianDate = new PersianDate();
        persianDate.initGrgDate(year, month, day, hour, minute, second);

        if (persianDate.getHour() < 10) {
            if (persianDate.getMinute() < 10)
                return "ساعت" + " " + "0" + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
            else
                return "ساعت" + " " + "0" + persianDate.getHour() + ":" + persianDate.getMinute();
        } else {
            if (persianDate.getMinute() < 10)
                return "ساعت" + " " + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
            else
                return "ساعت" + " " + persianDate.getHour() + ":" + persianDate.getMinute();
        }
    }

    public static String jalaliToGregorian(String value) {
        int year = Integer.parseInt(dateToString("yyyy", stringToDate("yyyy-MM-dd", value)));
        int month = Integer.parseInt(dateToString("MM", stringToDate("yyyy-MM-dd", value)));
        int day = Integer.parseInt(dateToString("dd", stringToDate("yyyy-MM-dd", value)));

        PersianDate persianDate = new PersianDate();
        persianDate.initJalaliDate(year, month, day);

        if (persianDate.getGrgMonth() < 10) {
            if (persianDate.getGrgDay() < 10)
                return persianDate.getGrgYear() + "-" + "0" + persianDate.getGrgMonth() + "-" + "0" + persianDate.getGrgDay();
            else
                return persianDate.getGrgYear() + "-" + "0" + persianDate.getGrgMonth() + "-" + persianDate.getGrgDay();
        } else {
            if (persianDate.getGrgDay() < 10)
                return persianDate.getGrgYear() + "-" + persianDate.getGrgMonth() + "-" + "0" + persianDate.getGrgDay();
            else
                return persianDate.getGrgYear() + "-" + persianDate.getGrgMonth() + "-" + persianDate.getGrgDay();
        }
    }

}