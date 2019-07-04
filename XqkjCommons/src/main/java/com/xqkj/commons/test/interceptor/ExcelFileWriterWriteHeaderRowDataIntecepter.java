package com.xqkj.commons.test.interceptor;

import com.xqkj.commons.export.model.ExcelHeaderInfoVO;
import com.xqkj.commons.export.model.ExcelRowCellInfoVO;
import com.xqkj.commons.intecepter.ProxyMethodIntecepter;
import com.xqkj.commons.model.InteceptReturnVO;
import com.xqkj.commons.model.IntecepterArgsVO;
import com.xqkj.methodargs.ExcelFileWriter_writeBodyRowDate;
import com.xqkj.methodargs.ExcelFileWriter_writeHeaderRowData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/4 10:03 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ExcelFileWriterWriteHeaderRowDataIntecepter implements ProxyMethodIntecepter{
    @Override
    public InteceptReturnVO preHandle(IntecepterArgsVO intecepterArgs) {
        Map<String, Object> objectMap = intecepterArgs.getArgMap();
        if (objectMap != null && !objectMap.isEmpty()
                && objectMap.get(ExcelFileWriter_writeHeaderRowData.excelHeaderInfoVO) != null) {
            ExcelHeaderInfoVO excelHeaderInfoVO= (ExcelHeaderInfoVO)objectMap.get(ExcelFileWriter_writeHeaderRowData.excelHeaderInfoVO);
            List<ExcelRowCellInfoVO> cellInfoVOList = excelHeaderInfoVO.getHeaderRowInfo().getCellInfoVOList();
            List<ExcelRowCellInfoVO> newList = new ArrayList<>();
            excelHeaderInfoVO.getHeaderRowInfo().setCellInfoVOList(newList);
            cellInfoVOList.forEach(item -> {
                if (!"testForCode".equals(item.getCellCode())) {
                    newList.add(item);
                }
            });
        }
        return null;
    }

    @Override
    public InteceptReturnVO afterHandle(Object retValue, IntecepterArgsVO intecepterArgs) {
        return null;
    }
}
