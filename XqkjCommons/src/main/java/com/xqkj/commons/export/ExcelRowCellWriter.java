package com.xqkj.commons.export;

import com.xqkj.commons.export.model.ExcelRowCellInfoVO;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.export.model.ExcelWriteResult;
import com.xqkj.commons.model.HandleResult;
import org.apache.poi.ss.usermodel.Cell;

import java.io.OutputStream;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/29 3:36 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public interface ExcelRowCellWriter {

    HandleResult<ExcelWriteResult> doWriteRowCellDate(OutputStream outputStream,
                                                      Cell cell,
                                                      ExcelRowCellInfoVO excelRowCellInfoVO,
                                                      ExcelWriteExtInfoVO excelWriteExtInfoVO);
}
