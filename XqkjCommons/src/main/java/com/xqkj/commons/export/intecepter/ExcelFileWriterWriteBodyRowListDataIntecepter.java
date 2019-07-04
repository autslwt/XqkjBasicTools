package com.xqkj.commons.export.intecepter;

import com.xqkj.commons.constant.EventTypeContants;
import com.xqkj.commons.constant.IntecepterManagerNames;
import com.xqkj.commons.event.EventManager;
import com.xqkj.commons.event.WriteListDateToSheetEvent;
import com.xqkj.commons.export.model.ExcelBodyDateListInfoVO;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.intecepter.impl.BasicMethodIntecepterImpl;
import com.xqkj.commons.model.InteceptReturnVO;
import com.xqkj.commons.model.IntecepterArgsVO;
import com.xqkj.commons.utils.Utils;
import com.xqkj.methodargs.ExcelFileWriter_writeBodyRowListData;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.OutputStream;
import java.util.Map;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/1 2:03 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ExcelFileWriterWriteBodyRowListDataIntecepter extends BasicMethodIntecepterImpl {

    @Override
    public InteceptReturnVO preHandle(IntecepterArgsVO intecepterArgs) {
        WriteListDateToSheetEvent beginEvent = convertToEvent(intecepterArgs,EventTypeContants.TYPE_BEFORE_RUN);
        if(beginEvent!=null){
            EventManager.publicEvent(beginEvent);
        }
        return null;
    }

    @Override
    public InteceptReturnVO afterHandle(Object retValue, IntecepterArgsVO intecepterArgs) {
        WriteListDateToSheetEvent afterEvent = convertToEvent(intecepterArgs,EventTypeContants.TYPE_AFTER_RUN,retValue);
        if(afterEvent!=null){
            EventManager.publicEvent(afterEvent);
        }
        return null;
    }

    private WriteListDateToSheetEvent convertToEvent(IntecepterArgsVO intecepterArgs, int eventType){
        return convertToEvent(intecepterArgs,eventType,null);
    }

    private WriteListDateToSheetEvent convertToEvent(IntecepterArgsVO intecepterArgs,int eventType,Object retVal){
        Map<String,Object> argsMap = intecepterArgs.getArgMap();
        if(argsMap==null || argsMap.isEmpty()){
            return null;
        }
        OutputStream outputStream= Utils.getAndCastMapValue(argsMap, ExcelFileWriter_writeBodyRowListData.outputStream);
        ExcelWriteExtInfoVO excelWriteExtInfoVO=Utils.getAndCastMapValue(argsMap,ExcelFileWriter_writeBodyRowListData.excelWriteExtInfoVO);
        Sheet sheet=Utils.getAndCastMapValue(argsMap, ExcelFileWriter_writeBodyRowListData.sheet);
        ExcelBodyDateListInfoVO excelBodyDateListInfoVO=Utils.getAndCastMapValue(argsMap,ExcelFileWriter_writeBodyRowListData.excelBodyDateListInfoVO);
        //
        WriteListDateToSheetEvent writeEvent = new WriteListDateToSheetEvent(intecepterArgs.getTarget(),
                eventType, outputStream, excelWriteExtInfoVO,sheet,excelBodyDateListInfoVO,retVal);
        //
        return writeEvent;
    }

    @Override
    public String getManagerName(){
        return IntecepterManagerNames.ExcelFileWriter_WriteBodyRowListData;
    }
}
