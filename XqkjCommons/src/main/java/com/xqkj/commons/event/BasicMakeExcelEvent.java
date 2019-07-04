package com.xqkj.commons.event;

import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.export.model.FileMakeResult;
import com.xqkj.commons.model.BasicPageQuery;
import com.xqkj.commons.model.HandleResult;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/15 4:30 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class BasicMakeExcelEvent extends BasicEvent{

    private String filePath;
    private String fileName;
    private String extName;
    private BasicPageQuery query;

    private ExcelWriteExtInfoVO excelWriteExtInfoVO;

    private Object retValue;

    public BasicMakeExcelEvent(){

    }

    public BasicMakeExcelEvent(Object source, Integer eventType,
                               String filePath,String fileName,String extName,
                               BasicPageQuery query, ExcelWriteExtInfoVO excelWriteExtInfoVO,
                               Object retValue){
        super(source,eventType);
        this.filePath=filePath;
        this.fileName=fileName;
        this.extName=extName;
        this.query=query;
        this.excelWriteExtInfoVO=excelWriteExtInfoVO;
        this.retValue=retValue;
    }

    public ExcelWriteExtInfoVO getExcelWriteExtInfoVO() {
        return excelWriteExtInfoVO;
    }

    public void setExcelWriteExtInfoVO(ExcelWriteExtInfoVO excelWriteExtInfoVO) {
        this.excelWriteExtInfoVO = excelWriteExtInfoVO;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public BasicPageQuery getQuery() {
        return query;
    }

    public void setQuery(BasicPageQuery query) {
        this.query = query;
    }

    public Object getRetValue() {
        return retValue;
    }

    public void setRetValue(Object retValue) {
        this.retValue = retValue;
    }
}
