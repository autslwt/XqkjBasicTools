package com.xqkj.commons.event;

import com.xqkj.commons.export.model.ExcelRowCellInfoVO;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.export.model.ExcelWriteResult;
import com.xqkj.commons.model.HandleResult;
import org.apache.poi.ss.usermodel.Cell;

import java.io.OutputStream;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/1 3:50 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class WriteDateToCellEvent extends BasicWriteExcelEvent{

    private transient Cell cell;

    private ExcelRowCellInfoVO excelRowCellInfoVO;

    public WriteDateToCellEvent(){

    }

    public WriteDateToCellEvent(Object source, Integer eventType,OutputStream outputStream,
                                ExcelWriteExtInfoVO excelWriteExtInfoVO,Cell cell,
                                ExcelRowCellInfoVO excelRowCellInfoVO){
        super(source,eventType,outputStream,excelWriteExtInfoVO,null);
        this.cell=cell;
        this.excelRowCellInfoVO=excelRowCellInfoVO;
    }

    public WriteDateToCellEvent(Object source, Integer eventType,OutputStream outputStream,
                                ExcelWriteExtInfoVO excelWriteExtInfoVO,Cell cell,
                                ExcelRowCellInfoVO excelRowCellInfoVO,
                                Object retValue){
        super(source,eventType,outputStream,excelWriteExtInfoVO,retValue);
        this.cell=cell;
        this.excelRowCellInfoVO=excelRowCellInfoVO;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public ExcelRowCellInfoVO getExcelRowCellInfoVO() {
        return excelRowCellInfoVO;
    }

    public void setExcelRowCellInfoVO(ExcelRowCellInfoVO excelRowCellInfoVO) {
        this.excelRowCellInfoVO = excelRowCellInfoVO;
    }
}
