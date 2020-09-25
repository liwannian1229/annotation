package com.lwn.request;

import com.lwn.common.CommonUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * SessionHolder
 */
public class SessionHolder {
    private SessionHolder() {
    }

    public static HttpSession getSession() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert sra != null;
        HttpServletRequest request = sra.getRequest();
        return request.getSession(true);
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra == null) {
            return null;
        }
        return sra.getRequest();
    }

    public static String getRemoteIp() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra == null) {
            return null;
        }
        String ip = sra.getRequest().getRemoteHost();
        String localIp = "127.0.0.1";
        String localHost = "localhost";
        if (CommonUtil.isEmpty(ip) || ip.contains(localIp) || ip.contains(localHost)) {
            ip = sra.getRequest().getHeader("Host");
            if (CommonUtil.isEmpty(ip) || ip.contains(localIp) || ip.contains(localHost)) {
                ip = sra.getRequest().getHeader("X-Real_IP");
                if (CommonUtil.isEmpty(ip) || ip.contains(localIp) || ip.contains(localHost)) {
                    ip = sra.getRequest().getHeader("Remote_Addr");
                }
            }
        }

        return ip;
    }

}
