package com.xqkj.commons.export.model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/11 5:45 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ExcelWriteExtInfoVO {

    private String fileAlisName;

    private String parentType;

    private String exportType;
    /**
     * 表示某次倒出的唯一值
     */
    private String uid= UUID.randomUUID().toString().replace("-","");

    private Integer dateSumAmount;

    private Integer hasHandledAmount=0;
    /**
     * 生成的excel文件最大行数--包括表头
     */
    private Integer excelMaxRow;
    /**
     * 每个excel文件写入时的初始数据列表--一般生成多文件时使用
     */
    private List<ExcelRowInfoVO> initRowDateList;
    /**
     * 是否生成多文件，并压缩称压缩包
     */
    private boolean zipAble;
    /**
     * 表头写实现类的注册名称--必须使用componentHoldHelper可以获取--默认使用ExcelProxFactory的注册方法注册
     */
    private String excelHeaderRowWriterName;
    /**
     * 单元格数据写实现类的注册名称--必须使用componentHoldHelper可以获取--默认使用ExcelProxFactory的注册方法注册
     */
    private String excelRowCellWriterName;

    private Map<String,Object> extMap;

    public Map<String, Object> getExtMap() {
        return extMap;
    }

    public void setExtMap(Map<String, Object> extMap) {
        this.extMap = extMap;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getDateSumAmount() {
        return dateSumAmount;
    }

    public void setDateSumAmount(Integer dateSumAmount) {
        this.dateSumAmount = dateSumAmount;
    }

    public Integer getHasHandledAmount() {
        return hasHandledAmount;
    }

    public void setHasHandledAmount(Integer hasHandledAmount) {
        this.hasHandledAmount = hasHandledAmount;
    }

    public Integer getExcelMaxRow() {
        return excelMaxRow;
    }

    public void setExcelMaxRow(Integer excelMaxRow) {
        this.excelMaxRow = excelMaxRow;
    }

    public List<ExcelRowInfoVO> getInitRowDateList() {
        return initRowDateList;
    }

    public void setInitRowDateList(List<ExcelRowInfoVO> initRowDateList) {
        this.initRowDateList = initRowDateList;
    }

    public void incrHasHandledAmount(int step){
        hasHandledAmount+=step;
    }

    public boolean isZipAble() {
        return zipAble;
    }

    public void setZipAble(boolean zipAble) {
        this.zipAble = zipAble;
    }

    public String getExcelHeaderRowWriterName() {
        return excelHeaderRowWriterName;
    }

    public void setExcelHeaderRowWriterName(String excelHeaderRowWriterName) {
        this.excelHeaderRowWriterName = excelHeaderRowWriterName;
    }

    public String getExcelRowCellWriterName() {
        return excelRowCellWriterName;
    }

    public void setExcelRowCellWriterName(String excelRowCellWriterName) {
        this.excelRowCellWriterName = excelRowCellWriterName;
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public String getFileAlisName() {
        return fileAlisName;
    }

    public void setFileAlisName(String fileAlisName) {
        this.fileAlisName = fileAlisName;
    }
}
