package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.fallback.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    @DeleteMapping("/eduvod/video/deleteAlyVideo/{videoId}")
    R removeVideo(@PathVariable("videoId") String videoId);

    @DeleteMapping("/eduvod/video/delete-batch")
    R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);

}
