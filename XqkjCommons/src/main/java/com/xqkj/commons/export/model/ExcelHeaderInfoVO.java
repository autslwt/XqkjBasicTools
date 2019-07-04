package com.xqkj.commons.export.model;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/10 7:31 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ExcelHeaderInfoVO {

    private ExcelRowInfoVO titleRowInfo;

    private ExcelRowInfoVO headerRowInfo;

    public ExcelRowInfoVO getTitleRowInfo() {
        return titleRowInfo;
    }

    public void setTitleRowInfo(ExcelRowInfoVO titleRowInfo) {
        this.titleRowInfo = titleRowInfo;
    }

    public ExcelRowInfoVO getHeaderRowInfo() {
        return headerRowInfo;
    }

    public void setHeaderRowInfo(ExcelRowInfoVO headerRowInfo) {
        this.headerRowInfo = headerRowInfo;
    }

}
