package com.xqkj.commons.export;

import com.xqkj.annotation.MethodInteceptAnno;
import com.xqkj.commons.constant.IntecepterManagerNames;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.export.model.FileMakeResult;
import com.xqkj.commons.model.BasicPageQuery;
import com.xqkj.commons.model.HandleResult;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/29 11:16 AM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public interface AnnoDataExcelFileMaker {

    /**
     *
     * @param filePath
     * @param fileName
     * @param extName
     * @param query
     * @param dateType
     * @param pageListDateMaker
     * @param excelWriteExtInfoVO
     * @return
     */
    @MethodInteceptAnno(managerName = IntecepterManagerNames.AnnoDataExcelFileMaker_MakeAndWriteAnnoExcelFile,
            argsNamesHolderClass = "com.xqkj.methodargs.AnnoDataExcelFileMaker_makeAndWriteAnnoExcelFile")
    <T,Q extends BasicPageQuery>
    HandleResult<FileMakeResult> makeAndWriteAnnoExcelFile(String filePath, String fileName, String extName,
                                                           Q query, Class<T> dateType,
                                                           PageListDateMaker<T,Q> pageListDateMaker,
                                                           ExcelWriteExtInfoVO excelWriteExtInfoVO);

    void setAnnoDataExcelFileWriter(AnnoDataExcelFileWriter annoDataExcelFileWriter);

    AnnoDataExcelFileWriter getAnnoDataExcelFileWriter();
}
