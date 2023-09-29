package cn.neorae.wtu.common.util;

import cn.hutool.core.util.ArrayUtil;
import jakarta.servlet.http.HttpServletRequest;

import static cn.hutool.extra.servlet.JakartaServletUtil.getClientIPByHeader;

public class IpUtil {

    public static String getClientIP(HttpServletRequest request, String... otherHeaderNames) {
        String[] headers = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        if (ArrayUtil.isNotEmpty(otherHeaderNames)) {
            headers = ArrayUtil.addAll(headers, otherHeaderNames);
        }

        return getClientIPByHeader(request, headers);
    }

}
