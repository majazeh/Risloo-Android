package com.majazeh.risloo.utils.managers;

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
    ---------- Current's ----------
    */

    public static Date currentDate() {
        Calendar calendar = Calendar.getInstance();

        return calendar.getTime();
    }

    public static long currentTimestamp() {
        long timeLong = currentDate().getTime();

        return timeLong / 1000;
    }

    /*
    ---------- Convert's ----------
    */

    // -------------------- Date & Timestamp

    public static Date timestampToDate(long value) {
        Timestamp timestamp = new Timestamp(value * 1000);
        long timeLong = timestamp.getTime();

        return new Date(timeLong);
    }

    public static long dateToTimestamp(Date value) {
        long timeLong = value.getTime();

        return timeLong / 1000;
    }

    // -------------------- Date & String

    @SuppressLint("SimpleDateFormat")
    public static Date stringToDate(String pattern, String value) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        try {
            return simpleDateFormat.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SuppressLint("SimpleDateFormat")
    public static String dateToString(String pattern, Date value) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(value);
    }

    // -------------------- Date & PersianDate

    public static Date persianToDate(PersianDate value) {
        int year = value.getGrgYear();
        int month = value.getGrgMonth() - 1;
        int day = value.getGrgDay();
        int hour = value.getHour();
        int minute = value.getMinute();
        int second = value.getSecond();

        return createDate(year, month, day, hour, minute, second);
    }

    public static PersianDate dateToPersian(Date value) {
        int year = Integer.parseInt(dateToString("yyyy", value));
        int month = Integer.parseInt(dateToString("MM", value));
        int day = Integer.parseInt(dateToString("dd", value));
        int hour = Integer.parseInt(dateToString("HH", value));
        int minute = Integer.parseInt(dateToString("mm", value));
        int second = Integer.parseInt(dateToString("ss", value));

        return createGrgPersianDate(year, month, day, hour, minute, second);
    }

    /*
    ---------- Create's ----------
    */

    public static Date createDate(int year, int month, int day, int hour, int minute, int second) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(year, month, day, hour, minute, second);

        return gregorianCalendar.getTime();
    }

    public static PersianDate createJalPersianDate(int year, int month, int day, int hour, int minute, int second) {
        PersianDate persianDate = new PersianDate();
        persianDate.initJalaliDate(year, month, day, hour, minute, second);

        return persianDate;
    }

    public static PersianDate createGrgPersianDate(int year, int month, int day, int hour, int minute, int second) {
        PersianDate persianDate = new PersianDate();
        persianDate.initGrgDate(year, month, day, hour, minute, second);

        return persianDate;
    }

    /*
    ---------- Timestamp's ----------
    */

    // -------------------- String

    public static long timestampAccurate(String time, String date) {
        long timeLong = 0;
        long dateLong = 0;

        if (!time.equals(""))
            timeLong = Long.parseLong(time);
        if (!date.equals(""))
            dateLong = Long.parseLong(date);

        Date td = timestampToDate(timeLong);
        Date dd = timestampToDate(dateLong);
        PersianDate tpd = dateToPersian(td);
        PersianDate dpd = dateToPersian(dd);

        int year = dpd.getShYear();
        int month = dpd.getShMonth();
        int day = dpd.getShDay();
        int hour = tpd.getHour();
        int minute = tpd.getMinute();
        int second = tpd.getSecond();

        PersianDate mpd = createJalPersianDate(year, month, day, hour, minute, second);
        Date md = persianToDate(mpd);

        return dateToTimestamp(md);
    }

    public static long timestampRelative(String day, String hour, String minute) {
        long dayLong = 0;
        long hourLong = 0;
        long minuteLong = 0;

        if (!day.equals(""))
            dayLong = Long.parseLong(day);
        if (!hour.equals(""))
            hourLong = Long.parseLong(hour);
        if (!minute.equals(""))
            minuteLong = Long.parseLong(minute);

        long dhms = dayLong * 24 * 60 * 60;
        long hms = hourLong * 60 * 60;
        long ms = minuteLong * 60;

        return dhms + hms + ms;
    }

    // -------------------- long

    public static long timestampPreFriday(long value) {
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        switch (persianDate.dayName()) {
            case "شنبه":
                return new Date(date.getTime() - (86400000)).getTime() / 1000;
            case "یک\u200Cشنبه":
                return new Date(date.getTime() - (2 * 86400000)).getTime() / 1000;
            case "دوشنبه":
                return new Date(date.getTime() - (3 * 86400000)).getTime() / 1000;
            case "سه\u200Cشنبه":
                return new Date(date.getTime() - (4 * 86400000)).getTime() / 1000;
            case "چهارشنبه":
                return new Date(date.getTime() - (5 * 86400000)).getTime() / 1000;
            case "پنج\u200Cشنبه":
                return new Date(date.getTime() - (6 * 86400000)).getTime() / 1000;
            case "جمعه":
                return new Date(date.getTime() - (7 * 86400000)).getTime() / 1000;
            default:
                return value;
        }
    }

    public static long timestampNxtSaturday(long value) {
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        switch (persianDate.dayName()) {
            case "شنبه":
                return new Date(date.getTime() + (7 * 86400000)).getTime() / 1000;
            case "یک\u200Cشنبه":
                return new Date(date.getTime() + (6 * 86400000)).getTime() / 1000;
            case "دوشنبه":
                return new Date(date.getTime() + (5 * 86400000)).getTime() / 1000;
            case "سه\u200Cشنبه":
                return new Date(date.getTime() + (4 * 86400000)).getTime() / 1000;
            case "چهارشنبه":
                return new Date(date.getTime() + (3 * 86400000)).getTime() / 1000;
            case "پنج\u200Cشنبه":
                return new Date(date.getTime() + (2 * 86400000)).getTime() / 1000;
            case "جمعه":
                return new Date(date.getTime() + (86400000)).getTime() / 1000;
            default:
                return value;
        }
    }

    /*
    ---------- Position's ----------
    */

    public static int positionTimestampDayName(long value) {
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        switch (persianDate.dayName()) {
            case "شنبه":
                return 0;
            case "یک\u200Cشنبه":
                return 1;
            case "دوشنبه":
                return 2;
            case "سه\u200Cشنبه":
                return 3;
            case "چهارشنبه":
                return 4;
            case "پنج\u200Cشنبه":
                return 5;
            case "جمعه":
                return 6;
            default:
                return 7;
        }
    }

    /*
    ---------- PersianDate's ----------
    */

    public static String jalND(String timestamp) {
        long value = Long.parseLong(timestamp);
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        return persianDate.dayName();
    }

    public static String jalHHcMM(String timestamp) {
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

    public static String jalNDsDDsNMsYYYY(String timestamp, String seperator) {
        long value = Long.parseLong(timestamp);
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        return persianDate.dayName() + seperator + persianDate.getShDay() + seperator + persianDate.monthName() + seperator + persianDate.getShYear();
    }

    public static String jalNDsDDsNMsYYYYsHHcMM(String timestamp, String seperator) {
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

    public static String jalNDsDDsNMsYYYYnHHcMM(String timestamp, String seperator) {
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

    public static String jalHHcMMsYYsMMsDD(String timestamp, String seperator) {
        long value = Long.parseLong(timestamp);
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        if (persianDate.getShMonth() < 10) {
            if (persianDate.getShDay() < 10) {
                if (persianDate.getHour() < 10) {
                    if (persianDate.getMinute() < 10)
                        return StringManager.suffix(String.valueOf(persianDate.getShYear()), 2) + seperator + "0" + persianDate.getShMonth() + seperator + "0" + persianDate.getShDay() + " " + "0" + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
                    else
                        return StringManager.suffix(String.valueOf(persianDate.getShYear()), 2) + seperator + "0" + persianDate.getShMonth() + seperator + "0" + persianDate.getShDay() + " " + "0" + persianDate.getHour() + ":" + persianDate.getMinute();
                } else {
                    if (persianDate.getMinute() < 10)
                        return StringManager.suffix(String.valueOf(persianDate.getShYear()), 2) + seperator + "0" + persianDate.getShMonth() + seperator + "0" + persianDate.getShDay() + " " + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
                    else
                        return StringManager.suffix(String.valueOf(persianDate.getShYear()), 2) + seperator + "0" + persianDate.getShMonth() + seperator + "0" + persianDate.getShDay() + " " + persianDate.getHour() + ":" + persianDate.getMinute();
                }
            } else {
                if (persianDate.getHour() < 10) {
                    if (persianDate.getMinute() < 10)
                        return StringManager.suffix(String.valueOf(persianDate.getShYear()), 2) + seperator + "0" + persianDate.getShMonth() + seperator + persianDate.getShDay() + " " + "0" + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
                    else
                        return StringManager.suffix(String.valueOf(persianDate.getShYear()), 2) + seperator + "0" + persianDate.getShMonth() + seperator + persianDate.getShDay() + " " + "0" + persianDate.getHour() + ":" + persianDate.getMinute();
                } else {
                    if (persianDate.getMinute() < 10)
                        return StringManager.suffix(String.valueOf(persianDate.getShYear()), 2) + seperator + "0" + persianDate.getShMonth() + seperator + persianDate.getShDay() + " " + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
                    else
                        return StringManager.suffix(String.valueOf(persianDate.getShYear()), 2) + seperator + "0" + persianDate.getShMonth() + seperator + persianDate.getShDay() + " " + persianDate.getHour() + ":" + persianDate.getMinute();
                }
            }
        } else {
            if (persianDate.getShDay() < 10) {

                if (persianDate.getHour() < 10) {
                    if (persianDate.getMinute() < 10)
                        return StringManager.suffix(String.valueOf(persianDate.getShYear()), 2) + seperator + persianDate.getShMonth() + seperator + "0" + persianDate.getShDay() + " " + "0" + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
                    else
                        return StringManager.suffix(String.valueOf(persianDate.getShYear()), 2) + seperator + persianDate.getShMonth() + seperator + "0" + persianDate.getShDay() + " " + "0" + persianDate.getHour() + ":" + persianDate.getMinute();
                } else {
                    if (persianDate.getMinute() < 10)
                        return StringManager.suffix(String.valueOf(persianDate.getShYear()), 2) + seperator + persianDate.getShMonth() + seperator + "0" + persianDate.getShDay() + " " + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
                    else
                        return StringManager.suffix(String.valueOf(persianDate.getShYear()), 2) + seperator + persianDate.getShMonth() + seperator + "0" + persianDate.getShDay() + " " + persianDate.getHour() + ":" + persianDate.getMinute();
                }
            } else {
                if (persianDate.getHour() < 10) {
                    if (persianDate.getMinute() < 10)
                        return StringManager.suffix(String.valueOf(persianDate.getShYear()), 2) + seperator + persianDate.getShMonth() + seperator + persianDate.getShDay() + " " + "0" + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
                    else
                        return StringManager.suffix(String.valueOf(persianDate.getShYear()), 2) + seperator + persianDate.getShMonth() + seperator + persianDate.getShDay() + " " + "0" + persianDate.getHour() + ":" + persianDate.getMinute();
                } else {
                    if (persianDate.getMinute() < 10)
                        return StringManager.suffix(String.valueOf(persianDate.getShYear()), 2) + seperator + persianDate.getShMonth() + seperator + persianDate.getShDay() + " " + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
                    else
                        return StringManager.suffix(String.valueOf(persianDate.getShYear()), 2) + seperator + persianDate.getShMonth() + seperator + persianDate.getShDay() + " " + persianDate.getHour() + ":" + persianDate.getMinute();
                }
            }
        }
    }

    public static String jalDDsNMsHHcMM(long timestamp, String seperator) {
        Date date = new Date(timestamp);
        PersianDate persianDate = dateToPersian(date);

        if (persianDate.getHour() < 10) {
            if (persianDate.getMinute() < 10)
                return persianDate.getShDay() + seperator + persianDate.monthName() + "  " + "ساعت" + seperator + "0" + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
            else
                return persianDate.getShDay() + seperator + persianDate.monthName() + "  " + "ساعت" + seperator + "0" + persianDate.getHour() + ":" + persianDate.getMinute();
        } else {
            if (persianDate.getMinute() < 10)
                return persianDate.getShDay() + seperator + persianDate.monthName() + "  " + "ساعت" + seperator + persianDate.getHour() + ":" + "0" + persianDate.getMinute();
            else
                return persianDate.getShDay() + seperator + persianDate.monthName() + "  " + "ساعت" + seperator + persianDate.getHour() + ":" + persianDate.getMinute();
        }
    }

    /*
    ---------- Week's ----------
    */

    public static String jalWeekString(long value) {
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        switch (persianDate.dayName()) {
            case "شنبه": {
                Date lastDay = new Date(date.getTime() + (6 * 86400000));
                PersianDate lastPersianDate = dateToPersian(lastDay);

                return arrangeWeek(persianDate.getShMonth(), persianDate.getShDay(), lastPersianDate.getShMonth(), lastPersianDate.getShDay());
            }
            case "یک\u200Cشنبه": {
                Date firstDay = new Date(date.getTime() - (86400000));
                Date lastDay = new Date(date.getTime() + (5 * 86400000));

                PersianDate firstPersianDate = dateToPersian(firstDay);
                PersianDate lastPersianDate = dateToPersian(lastDay);

                return arrangeWeek(firstPersianDate.getShMonth(), firstPersianDate.getShDay(), lastPersianDate.getShMonth(), lastPersianDate.getShDay());
            }
            case "دوشنبه": {
                Date firstDay = new Date(date.getTime() - (2 * 86400000));
                Date lastDay = new Date(date.getTime() + (4 * 86400000));

                PersianDate firstPersianDate = dateToPersian(firstDay);
                PersianDate lastPersianDate = dateToPersian(lastDay);

                return arrangeWeek(firstPersianDate.getShMonth(), firstPersianDate.getShDay(), lastPersianDate.getShMonth(), lastPersianDate.getShDay());
            }
            case "سه\u200Cشنبه": {
                Date firstDay = new Date(date.getTime() - (3 * 86400000));
                Date lastDay = new Date(date.getTime() + (3 * 86400000));

                PersianDate firstPersianDate = dateToPersian(firstDay);
                PersianDate lastPersianDate = dateToPersian(lastDay);

                return arrangeWeek(firstPersianDate.getShMonth(), firstPersianDate.getShDay(), lastPersianDate.getShMonth(), lastPersianDate.getShDay());
            }
            case "چهارشنبه": {
                Date firstDay = new Date(date.getTime() - (4 * 86400000));
                Date lastDay = new Date(date.getTime() + (2 * 86400000));

                PersianDate firstPersianDate = dateToPersian(firstDay);
                PersianDate lastPersianDate = dateToPersian(lastDay);

                return arrangeWeek(firstPersianDate.getShMonth(), firstPersianDate.getShDay(), lastPersianDate.getShMonth(), lastPersianDate.getShDay());
            }
            case "پنج\u200Cشنبه": {
                Date firstDay = new Date(date.getTime() - (5 * 86400000));
                Date lastDay = new Date(date.getTime() + (86400000));

                PersianDate firstPersianDate = dateToPersian(firstDay);
                PersianDate lastPersianDate = dateToPersian(lastDay);

                return arrangeWeek(firstPersianDate.getShMonth(), firstPersianDate.getShDay(), lastPersianDate.getShMonth(), lastPersianDate.getShDay());
            }
            case "جمعه": {
                Date firstDay = new Date(date.getTime() - (6 * 86400000));
                PersianDate firstPersianDate = dateToPersian(firstDay);

                return arrangeWeek(firstPersianDate.getShMonth(), firstPersianDate.getShDay(), persianDate.getShMonth(), persianDate.getShDay());
            }
            default:
                return "";
        }
    }

    public static ArrayList<Long> jalWeekTimestamps(long value) {
        Date date = timestampToDate(value);
        PersianDate persianDate = dateToPersian(date);

        switch (persianDate.dayName()) {
            case "شنبه": {
                ArrayList<Long> timestamps = new ArrayList<>();

                timestamps.add(new Date(date.getTime()).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (2 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (3 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (4 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (5 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (6 * 86400000)).getTime() / 1000);

                return timestamps;
            }
            case "یک\u200Cشنبه": {
                ArrayList<Long> timestamps = new ArrayList<>();

                timestamps.add(new Date(date.getTime() - (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime()).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (2 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (3 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (4 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (5 * 86400000)).getTime() / 1000);

                return timestamps;
            }
            case "دوشنبه": {
                ArrayList<Long> timestamps = new ArrayList<>();

                timestamps.add(new Date(date.getTime() - (2 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime()).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (2 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (3 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (4 * 86400000)).getTime() / 1000);

                return timestamps;
            }
            case "سه\u200Cشنبه": {
                ArrayList<Long> timestamps = new ArrayList<>();

                timestamps.add(new Date(date.getTime() - (3 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (2 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime()).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (2 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (3 * 86400000)).getTime() / 1000);

                return timestamps;
            }
            case "چهارشنبه": {
                ArrayList<Long> timestamps = new ArrayList<>();

                timestamps.add(new Date(date.getTime() - (4 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (3 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (2 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime()).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (2 * 86400000)).getTime() / 1000);

                return timestamps;
            }
            case "پنج\u200Cشنبه": {
                ArrayList<Long> timestamps = new ArrayList<>();

                timestamps.add(new Date(date.getTime() - (5 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (4 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (3 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (2 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime()).getTime() / 1000);
                timestamps.add(new Date(date.getTime() + (86400000)).getTime() / 1000);

                return timestamps;
            }
            case "جمعه": {
                ArrayList<Long> timestamps = new ArrayList<>();

                timestamps.add(new Date(date.getTime() - (6 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (5 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (4 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (3 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (2 * 86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime() - (86400000)).getTime() / 1000);
                timestamps.add(new Date(date.getTime()).getTime() / 1000);

                return timestamps;
            }
            default:
                return new ArrayList<>();
        }
    }

    /*
    ---------- Private's ----------
    */

    private static String arrangeWeek(int firstMonth, int firstDay, int lastMonth, int lastDay) {
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
                        return firstMonth + "/" + firstDay + " تا " + lastMonth + "/" + lastDay;
                }
            }
        }
    }

}