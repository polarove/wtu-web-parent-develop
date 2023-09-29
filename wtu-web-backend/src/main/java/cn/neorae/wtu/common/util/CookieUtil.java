package cn.neorae.wtu.common.util;

import cn.hutool.core.collection.ArrayIter;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.map.CaseInsensitiveMap;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
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
    public static Map<String, Cookie> getCookies(HttpServletRequest request) {

        final Cookie[] cookies = request.getCookies();
        if (ArrayUtil.isEmpty(cookies)) {
            return MapUtil.empty();
        }

        return IterUtil.toMap(
                new ArrayIter<>(request.getCookies()),
                new CaseInsensitiveMap<>(),
                Cookie::getName);
    }

    public static String getUUID(HttpServletRequest request, String key) {
        Map<String, Cookie> cookieMap = getCookies(request);
        return cookieMap.get(key).getValue();
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
