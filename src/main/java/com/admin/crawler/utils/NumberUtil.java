package com.admin.crawler.utils;


import com.sun.deploy.util.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NumberUtil {


    /**
     * 去除List中没用的东西
     *
     * @return
     */
    public static void removeListUseless(List<String> list) {

        for (int i = 0; i < list.size(); i++) {
            String tmp = list.get(i);
            tmp = tmp.replaceAll("元", "");
            tmp = tmp.replaceAll(",", "");
            tmp = tmp.replaceAll(" ", "");
            tmp = tmp.replaceAll("\\u00A0", "");
            tmp = tmp.replaceAll("\\s*", "");
            tmp = tmp.replaceAll("￥", "");
            tmp = tmp.replaceAll("%", "");
            tmp = tmp.replace((char) 12288, (char) 0);
            tmp = tmp.trim();
            list.set(i, tmp);
        }
    }

    /**
     * 将string转化为double并四舍五入保留newScale位数字
     *
     * @param input
     * @param newScale
     * @return
     */
    public static double string2double(String input, int newScale) {
        double temp = 0.00;
        try {
            temp = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return temp;
        }
        BigDecimal bg = BigDecimal.valueOf(temp);
        return bg.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Integer yuan2cent(String o_yuan) {
        if (StringUtil.isBlank(o_yuan)) {
            return null;
        }
        String currency = o_yuan.replaceAll("元|\\$|\\￥|\\,", "");
        BigDecimal decimal = new BigDecimal(currency);
        return decimal.multiply(new BigDecimal(100)).intValue();
    }

    /**
     * 时间转换成秒
     * time XX小时XX分XX秒  XX时XX分XX秒  XX分XX秒 ……
     *
     * @return
     * @author lc
     */
    public static Integer timeToSecond(String input) {
        String time = input.trim();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(time);
        List<Integer> s = new ArrayList<>();
        while (matcher.find()) {
            s.add(Integer.parseInt(matcher.group(0)));
        }

        int timeToSecond = 0;
        if (time.contains("时") && time.contains("分") && time.contains("秒")) {
            timeToSecond = s.get(0) * 60 * 60 + s.get(1) * 60 +
                    s.get(2);
        } else if (time.contains("分") && time.contains("秒") && !time.contains("时")) {
            timeToSecond = s.get(0) * 60 + s.get(1);
        } else if (time.contains("分") && time.contains("时") && !time.contains("秒")) {
            timeToSecond = s.get(0) * 60 * 60 + s.get(1) * 60;
        } else if (time.contains("秒") && time.contains("时") && !time.contains("分")) {
            timeToSecond = s.get(0) * 60 * 60 + s.get(1);
        } else if (time.contains("秒") && !time.contains("时") && !time.contains("分")) {
            timeToSecond = s.get(0);
        } else if (time.contains("分") && !time.contains("时") && !time.contains("秒")) {
            timeToSecond = s.get(0) * 60;
        } else if (time.contains("时") && !time.contains("秒") && !time.contains("分")) {
            timeToSecond = s.get(0) * 60 * 60;
        } else if (s.size() == 3) {
            timeToSecond = s.get(0) * 3600 + s.get(1) * 60 + s.get(2);
        } else {
            return 0;
        }

        return timeToSecond;
    }

    /**
     * 时间转换成秒
     * date 2016年8月1日至2016年8月31日
     *
     * @return
     * @author lc
     */
    public static List<String> dateFormat(String date) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(date.trim());
        List<Integer> s = new ArrayList<>();
        while (matcher.find()) {
            s.add(Integer.parseInt(matcher.group(0)));
        }
        List<String> result = new ArrayList<>();
        result.add(s.get(0) + "-" + s.get(1) + "-" + s.get(2));
        result.add(s.get(3) + "-" + s.get(4) + "-" + s.get(5));

        return result;
    }

    public static int mega2kilobytes(String mega) {
        if ("0".equals(mega) || "0.0".equals(mega) || "0.00".equals(mega)) {
            return 0;
        }
        if (mega.contains(".")) {
            Double kilobytes = Double.parseDouble(mega);
            return (int) (kilobytes * 1024);
        } else {
            return Integer.parseInt(mega) * 1024;
        }
    }

    public static boolean isIntegerNumbers(String... nums) {
        for (String num : nums) {
            if (!num.matches("[0-9]+")) {
                return false;
            }
        }
        return true;
    }


    /**
     * 流量转换成KB
     * flow 123MB123KB 123M  123K  123M123K
     *
     * @return
     * @author lc
     */
    public static int flowToKB(String flow) {
        flow = flow.trim();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(flow);
        List<Integer> s = new ArrayList<Integer>();
        while (matcher.find()) {
            s.add(Integer.parseInt(matcher.group(0)));
        }

        int flowToKB = 0;

        if (flow.contains("m") && flow.contains("k")) {
            flowToKB = s.get(0) * 1024 + s.get(1);
        } else if (flow.contains("m")) {
            flowToKB = s.get(0) * 1024;
        } else if (flow.contains("k")) {
            flowToKB = s.get(0);
        } else if (flow.contains("M") && flow.contains("K")) {
            flowToKB = s.get(0) * 1024 + s.get(1);
        } else if (flow.contains("M")) {
            flowToKB = s.get(0) * 1024;
        } else if (flow.contains("K")) {
            flowToKB = s.get(0);
        } else {
            //默认按照MB计算
            flowToKB = s.get(0) * 1024;
        }

        return flowToKB;
    }


    /**
     * 将时间转换成秒（**时/小时**分/分钟**秒 ——> **）
     *
     * @param string
     * @return
     */
    public static List<Integer> getNumberFromString(String string) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(string);
        List<Integer> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(Integer.parseInt(matcher.group(0)));
        }
        return result;
    }

    public static int time2Second(String strTime) {
        if (strTime.matches("\\d{0,2}:*\\d{1,2}:*\\d{1,2}")) {
            String[] strsplit = strTime.split(":");
            if (strsplit.length == 2) {
                return Integer.valueOf(strsplit[0]) * 60 + Integer.valueOf(strsplit[1]);
            } else if (strsplit.length == 3) {
                return Integer.valueOf(strsplit[0]) * 3600
                        + Integer.valueOf(strsplit[1]) * 60 + Integer.valueOf(strsplit[2]);
            }
        }
        Pattern p = Pattern.compile("((\\d{1,})(?:小时|时))*((\\d{1,})(?:分钟|分))*((\\d{1,})(?:秒))*");
        int second = 0;
        Matcher m = p.matcher(strTime);
        if (m.find()) {
            String lh = m.group(2);
            String lm = m.group(4);
            String ls = m.group(6);
            if (!StringUtil.isBlank(lh)) {
                second += Long.parseLong(lh) * 3600;
            }
            if (!StringUtil.isBlank(lm)) {
                second += Long.parseLong(lm) * 60;
            }
            if (!StringUtil.isBlank(ls)) {
                second += Long.parseLong(ls);
            }
        }
        return second;
    }

    public static BigDecimal yuanToDecimal(String value1) {
        if (value1.startsWith(".")) {
            value1 = "0" + value1;
        }
        String value = value1.replace(",", "").replace("元", "");
        return new BigDecimal(value);
    }

    public static BigDecimal percent2BigDecimal(String percent) {
        String value = percent.replace("%", "");
        return new BigDecimal(value).divide(new BigDecimal(100));
    }


    private static final Logger logger = LoggerFactory.getLogger(NumberUtil.class);

    /**
     * 无默认返回，返回null
     *
     * @param source
     * @return
     */
    public static Long strToLong(String source, Long defValue) {
        if (StringUtil.isBlank(source)) return defValue;
        if (!StringUtil.isNumeric(source)) return defValue;
        return Long.parseLong(source);
    }

    /**
     * 无默认返回
     * <p>
     * 若obj为null，则返回null
     *
     * @param source
     * @return
     */
    public static Long strToLong(String source) {
        if (StringUtil.isBlank(source)) return null;
        if (!StringUtil.isNumeric(source)) return null;
        return Long.parseLong(source);
    }

    public static Long objToLongDefault(Object obj, long defaultValue) {
        if (null == obj) return defaultValue;
        try {
            return Long.parseLong(obj.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Long objToLongDefault(Object obj, Long defaultValue) {
        if (null == obj) return defaultValue;
        try {
            return Long.parseLong(obj.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Boolean objToBooleanDefault(Object obj, Boolean defaultValue) {
        if (null == obj) return defaultValue;
        try {
            return Boolean.parseBoolean(obj.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 方法说明: 判断输入的数值是否为空或者0
     *
     * @param num
     * @return
     */
    public static boolean isNullOrZero(Integer num) {
        if (num == null || num == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 无默认返回
     * <p>
     * 若obj为null，则返回null
     *
     * @param obj
     * @return
     */
    public static Long objToLong(Object obj) {
        if (null == obj) return null;
        try {
            return Long.parseLong(obj.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public static int objToIntDefault(Object obj, int defaultValue) {
        if (null == obj) return defaultValue;
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Integer objToIntDefault(Object obj, Integer defaultValue) {
        if (null == obj) return defaultValue;
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Character objToCharacterDefault(Object obj, Character defaultValue) {
        if (null == obj) return defaultValue;
        try {
            char[] chars = obj.toString().toCharArray();
            return chars[0];
        } catch (Exception e) {
            return defaultValue;
        }
    }


    public static Byte objToByteDefault(Object obj, Byte defaultValue) {
        if (null == obj) return defaultValue;
        try {
            return Byte.parseByte(obj.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }


    public static Short objToShortDefault(Object obj, Short defaultValue) {
        if (null == obj) return defaultValue;
        try {
            return Short.parseShort(obj.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int objToPageIntDefault(Object obj, int defaultValue) {
        if (null == obj) return defaultValue;
        try {
            int pageNum = Integer.parseInt(obj.toString());
            return pageNum == 0 ? 1 : pageNum;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Long objToPageLongDefault(Object obj, Long defaultValue) {
        if (null == obj) return defaultValue;
        try {
            Long pageNum = Long.parseLong(obj.toString());
            return pageNum == 0L ? 1L : pageNum;
        } catch (Exception e) {
            return defaultValue;
        }
    }


    public static Float objToFloatDefault(Object obj, Float defaultValue) {
        if (null == obj) return defaultValue;
        try {
            return Float.parseFloat(obj.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static float objToFloatDefault(Object obj, float defaultValue) {
        if (null == obj) return defaultValue;
        try {
            return Float.parseFloat(obj.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static double objToDoubleDefault(Object obj, double defaultValue) {
        if (null == obj) return defaultValue;
        try {
            return Double.parseDouble(obj.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Double objToDoubleDefault(Object obj, Double defaultValue) {
        if (null == obj) return defaultValue;
        try {
            return Double.parseDouble(obj.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static BigDecimal objToBigDecimalDefault(Object obj, BigDecimal defaultValue) {
        if (null == obj) return defaultValue;
        try {
            return new BigDecimal(obj.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static BigDecimal objToBigDecimalZeroToDefault(Object obj, BigDecimal defaultValue) {
        if (null == obj) return defaultValue;
        try {
            BigDecimal objValue = new BigDecimal(obj.toString());
            if (objValue.compareTo(BigDecimal.ZERO) == 0) {
                return defaultValue;
            }
            return objValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 无默认返回
     * <p>
     * 若obj为null，则返回null
     *
     * @param obj
     * @return
     */
    public static Integer objToInteger(Object obj) {
        if (null == obj) return null;
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            logger.error("格式转换异常", e);
            return null;
        }
    }

    public static boolean isIntIllegal(String str) {
        boolean isIllegal = false;
        if (!isNumber(str)) {
            return true;
        }
        int sensor = Integer.valueOf(str);
        if (sensor <= 0) {
            isIllegal = true;
        }
        return isIllegal;
    }

    public static boolean isNumber(final String str) {
        return isCreatable(str);
    }

    public static final String JAVA_SPECIFICATION_VERSION = getSystemProperty("java.specification.version");

    private static String getSystemProperty(final String property) {
        try {
            return System.getProperty(property);
        } catch (final SecurityException ex) {
            // we are not allowed to look at this property
            System.err.println("Caught a SecurityException reading the system property '" + property
                    + "'; the SystemUtils property value will default to null.");
            return null;
        }
    }
    public static final boolean IS_JAVA_1_6 = getJavaVersionMatches("1.6");

    private static boolean getJavaVersionMatches(final String versionPrefix) {
        return isJavaVersionMatch(JAVA_SPECIFICATION_VERSION, versionPrefix);
    }

    static boolean isJavaVersionMatch(final String version, final String versionPrefix) {
        if (version == null) {
            return false;
        }
        return version.startsWith(versionPrefix);
    }


    public static boolean isCreatable(final String str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        final char[] chars = str.toCharArray();
        int sz = chars.length;
        boolean hasExp = false;
        boolean hasDecPoint = false;
        boolean allowSigns = false;
        boolean foundDigit = false;
        // deal with any possible sign up front
        final int start = chars[0] == '-' || chars[0] == '+' ? 1 : 0;
        final boolean hasLeadingPlusSign = start == 1 && chars[0] == '+';
        if (sz > start + 1 && chars[start] == '0') { // leading 0
            if (chars[start + 1] == 'x' || chars[start + 1] == 'X') { // leading 0x/0X
                int i = start + 2;
                if (i == sz) {
                    return false; // str == "0x"
                }
                // checking hex (it can't be anything else)
                for (; i < chars.length; i++) {
                    if ((chars[i] < '0' || chars[i] > '9')
                            && (chars[i] < 'a' || chars[i] > 'f')
                            && (chars[i] < 'A' || chars[i] > 'F')) {
                        return false;
                    }
                }
                return true;
            } else if (Character.isDigit(chars[start + 1])) {
                // leading 0, but not hex, must be octal
                int i = start + 1;
                for (; i < chars.length; i++) {
                    if (chars[i] < '0' || chars[i] > '7') {
                        return false;
                    }
                }
                return true;
            }
        }
        sz--; // don't want to loop to the last char, check it afterwords
        // for type qualifiers
        int i = start;
        // loop to the next to last char or to the last char if we need another digit to
        // make a valid number (e.g. chars[0..5] = "1234E")
        while (i < sz || i < sz + 1 && allowSigns && !foundDigit) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                foundDigit = true;
                allowSigns = false;

            } else if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent
                    return false;
                }
                hasDecPoint = true;
            } else if (chars[i] == 'e' || chars[i] == 'E') {
                // we've already taken care of hex.
                if (hasExp) {
                    // two E's
                    return false;
                }
                if (!foundDigit) {
                    return false;
                }
                hasExp = true;
                allowSigns = true;
            } else if (chars[i] == '+' || chars[i] == '-') {
                if (!allowSigns) {
                    return false;
                }
                allowSigns = false;
                foundDigit = false; // we need a digit after the E
            } else {
                return false;
            }
            i++;
        }
        if (i < chars.length) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                if (IS_JAVA_1_6 && hasLeadingPlusSign && !hasDecPoint) {
                    return false;
                }
                // no type qualifier, OK
                return true;
            }
            if (chars[i] == 'e' || chars[i] == 'E') {
                // can't have an E at the last byte
                return false;
            }
            if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent
                    return false;
                }
                // single trailing decimal point after non-exponent is ok
                return foundDigit;
            }
            if (!allowSigns
                    && (chars[i] == 'd'
                    || chars[i] == 'D'
                    || chars[i] == 'f'
                    || chars[i] == 'F')) {
                return foundDigit;
            }
            if (chars[i] == 'l'
                    || chars[i] == 'L') {
                // not allowing L with an exponent or decimal point
                return foundDigit && !hasExp && !hasDecPoint;
            }
            // last character is illegal
            return false;
        }
        // allowSigns is true iff the val ends in 'E'
        // found digit it to make sure weird stuff like '.' and '1E-' doesn't pass
        return !allowSigns && foundDigit;
    }

    /**
     * 从string转为Integer，并规定了范围
     *
     * @param str       需要转换的数字
     * @param minNumber 最小范围
     * @param maxNumber 最大范围
     * @return 成功返回Ineger，失败返回null
     */
    public static Integer str2Integer(String str, int minNumber, int maxNumber) {
        Integer lType = 0;
        if (StringUtil.isBlank(str) || !isNumber(str)) {// 校验是否是数字
            return null;
        } else {
            lType = Integer.parseInt(str);
            if (lType < minNumber || lType > maxNumber) {
                return null;
            }
        }
        return lType;
    }

    /**
     * 保留两位小数
     *
     * @param d
     * @return
     */
    public static String format2Str(BigDecimal d) {
        if (d == null) {
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        String ds = df.format(d);
        return ds;
    }

    /**
     * 比较数字是否在指定两个数字范围呢
     *
     * @param compareNum 需要转换的数字
     * @param minNumber  最小范围
     * @param maxNumber  最大范围
     * @return 成功返回Ineger，失败返回null
     */
    public static boolean between2Number(Number compareNum, Number minNumber, Number maxNumber) {
        if (compareNum.longValue() > maxNumber.longValue() || compareNum.longValue() < minNumber.longValue()) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Number> T parseNumber(String text, Class<T> targetClass) {
        if (StringUtil.isBlank(text) || null == targetClass) {
            logger.info("text string or target class must not be null or empty");
            return null;
        }
        String trimmed = trimAllWhitespace(text);

        if (targetClass.equals(Byte.class)) {
            return (T) (isHexNumber(trimmed) ? Byte.decode(trimmed) : Byte.valueOf(trimmed));
        } else if (targetClass.equals(Short.class)) {
            return (T) (isHexNumber(trimmed) ? Short.decode(trimmed) : Short.valueOf(trimmed));
        } else if (targetClass.equals(Integer.class)) {
            return (T) (isHexNumber(trimmed) ? Integer.decode(trimmed) : Integer.valueOf(trimmed));
        } else if (targetClass.equals(Long.class)) {
            return (T) (isHexNumber(trimmed) ? Long.decode(trimmed) : Long.valueOf(trimmed));
        } else if (targetClass.equals(BigInteger.class)) {
            return (T) (isHexNumber(trimmed) ? decodeBigInteger(trimmed) : new BigInteger(trimmed));
        } else if (targetClass.equals(Float.class)) {
            return (T) Float.valueOf(trimmed);
        } else if (targetClass.equals(Double.class)) {
            return (T) Double.valueOf(trimmed);
        } else if (targetClass.equals(BigDecimal.class) || targetClass.equals(Number.class)) {
            return (T) new BigDecimal(trimmed);
        } else {
            throw new IllegalArgumentException("Cannot convert String [" + text + "] to target class ["
                    + targetClass.getName() + "]");
        }
    }

    /**
     * Determine whether the given value String indicates a hex number, i.e. needs to be passed into
     * {@code Integer.decode} instead of {@code Integer.valueOf} (etc).
     */
    private static boolean isHexNumber(String value) {
        int index = (value.startsWith("-") ? 1 : 0);
        return (value.startsWith("0x", index) || value.startsWith("0X", index) || value.startsWith("#", index));
    }

    /**
     * Decode a {@link BigInteger} from a {@link String} value. Supports decimal, hex and octal notation.
     *
     * @see BigInteger#BigInteger(String, int)
     */
    private static BigInteger decodeBigInteger(String value) {
        int radix = 10;
        int index = 0;
        boolean negative = false;

        // Handle minus sign, if present.
        if (value.startsWith("-")) {
            negative = true;
            index++;
        }

        // Handle radix specifier, if present.
        if (value.startsWith("0x", index) || value.startsWith("0X", index)) {
            index += 2;
            radix = 16;
        } else if (value.startsWith("#", index)) {
            index++;
            radix = 16;
        } else if (value.startsWith("0", index) && value.length() > 1 + index) {
            index++;
            radix = 8;
        }

        BigInteger result = new BigInteger(value.substring(index), radix);
        return (negative ? result.negate() : result);
    }

    private static String trimAllWhitespace(String str) {
        if (!(str != null && str.length() > 0)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        int index = 0;
        while (sb.length() > index) {
            if (Character.isWhitespace(sb.charAt(index))) {
                sb.deleteCharAt(index);
            } else {
                index++;
            }
        }
        return sb.toString();
    }

    public static boolean isValidRangeForInteger(Integer obj, int min, int max) {
        return (obj != null && obj >= min && obj <= max);
    }

    public static boolean isNotValidRangeForInteger(Integer obj, int min, int max) {
        return (obj == null || obj < min || obj > max);
    }

    public static boolean isValidForLong(Long obj) {
        return (obj != null && obj >= 0);
    }

    public static boolean isNotValidForLong(Long obj) {
        return (obj == null || obj < 0);
    }

    public static boolean isValidForInteger(Integer obj) {
        return (obj != null && obj >= 0);
    }

    public static boolean isNotValidForInteger(Integer obj) {
        return (obj == null || obj < 0);
    }


    public static BigDecimal objToBigDecimalDivideOnehundredDefault(Object obj, BigDecimal defaultValue) {
        if (null == obj) return defaultValue;
        try {
            return new BigDecimal(obj.toString()).divide(BigDecimal.valueOf(100.00), 2, BigDecimal.ROUND_DOWN);
        } catch (Exception e) {
            return defaultValue;
        }
    }


    public static double strToDoubleWithDefault(String str, double def) {
        if (StringUtil.isBlank(str)) {
            return def;
        }
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return def;
        }
    }


    public static BigDecimal strToBigDecimalWithDefault(String str, double def) {
        if (StringUtil.isBlank(str)) {
            return new BigDecimal(0);
        }
        try {
            return new BigDecimal(str);
        } catch (Exception e) {
        }
        return new BigDecimal(def);
    }


    public static BigDecimal strToBigDecimalWithDefault(String str, BigDecimal def) {
        if (StringUtil.isBlank(str)) {
            return new BigDecimal(0);
        }
        try {
            return new BigDecimal(str);
        } catch (Exception e) {
        }
        return def;
    }

    public static Integer strToIntDefault(String value, int i) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {

        }
        return i;
    }

    public static boolean equals(Integer a, Integer b) {
        if (a == null && b != null) {
            return false;
        }
        if (a != null && b == null) {
            return false;
        }
        if (a == null && b == null) {
            return true;
        }
        if (a != null && b != null) {
            if (a.intValue() == b.intValue()) {
                return true;
            }
        }
        return false;
    }


    public static boolean equals(Long a, Long b) {
        if (a == null && b != null) {
            return false;
        }
        if (a != null && b == null) {
            return false;
        }
        if (a == null && b == null) {
            return true;
        }
        if (a != null && b != null) {
            if (a.intValue() == b.intValue()) {
                return true;
            }
        }
        return false;
    }

    public static boolean nq(Integer a, Integer b) {
        if (a == null && b != null) {
            return true;
        }
        if (a != null && b == null) {
            return true;
        }
        if (a == null && b == null) {
            return false;
        }

        if (a != null && b != null) {
            if (a.intValue() != b.intValue()) {
                return true;
            }
        }

        return false;
    }


    public static boolean equals(BigDecimal a, BigDecimal b) {
        if (a == null && b != null) {
            return false;
        }
        if (a != null && b == null) {
            return false;
        }
        if (a == null && b == null) {
            return true;
        }
        if (a != null && b != null) {
            if (a.compareTo(b) == 0) {
                return true;
            }
        }
        return false;
    }


    public static boolean ge(Integer a, Integer b) {
        if (a == null && b != null) {
            return false;
        }
        if (a != null && b == null) {
            return false;
        }
        if (a == null && b == null) {
            return true;
        }
        if (a != null && b != null) {
            if (a.intValue() >= b.intValue()) {
                return true;
            }
        }
        return false;
    }


    /**
     * 基本类型包装类解析
     */
    public static <T> T parseBasicType(Class<T> parameterType, Object value, Object... defaultValues) {
        Object defaultValue = null;
        if (defaultValues != null && defaultValues.length > 0) {
            defaultValue = defaultValues[0];
        }
        try {
            if (Number.class.isAssignableFrom(parameterType)) {
                if (parameterType == Integer.class) {
                    return (T) NumberUtil.objToIntDefault(value, NumberUtil.objToIntDefault(defaultValue, null));
                } else if (parameterType == Short.class) {
                    return (T) NumberUtil.objToShortDefault(value, NumberUtil.objToShortDefault(defaultValue, null));
                } else if (parameterType == Long.class) {
                    return (T) NumberUtil.objToLongDefault(value, NumberUtil.objToLongDefault(defaultValue, null));
                } else if (parameterType == Float.class) {
                    return (T) NumberUtil.objToFloatDefault(value, NumberUtil.objToFloatDefault(defaultValue, null));
                } else if (parameterType == Double.class) {
                    return (T) NumberUtil.objToDoubleDefault(value, NumberUtil.objToDoubleDefault(defaultValue, null));
                } else if (parameterType == Byte.class) {
                    return (T) NumberUtil.objToByteDefault(value, NumberUtil.objToByteDefault(defaultValue, null));
                }
            } else if (parameterType == Boolean.class) {
                return (T) NumberUtil.objToBooleanDefault(value, NumberUtil.objToBooleanDefault(defaultValue, Boolean.FALSE));
            } else if (parameterType == Character.class) {
                return (T) NumberUtil.objToCharacterDefault(value, NumberUtil.objToCharacterDefault(defaultValue, null));
            } else if (parameterType == String.class) {
                return (T) (value + "");
            } else if (parameterType == BigDecimal.class) {
                return (T) NumberUtil.objToBigDecimalDefault(value, NumberUtil.objToBigDecimalDefault(defaultValue, BigDecimal.ZERO));
            }
        } catch (Exception e) {
            logger.error("parseBasicType exception parameterType=" + parameterType.getClass().getSimpleName() + ",value=" + value, e);
        }
        return null;
    }

    public static String objToStringDefault(Object object, String s) {
        if (object == null) {
            return s;
        }
        return object.toString();
    }


    public static BigDecimal setScale(BigDecimal yuan, int i) {
        if (yuan == null) {
            return BigDecimal.ZERO;
        }
        return yuan.setScale(i, BigDecimal.ROUND_DOWN);
    }

    public static boolean compareTo100(BigDecimal yuan) {
        if (yuan != null && yuan.compareTo(new BigDecimal(100)) >= 0) {
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
        System.out.println(compareTo100(new BigDecimal(100)));

    }


}
