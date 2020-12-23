package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {

    @PostMapping("login")
    public R login(){
        return  R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606986561521&di=b16146e9faeb50b34dd4df88984b32e8&imgtype=0&src=http%3A%2F%2Fpic3.zhimg.com%2F50%2Fv2-0d0dacca6e9b8fb038abad02812489ee_hd.jpg%3Fsource%3D1940ef5c");
    }

}
