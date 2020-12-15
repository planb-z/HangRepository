package com.atguigu.vod.controller;

import com.atguigu.vod.Utils.Signer;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@CrossOrigin
@Api(value = "【鼎诚人寿-单点+埋点】 测试接口", description = "【鼎诚人寿-单点+埋点】 测试接口")
public class TestController {

    @GetMapping("/getElpUrl")
    @ApiOperation(value = "根据用户名、密钥、用户名生成单点链接")
    public String getSSOURL( String corpCode,
                            String sercret,
                         String loginName) {
        Signer signer = new Signer();
        long time = System.currentTimeMillis();
        String sign = signer.calculateSign("sso", sercret, corpCode, loginName, time);
        String domain = null;
        switch (corpCode) {
            case "jinj":
                domain = "cloud";
                break;
            case "shyf17":
                domain = "yufa";
                break;
            case "sh911":
                domain = "v4";
                break;
        }
        String result = domain + ".21tb.com/wx/elpSSO/ssoLogin.do?userName=" + loginName + "&timestamp=" + time + "&corpCode=" + corpCode + "&sign=" + sign;
        return result;
    }
}
