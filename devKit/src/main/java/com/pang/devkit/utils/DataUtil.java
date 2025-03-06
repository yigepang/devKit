package com.pang.devkit.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by hoozy on 2022/4/11
 * Describe:
 */
public class DataUtil {

    //求百分比
    public static String get(int up, int down) {
        if (down == 0) {
            throw new RuntimeException("Divisor cannot be zero");
        }
        double value = new BigDecimal((float) up / down).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return new DecimalFormat("#%").format(value);
    }

    //求百分比
    public static String getDoublePercent(double up, double down) {
        if (down == 0) {
            throw new RuntimeException("Divisor cannot be zero");
        }
        double f = up * 100 / down;
        BigDecimal bg = new BigDecimal(f);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1 + "%";
    }

    /**
     * 保留小数点后六位
     *
     * @param num
     * @return
     */
    public static double retain6(double num) {
        String result = String.format("%.6f", num);
        return Double.valueOf(result);
    }

    /**
     * 保留小数点后2位
     *
     * @param num
     * @return
     */
    public static double retain2(double num) {
        String result = String.format("%.2f", num);
        return Double.valueOf(result);
    }
}
