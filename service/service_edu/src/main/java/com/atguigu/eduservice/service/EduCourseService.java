package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQueryVo;
import com.atguigu.eduservice.entity.vo.CourseWebVo;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-12-04
 */
public interface EduCourseService extends IService<EduCourse> {
    String saveCourseInfo(CourseInfoVo courseInfoVo) throws GuliException;

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getCoursePublishInfo(String courseId);

    Boolean pulishCourse(String id);

    void removeCourse(String courseId);

    List<EduCourse> selectByTeacherId(String teacherId);

    Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseQueryVo courseQuery);

    CourseWebVo selectInfoWebById(String id);

    void updatePageViewCount(String id);
}
