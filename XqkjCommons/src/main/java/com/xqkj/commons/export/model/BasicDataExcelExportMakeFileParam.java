package com.xqkj.commons.export.model;

import com.xqkj.commons.model.BasicPageQuery;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/2 8:10 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class BasicDataExcelExportMakeFileParam {

    public String filePath;
    public String fileName;
    public String extName;
    public BasicPageQuery query;
    public ExcelWriteExtInfoVO excelWriteExtInfoVO;

    public BasicDataExcelExportMakeFileParam(String filePath,String fileName,String extName,
                                             BasicPageQuery query,ExcelWriteExtInfoVO excelWriteExtInfoVO){
        this.fileName=fileName;
        this.filePath=filePath;
        this.extName=extName;
        this.query=query;
        this.excelWriteExtInfoVO=excelWriteExtInfoVO;
    }
}
