package com.atguigu.vod.controller;

import com.atguigu.commonutils.R;
import com.atguigu.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file) {
        String videoId = vodService.uploadAlyVideo(file);
        return R.ok().data("videoId", videoId);
    }

    @DeleteMapping("deleteAlyVideo/{videoId}")
    public R deleteAlyVideo(@PathVariable String videoId) {
        vodService.deleteAlyVideo(videoId);
        return R.ok();
    }

    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }
}
