package com.xqkj.commons.export;

import com.xqkj.annotation.MethodInteceptAnno;
import com.xqkj.commons.constant.IntecepterManagerNames;
import com.xqkj.commons.export.model.*;
import com.xqkj.commons.basic.ComponentHolder;
import com.xqkj.commons.model.BasicPageQuery;
import com.xqkj.commons.model.HandleResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.OutputStream;

/**
 * 负责excel文件的写入
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/26 11:01 AM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public interface ExcelFileWriter {

    /**
     *
     * @param outputStream
     * @param workbook
     * @param sheetName
     * @param excelHeaderInfoVO
     * @param excelRowDataMaker
     * @param excelWriteExtInfoVO
     * @return
     */
    @MethodInteceptAnno(managerName= IntecepterManagerNames.ExcelFileWriter_WriteExcelData,
            argsNamesHolderClass = "com.xqkj.methodargs.ExcelFileWriter_writeExcelData")
    <Q extends BasicPageQuery>
    HandleResult<ExcelWriteResult> writeExcelData(OutputStream outputStream,
                                                  Workbook workbook, String sheetName,
                                                  Q query, ExcelHeaderInfoVO excelHeaderInfoVO,
                                                  ExcelRowDataMaker<Q> excelRowDataMaker,
                                                  ExcelWriteExtInfoVO excelWriteExtInfoVO);

    /**
     *
     * @param outputStream
     * @param excelExtName
     * @param query
     * @param excelHeaderInfoVO
     * @param excelRowDataMaker
     * @param excelWriteExtInfoVO
     * @return
     */
    <Q extends BasicPageQuery>
    HandleResult<ExcelWriteResult> createAndWriteExcelData(OutputStream outputStream, String excelExtName,
                                                           Q query, ExcelHeaderInfoVO excelHeaderInfoVO,
                                                           ExcelRowDataMaker<Q> excelRowDataMaker,
                                                           ExcelWriteExtInfoVO excelWriteExtInfoVO);

    /**
     *
     * @param outputStream
     * @param sheet
     * @param excelHeaderInfoVO
     * @param excelWriteExtInfoVO
     * @return
     */
    @MethodInteceptAnno(managerName= IntecepterManagerNames.ExcelFileWriter_WriteHeaderRowData,
            argsNamesHolderClass = "com.xqkj.methodargs.ExcelFileWriter_writeHeaderRowData")
    HandleResult<ExcelWriteResult> writeHeaderRowData(OutputStream outputStream,
                                                      Sheet sheet, ExcelHeaderInfoVO excelHeaderInfoVO,
                                                      ExcelWriteExtInfoVO excelWriteExtInfoVO);


    /**
     *
     * @param outputStream
     * @param sheet
     * @param excelBodyDateListInfoVO
     * @param excelWriteExtInfoVO
     * @return
     */
    @MethodInteceptAnno(managerName= IntecepterManagerNames.ExcelFileWriter_WriteBodyRowListData,
            argsNamesHolderClass = "com.xqkj.methodargs.ExcelFileWriter_writeBodyRowListData")
    HandleResult<ExcelWriteResult> writeBodyRowListData(OutputStream outputStream,
                                                        Sheet sheet, ExcelBodyDateListInfoVO excelBodyDateListInfoVO,
                                                        ExcelWriteExtInfoVO excelWriteExtInfoVO);

    /**
     *
     * @param outputStream
     * @param row
     * @param excelRowInfoVO
     * @param excelWriteExtInfoVO
     * @return
     */
    @MethodInteceptAnno(managerName= IntecepterManagerNames.ExcelFileWriter_WriteBodyRowData,
            argsNamesHolderClass = "com.xqkj.methodargs.ExcelFileWriter_writeBodyRowDate")
    HandleResult<ExcelWriteResult> writeBodyRowDate(OutputStream outputStream,
                                                    Row row, ExcelRowInfoVO excelRowInfoVO,
                                                    ExcelWriteExtInfoVO excelWriteExtInfoVO);

    /**
     *
     * @param outputStream
     * @param cell
     * @param excelRowCellInfoVO
     * @param excelWriteExtInfoVO
     * @return
     */
    @MethodInteceptAnno(managerName= IntecepterManagerNames.ExcelFileWriter_WriteRowCellData,
            argsNamesHolderClass = "com.xqkj.methodargs.ExcelFileWriter_writeRowCellData")
    HandleResult<ExcelWriteResult> writeRowCellData(OutputStream outputStream,
                                                    Cell cell, ExcelRowCellInfoVO excelRowCellInfoVO,
                                                    ExcelWriteExtInfoVO excelWriteExtInfoVO);


    void setComponentHolder(ComponentHolder componentHolder);
}
