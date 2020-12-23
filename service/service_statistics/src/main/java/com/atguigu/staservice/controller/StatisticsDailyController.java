package com.atguigu.staservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.staservice.entity.StatisticsDaily;
import com.atguigu.staservice.service.StatisticsDailyService;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-12-20
 */
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService dailyService;

    @PostMapping("daily/{day}")
    public R createStatisticsByDate(@PathVariable String day) {
        dailyService.createStatisticsByDay(day);
        return R.ok();
    }

    @GetMapping("show-chart/{type}/{begin}/{end}")
    public R showChart(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String, Object> map = dailyService.getChartData(type,begin, end);
        return R.ok().data(map);
    }

}

