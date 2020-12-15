package com.atguigu.demo;

public class demo {
    public static void main(String[] args) {
        Signer signer = new Signer();
        long time = System.currentTimeMillis();
        String corpCode = "dingchenglife";
        String sercret = "tbc123";
        String loginName = "admin";
        String sign = signer.calculateSign("sso", sercret, corpCode, loginName, time);
        String domain = null;
        switch (corpCode) {
            case "jinj":
                domain = "cloud";
                break;
            case "shyf17":
                domain = "yufa";
                break;
            case "dingchenglife":
                domain = "yufa";
                break;
            case "sh911":
                domain = "v4";
                break;
        }
        String result = domain + ".21tb.com/wx/elpSSO/ssoLogin.do?userName=" + loginName + "&timestamp=" + time + "&corpCode=" + corpCode + "&sign=" + sign;
        System.out.println(result);
    }
}
