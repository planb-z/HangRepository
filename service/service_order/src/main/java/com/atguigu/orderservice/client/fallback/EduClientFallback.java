package com.atguigu.orderservice.client.fallback;

import com.atguigu.commonutils.vo.CourseInfoForm;
import com.atguigu.orderservice.client.EduClient;
import com.atguigu.servicebase.exceptionhandler.GuliException;

public class EduClientFallback implements EduClient {
    @Override
    public CourseInfoForm getCourseInfoDto(String courseId) {
        throw new GuliException(20001,"调用服务失败 eduClient.getCourseInfoDto."+courseId);
    }
}
