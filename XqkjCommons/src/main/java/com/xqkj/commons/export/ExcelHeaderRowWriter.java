package com.xqkj.commons.export;

import com.xqkj.commons.export.model.ExcelHeaderInfoVO;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.export.model.ExcelWriteResult;
import com.xqkj.commons.model.HandleResult;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.OutputStream;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/29 3:37 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public interface ExcelHeaderRowWriter {

    HandleResult<ExcelWriteResult> doWriteHeaderRowDate(OutputStream outputStream,
                                                        Sheet sheet, ExcelHeaderInfoVO excelHeaderInfoVO,
                                                        ExcelWriteExtInfoVO excelWriteExtInfoVO);
}
