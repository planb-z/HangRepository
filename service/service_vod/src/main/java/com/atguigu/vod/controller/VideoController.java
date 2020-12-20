package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.vod.utils.ConstantProperties;
import com.atguigu.vod.utils.InitVodCilent;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api(description = "阿里云视频点播服务")
@CrossOrigin
@RestController
@RequestMapping("vod/video")
public class VideoController {


        @GetMapping("get-play-auth/{videoId}")
        public R getVideoPlayAuth(@PathVariable String videoId)throws Exception{

            //初始化
            DefaultAcsClient client = InitVodCilent.initVodClient(ConstantProperties.ACCESS_KEY_ID, ConstantProperties.ACCESS_KEY_SECRET);


            //请求
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);

            //响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            //得到播放凭证
            String playAuth = response.getPlayAuth();

            return R.ok().message("获取凭证成功").data("playAuth", playAuth);
        }
}
