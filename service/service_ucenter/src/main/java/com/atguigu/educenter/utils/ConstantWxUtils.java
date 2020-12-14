package com.atguigu.educenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantWxUtils implements InitializingBean {
    @Value("${WX_OPEN_APPID}")
    private String appId;

    @Value("${WX_OPEN_APPSECRET}")
    private String appSecret;

    @Value("${WX_OPEN_REDIRECTURL}")
    private String redirectUrl;

    @Value("${WX_OPEN_QRURL}")
    private String wxQrUrl;

    @Value("${WX_OPEN_ACCESSTOKEN_URL}")
    private String accessTokenUrl;

    @Value("${WX_OPEN_GET_USERINFO_URL}")
    private String getUserInfoUrl;

    public static String WX_OPEN_REDIRECT_URL;
    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_QRURL;
    public static String WX_OPEN_ACCESSTOKEN_URL;
    public static String WX_OPEN_GET_USERINFO_URL;
    @Override
    public void afterPropertiesSet() throws Exception {
        WX_OPEN_REDIRECT_URL = redirectUrl;
        WX_OPEN_APP_ID = appId;
        WX_OPEN_APP_SECRET = appSecret;
        WX_OPEN_QRURL = wxQrUrl;
        WX_OPEN_ACCESSTOKEN_URL = accessTokenUrl;
        WX_OPEN_GET_USERINFO_URL = getUserInfoUrl;
    }
}
