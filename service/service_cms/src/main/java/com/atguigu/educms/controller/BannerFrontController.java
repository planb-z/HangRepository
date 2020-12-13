package com.atguigu.educms.controller;

import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
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


    //查询所有幻灯片
    @GetMapping("getAllBanner")
    public R getAllBanner(){
       List<CrmBanner> crmBanners =  bannerService.selectAllBanner();
        return  R.ok().data("list",crmBanners);
    }

}
