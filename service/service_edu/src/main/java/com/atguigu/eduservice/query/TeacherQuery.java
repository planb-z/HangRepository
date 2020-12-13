package com.atguigu.eduservice.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TeacherQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("教师名称 模糊查询")
    private String name;

    @ApiModelProperty("头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间",example = "2020-11-30 00:00:00")
    private Date begin;

    @ApiModelProperty(value = "查询结束时间",example = "2020-11-30 00:00:00")
    private Date end;

}
