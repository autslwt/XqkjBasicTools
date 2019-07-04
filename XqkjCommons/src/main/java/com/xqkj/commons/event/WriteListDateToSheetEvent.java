package com.xqkj.commons.event;

import com.xqkj.commons.export.model.ExcelBodyDateListInfoVO;
import com.xqkj.commons.export.model.ExcelHeaderInfoVO;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.export.model.ExcelWriteResult;
import com.xqkj.commons.model.HandleResult;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.OutputStream;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/12 9:55 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class WriteListDateToSheetEvent extends BasicWriteExcelEvent {

    private transient Sheet sheet;
    private ExcelBodyDateListInfoVO excelBodyDateListInfoVO;

    public WriteListDateToSheetEvent() {

    }

    public WriteListDateToSheetEvent(Object source, Integer eventType, OutputStream outputStream,
                                     ExcelWriteExtInfoVO excelWriteExtInfoVO, Sheet sheet,
                                     ExcelBodyDateListInfoVO excelBodyDateListInfoVO) {
        super(source, eventType, outputStream, excelWriteExtInfoVO,null);
        this.sheet = sheet;
        this.excelBodyDateListInfoVO = excelBodyDateListInfoVO;
    }

    public WriteListDateToSheetEvent(Object source, Integer eventType, OutputStream outputStream,
                                     ExcelWriteExtInfoVO excelWriteExtInfoVO, Sheet sheet,
                                     ExcelBodyDateListInfoVO excelBodyDateListInfoVO,
                                     Object retValue) {
        super(source, eventType, outputStream, excelWriteExtInfoVO,retValue);
        this.sheet = sheet;
        this.excelBodyDateListInfoVO = excelBodyDateListInfoVO;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public ExcelBodyDateListInfoVO getExcelBodyDateListInfoVO() {
        return excelBodyDateListInfoVO;
    }

    public void setExcelBodyDateListInfoVO(ExcelBodyDateListInfoVO excelBodyDateListInfoVO) {
        this.excelBodyDateListInfoVO = excelBodyDateListInfoVO;
    }
}
