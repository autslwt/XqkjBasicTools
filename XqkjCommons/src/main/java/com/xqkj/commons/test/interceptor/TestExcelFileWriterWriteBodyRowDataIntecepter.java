package com.xqkj.commons.test.interceptor;

import com.xqkj.commons.constant.IntecepterManagerNames;
import com.xqkj.commons.export.model.ExcelRowCellInfoVO;
import com.xqkj.commons.export.model.ExcelRowInfoVO;
import com.xqkj.commons.intecepter.impl.BasicMethodIntecepterImpl;
import com.xqkj.commons.model.InteceptReturnVO;
import com.xqkj.commons.model.IntecepterArgsVO;
import com.xqkj.methodargs.ExcelFileWriter_writeBodyRowDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/4 10:00 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class TestExcelFileWriterWriteBodyRowDataIntecepter extends BasicMethodIntecepterImpl {
    @Override
    public String getManagerName() {
        return IntecepterManagerNames.ExcelFileWriter_WriteBodyRowData;
    }

    @Override
    public InteceptReturnVO preHandle(IntecepterArgsVO intecepterArgs) {
        Map<String, Object> objectMap = intecepterArgs.getArgMap();
        if (objectMap != null && !objectMap.isEmpty()
                && objectMap.get(ExcelFileWriter_writeBodyRowDate.excelRowInfoVO) != null) {
            ExcelRowInfoVO excelRowInfoVO = (ExcelRowInfoVO) objectMap.get(ExcelFileWriter_writeBodyRowDate.excelRowInfoVO);
            List<ExcelRowCellInfoVO> cellInfoVOList = excelRowInfoVO.getCellInfoVOList();
            List<ExcelRowCellInfoVO> newList = new ArrayList<>();
            excelRowInfoVO.setCellInfoVOList(newList);
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
