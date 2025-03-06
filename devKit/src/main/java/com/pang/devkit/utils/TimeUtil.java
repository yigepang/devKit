package com.pang.devkit.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * <p>
 * Description：时间转化工具类
 * </p>
 *
 * @author hoozy
 */
public class TimeUtil {

    /**
     * 时间戳转化为yyyy-MM-dd的格式
     *
     * @param stamp 时间戳（如1457059118）
     * @return
     */
    public static String stampToYyyyMMdd(long stamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String result = sdf.format(new Date(stamp * 1000L));
        return result;
    }

    /**
     * 时间戳转化为yyyy-MM-dd的格式
     *
     * @param stamp 时间戳（如1457059118）
     * @return
     */
    public static String stampToYyyyMMddHHmmSS(long stamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        String result = sdf.format(new Date(stamp * 1000L));
        return result;
    }

    /**
     * 时间戳转化为yyyy-MM-dd的格式(多了000的，如1457059118000)
     *
     * @param stamp 时间戳
     * @return
     */
    public static String longToYYMMDD(long stamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String result = sdf.format(new Date(stamp));
        return result;
    }

    /**
     * 时间戳转化为yyyy-MM-dd的格式(多了000的，如1457059118000)
     *
     * @param stamp 时间戳
     * @return
     */
    public static String stampToYyyyMM(long stamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String result = sdf.format(new Date(stamp));
        return result;
    }

    /**
     * 时间戳转化为yyyy-MM-dd HH:mm:ss的格式 000
     *
     * @param stamp 时间戳
     * @return
     */
    public static String stampToYyyyMMddHHmmss(long stamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = sdf.format(new Date(stamp));
        return result;
    }

    /**
     * yyyy-MM-dd格式时间转化为时间戳
     *
     * @param date
     * @return
     */
    public static Long yyyyMMddToStamp(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long dateStamp = 0;
        try {
            Date mDate = sdf.parse(date);
            dateStamp = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStamp;
    }

    /**
     * yyyy-M-dd格式时间转化为时间戳
     *
     * @param date
     * @return 时间戳多了000的，如1457059118000  13位
     */
    public static Long yyyyMMddToStamp2(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        long dateStamp = 0;
        try {
            Date mDate = sdf.parse(date);
            dateStamp = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStamp;
    }

    /**
     * yyyy-MM-dd HH:mm格式时间转化为时间戳
     *
     * @param date
     * @return
     */
    public static Long yyyyMMddHHmmToStamp(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long dateStamp = 0;
        try {
            Date mDate = sdf.parse(date);
            dateStamp = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStamp;
    }

    /**
     * yyyy-MM-dd HH:mm:ss格式时间转化为时间戳
     *
     * @param date
     * @return
     */
    public static Long yyyyMMddHHmmssToStamp(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        long dateStamp = 0;
        try {
            Date mDate = sdf.parse(date);
            dateStamp = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStamp;
    }

    /**
     * 将时间yyyy-MM-dd HH:mm:ss格式转换为yyyy.MM
     */
    public static String yyyyMMddHHmmssToyyyyMM(String date) {
        if (!StringUtils.isNullStr(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date parse = sdf.parse(date);
                long stamp = parse.getTime();
                SimpleDateFormat sdfResult = new SimpleDateFormat("yyyy.MM");
                String result = sdfResult.format(new Date(stamp));
                return result;
            } catch (ParseException e) {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }


    /**
     * 获得系统当前日期yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrentDateYyyyMMdd() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }

    /**
     * 获得系统当前日期yyyy-MM
     *
     * @return
     */
    public static String getCurrentDateYyyyMM() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }

    /**
     * 获得系统当前时间yyyyMMddHHmmss
     *
     * @return
     */
    public static String getCurrentTimeYyyyMMddHHmmss() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }

    /**
     * 获得系统当前时间HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTimeHHmmss() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }

    /**
     * 获得系统当前时间HH:mm
     *
     * @return
     */
    public static String getCurrentTimeHHmm() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }

    /**
     * 获得系统当前时间HH:mm
     *
     * @return
     */
    public static String getCurrentTimeHH() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }

    /**
     * 获得当前时间戳
     * 13位
     */
    public static long getCurrentTimeStamp() {
        return (new Date()).getTime();
    }

//    /**
//     * 获取当前时间戳
//     * 10位
//     */
//    public static long getTenCurrentTimeStamp() {
//        return
//    }

    /**
     * 获取当前日期是星期几
     */
    public static String getWeekOfDate() {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // 获得星期几（注意（这个与Date类是不同的）：1代表星期日、2代表星期1、3代表星期二，以此类推）
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 把毫秒转换成：1:20:30这里形式
     *
     * @param timeMs
     * @return
     */
    public static String stringForTime(int timeMs) {
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    /**
     * 获取前beforeNum年
     *
     * @return
     */
    public static int getBeforeYear(int beforeNum) {
        Calendar date = Calendar.getInstance();
        int year = Integer.valueOf(date.get(Calendar.YEAR)) - beforeNum;
        return year;
    }

    /**
     * 获取前beforeNum年
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static int getBeforeYear2(int beforeNum) {
        LocalDate localDate = LocalDate.now();
        LocalDate beforeYeaDater = localDate.minusYears(beforeNum);
        int year = beforeYeaDater.getYear();
        return year;
    }

    /**
     * 获取前beforeNum年
     */
    public static String getBeforeYear3(String pattern, int num) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, num);
        Date y = c.getTime();
        return format.format(y);
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static int getSysYear() {
        Calendar date = Calendar.getInstance();
        int year = Integer.valueOf(date.get(Calendar.YEAR));
        return year;
    }

    public static String isTodayOrYesterday(String date) {
        long days = getDiff(date) / (60 * 60 * 24 * 1000);
        if (days == 0) {
            return "今天" + date.substring(10, 16);
        } else if (days == 1) {
            return "昨天" + date.substring(10, 16);
        } else {
            return date.substring(0, 16);
        }
    }

    public static String isTodayOrYesterday2(String date) {
        long days = getDiff(date) / (60 * 60 * 24 * 1000);
        if (days == 0) {
            return "今天";
        } else if (days == 1) {
            return "昨天";
        } else {
            return date.substring(0, 10);
        }
    }

    public static long getDiff(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        long zeroStamp = zero.getTime();
        return Math.abs(zeroStamp - yyyyMMddToStamp(date));
    }

    /**
     * 获取当前日期的前后日期
     * dateFormat:返回日期格式  days：-1 前一天   1后一天
     */
    public static String getBeforeOrNextDay(String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }

    /**
     * 获取当前日期的前后一月
     */
    public static String getBeforeOrNextMonth(String dateFormat, int month) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.MONTH, month);
        return s.format(new Date(cal.getTimeInMillis()));
    }

    /**
     * 获取指定日期的前后日期
     * hoozy
     * dateFormat:返回日期格式  days：-1 前一天   1后一天
     */
    public static String getBeforeOrNextDate(String dateFormatString, String currentDate, int day) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString);
        Date parseDate = dateFormat.parse(currentDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDate);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param
     * @return
     */
    public static String chageFormat(String dateStr, String oldF, String newf) {
        String newDateStr = "";
        SimpleDateFormat odf = new SimpleDateFormat(oldF);
        SimpleDateFormat nwf = new SimpleDateFormat(newf);
        try {
            Date date = odf.parse(dateStr);
            newDateStr = nwf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDateStr;
    }

    /**
     * 获取上个月的第一天
     */
    public static String getLastMonthFirstDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastMonthFirstDateCal = Calendar.getInstance();
        lastMonthFirstDateCal.add(Calendar.MONTH, -1);
        lastMonthFirstDateCal.set(Calendar.DAY_OF_MONTH, 1);
        String lastMonthFirstTime = format.format(lastMonthFirstDateCal.getTime());
        return lastMonthFirstTime;
    }

