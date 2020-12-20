package com.atguigu.staservice.feign;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@ComponentScan
@FeignClient("service-ucenter")
public interface UcenterClient {

    @GetMapping("/ucenterservice/member/countregister/{day}")
    R registerCount(@PathVariable("day") String day);
}
