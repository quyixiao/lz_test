package com.admin.crawler.utils;

import com.lz.mybatis.plugin.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 陈金虎 2017年1月16日 下午11:43:50
 * @类描述：字符串工具类
 * @注意：本内容仅限于杭州霖梓网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */

@Slf4j
public class StringUtil {

    public static final Long INVITE_START_VALUE = 16796251L;

    private static final String COMMA = ",";
    private static final String[] DUOTRIKEY = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V"};
    private static final String[] CARDINALNUM = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    private static final String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 通过StringBuffer来组装字符串
     *
     * @param strings
     * @return
     */
    public static String appendStrs(Object... strings) {
        StringBuffer sb = new StringBuffer();
        for (Object str : strings) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 判断所有传入参数是否非空，当传入参数长度为0，或者任意有一个为空，则返回false，所有都非空，则返回true
     *
     * @param strArr
     * @return
     */
    public static boolean isAllNotEmpty(String... strArr) {
        boolean isAllNotEmpty = true;
        if (strArr == null || strArr.length < 1) {
            isAllNotEmpty = false;
            return isAllNotEmpty;
        }

        for (String str : strArr) {
            if (str == null || str.length() == 0) {
                isAllNotEmpty = false;
                break;
            }
        }
        return isAllNotEmpty;
    }
    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 把list按分隔符转换成字符串
     *
     * @param strList   list数据
     * @param separator 分隔符
     * @return
     */
    public static String turnListToStr(Collection<String> strList, String separator) {
        String result = "";
        if (strList == null || strList.size() < 1) {
            return result;
        }
        if (separator == null) {
            separator = ",";
        }

        for (String item : strList) {
            result = result + separator + item;
        }
        return result.substring(separator.length());
    }

    /**
     * 把字符串数组按分隔符转化成字符串
     *
     * @param strArr    字符串数组
     * @param separator 分隔符
     * @return
     */
    public static String turnArrayToStr(String separator, String... strArr) {
        String result = "";
        if (strArr == null || strArr.length < 1) {
            return result;
        }
        if (separator == null) {
            separator = ",";
        }

        for (String item : strArr) {
            result = result + separator + item;
        }
        return result.substring(separator.length());
    }

    public static String strToSecret(String str, int left, int right) {
        StringBuffer sb = new StringBuffer();
        int len = str.length() - left - right;
        if (len > 0) {
            sb.append(str.substring(0, left));
            for (int i = 0; i < len; i++) {
                sb.append("*");
            }
            sb.append(str.substring(str.length() - right));
        } else {
            return str;
        }
        return sb.toString();
    }


    public static String getLastString(String str, int num) {
        int len = str.length();
        if (len <= num) {
            return str;
        } else {
            return str.substring(len - num);
        }
    }

    public static List<String> splitToList(String source, String sep) {
        List<String> result = new ArrayList<String>();
        try {
            if (isBlank(source)) {
                return result;
            }
            String[] tempResult = source.split(sep);
            for (String item : tempResult) {
                result.add(item);
            }
        } catch (Exception e) {
            log.error("source = " + source + ", sep = " + sep, e);
        }
        return result;
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }



    public static List<Long> splitToLongList(String source, String sep) {
        List<Long> result = new ArrayList<Long>();
        try {
            if (isBlank(source)) {
                return result;
            }
            String[] tempResult = source.split(sep);
            for (String item : tempResult) {
                result.add(NumberUtil.objToLongDefault(item, 0l));
            }
        } catch (Exception e) {
            log.error("source = " + source + ", sep = " + sep, e);
        }
        return result;
    }


    public static void main(String[] args) {

        String a = "23054262562860836735,2405458646,2581014592,1781649584,3488204652,2475429671,3607845263,1725120010,2020396323,2477267853,2550996490,3390192979,3539855715,3615458572,";
        System.out.println(splitToList(a, ","));
    }

    /**
     * @param source 待处理字符串
     * @return
     * @方法描述：将字符串中的emoji符号转换为*
     * @author huyang 2017年4月6日下午12:38:04
     * @注意：本内容仅限于杭州霖梓网络科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
     */
    public static String filterEmoji(String source) {
        if (source != null) {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                source = emojiMatcher.replaceAll("*");
                return source;
            }
            return source;
        }
        return source;
    }

    public static String null2Str(Object str) {
        return (str != null) ? str.toString() : "";
    }

    public static String logisticsInfoDeal(String str) {
        if (str == null || "暂无".equals(str.trim())) {
            return "";
        }
        return str.trim();
    }

    /**
     * @param sStr
     * @return
     * @Title: UrlEncoder
     * @Description: 字符串编码
     */
    public final static String UrlEncoder(String sStr) {
        String sReturnCode = "";
        try {
            sReturnCode = URLEncoder.encode(null2Str(sStr), "utf-8");
        } catch (Exception ex) {
        }
        return sReturnCode;
    }

