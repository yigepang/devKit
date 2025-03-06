package com.pang.devkit.utils.simulutil;

/**
 * 由hoozy于2024/1/3 15:56进行创建
 * 描述：
 */
public class CheckResult {
    public static final int RESULT_MAYBE_EMULATOR = 0;//可能是模拟器
    public static final int RESULT_EMULATOR = 1;//模拟器
    public static final int RESULT_UNKNOWN = 2;//可能是真机

    public int result;
    public String value;

    public CheckResult(int result, String value) {
        this.result = result;
        this.value = value;
    }
}
