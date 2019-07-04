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
public class MakeAndWriteExcelFileEvent extends BasicMakeExcelEvent{

    private ExcelHeaderInfoVO excelHeaderInfoVO;

    public MakeAndWriteExcelFileEvent(){

    }

    public MakeAndWriteExcelFileEvent(Object source, Integer eventType,
                                      String filePath, String fileName, String extName,
                                      BasicPageQuery query, ExcelWriteExtInfoVO excelWriteExtInfoVO,
                                      ExcelHeaderInfoVO excelHeaderInfoVO){
        super(source,eventType,filePath,fileName,extName,query,excelWriteExtInfoVO,null);
        this.excelHeaderInfoVO=excelHeaderInfoVO;
    }

    public MakeAndWriteExcelFileEvent(Object source, Integer eventType,
                                      String filePath, String fileName, String extName,
                                      BasicPageQuery query, ExcelWriteExtInfoVO excelWriteExtInfoVO,
                                      ExcelHeaderInfoVO excelHeaderInfoVO,
                                      Object retValue){
        super(source,eventType,filePath,fileName,extName,query,excelWriteExtInfoVO,retValue);
        this.excelHeaderInfoVO=excelHeaderInfoVO;
    }

    public ExcelHeaderInfoVO getExcelHeaderInfoVO() {
        return excelHeaderInfoVO;
    }

    public void setExcelHeaderInfoVO(ExcelHeaderInfoVO excelHeaderInfoVO) {
        this.excelHeaderInfoVO = excelHeaderInfoVO;
    }
}
