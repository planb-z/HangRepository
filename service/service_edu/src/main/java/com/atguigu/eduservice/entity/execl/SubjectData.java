package com.atguigu.eduservice.entity.execl;

import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;
@Data
public class SubjectData {

    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
