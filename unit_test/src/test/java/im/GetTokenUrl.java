package im;

import com.atguigu.commonutils.HttpClientUtils;
import com.atguigu.commonutils.MD5Generator;
import org.junit.Test;

public class GetTokenUrl {


    private String imWxUseEnv = "USE_CURRNTE_ENVIRONMENT";
    private String imWxDomain = "http://cloud.21tb.com";
    private String getWxAccessTokenURL ="/biz-oim/wxAuth/getAccessToken.do";

    @Test
    public void getTokenUrl() throws Exception {
        long currentTIme = System.currentTimeMillis();
        String hexMD5 = MD5Generator.getHexMD5(imWxUseEnv + currentTIme);
        System.out.println(imWxDomain + getWxAccessTokenURL + "?md5Key=" + hexMD5 + "&currentTime=" + currentTIme);
    }
}
