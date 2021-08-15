package com.victortello.ws.webservice.security;


public class SecurityConstants {

    public static final long EXPIRATION_TIME = 3600000; // 1 hour
    public static final String TOKEN_PREFIX = "Barear ";
    public static final String SIGN_UP_URL = "/users";
    public static final String TOKEN_SECRET = "adp3jdu83u7sdh";
    public static final String HEADER_STRING = "authorization";
    public static final String VERIFICATION_EMAIL_URL = "/users/email-verification";
    
}