    /**
     * 获取任意时间的下一个月
     */
    public static String getAfterMonth(String dataValue, String dateFormat) {
        String lastMonth = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat(dateFormat);
        int year = Integer.parseInt(dataValue.substring(0, 4));
        String monthsString = dataValue.substring(5, 7);//根据传入的时间格式调整
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2));
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2));
        }
        cal.set(year, month, Calendar.DATE);
        lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }

    /**
     * 获取任意时间的上一个月
     */
    public static String getBeforeMonth(String dataValue, String dateFormat) {
        String lastMonth = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat(dateFormat);
        int year = Integer.parseInt(dataValue.substring(0, 4));
        String monthsString = dataValue.substring(5, 7);//根据传入的时间格式调整
        int month;
        if ("0".equals(monthsString.substring(0, 1))) {
            month = Integer.parseInt(monthsString.substring(1, 2));
        } else {
            month = Integer.parseInt(monthsString.substring(0, 2));
        }
        cal.set(year, month - 2, Calendar.DATE);
        lastMonth = dft.format(cal.getTime());
        return lastMonth;

    }

    //计算两个时间相差的秒数
    public static long getDifTimeSecond(String startTime, String endTime) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long eTime = df.parse(endTime).getTime();
        long sTime = df.parse(startTime).getTime();
        long diff = (eTime - sTime) / 1000;
        return diff;
    }

    /**
     * 某一个月第一天和最后一天
     *
     * @return
     */
    public static Map<String, String> getFirstday_Lastday_Month(String strs, String defaultFormat) {//defaultFormat:"yyyy-MM-dd"
        SimpleDateFormat df = new SimpleDateFormat(defaultFormat);
        Date date = null;
        Map<String, String> map = new HashMap<String, String>();
        try {
            date = df.parse(strs);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, -1);
            Date theDate = calendar.getTime();

            //上个月第一天
            GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
            gcLast.setTime(theDate);
            gcLast.set(Calendar.DAY_OF_MONTH, 1);
            String day_first = df.format(gcLast.getTime());
            StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
            day_first = str.toString();

            //上个月最后一天
            calendar.add(Calendar.MONTH, 1);    //加一个月
            calendar.set(Calendar.DATE, 1);        //设置为该月第一天
            calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
            String day_last = df.format(calendar.getTime());
            StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
            day_last = endStr.toString();

            map.put("first", day_first);
            map.put("last", day_last);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 某一个月的第一天
     */
    public static String getFirstDayMonth(String strs) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = df.parse(strs);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 0);
            Date theDate = calendar.getTime();

            GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
            gcLast.setTime(theDate);
            gcLast.set(Calendar.DAY_OF_MONTH, 1);
            String day_first = df.format(gcLast.getTime());
            StringBuffer str = new StringBuffer().append(day_first);
            day_first = str.toString();

            return day_first;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 某一个月的最后一天
     */
    public static String getLastDayMonth(String str) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = df.parse(str);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 0);

            calendar.add(Calendar.MONTH, 1);    //加一个月
            calendar.set(Calendar.DATE, 1);        //设置为该月第一天
            calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
            String day_last = df.format(calendar.getTime());
            StringBuffer endStr = new StringBuffer().append(day_last);
            day_last = endStr.toString();
            return day_last;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 当月第一天
     *
     * @return
     */
    private static String getFirstDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();

        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return str.toString();
    }

    /**
     * 当月最后一天
     *
     * @return
     */
    private static String getLastDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        String s = df.format(theDate);
        StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
        return str.toString();
    }
}
