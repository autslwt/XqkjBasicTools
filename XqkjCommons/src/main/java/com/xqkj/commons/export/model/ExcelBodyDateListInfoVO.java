package com.xqkj.commons.export.model;

import java.util.List;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/12 10:12 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ExcelBodyDateListInfoVO {

    private int beginRowIndex;

    private List<ExcelRowInfoVO> excelRowInfoVOList;

    public List<ExcelRowInfoVO> getExcelRowInfoVOList() {
        return excelRowInfoVOList;
    }

    public void setExcelRowInfoVOList(List<ExcelRowInfoVO> excelRowInfoVOList) {
        this.excelRowInfoVOList = excelRowInfoVOList;
    }

    public int getBeginRowIndex() {
        return beginRowIndex;
    }

    public void setBeginRowIndex(int beginRowIndex) {
        this.beginRowIndex = beginRowIndex;
    }
}
