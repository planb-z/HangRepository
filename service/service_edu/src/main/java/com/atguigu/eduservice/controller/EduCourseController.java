package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-12-04
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    //添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        try {
            String courseId = courseService.saveCourseInfo(courseInfoVo);
            return R.ok().data("courseId", courseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok();
    }

    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfo", courseInfo);
    }

    @PostMapping("updateCourse")
    public R updateCourse(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    @GetMapping("getPulishCourseInfo/{courseId}")
    public R getPulishCourseInfo(@PathVariable String courseId) {
        CoursePublishVo coursePublishInfo = courseService.getCoursePublishInfo(courseId);
        return R.ok().data("publishCourse", coursePublishInfo);
    }

    @PostMapping("publihCourse/{id}")
    public R publihCourse(@PathVariable String id) {
        Boolean result = courseService.pulishCourse(id);
        return result ? R.ok() : R.error();
    }

    @PostMapping("getListCourse/{current}/{limit}")
    public R getListCourse(@PathVariable Long current,@PathVariable Long limit,@RequestBody EduCourse eduCourse){
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(eduCourse.getTitle())){
            wrapper.like("title",eduCourse.getTitle());
        }
        if(!StringUtils.isEmpty(eduCourse.getStatus())){
            wrapper.eq("status",eduCourse.getStatus());
        }
        Page<EduCourse> page = new Page<>(current,limit);
        page.setDesc("gmt_create");
        courseService.page(page,wrapper);
        return R.ok().data("rows",page.getRecords()).data("total", page.getTotal());
    }

    @DeleteMapping("deleteCourse/{courseId}")
    public R getListCourse(@PathVariable String courseId){
       courseService.removeCourse(courseId);
       return R.ok();
    }

}

