package com.xqkj.commons.event;

import com.xqkj.commons.export.model.ExcelHeaderInfoVO;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.export.model.ExcelWriteResult;
import com.xqkj.commons.model.BasicPageQuery;
import com.xqkj.commons.model.HandleResult;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.OutputStream;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/12 9:58 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class WriteDateToExcelEvent extends BasicWriteExcelEvent{



    private transient Workbook workbook;
    private String sheetName;
    private BasicPageQuery query;
    private ExcelHeaderInfoVO excelHeaderInfoVO;

    public WriteDateToExcelEvent(){

    }

    public WriteDateToExcelEvent(Object source, Integer eventType,OutputStream outputStream,
                                 ExcelWriteExtInfoVO excelWriteExtInfoVO,Workbook workbook,
                                 String sheetName,BasicPageQuery query,ExcelHeaderInfoVO excelHeaderInfoVO){
        super(source,eventType,outputStream,excelWriteExtInfoVO,null);
        this.workbook=workbook;
        this.sheetName=sheetName;
        this.query=query;
        this.excelHeaderInfoVO=excelHeaderInfoVO;
    }

    public WriteDateToExcelEvent(Object source, Integer eventType,OutputStream outputStream,
                                 ExcelWriteExtInfoVO excelWriteExtInfoVO,Workbook workbook,
                                 String sheetName,BasicPageQuery query,ExcelHeaderInfoVO excelHeaderInfoVO,
                                 Object retValue){
        super(source,eventType,outputStream,excelWriteExtInfoVO,retValue);
        this.workbook=workbook;
        this.sheetName=sheetName;
        this.query=query;
        this.excelHeaderInfoVO=excelHeaderInfoVO;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public BasicPageQuery getQuery() {
        return query;
    }

    public void setQuery(BasicPageQuery query) {
        this.query = query;
    }

    public ExcelHeaderInfoVO getExcelHeaderInfoVO() {
        return excelHeaderInfoVO;
    }

    public void setExcelHeaderInfoVO(ExcelHeaderInfoVO excelHeaderInfoVO) {
        this.excelHeaderInfoVO = excelHeaderInfoVO;
    }
}
