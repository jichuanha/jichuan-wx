package com.hzkans.crm.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author wsh
 * @date 2018/11/27
 */
public class DateUtil {
    private static int FIRST_DATE_OF_WEEK = 1;
    public static final int BEFORE_START_DATE = -2;
    public static final int EQUAL_START_DATE = -1;
    public static final int BETWEEN_TOW_DATE = 0;
    public static final int EQUAL_END_DATE = 1;
    public static final int AFTER_END_DATE = 2;
    public static final int TREE_DATE_EQUAL = 3;
    public static final String NORMAL_DATE_PATTERN = "yyyy-MM-dd";
    public static final String ZHCN_DATE_PATTERN = "yyyy年MM月dd日";
    public static final String ZHCN_DATE_TIME_PATTERN = "yyyy年MM月dd日 HH:mm:ss";
    public static final String NORMAL_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date parse(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date d = sdf.parse(date);
            return d;
        } catch (ParseException var4) {
            throw new RuntimeException("日期转换错误", var4);
        }
    }

    public static Date add(Date date, int calendorField, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calendorField, amount);
        return cal.getTime();
    }

    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        switch(cal.get(7)) {
            case 1:
                return 7;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            default:
                return 6;
        }
    }

    public static int getDayOfMouth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(5);
    }

    public static long compareTo(Date date, Date compareDate) {
        date = parse(format(date, "yyMMdd"), "yyMMdd");
        compareDate = parse(format(compareDate, "yyMMdd"), "yyMMdd");
        long a = (date.getTime() - compareDate.getTime()) / 86400000L;
        return a;
    }

    public static boolean isEndOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(5, 1);
        int weekDay = cal.get(7);
        return weekDay == FIRST_DATE_OF_WEEK;
    }

    public static boolean isMonthEnd(Date nowDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        cal.add(5, 1);
        int day = cal.get(5);
        return day == 1;
    }

    public static boolean isQuarterEnd(Date nowDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        int month = cal.get(2);
        cal.add(5, 1);
        int day = cal.get(5);
        return day == 1 && (month == 2 || month == 5 || month == 8 || month == 11);
    }

    public static boolean isQuarterBegin(Date nowDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        int month = cal.get(2);
        int day = cal.get(5);
        return day == 1 && (month == 0 || month == 3 || month == 6 || month == 9);
    }

    public static boolean isHalfYearEnd(Date nowDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        int month = cal.get(2);
        cal.add(5, 1);
        int day = cal.get(5);
        return day == 1 && (month == 5 || month == 11);
    }

    public static boolean isHalfYearBegin(Date nowDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        int month = cal.get(2);
        int day = cal.get(5);
        return day == 1 && (month == 0 || month == 6);
    }

    public static boolean isYearEnd(Date nowDate) {
        return "1231".equals(format(nowDate, "MMdd"));
    }

    public static boolean isYearBegin(Date nowDate) {
        return "0101".equals(format(nowDate, "MMdd"));
    }

    public static Calendar getYMD(Date date) {
        String dateStr = format(date, "yyyyMMdd");
        date = parse(dateStr, "yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static boolean chkStlTime(String stlCycle, String stlCycleDay, Date tranDate) {
        boolean b = false;
        switch(stlCycle.toCharArray()[0]) {
            case '1':
                b = true;
                break;
            case '2':
                String nowDate = String.valueOf(getDayOfWeek(tranDate));
                if(nowDate.equals(stlCycleDay)) {
                    b = true;
                }
                break;
            case '3':
                if(stlCycleDay.equals("0")) {
                    boolean result = isMonthEnd(tranDate);
                    if(result) {
                        b = true;
                    }
                } else {
                    String nowDate1 = String.valueOf(getDayOfMouth(tranDate));
                    if(nowDate1.equals(stlCycleDay)) {
                        b = true;
                    }
                }
                break;
            case '4':
                if("1".equals(stlCycleDay)) {
                    if(isQuarterBegin(tranDate)) {
                        b = true;
                    }
                } else if("0".equals(stlCycleDay) && isQuarterEnd(tranDate)) {
                    b = true;
                }
                break;
            case '5':
                if("1".equals(stlCycleDay)) {
                    if(isHalfYearBegin(tranDate)) {
                        b = true;
                    }
                } else if("0".equals(stlCycleDay) && isHalfYearEnd(tranDate)) {
                    b = true;
                }
                break;
            case '6':
                if("1".equals(stlCycleDay)) {
                    if(isYearBegin(tranDate)) {
                        b = true;
                    }
                } else if("0".equals(stlCycleDay) && isYearEnd(tranDate)) {
                    b = true;
                }
        }

        return b;
    }

    public static boolean monFreCompare(Date startTime, Date endTime, int interTime) {
        boolean flag = false;
        long a = endTime.getTime() - startTime.getTime();
        long interval = a / 1000L;
        if(interval <= (long)interTime && interval > 0L) {
            flag = true;
        }

        return flag;
    }

    private DateUtil() {
    }

    public static boolean isBefore(Date firstDate, Date secondDate) {
        return compare(firstDate, secondDate) < 0;
    }

    public static boolean isAfter(Date firstDate, Date secondDate) {
        return compare(firstDate, secondDate) > 0;
    }

    public static boolean isEqual(Date firstDate, Date secondDate) {
        return compare(firstDate, secondDate) == 0;
    }

    public static int compare(Date firstDate, Date secondDate) {
        Calendar firstCalendar = null;
        if(firstDate != null) {
            firstCalendar = Calendar.getInstance();
            firstCalendar.setTime(firstDate);
        }

        Calendar secondCalendar = null;
        if(firstDate != null) {
            secondCalendar = Calendar.getInstance();
            secondCalendar.setTime(secondDate);
        }

        try {
            return firstCalendar.compareTo(secondCalendar);
        } catch (NullPointerException var5) {
            throw new IllegalArgumentException(var5);
        } catch (IllegalArgumentException var6) {
            throw new IllegalArgumentException(var6);
        }
    }

    public static int betweenTowDate(Date startDate, Date endDate, Date inDate) {
        if(isBefore(endDate, startDate)) {
            throw new IllegalArgumentException("endDate can not before startDate!");
        } else {
            int sflag = compare(inDate, startDate);
            int eflag = compare(inDate, endDate);
            int flag = 0;
            if(sflag < 0) {
                flag = -2;
            } else if(sflag == 0) {
                if(eflag == 0) {
                    flag = 3;
                } else {
                    flag = -1;
                }
            } else if(sflag > 0 && eflag < 0) {
                flag = 0;
            } else if(eflag == 0) {
                flag = 1;
            } else if(eflag > 0) {
                flag = 2;
            }

            return flag;
        }
    }

    public static int betweenTowDate(Date startDate, Date endDate) {
        return betweenTowDate(startDate, endDate, new Date());
    }

    public static String addDate(String startDate, int x) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;

        try {
            date = format.parse(startDate);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        if(date == null) {
            return "";
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(11, x);
            date = cal.getTime();
            cal = null;
            return format.format(date);
        }
    }

    public static void main(String[] args) {
        String startTime = "2017-02-20 9:40:12";
        String add = addDate(startTime, 168);
        System.out.println("add:{}" + add);
        Date startDate = parse(add, "yyyy-MM-dd HH:mm:ss");
        int count = compare(startDate, new Date());
        System.out.println(count);

        while(count < 0) {
            add = addDate(add, 168);
            if(compare(parse(add, "yyyy-MM-dd HH:mm:ss"), new Date()) > 0) {
                break;
            }
        }

        System.out.println("add2：{}" + add);
    }
}

