package com.xqkj.commons.test.interceptor;

import com.xqkj.commons.intecepter.ProxyMethodIntecepter;
import com.xqkj.commons.model.InteceptReturnVO;
import com.xqkj.commons.model.IntecepterArgsVO;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/11 6:12 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class TestWriteBodyRowDateIntecepter implements ProxyMethodIntecepter {

    @Override
    public InteceptReturnVO preHandle(IntecepterArgsVO intecepterArgs) {
        //
//        ExcelFileWriterIntecepterAnnoInfo.WriteBodyRowData param=(ExcelFileWriterIntecepterAnnoInfo.WriteBodyRowData)intecepterArgs.getParamObj();
//        System.out.println(JSON.toJSONString(param.excelRowInfoVO));
        System.out.println("TestWriteBodyRowDateIntecepter preHandle");
        return null;
    }

    @Override
    public InteceptReturnVO afterHandle(Object retValue, IntecepterArgsVO intecepterArgs) {
        //
//        ExcelFileWriterIntecepterAnnoInfo.WriteBodyRowData param=(ExcelFileWriterIntecepterAnnoInfo.WriteBodyRowData)intecepterArgs.getParamObj();
//        System.out.println(JSON.toJSONString(param.excelWriteExtInfoVO));
        System.out.println("TestWriteBodyRowDateIntecepter afterHandle");
        return null;
    }
}
