package com.xqkj.commons.export.model;

import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/10 7:31 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ExcelRowInfoVO {

    private int currentRowIndex;

    private List<ExcelRowCellInfoVO> cellInfoVOList;

    private List<CellRangeAddress> mergeInfoVOList;

    public int getCurrentRowIndex() {
        return currentRowIndex;
    }

    public void setCurrentRowIndex(int currentRowIndex) {
        this.currentRowIndex = currentRowIndex;
    }

    public List<ExcelRowCellInfoVO> getCellInfoVOList() {
        return cellInfoVOList;
    }

    public void setCellInfoVOList(List<ExcelRowCellInfoVO> cellInfoVOList) {
        this.cellInfoVOList = cellInfoVOList;
    }

    public List<CellRangeAddress> getMergeInfoVOList() {
        return mergeInfoVOList;
    }

    public void setMergeInfoVOList(List<CellRangeAddress> mergeInfoVOList) {
        this.mergeInfoVOList = mergeInfoVOList;
    }
}
