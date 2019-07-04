package com.xqkj.commons.event;

import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.export.model.ExcelWriteResult;
import com.xqkj.commons.model.HandleResult;

import java.io.OutputStream;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/15 4:30 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class BasicWriteExcelEvent extends BasicEvent{

    private transient OutputStream outputStream;

    private ExcelWriteExtInfoVO excelWriteExtInfoVO;

    private Object retValue;

    public BasicWriteExcelEvent(){

    }

    public BasicWriteExcelEvent(Object source, Integer eventType,OutputStream outputStream,
                                ExcelWriteExtInfoVO excelWriteExtInfoVO,
                                Object retValue){
        super(source,eventType);
        this.outputStream=outputStream;
        this.excelWriteExtInfoVO=excelWriteExtInfoVO;
        this.retValue=retValue;
    }

    public ExcelWriteExtInfoVO getExcelWriteExtInfoVO() {
        return excelWriteExtInfoVO;
    }

    public void setExcelWriteExtInfoVO(ExcelWriteExtInfoVO excelWriteExtInfoVO) {
        this.excelWriteExtInfoVO = excelWriteExtInfoVO;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Object getRetValue() {
        return retValue;
    }

    public void setRetValue(Object retValue) {
        this.retValue = retValue;
    }
}
