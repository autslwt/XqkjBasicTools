package com.xqkj.commons.export.impl;

import com.xqkj.commons.export.AnnoDataExcelFileMaker;
import com.xqkj.commons.export.AnnoDataExcelFileWriter;
import com.xqkj.commons.export.PageListDateMaker;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.export.model.FileMakeResult;
import com.xqkj.commons.model.BasicPageQuery;
import com.xqkj.commons.model.HandleResult;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/27 1:57 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class DefAnnoDataExcelFileMaker extends AbstractExcelFileMaker<AnnoDataExcelFileMaker, AbstractExcelFileMaker.WriteAnnoExcelDateParam>
        implements AnnoDataExcelFileMaker {

    private AnnoDataExcelFileWriter annoDataExcelFileWriter;

    @Override
    public <T, Q extends BasicPageQuery>
    HandleResult<FileMakeResult> makeAndWriteAnnoExcelFile(String filePath, String fileName, String extName,
                                                           Q query, Class<T> dateType,
                                                           PageListDateMaker<T, Q> pageListDateMaker,
                                                           ExcelWriteExtInfoVO excelWriteExtInfoVO) {
        //
        WriteAnnoExcelDateParam param = new WriteAnnoExcelDateParam(filePath, fileName, extName, query,
                dateType, pageListDateMaker, excelWriteExtInfoVO);
        HandleResult<FileMakeResult> handleResult = executeWrite(param,
                (arg, outputStream, workbook) -> {
                    WriteAnnoExcelDateParam realArg = (WriteAnnoExcelDateParam) arg;
                    return getAnnoDataExcelFileWriter().writeExcelAnnoData(outputStream, workbook, "sheet01",
                            arg.query, realArg.dateType, realArg.pageListDateMaker, arg.excelWriteExtInfoVO);
                });
        //
        return handleResult;
    }

    @Override
    public void setAnnoDataExcelFileWriter(AnnoDataExcelFileWriter annoDataExcelFileWriter) {
        this.annoDataExcelFileWriter = annoDataExcelFileWriter;
    }

    @Override
    public AnnoDataExcelFileWriter getAnnoDataExcelFileWriter() {
        return annoDataExcelFileWriter;
    }

}
