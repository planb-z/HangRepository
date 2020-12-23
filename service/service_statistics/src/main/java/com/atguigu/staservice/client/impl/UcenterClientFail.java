package com.atguigu.staservice.client.impl;

import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.staservice.client.UcenterClient;

public class UcenterClientFail  implements UcenterClient {
    @Override
    public R registerCount(String day) {
        throw new GuliException(20001,"registerCount clinet error");
    }
}
