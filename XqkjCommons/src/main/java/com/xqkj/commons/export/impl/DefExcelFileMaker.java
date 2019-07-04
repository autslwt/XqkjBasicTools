package com.xqkj.commons.export.impl;

import com.xqkj.commons.export.ExcelFileMaker;
import com.xqkj.commons.export.ExcelFileWriter;
import com.xqkj.commons.export.ExcelRowDataMaker;
import com.xqkj.commons.export.model.ExcelHeaderInfoVO;
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
public class DefExcelFileMaker extends AbstractExcelFileMaker<ExcelFileMaker, AbstractExcelFileMaker.WriteExcelDateParam>
        implements ExcelFileMaker {

    private ExcelFileWriter excelFileWriter;

    @Override
    public <Q extends BasicPageQuery>
    HandleResult<FileMakeResult> makeAndWriteExcelFile(String filePath, String fileName, String extName,
                                                       Q query, ExcelHeaderInfoVO excelHeaderInfoVO,
                                                       ExcelRowDataMaker<Q> excelRowDataMaker,
                                                       ExcelWriteExtInfoVO excelWriteExtInfoVO) {
        //
        WriteExcelDateParam param = new WriteExcelDateParam(filePath, fileName, extName, query,
                excelHeaderInfoVO, excelRowDataMaker, excelWriteExtInfoVO);
        HandleResult<FileMakeResult> handleResult = executeWrite(param,
                (arg, outputStream, workbook) -> {
                    WriteExcelDateParam realArg = (WriteExcelDateParam) arg;
                    return getExcelFileWriter().writeExcelData(outputStream, workbook, "sheet01",
                            arg.query, realArg.excelHeaderInfoVO, realArg.excelRowDataMaker, arg.excelWriteExtInfoVO);
                });
        //
        return handleResult;
    }


    @Override
    public void setExcelFileWriter(ExcelFileWriter excelFileWriter) {
        this.excelFileWriter = excelFileWriter;
    }

    @Override
    public ExcelFileWriter getExcelFileWriter() {
        return excelFileWriter;
    }

}
