package com.xqkj.commons.test.model;

import com.xqkj.commons.anno.ExcelCellInforAnno;
import com.xqkj.commons.progress.model.RunInforModel;

/**
 * @author lwt<br>
 * @description <br>
 * @date 2019/5/26
 */
public class TestModel {

    @ExcelCellInforAnno(header = "主键",index = 1)
    private Integer id;
    @ExcelCellInforAnno(header = "姓名",index = 2)
    private String name;
    @ExcelCellInforAnno(header = "备注",index = 3)
    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public static class RunInforModelExt extends RunInforModel{
        public int test;
    }

}
