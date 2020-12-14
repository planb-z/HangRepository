package com.atguigu.educenter.controller;

import com.atguigu.educenter.utils.ConstantWxUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
@Slf4j
public class WxApiController {

    @GetMapping("login")
    public String genQrConnect(HttpSession session) {

        //微信开放平台授权baseUrl
        String baseUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL
                + "?appid=%s"
                + "&redirect_uri=%s"
                + "&response_type=code"
                + "&scope=snsapi_login"
                + "&state=%s"
                + "#wechat_redirect";
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl =  URLEncoder.encode(ConstantWxUtils.WX_OPEN_REDIRECT_URL, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("login encode error" + e.getMessage());
        }
        String state = "never";
        String qrCodeUrl = String.format(baseUrl,ConstantWxUtils.WX_OPEN_APP_ID,redirectUrl,state);

        return "redirect:" + qrCodeUrl;


    }

    @GetMapping("callback")
    public String callback(String code, String state) {
        System.out.println("code=" + code);
        System.out.println("state=" + state);
        return "";
    }
}
