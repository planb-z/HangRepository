package com.atguigu.orderservice.client;

import com.atguigu.orderservice.client.fallback.EduClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-edu")
@Component
public interface EduClient {

    //根据课程id查询课程信息
    @GetMapping("/eduservice/course/getDto/{courseId}")
    com.atguigu.commonutils.vo.CourseInfoForm getCourseInfoDto(@PathVariable(value = "courseId") String courseId);

}
