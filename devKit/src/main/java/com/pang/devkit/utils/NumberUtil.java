package com.pang.devkit.utils;

import java.util.HashMap;

/**
 * 由hoozy于2023/4/28 09:13进行创建
 * 描述：
 */
public class NumberUtil {
    /**
     * @param currentValue 当前值
     * @param defaultValue 默认值
     * @param unitValue    默认单位值
     * @param defaultCount 默认单位数量
     * @param isLimitNum   单位数量限制
     * @return
     */
    public static HashMap<String, Integer> getBigMaxNum(double currentValue, double defaultValue, int unitValue, int defaultCount, boolean isLimitNum, int limitNum) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        int maxValue = (int) defaultValue;
        int count = defaultCount;
        if (!isLimitNum) {//对数量不进行限制
            if (currentValue > defaultValue) {

                int discuss = (int) (currentValue / unitValue);//求商
                int remain = (int) (currentValue % unitValue);//求余
                if (discuss >= (defaultCount - 1)) {//defaultCount包含了原点，需减1在与商进行比较
                    count = discuss;
                    //如果有余数,则增加一个单位的值unitValue
                    if (remain > 0) {
                        count = count + 1;
                    }
                    //最终最大值
                    maxValue = count * unitValue;
                    count = count + 1;
                }
            }
        } else {
            count = limitNum;
            maxValue = (int) currentValue;
        }
        hashMap.put("count", count);
        hashMap.put("maxValue", maxValue);
        return hashMap;
    }
}
