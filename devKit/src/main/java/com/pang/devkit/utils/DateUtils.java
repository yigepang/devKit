package com.pang.devkit.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 作者: Created by 22943 on 2020/4/16.
 */

public class DateUtils {

    public static String TodayDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String TodayDateBH() {
        return new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
    }

    public static String TodayDate(long time) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
    }

    public static String TodayDate2() {
        return new SimpleDateFormat("yyyy.MM.dd").format(new Date());
    }

    public static String TodayDate2(long time) {
        return new SimpleDateFormat("yyyy.MM.dd").format(new Date(time));
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(long s) {
        s = s * 1000;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(s);
        String res = simpleDateFormat.format(date);

        String[] ress = res.split(" ");
        res = ress[1].substring(0, 5);
        return res;
    }

    public static Date TodayDateToDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date date = new Date();
        String dateStr = sdf.format(date);
        Date mydate = null;
        try {
            mydate = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mydate;
    }


    /**
     * 根绝传入日期 获取上一天日期
     *
     * @param date 传入日期
     * @return 前一天日期
     */
    public static String YesterDayDate(String date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String yesterdayDate = null;
        try {
            Date date1 = simpleDateFormat.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(date1);
            c.set(Calendar.HOUR_OF_DAY, -24);
            yesterdayDate = simpleDateFormat.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return yesterdayDate;
    }

    /**
     * 根绝传入日期 获取明天日期
     *
     * @param date 传入日期
     * @return 明天天日期
     */
    public static String TomorrowDayDate(String date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String yesterdayDate = null;
        try {
            Date date1 = simpleDateFormat.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(date1);
            c.set(Calendar.HOUR_OF_DAY, +24);
            yesterdayDate = simpleDateFormat.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return yesterdayDate;
    }

    /**
     * 返回当前时间戳  毫秒
     *
     * @return
     */
    public static long GetNowTimeStamp() {
        long time = System.currentTimeMillis();

        return time;
    }

    /**
     * 获取每月天数
     *
     * @param date 传入日期
     * @return 返回当月天数
     */
    public static int getDaysOfMonth(Date date) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

    }


    /**
     * 时间戳转换成日期格式字符串  13位
     *
     * @param seconds 精确到毫秒的字符串
     * @param
     * @return
     */
    public static String timeStamp2Date(long seconds, String format) {

        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(seconds));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date_str).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time);
        return t;
    }

    public static boolean isTimeDifference(String strTime1, String strTime2) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        boolean a = false;
        try {
            Date now = df.parse(strTime1);

            Date date = df.parse(strTime2);
            long l = now.getTime() - date.getTime();
            //获取时间差
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

            if (day > 0) {
                //历史数据
                a = false;
            } else if (day == 0 && hour < 2) {
                //正在传输的井
                a = true;
            }
            //  a = "" + day + "天" + hour + "小时" + min + "分" + s + "秒";
            Log.i("info", "++++++++" + "" + day + "天" + hour + "小时" + min + "分" + s + "秒");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("info", "++++++++" + e.getMessage());

        }
        return a;
    }

    /**
     * 天时分秒
     *
     * @param strTime1
     * @param strTime2
     * @return
     */
    public static String getTimeDifferenceDayHourMinS(String strTime1, String strTime2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = "";
        try {
            Date now = df.parse(strTime1);
            Date date = df.parse(strTime2);
            long l = now.getTime() - date.getTime();
            //获取时间差
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            time = day + "天" + hour + "小时" + min + "分" + s + "秒";
            return time;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 时分秒
     *
     * @param endTime
     * @param startTime
     * @param df        :传入时间的格式
     */
    public static String getTimeDifferenceHourMinS(String endTime, String startTime, SimpleDateFormat df) {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = "";
        try {
            Date now = df.parse(endTime);
            Date date = df.parse(startTime);
            long l = now.getTime() - date.getTime();
            //获取时间差
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            if (day != 0) {
                time = day + "天" + hour + "小时" + min + "分" + s + "秒";
            } else if (hour != 0) {
                time = hour + "小时" + min + "分" + s + "秒";
            } else if (min != 0) {
                time = min + "分" + s + "秒";
            } else if (s != 0) {
                time = s + "秒";
            }
            return time;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }


    /**
     * 计算俩个时间差
     *
     * @param strTime1 strTime1
     * @param strTime2 strTime2
     * @return
     */
    public static long getTimeDifference(String strTime1, String strTime2) {
        long l = 0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        boolean a = false;
        try {
            Date now = df.parse(strTime1);

            Date date = df.parse(strTime2);
            l = now.getTime() - date.getTime();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return l;
    }

    //获取当前年份的一月一号
    public static String GetNewYear() {
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);

        return year + "-01-01";
    }

    //获取当前年份
    public static String GetYear() {
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);

        return year + "";
    }


    //传入当前日期，获取当日所在周的所有日期，最后一个参数times_week[7]代表当日处在该周的第几天week[7]=0周日，week[7]=1周一,week[7]=2周二,week[7]=6周六
    //week[0]周日当天的日期，week[1]周一当天的日期,week[6]周六当天的日期
    public static int[] GetWeeks(long show_time) {

        int[] times_week = new int[8];

        for (int i = 0; i < 7; i++) {
            //是周几被点击了，点击后的是哪个(finalI=1周日，finalI=2周一)
            final int finalI = i + 1;

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(show_time));
            //calendar.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
            int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;//获得当前日期是一个星期的第几天

            times_week[7] = day;//day=0周日，day=1周一,day=2周二,day=6周六

            //点击前是周几，day=1周日，day=2周一
            day = day + 1;

            long a = 0;
            a = 24 * 60 * 60 * 1000 * (finalI - day);
            long time = 0;
            time = show_time + a;

            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(new Date(time));
            times_week[i] = calendar1.get(Calendar.DAY_OF_MONTH);
        }


        return times_week;
    }


    /**
     * 根绝传入日期 获取上一天日期
     *
     * @param date 传入日期
     * @return 当天日期
     */
    public static long Today_time(String date) {
        SimpleDateFormat df;
        if (date.length() > 10) {
            df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        } else {
            df = new SimpleDateFormat("yyyy/MM/dd");
        }

        long time = 0;

        try {
            Date date1 = df.parse(date);
            time = date1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 根绝传入日期 获取上一天日期
     *
     * @param date 传入日期
     * @return 当天日期
     */
    public static long Today_time2(String date) {
        SimpleDateFormat df;
        if (date.length() > 10) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            df = new SimpleDateFormat("yyyy-MM-dd");
        }

        long time = 0;

        try {
            Date date1 = df.parse(date);
            time = date1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /* //日期转换为时间戳 */
    public static long timeToStamp(String timers) {
        Date d = new Date();
        long timeStemp = 0;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            d = sf.parse(timers);// 日期转换为时间戳
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        timeStemp = d.getTime();
        return timeStemp;
    }


    /**
     * 计算前5分钟 并以05结尾的时间
     *  
     *
     * @param
     * @param
     * @return
     */
    public static String getBeforeByHourTime() {


        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -15);// 15分钟之前的时间
        Date beforeD = beforeTime.getTime();
        String before5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:00").format(beforeD);
        Log.e("info", "时间1=" + before5);

        int su = Integer.parseInt(before5.substring(14, 16));
        if (su % 5 < 5) {  //43  41
            su = su - su % 5;
        } else {
            //47
            su = su - su % 5;
        }


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(before5);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null) {
            return null;
        }
        date.setMinutes(su);

        before5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        Log.e("info", "时间=" + before5);

        return before5;

    }

    /**
     * 求当前系统的前后时间
     * eg：前两小时 Date timeBeforeOrAfter = getTimeBeforeOrAfter(Calendar.HOUR_OF_DAY, -2)
     * new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timeBeforeOrAfter)
     */
    public static Date getTimeBeforeOrAfter(int calendarType, int time) {
        long l = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l);
        calendar.add(calendarType, time);
        return calendar.getTime();
    }

}
