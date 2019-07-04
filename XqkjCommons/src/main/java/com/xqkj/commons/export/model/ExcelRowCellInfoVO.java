package com.xqkj.commons.export.model;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/10 7:30 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ExcelRowCellInfoVO {

    private int currentCellIndex;

    private Object value;

    private String valueType;

    private String valueFormat;

    private Integer cellWidth;


    public int getCurrentCellIndex() {
        return currentCellIndex;
    }

    public void setCurrentCellIndex(int currentCellIndex) {
        this.currentCellIndex = currentCellIndex;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getValueFormat() {
        return valueFormat;
    }

    public void setValueFormat(String valueFormat) {
        this.valueFormat = valueFormat;
    }

    public Integer getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(Integer cellWidth) {
        this.cellWidth = cellWidth;
    }
}
