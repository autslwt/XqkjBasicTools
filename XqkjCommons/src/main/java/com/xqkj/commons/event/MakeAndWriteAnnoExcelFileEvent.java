package com.xqkj.commons.event;

import com.xqkj.commons.export.model.ExcelHeaderInfoVO;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.export.model.FileMakeResult;
import com.xqkj.commons.model.BasicPageQuery;
import com.xqkj.commons.model.HandleResult;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/27 10:31 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class MakeAndWriteAnnoExcelFileEvent extends BasicMakeExcelEvent{

    private transient Class dateType;

    public MakeAndWriteAnnoExcelFileEvent(){

    }

    public MakeAndWriteAnnoExcelFileEvent(Object source, Integer eventType,
                                          String filePath, String fileName, String extName,
                                          BasicPageQuery query, ExcelWriteExtInfoVO excelWriteExtInfoVO,
                                          Class dateType){
        super(source,eventType,filePath,fileName,extName,query,excelWriteExtInfoVO,null);
        this.dateType=dateType;
    }

    public MakeAndWriteAnnoExcelFileEvent(Object source, Integer eventType,
                                          String filePath, String fileName, String extName,
                                          BasicPageQuery query, ExcelWriteExtInfoVO excelWriteExtInfoVO,
                                          Class dateType,Object retValue){
        super(source,eventType,filePath,fileName,extName,query,excelWriteExtInfoVO,retValue);
        this.dateType=dateType;
    }

    public Class getDateType() {
        return dateType;
    }

    public void setDateType(Class dateType) {
        this.dateType = dateType;
    }
}
