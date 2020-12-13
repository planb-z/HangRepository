package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-12-04
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduVideoService videoService;


    @Autowired
    private VodClient vodClient;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) throws GuliException {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (0 == insert) {
            throw new GuliException(20001, "添加课程信息失败");
        }
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(eduCourse.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.save(courseDescription);
        return eduCourse.getId();
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        baseMapper.updateById(eduCourse);
        courseDescriptionService.updateById(description);
    }

    @Override
    public CoursePublishVo getCoursePublishInfo(String courseId) {
        return baseMapper.getPublishCourseInfo(courseId);
    }

    @Override
    public Boolean pulishCourse(String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        int i = baseMapper.updateById(eduCourse);
        return i > 0;
    }

    @Override
    public void removeCourse(String courseId) {

        //根据课程id删除小节
        videoService.removeByCourseId(courseId);

        //根据课程id删除章节
        chapterService.removeByCourseId(courseId);

        //根据课程id删除描述
        courseDescriptionService.removeById(courseId);

        //根据课程id删除课程本身
        baseMapper.deleteById(courseId);

    }
}
