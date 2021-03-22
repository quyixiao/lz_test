package com.admin.crawler.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;
import java.util.zip.GZIPOutputStream;


public class ServletUtils {
    private static final Logger logger = LoggerFactory.getLogger(ServletUtils.class);

    public static final long ONE_DAY_SECONDS = 60 * 60 * 24;
    public static final long ONE_WEEK_SECONDS = ONE_DAY_SECONDS * 7;
    public static final long ONE_MONTH_SECONDS = ONE_DAY_SECONDS * 30;
    public static final long ONE_YEAR_SECONDS = ONE_DAY_SECONDS * 365;
    private static final String CONTENT_TYPE = "content-type";

    public static final String EXCEL_TYPE = "application/vnd.ms-excel";
    public static final String HTML_TYPE = "text/html";
    public static final String JS_TYPE = "text/javascript";
    public static final String CSS_TYPE = "text/css";
    public static final String JSON_TYPE = "application/json";
    public static final String XML_TYPE = "text/xml";
    public static final String TEXT_TYPE = "text/plain";

    public static final String CODE_UTF8 = "UTF-8";

    /***
     * 线程编号
     */
    public static String KEY_AUTO_TN_START = "X-AUTO-FP-TN-START";


    public static final ThreadLocal<Object> threadLocalObj = new ThreadLocal<Object>();


    public static final InheritableThreadLocal<Long> inheritableThreadLocalTime = new InheritableThreadLocal<Long>();

    public static final InheritableThreadLocal<String> inheritableThreadLocalNo = new InheritableThreadLocal<String>();

    // public static final String CODE_GBK = "GBK";

    public static void setContentType(HttpServletResponse response,
                                      String contentType, String encoding) {
        setContentType(response, contentType);
        response.setCharacterEncoding(encoding);
    }

    public static void setContentType(HttpServletResponse response,
                                      String contentType) {
        response.setContentType(contentType);
    }


    /**
     * 创建gzip流
     *
     * @param response
     * @return
     * @throws IOException
     */
    public static OutputStream buildGzipOutputStream(
            HttpServletResponse response) throws IOException {
        response.setHeader("Content-Encoding", "gzip");
        response.setHeader("Vary", "Accept-Encoding");
        return new GZIPOutputStream(response.getOutputStream());
    }


    /**
     * 设置不缓存
     *
     * @param response
     */
    public static void setNoCacheHeader(HttpServletResponse response) {
        response.setDateHeader("Expires", 1L);
        response.addHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
    }

    /**
     * 设置缓存过期时间
     *
     * @param response
     * @param expiresSeconds
     */
    public static void setExpiresHeader(HttpServletResponse response,
                                        long expiresSeconds) {
        response.setDateHeader("Expires", System.currentTimeMillis()
                + expiresSeconds * 1000);
        response.setHeader("Cache-Control", "private, max-age="
                + expiresSeconds);
    }

    /**
     * 设置最后修改时间
     *
     * @param response
     * @param lastModifiedDate
     */
    public static void setLastModifiedHeader(HttpServletResponse response,
                                             long lastModifiedDate) {
        response.setDateHeader("Last-Modified", lastModifiedDate);
    }

    /**
     * 设置Etag
     *
     * @param response
     * @param etag
     */
    public static void setEtag(HttpServletResponse response, String etag) {
        response.setHeader("ETag", etag);
    }

    /**
     * 检查请求带过来的资源的最后修改时间是否已经改变
     *
     * @param request
     * @param response
     * @param lastModified
     * @return
     */
    public static boolean checkIfModifiedSince(HttpServletRequest request,
                                               HttpServletResponse response, long lastModified) {
        long ifModifiedSince = request.getDateHeader("If-Modified-Since");
        if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return false;
        }
        return true;
    }

    /**
     * 检查请求带过来的Etag是否和服务器匹配
     *
     * @param request
     * @param response
     * @param etag
     * @return
     */
    public static boolean checkIfNoneMatchEtag(HttpServletRequest request,
                                               HttpServletResponse response, String etag) {
        String headerValue = request.getHeader("If-None-Match");
        if (headerValue != null) {
            boolean conditionSatisfied = false;
            if (!"*".equals(headerValue)) {
                StringTokenizer commaTokenizer = new StringTokenizer(
                        headerValue, ",");
                while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
                    String currentToken = commaTokenizer.nextToken();
                    if (currentToken.trim().equals(etag)) {
                        conditionSatisfied = true;
                    }
                }
            } else {
                conditionSatisfied = true;
            }
            if (conditionSatisfied) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                response.setHeader("ETag", etag);
                return false;
            }
        }
        return true;
    }

    /**
     * 设定响应为文件下载
     *
     * @param response
     * @param fileName
     */
    public static void setFileDownloadHeader(HttpServletResponse response,
                                             String fileName) {
        try {
            String encodedfileName = new String(fileName.getBytes(),
                    "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + encodedfileName + "\"");
        } catch (UnsupportedEncodingException e) {
        }
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getIpAddress() {
        String ip = "127.0.0.1";
        try {
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();

            ip = request.getHeader("x-forwarded-for");

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            return ip;
        } catch (Exception e) {

        }
        return ip;
    }
}
