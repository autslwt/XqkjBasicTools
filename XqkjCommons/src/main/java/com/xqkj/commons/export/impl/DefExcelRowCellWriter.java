package com.xqkj.commons.export.impl;

import com.xqkj.commons.export.ExcelRowCellWriter;
import com.xqkj.commons.export.model.ExcelRowCellInfoVO;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.export.model.ExcelWriteResult;
import com.xqkj.commons.model.HandleResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/29 4:32 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class DefExcelRowCellWriter implements ExcelRowCellWriter {

    @Override
    public HandleResult<ExcelWriteResult> doWriteRowCellDate(OutputStream outputStream,
                                                                Cell cell,
                                                                ExcelRowCellInfoVO excelRowCellInfoVO,
                                                                ExcelWriteExtInfoVO excelWriteExtInfoVO) {
        ExcelWriteResult excelWriteResult = new ExcelWriteResult();
        Workbook workbook = cell.getSheet().getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat dateFormat = workbook.createDataFormat();
        String format = excelRowCellInfoVO.getValueFormat();
        Object objVal = excelRowCellInfoVO.getValue();
        if (objVal instanceof String) {// 字符串
            String cellVal = (String) objVal;
            cell.setCellValue(cellVal);
        } else if (isInteger(objVal)) {// 整数
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(Long.parseLong(objVal + ""));
        } else if (isDecimal(objVal)) {// 小数
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(Double.parseDouble(objVal + ""));
        } else if (objVal instanceof BigInteger) {// 整数
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(((BigInteger) objVal).intValue());
        } else if (objVal instanceof BigDecimal) {// 小数
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(((BigDecimal) objVal).doubleValue());
        } else if (objVal instanceof Date) {// 时间
            format = (format == null || format.trim().length() == 0) ? "yyyy-MM-dd HH:mm:ss" : format;
            cellStyle.setDataFormat(dateFormat.getFormat(format));
            cell.setCellStyle(cellStyle);
            cell.setCellValue((Date) objVal);
        } else {// 其他
            String cellVal = String.valueOf(objVal == null ? "" : objVal);
            cell.setCellValue(cellVal);
        }
        return HandleResult.success(excelWriteResult);
    }

    private boolean isInteger(Object objVal) {

        if (objVal instanceof Long || objVal instanceof Integer || objVal instanceof Short) {
            return true;
        }
        return false;
    }

    private boolean isDecimal(Object objVal) {

        if (objVal instanceof Double || objVal instanceof Float) {
            return true;
        }
        return false;
    }
}
