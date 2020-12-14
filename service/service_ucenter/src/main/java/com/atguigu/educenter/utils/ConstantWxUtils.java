package com.atguigu.educenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

public class ConstantWxUtils implements InitializingBean {




    @Value("${WX_OPEN_APPID}")
    private String redirectUrl;
    @Value("${WX_OPEN_APPSECRET}")
    private String appId;
    @Value("${WX_OPEN_REDIRECTURL}")
    private String appSecret;
    @Value("${WX_OPEN_QRURL}")
    private String wxQrUrl;

    public static String WX_OPEN_REDIRECT_URL;
    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_QRURL;

    @Override
    public void afterPropertiesSet() throws Exception {
        WX_OPEN_REDIRECT_URL = redirectUrl;
        WX_OPEN_APP_ID = appId;
        WX_OPEN_APP_SECRET = appSecret;
        WX_OPEN_QRURL = wxQrUrl;
    }
}
