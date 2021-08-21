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

    public static String jalDayName(String timestamp) {
        long value = Long.parseLong(timestamp);
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        return persianDate.dayName();
    }

    public static String jalHHsMM(String timestamp) {
        long value = Long.parseLong(timestamp);
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        if (persianDate.getHour() < 10) {
            if (persianDate.getMinute() < 10)
                return "0" + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
            else
                return "0" + persianDate.getHour() + ":" + persianDate.getMinute();
        } else {
            if (persianDate.getMinute() < 10)
                return persianDate.getHour() + ":" + "0" + persianDate.getMinute();
            else
                return persianDate.getHour() + ":" + persianDate.getMinute();
        }
    }

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

    public static String jalYYYYsNMMsDDsNDD(String timestamp, String seperator) {
        long value = Long.parseLong(timestamp);
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        return persianDate.dayName() + seperator + persianDate.getShDay() + seperator + persianDate.monthName() + seperator + persianDate.getShYear();
    }

    public static String jalHHoMMoYYoMMoDD(String timestamp) {
        long value = Long.parseLong(timestamp);
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        if (persianDate.getShMonth() < 10) {
            if (persianDate.getShDay() < 10) {
                if (persianDate.getHour() < 10) {
                    if (persianDate.getMinute() < 10)
                        return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + "0" + persianDate.getShMonth() + "-" + "0" + persianDate.getShDay() + " " + "0" + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
                    else
                        return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + "0" + persianDate.getShMonth() + "-" + "0" + persianDate.getShDay() + " " + "0" + persianDate.getHour() + ":" + persianDate.getMinute();
                } else {
                    if (persianDate.getMinute() < 10)
                        return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + "0" + persianDate.getShMonth() + "-" + "0" + persianDate.getShDay() + " " + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
                    else
                        return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + "0" + persianDate.getShMonth() + "-" + "0" + persianDate.getShDay() + " " + persianDate.getHour() + ":" + persianDate.getMinute();
                }
            } else {
                if (persianDate.getHour() < 10) {
                    if (persianDate.getMinute() < 10)
                        return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + "0" + persianDate.getShMonth() + "-" + persianDate.getShDay() + " " + "0" + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
                    else
                        return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + "0" + persianDate.getShMonth() + "-" + persianDate.getShDay() + " " + "0" + persianDate.getHour() + ":" + persianDate.getMinute();
                } else {
                    if (persianDate.getMinute() < 10)
                        return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + "0" + persianDate.getShMonth() + "-" + persianDate.getShDay() + " " + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
                    else
                        return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + "0" + persianDate.getShMonth() + "-" + persianDate.getShDay() + " " + persianDate.getHour() + ":" + persianDate.getMinute();
                }
            }
        } else {
            if (persianDate.getShDay() < 10) {

                if (persianDate.getHour() < 10) {
                    if (persianDate.getMinute() < 10)
                        return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + persianDate.getShMonth() + "-" + "0" + persianDate.getShDay() + " " + "0" + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
                    else
                        return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + persianDate.getShMonth() + "-" + "0" + persianDate.getShDay() + " " + "0" + persianDate.getHour() + ":" + persianDate.getMinute();
                } else {
                    if (persianDate.getMinute() < 10)
                        return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + persianDate.getShMonth() + "-" + "0" + persianDate.getShDay() + " " + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
                    else
                        return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + persianDate.getShMonth() + "-" + "0" + persianDate.getShDay() + " " + persianDate.getHour() + ":" + persianDate.getMinute();
                }
            } else {
                if (persianDate.getHour() < 10) {
                    if (persianDate.getMinute() < 10)
                        return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + persianDate.getShMonth() + "-" + persianDate.getShDay() + " " + "0" + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
                    else
                        return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + persianDate.getShMonth() + "-" + persianDate.getShDay() + " " + "0" + persianDate.getHour() + ":" + persianDate.getMinute();
                } else {
                    if (persianDate.getMinute() < 10)
                        return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + persianDate.getShMonth() + "-" + persianDate.getShDay() + " " + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
                    else
                        return StringManager.substring(String.valueOf(persianDate.getShYear()), 2) + "-" + persianDate.getShMonth() + "-" + persianDate.getShDay() + " " + persianDate.getHour() + ":" + persianDate.getMinute();
                }
            }
        }
    }

    public static String jalYYYYsNMMsDDsNDDsHHsMM(String timestamp, String seperator) {
        long value = Long.parseLong(timestamp);
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        if (persianDate.getHour() < 10) {
            if (persianDate.getMinute() < 10)
                return persianDate.dayName() + seperator + persianDate.getShDay() + seperator + persianDate.monthName() + seperator + persianDate.getShYear() + seperator + "ساعت" + seperator + "0" + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
            else
                return persianDate.dayName() + seperator + persianDate.getShDay() + seperator + persianDate.monthName() + seperator + persianDate.getShYear() + seperator + "ساعت" + seperator + "0" + persianDate.getHour() + ":" + persianDate.getMinute();
        } else {
            if (persianDate.getMinute() < 10)
                return persianDate.dayName() + seperator + persianDate.getShDay() + seperator + persianDate.monthName() + seperator + persianDate.getShYear() + seperator + "ساعت" + seperator + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
            else
                return persianDate.dayName() + seperator + persianDate.getShDay() + seperator + persianDate.monthName() + seperator + persianDate.getShYear() + seperator + "ساعت" + seperator + persianDate.getHour() + ":" + persianDate.getMinute();
        }
    }

    public static String jalYYYYsNMMsDDsNDDnlHHsMM(String timestamp, String seperator) {
        long value = Long.parseLong(timestamp);
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        if (persianDate.getHour() < 10) {
            if (persianDate.getMinute() < 10)
                return persianDate.dayName() + seperator + persianDate.getShDay() + seperator + persianDate.monthName() + seperator + persianDate.getShYear() + "\n" + "ساعت" + seperator + "0" + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
            else
                return persianDate.dayName() + seperator + persianDate.getShDay() + seperator + persianDate.monthName() + seperator + persianDate.getShYear() + "\n" + "ساعت" + seperator + "0" + persianDate.getHour() + ":" + persianDate.getMinute();
        } else {
            if (persianDate.getMinute() < 10)
                return persianDate.dayName() + seperator + persianDate.getShDay() + seperator + persianDate.monthName() + seperator + persianDate.getShYear() + "\n" + "ساعت" + seperator + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
            else
                return persianDate.dayName() + seperator + persianDate.getShDay() + seperator + persianDate.monthName() + seperator + persianDate.getShYear() + "\n" + "ساعت" + seperator + persianDate.getHour() + ":" + persianDate.getMinute();
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
            case "یک\u200Cشنبه":
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
            case "یک\u200Cشنبه":
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

                return dateCorrector(persianDate.getShMonth(), persianDate.getShDay(), lastPersianDate.getShMonth(), lastPersianDate.getShDay());
            }
            case "یک\u200Cشنبه": {
                Date firstDay = new Date(date.getTime() - (86400000));
                Date lastDay = new Date(date.getTime() + (5*86400000));

                PersianDate firstPersianDate = dateToPersian(firstDay);
                PersianDate lastPersianDate = dateToPersian(lastDay);

                return dateCorrector(firstPersianDate.getShMonth(), firstPersianDate.getShDay(), lastPersianDate.getShMonth(), lastPersianDate.getShDay());
            }
            case "دوشنبه": {
                Date firstDay = new Date(date.getTime() - (2*86400000));
                Date lastDay = new Date(date.getTime() + (4*86400000));

                PersianDate firstPersianDate = dateToPersian(firstDay);
                PersianDate lastPersianDate = dateToPersian(lastDay);

                return dateCorrector(firstPersianDate.getShMonth(), firstPersianDate.getShDay(), lastPersianDate.getShMonth(), lastPersianDate.getShDay());
            }
            case "سه\u200Cشنبه": {
                Date firstDay = new Date(date.getTime() - (3*86400000));
                Date lastDay = new Date(date.getTime() + (3*86400000));

                PersianDate firstPersianDate = dateToPersian(firstDay);
                PersianDate lastPersianDate = dateToPersian(lastDay);

                return dateCorrector(firstPersianDate.getShMonth(), firstPersianDate.getShDay(), lastPersianDate.getShMonth(), lastPersianDate.getShDay());
            }
            case "چهارشنبه": {
                Date firstDay = new Date(date.getTime() - (4*86400000));
                Date lastDay = new Date(date.getTime() + (2*86400000));

                PersianDate firstPersianDate = dateToPersian(firstDay);
                PersianDate lastPersianDate = dateToPersian(lastDay);

                return dateCorrector(firstPersianDate.getShMonth(), firstPersianDate.getShDay(), lastPersianDate.getShMonth(), lastPersianDate.getShDay());
            }
            case "پنج\u200Cشنبه": {
                Date firstDay = new Date(date.getTime() - (5*86400000));
                Date lastDay = new Date(date.getTime() + (86400000));

                PersianDate firstPersianDate = dateToPersian(firstDay);
                PersianDate lastPersianDate = dateToPersian(lastDay);

                return dateCorrector(firstPersianDate.getShMonth(), firstPersianDate.getShDay(), lastPersianDate.getShMonth(), lastPersianDate.getShDay());
            }
            case "جمعه": {
                Date firstDay = new Date(date.getTime() - (6*86400000));
                PersianDate firstPersianDate = dateToPersian(firstDay);

                return dateCorrector(firstPersianDate.getShMonth(), firstPersianDate.getShDay(), persianDate.getShMonth(), persianDate.getShDay());
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
            case "یک\u200Cشنبه":
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

    /*
    ---------- Date Corrector ----------
    */

    public static String dateCorrector(int firstMonth, int firstDay, int lastMonth, int lastDay) {
        if (firstMonth < 10) {
            if (firstDay < 10) {
                if (lastMonth < 10) {
                    if (lastDay < 10)
                        return "0" + firstMonth + "/" + "0" + firstDay + " تا " + "0" + lastMonth + "/" + "0" + lastDay;
                    else
                        return "0" + firstMonth + "/" + "0" + firstDay + " تا " + "0" + lastMonth + "/" + lastDay;
                } else {
                    if (lastDay < 10)
                        return "0" + firstMonth + "/" + "0" + firstDay + " تا " + lastMonth + "/" + "0" + lastDay;
                    else
                        return "0" + firstMonth + "/" + "0" + firstDay + " تا " + lastMonth + "/" + lastDay;
                }
            } else {
                if (lastMonth < 10) {
                    if (lastDay < 10)
                        return "0" + firstMonth + "/" + firstDay + " تا " + "0" + lastMonth + "/" + "0" + lastDay;
                    else
                        return "0" + firstMonth + "/" + firstDay + " تا " + "0" + lastMonth + "/" + lastDay;
                } else {
                    if (lastDay < 10)
                        return "0" + firstMonth + "/" + firstDay + " تا " + lastMonth + "/" + "0" + lastDay;
                    else
                        return "0" + firstMonth + "/" + firstDay + " تا " + lastMonth + "/" + lastDay;
                }
            }
        } else {
            if (firstDay < 10) {
                if (lastMonth < 10) {
                    if (lastDay < 10)
                        return firstMonth + "/" + "0" + firstDay + " تا " + "0" + lastMonth + "/" + "0" + lastDay;
                    else
                        return firstMonth + "/" + "0" + firstDay + " تا " + "0" + lastMonth + "/" + lastDay;
                } else {
                    if (lastDay < 10)
                        return firstMonth + "/" + "0" + firstDay + " تا " + lastMonth + "/" + "0" + lastDay;
                    else
                        return firstMonth + "/" + "0" + firstDay + " تا " + lastMonth + "/" + lastDay;
                }
            } else {
                if (lastMonth < 10) {
                    if (lastDay < 10)
                        return firstMonth + "/" + firstDay + " تا " + "0" + lastMonth + "/" + "0" + lastDay;
                    else
                        return firstMonth + "/" + firstDay + " تا " + "0" + lastMonth + "/" + lastDay;
                } else {
                    if (lastDay < 10)
                        return firstMonth + "/" + firstDay + " تا " + lastMonth + "/" + "0" + lastDay;
                    else
                        return firstMonth + "/" +firstDay + " تا " + lastMonth + "/" + lastDay;
                }
            }
        }
    }

}