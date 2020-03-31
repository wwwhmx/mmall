package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by www on 2020/3/30.
 */
@Slf4j
public class CookiesUtil {
    private final static String COOKIE_DOMAIN = ".hmx.com";
    private final static String COOKIE_NAME = "mmall_login_token";

    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                log.info("read cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    log.info("return cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);  //设置成0代表删除此cookies
                    log.info("del cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
                    response.addCookie(ck);
                    return ;
                }
            }
        }
    }

    public static void writeLoginToken(HttpServletResponse response, String token) {
        Cookie ck = new Cookie(COOKIE_NAME, token);
        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");

        //单位是秒
        //如果这个maxage不设置的话，cookies就不会写入硬盘，而是写在内存上，只在当前页面有效
        ck.setMaxAge((60 * 60 * 24 * 365));     //如果是-1,代表永久
        log.info("wirte cookieName:{}, cookieValue:{}", ck.getName(), ck.getValue());
        response.addCookie(ck);
    }

}
