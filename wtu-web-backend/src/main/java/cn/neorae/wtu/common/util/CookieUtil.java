package cn.neorae.wtu.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

public class CookieUtil {


    // 设置Cookie
    public static void setCookie(HttpServletResponse response, String key, String value, int expiry, String domain) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(expiry);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setDomain(domain);
        cookie.setPath("/");
        response.addCookie(cookie);
    }


    // 获取Cookie
    public static Map<String, String> getCookies(HttpServletRequest request) {

        Map<String, String> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (!"JSSESSIONID".equals(cookie.getName())) {
                    cookieMap.put(cookie.getName(), cookie.getValue());
                }
            }
        }
        return cookieMap;
    }

    public static String getUUID(HttpServletRequest request, String key) {
        Map<String, String> cookieMap = getCookies(request);
        return cookieMap.get(key);
    }

    public static void removeCookie(HttpServletResponse response, String key, String domain) {
        Cookie cookie = new Cookie(key, null);
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setDomain(domain);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
