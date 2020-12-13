package com.atguigu.educms.controller;

import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {


    @Autowired
    private CrmBannerService bannerService;


    @ApiOperation(value = "获取首页banner")
    @GetMapping("getAllBanner")
    @Cacheable(value="banner",key="'index'")
    public R index() {
        List<CrmBanner> list = bannerService.list(null);
        return R.ok().data("bannerList", list);
    }

}
