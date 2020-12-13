package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.execl.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

public class SubjectListener extends AnalysisEventListener<SubjectData> {

    private EduSubjectService subjectService;

    public SubjectListener(EduSubjectService eduSubjectService){
        this.subjectService = eduSubjectService;
    }


    public SubjectListener(){

    }

    @SneakyThrows
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null){
            throw new GuliException(20001,"execl 数据为空");
        }
        //一行一行读取，每次读取有两个值，第一个值一级分类，第二个值二级分类
        EduSubject existOne = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if(existOne == null){
            existOne = new EduSubject();
            existOne.setTitle(subjectData.getOneSubjectName());
            existOne.setParentId("0");
            subjectService.save(existOne);
        }
        String pid = existOne.getId();
        EduSubject existTwo = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(),pid);
        if(existTwo == null){
            existTwo  = new EduSubject();
            existTwo.setTitle(subjectData.getTwoSubjectName());
            existTwo.setParentId(existOne.getId());
            subjectService.save(existTwo);
        }
    }

    private EduSubject existOneSubject(EduSubjectService service,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        return service.getOne(wrapper);
    }

    private EduSubject existTwoSubject(EduSubjectService service,String name,String parentId){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",parentId);
        return service.getOne(wrapper);
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
