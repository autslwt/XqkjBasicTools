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
    @ExcelCellInforAnno(cellCode = "testForCode",header = "code测试",index = 4)
    private String testForCode;
    @ExcelCellInforAnno(cellCode = "showForCode",header = "code测试-2",index = 5)
    private String showForCode;


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

    public String getTestForCode() {
        return testForCode;
    }

    public void setTestForCode(String testForCode) {
        this.testForCode = testForCode;
    }

    public String getShowForCode() {
        return showForCode;
    }

    public void setShowForCode(String showForCode) {
        this.showForCode = showForCode;
    }
}
