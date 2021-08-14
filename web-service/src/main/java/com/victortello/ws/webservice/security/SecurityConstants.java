package com.victortello.ws.webservice.security;

import com.victortello.ws.webservice.AppProperties;
import com.victortello.ws.webservice.SpringApplicationContext;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 3600000; // 1 hour
    public static final String TOKEN_PREFIX = "Barear ";
    public static final String SIGN_UP_URL = "/users";
    public static final String TOKEN_SECRET = "adp3jdu83u7sdh";
    public static final String HEADER_STRING = "authorization";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
