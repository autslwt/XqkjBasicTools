package com.xqkj.commons.export;

import com.xqkj.annotation.MethodInteceptAnno;
import com.xqkj.commons.constant.IntecepterManagerNames;
import com.xqkj.commons.export.model.ExcelHeaderInfoVO;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.export.model.FileMakeResult;
import com.xqkj.commons.model.BasicPageQuery;
import com.xqkj.commons.model.HandleResult;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/27 1:48 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public interface ExcelFileMaker {

    /**
     *
     * @param filePath
     * @param fileName
     * @param extName
     * @param query
     * @param excelHeaderInfoVO
     * @param excelRowDataMaker
     * @param excelWriteExtInfoVO
     * @return
     */
    @MethodInteceptAnno(managerName = IntecepterManagerNames.ExcelFileMaker_MakeAndWriteExcelFile,
            argsNamesHolderClass = "com.xqkj.methodargs.ExcelFileMaker_makeAndWriteExcelFile")
    <Q extends BasicPageQuery>
    HandleResult<FileMakeResult> makeAndWriteExcelFile(String filePath, String fileName, String extName,
                                                       Q query, ExcelHeaderInfoVO excelHeaderInfoVO,
                                                       ExcelRowDataMaker<Q> excelRowDataMaker,
                                                       ExcelWriteExtInfoVO excelWriteExtInfoVO);



    void setExcelFileWriter(ExcelFileWriter ExcelFileWriter);

    ExcelFileWriter getExcelFileWriter();


}