    /**
     * @param sStr
     * @return
     * @Title: UrlDecoder
     * @Description: 字符串解码
     */
    public static String UrlDecoder(String sStr) {
        if (isEmpty(sStr)) {
            return "";
        } else {
            String sReturnCode = sStr;
            try {
                sReturnCode = URLDecoder.decode(sStr, "utf-8");
            } catch (Exception e) {
            }
            return sReturnCode;
        }
    }


    /**
     * 获取唯一编码
     *
     * @param userId
     * @return
     */
    public static String getUniqueCode(Long userId) {
        return Long.toString((userId + INVITE_START_VALUE), 64);
    }

    /**
     * fmai 根据基数产生随机四位数
     *
     * @return
     */
    public static String getFourRandomNum() {
        StringBuilder randomNum = new StringBuilder();
        int length = CARDINALNUM.length;
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            randomNum.append(CARDINALNUM[random.nextInt(length)]);
        }
        return randomNum.toString();
    }


    /**
     * fmai 根据基数产生随机5位数
     *
     * @return
     */
    public static String getFiveRandomNum() {
        StringBuilder randomNum = new StringBuilder();
        int length = CARDINALNUM.length;
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            randomNum.append(CARDINALNUM[random.nextInt(length)]);
        }
        return randomNum.toString();
    }


    /**
     * fmai 根据日期年月日时生成对应的32进制字符串
     *
     * @return
     */
    public static String toBinaryByTime() {
        StringBuilder binary = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        int year = (cal.get(Calendar.YEAR) - 2000) % 32;
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DATE);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        binary.append(DUOTRIKEY[year]).append(DUOTRIKEY[month]).append(DUOTRIKEY[day]).append(DUOTRIKEY[hour]);
        return binary.toString();
    }


    /**
     * 首字母大写
     *
     * @param s
     * @return
     */
    public static String firstCharUpperCase(String s) {
        StringBuffer sb = new StringBuffer(s.substring(0, 1).toUpperCase());
        sb.append(s.substring(1, s.length()));
        return sb.toString();
    }


    /**
     * 字符串空处理，去除首尾空格 如果str为null，返回"",否则返回str
     *
     * @param str
     * @return
     */
    public static String isNull(String str) {
        if (str == null) {
            return "";
        }
        return str.trim();
    }


    /**
     * 将对象转为字符串
     *
     * @param o
     * @return
     */
    public static String isNull(Object o) {
        if (o == null) {
            return "";
        }
        String str = "";
        if (o instanceof String) {
            str = (String) o;
        } else {
            str = o.toString();
        }
        return str.trim();
    }


    public static String removeDoubleChar(String str) {
        if (str.indexOf("\"") == 0) str = str.substring(1, str.length());   //去掉第一个 "
        if (str.lastIndexOf("\"") == (str.length() - 1)) str = str.substring(0, str.length() - 1);  //去掉最后一个 "
        return str;
    }


    /**
     * 通过请求参数获取键值对
     *
     * @param requestParams
     * @return
     */
    public static Map<String, String> getRequestParams(Map requestParams) {
        Map<String, String> params = new HashMap<String, String>();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }


    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }


        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("signInfo")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }


    /**
     * 以逗号隔开的元素
     *
     * @param value
     * @param i
     * @return
     */
    public static String getSpitStr(String value, int i) {
        String values[] = value.split(",", value.length());
        if (i == 0) {
            i = i + 1;
        }
        if (values.length >= i) {
            return values[i - 1];
        } else {
            return values[0];
        }
    }


    /**
     * 拼接get请求的url请求地址
     */
    public static String getRqstUrl(String url, Map<String, String> params) {
        StringBuilder builder = new StringBuilder(url);
        boolean isFirst = true;
        for (String key : params.keySet()) {
            if (key != null && params.get(key) != null) {
                if (isFirst) {
                    isFirst = false;
                    builder.append("?");
                } else {
                    builder.append("&");
                }
                builder.append(key)
                        .append("=")
                        .append(params.get(key));
            }
        }
        return builder.toString();
    }


    public static StringBuffer appendSb(String... strs) {
        StringBuffer stringBuffer = new StringBuffer();
        if (strs != null && strs.length > 0) {
            for (int i = 0; i < strs.length; i++) {
                stringBuffer.append(strs[i]);
            }
        }
        return stringBuffer;
    }


    /**
     * 截取一个字符串中两个字符串中间的字符串
     *
     * @param str
     * @param strStart
     * @param strEnd
     * @return
     */
    public static String subMidString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
        }
        if (strEndIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
        }
        /* 开始截取 */
        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
    }

    public static boolean getClassName(String className) {
        return false;
    }

    /**
     * Object转换String
     *
     * @param obj
     * @return
     */
    public static String objToString(Object obj) {
        if (null != obj) {
            return String.valueOf(obj);
        } else {
            return null;
        }
    }


    public static CharSequence getStringNum(String payNO) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(payNO);
        return m.replaceAll("").trim();
    }


    /**
     * 字符串转换为Ascii
     *
     * @param value
     * @return
     */
    public static String stringToAscii(String value, int step) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i] + step).append(",");
            } else {
                sbu.append((int) chars[i] + step);
            }
        }
        return sbu.toString();
    }

    /**
     * Ascii转换为字符串
     *
     * @param value
     * @return
     */
    public static String asciiToString(String value) {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }


    public static String getSignStr(Map<String, String> paramMap) {

        StringBuffer sbfStr = new StringBuffer();
        List<String> list = new ArrayList<String>(paramMap.keySet());
        Collections.sort(list);
        for (String key : list) {
            if (!"sign".equals(key) && isNotBlank(paramMap.get(key))) {
                sbfStr.append(key + "=" + paramMap.get(key) + "&");
            }
        }
        String pendVertContent = sbfStr.toString().substring(0, sbfStr.length() - 1);
        return pendVertContent;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }


    public static String getRandomStr(int length) {
        int randomNum;
        char randomChar;
        Random random = new Random();
        // StringBuffer类型的可以append增加字符
        StringBuffer str = new StringBuffer();

        for (int i = 0; i < length; i++) {
            // 可生成[0,n)之间的整数，获得随机位置
            randomNum = random.nextInt(base.length());
            // 获得随机位置对应的字符
            randomChar = base.charAt(randomNum);
            // 组成一个随机字符串
            str.append(randomChar);
        }
        return str.toString();
    }

    public static String removeEndStr(StringBuilder sbSelect) {
        if (sbSelect == null) {
            return "";
        }

        return sbSelect.toString().substring(0, sbSelect.toString().length() - 1);

    }


    public static String removeEndStr(StringBuilder sbSelect, String end) {
        if (sbSelect == null) {
            return "";
        }
        if (sbSelect.toString().endsWith(end)) {
            return sbSelect.toString().substring(0, sbSelect.toString().length() - 1);
        }
        return sbSelect.toString();

    }


    public static String removeEndStr(String sbSelect, String end) {
        if (sbSelect == null) {
            return "";
        }
        if (sbSelect.toString().endsWith(end)) {
            return sbSelect.toString().substring(0, sbSelect.toString().length() - 1);
        }
        return sbSelect.toString();

    }


    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


    /**
     * @param args
     */


    public final static String H5_SMS_BEGIN = "验证码是";
    public final static String H5_SMS_END = "。";
    public final static int SOURCE_TYPE = 0;


    public static int removeGB(String value) {
        if (StringUtil.isNotBlank(value) && value.contains("GB")) {
            value = value.replaceAll("GB", "");
            value = value.trim();
            return NumberUtil.objToIntDefault(value, 0);
        }
        return 0;
    }

    // "window.rawData"
    public static String getHtmlByHeader(String context, String header) {
        String contexts[] = context.split("\n");
        for (String c : contexts) {
            if (StringUtil.isNotBlank(c)) {
                String line = c.trim();
                if (line.startsWith(header)) {
                    line = line.substring(line.indexOf(header) + header.length());
                    line = line.trim();
                    line = line.substring(line.indexOf("=") + 1);
                    if (line.endsWith(";")) {
                        line = line.substring(0, line.length() - 1);
                    }
                    return line.trim();
                }
            }
        }
        return null;
    }

    public static String getOriginalData(String context, String dataStart, int dataStartCorrect, String dataEnd, int dataEndCorrect) {
        int m = 0;
        String result = context;
        if (isNotEmpty(dataStart)) {
            m = context.indexOf(dataStart);
            if (m < 0) {
                m = 0;
            } else {
                m += dataStart.length();
            }
        }

        m += dataStartCorrect;
        if (m > 0) {
            result = context.substring(m);
        }

        if (isNotEmpty(dataEnd)) {
            m = result.indexOf(dataEnd);
            if (m < 0) {
                m = 0;
            }
        }
        m += dataEndCorrect;
        if (m > 0) {
            result = result.substring(0, m);
        }
        return result;
    }


    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }


    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            //group 6728
            String group = matcher.group(2);
            //ch:'木' 26408
            ch = (char) Integer.parseInt(group, 16);
            //group1 \u6728
            String group1 = matcher.group(1);
            str = str.replace(group1, ch + "");
        }
        return str;
    }

    public static String decodeBase64(String res) {
        if (StringUtil.isBlank(res)) {
            return null;
        }
        try {
            return new String(Base64.getDecoder().decode(res), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
