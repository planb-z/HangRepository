package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQueryVo;
import com.atguigu.eduservice.entity.vo.CourseWebVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private EduChapterService chapterService;
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

    @ApiOperation(value = "分页课程列表")
    @PostMapping(value = "{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseQueryVo courseQuery){
        Page<EduCourse> pageParam = new Page<EduCourse>(page, limit);
        Map<String, Object> map = courseService.pageListWeb(pageParam, courseQuery);
        return  R.ok().data(map);
    }



    @ApiOperation(value = "根据ID查询课程")
    @GetMapping(value = "{courseId}")
    public R getById(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId){

        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.selectInfoWebById(courseId);

        //查询当前课程的章节信息
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoByCourseId(courseId);

        return R.ok().data("course", courseWebVo).data("chapterVoList", chapterVoList);
    }

}

