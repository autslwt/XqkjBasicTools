package com.xqkj.commons.export.model;

import java.util.List;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/11 2:15 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ExcelWriteResult {

    private List<ExcelRowInfoVO> notWriteDateList;

    private int nextRowIndex;

    public int getNextRowIndex() {
        return nextRowIndex;
    }

    public void setNextRowIndex(int nextRowIndex) {
        this.nextRowIndex = nextRowIndex;
    }

    public List<ExcelRowInfoVO> getNotWriteDateList() {
        return notWriteDateList;
    }

    public void setNotWriteDateList(List<ExcelRowInfoVO> notWriteDateList) {
        this.notWriteDateList = notWriteDateList;
    }
}
