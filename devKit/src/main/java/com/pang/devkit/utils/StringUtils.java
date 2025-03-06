package com.pang.devkit.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 由hoozy于2023/11/10 14:19进行创建
 * 描述：
 */
public class StringUtils {
    /**
     * 生成32位编码
     *
     * @return string
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

    public static float StringToFloat(String str) {
        if (str != null && str.length() > 0 && !str.equals("")) {
            return Float.parseFloat(str);
        }
        return 0;
    }

    /**
     * 为空时返回0“”
     *
     * @returni
     */
    public static String isNullCharaterStrReturnZero(String s) {
        if (s == null || s.length() == 0 || s.equals("null") || s.equals("NULL") || s.equals("NU") || s.equals("nu") || s.equals("Nu") || s.equals("nul") || s.equals("Nul") || s.equals("NUL")) {
            return "0";
        }
        return s;
    }

    /**
     * 为空时返回“”
     *
     * @return
     */
    public static String IsNullCharaterStrReturnWu(String s) {
        if (s == null || s.length() == 0 || s.equals("null") || s.equals("NULL") || s.equals("NU") || s.equals("nu") || s.equals("Nu") || s.equals("nul") || s.equals("Nul") || s.equals("NUL")) {
            return "无";
        }
        return s;
    }

    /**
     * 为空时返回“”
     *
     * @return
     */
    public static String IsNullCharaterStrReturnHeng(String s) {
        if (s == null || s.length() == 0 || s.equals("null") || s.equals("NULL") || s.equals("NU") || s.equals("nu") || s.equals("Nu") || s.equals("nul") || s.equals("Nul") || s.equals("NUL")) {
            return "-";
        }
        return s;
    }

    /**
     * 为空时返回“/”
     *
     * @return
     */
    public static String isNullReturnXieGang(String s) {
        if (s == null || s.length() == 0 || s.equals("null") || s.equals("NULL") || s.equals("NU") || s.equals("nu") || s.equals("Nu") || s.equals("nul") || s.equals("Nul") || s.equals("NUL")) {
            return "/";
        }
        return s;
    }

    /**
     * 为空时返回“”
     *
     * @return
     */
    public static String IsNull2CharaterStr(String s) {
        if (s == null || s.length() == 0 || s.equals("null") || s.equals("NULL") || s.equals("NU") || s.equals("nu") || s.equals("Nu") || s.equals("nul") || s.equals("Nul") || s.equals("NUL") || s.equals("请选择")) {
            return "";
        }
        if (s.contains("<BR/>")) {
            return s.replace("<BR/>", "\n");
        }

        if (s.contains("</br>")) {
            return s.replace("</br>", "\n");
        }
        if (isNumericS(s)) {
            return deleteNumZero(s);
        }
        return s;
    }

    /**
     * 为空时返回“”
     *
     * @return
     */
    public static String isNullStrReturnBlankSpace(String s) {
        if (s == null || s.length() == 0 || s.equals("null") || s.equals("NULL") || s.equals("NU") || s.equals("nu") || s.equals("Nu") || s.equals("nul") || s.equals("Nul") || s.equals("NUL") || s.equals("请选择")) {
            return " ";
        }
        return s;
    }

    /**
     * 判定是否为空
     *
     * @return
     */
    public static boolean isNullStr(String s) {
        if (s == null || s.length() == 0 || s.equals("null") || s.equals("NULL") || s.equals("NU") || s.equals("nu") || s.equals("Nu") || s.equals("nul") || s.equals("Nul") || s.equals("NUL")) {
            return true;
        }
        return false;
    }

    /**
     * 判定是否为空
     *
     * @return
     */
    public static boolean isNotNullStr(String s) {
        return !isNullStr(s);
    }

    /**
     * 截取日期的长度
     */
    public static String subStringData(String d) {
        if (null != d && d.length() > 10) {
            return d.substring(0, 10);
        } else {
            return d;
        }
    }


    /**
     * 判断传入的字符串是否为空
     *
     * @param s
     * @return true:空  ；false:不空
     */
    public static boolean IsNull(String s) {
        if (s == null) {
            return true;
        }
        return false;
    }

    /**
     * 为空时返回“”
     *
     * @return
     */
    public static String IsNullRECharaterStr(String s) {
        if (s == null || s.length() == 0 || s.equals("null")) {
            return "";
        }
        if (s.contains("<BR/>")) {
            return s.replace("<BR/>", "");
        }

        if (s.contains("<br/>")) {
            return s.replace("<br/>", "");
        }
        return s;
    }


    /**
     * 字符串转double
     *
     * @param str
     * @return
     */
    public static double StringToDouble(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        return Double.parseDouble(str);
    }

    /**
     * 生成唯一32位字符串
     *
     * @return
     */
    public static String CreateSpecialNumber() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 四舍五入 保留2位小数
     *
     * @param d
     * @return
     */
    public static String DoubleDecimalTwoFormat(double d) {
        String result = String.format("%.2f", d);

        return result;
    }

    /**
     * 四舍五入 保留2位小数
     *
     * @param d
     * @return
     */
    public static String DoubleDecimalTwoFormat(float d) {
        String result = String.format("%.2f", d);

        return result;
    }

