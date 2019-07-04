package com.xqkj.commons.event;

import com.xqkj.commons.export.model.ExcelHeaderInfoVO;
import com.xqkj.commons.export.model.ExcelRowInfoVO;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.export.model.ExcelWriteResult;
import com.xqkj.commons.model.HandleResult;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.OutputStream;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/12 9:56 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class WriteHeaderDateToSheetEvent extends BasicWriteExcelEvent {

    private transient Sheet sheet;
    private ExcelHeaderInfoVO excelHeaderInfoVO;

    public WriteHeaderDateToSheetEvent() {

    }

    public WriteHeaderDateToSheetEvent(Object source, Integer eventType, OutputStream outputStream,
                                       ExcelWriteExtInfoVO excelWriteExtInfoVO, Sheet sheet,
                                       ExcelHeaderInfoVO excelHeaderInfoVO) {
        super(source, eventType, outputStream, excelWriteExtInfoVO,null);
        this.sheet = sheet;
        this.excelHeaderInfoVO = excelHeaderInfoVO;
    }

    public WriteHeaderDateToSheetEvent(Object source, Integer eventType, OutputStream outputStream,
                                       ExcelWriteExtInfoVO excelWriteExtInfoVO, Sheet sheet,
                                       ExcelHeaderInfoVO excelHeaderInfoVO,
                                       Object retValue) {
        super(source, eventType, outputStream, excelWriteExtInfoVO,retValue);
        this.sheet = sheet;
        this.excelHeaderInfoVO = excelHeaderInfoVO;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public ExcelHeaderInfoVO getExcelHeaderInfoVO() {
        return excelHeaderInfoVO;
    }

    public void setExcelHeaderInfoVO(ExcelHeaderInfoVO excelHeaderInfoVO) {
        this.excelHeaderInfoVO = excelHeaderInfoVO;
    }
}
