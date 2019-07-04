package com.xqkj.commons.export.intecepter;

import com.xqkj.commons.constant.EventTypeContants;
import com.xqkj.commons.constant.IntecepterManagerNames;
import com.xqkj.commons.event.EventManager;
import com.xqkj.commons.event.WriteDateToExcelEvent;
import com.xqkj.commons.export.model.ExcelHeaderInfoVO;
import com.xqkj.commons.export.model.ExcelWriteExtInfoVO;
import com.xqkj.commons.intecepter.impl.BasicMethodWithCatchExceptionInteCeptorImpl;
import com.xqkj.commons.model.BasicPageQuery;
import com.xqkj.commons.model.InteceptReturnVO;
import com.xqkj.commons.model.IntecepterArgsVO;
import com.xqkj.commons.model.ObjectHolder;
import com.xqkj.commons.utils.Utils;
import com.xqkj.methodargs.ExcelFileWriter_writeExcelData;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.OutputStream;
import java.util.Map;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/7/1 11:09 AM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ExcelFileWriterWriteExcelDataIntecepter extends BasicMethodWithCatchExceptionInteCeptorImpl {

    @Override
    public void afterException(ObjectHolder<Exception> exHolder, IntecepterArgsVO intecepterArgs){
        WriteDateToExcelEvent exceptionEvent=convertToEvent(intecepterArgs,EventTypeContants.TYPE_RUN_EXCEPTION);
        if(exceptionEvent!=null){
            EventManager.publicEvent(exceptionEvent);
        }

    }

    @Override
    public InteceptReturnVO preHandle(IntecepterArgsVO intecepterArgs) {
        WriteDateToExcelEvent beginEvent = convertToEvent(intecepterArgs,EventTypeContants.TYPE_BEFORE_RUN);
        if(beginEvent!=null){
            EventManager.publicEvent(beginEvent);
        }
        return null;
    }

    @Override
    public InteceptReturnVO afterHandle(Object retValue, IntecepterArgsVO intecepterArgs) {
        WriteDateToExcelEvent afterEvent = convertToEvent(intecepterArgs,EventTypeContants.TYPE_AFTER_RUN);
        if(afterEvent!=null){
            EventManager.publicEvent(afterEvent);
        }
        return null;
    }

    private WriteDateToExcelEvent convertToEvent(IntecepterArgsVO intecepterArgs, int eventType){
        return convertToEvent(intecepterArgs,eventType,null);
    }

    private WriteDateToExcelEvent convertToEvent(IntecepterArgsVO intecepterArgs,int eventType,Object retVal){
        Map<String,Object> argsMap = intecepterArgs.getArgMap();
        if(argsMap==null || argsMap.isEmpty()){
            return null;
        }
        OutputStream outputStream= Utils.getAndCastMapValue(argsMap, ExcelFileWriter_writeExcelData.outputStream);
        ExcelWriteExtInfoVO excelWriteExtInfoVO=Utils.getAndCastMapValue(argsMap,ExcelFileWriter_writeExcelData.excelWriteExtInfoVO);
        Workbook workbook=Utils.getAndCastMapValue(argsMap,ExcelFileWriter_writeExcelData.workbook);
        String sheetName=Utils.getAndCastMapValue(argsMap,ExcelFileWriter_writeExcelData.sheetName);
        BasicPageQuery query=Utils.getAndCastMapValue(argsMap,ExcelFileWriter_writeExcelData.query);
        ExcelHeaderInfoVO excelHeaderInfoVO=Utils.getAndCastMapValue(argsMap,ExcelFileWriter_writeExcelData.excelHeaderInfoVO);
        //
        WriteDateToExcelEvent writeDateToExcelEvent = new WriteDateToExcelEvent(intecepterArgs.getTarget(),
                eventType, outputStream, excelWriteExtInfoVO, workbook,
                sheetName, query, excelHeaderInfoVO,retVal);
        return writeDateToExcelEvent;
    }

    @Override
    public String getManagerName(){
        return IntecepterManagerNames.ExcelFileWriter_WriteExcelData;
    }
}
