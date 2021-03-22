package com.admin.crawler.constant;

/**
 * @author yuzhong
 * @date 2017-12-20 10:46 AM
 */
public class CacheConstants {



    public static final long MINITS_OF_THREE = 3 * 60l;// 180秒
    // 单位秒
    public static final long SECOND_OF_ONE_MINITS = 1 * 60l;
    public static final long SECOND_OF_ONE = 1L;
    public static final long FIVETY_MINITS = 1 * 50l;
    public static final long SECOND_OF_THREE_MINITS = 3 * 60l;
    public static final long SECOND_OF_FIVE_MINITS = 5 * 60l;
    public static final long SECOND_OF_TEN_MINITS = 10 * 60l;
    public static final long SECOND_OF_THREE = 30l;// 30秒
    public static final long SECOND_OF_HALF_HOUR = 30 * 60l;
    public static final long SECOND_OF_AN_HOUR = 60 * 60l;
    public static final long SECOND_OF_SIX_HOUR = 6 * 60 * 60l;
    public static final long SECOND_OF_ONE_DAY = 24 * 60 * 60l;
    public static final long SECOND_OF_TWO_DAY = 2 * 24 * 60 * 60l;
    public static final long SECOND_OF_FOUR_DAY = 4 * 24 * 60 * 60l;
    public static final long SECOND_OF_ONE_WEEK = 7 * 24 * 60 * 60l;
    public static final long SECOND_OF_TWO_WEEK = 14 * 24 * 60 * 60l;
    public static final long SECOND_OF_ONE_MONTH = 30 * 24 * 60 * 60l;
    public static final long SECOND_OF_ONE_YEAR = 12 * 30 * 24 * 60 * 60l;
    public static final long DEFAULT_EXPIRE_SECOND = SECOND_OF_SIX_HOUR;
    public static final long HOUR_OF_ONE = 60 * 60l;// 1小时



    private String key;

    private long expire;

    private CacheConstants(String key, long expire) {
        this.key = key;
        this.expire = expire;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }
}
