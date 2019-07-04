package com.xqkj.commons.event;


import com.xqkj.commons.export.model.ExcelHeaderInfoVO;
import com.xqkj.commons.export.model.ExcelRowInfoVO;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.export.model.ExcelWriteResult;
import com.xqkj.commons.model.BasicPageQuery;
import com.xqkj.commons.model.HandleResult;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.OutputStream;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/11 5:51 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class WriteDateToRowEvent extends BasicWriteExcelEvent{

    private transient Row row;
    private ExcelRowInfoVO excelRowInfoVO;

    public WriteDateToRowEvent(){

    }

    public WriteDateToRowEvent(Object source, Integer eventType, OutputStream outputStream,
                               ExcelWriteExtInfoVO excelWriteExtInfoVO, Row row,
                               ExcelRowInfoVO excelRowInfoVO){
        super(source,eventType,outputStream,excelWriteExtInfoVO,null);
        this.row=row;
        this.excelRowInfoVO=excelRowInfoVO;
    }

    public WriteDateToRowEvent(Object source, Integer eventType, OutputStream outputStream,
                               ExcelWriteExtInfoVO excelWriteExtInfoVO, Row row,
                               ExcelRowInfoVO excelRowInfoVO,
                               Object retValue){
        super(source,eventType,outputStream,excelWriteExtInfoVO,retValue);
        this.row=row;
        this.excelRowInfoVO=excelRowInfoVO;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public ExcelRowInfoVO getExcelRowInfoVO() {
        return excelRowInfoVO;
    }

    public void setExcelRowInfoVO(ExcelRowInfoVO excelRowInfoVO) {
        this.excelRowInfoVO = excelRowInfoVO;
    }
}
