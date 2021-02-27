package com.datealive.utils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.datealive.pojo.User;
import com.datealive.shiro.MyProfile;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;


import java.io.InputStream;
import java.lang.reflect.Method;



/**
 * @Classname  UserUtils
 * @Description 获取当前登录的用户
 * @Date 2021/2/17 15:06
 * @author 冰羽
 */
@Slf4j
@Component
public class UserUtils {
    /**
     *
     * @Title: getSubjectUser
     * @Description: 获取shiro中登录的用户
     * @return
     */
    public static MyProfile getSubjectUser() {
        MyProfile user = (MyProfile) SecurityUtils.getSubject().getPrincipal();
        return user;
    }
    /**
     *
     * @Title: removeSubjectUser
     * @Description: 从shiro中移除登录的用户
     */
    public static void removeSubjectUser() {
        SecurityUtils.getSubject().logout();
    }
    /**
     *
     * @Title: getRequest
     * @Description: 获取当前的request
     * @return
     */
    public static HttpServletRequest getRequest() {
        return  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
    }

    /**
     * 获取IP地址
     *
     * @return
     */
    public static String getIpAddress() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    private static DbSearcher searcher;
    private static Method method;

    /**
     * 在服务启动时加载 ip2region.db 到内存中
     * 解决打包jar后找不到 ip2region.db 的问题
     */
    @PostConstruct
    private void initIp2regionResource() {
        try {
            InputStream inputStream = new ClassPathResource("/ipdb/ip2region.db").getInputStream();
            //将 ip2region.db 转为 ByteArray
            byte[] dbBinStr = FileCopyUtils.copyToByteArray(inputStream);
            DbConfig dbConfig = new DbConfig();
            searcher = new DbSearcher(dbConfig, dbBinStr);
            //二进制方式初始化 DBSearcher，需要使用基于内存的查找算法 memorySearch
            method = searcher.getClass().getMethod("memorySearch", String.class);
        } catch (Exception e) {
            log.error("initIp2regionResource exception:", e);
        }
    }

    /**
     * 根据ip从 ip2region.db 中获取地理位置
     *
     * @param ip
     * @return
     */
    public static String getCityInfo(String ip) {
        if (ip == null || !Util.isIpAddress(ip)) {
            log.error("Error: Invalid ip address");
            return null;
        }
        try {
            DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
            String ipInfo = dataBlock.getRegion();
            if (!StringUtils.isEmpty(ipInfo)) {
                ipInfo = ipInfo.replace("|0", "");
                ipInfo = ipInfo.replace("0|", "");
            }
            return ipInfo;
        } catch (Exception e) {
            log.error("getCityInfo exception:", e);
        }
        return null;
    }


    /**
     *
     * @Title: getSessionUser
     * @Description: 获取session中登录的用户（开启shiro后失效）
     * @return
     */
    @Deprecated
    public static User getSessionUser() {
        return (User)getRequest().getSession().getAttribute("user");
    }

    /**
     *
     * @Title: setSessionUser
     * @Description: 将当前登录的用户信息放入session（开启shiro后失效）
     * @param user
     */
    @Deprecated
    public static void setSessionUser(User user) {
        getRequest().getSession().setAttribute("user", user);
    }

    /**
     *
     * @Title: removeSessionUser
     * @Description: 从session中移除user（开启shiro后失效）
     */
    @Deprecated
    public static void removeSessionUser() {
        getRequest().getSession().removeAttribute("user");
    }
}
