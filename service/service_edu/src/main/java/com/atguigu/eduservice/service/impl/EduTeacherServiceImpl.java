package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.query.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-11-29
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");

        if(teacherQuery == null){
            baseMapper.selectPage(pageParam,queryWrapper);
            return;
        }

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        Date begin = teacherQuery.getBegin();
        Date end = teacherQuery.getEnd();
       if(!StringUtils.isEmpty(name)){
           queryWrapper.like("name",name);
       }
       if(!StringUtils.isEmpty(level)){
           queryWrapper.eq("level",level);
       }
       if(!StringUtils.isEmpty(begin)){
           queryWrapper.ge("gmt_create",begin);
       }
       if(!StringUtils.isEmpty(end)){
           queryWrapper.eq("gmt_create",end);
       }

       baseMapper.selectPage(pageParam,queryWrapper);
    }

    @Override
    public Map<String, Object> pageListWeb(Page<EduTeacher> pageParam) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        baseMapper.selectPage(pageParam,wrapper);
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();
        long current = pageParam.getCurrent();
        long size = pageParam.getSize();
        long pages = pageParam.getPages();
        Map<String,Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }
}