    /**
     * 四舍五入 保留0位小数
     *
     * @param d
     * @return
     */
    public static String DoubleDecimalFormat(double d) {
        String result = String.format("%.1f", d);
        return new DecimalFormat("0").format(d);
    }

    /**
     * 保留整数
     */
    public static String toSaveNoDecimal(String s) {
        if (!TextUtils.isEmpty(s) && s.contains(".")) {
            String resultString = s.substring(0, s.indexOf("."));
            return resultString;
        }
        if (!TextUtils.isEmpty(s)) {
            return s;
        }
        return "0";
    }

    /**
     * 判断文本是否超长有省略，进行弹框处理
     */
    public static void showAllText(Context context, TextView tv) {
        if (null != tv && null != tv.getLayout()) {
            int lNum = tv.getLayout().getEllipsisCount(tv.getLineCount() - 1);//判定 >0 则有隐藏
            if (lNum > 0) {
                AlertDialog.Builder ad = new AlertDialog.Builder(context);
                ad.setItems(new String[]{"" + tv.getText().toString().trim()}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
            }
        }
    }

    /**
     * 邮箱验证
     *
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        if (isNullStr(str)) return false;
        return Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$").matcher(str).matches();
    }

    /**
     * 是否是身份证
     *
     * @param str
     * @return
     */
    public static boolean isIdentity(String str) {
        if (isNullStr(str)) return false;
        if (str.length() == 15)
            return Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$").matcher(str).matches();
        if (str.length() == 18)
            return Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$").matcher(str).matches();
        return false;
    }

    /**
     * 字符串中是否全为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 符串中是否全为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumericInt(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断字符串是否为整数或者小数
     *
     * @param str
     * @return
     */
    public static boolean isNum(String str) {
        Pattern pattern = Pattern.compile("[0-9]*\\.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否为整数或者小数
     *
     * @param str
     * @return
     */
    public static boolean isNumTwo(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        if (str.indexOf(".") > 0) {//判断是否有小数点
            if (str.indexOf(".") == str.lastIndexOf(".") && str.split("\\.").length == 2) { //判断是否只有一个小数点
                return pattern.matcher(str.replace(".", "")).matches();
            } else {
                return false;
            }
        } else {
            return pattern.matcher(str).matches();
        }
    }

    /**
     * 判断一个字符串是不是整数、浮点数、科学计数
     *
     * @param str
     * @return
     */
    public static boolean isNumericS(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        String regx = "[+-]*\\d+\\.?\\d*[Ee]*[+-]*\\d+";
        Pattern pattern = Pattern.compile(regx);
        boolean isNumber = pattern.matcher(str).matches();
        if (isNumber) {
            return isNumber;
        }
        regx = "^[-\\+]?[.\\d]*$";
        pattern = Pattern.compile(regx);
        return pattern.matcher(str).matches();
    }

    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */
    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * 验证输入的名字是否为全中文
     */
    public static boolean isAllChiness(String name) {
        if (isNullStr(name)) {
            return false;
        } else {
            return Pattern.compile("^[\\u4e00-\\u9fa5]*$").matcher(name).matches();
        }
    }

    /**
     * 验证输入的名字是否为“中文”或者是否包含“·”
     */
    public static boolean isLegalName(String name) {
        if (isNullStr(name)) {
            return false;
        } else {
            return Pattern.compile("^[\\u4e00-\\u9fa5]+(·[\\u4e00-\\u9fa5]+)*$").matcher(name).matches();
        }
    }

    public static String encodeHeadInfo(String headInfo) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0, length = headInfo.length(); i < length; i++) {
            char c = headInfo.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                stringBuffer.append(String.format("\\u%04x", (int) c));
            } else {
                stringBuffer.append(c);
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 去除小数点后的0及小数，保留整数及小数后，去掉末尾0
     */
    public static String deleteZero(String s) {
        return s.replaceAll("\\.0*$|(\\.\\d*?)0+$", "$1");
    }

    public static String deleteNumZero(String s) {
        if (s.equals("0.0")) {
            return "0";
        }
        BigDecimal bd = new BigDecimal(s);
        bd = bd.stripTrailingZeros();
        return bd.toPlainString();
    }

    /**
     * 判定字符串 是否
     */
    public static boolean isCanUsePassword(String password) {
        if (isNullStr(password)) {
            return false;
        } else {
            return Pattern.compile("^(?![0-9A-Za-z]+$)(?![0-9A-Z\\W]+$)(?![0-9a-z\\W]+$)(?![A-Za-z\\W]+$)[0-9A-Za-z~!@#$%^&*()_+`\\-={}|\\[\\]\\\\:\";'<>?,./]{12,}$").matcher(password).matches();
        }
    }

    /**
     * 用特定字符分开
     *
     * @param items
     * @param joinString
     * @return
     */
    public static String joinWithDash(List<String> items, String joinString) {
        if (items == null || items.isEmpty()) {
            return "";
        }
        if (items.size() == 1) {
            return items.get(0);
        }
        StringBuffer buffer = new StringBuffer();
        for (String item : items) {
            buffer.append(item);
            if (!item.equals(items.get(items.size() - 1))) {
                buffer.append(joinString);
            }
        }
        return buffer.toString();
    }
}
