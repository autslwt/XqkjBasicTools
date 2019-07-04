package com.xqkj.commons.export.impl;

import com.xqkj.commons.export.ExcelHeaderRowWriter;
import com.xqkj.commons.export.model.*;
import com.xqkj.commons.model.HandleResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.OutputStream;
import java.util.List;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/29 4:30 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class DefExcelHeaderRowWriter implements ExcelHeaderRowWriter {

    @Override
    public HandleResult<ExcelWriteResult> doWriteHeaderRowDate(OutputStream outputStream,
                                                                  Sheet sheet, ExcelHeaderInfoVO excelHeaderInfoVO,
                                                                  ExcelWriteExtInfoVO excelWriteExtInfoVO) {
        ExcelWriteResult excelWriteResult = new ExcelWriteResult();
        int rowIndex = 0;
        if (excelHeaderInfoVO != null) {
            ExcelRowInfoVO titleRowInfo = excelHeaderInfoVO.getTitleRowInfo();
            rowIndex = writeTitleOrHeaderRow(titleRowInfo, sheet, rowIndex);
            //
            ExcelRowInfoVO headerRowInfo = excelHeaderInfoVO.getHeaderRowInfo();
            headerRowInfo.setCurrentRowIndex(rowIndex);
            rowIndex = writeTitleOrHeaderRow(headerRowInfo, sheet, rowIndex);
        }
        excelWriteResult.setNextRowIndex(rowIndex);
        return HandleResult.success(excelWriteResult);
    }

    private int writeTitleOrHeaderRow(ExcelRowInfoVO rowInfoVO, Sheet sheet, int rowIndex) {
        if (rowInfoVO != null && CollectionUtils.isNotEmpty(rowInfoVO.getCellInfoVOList())) {
            rowIndex = rowInfoVO.getCurrentRowIndex();
            Row titleRow = sheet.createRow(rowIndex++);
            List<ExcelRowCellInfoVO> titleCellDateList = rowInfoVO.getCellInfoVOList();
            int titleRowCellIndex = 0;
            for (ExcelRowCellInfoVO rowCellInfoVO : titleCellDateList) {
                Cell cell = titleRow.createCell(titleRowCellIndex++);
                cell.setCellValue(String.valueOf(rowCellInfoVO.getValue()));
            }
            //
            List<CellRangeAddress> mergeInfoVOList = rowInfoVO.getMergeInfoVOList();
            if (CollectionUtils.isNotEmpty(mergeInfoVOList)) {
                mergeInfoVOList.forEach(excelMergeInfoVO -> sheet.addMergedRegion(excelMergeInfoVO));
            }
        }
        return rowIndex;
    }

}
