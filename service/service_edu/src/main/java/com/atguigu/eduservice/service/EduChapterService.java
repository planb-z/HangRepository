package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-12-04
 */
public interface EduChapterService extends IService<EduChapter> {
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    Boolean deleteChapter(String chapterId) throws GuliException;

    void removeByCourseId(String courseId);
}
