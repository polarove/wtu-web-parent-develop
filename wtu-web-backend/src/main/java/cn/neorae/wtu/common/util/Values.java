package cn.neorae.wtu.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Values {


    @Value("${domain.name}")
    public String domain;

    @Value("${domain.frontend}")
    public String frontAddr;

    public static final String Fingerprint = "fingerprint";

    public static final String Token = "token";

    public static final Integer CookieExpiry = 60 * 60 * 24 * 365;
}
