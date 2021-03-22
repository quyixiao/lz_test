package com.admin.crawler.aop;


import com.admin.crawler.dto.test.UserInfo;
import com.admin.crawler.utils.*;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志异常处理
 *
 * @author zhuhuakun
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    public static final ThreadLocal<UserInfo> THREAD_LOCAL = new ThreadLocal<>();

    public static final ThreadLocal<Long> SPACE_ID = new ThreadLocal<>();


    @Pointcut(value = "execution(* com.admin.crawler.controller..*.*(..))")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String logNo = OrderUtil.getUserPoolOrder("tr");
        long start = System.currentTimeMillis();
        Object result = null;
        String uri = "";
        StringBuilder cm = new StringBuilder();
        result = null;
        Object arg = result;
        String ip = "";
        String username = "";
        String userId = "";
        String m = "";
        try {
            ch.qos.logback.classic.Logger.threadLocalNo.set(logNo);
            ch.qos.logback.classic.Logger.threadLocalTime.set(start);
            ch.qos.logback.classic.Logger.inheritableThreadLocalNo.set(logNo);
            ch.qos.logback.classic.Logger.inheritableThreadLocalTime.set(start);
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            Object[] args = point.getArgs();
            String token = request.getHeader("token");
            if (StringUtil.isNotBlank(token)) {
                Object user = redisCacheUtil.getObject(token);
                if (user != null) {
                    UserInfo userInfo = (UserInfo) user;
                    username = userInfo.getUsername();
                    userId = userInfo.getUserId() + "";
                    THREAD_LOCAL.set(userInfo);
                } else {
                    checkLogin(request.getRequestURI());
                }
            } else {
                checkLogin(request.getRequestURI());
            }
            String id = request.getHeader("spaceId");
            if (StringUtil.isNotBlank(id)) {   // "" " "
                SPACE_ID.set(NumberUtil.objToLongDefault(id, 0l));
            }
            //过滤掉spring的一些http请求信息，下面的转jsonstring会抛异常
            if (args != null && args.length > 0) {
                for (Object arg1 : args) {
                    if (arg instanceof HttpServletResponse ) {
                        continue;
                    } else if (arg1 instanceof HttpServletRequest) {
                        continue;
                    } else if (arg1 instanceof MultipartFile) {
                        continue;
                    } else if (arg1 instanceof MultipartFile[]) {
                        continue;
                    } else {
                        arg = arg1;
                    }
                }
            }
            m = request.getMethod();
            uri = request.getRequestURI();
            ip = ServletUtils.getIpAddress(request);

            // result的值就是被拦截方法的返回值
            String classMethod = point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName();
            if (StringUtil.isNotBlank(classMethod) && classMethod.length() > 0 && classMethod.contains(".")) {
                String classMethods[] = classMethod.split("\\.");
                if (classMethod.length() >= 2) {
                    cm.append(classMethods[classMethods.length - 2]).append(".").append(classMethods[classMethods.length - 1]);
                }
            }

            result = point.proceed();

            return result;
        } catch (Exception e) {
            logger.error("controller error.", e);
            result = R.error();
        } finally {
            THREAD_LOCAL.remove();
            logger.info(StringUtil.appendStrs(
                    "	", "cm=", cm.toString(),
                    "	", "m=", m,
                    "	", "username=", username,
                    "	", "userId=", userId,
                    "	", "m=", m,
                    "	", "uri=", uri,
                    "	", "ip=", ip,
                    "   ", "params=", JSON.toJSONString(arg),
                    "   ", "result=", JSON.toJSONString(result)
            ));

            ch.qos.logback.classic.Logger.threadLocalNo.remove();
            ch.qos.logback.classic.Logger.threadLocalTime.remove();
            ch.qos.logback.classic.Logger.inheritableThreadLocalNo.remove();
            ch.qos.logback.classic.Logger.inheritableThreadLocalTime.remove();

        }
        return result;

    }


    public boolean checkLogin(String uri) {
        if (true) {
            return true;
        }
        String[] noLogins = {"/user/login"};
        for (String noLogin : noLogins) {
            if (uri.endsWith(noLogin)) {
                return false;
            }
        }
        log.info("uri:" + uri + "，请登陆");
        //throw new RuntimeException("请登陆");
        return false;

    }


}
