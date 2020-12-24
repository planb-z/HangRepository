package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.query.TeacherQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-11-29
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    //1 查询讲师表所有数据
    @GetMapping("findAll")
    public R findAllTeacher() {
        return R.ok().data("items", teacherService.list(null));

    }

    //根据id删除  restful
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id) {
        boolean result = teacherService.removeById(id);
        if (result) {
            return R.ok();
        }
        return R.error();
    }

    //根据id查看 restful
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
            return R.ok().data("teacher",teacherService.getById(id));
    }

    //根据id查看 restful
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        teacherService.save(eduTeacher);
            return R.ok();
    }

    //分页
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current, @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        teacherService.page(pageTeacher, null);
        return R.ok().data("rows", pageTeacher.getRecords());
    }

    @PostMapping("pageTeacherCondition/{page}/{limit}")
    public R pageTeacherCondition(@PathVariable Long page, @PathVariable Long limit, TeacherQuery teacherQuery) {
        Page<EduTeacher> pageParam = new Page<>(page, limit);
        teacherService.pageQuery(pageParam, teacherQuery);
        return R.ok().data("rows", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping(value = "{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){

        //查询讲师信息
        EduTeacher teacher = teacherService.getById(id);

        //根据讲师id查询这个讲师的课程列表
        List<EduCourse> courseList = courseService.selectByTeacherId(id);

        return R.ok().data("teacher", teacher).data("courseList", courseList);
    }

    @ApiOperation("修改讲师")
    @PostMapping("updateTeacher")
    public R updateById(@RequestBody EduTeacher teacher){
        teacherService.updateById(teacher);
        return  R.ok();
    }

    @ApiOperation("测试抛出异常")
    @GetMapping("testError")
    public void testError() throws GuliException {
//        int i = 10 / 0;
        try{
            int i = 10 / 0;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"Guli出现自定义异常");
        }
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping(value = "{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){

        Page<EduTeacher> pageParam = new Page<>(page, limit);
        Map<String, Object> map = teacherService.pageListWeb(pageParam);
        return  R.ok().data(map);
    }


}

