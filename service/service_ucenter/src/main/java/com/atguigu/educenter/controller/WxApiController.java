package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantWxUtils;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
@Slf4j
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    @GetMapping("login")
    public String genQrConnect(HttpSession session) {

        //微信开放平台授权baseUrl
        String baseUrl = ConstantWxUtils.WX_OPEN_QRURL
                + "?appid=%s"
                + "&redirect_uri=%s"
                + "&response_type=code"
                + "&scope=snsapi_login"
                + "&state=%s"
                + "#wechat_redirect";
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(ConstantWxUtils.WX_OPEN_REDIRECT_URL, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("login encode error" + e.getMessage());
        }
        String state = "never";
        String qrCodeUrl = String.format(baseUrl, ConstantWxUtils.WX_OPEN_APP_ID, redirectUrl, state);

        return "redirect:" + qrCodeUrl;


    }

    @GetMapping("callback")
    public String callback(String code, String state) {
        String baseAccessTokenUrl = ConstantWxUtils.WX_OPEN_ACCESSTOKEN_URL
                + "?appid=%s"
                + "?secret=%s"
                + "?code=%s"
                + "?grant_type=authorization_code";
        String result = null;

        try {
            result = HttpClientUtils.get(baseAccessTokenUrl);
        } catch (Exception e) {
            log.error("wx  callback" + e.getMessage());
            e.printStackTrace();
        }

        Gson gson = new Gson();
        HashMap<String, String> map = gson.fromJson(result, HashMap.class);
        String accessToken = map.get("access_token");
        String openId = map.get("openid");

        //查询数据库当前用户是否曾经使用过微信登录
        UcenterMember member = memberService.getByOpenId(openId);
        /*
            如果此处微信没有绑定偶
            可以跳转去对应的页面提示用户绑定手机号
            判断手机号是否存在于数据库中
            如果是直接将微信和对应的手机号绑定
            否则进行注册操作将微信和手机号绑定
         */
        if (member == null) {
            System.out.println("新用户注册,openId" + openId);
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = ConstantWxUtils.WX_OPEN_GET_USERINFO_URL
                    + "?access_token=%s"
                    + "?openid=%s";
            String userInfoUrl = String.format(baseAccessTokenUrl, accessToken, openId);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
            } catch (Exception e) {
                log.error("获取微信人员信息失败，url" + userInfoUrl + ",errorMsg:" + e.getMessage());
                e.printStackTrace();
            }
            HashMap<String, String> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = mapUserInfo.get("nickname");
            String headimgurl = mapUserInfo.get("headimgurl");
            member = new UcenterMember();
            member.setNickname(nickname);
            member.setOpenid(openId);
            member.setAvatar(headimgurl);
            memberService.save(member);
        }
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return "redirect:http://localhost:3000?token=" + token;
    }
}
