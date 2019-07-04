package com.xqkj.commons.export.model;

import com.xqkj.commons.model.BasicPageQuery;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/2 2:49 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class BasicWriteExcelDateParam {

    public String filePath;
    public String fileName;
    public String extName;
    public BasicPageQuery query;
    public ExcelWriteExtInfoVO excelWriteExtInfoVO;

    public BasicWriteExcelDateParam() {

    }

    public BasicWriteExcelDateParam(String filePath, String fileName, String extName,
                                    BasicPageQuery query, ExcelWriteExtInfoVO excelWriteExtInfoVO) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.extName = extName;
        this.query = query;
        this.excelWriteExtInfoVO = excelWriteExtInfoVO;
    }


}
